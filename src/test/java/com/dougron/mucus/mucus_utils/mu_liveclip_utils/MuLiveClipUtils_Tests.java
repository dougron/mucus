package test.java.com.dougron.mucus.mucus_utils.mu_liveclip_utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mucus_utils.mu_liveclip_utils.MuLiveClipUtils;
import main.java.da_utils.ableton_live.ableton_live_clip.LiveClip;

class MuLiveClipUtils_Tests
{
	
	

	@Test
	void single_note_single_chord_mu_length_equals_2()
	{
		Mu mu = getSingleNoteSingleChordMu();
		assertThat(mu.getLengthInBars()).isEqualTo(2);
	}
	
	
	@Test
	void single_note_single_chord_mu_chord_is_Csharp_major()
	{
		Mu mu = getSingleNoteSingleChordMu();
		assertThat(
				mu.getChordAt(new BarsAndBeats(0, 0.0))
				.getAssociatedChordInKeyObject()
				.chordSymbol()
				).isEqualTo("C#Maj");
		assertThat(
				mu.getChordAt(new BarsAndBeats(1, 2.0))
				.getAssociatedChordInKeyObject()
				.chordSymbol()
				).isEqualTo("C#Maj");
	}
	
	
	@Test
	void single_note_single_chord_mu_time_signature_is_4_4()
	{
		Mu mu = getSingleNoteSingleChordMu();
		assertThat(mu.getTimeSignature(0).getName()).isEqualTo("4/4");
		assertThat(mu.getTimeSignature(1).getName()).isEqualTo("4/4");
	}
	
	
	@Test
	void single_note_single_chord_has_1_note()
	{
		Mu mu = getSingleNoteSingleChordMu();
		List<Mu> muList = mu.getMusWithNotes();
		assertThat(muList.size()).isEqualTo(1);
	}
	
	
	@Test
	void single_note_single_chord_note_mu_position_is_0()
	{
		Mu mu = getSingleNoteSingleChordMu();
		List<Mu> muList = mu.getMusWithNotes();
		assertThat(muList.get(0).getGlobalPositionInQuarters()).isEqualTo(0.0);
	}
	
	
	@Test
	void single_note_single_chord_note_mu_length_is_1()
	{
		Mu mu = getSingleNoteSingleChordMu();
		List<Mu> muList = mu.getMusWithNotes();
		assertThat(muList.get(0).getLengthInQuarters()).isEqualTo(1.0);
	}
	
	
	
	@Test
	void two_note_two_chord_mu_checks_out_all_the_above_in_one_test()
	{
		Mu mu = getTwoNoteTwoChordMu();
		assertThat(mu.getLengthInBars()).isEqualTo(4);
		assertThat(
				mu.getChordAt(new BarsAndBeats(0, 0.0)).getAssociatedChordInKeyObject()
				.chordSymbol()).isEqualTo("C#Maj");
		assertThat(
				mu.getChordAt(new BarsAndBeats(1, 2.0)).getAssociatedChordInKeyObject()
				.chordSymbol()).isEqualTo("C#Maj");
		assertThat(
				mu.getChordAt(new BarsAndBeats(2, 0.5)).getAssociatedChordInKeyObject()
				.chordSymbol()).isEqualTo("Bbmin");
		assertThat(
				mu.getChordAt(new BarsAndBeats(3, 2.0)).getAssociatedChordInKeyObject()
				.chordSymbol()).isEqualTo("Bbmin");
		assertThat(mu.getTimeSignature(0).getName()).isEqualTo("4/4");
		assertThat(mu.getTimeSignature(1).getName()).isEqualTo("4/4");
		assertThat(mu.getTimeSignature(2).getName()).isEqualTo("4/4");
		assertThat(mu.getTimeSignature(3).getName()).isEqualTo("4/4");
		List<Mu> muList = mu.getMusWithNotes();
		assertThat(muList.size()).isEqualTo(2);
		assertThat(muList.get(0).getGlobalPositionInQuarters()).isEqualTo(0.0);
		assertThat(muList.get(1).getGlobalPositionInQuarters()).isEqualTo(7.5);
		assertThat(muList.get(0).getLengthInQuarters()).isEqualTo(1.0);
		assertThat(muList.get(1).getLengthInQuarters()).isEqualTo(1.5);
	}
	
// privates ---------------------------------------------------------
	
	private Mu getSingleNoteSingleChordMu()
	{
		LiveClip[] clips = getSingleNoteSingleChordLiveClip();
		return MuLiveClipUtils.makeMu(clips[0], clips[1]);
	}

	
	
	private Mu getTwoNoteTwoChordMu()
	{
		LiveClip[] clips = getSingleNoteSingleChordLiveClip();
		clips[0].loopEnd = 16.0;
		clips[0].addNote(53, 7.5, 1.5, 64, 0);
		clips[1].loopEnd = 16.0;
		clips[1].addNote(49, 8.0, 8.0, 64, 0);
		clips[1].addNote(46, 8.0, 8.0, 64, 0);
		clips[1].addNote(41, 8.0, 8.0, 64, 0);
		return MuLiveClipUtils.makeMu(clips[0], clips[1]);
	}
	
	
	
	private LiveClip[] getSingleNoteSingleChordLiveClip()
	{
		LiveClip melodyClip = new LiveClip();
		melodyClip.loopEnd = 8.0;
		melodyClip.addNote(53, 0.0, 1.0, 64, 0);
		LiveClip chordClip = new LiveClip();
		chordClip.loopEnd = 8.0;
		chordClip.addNote(49, 0.0, 8.0, 64, 0);
		chordClip.addNote(44, 0.0, 8.0, 64, 0);
		chordClip.addNote(41, 0.0, 8.0, 64, 0);
		return new LiveClip[] {melodyClip, chordClip};
	}

}
