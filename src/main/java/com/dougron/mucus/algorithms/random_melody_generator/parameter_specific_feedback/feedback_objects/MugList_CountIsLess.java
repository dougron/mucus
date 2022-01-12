package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.ParameterAndIndex;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RNDValueObject;

public class MugList_CountIsLess implements FeedbackObject
{

	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.MUG_LIST_COUNT;
	
	
	
	private MugList_CountIsLess ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new MugList_CountIsLess();
			excludeList = new FeedbackObject[] {MugList_CountIsMore.getInstance()};
		}
		return instance;
	}
	

	@Override
	public String getDescription ()
	{
		return "MugList_CountIsLess";
	}

	
	
	@Override
	public boolean isTrue 
	(
			RMRandomNumberContainer aRndContainer,
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg
			)
	{
		int count = getTotalEmbellishmentCount(aRndContainer, aRmg);	
		int newCount = getTotalEmbellishmentCount(aNewRndContainer, aRmg);
		if (count > newCount) return true;
		return false;
	}



	public int getTotalEmbellishmentCount 
	(
			RMRandomNumberContainer aRndContainer, 
			RandomMelodyGenerator aRmg
			)
	{
		int count = 0;
		RNDValueObject rvo = aRndContainer.get(relatedParameter);
		for (ParameterAndIndex pai: rvo.getEqualOpportunityParameterAndIndexList(relatedParameter))
		{
			int thisItemCount = aRmg.getEmbellishmentCountOption((int)(pai.getStartValue() * aRmg.getEmbellishmentCountOptionsLength()));
			count += thisItemCount;
		}
		return count;
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
