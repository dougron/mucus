package main.java.com.dougron.mucus.algorithms.part_generators.chord_part_generator.octave_closest_to_centre;

import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

public class OctaveClosestToCentre
{

	// gets octave of note closest to note centre
	// for positioning notes in a pitch register
	public static int getOctaveClosestToCentre(int note, int centre)
	{
		int distance = 128; 	// artbitrarily large
		note = note % 12;
		int pitch = note;
		while (distance > 0)
		{
			pitch += 12;	// 12 = octave
			distance = centre - pitch;
		}
		return pitch;
	}
}
