package edu.sumdu.group5.lab3.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sumdu.group5.lab3.dao.BeanException;
import edu.sumdu.group5.lab3.dao.DAO;
import edu.sumdu.group5.lab3.dao.DAOFactory;
import edu.sumdu.group5.lab3.dao.DaoException;
import edu.sumdu.group5.lab3.dao.JdbcDAO;

import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;

/**
 * @implements Action
 * Class which update the specified device.
 * Author Roman
 */
public class UpdateDevicesAction implements Action {

    /**
     * Reference on the DAO object type
     */
    private DAO jdbcDao;


    private static boolean updatable;
    private static int updateID = -1;

    public static boolean isUpdatable() {
        return updatable;
    }

    public static int getUpdateID() {
        return updateID;
    }
    
    /**
     * Constructor which gets(creates) instance of the DAO object
     * @throws BeanException 
     */
    public UpdateDevicesAction() throws DaoException {
        jdbcDao = (JdbcDAO) new DAOFactory().newInstance("jdbcDAO");
    }

    /**
     * Processing the request data from client and sets session attributes based on the request parameters
     */

    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String deviceId = request.getParameter("id");
        if (deviceId != null) {
            List<Device> listdPort;
            List<Device> listdSlot;
            List<Device> listdChildCards;
            try {
                updateID = Integer.valueOf(deviceId);
                HttpSession session = request.getSession(true);
                int parentID = Integer.valueOf(session.getAttribute("currentRootDeviceID").toString());
                if (updatable) {
                    if (request.getParameter(DEVICENAME) != null) {
                        String newName = request.getParameter(DEVICENAME).toString();
                        jdbcDao.update(updateID, newName);
                    }
                    updatable = false;
                } else {
                    updatable = true;
                }
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
        }
        return "/deviceList.jsp";
    }

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