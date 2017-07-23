package org.talend.components.neo4j.runtime.connection;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.index.lucene.unsafe.batchinsert.LuceneBatchInserterIndexProvider;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserterIndex;
import org.neo4j.unsafe.batchinsert.BatchInserterIndexProvider;
import org.neo4j.unsafe.batchinsert.BatchInserters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.neo4j.tneo4jconnection.TNeo4jConnectionProperties;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BatchConnection extends Connection {

    /**
     * Name of the field index for the importId.
     */
    private final static String BACTH_INDEX_ID_NAME = "importId";

    /**
     * Logger.
     */
    private transient static final Logger LOG = LoggerFactory.getLogger(BatchConnection.class);

    /**
     * Batch inserter
     */
    private BatchInserter inserter;

    /**
     * Batch inserter index provider
     */
    private BatchInserterIndexProvider indexProvider;

    /**
     * Store the list of index
     */
    protected Map<String, BatchInserterIndex> batchInserterIndexes;

    /**
     * Constructor.
     */
    public BatchConnection(TNeo4jConnectionProperties props) {
        super(props);

        try {
            // Initialize batch inserter
            this.inserter = BatchInserters.inserter(new File(props.connectionBatch.databasePath.getStringValue()));

            // Init
            this.indexProvider = new LuceneBatchInserterIndexProvider(inserter);
            this.batchInserterIndexes = new HashMap<>();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Getter for inserter
     *
     * @return The batch inserter
     */
    public BatchInserter getInserter() {
        return this.inserter;
    }

    /**
     * Create and store the index, if it doesn't exist.
     *
     * @param indexName      Name of the index
     * @param indexCacheSize Number of element into the cache size for the index
     */
    public void createBatchIndex(String indexName, Integer indexCacheSize) {
        if (!batchInserterIndexes.containsKey(indexName)) {
            BatchInserterIndex index = indexProvider.nodeIndex(indexName, MapUtil.stringMap("type", "exact"));
            if (indexCacheSize > 0) {
                index.setCacheCapacity(BACTH_INDEX_ID_NAME, indexCacheSize);
            }
            batchInserterIndexes.put(indexName, index);
        } else {
            LOG.warn(String.format("Index [%s] already exist", indexName));
        }
    }

    /**
     * Flush the specified index on the disk, so it will be available after for search.
     *
     * @param indexName Name of the index to flush
     */
    public void flushBatchIndex(String indexName) {
        if (batchInserterIndexes.containsKey(indexName)) {
            BatchInserterIndex index = batchInserterIndexes.get(indexName);
            index.flush();
        } else {
            LOG.warn(String.format("Flushing a non exist index ... [%s]t", indexName));
        }
    }

    /**
     * Push a node importId into the batch index.
     *
     * @param indexName     The batch index name
     * @param node          Neo4j identifier
     * @param importIdValue Import identifier
     */
    public void indexNodeInBatchIndex(String indexName, long node, Object importIdValue) {
        if (batchInserterIndexes.containsKey(indexName)) {
            BatchInserterIndex index = batchInserterIndexes.get(indexName);

            Map<String, Object> props = new HashMap<>();
            props.put(BACTH_INDEX_ID_NAME, importIdValue);
            index.add(node, props);
        } else {
            LOG.warn(String.format("Can't index node %s/%s into a none existent index [%s]", node, importIdValue, indexName));
        }
    }

    /**
     * Find a node in the index.
     *
     * @param indexName Name of the batch index
     * @param value     Value to search in index
     * @return Neo4j node identifier
     */
    public Long findNodeInBatchIndex(String indexName, Object value) {
        Long nodeId = null;
        if (this.batchInserterIndexes.containsKey(indexName)) {
            IndexHits<Long> result = this.batchInserterIndexes.get(indexName).get(BACTH_INDEX_ID_NAME, value);
            if (result.size() > 0) {
                nodeId = result.getSingle();
            }
        } else {
            LOG.warn(String.format("Can't find object [%s] into index %s", value, indexName));
        }
        return nodeId;
    }

    /**
     * Create schema.
     *
     * @param type     Type of schema to create (ie. constraint or index on node property)
     * @param label    Node label
     * @param property Node property
     */
    public void createSchema(String type, String label, String property) {
        switch (type) {
            case "NODE_PROPERTY_UNIQUE":
                this.inserter.createDeferredConstraint(Label.label(label)).assertPropertyIsUnique(property).create();
                break;
            case "NODE_PROPERTY_INDEX":
                this.inserter.createDeferredSchemaIndex(Label.label(label)).on(property).create();
                break;
            default:
                // default is unique constraint
                this.inserter.createDeferredConstraint(Label.label(label)).assertPropertyIsUnique(property).create();
                break;
        }
    }

    @Override
    public void close() {
        if (this.inserter != null) {
            this.inserter.shutdown();
        }
        this.inserter = null;

        if (this.indexProvider != null) {
            this.indexProvider.shutdown();
        }
        this.indexProvider = null;
        this.batchInserterIndexes = null;
    }
}
