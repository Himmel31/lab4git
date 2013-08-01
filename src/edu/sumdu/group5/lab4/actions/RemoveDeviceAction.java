package edu.sumdu.group5.lab4.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * Class which deletes the specified device.
 * Deletes child devices ass well.
 * Author Sergey & Artem
 */
public class RemoveDeviceAction implements Action {

    /** The logger */
    private static final Logger log = Logger.getLogger(RemoveDeviceAction.class);

    /**
     * Reference on the DAO object type
     */
    private DAO jdbcDao;

    /**
     * Constructor which gets(creates) instance of the DAO object
     */
    public RemoveDeviceAction() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
        jdbcDao = (EjbDAO) new DAOFactory().newInstance("ejbDAO");
    }

    /**
     * Processing the request data from client and sets session attributes based on the request parameters
     */
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: deviceId = " + request.getParameter("id"));
        String deviceId = request.getParameter("id");
        if (deviceId != null) {
            Collection<Device> listdPort;
            Collection<Device> listdSlot;
            Collection<Device> listdChildCards;
            try {

                HttpSession session = request.getSession(true);
                int parentID = Integer.valueOf(session.getAttribute("currentRootDeviceID").toString());
                jdbcDao.removeDevice(Integer.valueOf(deviceId));

                listdPort = jdbcDao.getChildDevicesPorts(parentID);
                listdSlot = jdbcDao.getChildDevicesSlots(parentID);
                listdChildCards = getListCards((List<Device>) listdSlot);
            } catch (ModelException e) {
                request.getSession().setAttribute("errorMessage", e);
                log.error("Exception", e);
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
     * @throws ModelException
     */
    private List<Device> getListCards(List<Device> listSlots) throws ModelException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        List<Device> listCards = new ArrayList<Device>();
        for (Iterator<Device> i = listSlots.iterator(); i.hasNext(); ) {
            Device slot = (Device) i.next();
            Collection<Device> listd = jdbcDao.getChildDevices(slot.getId());
            for (Iterator<Device> i1 = listd.iterator(); i1.hasNext(); ) {
                Device card = (Device) i1.next();
                listCards.add(card);
            }
            listd = null;

        }
        return listCards;
    }

}