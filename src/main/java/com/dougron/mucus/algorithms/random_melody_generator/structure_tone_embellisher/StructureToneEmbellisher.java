package main.java.com.dougron.mucus.algorithms.random_melody_generator.structure_tone_embellisher;

import java.util.List;

import com.google.common.base.Preconditions;

import main.java.com.dougron.mucus.algorithms.generic_generator.ParameterObject;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

/*
 * based on the RandomMelodyGenerator.addEmbellishmentMus() call, but adapted to 
 * use the generic_generator ParameterObject
 */

public class StructureToneEmbellisher
{

	public static Mu addEmbellishmentMus(Mu aMu, ParameterObject aPo)
	{
		for (int key: aPo.getEmbellishmentRepetitionPattern())
		{
			Preconditions.checkArgument(aPo.getEmbellishments().containsKey(key), "Missing embellishments for key=" + key);
		}
		int index = 0;
		Mu previousStructureToneMu = null;
		double globalCutOffPositionInQuarters = -10000.0;	// artibrarily negatively large. Should be the negative position of the last structure tone in the phrase to accomodate looping.
		for (Mu structureTone: aMu.getMus())
		{
			int mugMapIndex = aPo.getEmbellishmentRepetitionPattern()[index];
			List<MuGenerator> mugList = aPo.getEmbellishments().get(mugMapIndex);
//			ArrayList<MuGenerator> mugList = aPo.getEmbellishments().get(aPo.getEmbellishmentRepetitionPattern()[index]);
			globalCutOffPositionInQuarters = getGlobalCutOffPositionInQuarters(
					aMu, aPo, previousStructureToneMu);
			Mu currentMu = structureTone;
			
			for (MuGenerator mug: mugList)
			{
				currentMu.addMuGenerator(mug.getDeepCopy());
				currentMu.generate();
				Mu recentlyAddedMu;
				if (currentMu.getMus().size() == 0)
				{
					recentlyAddedMu = currentMu;
				}
				else
				{
					recentlyAddedMu = currentMu.getMus().get(0);
					if (aPo.getNonAccentVelocity() != 0)
					{
						for (MuNote mn: recentlyAddedMu.getMuNotes())
						{
							mn.setVelocity(aPo.getNonAccentVelocity());
						}
					}
					
					// collision detection
					if (recentlyAddedMu.getGlobalPositionInQuarters() < globalCutOffPositionInQuarters)
					{
						currentMu.getMus().remove(0);
						break;
					}
					currentMu = currentMu.getMus().get(0);
				}
			}						
			index++;
			if (index >= aPo.getEmbellishmentRepetitionPattern().length) index = 0;
			previousStructureToneMu = structureTone;
		}
		return aMu;
	}

	
	
	public static double getGlobalCutOffPositionInQuarters 
	(
			Mu aMu,
			ParameterObject aPo, 
			Mu previousStructureToneMu
			)
	{
		double globalCutOffPositionInQuarters;
		if (previousStructureToneMu != null) 
		{ 
			globalCutOffPositionInQuarters 
			= previousStructureToneMu.getGlobalPositionInQuarters() 
			+ aPo.getClearanceOfEmbellishmentFromPreviousStructureTone();
		}
		else
		{
			// assumes aMu represents a looped phrase and therefore the last note represents the previous note for the first note
			Mu lastNote = aMu.getMus().get(aMu.getNumberOfMus() - 1);
			globalCutOffPositionInQuarters = lastNote.getGlobalPositionInQuarters() - aMu.getLengthInQuarters();
		}
		return globalCutOffPositionInQuarters;
	}
}
