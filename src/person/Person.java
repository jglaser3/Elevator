
package person;

import util.InvalidDataException;
/*
 * Person interface
 * @author Raphael Shejnberg
 */
public interface Person {
    
    
    int defaultStartFloor = 0;
    
    void arrived(int floor) throws InvalidDataException;
    void boardElevator();
    void pressButton();
    
    int getDirection();
    int getDestFloor();
    int getFloor();
  
    String getStatus();
    
    long getWaitTime();
    long getTimeInElevator();
    long getTimeStorage();
}
