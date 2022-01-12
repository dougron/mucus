package main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things;

public class NoteThing
{
	private int pitch;
	private int velocity;
	private int midiChannel;
	private int globalPositionInSubdivisions;
	
	public NoteThing (int aPitch, int aVelocity, int aMidiChannel, int aGlobalPosition)
	{
		pitch = aPitch;
		velocity = aVelocity;
		midiChannel = aMidiChannel;
		globalPositionInSubdivisions = aGlobalPosition;
	}

	public int getPitch ()
	{
		return pitch;
	}

	public int getVelocity ()
	{
		return velocity;
	}

	public int getMidiChannel ()
	{
		return midiChannel;
	}

	public int getGlobalPositionInSubdivisions ()
	{
		return globalPositionInSubdivisions;
	}
	
	
}