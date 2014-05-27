package elevator;

import floor.FloorFacade;
import person.Person;
import util.*;

/**
 *
 * @author Raphael Shejnberg
 */
public class ElevatorRequest {
    private int id;
    private int floor;
    private int direction;
    private boolean beingAttended = false;
    private boolean completed = false;
    private int elevatorID;
    
    public ElevatorRequest(Person p) throws DataOutOfBoundsException {
        setFloor(p.getFloor());
        setDirection(p.getDirection());
    }
    
    public ElevatorRequest(Person p, int elevatorID) throws DataOutOfBoundsException {
        setFloor(p.getFloor());
        setEID(elevatorID);
        setDirection(p.getDirection());
    }

    public void setFloor(int floor) throws DataOutOfBoundsException {
        if(!Bounds.floor(floor))
            throw new DataOutOfBoundsException("Elevator request field: floor", 0, FloorFacade.getInstance().getNumFloors());
        this.floor = floor;
    }
   
    
    public void setDirection(int direction) throws DataOutOfBoundsException {
        if(!Bounds.direction(direction))
            throw new DataOutOfBoundsException("Elevator request field: direction", -1, 1);
        this.direction = direction;
    }
    
    public int getEID() {
        return this.elevatorID;
    }
    
    private void setEID(int EID) {
        this.elevatorID = EID;
    }
    public void setBeingAttended() {
        this.beingAttended = true;
    }
    
    public boolean getBeingAttended() {
        return this.beingAttended;
    }
     public boolean getCompleted() {
        return this.beingAttended;
    }
    public void setCompleted() {
        this.completed = true;
    }
    
    public int getDirection() {
        return this.direction;
    }
    public int getFloor() {
        return this.floor;
    }
    public int getID() {
        return this.id;
    }
}
