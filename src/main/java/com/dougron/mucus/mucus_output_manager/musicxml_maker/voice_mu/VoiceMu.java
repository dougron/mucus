/*CODE CAN BE EDITED - Umple no longer used*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu;
import java.util.Comparator;
import java.util.List;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;

/**
 * wrapper class for sorting a Mu into voices
 */

public class VoiceMu
{

	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//VoiceMu Attributes

	/**
	 * best not to expose mu as may be tempted to use mu positioning rather than variables below, which may not be the same after splitting for score printing
	 */
	private Mu mu;
	private double globalPositionInQuarters;
	private double lengthInQuarters;
	private int topNote;
	private int voice;
	private boolean isRest;
	private double positionInBar;
	private boolean hasPositionInBar;
	private double endPositionInBar;
	private boolean hasEndPositionInBar;
	private boolean hasTieStart;
	private boolean hasTieEnd;
	private VoiceMu previousVoiceMu;
	private VoiceMu nextVoiceMu;
	private static int roundingAccuracy = 9;
	private static double minimumLength;
	private static boolean hasMinimumLength = false;

	//VoiceMu Associations
	private MXML_Measure measure;

	//Helper Variables
	private boolean canSetTopNote;
	private boolean canSetIsRest;

	
	
	//------------------------
	// CONSTRUCTOR
	//------------------------

	
	public VoiceMu(Mu aMu)
	{
		mu = aMu;
		canSetTopNote = true;
		voice = 1;
		canSetIsRest = true;
		hasPositionInBar = false;
		hasEndPositionInBar = false;
		hasTieStart = false;
		hasTieEnd = false;
		if (aMu == null 
//				|| !aMu.hasMuNotes()
				)
		{
			isRest = true;
			topNote = -1;
		} 
		else 
		{
			setGlobalPositionInQuarters(Mu.round(mu.getGlobalPositionInQuarters()));
			setLengthInQuarters(Mu.round(mu.getLengthInQuarters()));
			int n = 0;
			for (MuNote mn: mu.getMuNotes()) 
			{
				if (mn.getPitch() > n) n = mn.getPitch();
			}
			setTopNote(n);
		}
	}

	
	//------------------------
	// INTERFACE
	//------------------------

	
	public boolean setGlobalPositionInQuarters(double aGlobalPositionInQuarters)
	{
		boolean wasSet = false;
		globalPositionInQuarters = aGlobalPositionInQuarters;
		wasSet = true;
		hasEndPositionInBar = false;
		hasPositionInBar = false;
		return wasSet;
	}

	
	
	public boolean setLengthInQuarters(double aLengthInQuarters)
	{
		boolean wasSet = false;
		lengthInQuarters = aLengthInQuarters;
		wasSet = true;
		hasEndPositionInBar = false;
		return wasSet;
	}


	
	public boolean setTopNote(int aTopNote)
	{
		boolean wasSet = false;
		if (!canSetTopNote) { return false; }
		canSetTopNote = false;
		topNote = aTopNote;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setVoice(int aVoice)
	{
		boolean wasSet = false;
		voice = aVoice;
		wasSet = true;
		return wasSet;
	}
	
	

	public boolean setIsRest(boolean aIsRest)
	{
		boolean wasSet = false;
		if (!canSetIsRest) { return false; }
		canSetIsRest = false;
		isRest = aIsRest;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setHasPositionInBar(boolean aHasPositionInBar)
	{
		boolean wasSet = false;
		hasPositionInBar = aHasPositionInBar;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setHasEndPositionInBar(boolean aHasEndPositionInBar)
	{
		boolean wasSet = false;
		hasEndPositionInBar = aHasEndPositionInBar;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setHasTieStart(boolean aHasTieStart)
	{
		boolean wasSet = false;
		hasTieStart = aHasTieStart;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setHasTieEnd(boolean aHasTieEnd)
	{
		boolean wasSet = false;
		hasTieEnd = aHasTieEnd;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setPreviousVoiceMu(VoiceMu aPreviousVoiceMu)
	{
		boolean wasSet = false;
		previousVoiceMu = aPreviousVoiceMu;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setNextVoiceMu(VoiceMu aNextVoiceMu)
	{
		boolean wasSet = false;
		nextVoiceMu = aNextVoiceMu;
		wasSet = true;
		return wasSet;
	}



	public double getGlobalPositionInQuarters()
	{
		return globalPositionInQuarters;
	}



	public double getLengthInQuarters()
	{
		return lengthInQuarters;
	}

	
	
	public int getTopNote()
	{
		return topNote;
	}

	
	
	public int getVoice()
	{
		return voice;
	}

	
	
	public boolean getIsRest()
	{
		return isRest;
	}

	
	
	public boolean getHasPositionInBar()
	{
		return hasPositionInBar;
	}

	
	
	public boolean getHasEndPositionInBar()
	{
		return hasEndPositionInBar;
	}

	
	
	public boolean getHasTieStart()
	{
		return hasTieStart;
	}

	
	
	public boolean getHasTieEnd()
	{
		return hasTieEnd;
	}



	public VoiceMu getPreviousVoiceMu()
	{
		return previousVoiceMu;
	}



	public VoiceMu getNextVoiceMu()
	{
		return nextVoiceMu;
	}


	
	public boolean isIsRest()
	{
		return isRest;
	}


	
	public boolean isHasPositionInBar()
	{
		return hasPositionInBar;
	}


	
	public boolean isHasEndPositionInBar()
	{
		return hasEndPositionInBar;
	}


	
	public boolean isHasTieStart()
	{
		return hasTieStart;
	}


	
	public boolean isHasTieEnd()
	{
		return hasTieEnd;
	}


	
	public MXML_Measure getMeasure()
	{
		return measure;
	}

	
	
	public boolean hasMeasure()
	{
		boolean has = measure != null;
		return has;
	}


	
	public boolean setMeasure(MXML_Measure aNewMeasure)
	{
		boolean wasSet = false;
		measure = aNewMeasure;
		wasSet = true;
		setHasPositionInBar(false);
		return wasSet;
	}

	
	
	public void delete()
	{
		measure = null;
	}


	/**
	 * made available only for VoiceMuplet
	 */
	protected Mu getMu(){
		return mu;
	}



	public VoiceMu deepCopy(){
		return new VoiceMu(mu);
	}



	public List<MuNote> getMuNotes(){
		return mu.getMuNotes();
	}



	public List<Mu> getMus(){
		return mu.getMus();
	}

	

	/**
	 * calculates new positionInBar if it has not been calculated 
	 * but will return the default (0.0) if measure has not been set, 
	 * as positionInBar cannot be calculated
	 */
	public double getPositionInBar(){
		if (!hasPositionInBar)
		{
			if (measure == null){
				System.out.println("Error: VoiceMu attempting to calculate positionInBar without measure being present");
			}
			else
			{
				positionInBar = getGlobalPositionInQuarters() - measure.getPositionInQuarters();
				hasPositionInBar = true;
			}
		}
		return positionInBar;
	}


	
	/**
	 * calculates new positionInBar if it has not been calculated 
	 * but will return the defaukt (0.0) if measure has not been set, 
	 * as positionInBar cannot be calculated
	 */
	public double getEndPositionInBar(){
		if (!hasEndPositionInBar)
		{
			if (measure == null){
				System.out.println("Error: VoiceMu attempting to calculate endPositionInBar without measure being present");
			}
			else
			{
				endPositionInBar = getGlobalPositionInQuarters() - measure.getPositionInQuarters() + getLengthInQuarters();
				hasEndPositionInBar = true;
			}
		}
		return endPositionInBar;
	}

	
	
	public double getGlobalEnd(){
		return globalPositionInQuarters + lengthInQuarters;
	}



	public List<MuAnnotation> getMuAnnotations(){
		return mu.getMuAnnotations();
	}
	
	
	public static int getRoundingAccuracy()
	{
		return roundingAccuracy;
	}
	
	
	
	public static double getMinimumLength()
	{
		if (!hasMinimumLength) calculateMinimumLength();		
		return minimumLength;
	}



	private static void calculateMinimumLength()
	{
		minimumLength = Math.pow(10.0, -1.0 * getRoundingAccuracy());
		hasMinimumLength = true;
	}


	public String toString(){
//	    String str;
//	    if (vm instanceof VoiceMuRest)
//	    {
//	    	str = "VoiceMuRest:---\n";
//	    }
//	    else 
//	    {
//	    	str = "VoiceMu:---\n";
//	    }
		StringBuilder sb = new StringBuilder();
		sb.append(superToString());
	    sb.append("--globalPositionInQuarters=" + getGlobalPositionInQuarters() + "\n");
	    sb.append("  positionInBar=" + getPositionInBar());
	    sb.append("  lengthInQuarters=" + getLengthInQuarters() + "\n");
	    sb.append("  topNote=" + getTopNote());
	    sb.append("  voice=" + getVoice());
	    
	    if (measure == null) 
	    {
	    	sb.append("  measure = null\n");
	    }
	    else
	    {
	    	sb.append("  measure = " + getMeasure().getMeasureNumber() + "\n");
	    }
	    
//	    sb.append("  hasPositionInBar=" + getHasPositionInBar());
//	    sb.append(" hasEndPositionInBar=" + getHasEndPositionInBar() + "\n");
	    sb.append("  hasTieStart=" + getHasTieStart());
	    sb.append("  hasTieEnd=" + getHasTieEnd() + "\n");
	    sb.append("   previousVoiceMu" + "=");
	    if (getPreviousVoiceMu() == null)
	    {
	    	sb.append("null\n");
	    }
	    else 
	    {
	    	sb.append(getPreviousVoiceMu().toShortString());
	    }
	    sb.append("   " + "nextVoiceMu=");
	    if (getNextVoiceMu() == null)
	    {
	    	sb.append("null\n");
	    }
	    else 
	    {
	    	sb.append(getNextVoiceMu().toShortString());
	    }
	    
	    
	    return sb.toString();
	}



	public String superToString(){
		return super.toString();
	}



	public String toShortString(){
		return super.toString() + "["
				+ "globalPositionInQuarters" + ":" + getGlobalPositionInQuarters()+ ","
				+ "lengthInQuarters" + ":" + getLengthInQuarters()+ "," 
				+ "topNote" + ":" + getTopNote()+ "," 
				+ "voice" + ":" + getVoice()+ ","
				+ "isRest" + ":" + getIsRest()+ "," + System.getProperties().getProperty("line.separator")
				;
	}

	
	
	//------------------------
	// COMPARATORS
	//------------------------

	public static Comparator<VoiceMu> noteComparator = new Comparator<VoiceMu>() {// highest to lowest comparator
		@Override
		public int compare(VoiceMu o1, VoiceMu o2) 
		{
			if (o1.getTopNote() > o2.getTopNote()) return -1;
			if (o1.getTopNote() < o2.getTopNote()) return 1;
			return 0;
		}};
		
		
		
	public static Comparator<VoiceMu> posComparator = new Comparator<VoiceMu>() {// lowest to highest pos and shortest to longest if pos is the same
		@Override
		public int compare(VoiceMu o1, VoiceMu o2) 
		{
			if (o1.getGlobalPositionInQuarters() > o2.getGlobalPositionInQuarters()) return 1;
			if (o1.getGlobalPositionInQuarters() < o2.getGlobalPositionInQuarters()) return -1;
			if (o1.getLengthInQuarters() > o2.getLengthInQuarters()) return 1;
			if (o1.getLengthInQuarters() < o2.getLengthInQuarters()) return -1;
			return 0;
		}};
	
		
		
	public static Comparator<VoiceMu> lengthComparator = new Comparator<VoiceMu>() {// shortest to longest
		@Override
		public int compare(VoiceMu o1, VoiceMu o2) 
		{
			if (o1.getLengthInQuarters() > o2.getLengthInQuarters()) return 1;
			if (o1.getLengthInQuarters() < o2.getLengthInQuarters()) return -1;
			return 0;
		}};
			
		
		
	public static Comparator<VoiceMu> lengthThenPosComparator = new Comparator<VoiceMu>() {// shortest to longest
		@Override
		public int compare(VoiceMu o1, VoiceMu o2) 
		{
			if (o1.getLengthInQuarters() > o2.getLengthInQuarters()) return 1;
			if (o1.getLengthInQuarters() < o2.getLengthInQuarters()) return -1;
			if (o1.getGlobalPositionInQuarters() > o2.getGlobalPositionInQuarters()) return 1;
			if (o1.getGlobalPositionInQuarters() < o2.getGlobalPositionInQuarters()) return -1;
			return 0;
		}};


		
	public static Comparator<VoiceMu> longestToShortestComparator = new Comparator<VoiceMu>() {// shortest to longest
		@Override
		public int compare(VoiceMu o1, VoiceMu o2) 
		{
			if (o1.getLengthInQuarters() > o2.getLengthInQuarters()) return -1;
			if (o1.getLengthInQuarters() < o2.getLengthInQuarters()) return 1;
			return 0;
		}};


}