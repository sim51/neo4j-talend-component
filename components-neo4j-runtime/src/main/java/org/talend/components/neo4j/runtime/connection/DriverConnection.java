package org.talend.components.neo4j.runtime.connection;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.talend.components.neo4j.tneo4jconnection.TNeo4jConnectionProperties;

public class DriverConnection  extends Connection {

    private Driver driver;

    public DriverConnection(TNeo4jConnectionProperties props) {
        super(props);

        this.driver = GraphDatabase.driver(
                props.connectionRemote.url.getValue(),
                AuthTokens.basic(
                        props.connectionRemote.userPassword.userId.getValue(),
                        props.connectionRemote.userPassword.password.getValue()
                )
        );
    }

    @Override
    public void close() {
        if(this.driver != null){
            this.driver.close();
        }
    }
}
