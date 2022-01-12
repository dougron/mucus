/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel.MeasureItemInterfaceKernel;

// line 131 "../musicxml_measure_item_interface.ump"
public class MeasureItem_Rest implements MXML_MeasureItemInterface
{

	//------------------------
	// MEMBER VARIABLES
	//------------------------



	//MeasureItem_Rest Attributes
	private int type;
	private double offset;
	private double length;
	private boolean isWholeBarRest;
	private int voice;
	private MXML_Measure measure;
	private double roundingAccuracy = 0.0000000001;


	//MeasureItem_Rest Associations
	private List<MeasureItem_Annotation> measureItemAnnotations;


	//------------------------
	// CONSTRUCTOR
	//------------------------


	public MeasureItem_Rest(double aOffset, double aLength, boolean aIsWholeBarRest, int aVoice, MXML_Measure aMeasure)
	{
		type = MXML_Measure.IS_NOTE;
		offset = aOffset;
		length = aLength;
		
//		offset = BigDecimal.valueOf(aOffset).setScale(VoiceMu.getRoundingAccuracy(), RoundingMode.HALF_UP).doubleValue();
//	    length = BigDecimal.valueOf(aLength).setScale(VoiceMu.getRoundingAccuracy(), RoundingMode.HALF_UP).doubleValue();
		isWholeBarRest = aIsWholeBarRest;
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



	public boolean setIsWholeBarRest(boolean aIsWholeBarRest)
	{
		boolean wasSet = false;
		isWholeBarRest = aIsWholeBarRest;
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



	public boolean getIsWholeBarRest()
	{
		return isWholeBarRest;
	}



	public int getVoice()
	{
		return voice;
	}



	/**
	 * required to access the value for subdivision calculated for the entire measure
	 */
	public MXML_Measure getMeasure()
	{
		return measure;
	}



	public boolean isIsWholeBarRest()
	{
		return isWholeBarRest;
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



	public Element[] getElement(Document document)
	{
		return MeasureItemInterfaceKernel.getElement(document, this);
	}



	public String getTestOutput()
	{
		return MeasureItemInterfaceKernel.getTestOutput(this);
	}



	public double getLengthForTypeIndex()
	{		
		return Math.round(getLength() / getRoundingAccuracy()) * getRoundingAccuracy();
	}
	
	
	public double getRoundingAccuracy()
	{
		return roundingAccuracy;
	}


	public String toString()
	{
		return super.toString() + "["+
				"type" + ":" + getType()+ "," +
				"offset" + ":" + getOffset()+ "," +
				"length" + ":" + getLength()+ "," +
				"isWholeBarRest" + ":" + getIsWholeBarRest()+ "," +
				"voice" + ":" + getVoice()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "measure" + "=" + (getMeasure() != null ? !getMeasure().equals(this)  ? getMeasure().toString().replaceAll("  ","    ") : "this" : "null");
	}
}