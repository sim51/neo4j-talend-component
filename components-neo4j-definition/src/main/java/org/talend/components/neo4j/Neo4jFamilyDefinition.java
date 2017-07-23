package org.talend.components.neo4j;

import aQute.bnd.annotation.component.Component;
import com.google.auto.service.AutoService;
import org.talend.components.api.AbstractComponentFamilyDefinition;
import org.talend.components.api.ComponentInstaller;
import org.talend.components.api.Constants;

/**
 * Install all of the definitions provided for the Neo4j family of components.
 */

@AutoService(ComponentInstaller.class)
@Component(name = Constants.COMPONENT_INSTALLER_PREFIX + Configuration.COMPONENTS_NAME, provide = ComponentInstaller.class)
public class Neo4jFamilyDefinition extends AbstractComponentFamilyDefinition implements ComponentInstaller {

    public Neo4jFamilyDefinition(){
        super(Configuration.getComponentsName(), Configuration.getComponentsDefinition());
    }

    @Override
    public void install(ComponentFrameworkContext ctx) {
        ctx.registerComponentFamilyDefinition(this);
    }


}
