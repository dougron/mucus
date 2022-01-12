package main.java.com.dougron.mucus.mu_framework.length_model;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;

public class FixedLengthInBars implements LengthModel
{

	
	
	private int lengthInBars;
	private Mu associatedMu;



	public FixedLengthInBars(int aLengthInBars, Mu associatedMu)
	{
		lengthInBars = aLengthInBars;
		this.associatedMu = associatedMu;
	}

	
	
	@Override
	public int getLengthInBars()
	{
		return lengthInBars;
	}



	@Override
	public void calculateLength(List<Mu> mus)
	{
		// no calculation required
		
	}

	
	
	@Override
	public BarsAndBeats getLengthInBarsAndBeats()
	{
		return new BarsAndBeats(lengthInBars, 0.0);
	}

	

	@Override
	public double getLengthInQuarters()
	{
		int startBarGlobalIndex = associatedMu.getGlobalPositionInBars();
		double secondBarGlobalPositionInQuarters 
		= associatedMu.getGlobalPositionInQuarters(startBarGlobalIndex + 1);
		double globalPositionInQuarters = associatedMu.getGlobalPositionInQuarters();
		TimeSignatureGenAndMap tsgm = associatedMu.getMasterTimeSignatureGenAndMap();
		double lengthInQuarters = secondBarGlobalPositionInQuarters - globalPositionInQuarters;
		lengthInQuarters += addLengthOfTheRestOfTheBars(tsgm, startBarGlobalIndex);
		return lengthInQuarters;
	}
	
	
	
	private double addLengthOfTheRestOfTheBars(TimeSignatureGenAndMap tsgm, int aGlobalBarIndex)
	{
		double count = 0.0;
		for (int i = 1; i < lengthInBars; i++)
		{
			count += tsgm.getTimeSignature(aGlobalBarIndex + i).getLengthInQuarters();
		}
		return count;
	}



	public String toString()
	{
		return "FixedLengthInBars: assoc.mu=" + associatedMu.getName();
	}



	@Override
	public String positionToString()
	{
		return "FixedLength: " + lengthInBars + " bars";
	}



	@Override
	public void setSameLengthModel(Mu aMu)
	{
		aMu.setLengthInBars(lengthInBars);
		
	}



	@Override
	public String getContentString()
	{
		return "FixedLengthInBars=" + lengthInBars;
	}



	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("length_model");
		element.setAttribute("type", "FixedLengthInBars");
		Element bar_length = document.createElement("bar_length");
		bar_length.appendChild(document.createTextNode("" + lengthInBars));
		element.appendChild(bar_length);
		return element;
	}



	

	

}
