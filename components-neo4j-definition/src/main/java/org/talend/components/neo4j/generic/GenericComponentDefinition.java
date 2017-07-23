package org.talend.components.neo4j.generic;

import org.talend.components.api.component.AbstractComponentDefinition;
import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.api.component.runtime.DependenciesReader;
import org.talend.components.api.component.runtime.ExecutionEngine;
import org.talend.components.api.component.runtime.SimpleRuntimeInfo;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.neo4j.Configuration;
import org.talend.components.neo4j.Neo4jFamilyDefinition;
import org.talend.daikon.runtime.RuntimeInfo;

/**
 * Generic class that implement common methods.
 */
public abstract class GenericComponentDefinition extends AbstractComponentDefinition {

    /**
     * Compute the component name by the class name
     * @param clazz
     * @return
     */
    public static String getComponentName(Class clazz) {
        return  "t" + clazz.getSimpleName().substring(1).replace("Definition", "");
    }

    /**
     * Default constructor.
     *
     * @param clazz
     */
    public GenericComponentDefinition(Class clazz) {
        super(getComponentName(clazz),  ExecutionEngine.DI );
    }

    /**
     * Compute and search the property class, based on the name of definition class name.
     * Just do a replace of `Definition` by `Properties` on the class name.
     *
     * @return The property class of the component.
     */
    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        try {
            String className = this.getClass().getName().replace("Definition", "Properties");
            return (Class<? extends ComponentProperties>) this.getClass().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Property class `%s` not found"));
        }
    }

    /**
     * Read the component family from the property file.
     *
     * @return List of component's family
     */
    @Override
    public String[] getFamilies() {
        return Configuration.getComponentsFamily();
    }

    /**
     * Compute the runtime class name by this class name..
     *
     * @return List of component's family
     */
    @Override
    public RuntimeInfo getRuntimeInfo(ExecutionEngine engine, ComponentProperties properties, ConnectorTopology connectorTopology) {
        assertEngineCompatibility(engine);
        if(this.getSupportedConnectorTopologies().contains(connectorTopology)) {
            String runtimeClass = this.getClass().getName().replace("Definition", "Runtime");
            return new SimpleRuntimeInfo(
                    this.getClass().getClassLoader(),
                    DependenciesReader.computeDependenciesFilePath(
                            Configuration.getProjectGroupId(),
                            Configuration.getProjectArtifactId()
                    ),
                    runtimeClass
            );
        }
        return null;
    }

}
