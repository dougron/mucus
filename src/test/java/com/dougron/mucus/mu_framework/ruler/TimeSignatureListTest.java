package test.java.com.dougron.mucus.mu_framework.ruler;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureList;

/**
 * currently looking like RulerTest, TimeSignatureGeneratorFactoryListTest, MuTest
 * covers whether TimeSignatureList works or not
 * 
 * @author dougr
 *
 */

public class TimeSignatureListTest
{
		
	
	@Test
	void bars_and_beats_position_returns_correct_position_in_quarters() throws Exception
	{
		TimeSignatureList tsm = new TimeSignatureList();
		assertEquals(4.0, tsm.getBarsAndBeatsPositionInQuarters(new BarsAndBeats(1, 0.0)));
	}
	
	
	@Test
	void negative_bars_and_beats_position_returns_correct_position_in_quarters() throws Exception
	{
		TimeSignatureList tsm = new TimeSignatureList();
		assertEquals(-4.0, tsm.getBarsAndBeatsPositionInQuarters(new BarsAndBeats(-1, 0.0)));
	}
	
	
	
	
	
	

}
