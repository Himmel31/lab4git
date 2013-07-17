package edu.sumdu.group5.lab3.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import edu.sumdu.group5.lab3.ejb.DevicesHome;
import edu.sumdu.group5.lab3.ejb.DevicesRemote;
import edu.sumdu.group5.lab3.ejb.PlacesHome;
import edu.sumdu.group5.lab3.ejb.PlacesRemote;
import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.model.PlaceCl;

public class EjbDAO implements DAO {

	InitialContext in = null;
	Properties prop = null;
	PlacesHome placesH;
	DevicesHome devicesH;

	edu.sumdu.group5.lab3.model.PlaceCl pl;
	
    public EjbDAO()  {
    }

	public Collection<PlaceCl> getLocationList(Collection<PlacesRemote> plRemote) throws BeanException {
	   
		Collection<PlaceCl> res = new LinkedList();
        try {
            for (PlacesRemote dr : plRemote) {
            	PlaceCl d = new PlaceCl();
                d.setName(dr.getName());
                d.setLocationTypeID(dr.getLocationTypeID());
                d.setId(dr.getId().intValue());
                d.setParentID(dr.getParentID().intValue());
                
                res.add(d);
            }
        } catch (java.rmi.RemoteException e) {
          
            throw new BeanException(e);
        }
        return res;
    }
	
	public Collection<Device> getRootDevicesByPlaceIDBusinesMethod(Collection<DevicesRemote> devRemote) throws BeanException {
		   
		Collection<Device> res = new LinkedList();
        try {
            for (PlacesRemote dr : devRemote) {
            	Device d = new Device();
                d.setDevName(dr.getDevName());
                d.setDeviceTypeID(dr.getDeviceTypeID.intValue());
                d.setId(dr.getId().intValue());
                d.setParentID(dr.getParentID().intValue());
                d.setPlaceID(dr.PlaceID.intValue());
                res.add(d);
            }
        } catch (java.rmi.RemoteException e) {
          
            throw new BeanException(e);
        }
        return res;
    }
	
	@Override
	public void add(Device device) throws ModelException {
	
	}  
	
	@Override
	public Collection<PlaceCl> findAllLocation() throws ModelException, FinderException, BeanException {
		
		try {
		   	   Properties properties = new Properties();
		   	   properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		   	   properties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		   	   properties.setProperty(Context.PROVIDER_URL, "localhost:1099");
				
		   	   in = new InitialContext(properties);
		   	   Object obj =  in.lookup("PlacesEJB");
		   	   placesH   = (PlacesHome) PortableRemoteObject.narrow(obj,PlacesHome.class);

			   return getLocationList(placesH.findAllLocation());	
                   
        } catch (NamingException e) {
        	throw new BeanException (e);
		}catch (RemoteException e) {
			throw new BeanException (e);
		}
	}

	@Override
	public List<Device> getAllDevice() throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Device> getChildDevices(int deviceID) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Device> getChildDevicesPorts(int deviceID)
			throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Device> getChildDevicesSlots(int deviceID)
			throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Device getDeviceByID(int id) throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer, String> getIdDevicesTypes() throws ModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Device> getRootDevicesByPlaceID(int ID) throws ModelException {
		try {
		   	   Properties properties = new Properties();
		   	   properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		   	   properties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		   	   properties.setProperty(Context.PROVIDER_URL, "localhost:1099");
				
		   	   in = new InitialContext(properties);
		   	   Object obj =  in.lookup("DevicesEJB");
		   	   devicesH   = (DevicesHome) PortableRemoteObject.narrow(obj,DevicesHome.class);

			   return getRootDevicesByPlaceIDBusinesMethod(devicesH.findRootDevicesByPlaceID(new Long(ID)));	
                
     } catch (NamingException e) {
     	throw new BeanException (e);
		}catch (RemoteException e) {
			throw new BeanException (e);
		}
	}

	@Override
	public void removeDevice(int deviceID) throws ModelException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Integer ID, String devicename) throws ModelException {
		// TODO Auto-generated method stub

	}

}
