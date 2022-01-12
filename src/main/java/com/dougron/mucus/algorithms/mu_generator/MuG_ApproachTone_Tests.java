package main.java.com.dougron.mucus.algorithms.mu_generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import DataObjects.combo_variables.IntAndDouble;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator.AccentType;
import main.java.com.dougron.mucus.algorithms.mu_generator.enums.NeighbourToneType;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

class MuG_ApproachTone_Tests
{

	@Test
	final void testToString ()
	{
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		assertEquals
		(
				"MuG_ApproachTone_RRP: \n" + 
				"relativeRhythmicPosition=0:0:-1:0 UNACCENTED\n" + 
				"UPPER_NEIGHBOUR", 
				mug.toString()
		);
	}


	@Test
	final void testMuG_ApproachTone_RRPRelative_RhythmicPosition_AccentType ()
	{
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		assertNotEquals(null, mug);
	}

	
	@Test
	final void testMuG_ApproachTone_RRP_RelativeRhythmicPosition_AccentType_NeighbourToneType ()
	{
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		NeighbourToneType ntt = NeighbourToneType.LOWER_NEIGHBOUR;
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at, ntt );
		assertNotEquals(null, mug);
	}


	@Test
	final void testSetParent ()
	{
		Mu mu = new Mu("mu");
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		mu.addMuGenerator(mug);
		assertEquals(mu, mug.getParent());
	}


//	@Disabled
//	@Test
//	final void testGenerate ()
//	{
//		fail("Not yet implemented"); // TODO
//	}


	@Test
	final void testGetDeepCopy ()
	{
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		MuGenerator mug2 = mug.getDeepCopy();
		assertEquals(mug.toString(), mug2.toString());
	}

//	@Disabled
//	@Test
//	final void testSetAccentType ()
//	{
//		fail("Not yet implemented"); // TODO
//	}


	@Test
	final void testGetParameterObjectArray ()
	{
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		Object[] poa = mug.getParameterObjectArray();
		assertEquals("MuG_ApproachTone_RRP", poa[0]);
		assertEquals(0, poa[1]);
		assertEquals(0, poa[2]);
		assertEquals(-1, poa[3]);
		assertEquals(0, poa[4]);
		assertEquals(NeighbourToneType.UPPER_NEIGHBOUR, poa[5]);
		assertEquals(AccentType.UNACCENTED, poa[6]);
	}



	@Test
	final void testGetMuGeneratorFromXMLElement ()
	{
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		Mu mu = new Mu("mu");
		mu.addMuGenerator(mug);
		
		try 
		{
			Document document = getDocument();
			Element element = mug.getXMLElement(document);

			MuGenerator mug2 = MuG_ApproachTone_RRP.getMuGeneratorFromXMLElement(element);
			assertEquals(mug.toString(), mug2.toString());	  
		} 
		catch (ParserConfigurationException pce) 
		{
			fail("ParserConfigurationException");
		}   	
	}
	
	
	private static Document getDocument() throws ParserConfigurationException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.newDocument();
		return document;
	}


	@Test
	void when_interval_is_a_decending_5th_then_note_added_is_a_diatonic_step_above_the_second_note () throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("D")));
		Mu structureTone1 = new Mu("structureTone1");
		structureTone1.addMuNote(new MuNote(69, 64));
		parent.addMu(structureTone1, new BarsAndBeats(0, 0.0));
		
		Mu structureTone2 = new Mu("structureTone2");
		structureTone2.addMuNote(new MuNote(62, 64));
		parent.addMu(structureTone2, new BarsAndBeats(2, 0.0));
		
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		structureTone2.addMuGenerator(mug);
		structureTone2.generate();
		
		
		
//		for (Integer pitch: parent.getListOfTopPitches())
//		{
//			System.out.println(pitch);
//		}
//		for (IntAndDouble iad: parent.getListOfTopPitchesAndGlobalPositionsInQuarters())
//		{
//			System.out.println(iad.toString());
//		}
		ArrayList<Integer> list = parent.getListOfTopPitches();
		assertEquals(69, list.get(0));
		assertEquals(62, list.get(1));
		assertEquals(64, list.get(2));
	}	
	
	
	@Test
	void when_interval_is_an_ascending_5th_then_note_added_is_a_diatonic_step_below_the_second_note () throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("D")));
		Mu structureTone1 = new Mu("structureTone1");
		structureTone1.addMuNote(new MuNote(62, 64));
		parent.addMu(structureTone1, new BarsAndBeats(0, 0.0));
		
		Mu structureTone2 = new Mu("structureTone2");
		structureTone2.addMuNote(new MuNote(69, 64));
		parent.addMu(structureTone2, new BarsAndBeats(2, 0.0));
		
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		structureTone2.addMuGenerator(mug);
		structureTone2.generate();
		

		ArrayList<Integer> list = parent.getListOfTopPitches();
		assertEquals(62, list.get(0));
		assertEquals(69, list.get(1));
		assertEquals(67, list.get(2));
	}
	
	
	@Test
	void when_interval_is_a_unison_then_note_added_is_a_diatonic_step_above_due_to_default_upper_neighbour_setting () throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("D")));
		Mu structureTone1 = new Mu("structureTone1");
		structureTone1.addMuNote(new MuNote(69, 64));
		parent.addMu(structureTone1, new BarsAndBeats(0, 0.0));
		
		Mu structureTone2 = new Mu("structureTone2");
		structureTone2.addMuNote(new MuNote(69, 64));
		parent.addMu(structureTone2, new BarsAndBeats(2, 0.0));
		
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		structureTone2.addMuGenerator(mug);
		structureTone2.generate();
		
		
		
//		for (Integer pitch: parent.getListOfTopPitches())
//		{
//			System.out.println(pitch);
//		}
//		for (IntAndDouble iad: parent.getListOfTopPitchesAndGlobalPositionsInQuarters())
//		{
//			System.out.println(iad.toString());
//		}
		ArrayList<Integer> list = parent.getListOfTopPitches();
		assertEquals(69, list.get(0));
		assertEquals(69, list.get(1));
		assertEquals(71, list.get(2));
	}	
	
	
	@Test
	void when_interval_is_a_unison_and_neighbour_tone_set_to_lower_then_note_added_is_a_diatonic_step_below () throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("D")));
		Mu structureTone1 = new Mu("structureTone1");
		structureTone1.addMuNote(new MuNote(69, 64));
		parent.addMu(structureTone1, new BarsAndBeats(0, 0.0));
		
		Mu structureTone2 = new Mu("structureTone2");
		structureTone2.addMuNote(new MuNote(69, 64));
		parent.addMu(structureTone2, new BarsAndBeats(2, 0.0));
		
		AccentType at = AccentType.UNACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at, NeighbourToneType.LOWER_NEIGHBOUR);
		structureTone2.addMuGenerator(mug);
		structureTone2.generate();
		
		
		ArrayList<Integer> list = parent.getListOfTopPitches();
		assertEquals(69, list.get(0));
		assertEquals(69, list.get(1));
		assertEquals(67, list.get(2));
	}	
	
	
	@Test
	void when_interval_is_an_ascending_5th_and_embellishment_is_accented_then_second_structure_tone_is_moved_later_to_global_position_9_quarters () throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("D")));
		Mu structureTone1 = new Mu("structureTone1");
		structureTone1.addTag(MuTag.IS_STRUCTURE_TONE);
		structureTone1.addMuNote(new MuNote(62, 64));
		parent.addMu(structureTone1, new BarsAndBeats(0, 0.0));
		
		Mu structureTone2 = new Mu("structureTone2");
		structureTone2.addTag(MuTag.IS_STRUCTURE_TONE);
		structureTone2.addMuNote(new MuNote(69, 64));
		parent.addMu(structureTone2, new BarsAndBeats(2, 0.0));
		
		AccentType at = AccentType.ACCENTED;
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, -1, 0);
		MuGenerator mug = new MuG_ApproachTone_RRP(rrp, at);
		structureTone2.addMuGenerator(mug);
		structureTone2.generate();
		

		ArrayList<IntAndDouble> list = parent.getListOfTopPitchesAndGlobalPositionsInQuarters();
		assertEquals(0.0, list.get(0).d);
		assertEquals(9.0, list.get(1).d);
		assertEquals(8.0, list.get(2).d);
	}

}
