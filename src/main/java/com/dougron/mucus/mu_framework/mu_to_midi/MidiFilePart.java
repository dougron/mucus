package main.java.com.dougron.mucus.mu_framework.mu_to_midi;

import java.util.ArrayList;
import java.util.Collections;

import main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things.MidiThing;

public class MidiFilePart
{
	
	private static final int[] rotArr = new int[] {21, 14, 7, 0};

//	private int channel;
	ArrayList<MidiThing> list = new ArrayList<MidiThing>();

	public MidiFilePart ()
	{
//		channel = aChannel;
	}
	
	
	public void addMidiThing(MidiThing aMidiThing)
	{
		list.add(aMidiThing);
	}


	public MidiChunk getMidiChunk ()
	{
		Collections.sort(list, MidiThing.globalPositionComparator);
		MidiChunk mc = new MidiChunk("MTrk");
		int offset = 0;
		for (MidiThing mt: list)
		{
			int delta = mt.getGlobalPositionInSubdivisions() - offset;
			offset = mt.getGlobalPositionInSubdivisions();
			byte[] deltaBytes = getDeltaBytes(delta);
			byte[] thingBytes = mt.getBytes();
			
			for (byte b: deltaBytes)
			{
				mc.add(b);
			}
			for (byte b: thingBytes)
			{
				mc.add(b);
			}
//			if (mt instanceof TempoEvent && mt.getGlobalPositionInSubdivisions() == 0)
//			{
//				for (byte b: deltaBytes)
//				{
//					mc.add(b);
//				}
//				for (byte b: thingBytes)
//				{
//					mc.add(b);
//				}
//			}
		}
		
		return mc;
	}

	

	private byte[] getDeltaBytes (int value)
	{
		ArrayList<Integer> byteList = splitInto7BitItems(value);
		ArrayList<Integer> byteList2 = removeHighEndZeroItems(byteList);
		byte[] barr = getByteArrayWithContinuationBitsSet(byteList2);
		return barr;
	}
	
	
	
	private byte[] getByteArrayWithContinuationBitsSet (
			ArrayList<Integer> byteList2)
	{
		byte[] barr = new byte[byteList2.size()];
		for (int i = 0; i < byteList2.size(); i++)
		{
			if (i < byteList2.size() - 1)
			{
				barr[i] = (byte)((byteList2.get(i) + 0b10000000) & 0b11111111);
			}
			else
			{
				barr[i] = (byte)(byteList2.get(i) & 0b11111111);
			}
		}
		return barr;
	}



	private ArrayList<Integer> removeHighEndZeroItems (
			ArrayList<Integer> byteList)
	{
		ArrayList<Integer> byteList2 = new ArrayList<Integer>();
		boolean flag = false;
		for (int i = 0; i < byteList.size(); i++)
		{
			Integer b = byteList.get(i);
			if (flag)
			{
				byteList2.add(b);
			}
			else
			{
				if (b > 0)
				{
					byteList2.add(b);
					flag = true;
				}
				else if (i == byteList.size() - 1)
				{
					byteList2.add(b);
				}
			}
		}
		return byteList2;
	}



	private ArrayList<Integer> splitInto7BitItems (int value)
	{
		ArrayList<Integer> byteList = new ArrayList<Integer>();
		for (int rot: rotArr)
		{
			byteList.add(((value >> rot) & 0b1111111));
		}
		return byteList;
	}

}
