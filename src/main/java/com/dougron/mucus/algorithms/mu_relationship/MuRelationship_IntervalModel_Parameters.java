package main.java.com.dougron.mucus.algorithms.mu_relationship;

import main.java.com.dougron.mucus.algorithms.mu_development_factory.MuDevelopmentFactory.Relationship;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right.SuFi_IntervalModel_Generator;
import main.java.com.dougron.mucus.mu_framework.Mu;

public class MuRelationship_IntervalModel_Parameters implements MuRelationship
{

	
	private Mu relatedMu;
	private Relationship relationship;
	private Mu dependentMu;


	
	public MuRelationship_IntervalModel_Parameters(Mu aMu, Relationship aRelationship) 
	{
		relatedMu = aMu;
		relationship = aRelationship;
	}


	
	@Override
	public void setDependantMu(Mu aMu)
	{
		dependentMu = aMu;		
	}


	
	@Override
	public void generate()
	{
		SuFiSu sufisu = relatedMu.getSuFiSu();
		for (SuFi sufi: sufisu.getSufis())
		{
			if (sufi instanceof SuFi_IntervalModel_Generator)
			{
				SuFi_IntervalModel_Generator castSufi = (SuFi_IntervalModel_Generator) sufi;
				int[] intervalModels = getCopyOfIntervalModelsAdjustedForRelationship(castSufi, relationship);
				SuFi_IntervalModel_Generator newSufi = new SuFi_IntervalModel_Generator(castSufi.getStart(), castSufi.getEnd(), intervalModels);
				dependentMu.addSuFi(newSufi);
			}
		}
		
	}



	private int[] getCopyOfIntervalModelsAdjustedForRelationship(SuFi_IntervalModel_Generator sufi,
			Relationship aRelationship)
	{
		int[] arr = new int[sufi.numberOfIntervalModels()];
		int x = 1;
		if (aRelationship == Relationship.CONTRASTING) x = -1;
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = sufi.getIntervalModel(i) * x;
		}
		return arr;
	}




	
	
}
