package main.java.com.dougron.mucus.algorithms.mu_generator;

import main.java.com.dougron.mucus.algorithms.superimposifier.DurationModel;
import main.java.com.dougron.mucus.algorithms.superimposifier.PitchModel;
import main.java.com.dougron.mucus.algorithms.superimposifier.RhythmModel;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;

public class SuFi_Enclosure implements SuFi
{

	private SuFiSu parent;
	private AccentType accentType = AccentType.UNACCENTED;
	RhythmModel rhythmModel = new RhythmModel(new double[] {-0.5, -1.0});
	PitchModel pitchModel = new PitchModel(new int[] {2, -1});
	DurationModel durationModel = new DurationModel(0.5);
	
	
	public SuFi_Enclosure(AccentType aAccentType)
	{
		accentType = aAccentType;
	}



	public SuFi_Enclosure()
	{
	}



	@Override
	public boolean isWithinScope(double pos)
	{
		// TODO Auto-generated method stub
		return false;
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
		Mu parentNote = parent.getMu();
		if (accentType == AccentType.ACCENTED)
		{
			parentNote.movePosition(1.0);
		}
		rhythmModel.addMus(parentNote);
		pitchModel.addPitchesToMus(parentNote);
		durationModel.setDurations(parentNote);
	}

	
	
	@Override
	public SuFi getDeepCopy()
	{
		return new SuFi_Enclosure(accentType);
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		accentType = aAccentType;		
	}

}
