package test.java.com.dougron.mucus.algorithms;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.generic_generator.ParameterObject;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_ChordTone_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import main.java.com.dougron.mucus.algorithms.mu_generator.enums.ChordToneType;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.structure_tone_embellisher.StructureToneEmbellisher;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;

class StructureToneEmbellisher_Tests
{

	@Test
	final void when_there_is_one_structure_tone_and_one_embellishment_is_indicated_then_one_note_has_been_added ()
	{
		Mu mu = new Mu("parent");
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(52, 64));
		mu.addMu(note, new BarsAndBeats(1, 0.0));
		List<MuGenerator> embList = List.of(
					new MuG_ChordTone_RRP(new RelativeRhythmicPosition(0, 0, 0, -1), ChordToneType.CLOSEST_ABOVE)
				);
		// the below double step for setting the parameterObject.embellishments makes more sense when the 
		// StructureToneEmbellisher is being used to embellish many different parts.
		// instrument specific values are copied to the general parameters that StructureToneEmbellisher 
		// will use
		ParameterObject po = ParameterObject.builder()
				.bassEmbellishment(0, embList )
				.build();
		po.setEmbellishments(po.getBassEmbellishments());
		po.setEmbellishmentRepetitionPattern(po.getBassEmbellishmentRepetitionPattern());
		mu = StructureToneEmbellisher.addEmbellishmentMus(mu, po);
		assertThat(mu).isNotNull();
		assertThat(mu.getAllMus().size()).isEqualTo(3);	// mu, note and the embellishment that was added
	}
	
	
	@Test
	final void when_there_is_one_structure_tone_and_a_chord_tone_above_is_indicated_then_one_note_has_been_added_which_is_the_chord_tone_above ()
	{
		Mu mu = new Mu("parent");
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(52, 64));
		mu.addMu(note, new BarsAndBeats(1, 0.0));
		List<MuGenerator> embList = List.of(
					new MuG_ChordTone_RRP(new RelativeRhythmicPosition(0, 0, 0, -1), ChordToneType.CLOSEST_ABOVE)
				);
		ParameterObject po = ParameterObject.builder()
				.bassEmbellishment(0, embList )
				.build();
		po.setEmbellishments(po.getBassEmbellishments());
		po.setEmbellishmentRepetitionPattern(po.getBassEmbellishmentRepetitionPattern());
		mu = StructureToneEmbellisher.addEmbellishmentMus(mu, po);
		// assumes default chord of C, note 52=E, chord tone above = G (55)
		assertThat(note.getMus().get(0).getTopPitch()).isEqualTo(55);	
	}
	
	
	@Test
	final void embellished_note_acquires_the_velocity_of_the_non_accented_bass_note_parameter ()
	{
		Mu mu = new Mu("parent");
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(52, 64));
		mu.addMu(note, new BarsAndBeats(1, 0.0));
		List<MuGenerator> embList = List.of(
					new MuG_ChordTone_RRP(new RelativeRhythmicPosition(0, 0, 0, -1), ChordToneType.CLOSEST_ABOVE)
				);
		ParameterObject po = ParameterObject.builder()
				.bassEmbellishment(0, embList )
				.bassNonAccentVelocity(44)
				.build();
		po.setEmbellishments(po.getBassEmbellishments());
		po.setNonAccentVelocity(po.getBassNonAccentVelocity());
		po.setEmbellishmentRepetitionPattern(po.getBassEmbellishmentRepetitionPattern());
		mu = StructureToneEmbellisher.addEmbellishmentMus(mu, po);
		
		assertThat(note.getMus().get(0).getMuNotes().get(0).getVelocity()).isEqualTo(44);	
	}

	
}
