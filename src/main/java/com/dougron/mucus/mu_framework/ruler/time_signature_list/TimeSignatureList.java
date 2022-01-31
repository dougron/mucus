/**
 * 
 */
package main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;


/**
 * For the initial purposes of revisiting the design of TimeSignatureMap, TimeSignature
 * is imported from time_signature_utilities as it is a good working class (esp. in relation to 
 * the musicxml_maker can of worms)
 * 
 * Firstly, renamed it TimeSignatureList, cos its a List, not a Map.
 * 
 * Rules of operation
 * 
 * a zero length list will return the default timesignature
 * a list with content will return the timesignature from the bar index
 * a list with content will return the last bar if the bar index is greater than the list length.
 * 
 * @author dougr
 *
 */
public class TimeSignatureList 
{
	private List<TimeSignature> timeSignatures;
	private TimeSignature defaultTimeSignature = TimeSignature.FOUR_FOUR;
	
	
	public TimeSignatureList()
	{
		timeSignatures = new ArrayList<TimeSignature>();
	}



	public int getBarCount()
	{
		return timeSignatures.size();
	}



	public void add(TimeSignature timeSignature)
	{
		timeSignatures.add(timeSignature);
		
	}



	public double getLengthInQuarters()
	{
		double count = 0.0;
		for (TimeSignature ts: timeSignatures) count += ts.getLengthInQuarters();
		return count;
	}



//	public TimeSignature getLastTimeSignature()
//	{
//		return timeSignatures.get(timeSignatures.size() - 1);
//	}



	public double getLengthInQuarters(int startBarIndex, int endBarIndex)
	{
		double quarters = 0.0;
		for (int i = startBarIndex; i < endBarIndex; i++)
		{
			quarters += timeSignatures.get(i % timeSignatures.size()).getLengthInQuarters();
		}
		return quarters;
	}



	public String getTimeSignaturesToString()
	{
		String str = "";
		for (TimeSignature ts: timeSignatures)
		{
			str += ts.getNumerator() + "/" + ts.getDenominator() + "__";
		}
		return str;
	}
	
	
	
	public String toString()
	{
		return getTimeSignaturesToString();
	}



	public void replaceInTimeSignatureList(TimeSignatureList tsl, int barPosition)
	{
		int barpos = barPosition;
		for (TimeSignature ts: tsl.timeSignatures)
		{
			if (barpos >= timeSignatures.size()) break;
			timeSignatures.add(barpos, ts);
			barpos++;
			timeSignatures.remove(barpos);
		}		
	}
	
	
	
	public void replaceInTimeSignatureList(TimeSignature timeSignature, int barPosition)
	{
		if (barPosition < timeSignatures.size())
		{
			timeSignatures.add(barPosition, timeSignature);
			timeSignatures.remove(barPosition + 1);	
		}
	}



	public TimeSignatureList getSubsetTimeSignature(int barPosition, int barCount)
	{
		TimeSignatureList tsl = new TimeSignatureList();
		for (int i = 0; i < barCount; i++)
		{
			tsl.add(timeSignatures.get(barPosition + i));
		}
		return tsl;
	}



	public double getBarsAndBeatsPositionInQuarters(BarsAndBeats aBarsAndBeats)
	{
		double count = 0.0;
		if (aBarsAndBeats.getBarPosition() >= 0)
		{
			for (int i = 0; i < aBarsAndBeats.getBarPosition(); i++)
			{
				count += getTimeSignature(i).getLengthInQuarters();
			}
		}
		else
		{
			for (int i = 0; i > aBarsAndBeats.getBarPosition(); i--)
			{
				count -= getTimeSignature(i).getLengthInQuarters();
			}
		}
		return count + aBarsAndBeats.getOffsetInQuarters();
	}



	public BarsAndBeats getQuartersPositionInBarsAndBeats(double aQuartersPosition)
	{
		int barCount = 0;
		int tsIndex = 0;
		double remainder = aQuartersPosition;
		while (remainder >= getTimeSignature(tsIndex).getLengthInQuarters() )
		{
			barCount++;			
			remainder -= getTimeSignature(tsIndex).getLengthInQuarters();
			if (remainder == 0.0) break;
			tsIndex++;
		}
		return new BarsAndBeats(barCount, remainder);
	}



	public Iterator<TimeSignature> iterator()
	{
		return timeSignatures.iterator();
	}



	public BarsAndBeats getLengthInBarsAndBeats()
	{
		return new BarsAndBeats(getBarCount(), 0.0);
	}



	public TimeSignature getTimeSignature(int aBarIndex)
	{
		if (timeSignatures.size() == 0)
		{
			return defaultTimeSignature;
		}
		
		if (aBarIndex >= timeSignatures.size())
		{
			return timeSignatures.get(timeSignatures.size() - 1);
		}
		
		if (aBarIndex < 0)
		{
			return timeSignatures.get(0);
		}
		
		return timeSignatures.get(aBarIndex);
	}


	public void setDefaultTimeSignature(TimeSignature aTimeSignature)
	{
		defaultTimeSignature = aTimeSignature;
	}


	
	public Object[] getParameterObjectArray()
	{
		Object[] arr = new Object[timeSignatures.size() + 1];
		arr[0] = "TimeSignatureList";
		for (int i = 0; i < timeSignatures.size(); i ++)
		{
			Object[] tsArr = timeSignatures.get(i).getParameterObjectArray();
			if (tsArr.length == 1)
			{
				arr[i + 1] = tsArr[0];
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < arr.length - 1; j++)
				{
					sb.append(tsArr[j] + "/");
				}
				sb.append(arr[arr.length - 1]);
				arr[i + 1] = sb.toString();
			}
		}
		return arr;
	}
	
	
	
	
	
	
	
}
