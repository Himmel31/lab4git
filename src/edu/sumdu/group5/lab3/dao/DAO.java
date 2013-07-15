package edu.sumdu.group5.lab3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Collection;

import javax.ejb.FinderException;

import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.model.Place;
import edu.sumdu.group5.lab3.model.PlaceUser;

public interface DAO {
    public void add(Device device) throws ModelException;

    public Collection findAllLocation() throws ModelException, FinderException, BeanException;

    public List<Device> getAllDevice() throws ModelException;

    public List<Device> getChildDevices(int deviceID) throws ModelException;

    public List<Device> getChildDevicesPorts(int deviceID) throws ModelException;

    public List<Device> getChildDevicesSlots(int deviceID) throws ModelException;

    public Device getDeviceByID(int id) throws ModelException;

    public HashMap<Integer, String> getIdDevicesTypes() throws ModelException;

    public List<Device> getRootDevicesByPlaceID(int ID) throws ModelException;

    public void removeDevice(int deviceID) throws ModelException;

    public void update(Integer ID, String devicename) throws ModelException;
}
