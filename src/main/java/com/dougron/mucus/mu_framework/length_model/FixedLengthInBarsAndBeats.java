package main.java.com.dougron.mucus.mu_framework.length_model;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class FixedLengthInBarsAndBeats implements LengthModel
{

	
	
	private Mu associatedMu;
	private BarsAndBeats lengthInBarsAndBeats;



	public FixedLengthInBarsAndBeats(BarsAndBeats aBarsAndBeats, Mu aAssociatedMu)
	{
		associatedMu = aAssociatedMu;
		lengthInBarsAndBeats = aBarsAndBeats;
	}

	
	
	@Override
	public int getLengthInBars()
	{
		BarsAndBeats lengthInBarsAndBeats = getLengthInBarsAndBeats();
		return lengthInBarsAndBeats.getBarPosition();	// + (int)Math.signum(lengthInBarsAndBeats.getOffsetInQuarters());
	}

	
	
	@Override
	public double getLengthInQuarters()
	{
		double start = associatedMu.getGlobalPositionInQuarters();
		BarsAndBeats startBab = associatedMu.getGlobalPositionInBarsAndBeats();
		int endBarPosition = startBab.getBarPosition() + lengthInBarsAndBeats.getBarPosition();
		double endOffsetInQuarters = lengthInBarsAndBeats.getOffsetInQuarters();				
		return associatedMu.getGlobalPositionInQuarters(endBarPosition) + endOffsetInQuarters - start;
	}
	
	
	
	// this is not as simple as just returning the stored value, so as to accomodate the possibility of 
	// lengthInBarsAndBeats.offsetInQuarters being greater then the length of a bar or a negative value
	@Override
	public BarsAndBeats getLengthInBarsAndBeats()
	{
		int startBar = associatedMu.getGlobalPositionInBars();
		int calculatedEndBarPosition = startBar + lengthInBarsAndBeats.getBarPosition();		
		double globalEndPositionInQuarters 
		= associatedMu.getGlobalPositionInQuarters(calculatedEndBarPosition) 
			+ lengthInBarsAndBeats.getOffsetInQuarters();
		BarsAndBeats endBab = associatedMu.getGlobalPositionInBarsAndBeats(globalEndPositionInQuarters);
		BarsAndBeats lengthBab 
		= new BarsAndBeats(endBab.getBarPosition() - startBar, endBab.getOffsetInQuarters());
		return lengthBab;
	}

	
	
	@Override
	public void calculateLength(List<Mu> mus)
	{}

	
	
	@Override
	public String positionToString()
	{
		return getContentString();
	}



	@Override
	public void setSameLengthModel(Mu aMu)
	{
		aMu.setLengthInBarsAndBeats(new BarsAndBeats(
												lengthInBarsAndBeats.getBarPosition(), 
												lengthInBarsAndBeats.getOffsetInQuarters()));	
	}



	@Override
	public String getContentString()
	{
		return "FixedLengthInBarsAndBeats=" + lengthInBarsAndBeats.toString();
	}


	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("length_model");
		element.setAttribute("type", "FixedLengthInBarsAndBeats");
		Element bar_length = document.createElement("bar_length");
		bar_length.appendChild(document.createTextNode("" + lengthInBarsAndBeats.getBarPosition()));
		element.appendChild(bar_length);
		Element beat_length = document.createElement("beat_length");
		beat_length.appendChild(document.createTextNode("" + lengthInBarsAndBeats.getOffsetInQuarters()));
		element.appendChild(beat_length);
		return element;
	}
	

}
