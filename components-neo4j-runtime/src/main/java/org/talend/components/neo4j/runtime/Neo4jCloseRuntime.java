package org.talend.components.neo4j.runtime;

import org.apache.avro.Schema;
import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.neo4j.runtime.connection.Connection;
import org.talend.components.neo4j.tneo4jclose.TNeo4jCloseProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;

import java.io.IOException;
import java.util.List;

/**
 * Runtime execution for component `tNeo4jClose`.
 * Just close the underlying Neo4j connection.
 */
public class Neo4jCloseRuntime implements SourceOrSink {

    /**
     * The reference to `tNeo4jConnection`.
     */
    public String refConnection;

    /**
     * Initialize the runtime object and checking if component's properties are well sets.
     */
    @Override
    public ValidationResult initialize(RuntimeContainer container, ComponentProperties properties) {
        TNeo4jCloseProperties props = (TNeo4jCloseProperties) properties;

        // checking props
        if(props.referencedComponent.componentInstanceId.getValue().isEmpty()){
            return new ValidationResult(ValidationResult.Result.ERROR, "Reference to connection component is required");
        }

        this.refConnection = props.referencedComponent.componentInstanceId.getValue();
        return ValidationResult.OK;
    }

    /**
     * Just try to close the underlying connection.
     */
    @Override
    public ValidationResult validate(RuntimeContainer runtime) {
        if(runtime != null) {
            Connection connection = (Connection) runtime.getComponentData(this.refConnection,Connection.SHARED_NAME);
            if(connection != null){
                connection.close();
            }
        }
        return ValidationResult.OK;
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
