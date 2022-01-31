package main.java.com.dougron.mucus.mu_framework.mu_xml_utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation.TextPlacement;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagBundle;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGenerator;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class MuXMLUtility
{

	
	public static void saveMuToXMLFile(String aPath, Mu aMu)
	{
		int index = 0;
		List<Mu> muList = getListOfAllMusWithMuIndexSet(aMu, index);
		try 
	  	{
			Document document = getDocument();
	        Element root = document.createElement("mu_hierarchy"); 
          	document.appendChild(root);          	


          	for (Mu mu: muList)
          	{
          		root.appendChild(getMuXMLContent(document, mu));
          	}
 
          
          	doTheTransforminAndStreamin(aPath, document);
	  
	  	} 
	  	catch (ParserConfigurationException pce) 
	  	{
          	pce.printStackTrace();
      	} 
      	catch (TransformerException tfe) 
      	{
          	tfe.printStackTrace();
      	}
	}
	
	
	
	public static Mu loadMuFromXMLFile(String aPath)
	{
		HashMap<Integer, Mu> muMap = new HashMap<Integer, Mu>();
		try
		{
			Document doc = getXMLDocumentToLoad(aPath);   
			NodeList nodeList = doc.getElementsByTagName("mu"); 
			
			loadMuMap(muMap, nodeList);		
			makeParentChildSiblingPositionRelationships(muMap, nodeList);			 
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return muMap.get(0);
	}
	
	
	
	private static void loadMuMap(HashMap<Integer, Mu> muMap, NodeList nodeList)
	{ 
		for (int itr = 0; itr < nodeList.getLength(); itr++)   
		{  
			Node node = nodeList.item(itr);  
			String mu_index = node.getAttributes().getNamedItem("index").getNodeValue();
			
			Element element = (Element)node;
			
			String name = element.getElementsByTagName("name").item(0).getTextContent();
			Mu mu = new Mu(name);
			muMap.put(Integer.parseInt(mu_index), mu);
			
			addLengthModelToMuMapItem(element, mu);			
			addHasLeadingDoubleBarToMuMapItem(element, mu);			
			addChordListGeneratorToMuMapItem(element, mu);			
			addTupletInfoToMuMapItem(element, mu);			
			addMuTagsToMuMapItem(element, mu);
			addMuNotesToMuMapItem(element, mu);			
			addMuAnnotationToMuMapItem(element, mu);
			addStartPitchToMuMapItem(element, mu);
			addKeySignatureMapToMuMapItem(element, mu);			
			addMuGeneratorToMuMapItem(element, mu);
			
			NodeList ruler_list = element.getElementsByTagName("ruler");
			if (ruler_list.getLength() > 0)
			{
				Element ruler_element = (Element)ruler_list.item(0);
								
				addTimeSignatureGeneratorToMuMapItem(mu, ruler_element);	
				addTimeSignatureMapItemsToMuMapItem(mu, ruler_element);
				addTempoChangesToMuMapItem(mu, ruler_element);				
			}			
		}
	}



	private static void addTimeSignatureMapItemsToMuMapItem(Mu mu, Element ruler_element)
	{
		NodeList tsgm_node_list = ruler_element.getElementsByTagName("ts_gen_and_map");
		if (tsgm_node_list.getLength() > 0)
		{
			Element ts_gen_and_map = (Element) (tsgm_node_list.item(0));
			NodeList map_item_node_list = ts_gen_and_map.getElementsByTagName("map_item");
			for (int i = 0; i < map_item_node_list.getLength(); i++)
			{
				Element map_item = (Element)map_item_node_list.item(i);
				int bar_index = Integer.parseInt(map_item.getElementsByTagName("bar_index").item(0).getTextContent());				
				Element time_signature = (Element)map_item.getElementsByTagName("time_signature").item(0);
				TimeSignature ts = TimeSignature.getTimeSignatureFromXMLElement(time_signature);
				mu.getRuler().getTSGenAndMap().addTimeSignature(bar_index, ts);
			}
		}		
	}



	private static void addTempoChangesToMuMapItem(Mu mu, Element ruler_element)
	{
		NodeList tempo_node_list = ruler_element.getElementsByTagName("tempo_list");
		if (tempo_node_list.getLength() > 0)
		{
			Element tempo_list = (Element) (tempo_node_list.item(0));
			NodeList tempo_change_list = tempo_list.getElementsByTagName("tempo_change");
			for (int i = 0; i < tempo_change_list.getLength(); i++)
			{
				Element tempo_change = (Element) tempo_change_list.item(i);
				double the_tempo = Double.parseDouble(tempo_change.getElementsByTagName("tempo").item(0).getTextContent());
				double the_pos = Double.parseDouble(tempo_change.getElementsByTagName("float_bar_position").item(0).getTextContent());
				if (i == 0) 
				{
					mu.setStartTempo(the_tempo);
				}
				else
				{
					mu.addTempoChange(the_tempo, the_pos);
				}
			}
		}
	}



	private static void addTimeSignatureGeneratorToMuMapItem(Mu mu, Element ruler_element)
	{
		NodeList tsgm_node_list = ruler_element.getElementsByTagName("ts_gen_and_map");
		if (tsgm_node_list.getLength() > 0)
		{
			Element ts_gen_and_map = (Element) (tsgm_node_list.item(0));
			NodeList ts_node_list = ts_gen_and_map.getElementsByTagName("time_signature_generator");
			if (ts_node_list.getLength() > 0)
			{
				Element time_signature_generator = (Element) (ts_node_list.item(0));				
				mu.setTimeSignatureGenerator(TimeSignatureListGenerator.getTimeSignatureGeneratorFromXMLElement(time_signature_generator));
			}
		}		
	}

	

	private static void addMuGeneratorToMuMapItem(Element element, Mu mu)
	{
		NodeList mu_generator_list = element.getElementsByTagName("mu_generator");
		if (mu_generator_list.getLength() > 0)
		{
			Element mu_generator = (Element)mu_generator_list.item(0);
			mu.addMuGenerator(MuGenerator.getMuGeneratorFromXMLElement(mu_generator));
		}
	}



	private static void addKeySignatureMapToMuMapItem(Element element, Mu mu)
	{
		Element key_signature_map = (Element)element.getElementsByTagName("key_signature_map").item(0);
		int key = Integer.parseInt(key_signature_map.getElementsByTagName("key").item(0).getTextContent());
		mu.setXMLKey(key);
	}



	private static void addStartPitchToMuMapItem(Element element, Mu mu)
	{
		int start_pitch = Integer.parseInt(element.getElementsByTagName("start_pitch").item(0).getTextContent());
		mu.setStartPitch(start_pitch);
	}



	private static void addMuAnnotationToMuMapItem(Element element, Mu mu)
	{
		NodeList mu_annotation_list = element.getElementsByTagName("mu_annotation");
		for (int i = 0; i < mu_annotation_list.getLength(); i++)
		{
			Element mu_annotation = (Element) mu_annotation_list.item(i);
			String annotation = ((Element)mu_annotation.getElementsByTagName("annotation").item(0)).getTextContent();
			String placement = ((Element)mu_annotation.getElementsByTagName("placement").item(0)).getTextContent();
			NodeList font_size_list = mu_annotation.getElementsByTagName("font_size");
			if (font_size_list.getLength() > 0)
			{
				double font_size = Double.parseDouble(font_size_list.item(0).getTextContent());
				mu.addMuAnnotation(new MuAnnotation(annotation, font_size , TextPlacement.valueOf(placement)));
			}
			else
			{
				mu.addMuAnnotation(new MuAnnotation(annotation, TextPlacement.valueOf(placement)));
			}
		}
	}



	private static void addMuNotesToMuMapItem(Element element, Mu mu)
	{
		NodeList mu_note_list = element.getElementsByTagName("mu_note");
		for (int i = 0; i < mu_note_list.getLength(); i++)
		{
			Element mu_note = (Element) mu_note_list.item(i);
			Element pitch_element = (Element)mu_note.getElementsByTagName("pitch").item(0);
			Element velocity_element = (Element)mu_note.getElementsByTagName("velocity").item(0);
			int pitch = Integer.parseInt(pitch_element.getTextContent());
			int velocity = Integer.parseInt(velocity_element.getTextContent());
			mu.addMuNote(new MuNote(pitch, velocity));
		}
	}



	private static void addMuTagsToMuMapItem(Element element, Mu mu)
	{
		NodeList mu_tag_list = element.getElementsByTagName("mu_tag_bundle");
		for (int i = 0; i < mu_tag_list.getLength(); i++)
		{
			Element mu_tag_bundle = (Element) mu_tag_list.item(i);
			NodeList mu_tags = mu_tag_bundle.getElementsByTagName("mu_tag");
			MuTag[] muTagArr = new MuTag[mu_tags.getLength()];
			for (int j = 0; j < mu_tags.getLength(); j++)
			{
				Node mu_tag_node = mu_tags.item(j);
				String str = mu_tag_node.getTextContent();
				muTagArr[j] = MuTag.valueOf(str);
			}
			mu.addTag(muTagArr);
		}
	}



	private static void addTupletInfoToMuMapItem(Element element, Mu mu)
	{
		String is_tuplet_print_container = element.getElementsByTagName("is_tuplet_print_container").item(0).getTextContent();
		if (is_tuplet_print_container.equals("true"))
		{
			int tuplet_numerator = Integer.parseInt(element.getElementsByTagName("tuplet_numerator").item(0).getTextContent());
			int tuplet_denominator = Integer.parseInt(element.getElementsByTagName("tuplet_denominator").item(0).getTextContent());
			mu.setIsTupletPrintContainer(true);
			mu.setTupletNumerator(tuplet_numerator);
			mu.setTupletDenominator(tuplet_denominator);
		}
	}



	private static void addChordListGeneratorToMuMapItem(Element element, Mu mu)
	{
		String has_chord_list_generator = element.getElementsByTagName("has_chord_list_generator").item(0).getTextContent();
		if (has_chord_list_generator.equals("true")) 
		{
			Node chord_list_generator = element.getElementsByTagName("chord_list_generator").item(0);
			String chord_list_generator_type = chord_list_generator.getAttributes().getNamedItem("type").getNodeValue();
			Element chord_list_generator_element = (Element) chord_list_generator;
			String chord_symbol;
			double float_bar_position;
			NodeList nList;
			switch (chord_list_generator_type)
			{
			case "SingleChordGenerator":
				chord_symbol = chord_list_generator_element.getElementsByTagName("chord").item(0).getTextContent();
				mu.setChordListGenerator(new SingleChordGenerator(new Chord(chord_symbol)));
				break;
			case "SimpleEvenChordProgression":
				nList = chord_list_generator_element.getElementsByTagName("chord");
				String[] chordArray = new String[nList.getLength()];
				for (int i = 0; i < nList.getLength(); i++)
				{
					chordArray[i] = nList.item(i).getTextContent();						
				}
				mu.setChordListGenerator(new SimpleEvenChordProgression(chordArray));
				break;
			case "FloatBarChordProgression":
				nList = chord_list_generator_element.getElementsByTagName("float_bar_chord");
				double length_in_float_bars = Double.parseDouble(chord_list_generator_element.getElementsByTagName("length_in_float_bars").item(0).getTextContent());
				Object[] objArr = new Object[nList.getLength() * 2];
				for (int i = 0; i < nList.getLength(); i++)
				{
					Element float_bar_chord = (Element)nList.item(i);
					chord_symbol = float_bar_chord.getElementsByTagName("chord").item(0).getTextContent();
					float_bar_position = Double.parseDouble(float_bar_chord.getElementsByTagName("float_bar_position").item(0).getTextContent());
					objArr[i * 2] = float_bar_position;
					objArr[i * 2 + 1] = chord_symbol;
				}
				mu.setChordListGenerator(new FloatBarChordProgression(length_in_float_bars, objArr));
				break;
			}
		}
	}



	private static void addHasLeadingDoubleBarToMuMapItem(Element element, Mu mu)
	{
		String has_leading_double_bar = element.getElementsByTagName("has_leading_double_bar").item(0).getTextContent();
		if (has_leading_double_bar.equals("true")) mu.setHasLeadingDoubleBar(true);
	}



	private static void addLengthModelToMuMapItem(Element element, Mu mu)
	{
		Node length_model = element.getElementsByTagName("length_model").item(0);
		String length_model_type = length_model.getAttributes().getNamedItem("type").getNodeValue();
		Element length_model_element = (Element) length_model;
		int length_in_bars;
		double length_in_quarters;
		switch (length_model_type)
		{
		case "FixedLengthInBars":
			length_in_bars = Integer.parseInt(length_model_element.getElementsByTagName("bar_length").item(0).getTextContent());
			mu.setLengthInBars(length_in_bars);
			break;
		case "FixedLengthInBarsAndBeats":
			length_in_quarters = Double.parseDouble(length_model_element.getElementsByTagName("beat_length").item(0).getTextContent());
			length_in_bars = Integer.parseInt(length_model_element.getElementsByTagName("bar_length").item(0).getTextContent());
			mu.setLengthInBarsAndBeats(new BarsAndBeats(length_in_bars, length_in_quarters));;
			break;
		case "FixedLengthInQuarters":
			length_in_quarters = Double.parseDouble(length_model_element.getElementsByTagName("length_in_quarters").item(0).getTextContent());
			mu.setLengthInQuarters(length_in_quarters);
			break;
		case "LengthFromChildren":
			break;
		}
	}
	
	
	
	private static void makeParentChildSiblingPositionRelationships(HashMap<Integer, Mu> muMap, NodeList nodeList)
	{
		for (int itr = 0; itr < nodeList.getLength(); itr++)   
		{  
			Node node = nodeList.item(itr);  
			int muIndex = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			Element element = (Element)node;
			int parentIndex = Integer.parseInt(element.getElementsByTagName("parent_index").item(0).getTextContent());
			Node positionNode = element.getElementsByTagName("position_model").item(0);
			String positionType = positionNode.getAttributes().getNamedItem("type").getNodeValue();
			Element positionElement = (Element)positionNode;
			int barPosition;
			double quartersPosition;
			Mu sibling;
			Mu child = muMap.get(muIndex);
			switch (positionType)
			{
			case "BeginningOfParentInBars":
				barPosition = Integer.parseInt(positionElement.getElementsByTagName("bar_position").item(0).getTextContent());
				muMap.get(parentIndex).addMu(child, barPosition);
				break;
			case "BeginningOfParentInBarsAndBeats":
				barPosition = Integer.parseInt(positionElement.getElementsByTagName("bar_position").item(0).getTextContent());
				quartersPosition = Double.parseDouble(positionElement.getElementsByTagName("beat_position").item(0).getTextContent());
				muMap.get(parentIndex).addMu(child, new BarsAndBeats(barPosition, quartersPosition));
				break;
			case "BeginningOfParentInQuarters":
				quartersPosition = Double.parseDouble(positionElement.getElementsByTagName("position_in_quarters").item(0).getTextContent());
				muMap.get(parentIndex).addMu(child, quartersPosition);
				break;
			case "EndOfSiblingInBars":
				sibling = muMap.get(Integer.parseInt(positionElement.getElementsByTagName("sibling_index").item(0).getTextContent()));
				barPosition = Integer.parseInt(positionElement.getElementsByTagName("bar_position").item(0).getTextContent());
				muMap.get(parentIndex).addMuToEndOfSibling(child, barPosition, sibling);
				break;
			case "EndOfSiblingInBarsAndBeats":
				sibling = muMap.get(Integer.parseInt(positionElement.getElementsByTagName("sibling_index").item(0).getTextContent()));
				barPosition = Integer.parseInt(positionElement.getElementsByTagName("bar_position").item(0).getTextContent());
				quartersPosition = Double.parseDouble(positionElement.getElementsByTagName("beat_position").item(0).getTextContent());
				muMap.get(parentIndex).addMuToEndOfSibling(child, new BarsAndBeats(barPosition, quartersPosition), sibling);
				break;
			case "EndOfSiblingInQuarters":
				sibling = muMap.get(Integer.parseInt(positionElement.getElementsByTagName("sibling_index").item(0).getTextContent()));
				quartersPosition = Double.parseDouble(positionElement.getElementsByTagName("position_in_quarters").item(0).getTextContent());
				muMap.get(parentIndex).addMuToEndOfSibling(child, quartersPosition, sibling);
				break;
			}
		}
	}
	
	
	
	private static Document getXMLDocumentToLoad(String aPath)
			throws ParserConfigurationException, SAXException, IOException
	{
		File file = new File(aPath);  
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
		DocumentBuilder db = dbf.newDocumentBuilder();  
		Document doc = db.parse(file);  
		doc.getDocumentElement().normalize();
		return doc;
	}
	



	private static void doTheTransforminAndStreamin(String aPath, Document document)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();  
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(aPath));
		transformer.transform(domSource, streamResult);
	}



	private static Document getDocument() throws ParserConfigurationException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.newDocument();
		return document;
	}



	private static List<Mu> getListOfAllMusWithMuIndexSet(Mu aMu, int index)
	{
		List<Mu> muList = aMu.getAllMus();
		for (Mu mu: muList) 
		{
			mu.setMuIndex(index);
			index++;
		}
		return muList;
	}
	
	
	
	private static Element getMuXMLContent(Document document, Mu aMu)
	{
		Element element = document.createElement("mu");
		element.setAttribute("index", "" + aMu.getMuIndex());
		
		addNameXMLElement(document, element, aMu);		
		addParentIndexXMLElement(document, element, aMu);
		addPositionModelXMLElement(document, element, aMu);
		addLengthModelXMLElement(document, element, aMu);		
		addHasLeadingDoubleBarXMLElement(document, element, aMu);		
		addChordListGeneratorAndHasChordListGeneratorXMLElement(document, element, aMu);		
		addTupletXMLElements(document, element, aMu);		
		addMuTagBundleXMLElements(document, element, aMu);
		addMuNoteXMLElements(document, element, aMu);
		addMuAnnotationXMLElements(document, element, aMu);		
		addStartPitchXMLElement(document, element, aMu);		
		addKeySignatureMapXMLElement(document, element, aMu);		
		addMuGeneratorXMLElement(document, element, aMu);
		if (aMu.getRuler() != null)
		{
			element.appendChild(aMu.getRuler().getXMLElement(document));
		}
		
		return element;
	}
	
	
	
	private static void addMuGeneratorXMLElement(Document document, Element element, Mu aMu)
	{
		if (aMu.getMuGenerator() != null) element.appendChild(aMu.getMuGenerator().getXMLElement(document));
	}



	private static void addKeySignatureMapXMLElement(Document document, Element element, Mu aMu)
	{
		element.appendChild(aMu.getKeySignatureMap().getXMLElement(document));
	}



	private static void addStartPitchXMLElement(Document document, Element element, Mu aMu)
	{
		Element start_pitch = document.createElement("start_pitch");
		start_pitch.appendChild(document.createTextNode("" + aMu.getStartPitch()));
		element.appendChild(start_pitch);
	}



	private static void addMuAnnotationXMLElements(Document document, Element element, Mu aMu)
	{
		if (aMu.getMuAnnotations() != null)
		{
			for (MuAnnotation muan : aMu.getMuAnnotations())
			{
				Element muan_element = document.createElement("mu_annotation");

				Element annotation = document.createElement("annotation");
				annotation.appendChild(document.createTextNode(muan.getAnnotation()));
				muan_element.appendChild(annotation);

				Element placement = document.createElement("placement");
				placement.appendChild(document.createTextNode("" + muan.getPlacement()));
				muan_element.appendChild(placement);

				if (muan.getHasFontSize())
				{
					Element font_size = document.createElement("font_size");
					font_size.appendChild(document.createTextNode("" + muan.getFontSize()));
					muan_element.appendChild(font_size);
				}
				element.appendChild(muan_element);
			} 	
		}
	}



	private static void addMuNoteXMLElements(Document document, Element element, Mu aMu)
	{
		if (aMu.getMuNotes() != null)
		{
			for (MuNote mn : aMu.getMuNotes())
			{
				Element mu_note = document.createElement("mu_note");
				Element pitch = document.createElement("pitch");
				pitch.appendChild(document.createTextNode("" + mn.getPitch()));
				Element velocity = document.createElement("velocity");
				velocity.appendChild(document.createTextNode("" + mn.getVelocity()));
				mu_note.appendChild(pitch);
				mu_note.appendChild(velocity);
				element.appendChild(mu_note);
			} 
		}
	}



	private static void addMuTagBundleXMLElements(Document document, Element element, Mu aMu)
	{
		for (MuTagBundle muTagBundle: aMu.getMuTagBundles())
		{
			Element mu_tag_bundle = document.createElement("mu_tag_bundle");
			for (MuTag tag: muTagBundle.getMuTags())
			{
				Element mu_tag = document.createElement("mu_tag");
				mu_tag.appendChild(document.createTextNode("" + tag));
				mu_tag_bundle.appendChild(mu_tag);
			}
			element.appendChild(mu_tag_bundle);
		}
	}



	private static void addTupletXMLElements(Document document, Element element, Mu aMu)
	{
		Element is_tuplet_print_container = document.createElement("is_tuplet_print_container");
		is_tuplet_print_container.appendChild(document.createTextNode("" + aMu.isTupletPrintContainer()));
		element.appendChild(is_tuplet_print_container);
		if (aMu.isTupletPrintContainer())
		{
			Element tuplet_numerator = document.createElement("tuplet_numerator");
			tuplet_numerator.appendChild(document.createTextNode("" + aMu.getTupletNumerator()));
			element.appendChild(tuplet_numerator);
			Element tuplet_denominator = document.createElement("tuplet_denominator");
			tuplet_denominator.appendChild(document.createTextNode("" + aMu.getTupletDenominator()));
			element.appendChild(tuplet_denominator);
		}
	}



	private static void addChordListGeneratorAndHasChordListGeneratorXMLElement(Document document, Element element, Mu aMu)
	{
		Element has_chord_list_generator = document.createElement("has_chord_list_generator");
		has_chord_list_generator.appendChild(document.createTextNode("" + aMu.hasChordListGenerator()));
		element.appendChild(has_chord_list_generator);
		
		Element chord_list_generator = aMu.getChordListGenerator().getXMLFileContent(document);
		element.appendChild(chord_list_generator);
	}
	
	

	private static void addHasLeadingDoubleBarXMLElement(Document document, Element element, Mu aMu)
	{
		Element has_leading_double_bar = document.createElement("has_leading_double_bar");
		has_leading_double_bar.appendChild(document.createTextNode("" + aMu.hasLeadingDoubleBar()));
		element.appendChild(has_leading_double_bar);
	}



	private static void addLengthModelXMLElement(Document document, Element element, Mu aMu)
	{
		element.appendChild(aMu.getLengthModel().getXMLFileContent(document));
	}



	private static void addPositionModelXMLElement(Document document, Element element, Mu aMu)
	{
		Element position_model = aMu.getPositionModel().getXMLFileContent(document);
		element.appendChild(position_model);
	}



	private static void addParentIndexXMLElement(Document document, Element element, Mu aMu)
	{
		int parentIndex = -1;
		if (aMu.getParent() != null) parentIndex = aMu.getParent().getMuIndex();
		Element parent_index = document.createElement("parent_index");
		parent_index.appendChild(document.createTextNode("" + parentIndex));
		element.appendChild(parent_index);
	}



	private static void addNameXMLElement(Document document, Element element, Mu aMu)
	{
		Element name = document.createElement("name");
		name.appendChild(document.createTextNode(aMu.getName()));
		element.appendChild(name);
	}

}
