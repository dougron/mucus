package main.java.com.dougron.mucus.mu_framework.position_model;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;

public class BeginningOfParentInBarsAnBeats implements PositionModel
{

	
	
	private BarsAndBeats positionInBarsAndBeats;
	private Mu associatedMu;



	public BeginningOfParentInBarsAnBeats(BarsAndBeats aBarsAndBeatsPosition, Mu associatedMu)
	{
		positionInBarsAndBeats = aBarsAndBeatsPosition;
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
		double parentGlobalPositionInQuarters = associatedMu.getParent().getGlobalPositionInQuarters();
		TimeSignatureGenAndMap tsgm = associatedMu.getMasterTimeSignatureGenAndMap();
		int startBarIndex = tsgm.getBarPosition(parentGlobalPositionInQuarters);
		double position = 0.0;
		position = getLengthOfFirstBarInCaseitIsNotACompleteBar(tsgm, startBarIndex, position);
		position = addLengthInQuartersOfAllSubsequentBars(tsgm, startBarIndex, position);
		position += positionInBarsAndBeats.getOffsetInQuarters();
		return position;
	}



	private double addLengthInQuartersOfAllSubsequentBars(TimeSignatureGenAndMap tsgm, int startBarIndex,
			double position)
	{
		if (positionInBarsAndBeats.getBarPosition() > 0.0)
		{
			for (int i = 1; i < positionInBarsAndBeats.getBarPosition(); i++)
			{
				position += tsgm.getTimeSignature(startBarIndex + i).getLengthInQuarters();
			}
		}
		else
		{
			for (int i = -1; i >= positionInBarsAndBeats.getBarPosition(); i--)
			{
				position -= tsgm.getTimeSignature(startBarIndex + i).getLengthInQuarters();
			}
		}
		
		return position;
	}



	private double getLengthOfFirstBarInCaseitIsNotACompleteBar(TimeSignatureGenAndMap tsgm, int startBarIndex,
			double position)
	{
		if (positionInBarsAndBeats.getBarPosition() > 0)
		{
			double parentGlobalPositionInQuarters = associatedMu.getParent().getGlobalPositionInQuarters();
			double nextBarGlobalPositionInQuarters = tsgm.getPositionInQuarters(startBarIndex + 1);
			position += nextBarGlobalPositionInQuarters - parentGlobalPositionInQuarters;
		}
		else
		{
			double thisBarGlobalPositionInQuarters = tsgm.getPositionInQuarters(startBarIndex);
			double parentGlobalPositionInQuarters = associatedMu.getParent().getGlobalPositionInQuarters();
			position += thisBarGlobalPositionInQuarters - parentGlobalPositionInQuarters;
		}		
		return position;
	}

	
	
	@Override
	public BarsAndBeats getPositionInBarsAndBeats()
	{		
		double barPositionInGlobalQuarters 
		= associatedMu.getGlobalPositionInQuarters(associatedMu.getGlobalPositionInBars());
		double positionInGlobalQuarters = associatedMu.getGlobalPositionInQuarters();
		return new BarsAndBeats(getPositionInBars(), positionInGlobalQuarters - barPositionInGlobalQuarters);
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
		positionInBarsAndBeats = new BarsAndBeats(aPositionInBars, 0.0);

	}
	
	
	
	public String toString()
	{
		return "BeginningOfParentInBarsAndBeats: assoc.mu=" + associatedMu.getName();
	}



	@Override
	public String positionToString()
	{
		return "BeginningOfParent: " 
				+ positionInBarsAndBeats.getBarPosition() + " bars "
				+ positionInBarsAndBeats.getOffsetInQuarters() + " beats";
	}



	@Override
	public void movePosition(int aOffsetInBars)		// offset of 0 bars does not move this positionModel
	{
		if (aOffsetInBars != 0)
		{
			int barPosition 
			= getGlobalPositionInBars() - associatedMu.getParent().getGlobalPositionInBars() + aOffsetInBars;
			associatedMu.setPositionInBars(barPosition);
		}	
	}



	@Override
	public void movePosition(double aOffsetInQuarters)
	{
		double globalPositionInQuarters = associatedMu.getGlobalPositionInQuarters(aOffsetInQuarters);
		BarsAndBeats globalPositionInBarsAndBeats 
		= associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		BarsAndBeats newPositionInBarsAndBeats = new BarsAndBeats(
				globalPositionInBarsAndBeats.getBarPosition() 
					- associatedMu.getParent().getGlobalPositionInBars(),
				globalPositionInBarsAndBeats.getOffsetInQuarters()
				);
		positionInBarsAndBeats = newPositionInBarsAndBeats;
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
		positionInBarsAndBeats = newPositionInBarsAndBeats;
	}


	
	@Override
	public void addAMuToAParentWithSamePositionModel(Mu aChildMu, Mu aParentMu, HashMap<Mu, Mu> oldMuNewMuMap)
	{
		aParentMu.addMu(aChildMu, positionInBarsAndBeats.getDeepCopy());		
	}
	
	
	
	@Override
	public void addAMuToAParentWithSamePositionModelButOnlyBeginningOfParent(Mu aChildMu, Mu aParentMu)
	{
		aParentMu.addMu(aChildMu, positionInBarsAndBeats.getDeepCopy());		
	}



	@Override
	public String getContentString()
	{
		return "BeginningOfParentInBarsAnBeats=" + positionInBarsAndBeats.getBarPosition() + "," + positionInBarsAndBeats.getOffsetInQuarters();
	}
	
	
	
	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("position_model");				
		element.setAttribute("type", "BeginningOfParentInBarsAndBeats");		
		Element bar_position = document.createElement("bar_position");
		bar_position.appendChild(document.createTextNode("" + positionInBarsAndBeats.getBarPosition()));
		element.appendChild(bar_position);
		Element beat_position = document.createElement("beat_position");
		beat_position.appendChild(document.createTextNode("" + positionInBarsAndBeats.getOffsetInQuarters()));
		element.appendChild(beat_position);
		return element;
	}
}
