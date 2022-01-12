package main.java.com.dougron.mucus.mu_framework.ruler.tempo_list;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;

/*
 * replacement for TempoPosition and its implementations
 * 
 * after farting around with all sorts of functionality, I am going to try have this thing immutable
 * - when things change, make new ones!!!
 */
public class TempoChange
{

//	private BarsAndBeats positionInBarsAndBeats;
//	private double positionInQuarters;
	private double positionInFloatBars;
	private double tempo;
	
	

//	public TempoChange(double aTempo, double aPositionInQuarters, BarsAndBeats aBarsAndBeats)
//	{
//		positionInQuarters = aPositionInQuarters;
//		positionInBarsAndBeats = aBarsAndBeats;
//		tempo = aTempo;
//	}
	
	
	public TempoChange(double aTempo, double aPositionInFloatBars)
	{
		positionInFloatBars = aPositionInFloatBars;
		tempo = aTempo;
	}
	
	
	
	public TempoChange getDeepCopy()
	{
		return new TempoChange(tempo, positionInFloatBars);
	}


	public double getTempo()
	{
		return tempo;
	}

	
	
	public double getPositionInQuarters(TimeSignatureGenAndMap tsgm)
	{
		int barIndex = (int)positionInFloatBars;
		double positionInQuarters = tsgm.getPositionInQuarters(barIndex);
		double offsetInQuarters = tsgm.getTimeSignature(barIndex).getLengthInQuarters() * (positionInFloatBars - barIndex);	
		return positionInQuarters + offsetInQuarters;
	}
	

	
	public BarsAndBeats getPositionInBarsAndBeats(TimeSignatureGenAndMap tsgm)
	{
		int barIndex = (int)positionInFloatBars;
		double offsetInQuarters = tsgm.getTimeSignature(barIndex).getLengthInQuarters() * (positionInFloatBars - barIndex);		
		return new BarsAndBeats(barIndex, offsetInQuarters);
	}
	
	
	
	public double getPositionInFloatBars()
	{
		return positionInFloatBars;
	}

	
	
	public void setTempo(double aTempo)
	{
		tempo = aTempo;
	}	
	
	
	public String toString()
	{
		return "tempo=" + tempo + " at " + positionInFloatBars + " floatBars";
				
	}


	public double getSecondsDurationOfQuarter()
	{
		return 60.0 / tempo;
	}



	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("tempo_change");
		
		Element tempo_element = document.createElement("tempo");
		tempo_element.appendChild(document.createTextNode("" + tempo));
		element.appendChild(tempo_element);
		
		Element position_element = document.createElement("float_bar_position");
		position_element.appendChild(document.createTextNode("" + positionInFloatBars));
		element.appendChild(position_element);
		
		return element;
	}


	
}
