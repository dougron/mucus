package main.java.com.dougron.mucus.algorithms.random_melody_generator;

import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.da_utils.four_point_contour.FourPointContour;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

/*
 * this is the original set of options
 */

public class RMG_001 extends RandomMelodyGenerator
{

	private static RandomMelodyGenerator instance;
	


	private RMG_001 ()
	{
		initializeOptions();
	}


	
	public static RandomMelodyGenerator getInstance()
	{
		if (instance == null) instance = new RMG_001();
		return instance;
	}
	
	
	
	public void initializeOptions ()
	{
		super.setPhraseLengthOptions(new int[] {4, 6, 8, 10});
		super.setTimeSignatureOptions(new TimeSignature[] 
				{
						TimeSignature.FOUR_FOUR, 
						TimeSignature.THREE_FOUR, 
						TimeSignature.SEVEN_EIGHT_322, 
						TimeSignature.FIVE_EIGHT_32,
						TimeSignature.FIVE_FOUR,
						TimeSignature.SIX_EIGHT
				});
		super.setMinimumPhraseStartPercent(-0.2);
		super.setMaximumPhraseStartPercent(0.0);
		super.setMinimumPhraseEndPercent(0.45);
		super.setMaximumPhraseEndPercent(0.76);
		super.setStructureToneSpacingOptions(new double[] {1.0}); //{1.0, 1.0, 1.0, 1.0, 0.5};	// in FloatBars
		super.setMaximumTempo(145);
		super.setMinimumTempo(65);
		super.setXmlKeyOptions(new int[] {-6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6});
		super.setMinimumStartNote(50);
		super.setMaximumStartNote(70);
		super.setStructureToneContourOptions
		(	new FourPointContour[] 
				{
					new FourPointContour(FourPointContour.DOWN),
					new FourPointContour(FourPointContour.UP),
					new FourPointContour(FourPointContour.DOWNUP),
					new FourPointContour(FourPointContour.UPDOWN),
				}
		);
		super.setStructureToneContourRangeMinimum(5);
		super.setStructureToneContourRangeMaximum(14);
//		super.setDEFAULT_STRUCTURE_TONE_LENGTH(0.5);
		super.setClearanceOfEmbellishmentFromPreviousStructureToneOptions(new double[] {0.5, 1.0});
		super.setEmbellishmentCountOptions(new int[] {1, 2, 3, 4});
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
					//					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.ACCENTED, MuTag.STEP_JUMP},
					//					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.ACCENTED, MuTag.JUMP_STEP},
					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.UNACCENTED, MuTag.STEP_JUMP},
					new MuTag[] {MuTag.IS_ESCAPE_TONE, MuTag.UNACCENTED, MuTag.JUMP_STEP},
					new MuTag[] {MuTag.IS_ANTICIPATION},
//					new MuTag[] {MuTag.IS_APPROACH_TONE, MuTag.UNACCENTED},
//					new MuTag[] {MuTag.IS_APPROACH_TONE, MuTag.ACCENTED},
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
