package main.java.com.dougron.mucus.mu_framework.chord_list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.da_utils.chord_progression_analyzer.ChordInKeyObject;
import main.java.da_utils.chord_progression_analyzer.chord_chunk.ChordChunk;
import main.java.da_utils.combo_variables.IntAndString;
import main.java.da_utils.static_chord_scale_dictionary.CSD;
import main.java.da_utils.static_chord_scale_dictionary.CSD.XMLChordElement;
import main.java.da_utils.static_chord_scale_dictionary.ChordToneName;
import main.java.da_utils.static_chord_scale_dictionary.NotePattern;

/**
 * data object fpor ChordList
 * 
 * cobered in testing by ChordListTest
 * 
 * @author dougr
 *
 */
public class Chord
{

	
	
	private String name = "C";		// Default chord
	private NotePattern notePattern;
	private int[] chordTones;
	private int rootIndex = 0;
	private int[] allChordTones;
	private int[] allDiatonicNotes;
	private int[] extendedChordTones;
	private String rootName;
	private ChordChunk associatedChordChunk = null;
	
	
	

	
	public Chord(String aSymbol)
	{
		name = aSymbol;
		init();
	}

	
	
	public Chord()
	{
		init();
	}
	
	
	
	private void init()
	{
		notePattern = CSD.getNotePatternFromChordSymbol(name);
		chordTones = CSD.getChordTones(name);
		allChordTones = makeAllChordTonesAndSort(chordTones);
		if (chordTones.length > 0) rootIndex = chordTones[0]; else rootIndex = -1;
		if (chordTones.length > 0) rootName = makeRootName(); else rootName = "";
		extendedChordTones = CSD.getExtendedChordTones(name);
	}

	
	
	private String makeRootName()
	{
		String rn = "";
		if (name.length() > 2)
		{
			String[] arr = name.split("");
			if (arr[1].equals("b"))
			{
				if (arr[2].equals("b"))
				{
					rn = arr[0] + arr[1] + arr[2];
				}
				else
				{
					rn = arr[0] + arr[1];
				}
			}
			else
			{
				rn = arr[0];
			}
			if (arr[1].equals("#"))
			{
				if (arr[2].equals("#"))
				{
					rn = arr[0] + arr[1] + arr[2];
				}
				else
				{
					rn = arr[0] + arr[1];
				}
			}
			else
			{
				rn = arr[0] + arr[1];
			}
		}
		if (name.length() == 2)
		{
			String[] arr = name.split("");
			if (arr[1].equals("b") || arr[1].equals("#"))
			{
				rn = arr[0] + arr[1];
			}
			else
			{
				rn = arr[0];
			}
		}
		if (name.length() == 1)
		{
			rn = name;
		}
		return rn;
	}



	private int[] makeAllChordTonesAndSort(int[] tones)
	{
		ArrayList<Integer> list = makeListOfAllChordTones(tones);
		Collections.sort(list);
		int[] arr = makeArrayFromList(list);
		return arr;
	}



	private int[] makeArrayFromList(ArrayList<Integer> list)
	{
		return list.stream().mapToInt(i -> i).toArray();
	}



	private ArrayList<Integer> makeListOfAllChordTones(int[] tones)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 13; i++)
		{
			for (int tone: tones)
			{
				int x = i * 12 + tone;
				if (x < 128) list.add(x);
			}
		}
		return list;
	}
	
	
	
	private int[] makeAllDiatonicTones()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		addAllChordTones(list);
		addAllScaleTones(list);
		Collections.sort(list);
		int[] arr = makeArrayFromList(list);
		return arr;
	}



	private void addAllScaleTones(ArrayList<Integer> list)
	{
		for (int tone: associatedChordChunk.getAssociatedCIKO().getModScaleTones())
		{
			for (int i = 0; i < 13; i++)
			{
				int x = i * 12 + tone;
				if (x < 128)
					list.add(x);
			}
		}
	}



	private void addAllChordTones(ArrayList<Integer> list)
	{
		for (int i: allChordTones)
		{
			list.add(i);
		}
	}



	public BarsAndBeats getPositionInBarsAndBeats()
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public String name()
	{
		return name;
	}

	
	
	public int[] getChordTones()
	{
		return chordTones;
	}
	
	
	
	public int[] getExtendedChordTones()
	{
		return extendedChordTones;
	}

	
	
	public void setAssociatedChordChunk(ChordChunk aChordChunk)
	{
		associatedChordChunk = aChordChunk;
		allDiatonicNotes = makeAllDiatonicTones();
	}
	


	public int getClosestChordTone(int aPitch)
	{
		int dist = 127;
		
		int bestOption = 0;
		for (int index = 0; index < allChordTones.length; index++)
		{
			int tempDist = Math.abs(aPitch - allChordTones[index]);
			if (tempDist < dist)
			{
				dist = tempDist;
				bestOption = allChordTones[index];
			}
			else
			{
				return bestOption;
			}
		}
		return bestOption;
	}


	
	private int getClosestChordToneIndex(int aPitch, int[] aAllChordTones)
	{
		int dist = 127;
		
		int bestOption = 0;
		for (int index = 0; index < aAllChordTones.length; index++)
		{
			int tempDist = Math.abs(aPitch - aAllChordTones[index]);
			if (tempDist < dist)
			{
				dist = tempDist;
				bestOption = index;
			}
			else
			{
				return bestOption;
			}
		}
		return bestOption;
	}
	
	
	
	public int getClosestChordTone(int aPitch, int aContour, ChordToneName[] aChordToneNames)
	{
		int[] filteredModChordTones = getListOfIncludedModChordTones(aChordToneNames);
		int[] arr = makeAllChordTonesAndSort(filteredModChordTones);
		return getClosestChordTone(aPitch, aContour, arr);
	}



	public int[] getListOfIncludedModChordTones (
			ChordToneName[] aChordToneNames)
	{
		int[] chordTones = getChordTones();
		ChordToneName[] chordToneNames = notePattern.getChordToneNames();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < chordToneNames.length; i++)
		{
			if (containsChordToneName(aChordToneNames, chordToneNames[i]) 
						&& chordTones.length > i
					)
			{
				list.add(chordTones[i]);
			}
		}
		return list.stream().mapToInt(i -> i).toArray();
	}
	
	
	
	private boolean containsChordToneName (ChordToneName[] aChordToneNames,
			ChordToneName aChordToneName)
	{
		for (ChordToneName name: aChordToneNames)
		{
			if (name == aChordToneName) return true;
		}
		return false;
	}



	public int getClosestChordTone(int aPitch, int aContour)
	{
		return getClosestChordTone(aPitch, aContour, allChordTones);
	}



	private int getClosestChordTone(int aPitch, int aContour, int[] aAllChordTones)
	{
		int closestIndex = getClosestChordToneIndex(aPitch, aAllChordTones);
		int closestChordTone = aAllChordTones[closestIndex];
		if (aContour == 0)
		{
			return closestChordTone;
		}
		if (closestChordTone > aPitch)
		{
			if (aContour > 0)
			{
				int index = closestIndex - 1 + aContour;
				if (index < aAllChordTones.length)
				{
					return aAllChordTones[index];
				}
				else
				{
					return aAllChordTones[aAllChordTones.length - 1];
				}
			}
			else
			{
				int index = closestIndex + aContour;
				if (index > -1)
				{
					return aAllChordTones[index];
				}
				else
				{
					return aAllChordTones[0];
				}
			}
		}
		else if (closestChordTone < aPitch)
		{
			if (aContour > 0)
			{
				int index = closestIndex + aContour;
				if (index < aAllChordTones.length)
				{
					return aAllChordTones[index];
				}
				else
				{
					return aAllChordTones[aAllChordTones.length - 1];
				}
			}
			else
			{
				int index = closestIndex + 1 + aContour;
				if (index > -1)
				{
					return aAllChordTones[index];
				}
				else
				{
					return aAllChordTones[0];
				}
			}
		}
		else if (closestChordTone == aPitch)
		{
			int index = closestIndex + aContour;
			if (index < aAllChordTones.length)
			{
				return aAllChordTones[index];
			}
			else
			{
				return aAllChordTones[aAllChordTones.length - 1];
			}
		}
		return 0;
	}



	public int getRootIndex()
	{
		return rootIndex;
	}
	
	

	public IntAndString getRootNameAndAlteration(int aXmlKey)
	{
		if (rootName.length() == 0)
		{
			return new IntAndString(0, "");
		}
		else
		{
			String name = CSD.noteName(rootIndex, aXmlKey);
			return CSD.getNoteAndAlterationFromSymbol(name);
		}	
	}



	public XMLChordElement[] getXMLChordElements()
	{
		return notePattern.getXMLChordElements();
	}
	
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Chord: " + name + " rootIndex=" + rootIndex + " rootName=" + rootName + " chordTones=");
		for (int i: chordTones)
		{
			sb.append(i + ",");
		}
		sb.append("\n" + notePattern.toString());
		return sb.toString();
	}



	public int getClosestDiatonicNote(int aPitch, int aContour)
	{
		int closestIndex = getClosestDiatonicToneIndex(aPitch);
		int closestDiatonicTone = allDiatonicNotes[closestIndex];
		if (aContour == 0)
		{
			return closestDiatonicTone;
		}
		if (closestDiatonicTone > aPitch)
		{
			if (aContour > 0)
			{
				int index = closestIndex - 1 + aContour;
				if (index < allDiatonicNotes.length)
				{
					return allDiatonicNotes[index];
				}
				else
				{
					return allDiatonicNotes[allChordTones.length - 1];
				}
			}
			else
			{
				int index = closestIndex + aContour;
				if (index > -1)
				{
					return allDiatonicNotes[index];
				}
				else
				{
					return allDiatonicNotes[0];
				}
			}
		}
		else if (closestDiatonicTone < aPitch)
		{
			if (aContour > 0)
			{
				int index = closestIndex + aContour;
				if (index < allDiatonicNotes.length)
				{
					return allDiatonicNotes[index];
				}
				else
				{
					return allDiatonicNotes[allDiatonicNotes.length - 1];
				}
			}
			else
			{
				int index = closestIndex + 1 + aContour;
				if (index > -1)
				{
					return allDiatonicNotes[index];
				}
				else
				{
					return allDiatonicNotes[0];
				}
			}
		}
		else if (closestDiatonicTone == aPitch)
		{
			int index = closestIndex + aContour;
			if (index < allDiatonicNotes.length)
			{
				return allDiatonicNotes[index];
			}
			else
			{
				return allDiatonicNotes[allDiatonicNotes.length - 1];
			}
		}
		return 0;
	}
	
	
	
	private int getClosestDiatonicToneIndex(int aPitch)
	{
		int dist = 127;
		
		int bestOption = 0;
		for (int index = 0; index < allDiatonicNotes.length; index++)
		{
			int tempDist = Math.abs(aPitch - allDiatonicNotes[index]);
			if (tempDist < dist)
			{
				dist = tempDist;
				bestOption = index;
			}
			else
			{
				return bestOption;
			}
		}
		return bestOption;
	}



	// chord tones are the explicit tones defined in the NotePattern
	public boolean isChordTone(int aPitch)
	{
		for (int i: getChordTones())
		{
			if (i == aPitch % 12) return true;
		}
		return false;
	}
	
	
	
	// a utility to assist with identifying notes as chord tones when some notes from the notePattern chord tones must be excluded
	// use cases: bass only using root and fifth, or major 7 chords avoiding the root etc.
	public boolean isChordTone(int aPitch, ChordToneName[] aChordToneNames)
	{
		int[] filteredModChordTones = getListOfIncludedModChordTones(aChordToneNames);
		for (int i: filteredModChordTones)
		{
			if (i == aPitch % 12) return true;
		}
		return false;
	}
	


	// extended chord tones are viable extended harmonic tones. e.g. the 9th on a minor 7th chord
	public boolean isExtendedChordTone(int aPitch)
	{
		for (int i: getExtendedChordTones())
		{
			if (i == aPitch % 12) return true;
		}
		return false;
	}



	// scale tone is the non chord tones from the mode related to the chord
	public boolean isScaleTone(int aPitch)
	{
		aPitch = aPitch % 12;
		int[] arr = associatedChordChunk.getAssociatedCIKO().getModScaleTones();
		for (int i: arr)
		{
			if (i == aPitch) return true;
		}
		return false;
	}



	public boolean isNonScaleTone(int aPitch)
	{
		// scale tone (and hence non scale tone) should relate to the general key area
		// extended chord tones could be non scale tones - well actually it does not
		// it works with the chord related mode
		aPitch = aPitch % 12;
		int[] arr = associatedChordChunk.getAssociatedCIKO().getModNonTones();
		for (int i: arr)
		{
			if (i == aPitch) return true;
		} 
		return false;
	}



	public String getChordAnalysisString()
	{
		if (associatedChordChunk == null)
		{
			return "";
		}
		else
		{
			return associatedChordChunk.getAssociatedCIKO().toStringKeyChordAndSimpleFunction();
		}
	}



	public ChordInKeyObject getAssociatedChordInKeyObject ()
	{
		return associatedChordChunk.getAssociatedCIKO();
	}

	
	
	public NotePattern getNotePattern()
	{
		return notePattern;
	}
}
