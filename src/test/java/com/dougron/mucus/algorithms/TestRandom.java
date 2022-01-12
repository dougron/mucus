package test.java.com.dougron.mucus.algorithms;

import java.util.ArrayList;
import java.util.Random;

// Random that returns a particular value for testing purposes
public class TestRandom extends Random
{
	
/**
	 * 
	 */
	private static final long serialVersionUID = -5462127608116260963L;
	
	
	//	double value;
	ArrayList<Double> list = new ArrayList<Double>();
	int readIndex = 0;
	
	
	public TestRandom(double aValue)
	{
		list.add(aValue);
	}
	
	
	public TestRandom(double[] aValues)
	{
		for (double value: aValues)
		{
			list.add(value);
		}
	}
	
	
	
	public double nextDouble()
	{
		double x = list.get(readIndex);
		readIndex++;
		if (readIndex == list.size()) readIndex = 0;
		return x;
	}
}