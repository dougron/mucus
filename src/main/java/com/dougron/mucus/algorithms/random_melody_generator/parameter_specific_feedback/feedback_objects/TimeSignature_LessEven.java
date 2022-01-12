package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import time_signature_utilities.time_signature.TimeSignature;
import time_signature_utilities.ts_evenness.TS_Evenness;

public class TimeSignature_LessEven implements FeedbackObject
{

	static FeedbackObject instance;

	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.TIME_SIGNATURE;
	
	
	private TimeSignature_LessEven()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new TimeSignature_LessEven();
			excludeList = new FeedbackObject[] {TimeSignature_MoreEven.getInstance()};
		}
		return instance;
	}


	
	@Override
	public String getDescription()
	{
		return "TimeSignature_LessEven";
	}



	@Override
	public boolean isTrue(RMRandomNumberContainer aRndContainer, RMRandomNumberContainer aNewRndContainer, RandomMelodyGenerator aRmg)
	{
		double originalValue = aRndContainer.get(relatedParameter).getValue();
		int originalIndex = aRmg.getDoubleAsIndexForParameterOption(originalValue, aRmg.getTimeSignatureOptions().length);
		TimeSignature originalTS = aRmg.getTimeSignatureOption(originalIndex);
		double newValue = aNewRndContainer.get(relatedParameter).getValue();
		int newIndex = aRmg.getDoubleAsIndexForParameterOption(newValue, aRmg.getTimeSignatureOptions().length);
		TimeSignature newTS = aRmg.getTimeSignatureOption(newIndex);
		if (TS_Evenness.getTactusAndSuperTActusEvennessSum(newTS) < TS_Evenness.getTactusAndSuperTActusEvennessSum(originalTS)) return true;		
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
