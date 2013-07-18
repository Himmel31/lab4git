package edu.sumdu.group5.lab3.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.dao.BeanException;
import edu.sumdu.group5.lab3.dao.DAO;
import edu.sumdu.group5.lab3.dao.DAOFactory;
import edu.sumdu.group5.lab3.dao.DaoException;
import edu.sumdu.group5.lab3.dao.JdbcDAO;
import edu.sumdu.group5.lab3.dao.EjbDAO;

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
        jdbcDao = (EjbDAO) new DAOFactory().newInstance("ejbDAO");
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
        
        session.setAttribute("currentRootDeviceID", deviceId);
        session.setAttribute("currentRootDeviceName", currentNameDevice);
        if (deviceId != null) {
            Collection<Device> listdSlot;
            Collection<Device> listdPort;
            Collection<Device> listdChildCards = new ArrayList<Device>();
            listdPort = jdbcDao.getChildDevicesPorts(Integer.valueOf(deviceId));
            listdSlot = jdbcDao.getChildDevicesSlots(Integer.valueOf(deviceId));
            listdChildCards = getListCards((List<Device>) listdSlot);
            request.getSession().setAttribute("devicesChildCards", listdChildCards);
            request.getSession().setAttribute("devicesSlot", listdSlot);
            request.getSession().setAttribute("devicesPort", listdPort);
        }
        } catch (ModelException e1) {
            request.getSession().setAttribute("errorMessage", e1);
            return "/error.jsp";
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", e);
            return "/error.jsp";
        } catch (BeanException e) {
            request.getSession().setAttribute("errorMessage", e);
            return "/error.jsp";
        }catch (FinderException e) {
        	e.printStackTrace();
        	//request.getSession().setAttribute("errorMessage", e);
            //return "/error.jsp";
		}
        
        return "/deviceList.jsp";
    }

    /**
     * Gets list of cards based on the list of slots, in which this cards inserted
     *
     * @return list of cards
     * @throws BeanException 
     */
    private List<Device> getListCards(List<Device> listSlots) throws ModelException, BeanException {
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
