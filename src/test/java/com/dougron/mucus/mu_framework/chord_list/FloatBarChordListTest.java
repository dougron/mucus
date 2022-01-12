package test.java.com.dougron.mucus.mu_framework.chord_list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordListGenerator;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import time_signature_utilities.time_signature.TimeSignature;

class FloatBarChordListTest
{

	@Test
	final void instantiates()
	{
		ChordListGenerator clg = new FloatBarChordProgression(1.0, new Object[] {0.0, "Cm"});
		assertNotNull(clg);
	}
	
	
	@Test
	void returns_correct_toString() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(1.0, new Object[] {0.0, "Cm"});
		assertEquals("FloatBarChordProgression: lengthInFloatBars=1.0 \n0.0:Cm, ", clg.toString());
	}
	
	
	@Test
	void when_using_a_FloatBarChordProgression_then_returns_correct_toString() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(1.0, new Object[] {0.0, "Cm"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 1);
		assertEquals("Cm_", cl.chordsToString());
	}
	
	
	@Test
	void when_using_a_FloatBarChordProgression_then_chord_event_returns_correct_parameters() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(1.0, new Object[] {0.0, "Cm"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 1);
		ChordEvent ce = cl.getChordEventList().get(0);
		assertEquals("Cm", ce.getChordName());
		assertEquals(0, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
	}
	
	
	@Test
	void when_using_a_FloatBarChordProgression_then_getChord_at_0_is_that_chord() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(1.0, new Object[] {0.0, "Cm"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 1);
		assertEquals("Cm", cl.getChord(0, 0.0).name());
	}
	
	
	@Test
	void when_using_a_FloatBarChordProgression_which_must_repeat_then_returns_correct_toString() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(2.0, new Object[] {0.0, "Cm", 1.0, "Bb"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 4);
		assertEquals("Cm_Bb_Cm_Bb_", cl.chordsToString());
	}
	
	
	@Test
	void when_using_a_FloatBarChordProgression_which_must_repeat_then_chord_event_returns_correct_parameters() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(2.0, new Object[] {0.0, "Cm", 1.0, "Bb"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 4);
		ChordEvent ce = cl.getChordEventList().get(0);
		assertEquals("Cm", ce.getChordName());
		assertEquals(0, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(1);
		assertEquals("Bb", ce.getChordName());
		assertEquals(1, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(2);
		assertEquals("Cm", ce.getChordName());
		assertEquals(2, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(3);
		assertEquals("Bb", ce.getChordName());
		assertEquals(3, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
	}
	
	
	@Test
	void when_using_a_FloatBarChordProgression_which_must_repeat_then_getChord_at_0_1_2_3_is_correct_chord() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(2.0, new Object[] {0.0, "Cm", 1.0, "Bb"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 4);
		assertEquals("Cm", cl.getChord(0, 0.0).name());
		assertEquals("Bb", cl.getChord(1, 0.0).name());
		assertEquals("Cm", cl.getChord(2, 0.0).name());
		assertEquals("Bb", cl.getChord(3, 0.0).name());
	}
	
	
	@Test
	void when_getChord_beyond_the_scope_of_the_ChordList_then_null_is_returned() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(2.0, new Object[] {0.0, "Cm", 1.0, "Bb"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 4);
		assertEquals(null, cl.getChord(5, 0.0));
	}
	
	
	@Test
	void when_using_a_FloatBarChordProgression_with_two_chord_per_bar_then_chord_positions_are_correct() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(2.0, new Object[] {0.0, "Cm", 0.5, "Bb", 1.0, "Dm", 1.5, "G7"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		ChordList cl = clg.getChordList(tsgm, 4);
		
		ChordEvent ce = cl.getChordEventList().get(0);
		assertEquals("Cm", ce.getChordName());
		assertEquals(0, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(1);
		assertEquals("Bb", ce.getChordName());
		assertEquals(0, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(2.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(2);
		assertEquals("Dm", ce.getChordName());
		assertEquals(1, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(3);
		assertEquals("G7", ce.getChordName());
		assertEquals(1, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(2.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(4);
		assertEquals("Cm", ce.getChordName());
		assertEquals(2, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(5);
		assertEquals("Bb", ce.getChordName());
		assertEquals(2, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(2.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(6);
		assertEquals("Dm", ce.getChordName());
		assertEquals(3, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(7);
		assertEquals("G7", ce.getChordName());
		assertEquals(3, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(2.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
	}
	
	
	@Test
	void when_using_a_FloatBarChordProgression_with_two_chord_per_bar_and_7_8_time_then_chord_positions_are_correct() throws Exception
	{
		ChordListGenerator clg = new FloatBarChordProgression(1.0, new Object[] {0.0, "Cm", 0.5, "Bb"});
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(TimeSignature.SEVEN_EIGHT_322);
		ChordList cl = clg.getChordList(tsgm, 2);
		
		ChordEvent ce = cl.getChordEventList().get(0);
		assertEquals("Cm", ce.getChordName());
		assertEquals(0, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(1);
		assertEquals("Bb", ce.getChordName());
		assertEquals(0, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(1.5, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(2);
		assertEquals("Cm", ce.getChordName());
		assertEquals(1, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(0.0, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
		
		ce = cl.getChordEventList().get(3);
		assertEquals("Bb", ce.getChordName());
		assertEquals(1, ce.getPositionInBarsAndBeats().getBarPosition());
		assertEquals(1.5, ce.getPositionInBarsAndBeats().getOffsetInQuarters());
	}
	


}
