package config;
 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
 
public class CreateTestProperties {

    private String numFloors = "16";
    private String numElevators = "4";
    private String simTime = "1";
    private String scale = "1";
    private String maxPeople = "1";
    private String travelTime = "1";
    private String numPeopleCreatedPerMin = "1";   
    private String startingFloor = "1";
    
    public void writePropFile() {
 
	Properties prop = new Properties();
	OutputStream output = null;
 
	try {
                // select test file to write to
		output = new FileOutputStream("testProperties");
 
		// set properties based on what will be tested.
                // these values = "0" by default and should be set while testing
                prop.setProperty("numFloors", numFloors);
                prop.setProperty("numElevators", numElevators);
		prop.setProperty("simTime", simTime);
		prop.setProperty("scale", scale);
		prop.setProperty("maxPeople", maxPeople);
                prop.setProperty("travelTime", travelTime);
                prop.setProperty("numPeopleCreatedPerMin", numPeopleCreatedPerMin);
                prop.setProperty("startingFloor", startingFloor);
 
		// save properties to project root folder
		prop.store(output, null);
 
	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
	}
  }
    public void setNumFloors(String s){
        numFloors = s;
    }
    public void setNumElevators(String s){
        numElevators = s;
    }
    public void setsimTime(String s){
        simTime = s;
    }
    public void setScale(String s){
        scale = s;
    }
    public void setMaxPeople(String s){
        maxPeople = s;
    }
    public void setTravelTime(String s){
        travelTime = s;
    }
    public void setNumPeopleCreatedPerMin(String s){
        numPeopleCreatedPerMin = s;
    }
    public void setStartingFloor(String s){
        startingFloor = s;
    }
}