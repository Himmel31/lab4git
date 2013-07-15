package edu.sumdu.group5.lab3.actions;

import java.util.ArrayList;
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

/**
 * @implements Action
 * Class which deletes the specified device.
 * Deletes child devices ass well.
 * Author Sergey & Artem
 */
public class RemoveDeviceAction implements Action {

    /**
     * Reference on the DAO object type
     */
    private DAO jdbcDao;

    /**
     * Constructor which gets(creates) instance of the DAO object
     */
    public RemoveDeviceAction() throws DaoException {
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

                HttpSession session = request.getSession(true);
                int parentID = Integer.valueOf(session.getAttribute("currentRootDeviceID").toString());
                jdbcDao.removeDevice(Integer.valueOf(deviceId));

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