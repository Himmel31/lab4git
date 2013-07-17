package edu.sumdu.group5.lab3.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Collection;

import javax.ejb.FinderException;

import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.model.PlaceCl;
import edu.sumdu.group5.lab3.model.PlaceUser;

public interface DAO {
    public void add(Device device) throws ModelException, BeanException;

    public Collection findAllLocation() throws ModelException, FinderException, BeanException;

    public Collection<Device> getAllDevice() throws ModelException, BeanException;

    public Collection<Device> getChildDevices(int deviceID) throws ModelException, BeanException;

    public Collection<Device> getChildDevicesPorts(int deviceID) throws ModelException, BeanException;

    public Collection<Device> getChildDevicesSlots(int deviceID) throws ModelException, BeanException;

    public Device getDeviceByID(int id) throws ModelException, BeanException;

    public HashMap<Integer, String> getIdDevicesTypes() throws ModelException, BeanException;

    public Collection getRootDevicesByPlaceID(int ID) throws ModelException, BeanException, FinderException;

    public void removeDevice(int deviceID) throws ModelException, BeanException;

    public void update(Integer ID, String devicename) throws ModelException, BeanException;
}
