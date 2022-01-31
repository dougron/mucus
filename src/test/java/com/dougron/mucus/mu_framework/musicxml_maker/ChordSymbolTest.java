package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

class ChordSymbolTest
{
	
	String fileName = "ChordSymbolTest";

	@Test
	void given_default_mu_with_default_chords_then_xml_outputs_one_backup_and_chord_symbol()
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(1);
		mu.addTag(MuTag.PRINT_CHORDS);
		String expected = "part=mu\n" + 
				"measure=1\n" + 
				"time signature=4/4\n" + 
				"key signature=0\n" + 
				"division=1\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"backup=4\n" + 
				"chord symbol=C\n" +
				"forward=4\n";
//		System.out.println(MuXMLMaker.makeTestOutput(mu));
//		MuXMLMaker.makeXML(mu, TestingStuff.getQuickNastyXMLPathString(fileName));
		assertEquals(expected, MuXMLMaker.makeTestOutput(mu));
	}
	
	
	@Test
	void given_simple_one_chord_per_bar_then_xml_output_is_correct() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(2);
		mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Gm", "Bb"}));
		mu.addTag(MuTag.PRINT_CHORDS);
		String expected = "part=mu\n" + 
				"measure=1\n" + 
				"time signature=4/4\n" + 
				"key signature=0\n" + 
				"division=1\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"backup=4\n" + 
				"chord symbol=Gm\n" + 
				"forward=4\n" +
				"measure=2\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"backup=4\n" + 
				"chord symbol=Bb\n" +
				"forward=4\n";
//		System.out.println(MuXMLMaker.makeTestOutput(mu));
//		MuXMLMaker.makeXML(mu, TestingStuff.getQuickNastyXMLPathString(fileName));
		assertEquals(expected, MuXMLMaker.makeTestOutput(mu));
	}
	
	
	@Test
	void given_2_chords_per_bar_then_correct_backup_forward_and_chord_symbols_given() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(1);
		mu.setChordListGenerator(new FloatBarChordProgression(1.0, new Object[] {0.0, "Cm", 0.5, "B7"}));
		mu.addTag(MuTag.PRINT_CHORDS);
		String expected = "part=mu\n" + 
				"measure=1\n" + 
				"time signature=4/4\n" + 
				"key signature=0\n" + 
				"division=1\n" + 
				"rest: offset=0.0 length=4.0\n" + 
				"backup=4\n" + 
				"chord symbol=Cm\n" + 
				"forward=2\n" + 
				"chord symbol=B7\n" +
				"forward=2\n";
//		System.out.println(MuXMLMaker.makeTestOutput(mu));
//		MuXMLMaker.makeXML(mu, TestingStuff.getQuickNastyXMLPathString(fileName));
		assertEquals(expected, MuXMLMaker.makeTestOutput(mu));
	}
	
	
	@Test
	void given_2_chords_per_bar_in_7_8_time_then_correct_backup_forward_and_chord_symbols_given() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		mu.setLengthInBars(1);
		mu.setChordListGenerator(new FloatBarChordProgression(1.0, new Object[] {0.0, "Cm", 0.5, "B7"}));
		mu.addTag(MuTag.PRINT_CHORDS);
		String expected = "part=mu\n" + 
				"measure=1\n" + 
				"time signature=7/8\n" + 
				"key signature=0\n" + 
				"division=2\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"backup=7\n" + 
				"chord symbol=Cm\n" + 
				"forward=3\n" + 
				"chord symbol=B7\n" +
				"forward=4\n";
//		System.out.println(MuXMLMaker.makeTestOutput(mu));
//		MuXMLMaker.makeXML(mu, TestingStuff.getQuickNastyXMLPathString(fileName));
		assertEquals(expected, MuXMLMaker.makeTestOutput(mu));
	}

	
	
	
	
	
	
	
	
}
