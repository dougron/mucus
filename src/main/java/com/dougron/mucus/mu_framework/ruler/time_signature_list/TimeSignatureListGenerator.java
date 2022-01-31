package main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;


public interface TimeSignatureListGenerator
{

	TimeSignatureList getTimeSignatureList(int aBarCount);

	TimeSignatureList getTimeSignatureList(BarsAndBeats bab);

	double getLengthOfBarCount(int barCount);

	int getTimeSignatureCount();
	
	Object[] getParameterObjectArray();

	Element getXMLElement(Document document);

	static TimeSignatureListGenerator getTimeSignatureGeneratorFromXMLElement(Element element)
	{
		String type = element.getAttribute("type");
		Element time_signature;
		switch (type)
		{
		case "SingleTimeSignature":
			time_signature = (Element)element.getElementsByTagName("time_signature").item(0);
			TimeSignature ts = TimeSignature.getTimeSignatureFromXMLElement(time_signature);
			return new SingleTimeSignature(ts);
		
		case "RepeatingTimeSignatureList":			
			NodeList nList = element.getElementsByTagName("time_signature");
			TimeSignature[] tsArr = new TimeSignature[nList.getLength()];
			for (int i = 0; i < nList.getLength(); i++)
			{
				time_signature = (Element)nList.item(i);
				tsArr[i] = TimeSignature.getTimeSignatureFromXMLElement(time_signature);
			}
			return new RepeatingTimeSignatureList(tsArr);
		}
		return null;
	}

}
