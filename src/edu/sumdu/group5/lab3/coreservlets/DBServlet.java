package edu.sumdu.group5.lab3.coreservlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.sumdu.group5.lab3.model.ModelException;
import edu.sumdu.group5.lab3.actions.Action;

/**
 * Servlet class, processing all actions from client side
 * Author Sergey
 */
public class DBServlet extends HttpServlet {

    /**
     * Constructor of class
     * superclass no-argument constructor is called
     */
    public DBServlet() {
        super();
    }

    /**
     * Parse the path action string before ".perform" and return the value of it
     */
    private String getActionName(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.substring(1, path.lastIndexOf("."));
    }

    /**
     * Overrides method from superclass.
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Action action = ActionFactory.create(getActionName(request));
        String url = action.perform(request, response);
        if (url != null)
            getServletContext().getRequestDispatcher(url).forward(request, response);
    }

}