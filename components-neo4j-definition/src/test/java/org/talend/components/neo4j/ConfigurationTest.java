package org.talend.components.neo4j;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.talend.daikon.definition.Definition;

public class ConfigurationTest {

    @Test
    public void getComponentsDFamily_should_work(){
        String[] families = Configuration.getComponentsFamily();
        Assert.assertTrue(families.length == 2);
    }
}
