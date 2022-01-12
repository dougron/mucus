package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;

public class MeasureItem_Forward implements MXML_MeasureItemInterface
{

	//------------------------
		// MEMBER VARIABLES
		//------------------------


		//MeasureItem_Backup Attributes
		private int type;
		private int lengthInDivisions;


		//------------------------
		// CONSTRUCTOR
		//------------------------


		public MeasureItem_Forward(int aLengthInDivisions)
		{
			type = MXML_Measure.IS_NOTE;
			lengthInDivisions = aLengthInDivisions;
		}



		//------------------------
		// INTERFACE
		//------------------------



		public boolean setLengthInDivisions(int aLengthInDivisions)
		{
			boolean wasSet = false;
			lengthInDivisions = aLengthInDivisions;
			wasSet = true;
			return wasSet;
		}



		/**
		 * for now this means that this does not land in the 'attributes' node. Its not a note
		 */
		public int getType()
		{
			return type;
		}



		public int getLengthInDivisions()
		{
			return lengthInDivisions;
		}



		public void delete()
		{}



		public Element[] getElement(Document document)
		{
			Element element = document.createElement("forward");
			Element duration = document.createElement("duration");
			duration.appendChild(document.createTextNode("" + lengthInDivisions));
			element.appendChild(duration);
			return new Element[]{element};
		}



		public String getTestOutput()
		{
			String str = "forward=" + getLengthInDivisions();
			str += "\n";
			return str;
		}



		/**
		 * MXML_MeasureItemInterface and all implementing classes
		 * interface for all items that go in a measure 
		 * a class implementing this interface returns an array of Element to put in the musicxml document
		 */
		@Override
		public double getOffset()
		{
			return 0;
		}



		/**
		 * MXML_MeasureItemInterface and all implementing classes
		 * interface for all items that go in a measure 
		 * a class implementing this interface returns an array of Element to put in the musicxml document
		 */
		@Override
		public double getLength()
		{
			return 0;
		}



		public String toString()
		{
			return super.toString() + "["+
					"type" + ":" + getType()+ "," +
					"lengthInDivisions" + ":" + getLengthInDivisions()+ "]";
		}

}
