package org.talend.components.neo4j.tneo4jbatchrelationships;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.components.neo4j.generic.GenericComponentDefinition;
import org.talend.components.neo4j.tneo4jconnection.TNeo4jConnectionProperties;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * Properties of component `tNeo4jBatchRelationships`.
 */
public class TNeo4jBatchRelationshipsProperties extends ComponentPropertiesImpl {

    /**
     * The reference to the connection component.
     */
    public ComponentReferenceProperties<TNeo4jConnectionProperties> referencedComponent =
            new ComponentReferenceProperties<>(
                    "referencedComponent",
                    GenericComponentDefinition.getComponentName(TNeo4jConnectionProperties.class)
            );

    /**
     * Schema column for the edge's type.
     */
    public Property<String> relathionshipTypeField;

    /**
     * Edge's direction.
     */
    public Property<String> relationshipDirection;

    /**
     * Name of the index for starting node.
     */
    public Property<String> startIndexName;

    /**
     *
     */
    public Property<String> startIndexField;

    /**
     *Name of the index for ending node.
     */
    public Property<String> endIndexName;

    /**
     *
     */
    public Property<String> endIndexField;

    /**
     * Shall we continue if an error occurred when creating a relationship (like the from or to node is not found) ?
     */
    public Property<Boolean> skipOnError = PropertyFactory.newBoolean("skipOnError", false);

    /**
     * Default constructor.
     *
     * @param name
     */
    public TNeo4jBatchRelationshipsProperties(String name) {
        super(name);
    }


}
