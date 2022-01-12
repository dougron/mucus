package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public interface FeedbackObject
{

//	public static FeedbackObject getInstance()
//	{
//		return null;
//	}
	
	public String getDescription();

	public boolean isTrue(RMRandomNumberContainer aRndContainer, RMRandomNumberContainer aNewRndContainer, RandomMelodyGenerator aRmg);
	
	public FeedbackObject[] getExcludeList();

	public Parameter getRelatedParameter();
}
