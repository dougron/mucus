package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_distance_measure;

import java.util.ArrayList;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RNDValueObject;

public class ParameterDistanceMeasure
{
	
	public static RMRandomNumberContainer getQuantisedContainer
	(
			RMRandomNumberContainer aRndContainer,
			RandomMelodyGenerator aRmg
			)
	{
		RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
		for (Parameter p: Parameter.values())
		{
			RNDValueObject rvo = aRndContainer.get(p);
			if (rvo != null)
			{
				rndContainer.put(p, rvo.getQuantizedDeepCopy(p, aRmg));
			}		
		}
		return rndContainer;
	}

	
	
//	public static RMRandomNumberContainer getQuantisedContainerOLD
//	(
//			RMRandomNumberContainer aRndContainer,
//			RandomMelodyGenerator aRmg
//			)
//	{
//		RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
//		ArrayList<Double> list;
//		ArrayList<Double> newList;
//		for (Parameter p: Parameter.values())
//		{
//			list = aRndContainer.get(p);
//			newList = new ArrayList<Double>();
//			if (list != null)
//			{
//				if (p == Parameter.MUG_LISTS)
//				{
//					int countOptions = aRmg.getEmbellishmentCountOptionsLength();
//					int mugListOptionCount = aRmg.getOptionCount(p);
//					int rrpOptionCount = aRmg.getEmbellishmentRhythmOptionsCount();
//					boolean isCounter = true;
//					boolean first = true;
//					int count = 0;
//					for (Double d : list)
//					{
//						if (isCounter)
//						{
//							int quantValue = addQuantizedValueToList(newList, countOptions, d);
//							isCounter = false;
//							first = true;
//							count = aRmg.getEmbellishmentCountOption(quantValue);
//						} else
//						{
//							if (first)
//							{
//								addQuantizedValueToList(newList, mugListOptionCount, d);
//								first = false;
//							} else
//							{
//								addQuantizedValueToList(newList, rrpOptionCount, d);
//								first = true;
//								count--;
//								if (count == 0)
//									isCounter = true;
//							}
//
//						}
//					}
//				} else
//				{
//					int optionCount = aRmg.getOptionCount(p);
//					if (optionCount == -1)
//					{
//						for (Double d : list)
//							newList.add(d);
//					} else
//					{
//						for (int i = 0; i < list.size(); i++)
//						{
//							int quantValue = (int) ((list.get(i) * optionCount));
//							double newValue = (double) quantValue / optionCount;
//							newList.add(newValue);
//						}
//					}
//				} 
//			}
//			rndContainer.put(p, newList);
//		}
//		return rndContainer;
//	}



	public static int addQuantizedValueToList(ArrayList<Double> newList, int countOptions, Double d)
	{
		int quantValue = (int)((d * countOptions));
		double newValue = (double)quantValue / countOptions;
		newList.add(newValue);
		return quantValue;
	}
	
	
	
	public static double getSquaredDistanceMeasure
	(
			RMRandomNumberContainer rndContainer,
			RMRandomNumberContainer newRndContainer,
			RandomMelodyGenerator aRmg
			)
	{
		double distance = 0.0;
		RMRandomNumberContainer quantRndContainer1 = getQuantisedContainer(rndContainer, aRmg);
		RMRandomNumberContainer quantRndContainer2 = getQuantisedContainer(newRndContainer, aRmg);
		for (Parameter p: Parameter.values())
		{
			RNDValueObject rvo1 = quantRndContainer1.get(p);
			RNDValueObject rvo2 = quantRndContainer2.get(p);
			if (rvo1 == null)
			{
				if (rvo2 != null)
				{
					distance += rvo2.getSquaredDistanceMeasure(rvo1);
				}
			}
			else
			{
				distance += rvo1.getSquaredDistanceMeasure(rvo2);
			}
		}
		return distance;
	}
	
	
	// distance squared beyween parameters. missing parameters in lists with an unequal count of values are seen a 0.0
	public static double getSquaredDistanceMeasureOLD
	(
			RMRandomNumberContainer rndContainer,
			RMRandomNumberContainer newRndContainer,
			RandomMelodyGenerator aRmg
			)
	{
		double distance = 0.0;
		for (Parameter p: Parameter.values())
		{
			RMRandomNumberContainer quantRndContainer = getQuantisedContainer(rndContainer, aRmg);
			RMRandomNumberContainer quantRndContainer2 = getQuantisedContainer(newRndContainer, aRmg);
			RNDValueObject rvo1 = quantRndContainer.get(p);
			RNDValueObject rvo2 = quantRndContainer2.get(p);
			if (rvo1 == null)
			{
				if (rvo2 != null) distance += rvo2.getSquaredDistanceMeasure(rvo1);
			}
			else 
			{
				distance += rvo1.getSquaredDistanceMeasure(rvo1);
			}
		}
		return distance;
	}
	
	
	
	

}
