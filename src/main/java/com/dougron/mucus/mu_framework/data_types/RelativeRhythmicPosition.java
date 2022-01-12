package main.java.com.dougron.mucus.mu_framework.data_types;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RelativeRhythmicPosition
{

	
	
	private int barOffset;
	private int superTactusOffset;
	private int tactusOffset;
	private int subTactusOffset;
	
	

	public RelativeRhythmicPosition(int aBarOffset, int aSuperTactusOffset, int aTactusOffset, int aSubTactusOffset)
	{
		setBarOffset(aBarOffset);
		setSuperTactusOffset(aSuperTactusOffset);
		setTactusOffset(aTactusOffset);
		setSubTactusOffset(aSubTactusOffset);
	}



	public int getBarOffset()
	{
		return barOffset;
	}



	public void setBarOffset(int barOffset)
	{
		this.barOffset = barOffset;
	}



	public int getSuperTactusOffset()
	{
		return superTactusOffset;
	}



	public void setSuperTactusOffset(int superTactusOffset)
	{
		this.superTactusOffset = superTactusOffset;
	}



	public int getTactusOffset()
	{
		return tactusOffset;
	}



	public void setTactusOffset(int tactusOffset)
	{
		this.tactusOffset = tactusOffset;
	}



	public int getSubTactusOffset()
	{
		return subTactusOffset;
	}



	public void setSubTactusOffset(int subTactusOffset)
	{
		this.subTactusOffset = subTactusOffset;
	}
	
	
	
	public String toString()
	{
		return barOffset + ":"
				+ superTactusOffset + ":"
				+ tactusOffset + ":"
				+ subTactusOffset;
	}



	// same as toString, but this must not change as it is the format that data is saved in for saving to text file
	public String toStringForFile()
	{
		return barOffset + ";"
				+ superTactusOffset + ";"
				+ tactusOffset + ";"
				+ subTactusOffset;
	}
	
	
	public JSONObject getJSONObject()
	{
		JSONObject json = new JSONObject();
		json.put("barOffset", barOffset);
		json.put("supertactusOffset", subTactusOffset);
		json.put("tactusOffset", tactusOffset);
		json.put("subtactusOffset", subTactusOffset);
		return json;
	}



	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("relative_rhythmic_position");
		
		Element bar_offset = document.createElement("bar_offset");
		bar_offset.appendChild(document.createTextNode("" + barOffset));
		element.appendChild(bar_offset);
		
		Element super_tactus_offset = document.createElement("super_tactus_offset");
		super_tactus_offset.appendChild(document.createTextNode("" + superTactusOffset));
		element.appendChild(super_tactus_offset);
		
		Element tactus_offset = document.createElement("tactus_offset");
		tactus_offset.appendChild(document.createTextNode("" + tactusOffset));
		element.appendChild(tactus_offset);
		
		Element sub_tactus_offset = document.createElement("sub_tactus_offset");
		sub_tactus_offset.appendChild(document.createTextNode("" + subTactusOffset));
		element.appendChild(sub_tactus_offset);
		
		return element;
	}



	public static RelativeRhythmicPosition getRelativeRhythmicPositionFromXMLElement(Element element)
	{
		int bar_offset = Integer.parseInt(element.getElementsByTagName("bar_offset").item(0).getTextContent());
		int super_tactus_offset = Integer.parseInt(element.getElementsByTagName("super_tactus_offset").item(0).getTextContent());
		int tactus_offset = Integer.parseInt(element.getElementsByTagName("tactus_offset").item(0).getTextContent());
		int sub_tactus_offset = Integer.parseInt(element.getElementsByTagName("sub_tactus_offset").item(0).getTextContent());
		
		return new RelativeRhythmicPosition(bar_offset, super_tactus_offset, tactus_offset, sub_tactus_offset);
	}



	public int getPowerLength ()
	{
		int length = 0;
		length += Math.abs(subTactusOffset);
		length += Math.abs(tactusOffset) * 10;
		length += Math.abs(superTactusOffset) * 100;
		length += Math.abs(barOffset) * 1000;
		return length;
	}
}
