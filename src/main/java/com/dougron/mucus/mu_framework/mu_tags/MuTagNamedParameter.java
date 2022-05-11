package main.java.com.dougron.mucus.mu_framework.mu_tags;

public enum MuTagNamedParameter
{
	// SYNCOPATED_BEAT_GLOBAL_POSITION is problematic when the mu gets 
	// moved around, say to compensate for leading notes in musicxmlmaker.
	// much better to base chord decisions on SYNCOPATED_OFFSET_IN_QUARTERS
	SYNCOPATED_BEAT_GLOBAL_POSITION,													
	BEAT_STRENGTH_VALUE, 
	STRENGTH_OF_SYNCOPATED_BEAT_POSITION, 
	SYNCOPATED_OFFSET_IN_QUARTERS, 
	
	ORIGINAL_MU
}
