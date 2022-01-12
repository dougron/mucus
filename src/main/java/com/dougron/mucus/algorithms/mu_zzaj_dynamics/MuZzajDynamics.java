package main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics;

import java.util.List;

import main.java.com.dougron.mucus.mu_framework.Mu;

public interface MuZzajDynamics
{
	public double[] getPrediction(List<Mu> aSortedMuList);
}
