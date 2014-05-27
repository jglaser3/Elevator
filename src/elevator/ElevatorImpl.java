package elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import floor.FloorFacade;
import person.Person;
import Display.*;
import Panel.ElevatorPanel;
import Timer.*;
import util.*;

import static elevator.Elevator.*;
/**
 *
 * @author Raphael Shejnberg
 */
public class ElevatorImpl implements Elevator, Runnable {
    
    /**
     * The thread for an elevator.
     * 
     * @see #initThread() initThread()
     */
    private Thread elevatorThread;
    /**
     * An ArrayList of passengers on an elevator.
     * 
     * @see #getPassengers() getPassengers()
     */
    private ArrayList<Person> passengers;
    
    /**
     * The status of an elevator, active or idle.
     * 
     * @see #getStatus() getStatus()
     * @see #setStatusActive() setStatusActive()
     * @see #setStatusIdle() setStatusIdle()
     */
    private String status;
    
    /**
     * The floor of an elevator.
     * 
     * @see #getFloor() getFloor()
     * @see #setFloor(int) setFloor(int)
     */
    private int floor;
    
    /**
     * The ID of an elevator.
     * 
     * @see #getID() getID()
     * @see #setID(int) setID(int)
     */
    private int elevatorID;
    
    /**
     * The top floor of a building.
     * 
     * @see #getTopFloor() getTopFloor()
     * @see #setTopFloor(int) setTopFloor(int)
     */
    private int topFloor;
    
    /**
     * The direction an elevator is heading.
     * 
     * @see #getDirection() getDirection()
     * @see #setDirection(int) setDirection(int)
     */
    private int direction;
    
    /**
     * Whether or not the elevator is moving.
     * 
     * @see #getMotionState() getMotionState()
     * @see #changeMotionState() changeMotionState()
     */
    private boolean inMotion;
    
    /**
     * Whether or not the elevator's power is on.
     * 
     * @see #getPowerStatus() getPowerStatus()
     * @see #powerOn() powerOn()
     * @see #powerOff() powerOff()
     */
    private boolean on;
    
    /**
     * The elevator's button panel.
     * 
     * @see #getPanel() getPanel()
     */
    private ElevatorPanel panel;
    /**
     * This is the Constructor.
     * 
     * @param EID - elevator ID
     * @param topFloor - top floor an elevator can reach
     */
    public ElevatorImpl(int EID,int topFloor) throws InvalidDataException {
        
        panel = new ElevatorPanel(EID);
        passengers = new ArrayList<>();
        
        setID(EID);
        powerOn();
        setTopFloor(topFloor);
        setStatusActive();
        setFloor(startingFloor);
    }
    
    /**
     * Initializes a thread for an elevator.
     */
    public void initThread() {
        elevatorThread = new Thread(this);
        elevatorThread.setName("ElThread: "+ getID());
        elevatorThread.start();
    }
    /**
     * Sets the direction of the elevator.
     * 
     * @param direction the direction elevator is to be set to
     * @throws InvalidDataException 
     */
    private void setDirection(int direction) throws InvalidDataException {
        if(direction > 1 || direction < -1)
            throw new InvalidDataException("Value of direction must be between -1 and 1");
        else 
            this.direction = direction;
    }
    /**
     * Checks to see which direction an elevator is heading.
     * @see Direction.class
     * @return 0, 1, or -1 depending on the direction of the elevator.
     */
    public int getDirection() {
        if(panel.getLitButtons().isEmpty() || getFloor() == getDestFloor())
            return Direction.STABLE;
        else if (getFloor() < getDestFloor())
            return Direction.UP;
        else 
            return Direction.DOWN;
    }
    /**
     * Changes the state of the motion of the elevator.
     */
    private void changeMotionState()  {
        this.inMotion = !this.inMotion;
    }
    /**
     * Gets the state of the elevator's motion, true if moving and false if not.
     * 
     * @return true if elevator is in motion and false if not in motion
     */
    public boolean getMotionState() {
        return this.inMotion;
    }
    /**
     * Returns the immediate destination of this elevator and -1 if there is none
     * @return The floor number of the immediate destination
     */
    public int getDestFloor(){
        if(panel.getLitButtons().isEmpty())
            return -1;
        return panel.getLitButtons().get(0);
    }
    
   /**
     * Sets the floor of the elevator.
     * 
     * @param floor the floor to set the elevator to
     * @throws DataOutOfBoundsException if floor is out of bound 
     */
    private void setFloor(int floor) throws InvalidDataException {
        if(!Bounds.floor(floor))
            throw new DataOutOfBoundsException("Floor ", 0, getTopFloor());
        
        this.floor = floor;
    }
    /**
     * Gets the current floor of the elevator.
     * 
     * @return current floor of the elevator
     */
    public int getFloor(){
        return this.floor;
    }

    /**
     * Sets the ID of an elevator.
     * 
     * @param EID an int the elevator ID will be set to
     * @throws DataOutOfBoundException if EID is less than zero 
     */
    private void setID(int EID) throws InvalidDataException {
         
        if(EID < 0)
            throw new DataOutOfBoundsException("ID", 0, Integer.MAX_VALUE);
        else
            this.elevatorID = EID;
    }
    
    /**
     * Gets the ID of the current elevator.
     * 
     * @return elevator ID
     */
    public int getID(){
        return this.elevatorID;
    }

    /**
     * Sets the top floor an elevator can reach.
     * 
     * @param floor the floor to be set as the Top Floor
     * @throws InvalidDataException if top floor is less than 1
     */
    private void setTopFloor(int floor) throws InvalidDataException{
        if(floor < 1)
            throw new InvalidDataException("Floor number must be a non-zero positive integer.");
        else
            topFloor = floor;
    }
   
    /**
     * Gets the top floor an elevator can reach.
     * @see #topFloor
     * @return the top floor an elevator can reach
     */
    public int getTopFloor() {
        return topFloor;
    }
    /**
     * Gets the status of an elevator, whether its active or idle.
     * @see #status
     * @return the status, active or idle, of an elevator
     */
    public String getStatus() {
        return this.status;
    }
    /**
     * Sets an elevators status to Idle.
     * @see #status
     * @throws InvalidDataException 
     */
    private void setStatusIdle() throws InvalidDataException {
        
        if(!this.status.equals("idle"))
        {
            addDestination(startingFloor);
            this.status = "idle";
            ElevatorDisplay.printStatus(this.elevatorID, this.status);
        }
    }
    
    /**
     * Sets an elevators status to Active.
     * @see #status
     */
    private void setStatusActive() {
        if(getDestFloor() != startingFloor) 
        {
            this.status = "active";
            ElevatorDisplay.printStatus(this.elevatorID, this.status);
        }
    }
    
    /**
     * Sets an elevators power to On and outputs a string indicating so.
     * @see #on
     */
    @Override
    public void powerOn() {
        this.on = true;
        System.out.println(ElevatorDisplay.displayWithTimeAndID(type, this.elevatorID, "turned on."));
    }
    
    /**
     * Sets an elevators power to Off and outputs a string indicating so.
     * @see  #on
     */
    @Override
    public void powerOff() {
        this.on = false;
        System.out.println(ElevatorDisplay.displayWithTimeAndID(type, this.elevatorID, "turned off."));

    }
    
    
    /**
     * Gets the ArrayList for passengers
     * @return the passengers
     */
    public ArrayList<Person> getPassengers() {
        return passengers;
    }
    /**
     * Gets the max number of passengers.
     * 
     * @return the maximum number of passengers
     */
    public int getMaxPassengers() {
        return maxPassengers;
    }
    

    /**
     * Gets this elevator's power status.
     * 
     * @return the power status of the current elevator
     */
    private boolean getPowerStatus(){
        return this.on;
    }
    
    /**
     * Gets this elevator's panel
     * @return This elevator's ElevatorPanel
     */
    public ElevatorPanel getPanel() {
        return this.panel;
    }
    /**
     * Passengers disembark from elevator when they reach their destination.
     * 
     * @param p passenger to leave elevator
     * @throws InvalidDataException 
     */
    private void movePassengerOff(Person p) throws InvalidDataException {
        getPassengers().remove(p);
        int srcFloor = p.getFloor();
        //is srcFloor necessary?
        FloorFacade.getInstance().receivePerson(this.floor, p);
        p.arrived(this.floor);
        ElevatorDisplay.printDelivery(this.elevatorID, this.floor, srcFloor);
        
        
    }
     /* Board passengers onto the elevator when it reaches their floor.
     * 
     * @param p the person to board the elevator
     */
    private void movePassengerOn(Person p) {
        FloorFacade.getInstance().removePerson(floor, p);
        getPassengers().add(p);
        p.boardElevator();
        ElevatorDisplay.printPicksUp(this.elevatorID, p);
    }
    /**
     * Lets people off the elevator.
     * @see #passengers
     * @throws InvalidDataException 
     */
    private void letPeopleOff() throws InvalidDataException {
        Person[] peopleGettingOff = this.passengers.toArray(new Person[this.passengers.size()]);
        for (Person p : peopleGettingOff)
                if(p.getDestFloor() == this.floor)
                    movePassengerOff(p);
        
    }
    /**
     * Lets people onto the elevator.
     * @see #passengers
     */
    private void letPeopleOn() {
        if(getPassengers().size() < maxPassengers) 
            {
                
                int spaceAvailable = maxPassengers - this.passengers.size();
                ArrayList<Person> peopleBoarding = FloorFacade.getInstance().getPeopleToBoard(this.floor, spaceAvailable, this.direction);
                
                for(Person p : peopleBoarding) 
                    movePassengerOn(p);
            }
    }
    /**
     * Opens the elevator doors, lets people off, lets people on, and closes the door.
     * 
     * @throws InterruptedException
     * @throws InvalidDataException
     */
    public void doorOperation() throws InterruptedException, InvalidDataException{
        
        ElevatorDisplay.printDoorsOpened(this.elevatorID, this.floor);
        synchronized(panel.getLitButtons())
        {
            letPeopleOff();
            letPeopleOn();
            
        }
       
        Thread.sleep(msPerDoorOperation / SimulationTimer.getInstance().getScale());
        ElevatorDisplay.printDoorsClosed(this.elevatorID, this.floor);
    }
    /**
     * Adds a floor to an elevator panels list of destinations
     * @param destFloor
     * @throws InvalidDataException 
     */
    @Override
    public void addDestination(int destFloor) throws InvalidDataException {
        
            if(!Bounds.floor(floor))
                throw new DataOutOfBoundsException("Destination floor", 1, getTopFloor());

            //If request for floor currently being attended to
            if(elevatorAttendingTo(destFloor))
                return;
            
            panel.addLitButton(destFloor);
            panel.sortLitButtons(this.direction);
            
            if(getStatus().equals("idle"))
                setStatusActive();
            
            
            
            String time = SimulationTimer.getInstance().getTimeString();
            ElevatorDisplay.printRespondingTo(this.elevatorID, destFloor);
        
    }
    
    
    /**
     * Thread will continue while the elevator is on and will instruct the elevator
     * to go about visiting the floors in the panel
     * @see #panel.litButtons
     */
    @Override
    public void run() {
        try 
        {
        long defaultTime = 0;
        ElevatorDisplay.printThreadStarted(this.elevatorID);
        int idleIters = 0;
        while(getPowerStatus())
        {
            
            synchronized(this)
            {
                if(!getStatus().equals("idle") && panel.getLitButtons().isEmpty() && defaultTime == System.currentTimeMillis())
                {
                    defaultTime = System.currentTimeMillis() + (msIdleTime / SimulationTimer.getInstance().getScale());
                    if(!this.passengers.isEmpty())
                            addPassengerDestinations();
                    
                    try
                    {
                        this.wait(msIdleTime / SimulationTimer.getInstance().getScale());
                    }
                    catch (InterruptedException ex){
                        setStatusActive();
                    }
                }
            }
            synchronized(this)
            {
                if(panel.litButtonsEmpty() && getStatus().equals("active") && getPassengers().isEmpty())
                {
                       Thread.sleep(500);
                       idleIters++;
                       if(idleIters < 3)
                           continue;
                       
                        setStatusIdle();
                        idleIters = 0;
                }
               
                else if(!panel.litButtonsEmpty())
                {   
                    
                    
                    long currentMs = System.currentTimeMillis();
                    long pauseTime = currentMs + msPerFloor/ SimulationTimer.getInstance().getScale();
                    boolean floorIsStop = getFloor() == getDestFloor();
                    //If elevator receives request while returning to starting floor remove starting floor as destination
                    if(panel.getLitButtons().size() > 1 && panel.getLitButtons().get(0) == startingFloor)
                        panel.removeLitButton(getDestFloor());
                    //If elevator's status is idle and destination is not starting floor
                    if(getStatus().equals("idle") && getDestFloor() != startingFloor)
                        setStatusActive();
                                        
                    while(currentMs < pauseTime)
                    {
                        try
                        {
                            Thread.sleep(msPerFloor/ SimulationTimer.getInstance().getScale());
                            currentMs = System.currentTimeMillis();
                        }
                        catch(InterruptedException ex){}
                    }
                    if(getDirection() == Direction.DOWN && !floorIsStop)
                            setFloor(this.floor-1);
                
                    else if(getDirection() == Direction.UP && !floorIsStop)
                            setFloor(this.floor+1);
                    
                    
                    String msg = "";
                    floorIsStop = getFloor() == getDestFloor();
                    if(floorIsStop)
                        msg += " has arrived at floor " + getFloor();
                    else
                        msg +=  " is passing floor " + getFloor();
                    System.out.println(Display.displayWithTimeAndID(type, getID(), msg));
                    
                }
                if(getStatus().equals("idle"))
                    Thread.sleep(500);
                
                boolean personWaitingForElevator = FloorFacade.getInstance().personToPickUp(this);
                if(this.passengers.size() < maxPassengers) 
                    if(personWaitingForElevator && !elevatorAttendingTo(this.floor))
                        ElevatorController.getInstance().stealRequest(this, this.floor);
                
                if(personWaitingForElevator || elevatorAttendingTo(this.floor))
                {
                    try 
                    {
                            doorOperation();
                            panel.removeLitButton(new Integer(getFloor()));
                            addPassengerDestinations();
                            
                    } 
                    catch (InterruptedException ex) {}
                }
                
            }
        } 
        } 
        catch (InvalidDataException ex) {} catch (InterruptedException ex) {
            Logger.getLogger(ElevatorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    /**
     * This creates a string representation of all the passengers
     * @return String of all passengers
     */
    public String toString(){
        synchronized(getPassengers()){
            return getPassengers().toString();
        }
    }
    /**
     * This looks through passengers list and adds passengers dest floor to elevator destinations
     * @throws InvalidDataException 
     */
    private void addPassengerDestinations() throws InvalidDataException {
        synchronized(getPassengers())
        {
            for(Person p : getPassengers())
                  addDestination(p.getDestFloor());          
        }
    }
   
    /**
     * This checks the destination list to see if the location is already within the list.
     * 
     * @param floor the floor to check if exist in list
     * @return true if destination list has location within it
     */
    @Override
    public boolean elevatorAttendingTo(int floor) {
        return (panel.getLitButtons().contains(floor));
    }
    /**
     * Helper method to tell if an elevator is free
     * @return True when this elevator has nothing to do
     */
    public boolean free() {
        if(this.status.equals("idle") || (panel.litButtonsEmpty() && this.passengers.isEmpty()))
            return true;
        else
            return false;
    }
    /**
     * Helper method to tell if this elevator is appropriate to pick someone up
     * @param floor
     * @param direction
     * @return True if this elevator is approaching this floor in this direction
     */
    public boolean approaching(int floor, int direction) {
        if(this.direction == direction)
            if((this.floor < floor && this.direction == Direction.UP) ||
               (this.floor > floor && this.direction == Direction.DOWN))
                return true;
        return false;
    }

    

   


}