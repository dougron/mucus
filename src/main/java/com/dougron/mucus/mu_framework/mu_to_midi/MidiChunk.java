package main.java.com.dougron.mucus.mu_framework.mu_to_midi;

import java.util.ArrayList;

import com.google.common.collect.Lists;

public class MidiChunk
{
	
	
	private String header;
	ArrayList<Byte> byteList = new ArrayList<Byte>();

	
	public MidiChunk (String aHeader)
	{
//		if (aHeader.length() != 4) throw 
		header = aHeader;
	}

	
	
	public byte[] getByteArray ()
	{
		byte[] barr = new byte[getByteArraySize()];
		int index = 0;
		
		// header
		for (Character ch: Lists.charactersOf(header)) 
		{
            barr[index] = (byte)(int)ch;
			index++;
        }
		
		// length
		barr[7] = (byte)(byteList.size() & 0xff);
		barr[6] = (byte)((byteList.size() >> 8) & 0xff);
		barr[5] = (byte)((byteList.size() >> 16) & 0xff);
		barr[4] = (byte)((byteList.size() >> 24) & 0xff);
		
		// content
		index = 8;
		for (Byte b: byteList)
		{
			barr[index] = b;
			index++;
		}
		
		return barr;
	}



	public void add (int i)
	{
		byteList.add((byte)i);
		
	}



	public int getByteArraySize ()
	{
		return 8 + byteList.size();
	}
}
