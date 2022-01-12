package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class Mu_GenAndMapTest
{
	
	@Nested 
	class when_parent_without_length
	{

		@Test
		void _calls_getTimeSignaturesToString_then_returns_empty_string()
		{
			Mu parent = new Mu("parent");
			assertEquals("", parent.getTimeSignaturesToString());
		}
		
		
		@Test
		void _then_returns_default_time_signature_for_any_bar_index() throws Exception
		{
			Mu parent = new Mu("parent");
			assertEquals(TimeSignature.FOUR_FOUR, parent.getTimeSignature(0));
			assertEquals(TimeSignature.FOUR_FOUR, parent.getTimeSignature(100));
			assertEquals(TimeSignature.FOUR_FOUR, parent.getTimeSignature(-100));
		}
		
		
		@Test
		void _has_explicitly_set_time_signature_generator_then_returns_that_time_signature_for_any_bar_index() throws Exception
		{
			Mu parent = new Mu("parent");
			parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
			assertEquals(TimeSignature.THREE_FOUR, parent.getTimeSignature(0));
			assertEquals(TimeSignature.THREE_FOUR, parent.getTimeSignature(100));
			assertEquals(TimeSignature.THREE_FOUR, parent.getTimeSignature(-100));
		}

	}
	
//	@Test
//	void when_child_with_time_signature_is_added_to_parent_at_1_bar_then_child_time_signature_is_added_to_parent_time_signature() throws Exception
//	{
//		Mu parent = new Mu("parent");
//		Mu child = new Mu("child");
//		child.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
//		child.setLength(1);
//		parent.addMu(child, 1);
//		assertEquals("4/4__3/4__", parent.getTimeSignaturesToString());
//	}


}
