package main.java.com.dougron.mucus.algorithms.superimposifier.interval_types;

import main.java.com.dougron.mucus.mu_framework.Mu;

public class ChromaticNote implements IntervalType
{

	@Override
	public int getNearestNote(int aPitch, int aVector, Mu aMu)
	{
		return aPitch;
	}

}
