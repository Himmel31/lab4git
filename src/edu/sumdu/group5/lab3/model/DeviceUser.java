package edu.sumdu.group5.lab3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.sumdu.group5.lab3.model.Device;

/**
 * Author Sergey & Roman
 */

public class DeviceUser {

    protected List<Device> deviceList = new ArrayList<Device>();
    protected HashMap<Integer, String> deviceTypeMap = new HashMap<Integer, String>();

    public DeviceUser() {
    }

    public boolean hasDevices() {
        return !deviceList.isEmpty();
    }

    public void addDevice(Device aDevice) {
        deviceList.add(aDevice);
    }

    public void removeDevice(Device aDevice) {
        deviceList.remove(aDevice);
    }

    public void removeDevice(int id) {
        Device toRemove = findDevice(id);
        deviceList.remove(toRemove);
    }

    protected Device findDevice(int id) {
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
        return deviceList;
    }

    public void setDeviceList(List<Device> devices) {
        this.deviceList = devices;
    }

    public void setIdDevicesTypes(HashMap<Integer, String> deviceTypeMap) {
        this.deviceTypeMap = deviceTypeMap;
    }

    public HashMap<Integer, String> getIdDevicesTypes() {
        return deviceTypeMap;
    }

}
