package main.java.com.dougron.mucus.mucus_output_manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyParameterObject;
import main.java.com.dougron.mucus.mu_framework.Mu;

public class LoggerOutputManager implements MucusOutputManager
{
	
	public static final Logger logger = LogManager.getLogger(LoggerOutputManager.class);
	private static MucusOutputManager instance;

	
	private LoggerOutputManager()
	{
		
	}
	
	
	public static MucusOutputManager getInstance() 
	{
		if (instance == null)
		{
			instance = new LoggerOutputManager();
		}
		return instance;
	}

	
	
	@Override
	public void startNewSession (String aTimeStamp)
	{
		logger.info("start event timestamp=" + aTimeStamp);
	}

	
	
	@Override
	public void outputToPlayback (String aTimeStamp, Mu aMu)
	{
		logger.info("outputToPlayback:" + aTimeStamp + "\n" + aMu.toString());
	}

	
	
	@Override
	public void outputToMusicXML (String aTimeStamp, Mu aMu)
	{
		logger.info("outputToMusicXML:" + aTimeStamp + "\n" + aMu.toString());
	}

	
	
	@Override
	public void outputRndContainer (String aTimeStamp,
			RMRandomNumberContainer aRndContainer)
	{
		logger.info("outputRndContainer:" + aTimeStamp + "\n" + aRndContainer.toString());
	}

	
	
	@Override
	public void outputParameterObject (String aTimeStamp,
			RandomMelodyParameterObject aParameterObject)
	{
		logger.info("outputRndContainer:" + aTimeStamp + "\n" + aParameterObject.toString());
	}

	
	
	@Override
	public void outputMuAsXML (String aTimeStamp, Mu aMu)
	{
		logger.info("outputMuAsXML:" + aTimeStamp + "\n" + aMu.toString());
	}



	@Override
	public void outputThinkingFile (String aTimeStamp, JSONObject aThinking)
	{
		logger.info("outputThinkingFile:" + aTimeStamp + "\n" + aThinking.toString());
	}
 
	
	
	@Override
	public void outputStatVarFile (String aTimeStamp, JSONObject aStatVarJSON)
	{
		logger.info("outputStatVarFile:" + aTimeStamp + "\n" + aStatVarJSON.toString());
	}

	
	
	@Override
	public void outputUserFeedbackFile (String aTimeStamp,
			JSONObject aUserFeedbackFile)
	{
		logger.info("outputUserFeedbackFile:" + aTimeStamp + "\n" + aUserFeedbackFile.toString());
	}
   
	
	
	@Override
	public void outputUserApprovalFile (String aTimeStamp,
			JSONObject userApprovalFile)
	{
		logger.info("outputUserApprovalFile:" + aTimeStamp + "\n" + userApprovalFile.toString());
	}

	
	
	@Override
	public void outputBestOfABadBunchFile (String aTimeStamp,
			JSONObject aBestOfABadBunchFile)
	{
		logger.info("outputBestOfABadBunchFile:" + aTimeStamp + "\n" + aBestOfABadBunchFile.toString());
	}

	
	
	@Override
	public void addNewUser (String aTimeStamp, JSONObject aNewUser)
	{
		logger.info("addNewUser:" + aTimeStamp + "\n" + aNewUser.toString());
	}

	
	
	@Override
	public void logInExistingUser (String aTimeStamp,
			JSONObject aExistingUserFile)
	{
		logger.info("logInExistingUser:" + aTimeStamp + "\n" + aExistingUserFile.toString());
	}

	
	
	@Override
	public void saveBotVariationOptionItem (MucusInteractionData listMid)
	{
		logger.info("saveBotVariationOptionItem:\n" + listMid.toString());
	}



	
}
