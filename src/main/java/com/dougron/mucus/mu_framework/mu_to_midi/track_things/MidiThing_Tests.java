package main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class MidiThing_Tests
{

	@Test
	void noteOne_thing_returns_3_bytes ()
	{
		MidiThing noteOn = new NoteOn(44, 56, 1, 1000);
		byte[] barr = noteOn.getBytes();
		assertEquals(3, barr.length);
//		for (byte b: barr) 
//		{
//			int x = b & 0xff;
//			System.out.println(Integer.toHexString(x));
//		}
	}
	
	
	@Test
	void noteOff_thing_returns_3_bytes ()
	{
		MidiThing noteOn = new NoteOff(44, 56, 1, 1000);
		byte[] barr = noteOn.getBytes();
		assertEquals(3, barr.length);
	}
	
	
	@Test
	void position_comparator_sorts_list_correctly () throws Exception
	{
//		MidiFilePart mfp = new MidiFilePart();
		ArrayList<MidiThing> list = new ArrayList<MidiThing>();
		MidiThing noteOff = new NoteOff(48, 64, 1, 400);
		list.add(noteOff);
		MidiThing noteOn = new NoteOn(48, 64, 1, 200);
		list.add(noteOn);
		MidiThing endMarker = new TrackEndMarker(1000);
		list.add(endMarker);
		MidiThing tempoEvent = new TempoEvent(120.0, 0);
		list.add(tempoEvent);
//		list.forEach(n -> System.out.println(n));
		Collections.sort(list, MidiThing.globalPositionComparator);
//		list.forEach(n -> System.out.println(n));
		assertEquals(tempoEvent, list.get(0));
		assertEquals(noteOn, list.get(1));
		assertEquals(noteOff, list.get(2));
		assertEquals(endMarker, list.get(3));
	}
	
	
	
	@Test
	void position_comparator_sorts_list_correctly_when_two_events_share_a_position () throws Exception
	{
//		MidiFilePart mfp = new MidiFilePart();
		ArrayList<MidiThing> list = new ArrayList<MidiThing>();
		MidiThing noteOff = new NoteOff(48, 64, 1, 1000);
		list.add(noteOff);
		MidiThing noteOn = new NoteOn(48, 64, 1, 0);
		list.add(noteOn);
		MidiThing endMarker = new TrackEndMarker(1000);
		list.add(endMarker);
		MidiThing tempoEvent = new TempoEvent(120.0, 0);
		list.add(tempoEvent);
//		list.forEach(n -> System.out.println(n));
		Collections.sort(list, MidiThing.globalPositionComparator);
//		list.forEach(n -> System.out.println(n));
		assertEquals(tempoEvent, list.get(0));
		assertEquals(noteOn, list.get(1));
		assertEquals(noteOff, list.get(2));
		assertEquals(endMarker, list.get(3));
	}

}
