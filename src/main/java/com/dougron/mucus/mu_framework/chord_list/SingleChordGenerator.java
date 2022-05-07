package main.java.com.dougron.mucus.mu_framework.chord_list;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;


public class SingleChordGenerator implements ChordListGenerator
{

	Chord chord = ChordList.DEFAULT_CHORD;

	

	public SingleChordGenerator()
	{}

	
	
	public SingleChordGenerator(Chord aChord)
	{
		chord = aChord;
	}
	
	
	
	@Override
	public String chordsToString ()
	{
		return chord.name();
	}



	@Override
	public ChordList getChordList()
	{
		return new ChordList();
	}

	
	
	@Override
	public ChordList getChordList(TimeSignatureGenAndMap tsgm, int barCount)
	{
		ChordList cl = new ChordList();
		cl.setLengthInBarsAndBeats(new BarsAndBeats(barCount, 0.0));
		for (int i = 0; i < barCount; i++)
		{
			cl.addChord(chord, new BarsAndBeats(i, 0.0), tsgm);
		}
		return cl;
	}
	
	
	public String toString()
	{
		return "SingleChordGenerator: " + chord.toString();
	}



	@Override
	public Object[] getParameterObjectArray()
	{
		return new Object[] {"SingleChordGenerator", chord.name()};
	}



	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("chord_list_generator");
		element.setAttribute("type", "SingleChordGenerator");
		
		Element chord_element = document.createElement("chord");
		chord_element.appendChild(document.createTextNode(chord.name()));
		element.appendChild(chord_element);
		return element;
	}


}
