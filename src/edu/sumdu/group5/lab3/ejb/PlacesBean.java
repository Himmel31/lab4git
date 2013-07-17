package edu.sumdu.group5.lab3.ejb;

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

import edu.sumdu.group5.lab3.dao.ConnectionException;
import edu.sumdu.group5.lab3.dao.ConnectionFactory;
import edu.sumdu.group5.lab3.dao.DaoException;
import edu.sumdu.group5.lab3.model.PlaceCl;


public class PlacesBean implements EntityBean{

	private String name;
	private Long id=null;
	private Long parentID=null;
	private int locationTypeID;
    private EntityContext context;
	private Connection con=null;
	private PreparedStatement ptmt = null;
    private ResultSet rs = null;
    private DataSource ds = null;
    private PlaceCl pl;


    private Connection getConnection() throws DaoException {
        con = ConnectionFactory.getConnection();
        return con;
    }

    private void closeConnection() throws ConnectionException {
        ConnectionFactory.closeConnection();
    }
    
    

    private DataSource getDs(){
        if (ds==null)
            ds = (DataSource) context.lookup("java:/MySqlDS");
        return ds;
    }

    public PlaceCl getPlace() {
        return pl;
    }
    
    public Collection ejbFindAllLocation() throws FinderException{
 
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
            throw new FinderException("Couldn't execute querySQL " + e.getLocalizedMessage());
        } catch (DaoException e) {
        	throw new FinderException("Couldn't execute queryDAO " + e.getLocalizedMessage());
        }
        finally {
        	try{
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                
                closeConnection();
        	}catch(SQLException e){
        		throw new FinderException("");
        	}catch(ConnectionException e){
        		throw new FinderException("");
        	}
        }

    }
    
    public Long ejbFindByPrimaryKey(Long id) throws FinderException {
    	if (id == null) {
            throw new FinderException("Primary key cannot be null");
        }
      
        try {
            
            ptmt = con.prepareStatement("SELECT * from places where id_place=?");
            ptmt.setInt(1, id.intValue());
            rs = ptmt.executeQuery();
            if (!rs.next())
               throw new FinderException("Couldn't find a record. Id = "+id);
           
            return new Long(rs.getInt("id_place"));
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
        		throw new FinderException("");
        	}catch(ConnectionException e){
        		throw new FinderException("");
        	}
        }
    }
    


     public int getLocationTypeID() {
        return locationTypeID;
    }

    public void setLocationTypeID(int locationTypeID) {
        this.locationTypeID = locationTypeID;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getParentID() {
        return parentID;
    }

    public Long ejbCreate() throws CreateException{
    	return getId();
    }
    
    public void ejbPostCreate() throws CreateException{
    }
    
    @Override
	public void ejbActivate() throws EJBException, RemoteException {	
    	//id = (Integer) context.getPrimaryKey();
    	 this.id = (Long)context.getPrimaryKey();
    }

	@Override
	public void ejbLoad() throws EJBException, RemoteException {
      
        try {
            con = getConnection();
            ptmt = con.prepareStatement("SELECT * FROM places where id_place=?");
            ptmt.setLong(1, id);
            rs = ptmt.executeQuery();
            if (!rs.next())
               throw new EJBException("Couldn't find a record. Id = "+id);
            id = rs.getLong("id_place");
            name = rs.getString("place_name");
            locationTypeID = rs.getInt("id_location_type");
            parentID = rs.getLong("id_parent");

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

     public String toString_() throws RemoteException
     {
         return name;
     }


    @Override
	public void ejbPassivate() throws EJBException, RemoteException {	
		id = null;
	}

	@Override
	public void ejbRemove() throws RemoveException, EJBException,
			RemoteException {	
	}

	@Override
	public void ejbStore() throws EJBException, RemoteException {
	}

	public void setEntityContext(EntityContext context) throws EJBException,RemoteException {
        this.context=context;
    }

    /**
     *
     */
    public void unsetEntityContext() throws EJBException, RemoteException {
        this.context = null;
    }

	
}
