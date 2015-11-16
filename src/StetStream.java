// StetStream
// Stethoscope Audio Stream to Computer
// Fluvio L Lobo Fenoglietto


public class StetStream {

	public static void main(String[] args) {
		
		// Stream audio from the stethoscope to the computer
		int numberOfAudioPacketsToReceive = 1000;
		byte[] stethoscopeAudioData = new byte[128 * numberOfAudioPacketsToReceive];
		byte[] packet = new byte[128];
		int offSet = 0;
		
		System.out.println("Starting Audio Input");
		stethoscope.startAudioInput();
		
		int receivedPackets = 0;
		while (receivedPackets < numberOfAudioPacketsToReceive) {
			
			int read = stethoscope.getAudioInputStream().read(packet, 0, packet.length);
			
			if (read > 0) {
				
				for (int k = 0; k < packet.length; k++) {
					
					// Add the received packet to the audio data
					stethoscopeAudioData[k + offSet] = packet[k];
					
				} // End of for
				
				receivedPackets++;
				offSet += packet.length;
				
			} // End of if
			
		} // End of while

	} // End of main

} // End of StetStream class
