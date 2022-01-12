package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class PhraseEnd_Later implements FeedbackObject
{

	
	
	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.PHRASE_END_PERCENT;
	private double changeAmount = 0.15;
	
	
	private PhraseEnd_Later()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new PhraseEnd_Later();
			excludeList = new FeedbackObject[] {PhraseEnd_Earlier.getInstance()};
		}
		return instance;
	}
	
	
	
	@Override
	public String getDescription()
	{		
		return "PhraseEnd_Later";
	}

	
	
	@Override
	public boolean isTrue(RMRandomNumberContainer aRndContainer, RMRandomNumberContainer aNewRndContainer, RandomMelodyGenerator aRmg)
	{
		double originalValue = aRndContainer.get(relatedParameter).getValue();
		double newValue = aNewRndContainer.get(relatedParameter).getValue();
		if (newValue - originalValue > changeAmount) return true;		
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
