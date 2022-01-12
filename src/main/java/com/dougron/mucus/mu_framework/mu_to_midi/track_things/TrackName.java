package main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things;

import com.google.common.collect.Lists;

public class TrackName implements MidiThing
{
	
	private String name;
	
	

	public TrackName (String aName)
	{
		name = aName;
	}

	
	
	@Override
	public int getGlobalPositionInSubdivisions ()
	{
		return 0;
	}

	
	
	@Override
	public int getSortPriority ()
	{
		return 0;
	}

	
	
	@Override
	public byte[] getBytes ()
	{
		byte[] barr = new byte[4 + name.length()];
		barr[0] = (byte)0xFF;
		barr[1] = 0x03;
		barr[2] = (byte)(name.length() + 1);
		int index = 3;
		for (Character ch: Lists.charactersOf(name))
		{
			barr[index] = (byte)(int)ch;
			index++;
		}
		barr[barr.length - 1] = 0x00;
		return barr;
	}

}
