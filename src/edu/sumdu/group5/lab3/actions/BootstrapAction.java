package edu.sumdu.group5.lab3.actions;

import java.util.*;

import javax.ejb.FinderException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sumdu.group5.lab3.dao.BeanException;
import edu.sumdu.group5.lab3.dao.DAO;
import edu.sumdu.group5.lab3.dao.DAOFactory;
import edu.sumdu.group5.lab3.dao.DaoException;
import edu.sumdu.group5.lab3.dao.EjbDAO;
import edu.sumdu.group5.lab3.dao.JdbcDAO;

import edu.sumdu.group5.lab3.model.Device;
import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.model.PlaceCl;
import edu.sumdu.group5.lab3.model.PlaceUser;

/**
 * @implements Action
 * Class which is to get places data from database
 * Author Sergey
 */
public class BootstrapAction implements Action {

    /**
     * Reference on the DAO object type
     */
    private DAO jdbcDao;

    /**
     * Constructor which gets(creates) instance of the DAO object
     */
    public BootstrapAction() throws DaoException {
        jdbcDao = (EjbDAO) new DAOFactory().newInstance("ejbDAO");
    }

    /**
     * Processing the request data from client and sets session attributes based on the request parameters
     */
    public String perform(HttpServletRequest request, HttpServletResponse response) {
        String placeId = request.getParameter("id");
        request.getSession().setAttribute("check", null);
        if (placeId != null) {
            Collection<PlaceCl> listp;
            try {
                listp = jdbcDao.findAllLocation();
            } catch (ModelException e) {
                request.getSession().setAttribute("errorMessage", e);
                return "/error.jsp";
            } catch (FinderException e) {
            	request.getSession().setAttribute("errorMessage", e);
                return "/error.jsp";
			} catch (BeanException e) {
				request.getSession().setAttribute("errorMessage", e);
                return "/error.jsp";
			}
            LinkedList<PlaceCl> navigateListPlace = new LinkedList<PlaceCl>();

            PlaceUser pl = new PlaceUser();
            pl.setPlaces(listp);
            PlaceCl place1 = pl.findPlace(Integer.valueOf(placeId));
            if (place1.getParentID() == 0) {
                navigateListPlace.addFirst(place1);
            } else {
                navigateListPlace.addFirst(place1);
                while (place1.getParentID() != 0) {
                    place1 = pl.findPlace(place1.getParentID());
                    navigateListPlace.addFirst(place1);
                }
            }

            TreeSet<PlaceCl> idListPlace = new TreeSet<PlaceCl>();

            for (Iterator<PlaceCl> i = listp.iterator(); i.hasNext(); ) {
                PlaceCl place = (PlaceCl) i.next();

                if (place.getId() == Integer.valueOf(placeId) && place.getLocationTypeID() == 4) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentPlaceDevice", place.getId());
                    Collection<Device> listd;
                    try {
                        listd = jdbcDao.getRootDevicesByPlaceID(Integer.valueOf(place.getId()));
                    } catch (ModelException e) {
                        request.getSession().setAttribute("errorMessage", e);
                        return "/error.jsp";
                    } catch (BeanException e) {
                        request.getSession().setAttribute("errorMessage", e);
                        return "/error.jsp";
                    } catch (FinderException e) {
                        request.getSession().setAttribute("errorMessage", e);
                        return "/error.jsp";
                    }

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
            try {
                listp = jdbcDao.findAllLocation();
            } catch (ModelException e) {
                request.getSession().setAttribute("errorMessage", e);
                return "/error.jsp";
            } catch (FinderException e) {
            	request.getSession().setAttribute("errorMessage", e);
                return "/error.jsp";
			} catch (BeanException e) {
				request.getSession().setAttribute("errorMessage", e);
                return "/error.jsp";
			}
            TreeSet<PlaceCl> idListPlace = new TreeSet<PlaceCl>();

            for (Iterator<PlaceCl> i = listp.iterator(); i.hasNext(); ) {
                PlaceCl place = i.next();
               if (place.getParentID() == 0) {
                   idListPlace.add(place);
                }
            }
            request.getSession().setAttribute("places", idListPlace);
            request.getSession().setAttribute("navigatePlace", null);
            return "/placeList.jsp";
        }
    }
}
