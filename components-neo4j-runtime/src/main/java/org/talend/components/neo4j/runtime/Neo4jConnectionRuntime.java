package org.talend.components.neo4j.runtime;

import org.apache.avro.Schema;
import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.neo4j.runtime.connection.BatchConnection;
import org.talend.components.neo4j.runtime.connection.Connection;
import org.talend.components.neo4j.runtime.connection.DriverConnection;
import org.talend.components.neo4j.tneo4jconnection.TNeo4jConnectionProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;

import java.io.IOException;
import java.util.List;

/**
 * Runtime execution for component `tNeo4jConnection`.
 * Just close the underlying Neo4j connection.
 */
public class Neo4jConnectionRuntime implements SourceOrSink {

    public TNeo4jConnectionProperties properties;

    /**
     * Apply configuration.
     */
    @Override
    public ValidationResult initialize(RuntimeContainer container, ComponentProperties properties) {
        this.properties = (TNeo4jConnectionProperties) properties;
        return ValidationResult.OK;
    }

    /**
     * Check before to use.
     */
    @Override
    public ValidationResult validate(RuntimeContainer runtime) {
        // init result
        ValidationResultMutable vr = null;

        Connection connection = null;
        if(this.properties.useRemoteMode.getValue()) {
            connection = new DriverConnection(this.properties);
        }
        else {
            connection = new BatchConnection(this.properties);
        }

        if (runtime != null) {
            runtime.setComponentData(runtime.getCurrentComponentId(), Connection.SHARED_NAME, connection);
        }
        return vr;
    }

    public Connection createConnection(RuntimeContainer runtime){
        return null;
    }

    @Override
    public List<NamedThing> getSchemaNames(RuntimeContainer container) throws IOException {
        return null;
    }

    @Override
    public Schema getEndpointSchema(RuntimeContainer container, String schemaName) throws IOException {
        return null;
    }


}
