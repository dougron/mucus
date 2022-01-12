package main.java.com.dougron.mucus.algorithms.superimposifier.not_quite_sufis;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class SuFi_AddBarLengthEmbellishmentHolder implements SuFi
{

	private SuFiSu parent;
	private SuFi embellishmentGenerator;



	public SuFi_AddBarLengthEmbellishmentHolder(SuFi aEmbellishmentGenerator)
	{
		embellishmentGenerator = aEmbellishmentGenerator;
	}
	
	
	
	@Override
	public boolean isWithinScope(double pos)
	{
		return true;
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

	
	
	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void generate()
	{
		Mu mu = parent.getMu();
		Mu numu = new Mu("embellishment wrapper");
		numu.setLengthInBars(1);
		mu.addMu(numu, new BarsAndBeats(-1, 0.0));
		numu.addSuFi(embellishmentGenerator);
	}



	@Override
	public SuFi getDeepCopy()
	{
		return new SuFi_AddBarLengthEmbellishmentHolder(embellishmentGenerator.getDeepCopy());
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub
		
	}

}
