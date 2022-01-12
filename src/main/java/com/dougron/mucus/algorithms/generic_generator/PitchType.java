package main.java.com.dougron.mucus.algorithms.generic_generator;

import StaticChordScaleDictionary.ChordToneName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class PitchType
{
	@Getter
	private ChordToneName chordToneName;
	
	@Getter 
	private int vector;
}
