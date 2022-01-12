package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class StartNote_Higher implements FeedbackObject
{


	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.START_NOTE;
	
	
	
	private StartNote_Higher ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new StartNote_Higher();
			excludeList = new FeedbackObject[] {StartNote_Lower.getInstance(), StartNote_SignificantlyDifferent.getInstance()};
		}
		return instance;
	}
	

	@Override
	public String getDescription ()
	{
		return "StartNote_Higher";
	}

	
	
	@Override
	public boolean isTrue (
			RMRandomNumberContainer aRndContainer,
			RMRandomNumberContainer aNewRndContainer,
			RandomMelodyGenerator aRmg
		)
	{
		int originalStartNote = aRmg.getStartNoteOption(aRndContainer.get(relatedParameter).getValue());
		int newStartNote = aRmg.getStartNoteOption(aNewRndContainer.get(relatedParameter).getValue());
		if (newStartNote > originalStartNote) return true;
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
