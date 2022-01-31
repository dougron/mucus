package test.java.com.dougron.mucus.algorithms.part_generators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.part_generators.drum_part_generator.DrumPartGenerator;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

class DrumPartGenerator_Test
{

	@Test
	void given_4_4_time_then_get_tactus_hihat_returns_correct_list_of_mus() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(1);
		DrumPartGenerator.addTactusHiHatToDrumPart(parent);
		assertEquals(1, parent.getMuWithTag(MuTag.PART_DRUMS).size());
		assertEquals(4, parent.getMuWithTag(MuTag.PART_DRUMS).get(0).getMus().size());
	}

	

	@Test
	void given_7_8_time_then_get_tactus_hihat_returns_correct_list_of_mus() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(1);
		parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		DrumPartGenerator.addTactusHiHatToDrumPart(parent);
		ArrayList<Mu> musWithTag = parent.getMuWithTag(MuTag.PART_DRUMS);
		assertEquals(1, musWithTag.size());
		Mu muWithTag = musWithTag.get(0);
		assertEquals(3, muWithTag.getMus().size());
	}
}
