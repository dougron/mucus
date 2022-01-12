package main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things;

public class NoteOff extends NoteThing implements MidiThing
{
	
	private static final int sortPriority = 100;

	
	
	public NoteOff (int aPitch, int aVelocity, int aMidiChannel, int aGlobalPosition)
	{
		super(aPitch, aVelocity, aMidiChannel, aGlobalPosition);
	}
	

	
	@Override
	public byte[] getBytes ()
	{
		byte[] arr = new byte[] {
				(byte)(0x80 + getMidiChannel() - 1),
				(byte)getPitch(),
				(byte)getVelocity()
		};
		return arr;
	}
	
	
	
	@Override
	public int getSortPriority ()
	{
		return sortPriority;
	}
}