package edu.sumdu.group5.lab4.model;

import java.io.Serializable;
import org.apache.log4j.Logger;

/**
 * Author Sergey
 */

public class Device implements Serializable {

    /** The logger */
    private static final Logger log = Logger.getLogger(Device.class);

    protected int id;
    @Override
	public String toString() {
		return "Device [id=" + id + ", devName=" + devName + ", parentID="
				+ parentID + ", placeID=" + placeID + ", deviceTypeID="
				+ deviceTypeID + ", du=" + du + "]";
	}

	protected String devName;
    protected int parentID;
    protected int placeID;
    protected int deviceTypeID;
    protected DeviceUser du;

    public Device(int id, String devName, int deviceTypeID, int parentID, int placeID) {
        if (log.isDebugEnabled())
            log.debug("Constructor call. Arguments: id = " + id + " devName = " + devName + " deviceTypeID = " + deviceTypeID + " parentID = " + parentID + " placeID = " + placeID);
        this.id = id;
        this.devName = devName;
        this.parentID = parentID;
        this.placeID = placeID;
        this.deviceTypeID = deviceTypeID;
    }

    public Device() {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
    }

    public int getDeviceTypeID() {
        return deviceTypeID;
    }

    public void setDeviceTypeID(int deviceTypeID) {
        this.deviceTypeID = deviceTypeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public Device getChildCard(int id) {
        Device card = null;

        return card;

    }


}
