package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;

import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.ImmutableMap;

import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;
import main.java.da_utils.combo_variables.IntAndString;

public class MeasureItem_Clef implements MXML_MeasureItemInterface
{
	public static enum ClefType {TREBLE_CLEF, BASS_CLEF}
	public static final Map<ClefType, IntAndString> map = ImmutableMap.<ClefType, IntAndString>builder()
				.put(ClefType.BASS_CLEF, new IntAndString(4, "F"))
				.put(ClefType.TREBLE_CLEF, new IntAndString(2, "G"))
				.build();


	private ClefType clefType;
	private int type;
	
	
	public MeasureItem_Clef(ClefType aClefType)
	{
		setClefType(aClefType);
		type = MXML_Measure.IS_ATTRIBUTE;
	}

	
	
	public ClefType getClefType()
	{
		return clefType;
	}

	
	
	public void setClefType(ClefType clefType)
	{
		this.clefType = clefType;
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
		IntAndString clefData = map.get(clefType);
		Element element = document.createElement("clef");
		Element sign = document.createElement("sign");
		sign.appendChild(document.createTextNode(clefData.str));
		Element line = document.createElement("line");
		line.appendChild(document.createTextNode("" + clefData.i));
		element.appendChild(sign);
		element.appendChild(line);
		return new Element[] {element};
	}

	
	
	@Override
	public int getType()
	{
		return type;
	}

	
	
	@Override
	public String getTestOutput()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
