package main.java.com.dougron.mucus.mu_framework.chord_list;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;


public class ChordEvent
{

	private Chord chord;
	private BarsAndBeats positionInBarsAndBeats;	
	private double positionInQuarters;

	
	public ChordEvent(Chord aChord, BarsAndBeats aBarsAndBeats, TimeSignatureGenAndMap tsgm)
	{
		positionInBarsAndBeats = aBarsAndBeats;
		positionInQuarters = tsgm.getPositionInQuarters(aBarsAndBeats);
		chord = aChord;
	}

	
	
	public ChordEvent(Chord aChord, BarsAndBeats aBarsAndBeats, double aPositionInQuarters)
	{
		positionInBarsAndBeats = aBarsAndBeats;
		positionInQuarters = aPositionInQuarters;
		chord = aChord;
	}



	public Chord getChord()
	{
		return chord;
	}

	
	
	public BarsAndBeats getPositionInBarsAndBeats()
	{
		return positionInBarsAndBeats;
	}



	public double getPositionInQuarters()
	{
		return positionInQuarters;
	}

	
	
	public String toString()
	{
		return chord.name() + "\n" + positionInBarsAndBeats.toString();
	}



	public String getChordName()
	{
		return chord.name();
	}
}
