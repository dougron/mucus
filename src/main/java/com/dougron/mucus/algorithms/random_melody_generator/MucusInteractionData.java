package main.java.com.dougron.mucus.algorithms.random_melody_generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.bot_personality.BotPersonality;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.FeedbackObject;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.mu_xml_utility.MuXMLUtility;

public class MucusInteractionData
{
	

	private Mu mu;
	private RandomMelodyParameterObject po;
	private RMRandomNumberContainer rndContainer;
	private StringBuilder thinking = new StringBuilder();
	private JSONObject jsonThinking = new JSONObject();
	private ArrayList<FeedbackObject> feedbackOptionList;
	private ArrayList<FeedbackObject> parameterSpecificFeedbackList = new ArrayList<FeedbackObject>();
	private boolean escape;
	private String dateTimeStamp = "INTIAL_DATE_TIMESTAMP_PLACEHOLDER";
	private BotPersonality botPersonality;
	private Parameter[] parameterScope;
	private RandomMelodyGenerator rmg;
	
	
	
	
	
	
	
	public MucusInteractionData(Mu aMu, RandomMelodyParameterObject aParameterObject)
	{
		setMu(aMu);
		setPo(aParameterObject);
	}
	
	
	
	public MucusInteractionData()
	{
		// TODO Auto-generated constructor stub
	}



	public MucusInteractionData(String aThinking)
	{
		appendThinking(aThinking);
		appendJSONThinking("initial_goal", aThinking);
	}
	
	
	
	public void addParameterSpecificFeedbackItem (List<FeedbackObject> foList)
	{
		parameterSpecificFeedbackList.addAll(foList);		
	}
	


	public void addParameterSpecificFeedbackItem(FeedbackObject aFeedbackObject)
	{
		parameterSpecificFeedbackList.add(aFeedbackObject);
	}
	
	
	
	public void clearThinking()
	{
		thinking.setLength(0);	
		jsonThinking = new JSONObject();
	}



	public ArrayList<FeedbackObject> getParameterSpecificFeedbackList()
	{
		return parameterSpecificFeedbackList;
	}



	public void clearParameterSpecificFeedbackList()
	{
		parameterSpecificFeedbackList.clear();		
	}



	public void appendJSONThinking(String key, JSONObject aJSONObject)
	{
		jsonThinking.put(key, aJSONObject);
	}
	
	
	
	public void appendJSONThinking(String key, JSONArray aJSONArray)
	{
		jsonThinking.put(key, aJSONArray);
	}
	
	
	
	public void appendJSONThinking (String key, String aString)
	{
		jsonThinking.put(key, aString);	
	}
	
	
	
	public void appendJSONThinking (String key, Object aObject)
	{
		jsonThinking.put(key, aObject);	
	}



	public ArrayList<FeedbackObject> getFeedbackOptionList()
	{
		return feedbackOptionList;
	}



	public void setFeedbackOptionList(ArrayList<FeedbackObject> aFeedbackOptionList)
	{
		feedbackOptionList = aFeedbackOptionList;	
	}
	
	
	
	public void setEscape(boolean aBoolean)
	{
		escape = aBoolean;
	}



	public boolean getEscape()
	{
		return escape;
	}

	
	
	public Mu getMu()
	{
		return mu;
	}

	
	
	public void setMu(Mu aMu)
	{
		mu = aMu;
	}

	
	
	public RandomMelodyParameterObject getPo()
	{
		return po;
	}

	
	
	public void setPo(RandomMelodyParameterObject aParameterObject)
	{
		po = aParameterObject;
	}



	public RMRandomNumberContainer getRndContainer()
	{
		return rndContainer;
	}



	public void setRndContainer(RMRandomNumberContainer aRndContainer)
	{
		rndContainer = aRndContainer;
	}



	public String getThinkingToString()
	{
		return thinking.toString();
	}
	
	
	
	public StringBuilder getThinking()
	{
		return thinking;
	}



	public void appendThinking(String aString)
	{
		thinking.append(aString);
	}
	
	
	
	public static void saveToFile(MucusInteractionData aMid, String aDirectoryPath, String aDateTimeStamp)
	{
		saveToFile(aMid.getMu(), aDirectoryPath, aDateTimeStamp);
		saveToFile(aMid.getRndContainer(), aDirectoryPath, aDateTimeStamp);
		saveToFile(aMid.getPo(), aDirectoryPath, aDateTimeStamp);
		saveRandomMelodyGeneratorToFile(aDirectoryPath, aDateTimeStamp, aMid.getRandomMelodyGenerator());
		saveThinkingTextFile(aMid.getThinkingToString(), aDirectoryPath, aDateTimeStamp);
	}
	
	
	
	public static void saveThinkingTextFile(String aThinking, String aDirectoryPath, String aDateTimeStamp)
	{
		String file = aDirectoryPath + aDateTimeStamp + ".thinking";
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(file)));
			bw.write(aThinking);
			bw.close();
			System.out.println("saved file:" + file);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}



	private static void saveRandomMelodyGeneratorToFile
	(
			String aDirectoryPath, 
			String aDateTimeStamp,
			RandomMelodyGenerator aRmg
			)
	{
		aRmg.saveToJSON(aDirectoryPath, aDateTimeStamp);		
	}



	public static void saveToFile(RandomMelodyParameterObject aParameterObject, String aDirectoryPath,
			String aDateTimeStamp)
	{
		aParameterObject.saveToJSON(aDirectoryPath, aDateTimeStamp);
	}



	public static void saveToFile(RMRandomNumberContainer rndContainer, String aDirectoryPath, String aDateTimeStamp)
	{
		rndContainer.saveAsJSON(aDirectoryPath, aDateTimeStamp);
	}



	public static void saveToFile(Mu aMu, String aDirectoryPath, String aDateTimeStamp)
	{
		MuXMLUtility.saveMuToXMLFile(aDirectoryPath + aDateTimeStamp + ".muxml", aMu);
	}



	public String getDateTimeStamp()
	{
		return dateTimeStamp;
	}



	public void setDateTimeStamp(String dateTimeStamp)
	{
		this.dateTimeStamp = dateTimeStamp;
	}



	public BotPersonality getBotPersonality()
	{
		return botPersonality;
	}



	public void setBotPersonality(BotPersonality aBotPersonality)
	{
		this.botPersonality = aBotPersonality;
	}



	public Parameter[] getParameterScope()
	{
		return parameterScope;
	}



	public void setParameterScope(Parameter[] aParameterScope)
	{
		this.parameterScope = aParameterScope;
	}
	
	
	
	public RandomMelodyGenerator getRandomMelodyGenerator ()
	{
		return rmg;
	}
	


	public void setRandomMelodyGenerator (RandomMelodyGenerator rmg)
	{
		this.rmg = rmg;
	}



	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(" botPersonality=");
		if (botPersonality == null)
		{
			sb.append("null\n");
		}
		else
		{
			sb.append("\n" + botPersonality.toString() + "\n");
		}
		
		sb.append(" dateTimeStamp=");
		if (dateTimeStamp == null)
		{
			sb.append("null\n");
		}
		else
		{
			sb.append(dateTimeStamp + "\n");
		}
		
		sb.append(" escape=" + escape + "\n");
		
		sb.append(" parameterSpecificFeedbackList=");
		for (FeedbackObject fo: parameterSpecificFeedbackList)
		{
			sb.append(fo.getDescription() + ", ");
		}
		sb.append("\n");
		
		sb.append(" feedbackOptionList=");
		if (feedbackOptionList == null)
		{
			sb.append("null\n");
		}
		else
		{
			for (FeedbackObject fo: feedbackOptionList)
			{
				sb.append(fo.getDescription() + ", ");
			}
			sb.append("\n");
		}
		
		sb.append(" thinking=\n" + thinking.toString() + "\n");
		
		sb.append(" rndContainer=");
		if (rndContainer == null)
		{
			sb.append("null\n");
		}
		else
		{
			sb.append("\n" + rndContainer.toString() + "\n");
		}
		
		sb.append(" parameterObject=");
		if (po == null)
		{
			sb.append("null\n");
		}
		else
		{
			sb.append("\n" + po.toString() + "\n");
		}
		
		sb.append(" mu=");
		if (mu == null)
		{
			sb.append("null\n");
		}
		else
		{
			sb.append("\n" + mu.toString() + "\n");
		}
		
		return sb.toString();
	}



	public JSONObject getThinkingJSON() {
		// TODO Auto-generated method stub
		return null;
	}



//	public JSONObject getThinkingJSON ()
//	{
//		return jsonThinking;
//	}



	
	
}