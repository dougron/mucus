package main.java.com.dougron.mucus.mu_framework.mu_tags;

import java.util.ArrayList;
import java.util.HashMap;

public class MuTagBundle
{

	private ArrayList<MuTag> list = new ArrayList<MuTag>();
	private HashMap<MuTagNamedParameter, Object> namedParameters;
	
	
	public MuTagBundle copy()
	{
		MuTagBundle mtb = new MuTagBundle(list.toArray(new MuTag[list.size()]));
		if (namedParameters != null)
		{
			for (MuTagNamedParameter key : namedParameters.keySet())
			{
				mtb.addNamedParameter(key, (double) namedParameters.get(key));
			} 
		}
		return mtb;
	}
	
	
	public MuTagBundle(MuTag aMuTag)
	{
		addMuTag(aMuTag);
	}
	
	
	
	public MuTagBundle(MuTag[] aMuTagArr)
	{
		for (MuTag muTag: aMuTagArr)
		{
			addMuTag(muTag);
		}		
	}
	
	
	
	public void addMuTag(MuTag aMuTag)
	{
		list.add(aMuTag);
	}
	
	
	
	public boolean containsMuTag(MuTag aMuTag)
	{
		for (MuTag muTag: list)
		{
			if (muTag == aMuTag) return true;
		}
		return false;
	}
	
	
	
	public String toString()
	{
		if (list.size() == 1)
		{
			return "" + list.get(0);
		}
		else 
		{
			StringBuilder sb = new StringBuilder();
			for (MuTag muTag: list)
			{
				sb.append(muTag + "/");
			}
			return sb.toString();
		}		
	}
	
	
	public MuTag[] getMuTags()
	{
		return list.toArray(new MuTag[list.size()]);
	}



	public void addNamedParameter(MuTagNamedParameter aParameterName, Object aParameterValue)
	{
		if (namedParameters == null) namedParameters = new HashMap<MuTagNamedParameter, Object>();
		namedParameters .put(aParameterName, aParameterValue);		
	}
	
	
	
	public Object getNamedParameter(MuTagNamedParameter aParameterName)
	{
		if (namedParameters != null && namedParameters.containsKey(aParameterName)) return namedParameters.get(aParameterName);
		return null;
	}
	
	
	
	public HashMap<MuTagNamedParameter, Object> getNamedParameterMap()
	{
		return namedParameters;
	}
	
	
	
	public boolean hasNamedParameters()
	{
		if (namedParameters.size() > 0) return true;
		return false;
	}
	
	
}
