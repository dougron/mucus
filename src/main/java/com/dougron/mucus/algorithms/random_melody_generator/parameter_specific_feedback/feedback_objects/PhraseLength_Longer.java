package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class PhraseLength_Longer implements FeedbackObject
{

	static FeedbackObject instance;
	private static FeedbackObject[] excludeList; 
	private Parameter relatedParameter = Parameter.PHRASE_LENGTH;
	
	
	private PhraseLength_Longer()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new PhraseLength_Longer();
			excludeList = new FeedbackObject[] {PhraseLength_Shorter.getInstance()};
		}
		return instance;
	}


	
	@Override
	public String getDescription()
	{
		return "PhraseLength_Longer";
	}



	@Override
	public boolean isTrue(RMRandomNumberContainer aRndContainer, RMRandomNumberContainer aNewRndContainer, RandomMelodyGenerator aRmg)
	{
		double originalValue = aRndContainer.get(relatedParameter).getValue();
		int originalIndex = aRmg.getDoubleAsIndexForParameterOption(originalValue, aRmg.getPhraseLengthOptions().length);
		int originalLength = aRmg.getPhraseLengthOption(originalIndex);
		double newValue = aNewRndContainer.get(relatedParameter).getValue();
		int newIndex = aRmg.getDoubleAsIndexForParameterOption(newValue, aRmg.getPhraseLengthOptions().length);
		int newLength = aRmg.getPhraseLengthOption(newIndex);
		if (newLength > originalLength) return true;		
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
