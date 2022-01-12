package main.java.com.dougron.mucus.mu_framework.position_model;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class EndOfSiblingInBars implements PositionModel
{

	
	
	private int positionInBars;
	private Mu sibling;
//	private boolean hasUpToDatePositionInBarsAndBeats = false;
//	private BarsAndBeats positionInBarsAndBeats;			// hidden. this subclass of positionModel can only set positionInBars
	private Mu associatedMu;



	public EndOfSiblingInBars(int aPositionInBars, Mu associatedMu, Mu aSibling)
	{
		sibling = aSibling;
		this.associatedMu = associatedMu;
		positionInBars = aPositionInBars;
		
	}

	
	
	@Override
	public int getPositionInBars()
	{
		int pos = sibling.getEndPositionInBars();
		return pos + positionInBars;
	}



	@Override
	public double getPositionInQuarters()
	{
		double parentGlobalPositionInQuarters = associatedMu.getParent().getGlobalPositionInQuarters();
		double thisGlobalPositionInQuarters = associatedMu.getGlobalPositionInQuarters();
		return thisGlobalPositionInQuarters - parentGlobalPositionInQuarters;
	}
	


	@Override
	public int getGlobalPositionInBars()
	{
		return associatedMu.getParent().getGlobalPositionInBars() + getPositionInBars();
	}
	
	
	
	@Override
	public BarsAndBeats getPositionInBarsAndBeats()
	{
		BarsAndBeats globalPositionInBarsAndBeats = getGlobalPositionInBarsAndBeats();
		int globalBarPositionOfParent = associatedMu.getParent().getGlobalPositionInBars();
		return new BarsAndBeats(
				globalPositionInBarsAndBeats.getBarPosition() - globalBarPositionOfParent,
				globalPositionInBarsAndBeats.getOffsetInQuarters());
		
		
		
//		if (!hasUpToDatePositionInBarsAndBeats)
//		{
//			positionInBarsAndBeats = new BarsAndBeats(getPositionInBars(), 0.0);
//			hasUpToDatePositionInBarsAndBeats = true;
//		}
//		return positionInBarsAndBeats;		
	}

	
	
	@Override
	public double getGlobalPositionInQuarters()
	{
		if (positionInBars == 0) return sibling.getGlobalEndPositionInQuarters();
		return associatedMu.getGlobalPositionInQuarters(getGlobalPositionInBars());
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
			int siblingGlobalEndPosition = sibling.getGlobalBarIndexOfEnd();
			return siblingGlobalEndPosition + positionInBars - ancestorGlobalPosition;
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
	{
		positionInBars = aPositionInBars;		
//		hasUpToDatePositionInBarsAndBeats = false;
	}


	
	public String toString()
	{
		return "EndOfSiblingInBars: assoc.mu=" + associatedMu.getName();
	}



	@Override
	public String positionToString()
	{
		return "EndOfSibling: " + positionInBars + " bars";
	}



	@Override
	public void movePosition(int aOffsetInBars)
	{
		positionInBars += aOffsetInBars;
		
	}



	@Override
	public void movePosition(double aOffsetInQuarters)
	{
		double globalPositionInQuarters 
		= associatedMu.getGlobalPositionInQuarters() + aOffsetInQuarters;
		BarsAndBeats globalPositionInBarsAndBeats 
		= associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		int siblingEndBarIndex = sibling.getGlobalBarIndexOfEnd();
		BarsAndBeats newPositionInBarsAndBeats = new BarsAndBeats(
				globalPositionInBarsAndBeats.getBarPosition() - siblingEndBarIndex,
				globalPositionInBarsAndBeats.getOffsetInQuarters()
				);
		associatedMu.setPositionModel(new EndOfSiblingInBarsAndBeats(
												newPositionInBarsAndBeats, 
												associatedMu, 
												sibling
												)
									);
	}



	@Override
	public void movePosition(BarsAndBeats aOffsetInBarsAndBeats)
	{
		int endBarPositionOfSibling = sibling.getEndPositionInBars();
		int barPositionOfThis = associatedMu.getPositionInBars();
		BarsAndBeats newPositionInBarsAndBeats = new BarsAndBeats(
				barPositionOfThis - endBarPositionOfSibling + aOffsetInBarsAndBeats.getBarPosition(),
				aOffsetInBarsAndBeats.getOffsetInQuarters()
				);
		associatedMu.setPositionModel(new EndOfSiblingInBarsAndBeats(
												newPositionInBarsAndBeats, 
												associatedMu, 
												sibling
												)
									);
	}



	@Override
	public void addAMuToAParentWithSamePositionModel(Mu aChildMu, Mu aParentMu, HashMap<Mu, Mu> oldMuNewMuMap)
	{
		aParentMu.addMuToEndOfSibling(aChildMu, positionInBars, oldMuNewMuMap.get(sibling));		
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
		return "EndOfSiblingInBars=" + positionInBars + ",siblingIndex," + sibling.getMuIndex();
	}

	
	
	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("position_model");				
		element.setAttribute("type", "EndOfSiblingInBars");		
		Element bar_position = document.createElement("bar_position");
		bar_position.appendChild(document.createTextNode("" + positionInBars));
		element.appendChild(bar_position);
		Element sibling_index = document.createElement("sibling_index");
		sibling_index.appendChild(document.createTextNode("" + sibling.getMuIndex()));
		element.appendChild(sibling_index);
		return element;
	}
	

}
