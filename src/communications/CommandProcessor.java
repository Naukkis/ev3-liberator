package communications;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
/**
 * CommandProcessor hoitaa datan välityksen tietokoneen ja robotin välillä.  Luokka saa parametrina socketin ja luo tälle DataInputStreamin ja DataOutputStreamin.
 *
 * @author Team 2
 *
 */
public class CommandProcessor extends Thread {
	private Socket socket;
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
	/**
	 * Viimeisimmän ohjainkomennon sisältävä muuttuja.
	 */
	private int command;
	/**
	 * Viimeisimmän nopeusasetuksen sisältävä muuttuja.
	 */
	private int speedSetting;
	/**
	 * Demomoodin tilan sisältävä muuttuja 0 = Pois päältä, 1001 = päällä.
	 */
	private int demo;
	private boolean connected;

	public CommandProcessor(Socket socket) throws IOException {
		this.socket = socket;
		dataIn = new DataInputStream(socket.getInputStream());
		dataOut = new DataOutputStream(socket.getOutputStream());
		connected = true;
		command = -1;
		speedSetting = 1000;
		demo = 0;

	}

/**
*DataInputStreamin lukija. Käskyt tulevat Int-muodossa ja ne tallennetaan muuttujaan tyyppinsä tarkoituksen mukaan.
*/
	@Override
	public void run() {
		while (connected) {

			try {

				int temp = dataIn.readInt();
				if (temp > 11) {
					speedSetting = temp;
				}
				if(temp == 1001){
					demo = temp;
				}else {

					command = temp;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			// Nukutetaan säie hetkeksi, jotta kontrollit eivät jumita kaasua päälle
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void closeConnection() throws IOException {
		socket.close();
	}

	public int readInputBuffer() {
		return command;
	}

	/**
	 * Lähettää ohjaavalle tietokoneelle String-muotoisen viestin.
	 * @param
	 * @return
	 */
	public void sendMessage(String message) {
		try {
			dataOut.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Palauttaa viimeisimmän nopeusasetuksen.
	 * @param
	 * @return luettu nopeusasetus
	 */
	public int readSpeedSetting() {
		return speedSetting;
	}
	/**
	 * Palauttaa demomoodin tilan sisältävän muuttujan. 0 = demotila pois päältä, 1001 = demotila päällä.
	 * @param
	 * @return int 0 / 1001
	 */
	public int readDemo(){
		return demo;
	}
	/**
	 * Asettaa demomoodin pois päältä.
	 * @param
	 * @return
	 */
	public void setDemoModeOff() {
		demo = 0;
	}
	/**
	 * Pysäyttää CommandPRocessorin datavirran lukemisen.
	 * @param
	 * @return
	 */
	public void stopCommandProcessor() {
		connected = false;
	}

}
