package test.java.com.dougron.mucus.algorithms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right.SuFiSu_LeftToRight;
import main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right.SuFi_IntervalModel_Generator;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class SuperImposerifier_Mu004
{

	@Test
	public void test()
	{
		Mu mu = makeMu();
		String correctResult = "\nMu=====phrase===============\n" + 
				"PositionIsZero\n" + 
				"FixedLength: 4 bars\n" + 
				" no parent\n" + 
				" MuTags:\n" + 
				" MuNotes: 0 items\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 13 items\n" + 
				"   \n" + 
				"Mu=====note0===============\n" + 
				"BeginningOfParent: 0 bars 0.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=55 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note1===============\n" + 
				"BeginningOfParent: 0 bars 1.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=57 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note2===============\n" + 
				"BeginningOfParent: 0 bars 2.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=59 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note3===============\n" + 
				"BeginningOfParent: 0 bars 3.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=60 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note4===============\n" + 
				"BeginningOfParent: 0 bars 4.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=62 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note5===============\n" + 
				"BeginningOfParent: 0 bars 5.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=64 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note6===============\n" + 
				"BeginningOfParent: 0 bars 6.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=62 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note7===============\n" + 
				"BeginningOfParent: 0 bars 7.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=60 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note8===============\n" + 
				"BeginningOfParent: 0 bars 8.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=59 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note9===============\n" + 
				"BeginningOfParent: 0 bars 9.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=60 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note10===============\n" + 
				"BeginningOfParent: 0 bars 10.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=62 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note11===============\n" + 
				"BeginningOfParent: 0 bars 11.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=64 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items\n" + 
				"   \n" + 
				"Mu=====note12===============\n" + 
				"BeginningOfParent: 0 bars 12.0 beats\n" + 
				"FixedLength: 0.5 quarters\n" + 
				" parent=phrase\n" + 
				" MuTags:\n" + 
				" MuNotes: 1 items\n" + 
				"   MuNote: pitch=65 velocity=64\n" + 
				" MuAttonations: 0 items\n" + 
				" Mus: 0 items";
		
		
//		System.out.println(mu.toString());
		String result = mu.stateOfMusToString();
//		System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
//		System.out.println(result);
//		MuXMLMaker.makeXML(mu, TestingStuff.getQuickNastyXMLPathString("Superimposifier004"));
		assertEquals(correctResult, result);
	}

	private Mu makeMu()
	{
		double fill = 0.77;
		int startNote = 54;
				
		Mu mu = make4BarFourFourPhrase();
		
		mu.setStartPitch(startNote);
				
		SuFiSu_LeftToRight susu = createSuFiSu(mu);		
		SuFi_IntervalModel_Generator sufi1 = createSufi1(fill, mu, susu);		
		SuFi_IntervalModel_Generator sufi2 = createSufi2(susu);
		
		susu.addSufi(sufi1);
		susu.addSufi(sufi2);
		
		mu.runSuFiSuGenerator();
		return mu;
	}
	
	
	
	private static SuFi_IntervalModel_Generator createSufi2(SuFiSu_LeftToRight susu)
	{
		SuFi_IntervalModel_Generator sufi2 = new SuFi_IntervalModel_Generator(4.9, 8.0, susu);
		sufi2.addIntervalModel(-1);
		return sufi2;
	}

	
	
	private static SuFi_IntervalModel_Generator createSufi1(double fill, Mu mu, SuFiSu_LeftToRight susu)
	{
		SuFi_IntervalModel_Generator sufi1 = new SuFi_IntervalModel_Generator(0.0, mu.getLengthInQuarters() * fill, susu);
		sufi1.addIntervalModel(1);
		return sufi1;
	}

	
	
	private static SuFiSu_LeftToRight createSuFiSu(Mu mu)
	{
		SuFiSu_LeftToRight susu = new SuFiSu_LeftToRight(mu);
		mu.setGenerator(susu);
		return susu;
	}

	
	
//	private static ArrayList<Integer> makeAllNotes(int[] is) 
//	{
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		boolean b = true;
//		int octave = 0;
//		while (b) 
//		{
//			for (int i: is) 
//			{
//				int note = i + octave * 12;
//				if (note < 128) 
//				{
//					list.add(note);
//				} 
//				else 
//				{
//					b = false;
//				}
//			}
//			octave++;
//		}
//		Collections.sort(list);
//		return list;
//	}

	
	

	private static Mu make4BarFourFourPhrase() 
	{
		Mu mu = new Mu("phrase");
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.FOUR_FOUR));
		mu.setLengthInBars(4);
		return mu;
	}
	


}
