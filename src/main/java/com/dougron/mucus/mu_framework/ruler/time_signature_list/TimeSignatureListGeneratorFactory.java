package main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list;

import time_signature_utilities.time_signature.TimeSignature;



/**
 * returns different types of TimeSignatureGenerators depending on the arguments passed
 * 
 * a TimeSignatureListGenerator has the rules to generate an infinitely long list of time signatures.
 * 
 * @author dougr
 *
 */


public class TimeSignatureListGeneratorFactory
{

	private static final TimeSignature DEFAULT_TIMESIGNATURE = TimeSignature.FOUR_FOUR;

	
	
	public static TimeSignatureListGenerator getGenerator()
	{
		return new SingleTimeSignature(DEFAULT_TIMESIGNATURE);
	}

	
	
	public static TimeSignatureListGenerator getGenerator(TimeSignature aTimeSignature)
	{
		return new SingleTimeSignature(aTimeSignature);
	}



	public static TimeSignatureListGenerator getGenerator(TimeSignature[] timeSignatures)
	{
		return new RepeatingTimeSignatureList(timeSignatures);
	}
	
	
	
}
