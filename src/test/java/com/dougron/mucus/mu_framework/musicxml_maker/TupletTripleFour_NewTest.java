package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.com.dougron.mucus.mucus_output_manager.mu_output_manager.MuOutputManager;
import main.java.com.dougron.mucus.mucus_output_manager.mu_output_manager.PartTrackAndClip;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import main.java.da_utils.render_name.RenderName;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;


/*
 * made this while testing nested tuplets, and the TupletTripleFour
 * did not want to work properly
 */
class TupletTripleFour_NewTest
{
	
	MuOutputManager outputManager = MuOutputManager.builder()
			.path("D:/Documents/miscForBackup/mu_test_output/")
			.partTrackAndClip(new PartTrackAndClip(MuTag.PART_CHORDS, 1, 0))
			.build();

	
	
	@Test
	void test()
	{
//		System.out.println("weeeeelllllll....");
		
		Mu mu = makeMu();
		String filename = RenderName.dateAndTime();
//		System.out.println(filename);
		mu.addTag(MuTag.PART_CHORDS);
//		outputManager.outputToMusicXML(filename, mu);
		
		String correctResult = "part=010\n" + 
		"measure=1\n" + 
		"time signature=4/4\n" + 
		"key signature=-5\n" + 
		"division=3\n" + 
		"note: notes=54,58, offset=0.0 length=1.33333333\n" + 
		"note: notes=56,60, offset=1.33333333 length=1.33333333\n" + 
		"note: notes=54,58, offset=2.66666667 length=1.33333333\n" + 
		"measure=2\n" + 
		"rest: offset=0.0 length=4.0\n";

		String result = MuXMLMaker.makeTestOutput(mu);
		assertEquals(correctResult, result);
		
//		System.out.println("aaaand done.");
	}
	
	
	private Mu makeMu() 
	{
		Mu mu = new Mu("010");
		
		tupletTestFour(mu);
//		System.out.println(mu.toString());
		return mu;
	}
	
	
	private void tupletTestFour(Mu mu)
	{
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory
				.getGenerator(TimeSignature.FOUR_FOUR));
		mu.setLengthInBars(2);
		
		mu.setXMLKey(-5);
		
		mu.addMu(makeHalfTriplet(
					new int[] {54, 56, 54}, 
					4.0
					), 
				new BarsAndBeats(0, 0.0)
				);
	}
	
	
	private static Mu makeHalfTriplet(int[] is, double aLengthInQuarters)
	{
		Mu mu = new Mu("triplet-holder");
		mu.setLengthInQuarters(aLengthInQuarters);
		mu.setIsTupletPrintContainer(true);
		mu.setTupletNumerator(3);
		mu.setTupletDenominator(2);
		double microPos = 0.0;
		for (int i: is)
		{
			if (i >= 0)		// cater for rest when i = -1
			{
				mu.addMu(makeNoteMu(
							new int[] {i, i + 4}, 
							4.0 / 3, 
							"triplet"), 
						new BarsAndBeats(0, microPos)
				);
			}
						
			microPos += 4.0 / 3;
		}
		return mu;
	}
	
	
	
	private static Mu makeNoteMu(
			int[] notes, 
			double aLengthInQuarters, 
			String name) 
	{
		Mu mu = new Mu(name);
		mu.setLengthInQuarters(aLengthInQuarters);
		for (int note: notes)
		{
			mu.addMuNote(new MuNote(note, 64));			// 64 - default velocity
		}			
		return mu;
	}
	

}
