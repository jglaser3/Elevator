
package building;

import Display.Display;
import Display.Stats;
import Timer.SimulationTimer;
import elevator.ElevatorController;
import floor.FloorFacade;
import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import person.*;
import util.*;
/**
 *This elevator simulator program models a building including its floors, elevators, call boxes, controllers,
 * and people in order to determine the optimal elevator configuration for the building as well as the impact
 * of a downed elevator.
 *<br><br>
 *Input: The number of floors, number of elevators, max persons per elevator, speed of the elevator (time per
 * floor), and door operation time. These are read from an XML file.
 *<br><br>
 * Output: A timed narrative description of activity.
 *<br><br>
 * 
 * @author Raphael Shejnberg
 * @since version 1.0
 */
public final class Building implements Runnable, Serializable {
    /**
     * This is the thread that runs the simulator.
     * 
     * @see #getSimThread() getSimThread()
     */
    private transient Thread simThread;
    
    /**
     * The number of floors that exist in the building.
     * 
     * @see #getNumFloors() getNumFloors()
     * @see #setNumFloors(int) 
     */
    private int numFloors;
    
    /**
     * The number of elevators to be created.
     * 
     * @see #getNumElevators() getNumElevators()
     * @see #setNumElevators(int) setNumElevators(int)
     */
    private int numElevators;
    
    /**
     * Represents the default floor of all the elevators.
     * 
     * @see #getStartingFloor() getStartingFloor()
     * @see #setStartingFloor(int) setStartingFloor(int)
     */
    private int startingFloor;

    /**
     * Percent of the number of people that were generated
     * 
     * @see #getSpawnRateByFloor() getSpawnRateByFloor()
     * @see #getSpawnRateByFloor(int) getSpawnRateByFloor(int)
     * @see #setSpawnRateByFloor(int[]) setSpawnRateByFloor(int[])
     */
    private int spawnRateByFloor[];
    
    /**
     * Simulation time versus real time.
     * 
     * @see #getScale() getScale()
     * @see #setScale(int) setScale(int)
     */
    private int scale;
    
    /**
     * The max amount of people that can exist in the elevator.
     * 
     * @see #getMaxPeople() getMaxPeople()
     * @see #setMaxPeople(int) setMaxPeople(int)
     */
    private int maxPeople;
    
    /**
     * Door operation time in milliseconds.
     *
     * @see #getDoorOperationTime() getDoorOperationTime()
     * @see #setDoorOperationTime(int) setDoorOperationTime(int)
     */
    private int doorOperationTime;
    
    /**
     * Number of People to add per minute.
     * 
     * @see #getNumPeopleCreatedPerMin() getNumPeopleCreatedPerMin()
     * @see #setNumPeopleCreatedPerMin(int) setNumPeopleCreatedPerMin(int)
    */
    private int numPeopleCreatedPerMin;
    
    /**
     * Total runtime cap for program.
     * 
     * @see #getSimTime() getSimTime()
     * @see #setSimTime(int) setSimTime(int)
    */
    private int simulationTime;
    
    /**
     * Elevator travel time (in milliseconds).
     * 
     * @see #getTravelTime() getTravelTime()
     * @see #setTravelTime(int) setTravelTime(int)
    */
    private int travelTime;
    
    /**
     * This Building runs the simulator and builds the class through an xml configuration file.
     * 
     * @param numFloors
     * @param numElevators
     * @param propertiesFile
     * @param test - set to false during full project run / true to test
     * @throws util.InvalidDataException
     */ 
    public Building(String propertiesFile, boolean test) throws InvalidDataException {
        Properties prop = new Properties();
	InputStream input = null;
 
	try {
 
		input = new FileInputStream(propertiesFile); 
		// load the properties file from parameter
		prop.load(input);
 
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
        int numFloors = Integer.parseInt(prop.getProperty("numFloors"));
        int numElevators = Integer.parseInt(prop.getProperty("numElevators"));
        int simTime = Integer.parseInt(prop.getProperty("simTime"));
        int scale = Integer.parseInt(prop.getProperty("scale"));
        int maxPeople = Integer.parseInt(prop.getProperty("maxPeople"));
        int travelTime = Integer.parseInt(prop.getProperty("travelTime"));
        int numPeopleCreatedPerMin = Integer.parseInt(prop.getProperty("numPeopleCreatedPerMin"));
        int startingFloor = Integer.parseInt(prop.getProperty("startingFloor"));
        
        setNumFloors(numFloors);
        setNumElevators(numElevators);
        setSimTime(simTime);
        setScale(scale);
        setMaxPeople(maxPeople);
        setTravelTime(travelTime);
        int[] perc = {15, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 5};
        setSpawnRateByFloor(perc);
        setNumPeopleCreatedPerMin(numPeopleCreatedPerMin);
        setStartingFloor(startingFloor);
        
        if(test == false){
            SimulationTimer.getInstance().setScale(getScale());
            simThread = new Thread(this);
            FloorFacade.getInstance().initFloors(this.numFloors);
            ElevatorController.getInstance().initElevators(this.numElevators, this.numFloors);
            startSimulation();
        }
    }
    
    public Building() {
    
    }
    /**
     * Starts the simulation.
     */
    private void startSimulation() {
        getSimThread().start();
    }
    /**
     * Gets the thread that runs the building.
     * 
     * @return the thread that runs Building Simulation
     */
    private Thread getSimThread() {
        return simThread;
    }
    
      /*private void setTotalNumberPeople() {
        this.totalNumberPeople = getSpawnRatePerMinute() * (getSimTime() / 60);
        if(this.totalNumberPeople == 0)
            this.totalNumberPeople++;
    }
    
    private int getTotalNumberPeople() {
        return this.totalNumberPeople;
    }
    */
    /** 
     * Gets the amount of people created per minute.
     * 
     * @return The number of people to generate per minute
     */
    public int getNumPeopleCreatedPerMin() {
        return numPeopleCreatedPerMin;
    }
    
    /**
     * Sets the amount of people created per minute.
     * 
     * @param numPeopleCreatedPerMin the numPeopleCreatedPerMin to set
     * @throws InvalidDataException when spawn rate is 0 or less 
     */
    public void setNumPeopleCreatedPerMin(int numPeopleCreatedPerMin) throws InvalidDataException {
        if(numPeopleCreatedPerMin < 1)
            throw new DataOutOfBoundsException("Number of people spawned ", 1, Integer.MAX_VALUE);
        else
            this.numPeopleCreatedPerMin = numPeopleCreatedPerMin;
    }
    
    /**
     * Gets the amount of elevators to be created.
     * 
     * @return the number of elevators to make
     */
    public int getNumElevators() {
        return this.numElevators;
    }
    
    /**
     * Sets how many elevators to make.
     * 
     * @param numElevators how many elevators to make 
     * @throws InvalidDataException when Elevator is less than 1
     */
    public void setNumElevators(int numElevators) throws InvalidDataException {
        if(numElevators < 1)
            throw new DataOutOfBoundsException("Number of elevators", 1, Integer.MAX_VALUE);
        else
            this.numElevators = numElevators;
    }

    /**
     * Sets the default floor of all elevator.
     * 
     * @param startingFloor int array representation of which floor to go to when Idle
     * @throws DataOutOfBoundsException when floor is less than 1 or above the maxfloor
     */
    public void setStartingFloor(int startingFloor) throws InvalidDataException {
        if(startingFloor < 1 || startingFloor > getNumFloors())
            throw new DataOutOfBoundsException("Floor", 1, getNumFloors());
        this.startingFloor = startingFloor;
    }

   /**
    * Gets the default floor of the elevators.
    * 
    * @return int representation of which floor to go to when Idle
    */
    public int getStartingFloor() {
        return this.startingFloor;
    }

   /**
     * Gets the number of floors to create.
     * 
     * @return the number of floors to make
     */
    public int getNumFloors() {
        return this.numFloors;
    }

   /**
     * Sets the amount of floors to make.
     * 
     * @param numFloors how many floors to make
     * @throws InvalidDataException when floor is 0 or below 
     */
    public void setNumFloors(int numFloors) throws InvalidDataException {
        if(numFloors < 1)
            throw new DataOutOfBoundsException("The number of floors ", 1, Integer.MAX_VALUE);
        else
            this.numFloors = numFloors;
    }
 
   /**
     * Sets how long the simulation will run.
     * 
     * @param simulationTime length of time to run (in seconds)
     * @throws InvalidDataException when simTime is less than 0
     */ 
    public void setSimTime(int simulationTime) throws InvalidDataException {
        if(simulationTime < 1)
            throw new DataOutOfBoundsException("The simulation time ", 1, Integer.MAX_VALUE);
        else
            this.simulationTime = simulationTime;
    }

   /**
     * Gets how long the simulation will run.
     * 
     * @return length of time the simulation is to run (in seconds)
     */
    public int getSimTime() {
       return simulationTime;
    }
    
   /**
     * Gets how long it takes to open and close the elevator door.
     * 
     * @return the length of time to open and close the elevator door (in milliseconds)
     */
    public int getDoorOperationTime() {
        return doorOperationTime;
    }
    
    /**
     * Sets the length of time it takes to open and close the door.
     * 
     * @param doorOperationTime the doorOperationTime to set (in milliseconds)
     * @throws InvalidDataException when length of time is less than 1 millisecond
     */
    public void setDoorOperationTime(int doorOperationTime) throws InvalidDataException {
        if(doorOperationTime < 1)
            throw new DataOutOfBoundsException("doorOperationTime ", 1, Integer.MAX_VALUE);
        else
            this.doorOperationTime = doorOperationTime;
    }
    
    /**
     * Get the time ratio for simulation to real time.
     * 
     * @return the ratio of simulation time vs real time
     */ 
    public int getScale() {
        return this.scale;
    }
    
    /**
     * Set the time ratio for simulation to real time.
     * 
     * @param scale the ratio of simulation time to real time to set
     * @throws InvalidDataException when ration is less than 1
     */
    public void setScale(int scale) throws InvalidDataException {
        if(scale < 1)
            throw new DataOutOfBoundsException("scale", 1, Integer.MAX_VALUE);
        else
            this.scale = scale;
    }
    
    /**
     * Gets the time it takes for the elevator to go one floor.
     * 
     * @return the length of time it takes an elevator to travel to 1 floor (in milliseconds)
     */
    public int getTravelTime() {
        return travelTime;
    }

    /**
     * Sets the time it takes for the elevator to go one floor.
     * 
     * @param travelTime the length of time to set
     * @throws InvalidDataException when travel time for elevator is less than 1 millisecond
     */
    public void setTravelTime(int travelTime) throws InvalidDataException {
        if(travelTime < 1)
            throw new DataOutOfBoundsException("Travel-time", 1, Integer.MAX_VALUE);
        else
            this.travelTime = travelTime;
    }

    /**
     * Gets the max amount of people that can exist in the elevator.
     * 
     * @return the maximum number of people an elevator holds
     */
    public int getMaxPeople() {
        return maxPeople;
    }

    /**
     * Sets the max amount of people that can exist in the elevator.
     * 
     * @param maxPeople the maximum number of people to set
     * @throws InvalidDataException when maximum number of people is less than 1
     */
    public void setMaxPeople(int maxPeople) throws InvalidDataException {
        if(maxPeople < 1)
            throw new DataOutOfBoundsException("Max number of people", 1, Integer.MAX_VALUE);
        else
            this.maxPeople = maxPeople;
    }

    /**
     * This returns the array reference to the percentage tables.
     * 
     * @return a table of percentages referring to how much population density should be on each floor
     */
    private int[] getSpawnRateByFloor() {
        return spawnRateByFloor;
    }

    /**
     * Gets the percentage of people starting at each floor.
     * 
     * @param i the index of the desired floor 
     * @return the percentage of people starting on the desired floor
     */
    public int getSpawnRateByFloor(int i) {
        return spawnRateByFloor[i];
    }
    
    /**
     * Sets the percentage of people starting on each floor.
     * 
     * @param percentageStart the percentageStart to set
     * @throws InvalidDataException when percentageStart has less or more rows than what is necessary
     */
    public void setSpawnRateByFloor(int[] spawnRateByFloor) throws InvalidDataException {
        if(spawnRateByFloor.length != this.numFloors)
            throw new InvalidDataException("Not enough percentages for each floor");
        else
            this.spawnRateByFloor = spawnRateByFloor;
    }
    
    /**
     * Initializes a person with a random floor and destination floor and adds them to 
     * the appropriate lists
     * @param randInt
     * @param people
     * @param percentPeopleOnFloor
     * @param totalCount
     * @return The percent of people on the floor that the person has been created on
     * @throws InvalidDataException 
     */
    private int initPerson(Random randInt, ArrayList<Person> people, int percentPeopleOnFloor, int totalCount) throws InvalidDataException {
        int personFloor, personDestFloor;
        
        personFloor = 1 + randInt.nextInt(FloorFacade.getInstance().getNumFloors());
        do
        {
            personDestFloor = 1 + randInt.nextInt(FloorFacade.getInstance().getNumFloors());
            percentPeopleOnFloor = FloorFacade.getInstance().getTotalCreated(personFloor) / totalCount;            
        }
        while(personFloor == personDestFloor || personFloor >= this.numFloors|| personDestFloor >= this.numFloors || percentPeopleOnFloor >= getSpawnRateByFloor(personFloor));

        people.add(PersonFactory.create(personFloor, personDestFloor));
        System.out.println(Display.displayWithTime("Person created on floor " + personFloor + " going to " + personDestFloor));
        FloorFacade.getInstance().addPerson(people.get(people.size()-1).getFloor(), people.get(people.size()-1));
        return percentPeopleOnFloor;
    }
    /**
     * This method runs the Simulation by creating the people and adding them to floors. 
     * The simulation ends when the Time is over and all elevators are at floor 1 and are shutdown.
     */
    @Override
    public void run() {
        
        ArrayList<Person> people = new ArrayList<>();
        
        int totalCount = getNumPeopleCreatedPerMin() * (getSimTime() / 60);
        String currentTime = SimulationTimer.getInstance().getTimeString();
        Time finishTime = Time.valueOf(currentTime);
        finishTime.setSeconds(finishTime.getSeconds() + getSimTime());
        
        
        if(totalCount == 0)
            totalCount++;
        
        Random randInt = new Random();
        int personFloor, personDestFloor, percentPeopleOnFloor = 0;
        while(Time.valueOf(currentTime).compareTo(finishTime) < 0)
        {
            int waitTime = 60000 / getNumPeopleCreatedPerMin();
            try 
            {
                percentPeopleOnFloor = initPerson(randInt, people, percentPeopleOnFloor, totalCount);
                Thread.sleep(waitTime / SimulationTimer.getInstance().getScale());
            } 
            catch (InterruptedException | InvalidDataException ex) {} 
       
            currentTime = SimulationTimer.getInstance().getTimeString();
        }
        ElevatorController.getInstance().totalShutdown();
        
        while(!ElevatorController.getInstance().nothingToDo())
            try {
                Thread.sleep(2000 / SimulationTimer.getInstance().getScale());
            } catch (InterruptedException ex) {}
        
        System.out.println(Display.displayWithTime("Simulation has completed successfully."));
        synchronized(Building.class)
        {
            Stats statsDisplay = new Stats(this, people);
            statsDisplay.printPeople();
            statsDisplay.printWaitTimes();
            statsDisplay.printTimeInElevator();
        }
    }
}
