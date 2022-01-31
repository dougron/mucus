package test.java.com.dougron.mucus.algorithms.mu_generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.mu_generator.SuFi_Enclosure;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

/**
 * this test (and possibly all overwritable_vectors sufis) will be developed in several stages:
 * 
 * 	1)	working with the new positionModel system which actually works as of December 2020
 * 	2)	working with the yet to be completed chord and key analysis tools
 * 
 * @author dougr
 *
 */

class SuFi_Enclosure_Test
{

	@Test
	final void instantiates()
	{
		SuFi sufi = new SuFi_Enclosure();
		assertNotNull(sufi);
	}
	
	
	@Test
	void creates_two_embellishment_notes() throws Exception
	{
		Mu parent = new Mu("parent");
		
		Mu note = new Mu("note");
		note.addMuNote(new MuNote(50, MuNote.DEFAULT_STRUCTURE_TONE_VELOCITY));
		note.setLengthInQuarters(2.0);
		parent.addMu(note, 2);
		
		SuFi sufi = new SuFi_Enclosure();
		note.addSuFi(sufi);
		note.generate();
		assertEquals(8.0, note.getPositionInQuarters());
		assertEquals(2, note.getMus().size());
	}
	
	
	@Test
	void by_default_creates_two_embellishing_notes_an_eighth_and_a_quarter_before_the_note() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu note = new Mu("note");
		note.addMuNote(new MuNote(50, MuNote.DEFAULT_STRUCTURE_TONE_VELOCITY));
		note.setLengthInQuarters(2.0);
		parent.addMu(note, 2);
		SuFi sufi = new SuFi_Enclosure();
		note.addSuFi(sufi);
		note.generate();

		assertEquals(7.5, note.getMus().get(0).getGlobalPositionInQuarters());
		assertEquals(7.0, note.getMus().get(1).getGlobalPositionInQuarters());
	}
	

	@Test
	void when_time_signature_set_to_3_4_by_default_creates_two_embellishing_notes_an_eighth_and_a_quarter_before_the_note() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		Mu note = new Mu("note");
		note.addMuNote(new MuNote(50, MuNote.DEFAULT_STRUCTURE_TONE_VELOCITY));
		note.setLengthInQuarters(2.0);
		parent.addMu(note, 2);
		SuFi sufi = new SuFi_Enclosure();
		note.addSuFi(sufi);
		note.generate();
		
		assertEquals(5.5, note.getMus().get(0).getGlobalPositionInQuarters());
		assertEquals(5.0, note.getMus().get(1).getGlobalPositionInQuarters());
		}
	
	@Test
	void when_set_to_accented_then_moves_associated_note_by_1_quarter() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu note = new Mu("note");
		note.addMuNote(new MuNote(50, MuNote.DEFAULT_STRUCTURE_TONE_VELOCITY));
		note.setLengthInQuarters(2.0);
		parent.addMu(note, 2);
		SuFi sufi = new SuFi_Enclosure(SuFi.AccentType.ACCENTED);
		note.addSuFi(sufi);
		note.generate();

		assertEquals(9.0, note.getGlobalPositionInQuarters());
		assertEquals(8.5, note.getMus().get(0).getGlobalPositionInQuarters());
		assertEquals(8.0, note.getMus().get(1).getGlobalPositionInQuarters());
	}
	
	
	@Test
	void testName() throws Exception
	{
		Mu parent = new Mu("parent");
		Mu note = new Mu("note");
		note.addMuNote(new MuNote(50, MuNote.DEFAULT_STRUCTURE_TONE_VELOCITY));
		note.setLengthInQuarters(2.0);
		parent.addMu(note, 2);
		SuFi sufi = new SuFi_Enclosure(SuFi.AccentType.ACCENTED);
		note.addSuFi(sufi);
		note.generate();
		
		List<Mu> mus = note.getMus();
		assertEquals(52, mus.get(0).getTopPitch());
		assertEquals(49, mus.get(1).getTopPitch());
	}
}
