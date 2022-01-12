package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import DataObjects.combo_variables.IntAndString;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;


class Mu_Test
{

	@Test
	final void mu_instantiates()
	{
		Mu mu = new Mu("parent");
		assertNotNull(mu);
	}
	
	
	@Test
	void mu_returns_name() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals("parent", mu.getName());
	}
	
	
	@Test
	void mu_getParent_returns_null() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals(null, mu.getParent());
	}
	
	
	
	// position stuff ------------------------------------------------------------------------
	
	
	public void given_mu_without_parent_then_positionInBars_equals_0() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals(0, mu.getPositionInBars());
	}
	
	
	@Test
	void given_mu_without_parent_then_positionInQuarters_equals_0() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals(0.0, mu.getPositionInQuarters());
	}
	
	
	@Test
	void child_has_position_of_0_before_being_added_to_parent_and_position_1_when_added_at_position_1() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		assertEquals(0, child.getPositionInBars());
		mu.addMu(child, 1);
		assertEquals(1, child.getPositionInBars());
	}
		
	
	@Test
	void given_parent_with_child_at_position_1_length_2_when_sibling_added_to_end_of_child_then_position_of_sibling_equals_3_bars() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 1);
		Mu sibling = new Mu("child2");
		mu.addMuToEndOfSibling(sibling, 0, child);
		assertEquals(3, sibling.getPositionInBars());
	}
	
	
	@Test
	void given_parent_with_child_at_position_1_length_2_when_sibling_added_to_end_of_child_then_position_of_sibling_equals_12_quarters() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 1);
		Mu sibling = new Mu("child2");
		mu.addMuToEndOfSibling(sibling, 0, child);
		assertEquals(12.0, sibling.getPositionInQuarters());
	}
	
	
	@Test
	void when_child_added_at_bar_position_1_then_positionInQuarters_equals_4() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 1);
		assertEquals(4.0, child.getPositionInQuarters());
	}
	
	
	@Test
	void given_complex_time_signature_when_child_added_at_bar_position_1_then_positionInQuarters_equals_lengthInQuarters_of_bar_0() throws Exception
	{
		Mu mu = new Mu("parent");
		TimeSignature[] timeSignatures = new TimeSignature[]
				{
						TimeSignature.THREE_FOUR,
						TimeSignature.FOUR_FOUR
				};
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(timeSignatures));
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 1);
		assertEquals(3.0, child.getPositionInQuarters());
	}
	
	
//	@Test
//	void child_positioned_in_bars_and_beats_can_only_be_added_to_a_fixed_length_parent() throws Exception
//	{
//		// this could change but am rushing towards first CI event (14 Oct 2020)
//		Mu mu = new Mu("parent");
//		Mu child = new Mu("child");
//		mu.addMu(child, new BarsAndBeats(0, 2.0));
//		assertEquals(0, mu.getNumberOfMus());
//		mu.setLengthInBars(2);
//		mu.addMu(child, new BarsAndBeats(0, 2.0));
//		assertEquals(1, mu.getNumberOfMus());
//	}
	
	
	@Test
	void child_positioned_in_bars_and_beats_has_by_default_length_in_quarters_equal_to_length_in_quarters_default() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		mu.setLengthInBars(2);
		mu.addMu(child, new BarsAndBeats(0, 2.0));
		assertEquals(Mu.DEFAULT_FIXED_LENGTH_IN_QUARTERS, child.getLengthInQuarters());
	}
	
	
	@Test
	void child_positioned_with_bars_and_beat_0_2_returns_getPositionInBarsAndBeats_of_0_2() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(4);
		Mu child = new Mu("child");
		mu.addMu(child, new BarsAndBeats(1, 2.0));
		assertEquals(1, child.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(2.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
	}
	
	
	
	// global position in quarters - a more rigorous approach.
	
	
	
	@Nested
	public class child_at_2_bars
	{
		@Test
		void _and_grandchild_at_1_bars() throws Exception
		{
			Mu mu = new Mu("parent");
			TimeSignature[] tsArr = new TimeSignature[] {
					TimeSignature.FIVE_EIGHT_32,
					TimeSignature.SEVEN_EIGHT_322,
					TimeSignature.THREE_FOUR,
					TimeSignature.SIX_EIGHT
			};
			mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			mu.addMu(child, 2);
			child.addMu(grandchild, 1);
			assertEquals(9.0, grandchild.getGlobalPositionInQuarters());
		}
		
		
		@Test
		void _and_grandchild_at_1_bars_1_beat() throws Exception
		{
			Mu mu = new Mu("parent");
			TimeSignature[] tsArr = new TimeSignature[] {
					TimeSignature.FIVE_EIGHT_32,
					TimeSignature.SEVEN_EIGHT_322,
					TimeSignature.THREE_FOUR,
					TimeSignature.SIX_EIGHT
			};
			mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			mu.addMu(child, 2);
			child.addMu(grandchild, new BarsAndBeats(1, 1.0));
			assertEquals(10.0, grandchild.getGlobalPositionInQuarters());
		}
		
		
		@Test
		void _and_grandchild_at_5_5_quarters() throws Exception
		{
			Mu mu = new Mu("parent");
			TimeSignature[] tsArr = new TimeSignature[] {
					TimeSignature.FIVE_EIGHT_32,
					TimeSignature.SEVEN_EIGHT_322,
					TimeSignature.THREE_FOUR,
					TimeSignature.SIX_EIGHT
			};
			mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			mu.addMu(child, 2);
			child.addMu(grandchild, 5.5);
			assertEquals(11.5, grandchild.getGlobalPositionInQuarters());
		}
	}
	
	
	@Nested
	public class child_at_2_bars_1pnt5_beats
	{
		@Test
		void _and_grandchild_at_1_bars() throws Exception
		{
			Mu mu = new Mu("parent");
			TimeSignature[] tsArr = new TimeSignature[] {
					TimeSignature.FIVE_EIGHT_32,
					TimeSignature.SEVEN_EIGHT_322,
					TimeSignature.THREE_FOUR,
					TimeSignature.SIX_EIGHT
			};
			mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			mu.addMu(child, new BarsAndBeats(2, 1.5));
			child.addMu(grandchild, 1);
			assertEquals(9.0, grandchild.getGlobalPositionInQuarters());
		}
		
		
		@Test
		void _and_grandchild_at_1_bars_1_beat() throws Exception
		{
			Mu mu = new Mu("parent");
			TimeSignature[] tsArr = new TimeSignature[] {
					TimeSignature.FIVE_EIGHT_32,
					TimeSignature.SEVEN_EIGHT_322,
					TimeSignature.THREE_FOUR,
					TimeSignature.SIX_EIGHT
			};
			mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			mu.addMu(child, new BarsAndBeats(2, 1.5));
			child.addMu(grandchild, new BarsAndBeats(1, 1.0));
			assertEquals(10.0, grandchild.getGlobalPositionInQuarters());
		}
		
		
		@Test
		void _and_grandchild_at_5_5_quarters() throws Exception
		{
			Mu mu = new Mu("parent");
			TimeSignature[] tsArr = new TimeSignature[] {
					TimeSignature.FIVE_EIGHT_32,
					TimeSignature.SEVEN_EIGHT_322,
					TimeSignature.THREE_FOUR,
					TimeSignature.SIX_EIGHT
			};
			mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			mu.addMu(child, new BarsAndBeats(2, 1.5));
			child.addMu(grandchild, 5.5);
			assertEquals(13.0, grandchild.getGlobalPositionInQuarters());
		}
	}
	
	
	
	// length stuff ---------------------------------------------------------------------------
	
	
	@Test
	void given_mu_without_parent_then_lengthInBars_equals_0() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals(0, mu.getLengthInBars());
	}
	
	
	@Test
	void given_mu_without_parent_then_lengthInQuarters_equals_0() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals(0.0, mu.getLengthInQuarters());
	}
	
	
	@Test
	void given_parent_mu_when_length_set_to_2_bars_then_lengthInBars_returns_2() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(2);
		assertEquals(2, mu.getLengthInBars());
	}
	
	
	@Test
	void given_parent_mu_when_length_set_to_2_bars_then_lengthInQuarters_returns_8() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(2);
		assertEquals(8.0, mu.getLengthInQuarters());
	}
	
	
	@Test
	void given_parent_mu_with_complex_timeSignature_when_length_set_to_2_bars_then_lengthInQuarters_returns_sum_of_first_2_bars_in_quarters() throws Exception
	{
		Mu mu = new Mu("parent");
		TimeSignature[] timeSignatures = new TimeSignature[]
				{
						TimeSignature.THREE_FOUR,
						TimeSignature.FOUR_FOUR
				};
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(timeSignatures));
		mu.setLengthInBars(2);
		assertEquals(7.0, mu.getLengthInQuarters());
	}
	
	
	@Test
	void given_parent_mu_when_length_set_to_3_bars_then_lengthInBars_returns_3() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(3);
		assertEquals(3, mu.getLengthInBars());
	}
	
	
	@Test
	void given_parent_with_child_length_2_at_position_1_then_parent_length_equals_3() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 1);
		assertEquals(3, mu.getLengthInBars());
	}
	
	
	@Test
	void given_parent_with_child_at_position_1_length_2_when_child_length_3_added_to_end_of_sibling_then_parent_length_equals_6() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 1);
		Mu child2 = new Mu("child2");
		child2.setLengthInBars(3);
		mu.addMuToEndOfSibling(child2, 0, child);
		assertEquals(6, mu.getLengthInBars());
	}

	
	@Test
	void given_parent_with_child_at_position_1_length_2_and_child_length_3_added_to_end_of_sibling_when_2nd_child_changed_to_children_length_model_then_parent_length_equals_3() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 1);
		Mu child2 = new Mu("child2");
		child2.setLengthInBars(3);
		mu.addMuToEndOfSibling(child2, 0, child);
		child2.setToGetLengthFromChildren();
		assertEquals(3, mu.getLengthInBars());
	}
	
	
	@Test
	void when_child_at_1_and_grandchild_at_2_then_parent_length_equals_3() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		Mu grandchild = new Mu("grandchild");
		child.addMu(grandchild, 2);
		mu.addMu(child, 1);
		assertEquals(3, mu.getLengthInBars());
	}
	
	
	@Test
	void when_child_fixed_length_is_changed_then_this_is_reflected_in_the_parent_length() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		mu.addMu(child, 1);
		assertEquals(1, mu.getLengthInBars());
		child.setLengthInBars(2);
		assertEquals(3, mu.getLengthInBars());
		child.setToGetLengthFromChildren();
		Mu grandchild = new Mu("grandchild");
		grandchild.setLengthInBars(3);
		child.addMu(grandchild, 2);
		assertEquals(6, mu.getLengthInBars());
	}

	
	@Test
	void given_mu_with_lengthInQuarters_set_to_5_then_getLengthInQuarters_equals_5() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInQuarters(5.0);
		assertEquals(5.0, mu.getLengthInQuarters());
	}

	
	@Test
	void given_mu_with_lengthInQuarters_set_to_5_then_getLengthInBars_equals_minus_1() throws Exception
	{
		// a length in quarters mu cannot have a a length in bars at this point due to the co-dependancy of length and tme signature
		Mu mu = new Mu("parent");
		mu.setLengthInQuarters(5.0);
		assertEquals(2, mu.getLengthInBars());
	}
	
	
	@Test
	void given_parent_with_length_model_from_children_with_child_positioned_in_bars_and_beats_then_length_in_bars_is_next_full_bar() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		mu.addMu(child, new BarsAndBeats(2, 1.0));
		assertEquals(3, mu.getLengthInBars());
	}
	
	
	
	// end position stuff -----------------------------------------------------------------------------
	
	
	@Test
	void when_mu_has_length_4_bars_then_getEndPositionInBars_equals_4() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(4);
		assertEquals(4, mu.getEndPositionInBars());
	}
	
	
	@Test
	void given_mu_with_child_length_3_at_2_then_getEndPositionInBars_equals_5() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(3);
		mu.addMu(child, 2);
		assertEquals(5, mu.getEndPositionInBars());
	}
	
	
	@Test
	void given_mu_with_child_length_3_at_2_then_getEndPositionInBars_on_child_equals_5() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(3);
		mu.addMu(child, 2);
		assertEquals(5, child.getEndPositionInBars());
	}
	
	
	@Test
	void given_mu_with_child_at_1_and_grandchild_at_2_length_3_then_grandchild_getEndPositionInBars_equals_5() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		Mu grandchild = new Mu("grandchild");
		grandchild.setLengthInBars(3);
		mu.addMu(child, 1);
		child.addMu(grandchild, 2);
		assertEquals(5, grandchild.getEndPositionInBars());
	}
	

	
	// time signature stuff ------------------------------------------------------------------
	
	
	@Test
	void parent_getTimeSignaturesToString_returns_zero_length_string() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals("", mu.getTimeSignaturesToString());
	}
	
	
	@Test
	void parent_with_length_2_getTimeSignaturesToString_returns_correct_string() throws Exception
	{
		// correct string = "4/4__4/4__" 2 bars of default timesignature
		Mu mu = new Mu("parent");
		mu.setLengthInBars(2);
		assertEquals("4/4__4/4__", mu.getTimeSignaturesToString());
	}
	
	
	@Test
	void parent_with_time_signature_generator_returns_hasTimeSignatureGenerator_true_and_the_opposite() throws Exception
	{
		Mu mu = new Mu("parent");
		assertTrue(!mu.hasTimeSignatureGenerator());
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.FIVE_EIGHT_32));
		assertTrue(mu.hasTimeSignatureGenerator());
	}
	
	
	@Test
	void child_with_time_signature_generator_shows_in_parent_getTimeSignaturesToString() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		child.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		mu.addMu(child, 1);
		assertEquals("4/4__3/4__3/4__", mu.getTimeSignaturesToString());
	}
	
	
	@Test
	void grandchild_with_time_signature_generator_shows_in_parent_and_child_getTimeSignaturesToString() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		Mu grandchild = new Mu("grandchild");
		mu.addMu(child, 2);
		
		child.addMu(grandchild, 1);
		grandchild.setLengthInBars(2);
		grandchild.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		
		assertEquals("4/4__4/4__4/4__3/4__3/4__", mu.getTimeSignaturesToString());
		assertEquals("4/4__3/4__3/4__", child.getTimeSignaturesToString());
	}
	
	
	@Test
	void sibling_with_time_signature_generator_reflects_in_parent_getTimeSignature_toString_when_child_length_changes() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		Mu grandchild = new Mu("grandchild");
		mu.addMu(child, 2);
		child.addMu(grandchild, 1);
		grandchild.setLengthInBars(2);
		Mu sibling = new Mu("sibling");
		sibling.setLengthInBars(2);
		sibling.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		mu.addMuToEndOfSibling(sibling, 0, child);
		assertEquals(5, sibling.getGlobalPositionInBars());
		assertEquals("4/4__4/4__4/4__4/4__4/4__3/4__3/4__", mu.getTimeSignaturesToString());
		grandchild.setPositionInBars(2);
		assertEquals(6, sibling.getGlobalPositionInBars());
		assertEquals("4/4__4/4__4/4__4/4__4/4__4/4__3/4__3/4__", mu.getTimeSignaturesToString());
	}


	@Test
	void time_signature_before_start_of_piece_is_assumed_to_follow_the_pattern_of_the_time_signature_generator() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(4);
		TimeSignature[] tsArr = new TimeSignature[] {
				TimeSignature.FIVE_EIGHT_32,
				TimeSignature.SEVEN_EIGHT_322,
				TimeSignature.THREE_FOUR,
				TimeSignature.SIX_EIGHT
		};
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
		assertEquals(TimeSignature.FIVE_EIGHT_32, mu.getTimeSignature(-4));
		assertEquals(TimeSignature.SEVEN_EIGHT_322, mu.getTimeSignature(-3));
		assertEquals(TimeSignature.THREE_FOUR, mu.getTimeSignature(-2));
		assertEquals(TimeSignature.SIX_EIGHT, mu.getTimeSignature(-1));
	}
	
		
	@Test
	void child_positioned_in_bars_and_beats_is_not_allowed_time_signature() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(4);
		Mu child = new Mu("child");
		child.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.FIVE_EIGHT_32));
		assertTrue(child.hasTimeSignatureGenerator());
		mu.addMu(child, new BarsAndBeats(0, 2.0));
		assertFalse(child.hasTimeSignatureGenerator());
	}
	
	
	@Test
	void getTimesignature_called_on_child_gets_correct_time_signature() throws Exception
	{
		Mu mu = new Mu("parent");
		TimeSignature[] tsArr = new TimeSignature[] {
				TimeSignature.FIVE_EIGHT_32,
				TimeSignature.SEVEN_EIGHT_322,
				TimeSignature.THREE_FOUR,
				TimeSignature.SIX_EIGHT
		};
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
		Mu child = new Mu("child");
		mu.addMu(child, 2);
		child.setLengthInBars(2);
		assertEquals(TimeSignature.THREE_FOUR, child.getTimeSignature(0));
		assertEquals(TimeSignature.SEVEN_EIGHT_322, child.getTimeSignature(-1));
		assertEquals(TimeSignature.FIVE_EIGHT_32, child.getTimeSignature(-2));
		assertEquals(TimeSignature.SIX_EIGHT, child.getTimeSignature(-3));
		assertEquals(TimeSignature.SIX_EIGHT, child.getTimeSignature(1));
		assertEquals(TimeSignature.FIVE_EIGHT_32, child.getTimeSignature(2));
	}
	
	
	
	// MuAnnotation stuff ------------------------------------------------------------------------
	
	
	@Test
	void by_default_has_0_muAnnotations() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals(0, mu.getNumberOfMuAnnotations());
	}
	
	
	@Test
	void when_mu_annotation_is_added_then_has_1_muAnnotation() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.addMuAnnotation(new MuAnnotation("hello", MuAnnotation.TextPlacement.PLACEMENT_ABOVE));
		assertEquals(1, mu.getNumberOfMuAnnotations());
	}
	
	
	@Test
	void when_mu_annotation_is_added_then_getMuAnnotations_returns_list_of_MuAnnotation() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.addMuAnnotation(new MuAnnotation("hello", MuAnnotation.TextPlacement.PLACEMENT_ABOVE));
		assertTrue(mu.getMuAnnotations() instanceof List<?>);
	}

	
	
	// keySignatureMap stuff --------------------------------------------------------------------------
	
	
	@Test
	void mu_returns_default_keySignatureMap() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals(0, mu.getKeySignatureMap().getKey());
	}
	
	
	@Test
	void when_keySignatureMap_set_then_getKeySignatureMap_return_that_key() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setXMLKey(1);
		assertEquals(1, mu.getKeySignatureMap().getKey());
	}
	
	
	@Test
	void child_returns_correct_result_for_isDiatonicNoteInXMLKey_for_an_octave_of_semitones() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		mu.addMu(child, new BarsAndBeats(1, 1.5));
		assertTrue(child.isDiatonicNoteInXMLKey(36));		//c
		assertFalse(child.isDiatonicNoteInXMLKey(37));
		assertTrue(child.isDiatonicNoteInXMLKey(38));		//d
		assertFalse(child.isDiatonicNoteInXMLKey(39));
		assertTrue(child.isDiatonicNoteInXMLKey(40));		//e
		assertTrue(child.isDiatonicNoteInXMLKey(41));		//f
		assertFalse(child.isDiatonicNoteInXMLKey(42));
		assertTrue(child.isDiatonicNoteInXMLKey(43));		//g
		assertFalse(child.isDiatonicNoteInXMLKey(44));
		assertTrue(child.isDiatonicNoteInXMLKey(45));		//a
		assertFalse(child.isDiatonicNoteInXMLKey(46));
		assertTrue(child.isDiatonicNoteInXMLKey(47));		//b
	}
	
	
	@Test
	void when_xmlKey_of_parent_set_to_3_flats_then_child_returns_correct_result_for_isDiatonicNoteInXMLKey_for_an_octave_of_semitones() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setXMLKey(-3);						// xmlKey of Eb
		Mu child = new Mu("child");
		mu.addMu(child, new BarsAndBeats(1, 1.5));
		assertTrue(child.isDiatonicNoteInXMLKey(36));		//c
		assertFalse(child.isDiatonicNoteInXMLKey(37));
		assertTrue(child.isDiatonicNoteInXMLKey(38));		//d
		assertTrue(child.isDiatonicNoteInXMLKey(39));		//eb
		assertFalse(child.isDiatonicNoteInXMLKey(40));		//e
		assertTrue(child.isDiatonicNoteInXMLKey(41));		//f
		assertFalse(child.isDiatonicNoteInXMLKey(42));
		assertTrue(child.isDiatonicNoteInXMLKey(43));		//g
		assertTrue(child.isDiatonicNoteInXMLKey(44));		// ab
		assertFalse(child.isDiatonicNoteInXMLKey(45));		//a
		assertTrue(child.isDiatonicNoteInXMLKey(46));
		assertFalse(child.isDiatonicNoteInXMLKey(47));		//b
	}

	
	
	// MuNote stuff --------------------------------------------------------------------------------------
	
	
	@Test
	void return_empty_list_of_MuNotes() throws Exception
	{
		Mu mu = new Mu("parent");
		assertTrue(mu.getMuNotes() instanceof List<?>);
	}
	
	
	@Nested	
	public class mu_which_is_not_a_tuplet_print_container
	{
		
		@Test
		void _returns_tuplet_numerator_of_0() throws Exception
		{
			Mu mu = new Mu("parent");
			assertEquals(0, mu.getTupletNumerator());
		}

		
		@Test
		void _returns_tuplet_denominator_of_0() throws Exception
		{
			Mu mu = new Mu("parent");
			assertEquals(0, mu.getTupletDenominator());
		}
	}
	
	
	@Test
	void when_mu_has_no_muNotes_then_getTopPitch_returns_0() throws Exception
	{
		Mu mu = new Mu("parent");
		assertEquals(0, mu.getTopPitch());
	}
	
	
	// chordList stuff --------------------------------------------------------------------------------
	
	
	
	
	// next/previous mus with notes -------------------------------------------------------------
	
	
	@Test
	void given_parent_with_children_with_notes_then_children_return_correct_next_and_previous_mu() throws Exception
	{
		Mu parent = new Mu("parent");
		
		Mu child1 = new Mu("child1");
		child1.addMuNote(new MuNote(44, 64));
		parent.addMu(child1, 1.0);
		
		Mu child2 = new Mu("child1");
		parent.addMu(child2, 2.0);
		
		Mu child3 = new Mu("child1");
		child3.addMuNote(new MuNote(44, 64));
		parent.addMu(child3, 3.0);
		
		Mu child4 = new Mu("child1");
		child4.addMuNote(new MuNote(44, 64));
		parent.addMu(child4, 4.0);
		
		parent.makePreviousNextMusWithNotes();
		assertEquals(null, parent.getPreviousMu());
		assertEquals(null, child1.getPreviousMu());
		assertEquals(null, child2.getPreviousMu());
		assertEquals(child1, child3.getPreviousMu());
		assertEquals(child3, child4.getPreviousMu());
		
		assertEquals(null, parent.getNextMu());
		assertEquals(child3, child1.getNextMu());
		assertEquals(null, child2.getNextMu());
		assertEquals(child4, child3.getNextMu());
		assertEquals(null, child4.getNextMu());
	}
	
	
	// intervals -----------------------------------------------------------------------------
	
	
	@Test
	void given_mu_with_note_45_then_getSemitoneInterval_returns_5_for_argumant_of_50() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addMuNote(new MuNote(45, 64));
		IntAndString ias = mu.getSemitoneInterval(50);
		assertEquals(5, ias.i);
		assertEquals("s+5", ias.str);
		
		ias = mu.getSemitoneInterval(47);
		assertEquals(2, ias.i);
		assertEquals("s+2", ias.str);
		
		ias = mu.getSemitoneInterval(41);
		assertEquals(-4, ias.i);
		assertEquals("s-4", ias.str);
	}
	
	
	@Test
	public void given_parent_with_chord_then_child_returns_correct_diatonic_step_count() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Bb", "F"}));	// note to future self: requires two chords or else the ProgressionAnalyzer reads the single as a HM5 dominant. Sigh.
		parent.setLengthInBars(2);
		
		Mu child = new Mu("child");
		child.addMuNote(new MuNote(60, 48));
		child.setLengthInQuarters(1.0);
		parent.addMu(child, new BarsAndBeats(0, 2.0));
		
		IntAndString ias = child.getDiatonicInterval(62);
		assertEquals(1, ias.i);
		assertEquals("d+1", ias.str);
		
		ias = child.getDiatonicInterval(58);
		assertEquals(-1, ias.i);
		assertEquals("d-1", ias.str);
	}
	
	
	// mu.round() tests-----------------------------------------------------------------------
	
	
	@Test
	void rounding_delivers_correct_result() throws Exception
	{
//		System.out.println(0.666666666667 * 3 / 2);
//		System.out.println(Mu.round(0.666666666667 * 3 / 2));
		assertEquals(1.0, Mu.round(0.666666666667 * 3 / 2));
		assertEquals(1.0, Mu.round(0.66666666667 * 3 / 2));
		assertEquals(1.0, Mu.round(0.6666666667 * 3 / 2));
		assertEquals(1.0, Mu.round(0.666666667 * 3 / 2));
	}
	
	
	// deepCopy --------------------------------------------------------------
	
	
	@Test
	void given_default_mu_then_deep_copy_content_test_does_not_fail() throws Exception
	{
		Mu mu = new Mu("mu");
		String str = mu.getDeepCopyContentString();
		assertTrue(str instanceof String);
	}
	
	
	@Test
	void given_default_mu_then_deepCopyContentString_of_deepCopy_is_equal() throws Exception
	{
		Mu mu = new Mu("mu");
		String str = mu.getDeepCopyContentString();
		Mu copy = mu.getDeepCopy();
		String copyString = copy.getDeepCopyContentString();
		assertEquals(str, copyString);
	}
	
	
	@Test
	void given_child_mu_at_bar_position_then_deepCopyContentString_of_deepCopy_is_equal() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		parent.addMu(child, 1);
		assertEquals(parent.getDeepCopyContentString(), parent.getDeepCopy().getDeepCopyContentString());
	}
	
	
	
	@Test
	void given_child_mu_at_quarters_position_with_notes_then_deepCopyContentString_of_deepCopy_is_equal() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		child.addMuNote(new MuNote(64, 64));
		child.setLengthInQuarters(2.0);
		parent.addMu(child, 1.0);
		assertEquals(parent.getDeepCopyContentString(), parent.getDeepCopy().getDeepCopyContentString());
	}
	
	
	// string generators for edit distance analysis
	
	
	@Test
	void given_child_with_note_64_at_2_bars_then_pitch_string_is_correct() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		child.addMuNote(new MuNote(64, 64));
		child.setLengthInQuarters(2.0);
		parent.addMu(child, 2);
		assertEquals("64,", parent.getCommaSeparatedPitchString());
	}
	
	
	@Test
	void given_child_with_note_60_at_2_bars_then_pitch_string_is_correct() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		child.addMuNote(new MuNote(60, 64));
		child.setLengthInQuarters(2.0);
		parent.addMu(child, 2);
		assertEquals("60,", parent.getCommaSeparatedPitchString());
	}
	
	
	
	
	
	
}
