package edu.sumdu.group5.lab3.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface DevicesRemote extends EJBObject{

	public int getDeviceTypeID() throws RemoteException;

    public void setDeviceTypeID(int deviceTypeID) throws RemoteException;

    public int getId() throws RemoteException;

    public void setId(int id) throws RemoteException;

    public String getDevName() throws RemoteException;

    public void setDevName(String devName) throws RemoteException;

    public int getParentID() throws RemoteException;

    public void setParentID(int parentID) throws RemoteException;

    public int getPlaceID() throws RemoteException;

    public void setPlaceID(int placeID) throws RemoteException;
}
