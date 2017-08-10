package com.test.transfer.database;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.hsqldb.Server;

import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HSQLDataSource {

    private static final String url = "jdbc:hsqldb:hsql://localhost:9001/test";
    private static final String user = "SA";
    private static final String password = "";

    static{
        try {
            Server server = new Server();
            server.setDatabaseName(0, "test");
            server.setDatabasePath(0, "mem:test");
            server.setPort(9001);
            server.start();

            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            try(Connection conn = DriverManager.getConnection(url, user, password)){
                ScriptRunner sqlRunner = new ScriptRunner (conn);
                Reader reader = new InputStreamReader(HSQLDataSource.class.getResourceAsStream("/init.sql"));
                sqlRunner.runScript(reader);
                sqlRunner.closeConnection();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }
}
