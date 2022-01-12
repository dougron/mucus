package main.java.com.dougron.mucus.mu_framework.ruler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.tempo_list.TempoChange;
import main.java.com.dougron.mucus.mu_framework.ruler.tempo_list.TempoList;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureList;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGenerator;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

public class TimeSignatureListAndGenAndTempoListRuler implements Ruler
{

	private BarsAndBeats lengthInBarsAndBeats;
	private TimeSignatureList timeSignatureList;
	private TimeSignatureListGenerator timeSignatureListGenerator = TimeSignatureListGeneratorFactory.getGenerator();
	private TimeSignatureGenAndMap tsGenAndMap = new TimeSignatureGenAndMap();
	private TempoList tempoList;
	private boolean hasTimeSignature = false;
	private boolean hasTempo = false;
//	private TimeSignature defaultTimeSignature = TimeSignature.FOUR_FOUR;
	
	
	public Object[] getParameterObjectArray()
	{
		ArrayList<Object> list = new ArrayList<Object>();
		if (hasTimeSignature)
		{
			list.add("lengthInBarsAndBeats:" + lengthInBarsAndBeats.getBarPosition() + "," + lengthInBarsAndBeats.getOffsetInQuarters());
			list.add(getParameterString(timeSignatureListGenerator.getParameterObjectArray(), ","));
			list.add(getParameterString(tsGenAndMap.getParameterObjectArray(), ","));
		}
		if (timeSignatureList != null) list.add(getParameterString(timeSignatureList.getParameterObjectArray(), ",")); 
		if (hasTempo) list.add(getParameterString(tempoList.getParameterObjectArray(), ","));
		return list.toArray(new Object[list.size()]);
	}
	

	
	private Object getParameterString(Object[] aArray, String aSeparator)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < aArray.length - 1; i++)
		{
			sb.append(aArray[i] + aSeparator);
		}
		sb.append(aArray[aArray.length - 1]);
		return sb.toString();
	}



	public TimeSignatureListAndGenAndTempoListRuler()
	{
		setLengthInBarsAndBeats(new BarsAndBeats(0, 0.0));		
		tempoList = new TempoList();
	}

	
	
	@Override
	public BarsAndBeats getLengthInBarsAndBeats()
	{
		return lengthInBarsAndBeats;
	}

	
	
	@Override
	public double getLengthInQuarters()
	{
		return tsGenAndMap.getPositionInQuarters(lengthInBarsAndBeats);
	}
	
	
	
	@Override
	public double getLengthInSeconds()
	{
		double seconds = 0.0;
		double lengthInQuarters = getLengthInQuarters();		// total quarters to measure in seconds. Decremented in the loop until everything has been counted
		Iterator<TempoChange> tpit = tempoList.iterator();
		TempoChange previousTC = tpit.next();
		double quarterInSeconds = 60.0 / previousTC.getTempo();;
		double lengthAtTempo = lengthInQuarters;
		while (tpit.hasNext())
		{
			TempoChange tc = tpit.next();			
			lengthAtTempo = tc.getPositionInQuarters(tsGenAndMap) - previousTC.getPositionInQuarters(tsGenAndMap);
			if (lengthAtTempo > lengthInQuarters) lengthAtTempo = lengthInQuarters;
			seconds += lengthAtTempo * quarterInSeconds;
			
			lengthInQuarters -= lengthAtTempo;
			previousTC = tc;
			quarterInSeconds = 60.0 / tc.getTempo();
		}
		seconds += lengthAtTempo * quarterInSeconds;
		return seconds;
	}
	
	
	
	@Override
	public double getTempoAtBarsAndBeatsPosition(int aBarPosition, double aOffsetInQuarters)
	{
		return tempoList.getTempoAt(aBarPosition, aOffsetInQuarters, tsGenAndMap);
//		return 0.0;
	}
	
	
	
	@Override
	public double getTempoAtQuartersPosition(double aQuartersPosition)
	{
		return tempoList.getTempoAt(aQuartersPosition, tsGenAndMap);
	}
	
	
	
	@Override
	public double getTempoAtSecondsPosition(double aSecondsPosition)
	{
		return tempoList.getTempoAt(getSecondsPositionInQuarters(aSecondsPosition), tsGenAndMap);
	}

	
	
	private double getSecondsPositionInQuarters(double aSecondsPosition)
	{
		return 0.0;
	}



	@Override
	public String getTimeSignatureToString()
	{
		return tsGenAndMap.getTimeSignaturesToString(lengthInBarsAndBeats);
	}


	
	@Override
	public void setLengthInBarsAndBeats(BarsAndBeats aBarsAndBeats)
	{
		lengthInBarsAndBeats = aBarsAndBeats;
//		timeSignatureList = timeSignatureListGenerator.getTimeSignatureList(lengthInBarsAndBeats);
	}

	
	
	@Override
	public void setStartTempo(double aTempo)
	{
		tempoList.setStartTempo(aTempo);	
		hasTempo = true;
	}

	
	
	@Override
	public void addTempoChange(double aTempo, BarsAndBeats aBarsAndBeats)
	{
//		double positionInQuarters = tsGenAndMap.getPositionInQuarters(aBarsAndBeats);
//		tempoList.addTempoChange(aTempo,  positionInQuarters, timeSignatureList);	
//		hasTempo = true;
	}
	
	
	
	@Override
	public void addTempoChange(double aTempo, double aPositionInFloatBars)
	{
		tempoList.addTempoChangeAtFloatBarsPosition(aTempo, aPositionInFloatBars);
		hasTempo = true;
	}
	
	
	
	@Override
	public void addTempoChange(BarsAndBeats aBarsAndBeats, Ruler aRuler)
	{
		Iterator<TempoChange> tcit = aRuler.getTempoChangeIterator();
		while(tcit.hasNext())
		{
//			TempoChange tc = tcit.next();
//			double position = tc.getPositionInQuarters(tsGenAndMap);
//			double startPos = tsGenAndMap.getPositionInQuarters(aBarsAndBeats);
//			double newPosition = startPos + position;
//			BarsAndBeats bab = tsGenAndMap.getPositionInBarsAndBeats(newPosition);
//			tempoList.addTempoChange(tc.getTempo(), bab, timeSignatureList);
		}
//		hasTempo = true;
	}



	@Override
	public void setTimeSignatureGenerator(TimeSignatureListGenerator aGenerator)
	{
		timeSignatureListGenerator = aGenerator;
//		timeSignatureList = aGenerator.getTimeSignatureList(lengthInBarsAndBeats);
		tsGenAndMap.setTimeSignatureGenerator(aGenerator);
		hasTimeSignature = true;
	}

	

	@Override
	public void replaceTimeSignature(int aBarPosition, TimeSignature aTimeSignature)
	{
//		timeSignatureList.replaceInTimeSignatureList(aTimeSignature, aBarPosition);		
		tsGenAndMap.addTimeSignature(aBarPosition, aTimeSignature);
	}


	
	@Override
	public void replaceTimeSignature(BarsAndBeats aBarsAndBeats, Ruler aRuler)
	{
		Iterator<TimeSignature> tsit = aRuler.getTimeSignatureIterator();
		int barIndex = aBarsAndBeats.getBarPosition();
		while (tsit.hasNext())
		{
			replaceTimeSignature(barIndex, tsit.next());
			barIndex++;
		}
	}

	

	@Override
	public Iterator<TimeSignature> getTimeSignatureIterator()
	{
		return tsGenAndMap.iterator(lengthInBarsAndBeats);
	}

	

	@Override
	public Iterator<TempoChange> getTempoChangeIterator()
	{
		return tempoList.iterator();
	}

	

	@Override
	public boolean hasTimeSignature()
	{
		return hasTimeSignature ;
	}
 
	

	@Override
	public boolean hasTempo()
	{
		return hasTempo ;
	}


	
	@Override
	public TimeSignatureList getTimeSignatureList()
	{
		TimeSignatureList tsl = new TimeSignatureList();
		for (int i = 0; i < getBarIndexAfterEnd(); i++)
		{
			tsl.add(getTimeSignature(i));
		}		
		return tsl;
	}



	private int getBarIndexAfterEnd()
	{
		BarsAndBeats bab = getLengthInBarsAndBeats();
		if (bab.getOffsetInQuarters() > 0.0) return bab.getBarPosition() + 1;
		return bab.getBarPosition();
	}


	
	@Override
	public void setHasTimeSignature(boolean b)
	{
		hasTimeSignature = b;		
	}

 
	
	@Override
	public TimeSignature getTimeSignature(int aBarIndex)
	{
//		return timeSignatureList.getTimeSignature(aBarIndex);
		return tsGenAndMap.getTimeSignature(aBarIndex);
	}


	// depreciated functionality. can probably be removed
	@Override
	public void setDefaultTimeSignature(TimeSignature aTimeSignature)
	{
	}
	
	
	
	public String toString()
	{
//		StringBuilder sb = new StringBuilder();
//		if (hasTimeSignature) sb.append(" " + tsGenAndMap.getTimeSignaturesToString(lengthInBarsAndBeats));
//		if (hasTempo) sb.append(" has tempo");		
//		return sb.toString();
		return contentString();
	}

	

	@Override
	public double getPositionInQuarters(double aOffsetInQuarters)
	{
		return 0.0;		
	}



	@Override
	public BarsAndBeats getPositionInBarsAndBeats(double aPositionInQuarters)
	{
		return tsGenAndMap.getPositionInBarsAndBeats(aPositionInQuarters);
	}



	@Override
	public TimeSignatureGenAndMap getTSGenAndMap()
	{
		return tsGenAndMap;
	}



	@Override
	public void clearTimeSignatureMap()
	{
		tsGenAndMap.clearTimeSignatureMap();
		
	}



	@Override
	public double getClosestTactusInQuarters(double aPositionInFloatBars)
	{
		return tsGenAndMap.getClosestTactusInQuarters(aPositionInFloatBars);
	}



	public void setTempo(double aTempo, int aPositionInBars)
	{
		tempoList.addTempoChangeAtBarsAndBeatsPosition(aTempo, new BarsAndBeats(aPositionInBars, 0.0), tsGenAndMap);
		
	}


	
	public TimeSignatureListAndGenAndTempoListRuler getDeepCopy()
	{
		TimeSignatureListAndGenAndTempoListRuler ruler = new TimeSignatureListAndGenAndTempoListRuler();
		ruler.setLengthInBarsAndBeats(lengthInBarsAndBeats);
		ruler.setTimeSignatureGenerator(timeSignatureListGenerator);
		ruler.tsGenAndMap = tsGenAndMap.getDeepCopy();
		ruler.hasTempo = hasTempo;
		ruler.hasTimeSignature = hasTimeSignature;
		return ruler;
	}
	
	
	
	public String contentString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("lengthInBarsAndBeats=" + lengthInBarsAndBeats.toString() + " \n");
		sb.append("timeSignatureList:");
		if (timeSignatureList == null)
		{
			sb.append("null\n");
		}
		else
		{
			sb.append("\n" + timeSignatureList.toString() + "\n");
		}
		sb.append("timeSignatureListGenerator:\n" + timeSignatureListGenerator.toString() + "\n");
		sb.append("tsGenAndMap:\n" + tsGenAndMap.contentString() + "\n");
		sb.append("tempoList:\n" + tempoList.toString());
		sb.append("hasTimeSignature=" + hasTimeSignature + "\n");
		sb.append("hasTempo=" + hasTempo + "\n");
		return sb.toString();
	}



	@Override
	public Element getXMLElement(Document document)
	{
		Element ruler_element = document.createElement("ruler");
		if (hasTimeSignature)ruler_element.appendChild(tsGenAndMap.getXMLElement(document));
		if (hasTempo) ruler_element.appendChild(tempoList.getXMLElement(document));
		return ruler_element;
	}



	@Override
	public Stream<TempoChange> getTempoStream ()
	{
		return tempoList.stream();
	}



	


	


	


	


	


	

	

}
