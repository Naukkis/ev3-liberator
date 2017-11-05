package Sensors;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class DistanceSensor {
	private Port port;
	private SensorModes infraSensor;
	private SampleProvider distance;
	private float[] irSample;

	 public DistanceSensor(String port) {
			// distance sensor test
			this.port = LocalEV3.get().getPort(port);
			this.infraSensor = new EV3IRSensor(this.port);
			this.distance = ((EV3IRSensor) infraSensor).getDistanceMode();

			this.irSample = new float[distance.sampleSize()];
	}

	 public float getDistance() {
		 distance.fetchSample(irSample, 0);
		 return irSample[0];
	 }

	 public boolean distanceSmaller(int distance){
		 return getDistance() < distance;
	 }

	 public int getRemoteCommand(int chan){
		return ((EV3IRSensor) infraSensor).getRemoteCommand(chan);
	 }
}
