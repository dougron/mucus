package main.java.com.dougron.mucus.mucus_output_manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyParameterObject;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.mu_controller.MuController;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_xml_utility.MuXMLUtility;
import main.java.com.dougron.mucus.mucus_output_manager.continuous_integrator.ContinuousIntegrator;
import main.java.com.dougron.mucus.mucus_output_manager.mucus_lom_injector.MuucusLOMInjector;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;

public class LocalOutputManager implements MucusOutputManager
{

	private static MucusOutputManager instance;
	private MuucusLOMInjector injector = new MuucusLOMInjector(7800);
	private Map<MuTag, Integer[]> partTrackAndClipIndexMap = ImmutableMap.<MuTag, Integer[]>builder()

		.put(MuTag.PART_MELODY, new Integer[] {0, 0})
		.put(MuTag.PART_CHORDS, new Integer[] {1, 0})
		.put(MuTag.PART_DRUMS, new Integer[] {2, 0})
		.put(MuTag.PART_BASS, new Integer[] {3, 0})
		.build();
	
	private List<MuController> muControllerList = ImmutableList.<MuController>builder()
			.add(MuController.builder()
					.partNameTag(MuTag.PART_MELODY)
					.controllerTag(MuTag.CONTROLLER_LP)
					.lomPath("path live_set tracks 0 devices 4 parameters 1")
					.controllerName("melodyLP")
					.parameterName("value")
					.max(127.0)
					.build())
			.build();
	
	private JSONObject currentUser;
	private String path = "D:/Documents/miscForBackup/Mucus Output/";
	private static final String jsonFolderName = "json/";
	private static final String musicxmlFolderName = "musicxml/";
	private static final String botVariationOptionFolderName = "bot_variation_options/";
	
	
	private LocalOutputManager ()
	{
		
	}

	
	
	public static MucusOutputManager getInstance()
	{
		if (instance == null)
		{
			instance = new LocalOutputManager();
		}
		return instance;
	}
	
	
	
	public void setPath(String aPath)
	{
		path = aPath;
	}
	
	
	
	public void setPartTrackAndClipIndexMap (
			Map<MuTag, Integer[]> partTrackAndClipIndexMap)
	{
		this.partTrackAndClipIndexMap = partTrackAndClipIndexMap;
	}



	@Override
	public void startNewSession (String aTimeStamp)
	{
		try
		{
			FileWriter fw = new FileWriter(new File(path + jsonFolderName + aTimeStamp + ".start"));
			fw.write(currentUser.toString());
			fw.flush();
			fw.close();
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		
	}



	@Override
	public void outputToPlayback (String aTimeStamp, Mu aMu)
	{
//		List<Mu> melodyList = aMu.getMuWithTag(MuTag.PART_MELODY);
//		for (Mu mu: melodyList)
//		{
//			mu.setName(aTimeStamp);
//		}
		
		ContinuousIntegrator.injectMultiPartMuIntoLive(aMu, partTrackAndClipIndexMap, muControllerList, injector);	
	}



	@Override
	public void outputToMusicXML (String aTimeStamp, Mu aMu)
	{
		MuXMLMaker.makeMultiPartXML(aMu, path + musicxmlFolderName + aTimeStamp + ".musicxml");
		
	}



	@Override
	public void outputRndContainer (String aTimeStamp,
			RMRandomNumberContainer aRndContainer)
	{
		aRndContainer.saveAsJSON(path + jsonFolderName, aTimeStamp);
	}



	@Override
	public void outputParameterObject (String aTimeStamp,
			RandomMelodyParameterObject aParameterObject)
	{
		aParameterObject.saveToJSON(path + jsonFolderName, aTimeStamp);
		
	}



	@Override
	public void outputMuAsXML (String aTimeStamp, Mu aMu)
	{
		MuXMLUtility.saveMuToXMLFile(path + jsonFolderName + aTimeStamp + ".muxml", aMu);
		
	}



	@Override
	public void outputThinkingFile (String aTimeStamp, JSONObject aThinking)
	{
		String file = path + jsonFolderName + aTimeStamp + ".thinking";
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file)));
			bw.write(aThinking.toString());
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}



	@Override
	public void outputStatVarFile (String aTimeStamp, JSONObject aStatVarJSON)
	{
		String file = path + jsonFolderName + aTimeStamp + ".rmg_statvar";
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file)));
			bw.write(aStatVarJSON.toString());
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}



	@Override
	public void outputUserFeedbackFile (String aTimeStamp,
			JSONObject aUserFeedbackFile)
	{
		String file = path + jsonFolderName + aTimeStamp + ".user_feedback";
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file)));
			bw.write(aUserFeedbackFile.toString());
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		
	}



	@Override
	public void outputUserApprovalFile (String aTimeStamp,
			JSONObject userApprovalFile)
	{
		String file = path + jsonFolderName + aTimeStamp + ".user_approval";
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file)));
			bw.write(userApprovalFile.toString());
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}



	@Override
	public void outputBestOfABadBunchFile (String aTimeStamp,
			JSONObject aBestOfABadBunchFile)
	{
		// depreciated, nothing to see here.......
		
	}



	@Override
	public void addNewUser (String aTimeStamp, JSONObject aNewUser)
	{
		currentUser = aNewUser;
		
	}



	@Override
	public void logInExistingUser (String aTimeStamp,
			JSONObject aExistingUserFile)
	{
		currentUser = aExistingUserFile;
	}



	@Override
	public void saveBotVariationOptionItem (MucusInteractionData aMid)
	{
		String dirPath = path + botVariationOptionFolderName;
		String fileName = aMid.getDateTimeStamp() + "_" + aMid.getMu().getName();
		MucusInteractionData.saveToFile(
				aMid, 
				dirPath, 
				fileName);
		MuXMLMaker.makeMultiPartXML(aMid.getMu(), dirPath + fileName + ".musicxml");
	}
}
