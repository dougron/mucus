package main.java.com.dougron.mucus.mu_framework.length_model;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class LengthFromChildren implements LengthModel
{

	
	private int lengthInBars;
	private Mu associatedMu;
	private double lengthInQuarters;


	public LengthFromChildren(Mu associatedMu)
	{
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
		if (mus.size() == 0)
		{
			lengthInBars = 0;
			lengthInQuarters = 0.0;
		}
		else
		{
			int oldLengthInBars = lengthInBars;
			double oldLengthInQuarters = lengthInQuarters;
			calculateNewLengths(mus);
			if (associatedMu.hasParent() 
					&& associatedMu.getParent().hasLengthModelFromChildren()
					&& (lengthInBars > oldLengthInBars || lengthInQuarters > oldLengthInQuarters))
			{
				associatedMu.getParent().recalculateLength();
			}
		}		
	}



	private void calculateNewLengths(List<Mu> mus)
	{
		lengthInBars = 0;
		lengthInQuarters = associatedMu.getGlobalPositionInQuarters();
		for (Mu mu: mus)
		{
			int x = mu.getGlobalBarIndexAfterEnd();
			if (x > lengthInBars) lengthInBars = x;
			double xx = mu.getGlobalEndPositionInQuarters();
			if (xx > lengthInQuarters) lengthInQuarters = xx;
		}
		lengthInQuarters -= associatedMu.getGlobalPositionInQuarters();
		lengthInBars -= associatedMu.getPositionInBars();
	}

	
	
	@Override
	public double getLengthInQuarters()
	{
		calculateLength(associatedMu.getMus());
		return lengthInQuarters;		
	}
	
	
	
	@Override
	public BarsAndBeats getLengthInBarsAndBeats()
	{
		int globalBarIndexOfStart = associatedMu.getGlobalPositionInBars();
		BarsAndBeats bab 
		= associatedMu.getGlobalPositionInBarsAndBeats(associatedMu.getGlobalEndPositionInQuarters());
		return new BarsAndBeats(bab.getBarPosition() - globalBarIndexOfStart, bab.getOffsetInQuarters());
	}

	
	
	public String toString()
	{
		return "LengthFromChildren: assoc.mu=" + associatedMu.getName()
		+ "\nlengthInBars=" + lengthInBars
		+ "\nlengthInQuarters=" + lengthInQuarters;
	}
	
	
	
	@Override
	public String positionToString()
	{
		return "LengthFromChildren: " + lengthInBars + " bars";
	}



	@Override
	public void setSameLengthModel(Mu aMu)
	{}	// is default, no need to do anthing	




	@Override
	public String getContentString()
	{
		return "LengthFromChildren";
	}


	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("length_model");
		element.setAttribute("type", "LengthFromChildren");
	
		return element;
	}
	
}
