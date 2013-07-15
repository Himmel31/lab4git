package edu.sumdu.group5.lab3.actions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sumdu.group5.lab3.dao.DAO;
import edu.sumdu.group5.lab3.dao.DAOFactory;
import edu.sumdu.group5.lab3.dao.DaoException;
import edu.sumdu.group5.lab3.dao.JdbcDAO;
import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.actions.Action;

/**
 * @implements Action
 * Class which process adding of the specific device
 * Author Sergey & Artem
 */
public class AddDeviceAction implements Action {

    /**
     * Reference on the DAO object type
     */
    private DAO jdbcDao;

    /**
     * Constructor which gets(creates) instance of the DAO object
     */
    public AddDeviceAction() throws DaoException {
        jdbcDao = (JdbcDAO) new DAOFactory().newInstance("jdbcDAO");
    }

    /**
     * Processing the request data from client and sets session attributes based on the request parameters
     */
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        List<Device> listdPort = null;
        List<Device> listdSlot = null;
        List<Device> listdChildCards = null;
        Device newDevice = null;
        String str = request.getParameter(TYPE).toString();

        if (str.equals("router")) {

            try {
                newDevice = createDevice(request);
                jdbcDao.add(newDevice);
            } catch (ModelException e1) {
                request.getSession().setAttribute("errorMessage", e1);
                return "/error.jsp";
            }
            HttpSession session = request.getSession(true);
            int placeID = Integer.valueOf(session.getAttribute("currentPlaceDevice").toString());
            session.setAttribute("currentPlaceDevice", placeID);
            List<Device> listd;
            try {
                listd = jdbcDao.getRootDevicesByPlaceID(Integer.valueOf(placeID));
            } catch (ModelException e) {
                request.getSession().setAttribute("errorMessage", e);
                return "/error.jsp";
            }

            request.getSession().setAttribute("check", 1);
            request.getSession().setAttribute("devices", listd);

            return "/placeList.jsp";
        } else {

            try {
                newDevice = createDevice(request);
            } catch (ModelException e1) {
                request.getSession().setAttribute("errorMessage", e1);
                return "/error.jsp";
            }
            try {
                jdbcDao.add(newDevice);
            } catch (ModelException e1) {
                request.getSession().setAttribute("errorMessage", e1);
                return "/error.jsp";
            }
            try {
                HttpSession session = request.getSession(true);
                int parentID = Integer.valueOf(session.getAttribute("currentRootDeviceID").toString());
                listdPort = jdbcDao.getChildDevicesPorts(parentID);
                listdSlot = jdbcDao.getChildDevicesSlots(parentID);
                listdChildCards = getListCards(listdSlot);
            } catch (ModelException e) {
                request.getSession().setAttribute("errorMessage", e);
                return "/error.jsp";
            }
            request.getSession().setAttribute("devicesChildCards", listdChildCards);
            request.getSession().setAttribute("devicesSlot", listdSlot);
            request.getSession().setAttribute("devicesPort", listdPort);

            return "/deviceList.jsp";
        }
    }

    /**
     * Adds new object of the Device type
     *
     * @return created device is returning
     */
    private Device createDevice(HttpServletRequest request) throws ModelException {
        HashMap<Integer, String> map = jdbcDao.getIdDevicesTypes();
        HttpSession session = request.getSession(true);
        int placeID = Integer.valueOf(session.getAttribute("currentPlaceDevice").toString());
        int deviceTypeID = 0;
        int parentID;
        if (!request.getParameter(TYPE).toString().equals("card")) {
            parentID = Integer.valueOf(session.getAttribute("currentRootDeviceID").toString());
            String str = request.getParameter(TYPE).toString();

            if (str.equals("router")) {
                parentID = 0;
                for (Integer s : map.keySet()) {
                    if (map.get(s).toLowerCase().equals(str.toLowerCase())) {
                        deviceTypeID = s;
                        break;
                    }
                }
            } else if (str.equals("slot")) {
                for (Integer s : map.keySet()) {
                    if (map.get(s).toLowerCase().equals(str.toLowerCase())) {
                        deviceTypeID = s;
                        break;
                    }
                }
            } else if (str.equals("port")) {
                for (Integer s : map.keySet()) {
                    if (map.get(s).toLowerCase().equals(str.toLowerCase())) {
                        deviceTypeID = s;
                        break;
                    }
                }
            }
            Device device = new Device();
            device.setDevName(request.getParameter(DEVICENAME));
            device.setDeviceTypeID(deviceTypeID);
            device.setParentID(parentID);
            device.setPlaceID(placeID);

            return device;
        } else {
            parentID = new Integer(request.getParameter(PARENTID).toString());
            for (Integer s : map.keySet()) {
                if (map.get(s).toLowerCase().equals("card")) {
                    deviceTypeID = s;
                    break;
                }
            }
            Device device = new Device();
            device.setDevName(request.getParameter(DEVICENAME));
            device.setDeviceTypeID(deviceTypeID);
            device.setParentID(parentID);
            device.setPlaceID(placeID);

            return device;
        }
    }

    /**
     * Gets list of cards based on the list of slots, in which this cards inserted
     *
     * @return list of cards
     */
    private List<Device> getListCards(List<Device> listSlots) throws ModelException {
        List<Device> listCards = new ArrayList<Device>();
        for (Iterator<Device> i = listSlots.iterator(); i.hasNext(); ) {
            Device slot = (Device) i.next();
            List<Device> listd = jdbcDao.getChildDevices(slot.getId());
            for (Iterator<Device> i1 = listd.iterator(); i1.hasNext(); ) {
                Device card = (Device) i1.next();
                listCards.add(card);
            }
            listd = null;
        }
        return listCards;
    }
}
