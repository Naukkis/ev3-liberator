package behaviors;

import Sensors.TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Stopper implements Behavior{
	private RegulatedMotor power, steering, tower, cannon;
	private TouchSensor touch;
	private boolean suppressed;

	public Stopper(RegulatedMotor power, RegulatedMotor steering, RegulatedMotor tower, RegulatedMotor cannon, TouchSensor touch){
		this.power = power;
		this.steering = steering;
		this.tower = tower;
		this.cannon = cannon;
		this.touch = touch;
	}

	@Override
	public boolean takeControl() {
		if(touch.isPressed()) {
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		power.stop();
		steering.stop();
		tower.stop();
		cannon.stop();
		System.exit(0);
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}


}
