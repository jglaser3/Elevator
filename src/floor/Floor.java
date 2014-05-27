package floor;

import Panel.FloorCallBox;
import java.util.ArrayList;
import person.Person;

/**
 * Floor interface
 * @author Raphael Shejnberg
 */
public interface Floor {
    
    void addPerson(Person p);
    public void acceptPerson(Person p);
    void removePerson(Person p);
    Person getPerson(Person p);
    
    int getNumPeople();
    FloorCallBox getCallBox();
    ArrayList<Person> getPeople();
    int getTotalCreated();
}
