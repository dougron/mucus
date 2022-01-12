package main.java.com.dougron.mucus.algorithms.superimposifier;

import org.json.JSONObject;

import main.java.com.dougron.mucus.mu_framework.Mu;

public class DurationModel
{
	
	private double duration;


	public DurationModel(double aDurationInQuarters)
	{
		duration = aDurationInQuarters;
	}
	

	public void setDurations(Mu parentMu)
	{
		for (Mu mu: parentMu.getMus())
		{
			mu.setLengthInQuarters(duration);
		}
	}


	public JSONObject getAsJSON ()
	{
		JSONObject json = new JSONObject();
		json.put("duration", duration);
		return json;
	}
}
