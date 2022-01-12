package main.java.com.dougron.mucus.algorithms.superimposifier.overwritable_vectors;

import java.util.Comparator;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.interval_types.ChordTone;
import main.java.com.dougron.mucus.algorithms.superimposifier.interval_types.ChromaticNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.interval_types.DiatonicNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.interval_types.IntervalType;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class SuFiVector
{

//	public static enum IntervalType{CHORD_TONE, DIATONIC_NOTE, CHROMATIC_NOTE};
	public static final IntervalType CHORD_TONE = new ChordTone();
	public static final IntervalType DIATONIC_NOTE = new DiatonicNote();
	public static final IntervalType CHROMATIC_NOTE = new ChromaticNote();
	
	private BarsAndBeats positionInBarsAndBeats;
	private double positionInQuarters;
	private int vector;
	private IntervalType intervalType;
	
	
	private boolean hasEmbellishmentGenerator = false;
	private SuFi embellishmentGenerator;
	
	
	
	public SuFiVector(BarsAndBeats aPositionInBarsAndBeats, IntervalType aIntervalType, int aVector, Mu aMu)
	{
		positionInBarsAndBeats = aPositionInBarsAndBeats;
		intervalType = aIntervalType;
		vector = aVector;
		positionInQuarters = aMu.getPositionInQuarters(aPositionInBarsAndBeats);
	}
	
		
	
	public BarsAndBeats getPositionInBarsAndBeats()
	{
		return positionInBarsAndBeats;
	}
	
	
	
	public void setPositionInBarsAndBeats(BarsAndBeats positionInBarsAndBeats)
	{
		this.positionInBarsAndBeats = positionInBarsAndBeats;
	}

	
	
	public double getPositionInQuarters()
	{
		return positionInQuarters;
	}
	
	
	
	public void setPositionInQuarters(double positionInQuarters)
	{
		this.positionInQuarters = positionInQuarters;
	}

	
	
	public int getVector()
	{
		return vector;
	}

	
	
	public void setVector(int vector)
	{
		this.vector = vector;
	}

	
	
	public IntervalType getIntervalType()
	{
		return intervalType;
	}

	
	
	public void setIntervalType(IntervalType intervalType)
	{
		this.intervalType = intervalType;
	}
	
	
	
	public boolean hasEmbellishmentGenerator()
	{
		return hasEmbellishmentGenerator;
	}
	
	
	
	public SuFi getEmbellishmentGenerator()
	{
		return embellishmentGenerator;
	}



	public void setEmbellishmentGenerator(SuFi embellishmentGenerator)
	{
		this.embellishmentGenerator = embellishmentGenerator;
		hasEmbellishmentGenerator = true;
	}



	public static Comparator<SuFiVector> positionInQuartersComparantor = new Comparator<SuFiVector>()
	{

		@Override
		public int compare(SuFiVector o1, SuFiVector o2)
		{
			if (o1.getPositionInQuarters() > o2.getPositionInQuarters()) return 1;
			if (o1.getPositionInQuarters() < o2.getPositionInQuarters()) return -1;
			return 0;
		}
		
	};
	
}
