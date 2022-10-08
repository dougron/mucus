package test.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import main.java.da_utils.render_name.RenderName;

class StaccatoNoteScorePrintoutTests
{
	
	String path = "D:/Documents/miscForBackup/musicxml_staccato_test_output/";

	@Test
	void test()
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(2);
		mu.addTag(MuTag.PART_MELODY);
		
		Mu note = new Mu("note");
		note.addMuNote(64, 72);
//		note.addMuNote(60, 72);
//		note.addMuNote(67, 72);
		note.setLengthInQuarters(0.2);
		mu.addMu(note, BarsAndBeats.at(0, 2.0));
		note.addTag(MuTag.NOTATE_AS_STACCATO_EIGHTH);
		
		String timestamp = RenderName.dateAndTime();
		
		System.out.println(outputToMusicXML(timestamp, mu));
	}
	
	
	
	public String outputToMusicXML (String aTimeStamp, Mu aMu)
	{
		String filename = path + aTimeStamp + ".musicxml";
		MuXMLMaker.makeMultiPartXML(aMu, filename);
		return filename;
	}

}
