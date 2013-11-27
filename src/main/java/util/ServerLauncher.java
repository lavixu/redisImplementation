package main.java.util;

import java.io.IOException;
import java.util.logging.Logger;

import main.java.serialization.*;

public class ServerLauncher {
	 public static final Logger logger = Logger.getLogger(ServerLauncher.class.getName()); 
	 public static void main(String[] args) throws IOException {
		 
		    if(args.length == 0)
		    {
		    	logger.severe("File path needs to be passed as argument");
		    	return;
		    }
		 	String filename = args[0];
		 	boolean isLoaded = PersistData.loadData(filename);
		 	if(!isLoaded)
		 	{
		 		return;
		 	}
		 	new TelnetServer().run(); // launch the server
	    }

}
