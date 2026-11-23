package test.java.com.dougron.mucus.mu_framework.mu_xml_utility;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordAnalyzerType;
import main.java.com.dougron.mucus.mu_framework.chord_list.FloatBarChordProgression;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagBundle;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagNamedParameter;
import main.java.com.dougron.mucus.mu_framework.mu_xml_utility.MuXMLUtility;

class MuXmlUtility_Tests
{

	@Test
	void test()
	{
		Mu mu = getMu();
		String xmlString = MuXMLUtility.getMuAsXmlString(mu);
//		System.out.println(xmlString);
		assertThat(xmlString.length()).isGreaterThan(0);
	}
	
	
	@Test
	void mu_encoded_as_string_is_same_when_decoded_from_string()
	{
		Mu mu = getMu();
		String xmlString = MuXMLUtility.getMuAsXmlString(mu);
		Mu decodedMu = MuXMLUtility.getMuFromXmlString(xmlString);
		assertThat(mu.toString()).isEqualTo(decodedMu.toString());
//		System.out.println(mu.toString());
//		System.out.println(decodedMu.toString());
	}
	
	
	@Test
	void a_tag_bundles_named_parameters_are_kept_and_a_mu_reference_left_out()
	{
		Mu mu = getMu();
		Mu child = mu.getMus().get(0);
		MuTagBundle bundle = child.addTag(new MuTag[] {MuTag.IS_STRUCTURE_TONE, MuTag.IS_SYNCOPATION});
		bundle.addNamedParameter(MuTagNamedParameter.SYNCOPATED_BEAT_GLOBAL_POSITION, 4.0);
		bundle.addNamedParameter(MuTagNamedParameter.SYNCOPATED_OFFSET_IN_QUARTERS, -0.5);
		bundle.addNamedParameter(MuTagNamedParameter.STRENGTH_OF_SYNCOPATED_BEAT_POSITION, 3);
		bundle.addNamedParameter(MuTagNamedParameter.ORIGINAL_MU, mu);
		Mu decoded = MuXMLUtility.getMuFromXmlString(MuXMLUtility.getMuAsXmlString(mu));
		MuTagBundle back = decoded.getMus().get(0).getMuTagBundleContaining(MuTag.IS_SYNCOPATION).get(0);
		assertThat(back.containsMuTag(MuTag.IS_STRUCTURE_TONE)).isTrue();
		assertThat(back.getNamedParameter(MuTagNamedParameter.SYNCOPATED_BEAT_GLOBAL_POSITION)).isEqualTo(4.0);
		assertThat(back.getNamedParameter(MuTagNamedParameter.SYNCOPATED_OFFSET_IN_QUARTERS)).isEqualTo(-0.5);
		assertThat(back.getNamedParameter(MuTagNamedParameter.STRENGTH_OF_SYNCOPATED_BEAT_POSITION)).isEqualTo(3);
		assertThat(back.getNamedParameter(MuTagNamedParameter.ORIGINAL_MU)).isNull();
	}
	
	
	@Test
	void the_roots_chord_analyzer_type_is_kept()
	{
		Mu mu = getMu();
		mu.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		Mu decoded = MuXMLUtility.getMuFromXmlString(MuXMLUtility.getMuAsXmlString(mu));
		assertThat(decoded.getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.TONAL_PARSIMONY);
		assertThat(MuXMLUtility.getMuFromXmlString(MuXMLUtility.getMuAsXmlString(getMu())).getChordAnalyzerType()).isEqualTo(ChordAnalyzerType.DEFAULT);
	}
	
	
	@Test
	void chords_in_a_child_are_analysed_with_the_roots_analyzer_when_loaded()
	{
		Mu root = new Mu("root");
		root.setLengthInBars(4);
		Mu child = new Mu("child");
		child.setLengthInBars(4);
		child.setChordListGenerator(new FloatBarChordProgression(4.0, new Object[] {0.0, "Dm", 2.0, "F", 3.0, "Am"}));
		root.addMu(child, 0);
		root.setChordAnalyzerType(ChordAnalyzerType.TONAL_PARSIMONY);
		String before = root.getChordList().chordAnalysisToString();
		Mu decoded = MuXMLUtility.getMuFromXmlString(MuXMLUtility.getMuAsXmlString(root));
		assertThat(decoded.getChordList().getChordEventList().get(0).getChord().getAssociatedChordInKeyObject().toString())
				.isEqualTo(root.getChordList().getChordEventList().get(0).getChord().getAssociatedChordInKeyObject().toString());
		assertThat(decoded.getChordList().chordAnalysisToString()).isEqualTo(before);
	}
	
	
	private Mu getMu()
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(2);
		Mu child = new Mu("child");
		child.setLengthInQuarters(2.5);
		child.addMuNote(64, 48);
		mu.addMu(child, BarsAndBeats.at(0, 3.0));
		return mu;
	}
	
	

}
