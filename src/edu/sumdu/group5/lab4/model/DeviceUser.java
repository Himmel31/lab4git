package edu.sumdu.group5.lab4.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.sumdu.group5.lab4.model.Device;

import org.apache.log4j.Logger;

/**
 * Author Sergey & Roman
 */

public class DeviceUser {

    /** The logger */
    private static final Logger log = Logger.getLogger(DeviceUser.class);

    protected List<Device> deviceList = new ArrayList<Device>();
    protected HashMap<Integer, String> deviceTypeMap = new HashMap<Integer, String>();

    public DeviceUser() {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
    }

    public boolean hasDevices() {
        if (log.isDebugEnabled())
            log.debug("Method call");
        return !deviceList.isEmpty();
    }

    public void addDevice(Device aDevice) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + aDevice);
        deviceList.add(aDevice);
    }

    public void removeDevice(Device aDevice) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + aDevice);
        deviceList.remove(aDevice);
    }

    public void removeDevice(int id) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
        Device toRemove = findDevice(id);
        deviceList.remove(toRemove);
    }

    protected Device findDevice(int id) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
        Device found = null;

        Iterator<Device> iterator = deviceList.iterator();
        while (iterator.hasNext()) {
            Device current = (Device) iterator.next();
            if (current.getId() == id)
                found = current;
        }
        return found;
    }

    public List<Device> getDeviceList() {   
        if (log.isDebugEnabled())
            log.debug("Method call");
        return deviceList;
    }

    public void setDeviceList(List<Device> devices) {
        if (log.isDebugEnabled())
            log.debug("Method call");
        if (log.isDebugEnabled())
            log.debug("Method call");
        this.deviceList = devices;
    }

    public void setIdDevicesTypes(HashMap<Integer, String> deviceTypeMap) {
        if (log.isDebugEnabled())
            log.debug("Method call");
        this.deviceTypeMap = deviceTypeMap;
    }

    public HashMap<Integer, String> getIdDevicesTypes() {
        if (log.isDebugEnabled())
            log.debug("Method call");
        return deviceTypeMap;
    }

}
