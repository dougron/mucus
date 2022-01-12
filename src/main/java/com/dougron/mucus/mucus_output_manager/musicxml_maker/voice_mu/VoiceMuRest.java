/*CODE CAN BE EDITED - Umple no longer used*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu;

import main.java.com.dougron.mucus.mu_framework.Mu;

/**
 * basically a VoiceMu that can have position and length set in the arguments.
 * when functioning as a rest, aMu should be null, and that will set all the
 * relevant flags in the superclass
 */

public class VoiceMuRest extends VoiceMu
{

	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//VoiceMuRest Attributes
	private double positionInQuarters;
	private double length;
	private boolean isWholeBarRest;



	//------------------------
	// CONSTRUCTOR
	//------------------------



	public VoiceMuRest(Mu aMu, double aPositionInQuarters, double aLength)
	{
		super(aMu);
		positionInQuarters = aPositionInQuarters;
		length = aLength;
		isWholeBarRest = false;
		setGlobalPositionInQuarters(aPositionInQuarters);
		setLengthInQuarters(aLength);
	}



	//------------------------
	// INTERFACE
	//------------------------



	public boolean setPositionInQuarters(double aPositionInQuarters)
	{
		boolean wasSet = false;
		positionInQuarters = aPositionInQuarters;
		wasSet = true;
		return wasSet;
	}



	public boolean setLength(double aLength)
	{
		boolean wasSet = false;
		length = aLength;
		wasSet = true;
		return wasSet;
	}



	public boolean setIsWholeBarRest(boolean aIsWholeBarRest)
	{
		boolean wasSet = false;
		isWholeBarRest = aIsWholeBarRest;
		wasSet = true;
		return wasSet;
	}



	public double getPositionInQuarters()
	{
		return positionInQuarters;
	}



	public double getLength()
	{
		return length;
	}



	/**
	 * results in a whole note rest for any empty bar in any time signature (exceptions for 4/2 time and 3/16 and smaller noted from Wikipedia, but not catered for)
	 */
	public boolean getIsWholeBarRest()
	{
		return isWholeBarRest;
	}



	public boolean isIsWholeBarRest()
	{
		return isWholeBarRest;
	}



	public void delete()
	{
		super.delete();
	}


	/**
	 * is a new instance but only does constructor arguments. Must set things like measure and so on
	 */
	public VoiceMuRest deepCopy(){
		return new VoiceMuRest(null, positionInQuarters, length);
	}

}