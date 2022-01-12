package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class StructureToneMultiplier_Higher implements FeedbackObject
{


	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.STRUCTURE_TONE_MULTIPLIER;
	
	
	
	private StructureToneMultiplier_Higher ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new StructureToneMultiplier_Higher();
			excludeList = new FeedbackObject[] {StructureToneMultiplier_Lower.getInstance()};
		}
		return instance;
	}
	

	@Override
	public String getDescription ()
	{
		return "StructureToneMultiplier_Higher";
	}

	
	
	@Override
	public boolean isTrue (
			RMRandomNumberContainer aRndContainer,
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg)
	{
		int originalMultiplier = aRmg.getStructureToneMultiplier(aRndContainer.get(relatedParameter).getValue());
		int newMultiplier = aRmg.getStructureToneMultiplier(aNewRndContainer.get(relatedParameter).getValue());
		if (newMultiplier > originalMultiplier) return true;
		return false;
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
