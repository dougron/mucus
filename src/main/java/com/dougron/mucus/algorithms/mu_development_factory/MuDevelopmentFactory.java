package main.java.com.dougron.mucus.algorithms.mu_development_factory;

import java.util.Random;

import main.java.com.dougron.mucus.algorithms.mu_relationship.MuRelationship;
import main.java.com.dougron.mucus.algorithms.mu_relationship.MuRelationship_IntervalModel_Parameters;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right.SuFi_IntervalAndRhythmModel_Generator;
import main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right.SuFi_IntervalModel_Generator;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;

/**
 * methods for developing Mus
 * 
 * @author dougr
 *
 */


public class MuDevelopmentFactory
{
	
	public static enum Relationship {SIMILAR, CONTRASTING};
	private static Random rnd = new Random();
	
	
	public static void resetRandom(int seed)
	{
		rnd = new Random(seed);
	}
	

	public static Mu makeTwoPhraseSectionFromOnePhraseWithRelationship(Mu phrase1, Relationship relationship)
	{
		Mu section = new Mu("section");
		section.addMu(phrase1, 0);
		Mu phrase2 = new Mu("phrase2");
		phrase2.setLengthInBars(phrase1.getLengthInBars());
		phrase2.setStartPitch(phrase1.getStartPitch());
		phrase2.addMuAnnotation(new MuAnnotation(phrase2.getName()));
		MuRelationship muR = new MuRelationship_IntervalModel_Parameters(phrase1, relationship);
		phrase2.addMuRelationship(muR);
		section.addMuToEndOfSibling(phrase2, 0, phrase1);
		return section;
	}


	
	public static void addIntervalAndRythmModelGenerator(Mu mu, double aStart, double aEnd, Relationship relationship)
	{
		int[] intervalModels = getIntervalModelsFromMu(mu, relationship);
		double[][] rhythmModels = getRandomRhythmModel(new double[] {0.5, 1.0, 1.5, 1.5}, 4, 7);
		SuFi sufi = new SuFi_IntervalAndRhythmModel_Generator(aStart, aEnd, intervalModels, rhythmModels );
		mu.addSuFi(sufi);
	}


	
	private static double[][] getRandomRhythmModel(double[] lengthOptions, int min, int max)
	{
		int numberOfRhythmModels = rnd.nextInt(max - min) + min;
		System.out.println("numberOfRhythmModels=" + numberOfRhythmModels);
		double[][] darr = new double[numberOfRhythmModels][];
		for (int i = 0; i < numberOfRhythmModels; i++)
		{
			int index = (int)(rnd.nextDouble() * lengthOptions.length);
			darr[i] = new double[] {lengthOptions[index], lengthOptions[index]};
		}
		return darr;
	}



	private static int[] getIntervalModelsFromMu(Mu mu, Relationship relationship)
	{
		SuFi sufi = mu.getSuFiSu().getSufi(0);
		if (sufi instanceof SuFi_IntervalModel_Generator)
		{
			SuFi_IntervalModel_Generator castSufi = (SuFi_IntervalModel_Generator)sufi;
			Integer[] arr = castSufi.getIntervalModels();
			int[] newIntervalModels = new int[arr.length];
			int x = 1;
			if (relationship == Relationship.CONTRASTING) x = -1;
			for (int i = 0; i < arr.length; i++)
			{
				newIntervalModels[i] = arr[i] * x;
			}
			return newIntervalModels;
		}
		return new int[] {1};
	}

}
