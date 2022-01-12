package main.java.com.dougron.mucus.algorithms.superimposifier;

import org.json.JSONObject;

import main.java.com.dougron.mucus.mu_framework.Mu;

public class RhythmModel
{

	
	private double[] offsetsInQuarters;

	public RhythmModel(double[] someOffsetsInQuarters)
	{
		offsetsInQuarters = someOffsetsInQuarters;
	}

	
	
	public void addMus(Mu parentNote)
	{
		int index = 0;
		for (double pos: offsetsInQuarters)
		{
			Mu mu = new Mu("note" + index);
			parentNote.addMu(mu, pos);
			index++;
		}
	}



	public JSONObject getAsJSON ()
	{
		JSONObject json = new JSONObject();
		json.put("offsetsInQuarters", offsetsInQuarters);
		return json;
	}
}
