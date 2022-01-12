package main.java.com.dougron.mucus.algorithms.superimposifier;

import org.json.JSONObject;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

public class PitchModel
{

	private static final int DEFAULT_VELOCITY = 48;
	private int[] pitchOffsets;

	
	
	public PitchModel(int[] somePitchOffsets)
	{
		pitchOffsets = somePitchOffsets;
	}
	
	
	
	public void addPitchesToMus(Mu parentNote)
	{
		int referencePitch = parentNote.getTopPitch();
		int index = 0;
		for (Mu mu: parentNote.getMus())
		{
			mu.addMuNote(new MuNote(referencePitch + pitchOffsets[index], DEFAULT_VELOCITY));
			index++;
			if (index >= pitchOffsets.length) index = 0;
		}
	}



	public JSONObject getAsJSON ()
	{
		JSONObject json = new JSONObject();
		json.put("pitchOffsets", pitchOffsets);
		return json;
	}
	
}
