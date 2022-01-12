package main.java.com.dougron.mucus.mucus_output_manager;

import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyParameterObject;
import main.java.com.dougron.mucus.mu_framework.Mu;

public class ConsoleOutputManager implements MucusOutputManager
{

	private static MucusOutputManager instance;
	private String consoleString = "ConsoleOutputManager: ";
	private JSONObject currentUser;
	
	
	private ConsoleOutputManager ()
	{
		
	}

	
	
	public static MucusOutputManager getInstance()
	{
		if (instance == null)
		{
			instance = new ConsoleOutputManager();
		}
		return instance;
	}



	@Override
	public void startNewSession (String aTimeStamp)
	{
		System.out.println(consoleString + "startNewSession called at " + aTimeStamp + " with " + currentUser);
		
	}



	@Override
	public void outputToPlayback (String aTimeStamp, Mu aMu)
	{
		System.out.println(consoleString + "outputToPlayback called at " + aTimeStamp + " with mu " + aMu.getName());
//		System.out.println(aMu.toString());
		
	}



	@Override
	public void outputToMusicXML (String aTimeStamp, Mu aMu)
	{
		System.out.println(consoleString + "outputToMusicXML called at " + aTimeStamp + " with mu " + aMu.getName());
		
	}



	@Override
	public void outputRndContainer (String aTimeStamp,
			RMRandomNumberContainer aRndContainer)
	{
		System.out.println(consoleString + "outputRndContainer called at " + aTimeStamp);
//		System.out.println(aRndContainer.toString());
	}



	@Override
	public void outputParameterObject (String aTimeStamp,
			RandomMelodyParameterObject aParameterObject)
	{
		System.out.println(consoleString + "outputParameterObject called at " + aTimeStamp);
//		System.out.println(aParameterObject.toString());
	}



	@Override
	public void outputMuAsXML (String aTimeStamp, Mu aMu)
	{
		System.out.println(consoleString + "outputMuAsXML called at " + aTimeStamp + " with mu " + aMu.getName());
		
	}



	@Override
	public void outputThinkingFile (String aTimeStamp, JSONObject aThinking)
	{
		System.out.println(consoleString + "outputThinkingFile called at " + aTimeStamp);
		System.out.println(aThinking.toString(4));
		
	}



	@Override
	public void outputStatVarFile (String aTimeStamp, JSONObject aStatVarJSON)
	{
		System.out.println(consoleString + "outputStatVarFile called at " + aTimeStamp);
//		System.out.println(aStatVarJSON.toString(4));
	}



	@Override
	public void outputUserFeedbackFile (String aTimeStamp,
			JSONObject aUserFeedbackFile)
	{
		System.out.println(consoleString + "outputUserFeedbackFile called at " + aTimeStamp);
		System.out.println(aUserFeedbackFile.toString(4));
	}



	@Override
	public void outputUserApprovalFile (String aTimeStamp,
			JSONObject userApprovalFile)
	{
		System.out.println(consoleString + "outputUserApprovalFile called at " + aTimeStamp);
		System.out.println(userApprovalFile.toString(4));
		
	}



	@Override
	public void outputBestOfABadBunchFile (String aTimeStamp,
			JSONObject aBestOfABadBunchFile)
	{
		System.out.println(consoleString + "outputBestOfABadBunchFile called at " + aTimeStamp);
		
	}



	@Override
	public void addNewUser (String aTimeStamp, JSONObject aNewUser)
	{
//		addNewUserToDatabase(aNewUser);
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
		String fileName = aMid.getDateTimeStamp() + "_" + aMid.getMu().getName();
		System.out.println(consoleString + "saveBotVariationOptionItem called for " + fileName);
		
	}
}
