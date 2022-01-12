/**
 * 
 */
package main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right;


/*
 * works with SuFiSu_LeftToRight
 */


import java.util.ArrayList;
import java.util.List;

import DataObjects.combo_variables.DoubleAndDouble;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;

/**
 * @author dougr
 *
 */
public class SuFi_IntervalAndRhythmModel_Generator implements SuFi
{
	
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------


	private double start;
	private double end;
	private List<Integer> intervalModel;
	private List<DoubleAndDouble> rhythmModel;	// d1 = length, d2 = inter onset interval
	private int intervalModelIndex;
	private int rhythmModelIndex;
	
	private SuFiSu parent;
	



	//------------------------
	// CONSTRUCTOR
	//------------------------



	public SuFi_IntervalAndRhythmModel_Generator(double aStart, double aEnd, int[] intervalModels, double[][] rhythmModels)
	{
		start = aStart;
		end = aEnd;
		intervalModel = makeIntervalModelList(intervalModels);
		intervalModelIndex = 0;
		rhythmModel = makeRhythmModelList(rhythmModels);
		rhythmModelIndex = 0;		
	}
	

	
	private List<DoubleAndDouble> makeRhythmModelList(double[][] rhythmModels)
	{
		List<DoubleAndDouble> list = new ArrayList<DoubleAndDouble>();
		for (double[] darr: rhythmModels)
		{
			list.add(new DoubleAndDouble(darr[0], darr[1]));
		}
		return list;
	}



	private List<Integer> makeIntervalModelList(int[] intervalModels)
	{
		List<Integer> list = new ArrayList<Integer>();
		for (int i: intervalModels)
		{
			list.add(i);
		}
		return list;
	}


	
	@Override
	public boolean isWithinScope(double pos)
	{
		if (pos >= start && pos < end) return true;
		return false;
	}

	
	
	@Override
	public SuFiNote getNextNote()
	{
		SuFiNote previousNote = parent.getLastNote();
		int note = countToNextDiatonicNote(previousNote.getNote(), previousNote.getNextInterval());
		DoubleAndDouble dad = rhythmModel.get(rhythmModelIndex); 
		SuFiNote newNote = new SuFiNote(
				note, 
				dad.getD1(), 
				dad.getD2(),
				intervalModel.get(intervalModelIndex));
		intervalModelIndex++;
		if (intervalModelIndex >= intervalModel.size()) intervalModelIndex = 0;
		rhythmModelIndex++;
		if (rhythmModelIndex >= rhythmModel.size()) rhythmModelIndex = 0;
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

	
	
	@Override
	public SuFiNote getFirstNote()
	{
		int note = parent.getMu().getStartPitch();
		DoubleAndDouble dad = rhythmModel.get(rhythmModelIndex); 
		SuFiNote newNote =  new SuFiNote(
				note, 
				dad.getD1(), 
				dad.getD2(),
				intervalModel.get(intervalModelIndex));
		intervalModelIndex++;
		if (intervalModelIndex >= intervalModel.size()) intervalModelIndex = 0;
		rhythmModelIndex++;
		if (rhythmModelIndex >= rhythmModel.size()) rhythmModelIndex = 0;
		return newNote;
	}

	
	
	@Override
	public boolean setParent(SuFiSu aSuFiSu)
	{
		boolean wasSet = false;
		if (aSuFiSu != null && aSuFiSu instanceof SuFiSu_LeftToRight)
		{
			parent = aSuFiSu;
			wasSet = true;
		}
		return wasSet;
	}

	
	
	@Override
	public void reset()
	{
		intervalModelIndex = 0;
		rhythmModelIndex = 0;

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
