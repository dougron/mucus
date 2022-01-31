package test.java.com.dougron.mucus.algorithms.part_generators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.part_generators.chord_part_generator.ChordPartGenerator;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

class ChordPartGenerator_Test
{

	@Test
	void given_parent_4_4_time_and_length_1_bar_and_chord_of_Dm_then_padMu_contains_1_child_mu_anlyzed_as_i_chord_in_Dm_of_length_4_quarters() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(1);
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Dm")));
		Mu padMu = ChordPartGenerator.addPadMuToParentMu(parent, parent);
		
		Mu chordMu = padMu.getMus().get(0);		
		Chord chord = chordMu.getChordAt(new BarsAndBeats(0, 0.0));

		assertEquals(1, padMu.getMus().size());
		assertEquals(4.0, chordMu.getLengthInQuarters());
		assertEquals("d:Dmin(i)", chord.getChordAnalysisString());
	}
	
	
	@Test
	void given_parent_7_8_time_and_length_1_bar_and_chord_of_Dm_then_padMu_contains_1_child_mu_anlyzed_as_i_chord_in_Dm_of_length_3_5_quarters() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		parent.setLengthInBars(1);
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Dm")));
		Mu padMu = ChordPartGenerator.addPadMuToParentMu(parent, parent);
		
		Mu chordMu = padMu.getMus().get(0);		
		Chord chord = chordMu.getChordAt(new BarsAndBeats(0, 0.0));

		assertEquals(1, padMu.getMus().size());
		assertEquals(3.5, chordMu.getLengthInQuarters());
		assertEquals("d:Dmin(i)", chord.getChordAnalysisString());
	}
	
	
	@Test
	void given_parent_4_4_time_and_length_2_bar_and_chord_of_Dm_Gm_then_padMu_contains_2_corectly_positioned_and_analyzed_children() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(2);
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Dm", "Gm"}));
		Mu padMu = ChordPartGenerator.addPadMuToParentMu(parent, parent);
		
		Mu Dm = padMu.getMus().get(0);	
		Mu Gm = padMu.getMus().get(1);
		Chord chord_Dm = Dm.getChordAt(new BarsAndBeats(0, 0.0));
		Chord chord_Gm = Gm.getChordAt(new BarsAndBeats(0, 0.0));

		assertEquals(2, padMu.getMus().size());
		assertEquals(4.0, Dm.getLengthInQuarters());
		assertEquals(4.0, Gm.getLengthInQuarters());
		assertEquals("d:Dmin(i)", chord_Dm.getChordAnalysisString());
		assertEquals("d:Gmin(iv)", chord_Gm.getChordAnalysisString());
		assertEquals(0.0, Dm.getGlobalPositionInQuarters());
		assertEquals(4.0, Gm.getGlobalPositionInQuarters());
	}
	
	
	@Test
	void given_parent_7_8_time_and_length_2_bar_and_chord_of_Dm_Gm_then_padMu_contains_2_corectly_positioned_and_analyzed_children() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		parent.setLengthInBars(2);
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Dm", "Gm"}));
		Mu padMu = ChordPartGenerator.addPadMuToParentMu(parent, parent);
		
		Mu Dm = padMu.getMus().get(0);	
		Mu Gm = padMu.getMus().get(1);
		Chord chord_Dm = Dm.getChordAt(new BarsAndBeats(0, 0.0));
		Chord chord_Gm = Gm.getChordAt(new BarsAndBeats(0, 0.0));

		assertEquals(2, padMu.getMus().size());
		assertEquals(3.5, Dm.getLengthInQuarters());
		assertEquals(3.5, Gm.getLengthInQuarters());
		assertEquals("d:Dmin(i)", chord_Dm.getChordAnalysisString());
		assertEquals("d:Gmin(iv)", chord_Gm.getChordAnalysisString());
		assertEquals(0.0, Dm.getGlobalPositionInQuarters());
		assertEquals(3.5, Gm.getGlobalPositionInQuarters());
	}

}
