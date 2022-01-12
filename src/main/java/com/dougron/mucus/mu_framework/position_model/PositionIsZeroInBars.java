package main.java.com.dougron.mucus.mu_framework.position_model;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class PositionIsZeroInBars implements PositionModel
{

	
	
	private static final BarsAndBeats ZERO_BARS_AND_BEATS = new BarsAndBeats(0, 0.0);



	@Override
	public int getPositionInBars()
	{
		return 0;
	}



	@Override
	public double getPositionInQuarters()
	{
		return 0.0;
	}
	
	
	
	@Override
	public int getGlobalPositionInBars()
	{
		return 0;
	}
	
	
	
	@Override
	public BarsAndBeats getPositionInBarsAndBeats()
	{
		return ZERO_BARS_AND_BEATS;
	}

	
	
	@Override
	public int getLocalPositionInBars(Mu aAncestorMu)
	{	
		return 0;
	}
	
	
	@Override
	public BarsAndBeats getLocalPositionInBarsAndBeats(Mu aAncestorMu)
	{
		return getGlobalPositionInBarsAndBeats();
	}
	
	
	@Override
	public BarsAndBeats getLocalPositionInBarsAndBeats(BarsAndBeats aGlobalPositionInBarsAndBeats)
	{
		return aGlobalPositionInBarsAndBeats;
	}



	@Override
	public void setPositionInBars(int aPositionInBars)
	{
		// Nothing to do here	
	}
	
	
	@Override
	public BarsAndBeats getGlobalPositionInBarsAndBeats()
	{
		return new BarsAndBeats(0, 0.0);
	}



	@Override
	public double getGlobalPositionInQuarters()
	{
		return 0.0;
	}



	@Override
	public double getLocalPositionInQuarters(Mu aAncestorMu)
	{
		return 0.0;
	}


	
	public String toString()
	{
		return "PositionIsZeroInBars: assoc.mu=not required";
	}



	@Override
	public String positionToString()
	{
		return "PositionIsZero";
	}



	@Override
	public void movePosition(int aOffsetInBars)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void movePosition(double aOffsetInQuarters)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void movePosition(BarsAndBeats aOffsetInBarsAndBeats)
	{
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void addAMuToAParentWithSamePositionModel(Mu aChildMu, Mu aParentMu, HashMap<Mu, Mu> oldMuNewMuMap)
	{
		// nothing to do as this would be default and never added to a parent	
	}
	
	
	
	@Override
	public void addAMuToAParentWithSamePositionModelButOnlyBeginningOfParent(Mu aChildMu, Mu aParentMu)
	{
		// nothing to do as this would be default and never added to a parent	
	}



	@Override
	public String getContentString()
	{
		return "PositionIsZeroInBars";
	}



	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("position_model");				
		element.setAttribute("type", "PositionIsZeroInBars");		
		
		return element;
	}
	



}
