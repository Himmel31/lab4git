package edu.sumdu.group5.lab3.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface DevicesRemote extends EJBObject{

	public Long getDeviceTypeID() throws RemoteException;

    public void setDeviceTypeID(Long deviceTypeID) throws RemoteException;

    public Long getId() throws RemoteException;

    public void setId(Long id) throws RemoteException;

    public String getDevName() throws RemoteException;

    public void setDevName(String devName) throws RemoteException;

    public Long getParentID() throws RemoteException;

    public void setParentID(Long parentID) throws RemoteException;

    public Long getPlaceID() throws RemoteException;

    public void setPlaceID(Long placeID) throws RemoteException;
}
