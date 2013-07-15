package edu.sumdu.group5.lab3.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class which is creates connection to db
 * Author Sergey & Roman
 */
public class ConnectionFactory {

    String driverClassName;
    static String connectionUrl;
    static String dbUser;
    static String dbPwd;

    private static ConnectionFactory connectionFactory = null;
    private static Connection conn = null;

    /**
     * Constructor of class
     * defining class loader of the current class.
     */
    private ConnectionFactory() throws DaoException {
        try {
            readConfigFile();
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            throw new DaoException(e);
        }
    }

    private void readConfigFile() throws DaoException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("DBConnection.properties")); 
            Properties prop = new Properties();
            prop.load(reader);
            driverClassName = prop.getProperty("driverClassName");
            connectionUrl = prop.getProperty("connectionUrl");
            dbUser = prop.getProperty("dbUser");
            dbPwd = prop.getProperty("dbPwd");
        } catch (IOException e) {
            throw new DaoException("Something wrong with config file!",e);
        } finally {
            try {
                if (reader!=null)
                    reader.close();
            } catch (IOException e) {
                throw new DaoException("Something wrong with config file! Can't close!",e);
            }
        }
    }

    /**
     * @return instance of connection
     */
    public static Connection getConnection() throws DaoException {

        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
            try {
                conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return conn;
    }

    /**
     * closes connection
     */
    public static void closeConnection() throws ConnectionException {
        if (conn != null) {
            try {
                conn.close();
                connectionFactory = null;
            } catch (SQLException e) {
                throw new ConnectionException(e);
            }
        } 
    }

}
