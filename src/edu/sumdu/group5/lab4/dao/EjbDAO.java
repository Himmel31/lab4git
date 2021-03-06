package edu.sumdu.group5.lab4.dao;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import edu.sumdu.group5.lab4.ejb.devices.DevicesHome;
import edu.sumdu.group5.lab4.ejb.devices.DevicesRemote;
import edu.sumdu.group5.lab4.ejb.places.PlacesHome;
import edu.sumdu.group5.lab4.ejb.places.PlacesRemote;
import edu.sumdu.group5.lab4.model.Device;
import edu.sumdu.group5.lab4.model.ModelException;
import edu.sumdu.group5.lab4.model.Place;

import org.apache.log4j.Logger;

public class EjbDAO implements DAO {

    /** The logger */
    private static final Logger log = Logger.getLogger(EjbDAO.class);

    InitialContext in = null;
    Properties prop = null;
    PlacesHome placesH;
    DevicesHome devicesH;

    public EjbDAO() {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
    }

    private void setInitialContext() throws NamingException {
        if (log.isDebugEnabled())
            log.debug("Method call");
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
            throws ModelException, FinderException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findRootDevicesByPlaceID(new Long(id)));

        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    }

    private Collection<Device> getDevicesListFromRemoteDevicesList(
            Collection<DevicesRemote> devRemote) throws ModelException {
        if (log.isDebugEnabled())
        log.debug("Method call");
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
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
        return dev;
    }

    @Override
    public void add(Device device) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + device);
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            devicesH.create(device.getDevName(), new Long(device.getDeviceTypeID()),
                    new Long(device.getParentID()), new Long(device.getPlaceID()));

        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (CreateException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    }
    
    @Override
    public Collection<Place> findAllLocation() throws ModelException,
            FinderException, ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("PlacesEJB");
            placesH = (PlacesHome) PortableRemoteObject.narrow(obj,
                    PlacesHome.class);

            return getLocationList(placesH.findAllLocation());

        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    }

    private Collection<Place> getLocationList(
            Collection<PlacesRemote> plRemote) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        Collection<Place> res = new LinkedList();
        try {
            for (PlacesRemote pr : plRemote) {
                Place p = new Place();
                p.setName(pr.getName());
                p.setLocationTypeID(pr.getLocationTypeID());
                p.setId(pr.getId().intValue());
                p.setParentID(pr.getParentID().intValue());

                res.add(p);
            }
        } catch (java.rmi.RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
        return res;
    }

    @Override
    public Collection<Device> getAllDevice() throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findAllDevices());

        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (FinderException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    }

    @Override
    public Collection<Device> getChildDevices(int deviceId) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceId);
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            return getDevicesListFromRemoteDevicesList(devicesH
                    .findChildDevices(new Long(deviceId)));
        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (FinderException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    }

    @Override
    public Collection<Device> getChildDevicesPorts(int deviceId)
            throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceId);
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findChildDevicesPorts(new Long(deviceId)));
        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (FinderException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    }

    @Override
    public Collection<Device> getChildDevicesSlots(int deviceId)
            throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceId);
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);

            return getDevicesListFromRemoteDevicesList(devicesH
                    .findChildDevicesSlots(new Long(deviceId)));
        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (FinderException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    }

    @Override
    public Device getDeviceByID(int id) throws ModelException, ModelException, FinderException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            
            return getDeviceFromDeviceRemote(devicesH.findByPrimaryKey(new Long(id)));
        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }		
        
    }

    private Device getDeviceFromDeviceRemote(DevicesRemote devRemote) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
    	Device dev = new Device();
        try{
            dev.setDeviceTypeID(devRemote.getDeviceTypeID().intValue());
            dev.setDevName(devRemote.getDevName());
            dev.setId(devRemote.getId().intValue());
            dev.setParentID(devRemote.getParentID().intValue());
            dev.setPlaceID(devRemote.getPlaceID().intValue()); 
        }catch(RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    	return dev;
    }

    @Override
    public void removeDevice(int deviceID) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceID);
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            DevicesRemote rem = (DevicesRemote) devicesH.findByPrimaryKey(Long.valueOf(deviceID));
            rem.remove();
            //devicesH.removeById(new Long(deviceID));
            
        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (FinderException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoveException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void update(Integer id, String deviceName) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id + " " + deviceName);
        try {
            if (in == null)
                setInitialContext();
            Object obj = in.lookup("DevicesEJB");
            devicesH = (DevicesHome) PortableRemoteObject.narrow(obj,
                    DevicesHome.class);
            DevicesRemote rem = (DevicesRemote) devicesH.findByPrimaryKey(Long.valueOf(id));
            rem.setDevName(deviceName);

        } catch (NamingException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (RemoteException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (FinderException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }
    }

}
