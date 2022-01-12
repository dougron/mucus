package main.java.com.dougron.mucus.algorithms.mu_chord_tone_and_embellishment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import DataObjects.combo_variables.IntAndString;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagBundle;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagNamedParameter;
import time_signature_utilities.time_signature.TimeSignature;

public class ChordToneAndEmbellishmentTagger
{
	

	
	static Map<MuTag, Annotation> muTagAnnotations = ImmutableMap.<MuTag, Annotation>builder()
			.put(MuTag.IS_CHORD_TONE, new SimpleAnnotation(MuTag.IS_CHORD_TONE, "ct"))
			.put(MuTag.IS_EXTENDED_CHORD_TONE, new SimpleAnnotation(MuTag.IS_EXTENDED_CHORD_TONE, "ect"))
			.put(MuTag.IS_SYNCOPATION, new AnnotationWithNamedParameter(MuTag.IS_SYNCOPATION, "sync", MuTagNamedParameter.SYNCOPATED_BEAT_GLOBAL_POSITION))
			.put(MuTag.IS_SCALE_TONE, new SimpleAnnotation(MuTag.IS_SCALE_TONE, "st"))
			.put(MuTag.IS_NON_SCALE_TONE, new SimpleAnnotation(MuTag.IS_NON_SCALE_TONE, "nst"))
			.put(MuTag.IS_LOWER_CHROMATIC_APPROACH_TONE, new SimpleAnnotation(MuTag.IS_LOWER_CHROMATIC_APPROACH_TONE, "lcat"))
			.put(MuTag.IS_UPPER_CHROMATIC_APPROACH_TONE, new SimpleAnnotation(MuTag.IS_UPPER_CHROMATIC_APPROACH_TONE, "ucat"))
			.put(MuTag.IS_LOWER_DIATONIC_APPROACH_TONE, new SimpleAnnotation(MuTag.IS_LOWER_DIATONIC_APPROACH_TONE, "ldat"))
			.put(MuTag.IS_UPPER_DIATONIC_APPROACH_TONE, new SimpleAnnotation(MuTag.IS_UPPER_DIATONIC_APPROACH_TONE, "udat"))
			.put(MuTag.IS_PASSING_TONE, new SimpleAnnotation(MuTag.IS_PASSING_TONE, "pt"))
			.put(MuTag.IS_UPPER_NEIGHBOUR_TONE, new SimpleAnnotation(MuTag.IS_UPPER_NEIGHBOUR_TONE, "unt"))
			.put(MuTag.IS_LOWER_NEIGHBOUR_TONE, new SimpleAnnotation(MuTag.IS_LOWER_NEIGHBOUR_TONE, "lnt"))
			.put(MuTag.IS_ENCLOSURE_1, new SimpleAnnotation(MuTag.IS_ENCLOSURE_1, "enc1"))
			.put(MuTag.IS_ENCLOSURE_2, new SimpleAnnotation(MuTag.IS_ENCLOSURE_2, "enc2"))
			.put(MuTag.IS_ESCAPE_TONE, new SimpleAnnotation(MuTag.IS_ESCAPE_TONE, "esc"))
			.put(MuTag.IS_ANTICIPATION, new SimpleAnnotation(MuTag.IS_ANTICIPATION, "ant"))
			.put(MuTag.IS_APPROACH_TONE, new SimpleAnnotation(MuTag.IS_APPROACH_TONE, "appr"))
			.put(MuTag.IS_OCTAVE_ANTICIPATION, new SimpleAnnotation(MuTag.IS_OCTAVE_ANTICIPATION, "oct_ant"))
			.put(MuTag.PHRASE_START, new SimpleAnnotation(MuTag.PHRASE_START, "ph_start"))
			.put(MuTag.PHRASE_END, new SimpleAnnotation(MuTag.PHRASE_END, "ph_end"))
			.put(MuTag.IS_SHORT_NOTE, new SimpleAnnotation(MuTag.IS_SHORT_NOTE, "short"))
			.put(MuTag.IS_STRUCTURE_TONE, new SimpleAnnotation(MuTag.IS_STRUCTURE_TONE, "struct"))
			.build();
			
  	

	public static void addTags(Mu aMu)
	{
		aMu.makePreviousNextMusWithNotes();
		
		// rhythm stuff
		tagSyncopations(aMu);
		
		// harmonic stuff
		tagChordTones(aMu);
		tagExtendedChordTones(aMu);
		tagScaleTones(aMu);
		tagNonScaleTones(aMu);		
		
		// embellishment stuff
		tagEmbellishmentPatterns(aMu);
	}



	public static void tagEmbellishmentPatterns (Mu aMu)
	{
		tagLowerChromaticApproachNote(aMu);
		tagUpperChromaticApproachNote(aMu);
		tagDiatonicApproachNotes(aMu);
		tagPassingTones(aMu);
		tagNeighbourTones(aMu);
		tagEnclosures(aMu);
		tagEscapeTones(aMu);
		tagAnticipations(aMu);
	}
	
	
	
	public static void tagEmbellishmentPatternsNotContingentOnHarmony (Mu aMu)
	{
		tagLowerChromaticApproachNote(aMu);
		tagUpperChromaticApproachNote(aMu);
//		tagDiatonicApproachNotes(aMu);
		tagPassingTones(aMu);
		tagNeighbourTones(aMu);
		tagEnclosures(aMu);
		tagEscapeTones(aMu);
		tagAnticipations(aMu);
	}
	
	
	
	// only converts selected MuTags
	public static void makeTagsIntoXMLAnnotations(Mu aMu, MuTag[] aMuTags)
	{
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			for (MuTag muTag: aMuTags)
			{
				if (mu.hasTag(muTag) && muTagAnnotations.containsKey(muTag))
				{
					Annotation annotation = muTagAnnotations.get(muTag);
					annotation.addAnnotation(mu);
				}
			}
		}
	}
	
	
	
	public static void makeTagsIntoXMLAnnotations(Mu aMu)
	{	
		for (Annotation annotation: muTagAnnotations.values())
		{
			annotation.addAnnotation(aMu);
		}
	}
	
	
	
	// tactus level syncopation detector
	public static void tagSyncopations(Mu aMu)
	{
		MuTagBundle mtb;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			double nextTactusGlobalPosition = getNextTactusGlobalPosition(mu);
			if (mu.getStrengthOfGlobalPositionInQuarters(mu.getGlobalPositionInQuarters()) < mu.getStrengthOfGlobalPositionInQuarters(nextTactusGlobalPosition))
			{
				// not a syncopation
			}
			else
			{
				if (!hasNoteOnNextTactusOrCloserOnTheOtherSide(mu, nextTactusGlobalPosition))
				{
					mtb = mu.addTag(MuTag.IS_SYNCOPATION);
					mtb.addNamedParameter(MuTagNamedParameter.SYNCOPATED_BEAT_GLOBAL_POSITION, nextTactusGlobalPosition);
				}
			}		
		}
	}



	private static boolean hasNoteOnNextTactusOrCloserOnTheOtherSide(Mu aMu, double nextTactusGlobalPositionInQuarters)
	{
		if (aMu.getNextMu() == null)
		{
			return false;
		}
		else
		{
			double nextMuGlobalPositionInQuarters = aMu.getNextMu().getGlobalPositionInQuarters();
			double globalPositionInQuarters = aMu.getGlobalPositionInQuarters();
			double distanceToTactus = Mu.round(nextTactusGlobalPositionInQuarters - globalPositionInQuarters);
			if (Mu.round(nextMuGlobalPositionInQuarters - nextTactusGlobalPositionInQuarters) >= distanceToTactus)
			{
				return false;
			}
			else
			{
				return true;
			}
		}		
	}

	

	private static double getNextTactusGlobalPosition(Mu aMu)
	{
		BarsAndBeats globalPositionInBarsAndBeats = aMu.getGlobalPositionInBarsAndBeats();
		TimeSignature ts = aMu.getTimeSignature(globalPositionInBarsAndBeats.getBarPosition());
		double tactusPosition = 0.0;
		for (Double tactus: getTactusWithBeginningOfNextBarIncluded(ts))
		{
			if (tactus > globalPositionInBarsAndBeats.getOffsetInQuarters())
			{
				tactusPosition = tactus;
				break;
			}
		}
		return aMu.getGlobalPositionInQuarters(globalPositionInBarsAndBeats.getBarPosition()) + tactusPosition;
	}
	
	
	
	private static ArrayList<Double> getTactusWithBeginningOfNextBarIncluded(TimeSignature aTimeSignature)
	{
		Double[] tactii = aTimeSignature.getTactus();
		ArrayList<Double> newTactii = new ArrayList<Double>();
		double pos = 0.0;
		newTactii.add(pos);
		for (Double d: tactii)
		{
			pos += d;
			newTactii.add(pos);
		}
		return newTactii;
	}

	
	
	private static void tagAnticipations(Mu aMu)
	{
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			if (mu.getNextMu() != null 
					&& mu.getStrengthOfPositionInBarConsideringSyncopation() > mu.getNextMu().getStrengthOfPositionInBarConsideringSyncopation())
			{
				if (mu.getTopPitch() == mu.getNextMu().getTopPitch())
				{
					mu.addTag(MuTag.IS_ANTICIPATION);
				}
				else if (Math.abs(mu.getTopPitch() - mu.getNextMu().getTopPitch()) == 12)
				{
					mu.addTag(MuTag.IS_OCTAVE_ANTICIPATION);
				}					
			}
		}		
	}
	
	
	
	private static void tagEscapeTones(Mu aMu)
	{
		IntAndString previousSemitoneInterval;
		IntAndString nextSemitoneInterval;
		MuTagBundle mtb;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			if (mu.getNextMu() != null && mu.getPreviousMu() != null)
			{
				previousSemitoneInterval = mu.getSemitoneInterval(mu.getPreviousMu().getTopPitch());
				nextSemitoneInterval = mu.getSemitoneInterval(mu.getNextMu().getTopPitch());
				if (
						(
								(previousSemitoneInterval.i == 1 || previousSemitoneInterval.i == 2)
								&& (nextSemitoneInterval.i > 2)
						)
						||
						(
								(previousSemitoneInterval.i == -1 || previousSemitoneInterval.i == -2)
								&& (nextSemitoneInterval.i < -2)
						)
						||
						(
								(nextSemitoneInterval.i == -1 || nextSemitoneInterval.i == -2)
								&& (previousSemitoneInterval.i < -2)
						)
						||
						(
								(nextSemitoneInterval.i == 1 || nextSemitoneInterval.i == 2)
								&& (previousSemitoneInterval.i > 2)
						)
					)
				{
					mtb = mu.addTag(MuTag.IS_ESCAPE_TONE);
					addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
				}
			}
		}
	}
	
	
	
	private static void tagEnclosures(Mu aMu)
	{
		IntAndString firstSemitoneInterval;
		IntAndString secondSemitoneInterval;
		MuTagBundle mtb1;
		MuTagBundle mtb2;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			if (mu.getPreviousMu() != null && mu.getPreviousMu().getPreviousMu() != null)
			{
				Mu firstMu = mu.getPreviousMu().getPreviousMu();
				Mu secondMu = mu.getPreviousMu();
				firstSemitoneInterval = mu.getSemitoneInterval(firstMu.getTopPitch());
				secondSemitoneInterval = mu.getSemitoneInterval(secondMu.getTopPitch());
				if (
						(firstSemitoneInterval.i == 1 || firstSemitoneInterval.i == 2)
						&& (secondSemitoneInterval.i == -1 || secondSemitoneInterval.i == -2)
					)
				{
					mtb1 = firstMu.addTag(MuTag.IS_ENCLOSURE_1);
					mtb2 = secondMu.addTag(MuTag.IS_ENCLOSURE_2);
					addAccentRelationToNextNote(mtb1, firstMu, mu);
					addAccentRelationToNextNote(mtb2, firstMu, mu);		// note accent relationship is with the first note of the enclosure, although it is captured in both
				}
				if (
						(firstSemitoneInterval.i == -1 || firstSemitoneInterval.i == -2)
						&& (secondSemitoneInterval.i == 1 || secondSemitoneInterval.i == 2)
					)
				{
					mtb1 = firstMu.addTag(MuTag.IS_ENCLOSURE_1);
					mtb2 = secondMu.addTag(MuTag.IS_ENCLOSURE_2);
					addAccentRelationToNextNote(mtb1, firstMu, mu);
					addAccentRelationToNextNote(mtb2, firstMu, mu);		// note accent relationship is with the first note of the enclosure, although it is captured in both
				}
			}
		}		
	}
	
	
	
	private static void tagNeighbourTones(Mu aMu)
	{
		IntAndString nextIntervalSemi;
		MuTagBundle mtb;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{		
			if (mu.getNextMu() != null && mu.getPreviousMu() != null)
			{
				nextIntervalSemi = mu.getSemitoneInterval(mu.getNextMu().getTopPitch());
				if (mu.getPreviousMu().getTopPitch() == mu.getNextMu().getTopPitch())
				{
					if (nextIntervalSemi.i == 1 || nextIntervalSemi.i == 2)
					{
						mtb = mu.addTag(MuTag.IS_LOWER_NEIGHBOUR_TONE);
						addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
					}
					if (nextIntervalSemi.i == -1 || nextIntervalSemi.i == -2)
					{
						mtb = mu.addTag(MuTag.IS_UPPER_NEIGHBOUR_TONE);
						addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
					}
				}
			}
		}
	}
	
	
	
	private static void tagPassingTones(Mu aMu)
	{
		IntAndString nextIntervalSemi;
		IntAndString previousIntervalSemi;
		MuTagBundle mtb;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{			
			if (mu.getNextMu() != null && mu.getPreviousMu() != null)
			{
				nextIntervalSemi = mu.getSemitoneInterval(mu.getNextMu().getTopPitch());
				previousIntervalSemi = mu.getSemitoneInterval(mu.getPreviousMu().getTopPitch());
				if ((nextIntervalSemi.i == -1 || nextIntervalSemi.i == -2) && (previousIntervalSemi.i == 1 || previousIntervalSemi.i == 2))
				{
					mtb = mu.addTag(MuTag.IS_PASSING_TONE);
					addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
				}
				if ((nextIntervalSemi.i == 1 || nextIntervalSemi.i == 2) && (previousIntervalSemi.i == -1 || previousIntervalSemi.i == -2))
				{
					mtb = mu.addTag(MuTag.IS_PASSING_TONE);
					addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
				}
			}
		}		
	}
	
	
	
	private static void tagDiatonicApproachNotes(Mu aMu)
	{
		MuTagBundle mtb;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			IntAndString ias;
			if (mu.getNextMu() != null && mu.isDiatonicNote(mu.getTopPitch()))
			{
				ias = mu.getDiatonicInterval(mu.getNextMu().getTopPitch());
				if (ias.i == -1)
				{
					mtb = mu.addTag(MuTag.IS_UPPER_DIATONIC_APPROACH_TONE);
					addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
				}
				if (ias.i == 1)
				{
					mtb = mu.addTag(MuTag.IS_LOWER_DIATONIC_APPROACH_TONE);
					addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
				}
			}
		}		
	}
	
	
	
	private static void tagUpperChromaticApproachNote(Mu aMu)
	{
		MuTagBundle mtb;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			IntAndString ias;
			if (mu.getNextMu() != null)
			{
				ias = mu.getSemitoneInterval(mu.getNextMu().getTopPitch());
				if (ias.i == -1)
				{
					mtb = mu.addTag(MuTag.IS_UPPER_CHROMATIC_APPROACH_TONE);
					addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
				}
			}	
		}			
	}
	
	
	
	private static void tagLowerChromaticApproachNote(Mu aMu)
	{
		MuTagBundle mtb;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			IntAndString ias;
			if (mu.getNextMu() != null)
			{
				ias = mu.getSemitoneInterval(mu.getNextMu().getTopPitch());
				if (ias.i == 1)
				{
					mtb = mu.addTag(MuTag.IS_LOWER_CHROMATIC_APPROACH_TONE);
					addAccentRelationToNextNote(mtb, mu, mu.getNextMu());
				}
			}	
		}			
	}
	
	
	
	private static void addAccentRelationToNextNote(MuTagBundle mtb, Mu aMu, Mu relatedMu)
	{
		int muStrength = 0;
		if (!aMu.hasTag(MuTag.IS_SYNCOPATION))
		{
			muStrength = aMu.getStrengthOfPositionInBar();
		}
		int relatedMuStrength = 0;
		if (!relatedMu.hasTag(MuTag.IS_SYNCOPATION))
		{
			relatedMuStrength = relatedMu.getStrengthOfPositionInBar();
		}
		if (relatedMuStrength < muStrength)
		{
			mtb.addMuTag(MuTag.UNACCENTED);
		}
		else
		{
			mtb.addMuTag(MuTag.ACCENTED);
		}
	}
	
	
	
	private static void tagNonScaleTones(Mu aMu)
	{
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			if (mu != aMu)
			{
				if (mu.hasTag(MuTag.IS_SYNCOPATION))
				{
					if (mu.isTupletPrintContainer()) tagNonScaleTones(mu);
					Chord chord = getChordFromBeginningOfNextBar(mu);
					if (chord.isNonScaleTone(mu.getTopPitch()))
					{
						mu.addTag(MuTag.IS_NON_SCALE_TONE);
					}
				}
				else if (mu.isNonScaleTone())
				{
					mu.addTag(MuTag.IS_NON_SCALE_TONE);
				}
			}					
		}			
	}
	
	
	
	private static void tagScaleTones(Mu aMu)
	{
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			if (mu != aMu)
			{
				if (mu.hasTag(MuTag.IS_SYNCOPATION))
				{
					if (mu.isTupletPrintContainer()) tagScaleTones(mu);
					Chord chord = getPrevailingChordConsideringSyncopationTag(mu);
					if (chord.isScaleTone(mu.getTopPitch()))
					{
						mu.addTag(MuTag.IS_SCALE_TONE);
					}
				}
				else if (mu.isScaleTone())
				{
					mu.addTag(MuTag.IS_SCALE_TONE);
				}
			}					
		}			
	}

	
	
	
	private static Chord getPrevailingChordConsideringSyncopationTag(Mu aMu)
	{
		ArrayList<MuTagBundle> mtbs = aMu.getMuTagBundleContaining(MuTag.IS_SYNCOPATION);
		if (mtbs.size() > 0)
		{
			double prevailingChordPosition = (double)mtbs.get(0).getNamedParameter(MuTagNamedParameter.SYNCOPATED_BEAT_GLOBAL_POSITION);
			double parentEndInQuarters = aMu.getEndOfTopLevelMuInQuarters();
			if (parentEndInQuarters <= prevailingChordPosition)
			{
				return aMu.getPrevailingChord();
			}
			else
			{
				BarsAndBeats globalBarsAndBeatsPosition = aMu.getGlobalPositionInBarsAndBeats(prevailingChordPosition);
				return aMu.getChordAtGlobalPosition(globalBarsAndBeatsPosition);
			}			
		}
		else
		{
			return aMu.getPrevailingChord();
		}
	}



	private static void tagExtendedChordTones(Mu aMu)
	{
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			if (mu != aMu)
			{
				if (mu.isTupletPrintContainer())
					tagExtendedChordTones(mu);
				if (mu.hasTag(MuTag.IS_SYNCOPATION))
				{
					Chord chord = getChordFromBeginningOfNextBar(mu);
					if (chord.isExtendedChordTone(mu.getTopPitch()))
					{
						mu.addTag(MuTag.IS_EXTENDED_CHORD_TONE);
					}
				} 
				else if (mu.isExtendedChordTone())
				{
					mu.addTag(MuTag.IS_EXTENDED_CHORD_TONE);
				} 
			}			
		}			
	}
	
	
	
	private static void tagChordTones(Mu aMu)
	{
		List<Mu> musWithNotes = aMu.getMusWithNotesIgnoringTupletHolders();
		for (Mu mu: musWithNotes)
		{
			if (mu != aMu)
			{
				if (mu.isTupletPrintContainer())
					tagChordTones(mu);
				if (mu.hasTag(MuTag.IS_SYNCOPATION))
				{
					Chord chord = getChordFromBeginningOfNextBar(mu);
					if (chord.isChordTone(mu.getTopPitch()))
					{
						mu.addTag(MuTag.IS_CHORD_TONE);
					}
				} else if (mu.isChordTone())
				{
					mu.addTag(MuTag.IS_CHORD_TONE);
				} 
			}			
		}		
	}
	
	
	
	private static Chord getChordFromBeginningOfNextBar(Mu mu)
	{
		BarsAndBeats globalPositionInBarsAndBeats = mu.getGlobalPositionInBarsAndBeats();
		Chord chord = mu.getChordAtGlobalPosition(new BarsAndBeats(globalPositionInBarsAndBeats.getBarPosition() + 1, 0.0));
		if (chord == null)	// syncopated last note of the piece as in Anthropology looks for the chord from the next bar
							// may in future be catered for by syncopation at a tactus resolution rather than bar resolution as it
							// currently is, but this is a quick and nasty workaround.
		{
			chord = mu.getChordAtGlobalPosition(new BarsAndBeats(globalPositionInBarsAndBeats.getBarPosition(), 0.0));
		}
		return chord;
	}

}
