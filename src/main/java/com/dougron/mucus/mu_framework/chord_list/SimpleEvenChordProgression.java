package main.java.com.dougron.mucus.mu_framework.chord_list;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;


/**
 * generates a chordlist of one chord per bar from an input String array
 * 
 * @author dougr
 *
 */

public class SimpleEvenChordProgression implements ChordListGenerator
{
	
	
	
	private List<Chord> chordList;

	public SimpleEvenChordProgression(String[] progression)
	{
		chordList = new ArrayList<Chord>();
		for (String str: progression)
		{
			Chord chord = new Chord(str);
			chordList.add(chord);
		}
	}
	
	
	
	@Override
	public String chordsToString ()
	{
		StringBuilder sb = new StringBuilder();
		for (Chord chord: chordList)
		{
			sb.append(chord.name() + "_");
		}
		return sb.toString();
	}

	
	
	@Override
	public ChordList getChordList()
	{
		return null;
	}

	
	
	@Override
	public ChordList getChordList(TimeSignatureGenAndMap tsgm, int barCount)
	{
		ChordList cl = new ChordList();
		cl.setLengthInBarsAndBeats(new BarsAndBeats(barCount, 0.0));
		int index = 0;
		for (int i = 0; i < barCount; i++)
		{
			cl.addChord(chordList.get(index), new BarsAndBeats(i, 0.0), tsgm);
			index++;
			if (index >= chordList.size()) index = 0;
		}
		return cl;
	}



	@Override
	public Object[] getParameterObjectArray()
	{
		Object[] objArr = new Object[chordList.size() + 1];
		objArr[0] = "SimpleEvenChordProgression";
		for (int i = 0; i < chordList.size(); i++)
		{
			objArr[i + 1] = chordList.get(i).name();
		}
		return objArr;
	}
	
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SimpleEvenChordProgression: ");
		for (Chord chord: chordList)
		{
			sb.append(chord.name() + "_");
		}
		return sb.toString();
	}
	
	
	
	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("chord_list_generator");
		element.setAttribute("type", "SimpleEvenChordProgression");
		
		for (Chord chord: chordList)
		{
			Element chord_element = document.createElement("chord");
			chord_element.appendChild(document.createTextNode(chord.name()));
			element.appendChild(chord_element);
		}
		return element;
	}

}
