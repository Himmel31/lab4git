package edu.sumdu.group5.lab3.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;

import edu.sumdu.group5.lab3.dao.DAO;
import edu.sumdu.group5.lab3.dao.DAOFactory;
import edu.sumdu.group5.lab3.dao.DaoException;
import edu.sumdu.group5.lab3.dao.JdbcDAO;

/**
 * @implements Action
 * Class which gets and sets all devices to be shown on .jsp pages
 * Author Sergey
 */
public class ShowDevicesAction implements Action {

    /**
     * Reference on the DAO object type
     */
    private DAO jdbcDao;

    /**
     * Constructor which gets(creates) instance of the DAO object
     */
    public ShowDevicesAction() throws DaoException {
        jdbcDao = (JdbcDAO) new DAOFactory().newInstance("jdbcDAO");
    }

    /**
     * Processing the request data from client and sets session attributes based on the request parameters
     */
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String deviceId = request.getParameter("id");

        HttpSession session = request.getSession(true);
        String currentNameDevice = null;
        try {
            currentNameDevice = jdbcDao.getDeviceByID(Integer.valueOf(deviceId)).getDevName();
        } catch (ModelException e1) {
            request.getSession().setAttribute("errorMessage", e1);
            return "/error.jsp";
        }
        session.setAttribute("currentRootDeviceID", deviceId);
        session.setAttribute("currentRootDeviceName", currentNameDevice);
        if (deviceId != null) {
            List<Device> listdSlot;
            List<Device> listdPort;
            List<Device> listdChildCards = new ArrayList<Device>();
            try {

                listdPort = jdbcDao.getChildDevicesPorts(Integer.valueOf(deviceId));
                listdSlot = jdbcDao.getChildDevicesSlots(Integer.valueOf(deviceId));
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
