package main.java.com.dougron.mucus.mu_framework.chord_list;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import main.java.da_utils.combo_variables.DoubleAndString;
import main.java.da_utils.time_signature_utilities.time_signature.SuperTactus;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class FloatBarChordProgression implements ChordListGenerator
{

	
	double lengthInFloatBars;
	private List<DoubleAndString> chordPositionAndSymbolList = new ArrayList<DoubleAndString>();
	Object [] positionInFloatBarsCombosAndChordName;

	
	public FloatBarChordProgression(double aLengthInFloatBars, Object[] positionInFloatBarsCombosAndChordName)
	{
		this.positionInFloatBarsCombosAndChordName = positionInFloatBarsCombosAndChordName;
		lengthInFloatBars = aLengthInFloatBars;
		for (int i = 0; i < positionInFloatBarsCombosAndChordName.length; i += 2)
		{
			double pos = (double)positionInFloatBarsCombosAndChordName[i];
			String name = (String)positionInFloatBarsCombosAndChordName[i + 1];
			chordPositionAndSymbolList.add(new DoubleAndString(pos, name));
		}
	}
	


	@Override
	public String chordsToString ()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < chordPositionAndSymbolList.size(); i = i + 2)
		{
			sb.append(chordPositionAndSymbolList.get(i) + "_");
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
//		int lengthInBars = tsl.getLengthInBarsAndBeats().getBarPosition();
		double cycle = 0.0;
		double currentPos = 0.0;
		int index = 0;
		while (currentPos < barCount)
		{
			DoubleAndString positionInFloatBars = chordPositionAndSymbolList.get(index);
			currentPos = cycle + positionInFloatBars.d;
			Double floatPositionInBar = positionInFloatBars.d % 1.0;
			TimeSignature ts = tsgm.getTimeSignature((int)currentPos);
			double barLengthInQuarters = ts.getLengthInQuarters();
			double positionInBarInQuarters = barLengthInQuarters * floatPositionInBar;
			positionInBarInQuarters = getClosestTactusOrSuperTactus(positionInBarInQuarters, ts);
			int barIndex = (int)currentPos;
			cl.addChord(new Chord(positionInFloatBars.str), new BarsAndBeats(barIndex, positionInBarInQuarters), tsgm);		
			
			index++;
			if (index == chordPositionAndSymbolList.size())
			{
				index = 0;
				cycle += lengthInFloatBars;
				currentPos = cycle;
			}
		}
		cl.refreshChordAnalysis();
		return cl;
	}
	
	
	
	private static double getClosestTactusOrSuperTactus(double positionInBarsInQuarters, TimeSignature ts)
	{
		TreeMap<Integer, ArrayList<SuperTactus>> superTacMap = ts.getSuperTacMap();
		Double[] tactii = ts.getTactus();
		TreeMap<Integer, Double[]> allOptions = makeAllOptions(superTacMap, tactii);
		double distance = 1000.0;				// arbitrarily large
		List<Double> possibleTactii = new ArrayList<Double>();
	
		for (Integer key: allOptions.descendingKeySet())
		{
//			System.out.println("key=" + key);
			Double[] arr = allOptions.get(key);
			distance = iterateBackwardsThroughArrAndCalculateDistance(positionInBarsInQuarters, distance,
					possibleTactii, arr);
		}
		return possibleTactii.get(0);
	}


	
	private static double iterateBackwardsThroughArrAndCalculateDistance(double positionInBarsInQuarters,
			double distance, List<Double> possibleTactii, Double[] arr)
	{
		for (int i = arr.length - 1; i >= 0; i--)
		{
			distance = testDistanceToPositionAndAddToOrReplacePossibleTactiiContents
					(	
							positionInBarsInQuarters,
							distance, possibleTactii, arr, i
					);
		}
		return distance;
	}


	
	private static double testDistanceToPositionAndAddToOrReplacePossibleTactiiContents(double positionInBarsInQuarters,
			double distance, List<Double> possibleTactii, Double[] arr, int i)
	{
		Double d = arr[i];
//		System.out.println(d);
		double tempDist = Math.abs(positionInBarsInQuarters - d);
		if (distance == tempDist)
		{
			possibleTactii.add(d);
		}
		else if (distance > tempDist)
		{
			possibleTactii.clear();
			possibleTactii.add(d);
			distance = tempDist;
		}
		return distance;
	}


	
	private static TreeMap<Integer, Double[]> makeAllOptions(
			TreeMap<Integer, ArrayList<SuperTactus>> superTacMap,
			Double[] tactii)
	{
		Integer currentKey = 0;
		TreeMap<Integer, Double[]> map = new TreeMap<Integer, Double[]>();
		for (Integer key: superTacMap.keySet())
		{
			currentKey = key;
			ArrayList<SuperTactus> stList = superTacMap.get(key);
			Double[] arr = getSuperTactusPositionList(stList);
			map.put(currentKey, arr);
		}
		Double[] tactArr = getTactusPositionList(tactii);
		map.put(currentKey + 1, tactArr);
		return map;
	}


	private static Double[] getTactusPositionList(Double[] tactii)
	{
		Double[] arr = new Double[tactii.length];
		double pos = 0.0;
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = pos;
			pos += tactii[i];
		}		
		return arr;
	}


	private static Double[] getSuperTactusPositionList(ArrayList<SuperTactus> list)
	{
		Double[] arr = new Double[list.size()];
		double pos = 0.0;
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = pos;
			pos += list.get(i).getLengthInQuarters();
		}		
		return arr;
	}

	

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("FloatBarChordProgression: lengthInFloatBars=" + lengthInFloatBars + " \n");
		for (DoubleAndString das: chordPositionAndSymbolList)
		{
			sb.append(das.d + ":" + das.str + ", ");
		}
		return sb.toString();
	}

	
	
	@Override
	public Element getXMLFileContent(Document document)
	{
		Element element = document.createElement("chord_list_generator");
		element.setAttribute("type", "FloatBarChordProgression");
		
		Element length_in_float_bars = document.createElement("length_in_float_bars");
		length_in_float_bars.appendChild(document.createTextNode("" + lengthInFloatBars));
		element.appendChild(length_in_float_bars);
		
		for (DoubleAndString das: chordPositionAndSymbolList)
		{
			Element float_bar_chord = document.createElement("float_bar_chord");
			Element chord_element = document.createElement("chord");
			chord_element.appendChild(document.createTextNode(das.str));
			float_bar_chord.appendChild(chord_element);
			Element float_element = document.createElement("float_bar_position");
			float_element.appendChild(document.createTextNode("" + das.d));
			float_bar_chord.appendChild(float_element);
			element.appendChild(float_bar_chord);
		}
		return element;
	}
	
	// main --------------------------------------------------------------------------
	
	
	public static void main(String[] args)
	{
//		FloatBarChordProgression fbcp = new FloatBarChordProgression(1.0, new Object[] {0.0, "Dm"});
		System.out.println("closest=" + FloatBarChordProgression.getClosestTactusOrSuperTactus(1.75, TimeSignature.FIVE_EIGHT_32));
	}


	@Override
	public Object[] getParameterObjectArray()
	{
		Object[] objArr = new Object[chordPositionAndSymbolList.size() * 2 + 2];
		objArr[0] = "FloatBarChordProgression";
		objArr[1] = lengthInFloatBars;
		for (int i = 0; i < chordPositionAndSymbolList.size(); i++)
		{
			objArr[i * 2 + 2] = chordPositionAndSymbolList.get(i).d;
			objArr[i * 2 + 3] = chordPositionAndSymbolList.get(i).str;
		}
		return objArr;
	}


	
}
