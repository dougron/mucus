package test.java.com.dougron.mucus.mu_framework.ruler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.ruler.tempo_list.TempoChange;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class TempoChange_Test
{

	@Test
	void tempo_change_at_2_floatbars_returns_position_of_2_floatbars()
	{
		TempoChange tc = new TempoChange(120.0, 2.0);
		assertEquals(2.0, tc.getPositionInFloatBars());
	}
	
	
	
	@Nested
	class given_tempo_change_at_2_floatbars_when_time_signature_is_4_4
	{
		@Test
		void _returns_position_of_2_bars_0_beats() throws Exception
		{
			TempoChange tc = new TempoChange(120.0, 2.0);
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(2, tc.getPositionInBarsAndBeats(tsgm).getBarPosition());
			assertEquals(0.0, tc.getPositionInBarsAndBeats(tsgm).getOffsetInQuarters());
		}
		
		
		@Test
		void _returns_position_of_8_quarters() throws Exception
		{
			TempoChange tc = new TempoChange(120.0, 2.0);
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(8.0, tc.getPositionInQuarters(tsgm));
		}
	}
	
	
	@Nested
	class given_tempo_change_at_2_5_floatbars_when_time_signature_is_4_4
	{
		@Test
		void _returns_position_of_2_bars_2_beats() throws Exception
		{
			TempoChange tc = new TempoChange(120.0, 2.5);
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(2, tc.getPositionInBarsAndBeats(tsgm).getBarPosition());
			assertEquals(2.0, tc.getPositionInBarsAndBeats(tsgm).getOffsetInQuarters());
		}
		
		
		@Test
		void _returns_position_of_10_quarters() throws Exception
		{
			TempoChange tc = new TempoChange(120.0, 2.5);
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			assertEquals(10.0, tc.getPositionInQuarters(tsgm));
		}
	}
	
	
	@Nested
	class given_tempo_change_at_2_5_floatbars_when_time_signature_is_3_4
	{
		@Test
		void _returns_position_of_2_bars_1_5_beats() throws Exception
		{
			TempoChange tc = new TempoChange(120.0, 2.5);
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
			assertEquals(2, tc.getPositionInBarsAndBeats(tsgm).getBarPosition());
			assertEquals(1.5, tc.getPositionInBarsAndBeats(tsgm).getOffsetInQuarters());
		}
		
		
		@Test
		void _returns_position_of_7_5_quarters() throws Exception
		{
			TempoChange tc = new TempoChange(120.0, 2.5);
			TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
			tsgm.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
			assertEquals(7.5, tc.getPositionInQuarters(tsgm));
		}
	}
	
	
	
	
	
	

}
