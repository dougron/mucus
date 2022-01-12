package main.java.com.dougron.mucus.mu_framework.mu_to_midi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MidiChunkTest
{

	@Test
	void chunk_with_no_data_returns_byte_array_length_8 ()
	{
		MidiChunk mc = new MidiChunk("MThd");
		byte[] barr = mc.getByteArray();
		assertEquals(8, barr.length);
	}
	
	
	@Test
	void chunk_returns_correct_header  () throws Exception
	{
		MidiChunk mc = new MidiChunk("MThd");
		byte[] barr = mc.getByteArray();
		assertEquals(0x4d, barr[0]);
		assertEquals(0x54, barr[1]);
		assertEquals(0x68, barr[2]);
		assertEquals(0x64, barr[3]);
	}
	
	
	@Test
	void chunk_with_no_data_returns_length_word_of_00_00_00_00  () throws Exception
	{
		MidiChunk mc = new MidiChunk("MThd");
		byte[] barr = mc.getByteArray();
		assertEquals(0x00, barr[4]);
		assertEquals(0x00, barr[5]);
		assertEquals(0x00, barr[6]);
		assertEquals(0x00, barr[7]);
	}
	
	
	@Test
	void chunk_with_data_returns_correct_length_byte_array () throws Exception
	{
		MidiChunk mc = new MidiChunk("MThd");
		mc.add(0x00);
		byte[] barr = mc.getByteArray();
		assertEquals(9, barr.length);
	}
	
	
	@Test
	void chunk_with_one_data_byte_returns_length_word_of_00_00_00_01  () throws Exception
	{
		MidiChunk mc = new MidiChunk("MThd");
		mc.add(0x00);
		byte[] barr = mc.getByteArray();
		assertEquals(0x00, barr[4]);
		assertEquals(0x00, barr[5]);
		assertEquals(0x00, barr[6]);
		assertEquals(0x01, barr[7]);
	}
	
	
	@Test
	void chunk_with_one_data_byte_returns_correct_data_byte  () throws Exception
	{
		MidiChunk mc = new MidiChunk("MThd");
		mc.add(0x11);
		byte[] barr = mc.getByteArray();
		assertEquals(0x11, barr[8]);
	}
	
	
	@Test
	void chunk_with_two_data_bytes_returns_correct_length_byte_array () throws Exception
	{
		MidiChunk mc = new MidiChunk("MThd");
		mc.add(0x00);
		mc.add(0x00);
		byte[] barr = mc.getByteArray();
		assertEquals(10, barr.length);
	}
	
	
	@Test
	void chunk_with_two_data_bytes_returns_length_word_of_00_00_00_02  () throws Exception
	{
		MidiChunk mc = new MidiChunk("MThd");
		mc.add(0x00);
		mc.add(0x00);
		byte[] barr = mc.getByteArray();
		assertEquals(0x00, barr[4]);
		assertEquals(0x00, barr[5]);
		assertEquals(0x00, barr[6]);
		assertEquals(0x02, barr[7]);
	}
	
	
	@Test
	void chunk_with_two_data_bytes_returns_correct_data_bytes  () throws Exception
	{
		MidiChunk mc = new MidiChunk("MThd");
		mc.add(0x11);
		mc.add(0x12);
		byte[] barr = mc.getByteArray();
		assertEquals(0x11, barr[8]);
		assertEquals(0x12, barr[9]);
	}
}
