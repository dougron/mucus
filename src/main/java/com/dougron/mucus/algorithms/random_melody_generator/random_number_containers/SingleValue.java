package main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers;

import java.util.ArrayList;

import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.ParameterAndIndex;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public class SingleValue implements RNDValueObject
{
	double value;
	
	public SingleValue (double aValue)
	{
		value = aValue;
	}
	
	
	@Override
	public double getValue ()
	{
		return value;
	}
	
	
	public String toString()
	{
		return "SingleValue: " + value;
	}


	@Override
	public ArrayList<Double> getValueList ()
	{
//		return new ArrayList<Double>(){{add(value);}};
		return null;
	}


	@Override
	public double getValue (int aIndex)
	{
		return value;
	}


	@Override
	public void add (int aIndex, double aValue)
	{
		// does nothing for this class
		
	}


	@Override
	public ArrayList<Double> getValueList (int aIndex)
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RNDValueObject deepCopy ()
	{
		return new SingleValue(value);
	}


	@Override
	public String getTextFileString ()
	{
		return "SingleValue:" + value;
	}


	@Override
	public ArrayList<ParameterAndIndex> getEqualOpportunityParameterAndIndexList (
			Parameter aParameter)
	{
		ArrayList<ParameterAndIndex> list = new ArrayList<ParameterAndIndex>();
		list.add(new ParameterAndIndex(aParameter, 0, 0));
		return list;	
	}


	@Override
	public double getValue (int aParameterIndex, int aListIndex)
	{
		return value;
	}


	@Override
	public void change (ParameterAndIndex pai)
	{
		value = pai.getEndValue();	
	}


	@Override
	public RNDValueObject getQuantizedDeepCopy (Parameter aParameter,
			RandomMelodyGenerator aRmg)
	{
		int optionCount = aRmg.getOptionCount(aParameter);
		double quantizedValue = (double)((int)(value * optionCount)) / optionCount;
		return new SingleValue(quantizedValue);
	}


	@Override
	public double getSquaredDistanceMeasure (RNDValueObject aRvo)	// assumes aRvo is the same subclass as this
	{
		if (aRvo == null)
		{
			return Math.pow(value, 2.0);
		}
		return Math.pow((value - aRvo.getValue()), 2.0);
	}


	@Override
	public int getValueCount ()
	{
		return 1;
	}


	@Override
	public void addSelfToJSON (Parameter aParameterKey, JSONObject aJSONObject)
	{
		aJSONObject.put(aParameterKey.name(), value);
		
	}
	
	
	@Override
	public Object getAsJSON ()
	{
		return value;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}