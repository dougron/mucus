package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import time_signature_utilities.time_signature.TimeSignature;


/*
 * in case it is not obvious, this creates a septuplet of 16th notes where the 2nd note is a 2/7th and therefore should emerge as an 8th note within the septuplet
 */
public class TupletBrokenSeptuplet
{

	@Test
	public void test()
	{
		Mu mu = makeMu();
		String correctResult = "part=010\n" + 
				"measure=1\n" + 
				"time signature=4/4\n" + 
				"key signature=-5\n" + 
				"division=7\n" + 
				"note: notes=54,58, offset=0.0 length=0.14285714\n" + 
				"note: notes=56,60, offset=0.14285714 length=0.28571429\n" + 
				"note: notes=54,58, offset=0.42857143 length=0.14285714\n" + 
				"note: notes=53,57, offset=0.57142857 length=0.14285714\n" + 
				"note: notes=51,55, offset=0.71428571 length=0.14285714\n" + 
				"note: notes=53,57, offset=0.85714286 length=0.14285714\n" + 
				"rest: offset=1.0 length=1.0\n" + 
				"rest: offset=2.0 length=2.0\n";
		
		String result = MuXMLMaker.makeTestOutput(mu);
		assertEquals(correctResult, result);
	}
	
	
	
	private static Mu makeMu() 
	{
		Mu mu = new Mu("010");
		septupdoublet(mu);
		return mu;
	}
	
	
	
	private static void septupdoublet(Mu mu)
	{
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.FOUR_FOUR));
		mu.setLengthInBars(1);
		
		mu.setXMLKey(-5);
		
		mu.addMu(makeSeptuplet(new int[] {54, 56, 54, 53, 51, 53}, new int[] {1, 2, 1, 1, 1, 1}, 1.0, 4), new BarsAndBeats(0, 0.0));	
	}

	

	private static Mu makeSeptuplet(int[] is, int[] lengths, double aLengthInQuarters, int denom)
	{
		Mu mu = new Mu("septuplet-holder");
		mu.setLengthInQuarters(aLengthInQuarters);
		mu.setIsTupletPrintContainer(true);
		
		int numerator = 0;
		for (int x: lengths)
		{
			numerator += x;
		}
		
		mu.setTupletNumerator(numerator);
		mu.setTupletDenominator(denom);
		
		double microPos = 0.0;
		int index = 0;
		for (int i: is)
		{
			if (i >= 0)		// cater for rest when i = -1
			{
				mu.addMu(makeNoteMu(new int[] {i, i + 4}, aLengthInQuarters / numerator * lengths[index], "quadruplet"), new BarsAndBeats(0, microPos));
			}
						
			microPos += aLengthInQuarters / numerator * lengths[index];
			index++;
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
