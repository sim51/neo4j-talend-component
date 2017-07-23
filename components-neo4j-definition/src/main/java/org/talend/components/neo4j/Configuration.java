package org.talend.components.neo4j;

import org.talend.daikon.definition.Definition;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class Configuration {

    protected static final String COMPONENTS_NAME = "Neo4j_3.X";
    private static final   String CONFIG_PATH     = "META-INF/org.talend.components/" + COMPONENTS_NAME + "/configuration.properties";

    private static final Properties props;

    static {
        ClassLoader loader = Neo4jFamilyDefinition.class.getClassLoader();
        props = new Properties();
        try (InputStream resourceStream = loader.getResourceAsStream(CONFIG_PATH)) {
            props.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException("Error while bootstrapping runtime properties", e);
        }
    }

    public static String getProjectGroupId() {
        return props.getProperty("project.groupId");
    }

    public static String getProjectArtifactId() {
        return props.getProperty("project.artifactId");
    }

    public static String getProjectVersion() {
        return props.getProperty("project.version");
    }

    public static String getProjectName() {
        return props.getProperty("project.name");
    }

    public static String[] getComponentsFamily() {
        return props.getProperty("talend.family").split(";");
    }

    public static String getComponentsName() {
        return COMPONENTS_NAME;
    }

    public static Definition[] getComponentsDefinition() {
        List<Definition> result = new ArrayList<>();

        try {
            Set<Class> defsClazz = getComponentClasses();
            for(Class clazz : defsClazz) {
                result.add((Definition) clazz.newInstance());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to find the list of component definition");
        }
        return result.toArray(new Definition[result.size()]);
    }

    /**
     * Scans all classes accessible from the context class loader which belong
     * to the given package and subpackages.
     *
     * @return The classes
     */
    private static Set<Class> getComponentClasses() throws IOException {
        Set<Class> classes = new HashSet<>();

        Enumeration<URL> resources = Configuration.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
        while (resources.hasMoreElements()) {
            try {
                Manifest manifest = new Manifest(resources.nextElement().openStream());
                Attributes attributes = manifest.getMainAttributes();
                if(attributes.getValue("Bundle-Name").equals(getProjectName())) {
                    for(String clazz : attributes.getValue("TalendDefs").replaceAll("\"", "").split(",")) {
                        classes.add( Class.forName(clazz));
                    }
                }
            } catch (Exception E) {
                throw new RuntimeException("Can't read manifest ...");
            }
        }

        return classes;
    }

}
