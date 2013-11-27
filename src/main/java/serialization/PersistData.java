package main.java.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.impl.stringImpl.*;
import main.java.impl.zSetImpl.SortedKeyValue;
import main.java.impl.bitImpl.*;

public class PersistData {

	public static Logger logger = Logger.getLogger(PersistData.class.getName());
	private static String filePath = null;

	/*
	 * The order of implementation is String hashmap, expiryTimestamp hashMap,
	 * Bitset and then TreeSet
	 */
	public static void save() {
		KeyValueData data = new KeyValueData();

		try (FileOutputStream fileOut = new FileOutputStream(filePath, false);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);) {
			out.writeObject(data);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	// Reads the file data and initialized the corresponding hashmaps. Also
	// purges the expired keys from the list.
	public static Boolean loadData(String filePathArg) throws IOException {
		filePath = filePathArg;
		File file = new File(filePath);

		try {
			file.createNewFile();

		} catch (IOException e1) {
			logger.log(Level.SEVERE, e1.getMessage(), e1);
			return false;
		}

		// creates only if it does not already exist;
		ObjectInputStream in = null;
		try (FileInputStream fileIn = new FileInputStream(file);) {
			if (fileIn.available() > 0) {
				in = new ObjectInputStream(fileIn);
				KeyValueData data = (KeyValueData) in.readObject();
				if (data != null) {
					StringKeyValue.setKeyValueStr(data.getStringKeyValue());
					BitKeyValue.setKeyValueBit(data.getBitKeyValue());
					StringKeyValue.setTimeoutKeys(data.getTimeoutKeys());
					StringKeyValue.purgeExpiredKeys();
					SortedKeyValue.setzMemberMap(data.getSortedSetKeyVal());
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return false;
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return true;
	}
}
