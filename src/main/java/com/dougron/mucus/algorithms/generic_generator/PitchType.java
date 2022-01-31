package main.java.com.dougron.mucus.algorithms.generic_generator;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import main.java.da_utils.static_chord_scale_dictionary.ChordToneName;

@AllArgsConstructor
@ToString
public class PitchType
{
	@Getter
	private ChordToneName chordToneName;
	
	@Getter 
	private int vector;
}
