/*CODE CAN BE EDITED - this class removed from mu.ump on 2020_08_01 as multiple constructors required*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mu_framework.data_types;

import main.java.com.dougron.mucus.mu_framework.Mu;

// line 398 "../mu.ump"
public class MuAnnotation
{

	public static enum TextPlacement {PLACEMENT_ABOVE, PLACEMENT_BELOW};
	public static TextPlacement DEFAULT_TEXT_PLACEMENT = TextPlacement.PLACEMENT_ABOVE;

	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//MuAnnotation Attributes
	private String annotation;
	private double fontSize;
	private TextPlacement placement;


	//MuAnnotation Associations
	private Mu mu;


	//Helper variables
	private boolean hasFontSize;


	//------------------------
	// CONSTRUCTOR
	//------------------------


	public MuAnnotation(String aAnnotation, TextPlacement aTextPlacement)
	{
		annotation = aAnnotation;
		hasFontSize = false;
		setPlacement(aTextPlacement);
	}



	public MuAnnotation(String aAnnotation, double fontSize, TextPlacement aTextPlacement)
	{
		annotation = aAnnotation;
		setFontSize(fontSize);
		setPlacement(aTextPlacement);
	}

	
	
	public MuAnnotation(String aAnnotation)
	{
		annotation = aAnnotation;
		hasFontSize = false;
		setPlacement(DEFAULT_TEXT_PLACEMENT);
	}



	//------------------------
	// INTERFACE
	//------------------------

	


	public boolean setAnnotation(String aAnnotation)
	{
		boolean wasSet = false;
		annotation = aAnnotation;
		wasSet = true;
		return wasSet;
	}



	public String getAnnotation()
	{
		return annotation;
	}



	/* Code from template association_GetOne */
	public Mu getMu()
	{
		return mu;
	}



	public boolean hasMu()
	{
		boolean has = mu != null;
		return has;
	}



	/* Code from template association_SetOptionalOneToMany */
//	public boolean setMu(Mu aMu)
//	{
//		boolean wasSet = false;
//		Mu existingMu = mu;
//		mu = aMu;
//		if (existingMu != null && !existingMu.equals(aMu))
//		{
//			existingMu.removeMuAnnotation(this);
//		}
//		if (aMu != null)
//		{
//			aMu.addMuAnnotation(this);
//		}
//		wasSet = true;
//		return wasSet;
//	}



	public void delete()
	{
//		if (mu != null)
//		{
//			Mu placeholderMu = mu;
//			this.mu = null;
//			placeholderMu.removeMuAnnotation(this);
//		}
	}



	public String toString()
	{
		return super.toString() + "["+
				"annotation" + ":" + getAnnotation()+ "]" + System.getProperties().getProperty("line.separator") +
				"  " + "mu = "+(getMu()!=null?Integer.toHexString(System.identityHashCode(getMu())):"null");
	}



	public double getFontSize() 
	{
		if (hasFontSize)
		{
			return fontSize;
		}
		else 
		{
			return -1;
		}	  
	}



	public void setFontSize(double fontSize) 
	{
		this.fontSize = fontSize;
		hasFontSize = true;
	}



	public boolean getHasFontSize()
	{
		return hasFontSize;
	}



	public void useDefaultFontSizeOfOutputApplication()
	{
		hasFontSize = false;
	}



	public TextPlacement getPlacement() 
	{
		return placement;
	}



	public void setPlacement(TextPlacement placement) 
	{
		this.placement = placement;
	}



	public String parametersToString()
	{
		return annotation + "," + fontSize + "," + hasFontSize + "," + placement;
	}
}