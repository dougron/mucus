package main.java.com.dougron.mucus.mu_framework.agnostic_pack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lombok.Data;
import lombok.NonNull;
import main.java.com.dougron.mucus.mu_framework.Mu;

/*
 * Superclass for Pack objects for generators, based on the demo architecture
 * in the DougzJavaz/snadbox/mu_napkin_architecture package.
 * 
 * 'napkin' because written on the back of a piece of cleaning towel from 
 * Molton Brown 
 * 
 * should eventually find its way to some top level part of the framework which can look upon 
 * generation algorithms as options of an interface. Might be in 'mucus' but could be looking 
 * at a conflict of concerns as mucus originally contained a generation algorithm....... mmmmm 
 */


@Data
public class AgnosticPack
{
	@NonNull private String name;
	private Mu mu;
	@NonNull private transient Random rnd;
	private boolean debugMode = false;	
	private Map<String, Object> narrativeMap = new HashMap<String, Object>();
	private String timeStamp;
	
	
	
	public AgnosticPack(@NonNull String name, @NonNull Random rnd)
	{
		this.name = name;
		this.rnd = rnd;
	}
	
	
	
	public AgnosticPack(@NonNull String name, @NonNull Random rnd, String timeStamp)
	{
		this.name = name;
		this.rnd = rnd;
		this.timeStamp = timeStamp;
	}



//	public String getName()
//	{
//		return name;
//	}
//
//
//
//	public void setName(String name)
//	{
//		this.name = name;
//	}
//
//
//
//	public Mu getMu()
//	{
//		return mu;
//	}
//
//
//
//	public void setMu(Mu mu)
//	{
//		this.mu = mu;
//	}
//
//
//
//	public Random getRnd()
//	{
//		return rnd;
//	}
//
//
//
//	public void setRnd(Random rnd)
//	{
//		this.rnd = rnd;
//	}
//
//
//
//	public boolean isDebugMode()
//	{
//		return debugMode;
//	}
//
//
//
//	public void setDebugMode(boolean debugMode)
//	{
//		this.debugMode = debugMode;
//	}
//
//
//
//	public Map<String, Object> getNarrativeMap()
//	{
//		return narrativeMap;
//	}
//
//
//
//	public void setNarrativeMap(Map<String, Object> narrativeMap)
//	{
//		this.narrativeMap = narrativeMap;
//	}
//
//
//
//	public String getTimeStamp()
//	{
//		return timeStamp;
//	}
//
//
//
//	public void setTimeStamp(String timeStamp)
//	{
//		this.timeStamp = timeStamp;
//	}



	public void putNarrativeElement(String key, Object narrativeObject)
	{
		narrativeMap.put(key, narrativeObject);
	}



	
}
