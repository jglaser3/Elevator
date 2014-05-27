/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package elevator;

import Panel.ElevatorPanel;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import person.Person;
import util.InvalidDataException;

/**
 *
 * @author iGlaserMac
 */
public class ElevatorTest {
    
    public ElevatorTest() {
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
     * Test of addDestination method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testAddDestination() throws InvalidDataException {
        System.out.println("testing addDestination");
        int destination = 0;
        Elevator instance = new ElevatorImpl(0, 0);
        instance.addDestination(destination);
    }

    /**
     * Test of powerOn method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testPowerOn() throws InvalidDataException {
        System.out.println("testing powerOn");
        Elevator instance = new ElevatorImpl(0, 0);
        instance.powerOn();
    }

    /**
     * Test of powerOff method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testPowerOff() throws InvalidDataException {
        System.out.println("powerOff");
        Elevator instance = new ElevatorImpl(0, 0);
        instance.powerOff();
    }

    /**
     * Test of getPassengers method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetPassengers() throws InvalidDataException {
        System.out.println("testing getPassengers");
        Elevator instance = new ElevatorImpl(0, 0);
        ArrayList<Person> expResult = null;
        ArrayList<Person> result = instance.getPassengers();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStatus method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetStatus() throws InvalidDataException {
        System.out.println("testing getStatus");
        Elevator instance = new ElevatorImpl(0, 0);
        String expResult = "";
        String result = instance.getStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of getID method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetID() throws InvalidDataException {
        System.out.println("testing getID");
        Elevator instance = new ElevatorImpl(0, 0);
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFloor method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetFloor() throws InvalidDataException {
        System.out.println("testing getFloor");
        Elevator instance = new ElevatorImpl(0, 0);
        int expResult = 0;
        int result = instance.getFloor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDirection method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetDirection() throws InvalidDataException {
        System.out.println("testing getDirection");
        Elevator instance = new ElevatorImpl(0, 0);
        int expResult = 0;
        int result = instance.getDirection();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTopFloor method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetTopFloor() throws InvalidDataException {
        System.out.println("testing getTopFloor");
        Elevator instance = new ElevatorImpl(0, 0);
        int expResult = 0;
        int result = instance.getTopFloor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDestFloor method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetDestFloor() throws InvalidDataException {
        System.out.println("testing getDestFloor");
        Elevator instance = new ElevatorImpl(0, 0);
        int expResult = 0;
        int result = instance.getDestFloor();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxPassengers method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetMaxPassengers() throws InvalidDataException {
        System.out.println("testing getMaxPassengers");
        Elevator instance = new ElevatorImpl(0, 0);
        int expResult = 0;
        int result = instance.getMaxPassengers();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPanel method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetPanel() throws InvalidDataException {
        System.out.println("testing getPanel");
        Elevator instance = new ElevatorImpl(0, 0);
        ElevatorPanel expResult = null;
        ElevatorPanel result = instance.getPanel();
        assertEquals(expResult, result);
    }

    /**
     * Test of free method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testFree() throws InvalidDataException {
        System.out.println("testing free");
        Elevator instance = new ElevatorImpl(0, 0);
        boolean expResult = false;
        boolean result = instance.free();
        assertEquals(expResult, result);
    }

    /**
     * Test of approaching method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testApproaching() throws InvalidDataException {
        System.out.println("testing approaching");
        int floor = 0;
        int direction = 0;
        Elevator instance = new ElevatorImpl(0, 0);
        boolean expResult = false;
        boolean result = instance.approaching(floor, direction);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMotionState method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testGetMotionState() throws InvalidDataException {
        System.out.println("testing getMotionState");
        Elevator instance = new ElevatorImpl(0, 0);
        boolean expResult = false;
        boolean result = instance.getMotionState();
        assertEquals(expResult, result);
    }

    /**
     * Test of elevatorAttendingTo method, of class Elevator.
     * @throws util.InvalidDataException
     */
    @Test
    public void testElevatorAttendingTo() throws InvalidDataException {
        System.out.println("testing elevatorAttendingTo");
        int floor = 0;
        Elevator instance = new ElevatorImpl(0, 0);
        boolean expResult = false;
        boolean result = instance.elevatorAttendingTo(floor);
        assertEquals(expResult, result);
    }   
}