/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel.MeasureItemInterfaceKernel;

// line 323 "../musicxml_measure_item_interface.ump"
public class MeasureItem_KeySignature implements MXML_MeasureItemInterface
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MeasureItem_KeySignature Attributes
  private int type;
  private int xmlKey;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MeasureItem_KeySignature(int aXmlKey)
  {
    type = MXML_Measure.IS_ATTRIBUTE;
    xmlKey = aXmlKey;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setXmlKey(int aXmlKey)
  {
    boolean wasSet = false;
    xmlKey = aXmlKey;
    wasSet = true;
    return wasSet;
  }

  public int getType()
  {
    return type;
  }

  public int getXmlKey()
  {
    return xmlKey;
  }

  public void delete()
  {}

  // line 339 "../musicxml_measure_item_interface.ump"
   public Element[] getElement(Document document){
    Element element = document.createElement("key");
      	Element fifths = document.createElement("fifths");
      	fifths.appendChild(document.createTextNode("" + xmlKey));
     	element.appendChild(fifths);
	  	return new Element[]{element};
  }

  // line 349 "../musicxml_measure_item_interface.ump"
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
            "xmlKey" + ":" + getXmlKey()+ "]";
  }
}