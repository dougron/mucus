package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.ParameterSpecificFeedback;

public class PhraseLength_LessEven implements FeedbackObject
{

	
	
	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.PHRASE_LENGTH;
	
	
	
	private PhraseLength_LessEven()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new PhraseLength_LessEven();
			excludeList = new FeedbackObject[] {PhraseLength_MoreEven.getInstance()};
		}
		return instance;
	}
	
	
	
	@Override
	public String getDescription()
	{		
		return "PhraseLength_LessEven";
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
		if (ParameterSpecificFeedback.getBarLengthBalanceValue(newLength) > ParameterSpecificFeedback.getBarLengthBalanceValue(originalLength)) return true;		
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
