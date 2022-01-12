package test.java.com.dougron.mucus.mu_framework.chord_list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordListGenerator;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordListGeneratorFactory;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;


class ChordListGeneratorTest
{

	@Test
	final void single_chord_generator_instantiates()
	{
		ChordListGenerator clg = new SingleChordGenerator();
		assertNotNull(clg);
	}
	
	
	@Test
	void chord_list_generator_returns_a_chord_list() throws Exception
	{
		ChordListGenerator clg = new SingleChordGenerator();
		ChordList cl = clg.getChordList();
		assertTrue(cl instanceof ChordList);
	}
	
	
	@Test
	void chord_list_generator_with_no_construction_arguments_returns_chord_list_with_toString_equals_C_() throws Exception
	{
		ChordListGenerator clg = new SingleChordGenerator();
		ChordList cl = clg.getChordList();
		assertEquals("C_", cl.toString());
	}
	
	
	@Test
	void chord_list_generator_with_no_construction_arguments_and_length_of_4_bars_returns_chord_list_with_toString_equals_C_C_C_C_() throws Exception
	{
		ChordListGenerator clg = new SingleChordGenerator();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 4);
		assertEquals("C_C_C_C_", cl.chordsToString());
	}
	
	
	@Test
	void chord_list_generator_passed_a_string_array_will_convert_it_to_a_chord_progression() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = new ChordList(new String[] {"Am", "C", "Dm", "poopy"}, tsgm);
		assertEquals("Am_C_Dm_poopy_", cl.chordsToString());
	}
	
	
	@Test
	void chords_added_out_of_sequence_will_be_sorted_by_timeline_position() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = new ChordList();
		cl.addChord(new Chord("Am"), new BarsAndBeats(2, 0.0), tsgm);
		cl.addChord(new Chord("F"), new BarsAndBeats(3, 0.0), tsgm);
		cl.addChord(new Chord("G"), new BarsAndBeats(1, 0.0), tsgm);
		cl.addChord(new Chord("C"), new BarsAndBeats(0, 0.0), tsgm);
		assertEquals("C_G_Am_F_", cl.chordsToString());
	}
	
	
	@Test
	void given_chordlist_of_4_bars_of_C_when_replacing_chord_at_bar_position_2_with_Gm_then_chordstostring_equals_C_C_Gm_C_() throws Exception
	{
		ChordListGenerator clg = new SingleChordGenerator();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 4);
		cl.addChord(new Chord("Gm"), new BarsAndBeats(2, 0.0), tsgm);
		assertEquals("C_C_Gm_C_", cl.chordsToString());
	}
	
	
	@Test
	void given_chordlist_of_4_bars_of_C_when_placing_chord_at_bar_position_2_bars_2_beats_with_Gm_then_chordstostring_equals_C_C_C_Gm_C_() throws Exception
	{
		ChordListGenerator clg = new SingleChordGenerator();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 4);
		cl.addChord(new Chord("Gm"), new BarsAndBeats(2, 2.0), tsgm);
		assertEquals("C_C_C_Gm_C_", cl.chordsToString());
	}
	
	
//	@Test
//	void given_chordlist_of_6_bars_of_Dm_when_replacing_chords_with_another_chordlist_Gm_length_2_from_bar_2_then_chordsToString_equals_Dm_Dm_Gm_Gm_Dm_Dm() throws Exception
//	{
//		ChordListGenerator clg1 = new SingleChordGenerator(new Chord("Dm"));
//		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
//		ChordList cl1 = clg1.getChordList(tsgm, 6);
//		
//		ChordListGenerator clg2 = new SingleChordGenerator(new Chord("Gm"));
//		TimeSignatureList tsl2 = TimeSignatureListGeneratorFactory.getGenerator().getTimeSignatureList(2);
//		ChordList cl2 = clg2.getChordList(tsl2);
//		
//		cl1.addChordList(cl2, new BarsAndBeats(2, 0.0), tsl1);
//	}

	@Test
	void single_chord_list_generator_returns_correct_parameterObjectArray() throws Exception
	{
		ChordListGenerator clg = new SingleChordGenerator(new Chord("Ebm"));
		Object[] arr = clg.getParameterObjectArray();
		assertEquals("SingleChordGenerator", arr[0]);
		assertEquals("Ebm", arr[1]);
	}
	
	
	@Test
	void parameterObjectArray_from_new_single_chord_list_generator_is_same_as_original() throws Exception
	{
		ChordListGenerator clg1 = new SingleChordGenerator(new Chord("Ebm"));
		Object[] arr1 = clg1.getParameterObjectArray();
		ChordListGenerator clg2 = ChordListGeneratorFactory.getGenerator(arr1);
		Object[] arr2 = clg2.getParameterObjectArray();
		assertEquals(arr1.length, arr2.length);
		assertEquals(arr1[0], arr2[0]);
		assertEquals(arr1[1], arr2[1]);
	}
	
	
	@Test
	void simple_even_chord_progression_generator_returns_correct_parameterObjectArray() throws Exception
	{
		ChordListGenerator clg = new SimpleEvenChordProgression(new String[] {"Ebm", "Db", "B7", "E9"});
		Object[] arr = clg.getParameterObjectArray();
		assertEquals("SimpleEvenChordProgression", arr[0]);
		assertEquals("Ebm", arr[1]);
		assertEquals("Db", arr[2]);
		assertEquals("B7", arr[3]);
		assertEquals("E9", arr[4]);
	}
	
	
	@Test
	void parameterObjectArray_from_new_simple_even_chord_progression_generator_is_same_as_original() throws Exception
	{
		ChordListGenerator clg1 = new SimpleEvenChordProgression(new String[] {"Ebm", "Db", "B7", "E9"});
		Object[] arr1 = clg1.getParameterObjectArray();
		ChordListGenerator clg2 = ChordListGeneratorFactory.getGenerator(arr1);
		Object[] arr2 = clg2.getParameterObjectArray();
		assertEquals(arr1.length, arr2.length);
		assertEquals(arr1[0], arr2[0]);
		assertEquals(arr1[1], arr2[1]);
		assertEquals(arr1[2], arr2[2]);
		assertEquals(arr1[3], arr2[3]);
		assertEquals(arr1[4], arr2[4]);
	}
	
	
	@Test
	void float_bar_chord_progression_generator_returns_correct_parameterObjectArray() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(2.0, new Object[] {0.0, "Ebm", 0.5, "Db", 1.0, "B7", 1.5, "E9"});
		Object[] arr = clg.getParameterObjectArray();
		assertEquals("FloatBarChordProgression", arr[0]);
		assertEquals(2.0, arr[1]);
		assertEquals(0.0, arr[2]);
		assertEquals("Ebm", arr[3]);
		assertEquals(0.5, arr[4]);
		assertEquals("Db", arr[5]);
		assertEquals(1.0, arr[6]);
		assertEquals("B7", arr[7]);
		assertEquals(1.5, arr[8]);
		assertEquals("E9", arr[9]);
	}
	
	
	@Test
	void parameterObjectArray_from_new_float_bar_chord_progression_generator_is_same_as_original() throws Exception
	{
		ChordListGenerator clg1 = new FloatBarChordProgression(2.0, new Object[] {0.0, "Ebm", 0.5, "Db", 1.0, "B7", 1.5, "E9"});
		Object[] arr1 = clg1.getParameterObjectArray();
		ChordListGenerator clg2 = ChordListGeneratorFactory.getGenerator(arr1);
		Object[] arr2 = clg2.getParameterObjectArray();
		assertEquals(arr1.length, arr2.length);
		assertEquals(arr1[0], arr2[0]);
		assertEquals(arr1[1], arr2[1]);
		assertEquals(arr1[2], arr2[2]);
		assertEquals(arr1[3], arr2[3]);
		assertEquals(arr1[4], arr2[4]);
		assertEquals(arr1[5], arr2[5]);
		assertEquals(arr1[6], arr2[6]);
		assertEquals(arr1[7], arr2[7]);
		assertEquals(arr1[8], arr2[8]);
		assertEquals(arr1[9], arr2[9]);
	}
	
}
