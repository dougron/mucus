package main.java.com.dougron.mucus.mucus_output_manager.continuous_integrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.mu_controller.MuController;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.position_model.PositionIsZeroInBars;
import main.java.com.dougron.mucus.mucus_output_manager.mucus_lom_injector.MuucusLOMInjector;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;
import test.java.com.dougron.mucus.TestingStuff;
import timed_notes_and_controllers.TimedNote;

/**
 * class to wrap static code to output material from mucus_test items
 * 
 * @author dougr
 *
 */

public class ContinuousIntegrator
{

	public static String outputMuToXMLandLive(Mu aMu, String aFileName, int aTrackIndex, int aClipIndex,
			MuucusLOMInjector injector)
	{
		injectMuIntoLive(aTrackIndex, aClipIndex, aMu, injector);
		return makeMusicXML(aFileName, aMu);	
	}
	
	
	
	public static String outputMultiPartMuToXMLandLive
	(
			Mu aMu, 
			String aFileName, 
			Map<MuTag, Integer[]> partTrackAndClipIndexMap, 
			List<MuController> muControllerList, 
			MuucusLOMInjector aInjector
			)
	{
		injectMultiPartMuIntoLive(aMu, partTrackAndClipIndexMap, muControllerList, aInjector);
		return makeMultiPartMusicXML(aFileName, aMu);
	}
	
	
	// include timestamp in filename
	public static String outputMultiPartMuToXMLandLiveWithoutTimeStamp(
			Mu aMu, 
			String aFileName,
			Map<MuTag, Integer[]> partTrackAndClipIndexMap, 
			List<MuController> muControllerList, 
			MuucusLOMInjector aInjector)
	{
		injectMultiPartMuIntoLive(aMu, partTrackAndClipIndexMap, muControllerList, aInjector);
		return makeMultiPartMusicXMLWithoutAutomaticTimeStamp(aFileName, aMu);
	}
	
	
// privates ------------------------------------------------------------------------------------------
	
	
	
	private static String makeMultiPartMusicXMLWithoutAutomaticTimeStamp(String aFileName, Mu aMu)
	{
		return MuXMLMaker.makeMultiPartXML(aMu, TestingStuff.getQuickNastyXMLPathStringNoTimeStamp(aFileName));
	}



	public static String makeMultiPartMusicXML(String aFileName, Mu aMu)
	{
		return MuXMLMaker.makeMultiPartXML(aMu, TestingStuff.getQuickNastyXMLPathString(aFileName));
	}



	public static String makeMusicXML(String aFileName, Mu aMu)
	{
		return MuXMLMaker.makeXML(aMu, TestingStuff.getQuickNastyXMLPathString(aFileName));		
	}
	
	
	
	public static void injectMultiPartMuIntoLive
	(
			Mu aMu, 
			Map<MuTag, Integer[]> partTrackAndClipIndexMap,
			MuucusLOMInjector aInjector
			)
	{	
		aMu.setPositionModel(new PositionIsZeroInBars());
		aMu.setParent(null);
		double tempo = aMu.getStartTempo();
		aInjector.setTempo(tempo);
		sendNotesToLive(aMu, partTrackAndClipIndexMap, aInjector);
	}
	
	
	
	public static void injectMultiPartMuIntoLive
	(
			Mu aMu, 
			Map<MuTag, Integer[]> partTrackAndClipIndexMap,
			List<MuController> muControllerList, 
			MuucusLOMInjector aInjector
			)
	{	
		aMu.setPositionModel(new PositionIsZeroInBars());
		aMu.setParent(null);
		double tempo = aMu.getStartTempo();
		aInjector.setTempo(tempo);
		aInjector.sendControllerClearAllMessage();
		sendNotesToLive(aMu, partTrackAndClipIndexMap, aInjector);
		sendControllersToLive(aMu, muControllerList, aInjector);
	}



	private static void sendControllersToLive(Mu aMu, List<MuController> muControllerList,
			MuucusLOMInjector aInjector) {
		for (MuController muController: muControllerList)
		{
			ArrayList<Mu> taggedMus = aMu.getMuWithTag(muController.getPartNameTag());
			for (Mu mu: taggedMus)
			{
				injectControllersIntoLive(mu, muController, aInjector);		
			}
		}
	}



	private static void sendNotesToLive(
			Mu aMu, 
			Map<MuTag, Integer[]> partTrackAndClipIndexMap,
			MuucusLOMInjector aInjector
			) 
	{
		for (MuTag muTag: partTrackAndClipIndexMap.keySet())
		{
			ArrayList<Mu> taggedMus = aMu.getMuWithTag(muTag);
			if (taggedMus.size() > 0)
			{
				Integer[] trackAndClipIndex = partTrackAndClipIndexMap.get(muTag);
				injectMuIntoLive(trackAndClipIndex[0], trackAndClipIndex[1], taggedMus.get(0), aInjector);	// only adds the first one for now
			}
		}
	}

	
	
	private static void injectControllersIntoLive(Mu aMu, MuController aMuController, MuucusLOMInjector aInjector) 
	{
		String ccName = aMuController.getControllerName();
		double lengthInQuarters = aMu.getLengthInQuarters();
		aInjector.sendAssignControllerNameMessage(ccName);
		aInjector.sendControllerLengthInQuarters(ccName, lengthInQuarters);
		aInjector.sendControllerResolutionInNoteValues(ccName, "64n");
		aInjector.sendControllerOutputRange(ccName, aMuController.getMin(), aMuController.getMax());
		aInjector.setControllerPath(ccName, aMuController.getLomPath());
		aInjector.setControllerParameterName(ccName, aMuController.getParameterName());
		List<Mu> list = aMu.getMuWithTag(aMuController.getControllerTag());
		aInjector.sendControllerBreakPointList(ccName, list, lengthInQuarters);
		
		
	}



	private static void injectMuIntoLive(int aTrackIndex, int aClipIndex, Mu aMu, MuucusLOMInjector aInjector)
	{
//		clearClip(aTrackIndex, aClipIndex, aInjector);
		setLoopLength(aTrackIndex, aClipIndex, aInjector, aMu);
		setClipTimeSignature(aTrackIndex, aClipIndex, aInjector, aMu);
		injectNotes(aTrackIndex, aClipIndex, aInjector, aMu);
		setClipName(aTrackIndex, aClipIndex, aInjector, aMu.getName());
		
	}
	
	
	
	private static void setClipTimeSignature (int aTrackIndex, int aClipIndex,
			MuucusLOMInjector aInjector, Mu aMu)
	{
		TimeSignature ts = aMu.getTimeSignature(0);
		aInjector.setClipSignatureNumerator(aTrackIndex, aClipIndex, ts.getNumerator());
		aInjector.setClipSignatureDenominator(aTrackIndex, aClipIndex, ts.getDenominator());		
	}



	private static void setClipName(int aTrackIndex, int aClipIndex, MuucusLOMInjector aInjector, String aName)
	{
		aInjector.setClipName(aTrackIndex, aClipIndex, aName);		
	}



	private static void injectNotes(int aTrackIndex, int aClipIndex, MuucusLOMInjector aInjector, Mu aMu)
	{
		List<TimedNote> noteList = makeTimedNoteList(aMu);
		aInjector.replaceNotes(aTrackIndex, aClipIndex, noteList);
	}



	private static List<TimedNote> makeTimedNoteList(Mu aMu)
	{
		List<TimedNote> timedNoteList = new ArrayList<TimedNote>();
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			double position = mu.getGlobalPositionInQuarters();
			if (position < 0.0) position += aMu.getLengthInQuarters();
			for (MuNote muNote: mu.getMuNotes())
			{
				
				timedNoteList.add(
						new TimedNote(
								muNote.getPitch(), muNote.getVelocity(), 
								position, mu.getLengthInQuarters()
						)
				);
			}
		}
		return timedNoteList;
	}



	private static void setLoopLength(int aTrackIndex, int aClipIndex, MuucusLOMInjector aInjector, Mu aMu)
	{
		double lengthInQuarters = aMu.getLengthInQuarters();
		aInjector.setClipLoopEndPosition(aTrackIndex, aClipIndex, lengthInQuarters);
	}



//	private static void clearClip(int aTrackIndex, int aClipIndex, MuucusLOMInjector aInjector)
//	{
//		aInjector.deleteClip(aTrackIndex, aClipIndex);	
//		aInjector.createClip(aTrackIndex, aClipIndex, 4.0);
//	}



	



	

}
