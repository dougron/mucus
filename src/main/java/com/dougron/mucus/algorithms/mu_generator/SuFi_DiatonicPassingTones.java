package main.java.com.dougron.mucus.algorithms.mu_generator;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class SuFi_DiatonicPassingTones implements SuFi
{
	
	
	
	private SuFiSu parent;
	private double lengthInQuarters;
	private static final int DEFAULT_EMBELLISHMENT_VELOCITY = 48;
	private AccentType accentType;

	
	
	
	public SuFi_DiatonicPassingTones(double aLengthInQuarters, AccentType aAccentType)
	{
		lengthInQuarters = aLengthInQuarters;
		accentType = aAccentType;
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
		Mu embellishmentHolder = parent.getMu();
		switch (contour)
		{
		case 0:	
			dealWithZeroContour(mu, previousMu, embellishmentHolder);
			break;
		case 1:	
			dealWithPositiveContour(mu, previousMu, embellishmentHolder);
			break;
		case -1:
			dealWithNegativeContour(mu, previousMu, embellishmentHolder);
		}
		if (accentType == SuFi.AccentType.ACCENTED && embellishmentHolder.hasMus()) moveAssociatedChordTone(mu, lengthInQuarters);
	}

	
	
	private void moveAssociatedChordTone(Mu mu, double aLengthInQuarters)
	{
		BarsAndBeats bab = mu.getPositionInBarsAndBeats();
		BarsAndBeats newbab = new BarsAndBeats(bab.getBarPosition(), bab.getOffsetInQuarters() + aLengthInQuarters);
		mu.setPositionInBarsAndBeats(newbab);
	}




	private void dealWithNegativeContour(Mu mu, Mu previousMu, Mu embellishmentHolder)
	{
		int contour = -1;
		dealWithContourAndMakeNote(mu, previousMu, contour, embellishmentHolder);
	}



	private void dealWithPositiveContour(Mu mu, Mu previousMu, Mu embellishmentHolder)
	{
		int contour = 1;
		dealWithContourAndMakeNote(mu, previousMu, contour, embellishmentHolder);	
	}



	private void dealWithContourAndMakeNote(Mu mu, Mu previousMu, int contour, Mu embellishmentHolder)
	{
		int note = mu.getTopPitch();
		int previousNote = previousMu.getTopPitch();
		
		TimeSignature ts = embellishmentHolder.getTimeSignature(0);
		double posInBar = ts.getLengthInQuarters();
		while (true)
		{
			Mu numu = new Mu("pt");
			numu.setLengthInQuarters(lengthInQuarters);
			posInBar -= lengthInQuarters;
			embellishmentHolder.addMu(numu, new BarsAndBeats(0, posInBar));
			note = getNextDiatonicNote(note, -contour, numu);
			if (note - previousNote == 0 || Math.signum(note - previousNote) == -contour)
			{
				embellishmentHolder.removeMu(numu);
				break;
			}
			numu.addMuNote(new MuNote(note, DEFAULT_EMBELLISHMENT_VELOCITY));
		}
	}



	private void dealWithZeroContour(Mu mu, Mu previousMu, Mu embellishmentHolder)
	{
		// maybe make a random neighbour tone but for now do nothing
//		Random rnd = new Random(1);
//		int noteVector = getRandomNoteVectorThatIsNotZero(rnd);
//		makeNote(mu.getTopPitch(), noteVector, lengthInQuarters, mu);
	}



//	private int getRandomNoteVectorThatIsNotZero(Random rnd)
//	{
//		int noteVector = rnd.nextInt(2) * 2 - 1;
//		return noteVector;
//	}
//
//
//
//	private void makeNote(int aPitch, int noteVector, double lengthInQuarters, Mu mu)
//	{
//		TimeSignature ts = mu.getTimeSignatureList().getTimeSignature(0);
//		double barPosInQuarters = ts.getLengthInQuarters() - lengthInQuarters;
//		Mu emb = new Mu("escape-tone");
//		emb.setLengthInQuarters(lengthInQuarters);
//		parent.getMu().addMu(emb, new BarsAndBeats(0, barPosInQuarters));
//		int pitch = getNextDiatonicNote(aPitch, noteVector, emb);
//		emb.addMuNote(new MuNote(pitch, DEFAULT_EMBELLISHMENT_VELOCITY));
//	}



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
		return new SuFi_DiatonicPassingTones(lengthInQuarters, accentType);
	}




	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub
		
	}

}
