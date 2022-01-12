package main.java.com.dougron.mucus.mu_framework.chord_list;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;


public interface ChordListGenerator
{

	ChordList getChordList();

	ChordList getChordList(TimeSignatureGenAndMap tsgm, int barCount);

	Object[] getParameterObjectArray();

	Element getXMLFileContent(Document document);

	String chordsToString();
}
