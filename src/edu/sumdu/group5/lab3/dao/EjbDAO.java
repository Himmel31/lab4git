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

import edu.sumdu.group5.lab3.ejb.PlacesHome;
import edu.sumdu.group5.lab3.ejb.PlacesRemote;
import edu.sumdu.group5.lab3.ejb.ConvertFromRemoteToPlace;
import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.model.Place;

public class EjbDAO implements DAO {

	InitialContext in = null;
	Properties prop = null;
	Collection placesR = null;
    Place pl;
	
    public EjbDAO(){
    }
	
	@Override
	public void add(Device device) throws ModelException {
		/*try {
			in = new InitialContext(prop);
			Object obj =  in.lookup("/PlacesBean");
			PlacesHome placesH   = (PlacesHome) PortableRemoteObject.narrow(obj,PlacesHome.class);
			PlacesRemote PlacesR = placesH.create();
		} catch (NamingException e) {
			throw new BeanException (e);
		}catch (RemoteException e) {
			throw new BeanException (e);
		}catch (CreateException e) {
			throw new BeanException (e);
		}*/
		
	}  

	@Override
	public Collection findAllLocation() throws ModelException, FinderException, BeanException {
        Collection places;
		try {
			Properties properties = new Properties();
			properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			properties.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
			properties.setProperty(Context.PROVIDER_URL, "localhost:1099");
			
			in = new InitialContext(properties);
			Object obj =  in.lookup("PlacesEJB");
			PlacesHome placesH   = (PlacesHome) PortableRemoteObject.narrow(obj,PlacesHome.class);
			placesR = placesH.findAllLocation();
            places=ConvertFromRemoteToPlace.convert(placesR);
            
            //System.out.println("YEAAAHHHHH!!!!!!!!-- "+placesR.get(2).getName()+"------- ");
           /* for (Iterator it=placesR.iterator();it.hasNext();) {
                PlacesRemote pr= (PlacesRemote) it.next();
                //pl = pr.getPlace();
                System.out.println("YEAAAHHHHH!!!!!!!!REMOTE-- "+pr.getName()+"------- ");
                System.out.println("YEAAAHHHHH!!!!!!!!-- "+pl.getName()+"------- ");
                places.add(pl);
                System.out.println("PLACES SIZE LIST IS!!!!!!!!-- "+places.size()+"------- ");
            } */
        } catch (NamingException e) {
			e.printStackTrace();
			throw new BeanException (e);
		}catch (RemoteException e) {
			throw new BeanException (e);
		}

		return places;
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
	public List<Device> getRootDevicesByPlaceID(int ID) throws ModelException {
		// TODO Auto-generated method stub
		return null;
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
