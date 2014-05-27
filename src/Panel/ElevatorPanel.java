package Panel;

import java.util.ArrayList;
import java.util.Collections;

import person.Person;
import util.*;
import elevator.*;

/**
 * This class is for the panel contained in Elevators.
 * 
 * @author Raphael Shejnberg
 */
public class ElevatorPanel implements ButtonPanel {
    /**
     * The ID of the elevator that the current object's panel belongs to.
     * 
     * @see #getElevatorID() getElevatorID()
     * @see #setElevatorID(int) setElevatorID(int)
     */
    private int elevatorID;
    
    /**
     * A list of destinations that the elevator will attend to.
     * 
     * @see #getLitButtons() getLitButtons()
     */
    private ArrayList<Integer> litButtons = new ArrayList<>();
    
    /**
     * This is the default constructor.
     * 
     * @param EID the ID of the elevator that this panel belongs to
     */
    public ElevatorPanel(int EID) {
        setElevatorID(EID);
    }
    
    /**
     * Gets the ElevatorID for the current object.
     * 
     * @return the current objects ElevatorID
     */
    public int getElevatorID() {
        return this.elevatorID;
    }
    
    /**
     * Sets the ElevatorID for the current object.
     * 
     * @param elevatorID int to set to the current object's ElevatorID
     */
    private void setElevatorID(int elevatorID) {
        this.elevatorID = elevatorID;
    }
    
    /**
     * Checks if the current objects litButtons is empty.
     * 
     * @return boolean indicating if litButtons is empty
     */
    public boolean litButtonsEmpty() {
        if(this.litButtons.isEmpty())
            return true;
        else
            return false;
    }
    
    /**
     * Gets the current objects ArrayList of litButtons.
     * 
     * @return returns the current objects ArrayList of litButtons
     */
    public ArrayList<Integer> getLitButtons() {
        return this.litButtons;
    }
    
    /**
     * Adds a destination to the current objects litButtons.
     * 
     * @param floor an integer representing a destination floor
     */
    public void addLitButton(int floor) {
        if(!litButtons.contains(floor))
            litButtons.add(floor);
    }
    
    /**
     * Removes a destination from the current objects litButtons.
     * 
     * @param floor an integer representing a floor that the elevator has arrived at
     */
    public void removeLitButton(int floor) {
        litButtons.remove(new Integer(floor));
    }
    
    /**
     * Sorts the contents of the current objects litButtons based on the direction of the elevator.
     * 
     * @param direction int representing the elevators direction
     */
    public void sortLitButtons(int direction) {
        Collections.sort(this.litButtons);
        if(direction == Direction.DOWN)
            Collections.reverse(this.litButtons);
    }
    
    /**
     * How the panel communications with ElevatorController.
     * The panel communicates with ElevatorController via this method which submits 
     * a request to controller for it to handle.
     * 
     * @param p a person who has initiated the request
     */
    @Override
    public void pressButton(Person p) throws InvalidDataException {
        addLitButton(p.getDestFloor());
    }
    
    
}
