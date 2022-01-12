package main.java.com.dougron.mucus.algorithms.part_generators.bass_part_generator;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

public class BassPartGenerator
{

	
	static int DEFAULT_BASS_CENTRE_OF_GRAVITY = 30;
	static int DEFAULT_BASS_VELOCITY = 64;
	
	public static Mu addBassMuToParentMu(Mu parentMu, Mu scopeMu)
	{
		Mu bassMu = new Mu("bass");
		parentMu.addMu(bassMu, scopeMu.getPositionInBars());
		List<ChordEvent> chordList = scopeMu.getChordEventList();
//		int lengthInBars = scopeMu.getLengthInBars();
		
		ArrayList<Mu> muList = makeMuListFromChordEventsAndAddToPadMu(bassMu, chordList);
		
		setLengthOfMus(scopeMu, muList);
		
		addMuNotesToMuListItems(chordList, muList);
		
		
		return bassMu;
	}
	
	
	
	private static void addMuNotesToMuListItems(List<ChordEvent> chordList, ArrayList<Mu> muList)
	{
		for (int i = 0; i < muList.size(); i++)
		{
			Mu mu = muList.get(i);
			ChordEvent ce = chordList.get(i);
			int rootPitch = ce.getChord().getRootIndex();
			
			int distance = 128; 	// artbitrarily large

			while (distance > 0)
			{
				rootPitch += 12;	// 12 = octave
				distance = DEFAULT_BASS_CENTRE_OF_GRAVITY - rootPitch;
			}
			mu.addMuNote(new MuNote(rootPitch, DEFAULT_BASS_VELOCITY));
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

	
	
	private static ArrayList<Mu> makeMuListFromChordEventsAndAddToPadMu(Mu aMu, List<ChordEvent> chordList)
	{
		ArrayList<Mu> muList = new ArrayList<Mu>();
		for (ChordEvent ce: chordList)
		{
			Mu chordMu = new Mu(ce.getChordName());
			aMu.addMu(chordMu, ce.getPositionInBarsAndBeats());
			muList.add(chordMu);
		}
		return muList;
	}
}
