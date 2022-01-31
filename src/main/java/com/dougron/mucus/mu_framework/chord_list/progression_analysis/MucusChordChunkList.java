package main.java.com.dougron.mucus.mu_framework.chord_list.progression_analysis;


import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.da_utils.ableton_live.ableton_live_clip.LiveClip;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunk;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunkList;


public class MucusChordChunkList extends ChordChunkList
{

	private ChordList chordList;
//	private TimeSignatureGenAndMap tsgm;


	public MucusChordChunkList(ChordList aChordList)
	{
		chordList = aChordList;
		for (ChordEvent ce: aChordList.getChordEventList())
		{
			chunkList.add(new MucusChordChunk(ce, this));
		}
		setNextAndPrevious();
		setChunkLengths();
		lc = makeLiveClip();
	}
	
	
	
	private LiveClip makeLiveClip()
	{
		LiveClip lc = new LiveClip();
		for (ChordChunk mcc: chunkList)
		{
			lc.addNoteList(mcc.noteList);
		}
		return lc;
	}



	public String name()
	{
		return "MucusChordChunkList....";
	}
	
	
	
	public double getLength()
	{
		return chordList.getTsgm().getPositionInQuarters(chordList.getLengthInBarsAndBeats());
	}
	
	
	
	private void setChunkLengths()
	{
		for (int i = 0; i < chunkList.size(); i++)
		{
			ChordChunk cc = chunkList.get(i);
			if (i == chunkList.size() - 1)
			{
				cc.setLength(chordList.getTsgm().getPositionInQuarters(chordList.getLengthInBarsAndBeats()) - cc.position());
			}
			else
			{
				ChordChunk nextCC = chunkList.get(i + 1);
				cc.setLength(nextCC.position() - cc.position());
			}
		}
	}
	
	
	
//	public void associateChordChunksWithChords()
//	{
//		for (ChordChunk cc: chunkList)
//		{
//			((MucusChordChunk)cc).setAssociatedChordChunkOfChord();
//		}		
//	}

	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (ChordChunk mcc: chunkList)
		{
			sb.append(mcc.position() + " " + mcc.length() + " " + mcc.chordSymbol());
		}
		return sb.toString();
	}
	
	
}
