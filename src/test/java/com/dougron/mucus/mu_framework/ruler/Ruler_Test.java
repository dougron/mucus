package test.java.com.dougron.mucus.mu_framework.ruler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.Ruler;
import main.java.com.dougron.mucus.mu_framework.ruler.TimeSignatureListAndGenAndTempoListRuler;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class Ruler_Test
{
	
	Ruler r;
	
	
	@BeforeEach
	private void setup()
	{
		r = new TimeSignatureListAndGenAndTempoListRuler();
	}

	
	@Nested
	public class new_ruler
	{
		@Nested
		public class _has_length_of
		{
			@Test
			void _0_bar_and_0_beats()
			{				
				assertEquals(0, r.getLengthInBarsAndBeats().getBarPosition());
				assertEquals(0.0, r.getLengthInBarsAndBeats().getOffsetInQuarters());
			}
	 
			
			@Test
			void _0_quarters()
			{				
				assertEquals(0.0, r.getLengthInQuarters());
			}
			
			
			@Test
			void _0_seconds()
			{				
				assertEquals(0.0, r.getLengthInSeconds());
			}
		}
		
		
		@Nested
		public class _has_default_tempo_at		// default tempo is set in TempoList
		{
			
			@Test
			void _bars_and_beats_position_0_0() throws Exception
			{				
				assertEquals(120.0, r.getTempoAtBarsAndBeatsPosition(0, 0.0));
			}
			
			
			@Test
			void _quarters_position_0_0() throws Exception
			{				
				assertEquals(120.0, r.getTempoAtQuartersPosition(0.0));
			}
			
			
			@Test
			void _seconds_position_0_0() throws Exception
			{				
				assertEquals(120.0, r.getTempoAtSecondsPosition(0.0));
			}
		
		
		}
		
		
		@Test
		void _hasTimeSignature_equals_false() throws Exception
		{			
			assertFalse(r.hasTimeSignature());
		}
		
		
		@Test
		void _when_time_signature_generator_is_added_then_hasTimeSignature_equals_true() throws Exception
		{			
			r.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
			assertTrue(r.hasTimeSignature());
		}
		
		
		@Test
		void _hasTempo_equals_false() throws Exception
		{			
			assertFalse(r.hasTempo());
		} 
		
		
		@Nested
		class _when_start_tempo_is_set_to_150_then
		{
			@Test
			void _hasTempo_equals_true() throws Exception
			{			
				r.setStartTempo(150);
				assertTrue(r.hasTempo());
			}
			
			
			@Test
			void _tempo_at_0_bars_0_beats_is_150() throws Exception
			{
				r.setStartTempo(150);
				assertEquals(150.0, r.getTempoAtBarsAndBeatsPosition(0, 0.0));
			}
			
			
			@Test
			void _tempo_at_0_quarters_is_150() throws Exception
			{
				r.setStartTempo(150.0);
				assertEquals(150.0, r.getTempoAtQuartersPosition(0.0));
			}
			
			
			@Test
			void _tempo_at_0_seconds_is_150() throws Exception
			{
				r.setStartTempo(150.0);
				assertEquals(150.0, r.getTempoAtSecondsPosition(0.0));
			}
		}
			
		
		@Test
		void _has_no_time_signature_toString() throws Exception
		{			
			assertEquals("", r.getTimeSignatureToString());
		}
		
		
		@Test
		void _returns_default_time_signature() throws Exception
		{			
			assertEquals(TimeSignature.FOUR_FOUR, r.getTimeSignature(1));
		}
		
		
		@Nested
		public class _when_length_is_set_to_4_bars_0_beats
		{
			@Nested
			public class _has_length_of
			{
				@Test
				void _4_bars_0_beats() throws Exception
				{					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					assertEquals(4, r.getLengthInBarsAndBeats().getBarPosition());
					assertEquals(0.0, r.getLengthInBarsAndBeats().getOffsetInQuarters());
				}
				
				
				@Test
				void _16_quarters() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					assertEquals(16.0, r.getLengthInQuarters());
				}
				
				
				@Test
				void _8_seconds() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					assertEquals(8.0, r.getLengthInSeconds());
				}
			}
			
			
			@Test
			void _has_time_signature_tostring_equals_4_4__4_4__4_4__4_4() throws Exception
			{
				
				r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
				assertEquals("4/4__4/4__4/4__4/4__", r.getTimeSignatureToString());
			}
			
			
			@Test
			void _and_start_tempo_is_set_to_80_has_length_of_12_seconds() throws Exception
			{
				
				r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
				r.setStartTempo(80.0);
				assertEquals(12.0, r.getLengthInSeconds());
			}
			
			
			@Nested
			public class _and_tempo_set_to_80_at_2_bars_0_beats
			{
//				@Test
//				void _has_length_of_10_seconds() throws Exception
//				{
//					
//					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
//					r.addTempoChange(80.0, new BarsAndBeats(2, 0.0));
//					assertEquals(10.0, r.getLengthInSeconds());
//				}
				
				
				@Test
				void _has_length_of_4_bars_0_beats() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.addTempoChange(80.0, new BarsAndBeats(2, 0.0));
					assertEquals(4, r.getLengthInBarsAndBeats().getBarPosition());
					assertEquals(0.0, r.getLengthInBarsAndBeats().getOffsetInQuarters());
				}
				
				
				@Test
				void _has_length_of__16_quarters() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.addTempoChange(80.0, new BarsAndBeats(2, 0.0));
					assertEquals(16.0, r.getLengthInQuarters());
				}
			}
			
			
			@Nested
			public class _and_time_is_set_to_3_4
			{
				@Test
				void _then_length_is_4_bars_0_beats() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
					assertEquals(4, r.getLengthInBarsAndBeats().getBarPosition());
					assertEquals(0.0, r.getLengthInBarsAndBeats().getOffsetInQuarters());
				}

				
				@Test
				void _then_length_is_12_quarters() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
					assertEquals(12.0, r.getLengthInQuarters());
				}
				
				
				@Test
				void _then_length_is_6_seconds() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
					assertEquals(6.0, r.getLengthInSeconds());
				}
				
				
				@Test
				void _then_has_time_signature_tostring_equals_3_4__3_4__3_4__3_4__() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
					assertEquals("3/4__3/4__3/4__3/4__", r.getTimeSignatureToString());
				}
			}
			
			
			@Nested
			public class _and_bar_2_is_replaced_with_a_bar_of_7_8
			{
				@Test
				void _then_length_is_4_bars_0_beats() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.replaceTimeSignature(2, TimeSignature.SEVEN_EIGHT_322);
					assertEquals(4, r.getLengthInBarsAndBeats().getBarPosition());
					assertEquals(0.0, r.getLengthInBarsAndBeats().getOffsetInQuarters());
				}
				
				
				@Test
				void _then_length_is_15_5_quarters() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.replaceTimeSignature(2, TimeSignature.SEVEN_EIGHT_322);
					assertEquals(15.5, r.getLengthInQuarters());
				}
				
				
				@Test
				void _then_length_is_7_75_seconds() throws Exception
				{
					
					r.setLengthInBarsAndBeats(new BarsAndBeats(4, 0.0));
					r.replaceTimeSignature(2, TimeSignature.SEVEN_EIGHT_322);
					assertEquals(7.75, r.getLengthInSeconds());
				}
			}			
		}
	}
	
	
	@Nested
	public class given_ruler_8_bars_long
	{
		@Test
		void _when_adding_ruler_with_time_signature_from_child_at_4_bars_then_getTimesignature_at_bar_4_is_time_signature_from_child() throws Exception
		{
			
			r.setLengthInBarsAndBeats(new BarsAndBeats(8, 0.0));
			Ruler child = new TimeSignatureListAndGenAndTempoListRuler();
			child.setLengthInBarsAndBeats(new BarsAndBeats(2, 0.0));
			child.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
			r.replaceTimeSignature(new BarsAndBeats(4, 0.0), child);
			assertEquals("4/4__4/4__4/4__4/4__3/4__3/4__4/4__4/4__", r.getTimeSignatureToString());
		}
		
		
//		@Test
//		void _when_adding_ruler_with_tempo_change_at_4_bars_then_tempo_before_is_from_parent_and_after_is_from_child() throws Exception
//		{
//			
//			r.setLengthInBarsAndBeats(new BarsAndBeats(8, 0.0));
//			Ruler child = new TimeSignatureAndTempoListRuler();
//			child.setLengthInBarsAndBeats(new BarsAndBeats(2, 0.0));
//			child.setStartTempo(100);
//			r.addTempoChange(new BarsAndBeats(4, 0.0), child);
//			assertEquals(100, r.getTempoAtBarsAndBeatsPosition(4, 1.0));
//			assertEquals(120, r.getTempoAtBarsAndBeatsPosition(2, 1.0));
//		}
	
	
		
	}
	
	@Test
	void given_default_of_3_4_and_length_of_1_bar_in_7_8_time_then_ts_at_0_is_7_8_and_at_1_is_7_8() throws Exception
	{
		// the last bar persists...........
		
		
		r.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		r.setLengthInBarsAndBeats(new BarsAndBeats(1, 0.0));
		r.setDefaultTimeSignature(TimeSignature.THREE_FOUR);
		assertEquals(TimeSignature.SEVEN_EIGHT_322, r.getTimeSignature(0));
		assertEquals(TimeSignature.SEVEN_EIGHT_322, r.getTimeSignature(1));
	}
	
}
