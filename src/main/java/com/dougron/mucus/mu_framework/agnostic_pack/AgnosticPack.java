package main.java.com.dougron.mucus.mu_framework.agnostic_pack;

import java.util.LinkedHashMap;
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
 * 
 * mmmm ..... 24 June 2023, am experimenting with ditching this super class as it seems to
 * me that a pack in a project is specific to that project and must implement the way that that
 * project is going to handle all the algorithmic processes it intends to include. Also, as in the
 * case of Mubot004, the MubotPackage wraps paramter objects, a generated mu, a narrative and 
 * identification terms which do not need to be generalised (at this stage at least) to a super class 
 * in anticipation of future work. The real generalisation for the mubot framework is the algorithm
 * package folder structure. And instead of coercing the big project pack into the pack type required for each
 * algorithm when passing info to the process() (or whatever) method, actually should explicitly pass
 * stuff from the pack into such a process. mmmmm i can see the area becoming grey again. MAybe I should 
 * leave it as it is and see later.
 * 
 * ..... aaaand, left the above in for fun. Only going to remove the timeStamp
 * AgnosticPack has the following:
 * 		mu				-	generated mu, common end goal for all processes
 * 		narrativeMap 	-	log of behaviour during generation, meant for reading (mostly) and debugging
 * 		rnd				-	the rnd function used for rnd stuff, not entirely sure why its here but common enough to leave as such
 * 		name			-	human readable name, possibly used for filenames
 */


@Data
public class AgnosticPack
{
	@NonNull private String name;
	private Mu mu;
	@NonNull private transient Random rnd;
	private boolean debugMode = false;	
	private Map<String, Object> narrativeMap = new LinkedHashMap<String, Object>();
//	private String timeStamp;
	
	
	
	public AgnosticPack(@NonNull String name, @NonNull Random rnd)
	{
		this.name = name;
		this.rnd = rnd;
	}
	
	
	
//	public AgnosticPack(@NonNull String name, @NonNull Random rnd, String timeStamp)
//	{
//		this.name = name;
//		this.rnd = rnd;
//		this.timeStamp = timeStamp;
//	}



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
