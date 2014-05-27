package elevator;
import util.InvalidDataException;

/*
 * Factory class for Elevator
 * @see elevator.ElevatorImpl 
 * author Raphael Shejnberg
 */
public class ElevatorFactory {
    /*
     * Default constructor
     */
    private ElevatorFactory() {}
    
    /**
     * Elevator object builder
     * 
     * @param EID the elevator ID
     * @param topFloor the top floor of the building
     * @return new elevator object
     * @see elevator.ElevatorImpl#ElevatorImpl(int, int) 
     * @throws InvalidDataException 
     */
    public static Elevator create(int EID, int topFloor) throws InvalidDataException {
        return new ElevatorImpl(EID, topFloor);
    }
}
