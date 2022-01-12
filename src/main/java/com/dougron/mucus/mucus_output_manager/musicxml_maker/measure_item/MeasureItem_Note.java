package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel.MeasureItemInterfaceKernel;


public class MeasureItem_Note implements MXML_MeasureItemInterface
{

	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//MeasureItem_Note Attributes
	private int type;
	private double offset;
	private double length;
	private List<Integer> notes;
	private boolean hasTieStart;
	private boolean hasTieEnd;
	private int voice;
	private MXML_Measure measure;

	//MeasureItem_Note Associations
	private List<MeasureItem_Annotation> measureItemAnnotations;

	
	
	//------------------------
	// CONSTRUCTOR
	//------------------------

	
	public MeasureItem_Note(double aOffset, double aLength, int aVoice, MXML_Measure aMeasure)
	{
		type = MXML_Measure.IS_NOTE;
		offset = aOffset;
		length = aLength;
		notes = new ArrayList<Integer>();
		hasTieStart = false;
		hasTieEnd = false;
		voice = aVoice;
		measure = aMeasure;
		measureItemAnnotations = new ArrayList<MeasureItem_Annotation>();
	}

	
	
	//------------------------
	// INTERFACE
	//------------------------

	
	public boolean setOffset(double aOffset)
	{
		boolean wasSet = false;
		offset = aOffset;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setLength(double aLength)
	{
		boolean wasSet = false;
		length = aLength;
		wasSet = true;
		return wasSet;
	}


	
	public boolean addNote(Integer aNote)
	{
		boolean wasAdded = false;
		wasAdded = notes.add(aNote);
		return wasAdded;
	}

	
	
	public boolean removeNote(Integer aNote)
	{
		boolean wasRemoved = false;
		wasRemoved = notes.remove(aNote);
		return wasRemoved;
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

	
	
	public boolean setVoice(int aVoice)
	{
		boolean wasSet = false;
		voice = aVoice;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setMeasure(MXML_Measure aMeasure)
	{
		boolean wasSet = false;
		measure = aMeasure;
		wasSet = true;
		return wasSet;
	}

	
	
	public int getType()
	{
		return type;
	}

	
	
	public double getOffset()
	{
		return offset;
	}

	
	
	public double getLength()
	{
		return length;
	}


	
	public Integer getNote(int index)
	{
		Integer aNote = notes.get(index);
		return aNote;
	}

	
	
	public Integer[] getNotes()
	{
		Integer[] newNotes = notes.toArray(new Integer[notes.size()]);
		return newNotes;
	}

	
	
	public int numberOfNotes()
	{
		int number = notes.size();
		return number;
	}

	
	
	public boolean hasNotes()
	{
		boolean has = notes.size() > 0;
		return has;
	}

	
	
	public int indexOfNote(Integer aNote)
	{
		int index = notes.indexOf(aNote);
		return index;
	}

	
	
	public boolean getHasTieStart()
	{
		return hasTieStart;
	}

	
	
	public boolean getHasTieEnd()
	{
		return hasTieEnd;
	}

	
	
	public int getVoice()
	{
		return voice;
	}



	public MXML_Measure getMeasure()
	{
		return measure;
	}


	
	public boolean isHasTieStart()
	{
		return hasTieStart;
	}


	
	public boolean isHasTieEnd()
	{
		return hasTieEnd;
	}


	
	public MeasureItem_Annotation getMeasureItemAnnotation(int index)
	{
		MeasureItem_Annotation aMeasureItemAnnotation = measureItemAnnotations.get(index);
		return aMeasureItemAnnotation;
	}

	
	
	public List<MeasureItem_Annotation> getMeasureItemAnnotations()
	{
		List<MeasureItem_Annotation> newMeasureItemAnnotations = Collections.unmodifiableList(measureItemAnnotations);
		return newMeasureItemAnnotations;
	}

	
	
	public int numberOfMeasureItemAnnotations()
	{
		int number = measureItemAnnotations.size();
		return number;
	}

	
	
	public boolean hasMeasureItemAnnotations()
	{
		boolean has = measureItemAnnotations.size() > 0;
		return has;
	}

	
	
	public int indexOfMeasureItemAnnotation(MeasureItem_Annotation aMeasureItemAnnotation)
	{
		int index = measureItemAnnotations.indexOf(aMeasureItemAnnotation);
		return index;
	}


	
	public static int minimumNumberOfMeasureItemAnnotations()
	{
		return 0;
	}


	
	public boolean addMeasureItemAnnotation(MeasureItem_Annotation aMeasureItemAnnotation)
	{
		boolean wasAdded = false;
		if (measureItemAnnotations.contains(aMeasureItemAnnotation)) { return false; }
		measureItemAnnotations.add(aMeasureItemAnnotation);
		wasAdded = true;
		return wasAdded;
	}

	
	
	public boolean removeMeasureItemAnnotation(MeasureItem_Annotation aMeasureItemAnnotation)
	{
		boolean wasRemoved = false;
		if (measureItemAnnotations.contains(aMeasureItemAnnotation))
		{
			measureItemAnnotations.remove(aMeasureItemAnnotation);
			wasRemoved = true;
		}
		return wasRemoved;
	}


	
	public boolean addMeasureItemAnnotationAt(MeasureItem_Annotation aMeasureItemAnnotation, int index)
	{  
		boolean wasAdded = false;
		if(addMeasureItemAnnotation(aMeasureItemAnnotation))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfMeasureItemAnnotations()) { index = numberOfMeasureItemAnnotations() - 1; }
			measureItemAnnotations.remove(aMeasureItemAnnotation);
			measureItemAnnotations.add(index, aMeasureItemAnnotation);
			wasAdded = true;
		}
		return wasAdded;
	}

	
	
	public boolean addOrMoveMeasureItemAnnotationAt(MeasureItem_Annotation aMeasureItemAnnotation, int index)
	{
		boolean wasAdded = false;
		if(measureItemAnnotations.contains(aMeasureItemAnnotation))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfMeasureItemAnnotations()) { index = numberOfMeasureItemAnnotations() - 1; }
			measureItemAnnotations.remove(aMeasureItemAnnotation);
			measureItemAnnotations.add(index, aMeasureItemAnnotation);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addMeasureItemAnnotationAt(aMeasureItemAnnotation, index);
		}
		return wasAdded;
	}

	
	
	public void delete()
	{
		measureItemAnnotations.clear();
	}



	public String notesToString()
	{
		String str = "";
		for (Integer i: notes)
		{
			str += i + ",";
		}
		return str;
	}



	public Element[] getElement(Document document)
	{
		return MeasureItemInterfaceKernel.getElement(document, this);
	}



	public String toString()
	{
		return super.toString() + "["+
				"offset" + ":" + getOffset()+ "," +
				"length" + ":" + getLength()+ "," +
				"hasTieStart" + ":" + getHasTieStart()+ "," +
				"hasTieEnd" + ":" + getHasTieEnd()+ "]" +
				" notes:" + notesToString();
	}



	public String getTestOutput()
	{
		return MeasureItemInterfaceKernel.getTestOutput(this);
	}


	
	public double getLengthForTypeIndex()
	{
		return Mu.round(getLength());
	}

}