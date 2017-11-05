package communications;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Communicator-luokka luo ServerSocket-olion ja yhteyden muodostamisen jälkeen välittää yhteyssocketin
 * CommandProcessorille. CommandProsessorin säie käynnistetään konstruktrissa.
 *
 * @author Team 2
 *
 */
public class Communicator {
	private CommandProcessor commandProcessor;
	private ServerSocket serverSocket;
	public Communicator() throws IOException {
		this.serverSocket = new ServerSocket(1111);
		Socket socket = serverSocket.accept();
		this.commandProcessor = new CommandProcessor(socket);
		this.commandProcessor.start();
	}

	/**
	 * Palauttaa datavirrasta luetun käskyn.
	 * @return
	 */
	public int handleCommand() {
		return commandProcessor.readInputBuffer();
	}
	/**
	 * Välittää käskyn CommandProcessorille.
	 * @param Robotille välitettävä viesti.
	 * @return
	 */
	public void sendMessage(String message) {
		commandProcessor.sendMessage(message);
	}

	/**
	 * Palautta datavirrasta luetun nopeusasetuksen.
	 * @return
	 */
	public int getSpeedSetting() {
		return commandProcessor.readSpeedSetting();
	}

	/**
	 * Lukee demotilan sisältävän muuttujan.
	 * @return
	 */
	public int getDemo(){
		return commandProcessor.readDemo();
	}

	/**
	 * Asettaa demotilan pois päältä.
	 * @return
	 */
	public void setDemoModeOff(){
		commandProcessor.setDemoModeOff();
	}
	/**
	 * Pysäyttää CommandProcessorin ja sulkee yhteyssocketin.
	 *
	 * @return
	 */
	public void stopReader() throws IOException {
		this.commandProcessor.stopCommandProcessor();
		this.serverSocket.close();
	}
}
