package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class StructureToneSpacing_Longer implements FeedbackObject
{

	
	
	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.STRUCTURE_TONE_SPACING;

	
	
	private StructureToneSpacing_Longer()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new StructureToneSpacing_Longer();
			excludeList = new FeedbackObject[] {StructureToneSpacing_Shorter.getInstance()};
		}
		return instance;
	}
	
	
	
	@Override
	public String getDescription()
	{		
		return "StructureToneSpacing_Longer";
	}

	
	
	@Override
	public boolean isTrue(RMRandomNumberContainer aRndContainer, RMRandomNumberContainer aNewRndContainer, RandomMelodyGenerator aRmg)
	{
		double originalValue = aRndContainer.get(relatedParameter).getValue();
		double[] spacings = aRmg.getStructureToneSpacingOptions();
		double originalSpacing = spacings[(int)(originalValue * spacings.length)];
		double newValue = aNewRndContainer.get(relatedParameter).getValue();
		double newSpacing = spacings[(int)(newValue * spacings.length)];
		if (newSpacing > originalSpacing) return true;		
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
