package main.java.com.dougron.mucus.algorithms.mu_generator;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class SuFi_AddEmbellishmentToThisMu implements SuFi
{

	private SuFiSu parent;



	public SuFi_AddEmbellishmentToThisMu(SuFi aSuFi)
	{
		// TODO Auto-generated constructor stub
	}



	public SuFi_AddEmbellishmentToThisMu()
	{
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
	public boolean setParent(SuFiSu sufisu)
	{
		parent = sufisu;
		return true;
	}

	
	
	@Override
	public void reset()
	{
		// TODO Auto-generated method stub

	}
	
	
	
	@Override
	public void generate()
	{
		Mu parentMu = parent.getMu();
		if (parentMu.getMuNotes().size() > 0)
		{
			parentMu.addMu(new Mu("one"), new BarsAndBeats(0, 0.0));
			parentMu.addMu(new Mu("two"), new BarsAndBeats(0, 0.0));
		}		
	}

	
	
	@Override
	public SuFi getDeepCopy()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub
		
	}

}
