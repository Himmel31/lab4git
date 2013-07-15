package edu.sumdu.group5.lab3.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

/**
 * Author Sergey & Artem
 */

public class PlaceUser {

    protected Collection placeList = new ArrayList<Place>();

    public PlaceUser() {
    }

    public boolean hasPlaces() {
        return !placeList.isEmpty();
    }

    public void addPlace(Place aPlace) {
        placeList.add(aPlace);
    }

    public void removePlace(Place aPlace) {
        placeList.remove(aPlace);
    }

    public void removePlace(int id) {
        Place toRemove = findPlace(id);
        placeList.remove(toRemove);
    }

    public Place findPlace(int id) {
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
        return placeList;
    }

    public void setPlaces(Collection Places) {
        this.placeList = Places;
    }
}
