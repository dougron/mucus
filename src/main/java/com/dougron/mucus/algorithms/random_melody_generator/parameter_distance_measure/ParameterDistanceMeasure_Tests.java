package main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_distance_measure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.mu_chord_tone_and_embellishment.ChordToneAndEmbellishmentTagger;
import main.java.com.dougron.mucus.algorithms.part_generators.bass_part_generator.BassPartGenerator;
import main.java.com.dougron.mucus.algorithms.part_generators.chord_part_generator.ChordPartGenerator;
import main.java.com.dougron.mucus.algorithms.part_generators.drum_part_generator.DrumPartGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_001;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyParameterObject;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

class ParameterDistanceMeasure_Tests
{

//	@Test
//	void rnd_container_will_be_correctly_quantised() throws Exception
//	{
//		Random rnd = new TestRandom();
//		RandomMelodyGenerator rmg = RMG_001.getInstance();
//		RMRandomNumberContainer rndContainer = rmg.getRandomNumberContainer(rnd);
//		RandomMelodyParameterObject po = rmg.getParameterObject(rndContainer, rnd);
//		RMRandomNumberContainer quantisedContainer = ParameterDistanceMeasure.getQuantisedContainer(rndContainer, rmg);
////		System.out.println(rndContainer.toString() + "\n\n" + quantisedContainer.toString());
//	}
	
	
	@Test
	void quantized_rnd_container_returns_the_same_mu() throws Exception
	{
		Random rnd = new Random();
		RandomMelodyGenerator rmg = RMG_001.getInstance();
		RMRandomNumberContainer rndContainer = rmg.getRandomNumberContainer(rnd);
		Mu mu = getMu(rndContainer, rnd);
		RMRandomNumberContainer quantContainer = ParameterDistanceMeasure.getQuantisedContainer(rndContainer, rmg);
		Mu mu2 = getMu(quantContainer, rnd);
//		System.out.println(rndContainer.toString() + "\n\n" + quantContainer.toString());
//		System.out.println(mu.getCommaSeparatedPitchString() + "\n\n" + mu2.getCommaSeparatedPitchString());

//		String dateTimeStamp = RenderName.dateAndTime();
//		ContinuousIntegrator.makeMultiPartMusicXML("ParameterDistanceMeasure_" + dateTimeStamp + "_mu", mu);
//		ContinuousIntegrator.makeMultiPartMusicXML("ParameterDistanceMeasure_" + dateTimeStamp + "_mu2", mu2);
		assertEquals(mu.getDeepCopyContentString(), mu2.getDeepCopyContentString());
	}
	
	
	
	@Test
	void slightly_different_mu_registers_a_greater_than_zero_distance_measure() throws Exception
	{
		Random rnd = new Random();
		RandomMelodyGenerator rmg = RMG_001.getInstance();
		RMRandomNumberContainer rndContainer = rmg.getRandomNumberContainer(rnd);
		Parameter[] listOfMutableParameters = Parameter.values();
//				new Parameter[] 
//				{
//						Parameter.PHRASE_LENGTH, Parameter.TIME_SIGNATURE, Parameter.TEMPO, Parameter.XMLKEY, 
//						Parameter.START_NOTE, Parameter.STRUCTURE_TONE_CONTOUR, Parameter.STRUCTURE_TONE_MULTIPLIER, 
//						Parameter.CHORD_LIST_GENERATOR, Parameter.EMBELLISHMENT_REPETITION_PATTERN, Parameter.MUG_LISTS};
		RMRandomNumberContainer newRndContainer = rmg.getMutatedRandomNumberContainer
				(0.3, 4, listOfMutableParameters, rndContainer, rnd);
		double distance = ParameterDistanceMeasure.getSquaredDistanceMeasure(rndContainer, newRndContainer, rmg);
//		System.out.println("distance=" + distance);
		assertTrue(distance > 0.0);
	}
	
	
	
	
// privates same as Mu034, but returning an Mu not a MucusInteractionData------------------------------------------------------------------------------------------
	
	

//	int index = 0;
	private Mu getMu(RMRandomNumberContainer rndContainer, Random rnd)
	{
		Mu parent = new Mu("ParameterDisctanceTestPhrase");
		parent.addTag(MuTag.HAS_MULTIPART_CHILDREN);
		RandomMelodyGenerator rmg = RMG_001.getInstance();
		RandomMelodyParameterObject po = rmg.getParameterObject(rndContainer, rnd);
//		po.saveToJSON("D:/Documents/miscForBackup/QuickNastyOutput/", parent.getName() + RenderName.dateAndTime() + "_" + index);
//		index++;
		parent.setXMLKey(po.getXmlKey());
		
		Mu mu1 = getMelodyForMu(parent, po);
		getChordsPartForMu(parent, po, mu1);		
		getBassPartForMu(parent, po, mu1);		
		getDrumPartForMu(parent);
		
		return parent;
	}
	
	
	private Mu getMelodyForMu(Mu parent, RandomMelodyParameterObject po)
	{
		RandomMelodyGenerator rmg = RMG_001.getInstance();
		Mu mu1 = rmg.getMuPhrase(po);
		mu1.setName("melody");
		mu1.addTag(MuTag.PART_MELODY);
		mu1.addTag(MuTag.PRINT_CHORDS);
		mu1.setHasLeadingDoubleBar(true);
		mu1.setXMLKey(po.getXmlKey());
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(mu1);
		addNamesAsMuAnnotationsToStructureTones(mu1);
		parent.addMu(mu1, 0);
		parent.setStartTempo(mu1.getStartTempo());
		parent.setLengthInBars(mu1.getLengthInBars());
		return mu1;
	}



	private void addNamesAsMuAnnotationsToStructureTones(Mu mu1)
	{
		for (Mu mu: mu1.getAllMus())
		{
			if (mu.hasTag(MuTag.IS_STRUCTURE_TONE))
			{
				mu.addMuAnnotation(new MuAnnotation(mu.getName(), MuAnnotation.TextPlacement.PLACEMENT_BELOW));
			}
		}
	}



	private void getDrumPartForMu(Mu parent)
	{
		DrumPartGenerator.addTactusHiHatToDrumPart(parent);
		DrumPartGenerator.addKickAndSnareToDrumPart(parent);
		DrumPartGenerator.addSubTactusHiHatToDrumPart(parent);
		parent.getMu("drums").setHasLeadingDoubleBar(true);
	}



	private void getBassPartForMu(Mu parent, RandomMelodyParameterObject po, Mu mu1)
	{
		Mu mu3 = BassPartGenerator.addBassMuToParentMu(parent, mu1);
		mu3.setName("bass");
		mu3.addTag(MuTag.PART_BASS);
		mu3.setHasLeadingDoubleBar(true);
		mu3.setXMLKey(po.getXmlKey());
//		mu3.addTag(MuTag.BASS_CLEF);
	}



	private void getChordsPartForMu(Mu parent, RandomMelodyParameterObject po, Mu mu1)
	{
		Mu mu2 = ChordPartGenerator.addPadMuToParentMu(parent, mu1);
		mu2.setName("chords");
		mu2.addTag(MuTag.PART_CHORDS);
		mu2.setHasLeadingDoubleBar(true);
		mu2.setXMLKey(po.getXmlKey());
	}
}




class TestRandom extends Random
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7567582236186228328L;
	private double[] values = new double[] {0.1, 0.5, 0.8, 0.99};
	private int index = 1;
	
	public double nextDouble()
	{
		if (index == values.length - 1) 
		{
			index = 0;
		}
		else
		{
			index++;
		}
		return values[index];
	}
}
