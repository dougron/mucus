package main.java.com.dougron.mucus.algorithms.contour;

import java.util.ArrayList;
import java.util.List;

public class ContourFunction
{

	
	
	private List<BreakPoint> bpList = null;;



	public double getRawFunctionOutput(double aXPosition)
	{
		double startXPos = 0.0;
		double startYPos = 0.0;
		if (bpList == null || bpList.size() == 0)
		{
			return 0.0;
		}
		for (BreakPoint bp: bpList)
		{
			if (aXPosition >= startXPos && aXPosition < bp.getXPosition())
			{
				return getRawYValue(aXPosition, startXPos, startYPos, bp.getXPosition(), bp.getYPosition());
			}
			startXPos = bp.getXPosition();
			startYPos = bp.getYPosition();
		}
		return getRawYValue(aXPosition, startXPos, startYPos, 1.0, 0.0);
	}



	private double getRawYValue(double aXPosition, double startXPos, double startYPos, double endXPos, double endYPos)
	{
		double xPosPercent = (aXPosition - startXPos) / (endXPos - startXPos);
		double yRange = (endYPos - startYPos);
		double yValue = yRange * xPosPercent + startYPos;
		return yValue;
	}

	
	
	public void addBreakpoint(double aXPosition, double aYPosition)
	{
		if (bpList == null) bpList = new ArrayList<BreakPoint>();
		bpList.add(new BreakPoint(aXPosition, aYPosition));
	}



	public int getPitch(double aPositionInFloatBars, double noteRangeMultiplier, int aStartPitch, int aEndPitch, double aStartPositionInFloatBars, double aEndPositionInFloatBars)
	{
		double percentagePosition = (aPositionInFloatBars - aStartPositionInFloatBars) / (aEndPositionInFloatBars - aStartPositionInFloatBars);
		double rawValue = getRawFunctionOutput(percentagePosition);
		double doublePitch = (aEndPitch - aStartPitch) * percentagePosition + aStartPitch + (rawValue * noteRangeMultiplier);
		return (int)Math.round(doublePitch);
	}

}
