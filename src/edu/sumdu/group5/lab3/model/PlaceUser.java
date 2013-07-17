package edu.sumdu.group5.lab3.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

/**
 * Author Sergey & Artem
 */

public class PlaceUser {

    protected Collection placeList = new ArrayList<PlaceCl>();

    public PlaceUser() {
    }

    public boolean hasPlaces() {
        return !placeList.isEmpty();
    }

    public void addPlace(PlaceCl aPlace) {
        placeList.add(aPlace);
    }

    public void removePlace(PlaceCl aPlace) {
        placeList.remove(aPlace);
    }

    public void removePlace(int id) {
        PlaceCl toRemove = findPlace(id);
        placeList.remove(toRemove);
    }

    public PlaceCl findPlace(int id) {
        PlaceCl found = null;

        Iterator<PlaceCl> iterator = placeList.iterator();
        while (iterator.hasNext()) {
            PlaceCl current = (PlaceCl) iterator.next();
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
