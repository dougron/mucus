package main.java.com.dougron.mucus.mucus_output_manager.mucus_lom_injector;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.da_utils.list_partitioner.MyPartition;
import main.java.da_utils.udp.udp_utils.OSCMessMaker;
import main.java.da_utils.udp.udp_utils.StaticUDPConnection;
import timed_notes_and_controllers.TimedNote;


/**
 * sends messages via UDP to control Ableton Session view via the Max for Live Live Object Model
 * 
 */

public class MuucusLOMInjector
{

	private static final int MAX_NOTES_IN_MESSAGE = 1021;
	StaticUDPConnection conn;
	static OSCMessMaker startPlaybackMessage = null;
	static OSCMessMaker stopPlaybackMessage = null;
	
	public MuucusLOMInjector(int port)
	{
		conn = new StaticUDPConnection(port); 
		makePlayBackMessages();
	}
	
	
	
	//----------------------------------------------
	// INTERFACE
	//----------------------------------------------
	
	
	public void createClip(int aTrackIndex, int aClipIndex, double aLengthInQuarters)
	{
		conn.sendUDPMessage(getClipSlotPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getCreateClipMessage(aTrackIndex, aClipIndex, aLengthInQuarters));
	}
	
	
	
	public void deleteClip(int aTrackIndex, int aClipIndex)
	{
		conn.sendUDPMessage(getClipSlotPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getDeleteClipMessage(aTrackIndex, aClipIndex));
	}
	

	
	public void appendNotes(int aTrackIndex, int aClipIndex, List<TimedNote> noteList)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		splitOnMaxNotesAndSendAppendMessage(aTrackIndex, aClipIndex, noteList);		
	}


	
	public void replaceNotes(int aTrackIndex, int aClipIndex, List<TimedNote> noteList)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		splitOnMaxNotesAndSendReplaceAndAppendMessage(aTrackIndex, aClipIndex, noteList);
		
	}


	
	public void replaceNotes(int aTrackIndex, int aClipIndex, List<TimedNote> noteList, 
			double replaceStartPosition, int pitchStartPosition, double replaceLength, int pitchRange)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		splitOnMaxNotesAndSendReplaceRangeThenAppendMessages(
				aTrackIndex, aClipIndex, noteList, 
				replaceStartPosition, pitchStartPosition, 
				replaceLength, pitchRange);
	}


	
	public void removeNotes(int aTrackIndex, int aClipIndex)
	{
		replaceNotes(aTrackIndex, aClipIndex, new ArrayList<TimedNote>());
	}
	
	
	
	public void setClipStartPosition(int aTrackIndex, int aClipIndex, double aPosition)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getSimpleDoubleSetMessage("start_marker", aPosition));
	}
	
	
	
	public void setClipEndPosition(int aTrackIndex, int aClipIndex, double aPosition)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getSimpleDoubleSetMessage("end_marker", aPosition));
	}
	
	
	
	public void setClipLoopStartPosition(int aTrackIndex, int aClipIndex, double aPosition)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getSimpleDoubleSetMessage("loop_start", aPosition));
	}
	
	
	
	public void setClipLoopEndPosition(int aTrackIndex, int aClipIndex, double aPosition)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getSimpleDoubleSetMessage("loop_end", aPosition));
	}
	
	
	
	public void setClipSignatureNumerator(int aTrackIndex, int aClipIndex, int aNumerator)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getSimpleIntSetMessage("signature_numerator", aNumerator));
	}
	
	
	
	public void setClipSignatureDenominator(int aTrackIndex, int aClipIndex, int aDenominator)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getSimpleIntSetMessage("signature_denominator", aDenominator));
	}
	
	
	
	public void setClipName(int aTrackIndex, int aClipIndex, String aName)
	{
		conn.sendUDPMessage(getClipPathMessage(aTrackIndex, aClipIndex));
		conn.sendUDPMessage(getSimpleStringSetMessage("name", aName));
	}
	
	
	
	public void startPlayback()
	{
		conn.sendUDPMessage(startPlaybackMessage);
	}
	
	
	
	public void stopPlayback()
	{
		conn.sendUDPMessage(stopPlaybackMessage);
	}
	
	
	
	public void setTempo(double aTempo)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("tempo");
		mess.addItem(aTempo);
		conn.sendUDPMessage(mess);
	}
	
	
	
	public void sendTextMessage(String text)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem(text);
		conn.sendUDPMessage(mess);
	}
	
	
	
	public void sendControllerClearAllMessage()
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("controller");
		mess.addItem("clearAll");
		conn.sendUDPMessage(mess);
	}
	
	
	
	public void sendAssignControllerNameMessage(String ccName) 
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("controller");
		mess.addItem("assignFunctionName");
		mess.addItem(ccName);
		conn.sendUDPMessage(mess);	
	}
	
	

	public void sendControllerLengthInQuarters(String ccName, double lengthInQuarters) 
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("controller");
		mess.addItem(ccName);
		mess.addItem("lengthInQuarters");
		mess.addItem(lengthInQuarters);
		conn.sendUDPMessage(mess);		
	}



	public void sendControllerResolutionInNoteValues(String ccName, String aNoteValue) 
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("controller");
		mess.addItem(ccName);
		mess.addItem("resolutionInNoteValues");
		mess.addItem(aNoteValue);
		conn.sendUDPMessage(mess);		
	}



	public void sendControllerOutputRange(String ccName, double min, double max) 
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("controller");
		mess.addItem(ccName);
		mess.addItem("functionOutputRange");
		mess.addItem(min);
		mess.addItem(max);
		conn.sendUDPMessage(mess);		
		
	}



	public void setControllerPath(String ccName, String lomPath) 
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("controller");
		mess.addItem(ccName);
		mess.addItem("setPath");
		for (String str: lomPath.split(" "))
		{
			mess.addItem(str);
		}
		conn.sendUDPMessage(mess);		
	}
	


	public void sendControllerBreakPointList(String ccName, List<Mu> aMuList, double lengthInQuarters) 
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("controller");
		mess.addItem(ccName);
		mess.addItem("newFunction");
		for (Mu mu: aMuList)
		{
			mess.addItem(mu.getGlobalPositionInQuarters() / lengthInQuarters);
			mess.addItem(mu.getControllerValue());
		}
		conn.sendUDPMessage(mess);			
	}



	public void setControllerParameterName(String ccName, String parameterName) 
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("controller");
		mess.addItem(ccName);
		mess.addItem("setParameterName");
		mess.addItem(parameterName);
		conn.sendUDPMessage(mess);		
		
	}
	
	//---------------------------
	// PRIVATES
	//---------------------------

	
	private void splitOnMaxNotesAndSendReplaceRangeThenAppendMessages(
			int aTrackIndex, int aClipIndex, List<TimedNote> noteList, 
			double replaceStartPosition, int pitchStartPosition, 
			double replaceLength, int pitchRange)
	{
		boolean first = true;		// first message is a replace range message which deletes range to be replaced, subsequent messages are append messages
		for (List<TimedNote> partList: MyPartition.partition(noteList, MAX_NOTES_IN_MESSAGE))
		{
			if (first)
			{
				sendReplaceNoteRangeMessage(
						aTrackIndex, aClipIndex, partList, 
						replaceStartPosition, pitchStartPosition, 
						replaceLength, pitchRange);
				first = false;
			}
			else
			{
				conn.sendUDPMessage(getAddNotesMessage(aTrackIndex, aClipIndex, partList, "append_notes"));
			}
		}
	}


	
	private void splitOnMaxNotesAndSendReplaceAndAppendMessage(int aTrackIndex, int aClipIndex, List<TimedNote> noteList)
	{
		if (noteList.size() == 0)
		{
			conn.sendUDPMessage(getAddNotesMessage(aTrackIndex, aClipIndex, new ArrayList<TimedNote>(), "replace_notes"));			
		}
		else
		{
			boolean first = true;		// first message is a replace message which deletes notes to be replaced, subsequent messages are append messages
			for (List<TimedNote> partList: MyPartition.partition(noteList, MAX_NOTES_IN_MESSAGE))
			{
				if (first)
				{
					conn.sendUDPMessage(getAddNotesMessage(aTrackIndex, aClipIndex, partList, "replace_notes"));
					first = false;
				}
				else 
				{
					conn.sendUDPMessage(getAddNotesMessage(aTrackIndex, aClipIndex, partList, "append_notes"));
				}
			}
		}
	}
	
	
	
	
	private void splitOnMaxNotesAndSendAppendMessage(int aTrackIndex, int aClipIndex, List<TimedNote> noteList)
	{
		for (List<TimedNote> partList: MyPartition.partition(noteList, MAX_NOTES_IN_MESSAGE))
		{
			conn.sendUDPMessage(getAddNotesMessage(aTrackIndex, aClipIndex, partList, "append_notes"));
		}
	}
	
	
	
	OSCMessMaker getDeleteClipMessage(int aTrackIndex, int aClipIndex)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("call");
		mess.addItem("delete_clip");
		return mess;
	}
	
	
	
	public OSCMessMaker getCreateClipMessage(int aTrackIndex, int aClipIndex, double aLengthInQuarters)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("call");
		mess.addItem("create_clip");
		mess.addItem(aLengthInQuarters);
		return mess;
	}

	
	
	private void makePlayBackMessages()
	{
		startPlaybackMessage = new OSCMessMaker();
		startPlaybackMessage.addItem("play");
		startPlaybackMessage.addItem(1);
		
		stopPlaybackMessage = new OSCMessMaker();
		stopPlaybackMessage.addItem("play");
		stopPlaybackMessage.addItem(0);
		
	}


	
	private OSCMessMaker getSimpleIntSetMessage(String aPropertyName, int aInt)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("set");
		mess.addItem(aPropertyName);
		mess.addItem(aInt);
		return mess;
	}

	
	
	private OSCMessMaker getSimpleDoubleSetMessage(String aPropertyName, double aDouble)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("set");
		mess.addItem(aPropertyName);
		mess.addItem(aDouble);
		return mess;
	}
	
	
	
	private OSCMessMaker getSimpleStringSetMessage(String aPropertyName, String aStringValue)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("set");
		mess.addItem(aPropertyName);
		mess.addItem(aStringValue);
		return mess;
	}



	private void sendReplaceNoteRangeMessage(
			int aTrackIndex, int aClipIndex, List<TimedNote> noteList, 
			double replaceStartPosition, int pitchStartPosition, 
			double replaceLength, int pitchRange)
	{
		OSCMessMaker mess = getReplaceNoteRangeMessage(
				aTrackIndex, aClipIndex, noteList, 
				replaceStartPosition, pitchStartPosition, 
				replaceLength, pitchRange);
		
		conn.sendUDPMessage(mess);		
	}



	public OSCMessMaker getReplaceNoteRangeMessage(
			int aTrackIndex, int aClipIndex, List<TimedNote> noteList,
			double replaceStartPosition, int pitchStartPosition, 
			double replaceLength, int pitchRange)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem("replace_notes_range");
		addLOMNotesAndSize(noteList, mess);		
		addReplaceNoteRanges(replaceStartPosition, pitchStartPosition, replaceLength, pitchRange, mess);		
		addTrackAndClipIndex(aTrackIndex, aClipIndex, mess);
		return mess;
	}



	private void addTrackAndClipIndex(int aTrackIndex, int aClipIndex, OSCMessMaker mess)
	{
		mess.addItem(aTrackIndex);	// track index
		mess.addItem(aClipIndex);	// clip index
	}



	private void addReplaceNoteRanges(double replaceStartPosition, int pitchStartPosition, double replaceLength,
			int pitchRange, OSCMessMaker mess)
	{
		mess.addItem(replaceStartPosition);
		mess.addItem(pitchStartPosition);
		mess.addItem(replaceLength);
		mess.addItem(pitchRange);
	}



	private void addLOMNotesAndSize(List<TimedNote> noteList, OSCMessMaker mess)
	{
		for (TimedNote tn: noteList)
		{
			mess.addItem(tn.getNote().getPitch());	// note
			mess.addItem(tn.getPosition());		// pos
			mess.addItem(tn.getLength());		// len
			mess.addItem(tn.getNote().getVelocity());		// vel
		}
		mess.addItem(noteList.size());		// note count
	}



	public OSCMessMaker getAddNotesMessage(int aTrackIndex, int aClipIndex, List<TimedNote> noteList,
			String headerString)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess.addItem(headerString);
		addLOMNotesAndSize(noteList, mess);
		addTrackAndClipIndex(aTrackIndex, aClipIndex, mess);
		return mess;
	}



	public OSCMessMaker getClipPathMessage(int aTrackIndex, int aClipIndex)
	{
		OSCMessMaker pathMess = getClipSlotPathMessage(aTrackIndex, aClipIndex);
		pathMess.addItem("clip");
		return pathMess;
	}
	
	
	
	
	private OSCMessMaker getClipSlotPathMessage(int track, int clip)
	{
		OSCMessMaker mess = new OSCMessMaker();
		mess .addItem("path");
		mess.addItem("live_set");
		mess.addItem("tracks");
		mess.addItem(track);
		mess.addItem("clip_slots");
		mess.addItem(clip);
		return mess;
	}


	
}
