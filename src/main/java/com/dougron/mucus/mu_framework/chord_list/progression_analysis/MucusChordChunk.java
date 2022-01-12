package main.java.com.dougron.mucus.mu_framework.chord_list.progression_analysis;

import DataObjects.chord_chunk.ChordChunk;
import DataObjects.chord_chunk.ChordChunkList;
import StaticChordScaleDictionary.CSD;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;

public class MucusChordChunk extends ChordChunk
{

	private ChordEvent chordEvent;
	
	

	public MucusChordChunk(ChordEvent aChordEvent, ChordChunkList aChordChunkList)
	{
		setParent(aChordChunkList);
		chordEvent = aChordEvent;
		
		setPosition(chordEvent.getPositionInQuarters());
		this.noteList = makeNoteList(position(), chordEvent.getChord().getChordTones(), length());
		setChordOptionList(CSD.getChordOptions(makeNoteValueList(noteList)));
		if (getChordOptionList().size() > 0)
		{
			setBestChordOptionIndex(0);		// temporary setting until further context is established
											// the sequence of NotePatterns in the CSD.npArr would dictate which 
											// possibility would be favoured at this stage of the analysis
		}
	}
	
	
	public void setAssociatedChordChunkOfChord()
	{
		chordEvent.getChord().setAssociatedChordChunk(this);
	}



	
}
