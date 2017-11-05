package Sensors;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
/**
 * Lukee kosketussensoridataa.
 *
 * @author Team 2
 */
public class TouchSensor {

	private Port port;
	private SensorModes touchSensor;
	private SampleProvider touch;
	private float[] touchSample;

	public TouchSensor(String port) {
		this.port = LocalEV3.get().getPort(port);
		this.touchSensor = new EV3TouchSensor(this.port);
		this.touch = ((EV3TouchSensor)touchSensor).getTouchMode();
		this.touchSample = new float[touchSensor.sampleSize()];
	}
/**
 * Palauttaa true, jos kosketussensori yli puolenvälin.
 * @return
 */
	public boolean isPressed() {
		touch.fetchSample(touchSample, 0);
		if (touchSample[0] > 0.5) {
			return true;
		}
		return false;
	}
}
