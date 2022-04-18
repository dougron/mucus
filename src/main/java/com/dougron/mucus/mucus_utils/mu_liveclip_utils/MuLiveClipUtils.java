package main.java.com.dougron.mucus.mucus_utils.mu_liveclip_utils;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.SingleTimeSignature;
import main.java.da_utils.ableton_live.ableton_live_clip.LiveClip;
import main.java.da_utils.ableton_live.ableton_live_clip.LiveMidiNote;
import main.java.da_utils.chord_progression_analyzer.ProgressionAnalyzer;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunk;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunkList;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class MuLiveClipUtils
{
	
	/*
	 * make LiveClip into Mu.
	 */
	public static Mu makeMu(LiveClip melodyClip, LiveClip chordClip)
	{
		Mu parent = new Mu("parent");
		
		TimeSignature ts = getTimeSignatureFromLiveClip(melodyClip);
		
		int lengthInBars = setLengthInBars(melodyClip, parent, ts);
		
		parent.setTimeSignatureGenerator(new SingleTimeSignature(ts));
		
		setChordListGenerator(chordClip, parent, ts, lengthInBars);
		
		addMuNotes(melodyClip, parent);
		
		return parent;
	}



	private static void addMuNotes(LiveClip melodyClip, Mu parent)
	{
		for (LiveMidiNote lmn: melodyClip.noteList)
		{
			Mu note = new Mu("note");
			note.addMuNote(new MuNote(lmn.note, lmn.velocity));
			note.setLengthInQuarters(lmn.length);
			parent.addMu(note, lmn.position);
		}
	}



	private static void setChordListGenerator(LiveClip chordClip, Mu parent, TimeSignature ts, int lengthInBars)
	{
		Object[] floatBarChordArray = getFloatBarAndChordObjectArray(chordClip, ts);
		FloatBarChordProgression progression = new FloatBarChordProgression((double)lengthInBars, floatBarChordArray);
		parent.setChordListGenerator(progression);
	}



	private static int setLengthInBars(LiveClip melodyClip, Mu parent, TimeSignature ts)
	{
		int lengthInBars = (int)((melodyClip.loopEnd - melodyClip.loopStart) / ts.getLengthInQuarters());
		parent.setLengthInBars(lengthInBars);
		return lengthInBars;
	}


	
// privates ---------------------------------------------------------------------------------------------------
	
	private static Object[] getFloatBarAndChordObjectArray(LiveClip chordClip, TimeSignature ts)
	{
		ChordChunkList chunkList = new ChordChunkList(ProgressionAnalyzer.DEFAULT_REZ, chordClip);
		Object[] floatBarChordArray = new Object[chunkList.chunkList.size() * 2];
		int index = 0;
		for (ChordChunk cc: chunkList.chunkList)
		{
			floatBarChordArray[index] = cc.position() / ts.getLengthInQuarters();
			floatBarChordArray[index + 1] = cc.chordSymbol();
			index += 2;
		}
		return floatBarChordArray;
	}


	private static TimeSignature getTimeSignatureFromLiveClip(LiveClip melodyClip)
	{
		int num = melodyClip.signatureNumerator;
		int denom = melodyClip.signatureDenominator;
		TimeSignature ts = TimeSignature.getTimeSignature(num, denom);
		return ts;
	}
}
