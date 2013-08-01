package edu.sumdu.group5.lab4.ejb.places;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.sumdu.group5.lab4.dao.ConnectionException;
import edu.sumdu.group5.lab4.dao.ConnectionFactory;
import edu.sumdu.group5.lab4.dao.DaoException;

import org.apache.log4j.Logger;

/**
 * Places bean class
 * @author Sergey, Artem
 */
public class PlacesBean implements EntityBean{

	private String name;
	private Long id=null;
	private Long parentID=null;
	private int locationTypeID;
    private EntityContext context;
	private Connection con=null;
	private PreparedStatement ptmt = null;
    private ResultSet rs = null;
    private static DataSource ds = null;
    
    private final String dataSource ="DataSource";

    /** The logger */
    private static final Logger log = Logger.getLogger(PlacesBean.class);
    
    /**
     * 
     * @return  connection to db
     * @throws DaoException
     */
    private Connection getConnection() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        con = ConnectionFactory.getConnectionDS(getDs());
        return con;
    }

    /**
     * 
     * closes connection to db
     */
    private void closeConnection() throws ConnectionException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        ConnectionFactory.closeConnection();
    }
    
    /**
     * 
     * @return  id of location type
     */
    public int getLocationTypeID() {
       return locationTypeID;
    }

    /**
     * 
     * @return  name of place
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return  id of place
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @return  parent id of place
     */
    public Long getParentID() {
        return parentID;
    }

    /**
     *
     * @return  data source of db
     * @throws Exception
     */
    private DataSource getDs() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        if (ds==null){
            InitialContext cxt = null;
            try {
                cxt = new InitialContext();
            } catch (NamingException e) {
                DaoException e1 = new DaoException("No context!");
                log.error("Exception", e1);
                throw e1;
            }
            if ( cxt == null ) {
                DaoException e1 = new DaoException("No context!");
                log.error("Exception", e1);
                throw e1;
            }
            ds = (DataSource) context.lookup("java:jdbc/MSSQLDS");
        }
        return ds;
    }

    /**
     * @return collection of all places
     * @throws FinderException
     */
    public Collection ejbFindAllLocation() throws FinderException{
        if (log.isDebugEnabled())
            log.debug("Method call");
    	@SuppressWarnings("rawtypes")
		Collection col = new ArrayList();
        try {
            String querystring = "SELECT * FROM places";
           
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            rs = ptmt.executeQuery();
          
            while (rs.next()) {
                col.add(new Long(rs.getInt("id_place")));
            }
            return col;
        } catch (SQLException e) {
            FinderException e1 = new FinderException("Couldn't execute querySQL " + e.getLocalizedMessage());
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            FinderException e1 = new FinderException("Couldn't execute queryDAO " + e.getLocalizedMessage());
            log.error("Exception", e1);
            throw e1;
        }
        finally {
        	try{
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
        	}catch(SQLException e){
        		FinderException e1 = new FinderException("SQL exception"+ e.getLocalizedMessage());
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                FinderException e1 = new FinderException("Connection exception"+ e.getLocalizedMessage());
                log.error("Exception", e1);
                throw e1;
            }
        }

    }
    
    /**
     * 
     * @param id - id of place
     * @return id of place
     * @throws FinderException
     */
    public Long ejbFindByPrimaryKey(Long id) throws FinderException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
    	if (id == null) {
            FinderException e1 = new FinderException("Primary key cannot be null");
            log.error("Exception", e1);
            throw e1;
        }    
        try {
        	con = getConnection();
            ptmt = con.prepareStatement("SELECT * from places where id_place=?");
            ptmt.setInt(1, id.intValue());
            rs = ptmt.executeQuery();
            if (!rs.next()) {
                FinderException e1 = new FinderException("Couldn't find a record. Id = "+id);
                log.error("Exception", e1);
                throw e1;
            }
            return new Long(rs.getInt("id_place"));
        } catch (SQLException e) {
            FinderException e1 = new FinderException("Couldn't execute query");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            FinderException e1 = new FinderException("Couldn't execute query");
            log.error("Exception", e1);
            throw e1;
        } finally {
        	try{
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
        	}catch(SQLException e){
        		FinderException e1 = new FinderException("SQL exception"+ e.getLocalizedMessage());
                log.error("Exception", e1);
                throw e1;
            }catch(ConnectionException e){
        		FinderException e1 = new FinderException("Connection exception"+ e.getLocalizedMessage());
                log.error("Exception", e1);
                throw e1;
            }
        }
    }

    /**
     * 
     * @throws CreateException
     */
    public Long ejbCreate() throws CreateException{
    	return getId();
    }
    
    /**
     * 
     * @throws CreateException
     */
    public void ejbPostCreate() throws CreateException{
    }
    
    @Override
    /**
     * 
     * @throws EJBException
     * @throws RemoteException
     */
	public void ejbActivate() throws EJBException, RemoteException {	
    	 this.id = (Long)context.getPrimaryKey();
    }

	@Override
	/**
	 * 
	 * @throws EJBException
	 * @throws RemoteException
	 */
	public void ejbLoad() throws EJBException, RemoteException {
      
        try {
            if (log.isDebugEnabled())
                log.debug("Method call");
            con = getConnection();
            ptmt = con.prepareStatement("SELECT * FROM places where id_place=?");
            ptmt.setLong(1, id);
            rs = ptmt.executeQuery();
            if (!rs.next()) {
               EJBException e1 = new EJBException("SQL exception");
                log.error("Exception", e1);
                throw e1;
            }
            id = rs.getLong("id_place");
            name = rs.getString("place_name");
            locationTypeID = rs.getInt("id_location_type");
            parentID = rs.getLong("id_parent");

        } catch (SQLException e) {
            EJBException e1 = new EJBException("Couldn't execute query");
            log.error("Exception", e1);
            throw e1;
        }
        catch (DaoException e) {
            EJBException e1 = new EJBException("Couldn't execute queryDAO");
            log.error("Exception", e1);
            throw e1;
        }finally {
        	try{
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
        	}catch(SQLException e){
        		EJBException e1 = new EJBException("SQL exception");
                log.error("Exception", e1);
                throw e1;
        	}catch(ConnectionException e){
        		EJBException e1 = new EJBException("Smth wrong with connection");
                log.error("Exception", e1);
                throw e1;
        	}
        }
    }

    @Override
    /**
     * 
     * @throws EJBException
     * @throws RemoteException
     */
	public void ejbPassivate() throws EJBException, RemoteException {	
		id = null;
	}

	@Override
	/**
	 * 
	 * @throws EJBException
	 * @throws RemoteException
	 */
	public void ejbRemove() throws RemoveException, EJBException,
			RemoteException {	
	}

	@Override
	/**
	 * 
	 * @throws EJBException
	 * @throws RemoteException
	 */
	public void ejbStore() throws EJBException, RemoteException {
	}

	/**
     * 
     * @param context - context in which bean works
     * @throws EJBException
     * @throws RemoteException
     */
	public void setEntityContext(EntityContext context) throws EJBException,RemoteException {
        this.context=context;
    }

	/**
     * 
     * @throws EJBException
     * @throws RemoteException
     */
    public void unsetEntityContext() throws EJBException, RemoteException {
        this.context = null;
    }

	
}
