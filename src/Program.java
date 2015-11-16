/////////////////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 3M and its licensors 2013. All Rights Reserved. This software and         //
// associated files are licensed under the terms of the signed license agreement.  All     //
// sample code & sample applications are provided for demonstration purposes only and      //
// should not be used for commercial or diagnostic purposes.                               //
/////////////////////////////////////////////////////////////////////////////////////////////

// package com.mmm.healthcare.scopes.device.consolesample;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.mmm.healthcare.scope.*;

/**
 * Connects to a stethoscope using the console.
 * 
 * @author 3M Company
 * 
 */
public class Program {

	private static Scanner scanner = new Scanner(System.in);

	/**
	 * The main entry point into this application.
	 * 
	 * @param arguments
	 */
	public static void main(String[] arguments) throws Exception {

		String lincense = "Copyright (c) 3M and its licensors 2013. All Rights Reserved. This software "
			+ "and associated files are licensed under the terms of the signed license agreement. "
			+ "All sample code & sample applications are provided for demonstration purposes only "
			+ "and should not be used for commercial or diagnostic purposes.";
		System.out.println(lincense);

		System.out.println();
		System.out.println("Welcome to the stethoscope console sample application.");

		// find the stethoscope to connect to.
		Stethoscope stethoscope = selectStethoscope();
		if (stethoscope == null) {
			System.out.println();
			System.out.println("No stethosocpe was selected.");

			return;
		}

		// listens to button clicks, errors, low battery, etc on stethoscope.
		addStethoscopeListener(stethoscope);

		System.out.println();
		System.out.println("Attempting to connect to " + stethoscope.getSerialNumber() + " stethoscope.");

		// attempts to connect to the stethoscope using Bluetooth.
		// note, the stethoscope should be paired to the computer and ready to
		// connect.
		try {
			stethoscope.connect();
		} catch (IOException exception) {
			System.out.println();
			System.out.println("Could not connect to stethosocpe.");

			return;
		}

		System.out.println("Connect to " + stethoscope.getSerialNumber() + " stethoscope.");

		// displays most of the stethoscope properties (e.g. filter, sound
		// amplification level, etc).
		generateStethoscopeReport(stethoscope);

		setDisplayWithSampleImage(stethoscope);

		System.out.println();
		System.out.println("Listening to stethoscope button events.");
		System.out.println();
		System.out.println("Type 'quit' to exit the program...");

		// loop until quit is typed.
		String line = scanner.next();
		while (!line.equalsIgnoreCase("quit")) {
			line = scanner.next();
		}

		// disconnect the stethoscope.
		System.out.println("Disconnecting from stethoscope...");
		stethoscope.disconnect();

		System.exit(0);
	}

	/**
	 * Generates the stethoscope report.
	 * 
	 * @param stethoscope
	 *            A connected stethoscope.
	 */
	private static void generateStethoscopeReport(Stethoscope stethoscope) {

		System.out.println();
		System.out.println("==== General  ====");
		System.out.println("Name = " + stethoscope.getName());
		System.out.println("Filter = " + stethoscope.getFilter());
		System.out.println("SoundAmplificationLevel = " + stethoscope.getSoundAmplificationLevel());

		System.out.println();
		System.out.println("==== Buttons ====");
		System.out.println("IsMButtonEnabled = " + stethoscope.getIsMButtonEnabled());
		System.out.println("IsFilterButtonEnabled = " + stethoscope.getIsFilterButtonEnabled());
		System.out.println("IsPlusAndMinusButtonsEnabled = " + stethoscope.getIsPlusAndMinusButtonsEnabled());

		System.out.println();
		System.out.println("==== Timeout Settings  ====");
		System.out.println("SleepTimeoutMinutes = " + stethoscope.getSleepTimeoutMinutes());
		System.out.println("ActiveTimeoutDeciseconds = " + stethoscope.getActiveTimeoutDeciseconds());
		System.out.println("BacklightTimeoutDeciseconds = " + stethoscope.getBacklightTimeoutDeciseconds());
		System.out.println("BluetoothTimeoutDeciseconds = " + stethoscope.getBluetoothTimeoutDeciseconds());
		System.out.println("AutomaticOffTimeoutDeciseconds = " + stethoscope.getAutomaticOffTimeoutDeciseconds());
		System.out.println("BluetoothPairTimeoutDeciseconds = " + stethoscope.getBluetoothPairTimeoutDeciseconds());
	}

	/**
	 * Adds a listener to the stethoscope.
	 * 
	 * @param stethoscope
	 *            The connected stethoscope.
	 */
	private static void addStethoscopeListener(final Stethoscope stethoscope) {
		stethoscope.addStethoscopeListener(new IStethoscopeListener() {

			@Override
			public void plusButtonDown(boolean isLongButtonClick) {

				System.out.println();
				System.out.println("The plus button has been clicked.");
				System.out.println("The sound amplification level has been " + "changed to <"
						+ stethoscope.getSoundAmplificationLevel() + ">.");
				System.out.println("The plus button click was " + (isLongButtonClick ? "long" : "short") + ".");

			}

			@Override
			public void onAndOffButtonDown(boolean isLongButtonClick) {

				System.out.println();
				System.out.println("The on and off button has been clicked.");
				System.out.println("The on and off button click was " + (isLongButtonClick ? "long" : "short") + ".");
			}

			@Override
			public void minusButtonDown(boolean isLongButtonClick) {

				System.out.println();
				System.out.println("The minus button has been clicked.");
				System.out.println("The sound amplification level has been changed" + " to <"
						+ stethoscope.getSoundAmplificationLevel() + ">.");
				System.out.println("The minus button click was " + (isLongButtonClick ? "long" : "short") + ".");

			}

			@Override
			public void mButtonDown(boolean isLongButtonClick) {
				System.out.println();
				System.out.println("The m button has been clicked.");
				System.out.println("The m button click was " + (isLongButtonClick ? "long" : "short") + ".");
			}

			@Override
			public void filterButtonDown(boolean isLongButtonClick) {

				System.out.println();
				System.out.println("The filter button has been clicked.");
				System.out.println("The filter has been changed to <" + stethoscope.getFilter() + ">.");
				System.out.println("The filter button click was " + (isLongButtonClick ? "long" : "short") + ".");
			}

			@Override
			public void error(Errors error, String message) {
				System.out.println();
				System.out.println("The stethoscope has encountered an error. " + error.name() + ": " + message);
			}

			@Override
			public void lowBatteryLevel() {
				System.out.println();
				System.out.println("The stethoscope has a low battery level.");
			}

			@Override
			public void disconnected() {
				System.out.println();
				System.out.println("The stethoscope <" + stethoscope.getName() + "> has been disconnected.");
			}

			@Override
			public void mButtonUp() {
				System.out.println();
				System.out.println("The m button has been released.");
			}

			@Override
			public void plusButtonUp() {
				System.out.println();
				System.out.println("The plus button has been released");
			}

			@Override
			public void minusButtonUp() {
				System.out.println();
				System.out.println("The minus button has been released.");
			}

			@Override
			public void filterButtonUp() {
				System.out.println();
				System.out.println("The filter button has been released.");
			}

			@Override
			public void onAndOffButtonUp() {
				System.out.println();
				System.out.println("The power button has been released.");
			}

			@Override
			public void endOfOutputStream() {
				// used only during audio streaming. Not used in this sample.
			}

			@Override
			public void endOfInputStream() {
				// used only during audio streaming. Not used in this sample.
			}

			@Override
			public void outOfRange(boolean isOutOfRange) {

				if (isOutOfRange == true) {

					// stethoscope is out of range.

					System.out.println();
					System.out.println("The stethoscope is out of range.");
				} else {

					// stethoscope came back in to range.

					System.out.println();
					System.out.println("The stethoscope came back in to range.");
				}

			}

			@Override
			public void underrunOrOverrunError(boolean isUnderrun) {
				if (isUnderrun == true) {

					// stethoscope is underrun.

					System.out.println();
					System.out
							.println("The stethoscope is underrun. The stethoscope could not play audio continuously because not enough audio packets were received.");
				} else {

					// stethoscope is override.

					System.out.println();
					System.out
							.println("The stethoscope is overrun. the stethoscope discarded audio packets because too many audio packets were sent.");
				}

			}
		});
	}

	/**
	 * Finds a stethoscope to connect to from the user.
	 * 
	 * @return The selected stethoscope.
	 */
	private static Stethoscope selectStethoscope() {

		// gets the manager to find the stethoscopes.
		// note, the Bluetooth manager can also be used to pair and unpair
		// stethoscopes.
		IBluetoothManager manager = null;

		try {
			manager = ConfigurationFactory.getBluetoothManager();
		} catch (Exception exception) {
			// could not find or bad license file.
			System.out.println();
			System.out.println(exception.getMessage());
			System.out.println();
			System.out.println("Press any key to continue...");

			scanner.next();
			System.exit(0);
		}

		// verify the computer has a Bluetooth antenna.
		if (manager.hasBluetoothAntenna() == false) {
			System.out
					.println("Could not find Bluetooth antenna. Please make sure the USB dongle is plugged in and Blueooth is enabled.");

			System.exit(0);
		}

		// finds all the paired stethoscopes to this computer.
		Vector<Stethoscope> discoveredStethoscopes = manager.getPairedDevices();

		// finds the stethoscope the user wants to connect to by serial number.
		// serial number is located on the side of the stethoscope.
		System.out.println("Please select a stethoscope to connect to...");
		int index = 0;
		for (Stethoscope stethoscope : discoveredStethoscopes) {
			System.out.println("\t" + index + " - " + stethoscope.getSerialNumber());
			index++;
		}

		System.out.print(">");

		// reads in the selected stethoscope index.
		int selectedIndex = scanner.nextInt();
		Stethoscope stethoscope = discoveredStethoscopes.get(selectedIndex);

		return stethoscope;
	}

	/**
	 * Streams audio from the connected stethoscopes into the computer
	 * 
	 * @param stethoscope
	 * @throws IOException
	 */
	private static void streamfromStethoscope(Stethoscope stethoscope) throws IOException {
		
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
	}
	
	private static void setDisplayWithSampleImage(Stethoscope stethoscope) {
		String name = "sample.bmp";

		// attempt to get image from jar.
		InputStream imageFileStream = Program.class.getClassLoader().getResourceAsStream(name);

		// if not found in jar, find near the Program class.
		if (imageFileStream == null) {
			imageFileStream = Program.class.getResourceAsStream(name);
		}

		try {
			IBitmap bitmap = BitmapFactory.createBitmap(imageFileStream);
			stethoscope.setDisplay(bitmap);
		} catch (Exception e) {
			System.out.println("Could not find the bitmap " + name + " file.");
		}
	}
}
