package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mucus_output_manager.mu_output_manager.MuOutputManager;
import main.java.com.dougron.mucus.mucus_output_manager.mu_output_manager.PartTrackAndClip;
import main.java.da_utils.render_name.RenderName;

class NestedTuplet
{
	
	MuOutputManager outputManager = MuOutputManager.builder()
			.path("D:/Documents/miscForBackup/mu_test_output/")
			.partTrackAndClip(new PartTrackAndClip(MuTag.PART_CHORDS, 1, 0))
			.build();

	/*
	 * at 2024_11_02, nested tuplets as actual tuplet brackets within
	 * tuplet brackets in the score do not work.
	 * 
	 * future work in Mu using a subdivision system such as musicxml
	 * points the way forward, but may be a can of worms too far....
	 */
	
//	@Test
	void test()
	{
		Mu mu = new Mu("nested tuplet example");
		mu.setLengthInBars(1);
		
		Mu note1 = new Mu("note1");
		mu.addMu(note1, BarsAndBeats.at(0, 0.0));
		note1.setLengthInQuarters(2.0);
		note1.addMuNote(64, 48);
		
		Mu tuplet1 = new Mu("tuplet1");
		mu.addMu(tuplet1, BarsAndBeats.at(0, 2.0));
		tuplet1.setLengthInQuarters(2.0);
		tuplet1.setIsTupletPrintContainer(true);
		tuplet1.setTupletNumerator(3);
		tuplet1.setTupletDenominator(2);
		
		
		Mu note2 = new Mu("note2");
		tuplet1.addMu(note2, 0.0);
		note2.setLengthInQuarters(2.0 / 3.0);
		note2.addMuNote(65, 48);
		
//		Mu note3 = new Mu("note3");
//		tuplet1.addMu(note3, 2.0 / 3.0);
//		note3.setLengthInQuarters(2.0 / 3.0);
//		note3.addMuNote(67, 48);
		
//		Mu nestedTuplet = new Mu("nested_tuplet");
//		tuplet1.addMu(nestedTuplet, 2.0 / 3.0);
//		nestedTuplet.setLengthInQuarters(1.0 / 3.0);
//		nestedTuplet.addMuNote(75, 64);
//		nestedTuplet.setIsTupletPrintContainer(true);
//		nestedTuplet.setTupletNumerator(5);
//		nestedTuplet.setTupletDenominator(2);
		
//		Mu nestedTuplet2 = new Mu("nested_tuplet");
//		tuplet1.addMu(nestedTuplet2, 2.0 / 3.0 + 1.0 / 3.0);
//		nestedTuplet2.setLengthInQuarters(1.0 / 3.0);
//		nestedTuplet2.addMuNote(73, 64);
		
//		Mu nested1plet = new Mu("nested_1plet");
//		nestedTuplet.addMu(nested1plet, 0.0);
//		nested1plet.setLengthInQuarters( 2.0 / 3.0);
//		nested1plet.addMuNote(72, 64);
		
// triplets -------------------
//		Mu note3_0 = new Mu("note3_0");
//		tuplet1.addMu(note3_0, 2.0 / 3.0);
//		note3_0.setLengthInQuarters(2.0 / 3.0 / 3.0);
//		note3_0.addMuNote(69, 48);
//
//		Mu note3_1 = new Mu("note3_1");
//		tuplet1.addMu(note3_1, 2.0 / 3.0 + 2.0 / 3.0 / 3.0);
//		note3_1.setLengthInQuarters(2.0 / 3.0 / 3.0);
//		note3_1.addMuNote(75, 48);
//
//		Mu note3_2 = new Mu("note3_2");
//		tuplet1.addMu(note3_2, 2.0 / 3.0 + 2.0 / 3.0 / 3.0 * 2);
//		note3_2.setLengthInQuarters(2.0 / 3.0 / 3.0);
//		note3_2.addMuNote(74, 48);
		
// triplets -------------------
		Mu note3_0 = new Mu("note3_0");
		tuplet1.addMu(note3_0, 2.0 / 3.0);
		note3_0.setLengthInQuarters(2.0 / 3.0 / 4.0);
		note3_0.addMuNote(69, 48);

		Mu note3_1 = new Mu("note3_1");
		tuplet1.addMu(note3_1, 2.0 / 3.0 + 2.0 / 3.0 / 4.0);
		note3_1.setLengthInQuarters(2.0 / 3.0 / 4.0);
		note3_1.addMuNote(75, 48);

		Mu note3_2 = new Mu("note3_2");
		tuplet1.addMu(note3_2, 2.0 / 3.0 + 2.0 / 3.0 / 4.0 * 2);
		note3_2.setLengthInQuarters(2.0 / 3.0 / 4.0);
		note3_2.addMuNote(74, 48);
		
		Mu note3_3 = new Mu("note3_3");
		tuplet1.addMu(note3_3, 2.0 / 3.0 + 2.0 / 3.0 / 4.0 * 3);
		note3_3.setLengthInQuarters(2.0 / 3.0 / 4.0);
		note3_3.addMuNote(73, 48);
		
// quintuplets -------------------
//		Mu note3_0 = new Mu("note3_0");
//		tuplet1.addMu(note3_0, 2.0 / 3.0);
//		note3_0.setLengthInQuarters(2.0 / 3.0 / 5.0);
//		note3_0.addMuNote(69, 48);
//		
//		Mu note3_1 = new Mu("note3_1");
//		tuplet1.addMu(note3_1, 2.0 / 3.0 + 2.0 / 3.0 / 5.0);
//		note3_1.setLengthInQuarters(2.0 / 3.0 / 5.0);
//		note3_1.addMuNote(75, 48);
//		
//		Mu note3_2 = new Mu("note3_2");
//		tuplet1.addMu(note3_2, 2.0 / 3.0 + 2.0 / 3.0 / 5.0 * 2);
//		note3_2.setLengthInQuarters(2.0 / 3.0 / 5.0);
//		note3_2.addMuNote(74, 48);
//		
//		Mu note3_3 = new Mu("note3_3");
//		tuplet1.addMu(note3_3, 2.0 / 3.0 + 2.0 / 3.0 / 5.0 * 3);
//		note3_3.setLengthInQuarters(2.0 / 3.0 / 5.0);
//		note3_3.addMuNote(73, 48);
//		
//		Mu note3_4 = new Mu("note3_4");
//		tuplet1.addMu(note3_4, 2.0 / 3.0 + 2.0 / 3.0 / 5.0 * 4.0);
//		note3_4.setLengthInQuarters(2.0 / 3.0 / 5.0);
//		note3_4.addMuNote(72, 48);
// ---------------------------------		
		
		
		Mu note4 = new Mu("note4");
		tuplet1.addMu(note4, 4.0 / 3.0);
		note4.setLengthInQuarters(2.0 / 3.0);
		note4.addMuNote(69, 48);
		
		String filename = RenderName.dateAndTime();
		mu.addTag(MuTag.PART_CHORDS);
		outputManager.outputToMusicXML(filename, mu);
	}

}
