package behaviors;


import communications.Communicator;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;



/**
 * Behavior-rajapinnan toteuttava luokka, joka toimii demotilan logiikkana.
 * @author team2
 *
 */
public class DemoMode implements Behavior {
	private Communicator communicator;
	private RegulatedMotor power;
	private RegulatedMotor steering;
	private RegulatedMotor tower;
	private RegulatedMotor cannon;
	private int command;
	private boolean suppressed;

	public DemoMode(Communicator communicator, RegulatedMotor power, RegulatedMotor steering, RegulatedMotor tower, RegulatedMotor cannon) {
		this.communicator = communicator;
		this.power = power;
		this.steering = steering;
		this.tower = tower;
		this.cannon = cannon;
	}

	/* (non-Javadoc)
	 * @see lejos.robotics.subsumption.Behavior#takeControl()
	 * ottaa komennon, jos arvo 1001.
	 */
	@Override
	public boolean takeControl() {
		return communicator.getDemo() == 1001;
	}

	@Override
	public void action(){
		communicator.setDemoModeOff();
		power.setSpeed(600);
		power.backward();
		Delay.msDelay(2000);
		power.stop();
		steering.forward();
		Delay.msDelay(2000);
		steering.stop();
		tower.forward();
		Delay.msDelay(500);
		tower.stop();
		cannon.forward();
		Delay.msDelay(2800);
		cannon.stop();
		tower.backward();
		Delay.msDelay(500);
		tower.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
