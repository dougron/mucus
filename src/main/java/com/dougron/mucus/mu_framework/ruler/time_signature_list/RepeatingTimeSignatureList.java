package main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list;



import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;


public class RepeatingTimeSignatureList implements TimeSignatureListGenerator
{

	
	private TimeSignature[] timeSignatures;

	
	
	public RepeatingTimeSignatureList(TimeSignature[] someTimeSignatures)
	{
		timeSignatures = someTimeSignatures;
	}

	
	
	@Override
	public TimeSignatureList getTimeSignatureList(int aBarCount)
	{
		TimeSignatureList tsl = new TimeSignatureList();
		for (int i = 0; i < aBarCount; i++) tsl.add(timeSignatures[i % timeSignatures.length]);
		return tsl;
	}



	@Override
	public TimeSignatureList getTimeSignatureList(BarsAndBeats bab)
	{
		TimeSignatureList tsl = new TimeSignatureList();
		int index = 0;
		for (int i = 0; i < bab.getBarPosition(); i++) 
		{
			tsl.add(timeSignatures[index % timeSignatures.length]);
			index++;
		}
		double d = bab.getOffsetInQuarters();
		while (d > 0.0)
		{
			TimeSignature ts = timeSignatures[index % timeSignatures.length];
			tsl.add(ts);
			d -= ts.getLengthInQuarters();
			index++;
		}
		
		
		return tsl;
	}



	@Override
	public double getLengthOfBarCount(int barCount)
	{
		double count = 0.0;
		for (int i = 0; i < barCount; i++)
		{
			count += timeSignatures[i % timeSignatures.length].getLengthInQuarters();
		}
		return count;
	}
	
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("RepeatingTimeSignatureList:");
		for (TimeSignature ts: timeSignatures)
		{
			sb.append(ts.getName() + "__");
		}
		return sb.toString();
	}



	@Override
	public int getTimeSignatureCount()
	{
		return timeSignatures.length;
	}



	@Override
	public Object[] getParameterObjectArray()
	{
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("RepeatingTimeSignatureList");
		for (TimeSignature ts: timeSignatures)
		{
			Object[] arr = ts.getParameterObjectArray();
			if (arr.length == 1)
			{
				list.add(arr[0]);
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < arr.length - 1; i++)
				{
					sb.append(arr[i] + "/");
				}
				sb.append(arr[arr.length - 1]);
				list.add(sb.toString());
			}
		}
		return list.toArray(new Object[list.size()]);
	}
	
	
	
	@Override
	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("time_signature_generator");
		element.setAttribute("type", "RepeatingTimeSignatureList");
		
		for (TimeSignature ts: timeSignatures)
		{
			element.appendChild(ts.getXMLElement(document));
		}
		
		
		return element;
	}

	
}
