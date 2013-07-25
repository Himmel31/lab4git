package edu.sumdu.group5.lab4.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface Action describes behavior of actions with objects
 * Author Sergey
 */
public interface Action {

    String DEVICENAME = "devicename";
    String TYPE = "type";
    String IDPLACE = "idplace";
    String PARENTID = "parentid";

    /**
     * Method which handles request and response from the specific action
     */
    public String perform(HttpServletRequest request, HttpServletResponse response);
}