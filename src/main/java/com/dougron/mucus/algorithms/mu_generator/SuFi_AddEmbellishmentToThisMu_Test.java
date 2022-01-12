package main.java.com.dougron.mucus.algorithms.mu_generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.algorithms.superimposifier.overwritable_vectors.SuFiSu_OverwritableVectors;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

class SuFi_AddEmbellishmentToThisMu_Test
{

	@Test
	void instantiates()
	{
		SuFi sufi = new SuFi_AddEmbellishmentToThisMu();
		assertNotNull(sufi);
	}
	
	
	@Test
	void is_within_scope_is_true_for_any_position() throws Exception
	{
		SuFi sufi = new SuFi_AddEmbellishmentToThisMu();
		assertTrue(sufi.isWithinScope(0.0));
	}
	
	
	@Test
	void can_be_added_to_sufisu() throws Exception
	{
		SuFi sufi = new SuFi_AddEmbellishmentToThisMu();
		SuFiSu sufisu = new SuFiSu_OverwritableVectors(new Mu("mu"));
		assertTrue(sufisu.addSufi(sufi));
	}
	
	
	@Test
	void can_take_an_argument_defining_sufis_to_use() throws Exception
	{
		SuFi sufi = new SuFi_AddEmbellishmentToThisMu(new SuFi_Enclosure());
		assertNotNull(sufi);	// yes, it makes no sense just trying to get rid of yellow triangles
	}
	
	
	@Test
	void generates_no_mus_in_attached_mu_if_no_munotes_are_present_at_generation_time() throws Exception
	{
		Mu mu = new Mu("chordTone");
		mu.addSuFi(new SuFi_AddEmbellishmentToThisMu(new SuFi_Enclosure()));
		mu.generate();
		assertEquals(0, mu.getMus().size());
	}
	
	
	@Test
	void generates_no_mus_if_no_munotes_are_present_at_generation_time() throws Exception
	{
		Mu mu = new Mu("chordTone");
		mu.addSuFi(new SuFi_AddEmbellishmentToThisMu(new SuFi_Enclosure()));
		mu.addMuNote(new MuNote(48, 72));
		mu.generate();
		assertEquals(2, mu.getMus().size());
	}
	
	
	

}
