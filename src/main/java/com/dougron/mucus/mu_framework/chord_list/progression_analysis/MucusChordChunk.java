package main.java.com.dougron.mucus.mu_framework.chord_list.progression_analysis;

import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunk;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunkList;
import main.java.da_utils.static_chord_scale_dictionary.CSD;

public class MucusChordChunk extends ChordChunk
{

	private ChordEvent chordEvent;
	
	

	public MucusChordChunk(ChordEvent aChordEvent, ChordChunkList aChordChunkList)
	{
		setParent(aChordChunkList);
		chordEvent = aChordEvent;
		
		setPosition(chordEvent.getPositionInQuarters());
		this.noteList = makeNoteList(position(), chordEvent.getChord().getChordTones(), length());
//		System.out.println(chordEvent.getChord().name() + " " + noteList.toString());
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
