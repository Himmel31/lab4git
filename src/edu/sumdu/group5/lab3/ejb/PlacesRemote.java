package edu.sumdu.group5.lab3.ejb;

import edu.sumdu.group5.lab3.model.PlaceCl;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import javax.sql.DataSource;

public interface PlacesRemote extends EJBObject{

    public int getLocationTypeID() throws RemoteException;

    public void setLocationTypeID(int locationTypeID) throws RemoteException;

    public void setId(Long id) throws RemoteException;

    public void setParentID(Long parentID) throws RemoteException;

    public String getName() throws RemoteException;

    public void setName(String name) throws RemoteException;

    public Long getId() throws RemoteException;

    public Long getParentID() throws RemoteException;

}
