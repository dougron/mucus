package test.java.com.dougron.mucus.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionDataFactory;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_001;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_002;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.EmbellishmentClearanceFromPreviousStructureTone_Less;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.EmbellishmentClearanceFromPreviousStructureTone_More;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.EmbellishmentRepetitionPattern_LessVariety;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.EmbellishmentRepetitionPattern_MoreVariety;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.FeedbackObject;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.MugList_CountIsLess;
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
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RndContainerScoreWrapper;


class MucusInteractionDataFactory_Tests
{

	

//	@Test
//	final void testGenerateUserOption ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetMid ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetMelodyForMu ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetDrumPartForMu ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetBassPartForMu ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetChordsPartForMu ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testAddNamesAsMuAnnotationsToStructureTones ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testMakeGenerateUserOptionsThinking ()
//	{
//		fail("Not yet implemented"); // TODO
//	}

	
	@Disabled
	@Test
	final void when_random_numbers_for_phrase_length_are_within_the_minumum_mutation_of_the_original_then_makeUserRndContainerList_does_not_use_them ()
	{
		
		MucusInteractionData aMid = new MucusInteractionData();
		aMid.addParameterSpecificFeedbackItem(PhraseLength_Longer.getInstance());
		aMid.setRandomMelodyGenerator(RMG_001.getInstance());
		RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
		rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.5)); 	// 0.5 is the original value
		aMid.setRndContainer(rndContainer);
		int aUserOptionCount = 20;
		double aMinimumMutation = 0.15;
		ArrayList<RndContainerScoreWrapper> rndcList = MucusInteractionDataFactory.makeUserRndContainerList(
				aMid, 
				new TestRandom(new double[] {0.1, 0.45, 0.55, 0.9}), 	// 0.45 and 0.55 are within aMinimumMutation of 0.5
				aUserOptionCount, 
				aMinimumMutation
				);
//		for (RndContainerScoreWrapper wrapper: rndcList)
//		{
//			System.out.println(wrapper.toString());
//		}
		assertEquals(0.1, rndcList.get(0).rndContainer.get(Parameter.PHRASE_LENGTH).getValue());
		assertEquals(0.9, rndcList.get(1).rndContainer.get(Parameter.PHRASE_LENGTH).getValue());
	}
	
	
	@Test
	final void given_mug_list_count_is_less_then_values_actually_get_changed ()
	{
		
		MucusInteractionData aMid = new MucusInteractionData();
		aMid.addParameterSpecificFeedbackItem(MugList_CountIsLess.getInstance());
		aMid.setRandomMelodyGenerator(RMG_002.getInstance());
		RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
		Random rnd = new TestRandom(new double[] {0.6606218004053328, 0.8739816483352485, 0.25163310736305855, 0.9957609821541308});
		for (int i = 0; i < 4; i++)
		{
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, i, rnd); 
		}		
		aMid.setRndContainer(rndContainer);
		int aUserOptionCount = 20;
		double aMinimumMutation = 0.15;
		ArrayList<RndContainerScoreWrapper> rndcList = MucusInteractionDataFactory.makeUserRndContainerList(
				aMid, 
				new Random(), 	
				aUserOptionCount, 
				aMinimumMutation
				);
		for (RndContainerScoreWrapper wrapper: rndcList)
		{
			System.out.println(wrapper.toString());
		}
//		assertEquals(0.1, rndcList.get(0).rndContainer.get(Parameter.PHRASE_LENGTH).getValue());
//		assertEquals(0.9, rndcList.get(1).rndContainer.get(Parameter.PHRASE_LENGTH).getValue());
	}
	
	@Disabled
	@Test
	final void when_random_numbers_are_within_the_minumum_mutation_of_the_original_then_makeUserRndContainerList_does_not_use_them ()
	{
		Multimap<Parameter, FeedbackObject> map = getSingleValueParameterAndFeedbackObjectMultiMap();
		for (Parameter parameter: map.keySet())
		{
			for (FeedbackObject fo: map.get(parameter))
			{
				MucusInteractionData aMid = new MucusInteractionData();
				aMid.addParameterSpecificFeedbackItem(fo);
				aMid.setRandomMelodyGenerator(RMG_001.getInstance());
				RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
				rndContainer.setSingleValueVariable(parameter, new TestRandom(0.5)); 	// 0.5 is the original value
				aMid.setRndContainer(rndContainer);
				int aUserOptionCount = 20;
				double aMinimumMutation = 0.15;
				ArrayList<RndContainerScoreWrapper> rndcList = MucusInteractionDataFactory.makeUserRndContainerList(
						aMid, 
						new TestRandom(new double[] {0.1, 0.45, 0.55, 0.9}), 	// 0.45 and 0.55 are within aMinimumMutation of 0.5
						aUserOptionCount, 
						aMinimumMutation
						);
//				for (RndContainerScoreWrapper wrapper: rndcList)
//				{
//					System.out.println(wrapper.toString());
//				}
				assertEquals(0.1, rndcList.get(0).rndContainer.get(parameter).getValue());
				assertEquals(0.9, rndcList.get(1).rndContainer.get(parameter).getValue());
			}
		}
		
	}


	public Multimap<Parameter, FeedbackObject> getSingleValueParameterAndFeedbackObjectMultiMap ()
	{
		Multimap<Parameter, FeedbackObject> map = ArrayListMultimap.create();		
		map.put(Parameter.PHRASE_LENGTH, PhraseLength_Longer.getInstance());
		map.put(Parameter.PHRASE_LENGTH, PhraseLength_Shorter.getInstance());
		map.put(Parameter.PHRASE_LENGTH, PhraseLength_MoreEven.getInstance());
		map.put(Parameter.PHRASE_LENGTH, PhraseLength_LessEven.getInstance());
		map.put(Parameter.TIME_SIGNATURE, TimeSignature_MoreEven.getInstance());
		map.put(Parameter.TIME_SIGNATURE, TimeSignature_LessEven.getInstance());
		map.put(Parameter.TEMPO, Tempo_Faster.getInstance());
		map.put(Parameter.TEMPO, Tempo_Slower.getInstance());
		map.put(Parameter.START_NOTE, StartNote_Lower.getInstance());
		map.put(Parameter.START_NOTE, StartNote_Higher.getInstance());
		map.put(Parameter.START_NOTE, StartNote_SignificantlyDifferent.getInstance());
		map.put(Parameter.XMLKEY, XMLKey_UpTo5KeysSharper.getInstance());
		map.put(Parameter.XMLKEY, XMLKey_UpTo5KeysFlatter.getInstance());
		map.put(Parameter.STRUCTURE_TONE_CONTOUR, StructureToneContour_Different.getInstance());
		map.put(Parameter.STRUCTURE_TONE_MULTIPLIER, StructureToneMultiplier_Higher.getInstance());
		map.put(Parameter.STRUCTURE_TONE_MULTIPLIER, StructureToneMultiplier_Lower.getInstance());
		map.put(Parameter.PHRASE_START_PERCENT, PhraseStart_Earlier.getInstance());
		map.put(Parameter.PHRASE_START_PERCENT, PhraseStart_Later.getInstance());
		map.put(Parameter.PHRASE_END_PERCENT, PhraseEnd_Earlier.getInstance());
		map.put(Parameter.PHRASE_END_PERCENT, PhraseEnd_Later.getInstance());
		map.put(Parameter.STRUCTURE_TONE_SPACING, StructureToneSpacing_Longer.getInstance());
		map.put(Parameter.STRUCTURE_TONE_SPACING, StructureToneSpacing_Shorter.getInstance());
		map.put(Parameter.EMBELLISHMENT_CLEARANCE, EmbellishmentClearanceFromPreviousStructureTone_More.getInstance());
		map.put(Parameter.EMBELLISHMENT_CLEARANCE, EmbellishmentClearanceFromPreviousStructureTone_Less.getInstance());
		map.put(Parameter.EMBELLISHMENT_REPETITION_PATTERN, EmbellishmentRepetitionPattern_MoreVariety.getInstance());
		map.put(Parameter.EMBELLISHMENT_REPETITION_PATTERN, EmbellishmentRepetitionPattern_LessVariety.getInstance());
		return map;
	}
	

//	@Test
//	final void testGetScoreAndNarrativeOfNewRndContainer ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetSetOfMutableParameters ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testObject ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetClass ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testHashCode ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testEquals ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testClone ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testToString ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testNotify ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testNotifyAll ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testWait ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testWaitLong ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testWaitLongInt ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testFinalize ()
//	{
//		fail("Not yet implemented"); // TODO
//	}
	
	
//	@Test
//	void multimap_test () throws Exception
//	{
//		Multimap<Parameter, FeedbackObject> map = ArrayListMultimap.create();
//		
//		map.put(Parameter.PHRASE_LENGTH, PhraseLength_Longer.getInstance());
//		map.put(Parameter.PHRASE_LENGTH, PhraseLength_Shorter.getInstance());
//		System.out.println("that thing");
//	}

}
