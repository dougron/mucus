package main.java.com.dougron.mucus.algorithms.superimposifier.interval_types;

import main.java.com.dougron.mucus.mu_framework.Mu;

public interface IntervalType
{
	public int getNearestNote(int aPitch, int aVector, Mu aMu);
}
