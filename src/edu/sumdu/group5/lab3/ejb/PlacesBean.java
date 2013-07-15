package edu.sumdu.group5.lab3.ejb;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
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
import edu.sumdu.group5.lab3.model.Place;


public class PlacesBean implements EntityBean{

	private String name;
	private Long id=null;
	private Long parentID=null;
	private int locationTypeID;
    private EntityContext context;
	private Connection con=null;
	PreparedStatement ptmt = null;
    ResultSet rs = null;
    private DataSource ds = null;
    private Place pl;


    private Connection getConnection() throws DaoException {
        con = ConnectionFactory.getConnection();
        return con;
    }

    private void closeConnection() throws ConnectionException {
        ConnectionFactory.closeConnection();
    }
    
    public Long ejbCreate() throws CreateException{
    	return getId();
    }
    
    public void ejbPostCreate() throws CreateException{
    }

    private DataSource getDs(){
        if (ds==null)
            ds = (DataSource) context.lookup("java:/MySqlDS");
        return ds;
    }

    public Place getPlace() {
        return pl;
    }
    public Collection ejbFindAllLocation() throws FinderException{
 
        Collection places = new LinkedList<Place>();
        try {
            String querystring = "SELECT * FROM places";
            DataSource ds = getDs();
            if(ds == null)
            {
                System.err.println();
                System.err.println("=========DATASOURCE IS NULL!!!!!===============");
                System.out.println();
                System.out.println("=========DATASOURCE IS NULL!!!!!===============");
            }
            //con = ds.getConnection();
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_place");
                name = rs.getString("place_name");
                locationTypeID = rs.getInt("id_location_type");
                Integer parentID = rs.getInt("id_parent");
                Place pl = new Place(id, name, locationTypeID, parentID);
                places.add(pl);
            }
        } catch (SQLException e) {
            throw new FinderException("Couldn't execute querySQL");
        } catch (DaoException e) {
            throw new FinderException("Couldn't execute queryDAO");
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
        for (int i = 0; i < places.size();i++ ) {

                //System.out.println("EJB_FIND_ALL!!!!!!!!-- "+places.get(i).getName()+"------- ");                
            }
        return places;
    }
    
    public Long ejbFindByPrimaryKey(Long id) throws FinderException {
    	if (id == null) {
            throw new FinderException("Primary key cannot be null");
        }
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            
            prepStmt = con.prepareStatement("SELECT * from places where id_place=?");
            prepStmt.setInt(1, id.intValue());
            rs = prepStmt.executeQuery();
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

    @Override
	public void ejbActivate() throws EJBException, RemoteException {	
    	//id = (Integer) context.getPrimaryKey();
    }

	@Override
	public void ejbLoad() throws EJBException, RemoteException {
        Place place = (Place)context.getPrimaryKey();
        id = new Long(place.getId());
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            prepStmt = con.prepareStatement("SELECT * FROM places where id_place=?");
            prepStmt.setLong(1, id);
            rs = prepStmt.executeQuery();
            if (!rs.next())
               throw new EJBException("Couldn't find a record. Id = "+id);
            id = rs.getLong("id_place");
            name = rs.getString("place_name");
            locationTypeID = rs.getInt("id_location_type");
            parentID = rs.getLong("id_parent");
            //pl = new Place(id.intValue(),name,locationTypeID,parentID.intValue());

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
		//id = null;
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
