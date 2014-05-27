package Panel;

import elevator.ElevatorRequest;
import person.Person;
import util.InvalidDataException;

/**
 * An interface for all panels
 * @author raphaelshejnberg
 */
public interface ButtonPanel {
    void pressButton(Person p) throws InvalidDataException;
}
