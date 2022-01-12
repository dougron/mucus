package test.java.com.dougron.mucus.mu_framework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_Anticipation;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_Anticipation_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_EscapeTone;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_EscapeTone_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_NothingToAdd;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator.AccentType;
import main.java.com.dougron.mucus.algorithms.mu_generator.enums.EscapeToneType;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.Mu.PrintParameter;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_xml_utility.MuXMLUtility;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import time_signature_utilities.time_signature.TimeSignature;

class Mu_SaveToFileTests
{
	
	String path = "D:/Documents/miscForBackup/MuSaveToXMLTestOutputs/";


	@Test
	void mu_retrieved_from_muxml_file_is_same_as_original_mu() throws Exception
	{
		Mu mu = new Mu("original");
		mu.addMuAnnotation(new MuAnnotation("poopy", MuAnnotation.TextPlacement.PLACEMENT_ABOVE));
		mu.addMuAnnotation(new MuAnnotation("zzzzz", MuAnnotation.TextPlacement.PLACEMENT_BELOW));
		mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Dm", "F", "Eb", "Gdim"}));
		
		Mu child = new Mu("child");
		child.setLengthInBars(1);
		child.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		mu.addMu(child, 1);
		
		Mu child1 = new Mu("child1");
		child1.setLengthInBars(1);
		mu.addMu(child1, 3);
		child1.setHasLeadingDoubleBar(true);
		child1.addMuGenerator(new MuG_Anticipation_RRP(new RelativeRhythmicPosition(0, 0, 1, -1)));
		
		Mu grandchild = new Mu("grandchild");
		grandchild.setLengthInQuarters(1.0);
		grandchild.addMuNote(new MuNote(64, 72));
		child.addMu(grandchild, 0);
		
		
		Mu grandchild1 = new Mu("grandchild1");
		grandchild1.setLengthInBarsAndBeats(new BarsAndBeats(0, 2.0));
		child.addMu(grandchild1, 2.0);
		grandchild1.addTag(MuTag.IS_ESCAPE_TONE);
		
		Mu grandchild2 = new Mu("grandchild2");
		grandchild2.setLengthInQuarters(1.0);
		child1.addMu(grandchild2, new BarsAndBeats(0, -1.0));
		
		Mu grandchild3 = new Mu("grandchild3");
		grandchild3.setLengthInQuarters(1.0);
		child1.addMu(grandchild3, 2.0);
		grandchild3.addTag(new MuTag[] {MuTag.IS_ANTICIPATION, MuTag.ACCENTED});
		grandchild3.addTag(MuTag.BASS_CLEF);
		
		Mu grandchild4 = new Mu("grandchild4");
		grandchild4.setLengthInQuarters(1.0);
		child1.addMuToEndOfSibling(grandchild4, 0.0, grandchild2);
		
		Mu grandchild5 = new Mu("grandchild5");
		grandchild5.setLengthInQuarters(1.0);
		child1.addMuToEndOfSibling(grandchild5, 1, grandchild2);
		
		Mu grandchild6 = new Mu("grandchild6");
		grandchild5.setLengthInQuarters(1.0);
		child1.addMuToEndOfSibling(grandchild6, new BarsAndBeats(1, 2.0), grandchild2);
		
		String muPath = path + "testMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.NAME, PrintParameter.PARENT, 
				PrintParameter.POSITION_MODEL,
				PrintParameter.LENGTH_MODEL,
				PrintParameter.HAS_LEADING_DOUBLE_BAR,
				PrintParameter.HAS_CHORD_LIST_GENERATOR,
//				PrintParameter.CHORD_LIST,
				PrintParameter.MUS
		};
//		System.out.println(mu.getParameterToStringFromList(ppArr));
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void ruler_with_tempo_change_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(8);
		mu.addTempoChange(100.0, 4.5);
		
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.RULER,
//				PrintParameter.MUS
		};
		
		String muPath = path + "rulerTempoChangeMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
				
		System.out.println(mu.getParameterToStringFromList(ppArr));
		System.out.println(newMu.getParameterToStringFromList(ppArr));
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void ruler_with_new_start_tempo_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(8);
		mu.setStartTempo(100.0);
		
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.RULER,
//				PrintParameter.MUS
		};
		
		String muPath = path + "rulerStartTempoMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void ruler_with_custom_time_signature_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		TimeSignature ts = new TimeSignature(3, 8, 0.5, new Object[] {1, 1.5});
		TimeSignature[] tsArr = new TimeSignature[] {ts, TimeSignature.THREE_FOUR, TimeSignature.FIVE_EIGHT_32, TimeSignature.SIX_EIGHT};
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));	
		mu.setLengthInBars(8);
//		mu.getRuler().addTempoChange(150, new BarsAndBeats(3, 1.0));
		
		Mu child = new Mu("child");
		child.setLengthInBars(1);
		child.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		mu.addMu(child, 5);
		
		String muPath = path + "rulerCustomTSMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.RULER,
				PrintParameter.MUS
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void ruler_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		TimeSignature[] tsArr = new TimeSignature[] {TimeSignature.FOUR_FOUR, TimeSignature.THREE_FOUR, TimeSignature.FIVE_EIGHT_32, TimeSignature.SIX_EIGHT};
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));	
		mu.setLengthInBars(8);
//		mu.getRuler().addTempoChange(150, new BarsAndBeats(3, 1.0));
		
		Mu child = new Mu("child");
		child.setLengthInBars(1);
		child.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		mu.addMu(child, 5);
		
		String muPath = path + "rulerMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.RULER,
				PrintParameter.MUS
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void mug_nothing_to_add_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addMuGenerator(new MuG_NothingToAdd());		
		String muPath = path + "MuG_NothingToAddMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.MU_GENERATOR,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void mug_escape_tone_rrp_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addMuGenerator(new MuG_EscapeTone_RRP
				(
				new RelativeRhythmicPosition(0, 1, -1, 2), 
				EscapeToneType.JUMP_STEP, 
				AccentType.UNACCENTED
				));
		
		String muPath = path + "MuG_EscapeTone_RRPMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.MU_GENERATOR,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void mug_escape_tone_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addMuGenerator(new MuG_EscapeTone(1.0, EscapeToneType.JUMP_STEP, AccentType.UNACCENTED));
		
		String muPath = path + "MuG_EscapeToneMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.MU_GENERATOR,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void mug_anticipation_rrp_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addMuGenerator(new MuG_Anticipation_RRP(new RelativeRhythmicPosition(0, 1, -1, 2)));
		
		String muPath = path + "MuG_Anticipation_RRPMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.MU_GENERATOR,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void mug_anticipation_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addMuGenerator(new MuG_Anticipation(3, 0.75));
		
		String muPath = path + "MuG_AnticipationTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.MU_GENERATOR,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void key_signature_map_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setXMLKey(-6);
		
		String muPath = path + "keySignatureMapTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.KEY_SIGNTURE_MAP,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void start_pitch_survive_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.setStartPitch(44);
		
		String muPath = path + "startPitchTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.START_PITCH,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void mu_annotations_survive_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addMuAnnotation(new MuAnnotation("solo"));
		mu.addMuAnnotation(new MuAnnotation("solo", MuAnnotation.TextPlacement.PLACEMENT_BELOW));
		mu.addMuAnnotation(new MuAnnotation("solo", 24, MuAnnotation.TextPlacement.PLACEMENT_BELOW));
		
		String muPath = path + "muAnnotationTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.MU_ANNOTATIONS,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
		
	
	@Test
	void mu_notes_survive_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addMuNote(new MuNote(64, 96));
		mu.addMuNote(new MuNote(33, 44));
		
		String muPath = path + "muNoteTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.MU_NOTES,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
		
	
	@Test
	void mu_tags_survive_save_to_xml() throws Exception
	{
		Mu mu = new Mu("mu");
		mu.addTag(MuTag.IS_CHORD_TONE);
		mu.addTag(new MuTag[] {MuTag.IS_ANTICIPATION, MuTag.ACCENTED});
		
		String muPath = path + "muTagTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.MU_TAG_BUNDLES,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
		
	
	@Test
	void single_chord_list_generator_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("original");
		mu.setChordListGenerator(new SingleChordGenerator(new Chord("Dm")));
		mu.setLengthInBars(4);
		
		String muPath = path + "singleChordGeneratorTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.HAS_CHORD_LIST_GENERATOR,
				PrintParameter.CHORD_LIST_GENERATOR,
				PrintParameter.CHORD_LIST,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void simple_even_chord_list_generator_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("original");
		mu.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Am", "Dm", "C7", "G7"}));
		mu.setLengthInBars(4);
		
		String muPath = path + "simpleEvenChordGeneratorTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.HAS_CHORD_LIST_GENERATOR,
				PrintParameter.CHORD_LIST_GENERATOR,
				PrintParameter.CHORD_LIST,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	
	@Test
	void float_bar_chord_list_generator_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("original");
		mu.setChordListGenerator(new FloatBarChordProgression(2.0, new Object[] {0.0, "Am", 0.5, "Dm", 1.0, "C7", 1.5, "G7"}));
		mu.setLengthInBars(4);
		
		String muPath = path + "floatBarChordGeneratorTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.HAS_CHORD_LIST_GENERATOR,
				PrintParameter.CHORD_LIST_GENERATOR,
				PrintParameter.CHORD_LIST,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
		
	
	@Test
	void tuplet_information_survives_save_to_xml() throws Exception
	{
		Mu mu = new Mu("original");
		mu.setLengthInBars(4);
		
		Mu child = new Mu("tuplet_container");
		child.setIsTupletPrintContainer(true);
		child.setTupletNumerator(7);
		child.setTupletDenominator(4);
		child.setLengthInQuarters(2.0);
		mu.addMu(child, 1.0);
		
		String muPath = path + "tupletTestMuXML.muxml";
		MuXMLUtility.saveMuToXMLFile(muPath, mu);
		
		Mu newMu = MuXMLUtility.loadMuFromXMLFile(muPath);
		newMu.setMuIndices();
		PrintParameter[] ppArr = new PrintParameter[] {
				PrintParameter.IS_TUPLET_PRINT_CONTAINER,
				PrintParameter.TUPLET_NUMERATOR,
				PrintParameter.TUPLET_DENOMINATOR,
				PrintParameter.MUS,
		};
		assertEquals(mu.getParameterToStringFromList(ppArr), newMu.getParameterToStringFromList(ppArr));
	}
	
	

}
