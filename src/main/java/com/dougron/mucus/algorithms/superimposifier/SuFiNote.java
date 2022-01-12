/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.algorithms.superimposifier;

// line 85 "../SuFi.ump"
public class SuFiNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SuFiNote Attributes
  private int note;
  private double length;
  private double interOnsetInterval;
  private int nextInterval;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SuFiNote(int aNote, double aLength, double aInterOnsetInterval, int aNextInterval)
  {
    note = aNote;
    length = aLength;
    interOnsetInterval = aInterOnsetInterval;
    nextInterval = aNextInterval;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNote(int aNote)
  {
    boolean wasSet = false;
    note = aNote;
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

  public boolean setInterOnsetInterval(double aInterOnsetInterval)
  {
    boolean wasSet = false;
    interOnsetInterval = aInterOnsetInterval;
    wasSet = true;
    return wasSet;
  }

  public boolean setNextInterval(int aNextInterval)
  {
    boolean wasSet = false;
    nextInterval = aNextInterval;
    wasSet = true;
    return wasSet;
  }

  public int getNote()
  {
    return note;
  }

  public double getLength()
  {
    return length;
  }

  public double getInterOnsetInterval()
  {
    return interOnsetInterval;
  }

  /**
   * this must be saved, as any call to nextNote() uses this interval to calculate the note but saves the interval for the next calculation in the new SuFiNote
   */
  public int getNextInterval()
  {
    return nextInterval;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "note" + ":" + getNote()+ "," +
            "length" + ":" + getLength()+ "," +
            "interOnsetInterval" + ":" + getInterOnsetInterval()+ "," +
            "nextInterval" + ":" + getNextInterval()+ "]";
  }
}