package main.java.com.dougron.mucus.algorithms.generic_generator;

import java.util.List;
import java.util.Map;

import StaticChordScaleDictionary.ChordToneName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
//import main.java.com.dougron.lorez2021.bass_part_generator.PitchType;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import time_signature_utilities.time_signature.TimeSignature;


//main.java.com.dougron.lorez2021.bass_part_generator.Builder
@Builder
@ToString
public class ParameterObject
{

//	@Builder.Default	@Getter	@Setter	int[] phraseLengthOptions;
	@Builder.Default	@Getter	@Setter	int phraseLengthInBars = 1;
//	@Builder.Default	@Getter	@Setter	TimeSignature[] timeSignatureOptions;
	@Singular			@Getter	@Setter	List<TimeSignature> timeSignatures;
	@Builder.Default	@Getter	@Setter	double tempo = 90.0;
	@Builder.Default	@Getter	@Setter	Object[] chordFactoryChords = new Object[] {"SingleChordGenerator", "C"};
	
	
	
	// from LorezDrumGenerator
	@Builder.Default	@Getter	@Setter	private int hatDrive = 1;	
	@Builder.Default 	@Getter	@Setter private int hatSync = 1;	
	@Builder.Default 	@Getter	@Setter private int kikSnrDrive = 0;	
	@Builder.Default 	@Getter	@Setter private int kikSnrSync = 1;	
	@Builder.Default 	@Getter	@Setter	private int drumAccentVelocity = 64;	
	@Builder.Default 	@Getter	@Setter private int drumNonAccentVelocity = 48;
	
	
	// for Kik StructureToneEmbellisher
	@Builder.Default 	@Getter	@Setter private int[] kikEmbellishmentRepetitionPattern = new int[] {0};	
	@Singular			@Getter	@Setter	private Map<Integer, List<MuGenerator>> kikEmbellishments;
	@Builder.Default	@Getter	@Setter private double kikClearanceOfEmbellishmentFromPreviousStructureTone = 0.5;
	
	
	// from LorezBassGenerator
	@Builder.Default	@Getter	@Setter	private DurationType[] bassDurationByLoopingPattern = new DurationType[] {DurationType.STACCATO};	
	@Builder.Default	@Getter	@Setter	private DurationModel bassDurationStrategy = DurationModel.LOOPING_DURATION_TYPES;	
	@Builder.Default	@Getter	@Setter	private PitchType[] bassPitchByLoopingPitchTypes = new PitchType[] {new PitchType(ChordToneName.ROOT, 0)};	
	@Builder.Default	@Getter	@Setter	private DurationModel bassPitchStrategy = DurationModel.LOOPING_DURATION_TYPES;
	@Builder.Default	@Getter	@Setter	private int bassAccentVelocity = 64;
	@Builder.Default	@Getter	@Setter	private int bassNonAccentVelocity = 48;
	@Builder.Default	@Getter	@Setter	private int bassCentrePitch = 0;	// 0 = 'off' value and will not be set
	@Builder.Default 	@Getter	@Setter private int[] bassEmbellishmentRepetitionPattern = new int[] {0};	
	@Singular			@Getter	@Setter	private Map<Integer, List<MuGenerator>> bassEmbellishments;
	@Builder.Default	@Getter	@Setter private double bassClearanceOfEmbellishmentFromPreviousStructureTone = 0.5;
	
	
	/* 
	 * when the StructureToneEmbellisher is used, instrument specific values must be copied to the below generic variables
	 * no defaults set so that first time at least the program will fail if these values have not been set
	 * this would apply to any future processes that might need to use these general parameters
	 */
	@Setter	 	@Getter int accentVelocity;
	@Setter	 	@Getter  int nonAccentVelocity;
	// each index in embellishmentRepetitionPattern must have a corresponding key in embellishments
	@Setter	 	@Getter private int[] embellishmentRepetitionPattern;	
	@Setter		@Getter	private Map<Integer, List<MuGenerator>> embellishments;
	@Setter		@Getter private double clearanceOfEmbellishmentFromPreviousStructureTone;
	
	// LorezChordGenerator 
	@Builder.Default 	@Getter	@Setter	private int[] chordOnsetsByBeatStrength = new int[] {0};
	@Builder.Default	@Getter	@Setter	private DurationType[] chordDurationByLoopingPattern = new DurationType[] {DurationType.STACCATO};
	@Builder.Default	@Getter	@Setter	private DurationModel chordDurationStrategy = DurationModel.LOOPING_DURATION_TYPES;
	@Builder.Default	@Getter	@Setter	private int chordAccentVelocity = 64;
	@Builder.Default	@Getter	@Setter	private int chordNonAccentVelocity = 48;
	@Builder.Default	@Getter	@Setter	private int chordCentrePitch = 0;	// 0 = 'off' value and will not be set
	@Builder.Default 	@Getter	@Setter private int[] chordEmbellishmentRepetitionPattern = new int[] {0};	
	@Singular			@Getter	@Setter	private Map<Integer, List<MuGenerator>> chordEmbellishments;
	@Builder.Default	@Getter	@Setter private double chordClearanceOfEmbellishmentFromPreviousStructureTone = 0.5;
}
