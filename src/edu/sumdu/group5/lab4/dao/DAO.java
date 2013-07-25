package edu.sumdu.group5.lab4.dao;


import java.util.Collection;
import javax.ejb.FinderException;

import edu.sumdu.group5.lab4.model.Device;
import edu.sumdu.group5.lab4.model.ModelException;

public interface DAO {
    public void add(Device device) throws ModelException, BeanException;

    public Collection findAllLocation() throws ModelException, FinderException, BeanException;

    public Collection<Device> getAllDevice() throws ModelException, BeanException;

    public Collection<Device> getChildDevices(int deviceID) throws ModelException, BeanException;

    public Collection<Device> getChildDevicesPorts(int deviceID) throws ModelException, BeanException;

    public Collection<Device> getChildDevicesSlots(int deviceID) throws ModelException, BeanException;

    public Device getDeviceByID(int id) throws ModelException, BeanException, FinderException;

    public Collection getRootDevicesByPlaceID(int ID) throws ModelException, BeanException, FinderException;

    public void removeDevice(int deviceID) throws ModelException, BeanException;

    public void update(Integer ID, String devicename) throws ModelException, BeanException;
}
