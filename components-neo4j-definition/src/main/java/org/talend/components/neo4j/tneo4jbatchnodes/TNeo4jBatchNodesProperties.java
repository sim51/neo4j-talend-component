package org.talend.components.neo4j.tneo4jbatchnodes;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.components.neo4j.generic.GenericComponentDefinition;
import org.talend.components.neo4j.tneo4jconnection.TNeo4jConnectionProperties;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * Properties of component `tNeo4jBatchNodes`.
 */
public class TNeo4jBatchNodesProperties extends ComponentPropertiesImpl {

    /**
     * The reference to the connection component.
     */
    public ComponentReferenceProperties<TNeo4jConnectionProperties> referencedComponent =
            new ComponentReferenceProperties<>(
                    "referencedComponent",
                    GenericComponentDefinition.getComponentName(TNeo4jConnectionProperties.class)
            );

    /**
     * Schema column for the node's labels (separated by a ';').
     */
    public Property<String> labelField;

    /**
     * Schema column for the import id.
     */
    public Property<String> importIdField;

    /**
     * Name of the index to store (to be reused into relationship component).
     */
    public Property<String> importIndexName;

    /**
     * If true, when we create a node we will NOT save the field with its value.
     * It is useful to put it at true if this field is just a technical one for the import.
     */
    public Property<Boolean> excludeImportIdField;

    /**
     * Size of the import cache index.
     */
    public Property<Integer> importIndexCacheSize = PropertyFactory.newInteger("importIndexCacheSize", 0);

    /**
     * Default constructor.
     *
     * @param name
     */
    public TNeo4jBatchNodesProperties(String name) {
        super(name);
    }


}
