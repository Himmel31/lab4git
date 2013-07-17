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
	public void update (Integer IdDevice, String deviceName) throws RemoteException;
	public Collection<DevicesRemote> findAllDevices() throws RemoteException, FinderException;
	public Collection<DevicesRemote> findRootDevicesByPlaceID(Long id) throws RemoteException, FinderException;
	public Collection<DevicesRemote> findChildDevices(int deviceId) throws RemoteException, FinderException;
	public Collection<DevicesRemote> findChildDevicesSlots(int deviceId) throws RemoteException, FinderException;
	public Collection<DevicesRemote> findChildDevicesPorts(int deviceId) throws RemoteException, FinderException;
	public void remove(long id) throws RemoveException, RemoteException;
	public HashMap<Integer, String> getIdDevicesTypes()throws RemoteException;
}
