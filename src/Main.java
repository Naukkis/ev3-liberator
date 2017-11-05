

import java.io.IOException;
import communications.*;
import Sensors.TouchSensor;
import behaviors.*;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
/**
 * Robotin pääluokka. Luodaan moottorit, sensorit, käyttäytymismalliluokat ja niitäh ohjaava Arbitrator.
 *
 * @author Team 2
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {

		RegulatedMotor steering = new EV3MediumRegulatedMotor(MotorPort.A);
		RegulatedMotor power = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor tower = new EV3MediumRegulatedMotor(MotorPort.D);
		RegulatedMotor cannon = new EV3MediumRegulatedMotor(MotorPort.C);

		power.setSpeed((int)power.getMaxSpeed());
		steering.setSpeed((int)steering.getMaxSpeed());

		tower.setSpeed(200);
		cannon.setSpeed(400);

		TouchSensor touch = new TouchSensor("S3");

		// socket and input stream is created in communicator
		Communicator communicator = new Communicator();

		// create behaviors
		Moving mover = new Moving(communicator, power, steering, tower, cannon);
		Hangaround hangaround = new Hangaround();
		Stopper stopper = new Stopper(power, steering, tower, cannon, touch);
		BatteryLevelCommunicator batteryLevel = new BatteryLevelCommunicator(communicator);
		DemoMode demo = new DemoMode(communicator, power, steering, tower, cannon);
		Behavior[] behaviors = {hangaround, mover, batteryLevel, stopper, demo};

		Arbitrator arby = new Arbitrator(behaviors);
		arby.go();
	}

}
