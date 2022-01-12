package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.position_model.BeginningOfParentInBars;
import main.java.com.dougron.mucus.mu_framework.position_model.BeginningOfParentInBarsAnBeats;
import main.java.com.dougron.mucus.mu_framework.position_model.EndOfSiblingInBars;
import main.java.com.dougron.mucus.mu_framework.position_model.EndOfSiblingInBarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.position_model.EndOfSiblingInQuarters;

class Mu_PositionTests
{


	@Nested
	class parent_returns_position_of
	{
		@Test
		void _0_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			assertEquals(0, parent.getPositionInBars());
		}
		
		
		@Test
		void _0_0_bars_and_beats() throws Exception
		{
			Mu parent = new Mu("parent");
			BarsAndBeats bab = parent.getPositionInBarsAndBeats();
			assertEquals(0, bab.getBarPosition());
			assertEquals(0.0, bab.getOffsetInQuarters());
		}
		
		
		@Test
		void _0_quarters() throws Exception
		{
			Mu parent = new Mu("parent");
			assertEquals(0.0, parent.getPositionInQuarters());
		}
	}
	
	
	@Nested 
	class given_parent_with_child_at
	{
		
		@Nested
		class _1_bar_then_child_returns_position_of
		{
			@Test
			void _1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 1);
				assertEquals(1, child.getPositionInBars());
			}
			
			
			@Test
			void _1_0_bars_and_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 1);
				BarsAndBeats bab = child.getPositionInBarsAndBeats();
				assertEquals(1, bab.getBarPosition());
				assertEquals(0.0, bab.getOffsetInQuarters());
			}
			
			
			@Test
			void _4_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 1);
				assertEquals(4.0, child.getPositionInQuarters());
			}
		}
		
		
		@Nested
		class _minus_1_bar_then_child_returns_position_of
		{
			@Test
			void _minus_1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, -1);
				assertEquals(-1, child.getPositionInBars());
			}
			
			
			@Test
			void _minus_1_0_bars_and_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, -1);
				BarsAndBeats bab = child.getPositionInBarsAndBeats();
				assertEquals(-1, bab.getBarPosition());
				assertEquals(0.0, bab.getOffsetInQuarters());
			}
			

			@Test
			void _4_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, -1);
				assertEquals(-4.0, child.getPositionInQuarters());
			}
		}
		

		@Nested
		class _1_1_bars_and_beats_then_child_returns_position_of
		{

			@Test
			void _1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(1, 1.0));
				assertEquals(1, child.getPositionInBars());
			}
			
			
			@Test
			void _1_1_bars_and_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(1, 1.0));
				BarsAndBeats bab = child.getPositionInBarsAndBeats();
				assertEquals(1, bab.getBarPosition());
				assertEquals(1.0, bab.getOffsetInQuarters());
			}
			
			
			@Test
			void _5_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(1, 1.0));
				assertEquals(5.0, child.getPositionInQuarters());
			}
		}
		

		@Nested
		class _1_bars_minus_1_beats_then_child_returns_position_of
		{
			
			@Test
			void _0_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(1, -1.0));
				assertEquals(0, child.getPositionInBars());
			}
			
			
			@Test
			void _0_3_bars_and_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(1, -1.0));
				BarsAndBeats bab = child.getPositionInBarsAndBeats();
				assertEquals(0, bab.getBarPosition());
				assertEquals(3.0, bab.getOffsetInQuarters());
			}
			
			
			@Test
			void _3_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(1, -1.0));
				assertEquals(3.0, child.getPositionInQuarters());
			}
		
		}
		
		
		@Nested
		class _minus_1_bars_1_beats_then_child_returns_position_of
		{
			
			@Test
			void _minus_1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(-1, 1.0));
				assertEquals(-1, child.getPositionInBars());
			}
			

			@Test
			void _minus_1_1_bars_and_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(-1, 1.0));
				BarsAndBeats bab = child.getPositionInBarsAndBeats();
				assertEquals(-1, bab.getBarPosition());
				assertEquals(1.0, bab.getOffsetInQuarters());
			}
			

			@Test
			void _3_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, new BarsAndBeats(-1, 1.0));
				assertEquals(-3.0, child.getPositionInQuarters());
			}
		
		}
		
		
		@Nested
		class _6_quarters_returns_then_child_position_of
		{
			
			@Test
			void _1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 6.0);
				assertEquals(1, child.getPositionInBars());
			}
			
		
			@Test
			void _1_2_bars_and_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 6.0);
				BarsAndBeats bab = child.getPositionInBarsAndBeats();
				assertEquals(1, bab.getBarPosition());
				assertEquals(2.0, bab.getOffsetInQuarters());
			}
			
			
			@Test
			void _6_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, 6.0);
				assertEquals(6.0, child.getPositionInQuarters());
			}
		
		}


		@Nested
		class _minus_7_quarters_then_child_returns_position_of
		{
			
			@Test
			void _minus_2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, -7.0);
				assertEquals(-2, child.getPositionInBars());
			}
			
			
			@Test
			void _minus2_1_bars_and_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, -7.0);
				BarsAndBeats bab = child.getPositionInBarsAndBeats();
				assertEquals(-2, bab.getBarPosition());
				assertEquals(1.0, bab.getOffsetInQuarters());
			}
			
			
			@Test
			void _minus_7_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				parent.addMu(child, -7.0);
				assertEquals(-7.0, child.getPositionInQuarters());
			}
		
		}

	}

	
	@Nested
	class given_child_at_1_bar_and_grandchild_at
	{
		@Nested
		class _2_bar_then_grandchild_returns_position_of
		{
			@Test
			void _2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 2);
				parent.addMu(child, 1);
				assertEquals(2, grandchild.getPositionInBars());			
			}

		
			@Test
			void _2_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 2);
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getPositionInBarsAndBeats();
				assertEquals(2, bab.getBarPosition());
				assertEquals(0.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _8_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 2);
				parent.addMu(child, 1);
				assertEquals(8.0, grandchild.getPositionInQuarters());			
			}

		}

		
		@Nested
		class _minus_2_bar_then_grandchild_returns_position_of
		{
			@Test
			void _minus_2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -2);
				parent.addMu(child, 1);
				assertEquals(-2, grandchild.getPositionInBars());			
			}


			@Test
			void _minus_2_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -2);
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getPositionInBarsAndBeats();
				assertEquals(-2, bab.getBarPosition());
				assertEquals(0.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _minus_8_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -2);
				parent.addMu(child, 1);
				assertEquals(-8.0, grandchild.getPositionInQuarters());			
			}

		}


		@Nested
		class _2_bar_3_beats_then_grandchild_returns_position_of
		{

			@Test
			void _2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, 3.0));
				parent.addMu(child, 1);
				assertEquals(2, grandchild.getPositionInBars());			
			}


			@Test
			void _2_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, 3.0));
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getPositionInBarsAndBeats();
				assertEquals(2, bab.getBarPosition());
				assertEquals(3.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _11_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, 3.0));
				parent.addMu(child, 1);
				assertEquals(11.0, grandchild.getPositionInQuarters());			
			}

		}


		@Nested
		class _minus_2_bar_1_beats_then_grandchild_returns_position_of
		{

			@Test
			void _minus_2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-2, 1.0));
				parent.addMu(child, 1);
				assertEquals(-2, grandchild.getPositionInBars());			
			}


			@Test
			void _minus_2_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-2, 1.0));
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getPositionInBarsAndBeats();
				assertEquals(-2, bab.getBarPosition());
				assertEquals(1.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _minus_7_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-2, 1.0));
				parent.addMu(child, 1);
				assertEquals(-7.0, grandchild.getPositionInQuarters());			
			}

		}

		
		@Nested
		class _2_bar_minus_1_beats_then_grandchild_returns_position_of
		{

			@Test
			void _1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, -1.0));
				parent.addMu(child, 1);
				assertEquals(1, grandchild.getPositionInBars());			
			}


			@Test
			void _1_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, -1.0));
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getPositionInBarsAndBeats();
				assertEquals(1, bab.getBarPosition());
				assertEquals(3.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _7_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(2, -1.0));
				parent.addMu(child, 1);
				assertEquals(7.0, grandchild.getPositionInQuarters());			
			}
		}	


		@Nested
		class _minus_3_bar_minus_1_beats_then_grandchild_returns_position_of
		{

			@Test
			void _minus_4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-3, -1.0));
				parent.addMu(child, 1);
				assertEquals(-4, grandchild.getPositionInBars());			
			}


			@Test
			void _minus_4_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-3, -1.0));
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getPositionInBarsAndBeats();
				assertEquals(-4, bab.getBarPosition());
				assertEquals(3.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _minus_13_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, new BarsAndBeats(-3, -1.0));
				parent.addMu(child, 1);
				assertEquals(-13.0, grandchild.getPositionInQuarters());			
			}

		}

		
		@Nested
		class _6_quarters_then_grandchild_returns_position_of
		{
			@Test
			void _1_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 6.0);
				parent.addMu(child, 1);
				assertEquals(1, grandchild.getPositionInBars());			
			}


			@Test
			void _1_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 6.0);
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getPositionInBarsAndBeats();
				assertEquals(1, bab.getBarPosition());
				assertEquals(2.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _6_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, 6.0);
				parent.addMu(child, 1);
				assertEquals(6.0, grandchild.getPositionInQuarters());			
			}

		}


		@Nested
		class _minus_6_quarters_then_grandchild_returns_position_of
		{
			@Test
			void _minus_2_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -6.0);
				parent.addMu(child, 1);
				assertEquals(-2, grandchild.getPositionInBars());			
			}


			@Test
			void _minus_2_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -6.0);
				parent.addMu(child, 1);
				BarsAndBeats bab = grandchild.getPositionInBarsAndBeats();
				assertEquals(-2, bab.getBarPosition());
				assertEquals(2.0, bab.getOffsetInQuarters());			
			}


			@Test
			void _minus_6_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				child.addMu(grandchild, -6.0);
				parent.addMu(child, 1);
				assertEquals(-6.0, grandchild.getPositionInQuarters());			
			}

		}
		
	}
			

// grandchild at bars position
	
	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_length_3_bars_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBars());
			}
			
			
			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(20.0, grandsibling.getPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_position_of
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
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}
			
			
			@Test
			void _7_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _29_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(29.0, grandsibling.getPositionInQuarters());
			}
		}
	
	
		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
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
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBars());
			}
			
			
			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(26.0, grandsibling.getPositionInQuarters());
			}
		}
	
	}
		

	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_length_3_bars_2_beats_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBars());
			}
			
			
			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
		
			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(22.0, grandsibling.getPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}
			
			
			@Test
			void _7_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _29_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(29.0, grandsibling.getPositionInQuarters());
			}
		}
	
	
		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}
			
			
			@Test
			void _7_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _28_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(28.0, grandsibling.getPositionInQuarters());
			}
		}
	
	}
	
	
	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_length_9_quarters_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(4, grandsibling.getPositionInBars());
			}
			
			
			@Test
			void _4_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(4, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
		
			@Test
			void _17_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(17.0, grandsibling.getPositionInQuarters());
			}
		}
		
		
		@Nested
		class _grandsibling_at_2_bars_2_beats_returns_position_of
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
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(6, grandsibling.getPositionInBars());
			}
			
			
			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(6, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}
			
			
			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(26.0, grandsibling.getPositionInQuarters());
			}
		}
	
		
		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
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
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(5, grandsibling.getPositionInBars());
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
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(5, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(3.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
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
				child.addMu(grandchild, 2);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(23.0, grandsibling.getPositionInQuarters());
			}
		}
	
	}


// grandchild at bars and beats position
	
	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_1_beat_length_3_bars_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBars());
			}


			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(20.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_position_of
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
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}


			@Test
			void _7_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _29_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(29.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
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
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBars());
			}


			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(26.0, grandsibling.getPositionInQuarters());
			}
		}	
	}


	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_1_beat_length_3_bars_2_beats_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBars());
			}


			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(22.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}


			@Test
			void _7_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _29_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(29.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}


			@Test
			void _7_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _28_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(28.0, grandsibling.getPositionInQuarters());
			}
		}	
	}
	
	
	@Nested
	class given_child_at_1_bar_and_grandchild_at_2_bars_1_beat_length_9_quarters_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(4, grandsibling.getPositionInBars());
			}


			@Test
			void _4_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(4, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _18_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(18.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_2_beats_returns_position_of
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
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(6, grandsibling.getPositionInBars());
			}


			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(6, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(26.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
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
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBars());
			}


			@Test
			void _6_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _24_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, new BarsAndBeats(2, 1.0));
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(24.0, grandsibling.getPositionInQuarters());
			}
		}

	}
	
	
// grandchild at quarters position
	
	@Nested
	class given_child_at_1_bar_and_grandchild_at_10_quarters_length_3_bars_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBars());
			}


			@Test
			void _5_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _20_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(20.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_position_of
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
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}


			@Test
			void _7_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _29_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(29.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
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
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBars());
			}


			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBars(3);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(26.0, grandsibling.getPositionInQuarters());
			}
		}	
	}


	@Nested
	class given_child_at_1_bar_and_grandchild_at_10_quarters_length_3_bars_2_beats_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _5_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBars());
			}


			@Test
			void _5_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(5, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _22_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(22.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_1_beat_returns_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}


			@Test
			void _7_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _29_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 1.0), grandchild);
				assertEquals(29.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
		{
			@Test
			void _7_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getPositionInBars());
			}


			@Test
			void _7_bars_0_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(7, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(0.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _28_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInBarsAndBeats(new BarsAndBeats(3, 2.0));
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(28.0, grandsibling.getPositionInQuarters());
			}
		}	
	}


	@Nested
	class given_child_at_1_bar_and_grandchild_at_10_quarters_length_9_quarters_and
	{
		@Nested
		class _grandsibling_at_0_bars_returns_position_of
		{
			@Test
			void _4_bars() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(4, grandsibling.getPositionInBars());
			}


			@Test
			void _4_bars_3_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(4, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(3.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _19_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 0, grandchild);
				assertEquals(19.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_2_bars_2_beats_returns_position_of
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
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(6, grandsibling.getPositionInBars());
			}


			@Test
			void _6_bars_2_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(6, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(2.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _26_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, new BarsAndBeats(2, 2.0), grandchild);
				assertEquals(26.0, grandsibling.getPositionInQuarters());
			}
		}


		@Nested
		class _grandsibling_at_6_quarters_returns_position_of
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
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBars());
			}


			@Test
			void _6_bars_1_beats() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(6, grandsibling.getPositionInBarsAndBeats().getBarPosition());
				assertEquals(1.0, grandsibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			}


			@Test
			void _25_quarters() throws Exception
			{
				Mu parent = new Mu("parent");
				Mu child = new Mu("child");
				Mu grandchild = new Mu("grandchild");
				Mu grandsibling = new Mu("grandsibling");
				parent.addMu(child,  1);
				grandchild.setLengthInQuarters(9.0);
				child.addMu(grandchild, 10.0);
				child.addMuToEndOfSibling(grandsibling, 6.0, grandchild);
				assertEquals(25.0, grandsibling.getPositionInQuarters());
			}
		}

	}


// ---- random unclassified stuff that arose in testing other things
	
	@Test
	void given_child_at_1_quarter_and_grandchild_at_2_then_grandchild_position_in_quarters_is_2() throws Exception
	{
		// ..... relative to immediate parent
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		parent.addMu(child, 1.0);
		Mu grandchild = new Mu("child");
		child.addMu(grandchild, 2.0);
		assertEquals(2.0, grandchild.getPositionInQuarters());
	}
	
	
	@Test
	void given_child_at_1_quarter_and_grandchild_at_2_then_grandchild_position_is_0_bars_2_beats() throws Exception
	{
		// ..... relative to immediate parent
		Mu parent = new Mu("parent");
		Mu child = new Mu("child");
		parent.addMu(child, 1.0);
//		assertEquals(1.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
		Mu grandchild = new Mu("grandchild");
		child.addMu(grandchild, 2.0);
		assertEquals(2.0, grandchild.getPositionInBarsAndBeats().getOffsetInQuarters());
	}
	
	
// movePosition stuff ----------------------------------------------------------------------	
	
	@Nested
	class given_parent_then_movePosition_by
	{
		@Test
		void _2_bars_does_not_change_the_position() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.movePosition(2);
			assertEquals(0.0, parent.getGlobalPositionInQuarters());
		}
		
		@Test
		void _2_quarters_does_not_change_the_position() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.movePosition(2.0);
			assertEquals(0.0, parent.getGlobalPositionInQuarters());
		}
		
		
		@Test
		void _2_bars_1_beat_does_not_change_the_position() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.movePosition(new BarsAndBeats(2, 1.0));
			assertEquals(0.0, parent.getGlobalPositionInQuarters());
		}
	}

	
	@Nested
	class given_child_at_2_bars_when_movePosition_by
	{
		@Test
		void _1_bar_then_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child,  2);
			child.movePosition(1);
			assertEquals(3, child.getPositionInBars());
			assertEquals(12.0, child.getPositionInQuarters());
			assertEquals(3, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(0.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(3, child.getGlobalPositionInBars());
			assertEquals(12.0, child.getGlobalPositionInQuarters());
			assertEquals(3, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(0.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void _1_bar_then_parent_returns_length_of_2_bars_before_move_and_3_afterwards() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child,  2);
			assertEquals(2, parent.getLengthInBars());
			child.movePosition(1);
			assertEquals(3, parent.getLengthInBars());
		}
		
		
		@Test
		void _1_quarter_then_changes_to_position_in_bars_and_beats_and_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.movePosition(1.0);
			assertEquals(BeginningOfParentInBarsAnBeats.class, child.getClassOfPositionModel());
			assertEquals(2, child.getPositionInBars());
			assertEquals(9.0, child.getPositionInQuarters());
			assertEquals(2, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(2, child.getGlobalPositionInBars());
			assertEquals(9.0, child.getGlobalPositionInQuarters());
			assertEquals(2, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
	
		@Test
		void _1_bar_2_beats_then_changes_to_position_in_bars_and_beats_and_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.movePosition(new BarsAndBeats(1, 2.0));
			assertEquals(BeginningOfParentInBarsAnBeats.class, child.getClassOfPositionModel());
			assertEquals(3, child.getPositionInBars());
			assertEquals(14.0, child.getPositionInQuarters());
			assertEquals(3, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(3, child.getGlobalPositionInBars());
			assertEquals(14.0, child.getGlobalPositionInQuarters());
			assertEquals(3, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
	
	}

	
	@Nested
	class given_child_at_2_bars_2_beats_when_movePosition_by
	{
		@Test
		void _1_bar_then_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child,  new BarsAndBeats(2, 2.0));
			child.movePosition(1);
			assertEquals(3, child.getPositionInBars());
			assertEquals(12.0, child.getPositionInQuarters());
			assertEquals(3, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(0.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(3, child.getGlobalPositionInBars());
			assertEquals(12.0, child.getGlobalPositionInQuarters());
			assertEquals(3, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(0.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void _1_bar_then_parent_returns_length_of_3_bars_before_move_and_3_afterwards() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child,  new BarsAndBeats(2, 2.0));
			assertEquals(3, parent.getLengthInBars());
			child.movePosition(1);
			assertEquals(3, parent.getLengthInBars());
		}
		
		
		@Test
		void _1_quarter_then_changes_to_position_in_bars_and_beats_and_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child,  new BarsAndBeats(2, 2.0));
			child.movePosition(1.0);
			assertEquals(BeginningOfParentInBarsAnBeats.class, child.getClassOfPositionModel());
			assertEquals(2, child.getPositionInBars());
			assertEquals(11.0, child.getPositionInQuarters());
			assertEquals(2, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(3.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(2, child.getGlobalPositionInBars());
			assertEquals(11.0, child.getGlobalPositionInQuarters());
			assertEquals(2, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(3.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void _1_bar_2_beats_then_changes_to_position_in_bars_and_beats_and_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child,  new BarsAndBeats(2, 2.0));
			child.movePosition(new BarsAndBeats(1, 2.0));
			assertEquals(BeginningOfParentInBarsAnBeats.class, child.getClassOfPositionModel());
			assertEquals(3, child.getPositionInBars());
			assertEquals(14.0, child.getPositionInQuarters());
			assertEquals(3, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(3, child.getGlobalPositionInBars());
			assertEquals(14.0, child.getGlobalPositionInQuarters());
			assertEquals(3, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
	
	}
	
	
	@Nested
	class given_child_at_10_quarters_when_movePosition_by
	{
		@Test
		void _1_bar_then_changes_to_position_in_bars_and_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child,  10.0);
			child.movePosition(1);
			assertEquals(BeginningOfParentInBars.class, child.getClassOfPositionModel());
			assertEquals(3, child.getPositionInBars());
			assertEquals(12.0, child.getPositionInQuarters());
			assertEquals(3, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(0.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(3, child.getGlobalPositionInBars());
			assertEquals(12.0, child.getGlobalPositionInQuarters());
			assertEquals(3, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(0.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void _1_bar_then_parent_returns_length_of_3_bars_before_move_and_3_afterwards() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child,  10.0);
			assertEquals(3, parent.getLengthInBars());
			child.movePosition(1);
			assertEquals(3, parent.getLengthInBars());
		}
		
		
		@Test
		void _1_quarter_then_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 10.0);
			child.movePosition(1.0);
			assertEquals(2, child.getPositionInBars());
			assertEquals(11.0, child.getPositionInQuarters());
			assertEquals(2, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(3.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(2, child.getGlobalPositionInBars());
			assertEquals(11.0, child.getGlobalPositionInQuarters());
			assertEquals(2, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(3.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void _1_bar_2_beats_then_changes_to_position_in_bars_and_beats_and_returns_correct_positions_and_global_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 10.0);
			child.movePosition(new BarsAndBeats(1, 2.0));
			assertEquals(BeginningOfParentInBarsAnBeats.class, child.getClassOfPositionModel());
			assertEquals(3, child.getPositionInBars());
			assertEquals(14.0, child.getPositionInQuarters());
			assertEquals(3, child.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, child.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(3, child.getGlobalPositionInBars());
			assertEquals(14.0, child.getGlobalPositionInQuarters());
			assertEquals(3, child.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, child.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
	
	}

	
	@Nested
	class given_child_at_2_bars_length_2_bars_and_sibling_at_0_bars_then
	{
		@Test
		void _movePosition_of_1_bar_returns_sibling_position_of_5_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 0, child);
			sibling.movePosition(1);
			assertEquals(5, sibling.getPositionInBars());
			assertEquals(5, sibling.getGlobalPositionInBars());
		}
		
		
		@Test
		void _movePosition_of_1_bar_1_returns_sibling_positionModel_in_bars_and_beats_and_correct_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 0, child);
			sibling.movePosition(new BarsAndBeats(1, 1.0));
			assertEquals(EndOfSiblingInBarsAndBeats.class, sibling.getClassOfPositionModel());
			assertEquals(5, sibling.getPositionInBars());
			assertEquals(21.0, sibling.getPositionInQuarters());
			assertEquals(5, sibling.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(5, sibling.getGlobalPositionInBars());
			assertEquals(21.0, sibling.getGlobalPositionInQuarters());
			assertEquals(5, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void _movePosition_of_5_quarters_sibling_positionModel_in_bars_and_beats_and_correct_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 0, child);
			sibling.movePosition(5.0);
			assertEquals(EndOfSiblingInBarsAndBeats.class, sibling.getClassOfPositionModel());
			assertEquals(5, sibling.getPositionInBars());
			assertEquals(21.0, sibling.getPositionInQuarters());
			assertEquals(5, sibling.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(5, sibling.getGlobalPositionInBars());
			assertEquals(21.0, sibling.getGlobalPositionInQuarters());
			assertEquals(5, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
	}
	
	
	@Nested
	class given_child_at_2_bars_length_2_bars_2_beats_and_sibling_at_0_bars_then
	{
		@Test
		void _movePosition_of_2_bar_returns_sibling_position_of_6_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 0, child);
			sibling.movePosition(2);
			assertEquals(6, sibling.getPositionInBars());
			assertEquals(6, sibling.getGlobalPositionInBars());
		}
		
		
		@Test
		void _movePosition_of_1_bar_1_returns_sibling_positionModel_in_bars_and_beats_and_correct_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 0, child);
			sibling.movePosition(new BarsAndBeats(1, 1.0));
			assertEquals(EndOfSiblingInBarsAndBeats.class, sibling.getClassOfPositionModel());
			assertEquals(5, sibling.getPositionInBars());
			assertEquals(21.0, sibling.getPositionInQuarters());
			assertEquals(5, sibling.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(5, sibling.getGlobalPositionInBars());
			assertEquals(21.0, sibling.getGlobalPositionInQuarters());
			assertEquals(5, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		
		
		@Test
		void _movePosition_of_5_quarters_sibling_positionModel_in_bars_and_beats_and_correct_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBarsAndBeats(new BarsAndBeats(2, 2.0));
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 0, child);
			sibling.movePosition(5.0);
			assertEquals(EndOfSiblingInBarsAndBeats.class, sibling.getClassOfPositionModel());
			assertEquals(5, sibling.getPositionInBars());
			assertEquals(23.0, sibling.getPositionInQuarters());
			assertEquals(5, sibling.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(3.0, sibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(5, sibling.getGlobalPositionInBars());
			assertEquals(23.0, sibling.getGlobalPositionInQuarters());
			assertEquals(5, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(3.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
	}

	
	@Nested
	class given_child_at_2_bars_length_2_bars_and_sibling_at_1_bars_1_beats_then
	{
		@Test
		void _movePosition_of_1_bar_returns_sibling_position_of_6_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, new BarsAndBeats(1, 1.0), child);
			sibling.movePosition(1);
			assertEquals(6, sibling.getPositionInBars());
			assertEquals(6, sibling.getGlobalPositionInBars());
		}
		
		
		@Test
		void _movePosition_of_1_bar_1_beat_returns_sibling_positionModel_in_bars_and_beats_and_correct_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, new BarsAndBeats(1, 1.0), child);
			sibling.movePosition(new BarsAndBeats(1, 1.0));
			assertEquals(EndOfSiblingInBarsAndBeats.class, sibling.getClassOfPositionModel());
			assertEquals(6, sibling.getPositionInBars());
			assertEquals(25.0, sibling.getPositionInQuarters());
			assertEquals(6, sibling.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(6, sibling.getGlobalPositionInBars());
			assertEquals(25.0, sibling.getGlobalPositionInQuarters());
			assertEquals(6, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		

		@Test
		void _movePosition_of_5_quarters_sibling_positionModel_in_bars_and_beats_and_correct_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, new BarsAndBeats(1, 1.0), child);
			sibling.movePosition(5.0);
			assertEquals(EndOfSiblingInBarsAndBeats.class, sibling.getClassOfPositionModel());
			assertEquals(6, sibling.getPositionInBars());
			assertEquals(26.0, sibling.getPositionInQuarters());
			assertEquals(6, sibling.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, sibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(6, sibling.getGlobalPositionInBars());
			assertEquals(26.0, sibling.getGlobalPositionInQuarters());
			assertEquals(6, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
	}
	
	
	@Nested
	class given_child_at_2_bars_length_2_bars_and_sibling_at_5_quarters_then
	{
		@Test
		void _movePosition_of_1_bar_then_positionModel_is_EndOfSiblingInBars_and_returns_sibling_position_of_6_bars() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 5.0, child);
			sibling.movePosition(1);
			assertEquals(EndOfSiblingInBars.class, sibling.getClassOfPositionModel());
			assertEquals(6, sibling.getPositionInBars());
			assertEquals(6, sibling.getGlobalPositionInBars());
		}
		
		
		@Test
		void _movePosition_of_1_bar_1_beat_returns_sibling_positionModel_in_bars_and_beats_and_correct_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 5.0, child);
			sibling.movePosition(new BarsAndBeats(1, 1.0));
			assertEquals(EndOfSiblingInBarsAndBeats.class, sibling.getClassOfPositionModel());
			assertEquals(6, sibling.getPositionInBars());
			assertEquals(25.0, sibling.getPositionInQuarters());
			assertEquals(6, sibling.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(6, sibling.getGlobalPositionInBars());
			assertEquals(25.0, sibling.getGlobalPositionInQuarters());
			assertEquals(6, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(1.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
		

		@Test
		void _movePosition_of_5_quarters_sibling_positionModel_in_quarters_and_correct_positions() throws Exception
		{
			Mu parent = new Mu("parent");
			Mu child = new Mu("child");
			parent.addMu(child, 2);
			child.setLengthInBars(2);
			Mu sibling = new Mu("sibling");
			parent.addMuToEndOfSibling(sibling, 5.0, child);
			sibling.movePosition(5.0);
			assertEquals(EndOfSiblingInQuarters.class, sibling.getClassOfPositionModel());
			assertEquals(6, sibling.getPositionInBars());
			assertEquals(26.0, sibling.getPositionInQuarters());
			assertEquals(6, sibling.getPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, sibling.getPositionInBarsAndBeats().getOffsetInQuarters());
			assertEquals(6, sibling.getGlobalPositionInBars());
			assertEquals(26.0, sibling.getGlobalPositionInQuarters());
			assertEquals(6, sibling.getGlobalPositionInBarsAndBeats().getBarPosition());
			assertEquals(2.0, sibling.getGlobalPositionInBarsAndBeats().getOffsetInQuarters());
		}
	}
}











