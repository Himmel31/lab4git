package edu.sumdu.group5.lab4.actions;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sumdu.group5.lab4.actions.Action;
import edu.sumdu.group5.lab4.dao.DAO;
import edu.sumdu.group5.lab4.dao.DAOFactory;
import edu.sumdu.group5.lab4.dao.DaoException;
import edu.sumdu.group5.lab4.dao.EjbDAO;
import edu.sumdu.group5.lab4.dao.JdbcDAO;
import edu.sumdu.group5.lab4.model.Device;
import edu.sumdu.group5.lab4.model.ModelException;

import org.apache.log4j.Logger;

/**
 * @implements Action
 * Class which process adding of the specific device
 * Author Sergey & Artem
 */
public class AddDeviceAction implements Action {

    /** The logger */
    private static final Logger log = Logger.getLogger(AddDeviceAction.class);

    /**
     * Reference on the DAO object type
     */
    private DAO jdbcDao;

    /**
     * Constructor which gets(creates) instance of the DAO object
     */
    public AddDeviceAction() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
        jdbcDao = (EjbDAO) new DAOFactory().newInstance("ejbDAO");
    }

    /**
     * Processing the request data from client and sets session attributes based on the request parameters
     */
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + request.getParameter(TYPE).toString());
        Collection<Device> listdPort = null;
        Collection<Device> listdSlot = null;
        Collection<Device> listdChildCards = null;
        Device newDevice = null;
        String str = request.getParameter(TYPE).toString();
        try{
        if (str.equals("router")) {
            Collection listd;
            newDevice = createDevice(request);
            jdbcDao.add(newDevice);            
            HttpSession session = request.getSession(true);
            int placeID = Integer.valueOf(session.getAttribute("currentPlaceDevice").toString());
            session.setAttribute("currentPlaceDevice", placeID);
            listd = jdbcDao.getRootDevicesByPlaceID(Integer.valueOf(placeID));
            request.getSession().setAttribute("check", 1);
            request.getSession().setAttribute("devices", listd);

            return "/placeList.jsp";
        } else {
            newDevice = createDevice(request);
            jdbcDao.add(newDevice);
            HttpSession session = request.getSession(true);
            int parentID = Integer.valueOf(session.getAttribute("currentRootDeviceID").toString());
            listdPort = jdbcDao.getChildDevicesPorts(parentID);
            listdSlot = jdbcDao.getChildDevicesSlots(parentID);
            listdChildCards = getListCards((List<Device>) listdSlot);
            request.getSession().setAttribute("devicesChildCards", listdChildCards);
            request.getSession().setAttribute("devicesSlot", listdSlot);
            request.getSession().setAttribute("devicesPort", listdPort);

            return "/deviceList.jsp";
        }
        }catch (ModelException e) {
            log.error("Exception", e);
            request.getSession().setAttribute("errorMessage", e);
            return "/error.jsp";
        }  catch (FinderException e) {
            log.error("Exception", e);
            request.getSession().setAttribute("errorMessage", e);
            return "/error.jsp";
        }
    }

    /**
     * Adds new object of the Device type
     *
     * @return created device is returning
     * @throws ModelException
     */
    private Device createDevice(HttpServletRequest request) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + request.getParameter(TYPE).toString());        
        HttpSession session = request.getSession(true);
        int placeID = Integer.valueOf(session.getAttribute("currentPlaceDevice").toString());
        int deviceTypeID = 0;
        int parentID;
        if (!request.getParameter(TYPE).toString().equals("card")) {
            parentID = Integer.valueOf(session.getAttribute("currentRootDeviceID").toString());
            String str = request.getParameter(TYPE).toString();

            if (str.equals("router")) {
                deviceTypeID = 1;
            } else if (str.equals("slot")) {
                deviceTypeID = 2;
            } else if (str.equals("port")) {
                deviceTypeID = 4;
            }
            Device device = new Device();
            device.setDevName(request.getParameter(DEVICENAME));
            device.setDeviceTypeID(deviceTypeID);
            device.setParentID(parentID);
            device.setPlaceID(placeID);

            return device;
        } else {
            parentID = new Integer(request.getParameter(PARENTID).toString());
            deviceTypeID = 3;
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
     * @throws ModelException
     */
    private List<Device> getListCards(List<Device> listSlots) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        List<Device> listCards = new ArrayList<Device>();
        for (Iterator<Device> i = listSlots.iterator(); i.hasNext(); ) {
            Device slot = (Device) i.next();
            Collection listd = jdbcDao.getChildDevices(slot.getId());
            for (Iterator<Device> i1 = listd.iterator(); i1.hasNext(); ) {
                Device card = (Device) i1.next();
                listCards.add(card);
            }
            listd = null;
        }
        return listCards;
    }
}
