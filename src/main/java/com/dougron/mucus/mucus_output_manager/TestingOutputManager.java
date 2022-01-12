package main.java.com.dougron.mucus.mucus_output_manager;

import org.json.JSONArray;
import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyParameterObject;
import main.java.com.dougron.mucus.mu_framework.Mu;

public class TestingOutputManager implements MucusOutputManager
{
	
	JSONObject log = new JSONObject();

	
	
	public JSONObject getTestOutputLog ()
	{		
		return log;
	}

	

	@Override
	public void startNewSession (String aTimeStamp)
	{
		log.put("application_start_called", aTimeStamp);		
	}



	@Override
	public void outputToPlayback (String aTimeStamp, Mu aMu)
	{
		log.put("output_to_playback", aTimeStamp);	
	}



	@Override
	public void outputToMusicXML (String aTimeStamp, Mu aMu)
	{
		log.put("output_to_musicxml_maker", aTimeStamp);
	}



	@Override
	public void outputRndContainer (String aTimeStamp,
			RMRandomNumberContainer aRndContainer)
	{
		log.put("output_to_rnd_container_file", aTimeStamp);
		
	}



	@Override
	public void outputParameterObject (String aTimeStamp,
			RandomMelodyParameterObject aParameterObject)
	{
		log.put("output_to_parameter_object_file", aTimeStamp);
	}



	@Override
	public void outputMuAsXML (String aTimeStamp, Mu aMu)
	{
		log.put("output_to_muxml_file", aTimeStamp);	
	}



	@Override
	public void outputThinkingFile (String aTimeStamp, JSONObject aThinking)
	{
		log.put("output_to_thinking_file", aTimeStamp);	
	}



	@Override
	public void outputStatVarFile (String aTimeStamp, JSONObject aStatVarJSON)
	{
		log.put("output_to_statvar_file", aTimeStamp);	
		
	}



	@Override
	public void outputUserFeedbackFile (String aTimeStamp,
			JSONObject aUserFeedbackFile)
	{
		log.put("output_to_user_feedback_file", aTimeStamp);
		
	}



	@Override
	public void outputUserApprovalFile (String aTimeStamp,
			JSONObject userApprovalFile)
	{
		log.put("output_to_user_approval_file", aTimeStamp);
		
	}



	@Override
	public void outputBestOfABadBunchFile (String aTimeStamp,
			JSONObject aBestOfABadBunchFile)
	{
		log.put("output_to_best_of_a_bad_bunch_file", aTimeStamp);
		
	}



	@Override
	public void addNewUser (String aTimeStamp, JSONObject aNewUser)
	{
		log.put("new_user_added", aTimeStamp);
		
	}



	@Override
	public void logInExistingUser (String aTimeStamp,
			JSONObject aExistingUserFile)
	{
		log.put("existing_user_logged_in", aTimeStamp);
		
	}



	@Override
	public void saveBotVariationOptionItem (MucusInteractionData aMid)
	{
		if (!log.has("bot_variation_options")) log.put("bot_variation_options", new JSONArray());
		log.getJSONArray("bot_variation_options").put(aMid.getDateTimeStamp() + "_" + aMid.getMu().getName());
		
	}

}
