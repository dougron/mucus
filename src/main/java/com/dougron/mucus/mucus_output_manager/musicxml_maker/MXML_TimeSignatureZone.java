/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;

// line 30 "../musicxml_maker.ump"
public class MXML_TimeSignatureZone implements MXML_TimeSignatureZoneInterface
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MXML_TimeSignatureZone Attributes
  private int numerator;
  private int denominator;
  private int barCount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MXML_TimeSignatureZone(int aNumerator, int aDenominator, int aBarCount)
  {
    numerator = aNumerator;
    denominator = aDenominator;
    barCount = aBarCount;
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

  public boolean setBarCount(int aBarCount)
  {
    boolean wasSet = false;
    barCount = aBarCount;
    wasSet = true;
    return wasSet;
  }

  public int getNumerator()
  {
    return numerator;
  }

  public int getDenominator()
  {
    return denominator;
  }

  public int getBarCount()
  {
    return barCount;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "numerator" + ":" + getNumerator()+ "," +
            "denominator" + ":" + getDenominator()+ "," +
            "barCount" + ":" + getBarCount()+ "]";
  }
}