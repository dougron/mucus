package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RNDValueObject;

public class ChordProgression_FewerFunctionChanges implements FeedbackObject
{

	
	
	static FeedbackObject instance;
	private static FeedbackObject[] excludeList;
	private Parameter relatedParameter = Parameter.CHORD_LIST_GENERATOR;
	
	
	
	private ChordProgression_FewerFunctionChanges()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	public static FeedbackObject getInstance()
	{
		if (instance == null)
		{
			instance = new ChordProgression_FewerFunctionChanges();
			excludeList = new FeedbackObject[] {ChordProgression_MoreFunctionChanges.getInstance()};
		}
		return instance;
	}
	
	
	
	@Override
	public String getDescription()
	{		
		return "ChordProgression_FewerFunctionChanges";
	}

	
	
	@Override
	public boolean isTrue
	(
			RMRandomNumberContainer aRndContainer, 
			RMRandomNumberContainer aNewRndContainer, 
			RandomMelodyGenerator aRmg)
	{
		
		String[] chords = aRmg.getChordSuffix();
		RNDValueObject originalRVO = aRndContainer.get(relatedParameter);
		double functionAverage = (double)getFunctionChangeCount(originalRVO, chords) / originalRVO.getValueList().size();
		RNDValueObject newRVO = aNewRndContainer.get(relatedParameter);
		double newFunctionAverage = (double)getFunctionChangeCount(newRVO, chords) / originalRVO.getValueList().size();
		
		if (newFunctionAverage < functionAverage) return true;
		return false;
	}



	public int getFunctionChangeCount 
	(
			RNDValueObject newRVO,
			String[] chords
			)
	{
		ArrayList<Integer> rootList = getRootList(newRVO.getValueList(), chords);
		ArrayList<Integer> intervalList = getIntervalList(rootList);
		
		int functionCount = getCountOfFunctionalChanges(intervalList);
		return functionCount;
	}



	public int getCountOfFunctionalChanges (ArrayList<Integer> intervalList)
	{
		int functionCount = 0;
		for (Integer i: intervalList)
		{
			if (i == 1 || i == 3) functionCount++;
		}
		return functionCount;
	}


	// returns a list of diatonic interval movement. There is no differentiationbetween a rizing interval or a falling one
	// 0 - no movement, 1 - up/down a second, 2 - up/down a third, 3 - up/down a fourth
	public ArrayList<Integer> getIntervalList (ArrayList<Integer> rootList)
	{
		ArrayList<Integer> intervalList = new ArrayList<Integer>();
		for (int i = 0; i < rootList.size(); i++)
		{
			int nextValue;
			if (i == rootList.size() - 1)
			{
				nextValue = rootList.get(0);
			}
			else
			{
				nextValue = rootList.get(i + 1);
			}
			int interval = Math.abs(rootList.get(i) - nextValue);
			if (interval == 4) interval = 3;
			if (interval == 5) interval = 2;
			intervalList.add(interval);
		}
		return intervalList;
	}



	public ArrayList<Integer> getRootList (List<Double> list, String[] chords)
	{
		ArrayList<Integer> rootList = new ArrayList<Integer>();
		for (Double d: list)
		{
			rootList.add((int)(d * chords.length));
		}
		return rootList;
	}
	
	
	
//	private double getVarietyScore (int[] aPattern)
//	{
//		int changes = getPatternChangeCount(aPattern);
//		int variations = getVariationCount(aPattern);
//		return changes * variations / aPattern.length;
//	}



	public int getVariationCount (int[] aPattern)
	{
		int variations = 0;
		for (int i: aPattern)
		{
			if (i > variations) variations = i;
		}
		variations++;
		return variations;
	}



	public int getPatternChangeCount (int[] aPattern)
	{
		int changes = 0;
		for (int i = 0; i < aPattern.length; i++)
		{
			int a = aPattern[i];
			int b;
			if (i == aPattern.length - 1)
			{
				b = aPattern[0];
			}
			else
			{
				b = aPattern[i + 1];
			}
			if (a != b) changes++;
		}
		return changes;
	}



	@Override
	public FeedbackObject[] getExcludeList()
	{
		return excludeList ;
	}



	@Override
	public Parameter getRelatedParameter()
	{
		return relatedParameter ;
	}

}
