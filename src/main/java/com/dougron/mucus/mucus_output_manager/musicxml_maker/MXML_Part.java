/*CODE CAN BE EDITED - Umple no longer used*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.key_signature_utilities.KeySignatureZone;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_BarLine;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Clef;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Clef.ClefType;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Division;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_KeySignature;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_TimeSignature;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu.VoiceMu;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu.VoiceMuplet;
import main.java.da_utils.time_signature_utilities.time_signature_map.TSMapInterface;
import main.java.da_utils.time_signature_utilities.time_signature_map.TimeSignatureZone;
import test.java.com.dougron.mucus.mu_framework.key_signature_utilities.KeySignatureMap;


public class MXML_Part
{

	
	//------------------------
	// MEMBER VARIABLES
	//------------------------

	
	//MXML_Part Attributes
	private String name;
	private List<MXML_Measure> measures;
	private List<Mu> mus;
	private List<VoiceMu> voiceMus;
	private List<Mu> musWithAnnotationsOnly;
	private List<Mu> musWithOtherStuff;
	private ChordList chordListForPrint = null;
//	private int xmlKey;
	private ClefType defaultClef = null;

	
	
	//------------------------
	// CONSTRUCTOR
	//------------------------

	
	


	public MXML_Part(String aName)
	{
		name = aName;
		measures = new ArrayList<MXML_Measure>();
		mus = new ArrayList<Mu>();
		voiceMus = new ArrayList<VoiceMu>();
		musWithAnnotationsOnly = new ArrayList<Mu>();
		musWithOtherStuff = new ArrayList<Mu>();
	}

	
	//------------------------
	// INTERFACE
	//------------------------

	
	public boolean setName(String aName)
	{
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}
	
	
	
	public boolean addMeasure(MXML_Measure aMeasure)
	{
		boolean wasAdded = false;
		wasAdded = measures.add(aMeasure);
		return wasAdded;
	}

	
	
	public boolean removeMeasure(MXML_Measure aMeasure)
	{
		boolean wasRemoved = false;
		wasRemoved = measures.remove(aMeasure);
		return wasRemoved;
	}
	
	
	
	public boolean addMus(Mu aMus)
	{
		boolean wasAdded = false;
		wasAdded = mus.add(aMus);
		if (aMus.isTupletPrintContainer())
		{
			voiceMus.add(new VoiceMuplet(aMus));
		}
		else
		{
			voiceMus.add(new VoiceMu(aMus));
		}
		return wasAdded;
	}
	
	
	
	public void addMuWithOnlyAnnotations(Mu aMu)
	{
		musWithAnnotationsOnly.add(aMu);		
	}
	
	
	
	public void addMusWithOtherNotationIndicators(Mu aMu)
	{
		musWithOtherStuff.add(aMu);
	}

	
	
	public boolean removeMus(Mu aMus)
	{
		boolean wasRemoved = false;
		wasRemoved = mus.remove(aMus);
		return wasRemoved;
	}

	
	
	public String getName()
	{
		return name;
	}
	
	
	
	public MXML_Measure getMeasure(int index)
	{
		MXML_Measure aMeasure = measures.get(index);
		return aMeasure;
	}

	
	
	public MXML_Measure[] getMeasures()
	{
		MXML_Measure[] newMeasures = measures.toArray(new MXML_Measure[measures.size()]);
		return newMeasures;
	}

	
	
	public int numberOfMeasures()
	{
		int number = measures.size();
		return number;
	}

	
	
	public boolean hasMeasures()
	{
		boolean has = measures.size() > 0;
		return has;
	}

	
	
	public int indexOfMeasure(MXML_Measure aMeasure)
	{
		int index = measures.indexOf(aMeasure);
		return index;
	}
	
	
	
	public Mu getMus(int index)
	{
		Mu aMus = mus.get(index);
		return aMus;
	}

	
	
	public Mu[] getMus()
	{
		Mu[] newMus = mus.toArray(new Mu[mus.size()]);
		return newMus;
	}

	
	
	public int numberOfMus()
	{
		int number = mus.size();
		return number;
	}

	
	
	public boolean hasMus()
	{
		boolean has = mus.size() > 0;
		return has;
	}

	
	
	public int indexOfMus(Mu aMus)
	{
		int index = mus.indexOf(aMus);
		return index;
	}
	
	
	
	public VoiceMu getVoiceMus(int index)
	{
		VoiceMu aVoiceMus = voiceMus.get(index);
		return aVoiceMus;
	}

	
	
	public VoiceMu[] getVoiceMus()
	{
		VoiceMu[] newVoiceMus = voiceMus.toArray(new VoiceMu[voiceMus.size()]);
		return newVoiceMus;
	}

	
	
	public int numberOfVoiceMus()
	{
		int number = voiceMus.size();
		return number;
	}

	
	
	public boolean hasVoiceMus()
	{
		boolean has = voiceMus.size() > 0;
		return has;
	}

	
	
	public int indexOfVoiceMus(VoiceMu aVoiceMus)
	{
		int index = voiceMus.indexOf(aVoiceMus);
		return index;
	}

	
	
	public void delete()
	{}

	
	
	public Element getPartListElement(Document document, String partID){
		Element element = document.createElement("score-part");
		element.setAttribute("id", partID);
		Element partName = document.createElement("part-name");
		partName.appendChild(document.createTextNode(getName()));
		element.appendChild(partName);
		return element;
	}

	
	
	public Element getPartElement(Document document, String partID){
		Element element = document.createElement("part");
		element.setAttribute("id", partID);
		for (MXML_Measure m: getMeasures()) 
		{
			element.appendChild(m.getElement(document));
		}
		return element;
	}

	
	
	public void sortVoiceMus(Comparator<VoiceMu> comp){
		Collections.sort(voiceMus, comp);
	}

	
	
	public void makeMeasures(TSMapInterface tsm, KeySignatureMap ksm){
		makeMeasuresFromTimeSignatureZones(tsm, ksm, this);
		addKeySignatures(ksm, this);
		addClefs(this);
		sortVoiceMusIntoVoices(this);	// assigns voice parameter to each VoiceMu
		addVoicesToMeasures(this);		// adds VoiceMus to MXML_measures via addVoiceMu(). Every VoiceMu should get its measure attribute assigned, and every VoiceMu should be split into printable parts
		makeRests(this);				// add in the gaps for each voice
		reTieNotes(this);
		setDivisionForPart(this);
		addMusWithOnlyAnnotations(this);
		addOtherMus(this);
		addChords(this);
		for (MXML_Measure m: getMeasures()) 
		{
			m.pack();
		}
	}

	
	
	private void addClefs(MXML_Part aPart)
	{
		if (aPart.getDefaultClef() != null)
		{
			aPart.getMeasure(0).addMXMLMeasureItemInterface(new MeasureItem_Clef(aPart.getDefaultClef()));
		}	
	}


	
	private void addChords(MXML_Part aPart)
	{
		if (aPart.chordListForPrint != null)
		{
			for (ChordEvent ce: aPart.chordListForPrint.getChordEventList())
			{
				int measureIndex = ce.getPositionInBarsAndBeats().getBarPosition();
				if (aPart.hasMeasure(measureIndex))
				{
					aPart.getMeasure(measureIndex).addChordEvent(ce);
				}
			}
		}
		
	}


	
	private boolean hasMeasure(int measureIndex)
	{
		return measures.size() > measureIndex;
	}


	
	private void addOtherMus(MXML_Part part)
	{
		for (Mu mu: musWithOtherStuff)
		{
			int barIndex = mu.getGlobalPositionInBars();
			MXML_Measure measure = part.getMeasure(barIndex);
			if (mu.hasLeadingDoubleBar())
			{
				measure.addMXMLMeasureItemInterface
				(
						new MeasureItem_BarLine(MXML.BARLINE_LIGHT_LIGHT, MeasureItem_BarLine.Location.LEFT)
				);
			}
			if (mu.hasTag(MuTag.BASS_CLEF))
			{
				measure.addMXMLMeasureItemInterface
				(
						new MeasureItem_Clef(MeasureItem_Clef.ClefType.BASS_CLEF)
				);
			}
			
		}
		
		
		
	}


	// will have to add these MuAnnotations to a list in MXML_Measure along with position information
	private void addMusWithOnlyAnnotations(MXML_Part aPart)
	{
//		for (Mu mu: musWithAnnotationsOnly)
//		{
//			MXML_Measure measure = getMeasureFromGlobalPositionInQuarters(mu.getGlobalPositionInQuarters(), aPart);
//			for (MuAnnotation muan: mu.getMuAnnotations())
//			{
//				measure.addMXMLMeasureItemInterface(makeMeasureItemAnnotation(muan));
//			}
//		}	
		// this is that process
		for (Mu mu: musWithAnnotationsOnly)
		{
			MXML_Measure measure = getMeasureFromGlobalPositionInQuarters(mu.getGlobalPositionInQuarters(), aPart);
			double positionInBar = mu.getGlobalPositionInBarsAndBeats().getOffsetInQuarters();
			for (MuAnnotation muan: mu.getMuAnnotations())
			{
				 measure.addMuAnnotation(positionInBar, muan);
			}
		}
	}


	
//	private MXML_MeasureItemInterface makeMeasureItemAnnotation(MuAnnotation muan)
//	{
//		double fontSize = muan.getFontSize();
//		if (fontSize == -1) fontSize = MXML.textAnnotationFontSize;
//		return new MeasureItem_Annotation(muan.getAnnotation(), muan.getPlacement(), fontSize);
//	}

	

	// divide and conquer search algorithm
	private MXML_Measure getMeasureFromGlobalPositionInQuarters(double aGlobalPositionInQuarters, MXML_Part aPart)
	{
		int start = 0;
		int end = aPart.getMeasures().length;
		double lengthOfMeasuresInQuarters = aPart.getMeasure(aPart.getMeasures().length - 1).getEnd();
		if (aGlobalPositionInQuarters >= 0.0 && aGlobalPositionInQuarters < lengthOfMeasuresInQuarters)
		{
			while (true) 
			{
				int index = (end - start) / 2 + start;	//this is the divide and conquer sorting algorithm
				MXML_Measure m = aPart.getMeasure(index);
				switch (m.getHomeVector(aGlobalPositionInQuarters)) 
				{
				case 0: 
					return m;
				case -1:
					end = index;
					break;
				case 1:
					start = index;
					break;			
				}
			}
		}		
		return null;
	}

	

	public String getTestOutput(){
		String str = "part=" + getName() + "\n";
		for (MXML_Measure m: getMeasures())
		{
			str += m.getTestOutput();
		}		
		return str;	
	}

	

	public String toString()
	{
		return super.toString() + "["+
				"name" + ":" + getName()+ "]";
	}
	
	
	
	//----------------------------------
	// PRIVATES
	//----------------------------------
	
	
	private static void reTieNotes(MXML_Part part)
	{
		for (MXML_Measure m: part.getMeasures()) 
	{
		m.reTieNotes();
	}
	}
	
	
	
	private static void makeRests(MXML_Part part)
	{
		for (MXML_Measure m: part.getMeasures()) 
		{
			m.makeRests();
		}
	}



	private static void addKeySignatures(KeySignatureMap ksm, MXML_Part part) 
	{
	int index = 0;
	for (KeySignatureZone ksz: ksm.getKeySignatureZones()) 
	{
		part.getMeasures()[index].addMXMLMeasureItemInterface(new MeasureItem_KeySignature(ksz.getKey()));
	}	
}



	private static void setDivisionForPart(MXML_Part part) 
	{
	int divisions = getValueForDivisionsAttribute(part);
	part.getMeasures()[0].addMXMLMeasureItemInterface(new MeasureItem_Division(divisions));
	setDivisionsForEachMeasure(part, divisions);
}



	private static void setDivisionsForEachMeasure(MXML_Part part, int divisions)
	{
		for (MXML_Measure m: part.getMeasures()) 
		{
			m.setDivisions(divisions);
		}
	}



	private static int getValueForDivisionsAttribute(MXML_Part part)
	{
		TreeSet<Integer> set = getSetOfDivisions(part);
		return calculateDivisions(set);
	}



	private static int calculateDivisions(TreeSet<Integer> set)
	{
		int divisions = set.last();
		for (Integer i: set.descendingSet())
		{
			if ((double)divisions / i != (double)(divisions / i)) divisions *= i;
		}
		return divisions;
	}



	private static TreeSet<Integer> getSetOfDivisions(MXML_Part part)
	{
		TreeSet<Integer> set = new TreeSet<Integer>();
		for (MXML_Measure m: part.getMeasures()) 
		{
			set.add(m.calculateDivision());
		}
		return set;
	}



	private static void addVoicesToMeasures(MXML_Part part)
	{  
		for (VoiceMu vm: part.getVoiceMus()) 
		{
			assignVoiceMuToMeasure(part, vm);
		}
	}



	private static void assignVoiceMuToMeasure(MXML_Part part, VoiceMu vm)
	{
		int start = 0;
		int end = part.getMeasures().length;
		boolean b = true;
		int debugloopCount = 0;
		while (b) 
		{
//			int index = (int)(Math.round((end - start) / 2.0)) + start;	//this is the divide and conquer sorting algorithm
			if (debugloopCount > 1000) {
				System.out.println("debugloopCount failed on VoiceMu\n" + vm.toString());
				break;
			}
			debugloopCount++;
			int index = (end - start) / 2 + start;
			MXML_Measure m = part.getMeasure(index);
			switch (m.getHomeVector(vm)) 
			{
			case 0: 
				m.addVoiceMu(vm, vm.getVoice());
				b = false;
				break;
			case -1:
				end = index;
				break;
			case 1:
				start = index;
				break;			
			}
		}
	}



	private static void sortVoiceMusIntoVoices(MXML_Part part)
	{
		part.sortVoiceMus(VoiceMu.noteComparator);
		int highestVoice = 1;
		for (int i = 0; i < part.numberOfVoiceMus(); i++) 
		{
			VoiceMu[] vms = part.getVoiceMus();
			ArrayList<VoiceMu> loverlaps = getLowerOverLaps(i, vms);
			ArrayList<VoiceMu> upperlaps = getUpperOverLaps(i, vms);
			VoiceMu note = vms[i];
			for (VoiceMu overlap: loverlaps) 
			{		
				highestVoice = setVoicesOfOverlappedNotes(highestVoice, upperlaps, note, overlap);				
			}
		}
		if (highestVoice > MXML.MAX_NUMBER_OF_VOICES) 
		{
			System.out.println("Voice overrun");
			// deal with too many voices.... probably make another MXML_Part
			// will be messy, but you gotta keep those voices in control
		}
	}



	private static int setVoicesOfOverlappedNotes(int highestVoice, ArrayList<VoiceMu> upperlaps, VoiceMu note,
			VoiceMu overlap)
	{
		if (upperlaps.size() > 0) 
		{
			highestVoice = setVoicesWhereUpperOverlapsExist(highestVoice, note, overlap);
		} 
		else 
		{
			highestVoice = setVoicesWhereNoUpperOverlapsExist(highestVoice, note, overlap);
		}
		return highestVoice;
	}



	private static int setVoicesWhereNoUpperOverlapsExist(int highestVoice, VoiceMu note, VoiceMu overlap)
	{
		overlap.setVoice(1);
		while (note.getVoice() == overlap.getVoice()) 
		{
			highestVoice = setVoicesForOverlappingNotes(highestVoice, overlap);
		}
		return highestVoice;
	}



	private static int setVoicesWhereUpperOverlapsExist(int highestVoice, VoiceMu note, VoiceMu overlap)
	{
		if (note.getVoice() == overlap.getVoice()) 
		{
			highestVoice = setVoicesForOverlappingNotes(highestVoice, overlap);
		}
		return highestVoice;
	}



	private static int setVoicesForOverlappingNotes(int highestVoice, VoiceMu overlap)
	{
		int voice = overlap.getVoice() + 1;
		if (voice > highestVoice) highestVoice = voice;
		overlap.setVoice(voice);
		return highestVoice;
	}



	private static ArrayList<VoiceMu> getLowerOverLaps(int index, VoiceMu[] list)
	{
		VoiceMu note = list[index];
		ArrayList<VoiceMu> overlaps = new ArrayList<VoiceMu>();
		for (int i = index + 1; i < list.length; i++) 
		{
			VoiceMu testNote = list[i];
			if 
			(
					(testNote.getGlobalPositionInQuarters() >= note.getGlobalPositionInQuarters()
							&& testNote.getGlobalPositionInQuarters() < note.getGlobalEnd()) 
					||
					(testNote.getGlobalEnd() > note.getGlobalPositionInQuarters() 
							&& testNote.getGlobalEnd() <= note.getGlobalEnd())
					||
					(testNote.getGlobalPositionInQuarters() <= note.getGlobalPositionInQuarters()
							&& testNote.getGlobalEnd() >= note.getGlobalEnd())
					)
			{
				overlaps.add(testNote);
			}
		}
		return overlaps;
	}



	private static ArrayList<VoiceMu> getUpperOverLaps(int index, VoiceMu[] list)
	{
		VoiceMu note = list[0];
		ArrayList<VoiceMu> overlaps = new ArrayList<VoiceMu>();
		for (int i = 0; i < index; i++) 
		{
			VoiceMu testNote = list[i];
			if 
			(	
					(testNote.getGlobalPositionInQuarters() >= note.getGlobalPositionInQuarters() 
					&& testNote.getGlobalPositionInQuarters() < note.getGlobalEnd()) 
					||
					(testNote.getGlobalEnd() > note.getGlobalPositionInQuarters() 
							&& testNote.getGlobalEnd() <= note.getGlobalEnd()) 
					||
					(testNote.getGlobalPositionInQuarters() <= note.getGlobalPositionInQuarters() 
					&& testNote.getGlobalEnd() >= note.getGlobalEnd())
					)
			{
				overlaps.add(testNote);
			}
		}
		return overlaps;
	}



	private static void makeMeasuresFromTimeSignatureZones(TSMapInterface tsm, KeySignatureMap ksm, MXML_Part part)
	{
		int measureCount = 1;			// NB - this is a notation thing and will not correlate with bar numbers from the BarPositionObject which starts counting at 0;
		MXML_Measure previousMeasure = null;
		for (TimeSignatureZone tsz: tsm.getTimeSignatureZones()) 
		{
			for (int i = 0; i < tsz.getBarCount(); i++) 
			{
				MXML_Measure m = makeNewMeasure(tsm, ksm, measureCount, tsz, i);
				part.addMeasure(m);
				if (previousMeasure != null) previousMeasure.setNextMeasure(m);
				previousMeasure = m;
				measureCount++;
			}
		}
	}



	private static MXML_Measure makeNewMeasure(TSMapInterface tsm, KeySignatureMap ksm, int measureCount,
			TimeSignatureZone tsz, int i)
	{
		MXML_Measure m = new MXML_Measure
				(
						measureCount, 
						ksm.getKey(measureCount - 1),
						tsm.getBarStartInQuarter(measureCount - 1), 
						tsm.getTimeSignature(measureCount - 1).getLengthInQuarters(),
						tsm.getTimeSignature(measureCount - 1)
						);
		if (i == 0) m.addMXMLMeasureItemInterface(new MeasureItem_TimeSignature(tsz.getNumerator(), tsz.getDenominator()));
		return m;
	}


	
	public void addChordListForPrint(ChordList aChordList)
	{
		chordListForPrint  = aChordList;
//		xmlKey = aXmlKey;
	}


	
	public void setDefaultClef(MuTag aMuTag)
	{
		if (aMuTag == MuTag.BASS_CLEF)
		{
			defaultClef  = MeasureItem_Clef.ClefType.BASS_CLEF;
		}	
	}
	
	
	
	public ClefType getDefaultClef()
	{
		return defaultClef;
	}



}