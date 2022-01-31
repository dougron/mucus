/*CODE CAN BE EDITED - Umple no longer used*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Annotation;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Backup;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_ChordSymbol;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Forward;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Note;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_Rest;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_TupletNote;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.measure_item.MeasureItem_TupletRest;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.note_split_algorithm.MXML_NoteSplitAlgorithm;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu.VoiceMu;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu.VoiceMuRest;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu.VoiceMuplet;
import main.java.da_utils.combo_variables.DoubleAndDouble;
import main.java.da_utils.time_signature_utilities.greatest_common_factor_calculator.GreatestCommonFactor;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

// line 5 "../musicxml_measure.ump"
public class MXML_Measure
{

	//------------------------
	// STATIC VARIABLES
	//------------------------


	/**
	 * values for type of MXML_MeasureItemInterface subclass
	 */
	public static final int IS_ATTRIBUTE = 0;
	public static final int IS_NOTE = 1;
	public static final int IS_REST = 2;


	//------------------------
	// MEMBER VARIABLES
	//------------------------


	//MXML_Measure Attributes
	private int measureNumber;
	private int prevailingXMLKey;
	private double positionInQuarters;
	private double lengthInQuarters;
	private MXML_Measure nextMeasure;
	private int divisions;
	private HashMap<Integer, ArrayList<VoiceMu>> vmMap;
	private TimeSignature timeSignature;

	//MXML_Measure Associations
	private List<MXML_MeasureItemInterface> mXMLMeasureItemInterfaces;
	private ArrayList<ChordEvent> chordEventList = new ArrayList<ChordEvent>();
	private TreeMap<Double, ArrayList<MuAnnotation>> annotationMap;


	//------------------------
	// CONSTRUCTOR
	//------------------------


	public MXML_Measure(int aMeasureNumber, int aPrevailingXMLKey, double aPositionInQuarters, double aLengthInQuarters, TimeSignature aTimeSignature)
	{
		measureNumber = aMeasureNumber;
		prevailingXMLKey = aPrevailingXMLKey;
		positionInQuarters = aPositionInQuarters;
		lengthInQuarters = aLengthInQuarters;
		nextMeasure = null;
		timeSignature = aTimeSignature;
		mXMLMeasureItemInterfaces = new ArrayList<MXML_MeasureItemInterface>();
		vmMap = new HashMap<Integer, ArrayList<VoiceMu>>();
	}



	//------------------------
	// INTERFACE
	//------------------------


	public boolean setMeasureNumber(int aMeasureNumber)
	{
		boolean wasSet = false;
		measureNumber = aMeasureNumber;
		wasSet = true;
		return wasSet;
	}



	public boolean setPrevailingXMLKey(int aPrevailingXMLKey)
	{
		boolean wasSet = false;
		prevailingXMLKey = aPrevailingXMLKey;
		wasSet = true;
		return wasSet;
	}



	public boolean setPositionInQuarters(double aPositionInQuarters)
	{
		boolean wasSet = false;
		positionInQuarters = aPositionInQuarters;
		wasSet = true;
		return wasSet;
	}



	public boolean setLengthInQuarters(double aLengthInQuarters)
	{
		boolean wasSet = false;
		lengthInQuarters = aLengthInQuarters;
		wasSet = true;
		return wasSet;
	}



	public boolean setNextMeasure(MXML_Measure aNextMeasure)
	{
		boolean wasSet = false;
		nextMeasure = aNextMeasure;
		wasSet = true;
		return wasSet;
	}



	public boolean setDivisions(int aDivisions)
	{
		boolean wasSet = false;
		divisions = aDivisions;
		wasSet = true;
		return wasSet;
	}



	/**
	 * measure number for .musicxml. start count at 1
	 */
	public int getMeasureNumber()
	{
		return measureNumber;
	}



	public int getPrevailingXMLKey()
	{
		return prevailingXMLKey;
	}



	public double getPositionInQuarters()
	{
		return positionInQuarters;
	}



	public double getLengthInQuarters()
	{
		return lengthInQuarters;
	}



	public MXML_Measure getNextMeasure()
	{
		return nextMeasure;
	}



	/**
	 * this is set during the makeNotes() call;
	 */
	public int getDivisions()
	{
		return divisions;
	}



	public TimeSignature getTimeSignature()
	{
		return timeSignature;
	}



	public MXML_MeasureItemInterface getMXMLMeasureItemInterface(int index)
	{
		MXML_MeasureItemInterface aMXMLMeasureItemInterface = mXMLMeasureItemInterfaces.get(index);
		return aMXMLMeasureItemInterface;
	}



	public List<MXML_MeasureItemInterface> getMXMLMeasureItemInterfaces()
	{
		List<MXML_MeasureItemInterface> newMXMLMeasureItemInterfaces = Collections.unmodifiableList(mXMLMeasureItemInterfaces);
		return newMXMLMeasureItemInterfaces;
	}



	public int numberOfMXMLMeasureItemInterfaces()
	{
		int number = mXMLMeasureItemInterfaces.size();
		return number;
	}



	public boolean hasMXMLMeasureItemInterfaces()
	{
		boolean has = mXMLMeasureItemInterfaces.size() > 0;
		return has;
	}



	public int indexOfMXMLMeasureItemInterface(MXML_MeasureItemInterface aMXMLMeasureItemInterface)
	{
		int index = mXMLMeasureItemInterfaces.indexOf(aMXMLMeasureItemInterface);
		return index;
	}



	public static int minimumNumberOfMXMLMeasureItemInterfaces()
	{
		return 0;
	}



	public boolean addMXMLMeasureItemInterface(MXML_MeasureItemInterface aMXMLMeasureItemInterface)
	{
		boolean wasAdded = false;
		if (mXMLMeasureItemInterfaces.contains(aMXMLMeasureItemInterface)) { return false; }
		mXMLMeasureItemInterfaces.add(aMXMLMeasureItemInterface);
		wasAdded = true;
		return wasAdded;
	}



	public boolean removeMXMLMeasureItemInterface(MXML_MeasureItemInterface aMXMLMeasureItemInterface)
	{
		boolean wasRemoved = false;
		if (mXMLMeasureItemInterfaces.contains(aMXMLMeasureItemInterface))
		{
			mXMLMeasureItemInterfaces.remove(aMXMLMeasureItemInterface);
			wasRemoved = true;
		}
		return wasRemoved;
	}



	public boolean addMXMLMeasureItemInterfaceAt(MXML_MeasureItemInterface aMXMLMeasureItemInterface, int index)
	{  
		boolean wasAdded = false;
		if(addMXMLMeasureItemInterface(aMXMLMeasureItemInterface))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfMXMLMeasureItemInterfaces()) { index = numberOfMXMLMeasureItemInterfaces() - 1; }
			mXMLMeasureItemInterfaces.remove(aMXMLMeasureItemInterface);
			mXMLMeasureItemInterfaces.add(index, aMXMLMeasureItemInterface);
			wasAdded = true;
		}
		return wasAdded;
	}



	public boolean addOrMoveMXMLMeasureItemInterfaceAt(MXML_MeasureItemInterface aMXMLMeasureItemInterface, int index)
	{
		boolean wasAdded = false;
		if(mXMLMeasureItemInterfaces.contains(aMXMLMeasureItemInterface))
		{
			if(index < 0 ) { index = 0; }
			if(index > numberOfMXMLMeasureItemInterfaces()) { index = numberOfMXMLMeasureItemInterfaces() - 1; }
			mXMLMeasureItemInterfaces.remove(aMXMLMeasureItemInterface);
			mXMLMeasureItemInterfaces.add(index, aMXMLMeasureItemInterface);
			wasAdded = true;
		} 
		else 
		{
			wasAdded = addMXMLMeasureItemInterfaceAt(aMXMLMeasureItemInterface, index);
		}
		return wasAdded;
	}



	public void delete()
	{
		mXMLMeasureItemInterfaces.clear();
	}



	public ArrayList<VoiceMu> getFromVMMap(int index){
		return vmMap.get(index);
	}



	public boolean vmMapContainsKey(int index){
		return vmMap.containsKey(index);
	}



	public boolean putInVmMap(int aKey, ArrayList<VoiceMu> aList)
	{
		boolean wasSet = false;
		vmMap.put(aKey, aList);
		wasSet = true;
		return wasSet;
	}

	

	public void sortVmMapItem(int aKey, Comparator<VoiceMu> aComparator)
	{
		if (vmMap.containsKey(aKey)) Collections.sort(vmMap.get(aKey), aComparator);
	}

	
	
	public void addVoiceMu(VoiceMu vm, int voiceIndex)
	{
		vm.setMeasure(this);
		ArrayList<VoiceMu> vms = MXML_NoteSplitAlgorithm.splitVoiceMu(vm, this);
    	for (VoiceMu mv: vms)
    	{
    		addToVMMapOrPassToNextMeasure(voiceIndex, mv); 	    	
    	}					
		sortVmMapItem(voiceIndex, VoiceMu.posComparator);
	}




	public void reTieNotes()
	{
		for (Integer voice: getVmMap().keySet())
  		{
  			ArrayList<VoiceMu> list = MXML_NoteSplitAlgorithm.reTieNotes(getFromVMMap(voice), this);
  			Collections.sort(list, VoiceMu.posComparator);  			
  			putInVmMap(voice, list);
  		}
	}



	public void makeRests()
	{
		ArrayList<VoiceMu> restList = new ArrayList<VoiceMu>();
		if (getVmMap().size() == 0)		// if bar is empty, voice 1 is added and a whole bar rest added. 
		{
			ArrayList<VoiceMu> tempList = new ArrayList<VoiceMu>();
			VoiceMuRest vmr = makeWholeBarRestInVoiceOne();
			tempList.add(vmr);
			putInVmMap(1, tempList);			
		}
		for (int voice: getVmMap().keySet())
		{
			ArrayList<VoiceMu> list = getFromVMMap(voice);
			restList = makeRestList(voice, list, this);
			list.addAll(restList);
			Collections.sort(list, VoiceMu.posComparator);
		}
	}



	private VoiceMuRest makeWholeBarRestInVoiceOne()
	{
		VoiceMuRest vmr = new VoiceMuRest(null, getPositionInQuarters(), getLengthInQuarters());
		vmr.setMeasure(this);
		vmr.setVoice(1);
		vmr.setIsWholeBarRest(true);
		return vmr;
	}



	/**
	 * packs the VoiceMus into the mXMLMeasureInterface
	 */
	public void pack(){
		packVoiceMus();
		// addChordSymbols ---
		if (chordEventList.size() > 0)
		{
			makeBackup((int)Math.round(getLengthInQuarters() * getDivisions()), this);
			double pos = 0.0;
			for (ChordEvent ce: chordEventList)
			{
				double positionInBar = ce.getPositionInBarsAndBeats().getOffsetInQuarters();
				if (positionInBar - pos > 0) makeForward((int)Math.round((positionInBar - pos) * getDivisions()), this);
				addChordMeasureItem(ce);
				pos += positionInBar;
			}
			if (Math.abs(pos - getLengthInQuarters()) > 1e-8)
			{
				makeForward((int)Math.round((getLengthInQuarters() - pos) * getDivisions()), this);
			}
		}
		
		// addMuAnnotations
		if (annotationMap != null && annotationMap.size() > 0)
		{
			makeBackup((int)Math.round(getLengthInQuarters() * getDivisions()), this);
			double pos = 0.0;
			for (Double positionInBar: annotationMap.keySet())
			{
				
//				double positionInBar = ce.getPositionInBarsAndBeats().getOffsetInQuarters();
				if (positionInBar - pos > 0) makeForward((int)Math.round((positionInBar - pos) * getDivisions()), this);
				for (MuAnnotation muan: annotationMap.get(positionInBar))
				{
					addMXMLMeasureItemInterface(getAnnotationMeasureItem(muan));
					pos += positionInBar;
				}				
			}
			if (Math.abs(pos - getLengthInQuarters()) > 1e-8)
			{
				makeForward((int)Math.round((getLengthInQuarters() - pos) * getDivisions()), this);
			}
		}
		
	}



	private MXML_MeasureItemInterface getAnnotationMeasureItem(MuAnnotation muan)
	{
		double fontSize = muan.getFontSize();
		if (fontSize == -1) fontSize = MXML.textAnnotationFontSize;
		return new MeasureItem_Annotation(muan.getAnnotation(), muan.getPlacement(), fontSize);	
	}



	private void packVoiceMus()
	{
		boolean makeBackup = false;
		for (Integer voice: getVmMap().keySet()) 
		{	
			if (makeBackup) makeBackup((int)Math.round(getLengthInQuarters() * getDivisions()), this);
			makeBackup = true;	// wont do it first time round, or if there is only one voice
	   		ArrayList<VoiceMu> vms = getFromVMMap(voice);
	   		for (VoiceMu vm: vms) 
	   		{
				if (vm instanceof VoiceMuRest) 
				{
					castToVoiceMuRestAndAddAsMeasureItemRest(voice, vm);
				} 
				else 
				{
					addAnnotationsToFirstItemAndAddAllMeasureItemNotes(voice, vm);
				}
			}
		}
	}



	private void addChordMeasureItem(ChordEvent ce)
	{
		addMXMLMeasureItemInterface(new MeasureItem_ChordSymbol(ce, prevailingXMLKey));
		
	}



	private void addAnnotationsToFirstItemAndAddAllMeasureItemNotes(Integer voice, VoiceMu vm)
	{
		MXML_MeasureItemInterface[] mins = getMeasureItemNote(this, voice, vm);
		for (MuAnnotation muan: vm.getMuAnnotations())
		{
			addItemToAnnotationList(mins[0], muan);
		}
		for (MXML_MeasureItemInterface min: mins)
		{
			addMXMLMeasureItemInterface(min);
		}
	}



	private void castToVoiceMuRestAndAddAsMeasureItemRest(Integer voice, VoiceMu vm)
	{
		VoiceMuRest vmr = (VoiceMuRest)vm;
		addMXMLMeasureItemInterface(new MeasureItem_Rest(
			vm.getPositionInBar(), 
			vm.getLengthInQuarters(), 
			vmr.getIsWholeBarRest(), 
			voice, 
			this));
	}

	
	
	public void makeBackup(int divisionCount, MXML_Measure measure)
	{
		measure.addMXMLMeasureItemInterface(new MeasureItem_Backup(divisionCount));
	}
	
	
	
	public void makeForward(int divisionCount, MXML_Measure measure)
	{
		measure.addMXMLMeasureItemInterface(new MeasureItem_Forward(divisionCount));
	}


	
	public int calculateDivision()
	{
		if (getVmMap().size() == 0)
		{
			return 1;
		}
		else 
		{
			TreeSet<Integer> set = makeSetForGreatestCommonFactorCalculation();
			return GreatestCommonFactor.lcm(set.toArray(new Integer[set.size()]));
		}   	
	}



	private TreeSet<Integer> makeSetForGreatestCommonFactorCalculation()
	{
		TreeSet<Integer> set  = new TreeSet<Integer>();
	  	for (Integer voice: getVmMap().keySet()) 
	  	{
		  	addStartAndEndPointsRelevantToDivisionCalculation(set, voice);
	  	}
		return set;
	}



	private void addStartAndEndPointsRelevantToDivisionCalculation(TreeSet<Integer> set, Integer voice)
	{
		ArrayList<VoiceMu> vms = getFromVMMap(voice);
		for (VoiceMu vm: vms) 
		{
			if (vm instanceof VoiceMuplet)
			{
				addRelevantDivisionPointsFromVoiceMuplet(set, vm);
			}
			addRelevantStartPositionsFromVoiceMu(set, vm);
			addRelevantEndPositionsFromVoiceMu(set, vm);
		}
	}



	private void addRelevantEndPositionsFromVoiceMu(TreeSet<Integer> set, VoiceMu vm)
	{
		if (vm.getEndPositionInBar() % 1.0 == 0.0) 
		{
			set.add(1);
		} else 	
		{
			int div = getDiv(vm.getEndPositionInBar());
			set.add(div);
		}
	}



	private void addRelevantStartPositionsFromVoiceMu(TreeSet<Integer> set, VoiceMu vm)
	{
		if (vm.getPositionInBar() % 1.0 == 0.0) 
		{
			set.add(1);
		} 
		else 
		{
			int div = getDiv(vm.getPositionInBar());
			set.add(div);
		}
	}



	private void addRelevantDivisionPointsFromVoiceMuplet(TreeSet<Integer> set, VoiceMu vm)
	{
		for (Double x: ((VoiceMuplet)vm).getSetOfOnsAndOffs())
		{
			if (x % 1.0 == 0.0) 
			{
		   		set.add(1);
			} 
			else 
			{
		   		int div = getDiv(x);
		   		set.add(div);
			}
		}
	}



	public Element getElement(Document document){
		Element element = document.createElement("measure");
		element.setAttribute("number", "" + getMeasureNumber());
		//  	element.setAttribute("width", "1");		// 1 might create very narrow bars, actually has no effect at all, and does not stop the error when opening a file in musescore
		Element attributes = null;
		ArrayList<Element> noteElements = new ArrayList<Element>();
		attributes = addMeasureItemsToAttributesAndOrNoteElements(document, attributes, noteElements);
		if (attributes != null) element.appendChild(attributes);
		for (Element el: noteElements) 
		{
			element.appendChild(el);
		}
		return element;
	}



	private Element addMeasureItemsToAttributesAndOrNoteElements(Document document, Element attributes,
			ArrayList<Element> noteElements)
	{
		for (MXML_MeasureItemInterface mii: getMXMLMeasureItemInterfaces()) 
		{
			switch (mii.getType()) 
			{
			case MXML_Measure.IS_ATTRIBUTE:
				if (attributes == null) attributes = document.createElement("attributes");
				for (Element el: mii.getElement(document)) 
				{
					attributes.appendChild(el);
				}
				break;
			case MXML_Measure.IS_NOTE:
//				System.out.println(mii.getLength() + mii.getClass().descriptorString());
				for (Element el: mii.getElement(document)) 
				{
					noteElements.add(el);
				}
				break;
			}
		}
		return attributes;
	}



	/**
	 * returns direction for home bar of VoiceMu. -1 - before, 1 - after, 0 - this is it
	 */
	public int getHomeVector(VoiceMu vm){
		return getHomeVector(vm.getGlobalPositionInQuarters());
	}
	
	
	
	int getHomeVector(double aPositionInQuarters)
	{
		if (aPositionInQuarters < positionInQuarters) return -1;
		if (aPositionInQuarters >= getEnd()) return 1;
		return 0;
	}



	public double getEnd(){
		return positionInQuarters + lengthInQuarters;
	}



	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("MXML_Measure=" + getMeasureNumber() + " pos=" + getPositionInQuarters() + " len=" + getLengthInQuarters() + "\n");
		sb.append("divisions=" + getDivisions() + "\n");
		if (getNextMeasure() != null) sb.append("nextmeasureNumber=" + getNextMeasure().getMeasureNumber() + "\n");
		sb.append("mXMLMeasureItemInterfaces has " + numberOfMXMLMeasureItemInterfaces() + " items\n");
		sb.append("vmMap:-----\n");
		for (Integer key: getVmMap().keySet())
		{
			sb.append("  voice=" + key + ":" + getFromVMMap(key).toString() + "\n");
		}
		return sb.toString();
	}



	public String getTestOutput(){
		StringBuilder sb = new StringBuilder();
		sb.append("measure=" + getMeasureNumber() + "\n");
		for (MXML_MeasureItemInterface mii: getMXMLMeasureItemInterfaces())
		{
			sb.append(mii.getTestOutput());
		}
		return sb.toString();
	}



	public HashMap<Integer, ArrayList<VoiceMu>> getVmMap () 
	{
		return vmMap;
	}

	
	//---------------------------------
	// PRIVATES
	//---------------------------------
	
	
	
	private void addToVMMapOrPassToNextMeasure(int voiceIndex, VoiceMu mv)
	{
		if (mv.getPositionInBar() >= getLengthInQuarters())
		{
			passToNextMeasure(voiceIndex, mv);
		}
		else
		{
			addToVMMap(voiceIndex, mv);
		}
	}



	private void addToVMMap(int voiceIndex, VoiceMu mv)
	{
		if (!vmMapContainsKey(voiceIndex))
		{
			putInVmMap(voiceIndex, new ArrayList<VoiceMu>());
		}
		getFromVMMap(voiceIndex).add(mv);
	}



	private void passToNextMeasure(int voiceIndex, VoiceMu mv)
	{
		if (getNextMeasure() != null)
		{
			getNextMeasure().addVoiceMu(mv, voiceIndex); // this means that if a note runs over the end of the TimeSignatureMap, it will be dropped
		}
		else
		{
			// deal with notes that overflow the end of the piece. Add another measure, or make sure this never happens - not clear yet
		}
	}


	
	private static int getDiv(double d)
	{
		int i = 1;
		double sum = d;
		while (!closeEnoughToRounded(sum, 0.01))
		{
			sum += d;
			i++;
		}
		return i;
	}
	
	
	
	/* calculates whether a double is close enough to its rounded version to be within the errorMargin */
	
	private static boolean closeEnoughToRounded(double sum, double errorMargin)
	{
		double round = Math.round(sum);
		if (Math.abs(round - sum) / round <= errorMargin)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}


	
	private static MXML_MeasureItemInterface[] getMeasureItemNote(MXML_Measure measure, Integer voice, VoiceMu vm) 
	{
		if (vm instanceof VoiceMuplet)
		{
			return getMeasureItemTupletNotes(measure, voice, vm);
		}
		else 
		{
			return getMeasureItemNotes(measure, voice, vm);
		}
		
	}
	
	
	
	private static MXML_MeasureItemInterface[] getMeasureItemTupletNotes(MXML_Measure measure, Integer voice, VoiceMu vm)
	{
		boolean startTupletNotation = true;
		List<MXML_MeasureItemInterface> list = new ArrayList<MXML_MeasureItemInterface>();
		List<Mu> mus = vm.getMus();
		DoubleAndDouble[] rests = ((VoiceMuplet)vm).getRests();		// potential error: this does assume they are in order: most likely created in order but not sorted at any point
		int muIndex = 0;
		int restIndex = 0;
		boolean hasMusLeft = true;
		if (mus.size() == 0) hasMusLeft = false;
		boolean hasRestsLeft = true;
		if (rests.length == 0) hasRestsLeft = false;
		int actual = ((VoiceMuplet)vm).getTupletActualNotes();
		int normal = ((VoiceMuplet)vm).getTupletNormalNotes();
		boolean hasBracket = true;
//		if (vm.getLengthInQuarters() / normal < 1.0) hasBracket = true;		// personally, I always want a bracket on a tuplet
		while (hasMusLeft || hasRestsLeft)
		{
			double muPos = 10000000;
			double restPos = 10000000;	// arbitarily fukkin large;
			if (hasMusLeft) muPos = mus.get(muIndex).getPositionInBarsAndBeats().getOffsetInQuarters();
			if (hasRestsLeft) restPos = rests[restIndex].getD1();
			
			if (restPos < muPos)
			{
				MeasureItem_TupletRest mitr = makeTupletRest(measure, voice, vm, startTupletNotation, rests, restIndex,
						actual, normal, hasBracket);
				list.add(mitr);
				startTupletNotation = false;		// only the first item in a tuplet needs this
				restIndex++;
				if (restIndex >= rests.length) hasRestsLeft = false;
			}
			else 
			{
				MeasureItem_TupletNote mitn = makeTupletNoteAndAddMuNotesAndAnnotations(measure, voice, vm,
						startTupletNotation, mus, muIndex, actual, normal, hasBracket);
				list.add(mitn);
				startTupletNotation = false;		// only the first item in a tuplet needs this
				muIndex++;
				if (muIndex >= mus.size()) hasMusLeft = false;
			}
		}
		if (hasBracket) setFlagForEndOfTupletBracketNotationToLastItem(list);
		return list.toArray(new MXML_MeasureItemInterface[list.size()]);
	}



	private static void setFlagForEndOfTupletBracketNotationToLastItem(List<MXML_MeasureItemInterface> list)
	{
		MXML_MeasureItemInterface mii = list.get(list.size() - 1);
		if (mii instanceof MeasureItem_TupletNote)
		{
			((MeasureItem_TupletNote)mii).setHasEndOfTupletNotationElement(true);
		}
		if (mii instanceof MeasureItem_TupletRest)
		{
			((MeasureItem_TupletRest)mii).setHasEndOfTupletNotationElement(true);
		}
	}



	private static MeasureItem_TupletRest makeTupletRest(
			MXML_Measure measure, 
			Integer voice, 
			VoiceMu vm,
			boolean startTupletNotation, 
			DoubleAndDouble[] rests, 
			int restIndex, 
			int actual, 
			int normal,
			boolean hasBracket
			)
	{
		DoubleAndDouble dnd = rests[restIndex];
		MeasureItem_TupletRest mitr = new MeasureItem_TupletRest(
						vm.getPositionInBar() + dnd.getD1(), 
						dnd.getD2(), 
						false, 				// not sure if there absolutely won't be a full bar rest, but extrememly unlikely
						voice, 
						measure, 
						actual, 
						normal, 
						startTupletNotation, 
						hasBracket
					);
		return mitr;
	}



	private static MeasureItem_TupletNote makeTupletNoteAndAddMuNotesAndAnnotations(
			MXML_Measure measure, 
			Integer voice,
			VoiceMu vm, 
			boolean startTupletNotation, 
			List<Mu> mus, 
			int muIndex, 
			int actual, 
			int normal,
			boolean hasBracket
			)
	{
		Mu mu = mus.get(muIndex);								
		MeasureItem_TupletNote mitn = new MeasureItem_TupletNote(
						vm.getPositionInBar() + mu.getPositionInBarsAndBeats().getOffsetInQuarters(),
						mu.getLengthInQuarters(),
						voice,
						measure,
						actual,
						normal,
						startTupletNotation,
						hasBracket
				);
		addMuNoteToTupletNote(mu, mitn);
		addMuAnnotationsToTupletNote(mu, mitn);
		return mitn;
	}



	private static void addMuAnnotationsToTupletNote(Mu mu, MeasureItem_TupletNote mitn)
	{
		for (MuAnnotation muan: mu.getMuAnnotations())
		{
			addItemToAnnotationList(mitn, muan);
		}
	}



	private static void addMuNoteToTupletNote(Mu mu, MeasureItem_TupletNote mitn)
	{
		for (MuNote mn: mu.getMuNotes()) 
		{
			mitn.addNote(mn.getPitch());
		}
	}
	
	
	
	private static MeasureItem_Note[] getMeasureItemNotes(MXML_Measure measure, Integer voice, VoiceMu vm)
	{
		MeasureItem_Note min = new MeasureItem_Note
				(
					BigDecimal.valueOf(vm.getPositionInBar()).setScale(VoiceMu.getRoundingAccuracy(), RoundingMode.DOWN).doubleValue(),
					BigDecimal.valueOf(vm.getLengthInQuarters()).setScale(VoiceMu.getRoundingAccuracy(), RoundingMode.DOWN).doubleValue(),				   	 
				   	voice, 
				   	measure
			   	);
		for (MuNote mn: vm.getMuNotes()) 
		{
			min.addNote(mn.getPitch());
		}
		min.setHasTieEnd(vm.getHasTieEnd());
		min.setHasTieStart(vm.getHasTieStart());
		return new MeasureItem_Note[] {min};
	}
	
	
	
	private static void addItemToAnnotationList(MXML_MeasureItemInterface mii, MuAnnotation muan)
	{
		MeasureItem_Annotation mia = new MeasureItem_Annotation(muan.getAnnotation(), muan.getPlacement(), muan.getFontSize());
		if (mii instanceof MeasureItem_Note) ((MeasureItem_Note)mii).addMeasureItemAnnotation(mia);
		if (mii instanceof MeasureItem_Rest) ((MeasureItem_Rest)mii).addMeasureItemAnnotation(mia);
	}
	
	
	
	private ArrayList<VoiceMu> makeRestList(int voice, ArrayList<VoiceMu> list, MXML_Measure measure)
	{
		ArrayList<VoiceMu> restList = new ArrayList<VoiceMu>(); 		
		double pos = 0.0;
		for (VoiceMu vm: list)
		{
			if (vm instanceof VoiceMuplet)
			{
				((VoiceMuplet)vm).makeRests();
			}
			//System.out.println("measure=" + measureNumber + ": " + vm.getPosition());
			double restLength = vm.getPositionInBar() - pos;
			if (restLength > 0.0) 
			{
				VoiceMuRest newvmr = makeVoiceMuRest(measure.getPositionInQuarters() + pos, restLength, voice, measure);
				ArrayList<VoiceMu> vmrs = MXML_NoteSplitAlgorithm.splitVoiceMuRest(newvmr, measure);
				restList.addAll(vmrs);
			}
			pos += restLength + vm.getLengthInQuarters();			
		}
		double restLength = measure.getEnd() - measure.getPositionInQuarters() - pos;
		if (restLength > 0.0)	
		{
			VoiceMuRest newvmr = makeVoiceMuRest(measure.getPositionInQuarters() + pos, restLength, voice, measure);
			ArrayList<VoiceMu> vmrs = MXML_NoteSplitAlgorithm.splitVoiceMuRest(newvmr, measure);
			restList.addAll(vmrs);
		}
		return restList;
	}



	private VoiceMuRest makeVoiceMuRest(double pos, double len, int voice, MXML_Measure measure) 
	{
		VoiceMuRest vmr = new VoiceMuRest(null, pos, len);
		vmr.setVoice(voice);
		vmr.setMeasure(measure);
		return vmr;
	}



	public void addChordEvent(ChordEvent aChordEvent)
	{
		chordEventList .add(aChordEvent);		
	}



	public void addMuAnnotation(double aPositionInBar, MuAnnotation aMuAnnotation)
	{
		if (annotationMap == null) annotationMap = new TreeMap<Double, ArrayList<MuAnnotation>>();
		if (!annotationMap.containsKey(aPositionInBar)) annotationMap.put(aPositionInBar, new ArrayList<MuAnnotation>());
		annotationMap.get(aPositionInBar).add(aMuAnnotation);
	}

}