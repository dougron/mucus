package main.java.com.dougron.mucus.mu_framework.key_signature_utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

class KeySignatureMapTest
{

	
	// tests for KeySignatureMap that sets a single key for an entire piece
	
	@Test
	final void when_instantiated_with_a_key_then_returns_that_key()
	{
		KeySignatureMap ksm = new KeySignatureMap(1);
		assertEquals(1, ksm.getKey());
	}
	
	
	@Test
	void when_instantiated_with_a_key_then_returns_that_key_from_any_bar_index() throws Exception
	{
		KeySignatureMap ksm = new KeySignatureMap(1);
		assertEquals(1, ksm.getKey(1));
		assertEquals(1, ksm.getKey(10000));
	}
	
	
	@Test
	void when_instantiated_with_a_key_then_returns_that_key_from_any_bar_and_beats_position() throws Exception
	{
		KeySignatureMap ksm = new KeySignatureMap(1);
		assertEquals(1, ksm.getKey(new BarsAndBeats(0, 2.0)));
	}
	
	
	@Test
	void when_instantiated_with_a_key_then_isDiatonicNote_returns_correct_boolean_for_an_octave_of_semitones() throws Exception
	{
		KeySignatureMap ksm = new KeySignatureMap(0);
		assertTrue(ksm.isDiatonicNote(36));		//c
		assertFalse(ksm.isDiatonicNote(37));
		assertTrue(ksm.isDiatonicNote(38));		//d
		assertFalse(ksm.isDiatonicNote(39));
		assertTrue(ksm.isDiatonicNote(40));		//e
		assertTrue(ksm.isDiatonicNote(41));		//f
		assertFalse(ksm.isDiatonicNote(42));
		assertTrue(ksm.isDiatonicNote(43));		//g
		assertFalse(ksm.isDiatonicNote(44));
		assertTrue(ksm.isDiatonicNote(45));		//a
		assertFalse(ksm.isDiatonicNote(46));
		assertTrue(ksm.isDiatonicNote(47));		//b
	}

	
	
}
