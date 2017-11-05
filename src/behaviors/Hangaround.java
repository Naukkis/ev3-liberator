package behaviors;

import lejos.robotics.subsumption.Behavior;


/**
 * Behavior-rajapinnan toteuttava luokka.
 * Kun robotilla ei ole demotila päällä tai mitään ohjainkomentoa ei anneta, robotti pysyy paikallaan.
 *
 * @author team2
 *
 */
public class Hangaround implements Behavior{
	private boolean suppressed = false;
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		while (!suppressed) {
			Thread.yield();

		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
