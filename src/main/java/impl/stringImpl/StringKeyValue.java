package main.java.impl.stringImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class StringKeyValue {
	
	private static HashMap<String, String> keyValueStr = new HashMap<>();
	private static HashMap<String, Long> timeoutKeys = new HashMap<String, Long>();
	
	public static HashMap<String, String> getKeyValueStr() {
		return keyValueStr;
	}

	public static void setKeyValueStr(HashMap<String, String> keyValueStr) {
		StringKeyValue.keyValueStr = keyValueStr;
	}

	

	public static HashMap<String, Long> getTimeoutKeys() {
		return timeoutKeys;
	}

	public static void setTimeoutKeys(HashMap<String, Long> timeoutKeys) {
		StringKeyValue.timeoutKeys = timeoutKeys;
	}

	public void set(String key, String value) {
		keyValueStr.put(key, value);
	}

	public boolean set(String key, String value, Boolean update, long timeout) {
		Boolean success = set(key, value, update);
		if (success) {
			addTimeout(key, timeout);
		}
		return success;
	}

	public boolean set(String key, String value, Boolean update) {
		Boolean containsKey = keyValueStr.containsKey(key);
		if (containsKey && update || !containsKey && !update) {
			keyValueStr.put(key, value);
			return true;
		}

		return false;
	}

	public void set(String key, String value, long timeout) {
		keyValueStr.put(key, value);
		addTimeout(key, timeout);
	}

	public String get(String key) {
		// If the associated key has a timeout set, check if it has expired
		// (active expiry).
		if (timeoutKeys.containsKey(key)) {
			if (getCurrentTimeInMilli() > timeoutKeys.get(key)) {
				timeoutKeys.remove(key);
				keyValueStr.remove(key);
				return null;
			}
		}
		return keyValueStr.get(key);
	}

	private void addTimeout(String key, long timeout) {
		Long expireTime = getCurrentTimeInMilli() + timeout;
		timeoutKeys.put(key, expireTime);

	}

	public static Long getCurrentTimeInMilli() {
		return new java.util.Date().getTime();
	}
	
	public static void purgeExpiredKeys()
	{
		if (timeoutKeys == null || timeoutKeys.isEmpty())
		{
			return;
		}
		// Pick either 100 random keys for a large data set or pick all keys for small data set.
		int randomKeySize = timeoutKeys.size() > 100 ? 100 : timeoutKeys.size() ;
		int threshHold = randomKeySize/4;
		int deletedKeyCount =0 , counter =0;
		
		 
		Random random = new Random();
		List<String> keyList  = new ArrayList<String>(timeoutKeys.keySet());
		while (counter <= threshHold) {
			String key = keyList.get(random.nextInt(timeoutKeys.size()));
			Long timeout = timeoutKeys.get(key);
			if (getCurrentTimeInMilli() > timeout) 
			{
				keyValueStr.remove(key);
				timeoutKeys.remove(key);
				deletedKeyCount ++;
				counter ++;
			}
		}
		
		if(deletedKeyCount > threshHold)
		{	
			purgeExpiredKeys(); // call it recursively till we have less than 25% expiredKeys probability.
		}
	}

}
