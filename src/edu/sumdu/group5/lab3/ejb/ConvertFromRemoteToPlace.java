package edu.sumdu.group5.lab3.ejb;

import edu.sumdu.group5.lab3.model.Place;
import edu.sumdu.group5.lab3.dao.BeanException;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: setk0613
 * Date: 15.07.2013
 * Time: 18:10:42
 * To change this template use File | Settings | File Templates.
 */
public class ConvertFromRemoteToPlace {
    static List<Place> places = new LinkedList<Place>();

    public static List<Place> convert(Collection placesR) throws BeanException {
     for(Iterator it=placesR.iterator();it.hasNext();) {
                PlacesRemote pr= (PlacesRemote) it.next();
         try {
             places.add(new Place(pr.getId().intValue(),pr.getName(),pr.getLocationTypeID(),pr.getParentID().intValue()));
         } catch (RemoteException e) {
             throw new BeanException (e);
         }
     }
        return places;
    }
}
