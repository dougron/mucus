package main.java.com.dougron.mucus.mu_framework.length_model;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class FixedLengthInQuarters implements LengthModel
{

	private double lengthInQuarters;
	private Mu associatedMu;

	
	
	public FixedLengthInQuarters(double aLengthInQuarters, Mu associatedMu)
	{
		lengthInQuarters = aLengthInQuarters;
		this.associatedMu = associatedMu;
	}

	
	
	@Override
	public int getLengthInBars()
	{
		int startBar = associatedMu.getGlobalPositionInBars();
		double globalPositionInQuarters = associatedMu.getGlobalPositionInQuarters();
		BarsAndBeats globalPositionInBarsAndBeats 
		= associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters + lengthInQuarters);
		int endBar 
		= globalPositionInBarsAndBeats.getBarPosition() 
			+ (int)Math.signum(globalPositionInBarsAndBeats.getOffsetInQuarters());
		return endBar - startBar;
	}

	
	
	@Override
	public void calculateLength(List<Mu> mus)
	{}

	
	
	@Override
	public double getLengthInQuarters()
	{
		return lengthInQuarters;
	}
	
	
	
	@Override
	public BarsAndBeats getLengthInBarsAndBeats()
	{
		double globalPosition = associatedMu.getGlobalPositionInQuarters();
		double globalEndPosition = globalPosition + lengthInQuarters;
		int startBarPosition = associatedMu.getGlobalPositionInBars();
		BarsAndBeats endBab = associatedMu.getGlobalPositionInBarsAndBeats(globalEndPosition);
		BarsAndBeats lengthBab = new BarsAndBeats(
				endBab.getBarPosition() - startBarPosition,
				endBab.getOffsetInQuarters()
				);
		return lengthBab;
	}
	
	
	
	public String toString()
	{
		return "FixedLengthInQuarters: assoc.mu=" + associatedMu.getName();
	}
	
	
	
	@Override
	public String positionToString()
	{
		return "FixedLength: " + lengthInQuarters + " quarters";
	}



	@Override
	public void setSameLengthModel(Mu aMu)
	{
		aMu.setLengthInQuarters(lengthInQuarters);
		
	}



	@Override
	public String getContentString()
	{
		return "FixedLengthInQuarters=" + lengthInQuarters;
	}


	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("length_model");
		element.setAttribute("type", "FixedLengthInQuarters");
	
		Element length_in_quarters = document.createElement("length_in_quarters");
		length_in_quarters.appendChild(document.createTextNode("" + lengthInQuarters));
		element.appendChild(length_in_quarters);
		return element;
	}
	
	

}
