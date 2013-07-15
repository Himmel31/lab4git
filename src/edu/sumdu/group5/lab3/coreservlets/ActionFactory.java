package edu.sumdu.group5.lab3.coreservlets;

import java.util.HashMap;
import java.util.Map;

import edu.sumdu.group5.lab3.actions.*;

/**
 * Factory of actions
 * Author Sergey
 */
public class ActionFactory {

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
        Class klass = (Class) map.get(actionName);
        if (klass == null)
            throw new RuntimeException("Was unable to find an action named '" + actionName + "'.");

        Action actionInstance = null;
        try {
            actionInstance = (Action) klass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return actionInstance;
    }

    /**
     * @return map of default values of actions, where key-is value from path in browser which is before "." symbol;
     *         value-evaluates to the specified class
     */
    private static Map<String, Class> defaultMap() {
        Map<String, Class> map = new HashMap<String, Class>();

        map.put("index", BootstrapAction.class);
        map.put("device", ShowDevicesAction.class);
        map.put("addDeviceAction", AddDeviceAction.class);
        map.put("updateDeviceAction", UpdateDevicesAction.class);
        map.put("removeDeviceAction", RemoveDeviceAction.class);
        return map;
    }
}