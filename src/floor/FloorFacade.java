
package floor;

import elevator.Elevator;
import java.util.ArrayList;
import person.Person;
import person.PersonFactory;
import util.Direction;
import util.InvalidDataException;

/**
 *This class is a facade class that makes floor data globally available but still keeps the data hidden and secure.
 * @author Raphael Shejnberg
 */


public class FloorFacade {
    
    private static volatile FloorFacade self;
    
    private ArrayList<Floor> floors;

    /**
     * Application of singleton pattern 
     * @return reference to FloorFacade instance
     */
    
    public static FloorFacade getInstance(){
        if(self == null)
        {
            synchronized(FloorFacade.class)
            {
                if(self == null)
                    self = new FloorFacade();
            }
        }
        return self;
    }
    /**
     * Floor initializer. Creates numFloors amount of floors
     * @param numFloor 
     */
    public void initFloors(int numFloors) throws InvalidDataException{
        floors = new ArrayList<>();
        
        for(int i = 0; i < numFloors; i++)
            getFloors().add(FloorFactory.create(i));
        
    }
    
    /**
     * Returns list of floors
     * @return the ArrayList<Floor> floors
     */
    private ArrayList<Floor> getFloors() {
        return floors;
    }
    
    /** 
     * Getter for floor size
     * @return number of floors
     */
    public int getNumFloors(){
        return floors.size();
    }
    /*
     * Adds a newly created person to a floor
     * @param floor Floor to add person to
     * @param p Person object
     */
    public void addPerson(int floor, Person p) {
        getFloors().get(floor).addPerson(p);
    }
    /**
     * Adds a person getting off an elevator to a floor
     * @param floor Floor to add person to
     * @param p Person object
     */
    public void receivePerson(int floor, Person p) {
        getFloors().get(floor).acceptPerson(p);
    }
      /*
     * Gets person with designated index from designated floor
     * @param floor 
     * @param personIndex 
     * @return p
     */
    public void removePerson(int floor, Person p){
        this.floors.get(floor).removePerson(p);
    }
    /**
     * 
     * @param floor Floor number to check
     * @return The number of people on the specified floor
     */
    public int getNumPeople(int floor){
        return getFloors().get(floor).getNumPeople();
    }
    
    /**
     *
     * @param floor
     * @return
     */
    private ArrayList<Person> getPeople(int floor) {
        return getFloors().get(floor).getPeople();
    }
    
    public ArrayList<Person> getPeopleToBoard(int floor, int maxPeople, int elDirection) {
        ArrayList<Person> peopleToBoard = new ArrayList<>();
        for(Person p : getPeople(floor))
            if("waiting".equals(p.getStatus()) && (p.getDirection() == elDirection || elDirection == Direction.STABLE))
                peopleToBoard.add(p);
        return peopleToBoard;
    }
    /**
     * Gets how many people have been created and placed on target floor
     * @param floor the floor to look into
     * @return how many people have been created and placed
     */
    public int getTotalCreated(int floor){
        return getFloors().get(floor).getTotalCreated();
    }
    public void pressUp(Person p) throws InvalidDataException {
        getFloors().get(p.getFloor()).getCallBox().pressButton(p);
        
    }
    
    /**
     * Checks to see if someone on the floor is able to enter the elevator if he is traveling in same direction
     * @param el The elevator which is compared to people's destinations and directions
     * @return A boolean value that evaluates to true if a match is found
     */
    public boolean personToPickUp(Elevator el) {
        for(Person p : getFloors().get(el.getFloor()).getPeople()) {
           if(p.getStatus().equals("waiting") && (p.getDirection() == el.getDirection() || el.getDestFloor() == el.startingFloor))
               return true;                
        }
        return false;
    }
    /**
     * Prints all people on all floors
     */
    public void printAllFloors(){
        
        for(int i = 0; i < getFloors().size(); i++)
            System.out.println(getFloors().get(i).toString());
        
        
    }
    /**
     * This checks to see if all floors are empty. Use before termination to ensure completion
     * @return true if even a floor one person
     */
    public boolean floorsEmpty(){
        for(int i=0;i<getFloors().size();i++)
            if(getFloors().get(i).getNumPeople() > 0)
                return false;

        return false;
    }
}

