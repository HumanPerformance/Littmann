import java.io.IOException;
import java.util.Vector;

import com.mmm.healthcare.scope.ConfigurationFactory;
import com.mmm.healthcare.scope.IBluetoothManager;
import com.mmm.healthcare.scope.Stethoscope;

public class BTconnect {

	public static void main(String[] args) {

		// Find stethoscopes to connect to ::
		IBluetoothManager manager = ConfigurationFactory.getBluetoothManager();
		Vector<Stethoscope> pairedStethoscopes = manager.getPairedDevices();
		
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


	}

}
