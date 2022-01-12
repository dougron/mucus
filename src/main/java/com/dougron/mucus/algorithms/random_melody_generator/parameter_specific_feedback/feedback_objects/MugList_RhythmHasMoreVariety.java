package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import java.util.HashSet;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.ParameterAndIndex;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RNDValueObject;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;

public class MugList_RhythmHasMoreVariety implements FeedbackObject
{

	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.MUG_LIST_RHYTHM;
	
	
	
	private MugList_RhythmHasMoreVariety ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new MugList_RhythmHasMoreVariety();
			excludeList = new FeedbackObject[] {MugList_RhythmHasLessVariety.getInstance()};
		}
		return instance;
	}
	

	@Override
	public String getDescription ()
	{
		return "MugList_RhythmHasMoreVariety";
	}

	
	
	@Override
	public boolean isTrue 
	(
			RMRandomNumberContainer aRndContainer,
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg
			)
	{
		int rrpCount = getCountOfUniqueRRPs(aRndContainer, aRmg);		
		int newRrpCount = getCountOfUniqueRRPs(aNewRndContainer, aRmg);	
		if (rrpCount < newRrpCount) return true;
		return false;
	}



	public int getCountOfUniqueRRPs (RMRandomNumberContainer aRndContainer,
			RandomMelodyGenerator aRmg)
	{
		HashSet<RelativeRhythmicPosition> set = new HashSet<RelativeRhythmicPosition>();
		RNDValueObject rvo = aRndContainer.get(relatedParameter);
		for (ParameterAndIndex pai: rvo.getEqualOpportunityParameterAndIndexList(relatedParameter))
		{
			int index = (int) (aRmg.getEmbellishmentRhythmOptionsCount() * pai.getStartValue());
			RelativeRhythmicPosition rrp = aRmg.getEmbellishmentRhythmOption(index);
			set.add(rrp);
		}
		return set.size();
	}
	
	
	
	public int getTotalEmbellishmentCount 
	(
			RMRandomNumberContainer aRndContainer, 
			RandomMelodyGenerator aRmg
			)
	{
		int count = 0;
		RNDValueObject rvo = aRndContainer.get(Parameter.MUG_LIST_COUNT);
		for (ParameterAndIndex pai: rvo.getEqualOpportunityParameterAndIndexList(Parameter.MUG_LIST_COUNT))
		{
			int thisItemCount = aRmg.getEmbellishmentCountOption((int)(pai.getStartValue() * aRmg.getEmbellishmentCountOptionsLength()));
			count += thisItemCount;
		}
		return count;
	}
	


	public int getTotalRelativeRhythmicPositionPowerLength (
			RMRandomNumberContainer aRndContainer, RandomMelodyGenerator aRmg)
	{
		int length = 0;
		RNDValueObject rvo = aRndContainer.get(relatedParameter);
		for (ParameterAndIndex pai: rvo.getEqualOpportunityParameterAndIndexList(relatedParameter))
		{
			int index = (int) (aRmg.getEmbellishmentRhythmOptionsCount() * pai.getStartValue());
			RelativeRhythmicPosition rrp = aRmg.getEmbellishmentRhythmOption(index);
			length += rrp.getPowerLength();
		}
		return length;
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
