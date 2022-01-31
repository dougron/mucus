package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_MeasureItemInterface;
import main.java.da_utils.combo_variables.IntAndString;
import main.java.da_utils.static_chord_scale_dictionary.CSD.XMLChordElement;

public class MeasureItem_ChordSymbol implements MXML_MeasureItemInterface
{

	
	private String name;
	private ChordEvent chordEvent;
	private int xmlKey;

	
	public MeasureItem_ChordSymbol(ChordEvent ce, int aXmlKey)
	{
		name = ce.getChordName();
		chordEvent = ce;
		xmlKey = aXmlKey;
	}

	
	
	@Override
	public double getOffset()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public double getLength()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	@Override
	public Element[] getElement(Document document)
	{
		Element element = document.createElement("harmony");
		element.setAttribute("print-frame", "no");
		
		addRootElement(document, element);		
		addKindAndDegreeElements(document, element);
		
		return new Element[] {element};
	}



	private void addKindAndDegreeElements(Document document, Element element)
	{
		XMLChordElement[] xmlChordElements = chordEvent.getChord().getXMLChordElements();
		for (XMLChordElement x: xmlChordElements)
		{
			switch (x)
			{
			case MAJOR:	addKindElement(document, element, "major", ""); 	break;
			case MINOR:	addKindElement(document, element, "minor", "m"); 	break;
			case DIMINISHED:	addKindElement(document, element, "diminished", "dim"); 	break;
			case AUGMENTED:	addKindElementWithUseSymbols(document, element, "augmented"); 	break;
			case SUS_4:	addKindElement(document, element, "suspended-fourth", "sus4"); 	break;
			case SUS_2:	addKindElement(document, element, "suspended-second", "sus2"); 	break;
			case DOMINANT:	addKindElement(document, element, "dominant", "7"); 	break;
			case MAJOR_7:	addKindElement(document, element, "major-seventh", "Maj7"); 	break;
			case MINOR_7:	addKindElement(document, element, "minor-seventh", "m7"); 	break;
			case MAJOR_6:	addKindElement(document, element, "major-sixth", "6"); 	break;
			case MINOR_6:	addKindElement(document, element, "minor-sixth", "m6"); 	break;
			case MAJOR_MINOR:	addKindElement(document, element, "major-minor", "mMaj7"); 	break;
			case HALF_DIMINISHED:	addKindElement(document, element, "half-diminished", "m7b5"); 	break;
			case DIMINISHED_7:	addKindElement(document, element, "diminished-seventh", "dim7"); 	break;
			case AUGMENTED_5:	addDegreeElement(document, element, 5, 1, "alter");	break;
			case DIMINISHED_5:	addDegreeElement(document, element, 5, -1, "alter");	break;
			case ADD_7:	addDegreeElement(document, element, 7, 0, "add");	break;
			case NINE:	addDegreeElement(document, element, 9, 0, "add");	break;
			case FLAT_NINE:	addDegreeElement(document, element, 9, -1, "add");	break;
			case SHARP_NINE:	addDegreeElement(document, element, 9, 1, "add");	break;
			case ELEVEN:	addDegreeElement(document, element, 11, 0, "add");	break;
			case SHARP_ELEVEN:	addDegreeElement(document, element, 11, 1, "add");	break;
			case THIRTEEN:	addDegreeElement(document, element, 13, 0, "add");	break;
			case FLAT_THIRTEEN:	addDegreeElement(document, element, 13, -1, "add");	break;
			
			}
		}
	}



	private void addRootElement(Document document, Element element)
	{
		IntAndString rootData = chordEvent.getChord().getRootNameAndAlteration(xmlKey);
		
		Element root = document.createElement("root");
		Element root_step = document.createElement("root-step");
		root_step.appendChild(document.createTextNode(rootData.str));
		root.appendChild(root_step);
		if (rootData.i != 0)
		{
			Element root_alter = document.createElement("root-alter");
			root_alter.appendChild(document.createTextNode("" + rootData.i));
			root.appendChild(root_alter);
		}
		element.appendChild(root);
	}

	
	
	private void addDegreeElement(Document document, Element element, int degreeValue, int degreeAlteration, String degreeType)
	{
		Element degree = document.createElement("degree");
		Element degree_value = document.createElement("degree-value");
		degree_value.appendChild(document.createTextNode("" + degreeValue));
		Element degree_alter = document.createElement("degree-alter");
		degree_alter.appendChild(document.createTextNode("" + degreeAlteration));
		Element degree_type = document.createElement("degree-type");
		degree_type.appendChild(document.createTextNode(degreeType));
		degree.appendChild(degree_value);
		degree.appendChild(degree_alter);
		degree.appendChild(degree_type);
		element.appendChild(degree);
	}



	private void addKindElementWithUseSymbols(Document document, Element element, String kindText)
	{
		Element kind = document.createElement("kind");
		kind.appendChild(document.createTextNode(kindText));
		kind.setAttribute("use-symbols", "yes");
		element.appendChild(kind);
	}



	private void addKindElement(Document document, Element element, String kindText, String textAttribute)
	{
		Element kind = document.createElement("kind");
		kind.appendChild(document.createTextNode(kindText));
		if (textAttribute.length() > 0)
		{
			kind.setAttribute("text", textAttribute);
		}
		element.appendChild(kind);
	}



	@Override
	public int getType()
	{
		
		return MXML_Measure.IS_NOTE;
	}

	
	
	@Override
	public String getTestOutput()
	{
		String str = "chord symbol=" + name; 
		str += "\n";
		return str;
	}

}
