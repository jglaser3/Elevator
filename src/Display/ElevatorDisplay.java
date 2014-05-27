
package Display;

import person.Person;

/**
 * This class extends the functionality of Display with elevator specific methods
 * @author Raphael Shejnberg
 */
public class ElevatorDisplay extends Display {
    
    /*
     * The object type of anything that calls these class member functions
     */
    static String type = "Elevator";
    
    /*
     * Prints message stating elevator's next destination
     * @param ID integer elevator identifier
     * @param destFloor The destination of the elevator
     */
    public static void printRespondingTo(int ID, int destFloor) {
        System.out.println(displayWithTimeAndID(type, ID, " will visit floor " + destFloor));
    }
    /*
     * Print message stating that an elevator thread has been started
     * @param ID integer elevator identifier
     */
    public static void printThreadStarted(int ID) {
        System.out.println(displayWithTimeAndID(type, ID, " Thread started."));
    }
    /*
     * Prints the elevators current status
     * @param ID integer elevator identifier
     * @param status The elevators current status
     */
    public static void printStatus(int ID, String status) {
        if(status.equals("idle"))
            System.out.println(displayWithTimeAndID(type, ID, " is idle."));
        else
            System.out.println(displayWithTimeAndID(type, ID, " is active."));
            
    }
    /*
     * Prints a message about doors opening
     * @param ID integer elevator identifier
     * @param floor The elevators current floor
     */
    public static void printDoorsOpened(int ID, int floor) {
         System.out.println(displayWithTimeAndID(type, ID, " doors opened at floor "+ floor));

    }
    /*
     * Prints a message about doors closing
     * @param ID integer elevator identifier
     * @param floor The elevators current floor
     */
    public static void printDoorsClosed(int ID, int floor) {

            System.out.println(displayWithTimeAndID(type, ID, (" closes its doors on floor " + floor)));
    }
    
    public static void printFloorPassing() {
        
    }
    /*
     * Prints a message about picking up a person
     * @param ID integer elevator identifier
     * @param p A person who has just been picked up
     */
    public static void printPicksUp(int ID, Person p) {
        System.out.println(displayWithTimeAndID(type, ID, (" picks up " + displayPersonPath(p.getFloor(), p.getDestFloor()))));
    }
    /*
     * Prints a message about doors closing
     * @param ID integer elevator identifier
     * @param srcFloor The floor the passenger started on
     * @param floor The elevator's/passenger's current floor
     */
    public static void printDelivery(int ID, int floor, int srcFloor) {
        System.out.println(displayWithTimeAndID(type, ID, " successfully delivers passenger from floor " + srcFloor + " to " + floor));
    }
}
