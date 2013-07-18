package edu.sumdu.group5.lab3.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
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

    public EjbDAO() {
    }

    private void setInitialContext() throws NamingException {
        Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        properties.setProperty(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");
        properties.setProperty(Context.PROVIDER_URL, "localhost:1099");
        in = new InitialContext(properties);
    }

    @Override
    public Collection<Device> getRootDevicesByPlaceID(int id)
            throws BeanException, FinderException {

        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findRootDevicesByPlaceID(new Long(id)));

        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        }
    }

    private Collection<Device> getDevicesListFromRemoteDevicesList(
            Collection<DevicesRemote> devRemote) throws BeanException {
        Collection<Device> dev = new LinkedList();
        try {
            for (DevicesRemote dr : devRemote) {
                Device d = new Device();
                d.setDevName(dr.getDevName());
                d.setDeviceTypeID(dr.getDeviceTypeID().intValue());
                d.setId(dr.getId().intValue());
                d.setParentID(dr.getParentID().intValue());
                d.setPlaceID(dr.getPlaceID().intValue());
                dev.add(d);
            }
        } catch (java.rmi.RemoteException e) {
            throw new BeanException(e);
        }
        return dev;
    }

    @Override
    public void add(Device device) throws ModelException, BeanException {
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            devicesH.create(device.getDevName(), new Long(device.getDeviceTypeID()),
                    new Long(device.getParentID()), new Long(device.getPlaceID()));

        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        } catch (CreateException e) {
            throw new BeanException(e);
        }
    }
    
    @Override
    public Collection<PlaceCl> findAllLocation() throws ModelException,
            FinderException, BeanException {

        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("PlacesEJB");
            placesH = (PlacesHome) PortableRemoteObject.narrow(obj,
                    PlacesHome.class);

            return getLocationList(placesH.findAllLocation());

        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        }
    }

    private Collection<PlaceCl> getLocationList(
            Collection<PlacesRemote> plRemote) throws BeanException {

        Collection<PlaceCl> res = new LinkedList();
        try {
            for (PlacesRemote pr : plRemote) {
                PlaceCl d = new PlaceCl();
                d.setName(pr.getName());
                d.setLocationTypeID(pr.getLocationTypeID());
                d.setId(pr.getId().intValue());
                d.setParentID(pr.getParentID().intValue());

                res.add(d);
            }
        } catch (java.rmi.RemoteException e) {

            throw new BeanException(e);
        }
        return res;
    }

    @Override
    public Collection<Device> getAllDevice() throws BeanException {
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findAllDevices());

        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        } catch (FinderException e) {
            throw new BeanException(e);
        }
    }

    @Override
    public Collection<Device> getChildDevices(int deviceId) throws BeanException {
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findChildDevices(new Long(deviceId)));
        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        } catch (FinderException e) {
            new BeanException(e);
        }
        return null;
    }

    @Override
    public Collection<Device> getChildDevicesPorts(int deviceId)
            throws BeanException {
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findChildDevicesPorts(new Long(deviceId)));
        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        } catch (FinderException e) {
            new BeanException(e);
        }
        return null;
    }

    @Override
    public Collection<Device> getChildDevicesSlots(int deviceId)
            throws BeanException {
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findChildDevicesSlots(new Long(deviceId)));
        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        } catch (FinderException e) {
            new BeanException(e);
        }
        return null;
    }

    @Override
    public Device getDeviceByID(int id) throws ModelException, BeanException, FinderException {
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            
            return getDeviceFromDeviceRemote(devicesH.findByPrimaryKey(new Long(id)));
        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        } 
		
        
    }

    private Device getDeviceFromDeviceRemote(DevicesRemote devRemote) throws BeanException {
    	Device dev = new Device();
        try{
            dev.setDeviceTypeID(devRemote.getDeviceTypeID().intValue());
            dev.setDevName(devRemote.getDevName());
            dev.setId(devRemote.getId().intValue());
            dev.setParentID(devRemote.getParentID().intValue());
            dev.setPlaceID(devRemote.getPlaceID().intValue()); 
        }catch(RemoteException e) {
            throw new BeanException(e);
        }
    	return dev;
    }

    @Override
    public void removeDevice(int deviceID) throws BeanException {
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            devicesH.removeById(new Long(deviceID));
            
        } catch (NamingException e) {
            throw new BeanException(e);
        } catch (FinderException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        }
    }

    @Override
    public void update(Integer ID, String devicename) throws BeanException {
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            devicesH.updateDevice(ID.longValue(), devicename);

        } catch (NamingException e) {
            throw new BeanException(e);
        }  catch (FinderException e) {
            throw new BeanException(e);
        } catch (RemoteException e) {
            throw new BeanException(e);
        }
    }

}
