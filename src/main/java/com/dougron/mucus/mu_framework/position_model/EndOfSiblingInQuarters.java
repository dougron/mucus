package main.java.com.dougron.mucus.mu_framework.position_model;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class EndOfSiblingInQuarters implements PositionModel
{

	private double positionInQuarters;
	private Mu associatedMu;
	private Mu sibling;



	public EndOfSiblingInQuarters(double aPositionInQuarters, Mu aMuToAdd, Mu aSiblingToAddTo)
	{
		positionInQuarters = aPositionInQuarters;
		associatedMu = aMuToAdd;
		sibling = aSiblingToAddTo;
	}



	@Override
	public int getPositionInBars()
	{
		BarsAndBeats globalPositionInBarsAndBeats = getGlobalPositionInBarsAndBeats();
		int globalBarPositionOfParent = associatedMu.getParent().getGlobalPositionInBars();
		return globalPositionInBarsAndBeats.getBarPosition() - globalBarPositionOfParent;
	}

	
	
	@Override
	public double getPositionInQuarters()
	{
		double globalPositionOfParentInQuarters = associatedMu.getParent().getGlobalPositionInQuarters();
		return getGlobalPositionInQuarters() - globalPositionOfParentInQuarters;
	}

	
	
	@Override
	public BarsAndBeats getPositionInBarsAndBeats()
	{
		BarsAndBeats globalPositionInBarsAndBeats = getGlobalPositionInBarsAndBeats();
		int globalBarPositionOfParent = associatedMu.getParent().getGlobalPositionInBars();
		return new BarsAndBeats(
				globalPositionInBarsAndBeats.getBarPosition() - globalBarPositionOfParent,
				globalPositionInBarsAndBeats.getOffsetInQuarters());
	}

	
	
	@Override
	public int getGlobalPositionInBars()
	{
		return associatedMu.getGlobalPositionInBarsAndBeats(getGlobalPositionInQuarters()).getBarPosition();
	}

	
	
	@Override
	public double getGlobalPositionInQuarters()
	{
		return sibling.getGlobalEndPositionInQuarters() + positionInQuarters;
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
			double thisGlobalPosition = sibling.getGlobalEndPositionInQuarters() + positionInQuarters;
			int thisGlobalBarIndex = associatedMu.getGlobalPositionInBarsAndBeats(thisGlobalPosition).getBarPosition();
			return thisGlobalBarIndex - ancestorGlobalPosition;
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
	public void setPositionInBars(int aPositionInBars)
	{
		// TODO Auto-generated method stub

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
	public String positionToString()
	{
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void movePosition(int aOffsetInBars)
	{
		int positionInBars = getGlobalPositionInBars() - sibling.getGlobalBarIndexOfEnd();
		associatedMu.setPositionModel(new EndOfSiblingInBars(positionInBars + aOffsetInBars, associatedMu, sibling));		
	}



	@Override
	public void movePosition(double aOffsetInQuarters)
	{
		positionInQuarters += aOffsetInQuarters;
		
	}



	@Override
	public void movePosition(BarsAndBeats aOffsetInBarsAndBeats)
	{
		BarsAndBeats globalBAB = getGlobalPositionInBarsAndBeats();
		BarsAndBeats movedGlobalBAB = new BarsAndBeats(
				globalBAB.getBarPosition() + aOffsetInBarsAndBeats.getBarPosition(),
				aOffsetInBarsAndBeats.getOffsetInQuarters()
				);
		double globalPositionInQuarters = associatedMu.getGlobalPositionInQuarters(movedGlobalBAB);
		BarsAndBeats newGlobalBAB = associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		
		BarsAndBeats newBAB = new BarsAndBeats(
				newGlobalBAB.getBarPosition() - sibling.getGlobalBarIndexOfEnd(),
				newGlobalBAB.getOffsetInQuarters()
				);
		associatedMu.setPositionModel(new EndOfSiblingInBarsAndBeats(newBAB, associatedMu, sibling));
	}
	
	
	
	@Override
	public void addAMuToAParentWithSamePositionModel(Mu aChildMu, Mu aParentMu, HashMap<Mu, Mu> oldMuNewMuMap)
	{
		aParentMu.addMuToEndOfSibling(aChildMu, positionInQuarters, oldMuNewMuMap.get(sibling));		
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
		return "EndOfSiblingInQuarters=" + positionInQuarters + ",siblingIndex," + sibling.getMuIndex();
	}
	
	
	
	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("position_model");				
		element.setAttribute("type", "EndOfSiblingInQuarters");		
		Element position_in_quarters = document.createElement("position_in_quarters");
		position_in_quarters.appendChild(document.createTextNode("" + positionInQuarters));
		element.appendChild(position_in_quarters);
		Element sibling_index = document.createElement("sibling_index");
		sibling_index.appendChild(document.createTextNode("" + sibling.getMuIndex()));
		element.appendChild(sibling_index);
		return element;
	}


}
