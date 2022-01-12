package test.java.com.dougron.mucus.algorithms.part_generators;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.part_generators.drum_part_generator.SeparateDrumPartGenerator;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

class SeparateDrumPartGenerator_Tests
{

	@Test
	void given_4_4_time_then_get_tactus_hihat_returns_correct_list_of_mus() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(1);
		SeparateDrumPartGenerator.addTactusHiHatToHatPart(parent);
		ArrayList<Mu> hatPart = parent.getMuWithTag(MuTag.PART_HAT);
		assertEquals(1, hatPart.size());
		assertEquals(4, hatPart.get(0).getMus().size());
	}
	
	
	@Test
	void given_4_4_time_then_get_tactus_and_sub_tactus_hihat_returns_correct_list_of_mus() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(1);
		SeparateDrumPartGenerator.addTactusHiHatToHatPart(parent);
		SeparateDrumPartGenerator.addSubTactusHiHatToHatPart(parent);
		ArrayList<Mu> hatPart = parent.getMuWithTag(MuTag.PART_HAT);
		assertEquals(1, hatPart.size());
		assertEquals(8, hatPart.get(0).getMus().size());
	}
	
	
	@Test
	void given_4_4_time_then_get_kik_and_snare_returns_correct_list_of_mus () throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(1);
		SeparateDrumPartGenerator.addKikAndSnareToKikAndSnarePart(parent);
		ArrayList<Mu> kikPart = parent.getMuWithTag(MuTag.PART_KIK);
		assertEquals(1, kikPart.size());
		assertEquals(1, kikPart.get(0).getMus().size());
		ArrayList<Mu> snrPart = parent.getMuWithTag(MuTag.PART_SNARE);
		assertEquals(1, snrPart.size());
		assertEquals(1, snrPart.get(0).getMus().size());
	}
	
	
	@Test
	void given_4_4_time_and_length_of_2_bars_then_get_kik_snare_and_hat_returns_correct_list_of_mus () throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(2);
		SeparateDrumPartGenerator.addKikAndSnareToKikAndSnarePart(parent);
		SeparateDrumPartGenerator.addTactusHiHatToHatPart(parent);
		SeparateDrumPartGenerator.addSubTactusHiHatToHatPart(parent);
		
		ArrayList<Mu> hatPart = parent.getMuWithTag(MuTag.PART_HAT);
		assertEquals(1, hatPart.size());
		assertEquals(16, hatPart.get(0).getMus().size());
		
		ArrayList<Mu> kikPart = parent.getMuWithTag(MuTag.PART_KIK);
		assertEquals(1, kikPart.size());
		assertEquals(2, kikPart.get(0).getMus().size());
		
		ArrayList<Mu> snrPart = parent.getMuWithTag(MuTag.PART_SNARE);
		assertEquals(1, snrPart.size());
		assertEquals(2, snrPart.get(0).getMus().size());
	}

}
