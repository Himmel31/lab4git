package edu.sumdu.group5.lab4.model;

import java.io.Serializable;
import org.apache.log4j.Logger;
/**
 * Author Sergey
 */

@SuppressWarnings("rawtypes")
public class Place implements Comparable, Serializable {

    /** The logger */
    private static final Logger log = Logger.getLogger(Place.class);

	@Override
	public String toString() {
		return "Place [name=" + name + ", id=" + id + ", parentID=" + parentID
				+ ", locationTypeID=" + locationTypeID + "]";
	}

	public String name;
	public int id;
	public int parentID;
	public int locationTypeID;

    public Place(int id, String name, int locationTypeID, int parentID) {
        if (log.isDebugEnabled())
            log.debug("Constructor call. Arguments: id = " + id + " name = " + name + " locationTypeID = " + locationTypeID + " parentID = " + parentID);
        this.locationTypeID = locationTypeID;
        this.name = name;
        this.id = id;
        this.parentID = parentID;
    }
    
    public Place() {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
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
        Place entry = (Place) obj;

        int result = name.compareTo(entry.name);
        if (result != 0) {
            return result;
        }
        return 0;
    }


}
