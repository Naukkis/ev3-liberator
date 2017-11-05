package behaviors;

import communications.Communicator;
import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.robotics.subsumption.Behavior;
/**
 * Akun tilan ohjaavalle tietokoneelle välittävä luokka. Ottaa robotin haltuun, jos akun varaus liian matala.
 *
 * @author Team 2
 *
 */
public class BatteryLevelCommunicator implements Behavior {
	private Communicator communicator;

	private boolean suppressed;
	 private static final short [] note = {
		      2349,115, 0,5, 1760,165, 0,35, 1760,28, 0,13, 1976,23,
		      0,18, 1760,18, 0,23, 1568,15, 0,25, 1480,103, 0,18,
		      1175,180, 0,20, 1760,18, 0,23, 1976,20, 0,20, 1760,15,
		      0,25, 1568,15, 0,25, 2217,98, 0,23, 1760,88, 0,33, 1760,
		      75, 0,5, 1760,20, 0,20, 1760,20, 0,20, 1976,18, 0,23,
		      1760,18, 0,23, 2217,225, 0,15, 2217,218};
	public BatteryLevelCommunicator(Communicator communicator) {
		this.communicator = communicator;
	}

	@Override
	public boolean takeControl() {
		float batteryLevel = Battery.getVoltage();
		communicator.sendMessage(" " + batteryLevel);
		return batteryLevel < 1;

	}

	@Override
	public void action() {
		suppressed = false;
		play();
		System.exit(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

	public void play() {
	      for(int i=0; i<note.length; i+=2) {
	         final short w = note[i+1];
	         Sound.playTone(note[i], w);
	         Sound.pause(w*10);
	         if (suppressed)
	        	 return; // exit this method if suppress is called
	      }
	   }
}
