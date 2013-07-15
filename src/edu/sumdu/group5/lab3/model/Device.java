package edu.sumdu.group5.lab3.model;

import java.io.Serializable;

/**
 * Author Sergey
 */

public class Device implements Serializable {

    protected int id;
    protected String devName;
    protected int parentID;
    protected int placeID;
    protected int deviceTypeID;
    protected DeviceUser du;

    public Device(int id, String devName, int deviceTypeID, int parentID, int placeID) {
        this.id = id;
        this.devName = devName;
        this.parentID = parentID;
        this.placeID = placeID;
        this.deviceTypeID = deviceTypeID;
    }

    public Device() {
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
