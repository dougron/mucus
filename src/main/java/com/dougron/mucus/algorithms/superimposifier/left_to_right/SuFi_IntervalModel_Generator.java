package main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right;
import java.util.ArrayList;
import java.util.List;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;

/*
 * works with SuFiSu_LeftToRight
 */


public class SuFi_IntervalModel_Generator implements SuFi
{


	//------------------------
	// STATIC VARIABLES
	//------------------------


	public static final double DEFAULT_LENGTH = 0.5;
	public static final double DEFAULT_INTER_ONSET_INTERVAL = 1.0;


	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//SuFi_IntervalModel_Generator Attributes
	private double start;
	private double end;
	private List<Integer> intervalModel;
	private int intervalModelIndex;


	//SuFi_IntervalModel_Generator Associations
	private SuFiSu parent;



	//------------------------
	// CONSTRUCTOR
	//------------------------



	public SuFi_IntervalModel_Generator(double aStart, double aEnd, SuFiSu_LeftToRight aParent)
	{
		start = aStart;
		end = aEnd;
		intervalModel = new ArrayList<Integer>();
		intervalModelIndex = 0;
		if (!setParent(aParent))
		{
			throw new RuntimeException("Unable to create SuFi_IntervalModel_Generator due to aParent");
		}
	}

	
	public SuFi_IntervalModel_Generator(double aStart, double aEnd, int[] intervalModels)
	{
		start = aStart;
		end = aEnd;
		intervalModel = new ArrayList<Integer>();
		intervalModelIndex = 0;
		for (int i: intervalModels)
		{
			intervalModel.add(i);
		}
	}


	//------------------------
	// INTERFACE
	//------------------------



	



	public boolean setStart(double aStart)
	{
		boolean wasSet = false;
		start = aStart;
		wasSet = true;
		return wasSet;
	}



	public boolean setEnd(double aEnd)
	{
		boolean wasSet = false;
		end = aEnd;
		wasSet = true;
		return wasSet;
	}



	public boolean addIntervalModel(Integer aIntervalModel)
	{
		boolean wasAdded = false;
		wasAdded = intervalModel.add(aIntervalModel);
		return wasAdded;
	}



	public boolean removeIntervalModel(Integer aIntervalModel)
	{
		boolean wasRemoved = false;
		wasRemoved = intervalModel.remove(aIntervalModel);
		return wasRemoved;
	}



	public double getStart()
	{
		return start;
	}



	public double getEnd()
	{
		return end;
	}



	public Integer getIntervalModel(int index)
	{
		Integer aIntervalModel = intervalModel.get(index);
		return aIntervalModel;
	}



	public Integer[] getIntervalModels()
	{
		Integer[] newIntervalModel = intervalModel.toArray(new Integer[intervalModel.size()]);
		return newIntervalModel;
	}



	public int numberOfIntervalModels()
	{
		int number = intervalModel.size();
		return number;
	}



	public boolean hasIntervalModel()
	{
		boolean has = intervalModel.size() > 0;
		return has;
	}



	public int indexOfIntervalModel(Integer aIntervalModel)
	{
		int index = intervalModel.indexOf(aIntervalModel);
		return index;
	}



	public SuFiSu getParent()
	{
		return parent;
	}



	public boolean setParent(SuFiSu aNewParent)
	{
		boolean wasSet = false;
		if (aNewParent != null && aNewParent instanceof SuFiSu_LeftToRight)
		{
			parent = aNewParent;
			wasSet = true;
		}
		return wasSet;
	}



	public void delete()
	{
		parent = null;
	}



	/**
	 * sets the interval model using an easy to use java array
	 */
	public void addIntervalModel(int [] arr){
		for (int i: arr){
			addIntervalModel(i);
		}
	}



	/**
	 * test if the position that the next note is on is within the scope
	 */
	public boolean isWithinScope(double pos){
		if (pos >= start && pos < end) return true;
		return false;
	}



	/**
	 * returns the next note
	 */
	public SuFiNote getNextNote(){
		SuFiNote previousNote = parent.getLastNote();
		int note = countToNextDiatonicNote(previousNote.getNote(), previousNote.getNextInterval());
		SuFiNote newNote = new SuFiNote(
				note, 
				DEFAULT_LENGTH, 
				DEFAULT_INTER_ONSET_INTERVAL,
				intervalModel.get(intervalModelIndex));
		intervalModelIndex++;
		if (intervalModelIndex >= intervalModel.size()) intervalModelIndex = 0;
		return newNote;
	}



	private int countToNextDiatonicNote(int note, int nextInterval){
		int direction = (int)Math.signum(nextInterval);
		int count = Math.abs(nextInterval);
		while (true) {
			note += direction;
			if (parent.getMu().isDiatonicNoteInXMLKey(note)) {
				count--;
				if (count == 0) {
					return note;
				}
			}
		}
	}



	/**
	 * used for the first note
	 */
	public SuFiNote getFirstNote(){
		int note = parent.getMu().getStartPitch();
		SuFiNote newNote =  new SuFiNote(note, DEFAULT_LENGTH, DEFAULT_INTER_ONSET_INTERVAL, intervalModel.get(intervalModelIndex));
		intervalModelIndex++;
		if (intervalModelIndex >= intervalModel.size()) intervalModelIndex = 0;
		return newNote;
	}



	public void reset(){
		intervalModelIndex = 0;
	}



	public String toString()
	{
		return super.toString() + "["+
				"start" + ":" + getStart()+ "," +
				"end" + ":" + getEnd()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "parent = "+(getParent()!=null?Integer.toHexString(System.identityHashCode(getParent())):"null");
	}


	@Override
	public void generate()
	{
		// TODO Auto-generated method stub
		
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