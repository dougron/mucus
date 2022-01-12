package main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.ParameterAndIndex;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_001;

class RNDValueObject_Tests
{
	
	
	@Nested
	class given_single_value_object
	{
		@Test
		void _then_getValue_returns_correct_value () throws Exception
		{
			RNDValueObject rvo = new SingleValue(0.5);
			assertEquals(0.5, rvo.getValue());		
		}
		
		// these two commented out tests assume the SingleValue returns an arraylist of its single value from getValueList
		// currently I am assuming that these return null
//		@Test
//		void _then_getValueList_length_returns_1 () throws Exception
//		{
//			RNDValueObject rvo = new SingleValue(0.5);
//			assertEquals(1, rvo.getValueList().size());
//		}
		
		
//		@Test
//		void _then_getValueList_item_index_0_returns_correct_value() throws Exception
//		{
//			RNDValueObject rvo = new SingleValue(0.5);
//			assertEquals(0.5, rvo.getValueList().get(0));
//		}
		
		
		@Test
		void _text_file_to_string_returns_correct_value () throws Exception
		{
			RNDValueObject rvo = new SingleValue(0.5);
			assertEquals("SingleValue:0.5", rvo.getTextFileString());
		}
		
		
		@Test
		void _then_addValue_does_nothing_comma_as_expected () throws Exception
		{
			RNDValueObject rvo = new SingleValue(0.5);
			int parameterIndex = 0;
			rvo.add(parameterIndex, 0.1);
			assertEquals(0.5, rvo.getValue());
		}
		
		
		@Test
		void _then_getValue_with_parameterIndex_returns_the_only_value_available () throws Exception
		{
			RNDValueObject rvo = new SingleValue(0.5);
			int parameterIndex = 0;
			assertEquals(0.5, rvo.getValue(parameterIndex));
		}
		
		
		@Test
		void _then_getValueList_returns_null () throws Exception
		{
			RNDValueObject rvo = new SingleValue(0.5);
			assertEquals(null, rvo.getValueList());
			int parameterIndex = 0;
			assertEquals(null, rvo.getValueList(parameterIndex));
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_of_a_value_object_from_itself_equals_0 () throws Exception
		{
			RNDValueObject rvo = new SingleValue(0.5);
			assertEquals(0.0, rvo.getSquaredDistanceMeasure(rvo));
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_with_different_values_is_square_of_that_difference () throws Exception
		{
			RNDValueObject rvo = new SingleValue(0.5);
			RNDValueObject rvo2 = new SingleValue(0.1);
			assertEquals(0.16, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
			assertEquals(0.16, rvo2.getSquaredDistanceMeasure(rvo), 1e-6);
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_where_the_2nd_object_is_null_assumes_0_for_all_values () throws Exception
		{
			RNDValueObject rvo = new SingleValue(0.5);
			RNDValueObject rvo2 = null;
			assertEquals(0.25, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
		}
	}
	
	
	@Nested
	class given_value_list_object
	{
		@Test
		void _then_getValue_returns_first_item_in_list () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			assertEquals(0.1, rvo.getValue());
		}
		
		
		@Test
		void _then_empty_valueList_returns_getValue_of_minus1 () throws Exception
		{
			RNDValueObject rvo = new ValueList();
			assertEquals(-1, rvo.getValue());
		}
		
		
		@Test
		void _then_getValueList_length_is_the_length_of_the_input_argument () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			assertEquals(4, rvo.getValueList().size());
		}
		
		
		@Test
		void _then_getValueList_reflect_the_content_of_the_input_argument () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			assertEquals(0.1, rvo.getValueList().get(0));
			assertEquals(0.3, rvo.getValueList().get(1));
			assertEquals(0.5, rvo.getValueList().get(2));
			assertEquals(0.7, rvo.getValueList().get(3));
		}
		
		
		@Test
		void _then_getValueList_with_parameterIndex_reflect_the_content_of_the_input_argument () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			assertEquals(0.1, rvo.getValueList(0).get(0));
			assertEquals(0.3, rvo.getValueList(0).get(1));
			assertEquals(0.5, rvo.getValueList(0).get(2));
			assertEquals(0.7, rvo.getValueList(0).get(3));
			assertEquals(0.1, rvo.getValueList(1).get(0));
			assertEquals(0.3, rvo.getValueList(1).get(1));
			assertEquals(0.5, rvo.getValueList(1).get(2));
			assertEquals(0.7, rvo.getValueList(1).get(3));
		}
		
		
		@Test
		void _then_getValue_with_various_arguments_returns_the_expected_values () throws Exception
		{
			// these are not neccessarily the getValue() methods that are best for this subclass
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			assertEquals(0.1, rvo.getValue());
			assertEquals(0.1, rvo.getValue(0));	// parameterIndex
			assertEquals(0.1, rvo.getValue(1));
			assertEquals(0.1, rvo.getValue(0, 0));	// parameterIndex, listIndex: parameterIndex not relevant			
			assertEquals(0.3, rvo.getValue(0, 1));	// parameterIndex, listIndex
			assertEquals(0.5, rvo.getValue(0, 2));	// parameterIndex, listIndex
			assertEquals(0.7, rvo.getValue(0, 3));	// parameterIndex, listIndex
			assertEquals(0.1, rvo.getValue(1, 0));	// parameterIndex, listIndex
			assertEquals(0.3, rvo.getValue(1, 1));	// parameterIndex, listIndex
			assertEquals(0.5, rvo.getValue(1, 2));	// parameterIndex, listIndex
			assertEquals(0.7, rvo.getValue(1, 3));	// parameterIndex, listIndex
			
		}
		
		
		@Test
		void _text_file_to_string_returns_correct_value () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			assertEquals("ValueList:0.1,0.3,0.5,0.7,", rvo.getTextFileString());
			rvo = new ValueList(new double[] {0.1, 0.3, 0.5, 0.7});
			assertEquals("ValueList:0.1,0.3,0.5,0.7,", rvo.getTextFileString());
			Double[] arr = new Double[] {0.1, 0.3, 0.5, 0.7};
			java.util.List<Double> list = Arrays.asList(arr);
			rvo = new ValueList(list);
			assertEquals("ValueList:0.1,0.3,0.5,0.7,", rvo.getTextFileString());
		}
		
		
		@Test
		void _then_addValue_adds_items_as_expected () throws Exception
		{
			RNDValueObject rvo = new ValueList();
			Double[] arr = new Double[] {0.1, 0.3, 0.5, 0.7};
			int parameterIndex = 0;
			for (Double d: arr) rvo.add(parameterIndex, d);
			assertEquals("ValueList:0.1,0.3,0.5,0.7,", rvo.getTextFileString());
		}
		
		
		@Test
		void _then_deepCopy_returns_item_with_same_textFileString () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			RNDValueObject rvo2 = rvo.deepCopy();
			assertNotEquals(rvo, rvo2);
			assertEquals(rvo.getTextFileString(), rvo2.getTextFileString());
		}
		
		
		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_equal_size_to_thevalueList () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(4, list.size());
		}
		
		
		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_with_items_showing_correct_listIndex_values () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(0, list.get(0).getListIndex());
			assertEquals(1, list.get(1).getListIndex());
			assertEquals(2, list.get(2).getListIndex());
			assertEquals(3, list.get(3).getListIndex());
		}
		
		
		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_with_items_showing_correct_start_values () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(0.1, list.get(0).getStartValue());
			assertEquals(0.3, list.get(1).getStartValue());
			assertEquals(0.5, list.get(2).getStartValue());
			assertEquals(0.7, list.get(3).getStartValue());
		}
	
	
		@Test
		void _when_value_at_listIndex_is_changed_then_get_value_at_that_index_is_equal () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			list.get(0).setEndValue(0.88);
			rvo.change(list.get(0));
			assertEquals(0.88, rvo.getValue(0, 0));
		}
		
		
		@Test
		void _then_getQuantizedDeepCopy_returns_object_that_is_different () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.5, 0.7});
			RNDValueObject rvoCopy = rvo.getQuantizedDeepCopy(Parameter.MUG_LIST_RHYTHM, RMG_001.getInstance());
			assertNotEquals(rvo, rvoCopy);
		}
		
		
		@Test
		void _then_getQuantizedDeepCopy_returns_correctly_quantized_values () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.55, 0.9});
			RNDValueObject rvoCopy = rvo.getQuantizedDeepCopy(Parameter.MUG_LIST_RHYTHM, RMG_001.getInstance());
			assertEquals(0.0, rvoCopy.getValue(0, 0));
			assertEquals(0.25, rvoCopy.getValue(0, 1));
			assertEquals(0.5, rvoCopy.getValue(0, 2));
			assertEquals(0.75, rvoCopy.getValue(0, 3));
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_self_is_0 () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.55, 0.9});
			assertEquals(0.0, rvo.getSquaredDistanceMeasure(rvo));
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_with_different_values_is_square_of_that_difference () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.55, 0.9});
			RNDValueObject rvo2 = new ValueList(new Double[] {0.1, 0.8, 0.55, 0.9});
			assertEquals(0.25, rvo.getSquaredDistanceMeasure(rvo2));
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_with_different_lengths_is_based_on_the_assumption_that_the_shorter_uses_0_for_missing_items () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.1, 0.3, 0.55, 0.4});
			RNDValueObject rvo2 = new ValueList(new Double[] {0.1, 0.3, 0.55});
			assertEquals(0.16, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
			assertEquals(0.16, rvo2.getSquaredDistanceMeasure(rvo), 1e-6);
		}
		
		
		@Test
		void _then_toString_returns_a_string () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.5, 0.3, 0.55, 0.4});
			assertTrue(rvo.toString() instanceof String);
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_where_the_2nd_object_is_null_assumes_0_for_all_values () throws Exception
		{
			RNDValueObject rvo = new ValueList(new Double[] {0.5, 0.5});
			RNDValueObject rvo2 = null;
			assertEquals(0.5, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
		}
	}
	
	
	@Nested
	class given_indexed_single_value_object
	{
		@Test
		void _which_is_empty_returns_minus1_for_any_index () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			assertEquals(-1.0, rvo.getValue(0));
			assertEquals(-1.0, rvo.getValue(42));		
		}
		
		
		@Test
		void _with_values_returns_minus1_for_any_nonexistant_index () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			assertEquals(-1.0, rvo.getValue(1));
		}

		
		@Test
		void _with_values_returns_correct_value_for_any_existing_index () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			assertEquals(0.5, rvo.getValue(0));
			int arbitraryListIndex = 5;
			assertEquals(0.5, rvo.getValue(0, arbitraryListIndex));
		}
		
		
		@Test
		void _text_file_to_string_returns_correct_value () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
//			System.out.println(rvo.getTextFileString());
			assertEquals("IndexedSingleValue:0=0.5;1=0.75;", rvo.getTextFileString());
		}
		
		
		@Test
		void _then_getValueList_returns_null () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			assertEquals(null, rvo.getValueList());
			int arbitraryParameterIndex = 12;
			assertEquals(null, rvo.getValueList(arbitraryParameterIndex));
		}
		
		
		@Test
		void _then_getValue_with_no_arguments_returns_0 () throws Exception
		// uses a HashMap, so cannot return 'first' value as there is no assurance of order
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			assertEquals(0, rvo.getValue());
		}
		
		
		@Test
		void _then_change_value_reflects_that_change_when_getValue_for_same_parameterIndex_is_called () throws Exception
		{
			int arbitraryListIndex = 12;
			int significantParameterIndex = 0;
			ParameterAndIndex pai = new ParameterAndIndex(Parameter.CHORD_LIST_GENERATOR, significantParameterIndex, arbitraryListIndex);
			pai.setStartValue(0.5);
			pai.setEndValue(0.99);
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.change(pai);
			assertEquals(0.99, rvo.getValue(significantParameterIndex));
		}
		
		
		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_equal_size_to_the_number_of_items_added () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(3, list.size());
		}
		

		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_showing_expected_listIndex_values () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(0, list.get(0).getListIndex());
			assertEquals(0, list.get(1).getListIndex());
			assertEquals(0, list.get(2).getListIndex());
		}
		
		
		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_showing_expected_parameterIndex_values () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(2, 0.65);
			rvo.add(1, 0.75);			
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(0, list.get(0).getParameterIndex());
			assertEquals(1, list.get(1).getParameterIndex());
			assertEquals(2, list.get(2).getParameterIndex());
		}
		
		
		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_showing_expected_start_values () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(3, 0.35);
			rvo.add(2, 0.65);
			rvo.add(-1, 0.44);
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(0.5, list.get(1).getStartValue());
			assertEquals(0.75, list.get(2).getStartValue());
			assertEquals(0.65, list.get(3).getStartValue());
		}
		
		
		@Test
		void _then_deepCopy_returns_item_with_same_textFileString () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(2, 0.65);
			rvo.add(1, 0.75);
			RNDValueObject rvo2 = rvo.deepCopy();
			assertNotEquals(rvo, rvo2);
			assertEquals(rvo.getTextFileString(), rvo2.getTextFileString());
		}
		
		
		@Test
		void _then_getQuantizedDeepCopy_returns_object_that_is_different () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(2, 0.65);
			rvo.add(1, 0.75);
			RNDValueObject rvoCopy = rvo.getQuantizedDeepCopy(Parameter.MUG_LIST_RHYTHM, RMG_001.getInstance());
			assertNotEquals(rvo, rvoCopy);
		}
		
		
		@Test
		void _then_getQuantizedDeepCopy_returns_correctly_quantized_values () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.2);
			rvo.add(2, 0.65);
			rvo.add(1, 0.79);
			rvo.add(3, 0.4);
			RNDValueObject rvoCopy = rvo.getQuantizedDeepCopy(Parameter.MUG_LIST_RHYTHM, RMG_001.getInstance());
			assertEquals(0.0, rvoCopy.getValue(0));
			assertEquals(0.75, rvoCopy.getValue(1));
			assertEquals(0.5, rvoCopy.getValue(2));
			assertEquals(0.25, rvoCopy.getValue(3));
		}
		
		
		@Test
		void _then_getKeySeyForBothRVOs_returns_set_of_all_parameter_indices () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.2);
			rvo.add(2, 0.65);
			rvo.add(1, 0.79);
			RNDValueObject rvo1 = new IndexedSingleValue();
			rvo1.add(2, 0.2);
			rvo1.add(3, 0.65);
			rvo1.add(4, 0.79);
			HashSet<Integer> keySet = ((IndexedSingleValue)rvo).getKeySetForBothRVOs(rvo1);
			assertEquals(5, keySet.size());
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_self_is_0 () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.2);
			rvo.add(2, 0.65);
			rvo.add(1, 0.79);
			assertEquals(0.0, rvo.getSquaredDistanceMeasure(rvo));
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_with_different_values_is_square_of_that_difference () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.2);
			rvo.add(1, 0.65);
			rvo.add(2, 0.79);
			RNDValueObject rvo2 = new IndexedSingleValue();
			rvo2.add(0, 0.7);
			rvo2.add(1, 0.65);
			rvo2.add(2, 0.79);
			assertEquals(0.25, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_with_different_parameter_sets_is_based_on_the_assumption_that_missing_parameters_are_assumed_to_be_0 () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.2);
			rvo.add(1, 0.65);
			rvo.add(2, 0.4);
			RNDValueObject rvo2 = new IndexedSingleValue();
			rvo2.add(0, 0.2);
			rvo2.add(1, 0.65);

			assertEquals(0.16, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
			assertEquals(0.16, rvo2.getSquaredDistanceMeasure(rvo), 1e-6);
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_where_the_2nd_object_is_null_assumes_0_for_all_values () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.5);
			rvo.add(1, 0.5);
			RNDValueObject rvo2 = null;

			assertEquals(0.5, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
			
		}
		
		
		@Test
		void _then_toString_returns_a_string () throws Exception
		{
			RNDValueObject rvo = new IndexedSingleValue();
			rvo.add(0, 0.2);
			rvo.add(1, 0.65);
			rvo.add(2, 0.4);
			assertTrue(rvo.toString() instanceof String);
		}
		
		
	}
	
	
	@Nested
	class given_indexed_value_list_object
	{
		@Test
		void _which_is_empty_returns_null_for_any_parameterIndex () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			assertEquals(null, rvo.getValueList(0));
			assertEquals(null, rvo.getValueList(42));		
		}
		
		
		@Test
		void _then_getValueList_with_parameterIndex_returns_null_if_parameterIndex_does_not_exist () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(0, 0.8);
			rvo.add(0, 0.1);
			rvo.add(1, 0.5);
			rvo.add(1, 0.75);

			int arbitraryParameterIndex = 12;
			assertEquals(null, rvo.getValueList(arbitraryParameterIndex));
		}


		@Test
		void _with_values_returns_correct_value_for_any_existing_index () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(0, 0.8);
			rvo.add(0, 0.1);
			assertEquals(3, rvo.getValueList(0).size());
			assertEquals(0.5, rvo.getValueList(0).get(0));
			assertEquals(0.8, rvo.getValueList(0).get(1));
			assertEquals(0.1, rvo.getValueList(0).get(2));
		}
		
		
		@Test
		void _text_file_to_string_returns_correct_value () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(0, 0.8);
			rvo.add(0, 0.1);
			rvo.add(1, 0.5);
			rvo.add(1, 0.75);
//			System.out.println(rvo.getTextFileString());
			assertEquals("IndexedValueList:0=0.5,0.8,0.1,;1=0.5,0.75,;", rvo.getTextFileString());
		}
			
		
		@Test
		void _then_getValueList_returns_null () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(0, 0.8);
			rvo.add(0, 0.1);
			rvo.add(1, 0.5);
			rvo.add(1, 0.75);
			assertEquals(null, rvo.getValueList());
//			int arbitraryParameterIndex = 12;
//			assertEquals(null, rvo.getValueList(arbitraryParameterIndex));
		}
		
		
		@Test
		void _then_getValue_with_various_arguments_returns_the_expected_values () throws Exception
		{
			// these are not neccessarily the getValue() methods that are best for this subclass
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(0, 0.8);
			rvo.add(0, 0.1);
			rvo.add(1, 0.5);
			rvo.add(1, 0.75);
			assertEquals(0.0, rvo.getValue(0));	
			assertEquals(0.0, rvo.getValue(1));
		
			
		}
				

		@Test
		void _then_getValue_with_no_arguments_returns_0 () throws Exception
		// uses a HashMap, so cannot return 'first' value as there is no assurance of order
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			assertEquals(0, rvo.getValue());
		}
		
		
		@Test
		void _then_change_value_reflects_that_change_when_getValue_for_same_parameterIndex_is_called () throws Exception
		{
			int significantListIndex = 1;
			int significantParameterIndex = 0;
			ParameterAndIndex pai = new ParameterAndIndex(Parameter.CHORD_LIST_GENERATOR, significantParameterIndex, significantListIndex);
			pai.setStartValue(0.5);
			pai.setEndValue(0.99);
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.change(pai);
			assertEquals(0.99, rvo.getValue(significantParameterIndex, significantListIndex));
		}
		
		
		@Test
		void _then_change_value_reflects_no_change_when_value_to_change_identified_by_parameter_and_list_index_does_not_exist () throws Exception
		{
			int significantListIndex = 1;
			int significantParameterIndex = 3;
			ParameterAndIndex pai = new ParameterAndIndex(Parameter.CHORD_LIST_GENERATOR, significantParameterIndex, significantListIndex);
			pai.setStartValue(0.5);
			pai.setEndValue(0.99);
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.change(pai);
			assertEquals(0.0, rvo.getValue(significantParameterIndex, significantListIndex));
		}
		
	
		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_equal_size_to_the_number_of_items_added () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(6, list.size());
		}
		

		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_showing_expected_listIndex_values () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.25);
			rvo.add(2, 0.15);
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(0, list.get(0).getListIndex());
			assertEquals(1, list.get(1).getListIndex());
			assertEquals(0, list.get(2).getListIndex());
			assertEquals(1, list.get(3).getListIndex());
			assertEquals(0, list.get(4).getListIndex());
			assertEquals(1, list.get(5).getListIndex());
		}
		

		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_showing_expected_parameterIndex_values () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.25);
			rvo.add(2, 0.15);			
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(0, list.get(0).getParameterIndex());
			assertEquals(0, list.get(1).getParameterIndex());
			assertEquals(1, list.get(2).getParameterIndex());
			assertEquals(1, list.get(3).getParameterIndex());
			assertEquals(2, list.get(4).getParameterIndex());
			assertEquals(2, list.get(5).getParameterIndex());
		}
		
	
		@Test
		void _then_getEqualOpportunityParameterAndIndexList_returns_list_showing_expected_start_values () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.25);
			rvo.add(2, 0.15);
			ArrayList<ParameterAndIndex> list = rvo.getEqualOpportunityParameterAndIndexList(Parameter.CHORD_LIST_GENERATOR);
			assertEquals(0.5, list.get(0).getStartValue());
			assertEquals(0.6, list.get(1).getStartValue());
			assertEquals(0.75, list.get(2).getStartValue());
			assertEquals(0.25, list.get(3).getStartValue());
			assertEquals(0.65, list.get(4).getStartValue());
			assertEquals(0.15, list.get(5).getStartValue());
			
		}
		

		@Test
		void _then_deepCopy_returns_item_with_same_textFileString () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.25);
			rvo.add(2, 0.15);
			RNDValueObject rvo2 = rvo.deepCopy();
			assertNotEquals(rvo, rvo2);
			assertEquals(rvo.getTextFileString(), rvo2.getTextFileString());
		}
		

		@Test
		void _then_getQuantizedDeepCopy_returns_object_that_is_different () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(1, 0.75);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.25);
			rvo.add(2, 0.15);
			RNDValueObject rvoCopy = rvo.getQuantizedDeepCopy(Parameter.MUG_LIST_RHYTHM, RMG_001.getInstance());
			assertNotEquals(rvo, rvoCopy);
		}
		

		@Test
		void _then_getQuantizedDeepCopy_returns_correctly_quantized_values () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.55);
			rvo.add(1, 0.79);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.33);
			rvo.add(2, 0.15);
			RNDValueObject rvoCopy = rvo.getQuantizedDeepCopy(Parameter.MUG_LIST_RHYTHM, RMG_001.getInstance());
			assertEquals(0.5, rvoCopy.getValue(0, 0));
			assertEquals(0.5, rvoCopy.getValue(0, 1));
			assertEquals(0.75, rvoCopy.getValue(1, 0));
			assertEquals(0.25, rvoCopy.getValue(1, 1));
			assertEquals(0.5, rvoCopy.getValue(2, 0));
			assertEquals(0.0, rvoCopy.getValue(2, 1));
		}
		

		@Test
		void _then_getKeySeyForBothRVOs_returns_set_of_all_parameter_indices () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.2);
			rvo.add(2, 0.65);
			rvo.add(1, 0.79);
			RNDValueObject rvo1 = new IndexedValueList();
			rvo1.add(2, 0.2);
			rvo1.add(3, 0.65);
			rvo1.add(4, 0.79);
			HashSet<Integer> keySet = ((IndexedValueList)rvo).getKeySetForBothRVOs(rvo1);
			assertEquals(5, keySet.size());
		}
		

		@Test
		void _then_getSquaredDistanceMeasure_from_self_is_0 () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.55);
			rvo.add(1, 0.79);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.33);
			rvo.add(2, 0.15);
			assertEquals(0.0, rvo.getSquaredDistanceMeasure(rvo));
		}
		

		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_with_different_values_is_square_of_that_difference () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.55);
			rvo.add(1, 0.79);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.33);
			rvo.add(2, 0.15);
			RNDValueObject rvo2 = new IndexedValueList();
			rvo2.add(0, 0.55);
			rvo2.add(1, 0.79);
			rvo2.add(2, 0.65);
			rvo2.add(0, 0.1);
			rvo2.add(1, 0.33);
			rvo2.add(2, 0.15);
			assertEquals(0.25, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
		}
		

		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_with_same_parameter_sets_with_different_list_lengths_is_based_on_the_assumption_that_missing_parameters_are_assumed_to_be_0 () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.55);
			rvo.add(1, 0.79);
			rvo.add(2, 0.65);
			rvo.add(0, 0.6);
			rvo.add(1, 0.33);
			rvo.add(2, 0.4);
			RNDValueObject rvo2 = new IndexedValueList();
			rvo2.add(0, 0.55);
			rvo2.add(1, 0.79);
			rvo2.add(2, 0.65);
			rvo2.add(0, 0.6);
			rvo2.add(1, 0.33);


			assertEquals(0.16, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
			assertEquals(0.16, rvo2.getSquaredDistanceMeasure(rvo), 1e-6);
		}
 
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_with_different_parameter_is_based_on_the_assumption_that_missing_parameters_are_assumed_to_be_0 () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.55);
			rvo.add(1, 0.79);
			rvo.add(2, 0.4);
			rvo.add(0, 0.6);
			rvo.add(1, 0.33);

			RNDValueObject rvo2 = new IndexedValueList();
			rvo2.add(0, 0.55);
			rvo2.add(1, 0.79);

			rvo2.add(0, 0.6);
			rvo2.add(1, 0.33);


			assertEquals(0.16, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
			assertEquals(0.16, rvo2.getSquaredDistanceMeasure(rvo), 1e-6);
		}
		
		
		@Test
		void _then_getSquaredDistanceMeasure_from_a_value_object_where_the_2nd_object_is_null_assumes_0_for_all_values () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.5);
			rvo.add(1, 0.5);
			rvo.add(2, 0.5);
			rvo.add(0, 0.5);
			rvo.add(1, 0.5);

			RNDValueObject rvo2 = null;

			assertEquals(1.25, rvo.getSquaredDistanceMeasure(rvo2), 1e-6);
		}
		
		
		

		@Test
		void _then_toString_returns_a_string () throws Exception
		{
			RNDValueObject rvo = new IndexedValueList();
			rvo.add(0, 0.2);
			rvo.add(1, 0.65);
			rvo.add(2, 0.4);
			assertTrue(rvo.toString() instanceof String);
		}
		
		
	}
	
	
	
	
	
}
