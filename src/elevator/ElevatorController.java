package elevator;

import util.InvalidDataException;
import java.util.ArrayList;
import java.util.Collections;

import person.Person;

/**
 *
 * @author Raphael Shejnberg
 */
public class ElevatorController {
    /**
     * This is an ArrayList of all elevators.
     * 
     * @see #getElevators() getElevators()
     */
    private ArrayList<Elevator> elevators;
    
    /**
     * This is an ArrayList of all elevator requests.
     * 
     * @see #getRequests() getRequests()
     */
    private ArrayList<ElevatorRequest> requests = new ArrayList<>();
    
     /**
     * This is a thread-safe instance of an Elevator Controller.
     * 
     * @see #getInstance() getInstance()
     */
    private static volatile ElevatorController self;
    
    /**
     * This is the default constructor.
     */
    private ElevatorController(){};
    
    /**
     * Gets the ArrayList of elevators.
     * 
     * @return the current objects list of elevators 
     */
    public ArrayList<Elevator> getElevators() {
        return this.elevators;
    }
    
    /**
     * Gets the ArrayList of elevator requests.
     * 
     * @return the current objects list of elevator requests
     */
    private ArrayList<ElevatorRequest> getRequests() {
        return this.requests;
    }
    /**
     * Initiates elevators and outputs string indicating so.
     * 
     * @param numElevators number of elevators to initiate
     * @param topFloor top floor of the building
     * @see elevator.ElevatorFactory#create(int, int, int)
     * @throws InvalidDataException 
     */
    public void initElevators(int numElevators, int topFloor) throws InvalidDataException {
        
        this.elevators = new ArrayList<>();
        
        System.out.println("Activating elevators...");
        for(int i = 0; i < numElevators; i++)
            getElevators().add(ElevatorFactory.create(i, topFloor-1));
        
        System.out.println("Starting threads...");
        for(int i = 0; i < numElevators; i++) 
            getElevators().get(i).initThread();
        
    }
    
    /**
     * Gets only one Elevator Controller via a Singleton pattern method.
     * 
     * @return a single instance of an Elevator Controller
     */
    public static ElevatorController getInstance() {
        if(self == null)
            synchronized(ElevatorController.class)
            {
                if(self== null)
                    self = new ElevatorController();
            }
        
        return self;
    }
    
    /**
     * This returns true if an elevator is idle or heading in the direction of the persons floor
     * 
     * @param p a person waiting for an elevator
     * @return true if an elevator is available, false if not
     */
    public boolean isAvailable(Person p) {
        
        for(int i = 0; i < getElevators().size(); i++)
        {    
            Elevator thisEl = getElevators().get(i);
            
            if((thisEl.getStatus().equals("idle")) && thisEl.getPassengers().size() < thisEl.getMaxPassengers())
                return true;
            else if(thisEl.approaching(p.getFloor(), p.getDirection()))
                    return true;   
        }
        return false;
    }
    
    /**
     * Adds a request to Elevator Requests if it's not already requested.
     * 
     * @param er the elevator request to be added
     * @see #getRequests()
     * @see #respondToRequest(elevator.ElevatorRequest) 
     * @throws InvalidDataException 
     */
    public void addRequest(ElevatorRequest er) throws InvalidDataException {
        if(getRequests().contains(er))
            return;
        synchronized(getRequests()) 
        {
            getRequests().add(er);
            respondToRequest(er);
        }
    }
    /*
     * Called when an elevator is passing a floor that is not in its litButtons() but the floor
     * has someone who is waiting for an elevator going in the same direction as
     * 
     */
    public boolean stealRequest(Elevator thiefEl, int floorToSteal) {
        for(Elevator victimEl : getElevators())
            if(victimEl.getPanel().getLitButtons().contains(floorToSteal)) {
                int victimDistance = Math.abs(victimEl.getFloor() - floorToSteal);
                int thiefDistance = Math.abs(thiefEl.getFloor() - floorToSteal);
                 System.out.println(" STEALING REQUEST*********");
                if(victimDistance > thiefDistance) 
                {
                    victimEl.getPanel().removeLitButton(floorToSteal);
                    thiefEl.getPanel().addLitButton(floorToSteal);
                    System.out.println("  REQUEST STOLEN*********");
                    return true;
                }
                else 
                    return false;  
            }
        return false;
    }
    
    /**
     * Finds an elevator to respond to an elevator request and adds to its destination.
     * 
     * @param er the elevator request to respond to
     * @see #findElevator(elevator.ElevatorRequest) 
     * @see elevator.Elevator#addDestination(int)
     * @see elevator.ElevatorRequest#setBeingAttended() 
     * @throws InvalidDataException 
     */
    public void respondToRequest(ElevatorRequest er) throws InvalidDataException {
        Elevator el = findElevator(er);
        if(el != null) {
            el.addDestination(er.getFloor());
            er.setBeingAttended();
        }
    }
    
    /**
     * Clears complete requests from ElevatorRequests
     * 
     * @see #getRequests() 
     * @see elevator.ElevatorRequest#getCompleted()
     */
    public void clearCompletedRequests() {
        synchronized(getRequests()) 
        {
            for(ElevatorRequest er : getRequests())
                if(er.getCompleted())
                    getRequests().remove(er);
        }
    }
    
   /**
     * Return the elevator containing a specific person or return null.
     * 
     * @param p the person to find
     * @return the elevator containing the person or null
     */
    public Elevator findElevatorWithPerson(Person p) {
        for(Elevator e : getElevators())
            if(e.getPassengers().contains(p))
                return e;
        return null;
    }
    
    /**
     * Finds an elevator attending to a request or heading in the same direction as request
     * @param er the elevator request to find an elevator for
     * @return the elevator to meet the request
     * @throws InvalidDataException 
     */
    public Elevator findElevator(ElevatorRequest er) throws InvalidDataException {
        Elevator elevatorFound = null;
        for(Elevator el : getElevators()) 
            if (el.elevatorAttendingTo(er.getFloor()) && el.getDirection() == er.getDirection() )
                elevatorFound = el;
        for(Elevator el : getElevators())
            if(el.free())
               elevatorFound = el;
        for(Elevator el : getElevators())
            if(el.approaching(er.getFloor(), er.getDirection()))
                elevatorFound = el;
        return elevatorFound;
        
       
    }
    
    /**
     * Checks to see if all elevators have shut down.
     *
     * @return true if all elevators have shut down, false otherwise
     */
    public boolean nothingToDo() {
        boolean nothingToDo = true;
        for(Elevator el : getElevators())
            if(!el.getStatus().equals("idle") || el.getFloor() != el.startingFloor)
                nothingToDo = false;
        return nothingToDo;
    }
    
    /**
     * Returns true if an elevator is enroute to a floor.
     * 
     * @param floor the floor to check if being attended to
     * @return true or false depending on whether or an elevator is enroute
     */
    public boolean elevatorEnRoute(int floor) {
        for(Elevator el : getElevators())
            if(el.elevatorAttendingTo(floor))
                return true;
        return false;
    }
    
    /**
     * Deactivate all elevators.
     */
    public void totalShutdown() {
        for(Elevator el: getElevators()){
            el.powerOff();
        }
    }
    
    /**
     * Prints all of the passengers on elevators.
     */
    public void displayAllPassengers() {
        for(int el = 0; el < getElevators().size(); el++)
            System.out.println(getElevators().get(el).toString());
    }
}
