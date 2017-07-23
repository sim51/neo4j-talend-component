/**
 * Copyright (c) 2016 LARUS Business Automation [http://www.larus-ba.it]
 * <p>
 * This file is part of the "LARUS Integration Framework for Neo4j".
 * <p>
 * The "LARUS Integration Framework for Neo4j" is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created on 20/07/17
 */
package org.talend.components.neo4j.tneo4jconnection.properties;

import org.talend.components.common.UserPasswordProperties;
import org.talend.daikon.properties.PropertiesImpl;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * Talend component properties for a a remote Neo4j server.
 */
public class Neo4jRemoteConnectionProperties extends PropertiesImpl {

    /**
     * Url of the database (ex: bolt://localhost).
     */
    public Property<String> url = PropertyFactory.newProperty("url").setRequired();

    /**
     * Authentication properties (ie login and password)
     */
    public UserPasswordProperties userPassword = new UserPasswordProperties("userPassword");

    /**
     * Default constructor.
     *
     * @param name
     */
    public Neo4jRemoteConnectionProperties(String name) {
        super(name);
    }

    @Override
    public void setupLayout() {
        super.setupLayout();

        Form form = Form.create(this, Form.MAIN);
        form.addRow(url);
        form.addRow(userPassword.getForm(Form.MAIN));
    }
}
