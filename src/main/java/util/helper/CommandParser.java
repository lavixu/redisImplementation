package main.java.util.helper;
import java.util.ArrayList;

import main.java.impl.factory.*;
import main.java.impl.bitImpl.*;

import main.java.impl.stringImpl.*;
import main.java.impl.zSetImpl.*;
import main.java.serialization.PersistData;

public class CommandParser {
	
	public final String CRLF = "\r\n";
	public final String BULK_REPLY = "$";
	public final String ERROR = "-";
	public final String MULTI_BULK_REPLY = "*";
	public final String INTEGER_REPLY = ":";
	public final String NULL_MULTI_BULK_REPLY = "$-1";
	public final String STATUS = "+";
	
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
		case "zadd":
			result = zadd(args);
			break;
		case "zcard":
			result = zcard(args);
			break;
		case "zrange":
			result = zrange(args);
			break;
		case "zcount":
			result = zcount(args);
			break;
		default: result = "ExoRedis does not support this command.";
		
		}
		
		return result;
	}
	
	public String zcard(String[] args)
	{
		String result = null;
		if(args.length !=2)
		{
			result =  ERROR + "ERR wrong number of arguments for 'zcard' command" + CRLF;
			return result;
		}
		SortedKeyValue sortedKeyVal = KeyValuefactory.getSortedKeyValInstance();
		int noOfElements = sortedKeyVal.getCard(args[1]);
		result = INTEGER_REPLY + noOfElements + CRLF;
		return result;
	}
	
	public String zcount(String[] args)
	{
		String result = null;
		if(args.length != 4)
		{
			result =  ERROR + "ERR wrong number of arguments for 'zcount' command." + CRLF;
			return result;
		}
		String key = args[1];
		int minScore, maxScore;
		try
		{
			minScore = Integer.parseInt(args[2]);
			maxScore = Integer.parseInt(args[3]);
		}
		catch(NumberFormatException e)
		{
			result = ERROR + "WRONGTYPE minScore maxScore value for zcount." + CRLF;
			return result;
		}
		
		SortedKeyValue sortedKeyVal = KeyValuefactory.getSortedKeyValInstance();
		int count = sortedKeyVal.zCount(key, minScore, maxScore);
		result = INTEGER_REPLY + count + CRLF;
		return result;
	}
	
	public String zrange(String[] args)
	{
		String result = null;
		if(args.length < 4)
		{
			result = ERROR + "ERR wrong number of arguments for 'zrange' command." + CRLF;
			return result;
		}
		String key = args[1];
		boolean withScore = false;
		int minIndex, maxIndex;
		try
		{
			minIndex = Integer.parseInt(args[2]);
			maxIndex = Integer.parseInt(args[3]);
		}
		catch(NumberFormatException e)
		{
			result = ERROR +  "WRONGTYPE minScore maxScore value for zcount." + CRLF;
			return result;
		}
		
		if(args.length > 4)
		{
			if(args[4].equalsIgnoreCase("withscore"))
			{
				withScore = true;
			}
		}
		
		
		SortedKeyValue sortedKeyVal = KeyValuefactory.getSortedKeyValInstance();
		ArrayList<String> values = sortedKeyVal.zRange(key, minIndex, maxIndex, withScore);
		StringBuffer str = new StringBuffer();
		str.append(MULTI_BULK_REPLY + values.size() + CRLF);
		for(String tmp : values)
		{
			str.append(formatStrForBulkReply(tmp));
		}
		return str.toString();
	}
	
	public String formatStrForBulkReply(String input)
	{
		String result = null;
		if(input == null || input.isEmpty()) 
		{
			result = BULK_REPLY + "-1" + CRLF;
			return result;
		}
		result = BULK_REPLY + input.length() + CRLF + input + CRLF; 
		return result;
	}
	
	public String zadd(String[] args)
	{
		//Only supporting zadd key member score
		String result = null;
		if(args.length != 4)
		{
			result = ERROR + "ERR wrong number of arguments for 'zadd' command." + CRLF;
			return result;
		}
		
		SortedKeyValue sortedKeyVal = KeyValuefactory.getSortedKeyValInstance();
		String key = args[1], member = args[2]; 
		int score = 0;
		try
		{
			score = Integer.parseInt(args[3]);
		}
		catch(NumberFormatException e)
		{
			result = ERROR + "WRONGTYPE Operation against a member holding the wrong kind of score."+ CRLF;
			return result;
		}
		
		int elementAdded = sortedKeyVal.zAdd(key, member, score);
		result = INTEGER_REPLY + elementAdded + CRLF;
		return result;
	}
	
	public String getBit(String[] args)
	{
		String result = null;
		if(args.length != 3)
		{ 
			result = ERROR + "ERR wrong number of arguments for 'getbit' command" + CRLF;
			return result;
		}
		try
		{
			
			String key = args[1];
			int index = Integer.parseInt(args[2]);
			BitKeyValue bitKeyValue = KeyValuefactory.getBitKeyValueInstance();
			Boolean bool = bitKeyValue.get(key, index);
			int bitVal = ( bool == false)  ? 0 : 1;
			result = INTEGER_REPLY + bitVal + CRLF;
			return result;
		}
		catch(NumberFormatException e)
		{
			result = ERROR + "ERR Wrong argument for offset" + CRLF;
			return result;
		}
	}
	
	public String setBit(String[] args)
	{
		String result = null;
		if(args.length != 4)
		{ 
			result = ERROR + "ERR wrong number of arguments for 'setbit' command" + CRLF;
			return result;
		}
		try
		{
			String key = args[1];
			int index = Integer.parseInt(args[2]);
			int flag = Integer.parseInt(args[3]);
			int prevValue;
			BitKeyValue bitKeyValue = KeyValuefactory.getBitKeyValueInstance();
			if (flag == 0) 
				{
					prevValue = bitKeyValue.set(key, index, false) ;
				}
			else //Any other value apart from 0 is considered true. Check this.
				{
					prevValue = bitKeyValue.set(key, index, true);
				}
			
			result = INTEGER_REPLY + prevValue + CRLF;
		}
		catch(NumberFormatException e)
		{
			result = ERROR + "Wrong Format for index or offset." + CRLF;
		}
		
		return result;
		
	}
	
	public String getString(String[] args)
	{
		String result = null;
		if(args.length < 2)
		{
			result = ERROR + "ERR wrong number of arguments for 'get' command" + CRLF;
			return result ; //Minimum arguments for get are get key.
		}
		String key = args[1];
		StringKeyValue keyValPair = KeyValuefactory.getStringKeyValueInstance();
		result = formatStrForBulkReply(keyValPair.get(key));
		return result;
	}
		
	public String setString(String[] args)
	{
		String result = null;
		if(args.length < 3)
		{
			result = ERROR + "ERR wrong number of arguments for 'set' command" + CRLF;
			return result; // minimum arguments are set key value for set.
		}
		String key = args[1], value = args[2];
		Boolean update = null, updated = true;
		Long timeout = null;
		
		try
		{
			for(int i=3; i< args.length; i++)
			{
				if(args[i].equalsIgnoreCase("ex") && i+1 < args.length)
				{
					timeout = Long.parseLong(args[i+1])*1000; //converting seconds to milliseconds
					i++;
				}
				else if (args[i].equalsIgnoreCase("px")&& i+1 < args.length)
				{
					timeout = Long.parseLong(args[i+1]);
					i++;
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
				result = ERROR + "Wrong Argument type. Timeout cannot be negative." + CRLF;
				return result;
			}
			StringKeyValue strKeyVal = KeyValuefactory.getStringKeyValueInstance();
			
			if(update != null && timeout != null)
			{
				updated = strKeyVal.set(key, value, update, timeout);
			}
			else if (update!= null)
			{
				updated = strKeyVal.set(key, value, update);
			}
			else if(timeout != null)
			{
				strKeyVal.set(key, value, timeout);
			}
			else
			{
				strKeyVal.set(key, value);
			}
			
			if(updated)
			{
				result = STATUS + "OK" + CRLF;
			}
			else
			{
				result = NULL_MULTI_BULK_REPLY + CRLF;
			}
		}
		catch(NumberFormatException e)
		{
			result = ERROR + "Wrong argument type for set command" + CRLF;
		}
		return result;
	}

}
