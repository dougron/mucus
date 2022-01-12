package main.java.com.dougron.mucus.algorithms.generic_generator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
public class DynamicsPreset
{

	@Builder.Default	@Getter	@Setter	private int bassAccentVelocity = 64;
	@Builder.Default	@Getter	@Setter	private int bassNonAccentVelocity = 48;
	@Builder.Default 	@Getter	@Setter	private int drumAccentVelocity = 64;	
	@Builder.Default 	@Getter	@Setter private int drumNonAccentVelocity = 48;
	@Builder.Default	@Getter	@Setter	private int chordAccentVelocity = 64;
	@Builder.Default	@Getter	@Setter	private int chordNonAccentVelocity = 48;
	
	
	public void setDynamics(ParameterObject po)
	{
		po.setBassAccentVelocity(bassAccentVelocity);
		po.setBassNonAccentVelocity(bassNonAccentVelocity);
		po.setDrumAccentVelocity(drumAccentVelocity);
		po.setDrumNonAccentVelocity(drumNonAccentVelocity);
		po.setChordAccentVelocity(chordAccentVelocity);
		po.setChordNonAccentVelocity(chordNonAccentVelocity);
	}
	
}
