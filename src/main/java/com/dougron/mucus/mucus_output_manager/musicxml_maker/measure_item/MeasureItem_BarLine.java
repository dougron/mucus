package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;

public class MeasureItem_BarLine implements MXML_MeasureItemInterface
{
	
	public static enum Location{LEFT, RIGHT}

	private int barStyle;
	private Location location;;
	
	
	
	public MeasureItem_BarLine(int aMXMLBarStyle, Location aLocation)
	{
		this.barStyle = aMXMLBarStyle;
		location = aLocation;
	}

	
	
	@Override
	public double getOffset()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public double getLength()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public Element[] getElement(Document document)
	{
		Element element = document.createElement("barline");
		String loc;
		if (location == Location.LEFT) loc = "left"; else loc = "right";		
		element.setAttribute("location", loc);
		Element bar_style = document.createElement("bar-style");
		bar_style.appendChild(document.createTextNode(MXML.barlineBarStyle[barStyle]));
		element.appendChild(bar_style);
		return new Element[] {element};
	}

	
	
	@Override
	public int getType()
	{
		return MXML_Measure.IS_NOTE;
	}

	
	
	@Override
	public String getTestOutput()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
