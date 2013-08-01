package edu.sumdu.group5.lab4.ejb.devices;

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
import javax.sql.DataSource;

import edu.sumdu.group5.lab4.dao.ConnectionException;
import edu.sumdu.group5.lab4.dao.ConnectionFactory;
import edu.sumdu.group5.lab4.dao.DaoException;

import org.apache.log4j.Logger;

/**
 * Device bean class
 * @author Sergey, Artem
 */
public class DevicesBean implements EntityBean{
	
    /** The logger */
    private static final Logger log = Logger.getLogger(DevicesBean.class);
    
	private Long id;
	private String devName;
	private Long parentID;
	private Long placeID;
	private Long deviceTypeID;
	private Connection con=null;
	private PreparedStatement ptmt = null;
    private ResultSet rs = null;
    private EntityContext context=null;
    private static DataSource ds = null;

    private final String dataSource ="DataSource";
	
    /**
     * 
     * @return  connection to db
     * @throws DaoException
     */
    private Connection getConnection(DataSource ds) throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        con = ConnectionFactory.getConnectionDS(ds);
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
     *   id of type of the device
     */
    public void setDeviceTypeID(Long id) throws RemoteException{
        deviceTypeID = id;
    }

    /**
     *
     *   id of device
     */
    public void setId(Long id) throws RemoteException{
        this.id=id;
    }

    /**
     *
     *   name of device
     */
    public void setDevName(String str) throws RemoteException{
        devName = str;
    }

    /**
     *
     *  parent id of device
     */
    public void setParentID(Long id) throws RemoteException{
        parentID=id;
    }

    /**
     *
     *   id of location
     */
    public void setPlaceID(Long id) {
        placeID = id;
    }

    /**
     * 
     * @return  id of type of the device
     */
    public Long getDeviceTypeID() {
        return deviceTypeID;
    }

    /**
     * 
     * @return  id of device
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @return  name of the device
     */
    public String getDevName() {
        return devName;
    }

    /**
     * 
     * @return  parent id of device
     */
    public Long getParentID() {
        return parentID;
    }

    /**
     * 
     * @return  id of location
     */
    public Long getPlaceID() {
        return placeID;
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
            if(context == null){
                DaoException e1 = new DaoException("DaoException");
                log.error("Exception", e1);
                throw e1;
            }
            ds = (DataSource) context.lookup("java:jdbc/MSSQLDS");
        }
        return ds;
    }

    /**
     * 
     * @param id -  id of the current location
     * @return Collection of ids of devices
     * @throws FinderException
     * @throws RemoteException
     */
    public Collection ejbFindRootDevicesByPlaceID(Long id) throws FinderException, RemoteException {
        Collection devices = new ArrayList();
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
        try {
            String querystring = "SELECT * FROM devices where id_place=? and id_parent is NULL";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(id));
            rs = ptmt.executeQuery();
            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }

        } catch (SQLException e) {
            RemoteException e1 = new RemoteException("SQLException");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            RemoteException e1 = new RemoteException("DaoException");
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                RemoteException e1 = new RemoteException("SQLException");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                RemoteException e1 = new RemoteException("ConnectionException");
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;

     }
    
    /**
     * 
     * @param devName - name of the device
     * @param devTypeId - id of the device type
     * @param parentId - parent id
     * @param placeId - id of the place
     * @return id of device
     * @throws CreateException
     */
    public Long ejbCreate(String devName, Long devTypeId, Long parentId, Long placeId) throws CreateException{
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + devName + " " + devTypeId + " " + parentId + " " + placeId);
        try {
        	
            String querystring = "INSERT INTO devices VALUES(?,?,?,?,?)";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring, PreparedStatement.RETURN_GENERATED_KEYS);
            ptmt.setString(1, null);
            ptmt.setString(2, devName);
            ptmt.setLong(3, devTypeId);
            if (getParentID() != 0) {
                ptmt.setLong(4, parentId);
            } else {
                ptmt.setString(4, null);
            }
            ptmt.setLong(5, placeId);
            ptmt.executeUpdate();
            return getId();
        } catch (SQLException e) {
            CreateException e1 = new CreateException("Can not create device, SQL exception!");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            CreateException e1 = new CreateException("Can not create device, DAO exception");
            log.error("Exception", e1);
            throw e1;
       } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                CreateException e1 = new CreateException("Can not create device, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                CreateException e1 = new CreateException("Can not create device, Connection exception");
                log.error("Exception", e1);
                throw e1;
           }
        }
    }
    
   /**
    * 
    * @param devName - name of the device
    * @param devTypeId - id of the device type
    * @param parentId - parent id
    * @param placeId - id of the place
    * @throws CreateException
    */
    public void ejbPostCreate(String devName, Long devTypeId, Long parentId, Long placeId) throws CreateException{
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + devName + " " + devTypeId + " " + parentId + " " + placeId);
        try {
            String querystring = "INSERT INTO devices VALUES(?,?,?,?,?)";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring, PreparedStatement.RETURN_GENERATED_KEYS);
            ptmt.setString(1, null);
            ptmt.setString(2, devName);
            ptmt.setLong(3, devTypeId);
            if (getParentID() != 0) {
                ptmt.setLong(4, parentId);
            } else {
                ptmt.setString(4, null);
            }
            ptmt.setLong(5, placeId);
            ptmt.executeUpdate();
            
        } catch (SQLException e) {
            CreateException e1 = new CreateException("Can not create device, SQL exception!");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            CreateException e1 = new CreateException("Can not create device, DAO exception");
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                CreateException e1 = new CreateException("Can not create device, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                CreateException e1 = new CreateException("Can not create device, Connection exception");
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
        if (log.isDebugEnabled())
            log.debug("Method call");
		 try {
	            con = getConnection(getDs());
	            ptmt = con.prepareStatement("SELECT * FROM devices where id_device=?");
	            ptmt.setLong(1, id);
	            rs = ptmt.executeQuery();
	            if (!rs.next()) {
	                EJBException e1 = new EJBException("Couldn't find a record. Id = "+id);
                    log.error("Exception", e1);
                    throw e1;
                }
	            id = rs.getLong("id_device");
	            devName = rs.getString("device_name");
	            parentID = rs.getLong("id_parent");
	            placeID = rs.getLong("id_place");
	            deviceTypeID= rs.getLong("id_device_type");


	        } catch (SQLException e) {
	            EJBException e1 = new EJBException("Couldn't execute query SQL");
                log.error("Exception", e1);
                throw e1;
	        }
	        catch (DaoException e) {
	            EJBException e1 = new EJBException("Couldn't execute query DAO");
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
	        		EJBException e1 = new EJBException("1");
                    log.error("Exception", e1);
                    throw e1;
	        	}catch(ConnectionException e){
	        		EJBException e1 = new EJBException("2");
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

    /**
    * @param id - id of the device to remove
    * @throws FinderException
    */
	/*public void ejbHomeRemoveById(Long id) throws FinderException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
	    try {
            String querystring = "DELETE FROM devices WHERE id_device=? or id_parent=?";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(id));
            ptmt.setString(2, String.valueOf(id));
            ptmt.executeUpdate();
        } catch (SQLException e) {
            FinderException e1 = new FinderException("Can't remove device, SQL exception");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            FinderException e1 = new FinderException("Can't remove device, DAO exception");
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                FinderException e1 = new FinderException("Can't remove device, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                FinderException e1 = new FinderException("Can't remove device, Connection exception");
                log.error("Exception", e1);
                throw e1;
            }
        }
		
	}*/

	@Override
	/**
     * 
     * @throws EJBException
     * @throws RemoteException
     */
	public void ejbStore() throws EJBException, RemoteException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + getId() + " " + getDevName());
        try {
            String querystring = "UPDATE devices SET device_name=? WHERE id_device=?;";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, getDevName());
            ptmt.setLong(2, getId());
            ptmt.executeUpdate();

        } catch (SQLException e) {
            EJBException e1 = new EJBException("Can't update device, SQL exception");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            EJBException e1 = new EJBException("Can't update device, DAO exception");
            log.error("Exception", e1);
            throw e1;
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                EJBException e1 = new EJBException("Can't update device, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                EJBException e1 = new EJBException("Can't update device, Connection exception");
                log.error("Exception", e1);
                throw e1;
            }
        }
	}
	
	/**
     * @return collection of all devices
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection ejbFindAllDevices() throws RemoteException, FinderException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }
        } catch (SQLException e) {
            FinderException e1 = new FinderException("Can't find all devices, SQL exception");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            FinderException e1 = new FinderException("Can't find all devices, DAO exception");
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                FinderException e1 = new FinderException("Can't find all devices, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                FinderException e1 = new FinderException("Can't find all devices, Connection exception");
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
	}
	
	/**
	 * @param deviceId - id of the device
     * @return collection of child devices of the device
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection ejbFindChildDevices(Long deviceId) throws RemoteException, FinderException {
	    if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceId);
        Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices where id_parent=?";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceId));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }

        } catch (SQLException e) {
            FinderException e1 = new FinderException("Can't find child devices, SQL exception");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            FinderException e1 = new FinderException("Can't find child devices, DAO exception");
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();             
                closeConnection();
            } catch (SQLException e) {
                FinderException e1 = new FinderException("Can't find child devices, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                FinderException e1 = new FinderException("Can't find child devices, Connection exception");
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
	}
	
	/**
	 * @param deviceId - id of the device
     * @return collection of child slots of the device
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection ejbFindChildDevicesSlots(Long deviceId) throws RemoteException, FinderException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceId);
        Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices where id_parent=? AND id_device_type=2";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceId));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }

        } catch (SQLException e) {
            FinderException e1 = new FinderException("Can't find child devices, SQL exception");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            FinderException e1 = new FinderException("Can't find child devices, DAO exception");
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();             
                closeConnection();
            } catch (SQLException e) {
                FinderException e1 = new FinderException("Can't find child devices, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                FinderException e1 = new FinderException("Can't find child devices, Connection exception");
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
    }
	
	/**
	 * @param deviceId - id of the device
     * @return collection of child ports of the device
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection ejbFindChildDevicesPorts(Long deviceId) throws RemoteException, FinderException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceId);
        Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices where id_parent=? AND id_device_type=4";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceId));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }

        } catch (SQLException e) {
            FinderException e1 = new FinderException("Can't find child devices, SQL exception");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            FinderException e1 = new FinderException("Can't find child devices, DAO exception");
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();             
                closeConnection();
            } catch (SQLException e) {
                FinderException e1 = new FinderException("Can't find child devices, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                FinderException e1 = new FinderException("Can't find child devices, Connection exception");
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
    }
	
	@Override
	/**
     * 
     * @param context - context in which bean works
     * @throws EJBException
     * @throws RemoteException
     */
	public void setEntityContext(EntityContext context) throws EJBException,
			RemoteException {
		this.context=context;
		
	}

	@Override
	/**
     * 
     * @throws EJBException
     * @throws RemoteException
     */
	public void unsetEntityContext() throws EJBException, RemoteException {
		this.context = null;
		
	}
	
	/**
     * 
     * @param id - id of device
     * @return id of device
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
        	con = getConnection(getDs());
            ptmt = con.prepareStatement("SELECT * from devices where id_device=?");
            ptmt.setLong(1, id);
            rs = ptmt.executeQuery();
            if (!rs.next()) {
                FinderException e1 = new FinderException("Couldn't find a record. Id = "+id);
                log.error("Exception", e1);
                throw e1;
            }
            return new Long(rs.getInt("id_device"));
        } catch (SQLException e) {
            FinderException e1 = new FinderException("Couldn't execute query ");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            FinderException e1 = new FinderException("Can't update device, DAO exception");
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
                FinderException e1 = new FinderException("Can't find Device by ID " + id + ". SQL exception");
                log.error("Exception", e1);
                throw e1;
            }catch(ConnectionException e){
                FinderException e1 = new FinderException("Can't find Device by ID " + id + ". Connection exception");
                log.error("Exception", e1);
                throw e1;
            }
        }
    }

    @Override
    /**
     * 
     * @throws RemoteException
     * @throws EJBException
     */
    public void ejbRemove() throws RemoveException, EJBException,
            RemoteException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        try {
            String querystring = "DELETE FROM devices WHERE id_device=? or id_parent=?";
            con = getConnection(getDs());
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(id));
            ptmt.setString(2, String.valueOf(id));
            ptmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            RemoveException e1 = new RemoveException("Can't remove device, SQL exception");
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            RemoveException e1 = new RemoveException("Can't remove device, DAO exception");
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                RemoveException e1 = new RemoveException("Can't remove device, SQL exception");
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                RemoveException e1 = new RemoveException("Can't remove device, Connection exception");
                log.error("Exception", e1);
                throw e1;
            }
        }
        
    }
}
