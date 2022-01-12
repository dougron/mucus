package test.java.com.dougron.mucus.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_001;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_002;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.ParameterSpecificFeedback;
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


class ParameterSpecificFeedback_Test
{

	@Test
	void returns_singleton_class_of_feedbackObject() throws Exception
	{
		FeedbackObject[] foArr = ParameterSpecificFeedback.getParameterOptions(Parameter.PHRASE_LENGTH);
		assertEquals(PhraseLength_Longer.getInstance(), foArr[0]);
	}
	
	
	@Test
	void feedbackobject_returns_string_descriptor() throws Exception
	{
		assertEquals("PhraseLength_Longer", ParameterSpecificFeedback.getParameterOptions(Parameter.PHRASE_LENGTH)[0].getDescription());
	}
	
	
	@Nested
	class given_criteria_is_phrase_length_longer
	{
		@Test
		void _when_new_proposed_value_is_longer_then_parameter_specific_feedback_returns_trueCount_of_1() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseLength_Longer.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.75));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_shorter_then_parameter_specific_feedback_returns_trueCount_of_0() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseLength_Longer.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.1));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
	}
	
	
	@Nested
	class given_criteria_is_phrase_length_shorter
	{
		@Test
		void _when_proposed_value_is_longer_then_parameter_specific_feedback_returns_trueCount_of_0() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();	
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseLength_Shorter.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.75));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_proposed_value_is_shorter_then_parameter_specific_feedback_returns_trueCount_of_1() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseLength_Shorter.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.1));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
	}
	
	
	@Nested
	class given_criteria_is_phrase_length_more_even
	{
		@Test
		void _when_proposed_value_is_more_even_then_parameter_specific_feedback_returns_trueCount_of_1() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.3));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseLength_MoreEven.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.6));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_proposed_value_is_less_even_then_parameter_specific_feedback_returns_trueCount_of_0() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();	
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.6));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseLength_MoreEven.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.3));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
	}
		
	
	@Nested
	class given_criteria_is_phrase_length_less_even
	{
		@Test
		void _when_proposed_value_is_more_even_then_parameter_specific_feedback_returns_trueCount_of_0() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();	
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.3));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseLength_LessEven.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.6));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_proposed_value_is_less_even_then_parameter_specific_feedback_returns_trueCount_of_1() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();	
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.6));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseLength_LessEven.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.3));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
	}
	
		
	

	@Test
	void bar_balance_score_is_correct() throws Exception
	{
		assertEquals(0, ParameterSpecificFeedback.getBarLengthBalanceValue(1));
		assertEquals(0, ParameterSpecificFeedback.getBarLengthBalanceValue(2));
		assertEquals(1, ParameterSpecificFeedback.getBarLengthBalanceValue(3));
		assertEquals(0, ParameterSpecificFeedback.getBarLengthBalanceValue(4));
		assertEquals(2, ParameterSpecificFeedback.getBarLengthBalanceValue(5));
		assertEquals(1, ParameterSpecificFeedback.getBarLengthBalanceValue(6));
		assertEquals(2, ParameterSpecificFeedback.getBarLengthBalanceValue(7));
		assertEquals(0, ParameterSpecificFeedback.getBarLengthBalanceValue(8));
		assertEquals(3, ParameterSpecificFeedback.getBarLengthBalanceValue(9));
		assertEquals(2, ParameterSpecificFeedback.getBarLengthBalanceValue(10));
		assertEquals(3, ParameterSpecificFeedback.getBarLengthBalanceValue(11));
		assertEquals(1, ParameterSpecificFeedback.getBarLengthBalanceValue(12));
		assertEquals(3, ParameterSpecificFeedback.getBarLengthBalanceValue(13));
		assertEquals(2, ParameterSpecificFeedback.getBarLengthBalanceValue(14));
		assertEquals(3, ParameterSpecificFeedback.getBarLengthBalanceValue(15));
		assertEquals(0, ParameterSpecificFeedback.getBarLengthBalanceValue(16));
		assertEquals(4, ParameterSpecificFeedback.getBarLengthBalanceValue(17));
		assertEquals(3, ParameterSpecificFeedback.getBarLengthBalanceValue(18));
	}
	
	
	@Test
	void remainder_from_closest_power_of_2_below() throws Exception
	{
		assertEquals(0, ParameterSpecificFeedback.getRemainderFromPowerOfTwo(1));
		assertEquals(0, ParameterSpecificFeedback.getRemainderFromPowerOfTwo(2));
		assertEquals(1, ParameterSpecificFeedback.getRemainderFromPowerOfTwo(3));
		assertEquals(0, ParameterSpecificFeedback.getRemainderFromPowerOfTwo(4));
		assertEquals(1, ParameterSpecificFeedback.getRemainderFromPowerOfTwo(5));
		assertEquals(2, ParameterSpecificFeedback.getRemainderFromPowerOfTwo(6));
		assertEquals(3, ParameterSpecificFeedback.getRemainderFromPowerOfTwo(7));
		assertEquals(0, ParameterSpecificFeedback.getRemainderFromPowerOfTwo(8));
	}
	

	
	@Nested
	class given_criteria_is_time_signature_more_even
	{
		@Test
		void _when_new_proposed_value_is_more_even_then_specific_feedback_returns_trueCount_of_1() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();	
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, new TestRandom(0.4));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {TimeSignature_MoreEven.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, new TestRandom(0.1));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_less_even_then_parameter_specific_feedback_returns_trueCount_of_0() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, new TestRandom(0.1));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {TimeSignature_MoreEven.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, new TestRandom(0.4));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
	}
	
		
	@Nested
	class given_criteria_is_time_signature_less_even
	{
		@Test
		void _when_new_proposed_value_is_less_even_then_parameter_specific_feedback_returns_trueCount_of_1() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();	
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, new TestRandom(0.4));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {TimeSignature_LessEven.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, new TestRandom(0.1));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_more_even_then_parameter_specific_feedback_returns_trueCount_of_0() throws Exception
		{
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();	
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			rndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, new TestRandom(0.1));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {TimeSignature_LessEven.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.TIME_SIGNATURE, new TestRandom(0.4));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
	}
	
	
	@Nested
	class given_criteria_is_xml_key_up_to_5_keys_flatter
	{
		@Test
		void _when_new_proposed_value_is_within_5_flatter_keys_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {XMLKey_UpTo5KeysFlatter.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.4));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_beyond_5_flatter_keys_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {XMLKey_UpTo5KeysFlatter.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.0));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_a_sharper_key_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {XMLKey_UpTo5KeysFlatter.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.6));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
	}
	
	
	@Nested
	class given_criteria_is_xml_key_up_to_5_keys_sharper
	{
		@Test
		void _when_new_proposed_value_is_within_5_sharper_keys_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {XMLKey_UpTo5KeysSharper.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.6));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_beyond_5_sharper_keys_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.4));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {XMLKey_UpTo5KeysSharper.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.9));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_a_flatter_key_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {XMLKey_UpTo5KeysSharper.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.XMLKEY, new TestRandom(0.4));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
	}
	
	
	@Nested
	class given_criteria_is_tempo_is_faster
	{
		@Test
		void _when_new_proposed_value_is_faster_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.TEMPO, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {Tempo_Faster.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.TEMPO, new TestRandom(0.6));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_slower_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.TEMPO, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {Tempo_Faster.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.TEMPO, new TestRandom(0.4));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}		
	}
	
	
	@Nested
	class given_criteria_is_tempo_is_slower
	{
		@Test
		void _when_new_proposed_value_is_faster_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.TEMPO, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {Tempo_Slower.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.TEMPO, new TestRandom(0.6));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_slower_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.TEMPO, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {Tempo_Slower.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.TEMPO, new TestRandom(0.4));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}		
	}
	
	
	@Nested
	class given_criteria_is_structure_tone_spacing_is_longer
	{
		@Test
		void _when_new_proposed_value_is_longer_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, new TestRandom(0.1));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneSpacing_Longer.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, new TestRandom(0.9));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_shorter_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneSpacing_Longer.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, new TestRandom(0.1));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_structure_tone_spacing_is_shorter
	{
		@Test
		void _when_new_proposed_value_is_longer_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, new TestRandom(0.1));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneSpacing_Shorter.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, new TestRandom(0.9));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_shorter_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneSpacing_Shorter.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_SPACING, new TestRandom(0.1));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_structure_tone_contour_different
	{
		@Test
		void _when_new_proposed_value_is_different_enough_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_CONTOUR, new TestRandom(0.1));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneContour_Different.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_CONTOUR, new TestRandom(0.9));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_similar_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_CONTOUR, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneContour_Different.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_CONTOUR, new TestRandom(0.51));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_structure_tone_multiplier_is_larger
	{
		@Test
		void _when_new_proposed_value_is_higher_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneMultiplier_Higher.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, new TestRandom(0.9));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_lower_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneMultiplier_Higher.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, new TestRandom(0.5));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_structure_tone_multiplier_is_smaller
	{
		@Test
		void _when_new_proposed_value_is_higher_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneMultiplier_Lower.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, new TestRandom(0.9));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_lower_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StructureToneMultiplier_Lower.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.STRUCTURE_TONE_MULTIPLIER, new TestRandom(0.5));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_start_note_higher
	{
		@Test
		void _when_new_proposed_value_is_higher_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StartNote_Higher.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.9));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_lower_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StartNote_Higher.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.5));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_start_note_lower
	{
		@Test
		void _when_new_proposed_value_is_higher_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StartNote_Lower.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.9));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_lower_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StartNote_Lower.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.5));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_start_note_significanty_different
	{
		@Test
		void _when_new_proposed_value_is_much_higher_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StartNote_SignificantlyDifferent.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.9));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_much_lower_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StartNote_SignificantlyDifferent.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.1));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_similar_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {StartNote_SignificantlyDifferent.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.START_NOTE, new TestRandom(0.55));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}

	
	@Nested
	class given_criteria_is_phrase_start_earlier
	{
		@Test
		void _when_new_proposed_value_is_later_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseStart_Earlier.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, new TestRandom(0.9));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_earlier_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseStart_Earlier.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, new TestRandom(0.5));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_phrase_start_later
	{
		@Test
		void _when_new_proposed_value_is_later_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseStart_Later.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, new TestRandom(0.9));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_earlier_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseStart_Later.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_START_PERCENT, new TestRandom(0.5));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_phrase_end_earlier
	{
		@Test
		void _when_new_proposed_value_is_later_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseEnd_Earlier.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, new TestRandom(0.9));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_earlier_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseEnd_Earlier.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, new TestRandom(0.5));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_phrase_end_later
	{
		@Test
		void _when_new_proposed_value_is_later_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseEnd_Later.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, new TestRandom(0.9));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_is_earlier_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_002.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {PhraseEnd_Later.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.PHRASE_END_PERCENT, new TestRandom(0.5));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_embellishment_pattern_more_variety
	{
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new TestRandom(0.05));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {EmbellishmentRepetitionPattern_MoreVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new TestRandom(0.15));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new TestRandom(0.15));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {EmbellishmentRepetitionPattern_MoreVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new TestRandom(0.05));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_embellishment_pattern_less_variety
	{
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new TestRandom(0.05));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {EmbellishmentRepetitionPattern_LessVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new TestRandom(0.15));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new TestRandom(0.15));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {EmbellishmentRepetitionPattern_LessVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_REPETITION_PATTERN, new TestRandom(0.05));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_embellishment_clearance_more
	{
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, new TestRandom(0.25));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {EmbellishmentClearanceFromPreviousStructureTone_More.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, new TestRandom(0.75));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, new TestRandom(0.75));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {EmbellishmentClearanceFromPreviousStructureTone_More.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, new TestRandom(0.25));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_embellishment_clearance_less
	{
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, new TestRandom(0.25));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {EmbellishmentClearanceFromPreviousStructureTone_Less.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, new TestRandom(0.75));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();		
			rndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, new TestRandom(0.75));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {EmbellishmentClearanceFromPreviousStructureTone_Less.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setSingleValueVariable(Parameter.EMBELLISHMENT_CLEARANCE, new TestRandom(0.25));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}


	@Test
	void given_criteria_is_chord_progression_fewer_function_changes_then_getRootList_returns_correct_value () throws Exception
	{
		List<Double> list = Arrays.asList(0.0, 0.51, 0.17, 0.17, 0.55, 0.4, 0.0, 0.7, 0.17, 0.9);
				
		ChordProgression_FewerFunctionChanges ffc = (ChordProgression_FewerFunctionChanges)ChordProgression_FewerFunctionChanges.getInstance();
		String[] chords = new String[] {"", "m", "m", "", "", "m"};
		ArrayList<Integer> rootList = ffc.getRootList(list, chords );
//		for (Integer i: rootList) System.out.println(i);
//		System.out.println("-----");
		ArrayList<Integer> intervalList = ffc.getIntervalList(rootList);
//		for (Integer i: intervalList) System.out.println(i);
		assertEquals(3, intervalList.get(0));
		assertEquals(2, intervalList.get(1));
		assertEquals(0, intervalList.get(2));
		assertEquals(2, intervalList.get(3));
		assertEquals(1, intervalList.get(4));
		assertEquals(2, intervalList.get(5));
		assertEquals(3, intervalList.get(6));
		assertEquals(3, intervalList.get(7));
		assertEquals(3, intervalList.get(8));
		assertEquals(2, intervalList.get(9));
	}


	@Nested
	class given_criteria_is_chord_progression_more_function_changes
	{
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
//			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.0));
			rndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, new double[] {0.0, 0.4, 0.0, 0.4});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {ChordProgression_MoreFunctionChanges.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
//			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.0));
			newRndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, new double[] {0.0, 0.17, 0.0, 0.17});
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
//			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.0));
			rndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, new double[] {0.0, 0.17, 0.0, 0.17});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {ChordProgression_MoreFunctionChanges.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
//			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.0));
			newRndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, new double[] {0.0, 0.4, 0.0, 0.4});
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_chord_progression_fewer_function_changes
	{
		@Test
		void _when_new_proposed_value_has_more_function_changes_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
//			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.0));
			rndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, new double[] {0.0, 0.4, 0.0, 0.4});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {ChordProgression_FewerFunctionChanges.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
//			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.0));
			newRndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, new double[] {0.0, 0.17, 0.0, 0.17});
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_less_function_changes_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
//			rndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.0));
			rndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, new double[] {0.0, 0.17, 0.0, 0.17});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {ChordProgression_FewerFunctionChanges.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
//			newRndContainer.setSingleValueVariable(Parameter.PHRASE_LENGTH, new TestRandom(0.0));
			newRndContainer.setValueList(Parameter.CHORD_LIST_GENERATOR, new double[] {0.0, 0.4, 0.0, 0.4});
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	

	@Nested
	class given_criteria_is_mug_list_count_fewer
	{
		@Test
		void _when_new_proposed_value_has_higher_count_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_CountIsLess.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.9));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_lower_count_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.9));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_CountIsLess.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_mug_list_count_more
	{
		@Test
		void _when_new_proposed_value_has_higher_count_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.5));
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_CountIsMore.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.5));
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.2));
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_lower_count_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.5));
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.5));
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_CountIsMore.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.5));
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.7));
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_mug_list_rhythm_shorter_on_average
	{
		@Test
		void _when_new_proposed_value_has_shorter_length_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.5});
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_RhythmShorterOnAverage.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.2});
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_longer_length_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.5});
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_RhythmShorterOnAverage.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.8});
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_mug_list_rhythm_longer_on_average
	{
		@Test
		void _when_new_proposed_value_has_shorter_length_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.5});
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_RhythmLongerOnAverage.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.2});
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_longer_length_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.5});
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			rndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_RhythmLongerOnAverage.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.8});
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 0, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			newRndContainer.setIndexedSingleValue(Parameter.MUG_LIST_COUNT, 1, new TestRandom(0.1));	// sets the count value for the totalEmbellishmentCount calculation
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_mug_list_rhythm_has_more_variety
	{
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.2});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_RhythmHasMoreVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.5});
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.5});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_RhythmHasMoreVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.8});
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_mug_list_rhythm_has_less_variety
	{
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.2});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_RhythmHasLessVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.5});
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.5});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_RhythmHasLessVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 0, new double[] {0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_RHYTHM, 1, new double[] {0.8});
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_mug_list_pitch_has_more_variety
	{
		@Test
		void _getCountOfUniquePitchEmbellishments_returns_correct_count () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0, new double[] {0.5, 0.5, 0.5});
			MugList_PitchHasMoreVariety hmv = (MugList_PitchHasMoreVariety)MugList_PitchHasMoreVariety.getInstance();
			assertEquals(1, hmv.getCountOfUniquePitchEmbellishments(rndContainer, rmg));
		}
		
		
		@Test
		void _getCountOfUniquePitchEmbellishments_returns_correct_count_when_there_are_several_pitch_lists () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0, new double[] {0.5, 0.5, 0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.9});
			MugList_PitchHasMoreVariety hmv = (MugList_PitchHasMoreVariety)MugList_PitchHasMoreVariety.getInstance();
			assertEquals(2, hmv.getCountOfUniquePitchEmbellishments(rndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0,new double[] {0.5, 0.5, 0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.9});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_PitchHasMoreVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0,new double[] {0.5, 0.5, 0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.5});
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0, new double[] {0.5, 0.5, 0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.5});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_PitchHasMoreVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0, new double[] {0.5, 0.5, 0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.9});
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
	
	
	@Nested
	class given_criteria_is_mug_list_pitch_has_less_variety
	{
				
		@Test
		void _when_new_proposed_value_has_less_variety_then_parameter_specific_feedback_returns_trueCount_of_1 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0, new double[] {0.5, 0.5, 0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.9});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_PitchHasLessVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0, new double[] {0.5, 0.5, 0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.5});
			assertEquals(1, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}
		
		
		@Test
		void _when_new_proposed_value_has_more_variety_then_parameter_specific_feedback_returns_trueCount_of_0 () throws Exception
		{
			RandomMelodyGenerator rmg = RMG_001.getInstance();	
			RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0, new double[] {0.5, 0.5, 0.5});
			rndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.5});
			FeedbackObject[] feedbackCriteria = new FeedbackObject[] {MugList_PitchHasLessVariety.getInstance()};
			RMRandomNumberContainer newRndContainer = new RMRandomNumberContainer();
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 0, new double[] {0.5, 0.5, 0.5});
			newRndContainer.setIndexedValueList(Parameter.MUG_LIST_PITCH, 1, new double[] {0.5, 0.5, 0.9});
			assertEquals(0, ParameterSpecificFeedback.getTrueCount(rndContainer, feedbackCriteria, newRndContainer, rmg));
		}	
	}
}
