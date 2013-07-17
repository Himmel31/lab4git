package edu.sumdu.group5.lab3.ejb;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.sql.DataSource;

import edu.sumdu.group5.lab3.dao.ConnectionException;
import edu.sumdu.group5.lab3.dao.ConnectionFactory;
import edu.sumdu.group5.lab3.dao.DaoException;
import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;

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

    public void setDeviceTypeID(long deviceTypeID) {
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

    public Collection ejbFindRootDevicesByPlaceID(Long id) throws FinderException, BeanException{
        Collection devices = new ArrayList();
        try {
            String querystring = "SELECT * FROM devices where id_place=? and id_parent is NULL";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(id));
            rs = ptmt.executeQuery();
            while (rs.next()) {
                devices.add(new Long(rs.getInt("id_device")));
            }

        } catch (SQLException e) {
            throw new BeanException(e);
        } catch (DaoException e) {
            throw new BeanException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new BeanException(e);
            } catch (ConnectionException e) {
                throw new BeanException(e);
            }
        }
        return devices;

     }
    
    public Long ejbCreate() throws CreateException{
        try {
            String querystring = "INSERT INTO devices VALUES(?,?,?,?,?)";
            con = getConnection();
            ptmt = con.prepareStatement(querystring, PreparedStatement.RETURN_GENERATED_KEYS);
            ptmt.setString(1, null);
            ptmt.setString(2, getDevName());
            ptmt.setLong(3, getDeviceTypeID());
            if (getParentID() != 0) {
                ptmt.setLong(4, getParentID());
            } else {
                ptmt.setString(4, null);
            }
            ptmt.setLong(5, getPlaceID());
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
    
    public void ejbPostCreate() throws CreateException{
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
	            throw new EJBException("Couldn't execute query");
	        }
	        catch (DaoException e) {
	            throw new EJBException("Couldn't execute queryDAO");
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

	@Override
	public void ejbRemove() throws RemoveException, EJBException,
			RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ejbStore() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityContext(EntityContext arg0) throws EJBException,
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
            
            ptmt = con.prepareStatement("SELECT * from devices where id_device=?");
            ptmt.setInt(1, id.intValue());
            rs = ptmt.executeQuery();
            if (!rs.next())
               throw new FinderException("Couldn't find a record. Id = "+id);
           
            return new Long(rs.getInt("id_device"));
        } catch (SQLException e) {
            throw new FinderException("Couldn't execute query");
        } finally {
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
}
