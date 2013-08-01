package edu.sumdu.group5.lab4.ejb.devices;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Home interface of Devices bean
 * @author Sergey, Artem
 */
public interface DevicesHome extends EJBHome{

	/**
     * 
     * @param devName - name of the device
     * @param devTypeId - device type id
     * @param parentId - parent id
     * @param placeId - id of location
     * @return Remote Interface
     * @throws CreateException
     * @throws RemoteException
     */
	public DevicesRemote create(String devName, Long devTypeId, Long parentId, Long placeId) throws RemoteException, CreateException;
	
	/**
     * 
     * @param id - id of device
     * @return Remote Interface
     * @throws RemoteException
     * @throws FinderException
     */
	public DevicesRemote findByPrimaryKey(Long id) throws RemoteException, FinderException;
	
	/**
     * @return collection of remote interfaces
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection<DevicesRemote> findAllDevices() throws RemoteException, FinderException;
	
	/**
     * 
     * @param id - id of location
     * @return Collection of remote interfaces
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection<DevicesRemote> findRootDevicesByPlaceID(Long id) throws RemoteException, FinderException;
	
	/**
     * 
     * @param deviceId - id of the device
     * @return Collection of remote interfaces
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection<DevicesRemote> findChildDevices(Long deviceId) throws RemoteException, FinderException;
	
	/**
     * 
     * @param deviceId - id of the device
     * @return Collection of remote interfaces
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection<DevicesRemote> findChildDevicesSlots(Long deviceId) throws RemoteException, FinderException;
	
	/**
     * 
     * @param deviceId - id of the device
     * @return Collection of remote interfaces
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection<DevicesRemote> findChildDevicesPorts(Long deviceId) throws RemoteException, FinderException;
	
}
