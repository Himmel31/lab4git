package edu.sumdu.group5.lab3.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.sumdu.group5.lab3.dao.ConnectionFactory;
import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.model.PlaceCl;
import edu.sumdu.group5.lab3.model.PlaceUser;

/**
 * Author Sergey & Artem & Roman
 */

public class JdbcDAO implements DAO {

    Connection con = null;
    PreparedStatement ptmt = null;
    ResultSet rs = null;

    public JdbcDAO() {
    }

    private Connection getConnection() throws DaoException {
        con = ConnectionFactory.getConnection();
        return con;
    }

    private void closeConnection() throws ConnectionException {
        ConnectionFactory.closeConnection();
    }

    public HashMap<Integer, String> getIdDevicesTypes() throws ModelException {
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
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
        return deviceTypeMap;
    }

    public void add(Device device) throws ModelException {
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
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
    }

    public void update(Integer IDdevice, String deviceName) throws ModelException {
        try {
            String querystring = "UPDATE devices SET device_name=? WHERE id_device=?;";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, deviceName);
            ptmt.setInt(2, IDdevice);
            ptmt.executeUpdate();

        } catch (SQLException e) {
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
    }

    public List<PlaceCl> findAllLocation() throws ModelException {
        List<PlaceCl> places = new ArrayList<PlaceCl>();
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
                PlaceCl pl = new PlaceCl(id, placeName, locationTypeID, parentID);
                places.add(pl);
            }
        } catch (SQLException e) {
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
        return places;
    }

    public Device getDeviceByID(int id) throws ModelException {
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
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
        return device;
    }


    public List<Device> getAllDevice() throws ModelException {
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
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
        return devices;
    }

    public List<Device> getRootDevicesByPlaceID(int ID) throws ModelException {
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
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
        return devices;
    }

    public List<Device> getChildDevices(int deviceID) throws ModelException {
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
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();             
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
        return devices;
    }

    public List<Device> getChildDevicesSlots(int deviceID) throws ModelException {
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
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
        return devices;
    }

    public List<Device> getChildDevicesPorts(int deviceID) throws ModelException {
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
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();                 
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }
        return devices;
    }


    public void removeDevice(int deviceID) throws ModelException {
        try {
            String querystring = "DELETE FROM devices WHERE id_device=? or id_parent=?";
            con = getConnection();
            ptmt = con.prepareStatement(querystring);
            ptmt.setString(1, String.valueOf(deviceID));
            ptmt.setString(2, String.valueOf(deviceID));
            ptmt.executeUpdate();
        } catch (SQLException e) {
            throw new ModelException(e);
        } catch (DaoException e) {
            throw new ModelException(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ptmt != null)
                    ptmt.close();
                closeConnection();
            } catch (SQLException e) {
                throw new ModelException(e);
            } catch (ConnectionException e) {
                throw new ModelException(e);
            }
        }

    }

}
