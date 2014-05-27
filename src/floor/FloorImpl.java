package floor;

import Panel.ElevatorPanel;
import Panel.FloorCallBox;
import java.util.ArrayList;
import person.Person;
import util.DataOutOfBoundsException;
import util.InvalidDataException;

/**
 *
 * @author Raphael Shejnberg
 */
public class FloorImpl implements Floor{
    
    /*
     * List of people occupying this floor
     */
    private ArrayList<Person> people;
    
    /*
     * Int that serves as floor ID
     */
    private int floorNum;
    /*
     * The total number of people created on this floor
     */
    private int totalCreated;
    /*
     * 
     */
    private FloorCallBox callBox;
    /**
     * Constructor 
     * @param floorNum 
     */
    public FloorImpl(int floorNum) throws InvalidDataException{
        setFloorNum(floorNum);
        callBox = new FloorCallBox(floorNum);
        people = new ArrayList<>();
        totalCreated = 0;
    }
    /*
     * 
     */
    @Override
    public FloorCallBox getCallBox() {
        return this.callBox;
    }
    /**
     * @return ArrayList of people on floor
     */
    private ArrayList<Person> getPersonList() {
        return people;
    }
    
    /*
     * Gets the total number of people created
     * @return the totalCreated
     */
    public int getTotalCreated() {
        return totalCreated;
    }

    /** Modifies current number of people
     */
    private void addTotalCreated() {
        totalCreated++;
    }
    /*
     * Accepts person getting off of elevator
     */
    @Override
    public void acceptPerson(Person p) {
        getPersonList().add(p);
    }
    /**
     * Adds a person to the list of people currently on floor and increments total created and than presses button
     * @param x the person that will be added to the list
     */
    @Override
    public void addPerson(Person p) { 
        getPersonList().add(p);
        addTotalCreated();
        getPersonList().get( getPersonList().indexOf(p) ).pressButton();
    }
    /**
     * Gets the amount of people currently on floor
     * @return size of PersonList
     */
    @Override
    public int getNumPeople(){
        return getPersonList().size();
    }
    
    @Override
    public ArrayList<Person> getPeople() {
        return this.people;
    }
    /**
     * Sets location of person to target floor
     * @param loc this is the floorNumber
     */
    private void setFloorNum(int floorNum) throws InvalidDataException{
        if(floorNum < 0)
            throw new DataOutOfBoundsException("Floor number", 0, Integer.MAX_VALUE);
        else
            floorNum = floorNum;
    }
    /*
     * Get floorNum
     * @return this.floorNum
     */
    public int getFloorNum() {
        return this.floorNum;
    }
    /**
     * Removes a single person from a floor
     * @param personIndex
     */
    public void removePerson(Person p){
        getPersonList().remove(p);
    }
    /**
     * Gets the person from the list of people at target index
     * @param i location within the list of people
     * @return Person at index i in the list of people
     */
    @Override
    public Person getPerson(Person p) {
        return getPersonList().get(getPersonList().indexOf(p));
    }
    /**
     * Creates a string representation of the list of people
     * @return a string representation of the people currently in the floor
     */
    public String toString(){
        return getPersonList().toString();
    }
    
}
