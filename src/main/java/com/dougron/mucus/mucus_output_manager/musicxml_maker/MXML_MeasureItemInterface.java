/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/
package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * MXML_MeasureItemInterface and all implementing classes
 * interface for all items that go in a measure 
 * a class implementing this interface returns an array of Element to put in the musicxml document
 */
// line 9 "../musicxml_measure_item_interface.ump"
public interface MXML_MeasureItemInterface
{
  
  // ABSTRACT METHODS 

 public double getOffset();
 public double getLength();
 public Element[] getElement(Document document);
 public int getType();
 public String getTestOutput();

}