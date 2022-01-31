package main.java.com.dougron.mucus.algorithms.superimposifier.not_quite_sufis;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

/*
 * generates escape tones, including neighbour tones which are a special case of escape tone for repeated notes
 */

public class SuFi_EscapeTone implements SuFi
{
	
	public enum EscapeToneType{STEP_JUMP, JUMP_STEP}
	public enum NeighbourToneType{UPPER_NEIGHBOUR, LOWER_NEIGHBOUR}
	
	
	private SuFiSu parent;
	private double lengthInQuarters;
	private EscapeToneType escapeToneType;
	private static final int DEFAULT_EMBELLISHMENT_VELOCITY = 48;
	private AccentType accentType;
	private NeighbourToneType neighbourToneType;
	
	
	
	public SuFi_EscapeTone(double aLengthInQuarters, EscapeToneType aEscapeToneType, AccentType aAccentType)
	{
		lengthInQuarters = aLengthInQuarters;
		escapeToneType = aEscapeToneType;
		accentType = aAccentType;
		neighbourToneType = NeighbourToneType.UPPER_NEIGHBOUR;
	}
	
	
	
	public SuFi_EscapeTone(double aLengthInQuarters, EscapeToneType aEscapeToneType, AccentType aAccentType, NeighbourToneType aNeighbourToneType)
	{
		lengthInQuarters = aLengthInQuarters;
		escapeToneType = aEscapeToneType;
		accentType = aAccentType;
		neighbourToneType = aNeighbourToneType;
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
		int contour = getContourToPreviousNote(mu, previousMu);
		if (accentType == SuFi.AccentType.ACCENTED) moveAssociatedChordTone(mu, lengthInQuarters);
		switch (contour)
		{
		case 0:	
			dealWithZeroContour(mu, previousMu);
			break;
		case 1:	
			dealWithPositiveContour(mu, previousMu);
			break;
		case -1:
			dealWithNegativeContour(mu, previousMu);
		}
	}

	
	
	private void moveAssociatedChordTone(Mu mu, double aLengthInQuarters)
	{
		BarsAndBeats bab = mu.getPositionInBarsAndBeats();
		BarsAndBeats newbab = new BarsAndBeats(bab.getBarPosition(), bab.getOffsetInQuarters() + aLengthInQuarters);
		mu.setPositionInBarsAndBeats(newbab);
	}



	private void dealWithNegativeContour(Mu mu, Mu previousMu)
	{
		int contour = -1;
		dealWithContourAndMakeNote(mu, previousMu, contour);
	}



	private void dealWithPositiveContour(Mu mu, Mu previousMu)
	{
		int contour = 1;
		dealWithContourAndMakeNote(mu, previousMu, contour);	
	}



	private void dealWithContourAndMakeNote(Mu mu, Mu previousMu, int contour)
	{
		switch (escapeToneType)
		{
		case JUMP_STEP:
			makeNote(mu.getTopPitch(), contour, lengthInQuarters, mu);
			break;
		case STEP_JUMP:
			makeNote(previousMu.getTopPitch(), -contour, lengthInQuarters, mu);
		}
	}



	private void dealWithZeroContour(Mu mu, Mu previousMu)
	{
		int noteVector = 0;
		switch (neighbourToneType)
		{
		case UPPER_NEIGHBOUR: 
			noteVector = 1;
			break;
		case LOWER_NEIGHBOUR:
			noteVector = -1;
			break;
		}
		makeNote(mu.getTopPitch(), noteVector, lengthInQuarters, mu);
	}



//	private int getRandomNoteVectorThatIsNotZero(Random rnd)
//	{
//		int noteVector = rnd.nextInt(2) * 2 - 1;
//		return noteVector;
//	}



	private void makeNote(int aPitch, int noteVector, double lengthInQuarters, Mu mu)
	{
		TimeSignature ts = mu.getTimeSignature(0);
		double barPosInQuarters = ts.getLengthInQuarters() - lengthInQuarters;
		Mu emb = new Mu("escape-tone");
		emb.setLengthInQuarters(lengthInQuarters);
		parent.getMu().addMu(emb, new BarsAndBeats(0, barPosInQuarters));
		int pitch = getNextDiatonicNote(aPitch, noteVector, emb);
		emb.addMuNote(new MuNote(pitch, DEFAULT_EMBELLISHMENT_VELOCITY));
	}



	private int getNextDiatonicNote(int aPitch, int noteVector, Mu aMu)
	{
		while (true)
		{
			aPitch += noteVector;
			if (aMu.isDiatonicNoteInXMLKey(aPitch)) return aPitch;
		}
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
		return new SuFi_EscapeTone(lengthInQuarters, escapeToneType, accentType);
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub
		
	}

}
