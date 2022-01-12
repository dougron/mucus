package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class Mu_LengthTests
{


	@Test
	void parent_returns_length_of_0_bars_0_0_bars_and_beats_and_0_quarters() throws Exception
	{
		Mu parent = new Mu("parent");
		assertEquals(0, parent.getLengthInBars());
		assertEquals(0.0, parent.getLengthInQuarters());
		assertEquals(0, parent.getLengthInBarsAndBeats().getBarPosition());
		assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
	}
	
	
	@Nested
	class parent_with_length_of
	{
		@Nested
		class _2_bars_returns_length_of
		{
			@Test
			void _2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInBars(2);
				assertEquals(2, parent.getLengthInBars());
			}
			
			
			@Test
			void _8_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInBars(2);
				assertEquals(8.0, parent.getLengthInQuarters());
			}
			
			
			@Test
			void _6_quarters_when_time_signature_set_to_3_4() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInBars(2);
				parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
				assertEquals(6.0, parent.getLengthInQuarters());
			}
			
			
			@Test
			void _2_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInBars(2);
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
		}
		
		
		@Nested
		class _2_bars_2_beats_returns_length_of
		{
			@Test
			void _2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
				assertEquals(2, parent.getLengthInBars());
			}
			
			
			@Test
			void _10_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
				assertEquals(10.0, parent.getLengthInQuarters());
			}
			

			@Test
			void _6_quarters_when_time_signature_set_to_3_4() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
				parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
				assertEquals(8.0, parent.getLengthInQuarters());
			}
			
			
			@Test
			void _2_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
				BarsAndBeats bab = parent.getLengthInBarsAndBeats();
				assertEquals(2, bab.getBarPosition());
				assertEquals(2.0, bab.getOffsetInQuarters());
			}
		}
		
		
		@Nested
		class _11_quarters_returns_length_of
		{
			@Test
			void _3_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInQuarters(11.0);
				assertEquals(3, parent.getLengthInBars());
			}
			
			
			@Test
			void _11_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInQuarters(11.0);
				assertEquals(11.0, parent.getLengthInQuarters());
			}
			
			
			@Test
			void _11_quarters_when_time_signature_set_to_3_4() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInQuarters(11.0);
				parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
				assertEquals(11.0, parent.getLengthInQuarters());
			}
			
			
			@Test
			void _2_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				parent.setLengthInQuarters(11.0);
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(3.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
		}
		
	}
	
	
	@Nested
	class parent_with_child_at
	{
		
		@Nested
		class _2_bars_returns_length_of
		{
			@Test
			void _2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 2);
				assertEquals(2, parent.getLengthInBars());
			}
			
		
			@Test
			void _8_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 2);
				assertEquals(8.0, parent.getLengthInQuarters());
			}
			

			@Test
			void _6_quarters_when_time_signature_set_to_3_4() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 2);
				parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
				assertEquals(6.0, parent.getLengthInQuarters());
			}
			

			@Test
			void _2_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 2);
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
		}
	
	
		@Nested
		class _2_bars_1_beat_returns_length_of
		{
			@Test
			void _3_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(2, 1.0));
				assertEquals(3, parent.getLengthInBars());
			}
			
		
			@Test
			void _9_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(2, 1.0));
				assertEquals(9.0, parent.getLengthInQuarters());
			}
			
			
			@Test
			void _7_quarters_when_time_signature_set_to_3_4() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(2, 1.0));
				parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
				assertEquals(7.0, parent.getLengthInQuarters());
			}
			
			
			@Test
			void _2_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(2, 1.0));
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
		}
	
	
		@Nested
		class _7_quarters_returns_length_of
		{
			@Test
			void _2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 7.0);
				assertEquals(2, parent.getLengthInBars());
			}
			
			
			@Test
			void _7_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 7.0);
				assertEquals(7.0, parent.getLengthInQuarters());
			}
			
		
			@Test
			void _7_quarters_when_time_signature_set_to_3_4() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 7.0);
				parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
				assertEquals(7.0, parent.getLengthInQuarters());
			}
			
			
			@Test
			void _1_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 7.0);
				assertEquals(1, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(3.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
		}
	}
	

	
	@Nested
	class given_child_at_2_bars_and_grandchild_at_3_bars_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _3_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(3, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _12_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(12.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _2_bars_before_grandchild_is_added_and_5_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(2, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_0_beats_before_grandchild_is_added_and_5_bars_0_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _8_quarters_before_grandchild_is_added_and_20_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(8.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
	

	@Nested
	class given_child_at_2_bars_and_grandchild_at_3_bars_2_beats_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(4, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _14_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(14.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _2_bars_before_grandchild_is_added_and_6_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(2, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(6, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_0_beats_before_grandchild_is_added_and_5_bars_2_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _8_quarters_before_grandchild_is_added_and_22_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(8.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
		

	@Nested
	class given_child_at_2_bars_and_grandchild_at_13_quarters_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(4, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _13_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(13.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _21_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(21.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _21_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(21.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _2_bars_before_grandchild_is_added_and_6_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(2, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(6, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_0_beats_before_grandchild_is_added_and_5_bars_1_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _8_quarters_before_grandchild_is_added_and_21_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  2);
				assertEquals(8.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(21.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
	
	

	@Nested
	class given_child_at_2_bars_1_beat_and_grandchild_at_3_bars_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _3_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(3, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
		
			@Test
			void _11_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(11.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _3_bars_before_grandchild_is_added_and_5_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(3, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_1_beats_before_grandchild_is_added_and_5_bars_0_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _9_quarters_before_grandchild_is_added_and_20_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(9.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
	
	
	@Nested
	class given_child_at_2_bars_1_beat_and_grandchild_at_3_bars_2_beats_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(4, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _13_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(13.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _3_bars_before_grandchild_is_added_and_6_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(3, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(6, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_1_beats_before_grandchild_is_added_and_5_bars_2_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _9_quarters_before_grandchild_is_added_and_22_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(9.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
		
	
	@Nested
	class given_child_at_2_bars_1_beat_and_grandchild_at_13_quarters_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(4, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _13_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(13.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(22.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(22.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _3_bars_before_grandchild_is_added_and_6_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(3, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(6, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_1_beats_before_grandchild_is_added_and_5_bars_2_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _9_quarters_before_grandchild_is_added_and_22_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  new BarsAndBeats(2, 1.0));	
				assertEquals(9.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(22.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
	
	

	@Nested
	class given_child_at_9_quarters_and_grandchild_at_3_bars_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _3_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(3, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
		
			@Test
			void _11_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(11.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _3_bars_before_grandchild_is_added_and_5_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(3, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_1_beats_before_grandchild_is_added_and_5_bars_0_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _9_quarters_before_grandchild_is_added_and_20_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(9.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 3);
				assertEquals(20.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
	
	
	@Nested
	class given_child_at_9_quarters_and_grandchild_at_3_bars_2_beats_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(4, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _13_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(13.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _3_bars_before_grandchild_is_added_and_6_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(3, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(6, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_1_beats_before_grandchild_is_added_and_5_bars_2_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _9_quarters_before_grandchild_is_added_and_22_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(9.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(3, 2.0));
				assertEquals(22.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
		
	
	@Nested
	class given_child_at_9_quarters_and_grandchild_at_13_quarters_then
	{
		@Nested
		class _child_returns_length_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(4, child.getLengthInBars());
			}
			
			
			@Test
			void _3_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(3, child.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _13_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(13.0, child.getLengthInQuarters());
			}
		}
		
		
		@Nested
		class _child_returns_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, child.getEndPositionInBars());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, child.getEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, child.getEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(22.0, child.getEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandchild_returns_global_position_of_end_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, grandchild.getGlobalBarIndexOfEnd());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, grandchild.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandchild.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(22.0, grandchild.getGlobalEndPositionInQuarters());
			}
		}
	
		
		@Nested
		class _parent_returns_length_of
		{
			@Test
			void _3_bars_before_grandchild_is_added_and_6_bars_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(3, parent.getLengthInBars());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(6, parent.getLengthInBars());
			}
			
			
			@Test
			void _2_bars_1_beats_before_grandchild_is_added_and_5_bars_2_beats_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(2, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(1.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(5, parent.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(2.0, parent.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _9_quarters_before_grandchild_is_added_and_22_quarters_after() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child,  9.0);	
				assertEquals(9.0, parent.getLengthInQuarters());
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 13.0);
				assertEquals(22.0, parent.getLengthInQuarters());
			}
		}
	
		
	}
	

	@Test
	void child_at_2_bars_returns_length_of_0_bars() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		parent.addMu(child,  2);	
		assertEquals(0, child.getLengthInBars());
	}
	
	
	@Test
	void child_at_2_bars_returns_length_of_0_quarters() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		parent.addMu(child,  2);	
		assertEquals(0.0, child.getLengthInQuarters());
	}
	
	
	@Test
	void child_at_2_bars_returns_length_of_0_bars_0_beats() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		parent.addMu(child,  2);	
		assertEquals(0, child.getLengthInBarsAndBeats().getBarPosition());
		assertEquals(0.0, child.getLengthInBarsAndBeats().getOffsetInQuarters());
	}
}
