package org.talend.components.neo4j.tneo4jconnection;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.neo4j.tneo4jconnection.properties.Neo4jBatchConnectionProperties;
import org.talend.components.neo4j.tneo4jconnection.properties.Neo4jRemoteConnectionProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * Properties of component `tNeo4jBatchConnection`.
 */
public class TNeo4jConnectionProperties extends ComponentPropertiesImpl {

    /**
     * Remote or batch mode ?
     */
    public Property<Boolean> useRemoteMode = PropertyFactory.newProperty(Boolean.class, "useRemoteMode").setValue(false);

    /**
     * Batch configuration.
     */
    public Neo4jBatchConnectionProperties connectionBatch = new Neo4jBatchConnectionProperties("connectionBatch");

    /**
     * Remote configuration.
     */
    public Neo4jRemoteConnectionProperties connectionRemote = new Neo4jRemoteConnectionProperties("connectionRemote");

    /**
     * Default constructor.
     */
    public TNeo4jConnectionProperties(String name) {
        super(name);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();

        Form mainForm = new Form(this,Form.MAIN);
        mainForm.addRow(useRemoteMode);
        mainForm.addRow(connectionBatch.getForm(Form.MAIN));
        mainForm.addRow(connectionRemote.getForm(Form.MAIN));
    }

    @Override
    public void refreshLayout(Form form) {
        super.refreshLayout(form);

        if (Form.MAIN.equals(form.getName())) {
            // toggle forms
            form.getWidget(connectionBatch.getName()).setHidden(!useRemoteMode.getValue());
            form.getWidget(connectionRemote.getName()).setHidden(useRemoteMode.getValue());
        }
    }

    /**
     * On useRemoteMode change, refresh the main form.
     */
    public void afterUseRemoteMode() {
        refreshLayout(getForm(Form.MAIN));
    }
}
