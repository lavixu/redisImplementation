package factory;

import stringImpl.StringKeyValue;
import bitImpl.BitKeyValue;


public class KeyValuefactory {
	
	private static StringKeyValue strKeyValue = null;
	private static BitKeyValue bitKeyValue = null;
	
	public static StringKeyValue getStringKeyValueInstance()
	{
		if(strKeyValue == null)
		{
			strKeyValue = new StringKeyValue();
		}
		return strKeyValue;
		
	}
	
	public static BitKeyValue getBitKeyValueInstance()
	{
		if(bitKeyValue == null)
		{
			bitKeyValue = new BitKeyValue();
		}
		return bitKeyValue;
	}
	
	

}
