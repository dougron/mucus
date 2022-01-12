/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/
package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;
/**
 * interface for areas of a particular time signature for printing.
 * the score output would get a time signature at the beginning of each zone.
 * The original idea is that time_signature_utilities.TimeSignatureZone can impement this
 * without changing any code, but this involves too much dependancy, anyway the idea is off for
 * now till I figure out the details
 * 
 * musicxml_maker.MXML_TimeSignatureZone is a local
 * class that can be used if TimeSignatureZone is not being used
 */
// line 20 "../musicxml_maker.ump"
public interface MXML_TimeSignatureZoneInterface
{
  
  // ABSTRACT METHODS 

 public int getNumerator();
 public int getDenominator();
 public int getBarCount();
}