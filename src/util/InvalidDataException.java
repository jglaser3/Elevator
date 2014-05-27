package util;

/**
 * Exception class used whenever a method receives improper input regarding
 * a certain datatype.
 * 
 * @author Raphael Shejnberg
 */
public class InvalidDataException extends Exception {

    /**
     * Constructor
     * 
     * @param msg a message to print on error
     */
    public InvalidDataException(String msg) {
        super(msg);
    }
    
    /**
     * Constructor
     * 
     * @param type the object type that experienced this exception
     * @param msg a message to print on error
     */
    public InvalidDataException(String type, String msg) {
        super("Input for type: " + type + " is invalid. "+ msg);
    }
    
}
