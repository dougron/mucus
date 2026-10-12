package main.java.com.dougron.mucus.mu_framework.chord_list;

import main.java.da_utils.chord_progression_analyzer.ChordSequenceKeyAnalyzer;
import main.java.da_utils.chord_progression_analyzer.ProgressionAnalyzer;
import main.java.da_utils.chord_progression_analyzer.TonalParsimonyAnalyzer;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunkList;



/*
 * which ChordSequenceKeyAnalyzer a ChordList uses to work out the prevailing key and function of
 * each chord (and so the scale tones, non tones etc available through Chord)
 *
 * set on the root Mu with Mu.setChordAnalyzerType, which passes it on to every ChordList the Mu builds
 */
public enum ChordAnalyzerType
{
	PROGRESSION_ANALYZER
	{
		@Override
		public ChordSequenceKeyAnalyzer create(ChordChunkList ccl)
		{
			return new ProgressionAnalyzer(ccl);
		}
	},

	TONAL_PARSIMONY
	{
		@Override
		public ChordSequenceKeyAnalyzer create(ChordChunkList ccl)
		{
			return new TonalParsimonyAnalyzer(ccl);
		}
	};


	public static final ChordAnalyzerType DEFAULT = PROGRESSION_ANALYZER;


	public abstract ChordSequenceKeyAnalyzer create(ChordChunkList ccl);
}
