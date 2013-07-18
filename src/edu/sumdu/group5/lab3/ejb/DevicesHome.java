package edu.sumdu.group5.lab3.ejb;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

public interface DevicesHome extends EJBHome{

	public DevicesRemote create(String devName, Long devTypeId, Long parentId, Long placeId) throws RemoteException, CreateException;
	public DevicesRemote findByPrimaryKey(Long id) throws RemoteException, FinderException;
	public void updateDevice (Long IdDevice, String deviceName) throws FinderException,RemoteException;
	public Collection<DevicesRemote> findAllDevices() throws RemoteException, FinderException;
	public Collection<DevicesRemote> findRootDevicesByPlaceID(Long id) throws RemoteException, FinderException;
	public Collection<DevicesRemote> findChildDevices(Long deviceId) throws RemoteException, FinderException;
	public Collection<DevicesRemote> findChildDevicesSlots(Long deviceId) throws RemoteException, FinderException;
	public Collection<DevicesRemote> findChildDevicesPorts(Long deviceId) throws RemoteException, FinderException;
	public void removeById(Long id) throws FinderException,RemoteException;
	
}
