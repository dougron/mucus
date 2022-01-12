package main.java.com.dougron.mucus.algorithms.part_generators.chord_part_generator;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dougron.mucus.algorithms.part_generators.chord_part_generator.octave_closest_to_centre.OctaveClosestToCentre;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

public class ChordPartGenerator
{

	static int DEFAULT_PAD_CENTRE_OF_GRAVITY = 45;
	static int DEFAULT_PAD_VELOCITY = 48;
	
	
	
	public static Mu addPadMuToParentMu(Mu parentMu, Mu scopeMu)
	{
		Mu padMu = new Mu("pad");
		parentMu.addMu(padMu, scopeMu.getPositionInBars());
		List<ChordEvent> chordList = scopeMu.getChordEventList();
		ArrayList<Mu> muList = makeMuListFromChordEventsAndAddToPadMu(padMu, chordList);
		setLengthOfMus(scopeMu, muList);
		addMuNotesToMuListItems(chordList, muList);
		return padMu;
	}

	
	
	private static void addMuNotesToMuListItems(List<ChordEvent> chordList, ArrayList<Mu> muList)
	{
		for (int i = 0; i < muList.size(); i++)
		{
			Mu mu = muList.get(i);
			ChordEvent ce = chordList.get(i);
			int[] modChordTones = ce.getChord().getChordTones();
			int pitch;
			
			for (int modPitch: modChordTones)
			{
				pitch = OctaveClosestToCentre.getOctaveClosestToCentre(modPitch, DEFAULT_PAD_CENTRE_OF_GRAVITY);
				mu.addMuNote(new MuNote(pitch, DEFAULT_PAD_VELOCITY));
			}
		}
	}

	
	
	private static void setLengthOfMus(Mu scopeMu, ArrayList<Mu> muList)
	{
		for (int i = 0; i < muList.size(); i++)
		{
			double globalEndPositionInQuarters;
			if (i == muList.size() - 1)
			{
				globalEndPositionInQuarters = scopeMu.getGlobalEndPositionInQuarters();
			}
			else
			{
				globalEndPositionInQuarters = muList.get(i + 1).getGlobalPositionInQuarters();
			}
			Mu mu = muList.get(i);
			mu.setLengthInQuarters(globalEndPositionInQuarters - mu.getGlobalPositionInQuarters());
		}
	}

	
	
	private static ArrayList<Mu> makeMuListFromChordEventsAndAddToPadMu(Mu padMu, List<ChordEvent> chordList)
	{
		ArrayList<Mu> muList = new ArrayList<Mu>();
		for (ChordEvent ce: chordList)
		{
			Mu chordMu = new Mu(ce.getChordName());
			padMu.addMu(chordMu, ce.getPositionInBarsAndBeats());
			muList.add(chordMu);
		}
		return muList;
	}

}
