package main.java.com.dougron.mucus.algorithms.mu_generator;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import time_signature_utilities.time_signature.TimeSignature;

public class SuFi_OctaveAnticipation implements SuFi
{

	private SuFiSu parent;
	private double noteLength = 0.5;
	private int noteCount = 3;
	private static final int DEFAULT_EMBELLISHMENT_VELOCITY = 48;

	
	public SuFi_OctaveAnticipation(int aNoteCount, double aNoteLengthInQuarters)
	{
		noteLength = aNoteLengthInQuarters;
		noteCount = aNoteCount;
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
		Mu mu = getAssociatedChordToneMu();
		Mu previousMu = getPreviousMu(mu);
		TimeSignature ts = mu.getTimeSignature(0);
		double barPosInQuarters = ts.getLengthInQuarters();
		int contour = getContourToPreviousNote(mu, previousMu);
		int pitch = mu.getTopPitch() + getOctaveOffset(contour);		
		for (int i = 0; i < noteCount; i++)
		{
			barPosInQuarters -= noteLength;
			Mu numu = new Mu("emb");
			numu.addMuNote(new MuNote(pitch, DEFAULT_EMBELLISHMENT_VELOCITY));
			parent.getMu().addMu(numu, new BarsAndBeats(0, barPosInQuarters));
		}
	}
	
	
	
	private int getOctaveOffset(int contour)
	{
		switch (contour)
		{
		case 1:		return -12;
		case -1:	return 12;
		case 0:		return -12;		
		}
		return 0;
	}



	private int getContourToPreviousNote(Mu mu, Mu previousMu)
	{
		int noteMu = mu.getTopPitch();
		int notePreviousMu = previousMu.getTopPitch();
		return (int)Math.signum(noteMu - notePreviousMu);
	}



	private Mu getPreviousMu(Mu aMu)
	{
		Mu parent = aMu.getParent();
		Mu theMu = parent.getMus().get(parent.getMus().size() - 1);
		for (Mu mu: parent.getMus())
		{
			if (mu == aMu) return theMu;
			theMu = mu;
		}
		return null;
	}
	
	
	
	private Mu getAssociatedChordToneMu()
	{
		Mu chordToneMu = parent.getMu();
		while (!chordToneMu.hasMuNotes())
		{
			chordToneMu = chordToneMu.getParent();
		}
		return chordToneMu;
	}



	@Override
	public SuFi getDeepCopy()
	{
		return new SuFi_OctaveAnticipation(noteCount, noteLength);
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub
		
	}

}
