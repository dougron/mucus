package test.java.com.dougron.mucus.mu_framework.chord_list;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordAnalyzerType;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.com.dougron.mucus.mu_framework.chord_list.SimpleEvenChordProgression;

class ChordAnalyzerType_Tests
{

	// ProgressionAnalyzer puts every chord of this in D (keyIndex 2), TonalParsimonyAnalyzer in C (keyIndex 0)
	private static final String[] PROGRESSION = new String[] {"G", "C", "G", "Am", "Em", "Dm", "Am", "C", "Em", "G", "Em"};
	private static final int PROGRESSION_ANALYZER_KEY_INDEX = 2;
	private static final int TONAL_PARSIMONY_KEY_INDEX = 0;


	@Test
	void new_mu_and_its_chord_list_use_the_default_analyzer()
	{
		Mu mu = makeMu();
		assertThat(mu.getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.PROGRESSION_ANALYZER);
		assertThat(mu.getMasterChordList().getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.PROGRESSION_ANALYZER);
		assertAllChordsInKey(mu, PROGRESSION_ANALYZER_KEY_INDEX);
	}


	@Test
	void when_analyzer_is_set_then_chords_already_analysed_are_reanalysed()
	{
		Mu mu = makeMu();
		assertAllChordsInKey(mu, PROGRESSION_ANALYZER_KEY_INDEX);
		mu.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		assertThat(mu.getMasterChordList().getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.TONAL_PARSIMONY);
		assertAllChordsInKey(mu, TONAL_PARSIMONY_KEY_INDEX);
		mu.setChordAnalyzerType(ChordAnalyzerType.PROGRESSION_ANALYZER);
		assertAllChordsInKey(mu, PROGRESSION_ANALYZER_KEY_INDEX);
	}


	@Test
	void when_analyzer_is_set_before_chord_generator_then_new_chord_list_uses_it()
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(PROGRESSION.length);
		mu.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		mu.setChordListGenerator(new SimpleEvenChordProgression(PROGRESSION));
		assertThat(mu.getMasterChordList().getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.TONAL_PARSIMONY);
		assertAllChordsInKey(mu, TONAL_PARSIMONY_KEY_INDEX);
	}


	@Test
	void when_chord_list_is_recompiled_then_analyzer_is_kept()
	{
		Mu mu = makeMu();
		mu.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		mu.setLengthInBars(PROGRESSION.length * 2);
		assertThat(mu.getMasterChordList().getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.TONAL_PARSIMONY);
		assertAllChordsInKey(mu, TONAL_PARSIMONY_KEY_INDEX);
	}


	@Test
	void when_analyzer_is_set_on_a_child_mu_then_it_is_set_on_the_root()
	{
		Mu mu = makeMu();
		Mu child = new Mu("child");
		mu.addMu(child, 0);
		child.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		assertThat(mu.getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.TONAL_PARSIMONY);
		assertThat(child.getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.TONAL_PARSIMONY);
		assertAllChordsInKey(mu, TONAL_PARSIMONY_KEY_INDEX);
	}


	@Test
	void when_mu_is_deep_copied_then_analyzer_is_kept()
	{
		Mu mu = makeMu();
		mu.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		assertThat(mu.getDeepCopy().getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.TONAL_PARSIMONY);
	}


	@Test
	void when_chord_list_excerpt_is_made_then_it_uses_the_roots_analyzer()
	{
		Mu mu = makeMu();
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		mu.addMu(child, 0);
		mu.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		ChordList excerpt = child.getMuSpecificParentChordListExcerpt();
		assertThat(excerpt.getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.TONAL_PARSIMONY);
	}


	@Test
	void when_analyzer_is_set_to_null_then_default_is_used()
	{
		Mu mu = makeMu();
		mu.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		mu.setChordAnalyzerType(null);
		assertThat(mu.getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.DEFAULT);
		assertAllChordsInKey(mu, PROGRESSION_ANALYZER_KEY_INDEX);
	}



	private Mu makeMu()
	{
		Mu mu = new Mu("mu");
		mu.setLengthInBars(PROGRESSION.length);
		mu.setChordListGenerator(new SimpleEvenChordProgression(PROGRESSION));
		return mu;
	}


	private void assertAllChordsInKey(Mu mu, int keyIndex)
	{
		ChordList chordList = mu.getMasterChordList();
		chordList.getChord(0, 0.0);		// makes sure the analysis has run
		for (ChordEvent ce: chordList.getChordEventList())
		{
			assertThat(ce.getChord().getAssociatedChordInKeyObject().keyIndex)
				.as(ce.getChordName())
				.isEqualTo(keyIndex);
		}
	}

}
