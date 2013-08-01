package edu.sumdu.group5.lab4.actions;

import java.util.*;

import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import edu.sumdu.group5.lab4.dao.DAO;
import edu.sumdu.group5.lab4.dao.DAOFactory;
import edu.sumdu.group5.lab4.dao.DaoException;
import edu.sumdu.group5.lab4.dao.EjbDAO;
import edu.sumdu.group5.lab4.model.Device;
import edu.sumdu.group5.lab4.model.ModelException;
import edu.sumdu.group5.lab4.model.Place;
import edu.sumdu.group5.lab4.model.PlaceUser;

import org.apache.log4j.Logger;
/**
 * @implements Action
 * Class which is to get places data from database
 * Author Sergey
 */
public class BootstrapAction implements Action {

	/** The logger */
    private static final Logger log = Logger.getLogger(BootstrapAction.class);

    /**
     * Reference on the DAO object type
     */
    private DAO jdbcDao;

    /**
     * Constructor which gets(creates) instance of the DAO object
     */
    public BootstrapAction() throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Constructor call");
        jdbcDao = (EjbDAO) new DAOFactory().newInstance("ejbDAO");
    }

    /**
     * Processing the request data from client and sets session attributes based on the request parameters
     */
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: placeId = " + request.getParameter("id"));
        String placeId = request.getParameter("id");
        request.getSession().setAttribute("check", null);
        try {
            if (placeId != null) {
                Collection<Place> listp;
                listp = jdbcDao.findAllLocation();
                LinkedList<Place> navigateListPlace = new LinkedList<Place>();

                PlaceUser pl = new PlaceUser();
                pl.setPlaces(listp);
                Place place1 = pl.findPlace(Integer.valueOf(placeId));
                if (place1.getParentID() == 0) {
                    navigateListPlace.addFirst(place1);
                } else {
                    navigateListPlace.addFirst(place1);
                    while (place1.getParentID() != 0) {
                        place1 = pl.findPlace(place1.getParentID());
                        navigateListPlace.addFirst(place1);
                    }
                }
                TreeSet<Place> idListPlace = new TreeSet<Place>();

                for (Iterator<Place> i = listp.iterator(); i.hasNext(); ) {
                    Place place = (Place) i.next();

                    if (place.getId() == Integer.valueOf(placeId) && place.getLocationTypeID() == 4) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("currentPlaceDevice", place.getId());
                        Collection<Device> listd;
                        listd = jdbcDao.getRootDevicesByPlaceID(Integer.valueOf(place.getId()));
                        request.getSession().setAttribute("check", 1);
                        request.getSession().setAttribute("devices", listd);
                        request.getSession().setAttribute("navigatePlace", navigateListPlace);

                        return "/placeList.jsp";
                    }

                    if (place.getParentID() == Integer.valueOf(placeId)) {
                        idListPlace.add(place);
                    }
                }

                request.getSession().setAttribute("places", idListPlace);
                request.getSession().setAttribute("navigatePlace", navigateListPlace);
                return "/placeList.jsp";
            } else {
                Collection listp = null;
                    listp = jdbcDao.findAllLocation();           
                TreeSet<Place> idListPlace = new TreeSet<Place>();

                for (Iterator<Place> i = listp.iterator(); i.hasNext(); ) {
                    Place place = i.next();
                   if (place.getParentID() == 0) {
                       idListPlace.add(place);
                    }
                }
                request.getSession().setAttribute("places", idListPlace);
                request.getSession().setAttribute("navigatePlace", null);
                return "/placeList.jsp";
            }
        }catch (ModelException e) {
            request.getSession().setAttribute("errorMessage", e);
            log.error("Exception", e);
            return "/error.jsp";
        } catch (FinderException e) {
            request.getSession().setAttribute("errorMessage", e);
            log.error("Exception", e);
            return "/error.jsp";
        }
    }
}
