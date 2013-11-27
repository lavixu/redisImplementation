package main.java.serialization;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import main.java.impl.stringImpl.*;
import main.java.impl.zSetImpl.MemberScore;
import main.java.impl.zSetImpl.SortedKeyValue;
import main.java.impl.bitImpl.*;

public class KeyValueData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public HashMap<String, String> stringKeyValue = StringKeyValue.getKeyValueStr();
	public HashMap<String, Long> timeoutKeys = StringKeyValue.getTimeoutKeys();
	public HashMap<String, ArrayList<Integer>> bitKeyValue = BitKeyValue.getKeyValueBit();
	public HashMap<String, TreeSet<MemberScore>> sortedSetKeyVal = SortedKeyValue.getzMemberMap();
	
	public HashMap<String, TreeSet<MemberScore>> getSortedSetKeyVal() {
		return sortedSetKeyVal;
	}
	public void setSortedSetKeyVal(
			HashMap<String, TreeSet<MemberScore>> sortedSetKeyVal) {
		this.sortedSetKeyVal = sortedSetKeyVal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
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
