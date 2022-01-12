package main.java.com.dougron.mucus.algorithms.superimposifier.overwritable_vectors;

import java.util.ArrayList;
import java.util.List;

import ResourceUtils.RandomNumberSequence;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class SuFi_SimpleStructureTones implements SuFi
{
	
	
	private SuFiSu parent;
	private double startPercent;
	private double endPercent;
	private RandomNumberSequence rnd;

	List<SuFiVector> vectors;
	private boolean hasEmbellishmentGenerator = false;
	private SuFi[] embellishmentGenerators;
	private int embellishmentGeneratorsReadIndex = 0;

	
	public SuFi_SimpleStructureTones(double aStartPercent, double aEndPercent)
	{
		startPercent = aStartPercent;
		endPercent = aEndPercent;
		rnd = new RandomNumberSequence(24, 1);
		vectors = new ArrayList<SuFiVector>();
	}
	
	
	
	public SuFi_SimpleStructureTones(double aStartPercent, double aEndPercent, SuFi aEmbellishmentGenerator)
	{
		startPercent = aStartPercent;
		endPercent = aEndPercent;
		rnd = new RandomNumberSequence(24, 1);
		vectors = new ArrayList<SuFiVector>();
		hasEmbellishmentGenerator  = true;
		embellishmentGenerators = new SuFi[] {aEmbellishmentGenerator};
	}
	
	
	
	public SuFi_SimpleStructureTones(double aStartPercent, double aEndPercent, SuFi[] aEmbellishmentGenerators)
	{
		startPercent = aStartPercent;
		endPercent = aEndPercent;
		rnd = new RandomNumberSequence(24, 1);
		vectors = new ArrayList<SuFiVector>();
		hasEmbellishmentGenerator  = true;
		embellishmentGenerators = aEmbellishmentGenerators;
	}



	@Override
	public boolean isWithinScope(double pos)
	{
		Mu mu = parent.getMu();
		double lengthInQuarters = mu.getLengthInQuarters();
		if (pos >= startPercent * lengthInQuarters && pos < endPercent * lengthInQuarters) return true;
		return false;
	}
	
	
	
	private boolean isWithinScope(BarsAndBeats bab)
	{
		Mu mu = parent.getMu();
		return isWithinScope(mu.getPositionInQuarters(bab));
	}

	

	@Override
	public SuFiNote getNextNote()
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public SuFiNote getFirstNote()
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public boolean setParent(SuFiSu aSuFiSu)
	{
		boolean wasSet = false;
		if (aSuFiSu != null)
		{
			parent = aSuFiSu;
			wasSet = true;
			
		}
		return wasSet;
	}

	
	
	private void makeVectorList()
	{
		int lengthOfParentInBars = parent.getMu().getLengthInBars();
		for (int index = 0; index < lengthOfParentInBars; index++)
		{
			BarsAndBeats bab = new BarsAndBeats(index, 0.0);
			
			if (isWithinScope(bab))
			{
				int vector = getRandomVector();
				int count = getRandomIntervalInDiatonicSteps();
				SuFiVector sv = new SuFiVector(bab, SuFiVector.DIATONIC_NOTE, vector * count, parent.getMu());
				setSVEmbellishmentGenerator(sv);
				vectors.add(sv);
			}
		}	
	}



	private int getRandomIntervalInDiatonicSteps()
	{
		int count = (int)(rnd.next() * 2 + 2);
		return count;
	}



	private int getRandomVector()
	{
		int vector = (int)(Math.signum(rnd.next() * 2 - 1));
		if (vector == 0) vector = 1;
		return vector;
	}



	private void setSVEmbellishmentGenerator(SuFiVector sv)
	{
		if (hasEmbellishmentGenerator)
		{
			sv.setEmbellishmentGenerator(getNextEmbellishmentGenerator().getDeepCopy());
		}		
	}
	
	
	
	private SuFi getNextEmbellishmentGenerator()
	{
		SuFi sufi = embellishmentGenerators[embellishmentGeneratorsReadIndex ];
		embellishmentGeneratorsReadIndex ++;
		if (embellishmentGeneratorsReadIndex == embellishmentGenerators.length) embellishmentGeneratorsReadIndex = 0;
		return sufi;
	}



	@Override
	public void reset()
	{
		// TODO Auto-generated method stub

	}

	
	
	@Override
	public void generate()
	{
		if (parent instanceof SuFiSu_OverwritableVectors)
		{
			makeVectorList();
			SuFiSu_OverwritableVectors castParent = (SuFiSu_OverwritableVectors)parent;
			for (SuFiVector sv: castParent.vectors)
			{
				if (!(isWithinScope(sv.getPositionInBarsAndBeats()))) vectors.add(sv);
			}
			castParent.vectors = vectors;
		}
	}



	@Override
	public SuFi getDeepCopy()
	{
		if (hasEmbellishmentGenerator)
		{
			return new SuFi_SimpleStructureTones(startPercent, endPercent, embellishmentGenerators);
		}
		else 
		{
			return new SuFi_SimpleStructureTones(startPercent, endPercent);
		}
	}
	
	
	
	public String toString()
	{
		String str = "no embellishmentGenerator";
		if (hasEmbellishmentGenerator) str = embellishmentGenerators.toString();
		return "SuFi_SimpleStructureTones: " + startPercent + " to " + endPercent + " " + str;
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub
		
	}

}
