package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import time_signature_utilities.time_signature.TimeSignature;

public class TupletTripletThree
{

	@Test
	public void test()
	{
		Mu mu = makeMu();
		String correctResult = "part=010\n" + 
				"measure=1\n" + 
				"time signature=4/4\n" + 
				"key signature=-5\n" + 
				"division=6\n" + 
				"note: notes=49,53, offset=0.0 length=1.0\n" + 
				"note: notes=51,54, offset=1.0 length=0.5\n" + 
				"note: notes=53,56, offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.66666667\n" + 
				"note: notes=56,60, offset=2.66666667 length=0.66666667\n" + 
				"note: notes=54,58, offset=3.33333333 length=0.66666667\n" + 
				"measure=2\n" + 
				"rest: offset=0.0 length=4.0\n";
		
		String result = MuXMLMaker.makeTestOutput(mu);
//		System.out.println(result);
		assertEquals(correctResult, result);
	}
	
	
	
	private static Mu makeMu() 
	{
		Mu mu = new Mu("010");		
		tupletTestThree(mu);
		return mu;
	}
	
	
	
	private static void tupletTestThree(Mu mu)
	{
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.FOUR_FOUR));
		mu.setLengthInBars(2);
		
		mu.setXMLKey(-5);
		
		mu.addMu(makeNoteMu(new int[] {49, 53}, 1.0, "note"), new BarsAndBeats(0, 0.0));
		mu.addMu(makeNoteMu(new int[] {51, 54}, 0.5, "note"), new BarsAndBeats(0, 1.0));
		mu.addMu(makeNoteMu(new int[] {53, 56}, 0.5, "note"), new BarsAndBeats(0, 1.5));
		mu.addMu(makeQuarterTriplet(new int[] {-1, 56, 54}, 2.0), new BarsAndBeats(0, 2.0));
	}

	

	private static Mu makeQuarterTriplet(int[] is, double aLengthInQuarters)
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
				mu.addMu(makeNoteMu(new int[] {i, i + 4}, 2.0 / 3, "triplet"), new BarsAndBeats(0, microPos));
			}
						
			microPos += 2.0 / 3;
		}		
		return mu;
	}
	
	
	
	private static Mu makeNoteMu(int[] notes, double aLengthInQuarters, String name) 
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
