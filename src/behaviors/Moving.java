package behaviors;

import communications.Communicator;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

/**
 * Ohjainkomennot suorittava käyttäytymisluokka
 *
 * @author Team 2
 *
 */
public class Moving implements Behavior {
	private Communicator communicator;
	private RegulatedMotor power;
	private RegulatedMotor steering;
	private RegulatedMotor tower;
	private RegulatedMotor cannon;
	private int command;
	private boolean suppressed;
	private final int FORWARD = 0, RIGHT = 1, BACKWARD = 2, LEFT = 3, GAS_STOP = 4, STEERING_STOP = 5, TOWER_RIGHT = 6,
			TOWER_LEFT = 7, TOWER_STOP = 8, FIRE_CANNON_UP = 9, FIRE_CANNON_DOWN = 10, CANNON_STOP = 11;

	public Moving(Communicator communicator, RegulatedMotor power, RegulatedMotor steering, RegulatedMotor tower,
			RegulatedMotor cannon) {
		this.communicator = communicator;
		this.power = power;
		this.steering = steering;
		this.tower = tower;
		this.cannon = cannon;
	}

	/**
	 * Ottaa robotin haltuun kun ohjaavalta tietokoneelta saadun ohjauskomennon
	 * arvo on yli 0.
	 * @param
	 * @return Palauttaa true jos ohjauskomento on vastaanotettu.
	 *
	 */
	@Override
	public boolean takeControl() {
		command = communicator.handleCommand();
		return command >= 0;
	}

	/**
	 * Käsittelee ohjauskomennon (väliltä 0-10), suoritus vain kerran per kutsu.
	 *
	 * @param
	 * @return
	 *
	 */
	@Override
	public void action() {
		power.setSpeed(communicator.getSpeedSetting());
		if (command == FORWARD) {
			power.backward();
		} else if (command == RIGHT) {
			steering.backward();
		} else if (command == BACKWARD) {
			power.forward();
		} else if (command == LEFT) {
			steering.forward();
		} else if (command == GAS_STOP) {
			power.stop();
		} else if (command == STEERING_STOP) {
			steering.stop();
		} else if (command == TOWER_RIGHT) {
			tower.forward();
		} else if (command == TOWER_LEFT) {
			tower.backward();
		} else if (command == TOWER_STOP) {
			tower.stop();
		} else if (command == FIRE_CANNON_UP) {
			cannon.forward();
		} else if (command == FIRE_CANNON_DOWN) {
			cannon.backward();
		} else if (command == CANNON_STOP) {
			cannon.stop();
		}
	}

	/**
	 * Ei käytössä, koska action suoritetaan aina vain kerran.
	 *
	 * @param
	 * @return
	 *
	 */
	@Override
	public void suppress() {
		suppressed = true;
	}

}
