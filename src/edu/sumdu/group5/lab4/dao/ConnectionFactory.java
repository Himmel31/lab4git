package edu.sumdu.group5.lab4.dao;

import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ejb.EntityContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * Class which is creates connection to db
 * Author Sergey & Roman
 */
public class ConnectionFactory {

    /** The logger */
    private static final Logger log = Logger.getLogger(ConnectionFactory.class);

    static String driverClassName=null;
    static String connectionUrl;
    static String dbUser;
    static String dbPwd;

    private static ConnectionFactory connectionFactory = null;
    private static Connection conn=null ;

    /**
     * Constructor of class
     * defining class loader of the current class.
     */
    private ConnectionFactory() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
    }


    private static void readConfigFile() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        BufferedReader reader = null;
        if(driverClassName==null){
            try {
                reader = new BufferedReader(new FileReader("DBConnection.properties"));
                Properties prop = new Properties();
                prop.load(reader);
                driverClassName = prop.getProperty("driverClassName");
                connectionUrl = prop.getProperty("connectionUrl");
                dbUser = prop.getProperty("dbUser");
                dbPwd = prop.getProperty("dbPwd");
            } catch (IOException e) {
                DaoException e1 =  new DaoException("Something wrong with config file!",e);
                log.error("Exception", e1);
                throw e1;
            } finally {
                try {
                    if (reader!=null)
                        reader.close();
                } catch (IOException e) {
                    DaoException e1 =  new DaoException("Something wrong with config file! Can't close!",e);
                    log.error("Exception", e1);
                    throw e1;
                }
            }
        }
    }

    /**
     * @return instance of connection
     */
    public static Connection getConnectionDS(DataSource ds) throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        System.out.println("In getDsConnCF");
        if (conn == null) {
            System.out.println("In getDsConn in IF");
            try {
                conn = ds.getConnection();
            } catch (SQLException e) {
                DaoException e1 = new DaoException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }


        return conn;
    }

    /**
     * @return instance of connection
     */
    public static Connection getConnection() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        try {
            readConfigFile();
            Class.forName(driverClassName);
                if (connectionFactory == null) {
                    connectionFactory = new ConnectionFactory();
                    try {
                        conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
                    } catch (SQLException e) {
                        DaoException e1 = new DaoException(e);
                log.error("Exception", e1);
                throw e1;
                    }
                }
        } catch (ClassNotFoundException e) {
                DaoException e1 = new DaoException(e);
                log.error("Exception", e1);
                throw e1;
            }


        return conn;
    }

    /**
     * closes connection
     */
    public static void closeConnection() throws ConnectionException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        if (conn != null) {
            try {
                conn.close();
                conn=null;
                connectionFactory = null;
            } catch (SQLException e) {
                ConnectionException e1 = new ConnectionException(e);
                log.error("Exception", e1);
                throw e1;
            }
            System.out.println("Closed!!!!");
        } 
    }

}
