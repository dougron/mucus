package test.java.com.dougron.mucus.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.bot_personality.BotPersonality;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;

class BotPersonality_Tests
{

//	@Test
//	void ApprovedRejectedDistanceMeasure_sorts_highest_to_lowest () throws Exception
//	{
//		ApprovedRejectedDistanceMeasure a1 = new ApprovedRejectedDistanceMeasure();
//		a1.setProvocationFactorDistanceScore(0.0);
//		ApprovedRejectedDistanceMeasure a2 = new ApprovedRejectedDistanceMeasure();
//		a2.setProvocationFactorDistanceScore(1.0);
//		List<ApprovedRejectedDistanceMeasure> list = Arrays.asList(a1, a2);
//		Collections.sort(list, ApprovedRejectedDistanceMeasure.provocationDistanceComparator);
//		assertEquals(a2, list.get(0));
//	}
//	
//	
//	@Test
//	void bot_personality_safe_and_pleasing_returns_correct_ApprovedRejectedDistanceMeasure () throws Exception
//	{
//		ArrayList<ApprovedRejectedDistanceMeasure> ardmList = new ArrayList<ApprovedRejectedDistanceMeasure>();
//		Object[][] arr = new Object[][]
//				{
//						new Object[] {"0, 0", 0.0, 0.0},
//						new Object[] {"0, 1", 0.0, 1.0},
//						new Object[] {"1, 0", 1.0, 0.0},
//						new Object[] {"1, 1", 1.0, 1.0},
//						new Object[] {"1, 2", 1.0, 2.0},
//						new Object[] {"0, 2", 0.0, 2.0},
//						new Object[] {"2, 0", 2.0, 0.0},
//						new Object[] {"2, 1", 2.0, 1.0},
//						new Object[] {"2, 2", 2.0, 2.0},
//				};
//		int index = 0;
//		for (Object[] oarr: arr)
//		{
//			MucusInteractionData mid = new MucusInteractionData((String)oarr[0]);
//			mid.appendJSONThinking("bot_variation", index);
//			ardmList.add(new ApprovedRejectedDistanceMeasure(mid, (double)oarr[1], (double) oarr[2]));
//			
//			index++;
//		}
//
//		BotPersonality bp = new BotPersonality(0.0, 0.0);
//		bp.addDistancePercentagesToDistanceMeasure(ardmList);
//		bp.addProvocationFactorSortScore(ardmList);
//		assertEquals("0, 2", bp.getTopOfSortedList(ardmList).getThinkingToString());
//		
//		bp = new BotPersonality(0.0, 1.0);
//		bp.addDistancePercentagesToDistanceMeasure(ardmList);
//		bp.addProvocationFactorSortScore(ardmList);
//		assertEquals("2, 2", bp.getTopOfSortedList(ardmList).getThinkingToString());
//		
//		bp = new BotPersonality(1.0, 0.0);
//		bp.addDistancePercentagesToDistanceMeasure(ardmList);
//		bp.addProvocationFactorSortScore(ardmList);
//		assertEquals("0, 2", bp.getTopOfSortedList(ardmList).getThinkingToString());
//		
//		bp = new BotPersonality(1.0, 1.0);
//		bp.addDistancePercentagesToDistanceMeasure(ardmList);
//		bp.addProvocationFactorSortScore(ardmList);
//		assertEquals("2, 2", bp.getTopOfSortedList(ardmList).getThinkingToString());
//	}
	
	
	@Test
	void resolving_the_blah() throws Exception
	{
		BotPersonality bp = new BotPersonality(0.0, 0.0);
//		ApprovedRejectedDistanceMeasure x = new 
	}

}
