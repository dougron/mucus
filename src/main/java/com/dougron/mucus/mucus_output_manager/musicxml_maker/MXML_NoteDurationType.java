/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;

// line 41 "../musicxml_maker.ump"
public class MXML_NoteDurationType
{



	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//MXML_NoteDurationType Attributes
	private String xmlType;
	private int dotCount;



	//------------------------
	// CONSTRUCTOR
	//------------------------


	public MXML_NoteDurationType(String aXmlType, int aDotCount)
	{
		xmlType = aXmlType;
		dotCount = aDotCount;
	}



	//------------------------
	// INTERFACE
	//------------------------


	public boolean setXmlType(String aXmlType)
	{
		boolean wasSet = false;
		xmlType = aXmlType;
		wasSet = true;
		return wasSet;
	}



	public boolean setDotCount(int aDotCount)
	{
		boolean wasSet = false;
		dotCount = aDotCount;
		wasSet = true;
		return wasSet;
	}



	public String getXmlType()
	{
		return xmlType;
	}



	public int getDotCount()
	{
		return dotCount;
	}



	public void delete()
	{}



	public String toString()
	{
		return super.toString() + "["+
				"xmlType" + ":" + getXmlType()+ "," +
				"dotCount" + ":" + getDotCount()+ "]";
	}
}