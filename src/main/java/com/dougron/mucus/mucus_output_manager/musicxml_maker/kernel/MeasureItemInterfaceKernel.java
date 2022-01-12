package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import DataObjects.combo_variables.IntAndString;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_NoteDurationType;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Annotation;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Division;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_KeySignature;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Note;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Rest;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_TimeSignature;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_TupletNote;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_TupletRest;

public class MeasureItemInterfaceKernel {
	
	
	// ------------------------------------------------
	// MeaureItem_Note
	//-------------------------------------------------
	
	
	public static Element[] getElement(Document document, MeasureItem_Note min)
  	{
    	ArrayList<Element> list = new ArrayList<Element>();
    	addAnnotationElementsToList(document, min, list);
     	
    	addNoteElementsToList(document, min, list);  
    	
	  	return list.toArray(new Element[list.size()]);
  	}



	/**
	 * @param document
	 * @param min
	 * @param list
	 */
	private static void addAnnotationElementsToList(Document document, MeasureItem_Note min, ArrayList<Element> list) {
		if (!min.getHasTieEnd()) 
		{
			for (MeasureItem_Annotation mia : min.getMeasureItemAnnotations()) {
				for (Element element : mia.getElement(document)) {
					list.add(element);
				}
			} 
		}
	}



	/**
	 * @param document
	 * @param min
	 * @param list
	 */
	private static void addNoteElementsToList(Document document, MeasureItem_Note min, ArrayList<Element> list) {
		boolean isChord = false;
		boolean isFirstNote = true;
		for (Integer note: min.getNotes()) 
		{
			Element element = document.createElement("note");
			if (isChord) 
			{
				element.appendChild(document.createElement("chord"));
			}
			element.appendChild(makePitchChild(document, note, min));	
			element.appendChild(makeDurationChild(document, min));
			if (min.getHasTieEnd()) element.appendChild(makeChildWithAttribute(document, "tie", "type", "stop"));
			if (min.getHasTieStart()) element.appendChild(makeChildWithAttribute(document, "tie", "type", "start"));
			element.appendChild(makeVoiceChild(document, min));
			for (Element el: makeTypeChild(document, min)) 
			{
				element.appendChild(el);
			}
			
			if (min instanceof MeasureItem_TupletNote) 
			{
				element.appendChild(makeTupletTimeModification(document, ((MeasureItem_TupletNote)min)
						.getTimeModActual(), ((MeasureItem_TupletNote)min)
						.getTimeModNormal()));
			}
			// still add accidental......

			
			if (hasNotations(min, isFirstNote)) element.appendChild(makeNotationsChild(document, min));	
			
			
			list.add(element);
			isChord = true;
			isFirstNote = false;
		}
	}



	private static Element makeChildWithAttribute(Document document, String name, String attributeName, String attributeValue)
  	{
  		Element element = document.createElement(name);
  		element.setAttribute(attributeName, attributeValue);
  		return element;
  	}
  	
  	
  	
  	private static Element makeNotationsChild(Document document, MeasureItem_Note min)
  	{
  		Element notations = document.createElement("notations");
  		if (min.getHasTieEnd())
  		{
  			notations.appendChild(makeChildWithAttribute(document, "ties", "type", "stop"));
  		}
  		if (min.getHasTieStart())
  		{
  			notations.appendChild(makeChildWithAttribute(document, "tied", "type", "start"));
  		}
  		if (min instanceof MeasureItem_TupletNote && ((MeasureItem_TupletNote)min).getHasStartOfTupletNotationElement())
  		{
  			Element zzzz = makeChildWithAttribute(document, "tuplet", "type", "start");
  			String str = "no";
  			if (((MeasureItem_TupletNote)min).isHasTupletBracket()) str = "yes";
  			zzzz.setAttribute("bracket", str);
  			zzzz.setAttribute("number", "1");
  			notations.appendChild(zzzz);
  		}
  		if (min instanceof MeasureItem_TupletNote 
  				&& ((MeasureItem_TupletNote)min).hasEndOfTupletNotationElement()
  				&& ((MeasureItem_TupletNote)min).getHasTupletBracket()
  				)
  		{
  			Element zzzz = makeChildWithAttribute(document, "tuplet", "type", "stop");
  			String str = "no";
  			if (((MeasureItem_TupletNote)min).isHasTupletBracket()) str = "yes";
  			zzzz.setAttribute("bracket", str);
  			zzzz.setAttribute("number", "1");
  			
  			notations.appendChild(zzzz);
  		}
  		return notations;
  	}
  	
  	
  	
  	/* all possibilities for notations must be tested here. Currently catered for:
  	 * - start and end tie 
  	 * - very first note of a tuplet (first note or first note of first chord) start-of-tuplet notation
  	 * - hasEndOfTupletNotation so that a tuplet bracket can be completed
*/
  	
  	private static boolean hasNotations(MeasureItem_Note min, boolean isFirstNote)
  	{
  		return (min.getHasTieStart() 
  				|| min.getHasTieEnd() 
  				|| (min instanceof MeasureItem_TupletNote 
  						&& isFirstNote 
  						&& ((MeasureItem_TupletNote)min).getHasStartOfTupletNotationElement()
  						)
  				|| (min instanceof MeasureItem_TupletNote  
  						&& ((MeasureItem_TupletNote)min).hasEndOfTupletNotationElement()
  						)	
  				);
  	}



   	private static Element[] makeTypeChild(Document document, MeasureItem_Note min)
   	{
    	Element type = document.createElement("type");
//    	System.out.println("MeasureItemInterfaceKernel_152 " + min.getOffset() + " " + min.getLength() + " " + min.getLengthForTypeIndex());
		MXML_NoteDurationType na = MXML.xmlDurationNoteType.get(min.getLengthForTypeIndex());
		type.appendChild(document.createTextNode(na.getXmlType()));
		if (na.getDotCount() > 0) 
		{
			return new Element[] {type, document.createElement("dot")};
		} else 
		{
			return new Element[] {type};
		}
  	}



  	private static Element makeVoiceChild(Document document, MeasureItem_Note min) 
  	{
	  	Element voice = document.createElement("voice");
	  	voice.appendChild(document.createTextNode("" + min.getVoice()));
		return voice;
	}
	
	

  	private static Element makeDurationChild(Document document, MeasureItem_Note min) 
  	{
	  	Element duration = document.createElement("duration");
	  	int x = (int)Math.round(min.getLength() * min.getMeasure().getDivisions());
	  	duration.appendChild(document.createTextNode("" + x));
		return duration;
	}
	
	

  	private static Element makePitchChild(Document document, Integer note, MeasureItem_Note min) 
  	{
	  	Element pitch = document.createElement("pitch");
      
      	IntAndString ias = MXML.xmlKeyMap.get(min.getMeasure().getPrevailingXMLKey()).getXMLNote(note);
      
      	Element step = document.createElement("step");
      	step.appendChild(document.createTextNode(ias.str));
      	pitch.appendChild(step);
      
      	if (ias.i != 0) 
      	{
    	  	Element alter = document.createElement("alter");
    	  	alter.appendChild(document.createTextNode("" + ias.i));
    	  	pitch.appendChild(alter);
      	}
      
      	Element octave = document.createElement("octave");
      	octave.appendChild(document.createTextNode("" + (note / 12)));
      	pitch.appendChild(octave);
            
		return pitch;
	}  
  	
  	
  	
  	
  	
	// ------------------------------------------------
	// MeaureItem_TupletNote
	//-------------------------------------------------
	
  	// uses most of MeasureItem_Note code
	
	public static Element[] getElement(Document document, MeasureItem_TupletNote mitn)
  	{
    	ArrayList<Element> list = new ArrayList<Element>();
    	addAnnotationElementsToList(document, mitn, list);
     	
    	addNoteElementsToList(document, mitn, list);  
    	
	  	return list.toArray(new Element[list.size()]);
  	}
	
	
	
	private static Element makeTupletTimeModification(Document document, int timeModActual, int timeModNormal)
	{
//		MeasureItem_TupletNote mitn = (MeasureItem_TupletNote)min;
		Element timeMod = document.createElement("time-modification");
		timeMod.appendChild(getElementWithTextNode(document, "actual-notes", "" + timeModActual));
		timeMod.appendChild(getElementWithTextNode(document, "normal-notes", "" + timeModNormal));
		return timeMod;
	}


	

  	private static Element getElementWithTextNode(Document document, String name, String text)
	{
		Element element = document.createElement(name);
		element.appendChild(document.createTextNode(text));
		return element;
	}



  	
	// ------------------------------------------------
 	// MeaureItem_Rest
 	//-------------------------------------------------
  	

  	public static Element[] getElement(Document document, MeasureItem_Rest mir)
	{      
	   	Element element = document.createElement("note");
		element.appendChild(document.createElement("rest"));
		   
	   	element.appendChild(makeDurationChild(document, mir));
	   	element.appendChild(makeVoiceChild(document, mir));
	   	for (Element el: makeTypeChild(document, mir)) 
	   	{
		   	element.appendChild(el);
	   	}
    
	  	return new Element[]{element};
  	}

  	

   	private static Element[] makeTypeChild(Document document, MeasureItem_Rest mir)	
   	{
    	Element type = document.createElement("type");
    	MXML_NoteDurationType na = getNoteDurationType(mir);
		type.appendChild(document.createTextNode(na.getXmlType()));
		if (na.getDotCount() > 0) 
		{
			return new Element[] {type, document.createElement("dot")};
		} 
		else 
		{
			return new Element[] {type};
		}
  	}




	private static MXML_NoteDurationType getNoteDurationType(MeasureItem_Rest mir)
	{
		MXML_NoteDurationType na;
    	if (mir.getIsWholeBarRest())
    	{
    		na = MXML.xmlDurationRestType.get(4.0);		// whole bar rest is a whole note rest no matter the time signature
    	}
    	else
    	{
    		na = MXML.xmlDurationNoteType.get(mir.getLengthForTypeIndex());
    	}
		return na;
	}
  	
  	

  	private static Element makeVoiceChild(Document document, MeasureItem_Rest mir) 
  	{
	  	Element voice = document.createElement("voice");
	  	voice.appendChild(document.createTextNode("" + mir.getVoice()));
		return voice;
	}



  	private static Element makeDurationChild(Document document, MeasureItem_Rest mir) 
  	{
	  	Element duration = document.createElement("duration");
	  	int x = (int)Math.round(mir.getLength() * mir.getMeasure().getDivisions());
	  	duration.appendChild(document.createTextNode("" + x));
		return duration;
	}	

  	
  	
  	// ------------------------------------------------
  	// MeaureItem_TupletRest
  	//-------------------------------------------------	

  	
  	public static Element[] getElement(Document document, MeasureItem_TupletRest mir)
	{      
	   	Element element = document.createElement("note");
		element.appendChild(document.createElement("rest"));
		   
	   	element.appendChild(makeDurationChild(document, mir));
	   	element.appendChild(makeVoiceChild(document, mir));
	   	for (Element el: makeTypeChild(document, mir)) 
	   	{
		   	element.appendChild(el);
	   	}
	   	element.appendChild(makeTupletTimeModification(document, mir.getTimeModActual(), mir.getTimeModNormal()));
	   	if (mir.getHasStartOfTupletNotationElement()) 
	   	{
	   		element.appendChild(getTupletRestNotationsElement(document, mir));
	   	}
	  	return new Element[]{element};
  	}
  	
  	
  	
  	private static Element getTupletRestNotationsElement(Document document, MeasureItem_TupletRest mir)
	{
  		Element notations = document.createElement("notations");
  		Element zzzz = makeChildWithAttribute(document, "tuplet", "type", "start");
		String str = "no";
		if (mir.isHasTupletBracket()) str = "yes";
		zzzz.setAttribute("bracket", str);
		notations.appendChild(zzzz);
  		return notations;
	}

  	
  	
  	// ------------------------------------------------
 	// MeaureItem_Annotation
 	//-------------------------------------------------	
  	
  	

	// corresponds to (for now) PLACEMENT_ABOVE and PLACEMENT_BELOW as found in MeasureItem_Annotation
  	private static final String[] placements = new String[]{"above", "below"};
  	
  	
  	
  	public static Element[] getElement(Document document, MeasureItem_Annotation mia)
  	{
  		Element element = makeChildWithAttribute(document, "direction", "placement", placements[mia.getPlacementIndex()]);
  		Element directionType = document.createElement("direction-type");
  		Element words = document.createElement("words");
  		words.appendChild(document.createTextNode(mia.getText()));
  		if (mia.getHasFontSize())
  		{
  			words.setAttribute("font-size", "" + mia.getFontSize());
  		}
  		directionType.appendChild(words);
  		element.appendChild(directionType);
  		
  		return new Element[]{element};
  	}

  	
  	
  	//-------------------------------------------------------------------------------------
  	// all getTestOutput() methods for all classses implementing MXML_MeasureItemInterface
  	//-------------------------------------------------------------------------------------


	public static String getTestOutput(MeasureItem_Note note) {
		String str = "note: notes="; 
		for (Integer n: note.getNotes())
		{
			str += n + ",";
		}

		str += " offset=" + Mu.round(note.getOffset());
		str += " length=" + Mu.round(note.getLength());
		if (note.getHasTieEnd()) str += " tieEnd";
		if (note.getHasTieStart()) str += " tieStart";
		str += "\n";
		for (MeasureItem_Annotation man: note.getMeasureItemAnnotations())
		{
			str += "   " + man.getTestOutput() + "\n";
		}
		return str;
	}



	public static String getTestOutput(MeasureItem_Rest rest) {
		String str = "rest:"; 
		
		str += " offset=" + Mu.round(rest.getOffset());
		str += " length=" + Mu.round(rest.getLength());
		str += "\n";
		return str;
	}



	public static String getTestOutput(MeasureItem_KeySignature ks) {
		String str = "key signature=" + ks.getXmlKey(); 
		str += "\n";
		return str;
	}



	public static String getTestOutput(MeasureItem_Division division) {
		String str = "division=" + division.getDivision(); 
		str += "\n";
		return str;
	}



	public static String getTestOutput(MeasureItem_TimeSignature ts) {
		String str = "time signature=" + ts.getNumerator() + "/" + ts.getDenominator(); 
		str += "\n";
		return str;
	}



//	public static String getTestOutput(MeasureItem_Backup back) {
//		String str = "backup=" + back.getLengthInDivisions();
//		str += "\n";
//		return str;
//	}



	public static String getTestOutput(MeasureItem_Annotation annotation) {
		String str = "annotation: " + annotation.getText();
		str += "\n";
		return str;
	}
}
