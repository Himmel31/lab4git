package edu.sumdu.group5.lab4.ejb.devices;

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

    /**
     *
     * @return  id of type of the device
     */
    public void setDeviceTypeID(Long id) throws RemoteException;

    /**
     *
     * @return  id of device
     */
    public void setId(Long id) throws RemoteException;

    /**
     *
     * @return  name of device
     */
    public void setDevName(String str) throws RemoteException;

    /**
     *
     * @return parent id of device
     */
    public void setParentID(Long id) throws RemoteException;

    /**
     *
     * @return place id of device
     */
    public void setPlaceID(Long id) throws RemoteException;
}
