package main.java.com.dougron.mucus.mucus_utils.mu_comparator;

import java.util.Collections;
import java.util.List;

import main.java.com.dougron.mucus.mu_framework.Mu;

public class MuComparator
{

	
	public static boolean hasSameMelody(Mu mu1, Mu mu2)
	{
		
		if (!topNoteMelodyIsSame(mu1, mu2)) return false;
		return true;
	}

	
	// one to one comparison for as many notes that can be compared
	// anything after ignored
	private static boolean topNoteMelodyIsSame(Mu mu1, Mu mu2)
	{
		List<Mu> mu1List = mu1.getMusWithNotes();
		Collections.sort(mu1List, Mu.globalPositionInQuartersComparator);
		List<Mu> mu2List = mu2.getMusWithNotes();
		Collections.sort(mu2List, Mu.globalPositionInQuartersComparator);
		int size = mu1List.size();
		if (mu2List.size() < size) size = mu2List.size();
		for (int i = 0; i < size; i++)
		{
			if (mu1List.get(i).getTopPitch() != mu2List.get(i).getTopPitch()) return false;
		}
		return true;
	}
}
