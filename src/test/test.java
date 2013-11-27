package test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;



public class test {

	public static void main(String atgs[]) throws IOException {
		// PersistData.loadData();
		//
		// CommandParser parser = new CommandParser();
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		// String input = br.readLine();
		// String result = parser.parseCommand(input);
		// System.out.println(result);
		// while(!input.trim().equalsIgnoreCase("exit"))
		// {
		// input = br.readLine();
		// result = parser.parseCommand(input);
		// System.out.println(result);
		// }

		// String serverName = args[0];
		// int port = Integer.parseInt(args[1]);
		String serverName = "localhost";
		int port = 15000;
		try {

			Socket client = new Socket(serverName, port);
			OutputStream outToServer = client.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			DataOutputStream out = new DataOutputStream(outToServer);
			String command = br.readLine();
			while (command != null && !command.equalsIgnoreCase("exit")) {
				out.writeUTF(command);
				System.out.println(in.readUTF());
				command = br.readLine();
			}
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
