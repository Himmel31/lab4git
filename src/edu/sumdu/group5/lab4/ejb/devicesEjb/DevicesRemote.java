package edu.sumdu.group5.lab4.ejb.devicesEjb;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Remote Interface of Devices bean
 * @author Sergey, Artem
 */
public interface DevicesRemote extends EJBObject{

	/**
     * 
     * @return  id of type of the device
     */
	public Long getDeviceTypeID() throws RemoteException;

	/**
     * 
     * @return  id of device
     */
    public Long getId() throws RemoteException;

    /**
     * 
     * @return  name of device
     */
    public String getDevName() throws RemoteException;

    /**
     * 
     * @return parent id of device
     */
    public Long getParentID() throws RemoteException;

    /**
     * 
     * @return place id of device
     */
    public Long getPlaceID() throws RemoteException;

}
