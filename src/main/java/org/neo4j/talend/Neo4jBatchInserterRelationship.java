package org.neo4j.talend;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Neo4jBatchInserterRelationship extends Neo4jBatchInserterAbstract {

    private static Logger log = Logger.getLogger(Neo4jBatchInserterRelationship.class);

    private String relationshipTypeField;
    private String direction;
    private String startIndexName;
    private String startIndexField;
    private String endIndexName;
    private String endIndexField;

    /**
     * Constructor.
     */
    public Neo4jBatchInserterRelationship(Neo4jBatchDatabase graphDb, String relationshipTypeField, String direction, String startIndexName, String startIndexField, String endIndexName, String endIndexField) throws IOException {
        super(graphDb);

        if (StringUtils.isEmpty(relationshipTypeField)) {
            throw new RuntimeException("relationshipTypeField must be defined !!!");
        }
        this.relationshipTypeField = relationshipTypeField;

        if (StringUtils.isEmpty(direction)) {
            throw new RuntimeException("direction field must be defined !!!");
        }
        this.direction = direction;

        if (StringUtils.isEmpty(startIndexName)) {
            throw new RuntimeException("startIndexName field must be defined !!!");
        }
        this.startIndexName = startIndexName;

        if (StringUtils.isEmpty(startIndexField)) {
            throw new RuntimeException("startIndexField field must be defined !!!");
        }
        this.startIndexField = startIndexField;

        if (StringUtils.isEmpty(endIndexName)) {
            throw new RuntimeException("endIndexKey field must be defined !!!");
        }
        this.endIndexName = endIndexName;

        if (StringUtils.isEmpty(endIndexField)) {
            throw new RuntimeException("endIndexField field must be defined !!!");
        }
        this.endIndexField = endIndexField;

        this.batchDb = graphDb;

        // init the inserter
        this.batchDb.getInserter();
    }

    /**
     * Create the relationship.
     *
     * @param incoming Talend incoming object
     * @param columnList Attribute list of Talend object
     */
    public void create(Object incoming, List<String> columnList) {
        try {
            long startNode = this.batchDb.findNodeInBatchIndex(startIndexName, this.getObjectProperty(incoming, startIndexField));
            long endNode = this.batchDb.findNodeInBatchIndex(endIndexName, this.getObjectProperty(incoming, endIndexField));
            Map<String, Object> properties = constructMapFromObject(incoming, columnList);

            String type = (String) this.getObjectProperty(incoming, relationshipTypeField);
            if(StringUtils.isNotEmpty(type)) {

                properties.remove(relationshipTypeField);

                if (direction.endsWith("OUTGOING")) {
                    this.batchDb.getInserter().createRelationship(startNode, endNode, DynamicRelationshipType.withName(type), properties);
                } else {
                    this.batchDb.getInserter().createRelationship(endNode, startNode, DynamicRelationshipType.withName(type), properties);
                }
            }
            else {
                log.trace("Ignoring ioncoming object {" + properties.toString() + "} due to none relation type");
            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finish the process.
     */
    public void finish() {
    }

}
