package main.java.com.dougron.mucus.mucus_output_manager;

import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyParameterObject;
import main.java.com.dougron.mucus.mu_framework.Mu;

public interface MucusOutputManager
{

	void startNewSession (String aTimeStamp);

	void outputToPlayback (String aTimeStamp, Mu aMu);

	void outputToMusicXML (String aTimeStamp, Mu aMu);

	void outputRndContainer (String aTimeStamp, RMRandomNumberContainer aRndContainer);

	void outputParameterObject (String aTimeStamp, RandomMelodyParameterObject aParameterObject);

	void outputMuAsXML (String aTimeStamp, Mu aMu);

	void outputThinkingFile (String aTimeStamp, JSONObject aThinking);

	void outputStatVarFile (String aTimeStamp, JSONObject aStatVarJSON);

	void outputUserFeedbackFile (String aTimeStamp, JSONObject aUserFeedbackFile);

	void outputUserApprovalFile (String aTimeStamp, JSONObject userApprovalFile);

	void outputBestOfABadBunchFile (String aTimeStamp, JSONObject aBestOfABadBunchFile);

	void addNewUser (String aTimeStamp, JSONObject aNewUser);

	void logInExistingUser (String aTimeStamp, JSONObject aExistingUserFile);

	void saveBotVariationOptionItem (MucusInteractionData listMid);

}
