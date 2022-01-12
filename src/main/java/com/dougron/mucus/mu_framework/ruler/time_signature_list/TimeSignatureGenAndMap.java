package main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import time_signature_utilities.time_signature.TimeSignature;

/*
 * A way of dealing with the parent timeSignatureMap which does not rely on knowing the 
 * length of the piece in question
 */

public class TimeSignatureGenAndMap
{

	
	private TimeSignatureListGenerator timeSignatureGenerator; 
	private TimeSignatureList timeSignatureList;
	private Map<Integer, TimeSignature> tsMap = new HashMap<Integer, TimeSignature>();
	private boolean isUsingDefaultTimeSignatureGenerator;

	
	
	public String contentString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("TimeSignatureListGenerator:\n" + timeSignatureGenerator.toString() + "\n");
		sb.append("TimeSignatureList:\n" + timeSignatureList.toString() + "\n");
		sb.append("tsMap;\n");
		for (Integer key: tsMap.keySet())
		{
			sb.append("bar=" + key + ": " + tsMap.get(key).toString() + "\n");
		}
		return sb.toString();
	}
	
	
	
	public TimeSignatureGenAndMap()
	{
		setTimeSignatureGenerator(new SingleTimeSignature(TimeSignature.FOUR_FOUR));
		isUsingDefaultTimeSignatureGenerator = true;
	}
	
	
	
	public TimeSignatureGenAndMap(TimeSignature aTimeSignature)
	{
		setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(aTimeSignature));
	}
	
	
	
	public TimeSignatureGenAndMap(TimeSignature[] aTimeSignatureArray)
	{
		setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(aTimeSignatureArray));
	}
	
	
	
	public TimeSignatureGenAndMap getDeepCopy()
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap();
		tsgm.setTimeSignatureGenerator(timeSignatureGenerator);
		for (Integer key: tsMap.keySet())
		{
			tsgm.addTimeSignature(key, tsMap.get(key));
		}
		return tsgm;
	}
	
	
	
	public TimeSignature getTimeSignature(int aBarIndex)
	{
		if (tsMap.containsKey(aBarIndex)) return tsMap.get(aBarIndex);		
		while (aBarIndex < 0) aBarIndex += timeSignatureList.getBarCount();
		if (tsMap.containsKey(aBarIndex)) return tsMap.get(aBarIndex);
		return timeSignatureList.getTimeSignature(aBarIndex % timeSignatureList.getBarCount());
	}

	
	
	public void setTimeSignatureGenerator(TimeSignatureListGenerator aGenerator)
	{
		timeSignatureGenerator = aGenerator;	
		timeSignatureList = timeSignatureGenerator.getTimeSignatureList(timeSignatureGenerator.getTimeSignatureCount());
		isUsingDefaultTimeSignatureGenerator = false;
	}


	
	public void addTimeSignature(int aBarIndex, TimeSignature aTimeSignature)
	{
		tsMap.put(aBarIndex, aTimeSignature);
		
	}



	public double getPositionInQuarters(int aBarIndex)
	{
		double count = 0.0;
		if (aBarIndex > -1)
		{
			for (int i = 0; i < aBarIndex; i++)
			{
				count += getTimeSignature(i).getLengthInQuarters();
			}
		}
		else
		{
			for (int i = 0; i > aBarIndex; i--)
			{
				count -= getTimeSignature(i - 1).getLengthInQuarters();
			}
		}		
		return count;
	}



	public double getPositionInQuarters(BarsAndBeats aBarsAndBeats)
	{		
		return getPositionInQuarters(aBarsAndBeats.getBarPosition()) + aBarsAndBeats.getOffsetInQuarters();
	}



	public BarsAndBeats getPositionInBarsAndBeats(double aPositionInQuarters)
	{		
		if (aPositionInQuarters >= 0.0)
		{
			return barsAndBeatsPositionForPositivePositionInQuarters(aPositionInQuarters);
		}
		else
		{
			return barsAndBeatsPositionForNegativePositionInQuarters(aPositionInQuarters);
		}
	}



	private BarsAndBeats barsAndBeatsPositionForNegativePositionInQuarters(double aPositionInQuarters)
	{
		int barIndex = -1;
		while (true)
		{
			double d = aPositionInQuarters + getTimeSignature(barIndex).getLengthInQuarters();
			if (d >= 0.0)
			{
				return new BarsAndBeats(barIndex, d);
			}
			aPositionInQuarters += getTimeSignature(barIndex).getLengthInQuarters();
			barIndex--;
		}
	}



	private BarsAndBeats barsAndBeatsPositionForPositivePositionInQuarters(double aPositionInQuarters)
	{
		int barIndex = 0;
		while (true)
		{
			if (aPositionInQuarters - getTimeSignature(barIndex).getLengthInQuarters() < 0.0)
			{
				return new BarsAndBeats(barIndex, aPositionInQuarters);
			}
			aPositionInQuarters -= getTimeSignature(barIndex).getLengthInQuarters();
			barIndex++;
		}
	}



	public int getBarPosition(double aPositionInQuarters)
	{
		if (aPositionInQuarters >= 0.0)
		{
			return barsPositionForPositivePositionInQuarters(aPositionInQuarters);
		}
		else
		{
			return barsPositionForNegativePositionInQuarters(aPositionInQuarters);
		}
	}
	
	
	
	private int barsPositionForNegativePositionInQuarters(double aPositionInQuarters)
	{
		int barIndex = -1;
		while (true)
		{
			double d = aPositionInQuarters + getTimeSignature(barIndex).getLengthInQuarters();
			if (d >= 0.0)
			{
				return barIndex;
			}
			aPositionInQuarters += getTimeSignature(barIndex).getLengthInQuarters();
			barIndex--;
		}
	}



	private int barsPositionForPositivePositionInQuarters(double aPositionInQuarters)
	{
		int barIndex = 0;
		while (true)
		{
			if (aPositionInQuarters - getTimeSignature(barIndex).getLengthInQuarters() < 0.0)
			{
				return barIndex;
			}
			aPositionInQuarters -= getTimeSignature(barIndex).getLengthInQuarters();
			barIndex++;
		}
	}



	public String getTimeSignaturesToString(BarsAndBeats lengthInBarsAndBeats)
	{		
		Iterator<TimeSignature> it = iterator(lengthInBarsAndBeats);
		StringBuilder sb = new StringBuilder();
		while (it.hasNext())
		{
			sb.append(it.next().toString() + "__");
		}
		return sb.toString();
	}
	


	public Iterator<TimeSignature> iterator(BarsAndBeats lengthInBarsAndBeats)
	{
		ArrayList<TimeSignature> list = new ArrayList<TimeSignature>();
		
		for (int i = 0; i < getLengthInBars(lengthInBarsAndBeats); i++)
		{
			list.add(getTimeSignature(i));
		}
		return list.iterator();
	}



	private int getLengthInBars(BarsAndBeats aBarsAndBeats)	// includes full bar to accomodate extra beats not equalling a full bar
	{
		double lengthInQuarters = getPositionInQuarters(aBarsAndBeats);
		BarsAndBeats bab = getPositionInBarsAndBeats(lengthInQuarters);
		if (bab.getOffsetInQuarters() > 0.0) return bab.getBarPosition() + 1;
		return bab.getBarPosition();
	}



	public Double getQuartersPositionInFloatBars(double aPositionInQuarters)
	{
		BarsAndBeats bab = getPositionInBarsAndBeats(aPositionInQuarters);
		double offset = bab.getOffsetInQuarters() / getTimeSignature(bab.getBarPosition()).getLengthInQuarters();
		return bab.getBarPosition() + offset;
	}



	public double getBarsAndBeatsPositionInFloatBars(BarsAndBeats aBarsAndBeats)
	{
		double positionInQuarters = getPositionInQuarters(aBarsAndBeats);
		return getQuartersPositionInFloatBars(positionInQuarters);
	}



	public double getFloatBarsPositionInQuarters(double aPositionInFloatBars)
	{
		int barPosition = (int)aPositionInFloatBars;
		double positionInQuarters = getPositionInQuarters(barPosition);
		TimeSignature ts = getTimeSignature(barPosition);
		return positionInQuarters + ts.getLengthInQuarters() * (aPositionInFloatBars - barPosition);
	}

	
	
//	public double getFloatBarsPositionInQuarters(BigDecimal aPositionInFloatBars)
//	{
//		int barPosition = aPositionInFloatBars.intValueExact();
//		double positionInQuarters = getPositionInQuarters(barPosition);
//		TimeSignature ts = getTimeSignature(barPosition);
//		BigDecimal remainder = aPositionInFloatBars.remainder(new BigDecimal(1.0));
//		return positionInQuarters + ts.getLengthInQuarters() * remainder.doubleValue();
//	}


	public void clearTimeSignatureMap()
	{
		tsMap.clear();
	}



	public double getClosestTactusInQuarters(double aPositionInFloatBars)
	{
		double quartersPosition = getFloatBarsPositionInQuarters(aPositionInFloatBars);
		int barIndex = getBarPosition(quartersPosition);		
		double barPositionInQuarters = getFloatBarsPositionInQuarters(barIndex);
		double positionInBarInQuarters = quartersPosition - barPositionInQuarters;
		TimeSignature ts = getTimeSignature(barIndex);
		ArrayList<Double> tactii = getTactiiIncludingEndOfBarPosition(ts);
		double tactus = calculateClosetTactus(positionInBarInQuarters, ts, tactii);
		return barPositionInQuarters + tactus;
	}



	private double calculateClosetTactus(double positionInBarInQuarters, TimeSignature ts, ArrayList<Double> tactii)
	{
		double distance = ts.getLengthInQuarters();
		double tactus = -1.0;
		for (Double tac: tactii)
		{
			double tempDist = Math.abs(positionInBarInQuarters - tac);
			if (tempDist < distance)
			{
				distance = tempDist;
				tactus = tac;
			}
		}
		return tactus;
	}



	private ArrayList<Double> getTactiiIncludingEndOfBarPosition(TimeSignature ts)
	{
		Double[] tactii = ts.getTactusAsQuartersPositions();
		ArrayList<Double> newTactii = new ArrayList<Double>();
		for (Double d: tactii) newTactii.add(d);
		newTactii.add(ts.getLengthInQuarters());
		return newTactii;
	}
	
	
	
	private ArrayList<Double> getSubTactiiIncludingEndOfBarPosition(TimeSignature ts)
	{
		Double[] subTactii = ts.getSubTactusAsQuartersPositions();
		ArrayList<Double> newTactii = new ArrayList<Double>();
		for (Double d: subTactii) newTactii.add(d);
		newTactii.add(ts.getLengthInQuarters());
		return newTactii;
	}



	public double getClosestSubTactusInQuarters(double aPositionInFloatBars)
	{
		double quartersPosition = getFloatBarsPositionInQuarters(aPositionInFloatBars);
		int barIndex = getBarPosition(quartersPosition);		
		double barPositionInQuarters = getFloatBarsPositionInQuarters(barIndex);
		double positionInBarInQuarters = quartersPosition - barPositionInQuarters;
		TimeSignature ts = getTimeSignature(barIndex);
		ArrayList<Double> tactii = getSubTactiiIncludingEndOfBarPosition(ts);
		double tactus = calculateClosetTactus(positionInBarInQuarters, ts, tactii);
		return barPositionInQuarters + tactus;
	}



	public Object[] getParameterObjectArray()
	{
		Object[] arr = new Object[] 
				{
				"TimeSignatureGenAndMap", 
				isUsingDefaultTimeSignatureGenerator,
				getObjectListAsString(timeSignatureList.getParameterObjectArray(), ";"),
				getObjectListAsString(timeSignatureGenerator.getParameterObjectArray(), ";"),
				tsMapToString()
		};
		return arr;
	}



	private Object tsMapToString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("tsMap:");
		for (int key: tsMap.keySet())
		{
			Object[] arr = tsMap.get(key).getParameterObjectArray();
			if (arr.length == 1)
			{
				sb.append(key + "=" + arr[0]);
			}
			else
			{
				StringBuilder sb1 = new StringBuilder();
				sb1.append(key + "=");
				for (int i = 0; i < arr.length - 1; i++)
				{
					sb1.append(arr[i] + "/");
				}
				sb1.append(arr[arr.length - 1]);
				sb.append(sb1.toString());
			}
		}
		return sb.toString();
	}



	private String getObjectListAsString(Object[] aArray, String aSeparator)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < aArray.length - 1; i++)
		{
			sb.append(aArray[i] + aSeparator);
		}
		sb.append(aArray[aArray.length - 1]);
		return sb.toString();
	}



	public Element getXMLElement(Document document)
	{
		Element ts_gen_and_map = document.createElement("ts_gen_and_map");
		ts_gen_and_map.appendChild(timeSignatureGenerator.getXMLElement(document));
		for (Integer key: tsMap.keySet())
		{
			Element map_item = document.createElement("map_item");
			
			Element bar_index = document.createElement("bar_index");
			bar_index.appendChild(document.createTextNode("" + key));
			map_item.appendChild(bar_index);
			
			map_item.appendChild(tsMap.get(key).getXMLElement(document));
			ts_gen_and_map.appendChild(map_item);
		}
		return ts_gen_and_map;
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
