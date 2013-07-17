package edu.sumdu.group5.lab3.model;

import java.io.Serializable;

/**
 * Author Sergey
 */

@SuppressWarnings("rawtypes")
public class PlaceCl implements Comparable, Serializable {

	public String name;
	public int id;
	public int parentID;
	public int locationTypeID;

    public PlaceCl(int id, String name, int locationTypeID, int parentID) {
        this.locationTypeID = locationTypeID;
        this.name = name;
        this.id = id;
        this.parentID = parentID;
    }
    
    public PlaceCl() {
    }
    
    public int getLocationTypeID() {
        return locationTypeID;
    }

    public void setLocationTypeID(int locationTypeID) {
        this.locationTypeID = locationTypeID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getParentID() {
        return parentID;
    }

    @Override
    public int compareTo(Object obj) {
        PlaceCl entry = (PlaceCl) obj;

        int result = name.compareTo(entry.name);
        if (result != 0) {
            return result;
        }
        return 0;
    }


}
