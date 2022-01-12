package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class StartNote_SignificantlyDifferent
		implements FeedbackObject
{

	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.START_NOTE;
	private double significantDifference = 0.35;
	
	
	
	private StartNote_SignificantlyDifferent ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new StartNote_SignificantlyDifferent();
			excludeList = new FeedbackObject[] {StartNote_Lower.getInstance(), StartNote_Higher.getInstance()};
		}
		return instance;
	}
	

	@Override
	public String getDescription ()
	{
		return "StartNote_SignificantlyDifferent";
	}

	
	
	@Override
	public boolean isTrue (
			RMRandomNumberContainer aRndContainer,
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg)
	{
		double originalValue = aRndContainer.get(relatedParameter).getValue();
		double newValue = aNewRndContainer.get(relatedParameter).getValue();
		if (Math.abs(originalValue - newValue) > significantDifference ) return true;
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
