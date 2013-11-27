package util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;


import serialization.PersistData;

public class TelnetServer {
	private final Logger LOGGER = Logger.getLogger(TelnetServer.class.getName());
	private final int PORT = 15000;
	private static CommandParser commandParser = new CommandParser();
	private static ServerSocket serverSocket = null;

	
	public TelnetServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
		serverSocket.setSoTimeout(15*60*1000); //timeout at 15 minutes
	}

	public void run() throws IOException {
		Socket server = null;
		while (true) {
			try {

				server = serverSocket.accept();
				DataInputStream in = new DataInputStream(server.getInputStream());
				String command = in.readUTF();
				while (command != null && !command.equalsIgnoreCase("exit")) {
					String result = commandParser.parseCommand(command);
					DataOutputStream out = new DataOutputStream(server.getOutputStream());
					out.writeUTF(result);
					command = in.readUTF();
				}

			} 
			catch(EOFException ignore)
			{
				LOGGER.info("Client terminated.");
			}
			catch (IOException e) {
				LOGGER.severe(e.getMessage());
			}
			
			finally {
				PersistData.save();
			}
		}
	}

}
