package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.da_utils.four_point_contour.FourPointContour;

public class StructureToneContour_Different
		implements FeedbackObject
{

	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.STRUCTURE_TONE_CONTOUR;
	
	
	
	private StructureToneContour_Different ()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new StructureToneContour_Different();
			excludeList = new FeedbackObject[] {};
		}
		return instance;
	}
	

	@Override
	public String getDescription ()
	{
		return "StructureToneContour_Different";
	}

	
	
	@Override
	public boolean isTrue (
			RMRandomNumberContainer aRndContainer,
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg)
	{
		FourPointContour originalContour = aRmg.getStructureToneContourOption(aRndContainer.get(relatedParameter).getValue());
		FourPointContour newContour = aRmg.getStructureToneContourOption(aNewRndContainer.get(relatedParameter).getValue());
		if (originalContour != newContour) return true;
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
