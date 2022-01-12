package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.ChordProgression_FewerFunctionChanges;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.ChordProgression_MoreFunctionChanges;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.EmbellishmentClearanceFromPreviousStructureTone_Less;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.EmbellishmentClearanceFromPreviousStructureTone_More;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.EmbellishmentRepetitionPattern_LessVariety;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.EmbellishmentRepetitionPattern_MoreVariety;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.FeedbackObject;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_CountIsLess;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_CountIsMore;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_PitchHasLessVariety;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_PitchHasMoreVariety;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_RhythmHasLessVariety;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_RhythmHasMoreVariety;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_RhythmLongerOnAverage;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_RhythmShorterOnAverage;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.PhraseEnd_Earlier;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.PhraseEnd_Later;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.PhraseLength_LessEven;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.PhraseLength_Longer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.PhraseLength_MoreEven;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.PhraseLength_Shorter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.PhraseStart_Earlier;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.PhraseStart_Later;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.StartNote_Higher;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.StartNote_Lower;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.StartNote_SignificantlyDifferent;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.StructureToneContour_Different;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.StructureToneMultiplier_Higher;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.StructureToneMultiplier_Lower;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.StructureToneSpacing_Longer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.StructureToneSpacing_Shorter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.Tempo_Faster;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.Tempo_Slower;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.TimeSignature_LessEven;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.TimeSignature_MoreEven;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.XMLKey_UpTo5KeysFlatter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.XMLKey_UpTo5KeysSharper;

public class ParameterSpecificFeedback
{
	
	private static Map<Parameter, FeedbackObject[]> map = ImmutableMap.<Parameter, FeedbackObject[]>builder()
			.put(Parameter.PHRASE_LENGTH, new FeedbackObject[] 
					{
							PhraseLength_Longer.getInstance(), 
							PhraseLength_Shorter.getInstance(),
							PhraseLength_MoreEven.getInstance(), 
							PhraseLength_LessEven.getInstance(),
					})
			.put(Parameter.TIME_SIGNATURE, new FeedbackObject[] 
					{
							TimeSignature_MoreEven.getInstance(),
							TimeSignature_LessEven.getInstance()
					})
			.put(Parameter.TEMPO, new FeedbackObject[] 
					{
							Tempo_Faster.getInstance(),
							Tempo_Slower.getInstance()
					})
			.put(Parameter.START_NOTE, new FeedbackObject[] 
					{
							StartNote_Lower.getInstance(),
							StartNote_Higher.getInstance(),
							StartNote_SignificantlyDifferent.getInstance(),
					})
			.put(Parameter.XMLKEY, new FeedbackObject[] 
					{
							XMLKey_UpTo5KeysSharper.getInstance(),
							XMLKey_UpTo5KeysFlatter.getInstance()
					})
			.put(Parameter.STRUCTURE_TONE_CONTOUR, new FeedbackObject[] 
					{
							StructureToneContour_Different.getInstance()
					})
			.put(Parameter.STRUCTURE_TONE_MULTIPLIER, new FeedbackObject[] 
					{
							StructureToneMultiplier_Higher.getInstance(),
							StructureToneMultiplier_Lower.getInstance()
					})
			.put(Parameter.PHRASE_START_PERCENT, new FeedbackObject[] 
					{
							PhraseStart_Earlier.getInstance(),
							PhraseStart_Later.getInstance()
					})
			.put(Parameter.PHRASE_END_PERCENT, new FeedbackObject[] 
					{
							PhraseEnd_Earlier.getInstance(),
							PhraseEnd_Later.getInstance()
					})
			.put(Parameter.STRUCTURE_TONE_SPACING, new FeedbackObject[] 
					{
							StructureToneSpacing_Longer.getInstance(),
							StructureToneSpacing_Shorter.getInstance()
					})
			.put(Parameter.EMBELLISHMENT_CLEARANCE, new FeedbackObject[] 
					{
							EmbellishmentClearanceFromPreviousStructureTone_More.getInstance(),
							EmbellishmentClearanceFromPreviousStructureTone_Less.getInstance()
					})
			.put(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new FeedbackObject[] 
					{
							EmbellishmentRepetitionPattern_MoreVariety.getInstance(),
							EmbellishmentRepetitionPattern_LessVariety.getInstance()
					})
			.put(Parameter.CHORD_LIST_GENERATOR, new FeedbackObject[] 
					{
							ChordProgression_FewerFunctionChanges.getInstance(),
							ChordProgression_MoreFunctionChanges.getInstance()
					})
			.put(Parameter.MUG_LIST_COUNT, new FeedbackObject[] 
					{
							MugList_CountIsLess.getInstance(),
							MugList_CountIsMore.getInstance()
					})
			.put(Parameter.MUG_LIST_RHYTHM, new FeedbackObject[] 
					{
							MugList_RhythmHasLessVariety.getInstance(),
							MugList_RhythmHasMoreVariety.getInstance(),
							MugList_RhythmLongerOnAverage.getInstance(),
							MugList_RhythmShorterOnAverage.getInstance()
					})
			.put(Parameter.MUG_LIST_PITCH, new FeedbackObject[] 
					{
						MugList_PitchHasLessVariety.getInstance(),
						MugList_PitchHasMoreVariety.getInstance()
					})
			.build();

			
	
	private static FeedbackObject[] arr = null;
	private static ArrayList<FeedbackObject> list = null;
	
	
	
	public static ArrayList<FeedbackObject> getList()
	{
		if (list == null) list = getMapAsList(map);
		return list;
	}
	
	
	
	public static FeedbackObject[] getArray()
	{
		if (arr == null) arr = getMapAsArray(map);
		return arr;
	}
		
			
			
	private static FeedbackObject[] getMapAsArray (
			Map<Parameter, FeedbackObject[]> aMap)
	{
		ArrayList<FeedbackObject> list = getList();
		return list.toArray(new FeedbackObject[list.size()]);
	}

	

	private static ArrayList<FeedbackObject> getMapAsList (Map<Parameter, FeedbackObject[]> aMap)
	{
		ArrayList<FeedbackObject> list = new ArrayList<FeedbackObject>();
		for (Parameter parameter: aMap.keySet())
		{
			for (FeedbackObject fo: aMap.get(parameter))
			{
				list.add(fo);
			}
		}
		return list;
	}



	public static FeedbackObject[] getParameterOptions(Parameter aParameter)
	{
		if (map.containsKey(aParameter))
		{
			return map.get(aParameter);
		}		
		return null;		
	}
	
	
	
//	private static boolean parameterArrayContains (Parameter[] aParameterArray, Parameter aParameter)
//	{
//		for (Parameter parameter: aParameterArray)
//		{
//			if (parameter == aParameter) return true;
//		}
//		return false;
//	}



	public static Map<Parameter, FeedbackObject[]> getParameterSpecificFeedackMap()
	{
		return map;
	}



	public static Integer getTrueCount
	(
			RMRandomNumberContainer aRndContainer, 
			FeedbackObject[] aFeedbackCriteria, 
			RMRandomNumberContainer aNewRndContainer,
			RandomMelodyGenerator aRmg
	)
	{
		int count = 0;
		for (FeedbackObject fo: aFeedbackCriteria)
		{
			if (fo.isTrue(aRndContainer, aNewRndContainer, aRmg)) count++;
		}
		return count;
	}



	public static Integer getRemainderFromPowerOfTwo(int i)
	{
		int exp = 0;
		while (i >= Math.pow(2, exp))
		{
			exp++;
		}
		return (int)(i - Math.pow(2, exp - 1));
	}
	
	
	
	public static int getBarLengthBalanceValue(int aBarCount)
	{
		if (getRemainderFromPowerOfTwo(aBarCount) == 0) return 0;
		
		int[] twoPowerBounds = getTwoPowerBounds(aBarCount);
		int count = 1;
		while (true)
		{
			int centre = twoPowerBounds[0] + (twoPowerBounds[1] - twoPowerBounds[0]) / 2;
			if (aBarCount == centre)
			{
				return count;
			}
			if (aBarCount > centre) 
			{
				twoPowerBounds[0] = centre;
			}
			else
			{
				twoPowerBounds[1] = centre;
			}
			count++;
		}
	}



	private static int[] getTwoPowerBounds(int aBarCount)
	{
		int exp = 1;
		while (true)
		{
			if (aBarCount < Math.pow(2, exp))
			{
				return new int[] { (int) Math.pow(2, exp - 1), (int) Math.pow(2, exp) };
			} 
			exp++;
		}		
	}
	
	

	
}
