package main.java.com.dougron.mucus.mu_framework.data_types;

import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;

public class BarsAndBeats
{

	private int barPosition;
	private double offsetInQuarters;

	
	
	public BarsAndBeats(int aBarPosition, double aOffsetInQuarters)
	{
		barPosition = aBarPosition;
		offsetInQuarters = aOffsetInQuarters;
	}

	
	
	public int getBarPosition()
	{
		return barPosition;
	}

	
	
	public double getOffsetInQuarters()
	{
		return offsetInQuarters;
	}

	
	
	public void setBarPosition(int aBarPosition)
	{
		barPosition = aBarPosition;	
	}



	public void setOffsetInQuarters(double aOffsetInQuarters)
	{
		offsetInQuarters = aOffsetInQuarters;		
	}



	@Override
	public String toString()
	{
		return "BarsAndBeats [barPosition=" + barPosition + ", offsetInQuarters=" + offsetInQuarters + "]";
	}



	public boolean hasSameParametersAs(BarsAndBeats aBarsAndBeats)
	{		
		return (barPosition == aBarsAndBeats.getBarPosition() && compareDouble(offsetInQuarters, aBarsAndBeats.getOffsetInQuarters(), 0.0001));
	}



	private boolean compareDouble(double d1, double d2, double resolution)
	{
		if (Math.abs(d1 - d2) <= resolution) return true;
		return false;
	}



	public boolean isSamePositionAs(BarsAndBeats aBarsAndBeats)
	{
		if (barPosition == aBarsAndBeats.getBarPosition()
			&& offsetInQuarters == aBarsAndBeats.getOffsetInQuarters())
		{
			return true;
		}
		return false;
	}
	
	
	
	public boolean isSamePositionAs(int aBarPosition, double aOffsetInQuarters)
	{
		if (barPosition == aBarPosition
			&& offsetInQuarters == aOffsetInQuarters)
		{
			return true;
		}
		return false;
	}



	public int getPositionComparison(int aBarPos, double aOffsetInQuarters, TimeSignatureGenAndMap tsGenAndMap)
	{
		double thisInQuarters = tsGenAndMap.getPositionInQuarters(this);
		double argumentInQuarters = tsGenAndMap.getPositionInQuarters(new BarsAndBeats(aBarPos, aOffsetInQuarters));
		if (thisInQuarters > argumentInQuarters) return 1;
		if (thisInQuarters < argumentInQuarters) return -1;
		return 0;
	}



	public BarsAndBeats addBarsAndBeats(BarsAndBeats aBarsAndBeats, TimeSignatureGenAndMap tsgm)
	{
		// adds BarsAndBeats by simple addition, conversion to quarters and then conversion back to 
		// BarsAndBeats
		int bars = barPosition + aBarsAndBeats.getBarPosition();
		double beats = offsetInQuarters + aBarsAndBeats.getOffsetInQuarters();
		return tsgm.getPositionInBarsAndBeats(tsgm.getPositionInQuarters(new BarsAndBeats(bars, beats)));		
	}
	
	
	public BarsAndBeats getDeepCopy()
	{
		return new BarsAndBeats(barPosition, offsetInQuarters);
	}
	

}
