package main.java.com.dougron.mucus.mu_framework.position_model;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;

public class BeginningOfParentInBars implements PositionModel
{

	private int positionInBars;
	private boolean hasUpToDatePositionInBarsAndBeats = false;
	private BarsAndBeats positionInBarsAndBeats;			// hidden. this subclass of positionModel can only set positionInBars
	private Mu associatedMu;




	public BeginningOfParentInBars(int aBarPosition, Mu associatedMu)
	{
		positionInBars = aBarPosition;
		this.associatedMu = associatedMu;
	}

	
	
	@Override
	public int getPositionInBars()
	{
		return positionInBars;
	}



	@Override
	public double getPositionInQuarters()
	{
		TimeSignatureGenAndMap tsgm = associatedMu.getMasterTimeSignatureGenAndMap();
		int parentStartBarIndex = associatedMu.getParent().getGlobalPositionInBars();
		double position = getLocalPositionInQuarters(tsgm, parentStartBarIndex);
		return position;
	}



	private double getLocalPositionInQuarters(TimeSignatureGenAndMap tsgm, int parentStartBarIndex)
	{
		double position = 0.0;
		if (positionInBars >= 0)
		{
			for (int i = 0; i < positionInBars; i++)
			{
				position += tsgm.getTimeSignature(parentStartBarIndex + i).getLengthInQuarters();
			}
		}
		else
		{
			for (int i = -1; i >= positionInBars; i--)
			{
				position -= tsgm.getTimeSignature(parentStartBarIndex + i).getLengthInQuarters();
			}
		}		
		return position;
	}
	
	
	
	@Override
	public BarsAndBeats getPositionInBarsAndBeats()
	{
		if (!hasUpToDatePositionInBarsAndBeats)
		{
			positionInBarsAndBeats = new BarsAndBeats(positionInBars, 0.0);
			hasUpToDatePositionInBarsAndBeats = true;
		}
		return positionInBarsAndBeats;		
	}

	

	@Override
	public int getGlobalPositionInBars()
	{
		return associatedMu.getParent().getGlobalPositionInBars(0) + positionInBars;
	}
	
	
	
	@Override
	public double getGlobalPositionInQuarters()
	{
		int parentGlobalBarPosition = associatedMu.getParent().getGlobalPositionInBars();
		return associatedMu.getGlobalPositionInQuarters(parentGlobalBarPosition + positionInBars);
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
			return associatedMu.getParent().getLocalPositionInBars(0, aAncestorMu) + positionInBars;
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
			double ancestorGlobalPositionInQuarters = aAncestorMu.getGlobalPositionInQuarters();
			double thisGlobalPositionInQuarters = getGlobalPositionInQuarters();
			return thisGlobalPositionInQuarters - ancestorGlobalPositionInQuarters;
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
		hasUpToDatePositionInBarsAndBeats = false;
	}



	public String toString()
	{
		return "BeginningOfParentInBars: assoc.mu=" + associatedMu.getName();
	}



	@Override
	public String positionToString()
	{
		return "BeginningOfParent: " + positionInBars + " bars";
	}



	@Override
	public void movePosition(int aOffsetInBars)
	{
		positionInBars += aOffsetInBars;
		associatedMu.getParent().recalculateLength();
	}



	@Override
	public void movePosition(double aOffsetInQuarters)
	{
		double globalPositionInQuarters = associatedMu.getGlobalPositionInQuarters() + aOffsetInQuarters;
		BarsAndBeats globalPositionInBarsAndBeats 
		= associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		BarsAndBeats newPositionInBarsAndBeats = new BarsAndBeats(
				globalPositionInBarsAndBeats.getBarPosition() 
					- associatedMu.getParent().getGlobalPositionInBars(),
				globalPositionInBarsAndBeats.getOffsetInQuarters()
				);
		associatedMu.setPositionInBarsAndBeats(newPositionInBarsAndBeats);
	}



	@Override
	public void movePosition(BarsAndBeats aOffsetInBarsAndBeats)
	{
		BarsAndBeats globalPositionInBarsAndBeats = getGlobalPositionInBarsAndBeats();		
		int globalBarPosition 
		= globalPositionInBarsAndBeats.getBarPosition() + aOffsetInBarsAndBeats.getBarPosition();
		double globalPositionInQuarters = associatedMu.getGlobalPositionInQuarters(globalBarPosition) 
				+ aOffsetInBarsAndBeats.getOffsetInQuarters();
		BarsAndBeats newGlobalBarsAndBeatsPosition 
		= associatedMu.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		BarsAndBeats newPositionInBarsAndBeats = new BarsAndBeats(
				newGlobalBarsAndBeatsPosition.getBarPosition() 
					- associatedMu.getParent().getGlobalPositionInBars(),
				newGlobalBarsAndBeatsPosition.getOffsetInQuarters()
				);
		associatedMu.setPositionInBarsAndBeats(newPositionInBarsAndBeats);
	}



	@Override
	public void addAMuToAParentWithSamePositionModel(Mu aChildMu, Mu aParentMu, HashMap<Mu, Mu> oldMuNewMuMap)
	{
		aParentMu.addMu(aChildMu, positionInBars);		
	}



	@Override
	public void addAMuToAParentWithSamePositionModelButOnlyBeginningOfParent(Mu aChildMu, Mu aParentMu)
	{
		aParentMu.addMu(aChildMu, positionInBars);		
	}



	@Override
	public String getContentString()
	{
		return "BeginningOfParentInBars=" + positionInBars;
	}



	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("position_model");				
		element.setAttribute("type", "BeginningOfParentInBars");		
		Element bar_position = document.createElement("bar_position");
		bar_position.appendChild(document.createTextNode("" + positionInBars));
		element.appendChild(bar_position);
		return element;
	}



	



	












}
