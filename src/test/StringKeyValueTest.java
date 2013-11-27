package test;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.java.impl.stringImpl.*;

public class StringKeyValueTest {
	
	//Simple key Value insertion
	@Test
	public void testStringKeys()
	{
		String key = "key1", value = "909hfyd";
		StringKeyValue strKeyVal = new StringKeyValue();
		strKeyVal.set(key, value);
		Assert.assertEquals(value, strKeyVal.get(key));
	}
	
	//Insert only when not present
	@Test
	public void testInsertingNewElementOnly()
	{
		String key = "key1", value = "909hfyd";
		StringKeyValue strKeyVal = new StringKeyValue();
		strKeyVal.set(key, value, false); //Inserting the key already.
		boolean inserted = strKeyVal.set(key, value, false);
		Assert.assertEquals(inserted, false);
	}
	
	//Insert only when present
	@Test
	public void testOnlyUpdate()
	{
		String key = "key1", value = "909hfyd";
		StringKeyValue strKeyVal = new StringKeyValue();
		strKeyVal.set(key, value); //Inserting the key already.
		boolean inserted = strKeyVal.set(key, value, true);
		Assert.assertEquals(inserted, true);
	}
	
	//Test if the key is expired when inserted with a timeout
	@Test
	public void testKeyExpiry() throws InterruptedException
	{
		String key = "key1", value = "909hfyd";
		long timeout = 3*1000; //The function accepts in milliseconds.
		StringKeyValue strKeyVal = new StringKeyValue();
		strKeyVal.set(key, value,timeout);
		Thread.sleep(timeout+10);
		Assert.assertNull(strKeyVal.get(key));
	}

}
