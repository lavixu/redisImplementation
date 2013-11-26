package serialization;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import stringImpl.StringKeyValue;

import bitImpl.BitKeyValue;

public class KeyValueData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HashMap<String, String> stringKeyValue = StringKeyValue.getKeyValueStr();
	public HashMap<String, Long> timeoutKeys = StringKeyValue.getTimeoutKeys();
	public HashMap<String, ArrayList<Integer>> bitKeyValue = BitKeyValue.getKeyValueBit();
	
	public HashMap<String, Long> getTimeoutKeys() {
		return timeoutKeys;
	}
	public void setTimeoutKeys(HashMap<String, Long> timeoutKeys) {
		this.timeoutKeys = timeoutKeys;
	}
	
	public HashMap<String, String> getStringKeyValue() {
		return stringKeyValue;
	}
	public void setStringKeyValue(HashMap<String, String> stringKeyValue) {
		this.stringKeyValue = stringKeyValue;
	}
	public HashMap<String, ArrayList<Integer>> getBitKeyValue() {
		return bitKeyValue;
	}
	public void setBitKeyValue(HashMap<String, ArrayList<Integer>> bitKeyValue) {
		this.bitKeyValue = bitKeyValue;
	}
	

}
