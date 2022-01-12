package main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things;

public class TempoEvent implements MidiThing
{
	
	private static final int sortPriority = 0;
	
	private int globalPositionInSubdivisions;
	private double tempo;

	public TempoEvent (double aTempo, int aGlobalPosition)
	{
		globalPositionInSubdivisions = aGlobalPosition;
		tempo = aTempo;
	}

	
	
	@Override
	public int getGlobalPositionInSubdivisions ()
	{
		return globalPositionInSubdivisions;
	}

	
	
	@Override
	public byte[] getBytes ()
	{
		int usPerQuarter = (int)(60.0 / tempo * 1000000);
		int b1 = (usPerQuarter >> 16) & 0b11111111;
		int b2 = (usPerQuarter >> 8) & 0b11111111;
		int b3 = usPerQuarter & 0b11111111;
		return new byte[] {(byte)0xff, 0x51, 0x03, (byte)b1, (byte)b2, (byte)b3};
	}
	
	
	
	@Override
	public int getSortPriority ()
	{
		return sortPriority;
	}
	
}