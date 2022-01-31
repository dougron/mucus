package main.java.com.dougron.mucus.algorithms.part_generators.drum_part_generator.beat_strength_map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class BeatStrengthMap
{

	
		// all 8ths get strength 3. All tactii get 2. All supertactii alternate 0 and 1.
		// remaining 3s alternate 3 and 4
	
		public static Map<Integer, Double[]> makeBeatStrengthMap (
				TimeSignature ts)
		{
			Map<Double, Integer> map = new TreeMap<Double, Integer>();
			
			// all strength 3
			for (double pos = 0.0; pos < ts.getLengthInQuarters(); pos += 0.5)	// 0.5 = 1/8 note resolution
			{
				map.put(pos, 3);	
			}
			
			// tactii get strength 2
			Double[] tactii = ts.getTactusAsQuartersPositions();
			for (Double d: tactii)
			{
				map.put(d, 2);
			}
			
			// supertactii altenate 0 and 1
			Double[] supertactii = ts.getSuperTactusAsQuartersPositions();
			int strength = 0;
			for (Double d: supertactii)
			{
				map.put(d, strength);
				strength = (strength + 1) % 2;
			}
			
			// remaining 3s alternate 3 and 4
			strength = 0;
			for (Double d: map.keySet())
			{
				if (map.get(d) == 3)
				{
					if (strength == 1)
					{
						map.put(d, 4);
						strength = 0;
					}
					else
					{
						strength = 1;
					}
				}
			}
			
			Map<Integer, ArrayList<Double>> nextmap = new HashMap<Integer, ArrayList<Double>>();
			for (Double key: map.keySet())
			{
				if (!nextmap.containsKey(map.get(key)))
				{
					nextmap.put(map.get(key), new ArrayList<Double>());
				}
				nextmap.get(map.get(key)).add(key);
			}
			return convertMapToOutputFormat(nextmap);
		}
		
		
		
		private static Map<Integer, Double[]> convertMapToOutputFormat (
				Map<Integer, ArrayList<Double>> map)
		{
			Map<Integer, Double[]> newMap = new HashMap<Integer, Double[]>();
			for (Integer key: map.keySet())
			{
				newMap.put(key, map.get(key).toArray(new Double[map.get(key).size()]));
			}
			return newMap;
		}
}
