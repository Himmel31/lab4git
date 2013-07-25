package edu.sumdu.group5.lab4.coreservlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.sumdu.group5.lab4.actions.Action;
import edu.sumdu.group5.lab4.model.ModelException;

import org.apache.log4j.Logger;

/**
 * Servlet class, processing all actions from client side
 * Author Sergey
 */
public class DBServlet extends HttpServlet {

    /** The logger */
    private static final Logger log = Logger.getLogger(DBServlet.class);

    /**
     * Constructor of class
     * superclass no-argument constructor is called
     */
    public DBServlet() {        
        super();
        if (log.isDebugEnabled())
            log.debug("Constructor call");
    }

    /**
     * Parse the path action string before ".perform" and return the value of it
     */
    private String getActionName(HttpServletRequest request) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + request.getServletPath().lastIndexOf("."));
        String path = request.getServletPath();
        return path.substring(1, path.lastIndexOf("."));
    }

    /**
     * Overrides method from superclass.
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + getActionName(request));
        Action action = ActionFactory.create(getActionName(request));
        String url = action.perform(request, response);
        if (url != null)
            getServletContext().getRequestDispatcher(url).forward(request, response);
    }

}