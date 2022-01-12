package main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.ParameterAndIndex;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class IndexedSingleValue implements RNDValueObject
{

	HashMap<Integer, Double> map = new HashMap<Integer, Double>();

	
	@Override
	public double getValue ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public ArrayList<Double> getValueList ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public double getValue (int aParameterIndex)
	{
		if (map.containsKey(aParameterIndex))
		{
			return map.get(aParameterIndex);
		}
		return -1.0;
	}

	
	@Override
	public void add (int aIndex, double aValue)
	{
		map .put(aIndex, aValue);
		
	}

	
	@Override
	public ArrayList<Double> getValueList (int aIndex)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public String toString ()
	{
		StringBuilder sb = new StringBuilder("IndexedSingleValue");
		for (Integer key: map.keySet())
		{
			sb.append("\n" + key + "=" + map.get(key));
		}		
		return sb.toString();
	}


	@Override
	public RNDValueObject deepCopy ()
	{
		IndexedSingleValue isv = new IndexedSingleValue();
		for (Integer key: map.keySet())
		{
			isv.map.put(key, map.get(key));
		}
		return isv;
	}


	@Override
	public String getTextFileString ()
	{
		StringBuilder sb = new StringBuilder("IndexedSingleValue:");
		for (Integer key: map.keySet())
		{
			sb.append(key + "=" + map.get(key) + ";");
		}		
		return sb.toString();
	}
	
	// my very first lambda :D
	Comparator<ParameterAndIndex> parameterIndexComparator =
	(ParameterAndIndex p1, ParameterAndIndex p2) ->
	{
		if (p1.getParameterIndex() < p2.getParameterIndex()) return -1;
		if (p1.getParameterIndex() > p2.getParameterIndex()) return 1;
		return 0;
	};


	@Override
	public ArrayList<ParameterAndIndex> getEqualOpportunityParameterAndIndexList (Parameter aParameter)
	{
		ArrayList<ParameterAndIndex> list = new ArrayList<ParameterAndIndex>();
		for (Integer key: map.keySet())
		{
			ParameterAndIndex pai = new ParameterAndIndex(aParameter, key, 0);
			pai.setStartValue(map.get(key));
			list.add(pai);
		}
		Collections.sort(list, parameterIndexComparator);
		return list;
	}


	@Override
	public double getValue (int aParameterIndex, int aListIndex)
	{
		return map.get(aParameterIndex);
	}


	@Override
	public void change (ParameterAndIndex pai)
	{
		map.put(pai.getParameterIndex(), pai.getEndValue());	
	}
	
	
	
	@Override
	public RNDValueObject getQuantizedDeepCopy (Parameter aParameter,
			RandomMelodyGenerator aRmg)
	{
		int optionCount = aRmg.getOptionCount(aParameter);
		RNDValueObject rvo = new IndexedSingleValue();
		for (Integer key: map.keySet())
		{
			rvo.add(key, (double)((int)(map.get(key) * optionCount)) / optionCount);	
		}	
		return rvo;
	}


	@Override
	public double getSquaredDistanceMeasure (RNDValueObject aRvo)
	{
		HashSet<Integer> keySet = getKeySetForBothRVOs(aRvo);
		double x,y;
		double distance = 0.0;
		for (Integer key: keySet)
		{
			x = getValue(key);
			if (x < 0.0) x = 0.0;
			if (aRvo == null)
			{
				y = 0.0;
			}
			else
			{
				y = aRvo.getValue(key);
				if (y < 0.0) y = 0.0;
			}			
			distance += Math.pow(x - y, 2.0);
		}
		return distance;
	}


	protected HashSet<Integer> getKeySetForBothRVOs (RNDValueObject aRvo)
	{
		HashSet<Integer> keySet = new HashSet<Integer>();
		for (Integer key: map.keySet()) keySet.add(key);
		if (aRvo != null)
		{
			for (Integer key: ((IndexedSingleValue)aRvo).map.keySet()) keySet.add(key);
		}		
		return keySet;
	}


	@Override
	public int getValueCount ()
	{
		return map.size();
	}
	
	
	
	@Override
	public void addSelfToJSON (Parameter aParameterKey, JSONObject aJSONObject)
	{
		aJSONObject.put(aParameterKey.name(), map);
		
	}
	
	
	
	@Override
	public JSONObject getAsJSON ()
	{
		return new JSONObject(map);
	}

}
