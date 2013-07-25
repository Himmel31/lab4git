package edu.sumdu.group5.lab4.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

import org.apache.log4j.Logger;

/**
 * Author Sergey & Artem
 */

public class PlaceUser {

    /** The logger */
    private static final Logger log = Logger.getLogger(PlaceUser.class);

    protected Collection placeList = new ArrayList<Place>();

    public PlaceUser() {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
    }

    public boolean hasPlaces() {
        if (log.isDebugEnabled())
            log.debug("Method call");
        return !placeList.isEmpty();
    }

    public void addPlace(Place aPlace) {
        if (log.isDebugEnabled())
            log.debug("Method call");
        placeList.add(aPlace);
    }

    public void removePlace(Place aPlace) { 
        if (log.isDebugEnabled())
            log.debug("Method call");
        placeList.remove(aPlace);
    }

    public void removePlace(int id) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
        Place toRemove = findPlace(id);
        placeList.remove(toRemove);
    }

    public Place findPlace(int id) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
        Place found = null;

        Iterator<Place> iterator = placeList.iterator();
        while (iterator.hasNext()) {
            Place current = (Place) iterator.next();
            if (current.getId() == id)
                found = current;
        }
        return found;
    }

    public Collection getPlace() {
        if (log.isDebugEnabled())
            log.debug("Method call");
        return placeList;
    }

    public void setPlaces(Collection Places) {
        if (log.isDebugEnabled())
            log.debug("Method call");
        this.placeList = Places;
    }
}
