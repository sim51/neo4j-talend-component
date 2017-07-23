package org.talend.components.neo4j.tneo4jbatchschema;

import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.neo4j.generic.GenericComponentDefinition;
import org.talend.daikon.properties.property.Property;

import java.util.EnumSet;
import java.util.Set;

/**
 * Definition of component `tNeo4jBatchSchema`.
 */
public class TNeo4jBatchSchemaDefinition extends GenericComponentDefinition {

    /**
     * Default constructor.
     */
    public TNeo4jBatchSchemaDefinition() {
        super(TNeo4jBatchSchemaDefinition.class);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Property[] getReturnProperties() {
        return new Property[] { RETURN_ERROR_MESSAGE_PROP };
    }

    @Override
    public boolean isStartable() {
        return true;
    }

    @Override
    public Set<ConnectorTopology> getSupportedConnectorTopologies() {
        return EnumSet.of(ConnectorTopology.NONE);
    }


}
