package edu.sumdu.group5.lab3.ejb;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import edu.sumdu.group5.lab3.dao.ConnectionException;
import edu.sumdu.group5.lab3.dao.ConnectionFactory;
import edu.sumdu.group5.lab3.dao.DaoException;

public class DevicesBean implements EntityBean{
	
	private Long id;
	private String devName;
	private Long parentID;
	private Long placeID;
	private Long deviceTypeID;
	private Connection con=null;
	private PreparedStatement ptmt = null;
    private ResultSet rs = null;
    private EntityContext context;
	
	private Connection getConnection() throws DaoException {
        con = ConnectionFactory.getConnection();
        return con;
    }

    private void closeConnection() throws ConnectionException {
        ConnectionFactory.closeConnection();
    }
    
    public Long getDeviceTypeID() {
        return deviceTypeID;
    }

    public void setDeviceTypeID(Long deviceTypeID) {
        this.deviceTypeID = deviceTypeID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public Long getPlaceID() {
        return placeID;
    }

    public void setPlaceID(Long placeID) {
        this.placeID = placeID;
    }

    public Collection ejbFindRootDevicesByPlaceID(Long id) throws FinderException, RemoteException {
        Collection devices = new ArrayList();
        
        try {
            String querystring = "SELECT * FROM devices where id_place=? and id_parent is NULL";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(id));
            rs = ptmt.executeQuery();
            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }

        } catch (SQLException e) {
            throw new RemoteException("SQLException");
        } catch (DaoException e) {
            throw new RemoteException("DaoException");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new RemoteException("SQLException");
            } catch (ConnectionException e) {
                throw new RemoteException("ConnectionException");
            }
        }
        return devices;

     }
    
    public Long ejbCreate(String devName, Long devTypeId, Long parentId, Long placeId) throws CreateException{
        try {
        	
            String querystring = "INSERT INTO devices VALUES(?,?,?,?,?)";
            con = getConnection();
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
            throw new CreateException("Can not create device, SQL exception!");
        } catch (DaoException e) {
            throw new CreateException("Can not create device, DAO exception");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new CreateException("Can not create device, SQL exception");
            } catch (ConnectionException e) {
                throw new CreateException("Can not create device, Connection exception");
            }
        }
    }
    
    public void ejbPostCreate(String devName, Long devTypeId, Long parentId, Long placeId) throws CreateException{
        try {
            String querystring = "INSERT INTO devices VALUES(?,?,?,?,?)";
            con = getConnection();
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
            throw new CreateException("Can not create device, SQL exception!");
        } catch (DaoException e) {
            throw new CreateException("Can not create device, DAO exception");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                throw new CreateException("Can not create device, SQL exception");
            } catch (ConnectionException e) {
                throw new CreateException("Can not create device, Connection exception");
            }
        }
        
    }
    
	@Override
	public void ejbActivate() throws EJBException, RemoteException {
		this.id = (Long)context.getPrimaryKey();
		
	}

	@Override
	public void ejbLoad() throws EJBException, RemoteException {
		 try {
			 
	            con = getConnection();
	            ptmt = con.prepareStatement("SELECT * FROM devices where id_device=?");
	            ptmt.setLong(1, id);
	            rs = ptmt.executeQuery();
	            if (!rs.next())
	               throw new EJBException("Couldn't find a record. Id = "+id);
                
	            id = rs.getLong("id_device");
	            devName = rs.getString("device_name");
	            parentID = rs.getLong("id_parent");
	            placeID = rs.getLong("id_place");
	            deviceTypeID= rs.getLong("id_device_type");


	        } catch (SQLException e) {
	            throw new EJBException("Couldn't execute query SQL");
	        }
	        catch (DaoException e) {
	            throw new EJBException("Couldn't execute query DAO");
	        }finally {
	        	try{
	                if (rs != null)
	                    rs.close();
	                if (ptmt != null)
	                    ptmt.close();
	                closeConnection();
	        	}catch(SQLException e){
	        		throw new EJBException("1");
	        	}catch(ConnectionException e){
	        		throw new EJBException("2");
	        	}
	        }
		
	}

	@Override
	public void ejbPassivate() throws EJBException, RemoteException {
	    id = null;
	}

	public void ejbHomeRemoveById(Long id) throws FinderException {
	    try {
            String querystring = "DELETE FROM devices WHERE id_device=? or id_parent=?";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(id));
            ptmt.setString(2, String.valueOf(id));
            ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new FinderException("Can't remove device, SQL exception");
        } catch (DaoException e) {
            throw new FinderException("Can't remove device, DAO exception");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                throw new FinderException("Can't remove device, SQL exception");
            } catch (ConnectionException e) {
                throw new FinderException("Can't remove device, Connection exception");
            }
        }
		
	}

	@Override
	public void ejbStore() throws EJBException, RemoteException {
	   /* try {
            String querystring = "UPDATE devices SET device_name=? WHERE id_device=?;";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, devName);
            ptmt.setLong(2, id);
            ptmt.executeUpdate();

        } catch (SQLException e) {
            throw new EJBException("Can't store device", e);
        } catch (DaoException e) {
            throw new EJBException("Can't store device", e);
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                
                closeConnection();
            } catch (SQLException e) {
                throw new EJBException("Can't store device", e);
            } catch (ConnectionException e) {
                throw new EJBException("Can't store device", e);
            }
        }*/
	}
	
	public Collection ejbFindAllDevices() throws RemoteException, FinderException {
	    Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }
        } catch (SQLException e) {
            throw new FinderException("Can't find all devices, SQL exception");
        } catch (DaoException e) {
            throw new FinderException("Can't find all devices, DAO exception");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new FinderException("Can't find all devices, SQL exception");
            } catch (ConnectionException e) {
                throw new FinderException("Can't find all devices, Connection exception");
            }
        }
        return devices;
	}
	
	public Collection ejbFindChildDevices(Long deviceId) throws RemoteException, FinderException {
	    Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices where id_parent=?";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceId));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }

        } catch (SQLException e) {
            throw new FinderException("Can't find child devices, SQL exception");
        } catch (DaoException e) {
            throw new FinderException("Can't find child devices, DAO exception");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();             
                closeConnection();
            } catch (SQLException e) {
                throw new FinderException("Can't find child devices, SQL exception");
            } catch (ConnectionException e) {
                throw new FinderException("Can't find child devices, Connection exception");
            }
        }
        return devices;
	}
	
	public Collection ejbFindChildDevicesSlots(Long deviceId) throws RemoteException, FinderException {
        Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices where id_parent=? AND id_device_type=2";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceId));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }

        } catch (SQLException e) {
            throw new FinderException("Can't find child devices slots, SQL exception");
        } catch (DaoException e) {
            throw new FinderException("Can't find child devices slots, DAO exception");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();             
                closeConnection();
            } catch (SQLException e) {
                throw new FinderException("Can't find child devices slots, SQL exception");
            } catch (ConnectionException e) {
                throw new FinderException("Can't find child devices slots, Connection exception");
            }
        }
        return devices;
    }
	
	public Collection ejbFindChildDevicesPorts(Long deviceId) throws RemoteException, FinderException {
        Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices where id_parent=? AND id_device_type=4";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceId));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                devices.add(rs.getLong("id_device"));
            }

        } catch (SQLException e) {
            throw new FinderException("Can't find child devices slots, SQL exception");
        } catch (DaoException e) {
            throw new FinderException("Can't find child devices slots, DAO exception");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();             
                closeConnection();
            } catch (SQLException e) {
                throw new FinderException("Can't find child devices slots, SQL exception");
            } catch (ConnectionException e) {
                throw new FinderException("Can't find child devices slots, Connection exception");
            }
        }
        return devices;
    }
	
	public void ejbHomeUpdateDevice(Long IdDevice, String deviceName) throws FinderException  {
	    try {
            String querystring = "UPDATE devices SET device_name=? WHERE id_device=?;";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, deviceName);
            ptmt.setLong(2, IdDevice);
            ptmt.executeUpdate();

        } catch (SQLException e) {
            throw new FinderException("Can't update device, SQL exception");
        } catch (DaoException e) {
            throw new FinderException("Can't update device, DAO exception");
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                
                closeConnection();
            } catch (SQLException e) {
                throw new FinderException("Can't update device, SQL exception");
            } catch (ConnectionException e) {
                throw new FinderException("Can't update device, Connection exception");
            }
        }
	}
	
	@Override
	public void setEntityContext(EntityContext context) throws EJBException,
			RemoteException {
		this.context=context;
		
	}

	@Override
	public void unsetEntityContext() throws EJBException, RemoteException {
		this.context = null;
		
	}
	
	public Long ejbFindByPrimaryKey(Long id) throws FinderException {
		
        if (id == null) {
            throw new FinderException("Primary key cannot be null");
        }
      
        try {
        	con = getConnection();
            ptmt = con.prepareStatement("SELECT * from devices where id_device=?");
            ptmt.setLong(1, id);
            rs = ptmt.executeQuery();
            if (!rs.next())
               throw new FinderException("Couldn't find a record. Id = "+id);
           
            return new Long(rs.getInt("id_device"));
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new FinderException("Couldn't execute query ");
        } catch (DaoException e) {
            throw new FinderException("Can't update device, DAO exception");
        }finally {
            try{
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                
                closeConnection();
            }catch(SQLException e){
                throw new FinderException("Can't find Device by ID " + id + ". SQL exception");
            }catch(ConnectionException e){
                throw new FinderException("Can't find Device by ID " + id + ". Connection exception");
            }
        }
    }

    @Override
    public void ejbRemove() throws RemoveException, EJBException,
            RemoteException {
        try {
            String querystring = "DELETE FROM devices WHERE id_device=";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, id.toString());
            ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new RemoveException("Can't remove device, SQL exception");
        } catch (DaoException e) {
            throw new RemoveException("Can't remove device, DAO exception");
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                throw new RemoveException("Can't remove device, SQL exception");
            } catch (ConnectionException e) {
                throw new RemoveException("Can't remove device, Connection exception");
            }
        }
        
    }
}
