package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import time_signature_utilities.time_signature.TimeSignature;


public class Tuplet78Quadruplet
{

	@Test
	public void test()
	{
		Mu mu = makeMu();
		String correctResult = "part=010\n" + 
				"measure=1\n" + 
				"time signature=7/8\n" + 
				"key signature=-5\n" + 
				"division=280\n" + 
				"note: notes=54,58, offset=0.0 length=0.375\n" + 
				"note: notes=56,60, offset=0.375 length=0.375\n" + 
				"note: notes=54,58, offset=0.75 length=0.375\n" + 
				"note: notes=53,57, offset=1.125 length=0.375\n" + 
				"rest: offset=1.5 length=2.0\n";
		
		String result = MuXMLMaker.makeTestOutput(mu);
//		MuXMLMaker.makeXML(mu, TestingStuff.getQuickNastyXMLPathString("Tuplet78Quadruplet"));
		assertEquals(correctResult, result);
	}
	
	
	
	private static Mu makeMu() 
	{
		Mu mu = new Mu("010");	
		mu.setLengthInBars(1);
		sevenEightQuadruplet(mu);
		return mu;
	}
	
	
	
	private static void sevenEightQuadruplet(Mu mu)
	{
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));		
		mu.setXMLKey(-5);		
		makeEighthQuadlet(new int[] {54, 56, 54, 53}, 0, 0.0, 1.5, mu);
	}
	
	
	private static Mu makeEighthQuadlet(int[] is, int barIndex, double offset, double aLengthInQuarters, Mu parent)
	{
		Mu mu = new Mu("quadruplet-holder");
		mu.setLengthInQuarters(aLengthInQuarters);
		mu.setIsTupletPrintContainer(true);
		mu.setTupletNumerator(4);
		mu.setTupletDenominator(3);
		double microPos = 0.0;
		for (int i: is)
		{
			if (i >= 0)		// cater for rest when i = -1
			{
				Mu quadruplet = new Mu("quadruplet");
				quadruplet.setLengthInQuarters(1.5 / 4);
				quadruplet.addMuNote(new MuNote(i, 64));	// 64 - default velocity
				quadruplet.addMuNote(new MuNote(i + 4, 64));	// 64 - default velocity
				mu.addMu(quadruplet, new BarsAndBeats(barIndex, microPos));
			}
						
			microPos += 1.5 / 4;
		}	
		parent.addMu(mu, new BarsAndBeats(barIndex, offset));
		return mu;
	}

}
