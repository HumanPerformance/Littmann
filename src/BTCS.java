import java.io.IOException;
import java.util.Vector;

import com.mmm.healthcare.scope.ConfigurationFactory;
import com.mmm.healthcare.scope.IBluetoothManager;
import com.mmm.healthcare.scope.Stethoscope;

public class BTCS {

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
				System.out.println("Successfully connected to stethoscope!");
			} catch (IOException e) {
				System.out.println("Could not connect to the stethoscope!");
			}			
			
			// Stream audio from the stethoscope to the computer
			int numberOfAudioPacketsToReceive = 1000;
			byte[] stethoscopeAudioData = new byte[128 * numberOfAudioPacketsToReceive];
			byte[] packet = new byte[128];
			int offSet = 0;
			
			System.out.println("Starting Audio Input");
			stethoscope.startAudioInput();
			
			int receivedPackets = 0;
			while (receivedPackets < numberOfAudioPacketsToReceive) {
				
				int read = 0;
				try {
					read = stethoscope.getAudioInputStream().read(packet, 0, packet.length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (read > 0) {
					
					for (int k = 0; k < packet.length; k++) {
						
						// Add the received packet to the audio data
						stethoscopeAudioData[k + offSet] = packet[k];
						
					} // End of for
					
					receivedPackets++;
					offSet += packet.length;
					
				} // End of if
				
			} // End of while
			
			System.out.println("Stoping Audio Input");
			stethoscope.stopAudioInput();			
			
		} else if (numberofStethoscopes == 0) {
			
			System.out.println("No paired Stethoscopes were found!");
			System.out.println("Pair a bluetooth-enabled device and re-execute this program!");
			
		} // End of if-statement
				
	}

}
