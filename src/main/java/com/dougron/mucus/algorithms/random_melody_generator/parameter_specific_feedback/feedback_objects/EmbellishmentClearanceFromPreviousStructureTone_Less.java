package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class EmbellishmentClearanceFromPreviousStructureTone_Less implements FeedbackObject
{

	
	
	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.EMBELLISHMENT_CLEARANCE;

	
	
	private EmbellishmentClearanceFromPreviousStructureTone_Less()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new EmbellishmentClearanceFromPreviousStructureTone_Less();
			excludeList = new FeedbackObject[] {EmbellishmentClearanceFromPreviousStructureTone_More.getInstance()};
		}
		return instance;
	}
	
	
	
	@Override
	public String getDescription()
	{		
		return "EmbellishmentClearanceFromPreviousStructureTone_Less";
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
		double[] clearances = aRmg.getClearanceOfEmbellishmentFromPreviousStructureToneOptions();
		double originalClearance = clearances[(int)(originalValue * clearances.length)];
		double newValue = aNewRndContainer.get(relatedParameter).getValue();
		double newClearance = clearances[(int)(newValue * clearances.length)];
		if (newClearance < originalClearance) return true;		
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
