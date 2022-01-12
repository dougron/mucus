package main.java.com.dougron.mucus.mu_framework.mu_to_midi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.nd4j.shade.guava.collect.ImmutableSortedMap;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things.NoteOff;
import main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things.NoteOn;
import main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things.TempoEvent;
import main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things.TimeSignatureEvent;
import main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things.TrackEndMarker;
import main.java.com.dougron.mucus.mu_framework.mu_to_midi.track_things.TrackName;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureList;
import time_signature_utilities.time_signature.TimeSignature;
import time_signature_utilities.time_signature_map.TimeSignatureMap;
import time_signature_utilities.time_signature_map.TimeSignatureZone;


public class MuMidiMaker
{
	private int midiFileType = 0;
	private int subdivision = 720;
	private ImmutableSortedMap<MuTag, Integer> parameterChannelMap = ImmutableSortedMap.of(
				MuTag.PART_MELODY, 1,
				MuTag.PART_BASS, 2,
				MuTag.PART_CHORDS, 3,
				MuTag.PART_DRUMS, 10
			);

	
	
	public MuMidiMaker ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public byte[] getByteArray(Mu aMu)	// assumes a Mu with child mus of parts tagged with MuTags
	{
		ArrayList<MidiChunk> chunkList = new ArrayList<MidiChunk>();
		
		chunkList.addAll(getTrackChunks(aMu));
		chunkList.add(0, getHeaderChunk(chunkList.size()));
		
		int arraySize = getByteArraySize(chunkList);
		byte[] barr = getByteArrayFromMidiChunks(chunkList, arraySize);
		
		return barr;
	}
	
	
	
	private TimeSignatureMap getTimeSignatureMap(TimeSignatureList aTimeSignatureList)
	{
		TimeSignatureMap tsm = new TimeSignatureMap();
		Iterator<TimeSignature> tsit = aTimeSignatureList.iterator();
		while (tsit.hasNext())
		{
			tsm.addTimeSignature(tsit.next());
		}
		return tsm;
	}



	private byte[] getByteArrayFromMidiChunks (
			ArrayList<MidiChunk> chunkList,
			int arraySize)
	{
		byte[] barr = new byte[arraySize];
		
		int index = 0;
		for (MidiChunk mc: chunkList)
		{
			byte[] temparr = mc.getByteArray();
			for (byte b: temparr)
			{
				barr[index] = b;
				index++;
			}
		}
		return barr;
	}



	public int getByteArraySize (ArrayList<MidiChunk> chunkList)
	{
		int arraySize = 0;
		for (MidiChunk mc: chunkList)
		{
			arraySize += mc.getByteArraySize();
		}
		return arraySize;
	}



	private MidiChunk getHeaderChunk (int trackCount)
	{
		MidiChunk mc = new MidiChunk("MThd");
		
		// type
		mc.add(0x00);
		mc.add(midiFileType);
		
		// track chunk count
		mc.add((byte)((trackCount >> 8) & 0xff));
		mc.add((byte)(trackCount & 0xff));
		
		// subdivisions
		mc.add((byte)((subdivision >> 8) & 0xff));
		mc.add((byte)(subdivision & 0xff));
		
		
		return mc;
	}



	List<MidiChunk> getTrackChunks (Mu aMu)
	{
		switch (midiFileType)
		{
		case 0: 	return getType0Chunk(aMu);
			
		case 1:
			break;
		case 2:
			break;	
		}
		return null;
	}



	private List<MidiChunk> getType0Chunk (Mu aMu)
	{
		MidiFilePart mfp = new MidiFilePart();
		loadMidiFilePartWithNoteOnsAnfOffs(aMu, mfp);
		addTrackName(aMu, mfp);
		addTempoEvents(aMu, mfp);
		for (TimeSignatureZone tsz: getTimeSignatureMap(aMu.getTimeSignatureList()).getTimeSignatureZones())
		{
			mfp.addMidiThing(new TimeSignatureEvent
					(
						tsz.getNumerator(), 
						tsz.getDenominator(), 
						(int)(Math.round(aMu.getGlobalPositionInQuarters(tsz.getStartBar()) * subdivision))
					)
			);
		}
		
		mfp.addMidiThing(new TrackEndMarker((int)(Math.round(aMu.getGlobalEndPositionInQuarters() * subdivision))));
		
		MidiChunk trackChunk = mfp.getMidiChunk();
		return Arrays.asList(new MidiChunk[] {trackChunk});
	}



	public void addTempoEvents (Mu aMu, MidiFilePart mfp)
	{
		aMu.getRuler().getTempoStream()
//			.peek(n -> System.out.println(n))
			.forEach(tempo -> mfp.addMidiThing
				(
						new TempoEvent(tempo.getTempo(), 
						(int)(Math.round(tempo.getPositionInQuarters(aMu.getRuler().getTSGenAndMap()) * subdivision)))
				));
	}



	public void addTrackName (Mu aMu, MidiFilePart mfp)
	{
		mfp.addMidiThing(new TrackName(aMu.getName()));
	}



	public void loadMidiFilePartWithNoteOnsAnfOffs (Mu aMu, MidiFilePart mfp)
	{
		for (MuTag tag: parameterChannelMap.keySet())
		{
			int channel = parameterChannelMap.get(tag);
			
			ArrayList<Mu> muList = aMu.getMuWithTag(tag);
			for (Mu mu: muList)
			{
				List<Mu> muWithNotesList = mu.getMusWithNotes();
				for (Mu noteMu: muWithNotesList)
				{
					int start = (int)(Math.round(noteMu.getGlobalPositionInQuarters() * subdivision));
					int end = (int)(Math.round(noteMu.getGlobalEndPositionInQuarters() * subdivision));
					for (MuNote mn: noteMu.getMuNotes())
					{
						mfp.addMidiThing(new NoteOn(mn.getPitch(), mn.getVelocity(), channel, start));
						mfp.addMidiThing(new NoteOff(mn.getPitch(), mn.getVelocity(), channel, end));
					}
				}
			}
		}
	}



//	private void returnType0Part ()
//	{
//		// TODO Auto-generated method stub
//		
//	}



//	private ArrayList<Byte> getFileHeader ()
//	{
//		ArrayList<Byte> bList = new ArrayList<Byte>();
//		// MThd
//		bList.add((byte)0x4D);
//		bList.add((byte)0x54);
//		bList.add((byte)0x68);
//		bList.add((byte)0x64);
//		// chunk size
//		bList.add((byte)0x00);
//		bList.add((byte)0x00);
//		bList.add((byte)0x00);
//		bList.add((byte)0x06);
//		// format type - currently only type 1
//		bList.add((byte)0x00);
//		bList.add((byte)0x00);
//		// track count - for type 0 only can be 1
//		bList.add((byte)0x00);
//		bList.add((byte)0x01);
//		// subdivision
//		BigInteger bint = BigInteger.valueOf(subdivision);
//		byte[] byteArray = bint.toByteArray();
//		if (byteArray.length == 1) bList.add((byte)0x00);
//		for (byte b: byteArray) bList.add(b);
//		
//		return bList;
//	}



//	private byte[] makeByteArray (ArrayList<Byte> bList)
//	{
//		byte[] barr = new byte[bList.size()];
//		for (int i = 0; i < bList.size(); i++)
//		{
//			barr[i] = bList.get(i);
//		}
//		return barr;
//	}



	public static boolean saveByteArrayToFile (byte[] arr, String path)
	{
		File file = new File(path);
		
		try {
			  
            // Initialize a pointer
            // in file using OutputStream
            OutputStream os = new FileOutputStream(file);
  
            // Starts writing the bytes in it
            os.write(arr);
//            System.out.println("Successfully"
//                               + " byte inserted");
  
            // Close the file
            os.close();
            return true;
        }
  
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
		return false;
	}


//	only type 0 for now
//	public void setMidiFileType (int midiFileType)
//	{
//		this.midiFileType = midiFileType;
//	}

}
