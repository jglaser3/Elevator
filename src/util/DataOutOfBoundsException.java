package util;

/**
 * Exceptions class for when values are out of bounds extends InvalidDataException.
 * 
 * @see util.InvalidDataException
 * @author raphaelshejnberg
 */
public class DataOutOfBoundsException extends InvalidDataException {
    
    /**
     * Constructor
     * 
     * @param dataType the type of data that is out of bounds
     * @param lowerBound the lower bound of the range of values
     * @param higherBound the higher bound of the range of values
     */
    public DataOutOfBoundsException(String dataType, int lowerBound, int higherBound) {
        super(dataType + " must be in range: " + lowerBound + " - " + higherBound);
    }
    
}
