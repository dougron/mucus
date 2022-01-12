package test.java.com.dougron.mucus.mu_framework.chord_list;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import DataObjects.combo_variables.IntAndString;
import StaticChordScaleDictionary.ChordToneName;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;

class Chord_Test
{

	@Test
	final void default_chord_gives_chord_tones_0_4_7()
	{
		Chord chord = new Chord();
		int[] chordTones = chord.getChordTones();
		assertEquals(0, chordTones[0]);
		assertEquals(4, chordTones[1]);
		assertEquals(7, chordTones[2]);
	}
	
	
	@Test
	final void Gb_chord_gives_chord_tones_6_10_1()
	{
		Chord chord = new Chord("Gb");
		int[] chordTones = chord.getChordTones();
		assertEquals(6, chordTones[0]);
		assertEquals(10, chordTones[1]);
		assertEquals(1, chordTones[2]);
	}
	
	
	@Test
	void non_chord_returns_no_chord_tones() throws Exception
	{
		Chord chord = new Chord("hjcgsb");
		int[] chordTones = chord.getChordTones();
		assertEquals(0, chordTones.length);
//		assertEquals(2, chordTones[1]);
//		assertEquals(6, chordTones[2]);
	}
	
	
	@Test
	void chord_with_non_identifiable_root_will_return_root_of_minus1() throws Exception
	{
		Chord chord = new Chord("X");		// format is consistent with a major chord, and will return interval structure as such but root will be -1
		int[] chordTones = chord.getChordTones();
		assertEquals(-1, chordTones[0]);
	}
	
	
	@Test
	void getClosestChordTone_works() throws Exception
	{
		Chord chord = new Chord("Am");
		assertEquals(60, chord.getClosestChordTone(59));
		assertEquals(57, chord.getClosestChordTone(55));
	}
	
	
//	@Test
//	void getClosestChordToneBelowExclusive_works() throws Exception
//	{
//		Chord chord = new Chord("Am");
//		assertEquals(57, chord.getClosestChordToneBelowExclusive(59));
//		assertEquals(52, chord.getClosestChordToneBelowExclusive(55));
//	}
	
	
	@Test
	void when_chord_is_C_and_contour_is_1_and_reference_pitch_is_from_55_to_59_then_closest_chord_tone_is_60() throws Exception
	{
		Chord chord = new Chord();
		assertEquals(60, chord.getClosestChordTone(55, 1));
		assertEquals(60, chord.getClosestChordTone(56, 1));
		assertEquals(60, chord.getClosestChordTone(57, 1));
		assertEquals(60, chord.getClosestChordTone(58, 1));
		assertEquals(60, chord.getClosestChordTone(59, 1));
		assertEquals(64, chord.getClosestChordTone(60, 1));
		assertEquals(64, chord.getClosestChordTone(61, 1));
	}
	
	
	@Test
	void when_chord_is_C_and_contour_is_minus_1_and_reference_pitch_is_from_56_to_60_then_closest_chord_tone_is_55() throws Exception
	{
		Chord chord = new Chord();
		assertEquals(52, chord.getClosestChordTone(55, -1));
		assertEquals(55, chord.getClosestChordTone(56, -1));
		assertEquals(55, chord.getClosestChordTone(57, -1));
		assertEquals(55, chord.getClosestChordTone(58, -1));
		assertEquals(55, chord.getClosestChordTone(59, -1));
		assertEquals(55, chord.getClosestChordTone(60, -1));
		assertEquals(60, chord.getClosestChordTone(61, -1));
	}
	
	
	@Test
	void when_chord_is_C_and_contour_is_0_and_reference_pitch_is_from_58_to_62_then_closest_chord_tone_is_60() throws Exception
	{
		Chord chord = new Chord();
		assertEquals(55, chord.getClosestChordTone(57, 0));
		assertEquals(60, chord.getClosestChordTone(58, 0));
		assertEquals(60, chord.getClosestChordTone(59, 0));
		assertEquals(60, chord.getClosestChordTone(60, 0));
		assertEquals(60, chord.getClosestChordTone(61, 0));
		assertEquals(60, chord.getClosestChordTone(62, 0));
		assertEquals(64, chord.getClosestChordTone(63, 0));
	}
	
	
	@Test
	void returns_correct_root_index_and_root_name_and_alteration() throws Exception
	{
		Chord chord = new Chord("Bb");
		assertEquals(10, chord.getRootIndex());
		IntAndString rootNameAndAlteration = chord.getRootNameAndAlteration(-2);
		assertEquals(-1, rootNameAndAlteration.i);
		assertEquals("B", rootNameAndAlteration.str);
	}
	
	
	@Test
	void chord_Dsharp_minor7_returns_correct_chord_name() throws Exception
	{
		Chord chord = new Chord("D#m7");
		IntAndString ias = chord.getRootNameAndAlteration(5);
		assertEquals("D", ias.str);
		assertEquals(1, ias.i);
	}
	
	
	@Test
	void chord_D_minor7_returns_correct_chord_name() throws Exception
	{
		Chord chord = new Chord("Dm7");
		IntAndString ias = chord.getRootNameAndAlteration(0);
		assertEquals("D", ias.str);
		assertEquals(0, ias.i);
	}
	
	
	@Test
	void when_chord_tones_are_limited_to_THIRD_FIFTH_SEVENTH_then_chord_Dm7_returns_false_for_D_and_true_for_F_A_C_as_chord_tone () throws Exception
	{
		Chord chord = new Chord("Dm7");
		ChordToneName[] includedChordTones = new ChordToneName[] {ChordToneName.THIRD, ChordToneName.FIFTH, ChordToneName.SEVENTH};
		assertThat(chord.isChordTone(62, includedChordTones )).isFalse();
		assertThat(chord.isChordTone(60, includedChordTones )).isTrue();
		assertThat(chord.isChordTone(57, includedChordTones )).isTrue();
		assertThat(chord.isChordTone(53, includedChordTones )).isTrue();
	}

}
