package main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;



public class SingleTimeSignature implements TimeSignatureListGenerator
{

	
	
	private TimeSignature timeSignature;

	
	
	public SingleTimeSignature(TimeSignature aTimeSignature)
	{
		timeSignature = aTimeSignature;
	}

	
	
	@Override
	public TimeSignatureList getTimeSignatureList(int aBarCount)
	{
		TimeSignatureList tsl = new TimeSignatureList();
		for (int i = 0; i < aBarCount; i++) tsl.add(timeSignature);
		return tsl;
	}



	@Override
	public TimeSignatureList getTimeSignatureList(BarsAndBeats bab)
	{
		TimeSignatureList tsl = new TimeSignatureList();
		for (int i = 0; i < bab.getBarPosition(); i++) 
		{
			tsl.add(timeSignature);
		}
		double d = bab.getOffsetInQuarters();
		while (d > 0.0)
		{
			tsl.add(timeSignature);
			d -= timeSignature.getLengthInQuarters();
		}		
		return tsl;
	}



	@Override
	public double getLengthOfBarCount(int barCount)
	{		
		return timeSignature.getLengthInQuarters() * barCount;
	}
	
	
	
	public String toString()
	{
		return "SingleTimeSignature:" + timeSignature.getName();
	}



	@Override
	public int getTimeSignatureCount()
	{
		return 1;
	}



	@Override
	public Object[] getParameterObjectArray()
	{		
		Object[] arr = timeSignature.getParameterObjectArray();
		if (arr.length == 1)
		{
			return new Object[] {"SingleTimeSignature", timeSignature.getName()};
		}
		else
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < arr.length - 1; i++)
			{
				sb.append(arr[i] + "/");
			}
			sb.append(arr[arr.length - 1]);
			return new Object[] {"SingleTimeSignature:", sb.toString()};
		}
	}



	@Override
	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("time_signature_generator");
		element.setAttribute("type", "SingleTimeSignature");
		
		element.appendChild(timeSignature.getXMLElement(document));
		
		return element;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
