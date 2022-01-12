package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class EmbellishmentRepetitionPattern_MoreVariety implements FeedbackObject
{

	
	
	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.EMBELLISHMENT_REPETITION_PATTERN;
	
	
	
	private EmbellishmentRepetitionPattern_MoreVariety()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new EmbellishmentRepetitionPattern_MoreVariety();
			excludeList = new FeedbackObject[] {EmbellishmentRepetitionPattern_LessVariety.getInstance()};
		}
		return instance;
	}
	
	
	
	@Override
	public String getDescription()
	{		
		return "EmbellishmentRepetitionPattern_MoreVariety";
	}

	
	
	@Override
	public boolean isTrue
	(
			RMRandomNumberContainer aRndContainer, 
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg
			)
	{
		double originalValue = aRndContainer.get(relatedParameter).getValue();
		int[][] patterns = aRmg.getEmbellishmentRepetitionPatterns();
		int[] originalPattern = patterns[(int)(originalValue * patterns.length)];
		double newValue = aNewRndContainer.get(relatedParameter).getValue();
		int[] newPattern = patterns[(int)(newValue * patterns.length)];
		double originalVarietyScore = getVarietyScore(originalPattern);
		double newVarietyScore = getVarietyScore(newPattern);
		if (newVarietyScore > originalVarietyScore) return true;		
		return false;
	}
	
	
	
	private double getVarietyScore (int[] aPattern)
	{
		int changes = getPatternChangeCount(aPattern);
		int variations = getVariationCount(aPattern);
		return changes * variations / aPattern.length;
	}



	public int getVariationCount (int[] aPattern)
	{
		int variations = 0;
		for (int i: aPattern)
		{
			if (i > variations) variations = i;
		}
		variations++;
		return variations;
	}



	public int getPatternChangeCount (int[] aPattern)
	{
		int changes = 0;
		for (int i = 0; i < aPattern.length; i++)
		{
			int a = aPattern[i];
			int b;
			if (i == aPattern.length - 1)
			{
				b = aPattern[0];
			}
			else
			{
				b = aPattern[i + 1];
			}
			if (a != b) changes++;
		}
		return changes;
	}



	@Override
	public FeedbackObject[] getExcludeList()
	{
		return excludeList ;
	}



	@Override
	public Parameter getRelatedParameter()
	{
		return relatedParameter ;
	}

}
