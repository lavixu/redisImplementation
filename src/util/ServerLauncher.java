package util;

import java.io.IOException;

import serialization.PersistData;

public class ServerLauncher {
	 public static void main(String[] args) throws IOException {
		 	
		 	PersistData.loadData(); //load the data into memory.
		 	new TelnetServer().run(); // launch the server
	    }

}
