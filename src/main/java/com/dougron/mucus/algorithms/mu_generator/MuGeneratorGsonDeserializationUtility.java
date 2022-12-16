package main.java.com.dougron.mucus.algorithms.mu_generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MuGeneratorGsonDeserializationUtility
{

	
	public static MuGenerator getMuGenerator(String jsonString) throws ClassNotFoundException
	{
		Gson gson = new GsonBuilder().create();
		JsonObject jobj = gson.fromJson(jsonString, JsonObject.class);
		
		String classNameString = getClassNameString(jobj);
		Class clazz = Class.forName(classNameString);
		MuGenerator newMuggle = (MuGenerator)gson.fromJson(jsonString, clazz);
		return newMuggle;
	}


	
	public static MuGenerator getMuGenerator(JsonObject jobj) throws ClassNotFoundException
	{
		Gson gson = new GsonBuilder().create();
//		JsonObject jobj = gson.fromJson(jsonString, JsonObject.class);
		String classNameString = getClassNameString(jobj);
		Class clazz = Class.forName(classNameString);
		MuGenerator newMuggle = (MuGenerator)gson.fromJson(jobj, clazz);
		return newMuggle;
	}
	
	
	
	private static String getClassNameString(JsonObject jobj)
	{
		// this hack for inferring className for MuGenerators assumes only _RRP suffixed versions of MuGenerators,
		// in particular based on the options in LorezRandomGenerator,
		// and for data saved before 20 Nov 2022 which did not contain className info
		String classNameString;
		if (jobj.has("className"))
		{
			JsonElement className = jobj.get("className");
			classNameString = className.getAsString();
		}
		else
		{
			if (jobj.has("escapeToneType"))
			{
				classNameString = "main.java.com.dougron.mucus.algorithms.mu_generator.MuG_EscapeTone_RRP";
			}
			else if (jobj.has("neighbourToneType"))
			{
				classNameString = "main.java.com.dougron.mucus.algorithms.mu_generator.MuG_ApproachTone_RRP";
			}
			else if (jobj.has("chordToneType"))
			{
				classNameString = "main.java.com.dougron.mucus.algorithms.mu_generator.MuG_ChordTone_RRP";
			}
			else
			{
				classNameString = "main.java.com.dougron.mucus.algorithms.mu_generator.MuG_Anticipation_RRP";
			}
		}
		return classNameString;
	}
	
}
