package main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.ParameterAndIndex;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class IndexedValueList implements RNDValueObject
{
	
	LinkedHashMap<Integer, ArrayList<Double>> map = new LinkedHashMap<Integer, ArrayList<Double>>();
	TreeSet<Integer> sortedKeySet = new TreeSet<Integer>();
	private static final ArrayList<Double> emptyList = new ArrayList<Double>();
	
	
	
	public IndexedValueList ()
	{
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public double getValue ()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public ArrayList<Double> getValueList ()	// returns null as there is no assurance of which it might return
	{
//		return ((ArrayList<ArrayList<Double>>)map.values()).get(0);
		return null;
	}

	
	
	@Override
	public double getValue (int aParameterIndex)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public void add (int aParameterIndex, double aValue)
	{
		if (!map.containsKey(aParameterIndex)) map.put(aParameterIndex, new ArrayList<Double>());
		map.get(aParameterIndex).add(aValue);
		sortedKeySet.add(aParameterIndex);
	}

	

	@Override
	public ArrayList<Double> getValueList (int aParameterIndex)
	{
		if (map.containsKey(aParameterIndex))
		{
			return map.get(aParameterIndex);
		}
		return null;
	}
	
	
	
	@Override
	public String toString ()
	{
		StringBuilder sb = new StringBuilder("IndexedValueList");
		for (Integer key: map.keySet())
		{
			sb.append("\n" + key + "=");
			for (Double d: map.get(key))
			{
				sb.append(d + ",");
			}
		}
		return sb.toString();
	}


	
	@Override
	public RNDValueObject deepCopy ()
	{
		IndexedValueList newVo = new IndexedValueList();
		for (Integer key: map.keySet())
		{
			for (double d: map.get(key))
			{
				newVo.add(key, d);
			}	
		}
		return newVo;
	}


	
//	private ArrayList<Double> getCopyOfArrayList (ArrayList<Double> aList)
//	{
//		ArrayList<Double> list = new ArrayList<Double>();
//		list.addAll(aList);
//		return list;
//	}


	
	@Override
	public String getTextFileString ()
	{
		StringBuilder sb = new StringBuilder("IndexedValueList:");
		for (Integer key: map.keySet())
		{
			sb.append(key + "=");
			for (Double d: map.get(key))
			{
				sb.append(d + ",");
			}
			sb.append(";");
		}
		return sb.toString();
	}


	
	@Override
	public ArrayList<ParameterAndIndex> getEqualOpportunityParameterAndIndexList (
			Parameter aParameter)
	{
		ArrayList<ParameterAndIndex> list = new ArrayList<ParameterAndIndex>();
		
		for (Integer key: sortedKeySet)
		{
			ArrayList<Double> valueList = map.get(key);
			for (int i = 0; i < valueList.size(); i++)
			{
				ParameterAndIndex pai = new ParameterAndIndex(aParameter, key, i);
				pai.setStartValue(valueList.get(i));
				list.add(pai);
			}			
		}
		return list;
	}


	
	@Override
	public double getValue (int aParameterIndex, int aListIndex)
	{
		if (map.containsKey(aParameterIndex))
		{
			ArrayList<Double> list = map.get(aParameterIndex);
			if (list.size() > aListIndex) return list.get(aListIndex);			
		}
		return 0;
	}

	

	@Override
	public void change (ParameterAndIndex pai)
	{
		if (map.containsKey(pai.getParameterIndex()))
		{
			ArrayList<Double> list = map.get(pai.getParameterIndex());
			if (list.size() > pai.getListIndex())
			{
				list.add(pai.getListIndex(), pai.getEndValue());
				list.remove(pai.getListIndex() + 1);
			}				
		}			
	}

	
	
	@Override
	public RNDValueObject getQuantizedDeepCopy (Parameter aParameter,
			RandomMelodyGenerator aRmg)
	{
		int optionCount = aRmg.getOptionCount(aParameter);
		RNDValueObject rvo = new IndexedValueList();
		for (Integer key: sortedKeySet)
		{
			for (Double d: map.get(key))
			{
				rvo.add(key, (double)((int)(d * optionCount)) / optionCount);
			}	
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
			ArrayList<Double> list1 = getValueList(key);
			if (list1 == null) list1 = new ArrayList<Double>();
			ArrayList<Double> list2;
			if (aRvo == null)
			{
				list2 = emptyList;
			}
			else
			{
				list2 = aRvo.getValueList(key);
				if (list2 == null) list2 = emptyList;
			}					
			int length = list1.size();
			if (list2.size() > length) length = list2.size();
			for (int i = 0; i < length; i++)
			{
				x = 0.0;
				y = 0.0;
				if (i < list2.size()) y = list2.get(i);
				if (i < list1.size()) x = list1.get(i);
				distance += Math.pow(x - y, 2.0);
			}
		}
		return distance;
	}
	
	
	
	protected HashSet<Integer> getKeySetForBothRVOs (RNDValueObject aRvo)
	{
		HashSet<Integer> keySet = new HashSet<Integer>();
		for (Integer key: map.keySet()) keySet.add(key);
		if (aRvo != null)
		{
			for (Integer key : ((IndexedValueList) aRvo).map.keySet())
				keySet.add(key);
		}
		return keySet;
	}



	@Override
	public int getValueCount ()
	{
		int count = 0;
		for (ArrayList<Double> list: map.values())
		{
			count += list.size();
		}
		return count;
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
