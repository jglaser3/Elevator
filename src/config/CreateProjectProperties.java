package config;
 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
 
public class CreateProjectProperties {
    /**
     *
     */
    public static void writePropFile() {
 
	Properties prop = new Properties();
	OutputStream output = null;
 
	try {
                // select file to write to
		output = new FileOutputStream("projectProperties");
 
		// set properties based on version requirements
                prop.setProperty("numFloors", "16");
                prop.setProperty("numElevators", "4");
		prop.setProperty("simTime", "300");
		prop.setProperty("scale", "2");
		prop.setProperty("maxPeople", "10");
                prop.setProperty("travelTime", "1000");
                prop.setProperty("numPeopleCreatedPerMin", "15");
                prop.setProperty("startingFloor", "1");
 
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
}