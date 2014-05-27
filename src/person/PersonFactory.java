package person;

import util.InvalidDataException;

/**
 * A factory class for creating person objects
 * @author Raphael Shejnberg
 */
public class PersonFactory {
    /*
     * Default constructor
     */
    private PersonFactory(){}
    /*
     * PersonImpl object creator
     */
    public static Person create(int floorNum, int destFloor) throws InvalidDataException
    {
        return new PersonImpl(floorNum, destFloor);
    }
    
}
