/*CODE CAN BE EDITED - Umple no longer used*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.da_utils.time_signature_utilities.time_signature_map.TSMapInterface;
import test.java.com.dougron.mucus.mu_framework.key_signature_utilities.KeySignatureMap;

/**
 * this is the class that makes the xml
 */
public class MXML_Score
{


	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//MXML_Score Attributes
	private TSMapInterface globalTSMap;
	private KeySignatureMap globalKSMap;

	//MXML_Score Associations
	private List<MXML_Part> mXMLParts;



	//------------------------
	// CONSTRUCTOR
	//------------------------


	public MXML_Score(TSMapInterface aGlobalTSMap, KeySignatureMap aGlobalKSMap)
	{
		globalTSMap = aGlobalTSMap;
		globalKSMap = aGlobalKSMap;
		mXMLParts = new ArrayList<MXML_Part>();
	}


	//------------------------
	// INTERFACE
	//------------------------


	public boolean setGlobalTSMap(TSMapInterface aGlobalTSMap)
	{
		boolean wasSet = false;
		globalTSMap = aGlobalTSMap;
		wasSet = true;
		return wasSet;
	}



	public boolean setGlobalKSMap(KeySignatureMap aGlobalKSMap)
	{
		boolean wasSet = false;
		globalKSMap = aGlobalKSMap;
		wasSet = true;
		return wasSet;
	}



	public TSMapInterface getGlobalTSMap()
	{
		return globalTSMap;
	}



	public KeySignatureMap getGlobalKSMap()
	{
		return globalKSMap;
	}



	public MXML_Part getMXMLPart(int index)
	{
		MXML_Part aMXMLPart = mXMLParts.get(index);
		return aMXMLPart;
	}



	public List<MXML_Part> getMXMLParts()
	{
		List<MXML_Part> newMXMLParts = Collections.unmodifiableList(mXMLParts);
		return newMXMLParts;
	}



	public int numberOfMXMLParts()
	{
		int number = mXMLParts.size();
		return number;
	}



	public boolean hasMXMLParts()
	{
		boolean has = mXMLParts.size() > 0;
		return has;
	}



	public int indexOfMXMLPart(MXML_Part aMXMLPart)
	{
		int index = mXMLParts.indexOf(aMXMLPart);
		return index;
	}



	public static int minimumNumberOfMXMLParts()
	{
		return 0;
	}



	public boolean addMXMLPart(MXML_Part aMXMLPart)
	{
		boolean wasAdded = false;
		if (mXMLParts.contains(aMXMLPart)) { return false; }
		mXMLParts.add(aMXMLPart);
		wasAdded = true;
		return wasAdded;
	}



	public boolean removeMXMLPart(MXML_Part aMXMLPart)
	{
		boolean wasRemoved = false;
		if (mXMLParts.contains(aMXMLPart))
		{
			mXMLParts.remove(aMXMLPart);
			wasRemoved = true;
		}
		return wasRemoved;
	}



	public boolean addMXMLPartAt(MXML_Part aMXMLPart, int index)
	{  
		boolean wasAdded = false;
		if(addMXMLPart(aMXMLPart))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfMXMLParts()) { index = numberOfMXMLParts() - 1; }
			mXMLParts.remove(aMXMLPart);
			mXMLParts.add(index, aMXMLPart);
			wasAdded = true;
		}
		return wasAdded;
	}



	public boolean addOrMoveMXMLPartAt(MXML_Part aMXMLPart, int index)
	{
		boolean wasAdded = false;
		if(mXMLParts.contains(aMXMLPart))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfMXMLParts()) { index = numberOfMXMLParts() - 1; }
			mXMLParts.remove(aMXMLPart);
			mXMLParts.add(index, aMXMLPart);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addMXMLPartAt(aMXMLPart, index);
		}
		return wasAdded;
	}



	public void delete()
	{
		mXMLParts.clear();
	}



	public void addMuToPart(Mu mu, String partName){
		for (MXML_Part part: mXMLParts)
		{
			if (part.getName().equals(partName))
			{
				part.addMus(mu);
			}
		}
	}
	
	
	
	public void addAnnotatedMuToPart(Mu aMu, String partName)
	{
		for (MXML_Part part: mXMLParts)
		{
			if (part.getName().equals(partName))
			{
				part.addMuWithOnlyAnnotations(aMu);
			}
		}
		
	} 



	public void makeXML(String path){
		makePartMeasures(this, globalTSMap, globalKSMap);
	  	writeXMLFile(path, this);
	}


	
	/*
	 * outputs a simplified form of the xml output to the console for test purposes
	 */	
	public String makeTestOutput()
	{
		makePartMeasures(this, globalTSMap, globalKSMap);
		StringBuilder sb = new StringBuilder();
		for (MXML_Part part: this.getMXMLParts())
		{
			sb.append(part.getTestOutput());
		}
		return sb.toString();
	}



	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "");
		sb.append("\nglobalTSMap=");
		if (globalTSMap == null)
		{
			sb.append("null");
		} else
		{
			sb.append(globalTSMap.toString());
		}
		sb.append("\nglobalKSMap=");
		if (globalKSMap == null)
		{
			sb.append("null");
		} else
		{
			sb.append(globalKSMap.toString());
		}
		return sb.toString();
	}
	
	
	//--------------------------------
	// PRIVATES
	//--------------------------------
	
	private static void writeXMLFile(String path, MXML_Score score) throws TransformerFactoryConfigurationError
	{
		try 
	  	{

		  	Document document = makeDocument();
          	Element root = createScorePartwiseElement(document);
          	document.appendChild(root);          	
//          	root.appendChild(getWorkElement(score, document));
          	
          	root.appendChild(getIndentificationElement(document, path));
          	          	
          	// add defaults
          	

          	Element partList = document.createElement("part-list");
          	root.appendChild(partList);
          	
          	// add parts and items to part-list
          	int partCount = 1;
          	for (MXML_Part part: score.getMXMLParts()) 
          	{
          		String partID = "P" + partCount;
        	  	partList.appendChild(part.getPartListElement(document, partID));
        	  	root.appendChild(part.getPartElement(document, partID));
        	  	partCount++;
          	}
          
		  	renderXML(path, document);
	  
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



	private static void renderXML(String path, Document document)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();                    
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//Recordare//DTD MusicXML 3.1 Partwise//EN");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.musicxml.org/dtds/partwise.dtd");         
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(path));
		transformer.transform(domSource, streamResult);
	}



	private static Element createScorePartwiseElement(Document document)
	{
		Element root = document.createElement("score-partwise");          	
		root.setAttribute("version", "3.0");
		return root;
	}



	private static Document makeDocument() throws ParserConfigurationException
	{
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();	 
		documentFactory.setExpandEntityReferences(false);
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();	 
		Document document = documentBuilder.newDocument();
		document.setXmlStandalone(true);	// bizzarrely makes the standalone attribute dissappear in the xml output!?
		return document;
	}



//	private static Element getWorkElement(MXML_Score score, Document document)
//	{
//		Element work = document.createElement("work");
//		Element worktitle = document.createElement("work-title");
////		worktitle.appendChild(document.createTextNode(score.getName()));		// MXML_Score does not have a name() yet
//		return work;
//	}



	private static Element getIndentificationElement(Document document, String path)
	{
		Element identification = document.createElement("identification");
		// rights element
		identification.appendChild(elementWithTextNode(document, "rights", "Copyright © "));
		// encoding element
		Element encoding = document.createElement("encoding");
		encoding.appendChild(elementWithTextNode(document, "software", "MUCuS.musicxml_maker"));
		// these 'supports' elements pulled from the xmls made by both Sibelius and MuseScore
		encoding.appendChild(elementWithListOfAttributes(document, "supports", 
				new String[] {"element", "type", "value", "attribute"}, 
				new String[] {"print", "yes", "yes", "new-system"}));
		encoding.appendChild(elementWithListOfAttributes(document, "supports", 
				new String[] {"element", "type", "value", "attribute"}, 
				new String[] {"print", "yes", "yes", "new-page"}));
		encoding.appendChild(elementWithListOfAttributes(document, "supports", 
				new String[] {"element", "type"}, 
				new String[] {"accidental", "yes"}));
		encoding.appendChild(elementWithListOfAttributes(document, "supports", 
				new String[] {"element", "type"}, 
				new String[] {"beam", "yes"}));
		encoding.appendChild(elementWithListOfAttributes(document, "supports", 
				new String[] {"element", "type"}, 
				new String[] {"stem", "yes"}));
		identification.appendChild(encoding);		
		
		return identification;
	}
	
	
	
	private static Element elementWithTextNode(Document document, String name, String text)
	{
		Element element = document.createElement(name);
		element.appendChild(document.createTextNode(text));
		return element;
	}
	
	
	
	private static Element elementWithListOfAttributes(Document document, String name, String[] attribName, String[] attribValue)
	{
		Element element = document.createElement(name);
		for (int i = 0; i < attribName.length; i++)
		{
			element.setAttribute(attribName[i], attribValue[i % attribName.length]);
		}
		return element;
	}



	private static void makePartMeasures(MXML_Score score, TSMapInterface globalTSMap, KeySignatureMap globalKSMap) {
		for (MXML_Part p: score.getMXMLParts()) 
	   	{
		   	p.makeMeasures(globalTSMap, globalKSMap);
	   	}
	}


	public void addChordListToPartForPrinting(ChordList aChordList, String aPartName)
	{
		for (MXML_Part part: getMXMLParts())
		{
			if (part.getName().contentEquals(aPartName))
			{
				part.addChordListForPrint(aChordList);
			}
		}
		
	}



	
	
}