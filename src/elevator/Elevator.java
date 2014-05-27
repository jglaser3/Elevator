
package elevator;

import Panel.ElevatorPanel;
import java.util.ArrayList;
import person.Person;
import util.InvalidDataException;
/**
 * An interface that allows for multiple implementations of Elevator.
 * @author Raphael Shejnberg
 * 
 */
public interface Elevator{
    
    String type = "Elevator";
    
    int maxPassengers = 10;
    
    int msPerFloor = 500;
    
    int msPerDoorOperation = 500;
    
    int msIdleTime = 1500;
    
    int startingFloor = 0;
    
    void initThread();
    void addDestination(int destination) throws InvalidDataException;
    void powerOn();
    void powerOff();
    
    
    ArrayList<Person> getPassengers();
    String getStatus(); 
    
    int getID();
    int getFloor();
    int getDirection();
    int getTopFloor();
    int getDestFloor();
    int getMaxPassengers();
    ElevatorPanel getPanel();
    
    boolean free();
    boolean approaching(int floor, int direction);
    boolean getMotionState();
    boolean elevatorAttendingTo(int floor);
    ;
    
}
