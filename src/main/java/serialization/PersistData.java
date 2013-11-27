package main.java.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.java.impl.stringImpl.*;
import main.java.impl.bitImpl.*;

public class PersistData {
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();//TODO change them to logger statements.
		}
		return true;
	}
	
	
}
