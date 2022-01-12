package main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right;
import java.util.ArrayList;
import java.util.List;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;


/**
 * manager of Sufis
 */


public class SuFiSu_LeftToRight implements SuFiSu
{


	//------------------------
	// STATIC VARIABLES
	//------------------------


	public static final int DEFAULT_VELOCITY = 64;


	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//SuFiSu Attributes
	private List<SuFi> sufis;
	private SuFiNote lastNote;
	private int lastIndex;


	//SuFiSu Associations
	private Mu mu;


	//Helper Variables
	private boolean canSetLastNote;
	private boolean canSetLastIndex;



	//------------------------
	// CONSTRUCTOR
	//------------------------



	public SuFiSu_LeftToRight(Mu aMu)
	{
		sufis = new ArrayList<SuFi>();
		canSetLastNote = true;
		canSetLastIndex = true;
		if (!setMu(aMu))
		{
			throw new RuntimeException("Unable to create SuFiSu due to aMu");
		}
	}



	//------------------------
	// INTERFACE
	//------------------------



	@Override
	public boolean addSufi(SuFi aSufi)
	{
		boolean wasAdded = false;
		wasAdded = sufis.add(aSufi);
		return wasAdded;
	}



	public boolean removeSufi(SuFi aSufi)
	{
		boolean wasRemoved = false;
		wasRemoved = sufis.remove(aSufi);
		return wasRemoved;
	}



	public boolean setLastNote(SuFiNote aLastNote)
	{
		boolean wasSet = false;
		if (!canSetLastNote) { return false; }
		canSetLastNote = false;
		lastNote = aLastNote;
		wasSet = true;
		return wasSet;
	}



	public boolean setLastIndex(int aLastIndex)
	{
		boolean wasSet = false;
		if (!canSetLastIndex) { return false; }
		canSetLastIndex = false;
		lastIndex = aLastIndex;
		wasSet = true;
		return wasSet;
	}



	public SuFi getSufi(int index)
	{
		SuFi aSufi = sufis.get(index);
		return aSufi;
	}



	public List<SuFi> getSufis()
	{
		return sufis;
	}



	public int numberOfSufis()
	{
		int number = sufis.size();
		return number;
	}



	public boolean hasSufis()
	{
		boolean has = sufis.size() > 0;
		return has;
	}



	public int indexOfSufi(SuFi aSufi)
	{
		int index = sufis.indexOf(aSufi);
		return index;
	}



	public SuFiNote getLastNote()
	{
		return lastNote;
	}



	public int getLastIndex()
	{
		return lastIndex;
	}



	@Override
	public Mu getMu()
	{
		return mu;
	}



	@Override
	public boolean setMu(Mu aNewMu)
	{
		boolean wasSet = false;
		if (aNewMu != null)
		{
			mu = aNewMu;
			wasSet = true;
		}
		return wasSet;
	}



	public void delete()
	{
//		mu = null;
	}



	@Override
	public void generate(){
		resetAllSufis();
		int startNote = mu.getStartPitch();
		double pos = 0.0;

		mu.setStartPitch(getClosestStartNote(startNote));
		boolean isFirstNote = true;

		boolean runOutOfSuFis = false;
		int noteID = 0;

		while (true) {
			int index = sufis.size() - 1;
			while (true) {
				boolean noteWasReturned = false;
				SuFi sufi = sufis.get(index);
				if (sufi.isWithinScope(pos)) {
					if (isFirstNote) {
						lastNote = sufi.getFirstNote();
						noteWasReturned = true;
						isFirstNote = false;
					} else {
						lastNote = sufi.getNextNote();
						noteWasReturned = true;
					}
				}
				if (noteWasReturned) {
					break;
				} else {
					index--;
					if (index == -1) {
						runOutOfSuFis = true;
						break;
					}
				}
			}
			if (runOutOfSuFis) {
				break;
			} else {
				noteID = makeNewMuWithMuNoteAndAddToParent(pos, noteID);
				pos += lastNote.getInterOnsetInterval();				
			}
		}
	}



	private void resetAllSufis()
	{
		for (SuFi sufi: sufis)
		{
			sufi.reset();
		}
		
	}



	private int makeNewMuWithMuNoteAndAddToParent(double pos, int noteID)
	{
		Mu newMu = new Mu("note" + noteID);
		noteID++;
		MuNote mun = new MuNote(lastNote.getNote(), DEFAULT_VELOCITY);
		newMu.addMuNote(mun);
		newMu.setLengthInQuarters(lastNote.getLength());
		mu.addMu(newMu, new BarsAndBeats(0, pos));		// barpos=0; interesting as this generator does not naturally divide up into bars,so all is just offset from the beginning
		return noteID;
	}




	private int getClosestStartNote(int startNote){
		while (true) {
			if (mu.isDiatonicNoteInXMLKey(startNote)) return startNote;
			startNote++;
		}
	}


	
	public String toString()
	{
		return super.toString() + "["+
				"lastIndex" + ":" + getLastIndex()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "lastNote" + "=" + (getLastNote() != null ? !getLastNote().equals(this)  ? getLastNote().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
				"  " + "mu = "+(getMu()!=null?Integer.toHexString(System.identityHashCode(getMu())):"null");
	}
}