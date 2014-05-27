package floor;

import util.InvalidDataException;

/**
 * Floor object factory class
 * @author Raphael Shejnberg
 */

public class FloorFactory {   
    
    private FloorFactory(){}
    /**
     * 
     * @param floorNum The floor number
     * @return reference to proper Floor Object
     */
    public static Floor create(int floorNum) throws InvalidDataException{
        return new FloorImpl(floorNum);
    }
}
