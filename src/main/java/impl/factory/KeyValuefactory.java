package main.java.impl.factory;

import main.java.impl.bitImpl.*;
import main.java.impl.stringImpl.*;
import main.java.impl.zSetImpl.*;

public class KeyValuefactory {

	private static StringKeyValue strKeyValue = null;
	private static BitKeyValue bitKeyValue = null;
	private static SortedKeyValue sortedKeyVal = null;

	public static StringKeyValue getStringKeyValueInstance() {
		if (strKeyValue == null) {
			strKeyValue = new StringKeyValue();
		}
		return strKeyValue;

	}

	public static BitKeyValue getBitKeyValueInstance() {
		if (bitKeyValue == null) {
			bitKeyValue = new BitKeyValue();
		}
		return bitKeyValue;
	}

	public static SortedKeyValue getSortedKeyValInstance() {
		if (sortedKeyVal == null) {
			sortedKeyVal = new SortedKeyValue();
		}
		return sortedKeyVal;

	}

}
