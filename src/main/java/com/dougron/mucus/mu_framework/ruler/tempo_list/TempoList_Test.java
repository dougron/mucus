package main.java.com.dougron.mucus.mu_framework.ruler.tempo_list;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class TempoList_Test
{

	@Nested
	public class empty_tempoList
	{
		@Test
		void _returns_default_tempo_at_0_quarters() throws Exception
		{
			TempoList tl = new TempoList();
			assertEquals(120.0, tl.getStartTempo());
		}
		
		
		@Test
		void _returns_new_tempo_when_start_tempo_changed() throws Exception
		{
			TempoList tl = new TempoList();
			tl.setStartTempo(100.0);
			assertEquals(100.0, tl.getStartTempo());
		}
	}
	
	
//	@Test
//	void when_new_tempo_is_added_then() throws Exception
//	{
//		assertTrue(false);
//	}
	
	
	@Test
	void when_new_tempo_added_at_quarters_position_returns_default_tempo_before_and_new_tempo_on_and_after() throws Exception
	{
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
		tl.addTempoChangeAtQuartersPosition(140.0, 2.0, tsgm);
		assertEquals(120.0, tl.getTempoAt(1.0, tsgm));
		assertEquals(140.0, tl.getTempoAt(2.0, tsgm));
		assertEquals(140.0, tl.getTempoAt(3.0, tsgm));
	}
	
	
	@Test
	void when_new_tempo_added_at_bars_and_beats_position_returns_default_tempo_before_and_new_tempo_on_and_after() throws Exception
	{
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
		tl.addTempoChangeAtBarsAndBeatsPosition(140.0, new BarsAndBeats(1, 0.0), tsgm);
		assertEquals(120.0, tl.getTempoAt(0, 1.0, tsgm));
		assertEquals(140.0, tl.getTempoAt(1, 0, tsgm));
		assertEquals(140.0, tl.getTempoAt(1, 2.0, tsgm));
	}
	
	
	@Nested
	public class when_two_new_tempos_are_added_at_quarters_position_out_of_position_order
	{
		@Test
		void _tempo_between_added_items_is_from_the_earlier_position() throws Exception
		{
			TempoList tl = new TempoList();
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
//			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
			tl.addTempoChangeAtFloatBarsPosition(140.0, 4.0);						// 4.0 = floatbars
			tl.addTempoChangeAtFloatBarsPosition(100.0, 2.0);						// ditto
			assertEquals(100.0, tl.getTempoAt(12.0, tsgm));		// 12.0 = quarters
		}
		
		
		@Test
		void _tempo_after_added_items_is_from_the_later_position() throws Exception
		{
			TempoList tl = new TempoList();
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
//			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
			tl.addTempoChangeAtFloatBarsPosition(140.0, 4.0);
			tl.addTempoChangeAtFloatBarsPosition(100.0, 2.0);
			assertEquals(140.0, tl.getTempoAt(17.0, tsgm));
		}
		
		
		@Test
		void _tempo_before_added_items_is_default() throws Exception
		{
			TempoList tl = new TempoList();
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
//			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
			tl.addTempoChangeAtFloatBarsPosition(140.0, 4.0);
			tl.addTempoChangeAtFloatBarsPosition(100.0, 2.0);
			assertEquals(120.0, tl.getTempoAt(1.0, tsgm));
		}
	}
	
	
	@Nested
	public class when_two_new_tempos_are_added_at_bars_and_beats_position_out_of_position_order
	{
		@Test
		void _tempo_between_added_items_is_from_the_earlier_position() throws Exception
		{
			TempoList tl = new TempoList();
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
//			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
			tl.addTempoChangeAtBarsAndBeatsPosition(140.0, new BarsAndBeats(1, 0.0),  tsgm);
			tl.addTempoChangeAtBarsAndBeatsPosition(100.0, new BarsAndBeats(0, 2.0), tsgm);
			assertEquals(100.0, tl.getTempoAt(0, 3.0, tsgm));
		}
		
		
		@Test
		void _tempo_after_added_items_is_from_the_later_position() throws Exception
		{
			TempoList tl = new TempoList();
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
//			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
			tl.addTempoChangeAtBarsAndBeatsPosition(140.0, new BarsAndBeats(1, 0.0),  tsgm);
			tl.addTempoChangeAtBarsAndBeatsPosition(100.0, new BarsAndBeats(0, 2.0), tsgm);
			assertEquals(140.0, tl.getTempoAt(1, 1.0, tsgm));
		}
		
		
		@Test
		void _tempo_before_added_items_is_default() throws Exception
		{
			TempoList tl = new TempoList();
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
//			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
			tl.addTempoChangeAtBarsAndBeatsPosition(140.0, new BarsAndBeats(1, 0.0),  tsgm);
			tl.addTempoChangeAtBarsAndBeatsPosition(100.0, new BarsAndBeats(0, 2.0), tsgm);
			assertEquals(120.0, tl.getTempoAt(0, 1.0, tsgm));
		}
	}
	
	
	@Test
	void given_4_4_time_and_120_bpm_then_1_bar_0_beats_returns_position_of_2_seconds() throws Exception
	{
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		assertEquals(2.0, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(1, 0.0), tsgm));
	}
	
	
	@Test
	void given_4_4_time_and_120_bpm_then_minus_1_bar_0_beats_returns_position_of_minus_2_seconds() throws Exception
	{
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		assertEquals(-2.0, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(-1, 0.0), tsgm));
	}
	
	
	@Test
	void given_3_4_time_and_120_bpm_then_1_bar_0_beats_returns_position_of_1_5_seconds() throws Exception
	{
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(TimeSignature.THREE_FOUR);
		assertEquals(1.5, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(1, 0.0), tsgm));
	}
	
	
	@Test
	void given_alternating_4_4_and_3_4_time_and_120_bpm_then_various_bars_and_beats_positions_return_correct_position_in_seconds() throws Exception
	{
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(new TimeSignature[] {TimeSignature.FOUR_FOUR, TimeSignature.THREE_FOUR});
		assertEquals(3.5, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(2, 0.0), tsgm));
		assertEquals(5.5, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(3, 0.0), tsgm));
		assertEquals(4.5, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(2, 2.0), tsgm));
	}
	
	
	@Test
	void given_alternating_4_4_and_3_4_time_and_120_bpm_then_various_negative_bars_and_beats_positions_return_correct_position_in_seconds() throws Exception
	{
		// the trick here is to remember the way the cycling time signature would project backwards in time
		// bar -1 would be 3/4 because bar 0 is 4/4
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(new TimeSignature[] {TimeSignature.FOUR_FOUR, TimeSignature.THREE_FOUR});
		assertEquals(-3.5, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(-2, 0.0), tsgm));
		assertEquals(-1.5, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(-1, 0.0), tsgm));
		assertEquals(-5.0, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(-3, 0.0), tsgm));
		assertEquals(-2.5, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(-2, 2.0), tsgm));
		assertEquals(-4.5, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(-2, -2.0), tsgm));
	}
	
	
	@Test
	void given_4_4_time_and_tempo_change_of_80_at_1_floatbar_then_position_of_2_bars_0_beats_returns_time_of_5_seconds() throws Exception
	{
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tl.addTempoChangeAtFloatBarsPosition(80, 1.0);
		assertEquals(5.0, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(2, 0.0), tsgm));
		// and some extras
		assertEquals(2.0, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(1, 0.0), tsgm));
		assertEquals(5.75, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(2, 1.0), tsgm));
	}
	
	
	@Test
	void given_alternating_4_4_and_3_4_time_and_and_tempo_change_of_80_at_1_floatbar_then_position_of_2_bars_0_beats_returns_time_of_5_seconds() throws Exception
	{
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(new TimeSignature[] {TimeSignature.FOUR_FOUR, TimeSignature.THREE_FOUR});
		tl.addTempoChangeAtFloatBarsPosition(80, 1.0);
		assertEquals(4.25, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(2, 0.0), tsgm));
		// some extras
		assertEquals(5.75, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(2, 2.0), tsgm));
	}
	
	
	@Test
	void if_some_idiot_added_a_tempo_change_at_a_negative_floatbar_position_it_will_not_be_added() throws Exception
	{
		// this is a test that may become redundant if a forthcoming feature implements the ability to 
		// add tempo changes at a negative position
		TempoList tl = new TempoList();
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tl.addTempoChangeAtFloatBarsPosition(80, -1.0);
		assertEquals(2.0, tl.getBarsAndBeatsPositionInSeconds(new BarsAndBeats(1, 0.0), tsgm));
	}
	
	
	@Test
	void get_deep_copy_returns_equivalent_tempo_list() throws Exception
	{
		TempoList tl = new TempoList();
//		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tl.addTempoChangeAtFloatBarsPosition(80, 1.0);
		tl.addTempoChangeAtFloatBarsPosition(100, 2.0);
		String tlToString = tl.toString();
		assertEquals(tlToString, tl.getDeepCopy().toString());
	}
	
	
	
	
	
	
	
	
}
