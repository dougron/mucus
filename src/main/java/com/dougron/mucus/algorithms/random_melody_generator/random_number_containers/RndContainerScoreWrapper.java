package main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers;

import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;

public class RndContainerScoreWrapper
{
	
	public RMRandomNumberContainer rndContainer;
	public int score;
	public String narrative;
	public JSONArray jsonNarrative;
	
	
	public RndContainerScoreWrapper(RMRandomNumberContainer aRndContainer, int aScore, String aNarrative)
	{
		score = aScore;
		rndContainer = aRndContainer;
		narrative = aNarrative;
	}
	
	
	
	public RndContainerScoreWrapper (RMRandomNumberContainer aRndContainer, int aScore, String aNarrative, JSONArray aJSONArray)
	{
		score = aScore;
		rndContainer = aRndContainer;
		narrative = aNarrative;
		jsonNarrative = aJSONArray;
	}



	public String toString()
	{
		return "score=" + score + "\n" + narrative + "\n" + rndContainer.toString();
	}
	
	
	public JSONObject getJSON()
	{
		JSONObject j = new JSONObject();
		j.put("score", score);
		j.put("score_breakdown", jsonNarrative);
		return j;
	}
	
	
	public static Comparator<RndContainerScoreWrapper> scoreComparator = new Comparator<RndContainerScoreWrapper>()
	{
		public int compare(RndContainerScoreWrapper note1, RndContainerScoreWrapper note2)
		{
			if (note1.score < note2.score) return 1;
			if (note1.score > note2.score) return -1;
			return 0;
		}
	};
}