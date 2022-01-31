package main.java.com.dougron.mucus.mucus_utils.mucus_corpus_utility;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGenerator;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.ableton_live.ableton_live_clip.LiveClip;
import main.java.da_utils.ableton_live.ableton_live_clip.LiveMidiNote;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunk;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunkList;
import main.java.da_utils.combo_variables.IntAndString;
import main.java.da_utils.corpus_capture.CorpusItem;
import main.java.da_utils.corpus_capture.CorpusManager;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class MucusCorpusUtility
{
	
	// -----------------------------------------
	// INTERFACE
	// -----------------------------------------
	

	public static Mu getMu(IntAndString corpusInfo)
	{
		CorpusItem corpusItem = CorpusManager.getCorpusItem(corpusInfo.str);
		Mu mu = new Mu(corpusInfo.str);
		mu.setXMLKey(corpusInfo.i);
		addMelody(corpusItem, mu);
		mu.makePreviousNextMusWithNotes();
		addChords(corpusItem, mu);
		addPhraseBoundaryTags(corpusItem, mu);
		return mu;
	}
	
	
	
//	private static void addTimeSignature(CorpusItem aCorpusItem, Mu aMu)
//	{
//			
//	}



	private static void addPhraseBoundaryTags(CorpusItem aCorpusItem, Mu aMu)
	{
		if (aCorpusItem.containsPart("phrase_start"))
		{
			LiveClip lc = aCorpusItem.getLiveClip("phrase_start");
			addPhraseTags(aMu, lc);
			tagLastNoteAsPhraseEnd(aMu);
		}	
		
	}
	
	
	
	private static void tagLastNoteAsPhraseEnd(Mu aMu)
	{
		List<Mu> mus = aMu.getMusWithNotesIgnoringTupletHolders();
		mus.get(mus.size() - 1).addTag(MuTag.PHRASE_END);		
	}



	private static void addPhraseTags(Mu aMu, LiveClip aLc)
	{
		for (LiveMidiNote lmn: aLc.noteList)
		{
			if (lmn.note == CorpusItem.DEFAULT_NOTE_FOR_PHRASE_START)
			{
				Mu mu = aMu.getMuWithNoteAtGlobalQuartersPosition(lmn.position);
				if (mu != null)
				{
					mu.addTag(MuTag.PHRASE_START);
					if (mu.getPreviousMu() != null) mu.getPreviousMu().addTag(MuTag.PHRASE_END);
				}
			}
		}
		
	}



	private static void addChords(CorpusItem aCorpusItem, Mu aMu)
	{
		if (aCorpusItem.containsPart("chords"))
		{
			LiveClip lc = aCorpusItem.getLiveClip("chords");
			addChordsToMu(aMu, lc);
		}		
	}
	
	
	
	private static void addChordsToMu(Mu aMu, LiveClip aLc)
	{
		ChordChunkList ccl = new ChordChunkList("4n", aLc);
		Object[] objArr = new Object[ccl.chunkList.size() * 2];
		int index = 0;
		for (ChordChunk cc: ccl.chunkList)
		{
			double floatBarPosition = aMu.getGlobalPositionInFloatBars(cc.position());
			objArr[index] = floatBarPosition;
			objArr[index + 1] = cc.chordSymbol();
			index += 2;
		}	
		double lengthInFloatBars = aLc.length / 4 / aLc.signatureNumerator * aLc.signatureDenominator;
		aMu.setChordListGenerator(new FloatBarChordProgression(lengthInFloatBars, objArr));
	}
	


	private static void addMelody(CorpusItem aCorpusItem, Mu aMu)
	{
		if (aCorpusItem.containsPart("melody"))
		{
			LiveClip lc = aCorpusItem.getLiveClip("melody");
			makeMuMelody(aMu, lc);
		}	
	}


	private static void makeMuMelody(Mu aMu, LiveClip lcMelody)
	{
		ArrayList<LiveMidiNote> tripletList = new ArrayList<LiveMidiNote>();
		boolean hasTriplet = false;
//		Mu mu = new Mu(songName);
		aMu.setTimeSignatureGenerator(getTimeSignatureGenerator(lcMelody));
		aMu.setLengthInBars(aMu.getGlobalPositionInBarsAndBeats(lcMelody.loopEnd).getBarPosition() - aMu.getGlobalPositionInBarsAndBeats(lcMelody.loopStart).getBarPosition());
		for (LiveMidiNote lmn: lcMelody.noteList)
		{
			if (hasTripletPosition(lmn) || hasTripletLength(lmn))
			{
				tripletList.add(lmn);
				hasTriplet = true;
			}
			else
			{
				if (hasTriplet)
				{
					Mu tripletHolder = new Mu("triplet-holder");
					tripletHolder.setIsTupletPrintContainer(true);
					double position = tripletList.get(0).position;
					double endPosition = Mu.round(tripletList.get(tripletList.size() - 1).position + tripletList.get(tripletList.size() - 1).length, 10);
					double length = Mu.round(endPosition - position, 10);
					tripletHolder.setLengthInQuarters(length);
					tripletHolder.setTupletNumerator(3);
					tripletHolder.setTupletDenominator(2);					
					for (LiveMidiNote tripLmn: tripletList)
					{
						Mu tripMu = new Mu("triplet");
						tripMu.setLengthInQuarters(tripLmn.length);
						tripMu.addMuNote(new MuNote(tripLmn.note, tripLmn.velocity));
						double pos = tripLmn.position - position;
						tripletHolder.addMu(tripMu, pos);
					}
					aMu.addMu(tripletHolder, position);
					hasTriplet = false;
					tripletList.clear();
				}
				Mu noteMu = new Mu("note");
				noteMu.addMuNote(new MuNote(lmn.note, lmn.velocity));
				noteMu.setLengthInQuarters(lmn.length);
				aMu.addMu(noteMu, lmn.position - lcMelody.loopStart);
			}
			
		}
		if (hasTriplet)
		{
			Mu tripletHolder = new Mu("triplet-holder");
			tripletHolder.setIsTupletPrintContainer(true);
			double position = tripletList.get(0).position;
			double endPosition = Mu.round(tripletList.get(tripletList.size() - 1).position + tripletList.get(tripletList.size() - 1).length);
			double length = Mu.round(endPosition - position);
			tripletHolder.setLengthInQuarters(length);
			tripletHolder.setTupletNumerator(3);
			tripletHolder.setTupletDenominator(2);					
			for (LiveMidiNote tripLmn: tripletList)
			{
				Mu tripMu = new Mu("triplet");
				tripMu.setLengthInQuarters(tripLmn.length);
				tripMu.addMuNote(new MuNote(tripLmn.note, tripLmn.velocity));
				double pos = tripLmn.position - position;
				tripletHolder.addMu(tripMu, pos);
			}
			aMu.addMu(tripletHolder, position);
			hasTriplet = false;
			tripletList.clear();
		}
	}

	
	
	private static double[] tripletLengths = new double[] {0.333333333333, 0.666666666667};
	private static double tripletTestEpsilon = 0.001;
	
	private static boolean hasTripletPosition(LiveMidiNote lmn)
	{
		double modPosition = lmn.position % 1.0;
		for (double d: tripletLengths)
		{
			if (Math.abs(modPosition - d) < tripletTestEpsilon) return true;
		}
		return false;
	}
	
	
	
	private static boolean hasTripletLength(LiveMidiNote lmn)
	{
		double modLength = lmn.length % 1.0;
		for (double d: tripletLengths)
		{
			if (Math.abs(modLength - d) < tripletTestEpsilon) return true;
		}
		return false;
	}
	


	private static TimeSignatureListGenerator getTimeSignatureGenerator(LiveClip aLc)
	{
	
		TimeSignature ts = TimeSignature.getTimeSignature(aLc.signatureNumerator, aLc.signatureDenominator);
		if (ts == null)
		{
			return TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.FOUR_FOUR);
		}
		else
		{
			return TimeSignatureListGeneratorFactory.getGenerator(ts);
		}
		
		
	}


}
