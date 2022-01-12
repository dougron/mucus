package main.java.com.dougron.mucus.mu_framework.position_model;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;


public class EndOfSiblingInBarsAndBeats implements PositionModel
{

	
	
	private BarsAndBeats positionInBarsAndBeats;
	private Mu associatedMu;
	private Mu sibling;



	public EndOfSiblingInBarsAndBeats(BarsAndBeats aBarsAndBeats, Mu aMuToAdd, Mu aSiblingToAddTo)
	{
		positionInBarsAndBeats = aBarsAndBeats;
		associatedMu = aMuToAdd;
		sibling = aSiblingToAddTo;
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
	
		double parentGlobalPositionInQuarters = associatedMu.getParent().getGlobalPositionInQuarters();
		return getGlobalPositionInQuarters() - parentGlobalPositionInQuarters;
		
	}
	
	
	
	@Override
	public BarsAndBeats getPositionInBarsAndBeats()
	{
		BarsAndBeats globalPositionInBarsAndBeats = getGlobalPositionInBarsAndBeats();
		int globalBarIndexOfParent = associatedMu.getParent().getGlobalPositionInBars();
		return new BarsAndBeats(
				globalPositionInBarsAndBeats.getBarPosition() - globalBarIndexOfParent,
				globalPositionInBarsAndBeats.getOffsetInQuarters());
	}

	
	
	@Override
	public int getGlobalPositionInBars()
	{
		int globalBarIndexOfSiblingEnd = getGlobalBarIndexOfSiblingEnd();
		int barIndexOfStart = globalBarIndexOfSiblingEnd + positionInBarsAndBeats.getBarPosition();
		double globalPositionInQuarters 
		= associatedMu.getGlobalPositionInQuarters(barIndexOfStart) 
			+ positionInBarsAndBeats.getOffsetInQuarters();
		return associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters).getBarPosition();
	}

	
	
	private int getGlobalBarIndexOfSiblingEnd()
	{
		return sibling.getGlobalEndPositionInBarsAndBeats().getBarPosition();
	}



	@Override
	public double getGlobalPositionInQuarters()
	{
		int globalBarIndexOfSiblingEnd = getGlobalBarIndexOfSiblingEnd();
		int barIndexOfStart = globalBarIndexOfSiblingEnd + positionInBarsAndBeats.getBarPosition();
		double globalPositionInQuarters 
		= associatedMu.getGlobalPositionInQuarters(barIndexOfStart) 
			+ positionInBarsAndBeats.getOffsetInQuarters();
		return globalPositionInQuarters;
	}

	
	
	@Override
	public BarsAndBeats getGlobalPositionInBarsAndBeats()
	{
		return associatedMu.getGlobalPositionInBarsAndBeats(getGlobalPositionInQuarters());
	}

	
	
	@Override
	public int getLocalPositionInBars(Mu aAncestorMu)
	{
		if (associatedMu == aAncestorMu)
		{
			return 0;
		}
		else 
		{
			int ancestorGlobalPosition = aAncestorMu.getGlobalPositionInBars();
			BarsAndBeats thisGlobalPosition = getGlobalPositionInBarsAndBeats();
			return thisGlobalPosition.getBarPosition() - ancestorGlobalPosition;
		}
	}

	
	
	@Override
	public double getLocalPositionInQuarters(Mu aAncestorMu)
	{
		if (associatedMu == aAncestorMu)
		{
			return 0.0;
		}
		else 
		{
			double ancestorGlobalPosition = aAncestorMu.getGlobalPositionInQuarters();
			double thisGlobalPosition = getGlobalPositionInQuarters();
			return thisGlobalPosition - ancestorGlobalPosition;
		}
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
	{}

	
	
	@Override
	public String positionToString()
	{
		return null;
	}



	@Override
	public void movePosition(int aOffsetInBars)
	{
		if (aOffsetInBars != 0)
		{
			positionInBarsAndBeats = new BarsAndBeats(
												positionInBarsAndBeats.getBarPosition() + aOffsetInBars,
												0.0
												);
		}	
	}



	@Override
	public void movePosition(double aOffsetInQuarters)
	{
		double globalPositionInQuarters = associatedMu.getGlobalPositionInQuarters() + aOffsetInQuarters;
		BarsAndBeats globalPositionInBAB 
		= associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		int siblingEndBarIndex = sibling.getGlobalBarIndexOfEnd();
		BarsAndBeats newPositionInBAB = new BarsAndBeats(
				globalPositionInBAB.getBarPosition() - siblingEndBarIndex,
				globalPositionInBAB.getOffsetInQuarters()
				);
		positionInBarsAndBeats = newPositionInBAB;
	}



	@Override
	public void movePosition(BarsAndBeats aOffsetInBarsAndBeats)
	{
		BarsAndBeats globalPositionInBAB 
		= associatedMu.getEndPositionInBarsAndBeats();
		double globalPositionInQuarters 
		= associatedMu.getGlobalPositionInQuarters(
										globalPositionInBAB.getBarPosition() 
										+ aOffsetInBarsAndBeats.getBarPosition()
										) 
										+ aOffsetInBarsAndBeats.getOffsetInQuarters(); 
		BarsAndBeats newGlobalPositionInBAB 
		= associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		positionInBarsAndBeats = new BarsAndBeats(
				newGlobalPositionInBAB.getBarPosition() - sibling.getGlobalBarIndexOfEnd(),
				newGlobalPositionInBAB.getOffsetInQuarters()			
				);
	}
	
	
	
	@Override
	public void addAMuToAParentWithSamePositionModel(Mu aChildMu, Mu aParentMu, HashMap<Mu, Mu> oldMuNewMuMap)
	{
		aParentMu.addMuToEndOfSibling(aChildMu, positionInBarsAndBeats.getDeepCopy(), oldMuNewMuMap.get(sibling));		
	}
	
	
	
	@Override
	public void addAMuToAParentWithSamePositionModelButOnlyBeginningOfParent(Mu aChildMu, Mu aParentMu)
	{
		BarsAndBeats positionInBarsAndBeats = associatedMu.getPositionInBarsAndBeats();
		aParentMu.addMu(aChildMu, positionInBarsAndBeats);		
	}



	@Override
	public String getContentString()
	{
		return "EndOfSiblingInBarsAndBeats=" + positionInBarsAndBeats.toString() 
		+ positionInBarsAndBeats.getBarPosition() 
		+ "," + positionInBarsAndBeats.getOffsetInQuarters()
		+ ",siblingIndex," + sibling.getMuIndex();
	}


	
	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("position_model");				
		element.setAttribute("type", "EndOfSiblingInBarsAndBeats");		
		Element bar_position = document.createElement("bar_position");
		bar_position.appendChild(document.createTextNode("" + positionInBarsAndBeats.getBarPosition()));
		element.appendChild(bar_position);
		Element beat_position = document.createElement("beat_position");
		beat_position.appendChild(document.createTextNode("" + positionInBarsAndBeats.getOffsetInQuarters()));
		element.appendChild(beat_position);
		Element sibling_index = document.createElement("sibling_index");
		sibling_index.appendChild(document.createTextNode("" + sibling.getMuIndex()));
		element.appendChild(sibling_index);
		return element;
	}
}
