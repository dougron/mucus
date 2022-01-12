package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel.MeasureItemInterfaceKernel;

// line 94 "../musicxml_measure_item_interface.ump"
public class MeasureItem_TupletNote extends MeasureItem_Note
{


	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//MeasureItem_TupletNote Attributes
	private int timeModActual;
	private int timeModNormal;
	private boolean hasStartOfTupletNotationElement;
	private boolean hasEndOfTupletNotationElement;
	private boolean hasTupletBracket;



	//------------------------
	// CONSTRUCTOR
	//------------------------


	public MeasureItem_TupletNote(double aOffset, double aLength, int aVoice, MXML_Measure aMeasure, int aTimeModActual, int aTimeModNormal, boolean aHasStartOfTupletNotationElement, boolean aHasTupletBracket)
	{
		super(aOffset, aLength, aVoice, aMeasure);
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
//		double d1 = getLength() * getTimeModActual();
//		double d2 = d1 / getTimeModNormal();
		return Mu.round(Mu.round(getLength() * getTimeModActual()) / getTimeModNormal());
	}

	
	
	public boolean hasEndOfTupletNotationElement()
	{
		return hasEndOfTupletNotationElement;
	}

	
	
	public void setHasEndOfTupletNotationElement(boolean hasEndOfTupletNotationElement)
	{
		this.hasEndOfTupletNotationElement = hasEndOfTupletNotationElement;
	}

}