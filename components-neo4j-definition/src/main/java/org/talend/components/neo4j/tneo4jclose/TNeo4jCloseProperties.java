package org.talend.components.neo4j.tneo4jclose;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.components.neo4j.generic.GenericComponentDefinition;
import org.talend.components.neo4j.tneo4jconnection.TNeo4jConnectionProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

import static org.talend.daikon.properties.presentation.Widget.widget;

/**
 * Properties of component `tNeo4jBatchClose`.
 */
public class TNeo4jCloseProperties extends ComponentPropertiesImpl {

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
     */
    public TNeo4jCloseProperties(String name) {
        super(name);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();

        Form mainForm = new Form(this,Form.MAIN);
        mainForm.addRow(widget(referencedComponent).setWidgetType(Widget.COMPONENT_REFERENCE_WIDGET_TYPE));
    }


}
