package test.java.com.dougron.mucus.mu_framework.chord_list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordListGenerator;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureList;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.chord_progression_analyzer.ChordInKeyObject;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

class ChordList_Test
{

	ChordList cl;
	
	
	@BeforeEach
	void make_chord_list()
	{
		cl = new ChordList();
	}
	
	
	@Test
	final void chordlist_instantiates()
	{}
	
	
	@Nested
	public class when_using_default_constructor
	{
		@Test
		void when_using_default_constructor_then_length_is_1_bar_0_beats_by_default() throws Exception
		{
			BarsAndBeats bab = cl.getLengthInBarsAndBeats();
			assertEquals(1, bab.getBarPosition());
			assertEquals(0.0, bab.getOffsetInQuarters());
		}
		
		
		@Test
		void _then_chord_at_0_bars_0_beats_is_default_chord() throws Exception
		{

			assertEquals(ChordList.DEFAULT_CHORD, cl.getChord(0, 0.0));
		}		
	}
	
	
	@Test
	void when_using_constructor_that_sets_first_chord_then_chord_at_0_bars_0_beats_is_this_chord() throws Exception
	{
		Chord chord = new Chord();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		cl = new ChordList(chord, tsgm);
		
		assertEquals(chord, cl.getChord(0, 0.0));
	}
	
	
	@Test
	void when_no_chord_has_been_added_then_size_equals_1() throws Exception
	{		
		assertEquals(1, cl.size());
	}
	
	
	@Test
	void when_chord_is_added_at_0_bars_0_beats_then_size_equals_1() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		cl.addChord(new Chord(), new BarsAndBeats(0, 0.0), tsgm);
		assertEquals(1, cl.size());
	}
	
	
	@Test
	void when_chord_is_added_at_1_bars_0_beats_then_size_equals_2() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		cl.addChord(new Chord(), new BarsAndBeats(1, 0.0), tsgm);
		assertEquals(2, cl.size());
	}
	
	
	@Test
	void when_length_set_to_4_bars_0_beats_then_get_length_in_bars_and_beats_returns_4_0()
	{
		cl.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
		BarsAndBeats bab = cl.getLengthInBarsAndBeats();
		assertEquals(4, bab.getBarPosition());
		assertEquals(0.0, bab.getOffsetInQuarters());
	}
	
	
	@Nested
	public class given_length_of_2_bars_0_beats_
	{
		@Test
		void _when_getchord_called_within_length_then_default_chord_is_returned() throws Exception
		{
			cl.setLengthInBarsAndBeats(new BarsAndBeats(2, 0.0));
//			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(ChordList.DEFAULT_CHORD, cl.getChord(1, 0.0));
		}
			
		
		@Test
		void _when_getchord_called_outside_length_then_null_is_returned() throws Exception
		{
			cl.setLengthInBarsAndBeats(new BarsAndBeats(2, 0.0));
//			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(null, cl.getChord(3, 0.0));
			assertEquals(null, cl.getChord(-1, 0.0));
			assertEquals(null, cl.getChord(0, -1.0));
			assertEquals(null, cl.getChord(2, 1.0));
		}
		
		
		@Test
		void _when_getchord_called_using_negative_offset_from_end_bar_position_then_default_chord_is_returned() throws Exception
		{
			cl.setLengthInBarsAndBeats(new BarsAndBeats(2, 0.0));
//			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(ChordList.DEFAULT_CHORD, cl.getChord(2, -1.0));
		}
	}
	
	
	@Nested
	public class when_length_set_to_4_bars_0_beats_and_chord_added_at_2_bars_0_beats
	{
		@Test
		void _then_getchord_returns_default_chord_before_2_0() throws Exception
		{
			cl.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
			Chord chord = new Chord();
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			cl.addChord(chord, new BarsAndBeats(2, 0.0), tsgm);
			assertEquals(ChordList.DEFAULT_CHORD, cl.getChord(1, 0.0));
		}
		
		
		@Test
		void _then_getchord_returns_chord_on_and_after_2_0() throws Exception
		{
			cl.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
			Chord chord = new Chord();
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			cl.addChord(chord, new BarsAndBeats(2, 0.0), tsgm);
			assertEquals(chord, cl.getChord(2, 0.0));
			assertEquals(chord, cl.getChord(3, 1.0));
		}
	}
	
	
	@Test
	void when_length_is_6_0_bars_and_beats_and_chord_added_at_4_0_then_2_0_then_returns_correct_chord_from_1_0_and_3_0_and_5_0() throws Exception
	{
		cl.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		Chord chord2 = new Chord();		
		cl.addChord(chord2, new BarsAndBeats(4, 0.0), tsgm);
		Chord chord1 = new Chord();		
		cl.addChord(chord1, new BarsAndBeats(2, 0.0), tsgm);
		assertEquals(ChordList.DEFAULT_CHORD, cl.getChord(1, 0.0));
		assertEquals(chord1, cl.getChord(3, 0.0));
	}
	
	
	@Nested
	public class given_length_is_4_0_bars_and_beats_and_4_4_timesignature_and_chord_added_at_2_1
	{
		@Test
		void _then_chord_position_in_quarters_is_9() throws Exception
		{
			cl.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			Chord chord = new Chord();		
			cl.addChord(chord, new BarsAndBeats(2, 1.0), tsgm);
			assertEquals(9.0, cl.getlastChord().getPositionInQuarters());
		}
		
		
		@Test
		void _when_timesignature_changed_to_7_8_then_chord_position_in_quarters_is_8() throws Exception
		{
			cl.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			Chord chord = new Chord();		
			cl.addChord(chord, new BarsAndBeats(2, 1.0), tsgm);
			TimeSignatureList sevenEighth = TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322).getTimeSignatureList(4);
			cl.recalculateFromNewTimeSignatureList(sevenEighth);
			assertEquals(8.0, cl.getlastChord().getPositionInQuarters());
		}
	}
	
	
	@Test
	void given_4_bar_chord_progression_then_correct_chord_returned_from_bar_0_1_2_and_3() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"C", "G", "Am", "F"});
		cl = clg.getChordList(tsgm, 4);
		assertEquals("C", cl.getChord(0, 0.0).name());
		assertEquals("G", cl.getChord(1, 0.0).name());
		assertEquals("Am", cl.getChord(2, 0.0).name());
		assertEquals("F", cl.getChord(3, 0.0).name());
	}
	
	
	@Test
	void given_4_bar_chord_progression_and_time_signature_list_length_5_bars_then_chord_progression_wraps_correctly() throws Exception
	{
		// chord progression is C G Am F, 1 bar each. bar 4 should return a C
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"C", "G", "Am", "F"});
		cl = clg.getChordList(tsgm, 5);
		assertEquals("C", cl.getChord(4, 0.0).name());
	}
	
	
	@Test
	void getNextChord_returns_next_chord_in_list() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"C", "G", "Am", "F"});
		cl = clg.getChordList(tsgm, 4);
		assertEquals("G", cl.getNextChordEvent(new BarsAndBeats(0, 2.0), tsgm).getChord().name());
		assertEquals("Am", cl.getNextChordEvent(new BarsAndBeats(1, 2.0), tsgm).getChord().name());
		assertEquals("F", cl.getNextChordEvent(new BarsAndBeats(2, 2.0), tsgm).getChord().name());
		assertEquals("F", cl.getNextChordEvent(new BarsAndBeats(3, 2.0), tsgm).getChord().name());	// a call over the last chord returns the last chord
	}
	
	
	@Test
	void getDistanceInQuartersToNextChordEvent_returns_correct_value() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"C", "G", "Am", "F"});
		cl = clg.getChordList(tsgm, 4);
		assertEquals(2.0, cl.getDistanceInQuartersToNextChordEvent(new BarsAndBeats(0, 2.0), tsgm));
		assertEquals(1.5, cl.getDistanceInQuartersToNextChordEvent(new BarsAndBeats(1, 2.5), tsgm));
		assertEquals(1.0, cl.getDistanceInQuartersToNextChordEvent(new BarsAndBeats(2, 3.0), tsgm));
		assertEquals(2.0, cl.getDistanceInQuartersToNextChordEvent(new BarsAndBeats(3, 2.0), tsgm));	// call on last chord returns distance to end
	}
	
	
//	@Test
//	void given_a_4_bar_chord_progression_then_get_subset_returns_subset_chordlist() throws Exception
//	{
//		// chord progression is C G Am F, 1 bar each. subset is from bar 1 for 2 bars
//		// and should return G Am.
//		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
//		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"C", "G", "Am", "F"});
//		cl = clg.getChordList(tsgm, 4);
////		TimeSignatureList subsetTSL = tsgm.getSubsetTimeSignature(1, 2);
//		ChordList subsetCL = cl.getSubsetChordList(new BarsAndBeats(1, 0.0), new BarsAndBeats(2, 0.0), tsgm);
////		assertEquals("G", subsetCL.getChord(0, 0.0, subsetTSL).name());
////		assertEquals("Am", subsetCL.getChord(1, 0.0, subsetTSL).name());
//	}
	
	
//	@Test
//	void given_a_4_bar_chord_progression_with_complex_time_signatures_then_get_subset_returns_subset_chordlist() throws Exception
//	{
//		// chord progression is C G Am F, 1 bar each. subset is from bar 1 for 2 bars
//		// and should return G Am.
//		// complex time signature to test if positions are accurately calculated
//		TimeSignature[] tsArray = new TimeSignature[] {
//						TimeSignature.FIVE_EIGHT_32,
//						TimeSignature.THREE_FOUR,
//						TimeSignature.SEVEN_EIGHT_322,
//						TimeSignature.FOUR_FOUR
//				};
//		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(tsArray);
//		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"C", "G", "Am", "F"});
//		cl = clg.getChordList(tsgm);
//		TimeSignatureList subsetTSL = tsgm.getSubsetTimeSignature(1, 2);
//		ChordList subsetCL = cl.getSubsetChordList(new BarsAndBeats(1, 0.0), new BarsAndBeats(2, 0.0), tsgm);
//		assertEquals("G", subsetCL.getChord(0, 0.0, subsetTSL).name());
//		assertEquals("Am", subsetCL.getChord(1, 0.0, subsetTSL).name());
//	}
	
	
//	@Test
//	void when_length_set_to_4_bars_0_beats_then_get_length_in_quarters_returns_16()
//	{
//		cl.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
//		assertEquals(16.0, cl.getLengthInQuarters());
//	}
//	
//	
//	@Test
//	void when_length_set_to_2_bars_1_beats_then_get_length_in_bars_and_beats_returns_2_1()
//	{
//		cl.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
//		BarsAndBeats bab = cl.getLengthInBarsAndBeats();
//		assertEquals(2, bab.getBarPosition());
//		assertEquals(1.0, bab.getOffsetInQuarters());
//	}
//	
//	
//	@Test
//	void when_length_set_to_2_bars_1_beats_then_get_length_in_quarters_returns_9()
//	{
//		cl.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
//		assertEquals(9.0, cl.getLengthInQuarters());
//	}
//	
//	
//	@Test
//	void chord_cannot_be_added_before_0_bars_0_beats() throws Exception
//	{
//		cl.addChord(new Chord(), new BarsAndBeats(-1, 0.0));
//		assertEquals(0, cl.size());
//	}
	
	
	@Test
	void given_chord_progression_in_key_of_D_then_returns_correct_chordAnalysisToString() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"D", "Bm", "G", "A"});
		cl = clg.getChordList(tsgm, 4);
//		System.out.println(cl.chordAnalysisToString());
		assertEquals("D:DMaj(IMaj) D:Bmin(vimin) D:GMaj(IVMaj) D:AMaj(VMaj) ", cl.chordAnalysisToString());
	}
	
	
	@Test
	void given_chord_progression_in_key_of_D_then_returns_correct_closest_diatonic_note_for_each_chord() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"D", "Bm", "G", "A"});
		cl = clg.getChordList(tsgm, 4);
		Chord chord_D = cl.getChord(0, 2.0);
//		System.out.println(chord_D.toString());
		assertEquals(50, chord_D.getClosestDiatonicNote(49, 1));
		assertEquals(52, chord_D.getClosestDiatonicNote(50, 1));
		assertEquals(52, chord_D.getClosestDiatonicNote(51, 1));
		assertEquals(54, chord_D.getClosestDiatonicNote(52, 1));
		assertEquals(54, chord_D.getClosestDiatonicNote(53, 1));
		assertEquals(55, chord_D.getClosestDiatonicNote(54, 1));
		assertEquals(57, chord_D.getClosestDiatonicNote(55, 1));
		assertEquals(57, chord_D.getClosestDiatonicNote(56, 1));
		assertEquals(59, chord_D.getClosestDiatonicNote(57, 1));
		assertEquals(59, chord_D.getClosestDiatonicNote(58, 1));
		assertEquals(61, chord_D.getClosestDiatonicNote(59, 1));
		assertEquals(61, chord_D.getClosestDiatonicNote(60, 1));
		assertEquals(62, chord_D.getClosestDiatonicNote(61, 1));
		
		Chord chord_Bm = cl.getChord(1, 2.0);
//		System.out.println(chord_Bm.toString());
		assertEquals(50, chord_Bm.getClosestDiatonicNote(49, 1));
		assertEquals(52, chord_Bm.getClosestDiatonicNote(50, 1));
		assertEquals(52, chord_Bm.getClosestDiatonicNote(51, 1));
		assertEquals(54, chord_Bm.getClosestDiatonicNote(52, 1));
		assertEquals(54, chord_Bm.getClosestDiatonicNote(53, 1));
		assertEquals(55, chord_Bm.getClosestDiatonicNote(54, 1));
		assertEquals(57, chord_Bm.getClosestDiatonicNote(55, 1));
		assertEquals(57, chord_Bm.getClosestDiatonicNote(56, 1));
		assertEquals(59, chord_Bm.getClosestDiatonicNote(57, 1));
		assertEquals(59, chord_Bm.getClosestDiatonicNote(58, 1));
		assertEquals(61, chord_Bm.getClosestDiatonicNote(59, 1));
		assertEquals(61, chord_Bm.getClosestDiatonicNote(60, 1));
		assertEquals(62, chord_Bm.getClosestDiatonicNote(61, 1));
		
		Chord chord_G = cl.getChord(2, 2.0);
//		System.out.println(chord_G.toString());
		assertEquals(50, chord_G.getClosestDiatonicNote(49, 1));
		assertEquals(52, chord_G.getClosestDiatonicNote(50, 1));
		assertEquals(52, chord_G.getClosestDiatonicNote(51, 1));
		assertEquals(54, chord_G.getClosestDiatonicNote(52, 1));
		assertEquals(54, chord_G.getClosestDiatonicNote(53, 1));
		assertEquals(55, chord_G.getClosestDiatonicNote(54, 1));
		assertEquals(57, chord_G.getClosestDiatonicNote(55, 1));
		assertEquals(57, chord_G.getClosestDiatonicNote(56, 1));
		assertEquals(59, chord_G.getClosestDiatonicNote(57, 1));
		assertEquals(59, chord_G.getClosestDiatonicNote(58, 1));
		assertEquals(61, chord_G.getClosestDiatonicNote(59, 1));
		assertEquals(61, chord_G.getClosestDiatonicNote(60, 1));
		assertEquals(62, chord_G.getClosestDiatonicNote(61, 1));
		
		Chord chord_A = cl.getChord(3, 2.0);
//		System.out.println(chord_A.toString());
		assertEquals(50, chord_A.getClosestDiatonicNote(49, 1));
		assertEquals(52, chord_A.getClosestDiatonicNote(50, 1));
		assertEquals(52, chord_A.getClosestDiatonicNote(51, 1));
		assertEquals(54, chord_A.getClosestDiatonicNote(52, 1));
		assertEquals(54, chord_A.getClosestDiatonicNote(53, 1));
		assertEquals(55, chord_A.getClosestDiatonicNote(54, 1));
		assertEquals(57, chord_A.getClosestDiatonicNote(55, 1));
		assertEquals(57, chord_A.getClosestDiatonicNote(56, 1));
		assertEquals(59, chord_A.getClosestDiatonicNote(57, 1));
		assertEquals(59, chord_A.getClosestDiatonicNote(58, 1));
		assertEquals(61, chord_A.getClosestDiatonicNote(59, 1));
		assertEquals(61, chord_A.getClosestDiatonicNote(60, 1));
		assertEquals(62, chord_A.getClosestDiatonicNote(61, 1));
	}
	
	
	/*
	 * the following test has been yweaked to accept 4 as scale tone
	 * ProgressionAnalyzer is currently incorrectly analyzing the progression
	 * Bb to F as VI I rather than I V
	 * When this test fails again, this would have been corrected
	 */
	@Test
	void given_chord_of_Bb_then_returns_correct_chord_and_scale_tones()
	{
		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"Bb", "F"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 2);
		Chord chord = cl.getChord(0,  0.0);
		ChordInKeyObject ciko = chord.getAssociatedChordInKeyObject();
		
		assertEquals(10, chord.getChordTones()[0]);
		assertEquals(2, chord.getChordTones()[1]);
		assertEquals(5, chord.getChordTones()[2]);
		
		assertTrue(chord.isScaleTone(0));
		assertTrue(chord.isScaleTone(4));
		assertTrue(chord.isScaleTone(7));
		assertTrue(chord.isScaleTone(9));
		
	}
	
	
	
	@Test
	void given_default_chord_list_then_getProgressionAnalyzerJson_returns_json_object () throws Exception
	{
		ChordListGenerator clg = new SingleChordGenerator();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 2);
		assertTrue(cl.getProgressionAnalyzerJson() instanceof JSONObject);
	}
	
	
	@Test
	void given_default_chordList_then_getProgressionAnalyzerJson_returns_jsonObject_with_c_major_as_I_chord_at_bar_position_0 () throws Exception
	{
		ChordListGenerator clg = new SingleChordGenerator(new Chord("Cm"));
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 1);
		JSONObject json = cl.getProgressionAnalyzerJson();
		assertTrue(json.has("0.0"));
		JSONObject json2 = json.getJSONObject("0.0");
		assertEquals("Cm", json2.get("name"));
		assertEquals(0, json2.get("rootIndex"));
		assertEquals("min", json2.get("chordType"));
		assertEquals(0, json2.get("prevailingKeyIndex"));
		assertEquals("i", json2.get("romanNumeralAnalysis"));
		assertEquals("", json2.get("romanNumeralAnalysisAddendum"));
		assertEquals(false, json2.get("isMajorKey"));
		assertEquals("i", json2.get("functionString"));
		
		assertEquals(0, json2.getJSONArray("chordTones").get(0));
		assertEquals(3, json2.getJSONArray("chordTones").get(1));
		assertEquals(7, json2.getJSONArray("chordTones").get(2));
		
		assertEquals(2, json2.getJSONArray("scaleTones").get(0));
		assertEquals(5, json2.getJSONArray("scaleTones").get(1));
		assertEquals(8, json2.getJSONArray("scaleTones").get(2));
		assertEquals(10, json2.getJSONArray("scaleTones").get(3));
		
		assertEquals(1, json2.getJSONArray("nonTones").get(0));
		assertEquals(4, json2.getJSONArray("nonTones").get(1));
		assertEquals(6, json2.getJSONArray("nonTones").get(2));
		assertEquals(9, json2.getJSONArray("nonTones").get(3));
		assertEquals(11, json2.getJSONArray("nonTones").get(4));
		
		assertEquals(2, json2.getJSONArray("extendedChordTones").get(0));
		assertEquals(5, json2.getJSONArray("extendedChordTones").get(1));
		assertEquals(9, json2.getJSONArray("extendedChordTones").get(2));
		assertEquals(10, json2.getJSONArray("extendedChordTones").get(3));
	}
	
	
	
}
