package main.java.com.dougron.mucus.algorithms.superimposifier.interval_types;

import main.java.com.dougron.mucus.mu_framework.Mu;

public class DiatonicNote implements IntervalType
{

	@Override
	public int getNearestNote(int aPitch, int aVector, Mu aMu)
	{
		while (!aMu.isDiatonicNoteInXMLKey(aPitch))
		{
			aPitch += aVector;
		}
		return aPitch;
	}
}
