package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class Mu_RulerTests
{

	@Test
	void gives_correct_tostring_with_multiple_children_with_chords() throws Exception
	{
		Mu parent = new Mu("parent");
		
		Mu child1 = new Mu("child1");
		child1.setLengthInBars(4);
		child1.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));		
		parent.addMu(child1, 0);
		
		Mu child2 = new Mu("child2");
		child2.setLengthInBars(4);
		child2.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.FOUR_FOUR));		
		parent.addMuToEndOfSibling(child2, 0, child1);
		
		assertEquals("3/4__3/4__3/4__3/4__4/4__4/4__4/4__4/4__", parent.getRuler().getTimeSignatureToString());
	}


	@Test
	void get_start_tempo_returns_same_value_as_set_start_tempo() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setStartTempo(110.0);
		assertEquals(110.0, parent.getStartTempo());
	}
}
