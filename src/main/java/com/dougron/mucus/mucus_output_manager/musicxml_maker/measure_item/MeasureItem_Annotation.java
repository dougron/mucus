package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation.TextPlacement;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel.MeasureItemInterfaceKernel;

/**
 * the annotation can be connected in two ways. Either directly in the MXML_MeasureItemInterface list in MXML_Part, 
 * in which case it will be added to the beginning of the bar, or it can be added to a MeasureItem_Note, in which case 
 * it will be added to the .xml prior to the contents of the MeasureItem_Note
 */

public class MeasureItem_Annotation implements MXML_MeasureItemInterface
{

	
	//------------------------
	// STATIC VARIABLES
	//------------------------

	public static final int PLACEMENT_ABOVE = 0;
	public static final int PLACEMENT_BELOW = 1;

	
	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//MeasureItem_Annotation Attributes
	private int type;
	private String text;
	private TextPlacement placement;
	private double fontSize;
	private boolean hasFontSize;

	
	
	//------------------------
	// CONSTRUCTOR
	//------------------------

	
	public MeasureItem_Annotation(String aText, TextPlacement textPlacement, double aFontSize)
	{
		type = MXML_Measure.IS_NOTE;
		text = aText;
		placement = textPlacement;
		fontSize = aFontSize;
		hasFontSize = false;
		// line 230 "../musicxml_measure_item_interface.ump"
		if (fontSize > 0)
		{
			hasFontSize = true;
		}
	}

	
	
	//------------------------
	// INTERFACE
	//------------------------

	
	public boolean setText(String aText)
	{
		boolean wasSet = false;
		text = aText;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setPlacement(TextPlacement aPlacement)
	{
		boolean wasSet = false;
		placement = aPlacement;
		wasSet = true;
		return wasSet;
	}

	
	
	public boolean setFontSize(double aFontSize)
	{
		boolean wasSet = false;
		fontSize = aFontSize;
		wasSet = true;
		if (fontSize > 0)
		{
			hasFontSize = true;
		}
		return wasSet;
	}

	
	
	public int getType()
	{
		return type;
	}

	
	
	public String getText()
	{
		return text;
	}

	
	
	public TextPlacement getPlacement()
	{
		return placement;
	}

	
	
	public int getPlacementIndex()
	{
		switch (placement)
		{
		case PLACEMENT_ABOVE: return 0;
		case PLACEMENT_BELOW: return 1;
		default: return 0;
		}
	}

	
	
	/**
	 * fontSize should be set to 0 or below if it is to be ignored
	 */
	public double getFontSize()
	{
		return fontSize;
	}

	
	
	public boolean getHasFontSize()
	{
		return hasFontSize;
	}


	
	public boolean isHasFontSize()
	{
		return hasFontSize;
	}

	
	
	public void delete()
	{}


	
	public Element[] getElement(Document document){
		return MeasureItemInterfaceKernel.getElement(document, this);
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
				"text" + ":" + getText()+ "," +
				"placement" + ":" + getPlacement()+ "," +
				"fontSize" + ":" + getFontSize()+ "," +
				"hasFontSize" + ":" + getHasFontSize()+ "]";
	}
}