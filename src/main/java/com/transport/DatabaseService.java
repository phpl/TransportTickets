package com.transport;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Log4j
public class DatabaseService {

    private Connection connection = null;

    private Properties properties;

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

    private void createSshTunnel() throws JSchException {
        int sshPort = Integer.parseInt(properties.getProperty("SSH_PORT"));
        int localPort = Integer.parseInt(properties.getProperty("LOCAL_PORT"));
        int remotePort = Integer.parseInt(properties.getProperty("REMOTE_PORT"));
        String sshRemoteHost = properties.getProperty("REMOTE_HOST");
        String sshUser = properties.getProperty("SSH_USER");
        String sshPassword = "";
        JSch jsch = new JSch();

        Session session = jsch.getSession(sshUser, sshRemoteHost, sshPort);
        session.setPassword(sshPassword);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        config.put("Compression", "yes");
        config.put("ConnectionAttempts", "2");
        session.setConfig(config);

        session.connect();

        session.setPortForwardingL(localPort, sshRemoteHost, remotePort);
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
        String dbUrl = properties.getProperty("DB_URL");
        String dbUser = properties.getProperty("USER");
        String dbPassword = properties.getProperty("PASSWORD");

        try {
            createSshTunnel();
            Class.forName(properties.getProperty("JDBC_DRIVER"));
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (ClassNotFoundException | SQLException | JSchException e) {
            e.printStackTrace();
        }

        log.info("Connected to the Database");
    }

    public void closeConnection() {
        try {
            if (connection != null)
                connection.close();
            log.info("Closed connection");
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
                log.error("Rollbacking transaction");
                getConnection().rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void commitTransaction() {
        if (getConnection() != null) {
            try {
                log.error("Commiting transaction");
                getConnection().commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
