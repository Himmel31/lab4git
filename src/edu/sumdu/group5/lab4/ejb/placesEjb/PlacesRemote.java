package edu.sumdu.group5.lab4.ejb.placesEjb;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

/**
 * Remote Interface of Places bean
 * @author Sergey, Artem
 */
public interface PlacesRemote extends EJBObject{

	/**
     * @return  id of location type
     */
    public int getLocationTypeID() throws RemoteException;

    /**
     * 
     * @return  name of place
     */
    public String getName() throws RemoteException;

    /**
     * 
     * @return  id of place
     */
    public Long getId() throws RemoteException;

    /**
     * 
     * @return  parent id of place
     */
    public Long getParentID() throws RemoteException;

}
