/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel.MeasureItemInterfaceKernel;

// line 296 "../musicxml_measure_item_interface.ump"
public class MeasureItem_Division implements MXML_MeasureItemInterface
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MeasureItem_Division Attributes
  private int type;
  private int division;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MeasureItem_Division(int aDivision)
  {
    type = MXML_Measure.IS_ATTRIBUTE;
    division = aDivision;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDivision(int aDivision)
  {
    boolean wasSet = false;
    division = aDivision;
    wasSet = true;
    return wasSet;
  }

  public int getType()
  {
    return type;
  }

  public int getDivision()
  {
    return division;
  }

  public void delete()
  {}

  // line 310 "../musicxml_measure_item_interface.ump"
   public Element[] getElement(Document document){
    Element element = document.createElement("divisions");
      	element.appendChild(document.createTextNode("" + division));     
	  	return new Element[]{element};
  }

  // line 317 "../musicxml_measure_item_interface.ump"
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
            "division" + ":" + getDivision()+ "]";
  }
}