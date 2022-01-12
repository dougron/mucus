package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

class Mu_ChordListTests
{

	// chordlist stuff --------------------------------------------------------------------------
	
	
		@Test
		void parent_getChordListToString_returns_zero_length_string() throws Exception
		{
			Mu mu = new Mu("parent");
			assertEquals("", mu.getChordListToString());
		}
		
		
		@Test
		void parent_with_length_2_getChordListToString_returns_correct_string() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setLengthInBars(2);
			assertEquals("C_C_", mu.getChordListToString());
		}
		
		
		@Test
		void when_chord_list_generator_is_set_then_getChordListToString_returns_correct_string() throws Exception
		{
			Mu mu = new Mu("parent");		
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			mu.setLengthInBars(4);
			assertEquals("Am_C_Dm_E_", mu.getChordListToString());
		}
		
		
		@Test
		void when_chord_list_generator_is_set_then_getChordEventList_returns_correct_list_of_chord_events() throws Exception
		{
			Mu mu = new Mu("parent");		
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			mu.setLengthInBars(4);
			List<ChordEvent> ceList = mu.getChordEventList();
			assertEquals("Am", ceList.get(0).getChordName());
			assertEquals("C", ceList.get(1).getChordName());
			assertEquals("Dm", ceList.get(2).getChordName());
			assertEquals("E", ceList.get(3).getChordName());
			
			List<String> chordNames = ceList.stream()
					.map(ChordEvent::getChordName)
					.collect(Collectors.toList());
			assertEquals(chordNames, Arrays.asList("Am", "C", "Dm", "E"));
		}
		
		
//		@Test
//		void when_chord_list_generator_is_set_then_getChordAt_returns_correct_chord_for_given_position_in_bars_and_beats() throws Exception
//		{
//			Mu mu = new Mu("parent");		
//			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
//			mu.setLengthInBars(4);
//			assertEquals("Am", mu.getChordAt(new BarsAndBeats(0, 0.0)).getChordName());
//			assertEquals("C", ceList.get(1).getChordName());
//			assertEquals("Dm", ceList.get(2).getChordName());
//			assertEquals("E", ceList.get(3).getChordName());
//		}
		
		
		@Test
		void when_child_has_chordList_then_parent_getChordListToString_returns_correct_string() throws Exception
		{
			Mu mu = new Mu("parent");		
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			Mu child = new Mu("child");
			child.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Xdim", "Y7"}));
			child.setLengthInBars(2);
			mu.addMu(child, 2);
			assertEquals("Am_C_Xdim_Y7_", mu.getChordListToString());
		}
		
		
		@Test
		void given_parent_with_two_chord_per_bar_when_child_has_one_chord_per_bar_then_entire_scope_of_child_is_replaced_in_parent() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setChordListGenerator(new FloatBarChordProgression(2.0, new Object[] {0.0, "Am", 0.5, "G", 1.0, "F", 1.5, "E"}));
			mu.setLengthInBars(4);	
			Mu child = new Mu("child");				
			child.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Gb"}));
			child.setLengthInBars(2);
			mu.addMu(child, 2);
			assertEquals("Am_G_F_E_Db_Gb_", mu.getChordListToString());
		}
		
		
		// getChordAt stuff --------------------------------------------------------------------
		
		
		@Test
		void given_default_mu_with_length_4_then_getChordAt_within_range_returns_chord_of_C() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setLengthInBars(4);	
			assertEquals("C", mu.getChordAt(new BarsAndBeats(0, 0.0)).name());
			assertEquals("C", mu.getChordAt(new BarsAndBeats(3, 0.0)).name());
		}

		
		@Test
		void given_mu_with_chord_progression_and_length_4_then_getChordAt_within_range_returns_correct_chord() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			mu.setLengthInBars(4);	
			assertEquals("Am", mu.getChordAt(new BarsAndBeats(0, 0.0)).name());
			assertEquals("C", mu.getChordAt(new BarsAndBeats(1, 1.0)).name());
			assertEquals("Dm", mu.getChordAt(new BarsAndBeats(2, 2.0)).name());
			assertEquals("E", mu.getChordAt(new BarsAndBeats(3, 3.0)).name());
		}
		
		
		@Test
		void when_child_has_chord_progression_then_getChordAt_returns_correct_chords() throws Exception
		{
			Mu mu = new Mu("parent");		
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			Mu child = new Mu("child");
			child.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Xdim", "Y7"}));
			child.setLengthInBars(2);
			mu.addMu(child, 2);
			assertEquals("Am", mu.getChordAt(new BarsAndBeats(0, 0.0)).name());
			assertEquals("C", mu.getChordAt(new BarsAndBeats(1, 1.0)).name());
			assertEquals("Xdim", mu.getChordAt(new BarsAndBeats(2, 2.0)).name());
			assertEquals("Y7", mu.getChordAt(new BarsAndBeats(3, 3.0)).name());
		}
		
		
		@Test
		void when_child_has_chord_progression_then_child_getChordAt_returns_correct_chords() throws Exception
		{
			Mu mu = new Mu("parent");		
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			Mu child = new Mu("child");
			child.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Xdim", "Y7"}));
			child.setLengthInBars(2);
			mu.addMu(child, 2);
			assertEquals("Am", child.getChordAt(new BarsAndBeats(-2, 0.0)).name());
			assertEquals("C", child.getChordAt(new BarsAndBeats(-1, 1.0)).name());
			assertEquals("Xdim", child.getChordAt(new BarsAndBeats(0, 2.0)).name());
			assertEquals("Y7", child.getChordAt(new BarsAndBeats(1, 3.0)).name());
		}
		
		
		@Test
		void when_getChordAt_is_outside_the_scope_of_the_parent_then_returns_null() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setLengthInBars(4);	
			assertEquals(null, mu.getChordAt(new BarsAndBeats(5, 0.0)));
			assertEquals(null, mu.getChordAt(new BarsAndBeats(-5, 0.0)));
		}
		
		
		@Test
		void given_default_chord_progression_then_chord_tones_are_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setLengthInBars(4);	
			int[] chordTones = mu.getChordAt(new BarsAndBeats(1,  0.0)).getChordTones();
			assertEquals(0, chordTones[0]);
			assertEquals(4, chordTones[1]);
			assertEquals(7, chordTones[2]);
		}
		
		
		@Test
		void given_chord_progression_then_chord_tones_are_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			mu.setLengthInBars(4);	
			int[] chordTones = mu.getChordAt(new BarsAndBeats(0,  0.0)).getChordTones();
			assertEquals(9, chordTones[0]);
			assertEquals(0, chordTones[1]);
			assertEquals(4, chordTones[2]);
			
			int[] chordTones2 = mu.getChordAt(new BarsAndBeats(3,  0.0)).getChordTones();
			assertEquals(4, chordTones2[0]);
			assertEquals(8, chordTones2[1]);
			assertEquals(11, chordTones2[2]);
		}
		
		
		// get prevailing chord -------------------------------------------
		
		
		@Test
		void given_parent_with_chord_progression_then_get_prevailing_chord_on_children_returns_correct_chord() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			mu.setLengthInBars(4);
			
			Mu child1 = new Mu("child1");
			mu.addMu(child1, new BarsAndBeats(0, 2.5));
			assertEquals("Am", child1.getPrevailingChord().name());
			assertEquals(52, child1.getPrevailingChord().getClosestChordTone(49, 1));
			assertEquals(57, child1.getPrevailingChord().getClosestChordTone(49, 2));
			assertEquals(48, child1.getPrevailingChord().getClosestChordTone(49, -1));
			assertEquals(50, child1.getPrevailingChord().getClosestDiatonicNote(49, 1));
		}
		
		
		
		// isXXXTone stuff -----------------------------------------------------
		
		
		@Test
		void given_parent_with_chord_then_isChordTone_returns_correct_for_octave_of_notes() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "C", "Dm", "E"}));
			mu.setLengthInBars(4);
			
			Mu child1 = new Mu("child1");
			mu.addMu(child1, new BarsAndBeats(0, 2.5));
			
			child1.addMuNote(new MuNote(57, 64));	// a		
			assertTrue(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(58, 64));			
			assertFalse(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(59, 64));	// b	
			assertFalse(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(60, 64));	// c	
			assertTrue(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(61, 64));			
			assertFalse(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(62, 64));	// d		
			assertFalse(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(63, 64));			
			assertFalse(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(64, 64));	//e		
			assertTrue(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(65, 64));	// f		
			assertFalse(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(66, 64));			
			assertFalse(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(67, 64));	// g		
			assertFalse(child1.isChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(68, 64));			
			assertFalse(child1.isChordTone());
			
		}
		
		
		@Test
		void given_parent_with_chord_with_scale_tones_different_to_extended_tones_then_isExtendedChordTone_returns_correct_for_octave_of_notes() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Dm7b5", "G7", "Cm7"}));
			mu.setLengthInBars(4);
			
			Mu child1 = new Mu("child1");
			mu.addMu(child1, new BarsAndBeats(0, 2.5));
			
			child1.addMuNote(new MuNote(57, 64));	// a		
			assertFalse(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(58, 64));	// Bb
			assertTrue(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(59, 64));	// b	
			assertFalse(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(60, 64));	// c	
			assertFalse(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(61, 64));			
			assertFalse(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(62, 64));	// d		
			assertFalse(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(63, 64));			
			assertFalse(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(64, 64));	//e		
			assertTrue(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(65, 64));	// f		
			assertFalse(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(66, 64));			
			assertFalse(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(67, 64));	// g		
			assertTrue(child1.isExtendedChordTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(68, 64));			
			assertFalse(child1.isExtendedChordTone());
			
		}
		
		
		@Test
		void given_parent_with_chord_with_scale_tones_different_to_extended_tones_then_isScaleTone_returns_correct_for_octave_of_notes() throws Exception
		{
			Mu mu = new Mu("parent");
			mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Dm7b5", "G7", "Cm7"}));
			mu.setLengthInBars(4);
			
			Mu child1 = new Mu("child1");
			mu.addMu(child1, new BarsAndBeats(0, 2.5));
			
			child1.addMuNote(new MuNote(57, 64));	// a		
			assertFalse(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(58, 64));	// Bb
			assertTrue(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(59, 64));	// b	
			assertFalse(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(60, 64));	// c	
			assertFalse(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(61, 64));			
			assertFalse(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(62, 64));	// d		
			assertFalse(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(63, 64));			
			assertTrue(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(64, 64));	//e		
			assertFalse(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(65, 64));	// f		
			assertFalse(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(66, 64));			
			assertFalse(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(67, 64));	// g		
			assertTrue(child1.isScaleTone());
			child1.clearMuNotes();
			child1.addMuNote(new MuNote(68, 64));			
			assertFalse(child1.isScaleTone());			
		}
		
		
		
		
}
