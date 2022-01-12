package main.java.com.dougron.mucus.algorithms.mu_chord_tone_and_embellishment;

import java.util.ArrayList;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation.TextPlacement;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagBundle;

public class SimpleAnnotation implements Annotation
{
	
	private MuTag muTag;
	private String annotationString;


	public SimpleAnnotation(MuTag aMuTag, String aAnnotationString)
	{
		muTag = aMuTag;
		annotationString = aAnnotationString;
	}

	
	
	public void addAnnotation(Mu aMu)
	{
		ArrayList<MuTagBundle> list;
		for (Mu mu: aMu.getMusWithNotesIgnoringTupletHolders())
		{
			list = mu.getMuTagBundleContaining(muTag);
			if (list.size() > 0)
			{
				String str = annotationString;
				MuTagBundle mtb = list.get(0);		// only going to tag the first one for now
				if (mtb.containsMuTag(MuTag.ACCENTED)) str = "ac_" + annotationString;
				if (mtb.containsMuTag(MuTag.UNACCENTED)) str = "un_" + annotationString;
				mu.addMuAnnotation(new MuAnnotation(str, TextPlacement.PLACEMENT_BELOW));
			}
		}			
	}
	
	
	
	public String toString()
	{
		return muTag + "=" + annotationString;
	}
}
