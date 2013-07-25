package edu.sumdu.group5.lab4.coreservlets;

import java.util.HashMap;
import java.util.Map;

import edu.sumdu.group5.lab4.actions.*;

import org.apache.log4j.Logger;

/**
 * Factory of actions
 * Author Sergey
 */
public class ActionFactory {

    /** The logger */
    private static final Logger log = Logger.getLogger(ActionFactory.class);

    /**
     * Map of actions
     */
    private static Map<String, Class> map = defaultMap();

    /**
     * Creates instance of Action
     *
     * @return instance of Action
     */
    public static Action create(String actionName) {
        if (log.isDebugEnabled())
            log.debug("Method call. Arguments: " + actionName);
        Class klass = (Class) map.get(actionName);
        if (klass == null)
            throw new RuntimeException("Was unable to find an action named '" + actionName + "'.");

        Action actionInstance = null;
        try {
            actionInstance = (Action) klass.newInstance();
        } catch (Exception e) {
            RuntimeException e1 = new RuntimeException(e);
            log.error("Exception", e1);
            throw e1;
        }

        return actionInstance;
    }

    /**
     * @return map of default values of actions, where key-is value from path in browser which is before "." symbol;
     *         value-evaluates to the specified class
     */
    private static Map<String, Class> defaultMap() {
        if (log.isDebugEnabled())
            log.debug("Method call");
        Map<String, Class> map = new HashMap<String, Class>();

        map.put("index", BootstrapAction.class);
        map.put("device", ShowDevicesAction.class);
        map.put("addDeviceAction", AddDeviceAction.class);
        map.put("updateDeviceAction", UpdateDevicesAction.class);
        map.put("removeDeviceAction", RemoveDeviceAction.class);
        return map;
    }
}