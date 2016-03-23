package org.neo4j.talend;

import org.junit.Assert;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.util.List;

public class Neo4jBatchInserterRelationshiptest extends Neo4jBatchUnitTest {

    @Test
    public void insert_edge_should_succeed() throws Exception {
        Neo4jBatchInserterNode nodeInserter =  getNeo4jBatchInserterNode(false);

        // populate the db with nodes
        List<String> columns = DummyTalendPojo.getColumnList();
        for(int i=0; i < 100; i++) {
            DummyTalendPojo pojo = DummyTalendPojo.getDummyTalendPojo(i);
            nodeInserter.create(pojo, columns);
        }
        nodeInserter.finish();

        // create relationship
        Neo4jBatchInserterRelationship nodeRelationship =  getNeo4jBatchInserterRelationship();
        for(int i=0; i < 100; i++) {
            nodeRelationship.create(DummyTalendPojo.getDummyTalendPojo(i), DummyTalendPojo.getColumnList());
        }
        nodeRelationship.finish();

        // check the database data
        batchDb.shutdown();

        // Testing it with real graphdb
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        try (Transaction tx = graphDb.beginTx()) {
            String result = graphDb.execute("MATCH ()-[r:" + REL_TYPE + "]-() RETURN count(r) AS count").resultAsString();
            Assert.assertEquals("+-------+\n| count |\n+-------+\n| 100   |\n+-------+\n1 row\n", result);
        }
        graphDb.shutdown();
    }
}
