package test.java.com.dougron.mucus.mu_framework.ruler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class TimeSignatureGenAndMap_Test
{

	@Test
	final void instantiates()
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		assertNotNull(tsgm);
	}
	
	
	@Test
	void getTimeSignature_returns_default_for_any_bar_index() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		assertEquals(TimeSignature.FOUR_FOUR, tsgm.getTimeSignature(0));
		assertEquals(TimeSignature.FOUR_FOUR, tsgm.getTimeSignature(-10));
		assertEquals(TimeSignature.FOUR_FOUR, tsgm.getTimeSignature(1000));
	}
	
	
	@Test
	void when_using_single_time_signature_generator_then_returns_that_time_signature_for_any_bar_index() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		assertEquals(TimeSignature.THREE_FOUR, tsgm.getTimeSignature(0));
		assertEquals(TimeSignature.THREE_FOUR, tsgm.getTimeSignature(-10));
		assertEquals(TimeSignature.THREE_FOUR, tsgm.getTimeSignature(1000));
	}
	
	
	@Nested
	class when_using_repeating_time_signature_generator
	{
		@Test
		void _returns_correct_time_signature_at_all_positive_bar_indices() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			
			TimeSignature[] tsArr = new TimeSignature[]
					{
							TimeSignature.FIVE_EIGHT_32,
							TimeSignature.SIX_EIGHT,
							TimeSignature.SEVEN_EIGHT_322
					};
			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr ));
			assertEquals(TimeSignature.FIVE_EIGHT_32, tsgm.getTimeSignature(0));
			assertEquals(TimeSignature.SIX_EIGHT, tsgm.getTimeSignature(1));
			assertEquals(TimeSignature.SEVEN_EIGHT_322, tsgm.getTimeSignature(2));
			assertEquals(TimeSignature.FIVE_EIGHT_32, tsgm.getTimeSignature(3));
			assertEquals(TimeSignature.SIX_EIGHT, tsgm.getTimeSignature(4));
			assertEquals(TimeSignature.SEVEN_EIGHT_322, tsgm.getTimeSignature(5));
		}
		
		
		@Test
		void _returns_correct_time_signature_at_all_negative_bar_indices() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			
			TimeSignature[] tsArr = new TimeSignature[]
					{
							TimeSignature.FIVE_EIGHT_32,
							TimeSignature.SIX_EIGHT,
							TimeSignature.SEVEN_EIGHT_322
					};
			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr ));
			assertEquals(TimeSignature.FIVE_EIGHT_32, tsgm.getTimeSignature(-3));
			assertEquals(TimeSignature.SIX_EIGHT, tsgm.getTimeSignature(-2));
			assertEquals(TimeSignature.SEVEN_EIGHT_322, tsgm.getTimeSignature(-1));
			assertEquals(TimeSignature.FIVE_EIGHT_32, tsgm.getTimeSignature(3));
			assertEquals(TimeSignature.SIX_EIGHT, tsgm.getTimeSignature(4));
			assertEquals(TimeSignature.SEVEN_EIGHT_322, tsgm.getTimeSignature(5));
		}
	}
	
			
	@Test
	void when_a_bar_index_is_superimposed_then_returns_correct_time_signature_from_that_bar_index() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tsgm.addTimeSignature(4, TimeSignature.THREE_FOUR);
		assertEquals(TimeSignature.THREE_FOUR, tsgm.getTimeSignature(4));
	}
	
	
	@Nested
	class given_a_position_in_bars
	{
		@Test
		void _then_returns_correct_position_in_quarters() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(0.0, tsgm.getPositionInQuarters(0));
			assertEquals(4.0, tsgm.getPositionInQuarters(1));
			assertEquals(-4.0, tsgm.getPositionInQuarters(-1));
			assertEquals(40.0, tsgm.getPositionInQuarters(10));
			assertEquals(-36.0, tsgm.getPositionInQuarters(-9));
		}
		
		
		@Test
		void _and_a_complex_time_signature_then_returns_correct_position_in_quarters() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();

			TimeSignature[] tsArr = new TimeSignature[]
					{
							TimeSignature.FIVE_EIGHT_32,
							TimeSignature.SIX_EIGHT,
							TimeSignature.SEVEN_EIGHT_322
					};
			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr ));
			assertEquals(2.5, tsgm.getPositionInQuarters(1));
			assertEquals(-3.5, tsgm.getPositionInQuarters(-1));
			assertEquals(11.5, tsgm.getPositionInQuarters(4));
			assertEquals(-12.5, tsgm.getPositionInQuarters(-4));
		}
	}	
	
	
	@Test
	void given_a_positive_position_in_bars_and_a_superimposed_time_signature_then_returns_correct_positionInQuarters() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tsgm.addTimeSignature(4, TimeSignature.THREE_FOUR);
		assertEquals(16.0, tsgm.getPositionInQuarters(4));
		assertEquals(19.0, tsgm.getPositionInQuarters(5));
		assertEquals(23.0, tsgm.getPositionInQuarters(6));
	}

	
	@Test
	void given_a_negative_position_in_bars_and_a_superimposed_time_signature_then_returns_correct_positionInQuarters() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tsgm.addTimeSignature(-2, TimeSignature.THREE_FOUR);
		assertEquals(-4.0, tsgm.getPositionInQuarters(-1));
		assertEquals(-7.0, tsgm.getPositionInQuarters(-2));
		assertEquals(-11.0, tsgm.getPositionInQuarters(-3));
	}

	
	@Test
	void position_in_bars_and_beats_returns_correct_position_in_quarters() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		assertEquals(5.0, tsgm.getPositionInQuarters(new BarsAndBeats(1, 1.0)));
		assertEquals(6.0, tsgm.getPositionInQuarters(new BarsAndBeats(2, -2.0)));
		assertEquals(-7.0, tsgm.getPositionInQuarters(new BarsAndBeats(-2, 1.0)));
		assertEquals(-5.0, tsgm.getPositionInQuarters(new BarsAndBeats(-1, -1.0)));
	}
	
	
	@Nested
	class given_a_positive_position_in_quarters
	{
		@Test
		void _returns_correct_position_in_bars_and_beats() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			BarsAndBeats bab = tsgm.getPositionInBarsAndBeats(7.0);
			assertEquals(1, bab.getBarPosition());
			assertEquals(3.0, bab.getOffsetInQuarters());
			BarsAndBeats bab2 = tsgm.getPositionInBarsAndBeats(9.0);
			assertEquals(2, bab2.getBarPosition());
			assertEquals(1.0, bab2.getOffsetInQuarters());
			BarsAndBeats bab3 = tsgm.getPositionInBarsAndBeats(12.0);
			assertEquals(3, bab3.getBarPosition());
			assertEquals(0.0, bab3.getOffsetInQuarters());
		}
		
		
		@Test
		void _and_superimposed_time_signature_returns_correct_position_in_bars_and_beats() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			tsgm.addTimeSignature(4, TimeSignature.THREE_FOUR);
			BarsAndBeats bab = tsgm.getPositionInBarsAndBeats(20.0);
			assertEquals(5, bab.getBarPosition());
			assertEquals(1.0, bab.getOffsetInQuarters());
		}
		
		
		@Test
		void _then_returns_correct_bar_index() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			tsgm.addTimeSignature(4, TimeSignature.THREE_FOUR);
			assertEquals(5, tsgm.getBarPosition(20.0));
			assertEquals(6, tsgm.getBarPosition(23.0));
		}
	}
	
	
	@Nested
	class given_a_negative_position_in_quarters
	{
		@Test
		void _returns_correct_position_in_bars_and_beats() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			BarsAndBeats bab = tsgm.getPositionInBarsAndBeats(-7.0);
			assertEquals(-2, bab.getBarPosition());
			assertEquals(1.0, bab.getOffsetInQuarters());
			BarsAndBeats bab2 = tsgm.getPositionInBarsAndBeats(-12.0);
			assertEquals(-3, bab2.getBarPosition());
			assertEquals(0.0, bab2.getOffsetInQuarters());
		}
		
		
		@Test
		void _and_superimposed_time_signature_returns_correct_position_in_bars_and_beats() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			tsgm.addTimeSignature(-2, TimeSignature.THREE_FOUR);
			BarsAndBeats bab = tsgm.getPositionInBarsAndBeats(-9.0);
			assertEquals(-3, bab.getBarPosition());
			assertEquals(2.0, bab.getOffsetInQuarters());
		}
		
		
		@Test
		void _then_returns_correct_bar_index() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			tsgm.addTimeSignature(-2, TimeSignature.THREE_FOUR);
			assertEquals(-2, tsgm.getBarPosition(-7.0));
			assertEquals(-3, tsgm.getBarPosition(-8.0));
		}
	}

	
	@Nested
	class given_default_time_signature
	{
		@Test
		void _when_11_quarters_is_passed_then_returns_2_75_floatbars() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(2.75, tsgm.getQuartersPositionInFloatBars(11.0));
		}
		
		
		@Test
		void _when_2_bars_1_beat_is_passed_then_returns_2_25_floatbars() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(2.25, tsgm.getBarsAndBeatsPositionInFloatBars(new BarsAndBeats(2, 1.0)));
		}
		
		
		@Test
		void _then_float_bar_position_of_1_5_returns_position_of_6_quarters() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(6.0, tsgm.getFloatBarsPositionInQuarters(1.5));
		}
	}
	
	
	@Nested
	class given_3_4_signature
	{
		@Test
		void _when_11_quarters_is_passed_then_returns_3_6666_floatbars() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
			assertEquals(3.6666, tsgm.getQuartersPositionInFloatBars(11.0), 0.001);
		}
				
		
		@Test
		void given_3_4_signature_when_2_bars_1_beat_is_passed_then_returns_2_3333_floatbars() throws Exception
		{
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
			assertEquals(2.3333, tsgm.getBarsAndBeatsPositionInFloatBars(new BarsAndBeats(2, 1.0)), 0.001);
		}		
	}
	
	
	@Test
	void given_5_8_time_signature_then_float_bar_position_of_1_5_returns_position_of_3_75_quarters() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(TimeSignature.FIVE_EIGHT_32);		
		assertEquals(3.75, tsgm.getFloatBarsPositionInQuarters(1.5));
	}
	

// getClosestTactus stuff ------------------------------------------------------
	
	@Test
	void given_default_time_signature_when_passed_position_in_floatbars_then_getClosestTactusInQuarters_returns_correct_values() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		assertEquals(0.0, tsgm.getClosestTactusInQuarters(0.0));
		assertEquals(10.0, tsgm.getClosestTactusInQuarters(2.5));
		assertEquals(81.0, tsgm.getClosestTactusInQuarters(20.15));
	}
	
	
	@Test
	void given_7_8_time_signature_when_passed_position_in_floatbars_then_getClosestTactusInQuarters_returns_correct_values() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(TimeSignature.SEVEN_EIGHT_322);
		assertEquals(0.0, tsgm.getClosestTactusInQuarters(0.0));
		assertEquals(8.5, tsgm.getClosestTactusInQuarters(2.5));
		assertEquals(70.0, tsgm.getClosestTactusInQuarters(20.15));
	}
	
	
	@Test
	void given_repeating_time_signature_when_passed_position_in_floatbars_then_getClosestTactusInQuarters_returns_correct_values() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		
		TimeSignature[] tsArr = new TimeSignature[]
				{
						TimeSignature.FIVE_EIGHT_32,
						TimeSignature.SIX_EIGHT,
						TimeSignature.SEVEN_EIGHT_322
				};
		tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr ));
		assertEquals(0.0, tsgm.getClosestTactusInQuarters(0.0));
		assertEquals(7.0, tsgm.getClosestTactusInQuarters(2.5));
		assertEquals(59.5, tsgm.getClosestTactusInQuarters(20.15));
	}
	
	
// getClosestSubTactus stuff ---------------------------------------------------------
	
		
	@Test
	void given_default_time_signature_when_passed_position_in_floatbars_then_getClosestSubTactusInQuarters_returns_correct_values() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		assertEquals(0.0, tsgm.getClosestSubTactusInQuarters(0.0));
		assertEquals(9.5, tsgm.getClosestSubTactusInQuarters(2.4));
		assertEquals(80.5, tsgm.getClosestSubTactusInQuarters(20.15));
	}
	
	
	@Test
	void given_7_8_time_signature_when_passed_position_in_floatbars_then_getClosestSubTactusInQuarters_returns_correct_values() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(TimeSignature.SEVEN_EIGHT_322);
		assertEquals(0.0, tsgm.getClosestSubTactusInQuarters(0.0));
		assertEquals(8.0, tsgm.getClosestSubTactusInQuarters(2.3));
		assertEquals(70.5, tsgm.getClosestSubTactusInQuarters(20.15));
	}
	
	
	@Test
	void given_repeating_time_signature_when_passed_position_in_floatbars_then_getClosestSubTactusInQuarters_returns_correct_values() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		
		TimeSignature[] tsArr = new TimeSignature[]
				{
						TimeSignature.FIVE_EIGHT_32,
						TimeSignature.SIX_EIGHT,
						TimeSignature.SEVEN_EIGHT_322
				};
		tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
		assertEquals(0.0, tsgm.getClosestSubTactusInQuarters(0.0));
		assertEquals(6.5, tsgm.getClosestSubTactusInQuarters(2.3));
		assertEquals(60.0, tsgm.getClosestSubTactusInQuarters(20.15));
	}
	
	
	@Test
	void deepcopy_returns_equivalent_deep_copy() throws Exception
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		
		TimeSignature[] tsArr = new TimeSignature[]
				{
						TimeSignature.FIVE_EIGHT_32,
						TimeSignature.SIX_EIGHT,
						TimeSignature.SEVEN_EIGHT_322
				};
		tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
		tsgm.addTimeSignature(7, TimeSignature.THREE_FOUR);
//		System.out.println(tsgm.contentString());
		assertEquals(tsgm.contentString(), tsgm.getDeepCopy().contentString());
	}
}
