package Display;
import Timer.SimulationTimer;

/**
 * This helper class is used to print and format messages.
 * 
 * @author raphaelshejnberg
 */

public class Display {
    /**
     * A string of the current time.
     * 
     * @see Timer.SimulationTimer#getInstance()
     * @see Timer.SimulationTimer#getTimeString()
     */
    static String time = SimulationTimer.getInstance().getTimeString();
    
    /**
     * Updates the value contained in this time.
     * 
     * @see #time
     * @see Timer.SimulationTimer#getInstance()
     * @see Timer.SimulationTimer#getTimeString()
     */
    private static void updateTime() {
        Display.time = SimulationTimer.getInstance().getTimeString();
    }
    
    /**
     * Prepends the time to a string.
     * 
     * @param msg string containing a message
     * @return string containing the time
     */
    public static String displayWithTime(String msg) {
        return " [" + Display.time + "] " + msg;
    }
    
    /**
     * Formats a string to display the ID alongside a message.
     * 
     * @param type string describing the item type
     * @param ID uniquely identifying integer for the type
     * @param msg message in string format
     * @return string containing the item type, ID, and the message
     */
    public static String displayWithID(String type, int ID, String msg) {
        return type + " ID: " + ID + " " + msg;
    }
    
    /**
     * Formats a string to display an objects type, ID, and a message.
     * 
     * @param type string describing the item type
     * @param ID integer identifier
     * @param msg string message
     * @return formatted string
     */
    public static String displayWithTimeAndID(String type, int ID, String msg) {
        updateTime();
        return " [" + Display.time + "] " + type + " ID: " + ID + " " + msg;
    }
    
    /**
     * Formats a string to display the floor origin and destination of a person.
     * 
     * @param currentFloor the current floor of the person
     * @param destFloor the destination floor of the person
     * @return formatted string
     */
    public static String displayPersonPath(int currentFloor, int destFloor) {
        return "Person going from floor " + currentFloor + " to " + destFloor;
    }
    
}

