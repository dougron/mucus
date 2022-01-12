package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;

class MuWithPositionRelationShip_MuXMLMaker_Test
{
	
	@Test
	void given_zero_length_mu_then_returns_an_invalid_mu_message() throws Exception
	{
		Mu mu = new Mu("parent");
		String correctResult = "not a valid Mu";
		String result = MuXMLMaker.makeTestOutput(mu);
		assertEquals(correctResult, result);
	}

	
	@Test
	final void given_a_mu_length_4_without_notes_with_an_annotation_then_test_output_is_correct()
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(4);
		mu.addMuAnnotation(new MuAnnotation("hello world", MuAnnotation.TextPlacement.PLACEMENT_ABOVE));
		
		String correctResult = "part=parent\n" + 
				"measure=1\n" + 
				"time signature=4/4\n" + 
				"key signature=0\n" + 
				"division=1\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"backup=4\n" +
				"annotation: hello world\n" + 
				"forward=4\n" +
				"measure=2\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"measure=3\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"measure=4\n" + 
				"rest: offset=0.0 length=4.0\n";
		String result = MuXMLMaker.makeTestOutput(mu);
		assertEquals(correctResult, result);
	}
	
	
	@Test
	void given_parent_with_annotation_and_no_notes_with_child_length_2_at_2_bars_with_annotation_then_test_output_is_correct() throws Exception
	{
		Mu mu = new Mu("parent");
		mu.addMuAnnotation(new MuAnnotation("hello world", MuAnnotation.TextPlacement.PLACEMENT_ABOVE));
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		child.addMuAnnotation(new MuAnnotation("child", MuAnnotation.TextPlacement.PLACEMENT_BELOW));
		mu.addMu(child, 2);
		
		String correctResult = "part=parent\n" + 
				"measure=1\n" + 
				"time signature=4/4\n" + 
				"key signature=0\n" + 
				"division=1\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"backup=4\n" +
				"annotation: hello world\n" + 
				"forward=4\n" +
				"measure=2\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"measure=3\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"backup=4\n" +
				"annotation: child\n" + 
				"forward=4\n" +
				"measure=4\n" + 
				"rest: offset=0.0 length=4.0\n";
		String result = MuXMLMaker.makeTestOutput(mu);
		assertEquals(correctResult, result);
	}

}
