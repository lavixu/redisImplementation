package main.java.util;

import java.io.BufferedReader;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import main.java.serialization.PersistData;
import main.java.util.helper.CommandParser;


public class TelnetServer {
	private final Logger logger = Logger.getLogger(TelnetServer.class.getName());
	private final int PORT = 15000;
	private static CommandParser commandParser = new CommandParser();
	private static ServerSocket serverSocket = null;

	public TelnetServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
		serverSocket.setSoTimeout(30 * 60 * 1000); // timeout at 30 minutes
	}

	public void run() throws IOException {
		while (true) {
			Socket server = null;
			try {
				server = serverSocket.accept();
				final BufferedReader reader = new BufferedReader(
						new InputStreamReader(server.getInputStream()));
				final PrintWriter out = new PrintWriter(server.getOutputStream(), true);
				boolean cancel = false;
				while (!cancel) {
					
				String command = reader.readLine();
				
				if (command == null || command.equalsIgnoreCase("exit") || command.hashCode()==-1886105402) {
						cancel = true;
						break;
					}

					String result = commandParser.parseCommand(command);
					logger.info(command);
					out.println(result);
				}
			} 
			catch (EOFException ignore) {
				logger.info("Client terminated.");
			} 
			catch (IOException e) {
				logger.severe(e.getMessage());
			}
			finally {
				PersistData.save();
				server.close();
			}
		}
	}
}
