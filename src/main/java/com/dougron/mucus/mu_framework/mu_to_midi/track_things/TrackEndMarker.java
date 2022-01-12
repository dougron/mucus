package main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things;

public class TrackEndMarker implements MidiThing
{
	
	private static final int sortPriority = 1000;	
	private static final byte[] bytes = new byte[] {(byte)0xff, 0x2f, 0x00};
	
	private int globalPositionInSubdivisions;



	public TrackEndMarker (int aGlobalPosition)
	{
		globalPositionInSubdivisions = aGlobalPosition;
	}
	
	
	
	@Override
	public byte[] getBytes ()
	{
		return bytes;
	}



	public int getGlobalPositionInSubdivisions ()
	{
		return globalPositionInSubdivisions;
	}
	
	
	
	@Override
	public int getSortPriority ()
	{
		return sortPriority;
	}
}