package main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.ParameterAndIndex;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class ValueList implements RNDValueObject
{
	
	
	ArrayList<Double> valueList;

	
	
	public ValueList (Double[] ds)
	{
		valueList = new ArrayList<Double>();
		Collections.addAll(valueList, ds);
	}

	
	
	public ValueList (List<Double> aList)
	{
		valueList = new ArrayList<Double>();
		valueList.addAll(aList);
	}

	
	
	public ValueList (double[] aArr)
	{
		valueList = new ArrayList<Double>();
		for (double d: aArr) valueList.add(d);
	}
	
	
	



	public ValueList ()
	{
		valueList = new ArrayList<Double>();
	}



	@Override
	public double getValue ()
	{
		if (valueList.size() == 0) return -1;
		return valueList.get(0);
	}
	
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ValueList\n");
		for (Double d: valueList)
		{
			sb.append(d + "\n");
		}
		return sb.toString();
	}

	
	
	@Override
	public ArrayList<Double> getValueList ()
	{
		return valueList;
	}

	
	
	@Override
	public double getValue (int aParameterIndex)	// this index does not refer to the index in the valueList it is the key of a Map of valueLists
	{
		return getValue();
	}

	
	
	@Override
	public void add (int aParameterIndex, double aValue)
	{
		valueList.add(aValue);	
	}

	
	
	@Override
	public ArrayList<Double> getValueList (int aParameterIndex)
	{
		return valueList;
	}



	@Override
	public RNDValueObject deepCopy ()
	{
		return new ValueList(valueList);
	}



	@Override
	public String getTextFileString ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ValueList:");
		for (Double d: valueList)
		{
			sb.append(d + ",");
		}
		return sb.toString();
	}
	
	
	
	@Override
	public ArrayList<ParameterAndIndex> getEqualOpportunityParameterAndIndexList (
			Parameter aParameter)
	{
		ArrayList<ParameterAndIndex> list = new ArrayList<ParameterAndIndex>();
		for (int i = 0; i < valueList.size(); i++)
		{
			ParameterAndIndex pai = new ParameterAndIndex(aParameter, 0, i);
			pai.setStartValue(valueList.get(i));
			list.add(pai);
		}
		return list;
	}



	@Override
	public double getValue (int aParameterIndex, int aListIndex)
	{
		if (aListIndex >= valueList.size()) return -1;
		return valueList.get(aListIndex);
	}



	@Override
	public void change (ParameterAndIndex pai)
	{
		valueList.add(pai.getListIndex(), pai.getEndValue());
		valueList.remove(pai.getListIndex() + 1);	
	}
	
	
	
	@Override
	public RNDValueObject getQuantizedDeepCopy (Parameter aParameter,
			RandomMelodyGenerator aRmg)
	{
		int optionCount = aRmg.getOptionCount(aParameter);
		double[] arr = new double[valueList.size()];
		for (int i = 0; i < valueList.size(); i++)
		{
			arr[i] = (double)((int)(valueList.get(i) * optionCount)) / optionCount;	
		}	
		return new ValueList(arr);
	}



	@Override
	public double getSquaredDistanceMeasure (RNDValueObject aRvo)
	{
		ArrayList<Double> list;
		if (aRvo == null)
		{
			list = new ArrayList<Double>();
		}
		else
		{
			list = aRvo.getValueList();
		}
		int length = valueList.size();
		if (list.size() > length) length = list.size();
		double x,y;
		double distance = 0.0;
		for (int i = 0; i < length; i++)
		{
			x = 0.0;
			y = 0.0;
			if (i < list.size()) y = list.get(i);
			if (i < valueList.size()) x = valueList.get(i);
			distance += Math.pow(x - y, 2.0);
		}
		return distance;
	}



	@Override
	public int getValueCount ()
	{
		return valueList.size();
	}



	@Override
	public void addSelfToJSON (Parameter aParameterKey, JSONObject aJSONObject)
	{
		aJSONObject.put(aParameterKey.name(), valueList);
		
	}
	
	
	
	@Override
	public JSONArray getAsJSON ()
	{
		return new JSONArray(valueList);
	}
	
}