package org.talend.components.neo4j.tneo4jbatchschema;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.components.neo4j.generic.GenericComponentDefinition;
import org.talend.components.neo4j.tneo4jconnection.TNeo4jConnectionProperties;

/**
 * Properties of component `tNeo4jBatchSchema`.
 */
public class TNeo4jBatchSchemaProperties extends ComponentPropertiesImpl {

    /**
     * The reference to the connection component.
     */
    public ComponentReferenceProperties<TNeo4jConnectionProperties> referencedComponent =
            new ComponentReferenceProperties<>(
                    "referencedComponent",
                    GenericComponentDefinition.getComponentName(TNeo4jConnectionProperties.class)
            );

    /**
     * Default constructor.
     * @param name
     */
    public TNeo4jBatchSchemaProperties(String name) {
        super(name);
    }

}
