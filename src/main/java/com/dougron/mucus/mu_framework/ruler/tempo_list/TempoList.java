package main.java.com.dougron.mucus.mu_framework.ruler.tempo_list;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;


public class TempoList
{

	private static final double DEFAULT_TEMPO = 120.0;
	private ArrayList<TempoChange> tempos = new ArrayList<TempoChange>();
			
		
	public TempoList ()
	{
		tempos.add(new TempoChange(DEFAULT_TEMPO, 0.0));
	}
			
	public double getStartTempo()
	{
		return tempos.get(0).getTempo();
	}

	
	
	public Object[] getParameterObjectArray()
	{
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("TempoList");
		for (TempoChange tc: tempos)
		{
			list.add(tc.getTempo() + "/" + tc.getPositionInFloatBars());
		}
		
		
		return list.toArray(new Object[list.size()]);
	}	
	
	
	
	public void setStartTempo(double aTempo)
	{
		tempos.get(0).setTempo(aTempo);
		
	}
	
	
	public TempoList getDeepCopy()
	{
		TempoList tl = new TempoList();
		tl.tempos.clear();
		for (TempoChange tc: tempos)
		{
			tl.addTempoChangeAtFloatBarsPosition(tc.getTempo(), tc.getPositionInFloatBars());
		}
		return tl;
	}
	
	
	
	public void addTempoChangeAtFloatBarsPosition(double aTempo, double aPositionInFloatBars)
	{
		try
		{
			if (aPositionInFloatBars < 0.0) throw new InvalidTempoChangePosition();
			tempos.add(new TempoChange(aTempo, aPositionInFloatBars));
			Collections.sort(tempos, positionInFloatBarsComparator); 		
		}
		catch (InvalidTempoChangePosition e) 
		{
			System.out.print(e.getMessage());
		}
		
	}
	
	
	
	public void addTempoChangeAtQuartersPosition(double aTempo, double aQuartersPosition, TimeSignatureGenAndMap tsgm)
	{
		addTempoChangeAtFloatBarsPosition(aTempo, tsgm.getQuartersPositionInFloatBars(aQuartersPosition));		
	}
	
	
	
	public void addTempoChangeAtBarsAndBeatsPosition(double aTempo, BarsAndBeats aBarsAndBeats, TimeSignatureGenAndMap tsgm)
	{
		addTempoChangeAtFloatBarsPosition(aTempo, tsgm.getBarsAndBeatsPositionInFloatBars(aBarsAndBeats));		
	}
	

			
	public static Comparator<TempoChange> positionInFloatBarsComparator = new Comparator<TempoChange>()
			{
				@Override
				public int compare(TempoChange o1, TempoChange o2)
				{
					if (o1.getPositionInFloatBars() < o2.getPositionInFloatBars()) return -1;
					if (o1.getPositionInFloatBars() > o2.getPositionInFloatBars()) return 1;
					return 0;
				}	
			};
			

	public double getTempoAt(double aQuartersPosition, TimeSignatureGenAndMap tsgm)
	{
		TempoChange previousTP = tempos.get(0);
		for (TempoChange tp: tempos)
		{
			if (aQuartersPosition < tp.getPositionInQuarters(tsgm)) 
			{
				return previousTP.getTempo();
			}
			previousTP = tp;
		}
		return previousTP.getTempo();
	}



	public double getTempoAt(int aBarPos, double aOffsetInQuarters, TimeSignatureGenAndMap tsgm)
	{
		TempoChange previousTP = tempos.get(0);
		for (TempoChange tp: tempos)
		{
			if (tp.getPositionInBarsAndBeats(tsgm).getPositionComparison(aBarPos, aOffsetInQuarters, tsgm) == 1) 
			{
				return previousTP.getTempo();
			}
			previousTP = tp;
		}
		return previousTP.getTempo();
	}



	public TempoChange getLastTempoChange()
	{
		if (tempos.size() > 0) return tempos.get(tempos.size() - 1);
		return null;
	}
	
	
	public Stream<TempoChange> stream()
	{
		return tempos.stream();
	}



//	public void redoTempoChangesKeepingBarAndBeatsPosition(TimeSignatureList tsl)
//	{
//		List<TempoChange> list = new ArrayList<TempoChange>();
//		for (TempoChange tc: tempos)
//		{
//			TempoChange newtc = new TempoChange(
//					tc.getTempo(), 
//					tsl.getBarsAndBeatsPositionInQuarters(tc.getPositionInBarsAndBeats()),
//					tc.getPositionInBarsAndBeats());
//			list.add(newtc);
//		}
//		tempos = list;
//	}
//
//
//
//	public void redoTempoChangesKeepingQuartersPosition(TimeSignatureList tsl)
//	{
//		List<TempoChange> list = new ArrayList<TempoChange>();
//		for (TempoChange tc: tempos)
//		{
//			TempoChange newtc = new TempoChange(
//					tc.getTempo(), 
//					tc.getPositionInQuarters(),
//					tsl.getQuartersPositionInBarsAndBeats(tc.getPositionInQuarters()));
//			list.add(newtc);
//		}
//		tempos = list;
//	}
	
	
	
	public Iterator<TempoChange> iterator()
	{
		return tempos.iterator();
	}



	public double getBarsAndBeatsPositionInSeconds(BarsAndBeats aBarsAndBeats, TimeSignatureGenAndMap tsgm)
	{
		double time = 0.0;
		double positionInQuarters = tsgm.getPositionInQuarters(aBarsAndBeats);
		TempoChange tc = tempos.get(0);
		double secondsDurationOfQuarter = tc.getSecondsDurationOfQuarter();
		double tempoChangePositionInQuarters = tsgm.getFloatBarsPositionInQuarters(tc.getPositionInFloatBars());
		for (int i = 1; i < tempos.size(); i++)
		{
			tc = tempos.get(i);
			double newPositionInQuarters = tsgm.getFloatBarsPositionInQuarters(tc.getPositionInFloatBars());
			double duration = newPositionInQuarters - tempoChangePositionInQuarters;
			if (duration > positionInQuarters) duration = positionInQuarters;
			time += duration * secondsDurationOfQuarter;
			secondsDurationOfQuarter = tc.getSecondsDurationOfQuarter();
			tempoChangePositionInQuarters = newPositionInQuarters;
			positionInQuarters -= duration;
		}
		time += positionInQuarters * secondsDurationOfQuarter;
		return time;
	}
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("TempoList");
		for (TempoChange tc: tempos)
		{
			sb.append("\n -" + tc.toString());
		}	
		return sb.toString() + "\n";
	}



	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("tempo_list");		
		for (TempoChange tc: tempos)
		{
			element.appendChild(tc.getXMLElement(document));
		}
		return element;
	}



	



	



	
}
