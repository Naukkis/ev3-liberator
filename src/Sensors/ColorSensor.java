package Sensors;

import java.util.ArrayList;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ColorSensor {

	final String[] colors = { "YELLOW", "GREEN", "BLUE", "RED" };
	private final int YELLOW = 0;
	private final int GREEN = 1;
	private final int BLUE = 2;
	private final int RED = 3;
	private final int r = 0;
	private final int g = 1;
	private final int b = 2;

	private Port port;
	private SensorModes colorSensor;
	private SampleProvider colorProvider;
	private float[] colorSample;
	private ArrayList<int[]> calibration;

	public ColorSensor(String port) {
		this.port = LocalEV3.get().getPort(port);
		this.colorSensor = new EV3ColorSensor(this.port);
		this.colorProvider = ((EV3ColorSensor) colorSensor).getRGBMode();
		this.colorSample = new float[colorProvider.sampleSize()];
		this.calibration = new ArrayList<int[]>();
	}

	public int[] getColorSample() {
		colorProvider.fetchSample(colorSample, 0);
		int r = Math.round(colorSample[0] * 765);
		int g = Math.round(colorSample[1] * 765);
		int b = Math.round(colorSample[2] * 765);
		int[] colorSample =  {r, g, b};
		return colorSample;
	}

	public void colorCalibration(TouchSensor touchSensor) {
		int calibrationPhase = 0;
		System.out.println("Ready");
		while (calibrationPhase < 4) {
			if (touchSensor.isPressed()) {
				calibration.add(getColorSample());
				System.out.println("Red: " + calibration.get(calibrationPhase)[0]);
				System.out.println("Green: " + calibration.get(calibrationPhase)[1]);
				System.out.println("Blue: " + calibration.get(calibrationPhase)[2]);
				Delay.msDelay(500);
				calibrationPhase++;
			}
		}
	}

	public double[] getCurrentColors (ArrayList<int[]> colorSamples) {
		int[] latestColorValue = getColorSample();
		ArrayList<int[]> calibratedColors = new ArrayList<int[]>();
		for (int[] originalColorValues : colorSamples) {
			int colorDifferenceR = originalColorValues[r] - latestColorValue[r];
			colorDifferenceR = colorDifferenceR * colorDifferenceR;

			int colorDifferenceG = originalColorValues[g] - latestColorValue[g];
			colorDifferenceG = colorDifferenceG * colorDifferenceG;

			int colorDifferenceB = originalColorValues[b] - latestColorValue[b];
			colorDifferenceB = colorDifferenceB * colorDifferenceB;
			int[] rgb = new int[] {colorDifferenceR, colorDifferenceG, colorDifferenceB};
			calibratedColors.add(rgb);
		}

		double finalYELLOW = Math.sqrt(calibratedColors.get(YELLOW)[r] + calibratedColors.get(YELLOW)[g] + calibratedColors.get(YELLOW)[b]);
		double finalGREEN = Math.sqrt(calibratedColors.get(GREEN)[r] + calibratedColors.get(GREEN)[g] + calibratedColors.get(GREEN)[b]);
		double finalBLUE = Math.sqrt(calibratedColors.get(BLUE)[r] + calibratedColors.get(BLUE)[g] + calibratedColors.get(BLUE)[b]);
		double finalRED = Math.sqrt(calibratedColors.get(RED)[r] + calibratedColors.get(RED)[g] + calibratedColors.get(RED)[b]);

		double[] finalColors = {finalYELLOW, finalGREEN, finalBLUE, finalRED};
		return finalColors;
	}
	public boolean isColor(){
		int color = 0;
		double[] currentColors = getCurrentColors(calibration);
		double smallest = 255;
		for (int i = 0; i < currentColors.length; i++) {
			if (currentColors[i] < smallest) {
				smallest = currentColors[i];
				color = i;
			}
		}
		if (colors[color].equals("RED")) {
			return true;
		}

		return false;
	}

	// TESTAAMATONTA, vaihtoehtoiset metodit kalibrointiin, kalibroitavien värien määrä annetaan parametrina
	// isColor pitää kans muuttaa, jos haluu testaa näitä

	public void calibrateAnyNumberOfColors(TouchSensor touchSensor, int amountOfColorsNeededToBeCalibrated) {
		int calibrationPhase = 0;
		System.out.println("Ready");
		while (calibrationPhase < amountOfColorsNeededToBeCalibrated) {
			if (touchSensor.isPressed()) {
				calibration.add(getColorSample());
				System.out.println("Red: " + calibration.get(calibrationPhase)[0]);
				System.out.println("Green: " + calibration.get(calibrationPhase)[1]);
				System.out.println("Blue: " + calibration.get(calibrationPhase)[2]);
				Delay.msDelay(500);
				calibrationPhase++;
			}
		}
	}

	public double[] getWhateverAmountOfCurrentColors(ArrayList<int[]> colorSamples) {

		int[] latestColorValue = getColorSample();
		ArrayList<int[]> calibratedColors = new ArrayList<int[]>();
		for (int[] originalColorValues : colorSamples) {
			int colorDifferenceR = originalColorValues[r] - latestColorValue[r];
			colorDifferenceR = colorDifferenceR * colorDifferenceR;

			int colorDifferenceG = originalColorValues[g] - latestColorValue[g];
			colorDifferenceG = colorDifferenceG * colorDifferenceG;

			int colorDifferenceB = originalColorValues[b] - latestColorValue[b];
			colorDifferenceB = colorDifferenceB * colorDifferenceB;
			int[] rgb = new int[] { colorDifferenceR, colorDifferenceG, colorDifferenceB };
			calibratedColors.add(rgb);
		}

		double[] finalColors = new double[calibratedColors.size()];

		for (int i = 0; i<calibratedColors.size();i++) {
			finalColors[i] = Math.sqrt(calibratedColors.get(0)[r] + calibratedColors.get(0)[g] + calibratedColors.get(0)[b]);
		}

		return finalColors;
	}

	public boolean betterIsColor(int colorToFind) {
		int color = 0;
		double[] currentColors = getCurrentColors(calibration);
		double smallest = 255;
		for (int i = 0; i < currentColors.length; i++) {
			if (currentColors[i] < smallest) {
				smallest = currentColors[i];
				color = i;
			}
		}
		if (color == colorToFind) {
			return true;
		}
		return false;

	}
}
