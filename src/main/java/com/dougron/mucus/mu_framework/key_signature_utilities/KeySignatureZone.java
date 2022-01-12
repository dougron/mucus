package main.java.com.dougron.mucus.mu_framework.key_signature_utilities;


public class KeySignatureZone
{

	
	//------------------------
	// MEMBER VARIABLES
	//------------------------

	
	
	//KeySignatureZone Attributes
	private int key;
	private int lengthInBars;

	
	
	//------------------------
	// CONSTRUCTOR
	//------------------------

	
	
	public KeySignatureZone(int aKey, int aLengthInBars)
	{
		key = aKey;
		lengthInBars = aLengthInBars;
	}

	
	
	//------------------------
	// INTERFACE
	//------------------------

	
	
	public boolean setKey(int aKey)
	{
		boolean wasSet = false;
		key = aKey;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setLengthInBars(int aLengthInBars)
	{
		boolean wasSet = false;
		lengthInBars = aLengthInBars;
		wasSet = true;
		return wasSet;
	}

	
	
	public int getKey()
	{
		return key;
	}

	
	
	public int getLengthInBars()
	{
		return lengthInBars;
	}

	
	
	public void delete()
	{}


	
	public String toString()
	{
		return super.toString() + "["+
				"key" + ":" + getKey()+ "," +
				"lengthInBars" + ":" + getLengthInBars()+ "]";
	}
}