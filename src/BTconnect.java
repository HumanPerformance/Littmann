// BTconnect
// Bluetooth connection to 3M Littmann Stethoscope
// Fluvio L Lobo Fenoglietto


// Imported libraries and dependencies
import java.io.IOException;
import java.util.Vector;
import com.mmm.healthcare.scope.ConfigurationFactory;
import com.mmm.healthcare.scope.IBluetoothManager;
import com.mmm.healthcare.scope.Stethoscope;

// Bluetooth connect class
public class BTconnect {

	public static void main(String[] args) {

		// Find stethoscopes to connect to ::
		IBluetoothManager manager = ConfigurationFactory.getBluetoothManager();
		Vector<Stethoscope> pairedStethoscopes = manager.getPairedDevices();
		
		// Verification of paired devices
		int numberofStethoscopes = pairedStethoscopes.size();	
		if (numberofStethoscopes > 0) {
		
			// List discovered devices
			for (Stethoscope discoveredStethoscope : pairedStethoscopes) {
				System.out.println(discoveredStethoscope.getSerialNumber());
			}
			
			// Connect to the first discovered stethoscope
			Stethoscope stethoscope = pairedStethoscopes.get(0);
			try {
				stethoscope.connect();
			} catch (IOException e) {
				System.out.println("Could not connect to the stethoscope!!");
			}		
			
		} else if (numberofStethoscopes == 0) {
			
			System.out.println("No paired Stethoscopes were found!");
			System.out.println("Pair a bluetooth-enabled device and re-execute this program!");
			
		}


	}

}
