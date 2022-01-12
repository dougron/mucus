package main.java.com.dougron.mucus.algorithms.part_generators.drum_part_generator.beat_strength_map;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;


import time_signature_utilities.time_signature.TimeSignature;

class BeatStrengthMap_Tests
{

	@Test
	void beat_strength_map_for_4_4_time_returns_correct_values () throws Exception
	{
		TimeSignature ts = TimeSignature.FOUR_FOUR;
		Map<Integer, Double[]> map = BeatStrengthMap.makeBeatStrengthMap(ts);
		assertThat(map.get(0)[0]).isEqualTo(0.0);
		assertThat(map.get(1)[0]).isEqualTo(2.0);
		assertThat(map.get(2)[0]).isEqualTo(1.0);
		assertThat(map.get(2)[1]).isEqualTo(3.0);
		assertThat(map.get(3)[0]).isEqualTo(0.5);
		assertThat(map.get(3)[1]).isEqualTo(2.5);
		assertThat(map.get(4)[0]).isEqualTo(1.5);
		assertThat(map.get(4)[1]).isEqualTo(3.5);
	}
	
	
//	@Test
//	void print_beat_strength_map()throws Exception
//	{
//		TimeSignature ts = TimeSignature.SEVEN_EIGHT_223;
//		Map<Integer, Double[]> map = BeatStrengthMap.makeBeatStrengthMap(ts);
//		for (Integer key: map.keySet())
//		{
//			System.out.println("key=" + key);
//			for (Double d: map.get(key))
//			{
//				System.out.println(d);
//			}
//		}
//	}
	
	
//	@Test
//	void highLowValue_from_7_8_timesignature () throws Exception
//	{
//		TimeSignature ts = TimeSignature.SEVEN_EIGHT_223;
//		Map<Integer, Double[]> map = BeatStrengthMap.makeBeatStrengthMap(ts);
////		HighLowValue hlv = KikSnrMap.getInstance().getHighLowValue(0, 0);
//		for (int drive = 0; drive < 4; drive++)
//		{
//			for (int sync = 0; sync < 4; sync++)
//			{
//				System.out.println(drive + "," + sync + ": " + KikSnrMap.getInstance().getHighLowValue(drive, sync));
//			}
//		}
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
