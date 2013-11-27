package main.java.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import main.java.impl.stringImpl.*;
import main.java.impl.zSetImpl.SortedKeyValue;
import main.java.impl.bitImpl.*;

public class PersistData {
	
	public static Logger logger = Logger.getLogger(PersistData.class.getName());
	/* The order of implementation is
	 * String hashmap, expiryTimestamp hashMap, Bitset and then TreeSet
	 * 
	 * */
	public static void save() {
		KeyValueData data = new KeyValueData();
		
		try (FileOutputStream fileOut = new FileOutputStream("/tmp/data", false);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);){
			out.writeObject(data);
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
		
		
	}
	
	//Reads the file data and initialized the corresponding hashmaps. Also purges the expired keys from the list.
	public static Boolean loadData()
	{
		try (FileInputStream fileIn = new FileInputStream("/tmp/data");
				ObjectInputStream in = new ObjectInputStream(fileIn);)

		{
			KeyValueData data = (KeyValueData) in.readObject();
			if (data != null) {
				StringKeyValue.setKeyValueStr(data.getStringKeyValue());
				BitKeyValue.setKeyValueBit(data.getBitKeyValue());
				StringKeyValue.setTimeoutKeys(data.getTimeoutKeys());
				StringKeyValue.purgeExpiredKeys();
				SortedKeyValue.setzMemberMap(data.getSortedSetKeyVal());
			}
		}
		catch(Exception e)
		{
			logger.severe(e.getMessage());
		}
		return true;
	}
	
	
}
