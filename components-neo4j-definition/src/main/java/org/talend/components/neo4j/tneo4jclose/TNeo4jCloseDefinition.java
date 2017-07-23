package org.talend.components.neo4j.tneo4jclose;

import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.neo4j.generic.GenericComponentDefinition;
import org.talend.daikon.properties.property.Property;

import java.util.EnumSet;
import java.util.Set;

/**
 * Definition of component `tNeo4jBatchClose`.
 *
 */
public class TNeo4jCloseDefinition extends GenericComponentDefinition {

    /**
     * Default constructor.
     */
    public TNeo4jCloseDefinition() {
        super(TNeo4jCloseDefinition.class);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Property[] getReturnProperties() {
        return new Property[] { RETURN_ERROR_MESSAGE_PROP };
    }

    @Override
    public boolean isStartable() {
        return false;
    }

    @Override
    public Set<ConnectorTopology> getSupportedConnectorTopologies() {
        return EnumSet.of(ConnectorTopology.NONE);
    }

}
