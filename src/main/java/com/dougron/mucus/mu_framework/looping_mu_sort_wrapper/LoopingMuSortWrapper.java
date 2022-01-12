package main.java.com.dougron.mucus.mu_framework.looping_mu_sort_wrapper;

import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import main.java.com.dougron.mucus.mu_framework.Mu;

/*
 * assumes a Mu is to play as a loop and therefore anything with a negative position
 * 
 * i.e. starting before beat 1 of bar 1, is wrapped to the end of the loop and sorted 
 * as such so that correct length calculations can be made
 * 
 */



@AllArgsConstructor
public class LoopingMuSortWrapper
{
	@Getter
	Mu mu;
	Mu parent;
	
	public double getGlobalPositionInQuarters()
	{
		if (mu.getGlobalPositionInQuarters() < 0)
		{
			return mu.getGlobalPositionInQuarters() + parent.getLengthInQuarters();
		}
		else
		{
			return mu.getGlobalPositionInQuarters();
		}
	}
	
	
	public static Comparator<LoopingMuSortWrapper> globalPositionInQuartersComparator = new Comparator<LoopingMuSortWrapper>()
	{

		@Override
		public int compare(LoopingMuSortWrapper o1, LoopingMuSortWrapper o2)
		{
			double pos1 = o1.getGlobalPositionInQuarters();
			double pos2 = o2.getGlobalPositionInQuarters();
			if (pos1 < pos2) return -1;
			if (pos2 < pos1) return 1;
			return 0;
		}		
	};
}