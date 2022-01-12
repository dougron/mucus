package main.java.com.dougron.mucus.mu_framework.position_model;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class BeginningOfParentInQuarters implements PositionModel
{

	
	
	private double positionInQuarters;
	private Mu associatedMu;



	public BeginningOfParentInQuarters(double aPositionInQuarters, Mu associatedMu)
	{
		positionInQuarters = aPositionInQuarters;
		this.associatedMu = associatedMu;
	}



	@Override
	public int getPositionInBars()
	{
		int parentGlobalPositionInBars = associatedMu.getParent().getGlobalPositionInBars();
		int thisGlobalPositionInBars = associatedMu.getGlobalPositionInBars();
		return thisGlobalPositionInBars - parentGlobalPositionInBars;
	}

	
	
	@Override
	public double getPositionInQuarters()
	{
		return positionInQuarters;
	}


	
	@Override
	public BarsAndBeats getPositionInBarsAndBeats()
	{		
		BarsAndBeats parentGlobalPositionInBarsAndBeats 
		= associatedMu.getParent().getGlobalPositionInBarsAndBeats();
		BarsAndBeats wildPositionInBarsAndBeats 
		= new BarsAndBeats(
				parentGlobalPositionInBarsAndBeats.getBarPosition(), 
				parentGlobalPositionInBarsAndBeats.getOffsetInQuarters() + getPositionInQuarters()
				);
		double globalPositionInQuarters 
		= associatedMu.getGlobalPositionInQuarters(wildPositionInBarsAndBeats);
		BarsAndBeats actualGlobalPositionInBarsAndBeats 
		= associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		
		int actualBarIndex 
		= actualGlobalPositionInBarsAndBeats.getBarPosition() 
			- parentGlobalPositionInBarsAndBeats.getBarPosition();
		double actualOffset;
		if (actualBarIndex == 0)
		{
			actualOffset 
			= actualGlobalPositionInBarsAndBeats.getOffsetInQuarters() 
				- parentGlobalPositionInBarsAndBeats.getOffsetInQuarters();
		}
		else 
		{
			actualOffset = actualGlobalPositionInBarsAndBeats.getOffsetInQuarters();
		}
		BarsAndBeats positionInBarsAndBeats = new BarsAndBeats(actualBarIndex, actualOffset);
		return positionInBarsAndBeats;	
	}

	
	
	@Override
	public int getGlobalPositionInBars()
	{
		double globalPositionInQuarters = getGlobalPositionInQuarters();
		BarsAndBeats bab = associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		return bab.getBarPosition();
	}

	
	
	@Override
	public double getGlobalPositionInQuarters()
	{	
		double parentGlobalPositionInQuarters = associatedMu.getParent().getGlobalPositionInQuarters();
		return parentGlobalPositionInQuarters + getPositionInQuarters();
	}



	@Override
	public BarsAndBeats getGlobalPositionInBarsAndBeats()
	{
		return associatedMu.getGlobalPositionInBarsAndBeats(getGlobalPositionInQuarters());
	}
	
	
	
	@Override
	public int getLocalPositionInBars(Mu aAncestorMu)
	{
		int ancestorGlobalPosition = aAncestorMu.getGlobalPositionInBars();
		int thisGlobalPosition = getGlobalPositionInBars();
		return thisGlobalPosition - ancestorGlobalPosition;
	}

	
	
	@Override
	public double getLocalPositionInQuarters(Mu aAncestorMu)
	{
		double ancestorGlobalPosition = aAncestorMu.getGlobalPositionInQuarters();
		double thisGlobalPosition = getGlobalPositionInQuarters();
		return thisGlobalPosition - ancestorGlobalPosition;
	}
	
	
	
	@Override
	public BarsAndBeats getLocalPositionInBarsAndBeats(Mu aAncestorMu)
	{
		if (associatedMu == aAncestorMu)
		{
			return new BarsAndBeats(0, 0.0);
		}
		else 
		{
			BarsAndBeats ancestorGlobalPosition = aAncestorMu.getGlobalPositionInBarsAndBeats();
			BarsAndBeats thisGlobalPosition = getGlobalPositionInBarsAndBeats();
			int barPosition = thisGlobalPosition.getBarPosition() - ancestorGlobalPosition.getBarPosition();
			return new BarsAndBeats(barPosition, thisGlobalPosition.getOffsetInQuarters());
		}
	}
	
	
	@Override
	public BarsAndBeats getLocalPositionInBarsAndBeats(BarsAndBeats aGlobalPositionInBarsAndBeats)
	{
		BarsAndBeats thisGlobalPosition = getGlobalPositionInBarsAndBeats();
		int barPosition = aGlobalPositionInBarsAndBeats.getBarPosition() - thisGlobalPosition.getBarPosition();
		return new BarsAndBeats(barPosition, aGlobalPositionInBarsAndBeats.getOffsetInQuarters());
	}

	
	
	@Override
	public void setPositionInBars(int aPositionInBars)
	{
		associatedMu.setPositionModel(new BeginningOfParentInBars(aPositionInBars, associatedMu));
	}
	
	
	
	public String toString()
	{
		return "BeginningOfParentInQuarters:\n" + positionInQuarters + " quarters" + "\nassoc.mu=" + associatedMu.getName();
	}



	@Override
	public String positionToString()
	{
		return "BeginningOfParent: " 
				+ positionInQuarters + " quarters";
	}



	@Override
	public void movePosition(int aOffsetInBars)
	{
		int newPositionInBars = getPositionInBars() + aOffsetInBars;
		associatedMu.setPositionInBars(newPositionInBars);		
	}



	@Override
	public void movePosition(double aOffsetInQuarters)
	{
		positionInQuarters += aOffsetInQuarters;		
	}



	@Override
	public void movePosition(BarsAndBeats aOffsetInBarsAndBeats)
	{
		BarsAndBeats globalPositionInAndBeats = associatedMu.getGlobalPositionInBarsAndBeats();
		BarsAndBeats tempBab = new BarsAndBeats(
				globalPositionInAndBeats.getBarPosition() + aOffsetInBarsAndBeats.getBarPosition(),
				globalPositionInAndBeats.getOffsetInQuarters());
		int parentGlobalBarPosition = associatedMu.getParent().getGlobalPositionInBars();
		BarsAndBeats newPositionInBarsAndBeats = new BarsAndBeats(
				tempBab.getBarPosition() - parentGlobalBarPosition,
				tempBab.getOffsetInQuarters()
				);
		associatedMu.setPositionInBarsAndBeats(newPositionInBarsAndBeats);
	}

	
	
	@Override
	public void addAMuToAParentWithSamePositionModel(Mu aChildMu, Mu aParentMu, HashMap<Mu, Mu> oldMuNewMuMap)
	{
		aParentMu.addMu(aChildMu, positionInQuarters);		
	}
	
	
	
	@Override
	public void addAMuToAParentWithSamePositionModelButOnlyBeginningOfParent(Mu aChildMu, Mu aParentMu)
	{
		aParentMu.addMu(aChildMu, positionInQuarters);		
	}



	@Override
	public String getContentString()
	{
		return "BeginningOfParentInQuarters=" + positionInQuarters;
	}

	
	
	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("position_model");				
		element.setAttribute("type", "BeginningOfParentInQuarters");		
		Element beat_position = document.createElement("position_in_quarters");
		beat_position.appendChild(document.createTextNode("" + positionInQuarters));
		element.appendChild(beat_position);
		return element;
	}
}
