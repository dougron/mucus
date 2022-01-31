package main.java.com.dougron.mucus.algorithms.random_melody_generator;

import DataObjects.contour.FourPointContour;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

/*
 * this is the expanded set of options from April meeting with supers to try to throw the bot discriminator behaviour
 * into sharper relief
 */

public class RMG_002 extends RandomMelodyGenerator
{

	private static RandomMelodyGenerator instance;
	
//	private int[] phraseLengthOptions = new int[] {4};
	
	


	private RMG_002 ()
	{
		initializeOptions();
	}


	
	public static RandomMelodyGenerator getInstance()
	{
		if (instance == null) instance = new RMG_002();
		return instance;
	}
	
	
	
	public void initializeOptions ()
	{
		super.setPhraseLengthOptions(new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
		super.setTimeSignatureOptions(new TimeSignature[] 
				{
						TimeSignature.FIVE_EIGHT_32,
						TimeSignature.SIX_EIGHT,
						TimeSignature.SEVEN_EIGHT_322, 
						TimeSignature.SEVEN_EIGHT_223,
						TimeSignature.NINE_EIGHT_333,
						TimeSignature.TEN_EIGHT_3322,
						TimeSignature.ELEVEN_EIGHT_3332,
						TimeSignature.TWELVE_EIGHT,
						
						TimeSignature.TWO_FOUR,
						TimeSignature.THREE_FOUR,
						TimeSignature.FOUR_FOUR, 
						TimeSignature.TWO_TWO,
						TimeSignature.FIVE_FOUR,
						TimeSignature.SIX_FOUR_33,
						TimeSignature.SIX_FOUR_222,
						TimeSignature.SEVEN_FOUR_322,
						TimeSignature.EIGHT_FOUR,
						TimeSignature.THIRTEEN_FOUR_CLAPHAM,
						
						
				});
		super.setMinimumPhraseStartPercent(-0.2);
		super.setMaximumPhraseStartPercent(0.15);
		super.setMinimumPhraseEndPercent(0.45);
		super.setMaximumPhraseEndPercent(0.76);
		super.setStructureToneSpacingOptions(new double[] {0.5, 1.0, 2.0}); //{1.0, 1.0, 1.0, 1.0, 0.5};	// in FloatBars
		super.setMaximumTempo(190);
		super.setMinimumTempo(65);
		super.setXmlKeyOptions(new int[] {-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6});
		super.setMinimumStartNote(45);
		super.setMaximumStartNote(75);
		super.setStructureToneContourOptions
		(	new FourPointContour[] 
				{
					new FourPointContour(FourPointContour.DOWN),
					new FourPointContour(FourPointContour.UP),
					new FourPointContour(FourPointContour.DOWNUP),
					new FourPointContour(FourPointContour.UPDOWN),
					new FourPointContour(FourPointContour.STRAIGHT),
					new FourPointContour(0.5, 1.0, -1.0),
					new FourPointContour(0.5, -1.0, 1.0),
					new FourPointContour(0.25, 1.0, 0.0),
					new FourPointContour(0.25, -1.0, 0.0),
					new FourPointContour(0.75, 1.0, 0.0),
					new FourPointContour(0.75, -1.0, 0.0),
				}
		);
		super.setStructureToneContourRangeMinimum(2);
		super.setStructureToneContourRangeMaximum(18);
//		super.setDEFAULT_STRUCTURE_TONE_LENGTH(0.5);
		super.setClearanceOfEmbellishmentFromPreviousStructureToneOptions(new double[] {1.0, 0.5, 1.5, 2.0});
		super.setEmbellishmentCountOptions(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
		super.setEmbellishmentRepetitionPatterns
		(
				new int[][]
				{
					new int[] {0},
					new int[] {0, 1},
					new int[] {0, 1, 2},
					new int[] {0, 1, 0, 2},
					new int[] {0, 1, 2, 3},
					new int[] {0, 1, 0, 2, 3},
					new int[] {0, 1, 0, 1, 2},
					new int[] {0, 1, 0, 2, 1},
					new int[] {0, 0, 1},
					new int[] {0, 0, 1, 1},
					new int[] {0, 0, 1, 2},
				}
		);
		super.setEmbellishmentPitchOptions
		(
				new MuTag[][]
				{
					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.ACCENTED, MuTag.STEP_JUMP},
					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.ACCENTED, MuTag.JUMP_STEP},
					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.UNACCENTED, MuTag.STEP_JUMP},
					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.UNACCENTED, MuTag.JUMP_STEP},
					new MuTag[] {MuTag.IS_ANTICIPATION},
					new MuTag[] {MuTag.IS_APPROACH_TONE, MuTag.UNACCENTED},
					new MuTag[] {MuTag.IS_APPROACH_TONE, MuTag.ACCENTED}
				}
		);
		super.setEmbellishmentRhythmOptions
		(
				new RelativeRhythmicPosition[]
				{
					new RelativeRhythmicPosition(0, 0, 0, -1),	
					new RelativeRhythmicPosition(0, 0, -1, 0),	
					new RelativeRhythmicPosition(0, 0, -1, -1),	
					new RelativeRhythmicPosition(0, 0, -2, 0),	
				}
		);

//		private double DEFAULT_CHORD_RHYTHM_IN_FLOATBARS = 1.0;
		super.setChordSuffix(new String[] {"", "m", "m", "", "", "m"});
	}
	
	



}
