package edu.sumdu.group5.lab4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.sumdu.group5.lab4.dao.ConnectionFactory;
import edu.sumdu.group5.lab4.model.Device;
import edu.sumdu.group5.lab4.model.ModelException;
import edu.sumdu.group5.lab4.model.Place;
import edu.sumdu.group5.lab4.model.PlaceUser;

import org.apache.log4j.Logger;

/**
 * Author Sergey & Artem & Roman
 */

public class JdbcDAO implements DAO {

    
    /** The logger */
    private static final Logger log = Logger.getLogger(JdbcDAO.class);

    Connection con = null;
    PreparedStatement ptmt = null;
    ResultSet rs = null;

    private final String driverManager ="DriverManager";

    public JdbcDAO() {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
    }

    private Connection getConnection() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        con = ConnectionFactory.getConnection();
        return con;
    }

    private void closeConnection() throws ConnectionException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        ConnectionFactory.closeConnection();
    }

    public HashMap<Integer, String> getIdDevicesTypes() throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        HashMap<Integer, String> deviceTypeMap = new HashMap<Integer, String>();
        try {
            String querystring = "SELECT * FROM device_type";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            rs = ptmt.executeQuery();

            while (rs.next()) {
                Integer deviceTypeID = rs.getInt("id_device_type");
                String deviceName = rs.getString("device_name");
                deviceTypeMap.put(deviceTypeID, deviceName);
            }

        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
        return deviceTypeMap;
    }

    public void add(Device device) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + device);
        try {
            String querystring = "INSERT INTO devices VALUES(?,?,?,?,?)";
            con = getConnection();
            ptmt = con.prepareStatement(querystring, PreparedStatement.RETURN_GENERATED_KEYS);
            ptmt.setString(1, null);
            ptmt.setString(2, device.getDevName());
            ptmt.setInt(3, device.getDeviceTypeID());
            if (device.getParentID() != 0) {
                ptmt.setInt(4, device.getParentID());
            } else {
                ptmt.setString(4, null);
            }
            ptmt.setInt(5, device.getPlaceID());
            ptmt.executeUpdate();
        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
    }

    public void update(Integer IDdevice, String deviceName) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + IDdevice + " " + deviceName);
        try {
            String querystring = "UPDATE devices SET device_name=? WHERE id_device=?;";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, deviceName);
            ptmt.setInt(2, IDdevice);
            ptmt.executeUpdate();

        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
    }

    public List<Place> findAllLocation() throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        List<Place> places = new ArrayList<Place>();
        try {
            String querystring = "SELECT * FROM places";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_place");
                String placeName = rs.getString("place_name");
                Integer locationTypeID = rs.getInt("id_location_type");
                Integer parentID = rs.getInt("id_parent");
                Place pl = new Place(id, placeName, locationTypeID, parentID);
                places.add(pl);
            }
        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
        return places;
    }

    public Device getDeviceByID(int id) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + id);
        Device device = null;
        try {
            String querystring = "SELECT * FROM devices WHERE id_device=?";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(id));
            rs = ptmt.executeQuery();
            if (rs.next()) {
                Integer deviceID = rs.getInt("id_device");
                String deviceName = rs.getString("device_name");
                Integer deviceTypeID = rs.getInt("id_device_type");
                Integer parentID = rs.getInt("id_parent");
                Integer placeID = rs.getInt("id_place");
                device = new Device(deviceID, deviceName, deviceTypeID, parentID, placeID);

            }
        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
        return device;
    }


    public List<Device> getAllDevice() throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        List<Device> devices = new ArrayList<Device>();
        try {
            String querystring = "SELECT * FROM devices";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            rs = ptmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_device");
                String deviceName = rs.getString("device_name");
                Integer deviceTypeID = rs.getInt("id_device_type");
                Integer parentID = rs.getInt("id_parent");
                Integer placeID = rs.getInt("id_place");
                Device dev = new Device(id, deviceName, deviceTypeID, parentID, placeID);
                devices.add(dev);
            }
        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
    }

    public List<Device> getRootDevicesByPlaceID(int ID) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + ID);
        List<Device> devices = new ArrayList<Device>();
        try {
            String querystring = "SELECT * FROM devices where id_place=? and id_parent is NULL";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(ID));
            rs = ptmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_device");
                String deviceName = rs.getString("device_name");
                Integer deviceTypeID = rs.getInt("id_device_type");
                Integer parentID = rs.getInt("id_parent");
                Integer placeID = rs.getInt("id_place");
                Device dev = new Device(id, deviceName, deviceTypeID, parentID, placeID);
                devices.add(dev);
            }

        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
    }

    public List<Device> getChildDevices(int deviceID) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceID);
        List<Device> devices = new ArrayList<Device>();
        try {
            String querystring = "SELECT * FROM devices where id_parent=?";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceID));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id_device");
                String deviceName = rs.getString("device_name");
                Integer deviceTypeID = rs.getInt("id_device_type");
                Integer parentID = rs.getInt("id_parent");
                Integer placeID = rs.getInt("id_place");
                Device dev = new Device(id, deviceName, deviceTypeID, parentID, placeID);
                devices.add(dev);
            }

        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();             
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
    }

    public List<Device> getChildDevicesSlots(int deviceID) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceID);
        List<Device> devices = new ArrayList<Device>();
        try {
            String querystring = "SELECT * FROM devices where id_parent=? and id_device_type=2";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceID));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id_device");
                String deviceName = rs.getString("device_name");
                Integer deviceTypeID = rs.getInt("id_device_type");
                Integer parentID = rs.getInt("id_parent");
                Integer placeID = rs.getInt("id_place");
                Device dev = new Device(id, deviceName, deviceTypeID, parentID, placeID);
                devices.add(dev);
            }

        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
    }

    public List<Device> getChildDevicesPorts(int deviceID) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceID);
        List<Device> devices = new ArrayList<Device>();
        try {
            String querystring = "SELECT * FROM devices where id_parent=? and id_device_type=4";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceID));
            rs = ptmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id_device");
                String deviceName = rs.getString("device_name");
                Integer deviceTypeID = rs.getInt("id_device_type");
                Integer parentID = rs.getInt("id_parent");
                Integer placeID = rs.getInt("id_place");
                Device dev = new Device(id, deviceName, deviceTypeID, parentID, placeID);
                devices.add(dev);
            }

        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }
        return devices;
    }


    public void removeDevice(int deviceID) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + deviceID);
        try {
            String querystring = "DELETE FROM devices WHERE id_device=? or id_parent=?";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceID));
            ptmt.setString(2, String.valueOf(deviceID));
            ptmt.executeUpdate();
        } catch (SQLException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } catch (DaoException e) {
            ModelException e1 = new ModelException(e);
            log.error("Exception", e1);
            throw e1;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            } catch (ConnectionException e) {
                ModelException e1 = new ModelException(e);
                log.error("Exception", e1);
                throw e1;
            }
        }

    }

}
