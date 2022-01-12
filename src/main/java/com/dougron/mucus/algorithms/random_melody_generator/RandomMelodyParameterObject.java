package main.java.com.dougron.mucus.algorithms.random_melody_generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;

import DataObjects.contour.FourPointContour;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordListGenerator;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import time_signature_utilities.time_signature.TimeSignature;

public class RandomMelodyParameterObject
{
	
	private static String fileExtension = ".rmg_parameter_object";
		
	private ChordListGenerator chordListGenerator;
	private double clearanceOfEmbellishmentFromPreviousStructureTone;
	private ArrayList<ArrayList<MuGenerator>> embellishments;
	private int[] embellishmentCounts;	
	private int[] embellishmentRepetitionPattern;
	private double phraseEndPercent;
	private int phraseLength;
	private double phraseStartPercent;
	private RMRandomNumberContainer rndContainer;
	private int startNote;
	private FourPointContour structureToneContour;
	private int structureToneMultiplier;
	private double structureToneSpacingInFloatBars;	
	private int tempo;
	private TimeSignature timeSignature;		
	private int xmlKey;	
		
	
	
	public void setRndContainer(RMRandomNumberContainer aRndContainer)
	{
		rndContainer = aRndContainer;		
	}
	public RMRandomNumberContainer getRndContainer()
	{
		return rndContainer;
	}
	public double getClearanceOfEmbellishmentFromPreviousStructureTone()
	{
		return clearanceOfEmbellishmentFromPreviousStructureTone;
	}
	public void setClearanceOfEmbellishmentFromPreviousStructureTone(
			double clearanceOfEmbellishmentFromPreviousStructureTone)
	{
		this.clearanceOfEmbellishmentFromPreviousStructureTone = clearanceOfEmbellishmentFromPreviousStructureTone;
	}
	public int getPhraseLength()
	{
		return phraseLength;
	}
	public void setPhraseLength(int phraseLength)
	{
		this.phraseLength = phraseLength;
	}
	public TimeSignature getTimeSignature()
	{
		return timeSignature;
	}
	public void setTimeSignature(TimeSignature timeSignature)
	{
		this.timeSignature = timeSignature;
	}
	public double getPhraseStartPercent()
	{
		return phraseStartPercent;
	}
	public void setPhraseStartPercent(double phraseStartPercent)
	{
		this.phraseStartPercent = phraseStartPercent;
	}
	public double getPhraseEndPercent()
	{
		return phraseEndPercent;
	}
	public void setPhraseEndPercent(double phraseEndPercent)
	{
		this.phraseEndPercent = phraseEndPercent;
	}
	public double getStructureToneSpacingInFloatBars()
	{
		return structureToneSpacingInFloatBars;
	}
	public void setStructureToneSpacingInFloatBars(double structureToneSpacingInFloatBars)
	{
		this.structureToneSpacingInFloatBars = structureToneSpacingInFloatBars;
	}
	public int getTempo()
	{
		return tempo;
	}
	public void setTempo(int tempo)
	{
		this.tempo = tempo;
	}
	public int getXmlKey()
	{
		return xmlKey;
	}
	public void setXmlKey(int xmlKey)
	{
		this.xmlKey = xmlKey;
	}
	public int getStartNote()
	{
		return startNote;
	}
	public void setStartNote(int startNote)
	{
		this.startNote = startNote;
	}
	public FourPointContour getStructureToneContour()
	{
		return structureToneContour;
	}
	public void setStructureToneContour(FourPointContour structureToneContour)
	{
		this.structureToneContour = structureToneContour;
	}
	public int getStructureToneMultiplier()
	{
		return structureToneMultiplier;
	}
	public void setStructureToneMultiplier(int structureToneMultiplier)
	{
		this.structureToneMultiplier = structureToneMultiplier;
	}
	public ChordListGenerator getChordListGenerator()
	{
		return chordListGenerator;
	}
	public void setChordListGenerator(ChordListGenerator chordListGenerator)
	{
		this.chordListGenerator = chordListGenerator;
	}
	
	
	public int[] getEmbellishmentRepetitionPattern()
	{
		return embellishmentRepetitionPattern;
	}
	public void setEmbellishmentRepetitionPattern(int[] embellishmentRepetitionPattern)
	{
		this.embellishmentRepetitionPattern = embellishmentRepetitionPattern;
	}
	public ArrayList<ArrayList<MuGenerator>> getEmbellishments()
	{
		return embellishments;
	}
	public void setEmbellishments(ArrayList<ArrayList<MuGenerator>> embellishments)
	{
		this.embellishments = embellishments;
	}
	
	
	public int[] getEmbellishmentCounts ()
	{
		return embellishmentCounts;
	}
	public int getEmbellishmentCount (int index)
	{
		if (index >= embellishmentCounts.length)
		{
			return 0;
		}
		return embellishmentCounts[index];
	}
	public void setEmbellishmentCounts (int[] embellishmentCounts)
	{
		this.embellishmentCounts = embellishmentCounts;
	}
	
	
	
	// this is what goes to the .rmg_parameter_object file, so fuck with it at the peril of having 
	// to write all sorts of stuff to wangle it into shape for datamining
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
	
		sb.append("phraseLength=" + phraseLength + "\n");	
		sb.append("timeSignature=" + timeSignature + "\n");				
		sb.append("phraseStartPercent=" + phraseStartPercent + "\n");		
		sb.append("phraseEndPercent=" + phraseEndPercent + "\n");				
		sb.append("structureToneSpacingInFloatBars=" + structureToneSpacingInFloatBars + "\n");				
		sb.append("tempo=" + tempo + "\n");				
		sb.append("xmlKey=" + xmlKey + "\n");				
		sb.append("startNote=" + startNote + "\n");	
		if (structureToneContour != null)
		{
			sb.append("structureToneContour:" + structureToneContour.toStringForFile() + "\n");				
		}
		sb.append("structureToneMultiplier=" + structureToneMultiplier + "\n");				
		if (chordListGenerator != null)
		{
			sb.append("chordListGenerator=" + chordListGenerator.toString() + "\n");
		}
		sb.append("clearanceOfEmbellishmentFromPreviousStructureTone=" + clearanceOfEmbellishmentFromPreviousStructureTone + "\n");
			
		if (embellishmentRepetitionPattern != null)
		{
			sb.append("embellishmentRepetitionPattern=");
			for (int i: embellishmentRepetitionPattern) sb.append(i + ", ");
			sb.append("\n");
		}		
		
		if (embellishments != null)
		{
			int index = 0;
			for (ArrayList<MuGenerator> list: embellishments)
			{
				sb.append("embellishmentIndex=" + index + "\n");
				for (MuGenerator mug: list)
				{
					sb.append("   " + mug.toOneLineString() + "\n");
				}
				index++;
			}
		}		
		return sb.toString();
	}
	
	
	
	public void saveToStringToFile(String aPath)
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(aPath + fileExtension )));
			bw.write(toString());
			bw.close();		
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		   
	}
	
	
	
	public void saveToJSON(String aDirectoryPath, String aFileName)
	{
		JSONObject jobj = makeJSONObject();						
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(aDirectoryPath + aFileName + fileExtension));
			bw.write(jobj.toString());
			bw.close();
//			System.out.println("file written successfully");
		}
		catch (Exception ex)
		{}
	}
	
	
	
	public JSONObject makeJSONObject ()
	{
		JSONObject jobj = new JSONObject();
		jobj.put("phraseLength", phraseLength);
		jobj.put("timeSignature", timeSignature);
		jobj.put("phraseStartPercent", phraseStartPercent);
		jobj.put("phraseEndPercent", phraseEndPercent);
		jobj.put("structureToneSpacingInFloatBars", structureToneSpacingInFloatBars);
		jobj.put("tempo", tempo);
		jobj.put("xmlKey", xmlKey);
		jobj.put("startNote", startNote);
		jobj.put("structureToneMultiplier", structureToneMultiplier);
		jobj.put("clearanceOfEmbellishmentFromPreviousStructureTone", clearanceOfEmbellishmentFromPreviousStructureTone);
		jobj.put("chordListGenerator", makeChordListGeneratorMap());
		jobj.put("chordAnalysis", makeChordAnalysisJson());
		jobj.put("embellishmentRepetitionPattern", embellishmentRepetitionPattern);
		jobj.put("embellishmentCounts", embellishmentCounts);
		jobj.put("structureToneContour", structureToneContour.getParametersForJSON());
		jobj.put("embellishmentIndex", makeEmbellishmentMap());
		jobj.put("embellishmentIndex_expanded", makeExpandedEmbellishmentMap());
		return jobj;
	}
	
	
	

	
	
	private JSONObject makeChordAnalysisJson ()
	{
		TimeSignatureGenAndMap tsgm = new TimeSignatureGenAndMap(timeSignature);
		ChordList cl = chordListGenerator.getChordList(tsgm, phraseLength);		
		return cl.getProgressionAnalyzerJson();
	}
	
	
	
	private Map<Integer, ArrayList<JSONObject>> makeExpandedEmbellishmentMap () 
	{
		Map<Integer, ArrayList<JSONObject>> tempMap = new TreeMap<Integer, ArrayList<JSONObject>>();
		
		int index = 0;
		for (ArrayList<MuGenerator> muglist: embellishments) 
		{
			ArrayList<JSONObject> vTempList = new ArrayList<JSONObject>();
			for (MuGenerator mug: muglist)
			{
				vTempList.add(mug.getJSONObject());
			}
			tempMap.put(index, vTempList);
			index++;
		}
		return tempMap;
	}
	
	
	public Map<Integer, ArrayList<String>> makeEmbellishmentMap ()
	{
		Map<Integer, ArrayList<String>> tempMap = new TreeMap<Integer, ArrayList<String>>();
		
		int index = 0;
		for (ArrayList<MuGenerator> muglist: embellishments)
		{
			ArrayList<String> vTempList = new ArrayList<String>();
			for (MuGenerator mug: muglist)
			{
				vTempList.add(mug.toOneLineStringForJSON());
			}
			tempMap.put(index, vTempList);
			index++;
		}
		return tempMap;
	}
	
	
	
	public Map<Double, String> makeChordListGeneratorMap ()
	{
		Map<Double, String> tempMap = new TreeMap<Double, String>();
		String chords = chordListGenerator.toString();
		for (String str: chords.split("\n")[1].split(", "))
		{
			String[] split = str.split(":");
			tempMap.put(Double.parseDouble(split[0]), split[1]);
		}
		return tempMap;
	}
	
	
	
	public String getDescription(Parameter aParameter)
	{
		StringBuilder sb;
		switch (aParameter)
		{
		case CHORD_LIST_GENERATOR:
			return chordListGenerator.chordsToString();
		case EMBELLISHMENT_CLEARANCE:
			return clearanceOfEmbellishmentFromPreviousStructureTone + " quarters";
		case EMBELLISHMENT_REPETITION_PATTERN:
			sb = new StringBuilder();
			for (int i: embellishmentRepetitionPattern) sb.append(i + ",");
			return sb.toString();
		case MUG_LISTS:
			break;
		case PHRASE_END_PERCENT:
			return "" + phraseEndPercent;
		case PHRASE_LENGTH:
			return getPhraseLength() + " bars";
		case PHRASE_START_PERCENT:
			return "" + phraseStartPercent;
		case START_NOTE:
			return "" + getStartNote();
		case STRUCTURE_TONE_CONTOUR:
			return structureToneContour.toOneLinerString();
		case STRUCTURE_TONE_MULTIPLIER:
			return "" + structureToneMultiplier;
		case STRUCTURE_TONE_SPACING:
			return structureToneSpacingInFloatBars + " floatBars";
		case TEMPO:
			return getTempo() + " bpm";
		case TIME_SIGNATURE:
			return getTimeSignature().getName();
		case XMLKEY:
			return "" + getXmlKey();
		case MUG_LIST_COUNT:
			if (embellishmentCounts.length > 0) 
			{
				sb = new StringBuilder();
				for (int i: embellishmentCounts) sb.append(i + ",");
				return sb.toString();
			}
			return "n/a";
		case MUG_LIST_RHYTHM:
			return getEmbellishmentsToStrings();
		case MUG_LIST_PITCH:
			return getEmbellishmentsToStrings();
		default:
			break;
		
		}
		return "description not availble yet";
	}
	
	
	
	public String getEmbellishmentsToStrings ()
	{
		StringBuilder sb;
		if (embellishments.size() > 0) 
		{
			sb = new StringBuilder();
			for (int i = 0; i < embellishments.size(); i++)
			{
				sb.append("\n" + i + "=");
				for (MuGenerator mug: embellishments.get(i))
				{
					sb.append("\t" + mug.toOneLineString() + "\n");
				}
			}
			return sb.toString();
		}
		return "n/a";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}