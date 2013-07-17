package edu.sumdu.group5.lab3.ejb;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface DevicesHome extends EJBHome{

	public DevicesRemote create() throws RemoteException, CreateException;
	public DevicesRemote findByPrimaryKey(Long id) throws RemoteException, FinderException;
	//public Collection<DevicesRemote> findAllDevices() throws RemoteException, FinderException;
	public Collection<DevicesRemote> findRootDevicesByPlaceID(Long id) throws RemoteException, FinderException;
}
