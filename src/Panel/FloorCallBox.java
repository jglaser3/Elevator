/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Panel;

import elevator.ElevatorController;
import elevator.ElevatorRequest;
import person.Person;
import util.Direction;
import util.InvalidDataException;

/**
 * This class is for the CallBoxes located on floors.
 * 
 * @author raphaelshejnberg
 */
public class FloorCallBox implements ButtonPanel {
    
    /**
     * The floor number of the floor which this object belongs to.
     * 
     * @see #getFloor() getFloor()
     * @see #setFloor(int) setFloor(int)
     */
    private int floor;
    
    /**
     * Indicates if the up button is active or inactive.
     * 
     * @see #getUp() getUp()
     * @see #pressButton(person.Person) pressButton(person.Person)
     * @see #deactivateUpButton() deactivateUpButton()
     */
    private boolean up;
    
    /**
     * Indicates if down button is active or inactive.
     * 
     * @see #getDown() getDown()
     * @see #pressButton(person.Person) pressButton(person.Person)
     * @see #deactivateDownButton() deactivateDownButton()
     */
    private boolean down;
    
    /**
     * This is the default constructor.
     * 
     * @param floor int representing the floor number this call-box belongs to
     */
    public FloorCallBox(int floor) {
        setFloor(floor);
    }
    
    /**
     * Sets the current object's floor and is only callable from the constructor.
     * 
     * @param floor int representing the floor number this call-box belongs to
     */
    private void setFloor(int floor) {
        this.floor = floor;
    }
    
    /**
     * Gets the current object's floor.
     * 
     * @return floor floor number that this call-box belongs to
     */
    public int getFloor() {
        return floor;
    }
    
    /**
     * Called by person, this initiates submission of request based on the direction of the person.
     * 
     * @param p the person who submitted the request
     */
    @Override
    public void pressButton(Person p) throws InvalidDataException {
        
        if(p.getDirection() == Direction.UP)
            if(!this.up)
            {
                this.up = true;
                submitRequest(p);
            }
        if(p.getDirection() == Direction.DOWN) 
            if(!this.down) 
            {
                this.down = true;
                submitRequest(p);
            }
        
    }
    
    /**
     * Sets the current objects Up Button to false. 
     */
    public void deactivateUpButton() {
        this.up = false;
    }
    
    /**
     * Sets the current objects Down Button to false.
     */
    public void deactivateDownButton() {
        this.down = false;
    }
    
    /**
     * Gets the current objects Up Button status (active or inactive).
     * 
     * @return returns the current objects Up Buttons status (active or inactive).
     */
    public boolean getUp() {
        return this.up;
    }
    
    /**
     * Gets the current objects Down Button status (active or inactive).
     * 
     * @return returns the current objects Down Button status (active or inactive).
     */
    public boolean getDown() {
        return this.down;
    }
    
    /**
     * This builds the request and passes it to ElevatorController.
     * 
     * @see elevator.ElevatorController#getInstance()
     * @see elevator.ElevatorController#addRequest(elevator.ElevatorRequest)
     */
    private void submitRequest(Person p) throws InvalidDataException {
        ElevatorRequest er = new ElevatorRequest(p);
        ElevatorController.getInstance().addRequest(er);
    }
}
