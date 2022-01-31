package test.java.com.dougron.mucus.algorithms.mu_generator;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_ChordTone_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import main.java.com.dougron.mucus.algorithms.mu_generator.enums.ChordToneType;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.da_utils.static_chord_scale_dictionary.ChordToneName;


class MuG_ChordToneRRP_Tests
{

	@Test
	final void initializes ()
	{
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_ABOVE);		
	}
	
	
	@Test
	void mug_chord_tone_creates_note () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(52, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_ABOVE);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().size()).isEqualTo(1);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_50_and_chordToneType_is_CLOSEST_ABOVE_then_note_is_55 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(50, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_ABOVE);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(55);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_50_and_chordToneType_is_CLOSEST_ABOVE_OR_EQUAL_then_note_is_50 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(50, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_ABOVE_OR_EQUAL);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(50);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_51_and_chordToneType_is_CLOSEST_ABOVE_OR_EQUAL_then_note_is_55 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(51, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_ABOVE_OR_EQUAL);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(55);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_51_and_chordToneType_is_CLOSEST_BELOW_then_note_is_50 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(51, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_BELOW);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(50);
	}
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_50_and_chordToneType_is_CLOSEST_BELOW_then_note_is_46 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(50, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_BELOW);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(46);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_50_and_chordToneType_is_CLOSEST_BELOW_OR_EQUAL_then_note_is_50 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(50, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_BELOW_OR_EQUAL);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(50);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_49_and_chordToneType_is_CLOSEST_then_note_is_50 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(49, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(50);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_49_and_chordToneType_is_CLOSEST_ABOVE_and_jumpCount_is_2_then_note_is_55 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(49, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_ABOVE, 2);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(55);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_51_and_chordToneType_is_CLOSEST_BELOW_and_jumpCount_is_2_then_note_is_46 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(51, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP(rrp, ChordToneType.CLOSEST_BELOW, 2);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(46);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_49_and_chordToneType_is_CLOSEST_BELOW_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_43 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(49, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST_BELOW, 
						new ChordToneName[] 
								{
										ChordToneName.ROOT,
										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(43);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_51_and_chordToneType_is_CLOSEST_BELOW_and_jumpCount_is_2_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_43 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(51, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST_BELOW, 
						2,
						new ChordToneName[] 
								{
//										ChordToneName.ROOT,
//										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(43);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_51_and_chordToneType_is_CLOSEST_ABOVE_and_jumpCount_is_2_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_62 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(51, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST_ABOVE, 
						2,
						new ChordToneName[] 
								{
										ChordToneName.ROOT,
										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(62);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_46_and_chordToneType_is_CLOSEST_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_43 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(46, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST, 
						2,
						new ChordToneName[] 
								{
										ChordToneName.ROOT,
										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(43);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_47_and_chordToneType_is_CLOSEST_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_50 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(47, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST, 
						2,
						new ChordToneName[] 
								{
										ChordToneName.ROOT,
										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(50);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_46_and_chordToneType_is_CLOSEST_BELOW_OR_EQUAL_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_43 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(46, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST_BELOW_OR_EQUAL, 
						2,
						new ChordToneName[] 
								{
										ChordToneName.ROOT,
										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(43);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_43_and_chordToneType_is_CLOSEST_BELOW_OR_EQUAL_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_43 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(43, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST_BELOW_OR_EQUAL, 
						2,
						new ChordToneName[] 
								{
										ChordToneName.ROOT,
										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(43);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_43_and_chordToneType_is_CLOSEST_ABOVE_OR_EQUAL_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_43 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(43, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST_ABOVE_OR_EQUAL, 
						2,
						new ChordToneName[] 
								{
										ChordToneName.ROOT,
										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(43);
	}
	
	
	@Test
	void when_chord_is_Gm_and_parent_note_is_44_and_chordToneType_is_CLOSEST_ABOVE_OR_EQUAL_and_chord_tones_are_limited_to_ROOT_or_FIFTH_then_note_is_50 () throws Exception
	{
		// 50 = D, 55 = G
		Mu parent = new Mu("parent");
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("Gm")));
		Mu note = new Mu("note");
		note.setLengthInQuarters(1.0);
		note.addMuNote(new MuNote(44, 64));
		parent.addMu(note, 1.5);
		RelativeRhythmicPosition rrp = new RelativeRhythmicPosition(0, 0, 0, -1);
		MuGenerator mug = new MuG_ChordTone_RRP
				(
						rrp, 
						ChordToneType.CLOSEST_ABOVE_OR_EQUAL, 
						2,
						new ChordToneName[] 
								{
										ChordToneName.ROOT,
										ChordToneName.FIFTH
								}
				);
		note.addMuGenerator(mug);
		note.generate();
		assertThat(note.getMus().get(0).getMuNotes().get(0).getPitch()).isEqualTo(50);
	}

}
