/*CODE CAN BE EDITED. Umple no longer used
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel.MeasureItemInterfaceKernel;

// line 168 "../musicxml_measure_item_interface.ump"
public class MeasureItem_TupletRest extends MeasureItem_Rest
{


	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//MeasureItem_TupletRest Attributes
	private int timeModActual;
	private int timeModNormal;
	private boolean hasStartOfTupletNotationElement;
	private boolean hasEndOfTupletNotationElement;
	private boolean hasTupletBracket;



	//------------------------
	// CONSTRUCTOR
	//------------------------


	public MeasureItem_TupletRest(double aOffset, double aLength, boolean aIsWholeBarRest, int aVoice, MXML_Measure aMeasure, int aTimeModActual, int aTimeModNormal, boolean aHasStartOfTupletNotationElement, boolean aHasTupletBracket)
	{
		super(aOffset, aLength, aIsWholeBarRest, aVoice, aMeasure);
		timeModActual = aTimeModActual;
		timeModNormal = aTimeModNormal;
		hasStartOfTupletNotationElement = aHasStartOfTupletNotationElement;
		hasTupletBracket = aHasTupletBracket;
	}



	//------------------------
	// INTERFACE
	//------------------------


	public boolean setTimeModActual(int aTimeModActual)
	{
		boolean wasSet = false;
		timeModActual = aTimeModActual;
		wasSet = true;
		return wasSet;
	}



	public boolean setTimeModNormal(int aTimeModNormal)
	{
		boolean wasSet = false;
		timeModNormal = aTimeModNormal;
		wasSet = true;
		return wasSet;
	}



	public boolean setHasStartOfTupletNotationElement(boolean aHasStartOfTupletNotationElement)
	{
		boolean wasSet = false;
		hasStartOfTupletNotationElement = aHasStartOfTupletNotationElement;
		wasSet = true;
		return wasSet;
	}



	public boolean setHasTupletBracket(boolean aHasTupletBracket)
	{
		boolean wasSet = false;
		hasTupletBracket = aHasTupletBracket;
		wasSet = true;
		return wasSet;
	}



	public int getTimeModActual()
	{
		return timeModActual;
	}



	public int getTimeModNormal()
	{
		return timeModNormal;
	}



	public boolean getHasStartOfTupletNotationElement()
	{
		return hasStartOfTupletNotationElement;
	}



	public boolean getHasTupletBracket()
	{
		return hasTupletBracket;
	}



	public boolean isHasStartOfTupletNotationElement()
	{
		return hasStartOfTupletNotationElement;
	}



	public boolean isHasTupletBracket()
	{
		return hasTupletBracket;
	}



	public void delete()
	{
		super.delete();
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
		double length = getLength() * getTimeModActual() / getTimeModNormal();
		return Mu.round(length);
//		return Math.round(length / getRoundingAccuracy()) * getRoundingAccuracy();
	}


	public String toString()
	{
		return super.toString() + "["+
				"timeModActual" + ":" + getTimeModActual()+ "," +
				"timeModNormal" + ":" + getTimeModNormal()+ "," +
				"hasStartOfTupletNotationElement" + ":" + getHasStartOfTupletNotationElement()+ "," +
				"hasTupletBracket" + ":" + getHasTupletBracket()+ "]";
	}



	public boolean isHasEndOfTupletNotationElement()
	{
		return hasEndOfTupletNotationElement;
	}



	public void setHasEndOfTupletNotationElement(boolean hasEndOfTupletNotationElement)
	{
		this.hasEndOfTupletNotationElement = hasEndOfTupletNotationElement;
	}
}