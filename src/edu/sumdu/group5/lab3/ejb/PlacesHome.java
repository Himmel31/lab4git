package edu.sumdu.group5.lab3.ejb;

import edu.sumdu.group5.lab3.model.PlaceCl;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface PlacesHome extends EJBHome {
	public PlacesRemote create() throws RemoteException, CreateException;
	public PlacesRemote findByPrimaryKey(Long id) throws RemoteException, FinderException;
	public Collection<PlacesRemote> findAllLocation() throws RemoteException, FinderException;
}
