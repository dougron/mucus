package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class Mu_RelativeRhythmicPosition_Test
{

	// get relative rhythmic position from 2 Mus--------------------------------------------
		// bar:supertactus:tactus:subtactus
		
		
		@Test
		void mu_on_next_bar_returns_correct_relative_rhythmic_position() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 1);
			parent.addMu(child2, 2);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void mu_on_previous_bar_returns_correct_relative_rhythmic_position() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 2);
			parent.addMu(child2, 1);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(-1, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_mu_not_on_beginning_of_bar_then_mu_on_previous_bar_returns_correct_relative_rhythmic_position() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 1);
			parent.addMu(child2, 9.0);
			RelativeRhythmicPosition rrp = child2.getRelativeRhythmicPosition(child1);
			assertEquals(-2, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		

		@Test
		void given_mu_not_on_beginning_of_bar_then_mu_in_previous_bar_but_not_on_first_beat_returns_correct_relative_rhythmic_position() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 10.0);
			parent.addMu(child2, 6.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(-1, rrp.getBarOffset());
			assertEquals(-1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void next_mu_at_supertactus_position_returns_correct_relative_rhythmic_position() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 4.0);
			parent.addMu(child2, 6.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void next_mu_in_next_bar_not_at_beginning_returns_correct_relative_rhythmic_position() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 4.0);
			parent.addMu(child2, 9.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(1, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void previous_mu_at_previous_supertactus_returns_correct_relative_rhythmic_position() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 4.0);
			parent.addMu(child2, 2.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(-1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		

		@Test
		void given_8_4_time_when_other_mu_is_on_subsequent_supertactii_then_superTactusOffset_equals_1() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 2.0);
			parent.addMu(child2, 4.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
//			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_on_previous_supertactii_then_superTactusOffset_equals_minus1() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 6.0);
			parent.addMu(child2, 4.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(-1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_two_supertactii_later_then_superTactusOffset_equals_2() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 2.0);
			parent.addMu(child2, 6.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(2, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_two_supertactii_previous_then_superTactusOffset_equals_minus2() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 6.0);
			parent.addMu(child2, 2.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(-2, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_in_the_next_bar_before_first_supertactus_then_superTactusOffset_equals_0() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 2.0);
			parent.addMu(child2, 9.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(1, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_in_the_next_bar_after_first_supertactus_then_superTactusOffset_equals_1() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 2.0);
			parent.addMu(child2, 11.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(1, rrp.getSuperTactusOffset());
			assertEquals(1, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_in_the_next_bar_after_2nd_supertactus_then_superTactusOffset_equals_2() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 2.0);
			parent.addMu(child2, 13.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(2, rrp.getSuperTactusOffset());
			assertEquals(1, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_in_the_next_bar_on_2nd_supertactus_then_superTactusOffset_equals_2() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 2.0);
			parent.addMu(child2, 12.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(2, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_in_the_previous_bar_after_last_supertactus_then_superTactusOffset_equals_0() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 8.0);
			parent.addMu(child2, 7.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(-1, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_8_4_time_when_other_mu_is_in_the_previous_bar_on_last_supertactus_then_superTactusOffset_equals_minus1() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 8.0);
			parent.addMu(child2, 6.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(-1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		@Test
		void given_8_4_time_when_other_mu_is_in_the_previous_bar_before_last_supertactus_then_superTactusOffset_equals_minus1() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.EIGHT_FOUR));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 8.0);
			parent.addMu(child2, 5.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(-1, rrp.getSuperTactusOffset());
			assertEquals(-1, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void subtactus_position_in_middle_of_bar_returns_correct_positive_offset() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 1.0);
			parent.addMu(child2, 1.5);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(1, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void subtactus_position_in_middle_of_bar_returns_correct_negative_offset() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 1.0);
			parent.addMu(child2, 0.5);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(-1, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void subtactus_position_at_end_of_bar_returns_correct_negative_offset() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 4.0);
			parent.addMu(child2, 3.5);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(-1, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void subtactus_position_at_end_of_bar_returns_correct_positive_offset() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 3.0);
			parent.addMu(child2, 3.5);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(0, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(1, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_7_8_time_then_bar_displacement_returns_correct_offset() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 1);
			parent.addMu(child2, 2);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
			
			rrp = child2.getRelativeRhythmicPosition(child1);
			assertEquals(-1, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_7_8_time_then_bar_and_supertactus_displacement_returns_correct_offset() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 1.5);
			parent.addMu(child2, 6.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(1, rrp.getSuperTactusOffset());
			assertEquals(1, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
			
			rrp = child2.getRelativeRhythmicPosition(child1);
			assertEquals(-1, rrp.getBarOffset());
			assertEquals(-1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(0, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_7_8_time_then_bar_and_supertactus_and_tactus_displacement_returns_correct_offset() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 1.0);
			parent.addMu(child2, 6.5);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(1, rrp.getBarOffset());
			assertEquals(1, rrp.getSuperTactusOffset());
			assertEquals(1, rrp.getTactusOffset());
			assertEquals(1, rrp.getSubTactusOffset());
			
			rrp = child2.getRelativeRhythmicPosition(child1);
			assertEquals(-1, rrp.getBarOffset());
			assertEquals(-1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(-1, rrp.getSubTactusOffset());
		}
		
		
		@Test
		void given_changing_time_signature_then_bar_and_supertactus_and_tactus_displacement_returns_correct_offset() throws Exception
		{
			Mu parent = new Mu("parent");
			TimeSignature[] timeSignatures = new TimeSignature[] {TimeSignature.SEVEN_EIGHT_322, TimeSignature.SIX_EIGHT, TimeSignature.THREE_FOUR, TimeSignature.FIVE_EIGHT_32};
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(timeSignatures));
			Mu child1 = new Mu("child1");
			Mu child2 = new Mu("child2");
			parent.addMu(child1, 0.5);
			parent.addMu(child2, 8.0);
			RelativeRhythmicPosition rrp = child1.getRelativeRhythmicPosition(child2);
			assertEquals(2, rrp.getBarOffset());
			assertEquals(0, rrp.getSuperTactusOffset());
			assertEquals(1, rrp.getTactusOffset());
			assertEquals(1, rrp.getSubTactusOffset());
			
			rrp = child2.getRelativeRhythmicPosition(child1);
			assertEquals(-2, rrp.getBarOffset());
			assertEquals(-1, rrp.getSuperTactusOffset());
			assertEquals(0, rrp.getTactusOffset());
			assertEquals(-2, rrp.getSubTactusOffset());
		}
		

		// given a mu as startpoint and a RelativeRhythmicPosition, find position for new Mu and add to this.
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_1_bar_0_supertactus_0_tactus_0_subtactus_then_new_mu_global_position_equals_2_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(1, 0, 0, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(2, grandchild.getGlobalPositionInBars());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_2_bar_0_supertactus_0_tactus_0_subtactus_then_new_mu_global_position_equals_3_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(2, 0, 0, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(3, grandchild.getGlobalPositionInBars());
		}
		
		
		@Test
		void given_child_at_2_bar_and_relative_rhythmic_position_minus1_bar_0_supertactus_0_tactus_0_subtactus_then_new_mu_global_position_equals_1_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(-1, 0, 0, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(1, grandchild.getGlobalPositionInBars());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_0_bar_1_supertactus_0_tactus_0_subtactus_then_new_mu_global_position_equals_1_bar_2_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 1, 0, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(1, grandchild.getGlobalPositionInBars());
			assertEquals(2.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_0_bar_3_supertactus_0_tactus_0_subtactus_then_new_mu_global_position_equals_2_bar_2_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 3, 0, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(2, grandchild.getGlobalPositionInBars());
			assertEquals(2.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_2_bar_and_relative_rhythmic_position_0_bar_minus3_supertactus_0_tactus_0_subtactus_then_new_mu_global_position_equals_0_bar_2_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, -3, 0, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(0, grandchild.getGlobalPositionInBars());
			assertEquals(2.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_0_bar_0_supertactus_1_tactus_0_subtactus_then_new_mu_global_position_equals_1_bar_1_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 1, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(1, grandchild.getGlobalPositionInBars());
			assertEquals(1.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_0_bar_0_supertactus_minus1_tactus_0_subtactus_then_new_mu_global_position_equals_0_bar_3_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(0, grandchild.getGlobalPositionInBars());
			assertEquals(3.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_0_bar_0_supertactus_5_tactus_0_subtactus_then_new_mu_global_position_equals_2_bar_1_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 5, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(2, grandchild.getGlobalPositionInBars());
			assertEquals(1.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_2_bar_and_relative_rhythmic_position_0_bar_0_supertactus_minus5_tactus_0_subtactus_then_new_mu_global_position_equals_0_bar_3_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -5, 0);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(0, grandchild.getGlobalPositionInBars());
			assertEquals(3.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_0_bar_0_supertactus_0_tactus_1_subtactus_then_new_mu_global_position_equals_1_bar_0pnt5_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, 1);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(1, grandchild.getGlobalPositionInBars());
			assertEquals(0.5, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_0_bar_0_supertactus_0_tactus_minus1_subtactus_then_new_mu_global_position_equals_0_bar_3pnt5_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(0, grandchild.getGlobalPositionInBars());
			assertEquals(3.5, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_1_bar_and_relative_rhythmic_position_0_bar_0_supertactus_0_tactus_11_subtactus_then_new_mu_global_position_equals_2_bar_1pnt5_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 1);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, 11);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(2, grandchild.getGlobalPositionInBars());
			assertEquals(1.5, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void given_child_at_2_bar_and_relative_rhythmic_position_0_bar_0_supertactus_0_tactus_minus11_subtactus_then_new_mu_global_position_equals_0_bar_2pnt5_beat() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -11);
			Mu grandchild = child.addMuAtBarsAndBeatsPosition(child, rrp);
			assertEquals(0, grandchild.getGlobalPositionInBars());
			assertEquals(2.5, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
	
		
		
		
}
