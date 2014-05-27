/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package building;

import config.CreateProjectProperties;
import config.CreateTestProperties;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import util.InvalidDataException;

/**
 *
 * @author iGlaserMac
 */
public class BuildingTest {
    
    public BuildingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNumPeopleCreatedPerMin method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetNumPeopleCreatedPerMin() throws InvalidDataException {
        System.out.println("testing getNumPeopleCreatedPerMin using custom parameter = 5...");
        CreateTestProperties prop = new CreateTestProperties();
        prop.setNumPeopleCreatedPerMin("5");
        prop.writePropFile();
        Building instance = new Building("testProperties", true);
        int expResult = 5;
        int result = instance.getNumPeopleCreatedPerMin();
        assertEquals("testing getNumPeopleCreatedPerMin using Building(16,4)",
                expResult, result);
    }

    /**
     * Test of setNumPeopleCreatedPerMin method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testSetNumPeopleCreatedPerMinError() throws InvalidDataException {
        System.out.println("testing setNumPeopleCreatedPerMin using invalid parameter = 0 ...");
        CreateTestProperties prop = new CreateTestProperties();
        prop.setNumPeopleCreatedPerMin("0");
        prop.writePropFile();
        Building instance;
        try {
            instance = new Building("testProperties", true);
        } catch (InvalidDataException ex) {
            assertEquals("testing invalid data error for setNumPeopleCreatedPerMin", 
                    "Number of people spawned  must be in range: 1 - 2147483647", ex.getMessage());
            Logger.getLogger(BuildingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getNumElevators method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetNumElevators() throws InvalidDataException {
        System.out.println("testing getNumElevators using valid parameter ...");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        Building instance = new Building("testProperties", true);
        int expResult = 4;
        int result = instance.getNumElevators();
        assertEquals("testing getNumElevators using Building value = 4", expResult, result);
    }

    /**
     * Test of setNumElevators method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testSetNumElevatorsError() throws InvalidDataException {
        System.out.println("testing setNumElevators using invalid value = 8");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        int numElevators = 8;
        Building instance;
        try {
            instance = new Building("testProperties", true);
            instance.setNumElevators(numElevators);
        } catch (InvalidDataException ex) {
            assertEquals("testing invalid data error for setNumElevators", 
                    "Not enough percentages for each floor", ex.getMessage());
            Logger.getLogger(BuildingTest.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    /**
     * Test of setStartingFloor method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testSetStartingFloorError() throws InvalidDataException {
        System.out.println("testing setStartingFloor using invalid parameter = 17 ...");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        int startingFloor = 17;
        Building instance;
        try {
            instance = new Building("testProperties", true);
            instance.setStartingFloor(startingFloor);
        } catch (InvalidDataException ex) {
            assertEquals("testing invalid data error for setStartingFloor", 
                    "Floor must be in range: 1 - 16", ex.getMessage());
            Logger.getLogger(BuildingTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /**
     * Test of getStartingFloor method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetStartingFloor() throws InvalidDataException {
        System.out.println("testing getStartingFloor using valid parameter...");
        CreateTestProperties prop = new CreateTestProperties();
        prop.setStartingFloor("16");
        prop.writePropFile();
        Building instance = new Building("testProperties", true);
        int expResult = 16;
        int result = instance.getStartingFloor();
        assertEquals("testing getStartingFloor using valid parameter = 16", expResult, result);
    }

    /**
     * Test of getNumFloors method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetNumFloors() throws InvalidDataException {
        System.out.println("testing getNumFloors using valid parameter...");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        Building instance = new Building("testProperties", true);;
        int expResult = 16;
        int result = instance.getNumFloors();
        assertEquals("testing getNumFloors using default parameter = 16", expResult, result);
    }

    /**
     * Test of setNumFloors method, of class Building.
     */
    @Test
    public void testSetNumFloorsError() {
        System.out.println("testing InvalidDataException for setNumFloors...");
        CreateTestProperties prop = new CreateTestProperties();
        prop.setNumFloors("1");
        prop.writePropFile();
        Building instance;
        try {
            instance = new Building("testProperties", true);
        } catch (InvalidDataException ex) {
            assertEquals("testing invalid data error for setNumFloors", 
                    "Not enough percentages for each floor", ex.getMessage());
        }
    }

    /**
     * Test of setSimTime method, of class Building.
     */
    @Test
    public void testSetSimTimeError(){
        System.out.println("testing InvalidDataException for setSimTime...");
        CreateTestProperties prop = new CreateTestProperties();
        prop.setsimTime("0");
        prop.writePropFile();
        Building instance;
        try {
            instance = new Building("testProperties", true);
        } catch (InvalidDataException ex) {
            assertEquals("testing invalid data error for setsimTime", 
                    "The simulation time  must be in range: 1 - 2147483647", ex.getMessage());
        }
    }

    /**
     * Test of getSimTime method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetSimTime() throws InvalidDataException {
        System.out.println("testing getSimTime using valid parameter");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        Building instance = new Building("testProperties", true);
        int expResult = 1;
        int result = instance.getSimTime();
        assertEquals("test of getSimTime using valid parameter = 1", expResult, result);
    }

    /**
     * Test of getDoorOperationTime method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetDoorOperationTime() throws InvalidDataException {
        System.out.println("testing getDoorOperationTime with valid parameter...");
        Building instance = new Building("testProperties", true);
        int expResult = 1;
        instance.setDoorOperationTime(expResult);
        int result = instance.getDoorOperationTime();
        assertEquals("test of getDoorOperationTime using valid parameter = 1", expResult, result);
    }

    /**
     * Test of setDoorOperationTime method, of class Building.
     */
    @Test
    public void testSetDoorOperationTimeError(){
        System.out.println("testing setDoorOperationTime");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        Building instance;
        try {
            instance = new Building("testProperties", true);
            instance.setDoorOperationTime(0);
        } catch (InvalidDataException ex) {
            assertEquals("testing invalid data error for setsimTime", 
                    "doorOperationTime  must be in range: 1 - 2147483647", ex.getMessage());
        }
    }

    /**
     * Test of getScale method, of class Building.
     */
    @Test
    public void testGetScale() {
        System.out.println("testing getScale");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        Building instance = new Building("testProperties", true);
        int expResult = 0;
        int result = instance.getScale();
        assertEquals(expResult, result);
    }

    /**
     * Test of setScale method, of class Building.
     */
    @Test
    public void testSetScale(){
        System.out.println("testing setScale");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        int scale = 0;
        Building instance = new Building("testProperties", true);
        instance.setScale(scale);
    }

    /**
     * Test of getTravelTime method, of class Building.
     */
    @Test
    public void testGetTravelTime() {
        System.out.println("testing getTravelTime");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        Building instance = new Building("testProperties", true);
        int expResult = 0;
        int result = instance.getTravelTime();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTravelTime method, of class Building.
     */
    @Test
    public void testSetTravelTime(){
        System.out.println("testing setTravelTime");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        int travelTime = 0;
        Building instance = new Building("testProperties", true);
        instance.setTravelTime(travelTime);
    }

    /**
     * Test of getMaxPeople method, of class Building.
     */
    @Test
    public void testGetMaxPeople() {
        System.out.println("testing getMaxPeople");
        Building instance = new Building("testProperties", true);
        int expResult = 0;
        int result = instance.getMaxPeople();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMaxPeople method, of class Building.
     */
    @Test
    public void testSetMaxPeople() {
        System.out.println("testing setMaxPeople");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        int maxPeople = 0;
        Building instance = new Building("testProperties", true);
        instance.setMaxPeople(maxPeople);
    }

    /**
     * Test of getSpawnRateByFloor method, of class Building.
     */
    @Test
    public void testGetSpawnRateByFloor() {
        System.out.println("testing getSpawnRateByFloor");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        int i = 0;
        Building instance = new Building("testProperties", true);
        int expResult = 0;
        int result = instance.getSpawnRateByFloor(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of setSpawnRateByFloor method, of class Building.
     * @throws util.InvalidDataException
     */
    @Test
    public void testSetSpawnRateByFloor() throws InvalidDataException {
        System.out.println("testing setSpawnRateByFloor");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        int[] spawnRateByFloor = null;
        Building instance = new Building("testProperties", true);
        instance.setSpawnRateByFloor(spawnRateByFloor);
    }

    /**
     * Test of run method, of class Building.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        CreateTestProperties prop = new CreateTestProperties();
        prop.writePropFile();
        Building instance = new Building("testProperties", true);
        instance.run();
    }
    
}