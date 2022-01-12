package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class Mu_GlobalPositionTests
{


	@Nested
	class parent_returns_global_position_of
	{
		@Test
		void _0_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			assertEquals(0, parent.getGlobalPositionInBars());
		}
		
		
		@Test
		void _0_0_bars_and_beats() throws Exception
		{
			Mu parent = new Mu("parent");
			BarsAndBeats bab = parent.getGlobalPositionInBarsAndBeats();
			assertEquals(0, bab.getBarPosition());
			assertEquals(0.0, bab.getOffsetInQuarters());
		}
		
		
		@Test
		void _0_quarters() throws Exception
		{
			Mu parent = new Mu("parent");
			assertEquals(0.0, parent.getGlobalPositionInQuarters());
		}
	}
	
	
	@Nested
	class parent_of_length_2_returns_global_bar_index_after_end_of
	{
		@Test
		void _2_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInBars(2);
			assertEquals(2, parent.getGlobalBarIndexAfterEnd());
		}
		
		
		@Test
		void _2_0_bars_and_beats() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInBars(2);
			BarsAndBeats bab = parent.getGlobalEndPositionInBarsAndBeats();
			assertEquals(2, bab.getBarPosition());
			assertEquals(0.0, bab.getOffsetInQuarters());
		}
		
		
		@Test
		void _8_quarters() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInBars(2);
			assertEquals(8.0, parent.getGlobalEndPositionInQuarters());
		}
	}

	
	@Nested
	class parent_of_length_2_returns_global_bar_index_of_end_of
	{
		@Test
		void _2_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInBars(2);
			assertEquals(2, parent.getGlobalBarIndexOfEnd());
		}
	}
	
	
	@Nested
	class parent_of_length_3_bars_3_beats_returns_global_bar_index_after_end_of
	{
		@Test
		void _4_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInBarsAndBeats(new BarsAndBeats(3, 3.0));
			assertEquals(4, parent.getGlobalBarIndexAfterEnd());
		}
		
		
		@Test
		void _3_3_bars_and_beats() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInBarsAndBeats(new BarsAndBeats(3, 3.0));
			BarsAndBeats bab = parent.getGlobalEndPositionInBarsAndBeats();
			assertEquals(3, bab.getBarPosition());
			assertEquals(3.0, bab.getOffsetInQuarters());
		}
		
		
		@Test
		void _15_quarters() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInBarsAndBeats(new BarsAndBeats(3, 3.0));
			assertEquals(15.0, parent.getGlobalEndPositionInQuarters());
		}
	}
	
	
	@Nested
	class parent_of_length_3_bars_3_beats_returns_global_bar_index_of_end_of
	{
		@Test
		void _3_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInBarsAndBeats(new BarsAndBeats(3, 3.0));
			assertEquals(3, parent.getGlobalBarIndexOfEnd());
		}
	}
	
	
	@Nested
	class parent_of_length_13_quarters_returns_global_bar_index_after_end_of
	{
		@Test
		void _4_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInQuarters(13.0);
			assertEquals(4, parent.getGlobalBarIndexAfterEnd());
		}
		
		
		@Test
		void _3_3_bars_and_beats() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInQuarters(13.0);
			BarsAndBeats bab = parent.getGlobalEndPositionInBarsAndBeats();
			assertEquals(3, bab.getBarPosition());
			assertEquals(1.0, bab.getOffsetInQuarters());
		}
		
		
		@Test
		void _15_quarters() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInQuarters(13.0);
			assertEquals(13.0, parent.getGlobalEndPositionInQuarters());
		}
	}
	
	
	@Nested
	class parent_of_length_13_quarters_returns_global_bar_index_of_end_of
	{
		@Test
		void _3_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setLengthInQuarters(13.0);
			assertEquals(3, parent.getGlobalBarIndexOfEnd());
		}
	}

	
	@Nested
	class given_parent_with
	{
		@Nested
		class _child_at_1_bar
		{
			@Nested
			class _returns_global_position_of
			{
				@Test
				void _1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, 1);
					assertEquals(1, child.getGlobalPositionInBars());
				}
				
				
				@Test
				void _1_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, 1);
					BarsAndBeats bab = child.getGlobalPositionInBarsAndBeats();
					assertEquals(1, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _4_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, 1);
					assertEquals(4.0, child.getGlobalPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_returns_global_end_position_of
			{
				@Test
				void _3_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, 1);
					assertEquals(3, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, 1);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(3, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _12_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, 1);
					assertEquals(12.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_1_beat_returns_global_end_position_of
			{
				@Test
				void _4_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, 1);
					assertEquals(4, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, 1);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(3, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _13_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, 1);
					assertEquals(13.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_6_quarters_returns_global_end_position_of
			{
				@Test
				void _3_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, 1);
					assertEquals(3, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _2_2_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, 1);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(2, bab.getBarPosition());
					assertEquals(2.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _10_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, 1);
					assertEquals(10.0, child.getGlobalEndPositionInQuarters());
				}
			}				
		}
		
		
		@Nested
		class _child_at_minus_1_bar
		{
			@Nested
			class _returns_global_position_of
			{

				@Test
				void _minus_1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, -1);
					assertEquals(-1, child.getGlobalPositionInBars());
				}
				
			
				@Test
				void _minus_1_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, -1);
					BarsAndBeats bab = child.getGlobalPositionInBarsAndBeats();
					assertEquals(-1, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				

				@Test
				void _minus_4_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, -1);
					assertEquals(-4.0, child.getGlobalPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_returns_global_end_position_of
			{
				@Test
				void _1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, -1);
					assertEquals(1, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, -1);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(1, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _4_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, -1);
					assertEquals(4.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_1_beat_returns_global_end_position_of
			{
				@Test
				void _2_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, -1);
					assertEquals(2, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _1_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, -1);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(1, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _5_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, -1);
					assertEquals(5.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_6_quarters_returns_global_end_position_of
			{
				@Test
				void _1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, -1);
					assertEquals(1, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _0_2_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, -1);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(0, bab.getBarPosition());
					assertEquals(2.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _2_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, -1);
					assertEquals(2.0, child.getGlobalEndPositionInQuarters());
				}
			}				
		}
		
		
		@Nested
		class _child_at_1_1_bars_and_beats
		{
			@Nested
			class _returns_global_position_of
			{

				@Test
				void _1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					assertEquals(1, child.getGlobalPositionInBars());
				}
				
				
				@Test
				void _1_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					BarsAndBeats bab = child.getGlobalPositionInBarsAndBeats();
					assertEquals(1, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _5_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					assertEquals(5.0, child.getGlobalPositionInQuarters());
				}
			
			}
			
			
			@Nested
			class _and_length_2_bars_returns_global_end_position_of
			{
				@Test
				void _3_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					assertEquals(3, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(3, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _12_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					assertEquals(12.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_1_beat_returns_global_end_position_of
			{
				@Test
				void _4_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					assertEquals(4, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_2_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(3, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _14_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					assertEquals(13.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_6_quarters_returns_global_end_position_of
			{
				@Test
				void _3_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					assertEquals(3, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(2, bab.getBarPosition());
					assertEquals(3.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _11_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(1, 1.0));
					assertEquals(11.0, child.getGlobalEndPositionInQuarters());
				}
			}				
		}
		
		
		@Nested
		class _child_at_1_bars_minus_1_beats
		{
			@Nested
			class _returns_global_position_of
			{
				@Test
				void _0_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					assertEquals(0, child.getGlobalPositionInBars());
				}
				
				
				@Test
				void _0_3_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					BarsAndBeats bab = child.getGlobalPositionInBarsAndBeats();
					assertEquals(0, bab.getBarPosition());
					assertEquals(3.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _3_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					assertEquals(3.0, child.getGlobalPositionInQuarters());
				}
			
			}

			
			@Nested
			class _and_length_2_bars_returns_global_end_position_of
			{
				@Test
				void _2_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					assertEquals(2, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _2_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(2, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _8_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					assertEquals(8.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_1_beat_returns_global_end_position_of
			{
				@Test
				void _3_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					assertEquals(3, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _2_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(2, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _9_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					assertEquals(9.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_6_quarters_returns_global_end_position_of
			{
				@Test
				void _3_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					assertEquals(3, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _2_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(2, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _9_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(1, -1.0));
					assertEquals(9.0, child.getGlobalEndPositionInQuarters());
				}
			}
		}
		
		
		@Nested
		class _child_at_minus_1_bars_1_beats
		{
			@Nested
			class _returns_global_position_of
			{

				@Test
				void _minus_1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					assertEquals(-1, child.getGlobalPositionInBars());
				}
				

				@Test
				void _minus_1_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					BarsAndBeats bab = child.getGlobalPositionInBarsAndBeats();
					assertEquals(-1, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				

				@Test
				void _3_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					assertEquals(-3.0, child.getGlobalPositionInQuarters());
				}
			
			}
			
			
			@Nested
			class _and_length_2_bars_returns_global_end_position_of
			{
				@Test
				void _1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					assertEquals(1, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _1_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(1, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _4_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					assertEquals(4.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_1_beat_returns_global_end_position_of
			{
				
				@Test
				void _2_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					assertEquals(2, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _1_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(1, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _5_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					assertEquals(5.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_6_quarters_returns_global_end_position_of
			{
				@Test
				void _1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					assertEquals(1, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _0_3_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(0, bab.getBarPosition());
					assertEquals(3.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _3_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, new BarsAndBeats(-1, 1.0));
					assertEquals(3.0, child.getGlobalEndPositionInQuarters());
				}
			}
		}
		
		
		@Nested
		class _child_at_6_quarters
		{
			@Nested
			class _returns_global_position_of
			{

				@Test
				void _1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, 6.0);
					assertEquals(1, child.getGlobalPositionInBars());
				}
				
			
				@Test
				void _1_2_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, 6.0);
					BarsAndBeats bab = child.getGlobalPositionInBarsAndBeats();
					assertEquals(1, bab.getBarPosition());
					assertEquals(2.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _6_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, 6.0);
					assertEquals(6.0, child.getGlobalPositionInQuarters());
				}
			
			}
			
			
			@Nested
			class _and_length_2_bars_returns_global_end_position_of
			{
				@Test
				void _3_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, 6.0);
					assertEquals(3, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, 6.0);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(3, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _12_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, 6.0);
					assertEquals(12.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_1_beat_returns_global_end_position_of
			{
				
				@Test
				void _4_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, 6.0);
					assertEquals(4, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, 6.0);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(3, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _13_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, 6.0);
					assertEquals(13.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_6_quarters_returns_global_end_position_of
			{
				@Test
				void _3_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, 6.0);
					assertEquals(3, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _3_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, 6.0);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(3, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _12_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, 6.0);
					assertEquals(12.0, child.getGlobalEndPositionInQuarters());
				}
			}								
		}
		
		
		@Nested
		class _child_at_minus_7_quarters
		{
			@Nested
			class _returns_global_position_of
			{

				@Test
				void _minus_2_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, -7.0);
					assertEquals(-2, child.getGlobalPositionInBars());
				}
				
				
				@Test
				void _minus2_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, -7.0);
					BarsAndBeats bab = child.getGlobalPositionInBarsAndBeats();
					assertEquals(-2, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _minus_7_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					parent.addMu(child, -7.0);
					assertEquals(-7.0, child.getGlobalPositionInQuarters());
				}
			
			}

			
			@Nested
			class _and_length_2_bars_returns_global_end_position_of
			{
				@Test
				void _0_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, -7.0);
					assertEquals(0, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _0_0_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, -7.0);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(0, bab.getBarPosition());
					assertEquals(0.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _0_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBars(2);
					parent.addMu(child, -7.0);
					assertEquals(0.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_2_bars_1_beat_returns_global_end_position_of
			{
				
				@Test
				void _1_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, -7.0);
					assertEquals(1, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _0_1_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, -7.0);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(0, bab.getBarPosition());
					assertEquals(1.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _1_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInBarsAndBeats(new BarsAndBeats(2, 1.0));
					parent.addMu(child, -7.0);
					assertEquals(1.0, child.getGlobalEndPositionInQuarters());
				}
			}
			
			
			@Nested
			class _and_length_6_quarters_returns_global_end_position_of
			{
				@Test
				void _0_bars() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, -7.0);
					assertEquals(0, child.getGlobalBarIndexAfterEnd());
				}
				
				
				@Test
				void _minus_1_3_bars_and_beats() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, -7.0);
					BarsAndBeats bab = child.getGlobalEndPositionInBarsAndBeats();
					assertEquals(-1, bab.getBarPosition());
					assertEquals(3.0, bab.getOffsetInQuarters());
				}
				
				
				@Test
				void _minus_1_quarters() throws Exception
				{
					Mu parent = new Mu("parent");
					Mu child = new Mu("child");
					child.setLengthInQuarters(6.0);
					parent.addMu(child, -7.0);
					assertEquals(-1.0, child.getGlobalEndPositionInQuarters());
				}
			}				
		}
	}
	
	
	@Nested 
	class given_parent_with_child_at_1_bar_and_grandchild_at
	{
		
		@Nested
		class _2_bar_then_grandchild_returns_global_position_of
		{
			@Test
			void _3_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 2);
				parent.addMu(child, 1);
				assertEquals(3, grandchild.getGlobalPositionInBars());			
			}
			
			
			@Test
			void _3_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 2);
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getGlobalPositionInBarsAndBeats();
				assertEquals(3, bab.getBarPosition());
				assertEquals(0.0, bab.getOffsetInQuarters());			
			}
			
			
			@Test
			void _12_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 2);
				parent.addMu(child, 1);
				assertEquals(12.0, grandchild.getGlobalPositionInQuarters());			
			}
				
		}
		
		
		@Nested
		class _minus_2_bar_then_grandchild_returns_global_position_of
		{
			@Test
			void _minus_1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -2);
				parent.addMu(child, 1);
				assertEquals(-1, grandchild.getGlobalPositionInBars());			
			}
			
			
			@Test
			void _minus_1_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -2);
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getGlobalPositionInBarsAndBeats();
				assertEquals(-1, bab.getBarPosition());
				assertEquals(0.0, bab.getOffsetInQuarters());			
			}
			
			
			@Test
			void _minus_4_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -2);
				parent.addMu(child, 1);
				assertEquals(-4.0, grandchild.getGlobalPositionInQuarters());			
			}
				
		}
		
		
		@Nested
		class _2_bar_3_beats_then_grandchild_returns_global_position_of
		{

			@Test
			void _3_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, 3.0));
				parent.addMu(child, 1);
				assertEquals(3, grandchild.getGlobalPositionInBars());			
			}
			

			@Test
			void _3_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, 3.0));
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getGlobalPositionInBarsAndBeats();
				assertEquals(3, bab.getBarPosition());
				assertEquals(3.0, bab.getOffsetInQuarters());			
			}
			

			@Test
			void _15_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, 3.0));
				parent.addMu(child, 1);
				assertEquals(15.0, grandchild.getGlobalPositionInQuarters());			
			}
				
		}
		
		
		@Nested
		class _minus_2_bar_1_beats_then_grandchild_returns_global_position_of
		{

			@Test
			void _minus_1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-2, 1.0));
				parent.addMu(child, 1);
				assertEquals(-1, grandchild.getGlobalPositionInBars());			
			}
			
		
			@Test
			void _minus_1_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-2, 1.0));
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getGlobalPositionInBarsAndBeats();
				assertEquals(-1, bab.getBarPosition());
				assertEquals(1.0, bab.getOffsetInQuarters());			
			}
			

			@Test
			void _minus_3_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-2, 1.0));
				parent.addMu(child, 1);
				assertEquals(-3.0, grandchild.getGlobalPositionInQuarters());			
			}
				
		}
		
		
		@Nested
		class _2_bar_minus_1_beats_then_grandchild_returns_global_position_of
		{

			@Test
			void _2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, -1.0));
				parent.addMu(child, 1);
				assertEquals(2, grandchild.getGlobalPositionInBars());			
			}
			
		
			@Test
			void _2_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, -1.0));
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getGlobalPositionInBarsAndBeats();
				assertEquals(2, bab.getBarPosition());
				assertEquals(3.0, bab.getOffsetInQuarters());			
			}
			

			@Test
			void _11_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, -1.0));
				parent.addMu(child, 1);
				assertEquals(11.0, grandchild.getGlobalPositionInQuarters());			
			}
		}	
			
		
		@Nested
		class _minus_3_bar_minus_1_beats_then_grandchild_returns_global_position_of
		{

			@Test
			void _minus_3_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-3, -1.0));
				parent.addMu(child, 1);
				assertEquals(-3, grandchild.getGlobalPositionInBars());			
			}


			@Test
			void _minus_3_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-3, -1.0));
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getGlobalPositionInBarsAndBeats();
				assertEquals(-3, bab.getBarPosition());
				assertEquals(3.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _minus_9_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-3, -1.0));
				parent.addMu(child, 1);
				assertEquals(-9.0, grandchild.getGlobalPositionInQuarters());			
			}

		}
		
		
		@Nested
		class _6_quarters_then_grandchild_returns_global_position_of
		{
			@Test
			void _2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 6.0);
				parent.addMu(child, 1);
				assertEquals(2, grandchild.getGlobalPositionInBars());			
			}
			
			
			@Test
			void _2_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 6.0);
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getGlobalPositionInBarsAndBeats();
				assertEquals(2, bab.getBarPosition());
				assertEquals(2.0, bab.getOffsetInQuarters());			
			}
			
			
			@Test
			void _10_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 6.0);
				parent.addMu(child, 1);
				assertEquals(10.0, grandchild.getGlobalPositionInQuarters());			
			}
				
		}
		
		
		@Nested
		class _minus_6_quarters_then_grandchild_returns_global_position_of
		{
			@Test
			void _minus_1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -6.0);
				parent.addMu(child, 1);
				assertEquals(-1, grandchild.getGlobalPositionInBars());			
			}
			
			
			@Test
			void _minus_1_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -6.0);
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getGlobalPositionInBarsAndBeats();
				assertEquals(-1, bab.getBarPosition());
				assertEquals(2.0, bab.getOffsetInQuarters());			
			}
			
			
			@Test
			void _minus_2_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -6.0);
				parent.addMu(child, 1);
				assertEquals(-2.0, grandchild.getGlobalPositionInQuarters());			
			}
				
		}
		
		
	}


	@Nested
	class given_child_at_1_bar_with_length_2_bars
	{
		@Nested
		class _and_sibling_at_0_bars_then_sibling_returns_global_position_of
		{
			@Test
			void _3_bars()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 1);
				child.setLengthInBars(2);
				parent.addMuToEndOfSibling(sibling, 0, child);
				assertEquals(3, sibling.getGlobalPositionInBars());		
			}
			
			
			@Test
			void _3_0_bars_and_beats()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 1);
				child.setLengthInBars(2);
				parent.addMuToEndOfSibling(sibling, 0, child);	
				assertEquals(3, sibling.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, sibling.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _12_quarters()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 1);
				child.setLengthInBars(2);
				parent.addMuToEndOfSibling(sibling, 0, child);
				assertEquals(12.0, sibling.getGlobalEndPositionInQuarters());
			}
		}
		
		
		@Nested
		class _and_sibling_at_1_bars_then_sibling_returns_global_position_of
		{
			@Test
			void _4_bars()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 1);
				child.setLengthInBars(2);
				parent.addMuToEndOfSibling(sibling, 1, child);
				assertEquals(4, sibling.getGlobalPositionInBars());		
			}
			
			
			@Test
			void _4_0_bars_and_beats()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 1);
				child.setLengthInBars(2);
				parent.addMuToEndOfSibling(sibling, 1, child);	
				assertEquals(4, sibling.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, sibling.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _16_quarters()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 1);
				child.setLengthInBars(2);
				parent.addMuToEndOfSibling(sibling, 1, child);
				assertEquals(16.0, sibling.getGlobalEndPositionInQuarters());
			}
		}			
	}
	

	@Nested
	class given_child_at_1_bar_2_beat_with_length_3_bars_and_sibling_at_0_bars_then_sibling_returns_global_position_of
	{
		@Test
		void _4_bars()
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			Mu sibling = new Mu("sibling");
			
			parent.addMu(child, new BarsAndBeats(1, 2.0));
			child.setLengthInBars(3);
			parent.addMuToEndOfSibling(sibling, 0, child);
			assertEquals(4, sibling.getGlobalPositionInBars());		
		}
		
		
		@Test
		void _4_0_bars_and_beats()
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			Mu sibling = new Mu("sibling");
			
			parent.addMu(child, new BarsAndBeats(1, 2.0));
			child.setLengthInBars(3);
			parent.addMuToEndOfSibling(sibling, 0, child);	
			assertEquals(4, sibling.getGlobalEndPositionInBarsAndBeats().getBarPosition());
			assertEquals(0.0, sibling.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void _16_quarters()
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			Mu sibling = new Mu("sibling");
			
			parent.addMu(child, new BarsAndBeats(1, 2.0));
			child.setLengthInBars(3);
			parent.addMuToEndOfSibling(sibling, 0, child);
			assertEquals(16.0, sibling.getGlobalEndPositionInQuarters());
		}
		
		
		@Nested
		class given_child_at_5_quarters_with_length_3_bars_and_sibling_at_0_bars_then_sibling_returns_global_position_of
		{
			@Test
			void _4_bars()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 5.0);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, 0, child);
				assertEquals(4, sibling.getGlobalPositionInBars());		
			}
			
			
			@Test
			void _4_0_bars_and_beats()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 5.0);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, 0, child);	
				assertEquals(4, sibling.getGlobalEndPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, sibling.getGlobalEndPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _16_quarters()
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 5.0);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, 0, child);
				assertEquals(16.0, sibling.getGlobalEndPositionInQuarters());
			}
		}
	}	


	@Nested
	class given_child_at_2_bars_with_length_3_bars_and
	{
		@Nested
		class _sibling_at_1_bar_1_beat_then_sibling_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 2);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, new BarsAndBeats(1, 1.0), child);
				assertEquals(6, sibling.getGlobalPositionInBars());
				child.setLengthInBars(2);
				assertEquals(5, sibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _6_bars_1_beat() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 2);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, new BarsAndBeats(1, 1.0), child);
				assertEquals(6, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
				child.setLengthInBars(2);
				assertEquals(5, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _25_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 2);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, new BarsAndBeats(1, 1.0), child);
				assertEquals(25, sibling.getGlobalPositionInQuarters());
			}
		}
				

		@Nested
		class _sibling_at_7_quarters_then_sibling_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 2);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, 7.0, child);
				assertEquals(6, sibling.getGlobalPositionInBars());
				child.setLengthInBars(2);
				assertEquals(5, sibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _6_bars_3_beat() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 2);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, 7.0, child);
				assertEquals(6, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(3.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
				child.setLengthInBars(2);
				assertEquals(5, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(3.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _27_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu sibling = new Mu("sibling");
				
				parent.addMu(child, 2);
				child.setLengthInBars(3);
				parent.addMuToEndOfSibling(sibling, 7.0, child);
				assertEquals(27, sibling.getGlobalPositionInQuarters());
			}
		}

	}

	
// grandchild at bars position
	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_length_3_bars_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _6_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
		
			@Test
			void _24_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(24.0, grandsibling.getGlobalPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _8_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _33_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(33.0, grandsibling.getGlobalPositionInQuarters());
			}
		}
	
	
		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _7_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _30_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(30.0, grandsibling.getGlobalPositionInQuarters());
			}
		}	
	}
	
	
	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_length_3_bars_2_beats_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
		
			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(26.0, grandsibling.getGlobalPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _8_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _33_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(33.0, grandsibling.getGlobalPositionInQuarters());
			}
		}
	
	
		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _8_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _32_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(32.0, grandsibling.getGlobalPositionInQuarters());
			}
		}	
	}


	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_length_9_quarters_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _5_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
		
			@Test
			void _21_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(21.0, grandsibling.getGlobalPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandsibling_at_2_bars_2_beats_returns_global_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _7_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _30_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(30.0, grandsibling.getGlobalPositionInQuarters());
			}
		}
	
		
		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBars());
			}
			
			
			@Test
			void _6_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(3.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _27_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(27.0, grandsibling.getGlobalPositionInQuarters());
			}
		}
	
	}

	
	
// grandchild at bars and beats position
	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_1_beat_length_3_bars_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _6_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _24_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(24.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _8_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _33_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(33.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _7_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _30_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(30.0, grandsibling.getGlobalPositionInQuarters());
			}
		}	
	}


	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_1_beat_length_3_bars_2_beats_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(26.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _8_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _33_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(33.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _8_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _32_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(32.0, grandsibling.getGlobalPositionInQuarters());
			}
		}	
	}


	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_1_beat_length_9_quarters_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(22.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_2_beats_returns_global_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _7_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _30_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(30.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _7_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _28_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(28.0, grandsibling.getGlobalPositionInQuarters());
			}
		}

	}

	
	
// grandchild at quarters position
	@Nested
	class given_child_at_1_bar_and_grandchild_at_10_quarters_length_3_bars_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _6_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _24_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(24.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _8_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _33_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(33.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _7_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _30_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(30.0, grandsibling.getGlobalPositionInQuarters());
			}
		}	
	}


	@Nested
	class given_child_at_1_bar_and_grandchild_at_10_quarters_length_3_bars_2_beats_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _6_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(6, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(26.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _8_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _33_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(33.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _8_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _8_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(8, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _32_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(32.0, grandsibling.getGlobalPositionInQuarters());
			}
		}	
	}


	@Nested
	class given_child_at_1_bar_and_grandchild_at_10_quarters_length_9_quarters_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_global_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _5_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(3.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _23_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(23.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_2_beats_returns_global_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _7_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _30_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(30.0, grandsibling.getGlobalPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_global_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBars());
			}


			@Test
			void _7_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getGlobalPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _29_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(29.0, grandsibling.getGlobalPositionInQuarters());
			}
		}

	}


// tests from Mu_tests after the above were created. May duplicate some of the above --------------
	
	@Test
	void when_child_at_1_and_grandchild_at_2_then_grandchild_global_position_with_0_offset_equals_3_bars() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		Mu grandchild = new Mu("grandchild");
		child.addMu(grandchild, 2);
		mu.addMu(child, 1);
		assertEquals(3, grandchild.getGlobalPositionInBars(0));
	}
	
	
	@Test
	void when_child_at_1_and_grandchild_at_2_then_grandchild_getGlobalPositionInQuarters_with_0_offset_equals_12() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		Mu grandchild = new Mu("grandchild");
		child.addMu(grandchild, 2);
		mu.addMu(child, 1);
		assertEquals(12.0, grandchild.getGlobalPositionInQuarters(0.0));
	}
	
	
	@Test
	void given_complex_time_signature_when_child_at_1_and_grandchild_at_2_then_grandchild_getGlobalPositionInQuarters_with_0_offset_equals_sum_of_first_3_bars_in_quarters() throws Exception
	{
		Mu mu = new Mu("parent");
		TimeSignature[] timeSignatures = new TimeSignature[]
				{
						TimeSignature.FIVE_EIGHT_32,
						TimeSignature.THREE_FOUR,
						TimeSignature.FOUR_FOUR
				};
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(timeSignatures));
		Mu child = new Mu("child");
		Mu grandchild = new Mu("grandchild");
		child.addMu(grandchild, 2);
		mu.addMu(child, 1);
		assertEquals(9.5, grandchild.getGlobalPositionInQuarters(0.0));
	}
	
	
	@Test
	void given_child_length_2_at_position_1_bars_when_sibling_added_then_sibling_positionInQuarters_equals_12() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 1);
		Mu sibling = new Mu("sibling");
		mu.addMuToEndOfSibling(sibling, 0, child);
		assertEquals(12.0, sibling.getGlobalPositionInQuarters());
	}
	
	
	@Test
	void given_child_at_1_bar_2_beats_then_globalPositionInQuarters_equals_6() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(4);
		Mu child = new Mu("child");
		mu.addMu(child, new BarsAndBeats(1, 2.0));
		assertEquals(6.0, child.getGlobalPositionInQuarters());
	}
	
	
	@Test
	void given_a_child_at_a_negative_position_in_bars_then_globalPositionInBars_returns_correct_value() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		mu.addMu(child, -1);
		assertEquals(-1, child.getGlobalPositionInBars(0));
	}
	
	
	@Test
	void given_a_grandchild_at_negative_position_in_bars_then_returns_correct_globalPositionInQuarters() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		mu.addMu(child, 2);
		Mu grandchild = new Mu("grandchild");
		child.addMu(grandchild, -1);
		assertEquals(4.0, grandchild.getGlobalPositionInQuarters());
	}
	
	
	@Test
	void given_a_grandchild_at_negative_position_in_bars_and_3_4_time_then_returns_correct_globalPositionInQuarters() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		Mu child = new Mu("child");
//		child.setLengthInBars(1);
		mu.addMu(child, 2);
		Mu grandchild = new Mu("grandchild");
		child.addMu(grandchild, -1);
		assertEquals(3.0, grandchild.getGlobalPositionInQuarters());
	}
	
	
	@Test
	void given_a_child_at_a_negative_position_in_bars_and_beats_then_globalPositionInQuarters_returns_correct_value() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		mu.setLengthInBars(1);			// need to set the parent to other than getLengthFromChildren
		mu.addMu(child, new BarsAndBeats(-1, 1.0));
		assertEquals(-3.0, child.getGlobalPositionInQuarters());
	}
	
	
	@Test
	void given_a_grandchild_at_a_negative_position_in_bars_and_beats_then_globalPositionInQuarters_returns_correct_value() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		child.setLengthInBars(4);
		Mu grandchild = new Mu("grandchild");
		mu.addMu(child, 0);
		child.addMu(grandchild, new BarsAndBeats(-1, 1.0));
		assertEquals(-3.0, grandchild.getGlobalPositionInQuarters());
	}
	
	
	
	@Test
	void given_4_4_time_when_global_positionInFloatBars_is_1pnt5_then_globalPositionInQuartes_equals_6() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(4);
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
		assertEquals(6.0, mu.getGlobalPositionInQuartersFromGlobalPositionInFloatBars(1.5));
	}
	
	
	@Test
	void given_4_4_time_when_global_positionInFloatBars_is_1pnt5_then_globalPositionInBarsANdBeats_equals_1_bar_2_beats() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(4);
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
		assertEquals(1, mu.getGlobalPositionInBarsAndBeatsFromGlobalPositionInFloatBars(1.5).getBarPosition());
		assertEquals(2.0, mu.getGlobalPositionInBarsAndBeatsFromGlobalPositionInFloatBars(1.5).getOffsetInQuarters());
	}
	
	
	@Test
	void given_3_4_time_when_global_positionInFloatBars_is_1pnt5_then_globalPositionInQuartes_equals_4pnt5() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(4);
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		assertEquals(4.5, mu.getGlobalPositionInQuartersFromGlobalPositionInFloatBars(1.5));
	}
	
	
	@Test
	void given_3_4_time_when_global_positionInFloatBars_is_1pnt5_then_globalPositionInBarsANdBeats_equals_1_bar_1pnt5_beats() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(4);
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		assertEquals(1, mu.getGlobalPositionInBarsAndBeatsFromGlobalPositionInFloatBars(1.5).getBarPosition());
		assertEquals(1.5, mu.getGlobalPositionInBarsAndBeatsFromGlobalPositionInFloatBars(1.5).getOffsetInQuarters());
	}
}
