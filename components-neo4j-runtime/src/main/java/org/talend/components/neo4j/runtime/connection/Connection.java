package org.talend.components.neo4j.runtime.connection;

import org.talend.components.neo4j.tneo4jconnection.TNeo4jConnectionProperties;

public abstract class Connection {

    public final static String SHARED_NAME = "neo4j_connection";

    public Connection(TNeo4jConnectionProperties props){
    }

    public abstract void close();

}
