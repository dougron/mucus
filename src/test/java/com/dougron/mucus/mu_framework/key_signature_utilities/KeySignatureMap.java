/*CODE CAN BE EDITED - Umple no longer used*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package test.java.com.dougron.mucus.mu_framework.key_signature_utilities;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.key_signature_utilities.KeySignatureZone;
import main.java.da_utils.static_chord_scale_dictionary.CSD;


/**
 * Classes for management of KeySignatures in a structure using bar counts. Currently, for testing purposes 
 * and simplicities sake, only accomodates one key signature. Multiple key signature zones can be implemented
 * in a similar way to the TimeSignatureMap
 * 
 * as of 2021_01_07 am assuming that aKey is an xmlKey
 */

public class KeySignatureMap
{

	
	public static final KeySignatureMap KEY_SIGNATURE_MAP_OF_C_MAJOR = new KeySignatureMap(0);

	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//KeySignatureMap Attributes
	private int key;
	private List<KeySignatureZone> keySignatureZones;
	private int[] diatonicNotes;


	//------------------------
	// CONSTRUCTOR
	//------------------------


	public KeySignatureMap(int aKey)
	{
		key = aKey;
		keySignatureZones = new ArrayList<KeySignatureZone>();
		keySignatureZones.add(new KeySignatureZone(key, 1));	// 1 - artibrary length, as it will not be followed until the class is fully implemented
		diatonicNotes = makeDiatonicNotes(aKey);
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

	
	
	public int getKey()
	{
		return key;
	}


	
	public KeySignatureZone getKeySignatureZone(int index)
	{
		KeySignatureZone aKeySignatureZone = keySignatureZones.get(index);
		return aKeySignatureZone;
	}

	
	
	public KeySignatureZone[] getKeySignatureZones()
	{
		KeySignatureZone[] newKeySignatureZones = keySignatureZones.toArray(new KeySignatureZone[keySignatureZones.size()]);
		return newKeySignatureZones;
	}

	
	
	public int numberOfKeySignatureZones()
	{
		int number = keySignatureZones.size();
		return number;
	}

	
	
	public boolean hasKeySignatureZones()
	{
		boolean has = keySignatureZones.size() > 0;
		return has;
	}

	
	
	public int indexOfKeySignatureZone(KeySignatureZone aKeySignatureZone)
	{
		int index = keySignatureZones.indexOf(aKeySignatureZone);
		return index;
	}

	
	
	public void delete()
	{}


	
	/**
	 * temporary measure until full implementation where key signatures can change
	 */
	public int getKey(int barIndex){
		return key;
	}
	
	
	
	public int getKey(BarsAndBeats barsAndBeats)
	{
		return key;
	}

	

	public String toString()
	{
		return "keySignatureMap: key=" + getKey();
	}



	public boolean isDiatonicNote(int aPitch)
	{
		if (getIndex(diatonicNotes, aPitch % 12) > -1)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	
	
	private static int getIndex(int[] arr, int item)
	{
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] == item) return i;
		}
		return -1;
	}

	 
	
	private int[] makeDiatonicNotes(int aKey)
	{
		int[] arr = CSD.getDiatonicModeDegrees(CSD.IONIAN_MODE);
		int[] output = new int[arr.length];
	
		int x = (aKey + 12) * 7;
		for (int i = 0; i < arr.length; i++)
		{
			output[i] = ((arr[i] + x) % 12);
		}
		return output;
	}



	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("key_signature_map");
		Element key_element = document.createElement("key");
		key_element.appendChild(document.createTextNode("" + key));
		element.appendChild(key_element);
		return element;
	}
	
}