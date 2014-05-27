package util;

import floor.FloorFacade;

/**
 * This helper class is filled with methods that return boolean values 
 * indicating if a type is within its permissible bounds.
 * 
 * @author Raphael Shejnberg
 */
public class Bounds {
    
    /**
     * This is the default constructor.
     */
    private Bounds() {}
    
    /**
     * Checks if an integer is between 0 and the top floor of the building.
     * 
     * @return boolean value true if int is within building floors, false otherwise
     */
    public static boolean floor(int floor)  {
          if(floor < 0 || floor > FloorFacade.getInstance().getNumFloors())
            return false;
          return true;
    }
    
    /**
     * Checks if an integer is between -1 and 1 permissible values for direction.
     * 
     * @return boolean value true if a permissible direction, false otherwise
     */
    public static boolean direction(int direction) {
        if (direction < -1 || direction > 1)
            return false;
        return true;
    }
}
