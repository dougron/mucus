package main.java.com.dougron.mucus.algorithms.superimposifier;

import java.util.List;

import main.java.com.dougron.mucus.mu_framework.Mu;

public interface SuFiSu
{

	boolean addSufi(SuFi aSufi);

	Mu getMu();

	boolean setMu(Mu aNewMu);

	void generate();

	SuFiNote getLastNote();

	List<SuFi> getSufis();

	SuFi getSufi(int index);

}