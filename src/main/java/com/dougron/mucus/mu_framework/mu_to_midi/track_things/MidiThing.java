package main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things;

import java.util.Comparator;

public interface MidiThing
{
	
	
	public int getGlobalPositionInSubdivisions();
	public int getSortPriority();
	public byte[] getBytes();
	
	
	
	public static Comparator<MidiThing> globalPositionComparator = new Comparator<MidiThing>()
			{

				@Override
				public int compare (MidiThing o1, MidiThing o2)
				{
					if (o1.getGlobalPositionInSubdivisions() > o2.getGlobalPositionInSubdivisions()) return 1;
					if (o1.getGlobalPositionInSubdivisions() < o2.getGlobalPositionInSubdivisions()) return -1;
					if (o1.getSortPriority() > o2.getSortPriority()) return 1;
					if (o1.getSortPriority() < o2.getSortPriority()) return -1;
					return 0;
				}
		
			};
}
