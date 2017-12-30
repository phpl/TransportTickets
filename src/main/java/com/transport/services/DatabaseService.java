package com.transport.services;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseService {
    private static Logger logger = Logger.getLogger(DatabaseService.class);

    private Connection connection = null;

    private Properties properties = null;

    public DatabaseService() {
        properties = loadProperties();
    }

    public void setAutoCommit(boolean commit) {
        try {
            this.connection.setAutoCommit(commit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        InputStream stream = null;

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            stream = classLoader.getResourceAsStream("application.properties");
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public void connectToDatabase() {
        try {
            Class.forName(properties.getProperty("JDBC_DRIVER"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(properties.getProperty("DB_URL"), properties.getProperty("USER"), properties.getProperty("PASSWORD"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.info("Connected to the Database");
    }

    public void closeConnection() {
        try {
            if (connection != null)
                connection.close();
            logger.info("Closed connection");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void rollbackTransaction() {
        if (getConnection() != null) {
            try {
                System.err.print("Rollbacking transaction");
                getConnection().rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
