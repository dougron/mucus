package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

class Mu_LocalPosition_Tests
{

		
		@Test
		void when_child_at_1_and_grandchild_at_2_then_grandchild_local_position_in_relation_to_child_equals_2_bars() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			child.addMu(grandchild, 2);
			mu.addMu(child, 1);
			assertEquals(2, grandchild.getLocalPositionInBars(0, child));
			// this getLocalPositionInBars call with an offset value has become redundant,
			// wont change this one but wont add this offset to localPositionInQuarters or 
			// localPosditionInBarsAndBeats
		}
		
		
		// even time signature
		@Test
		void given_child_at_1_grandchild_at_2_greatgrandchild_at_3_then_local_position_in_relation_to_child_equals_5_bars() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			child.addMu(grandchild, 2);
			mu.addMu(child, 1);
			grandchild.addMu(greatgrandchild, 3);
			assertEquals(5, greatgrandchild.getLocalPositionInBars(0, child));
			assertEquals(5, greatgrandchild.getLocalPositionInBars(child));
			assertEquals(20.0, greatgrandchild.getLocalPositionInQuarters(child));
			assertEquals(5, greatgrandchild.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandchild.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_5_quarters_then_greatgrandchild_position_local_to_child_equals_3_bars_1_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 5.0);
			mu.addMu(child, 1);
			assertEquals(3, greatgrandchild.getLocalPositionInBars(child));
			assertEquals(13.0, greatgrandchild.getLocalPositionInQuarters(child));
			assertEquals(3, greatgrandchild.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(1.0, greatgrandchild.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_2_bars_3_quartrs_then_greatgrandchild_position_local_to_child_equals_4_bars_3_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, new BarsAndBeats(2, 3.0));
			mu.addMu(child, 1);
			assertEquals(4, greatgrandchild.getLocalPositionInBars(child));
			assertEquals(19.0, greatgrandchild.getLocalPositionInQuarters(child));
			assertEquals(4, greatgrandchild.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandchild.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
// siblings -------------------------------------------------------------------------------------------
		
		// 3 different lengths and sibling position in bars
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_8_bars_0_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(32.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_8_bars_0_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(32.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_4_bars_3_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(7, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(28.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(7, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		// 3 different lengths and sibling position in quarters
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(35.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
	
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(33.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(1.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(9, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(37.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(9, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(1.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		
		// 3 different lengths and sibling position in barsAndBeats
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(35.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}

		
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(7, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(31.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(7, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}

		
		@Test
		void when_child_at_1_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, 1);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(35.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		// do all again with a) child position in quarters and b) child position in bars and beats
		// following 9 are child position in quarters
		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_8_bars_0_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(31.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_8_bars_0_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(31.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_4_bars_3_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(7, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(27.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(7, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		// 3 different lengths and sibling position in quarters
		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(34.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(32.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(1.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(9, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(36.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(9, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(1.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		
		// 3 different lengths and sibling position in barsAndBeats
		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(34.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}

		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(7, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(30.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(7, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}

		
		@Test
		void when_child_at_5_quarters_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, 5.0);
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(34.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}
		
		
		// following 9 are child position in barsAndBeats
		
		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_8_bars_0_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(30.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}


		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_8_bars_0_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(30.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}


		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_1_bars_then_greatgrandsibling_position_local_to_child_equals_4_bars_3_beats() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 1, greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(7, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(26.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(7, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(0.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}


		// 3 different lengths and sibling position in quarters

		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(33.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}


		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(31.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(1.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}


		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_7_quarters_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, 7.0, greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(9, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(35.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(9, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(1.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}



		// 3 different lengths and sibling position in barsAndBeats

		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBars(2);
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(33.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}


		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_6_quarters_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInQuarters(6.0);
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(7, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(29.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(7, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}


		@Test
		void when_child_at_1_bar_2_beats_and_grandchild_at_2_and_greatgrandchild_at_3_bars_length_2_bars_2_beats_greatgrandsibling_at_1_bar_3_beats_then_greatgrandsibling_position_local_to_child_is_correct() throws Exception
		{
			Mu mu = new Mu("parent");
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			Mu greatgrandsibling = new Mu("greatgrandsibling");
			child.addMu(grandchild, 2);
			grandchild.addMu(greatgrandchild, 3);
			greatgrandchild.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			grandchild.addMuToEndOfSibling(greatgrandsibling, new BarsAndBeats(1, 3.0), greatgrandchild);
			mu.addMu(child, new BarsAndBeats(1, 2.0));
			assertEquals(8, greatgrandsibling.getLocalPositionInBars(child));
			assertEquals(33.0, greatgrandsibling.getLocalPositionInQuarters(child));
			assertEquals(8, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getBarPosition());
			assertEquals(3.0, greatgrandsibling.getLocalPositionInBarsAndBeats(child).getOffsetInQuarters());
		}



				
				
		
		@Test
		void given_complex_time_signature_when_child_at_1_grandchild_at_1_greatgrandchild_at_1_then_local_position_in_relation_to_child_equals_sum_of_5_bars_starting_from_child_position() throws Exception
		{
			Mu mu = new Mu("parent");
			TimeSignature[] timeSignatures = new TimeSignature[]
					{
							TimeSignature.FIVE_EIGHT_32,
							TimeSignature.THREE_FOUR,
							TimeSignature.SEVEN_EIGHT_322,
							TimeSignature.FOUR_FOUR
					};
			mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(timeSignatures));
			Mu child = new Mu("child");
			Mu grandchild = new Mu("grandchild");
			Mu greatgrandchild = new Mu("greatgrandchild");
			child.addMu(grandchild, 1);
			mu.addMu(child, 1);
			grandchild.addMu(greatgrandchild, 1);
			assertEquals(6.5, greatgrandchild.getLocalPositionInQuarters(child));
		}
		
		
		
		
		
		
		
		
		
		
		
		
		

}
