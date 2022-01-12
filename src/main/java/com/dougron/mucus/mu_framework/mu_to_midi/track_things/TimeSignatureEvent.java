package main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things;

public class TimeSignatureEvent implements MidiThing
{
	
	private static final int sortPriority = 0;
	
	private int globalPositionInSubdivisions;
//	private double tempo;

	private int numerator;
	private int denominator;
	
	
	public TimeSignatureEvent (int aNumerator, int aDenominator, int aGlobalPosition)
	{
		globalPositionInSubdivisions = aGlobalPosition;
		numerator = aNumerator;
		denominator = aDenominator;
	}

	
	
	@Override
	public int getGlobalPositionInSubdivisions ()
	{
		return globalPositionInSubdivisions;
	}

	
	
	@Override
	public byte[] getBytes ()
	{
//		int usPerQuarter = (int)(60.0 / tempo * 1000000);
		int midiClocksPerMetronome = 24;
		int thirtySecondsInQuarter = 32;
		int denominatorExponent = (int) (Math.log(denominator) / Math.log(2) + 1e-10);
		return new byte[] 
				{
						(byte)0xff, 
						0x58, 
						0x04, 
						(byte)numerator, 
						(byte)denominatorExponent, 
						(byte)midiClocksPerMetronome, 
						(byte)thirtySecondsInQuarter };
	}
	
	
	
	@Override
	public int getSortPriority ()
	{
		return sortPriority;
	}
	
}