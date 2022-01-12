package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class Temp_Test
{
	
	@Test
	void sibling_with_time_signature_generator_reflects_in_parent_getTimeSignature_toString_when_child_length_changes() throws Exception
	{
		Mu mu = new Mu("parent");
		Mu child = new Mu("child");
		Mu grandchild = new Mu("grandchild");
		mu.addMu(child, 2);
		child.addMu(grandchild, 1);
		grandchild.setLengthInBars(2);
		Mu sibling = new Mu("sibling");
		sibling.setLengthInBars(2);
		sibling.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		mu.addMuToEndOfSibling(sibling, 0, child);
		assertEquals(5, sibling.getGlobalPositionInBars());
		assertEquals("4/4__4/4__4/4__4/4__4/4__3/4__3/4__", mu.getTimeSignaturesToString());
		grandchild.setPositionInBars(2);
		assertEquals(6, sibling.getGlobalPositionInBars());
		assertEquals("4/4__4/4__4/4__4/4__4/4__4/4__3/4__3/4__", mu.getTimeSignaturesToString());
	}
}
