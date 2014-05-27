package building;

import Timer.SimulationTimer;
import config.CreateProjectProperties;
import java.io.IOException;
import elevator.*;
import floor.FloorFacade;
import util.InvalidDataException;
/**
 * Main driver class for elevator simulation program
 * @author Raphael Shejnberg
 */

public class Driver {

    public static void main(String[] args) throws InterruptedException, InvalidDataException, IOException {
        CreateProjectProperties.writePropFile();
        Building simulation = new Building("projectProperties", false);
    }
    
    private static void wait(boolean cond) throws InterruptedException {
        while(cond){
            System.out.println(cond);
            Thread.sleep(500);
            
        }
    }
}