package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class Tempo_Slower implements FeedbackObject
{
	
	
	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.TEMPO;
	
	
	
	private Tempo_Slower ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new Tempo_Slower();
			excludeList = new FeedbackObject[] {Tempo_Faster.getInstance()};
		}
		return instance;
	}
	

	@Override
	public String getDescription ()
	{
		return "Tempo_Slower";
	}

	
	
	@Override
	public boolean isTrue (
			RMRandomNumberContainer aRndContainer,
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg)
	{
		int originalTempo = aRmg.getTempoOption(aRndContainer.get(relatedParameter).getValue());
		int newTempo = aRmg.getTempoOption(aNewRndContainer.get(relatedParameter).getValue());
		if (newTempo < originalTempo) return true;
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
