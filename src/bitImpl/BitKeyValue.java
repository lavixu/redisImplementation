package bitImpl;

import java.util.ArrayList;
import java.util.HashMap;

public class BitKeyValue {
	
	public static HashMap<String, ArrayList<Integer> > getKeyValueBit() {
		return keyValueBit;
	}

	public static void setKeyValueBit(HashMap<String, ArrayList<Integer>> keyValueBit) {
		BitKeyValue.keyValueBit = keyValueBit;
	}

	private static HashMap<String, ArrayList<Integer> > keyValueBit = new HashMap<String, ArrayList<Integer>>();
	
	
//	public void set(String key, int index, Boolean flag) {
//		BitSet bitset = get(key);
//		if (bitset == null)
//		{
//			bitset = new BitSet(index);
//		}
//		bitset.set(index,flag);
//		keyValueBit.put(key, bitset);
//	}
//	
//	private BitSet get(String key)
//	{
//		return keyValueBit.get(key);
//	}
//	
	private ArrayList<Integer> get(String key)
	{
		return keyValueBit.get(key);
	}
	
	public int set(String key, int index, Boolean flag)
	{
		ArrayList<Integer> value = get(key);
		int prevValue = 0;
		// Storing the bits as array of integers each with 32 bits.
		int arrIndex = index / 32;
		int bitindex = 32 - index % 32;
		if (index % 32 == 0)// Handling the corner case of 32 multiples as this
							// is zero based indexing
		{
			arrIndex++;
			bitindex = 0;
		}
		// Updating from left to right for convenience internally.

		if (value == null) {
			value = new ArrayList<>();
			ensureCapacityForArray(arrIndex +1, value);
			int intVal = flag ? 1 << bitindex : ~(1 << bitindex);
			value.set(arrIndex, intVal);
		}
		else
		{
			int currentSize = value.size();
			if(currentSize < arrIndex)
			{
				ensureCapacityForArray(arrIndex+1, value);
				int indexToUpdate = flag ? 1 << bitindex : ~(1<< bitindex);
				value.set(arrIndex, indexToUpdate);
			}
			else
			{
				int currentVal = value.get(arrIndex);
				prevValue =  ((currentVal & 1 << bitindex) != 0) ? 1 : 0;
				currentVal = flag ?  (currentVal | 1 << bitindex) :  currentVal & ~(1<< bitindex);
				value.set(arrIndex, currentVal);
			}
		}
		keyValueBit.put(key,  value);
		return prevValue;
	}
	
	//Explicitly adding 0's to all the newly added capacity to avoid null pointers.
	private void ensureCapacityForArray(int capacity, ArrayList<Integer> array)
	{
		int initialSize = array.size();
		array.ensureCapacity(capacity);
		for(int i = initialSize; i <=capacity; i++)
		{
			array.add(0);
		}
				
	}
	
	
	
	public Boolean get(String key, int index) {
		ArrayList<Integer> value = get(key);
		if (value == null) {
			return false;
		} else {
			int arrIndex = index / 32;
			int bitindex = 32 - index % 32;
			if (index % 32 == 0)// Handling the corner case of 32 multiples as
								// this
								// is zero based indexing
			{
				arrIndex++;
				bitindex = 0;
			}
			int num = value.get(arrIndex);
			return ((num & 1 << bitindex) != 0);
		}
	}

}
