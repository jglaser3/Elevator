package person;

import Display.Display;
import Timer.SimulationTimer;
import elevator.ElevatorController;
import elevator.ElevatorRequest;
import floor.FloorFacade;
import java.sql.Time;
import util.*;

/**
 *
 * @author Raphael Shejnberg
 */
public class PersonImpl implements Person{
    /*
     * A integer representing the persons direction. Value is calculated
     */
    private int direction;
    /*
     * The floor number of the floor the person is currently on
     */
    private int floor;
    /*
     * A string indicating what the person is currently doing in relation
     * to the elevators of the building
     */
    private String status;
    /*
     * The floor number a person is trying to get to
     */
    private int destFloor = 0;
    /*
     * The time this person waited for an elevator
     */
    private long waitTime;
    /*
     * The time this person spent inside an elevator
     */
    private long timeInElevator;
    /*
     * 
     */
    private long timeStorage;
    /*
     * Constructor
     * @param floorNum - The number of the floor this person is placed on
     * @param destFloor - The number of the floor this person is going to
     */
    public PersonImpl(int floorNum,int destFloor) throws InvalidDataException{
        try 
        {
            setFloor(floorNum);
            setDestFloor(destFloor);
            setStatusUnavailable();
        } 
        catch (InvalidDataException ex) {}
        setDirection();
    }
    /**
     * 
     */
    /*
     * Getter for this.waitTime
     * @return this.waitTime
     */
    public long getWaitTime() {
        return this.waitTime;
    }

    /*
     * Setter for this.waitTime
     * @param waitTime - A long value representing the amount of time this person waited for an elevator
     */
    private void setWaitTime(long waitTime) throws InvalidDataException {
        if(waitTime < 0)
            throw new DataOutOfBoundsException("Wait-time", 0, Integer.MAX_VALUE);
        else
            this.waitTime = waitTime;
    }

    /*
     * Getter for this.timeInElevator
     * @return this.timeInElevator;
     */
    public long getTimeInElevator() {
        return this.timeInElevator;
    }

    /*
     * Setter for this.timeInElevator;
     * @param timeInelevator - A long value representing the amount of time this person spent in an elevator
     */
    private void setTimeInElevator(long timeInElevator) throws InvalidDataException {
        if(timeInElevator < 0)
            throw new DataOutOfBoundsException("Ride-time", 0, Integer.MAX_VALUE);
        else
            this.timeInElevator = timeInElevator;
    }

    /*
     * 
     */
    public long getTimeStorage() {
        return this.timeStorage;
    }

    
    public void setTimeStorage(long time) throws InvalidDataException {
        if(time < 0)
            throw new DataOutOfBoundsException("Time-storage", 0, Integer.MAX_VALUE);
        else
            this.timeStorage = time;
    }
    /*
     * Getter for this.status
     * @return this.status 
     */
    public String getStatus() {
        return this.status;
    }
    /*
     * Sets this.status to 'waiting' indicating that this person is
     * waiting for an elevator
     */
    private void setStatusWaiting() {
        this.status = "waiting";
    }
    /*
     * Sets this.status to 'riding indicating that this person is riding in an 
     * elevator
     */
    private void setStatusRiding() {
        this.status = "riding";
    }
    /*
     * Sets this.status to 'unavailable' indicating that this person has already
     * reached their destination
     */
    private void setStatusUnavailable() {
        this.status = "unavailable";
    }
    /*
     * Getter for this.floor
     * @return this.floor - The current floor the person is on
     */
    @Override
    public int getFloor() {
        return this.floor;
    }
    /*
     * Setter for this.floor
     * @param floorNum - An integer to set this.floor to
     */
    private void setFloor(int floorNum) throws InvalidDataException{
        if(floorNum < 0)
            throw new DataOutOfBoundsException("Floor-number", 1, Integer.MAX_VALUE);
        else
            this.floor = floorNum;
    }
    /*
     * Getter for this.destFloor
     * @return this.destFloor - An integer representing this destination floor of this person
     */
    @Override
    public int getDestFloor() {
        return this.destFloor;
    }
    /*
     * Setter for this.destFloor
     * @param destFloor - An integer representing the destination floor of this person
     */
    private void setDestFloor(int destFloor) throws InvalidDataException{
        if(destFloor < 0)
            throw new DataOutOfBoundsException("Destination", 1, Integer.MAX_VALUE);
        else
            this.destFloor = destFloor;
    }
    
    
    /*
     * Getter for this.direction
     * @return this.direction - An integer representing the direction of this person
     */
    @Override
    public int getDirection() {
        return this.direction;
    }
    /*
     * Setter for this.direction, determines direction based on this.floor
     * and this.destFloor
     */
    private void setDirection() {
        if(this.floor < this.destFloor) 
            this.direction = Direction.UP;
        else if(this.floor > this.destFloor)
            this.direction = Direction.DOWN;
        else 
            this.direction = Direction.STABLE;

    }
    
    /*
     * Presses the appropriate button on the elevator or floor panel
     * @see ElevatorPanel
     * @see FloorCallBox
     */
    @Override
    public void pressButton() {
        try 
        {
            
            if(getStatus().equals("unavailable")) 
            {
               if(getFloor() < getDestFloor())
                    System.out.println(Display.displayWithTimeAndID("Floor", getFloor(), " calls for an elevator going up."));
               else
                    System.out.println(Display.displayWithTimeAndID("Floor", getFloor(), " calls for an elevator going down."));  

               Time current = Time.valueOf(SimulationTimer.getInstance().getTimeString());
               long currentLong = current.getTime();
                setTimeStorage(currentLong);
                FloorFacade.getInstance().pressUp(this);
                setStatusWaiting();
            }
            else if (getStatus().equals("riding")) 
                ElevatorController.getInstance().findElevatorWithPerson(this).getPanel().pressButton(this);
        } 
        catch (InvalidDataException ex) {}
    }
    
    
    /**
     * Marks the arrival of a person to their destination floor
     * @param floor - The floor that the person came from
     */
    @Override
    public void arrived(int floor) {
        try 
        {
            Time current = Time.valueOf(SimulationTimer.getInstance().getTimeString());
            long currentLong = current.getTime();
            
            setTimeInElevator(currentLong-getTimeStorage());
            System.out.println(Display.displayPersonPath(this.floor, this.destFloor) + " had a ride time of "+ this.timeInElevator / 1000 + " seconds.");
            setStatusUnavailable();
            setFloor(floor);
        } 
        catch (InvalidDataException ex) {}
        
    }
    /*
     * Starts time keeping for the length of the elevator ride and marks the users status as 'riding'
     */
    @Override
    public void boardElevator() {
        
        setStatusRiding();
        Time current = Time.valueOf(SimulationTimer.getInstance().getTimeString());
        long currentLong = current.getTime();
        long difference = currentLong - getTimeStorage();
        
        try 
        {
            setWaitTime(difference);
            setTimeStorage(currentLong);
        } 
        catch (InvalidDataException ex) {}
        
        System.out.println(Display.displayWithTime(Display.displayPersonPath(this.floor, this.destFloor) + " had a wait time of "+ getWaitTime() / 1000+ " seconds."));
    }  
}
