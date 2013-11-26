package helper;
import factory.KeyValuefactory;
import bitImpl.BitKeyValue;
import serialization.PersistData;
import stringImpl.StringKeyValue;

public class CommandParser {
	

	
	public String parseCommand(String input)
	{
		String[] args = input.split("\\s+");
		String command = args[0].toLowerCase();
		String result = "";
		switch (command) {
		case "get":
			result = getString(args);
			break;
		case "set":
			result = setString(args);
			break;
		case "getbit":
			result = getBit(args);
			break;
		case "setbit":
			result = setBit(args);
			break;
		case "save":
			PersistData.save();
			result = "";
			break;
			
		}
		return result;
	}
	
	public String getBit(String[] args)
	{
		if(args.length != 3)
		{ 
			return "ERR wrong number of arguments for 'getbit' command";
		}
		try
		{
			
			String key = args[1];
			int index = Integer.parseInt(args[2]);
			BitKeyValue bitKeyValue = KeyValuefactory.getBitKeyValueInstance();
			Boolean bool = bitKeyValue.get(key, index);
			int result = bool == false ? 0 : 1;
			return "(integer) "+result;
		}
		catch(NumberFormatException e)
		{
			return e.getMessage();
		}
	}
	
	public String setBit(String[] args)
	{
		if(args.length != 4)
		{ 
			return "ERR wrong number of arguments for 'setbit' command";
		}
		try
		{
			String key = args[1];
			int index = Integer.parseInt(args[2]);
			int flag = Integer.parseInt(args[3]);
			BitKeyValue bitKeyValue = KeyValuefactory.getBitKeyValueInstance();
			if (flag == 0) 
				{
					bitKeyValue.set(key, index, false) ;
				}
			else //Any other value apart from 0 is considered true. Check this.
				{
					bitKeyValue.set(key, index, true);
				}
			return "(integer) 0";
		}
		catch(NumberFormatException e)
		{
			return e.getMessage();
		}
		
	}
	
	public String getString(String[] args)
	{
		if(args.length < 2)
		{
			return "ERR wrong number of arguments for 'get' command"; //Minimum arguments for get are get key.
		}
		String key = args[1];
		StringKeyValue keyValPair = KeyValuefactory.getStringKeyValueInstance();
		return keyValPair.get(key);
	}
		
	public String setString(String[] args)
	{
		if(args.length <3)
		{
			return "ERR wrong number of arguments for 'set' command"; // minimum arguments are set key value for set.
		}
		String key = args[1];
		String value = args[2];
		Boolean update = null;
		Long timeout = null;
		try
		{
			for(int i=2; i< args.length; i++)
			{
				if(args[i].equalsIgnoreCase("ex") && i+1 < args.length)
				{
					timeout = Long.parseLong(args[i+1])*1000; //converting seconds to milliseconds
				}
				else if (args[i].equalsIgnoreCase("px")&& i+1 < args.length)
				{
					timeout = Long.parseLong(args[i+1]);
					
				}
				else if(args[i].equalsIgnoreCase("nx"))
				{
					update = false;
				}
				else if(args[i].equalsIgnoreCase("xx"))
				{
					update = true;
				} 
			}
			if(timeout != null && timeout.longValue() < 0)
			{
				return "Timeout cannot be negative";
			}
			StringKeyValue strKeyVal = KeyValuefactory.getStringKeyValueInstance();
			if(update != null && timeout != null)
			{
				strKeyVal.set(key, value, update, timeout);
			}
			else if (update!= null)
			{
				strKeyVal.set(key, value, update);
			}
			else if(timeout != null)
			{
				strKeyVal.set(key, value, timeout);
			}
			else
			{
				strKeyVal.set(key, value);
			}
		}
		catch(NumberFormatException e)
		{
			return  e.getMessage();
		
		}
		return "Successully added";
	}

}
