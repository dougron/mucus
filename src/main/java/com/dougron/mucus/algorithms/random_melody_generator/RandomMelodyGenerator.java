package main.java.com.dougron.mucus.algorithms.random_melody_generator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import DataObjects.contour.FourPointContour;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_Anticipation_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_ApproachTone_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_EscapeTone_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_NothingToAdd;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator.AccentType;
import main.java.com.dougron.mucus.algorithms.mu_generator.enums.EscapeToneType;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.IndexedSingleValue;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.IndexedValueList;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RNDValueObject;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.ValueList;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordListGenerator;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.static_chord_scale_dictionary.CSD;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public abstract class RandomMelodyGenerator
{
	
	private final int DEFAULT_STRUCTURE_TONE_VELOCITY = 72;
	
	private int[] phraseLengthOptions = new int[] {4, 6, 8, 10};
	private TimeSignature[] timeSignatureOptions = new TimeSignature[] 
			{
					TimeSignature.FOUR_FOUR, 
					TimeSignature.THREE_FOUR, 
					TimeSignature.SEVEN_EIGHT_322, 
					TimeSignature.FIVE_EIGHT_32,
					TimeSignature.FIVE_FOUR,
					TimeSignature.SIX_EIGHT
			};
	private double minimumPhraseStartPercent = -0.2;
	private double maximumPhraseStartPercent = 0.0;
	private double minimumPhraseEndPercent = 0.45;
	private double maximumPhraseEndPercent = 0.76;
	private double[] structureToneSpacingOptions = new double[] {1.0}; //{1.0, 1.0, 1.0, 1.0, 0.5};	// in FloatBars
	private int maximumTempo = 145;
	private int minimumTempo = 65;
	private int[] xmlKeyOptions = new int[] {-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6};
	private int minimumStartNote = 50;
	private int maximumStartNote = 70;
	private FourPointContour[] structureToneContourOptions = new FourPointContour[] {
		new FourPointContour(FourPointContour.DOWN),
		new FourPointContour(FourPointContour.UP),
		new FourPointContour(FourPointContour.DOWNUP),
		new FourPointContour(FourPointContour.UPDOWN),
	};
	private int structureToneContourRangeMinimum = 5;
	private int structureToneContourRangeMaximum = 14;
	private double DEFAULT_STRUCTURE_TONE_LENGTH = 0.5;
	private double[] clearanceOfEmbellishmentFromPreviousStructureToneOptions = new double[] {1.0, 0.5};
	private int[] embellishmentCountOptions = new int[] {1, 2, 3, 4};
	private int[][] embellishmentRepetitionPatterns = new int[][]
			{
				new int[] {0},
				new int[] {0, 1},
				new int[] {0, 1, 2},
				new int[] {0, 1, 0, 2},
				new int[] {0, 1, 2, 3},
				new int[] {0, 1, 0, 2, 3},
				new int[] {0, 1, 0, 1, 2},
				new int[] {0, 1, 0, 2, 1},
				new int[] {0, 0, 1},
				new int[] {0, 0, 1, 1},
				new int[] {0, 0, 1, 2},
			};
	private MuTag[][] embellishmentPitchOptions = new MuTag[][]
			{
//					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.ACCENTED, MuTag.STEP_JUMP},
//					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.ACCENTED, MuTag.JUMP_STEP},
					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.UNACCENTED, MuTag.STEP_JUMP},
					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.UNACCENTED, MuTag.JUMP_STEP},
					new MuTag[] {MuTag.IS_ANTICIPATION},
			};
	private RelativeRhythmicPosition[] embellishmentRhythmOptions = new RelativeRhythmicPosition[]
			{
				new RelativeRhythmicPosition(0, 0, 0, -1),	
				new RelativeRhythmicPosition(0, 0, -1, 0),	
				new RelativeRhythmicPosition(0, 0, -1, -1),	
				new RelativeRhythmicPosition(0, 0, -2, 0),	
			};

	private double DEFAULT_CHORD_RHYTHM_IN_FLOATBARS = 1.0;
	private String[] chordSuffix = new String[] {"", "m", "m", "", "", "m"};

	private String randomMelodyGeneratorStaticVariablesPath = "D:/Documents/miscForBackup/RandomMelodyStaticVariableFiles/";
	private String fileNameHeader = "rmsvf_";
//	private HashMap<Integer, ParameterBundle> repMap = new HashMap<Integer, ParameterBundle>()
//			{{
//				put(0, new ParameterBundle(Parameter.MUG_COUNT_0, Parameter.MUG_PITCH_0, Parameter.MUG_RHYTHM_0));
//				put(1, new ParameterBundle(Parameter.MUG_COUNT_1, Parameter.MUG_PITCH_1, Parameter.MUG_RHYTHM_1));
//				put(2, new ParameterBundle(Parameter.MUG_COUNT_2, Parameter.MUG_PITCH_2, Parameter.MUG_RHYTHM_2));
//				put(3, new ParameterBundle(Parameter.MUG_COUNT_3, Parameter.MUG_PITCH_3, Parameter.MUG_RHYTHM_3));
//				
//			}};
	

	// ==========================================
	//  INTERFACE
	//===========================================
	
	
	public RMRandomNumberContainer getRandomNumberContainer(Random rnd)
	{
		RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
		
		rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, rnd);
		rndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, rnd);
		rndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, rnd);
		rndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, rnd);
		rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, rnd);
		rndContainer.setSingleValueVariable(Parameter.TEMPO, rnd);
		rndContainer.setSingleValueVariable(Parameter.XMLKEY, rnd);
		rndContainer.setSingleValueVariable(Parameter.START_NOTE, rnd);
		rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_CONTOUR, rnd);
		rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, rnd);
		rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, rnd);
		rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, rnd);
		

		addMugListCountPitchRhythmItemsToRndContainer(rnd, rndContainer);
		
		
		rndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, getRandomChordListArray(rnd));
		
		return rndContainer;
	}



	public void addMugListCountPitchRhythmItemsToRndContainer (Random rnd,
			RMRandomNumberContainer rndContainer)
	{
		int maxEmbellishmentCount = getMaxValue(embellishmentCountOptions);
		int maxEmbellishmentPatternIndex = getMaxFromEmbellishmentPatterns();
		for (int i = 0; i < maxEmbellishmentPatternIndex; i++)
		{
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, i,
					rnd);
		}
		for (int i = 0; i < maxEmbellishmentPatternIndex; i++)
		{
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, i,
					getARndArray(rnd, maxEmbellishmentCount));
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, i,
					getARndArray(rnd, maxEmbellishmentCount));
		}
	}



	//fill all chord possibilities, one chord per bar, without repeating a chord
	public double[] getRandomChordListArray (Random rnd)
	{
		int previousIndex = -1;
		int index = -1;
		double[] arr = new double[getMaxValue(phraseLengthOptions)];
		double next = 0.0;
		for (int i = 0; i < arr.length; i++)
		{
			while (index == previousIndex)
			{
				next = rnd.nextDouble();
				index = (int) (chordSuffix.length * next);
			}
			arr[i] = next;
			previousIndex = index;
		}
		return arr;
	}



	public double[] getARndArray (Random rnd, int count)
	{
		double[] aRndArr = new double[count];
		for (int j = 0; j < count; j++)
		{
			aRndArr[j] = rnd.nextDouble();
		}
		return aRndArr;
	}



	public int getMaxFromEmbellishmentPatterns ()
	{
		int x = 0;
		for (int[] iarr : embellishmentRepetitionPatterns)
		{
			int max = getMaxValue(iarr);
			if (max > x)
				x = max;
		}
		return x;
	}
	
	
	
	protected int getMaxValue (int[] arr)
	{
		int x = 0;
		for (int i: arr)
		{
			if (i > x) x = i;
		}
		return x;
	}



	public RandomMelodyParameterObject getParameterObject(RMRandomNumberContainer rndContainer, Random rnd)
	{			
		RandomMelodyParameterObject po = new RandomMelodyParameterObject();
		
		handlePhraseLength(rndContainer, po);				
		handleTimeSignature(rndContainer, po);			
		handlePhraseStartPercent(rndContainer, po);			
		handlePhraseEndPercent(rndContainer, po);		
		handleStructureToneSpacing(rndContainer, po);	
		handleTempo(rndContainer, po);		
		handleXMLKey(rndContainer, po);			
		handleStartNote(rndContainer, po);		
		handleStructureToneContour(rndContainer, po);		
		handleStructureToneMultiplier(rndContainer, po);		
		handleChordListGenerator(rndContainer, rnd, po);
		handleEmbellishmentClearance(rndContainer, po);
		handleEmbellishmentRepetitionPattern(rndContainer, po);		
		handleMugLists(rndContainer, rnd, po);
		
		po.setRndContainer(rndContainer);
		return po;
	}



	public void handleMugLists (RMRandomNumberContainer rndContainer,
			Random rnd, RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.MUG_LIST_COUNT)
				&& rndContainer.containsKey(Parameter.MUG_LIST_PITCH)
				&& rndContainer.containsKey(Parameter.MUG_LIST_RHYTHM))
		{
			int embellishmentCount = getNumberOfEmbellishmentPatternsInRepetitionPattern(po);		
			int[] ec = getEmbellishmentCounts(embellishmentCount, rndContainer, rnd);
			po.setEmbellishmentCounts(ec);
			po.setEmbellishments(getListOfMugListsUsingSeparateParameters(ec, rndContainer, rnd));
		}	
	}



	public void handleEmbellishmentRepetitionPattern (
			RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.EMBELLISHMENT_REPETITION_PATTERN))
		{
			int index = rndContainer.getItemAsIntIndex(Parameter.EMBELLISHMENT_REPETITION_PATTERN, embellishmentRepetitionPatterns.length);
			po.setEmbellishmentRepetitionPattern(embellishmentRepetitionPatterns[index]);		
		}
	}



	public void handleEmbellishmentClearance (
			RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.CHORD_LIST_GENERATOR))
		{
			int index = rndContainer.getItemAsIntIndex(Parameter.EMBELLISHMENT_CLEARANCE, clearanceOfEmbellishmentFromPreviousStructureToneOptions.length);
			po.setClearanceOfEmbellishmentFromPreviousStructureTone(clearanceOfEmbellishmentFromPreviousStructureToneOptions[index]);
		}
	}



	public void handleChordListGenerator (RMRandomNumberContainer rndContainer,
			Random rnd, RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.CHORD_LIST_GENERATOR))
		{
			po.setChordListGenerator(makeRandomChordListGenerator(po.getPhraseLength(), po.getXmlKey(), rndContainer, rnd));
		}
		
	}



	public void handleStructureToneMultiplier (
			RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.STRUCTURE_TONE_CONTOUR))
		{
			po.setStructureToneMultiplier(getStructureToneMultiplier(rndContainer.get(Parameter.STRUCTURE_TONE_MULTIPLIER).getValue()));
		}
		
	}



	public void handleStructureToneContour (
			RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.STRUCTURE_TONE_CONTOUR))
		{
			po.setStructureToneContour(getStructureToneContourOption(rndContainer.get(Parameter.STRUCTURE_TONE_CONTOUR).getValue()));
		}	
	}



	public void handleStartNote (RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.START_NOTE))
		{
			double d = rndContainer.get(Parameter.START_NOTE).getValue();
			po.setStartNote((int)(d * (maximumStartNote - minimumStartNote) + minimumStartNote));
		}
		
	}



	public void handleXMLKey (RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.XMLKEY))
		{
			po.setXmlKey(xmlKeyOptions[getXMLKeyOptionIndex(rndContainer.get(Parameter.XMLKEY).getValue())]);
		}
	}



	public void handleTempo (RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.TEMPO))
		{
			po.setTempo(getTempoOption(rndContainer.get(Parameter.TEMPO).getValue()));
		}
	}



	public void handleStructureToneSpacing (
			RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.STRUCTURE_TONE_SPACING))
		{
			int index = rndContainer.getItemAsIntIndex(Parameter.STRUCTURE_TONE_SPACING, structureToneSpacingOptions.length);
			po.setStructureToneSpacingInFloatBars(structureToneSpacingOptions[index]);
		}
	}



	public void handlePhraseEndPercent (RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.PHRASE_END_PERCENT))
		{
			double d = rndContainer.get(Parameter.PHRASE_END_PERCENT).getValue();
			po.setPhraseEndPercent(d * (maximumPhraseEndPercent - minimumPhraseEndPercent) + minimumPhraseEndPercent);	
		}
	}



	public void handlePhraseStartPercent (RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.PHRASE_START_PERCENT))
		{
			double d = rndContainer.get(Parameter.PHRASE_START_PERCENT).getValue();
			po.setPhraseStartPercent(d * (maximumPhraseStartPercent - minimumPhraseStartPercent) + minimumPhraseStartPercent);			
		}
	}



	public void handleTimeSignature (RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.TIME_SIGNATURE))
		{
			double d = rndContainer.get(Parameter.TIME_SIGNATURE).getValue();
			int index = getDoubleAsIndexForParameterOption(d, timeSignatureOptions.length);
			po.setTimeSignature(getTimeSignatureOption(index));
		}
	}



	public void handlePhraseLength (RMRandomNumberContainer rndContainer,
			RandomMelodyParameterObject po)
	{
		if (rndContainer.containsKey(Parameter.PHRASE_LENGTH))
		{
			double d = rndContainer.get(Parameter.PHRASE_LENGTH).getValue();
			int index = getDoubleAsIndexForParameterOption(d, phraseLengthOptions.length);
			po.setPhraseLength(getPhraseLengthOption(index));
		}
	}
	
	
	
	



	public int getStructureToneMultiplier(double aRndValue)
	{
		return (int)(aRndValue * (structureToneContourRangeMaximum - structureToneContourRangeMinimum) + structureToneContourRangeMinimum);
	}
	



	public FourPointContour getStructureToneContourOption (
			double aRndValue)
	{
		return structureToneContourOptions[RMRandomNumberContainer.getItemAsIntIndex(aRndValue, structureToneContourOptions.length)];
	}
	
	
	
	public int getXMLKeyOptionIndex(double rndValue)
	{
		return (int)(rndValue * xmlKeyOptions.length);
	}
	
	
	
	public int getStartNoteOption(double rndValue)
	{
		return (int)(rndValue * (maximumStartNote - minimumStartNote) + minimumStartNote);
	}
	
	
	
	public int getTempoOption(double rndValue)
	{
		return (int)(rndValue * (maximumTempo - minimumTempo) + minimumTempo);
	}
	
	
	
	public TimeSignature getTimeSignatureOption(int aIndex)
	{
		return timeSignatureOptions[aIndex];
	}



	public int getDoubleAsIndexForParameterOption(double aDouble, int lengthOfOptionList)
	{
		return (int)(lengthOfOptionList * aDouble);
	}
	
	
	
	public int getPhraseLengthOption(int aIndex)
	{
		return phraseLengthOptions[aIndex];
	}
	
	
	
	public RMRandomNumberContainer getMutatedRandomNumberContainer
	(
			double minimumMutation,
			int numberOfMutantAttacks, 
			Parameter[] listOfMutableParameters,
			RMRandomNumberContainer rndContainer,
			Random rnd
			)
	{
		RMRandomNumberContainer mutant = rndContainer.deepCopy();
		ArrayList<ParameterAndIndex> equalOpportunityParameters 
		= getEqualOpportunityParameterAndIndexObjects(
				listOfMutableParameters, mutant);			
		mutateRandomNumberContainer
		(
				minimumMutation, 
				numberOfMutantAttacks, 
				rndContainer, 
				mutant, 
				equalOpportunityParameters, 
				rnd
				);
		return mutant;
	}
	
	
		
	public Mu getMuPhrase(RandomMelodyParameterObject po)
	{
		Mu mu = getStructureToneMu(po);		
		addEmbellishmentMus(mu, po);
		makeLegatoStructureTones(mu);
		mu.setHasLeadingDoubleBar(true);
		return mu;
	}
	
	
	
	public String saveStaticVariablesAsTextFile(String aFileNameTimeStamp)
	{
		String path = randomMelodyGeneratorStaticVariablesPath + aFileNameTimeStamp + fileNameHeader + ".rmg_statvar";
		saveToFile(path);
		return path;
	}
	
	
	
	public String saveStaticVariablesAsTextFile(String aDirectoryPath, String aFileNameTimeStamp)
	{
		String path = aDirectoryPath + aFileNameTimeStamp + ".rmg_statvar";
		saveToFile(path);
		return path;
	}
	
	
	
	public String saveToJSON(String aDirectoryPath, String aFileNameTimeStamp)
	{
		String path = aDirectoryPath + aFileNameTimeStamp + ".rmg_statvar";
		try 
		{
			FileWriter file = new FileWriter(path);
			BufferedWriter output = new BufferedWriter(file);
			output.write(makeJSONObject().toString());
			output.close();
		}
		catch (Exception e)
		{
			
		}
		return path;
	}
	
	
	
	public JSONObject makeJSONObject()
	{
		JSONObject obj = new JSONObject();
		
		obj.put("phraseLengthOptions", phraseLengthOptions);
		obj.put("timeSignatureOptions", getJSONTimeSignatureOptions());
		obj.put("minimumPhraseStartPercent", minimumPhraseStartPercent);
		obj.put("maximumPhraseStartPercent", maximumPhraseStartPercent);
		obj.put("minimumPhraseEndPercent", minimumPhraseEndPercent);
		obj.put("maximumPhraseEndPercent", maximumPhraseEndPercent);		
		obj.put("structureToneSpacingOptions", structureToneSpacingOptions);		
		obj.put("maximumTempo", maximumTempo);
		obj.put("minimumTempo", minimumTempo);		
		obj.put("xmlKeyOptions", xmlKeyOptions);		
		obj.put("minimumStartNote", minimumStartNote);
		obj.put("maximumStartNote", maximumStartNote);		
		obj.put("structureToneContourOptions", getContourOptionsAsDoubleArray());
		obj.put("structureToneContourRangeMinimum", structureToneContourRangeMinimum);
		obj.put("structureToneContourRangeMaximum", structureToneContourRangeMaximum);
		obj.put("clearanceOfEmbellishmentFromPreviousStructureToneOptions", clearanceOfEmbellishmentFromPreviousStructureToneOptions);
		obj.put("embellishmentCountOptions", embellishmentCountOptions);
		obj.put("embellishmentRepetitionPatterns", embellishmentRepetitionPatterns);
		obj.put("embellishmentPitchOptions", embellishmentPitchOptions);
		obj.put("embellishmentRhythmOptions", getRelativeRhythmicPositionsForJSON());
		
		return obj;
	}



	public ArrayList<Integer[]> getRelativeRhythmicPositionsForJSON ()
	{
		ArrayList<Integer[]> list = new ArrayList<Integer[]>();
		for (RelativeRhythmicPosition rrp: embellishmentRhythmOptions)
		{
			list.add(new Integer[] 
					{
							rrp.getBarOffset(), 
							rrp.getSuperTactusOffset(), 
							rrp.getTactusOffset(), 
							rrp.getSubTactusOffset()
					});
		}
		return list;
	}



	public ArrayList<Double[][]> getContourOptionsAsDoubleArray ()
	{
		ArrayList<Double[][]> list = new ArrayList<Double[][]>();
		for (FourPointContour fpc: structureToneContourOptions)
		{
			double[][] arr = fpc.getParametersForJSON();
			Double[][] darr = new Double[][] {
				new Double[] {arr[0][0], arr[0][1]},
				new Double[] {arr[1][0], arr[1][1]},
				new Double[] {arr[2][0], arr[2][1]},
				new Double[] {arr[3][0], arr[3][1]},
			};
			list.add(darr);
		}
		return list;
	}



	private void saveToFile(String path)
	{
		try 
		{
			FileWriter file = new FileWriter(path);
			BufferedWriter output = new BufferedWriter(file);
			addPhraseLengthOptions(output);
			addTimeSignatureOptions(output);
			addPhraseStartAndEndConstraints(output);
			addStructureToneSpacingOptions(output);
			addTempoConstraints(output);
			addXMLKeyOptions(output);
			addStartNoteConstraints(output);		    	  
			addStructureToneContourOptions(output);		    	
			addStructureToneMaximumAndMinimum(output);
			addClearanceOfEmbellishmentFromPreviousStructureToneOptions(output);
			addEmbellishmentCountOptions(output);
			addEmbellishmentRepetitionPatterns(output);
			addEmbellishmentPitchOptions(output);
			addEmbellishmentRhythmOptions(output);
			output.close();
		}
		catch (Exception e) 
		{
			e.getStackTrace();
		}
	}
	
	



	// ============================
	// PRIVATES
	// ============================
	

	private ArrayList<Object[]> getJSONTimeSignatureOptions()
	{
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		for (TimeSignature ts: timeSignatureOptions)
		{
			list.add(ts.getJSONEntry());
		}
		return list;
	}
	
	
	private void addEmbellishmentRhythmOptions(BufferedWriter output) throws IOException
	{
		for (RelativeRhythmicPosition rrp: embellishmentRhythmOptions)
		{
			output.write("embellishmentRhythmOptions:" + rrp.toStringForFile());
			output.newLine();
		}
	}



	private void addEmbellishmentPitchOptions(BufferedWriter output) throws IOException
	{
		for (MuTag[] muTagArr: embellishmentPitchOptions)
		{
			String str = makeStringFromMutagArray(muTagArr);
			output.write("embellishmentPitchOptions:" + str);
			output.newLine();
		}
	}



	private String makeStringFromMutagArray(MuTag[] muTagArr)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < muTagArr.length; i++)
		{
			sb.append("" + muTagArr[i]);
			if (i < muTagArr.length - 1) sb.append(",");
		}
		return sb.toString();
	}



	private void addEmbellishmentRepetitionPatterns(BufferedWriter output) throws IOException
	{
		for (int[] iarr: embellishmentRepetitionPatterns)
		{
			String str = makeIntArrIntoString(iarr);
			output.write("embellishmentRepetitionPatterns:" + str);
			output.newLine();
		}
	}



	private String makeIntArrIntoString(int[] iarr)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < iarr.length; i++)
		{
			sb.append("" + iarr[i]);
			if (i < iarr.length - 1) sb.append(",");
		}
		return sb.toString();
	}



	private void addEmbellishmentCountOptions(BufferedWriter output) throws IOException
	{
		for (int i: embellishmentCountOptions)
		{
			output.write("embellishmentCountOptions:" + i);
			output.newLine();
		}
	}



	private void addStructureToneContourOptions(BufferedWriter output) throws IOException
	{
		for (FourPointContour fpc: structureToneContourOptions)
		{
			output.write("structureToneContourOptions:" + fpc.toStringForFile());
			output.newLine();
		}
	}



	private void addClearanceOfEmbellishmentFromPreviousStructureToneOptions(BufferedWriter output)
			throws IOException
	{
		for (double d: clearanceOfEmbellishmentFromPreviousStructureToneOptions)
		{
			output.write("clearanceOfEmbellishmentFromPreviousStructureToneOptions:" + d);
			output.newLine();
		}
	}



	private void addStructureToneMaximumAndMinimum(BufferedWriter output) throws IOException
	{
		output.write("structureToneContourRangeMinimum:" + structureToneContourRangeMinimum);
		output.newLine();
		output.write("structureToneContourRangeMaximum:" + structureToneContourRangeMaximum);
		output.newLine();
	}



	private void addStartNoteConstraints(BufferedWriter output) throws IOException
	{
		output.write("minimumStartNote:" + minimumStartNote);
		output.newLine();
		output.write("maximumStartNote:" + maximumStartNote);
		output.newLine();
	}



	private void addXMLKeyOptions(BufferedWriter output) throws IOException
	{
		for (double d: xmlKeyOptions)
		{
			output.write("xmlKeyOptions:" + d);
			output.newLine();
		}
	}



	private void addTempoConstraints(BufferedWriter output) throws IOException
	{
		output.write("maximumTempo:" + maximumTempo);
		output.newLine();
		output.write("minimumTempo:" + minimumTempo);
		output.newLine();
	}
	
	
	
	private void addStructureToneSpacingOptions(BufferedWriter output) throws IOException
	{
		for (double d: structureToneSpacingOptions)
		{
			output.write("structureToneSpacingOptions:" + d);
			output.newLine();
		}
	}



	private void addPhraseStartAndEndConstraints(BufferedWriter output) throws IOException
	{
		output.write("minimumPhraseStartPercent:" +  minimumPhraseStartPercent);
		output.newLine();
		output.write("maximumPhraseStartPercent:" + maximumPhraseStartPercent);
		output.newLine();
		output.write("minimumPhraseEndPercent:" + minimumPhraseEndPercent);
		output.newLine();
		output.write("maximumPhraseEndPercent:" + maximumPhraseEndPercent);
		output.newLine();
	}



	private void addTimeSignatureOptions(BufferedWriter output) throws IOException
	{
		for (TimeSignature ts: timeSignatureOptions)
		{
			output.write("timeSignatureOptions:" + ts.toStringForFile());
			output.newLine();
		}
	}



	private void addPhraseLengthOptions(BufferedWriter output) throws IOException
	{
		for (int i: phraseLengthOptions)
		{
			output.write("phraseLengthOptions:" + i);
			output.newLine();
		}
	}
	
	
	
	private void makeLegatoStructureTones(Mu aMu)
	{
		List<Mu> muList = aMu.getMusWithNotes();
		Collections.sort(muList, Mu.globalPositionInQuartersComparator);
		for (int i = 0; i < muList.size(); i++)
		{
			Mu mu = muList.get(i);
			if (mu.hasTag(MuTag.IS_STRUCTURE_TONE))
			{
				double globalPositionInQuarters = mu.getGlobalPositionInQuarters();
				if (i == muList.size() - 1)
				{
					mu.setLengthInBarsAndBeats(new BarsAndBeats(1, 0));				
				}
				else
				{	
					double nextGlobalPositionInQuarters = muList.get(i + 1).getGlobalPositionInQuarters();
					mu.setLengthInQuarters(nextGlobalPositionInQuarters - globalPositionInQuarters); 
				}
			}
		}
	}



	private ArrayList<ParameterAndIndex> getEqualOpportunityParameterAndIndexObjects
	(
			Parameter[] listOfMutableParameters, 
			RMRandomNumberContainer rndContainer
			)
	{
		ArrayList<ParameterAndIndex> equalOpportunityParameters = new ArrayList<ParameterAndIndex>();
		
		for (Parameter p: listOfMutableParameters)
		{
//			int index = 0;
//			ArrayList<Double> list = rndContainer.get(p);
			RNDValueObject rvo = rndContainer.get(p);
			if (rvo != null)
			{
				equalOpportunityParameters.addAll(rvo.getEqualOpportunityParameterAndIndexList(p)); 
			}
		}
		return equalOpportunityParameters;
	}
	
	
	
	private int[] getEmbellishmentCounts 
	(
			int embellishmentGroupCount, 	// this is the number of different emebellishment groups in an embellishment pattern
			RMRandomNumberContainer rndContainer, 
			Random rnd
			)
	{
		double currentRnd;
		int[] arr = new int[embellishmentGroupCount];
		for (int i = 0; i < embellishmentGroupCount; i++)
		{
			currentRnd = getCurrentRndFromIndexedSingleValueOrCreate(
					rndContainer, rnd, i, Parameter.MUG_LIST_COUNT); 
			int count = embellishmentCountOptions[(int)(currentRnd * embellishmentCountOptions.length)];
			arr[i] = count;
		}
		return arr;
	}



	public double getCurrentRndFromIndexedSingleValueOrCreate (
			RMRandomNumberContainer rndContainer, Random rnd, int aParameterIndex,
			Parameter aParameter)
	{
		double currentRnd;
		RNDValueObject rvo = rndContainer.get(aParameter);
		if (rvo == null)
		{
			rndContainer.put(aParameter, new IndexedSingleValue());
		}
		currentRnd = rndContainer.get(aParameter).getValue(aParameterIndex);
		if (currentRnd < 0.0) 
		{
			currentRnd = rnd.nextDouble();
			rndContainer.get(aParameter).add(aParameterIndex, currentRnd);
		}
		return currentRnd;
	}
	
	
	
	public double getCurrentRndFromIndexedValueListOrCreate (
			RMRandomNumberContainer rndContainer, Random rnd, int aParameterIndex, int aListIndex,
			Parameter aParameter)
	{
		double currentRnd;
		RNDValueObject rvo = rndContainer.get(aParameter);
		if (rvo == null)
		{
			rndContainer.put(aParameter, new IndexedValueList());
		}
		ArrayList<Double> list = rndContainer.get(aParameter).getValueList(aParameterIndex);
		if (list == null || list.size() <= aListIndex)
		{
			rndContainer.get(aParameter).add(aParameterIndex, rnd.nextDouble());
		}
		currentRnd = rndContainer.get(aParameter).getValueList(aParameterIndex).get(aListIndex);
		
		return currentRnd;
	}
	
	
	
	private ArrayList<ArrayList<MuGenerator>> getListOfMugListsUsingSeparateParameters
	(
			int[] embellishmentCounts, 	// this is the number of different emebellishment groups in an embellishment pattern
			RMRandomNumberContainer rndContainer, 
			Random rnd
			)
	{
		ArrayList<ArrayList<MuGenerator>> embellishments = new ArrayList<ArrayList<MuGenerator>>();
		double currentRnd = 0.0;
		for (int i = 0; i < embellishmentCounts.length; i++)
		{
			ArrayList<MuGenerator> mugList = new ArrayList<MuGenerator>();
			for (int j = 0; j < embellishmentCounts[i]; j++)
			{
				currentRnd = getCurrentRndFromIndexedValueListOrCreate 
						(
								rndContainer, 
								rnd, i, j,
								Parameter.MUG_LIST_PITCH
								);
				MuTag[] pitchEmbellishment = embellishmentPitchOptions[(int)(currentRnd * embellishmentPitchOptions.length)];
				
				currentRnd = getCurrentRndFromIndexedValueListOrCreate 
						(
								rndContainer, 
								rnd, i, j,
								Parameter.MUG_LIST_RHYTHM
								);
				RelativeRhythmicPosition rrp = embellishmentRhythmOptions[(int)(currentRnd * embellishmentRhythmOptions.length)];
			
				MuGenerator mug = getMuG(pitchEmbellishment, rrp);
				mugList.add(mug);
			}
			embellishments.add(mugList);
		}		
		return embellishments;
	}
	
	
	
//	private double getCurrentRnd 
//	(
//			Parameter aParameter, 
//			int aIndex,
//			RMRandomNumberContainer rndContainer, 
//			Random rnd
//			)
//	{
//		ArrayList<Double> list = rndContainer.get(aParameter);
//		if (list == null)
//		{
//			list = new ArrayList<Double>();
//			double value = rnd.nextDouble();
//			list.add(value);
//			rndContainer.put(aParameter, list);
//			return value;
//		}
//		else
//		{
//			if (list.size() <= aIndex)
//			{
//				double value = rnd.nextDouble();
//				list.add(value);
//				return value;
//			}
//			else
//			{
//				return list.get(aIndex);
//			}
//		}
//	}



//	private ArrayList<ArrayList<MuGenerator>> getListOfMugLists
//	(
//			int embellishmentCount, 	// this is the number of different embellishment groups in an embellishment pattern
//			RMRandomNumberContainer rndContainer, 
//			Random rnd
//			)
//	{
//		ArrayList<Double> mugListRnds = getMugListRndsFromRndContainerAndInitializeIfNull(rndContainer);
//		ArrayList<ArrayList<MuGenerator>> embellishments = new ArrayList<ArrayList<MuGenerator>>();
//		int mugListIndex = 0;
//		double currentRnd = 0.0;
//		for (int i = 0; i < embellishmentCount; i++)
//		{
//			currentRnd = getCurrentRnd(mugListRnds, mugListIndex, rnd);
//			setMugCount(rndContainer, i, currentRnd);
//			int count = embellishmentCountOptions[(int)(currentRnd * embellishmentCountOptions.length)];
//			mugListIndex++;
//			ArrayList<MuGenerator> mugList = new ArrayList<MuGenerator>();
//			for (int j = 0; j < count; j++)
//			{
//				currentRnd = getCurrentRnd(mugListRnds, mugListIndex, rnd);
//				setOrAddMugPitch(rndContainer, i, currentRnd);
//				MuTag[] pitchEmbellishment = embellishmentPitchOptions[(int)(currentRnd * embellishmentPitchOptions.length)];
//				mugListIndex++;
//				
//				
//				currentRnd = getCurrentRnd(mugListRnds, mugListIndex, rnd);
//				setOrAddMugRhythm(rndContainer, i, currentRnd);
//				RelativeRhythmicPosition rrp = embellishmentRhythmOptions[(int)(currentRnd * embellishmentRhythmOptions.length)];
//				mugListIndex++;
//				
//				MuGenerator mug = getMuG(pitchEmbellishment, rrp);
//				mugList.add(mug);
//				
//			}
//			embellishments.add(mugList);
//		}
//		return embellishments;
//	}
	
	
	
//	private void setOrAddMugRhythm (RMRandomNumberContainer rndContainer, int aParameterIndex, double currentRnd)
//	{
////		ParameterBundle pb = repMap.get(aParameterBundleKey);
////		ArrayList<Double> list = rndContainer.get(pb.getRhythmParameter());
//		RNDValueObject rvo = rndContainer.get(Parameter.MUG_LIST_RHYTHM);
//		if (list == null)
//		{
//			list = new ArrayList<Double>();
//			rndContainer.put(pb.getRhythmParameter(), list);
//		}
//		list.add(currentRnd);	
//	}
//
//
//
//
//	private void setOrAddMugPitch (RMRandomNumberContainer rndContainer, int aParameterBundleKey,
//			double currentRnd)
//	{
//		ParameterBundle pb = repMap.get(aParameterBundleKey);
//		ArrayList<Double> list = rndContainer.get(pb.getPitchParameter());
//		if (list == null)
//		{
//			list = new ArrayList<Double>();
//			rndContainer.put(pb.getPitchParameter(), list);
//		}
//		list.add(currentRnd);	
//	}


//
//	private void setMugCount (RMRandomNumberContainer rndContainer, int aParameterBundleKey, double currentRnd)
//	{
//		ParameterBundle pb = repMap.get(aParameterBundleKey);
//		ArrayList<Double> list = new ArrayList<Double>();
//		list.add(currentRnd);
//		rndContainer.put(pb.getCountParameter(), list);		
//	}



//	private ArrayList<Double> getMugListRndsFromRndContainerAndInitializeIfNull(
//			RMRandomNumberContainer rndContainer)
//	{
//		ArrayList<Double> mugListRnds = rndContainer.get(Parameter.MUG_LISTS);
//		if (mugListRnds == null)
//		{
//			mugListRnds = new ArrayList<Double>();
//			rndContainer.put(Parameter.MUG_LISTS, mugListRnds);
//		}
//		return mugListRnds;
//	}
	
	
	
	private void mutateRandomNumberContainer
	(
			double minimumMutation, 
			int numberOfMutantAttacks, 
			RMRandomNumberContainer originalContainer, 
			RMRandomNumberContainer containerToBeMutated,
			ArrayList<ParameterAndIndex> equalOpportunityParameters,
			Random rnd
			)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("mutant attackers:\n");
		JSONArray jarr = new JSONArray();
		for (int i = 0; i < numberOfMutantAttacks; i++)
		{
			if (equalOpportunityParameters.size() == 0)
			{
//				System.out.println("XXXXX");
				break;
			}
			int indexToMutate = rnd.nextInt(equalOpportunityParameters.size());
			ParameterAndIndex mutantAttacker = equalOpportunityParameters.get(indexToMutate);			
//			ArrayList<Double> listContainingItemToMutate = originalContainer.get(mutantAttacker.getParameter());
//			double valueOfItemToBeMutated = listContainingItemToMutate.get(mutantAttacker.getIndex());
			double valueOfItemToBeMutated = originalContainer.getValue(mutantAttacker);
			double x = getNewValueWithMoreThanMinimumMutationFromValueOfItemToBeMutated
					(minimumMutation, rnd, valueOfItemToBeMutated);
			mutantAttacker.setStartValue(valueOfItemToBeMutated);
			mutantAttacker.setEndValue(x);
			sb.append(mutantAttacker.toString() + "\n");
			jarr.put(mutantAttacker.asJSON());
			containerToBeMutated.changeValue(mutantAttacker);
			containerToBeMutated.setNarrative(sb.toString());
			containerToBeMutated.appendJSONThinking("mutant_attackers", jarr);
		}
	}



	private double getNewValueWithMoreThanMinimumMutationFromValueOfItemToBeMutated(double minimumMutation,
			Random rnd, double valueOfItemToBeMutated)
	{
		double distance = 0.0;
		double x = 0.0;
		while (distance < minimumMutation)
		{
			x = rnd.nextDouble();
			distance = Math.abs(valueOfItemToBeMutated - x);
		}
		return x;
	}



	private void addEmbellishmentMus(Mu mu, RandomMelodyParameterObject po)
	{
		int index = 0;
		Mu previousStructureToneMu = null;
		double globalCutOffPositionInQuarters = -10000.0;	// artibrarily negatively large. Should be the negative position of the last structure tone in the phrase to accomodate looping.
		for (Mu structureTone: mu.getMus())
		{
			ArrayList<MuGenerator> mugList = po.getEmbellishments().get(po.getEmbellishmentRepetitionPattern()[index]);
			if (previousStructureToneMu != null) globalCutOffPositionInQuarters = previousStructureToneMu.getGlobalPositionInQuarters() + po.getClearanceOfEmbellishmentFromPreviousStructureTone();
			Mu currentMu = structureTone;
			
			for (MuGenerator mug: mugList)
			{
				currentMu.addMuGenerator(mug.getDeepCopy());
				currentMu.generate();
				Mu recentlyAddedMu = currentMu.getMus().get(0);
				if (previousStructureToneMu != null)	// collision with previous structure tone check
				{ 
					if (recentlyAddedMu.getGlobalPositionInQuarters() < globalCutOffPositionInQuarters)
					{
						currentMu.getMus().remove(0);
						break;
					}
				}
				currentMu = currentMu.getMus().get(0);
			}						
			index++;
			if (index >= po.getEmbellishmentRepetitionPattern().length) index = 0;
			previousStructureToneMu = structureTone;
		}
	}


	private Mu getStructureToneMu(RandomMelodyParameterObject po)
	{
		Mu mu = new Mu("Mu029");
		mu.setXMLKey(po.getXmlKey());
		mu.setStartTempo(po.getTempo());
		mu.setChordListGenerator(po.getChordListGenerator());
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(po.getTimeSignature()));
		mu.setLengthInBars(po.getPhraseLength());
		int index = 0;
		
		double position = 0.0;
		while (true)
		{
			double percentPosition = position / po.getPhraseLength();
			if (percentPosition > po.getPhraseEndPercent()) break;
			if (po.getPhraseStartPercent() < percentPosition && po.getPhraseEndPercent() > percentPosition)
			{
				Mu note = addNoteMuToMuAtClosestTactusPosition(mu, index, position);
				index++;
				int pitch = getNearestChordToneFromPositionInPhraseAndContour(po, percentPosition, note);
				note.addMuNote(new MuNote(pitch, DEFAULT_STRUCTURE_TONE_VELOCITY));				
			}
			position += po.getStructureToneSpacingInFloatBars();			
		}
		return mu;
	}



	private Mu addNoteMuToMuAtClosestTactusPosition(Mu mu, int index, double position)
	{
		Mu note = new Mu("ct" + index);
		BarsAndBeats bab = getBarsAndBeatsPositionOfNearestTactus(mu, position);	
		mu.addMu(note, bab);
		note.setLengthInQuarters(DEFAULT_STRUCTURE_TONE_LENGTH);
		note.addTag(MuTag.IS_STRUCTURE_TONE);
		return note;
	}



	private int getNearestChordToneFromPositionInPhraseAndContour(RandomMelodyParameterObject po,
			double percentPosition, Mu note)
	{
		double internalPercentPosition = (percentPosition - po.getPhraseStartPercent()) / (po.getPhraseEndPercent() - po.getPhraseStartPercent());
		double mult = po.getStructureToneContour().getValue(internalPercentPosition) * po.getStructureToneMultiplier();
		int contourOffset = (int)Math.round(mult);
		int pitch = note.getPrevailingChord().getClosestChordTone(po.getStartNote() + contourOffset);
		return pitch;
	}



	private BarsAndBeats getBarsAndBeatsPositionOfNearestTactus(Mu mu, double position)
	{
		BarsAndBeats bab = mu.getGlobalPositionInBarsAndBeatsFromGlobalPositionInFloatBars(position);

		TimeSignature ts = mu.getTimeSignature(bab.getBarPosition());
		Double[] tactii = ts.getTactusAsQuartersPositions();
		double dist = 1000.0;	// arbitrarily large
		double selectedPos = -1.0;	// starting value, no other relevance
		for (Double d: tactii)
		{
			double tempDist = Math.abs(d - bab.getOffsetInQuarters());
			if (tempDist < dist)
			{
				dist = tempDist;
				selectedPos = d;
			}
		}
		bab = new BarsAndBeats(bab.getBarPosition(), selectedPos);
		return bab;
	}
	
	
	
	private MuGenerator getMuG(MuTag[] pitchEmbellishment, RelativeRhythmicPosition rrp)
	{
		MuGenerator mug = new MuG_NothingToAdd();
		switch (pitchEmbellishment[0])
		{
		case IS_ANTICIPATION:					
			mug = new MuG_Anticipation_RRP(rrp);
			break;
		case IS_ESCAPE_TONE:
			mug = getEscapeToneMuG(pitchEmbellishment, rrp);
			break;
		case IS_APPROACH_TONE:
			mug = getApproachTone(pitchEmbellishment, rrp);
			break;
		default:
			break;
		}
		return mug;
	}
	
	
	
	private MuGenerator getApproachTone (MuTag[] pitchEmbellishment, RelativeRhythmicPosition rrp)
	{
		MuGenerator mug;
		if (muTagArrayContains(pitchEmbellishment, MuTag.ACCENTED))
		{
			mug = new MuG_ApproachTone_RRP(rrp, AccentType.ACCENTED);
		}
		else
		{
			mug = new MuG_ApproachTone_RRP(rrp, AccentType.UNACCENTED);
		}
		return mug;
	}



	private MuGenerator getEscapeToneMuG(MuTag[] pitchEmbellishment, RelativeRhythmicPosition rrp)
	{
		MuGenerator mug;
		EscapeToneType escType = EscapeToneType.STEP_JUMP;
		if (muTagArrayContains(pitchEmbellishment, MuTag.JUMP_STEP)) escType = EscapeToneType.JUMP_STEP;
		if (muTagArrayContains(pitchEmbellishment, MuTag.ACCENTED))
		{
			mug = new MuG_EscapeTone_RRP(rrp, escType, AccentType.ACCENTED);
		}
		else
		{
			mug = new MuG_EscapeTone_RRP(rrp, escType, AccentType.UNACCENTED);
		}
		return mug;
	}
	
	
	
	private boolean muTagArrayContains(MuTag[] aMuTagArray, MuTag aMuTag)
	{
		for (MuTag mutag: aMuTagArray)
		{
			if (mutag == aMuTag) return true;
		}
		return false;
	}
	
	
	
//	private double getCurrentRnd(ArrayList<Double> mugListRnds, int mugListIndex, Random rnd)
//	{
//		double currentRnd;
//		if (mugListIndex < mugListRnds.size())
//		{
//			currentRnd = mugListRnds.get(mugListIndex);
//		}
//		else
//		{
//			currentRnd = rnd.nextDouble();
//			mugListRnds.add(currentRnd);
//		}
//		return currentRnd;
//	}
	
	
	
	private int getNumberOfEmbellishmentPatternsInRepetitionPattern(RandomMelodyParameterObject po)
	{
		int embellishmentCount = 0;
		for (int x: po.getEmbellishmentRepetitionPattern()) 
		{
			if (x > embellishmentCount) embellishmentCount = x;
		}
		embellishmentCount++;
		return embellishmentCount;
	}

	
	
	private ChordListGenerator makeRandomChordListGenerator(
			int phraseLength, 
			int xmlKey, 
			RMRandomNumberContainer rndContainer, 
			Random rnd
			)
	{
//		ArrayList<Double> porList = por.get(Parameter.CHORD_LIST_GENERATOR);
		RNDValueObject rvo = rndContainer.get(Parameter.CHORD_LIST_GENERATOR);
		if (rvo == null) 
		{
			rndContainer.put(Parameter.CHORD_LIST_GENERATOR, new ValueList());
		}
		rvo = rndContainer.get(Parameter.CHORD_LIST_GENERATOR);
		
		double positionInFloatBars = 0.0;
		int modKeyCentre = getModuloKeyCentre(xmlKey);
		int[] intervals = CSD.IONIAN_MODE.getDiatonicIntervals();
		
		int previousIndex = -1;
		int index = -1;
		int porIndex = 0;
		
		
		ArrayList<Object> list = new ArrayList<Object>();
		
		for (int i = 0; i < phraseLength; i++)
		{
			index = getIndexOfChordRoot(rnd, rvo, previousIndex, index, porIndex);
			
			previousIndex = index;
			int root = modKeyCentre + intervals[index];
			String rootPitchName = CSD.noteName(root);
			
			list.add(positionInFloatBars);
			list.add(rootPitchName + chordSuffix[index]);
			
			positionInFloatBars += DEFAULT_CHORD_RHYTHM_IN_FLOATBARS;
			porIndex++;
		}
		Object[] arr = list.toArray(new Object[list.size()]);
		return new FloatBarChordProgression((double)phraseLength, arr);
	}



	public int getIndexOfChordRoot(Random rnd, RNDValueObject rvo, int previousIndex, int index,
			int porIndex)
	{
		double rndValue = rvo.getValue(0, porIndex);	// 0 - irrelevant placeholder as only used in Indexed RNDValueObjects and this is a ValueList
		if (rndValue >= 0.0)
		{
			index = (int)(rndValue * chordSuffix.length);
		}
		else
		{
			while (index == previousIndex)
			{
				rndValue = rnd.nextDouble();
				index = (int)(rndValue * chordSuffix.length);
			}
			rvo.add(0, rndValue);	// 0 - placeholder again
		}
		return index;
	}
	
	
	
	private int getModuloKeyCentre(int xmlKey)
	{
		int modKeyCentre = xmlKey * -5;
		while (modKeyCentre < 0) modKeyCentre += 12;
		modKeyCentre = modKeyCentre % 12;
		return modKeyCentre;
	}



	public int getOptionCount(Parameter aParameter)
	{
		switch (aParameter)
		{
		case CHORD_LIST_GENERATOR:
			return chordSuffix.length;
		case EMBELLISHMENT_CLEARANCE:
			return clearanceOfEmbellishmentFromPreviousStructureToneOptions.length;
		case EMBELLISHMENT_REPETITION_PATTERN:
			return embellishmentRepetitionPatterns.length;
		case MUG_LISTS:
			return embellishmentPitchOptions.length;
		case MUG_LIST_COUNT:
			return embellishmentCountOptions.length;
		case MUG_LIST_PITCH:
			return embellishmentPitchOptions.length;
		case MUG_LIST_RHYTHM:
			return embellishmentRhythmOptions.length;
		case PHRASE_END_PERCENT:
			return 1000;		// continuous value parameter. this quantizes to 3 decimal places
		case PHRASE_LENGTH:
			return phraseLengthOptions.length;
		case PHRASE_START_PERCENT:
			return 1000;		// as with PHRASE_END_PERCENT
		case START_NOTE:
			return maximumStartNote - minimumStartNote;
		case STRUCTURE_TONE_CONTOUR:
			return structureToneContourOptions.length;
		case STRUCTURE_TONE_MULTIPLIER:
			return structureToneContourRangeMaximum - structureToneContourRangeMinimum;
		case STRUCTURE_TONE_SPACING:
			return structureToneSpacingOptions.length;
		case TEMPO:
			return maximumTempo - minimumTempo;
		case TIME_SIGNATURE:
			return timeSignatureOptions.length;
		case XMLKEY:
			return xmlKeyOptions.length;
		default:
			break;
		
		}
		return -1;
	}



	public int getEmbellishmentCountOptionsLength()
	{
		return embellishmentCountOptions.length;
	}



	public int getEmbellishmentCountOption(int index)
	{
		return embellishmentCountOptions[index];
	}



	public int getEmbellishmentRhythmOptionsCount()
	{
		return embellishmentRhythmOptions.length;
	}
	
	
	public RelativeRhythmicPosition[] getEmbellishmentRhythmOptions ()
	{
		return embellishmentRhythmOptions;
	}
	
	
	
	public RelativeRhythmicPosition getEmbellishmentRhythmOption (int index)
	{
		return embellishmentRhythmOptions[index];
	}
	
	
	
	public int getEmbellishmentPitchOptionsCount()
	{
		return embellishmentPitchOptions.length;
	}
	
	
	public MuTag[][] getEmbellishmentPitchOptions ()
	{
		return embellishmentPitchOptions;
	}
	
	
	
	public MuTag[] getEmbellishmentPitchOption (int index)
	{
		return embellishmentPitchOptions[index];
	}



	public int[] getEmbellishmentCountOptions()
	{
		return embellishmentCountOptions;
	}



	public int[] getPhraseLengthOptions ()
	{
		return phraseLengthOptions;
	}



	public void setPhraseLengthOptions (int[] phraseLengthOptions)
	{
		this.phraseLengthOptions = phraseLengthOptions;
	}



	public double[] getStructureToneSpacingOptions ()
	{
		return structureToneSpacingOptions;
	}



	public void setTimeSignatureOptions (TimeSignature[] timeSignatureOptions)
	{
		this.timeSignatureOptions = timeSignatureOptions;
	}



	public String[] getChordSuffix ()
	{
		return chordSuffix;
	}



	public TimeSignature[] getTimeSignatureOptions ()
	{
		return timeSignatureOptions;
	}



	public void setMinimumPhraseStartPercent (double minimumPhraseStartPercent)
	{
		this.minimumPhraseStartPercent = minimumPhraseStartPercent;
	}



	public double[] getClearanceOfEmbellishmentFromPreviousStructureToneOptions ()
	{
		return clearanceOfEmbellishmentFromPreviousStructureToneOptions;
	}



	public void setMaximumPhraseStartPercent (double maximumPhraseStartPercent)
	{
		this.maximumPhraseStartPercent = maximumPhraseStartPercent;
	}



	public int[][] getEmbellishmentRepetitionPatterns ()
	{
		return embellishmentRepetitionPatterns;
	}



	public void setMinimumPhraseEndPercent (double minimumPhraseEndPercent)
	{
		this.minimumPhraseEndPercent = minimumPhraseEndPercent;
	}



	public void setMaximumPhraseEndPercent (double maximumPhraseEndPercent)
	{
		this.maximumPhraseEndPercent = maximumPhraseEndPercent;
	}



	public void setStructureToneSpacingOptions (
			double[] structureToneSpacingOptions)
	{
		this.structureToneSpacingOptions = structureToneSpacingOptions;
	}



	public void setMaximumTempo (int maximumTempo)
	{
		this.maximumTempo = maximumTempo;
	}



	public void setMinimumTempo (int minimumTempo)
	{
		this.minimumTempo = minimumTempo;
	}



	public void setXmlKeyOptions (int[] xmlKeyOptions)
	{
		this.xmlKeyOptions = xmlKeyOptions;
	}



	public void setMinimumStartNote (int minimumStartNote)
	{
		this.minimumStartNote = minimumStartNote;
	}



	public void setMaximumStartNote (int maximumStartNote)
	{
		this.maximumStartNote = maximumStartNote;
	}



	public void setStructureToneContourOptions (
			FourPointContour[] structureToneContourOptions)
	{
		this.structureToneContourOptions = structureToneContourOptions;
	}



	public void setStructureToneContourRangeMinimum (
			int structureToneContourRangeMinimum)
	{
		this.structureToneContourRangeMinimum = structureToneContourRangeMinimum;
	}



	public void setStructureToneContourRangeMaximum (
			int structureToneContourRangeMaximum)
	{
		this.structureToneContourRangeMaximum = structureToneContourRangeMaximum;
	}



	public void setClearanceOfEmbellishmentFromPreviousStructureToneOptions (
			double[] clearanceOfEmbellishmentFromPreviousStructureToneOptions)
	{
		this.clearanceOfEmbellishmentFromPreviousStructureToneOptions = clearanceOfEmbellishmentFromPreviousStructureToneOptions;
	}



	public void setEmbellishmentCountOptions (int[] embellishmentCountOptions)
	{
		this.embellishmentCountOptions = embellishmentCountOptions;
	}



	public void setEmbellishmentRepetitionPatterns (
			int[][] embellishmentRepetitionPatterns)
	{
		this.embellishmentRepetitionPatterns = embellishmentRepetitionPatterns;
	}



	public void setEmbellishmentPitchOptions (MuTag[][] embellishmentPitchOptions)
	{
		this.embellishmentPitchOptions = embellishmentPitchOptions;
	}



	public void setEmbellishmentRhythmOptions (
			RelativeRhythmicPosition[] embellishmentRhythmOptions)
	{
		this.embellishmentRhythmOptions = embellishmentRhythmOptions;
	}



	public void setChordSuffix (String[] chordSuffix)
	{
		this.chordSuffix = chordSuffix;
	}



	public void setRandomMelodyGeneratorStaticVariablesPath (
			String randomMelodyGeneratorStaticVariablesPath)
	{
		this.randomMelodyGeneratorStaticVariablesPath = randomMelodyGeneratorStaticVariablesPath;
	}



	public void setFileNameHeader (String fileNameHeader)
	{
		this.fileNameHeader = fileNameHeader;
	}



}



class ParameterBundle
{
	
	
	private Parameter countParameter;
	private Parameter pitchParameter;
	private Parameter rhythmParameter;
	
	
	public ParameterBundle (Parameter aCountParameter, Parameter aPitchParameter, Parameter aRhythmParameter)
	{
		countParameter = aCountParameter;
		pitchParameter = aPitchParameter;
		rhythmParameter = aRhythmParameter;
	}


	public Parameter getCountParameter ()
	{
		return countParameter;
	}


	public Parameter getPitchParameter ()
	{
		return pitchParameter;
	}


	public Parameter getRhythmParameter ()
	{
		return rhythmParameter;
	}
}
