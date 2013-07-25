package edu.sumdu.group5.lab4.ejb.placesEjb;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Home interface of Places bean
 * @author Sergey, Artem
 */
public interface PlacesHome extends EJBHome {
	
	/**
     * 
     * @return Remote Interface
     * @throws CreateException
     * @throws RemoteException
     */
	public PlacesRemote create() throws RemoteException, CreateException;
	
	/**
     * 
     * @param id - id of location
     * @return Remote Interface
     * @throws RemoteException
     * @throws FinderException
     */
	public PlacesRemote findByPrimaryKey(Long id) throws RemoteException, FinderException;
	
	/**
     * @return collection of remote interfaces
     * @throws FinderException
     * @throws RemoteException
     */
	public Collection<PlacesRemote> findAllLocation() throws RemoteException, FinderException;
}
