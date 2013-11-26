package test;
import org.testng.Assert;
import org.testng.annotations.*;

import bitImpl.BitKeyValue;

public class BitKeyValueTest {
	
	//Basic test case with setting and retrieval of a index in bitset.
	@Test
	public void testSetGetBit()
	{
		String key = "key1";
		int index = 32; 
		boolean flag = true;
		BitKeyValue bitKeyValue = new BitKeyValue();
		bitKeyValue.set(key, index, flag);
		Assert.assertEquals(flag, (boolean)bitKeyValue.get(key, index));
		bitKeyValue.set(key, index, !flag); //flip the bit and check if it works.
		Assert.assertEquals(!flag, (boolean)bitKeyValue.get(key, index));
		
	}
	
	// Assign a smaller index and test if the array expands on adding a higher index.
	@Test
	public void testExpandingArray()
	{
		String key = "key1";
		int index = 32, index1 = 65; 
		boolean flag = true;
		BitKeyValue bitKeyValue = new BitKeyValue();
		bitKeyValue.set(key, index, flag);
		bitKeyValue.set(key, index1, flag);
		Assert.assertEquals(flag, (boolean) bitKeyValue.get(key, index1));
	}

}
