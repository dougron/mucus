package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class XMLKey_UpTo5KeysSharper
		implements FeedbackObject
{

	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.XMLKEY;
	
	
	
	private XMLKey_UpTo5KeysSharper ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new XMLKey_UpTo5KeysSharper();
			excludeList = new FeedbackObject[] {XMLKey_UpTo5KeysFlatter.getInstance()};
		}
		return instance;
	}
	
	

	@Override
	public String getDescription ()
	{
		return "XMLKey_UpTo5KeysSharper";
	}

	
	
	@Override
	public boolean isTrue 
	(
			RMRandomNumberContainer aRndContainer,
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg
			)
	{
		int originalKey = aRmg.getXMLKeyOptionIndex(aRndContainer.get(relatedParameter).getValue());
		int newKey = aRmg.getXMLKeyOptionIndex(aNewRndContainer.get(relatedParameter).getValue());
		if (newKey < originalKey) newKey += 12;
		if (newKey - originalKey > 0 && newKey - originalKey < 6) return true;
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
