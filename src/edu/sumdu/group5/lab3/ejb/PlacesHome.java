package edu.sumdu.group5.lab3.ejb;

import edu.sumdu.group5.lab3.model.Place;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface PlacesHome extends EJBHome {
	public PlacesRemote create() throws RemoteException, CreateException;
	public PlacesRemote findByPrimaryKey(Long id) throws RemoteException, FinderException;
	public Collection findAllLocation() throws RemoteException, FinderException;
}
