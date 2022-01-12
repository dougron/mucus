

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel.MeasureItemInterfaceKernel;


public class MeasureItem_TimeSignature implements MXML_MeasureItemInterface
{


	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//MeasureItem_TimeSignature Attributes
	private int type;
	private int numerator;
	private int denominator;



	//------------------------
	// CONSTRUCTOR
	//------------------------


	public MeasureItem_TimeSignature(int aNumerator, int aDenominator)
	{
		type = MXML_Measure.IS_ATTRIBUTE;
		numerator = aNumerator;
		denominator = aDenominator;
	}



	//------------------------
	// INTERFACE
	//------------------------

	public boolean setNumerator(int aNumerator)
	{
		boolean wasSet = false;
		numerator = aNumerator;
		wasSet = true;
		return wasSet;
	}



	public boolean setDenominator(int aDenominator)
	{
		boolean wasSet = false;
		denominator = aDenominator;
		wasSet = true;
		return wasSet;
	}



	public int getType()
	{
		return type;
	}



	public int getNumerator()
	{
		return numerator;
	}



	public int getDenominator()
	{
		return denominator;
	}



	public void delete()
	{}



	public Element[] getElement(Document document){
		Element element = document.createElement("time");
		Element num = document.createElement("beats");
		num.appendChild(document.createTextNode("" + numerator));
		Element denom = document.createElement("beat-type");
		denom.appendChild(document.createTextNode("" + denominator));
		element.appendChild(num);
		element.appendChild(denom);
		return new Element[]{element};
	}




	public String getTestOutput(){
		return MeasureItemInterfaceKernel.getTestOutput(this);
	}


	/**
	 * MXML_MeasureItemInterface and all implementing classes
	 * interface for all items that go in a measure 
	 * a class implementing this interface returns an array of Element to put in the musicxml document
	 */
	@Override
	public double getOffset(){
		return 0;
	}



	/**
	 * MXML_MeasureItemInterface and all implementing classes
	 * interface for all items that go in a measure 
	 * a class implementing this interface returns an array of Element to put in the musicxml document
	 */
	@Override
	public double getLength(){
		return 0;
	}


	public String toString()
	{
		return super.toString() + "["+
				"type" + ":" + getType()+ "," +
				"numerator" + ":" + getNumerator()+ "," +
				"denominator" + ":" + getDenominator()+ "]";
	}
}