package main.java.com.dougron.mucus.algorithms.superimposifier.not_quite_sufis;

import main.java.com.dougron.mucus.algorithms.superimposifier.DurationModel;
import main.java.com.dougron.mucus.algorithms.superimposifier.PitchModel;
import main.java.com.dougron.mucus.algorithms.superimposifier.RhythmModel;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;

/**
 * class reconstituted from the repository. Does not strictly function as a superimposifier
 * but caused errors of back compatibility with Mu015 so decided to keep a version to keep the tests working
 * 
 * @author dougr
 *
 */

public class SuFi_Anticipation implements SuFi
{

	private SuFiSu parent;
	private double noteLength = 0.5;
	private int noteCount = 3;
//	private AccentType accentType;
//	private static final int DEFAULT_EMBELLISHMENT_VELOCITY = 48;
	private RhythmModel rhythmModel;
	private PitchModel pitchModel;
	private DurationModel durationModel;

	
//	public SuFi_Anticipation(int aNoteCount, double aNoteLengthInQuarters, AccentType aAccentType)
//	{
//		noteLength = aNoteLengthInQuarters;
//		noteCount = aNoteCount;
//		accentType = aAccentType;
//	}
	
	
	public SuFi_Anticipation(int aNoteCount, double aNoteLengthInQuarters)
	{
		noteLength = aNoteLengthInQuarters;
		noteCount = aNoteCount;
//		accentType = AccentType.UNACCENTED;
		rhythmModel = makeRhythmModel(aNoteCount, aNoteLengthInQuarters);
		pitchModel = new PitchModel(new int[] {0});
		durationModel = new DurationModel(aNoteLengthInQuarters);
	}



	private RhythmModel makeRhythmModel(int aNoteCount, double aNoteLengthInQuarters)
	{
		double[] arr = new double[aNoteCount];
		for (int i = 0; i < aNoteCount; i++)
		{
			arr[i] = -1 * aNoteLengthInQuarters * (i + 1);
		}
		return new RhythmModel(arr);
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
		Mu mu = parent.getMu();
//		int pitch = mu.getParent().getTopPitch();
		rhythmModel.addMus(mu);
		pitchModel.addPitchesToMus(mu);
		durationModel.setDurations(mu);
	}



	@Override
	public SuFi getDeepCopy()
	{
		return new SuFi_Anticipation(noteCount, noteLength);
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub
		
	}

}
