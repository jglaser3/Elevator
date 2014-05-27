
package Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import javax.swing.Timer;
import util.DataOutOfBoundsException;
import util.InvalidDataException;


/**
 * Timer
 * 
 * @author Raphael Shejnberg
 */
public class SimulationTimer {
    
    /**
     * Allows for use of the timer to update each simulated second.
     * 
     * @see #setTimer(javax.swing.Timer) setTimer(javax.swing.Timer)
     */
    private Timer timer;
    
    /**
     * The current simulation to real time ratio.
     * 
     * @see #getScale() getScale()
     * @see #setScale(int) setScale(int)
     */
    private int scale;
    
    /**
     * This is a thread-safe instance of a Simulation Timer.
     * 
     * @see #getInstance() #getInstance()
     */
    private static volatile SimulationTimer self;

    /**
     * The current simulation time.
     * 
     * @see #getTime() getTime()
     * @see #setTime(long) setTime(long)
     */
    private static  Time time= new Time(0);
    
    /**
     * Gets the current simulation time.
     * 
     * @return the current time
     * @see #getTimeString()
     */
    private Time getTime() {
        return time;
    }

    /**
     * Sets the current simulation time.
     * 
     * @param t the simulation time to set
     * @throws DataOutOfBoundException when time is less than or equal to 0
     */
    private void setTime(long t) throws InvalidDataException {
        if(t <= 0)
            throw new DataOutOfBoundsException("Time", 1, Integer.MAX_VALUE);
        else
            time.setTime(t);
    }
    
    /**
     * Sets the current objects timer.
     * 
     * @param t the simulation time to set
     * @throws InvalidDataException 
     */
    private void setTimer(Timer t) throws InvalidDataException {
        this.timer = t;
    }
    
    /**
     * This is the default constructor.
     */
    private SimulationTimer(){}
    
    /**
     * Gets only one simulation timer via a Singleton pattern method.
     * 
     * @return a single simulation timer instance
     */
    public static SimulationTimer getInstance(){
        if(self == null)
            synchronized(SimulationTimer.class)
            {
                if(self == null)
                    self = new SimulationTimer();
            }
        
        return self;
    }
    
    /**
     * Updates the timer on iterations occurring n seconds apart.
     */
    ActionListener setTimeAction = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent evt) {
            synchronized(getTime())
            {
                Time newTime = Time.valueOf(getTime().toString());
                newTime.setSeconds(newTime.getSeconds() + 1);
                try 
                {
                    setTime(newTime.getTime());
                } 
                catch (InvalidDataException ex) {}
            }
        }
    };
            
    /**
     * Sets the time rate to target ratio.
     * 
     * @param scale the current simulation to real time ratio to set
     * @throws DataOutOfBoundsException if scale is less than 1
     */
    public void setScale(int scale) throws InvalidDataException {
        if(scale < 1)
            throw new DataOutOfBoundsException("Ratio", 1, Integer.MAX_VALUE);
        
        this.scale = scale;
        setTimer(new Timer((1000 / scale), setTimeAction));
        timer.start();
    }
    
    /**
     * Gets the simulation time to real time ratio.
     * 
     * @return the simulation time to real time ratio
     */
    public int getScale(){
        return scale;
    }
    
    /**
     * Gets the current time as a string.
     * 
     * @return a string representing the current time
     */
    public String getTimeString(){
        synchronized(getTime())
        {
            return getTime().toString();
        }
    }
}
