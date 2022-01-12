package main.java.com.dougron.mucus.mucus_utils.max_document_maker;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

/*
 * class for hacking the track and device structure of Ableton to allow an algorithm to get 
 * some of the options available.
 * as of jan 2022, not actively part of the mucus ecosystem
 * 
 */


public class MaxDocumentMaker extends MaxObject {
	
	String path = "D:/Documents/algorithm data for googledrive/MaxDocumentMaker output/";
	Document doc;
	Element rootElement;
	boolean docIsOpen = false;
	Element currentTrack;
	Element trackProperties;
	boolean hasTrackProperties = false;
	
	Element currentDevices;
	boolean hasCurrentDevices = false;
	Element currentChains;
	boolean hasCurrentChains = false;
	Element currentChain;
	boolean hasCurrentChain = false;
	Element currentDevice;
	String[] typeNameArr = new String[] {"undefined", "instrument", "audio_effect", "midi_effect", "max_for_live_device"};
	
	Element deviceProperties;
	private boolean hasDeviceProperties = false;
	
	public MaxDocumentMaker() {
		post("initializing MaxDocumentMaker");
		initializeInOuts();
		post("MaxDocumentMaker initialized.");
	}
	
	public void bang() {
		post("bang in");
	}
	
	public void startDocument() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();
			docIsOpen = true;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (docIsOpen) {
			 // root element
	         rootElement = doc.createElement("abletonlive");
	         doc.appendChild(rootElement);
		}
	}
	
	public void addTrack(String name, int index) {
		currentTrack = addTrackElement("track", name, index);
		hasCurrentDevices = false;
		hasTrackProperties = false;
	}
	
	public void addChain(String name, int index) {
		post ("addChain(" + name + ", " + index + ") called");
		if (!hasCurrentChains) {
			hasCurrentChains = true;
			currentChains = doc.createElement("chains");
			currentDevice.appendChild(currentChains);
		}
		currentChain = doc.createElement("chain");
		currentChains.appendChild(currentChain);
//		hasDeviceProperties = false;

      	// setting attribute to element
		Attr attrIndex = doc.createAttribute("index");
		attrIndex.setValue("" + index);
		currentChain.setAttributeNode(attrIndex);
		
		Attr attrName = doc.createAttribute("name");
		attrName.setValue(name);
		currentChain.setAttributeNode(attrName);
	}
	
	public void can_have_chains(int i) {
		checkDeviceProperties();
		Element element = doc.createElement("can_have_chains");
		deviceProperties.appendChild(element);
		element.setTextContent("" + i);
	}
	
	public void can_have_drum_pads(int i) {
		checkDeviceProperties();
		Element element = doc.createElement("can_have_drum_pads");
		deviceProperties.appendChild(element);
		element.setTextContent("" + i);
	}
	
	private void checkDeviceProperties() {
		if (!hasDeviceProperties ) {
			deviceProperties = doc.createElement("device_properties");
			currentDevice.appendChild(deviceProperties);
			hasDeviceProperties = true;
		}
		
	}

	public void has_midi_input(int i) {
		checkTrackProperties();
		Element element = doc.createElement("has_midi_input");
		trackProperties.appendChild(element);
		element.setTextContent("" + i);
	}
	
	public void has_midi_output(int i) {
		checkTrackProperties();
		Element element = doc.createElement("has_midi_output");
		trackProperties.appendChild(element);
		element.setTextContent("" + i);
	}
	
	public void has_audio_input(int i) {
		checkTrackProperties();
		Element element = doc.createElement("has_audio_input");
		trackProperties.appendChild(element);
		element.setTextContent("" + i);
	}
	
	public void has_audio_output(int i) {
		checkTrackProperties();
		Element element = doc.createElement("has_audio_output");
		trackProperties.appendChild(element);
		element.setTextContent("" + i);
	}
	
	private void checkTrackProperties() {
		if (!hasTrackProperties) {
			trackProperties = doc.createElement("track_properties");
			currentTrack.appendChild(trackProperties);
			hasTrackProperties = true;
		}
		
	}

	public void addReturnTrack(String name, int index) {
		currentTrack = addTrackElement("return_track", name, index);
	}
	
	public void addDevice(String name, int index, int type) {
		if (!hasCurrentDevices) {
			hasCurrentDevices = true;
			currentDevices = doc.createElement("devices");
			currentTrack.appendChild(currentDevices);
		}
		currentDevice = doc.createElement("device");
		currentDevices.appendChild(currentDevice);
		hasDeviceProperties = false;
		hasCurrentChains = false;

      	// setting attribute to element
		Attr attrIndex = doc.createAttribute("index");
		attrIndex.setValue("" + index);
		currentDevice.setAttributeNode(attrIndex);
		
		Attr attrName = doc.createAttribute("name");
		attrName.setValue(name);
		currentDevice.setAttributeNode(attrName);
		
		Attr attrType = doc.createAttribute("type");
		attrType.setValue("" + type);
		currentDevice.setAttributeNode(attrType);
		
		Attr attrTypeName = doc.createAttribute("type_name");
		attrTypeName.setValue("" + typeNameArr[type]);
		currentDevice.setAttributeNode(attrTypeName);
		
	}
	
	private Element addTrackElement(String elementName, String name, int index) {
		 // track element
		Element element = doc.createElement(elementName);
		rootElement.appendChild(element);

      	// setting attribute to element
		Attr attrIndex = doc.createAttribute("index");
		attrIndex.setValue("" + index);
		element.setAttributeNode(attrIndex);
		Attr attrName = doc.createAttribute("name");
		attrName.setValue(name);
		element.setAttributeNode(attrName);
		return element;
	}

	public void saveDocument(String filename) {
		if (docIsOpen) {
			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(path + filename + ".xml"));
				transformer.transform(source, result);
				System.out.println("File saved!");
				docIsOpen = false;
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	

	
// ----- privates ------------------------------------------------------------
	
	private void initializeInOuts() {
		// TODO Auto-generated method stub
		declareInlets(new int[] {
			DataTypes.ALL,
		});
		declareOutlets(new int[] {
				DataTypes.ALL,
		});
		setInletAssist(new String[] {
				"all inputs",
		});
		setOutletAssist(new String[] {
				"all outs",
		});
	}	
}
