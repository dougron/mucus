package test.java.com.dougron.mucus.algorithms;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.mu_chord_tone_and_embellishment.ChordToneAndEmbellishmentTagger;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;

class ChordToneAndEmbellishmentTagger_Test
{

	@Test
	final void test()
	{
		Mu mu = new Mu("mu");
		ChordToneAndEmbellishmentTagger.addTags(mu);
	}
	
	

	Mu makeMuWithAllSemitones()
	{
		Mu parent = new Mu("parent");
		parent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator());
		parent.setLengthInBars(3);
		
		Mu child_c = new Mu("C");
		child_c.setLengthInQuarters(1.0);
		child_c.addMuNote(new MuNote(60, 48));
		parent.addMu(child_c, 0.0);
		
		Mu child_csharp = new Mu("Db");
		child_csharp.setLengthInQuarters(1.0);
		child_csharp.addMuNote(new MuNote(61, 48));
		parent.addMu(child_csharp, 1.0);
		
		Mu child_d = new Mu("D");
		child_d.setLengthInQuarters(1.0);
		child_d.addMuNote(new MuNote(62, 48));
		parent.addMu(child_d, 2.0);
		
		Mu child_dsharp = new Mu("Eb");
		child_dsharp.setLengthInQuarters(1.0);
		child_dsharp.addMuNote(new MuNote(63, 48));
		parent.addMu(child_dsharp, 3.0);
		
		Mu child_e = new Mu("E");
		child_e.setLengthInQuarters(1.0);
		child_e.addMuNote(new MuNote(64, 48));
		parent.addMu(child_e, 4.0);
		
		Mu child_f = new Mu("F");
		child_f.setLengthInQuarters(1.0);
		child_f.addMuNote(new MuNote(65, 48));
		parent.addMu(child_f, 5.0);
		
		Mu child_fsharp = new Mu("Gb");
		child_fsharp.setLengthInQuarters(1.0);
		child_fsharp.addMuNote(new MuNote(66, 48));
		parent.addMu(child_fsharp, 6.0);
		
		Mu child_g = new Mu("G");
		child_g.setLengthInQuarters(1.0);
		child_g.addMuNote(new MuNote(67, 48));
		parent.addMu(child_g, 7.0);
		
		Mu child_aflat = new Mu("Ab");
		child_aflat.setLengthInQuarters(1.0);
		child_aflat.addMuNote(new MuNote(68, 48));
		parent.addMu(child_aflat, 8.0);
		
		Mu child_a = new Mu("A");
		child_a.setLengthInQuarters(1.0);
		child_a.addMuNote(new MuNote(69, 48));
		parent.addMu(child_a, 9.0);
		
		Mu child_bflat = new Mu("Bb");
		child_bflat.setLengthInQuarters(1.0);
		child_bflat.addMuNote(new MuNote(70, 48));
		parent.addMu(child_bflat, 10.0);
		
		Mu child_b = new Mu("B");
		child_b.setLengthInQuarters(1.0);
		child_b.addMuNote(new MuNote(71, 48));
		parent.addMu(child_b, 11.0);
		
		return parent;
	}
	
	
	@Test
	void syncopations_will_be_tagged_as_such_and_other_not() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(2);
		
		Mu note1 = makeMuWithNote(60, 64, 1.0, "note1");
		parent.addMu(note1, 1.5);
		Mu note2 = makeMuWithNote(61, 64, 1.0, "note2");
		parent.addMu(note2, 3.0);
		Mu note3 = makeMuWithNote(63, 64, 1.0, "note3");
		parent.addMu(note3, 6.0);
		Mu note4 = makeMuWithNote(64, 64, 1.0, "note4");
		parent.addMu(note4, 7.0);		// not a syncopation as there is no next tactus. it is on 4th beat of last bar in 4/4 time
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertTrue(note1.hasTag(MuTag.IS_SYNCOPATION));
		assertTrue(note2.hasTag(MuTag.IS_SYNCOPATION));
		assertFalse(note3.hasTag(MuTag.IS_SYNCOPATION));
		assertFalse(note4.hasTag(MuTag.IS_SYNCOPATION));
	}
	
	
	@Test
	void chord_tone_will_be_tagged_as_such_and_non_chord_tone_will_not() throws Exception
	{
		Mu parent = makeMuWithAllSemitones();
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("C")));
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertTrue(parent.getMu("C").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("Db").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("D").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("Eb").hasTag(MuTag.IS_CHORD_TONE));
		assertTrue(parent.getMu("E").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("F").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("Gb").hasTag(MuTag.IS_CHORD_TONE));
		assertTrue(parent.getMu("G").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("Ab").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("A").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("Bb").hasTag(MuTag.IS_CHORD_TONE));
		assertFalse(parent.getMu("B").hasTag(MuTag.IS_CHORD_TONE));		
	}
	
	

	@Test
	void extended_chord_tone_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = makeMuWithAllSemitones();
		parent.setChordListGenerator(new SingleChordGenerator(new Chord("C")));
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertFalse(parent.getMu("C").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertFalse(parent.getMu("Db").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertTrue(parent.getMu("D").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertFalse(parent.getMu("Eb").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertFalse(parent.getMu("E").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertFalse(parent.getMu("F").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertTrue(parent.getMu("Gb").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertFalse(parent.getMu("G").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertFalse(parent.getMu("Ab").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertTrue(parent.getMu("A").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertFalse(parent.getMu("Bb").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		assertTrue(parent.getMu("B").hasTag(MuTag.IS_EXTENDED_CHORD_TONE));		
	}
	
	
	@Test
	void scale_tone_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		// fun fact: at 23 Jan 2021 a single chord gets analyzed as a V chord and acquires a harmonic minor 5 scale. 
		Mu parent = makeMuWithAllSemitones();
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"C", "C", "C", "C", "G"}));
		parent.setLengthInBars(5);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertFalse(parent.getMu("C").hasTag(MuTag.IS_SCALE_TONE));
		assertFalse(parent.getMu("Db").hasTag(MuTag.IS_SCALE_TONE));
		assertTrue(parent.getMu("D").hasTag(MuTag.IS_SCALE_TONE));
		assertFalse(parent.getMu("Eb").hasTag(MuTag.IS_SCALE_TONE));
		assertFalse(parent.getMu("E").hasTag(MuTag.IS_SCALE_TONE));
		assertTrue(parent.getMu("F").hasTag(MuTag.IS_SCALE_TONE));
		assertFalse(parent.getMu("Gb").hasTag(MuTag.IS_SCALE_TONE));
		assertFalse(parent.getMu("G").hasTag(MuTag.IS_SCALE_TONE));
		assertFalse(parent.getMu("Ab").hasTag(MuTag.IS_SCALE_TONE));
		assertTrue(parent.getMu("A").hasTag(MuTag.IS_SCALE_TONE));
		assertFalse(parent.getMu("Bb").hasTag(MuTag.IS_SCALE_TONE));
		assertTrue(parent.getMu("B").hasTag(MuTag.IS_SCALE_TONE));		
	}
	
	
	@Test
	void non_scale_tone_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = makeMuWithAllSemitones();
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"C", "C", "C", "G"}));
		parent.setLengthInBars(4);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertFalse(parent.getMu("C").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertTrue(parent.getMu("Db").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertFalse(parent.getMu("D").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertTrue(parent.getMu("Eb").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertFalse(parent.getMu("E").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertFalse(parent.getMu("F").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertTrue(parent.getMu("Gb").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertFalse(parent.getMu("G").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertTrue(parent.getMu("Ab").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertFalse(parent.getMu("A").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertTrue(parent.getMu("Bb").hasTag(MuTag.IS_NON_SCALE_TONE));
		assertFalse(parent.getMu("B").hasTag(MuTag.IS_NON_SCALE_TONE));		
	}
	
	
	@Test
	void lower_chromatic_approach_notes_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(2);
		Mu note1 = makeMuWithNote(60, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(61, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(63, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		Mu note4 = makeMuWithNote(64, 64, 1.0, "note4");
		parent.addMu(note4, 3.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertTrue(note1.hasTag(MuTag.IS_LOWER_CHROMATIC_APPROACH_TONE));
		assertFalse(note2.hasTag(MuTag.IS_LOWER_CHROMATIC_APPROACH_TONE));
		assertTrue(note3.hasTag(MuTag.IS_LOWER_CHROMATIC_APPROACH_TONE));
		assertFalse(note4.hasTag(MuTag.IS_LOWER_CHROMATIC_APPROACH_TONE));
	}



	private Mu makeMuWithNote(int aPitch, int aVelocity, double aLengthInQuarters, String aName)
	{
		Mu mu = new Mu(aName);
		mu.setLengthInQuarters(aLengthInQuarters);
		mu.addMuNote(new MuNote(aPitch, aVelocity));
		return mu;
	}
	
	
	@Test
	void upper_chromatic_approach_notes_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(2);
		Mu note1 = makeMuWithNote(60, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(59, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(63, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		Mu note4 = makeMuWithNote(62, 64, 1.0, "note4");
		parent.addMu(note4, 3.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertTrue(note1.hasTag(MuTag.IS_UPPER_CHROMATIC_APPROACH_TONE));
		assertFalse(note2.hasTag(MuTag.IS_UPPER_CHROMATIC_APPROACH_TONE));
		assertTrue(note3.hasTag(MuTag.IS_UPPER_CHROMATIC_APPROACH_TONE));
		assertFalse(note4.hasTag(MuTag.IS_UPPER_CHROMATIC_APPROACH_TONE));
	}
	
	
	@Test
	void diatonic_approach_notes_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = new Mu("parent");
		// must have harmonic context as notes have to be diatonic in the prevailing key
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Db", "Db", "Ab"}));	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(63, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(66, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		Mu note4 = makeMuWithNote(65, 64, 1.0, "note4");
		parent.addMu(note4, 3.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertTrue(note1.hasTag(MuTag.IS_LOWER_DIATONIC_APPROACH_TONE));
		assertFalse(note1.hasTag(MuTag.IS_UPPER_DIATONIC_APPROACH_TONE));
		
		assertFalse(note2.hasTag(MuTag.IS_LOWER_DIATONIC_APPROACH_TONE));
		assertFalse(note2.hasTag(MuTag.IS_UPPER_DIATONIC_APPROACH_TONE));
		
		assertFalse(note3.hasTag(MuTag.IS_LOWER_DIATONIC_APPROACH_TONE));
		assertTrue(note3.hasTag(MuTag.IS_UPPER_DIATONIC_APPROACH_TONE));
		
		assertFalse(note4.hasTag(MuTag.IS_LOWER_DIATONIC_APPROACH_TONE));
		assertFalse(note4.hasTag(MuTag.IS_UPPER_DIATONIC_APPROACH_TONE));	
	}
	
	
	@Test
	void passing_tones_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(63, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(65, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		Mu note4 = makeMuWithNote(63, 64, 1.0, "note4");
		parent.addMu(note4, 3.0);
		Mu note5 = makeMuWithNote(62, 64, 1.0, "note5");
		parent.addMu(note5, 4.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertFalse(note1.hasTag(MuTag.IS_PASSING_TONE));
		assertTrue(note2.hasTag(MuTag.IS_PASSING_TONE));
		assertFalse(note3.hasTag(MuTag.IS_PASSING_TONE));
		assertTrue(note4.hasTag(MuTag.IS_PASSING_TONE));
		assertFalse(note5.hasTag(MuTag.IS_PASSING_TONE));
	}
	
	
	@Test
	void neighbour_tones_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(63, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(61, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		Mu note4 = makeMuWithNote(60, 64, 1.0, "note4");
		parent.addMu(note4, 3.0);
		Mu note5 = makeMuWithNote(61, 64, 1.0, "note5");
		parent.addMu(note5, 4.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertFalse(note1.hasTag(MuTag.IS_UPPER_NEIGHBOUR_TONE));
		assertFalse(note1.hasTag(MuTag.IS_LOWER_NEIGHBOUR_TONE));
		
		assertTrue(note2.hasTag(MuTag.IS_UPPER_NEIGHBOUR_TONE));
		assertFalse(note2.hasTag(MuTag.IS_LOWER_NEIGHBOUR_TONE));
		
		assertFalse(note3.hasTag(MuTag.IS_UPPER_NEIGHBOUR_TONE));
		assertFalse(note3.hasTag(MuTag.IS_LOWER_NEIGHBOUR_TONE));
		
		assertFalse(note4.hasTag(MuTag.IS_UPPER_NEIGHBOUR_TONE));
		assertTrue(note4.hasTag(MuTag.IS_LOWER_NEIGHBOUR_TONE));
		
		assertFalse(note5.hasTag(MuTag.IS_UPPER_NEIGHBOUR_TONE));
		assertFalse(note5.hasTag(MuTag.IS_LOWER_NEIGHBOUR_TONE));

	}
	
	
	@Test
	void enclosures_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(63, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(62, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		Mu note4 = makeMuWithNote(58, 64, 1.0, "note4");
		parent.addMu(note4, 3.0);
		Mu note5 = makeMuWithNote(60, 64, 1.0, "note5");
		parent.addMu(note5, 4.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertTrue(note1.hasTag(MuTag.IS_ENCLOSURE_1));
		assertFalse(note1.hasTag(MuTag.IS_ENCLOSURE_2));
		
		assertFalse(note2.hasTag(MuTag.IS_ENCLOSURE_1));
		assertTrue(note2.hasTag(MuTag.IS_ENCLOSURE_2));
		
		assertTrue(note3.hasTag(MuTag.IS_ENCLOSURE_1));
		assertFalse(note3.hasTag(MuTag.IS_ENCLOSURE_2));
		
		assertFalse(note4.hasTag(MuTag.IS_ENCLOSURE_1));
		assertTrue(note4.hasTag(MuTag.IS_ENCLOSURE_2));
		
		assertFalse(note5.hasTag(MuTag.IS_ENCLOSURE_1));
		assertFalse(note5.hasTag(MuTag.IS_ENCLOSURE_2));
	}
	
	
	@Test
	void escape_tones_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(66, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(65, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		Mu note4 = makeMuWithNote(67, 64, 1.0, "note4");
		parent.addMu(note4, 3.0);
		Mu note5 = makeMuWithNote(60, 64, 1.0, "note5");
		parent.addMu(note5, 4.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertFalse(note1.hasTag(MuTag.IS_ESCAPE_TONE));
		assertTrue(note2.hasTag(MuTag.IS_ESCAPE_TONE));
		assertFalse(note3.hasTag(MuTag.IS_ESCAPE_TONE));
		assertTrue(note4.hasTag(MuTag.IS_ESCAPE_TONE));
		assertFalse(note5.hasTag(MuTag.IS_ESCAPE_TONE));
	}
	
	
	@Test
	void anticipations_will_be_tagged_as_such_and_others_will_not() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 3.0);
		Mu note2 = makeMuWithNote(61, 64, 1.0, "note2");
		parent.addMu(note2, 4.0);
		Mu note3 = makeMuWithNote(60, 64, 1.0, "note3");
		parent.addMu(note3, 5.0);
		Mu note4 = makeMuWithNote(72, 64, 1.0, "note4");	// is anticipation of note5 which is a syncopation of a stronger beat
		parent.addMu(note4, 6.0);
		Mu note5 = makeMuWithNote(60, 64, 1.0, "note5");
		parent.addMu(note5, 7.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertTrue(note1.hasTag(MuTag.IS_ANTICIPATION));
		assertFalse(note1.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		assertFalse(note2.hasTag(MuTag.IS_ANTICIPATION));
		assertFalse(note2.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		assertFalse(note3.hasTag(MuTag.IS_ANTICIPATION));
		assertTrue(note3.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		assertFalse(note4.hasTag(MuTag.IS_ANTICIPATION));
		assertTrue(note4.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		assertFalse(note5.hasTag(MuTag.IS_ANTICIPATION));
		assertFalse(note5.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
	}
	
	
	@Test
	void given_child_is_a_chord_tone_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		// must have harmonic context as notes have to be diatonic in the prevailing key
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Db", "Db", "Ab"}));	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note1.hasTag(MuTag.IS_CHORD_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "ct"));
	}
	
	
	@Test
	void given_child_is_an_extended_chord_tone_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		// must have harmonic context as notes have to be diatonic in the prevailing key
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Db", "Db", "Ab"}));	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(63, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note1.hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "ect"));
	}
	
	
	@Test
	void given_child_is_a_syncopation_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		// must have harmonic context as notes have to be diatonic in the prevailing key
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Db", "Db", "Ab"}));	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(63, 64, 1.0, "note1");
		parent.addMu(note1, 1.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note1.hasTag(MuTag.IS_EXTENDED_CHORD_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "sync_2.0"));
	}
	
	
	@Test
	void given_child_is_a_non_scale_chord_tone_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		// must have harmonic context as notes have to be diatonic in the prevailing key
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Db", "Db", "Ab"}));	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(62, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note1.hasTag(MuTag.IS_NON_SCALE_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "nst"));
	}
	
	
	@Test
	void given_child_is_a_scale_chord_tone_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		// must have harmonic context as notes have to be diatonic in the prevailing key
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Db", "Db", "Ab"}));	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(66, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note1.hasTag(MuTag.IS_SCALE_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "st"));
	}
	
	
	@Test
	void given_child_is_a_chromatic_approach_note_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		// must have harmonic context as notes have to be diatonic in the prevailing key
//		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Db", "Db", "Ab"}));	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(66, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(67, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(66, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note1.hasTag(MuTag.IS_LOWER_CHROMATIC_APPROACH_TONE));
		assertTrue(note2.hasTag(MuTag.IS_UPPER_CHROMATIC_APPROACH_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "ac_lcat"));
		assertTrue(hasAnnotation(note2, "un_ucat"));
	}
	
	
	@Test
	void given_child_is_a_diatonic_approach_note_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		// must have harmonic context as notes have to be diatonic in the prevailing key
		parent.setChordListGenerator(new SimpleEvenChordProgression(new String[] {"Db", "Db", "Db", "Ab"}));	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(63, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(61, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note1.hasTag(MuTag.IS_LOWER_DIATONIC_APPROACH_TONE));
		assertTrue(note2.hasTag(MuTag.IS_UPPER_DIATONIC_APPROACH_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "ac_ldat"));
		assertTrue(hasAnnotation(note2, "un_udat"));
	}
	
	
	@Test
	void given_child_is_a_passing_tone_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(63, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(65, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note2.hasTag(MuTag.IS_PASSING_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note2, "un_pt"));
	}
	
	
	@Test
	void given_child_is_a_neighbour_tone_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(63, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(61, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);
		Mu note4 = makeMuWithNote(63, 64, 1.0, "note4");
		parent.addMu(note4, 3.0);
				
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note2.hasTag(MuTag.IS_UPPER_NEIGHBOUR_TONE));
		assertTrue(note3.hasTag(MuTag.IS_LOWER_NEIGHBOUR_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note2, "un_unt"));
		assertTrue(hasAnnotation(note3, "un_lnt"));		// unaccented as note4 is syncopation of 1st beat of next bar, therefore stronger
	}
	
	
	@Test
	void given_children_are_an_enclosure_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(64, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(63, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);		
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note1.hasTag(MuTag.IS_ENCLOSURE_1));
		assertTrue(note2.hasTag(MuTag.IS_ENCLOSURE_2));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "ac_enc1"));
		assertTrue(hasAnnotation(note2, "ac_enc2"));		
	}
	
	
	@Test
	void given_child_is_an_escape_tone_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");	
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 0.0);
		Mu note2 = makeMuWithNote(64, 64, 1.0, "note2");
		parent.addMu(note2, 1.0);
		Mu note3 = makeMuWithNote(63, 64, 1.0, "note3");
		parent.addMu(note3, 2.0);		
		
		ChordToneAndEmbellishmentTagger.addTags(parent);		
		assertTrue(note2.hasTag(MuTag.IS_ESCAPE_TONE));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note2, "un_esc"));
		
	}
	
	
	@Test
	void given_children_are_anticipations_when_tags_are_converted_to_xml_annotations_then_annotation_list_contains_correct_annotation() throws Exception
	{
		Mu parent = new Mu("parent");
		parent.setLengthInBars(4);
		Mu note1 = makeMuWithNote(61, 64, 1.0, "note1");
		parent.addMu(note1, 3.0);
		Mu note2 = makeMuWithNote(61, 64, 1.0, "note2");
		parent.addMu(note2, 4.0);
		Mu note3 = makeMuWithNote(60, 64, 1.0, "note3");
		parent.addMu(note3, 5.0);
		Mu note4 = makeMuWithNote(72, 64, 1.0, "note4");	// is anticipation of note5 which is a syncopation of a stronger beat
		parent.addMu(note4, 6.0);
		Mu note5 = makeMuWithNote(60, 64, 1.0, "note5");
		parent.addMu(note5, 7.0);
		
		ChordToneAndEmbellishmentTagger.addTags(parent);
		
		assertTrue(note1.hasTag(MuTag.IS_ANTICIPATION));
		assertFalse(note1.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		assertFalse(note2.hasTag(MuTag.IS_ANTICIPATION));
		assertFalse(note2.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		assertFalse(note3.hasTag(MuTag.IS_ANTICIPATION));
		assertTrue(note3.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		assertFalse(note4.hasTag(MuTag.IS_ANTICIPATION));
		assertTrue(note4.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		assertFalse(note5.hasTag(MuTag.IS_ANTICIPATION));
		assertFalse(note5.hasTag(MuTag.IS_OCTAVE_ANTICIPATION));
		
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(parent);
		assertTrue(hasAnnotation(note1, "ant"));
		assertTrue(hasAnnotation(note3, "oct_ant"));
	}



	private boolean hasAnnotation(Mu aMu, String annotation)
	{
		for (MuAnnotation muan: aMu.getMuAnnotations())
		{
//			System.out.println(muan.getAnnotation());
			if (muan.getAnnotation().equals(annotation)) return true;
		}
		return false;
	}
	
	
	
	
	

}
