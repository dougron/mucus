package main.java.com.dougron.mucus.mu_framework.mu_to_midi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

class MuMidiMaker_Tests
{
	
	String path = "D:/Documents/miscForBackup/midi_file_tests/byte_test.mid";
	
	@Test
	void MuMidiMaker_with_no_content_returns_byte_array_of_34 () throws Exception
	{
		// 14 bytes header chunk and 8 bytes track chunk and 4 bytes end-of-track-event
		Mu mu = new Mu("moo");
		mu.setLengthInBars(1);
		MuMidiMaker mmm = new MuMidiMaker();
		byte[] arr = mmm.getByteArray(mu);
		
		
		
		assertEquals(50, arr.length);
		
	}
	

	@Test
	void muMidiMaker_with_no_content_and_default_type_0_returns_byte_array_with_correct_track_placement_of_track_headers () throws Exception
	{
		Mu mu = new Mu("moo");
		mu.setLengthInBars(1);
		MuMidiMaker mmm = new MuMidiMaker();
		byte[] arr = mmm.getByteArray(mu);
		//file header
		assertEquals(0x4d, arr[0]);
		assertEquals(0x54, arr[1]);
		assertEquals(0x68, arr[2]);
		assertEquals(0x64, arr[3]);
		// track header
		assertEquals(0x4d, arr[14]);
		assertEquals(0x54, arr[15]);
		assertEquals(0x72, arr[16]);
		assertEquals(0x6b, arr[17]);
	}
	

	@Test
	void muMidiMaker_passed_mu_with_one_note_and_default_settings_returns_length_of_44 () throws Exception
	{
		// header 14, track header 8, tempo event 1 delta byte and 6 message bytes
		// note on 2 delta byte and 3 other, note off 2 delta bytes and 3 other 
		// and track end 2 delta bytes and 2 other
		Mu mu = new Mu("moo");
		Mu melody = new Mu("melody");
		melody.setLengthInBars(1);
		melody.addTag(MuTag.PART_MELODY);
		mu.addMu(melody, 0);
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.5);
		note.addMuNote(new MuNote(64, 72));
		melody.addMu(note, 1.0);
		MuMidiMaker mmm = new MuMidiMaker();
		byte[] arr = mmm.getByteArray(mu);
		
		MuMidiMaker.saveByteArrayToFile(arr, path);
		
		assertEquals(60, arr.length);
		
	}
	
	@Disabled
	@Test
	void muMidiMaker_passed_mu_with_one_note_and_default_type_0_returns_track_count_bytes_00_01 () throws Exception
	{
		Mu mu = new Mu("moo");
		Mu melody = new Mu("melody");
		melody.addTag(MuTag.PART_MELODY);
		mu.addMu(melody, 0);
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.5);
		note.addMuNote(new MuNote(64, 72));
		melody.addMu(note, 1.0);
		MuMidiMaker mmm = new MuMidiMaker();
		byte[] arr = mmm.getByteArray(mu);
		assertEquals(0x00, arr[10]);
		assertEquals(0x01, arr[11]);
	}

//	@Test
//	void given_an_empty_mu_then_muMidiMaker_return_single_MidiFilePart_which_is_empty ()
//	{
//		Mu mu = new Mu("moo");
//		MuMidiMaker mmm = new MuMidiMaker();
//		byte[] arr = mmm.getByteArray(mu);
////		MuMidiMaker.saveByteArrayToFile(arr, path);
//		List<MidiFilePart> list = mmm.getMidiFilePartList(mu);
//		assertEquals(1, list.size());
//		assertEquals(0, list.get(0).list.size());
//	}
//	
//	
//	@Test
//	void given_a_mu_with_a_single_melody_part_note_then_MuMidiMaker_returns_single_MidiFilePart_with_2_items () throws Exception
//	{
//		// 2 items = NoteOn and NoteOff
//		Mu mu = new Mu("moo");
//		Mu melody = new Mu("melody");
//		melody.addTag(MuTag.PART_MELODY);
//		mu.addMu(melody, 0);
//		Mu note = new Mu("note");
//		note.setLengthInQuarters(1.5);
//		note.addMuNote(new MuNote(64, 72));
//		melody.addMu(note, 1.0);
//		MuMidiMaker mmm = new MuMidiMaker();
//		List<MidiFilePart> list = mmm.getMidiFilePartList(mu);
//		assertEquals(2, list.get(0).list.size());
//		
//	}

}
