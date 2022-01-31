package main.java.com.dougron.mucus.algorithms.part_generators.drum_part_generator;

import java.util.ArrayList;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class DrumPartGenerator
{
	
	
	// String names
	public static final String HAT = "hat";
	public static final String KICK = "kick";
	public static final String SNARE = "snare";
	
	// pitches
	public static final int PITCH_CLOSED_HAT = 42;
	public static final int PITCH_KICK = 36;
	public static final int PITCH_SNARE = 37;
	
	// other defaults
	private static final int DEFAULT_HAT_VELOCITY = 48;
	private static final int DEFAULT_UNACCENTED_HAT_VELOCITY = 20;
	private static final int DEFAULT_KICK_VELOCITY = 64;
	private static final int DEFAULT_SNARE_VELOCITY = 64;
	private static final double DEFAULT_DURATION_IN_QUARTERS = 0.5;

	
	
	
	public static ArrayList<Mu> addTactusHiHatToDrumPart(Mu aMu)
	{
		ArrayList<Mu> list = new ArrayList<Mu>();
		
		Mu drumPart = getDrumPart(aMu);
		
		for (int barIndex = 0; barIndex < aMu.getEndPositionInBars(); barIndex++)
		{
			TimeSignature ts = aMu.getTimeSignature(barIndex);
			Double[] tactii = ts.getTactusAsQuartersPositions();
			for (Double pos: tactii)
			{
				Mu mu = new Mu(HAT);
				mu.addTag(MuTag.CLOSED_HIHAT);
				mu.addMuNote(new MuNote(PITCH_CLOSED_HAT, DEFAULT_HAT_VELOCITY));
				mu.setLengthInQuarters(DEFAULT_DURATION_IN_QUARTERS);
				drumPart.addMu(mu, new BarsAndBeats(barIndex, pos));
				list.add(mu);
			}
		}
		
		return list;
	}
	
	
	public static ArrayList<Mu> addKickAndSnareToDrumPart(Mu aMu)
	{
		ArrayList<Mu> list = new ArrayList<Mu>();
		
		Mu drumPart = getDrumPart(aMu);
		
		for (int barIndex = 0; barIndex < aMu.getEndPositionInBars(); barIndex++)
		{
			TimeSignature ts = aMu.getTimeSignature(barIndex);
			Double[] tactii = ts.getSuperTactusAsQuartersPositions();
			boolean doef = true;
			String name = "";
			MuTag tag = MuTag.KICK_DRUM;
			int pitch = PITCH_KICK;
			int velocity = DEFAULT_KICK_VELOCITY;
			for (Double pos: tactii)
			{
				if (doef)
				{
					name = KICK;
					tag = MuTag.KICK_DRUM;
					pitch = PITCH_KICK;
					velocity = DEFAULT_KICK_VELOCITY;
					doef = false;
				}
				else
				{
					name = SNARE;
					tag = MuTag.SNARE_DRUM;
					pitch = PITCH_SNARE;
					velocity = DEFAULT_SNARE_VELOCITY;
					doef = true;
				}
				Mu mu = new Mu(name);
				mu.addTag(tag);
				mu.addMuNote(new MuNote(pitch, velocity));
				mu.setLengthInQuarters(DEFAULT_DURATION_IN_QUARTERS);
				drumPart.addMu(mu, new BarsAndBeats(barIndex, pos));
				list.add(mu);
			}
		}
		
		return list;
	}



	public static ArrayList<Mu> addSubTactusHiHatToDrumPart(Mu aMu)
	{
		ArrayList<Mu> list = new ArrayList<Mu>();		
		Mu drumPart = getDrumPart(aMu);
		
		for (int barIndex = 0; barIndex < aMu.getEndPositionInBars(); barIndex++)
		{
			TimeSignature ts = aMu.getTimeSignature(barIndex);
			ArrayList<Double> tactii = getPositionsForOffSubTactii(ts);
			for (Double pos: tactii)
			{
				Mu mu = new Mu(HAT);
				mu.addTag(new MuTag[] {MuTag.CLOSED_HIHAT, MuTag.UNACCENTED});
				mu.addMuNote(new MuNote(PITCH_CLOSED_HAT, DEFAULT_UNACCENTED_HAT_VELOCITY));
				mu.setLengthInQuarters(DEFAULT_DURATION_IN_QUARTERS);
				drumPart.addMu(mu, new BarsAndBeats(barIndex, pos));
				list.add(mu);
			}
		}	
		return list;	
	}


	
	private static ArrayList<Double> getPositionsForOffSubTactii(TimeSignature ts)
	{
		Double[] tactii = ts.getTactusAsQuartersPositions();
		Double[] subTactii = ts.getSubTactusAsQuartersPositions();
		ArrayList<Double> offSubTactii = new ArrayList<Double>();
		for (Double d: subTactii)
		{
			if (!arrayContains(tactii, d)) offSubTactii.add(d);
		}
		return offSubTactii;
	}
	

// privates ------------------------------------------------------------------------
	
	
	private static double threshold = 0.00000001;
	private static boolean arrayContains(Double[] array, Double d)
	{
		for (Double dd: array) 
		{
			if (Math.abs(dd - d) < threshold) return true;
		}
		return false;
	}

	

	private static Mu getDrumPart(Mu aMu)
	// will only return the first one if there are more than one, will create one if there are none
	{
		ArrayList<Mu> drumParts = aMu.getMuWithTag(MuTag.PART_DRUMS);
		Mu drumPart;
		if (drumParts.size() == 0) 
		{
			drumPart = new Mu("drums");
			drumPart.addTag(MuTag.PART_DRUMS);
			aMu.addMu(drumPart, 0);
		}
		else
		{
			drumPart = drumParts.get(0);
		}
		drumPart.setLengthInBarsAndBeats(aMu.getLengthInBarsAndBeats());
		return drumPart;
	}

}
