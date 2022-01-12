package main.java.com.dougron.mucus.algorithms.mu_generator;

import java.util.ArrayList;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.algorithms.mu_generator.enums.NeighbourToneType;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

/*
 * generates escape tones, including neighbour tones which are a special case of escape tone for repeated notes
 */

public class MuG_ApproachTone_RRP implements MuGenerator
{
	
	private Mu parent;
	private RelativeRhythmicPosition relativeRhythmicPosition;
//	private EscapeToneType escapeToneType;
	private static final int DEFAULT_EMBELLISHMENT_VELOCITY = 48;
	private AccentType accentType;
	private NeighbourToneType neighbourToneType;
	private boolean hasParameterObjectArray;
	private Object[] parameterObjectArray;
	
	
	
	@Override
	public JSONObject getJSONObject ()
	{
		JSONObject content = new JSONObject();
		content.put("relativeRhythmicPosition", relativeRhythmicPosition.getJSONObject());
		content.put("accentType", accentType);
		content.put("neighbourToneType", neighbourToneType);
		
		JSONObject json = new JSONObject();
		json.put("MuG_ApproachTone_RRP", content);
		return json;
	}
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_ApproachTone_RRP: ");
		sb.append("\nrelativeRhythmicPosition=" + relativeRhythmicPosition.toString() + " " + accentType);
		sb.append("\n" + neighbourToneType);
		return sb.toString();
	}
	
	
	
	public String toOneLineString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_ApproachTone_RRP: ");
		sb.append("relativeRhythmicPosition=" + relativeRhythmicPosition.toString() + "," + accentType);
		sb.append("," + neighbourToneType);
		return sb.toString();
	}
	
	
	
	@Override
	public String toOneLineStringForJSON ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_ApproachTone_RRP: ");
		sb.append("relativeRhythmicPosition=" + relativeRhythmicPosition.toString() + " " + accentType);
		sb.append(" " + neighbourToneType);
		return sb.toString();
	}
	
	
	
	public MuG_ApproachTone_RRP(
			RelativeRhythmicPosition aRelativeRhythmicPosition, 
//			EscapeToneType aEscapeToneType, 
			AccentType aAccentType
			)
	{
		relativeRhythmicPosition = aRelativeRhythmicPosition;
//		escapeToneType = aEscapeToneType;
		accentType = aAccentType;
		neighbourToneType = NeighbourToneType.UPPER_NEIGHBOUR;
	}
	
	
	
	public MuG_ApproachTone_RRP(
			RelativeRhythmicPosition aRelativeRhythmicPosition, 
//			EscapeToneType aEscapeToneType, 
			AccentType aAccentType, 
			NeighbourToneType aNeighbourToneType
			)
	{
		relativeRhythmicPosition = aRelativeRhythmicPosition;
//		escapeToneType = aEscapeToneType;
		accentType = aAccentType;
		neighbourToneType = aNeighbourToneType;
	}
	


	@Override
	public void setParent(Mu aMu)
	{
		parent = aMu;
	}
	
	
	
	@Override
	public Mu getParent ()
	{
		return parent;
	}

	
	
	@Override
	public void reset()
	{
		// TODO Auto-generated method stub

	}
	


	@Override
	public void generate()
	{
		Mu mu = getAssociatedChordToneMu();
		Mu previousMu = getPreviousMu(mu);
		int contour = getContourToPreviousNote(mu, previousMu);
		Mu embellishment = mu.addMuAtQuartersPosition(mu, relativeRhythmicPosition);
		embellishment.addTag(MuTag.IS_APPROACH_TONE);
		double gap = mu.getGlobalPositionInQuarters() - embellishment.getGlobalPositionInQuarters();
		embellishment.setLengthInQuarters(gap);
		
		
		if (
				accentType == MuGenerator.AccentType.ACCENTED
				&& mu.hasTag(MuTag.IS_STRUCTURE_TONE)
				&& MuGenerator.accentBehaviour == AccentBehaviour.ONLY_ON_STRUCTURE_TONES
				) 
			moveAssociatedChordTone(mu, gap);
		
		switch (contour)
		{
		case 0:	
			dealWithZeroContour(mu, previousMu, embellishment, gap);
			break;
		case 1:	
			dealWithPositiveContour(mu, previousMu, embellishment, gap);
			break;
		case -1:
			dealWithNegativeContour(mu, previousMu, embellishment, gap);
		}
	}

	
	
	private void moveAssociatedChordTone(Mu mu, double offsetInQuarters)
	{
		BarsAndBeats bab = mu.getPositionInBarsAndBeats();
		BarsAndBeats newbab = new BarsAndBeats(bab.getBarPosition(), bab.getOffsetInQuarters() + offsetInQuarters);
		mu.setPositionInBarsAndBeats(newbab);
	}



	private void dealWithNegativeContour(Mu mu, Mu previousMu, Mu embellishmentMu, double lengthInQuarters)
	{
		int noteVector = -1;
		for (MuNote note: mu.getMuNotes())
		{
			makeNote(note.getPitch(), noteVector, lengthInQuarters, mu, embellishmentMu);
		}
	}



	private void dealWithPositiveContour(Mu mu, Mu previousMu, Mu embellishmentMu, double lengthInQuarters)
	{
		int noteVector = 1;	
		for (MuNote note: mu.getMuNotes())
		{
			makeNote(note.getPitch(), noteVector, lengthInQuarters, mu, embellishmentMu);
		}	
	}



	private void dealWithZeroContour(Mu mu, Mu previousMu, Mu embellishmentMu, double lengthInQuarters)
	{
		int noteVector = 0;
		switch (neighbourToneType)		// these vectors may make no sense but it is because I have reversed
		{								// the vector sign in makeNote so this behaves opposite to EscapeTone
		case UPPER_NEIGHBOUR: 
			noteVector = -1;
			break;
		case LOWER_NEIGHBOUR:
			noteVector = 1;
			break;
		}
		for (MuNote note: mu.getMuNotes())
		{
			makeNote(note.getPitch(), noteVector, lengthInQuarters, mu, embellishmentMu);
		}
	}



	private void makeNote(int aPitch, int noteVector, double lengthInQuarters, Mu mu, Mu embellishmentMu)
	{
		int pitch = getNextDiatonicNote(aPitch, noteVector * -1, embellishmentMu);
		embellishmentMu.addMuNote(new MuNote(pitch, DEFAULT_EMBELLISHMENT_VELOCITY));
	}



	private int getNextDiatonicNote(int aPitch, int noteVector, Mu aMu)
	{
		while (true)
		{
			aPitch += noteVector;
			if (aMu.isDiatonicNoteInXMLKey(aPitch)) return aPitch;
		}
	}



	private int getContourToPreviousNote(Mu mu, Mu previousMu)
	{
		int noteMu = mu.getTopPitch();
		int notePreviousMu = previousMu.getTopPitch();
		return (int)Math.signum(noteMu - notePreviousMu);
	}



	private Mu getPreviousMu(Mu aMu)
	{
		Mu parent = aMu.getParent();
		Mu theMu = parent.getMus().get(parent.getMus().size() - 1);
		for (Mu mu: parent.getMus())
		{
			if (mu == aMu) return theMu;
			theMu = mu;
		}
		return null;
	}



	private Mu getAssociatedChordToneMu()
	{
		Mu chordToneMu = parent;
		while (!chordToneMu.hasMuNotes())
		{
			chordToneMu = chordToneMu.getParent();
		}
		return chordToneMu;
	}



	@Override
	public MuGenerator getDeepCopy()
	{
		return new MuG_ApproachTone_RRP(relativeRhythmicPosition, accentType);
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public Object[] getParameterObjectArray()
	{
		if (!hasParameterObjectArray)
		{
			parameterObjectArray = makeParameterObjectArray();
		}
		return parameterObjectArray;
	}



	private Object[] makeParameterObjectArray()
	{
		ArrayList<Object> list = new ArrayList<Object>();
		list.add("MuG_ApproachTone_RRP");
		list.add(relativeRhythmicPosition.getBarOffset());
		list.add(relativeRhythmicPosition.getSuperTactusOffset());
		list.add(relativeRhythmicPosition.getTactusOffset());
		list.add(relativeRhythmicPosition.getSubTactusOffset());
		list.add(neighbourToneType);
		list.add(accentType);
		return list.toArray(new Object[list.size()]);
	}
	
	
	
	@Override
	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("mu_generator");
		element.setAttribute("type", "MuG_EscapeTone_RRP");
		
		Element parentIndex = document.createElement("parent_index");
		parentIndex.appendChild(document.createTextNode("" + parent.getMuIndex()));
		element.appendChild(parentIndex);
		
//		Element escape_tone_type = document.createElement("escape_tone_type");
//		escape_tone_type.appendChild(document.createTextNode("" + escapeToneType));
//		element.appendChild(escape_tone_type);
		
		Element neighbour_tone_type = document.createElement("neighbour_tone_type");
		neighbour_tone_type.appendChild(document.createTextNode("" + neighbourToneType));
		element.appendChild(neighbour_tone_type);
		
		Element accent_type = document.createElement("accent_type");
		accent_type.appendChild(document.createTextNode("" + accentType));
		element.appendChild(accent_type);
		
		element.appendChild(relativeRhythmicPosition.getXMLElement(document));
		return element;
	}


	
	public static MuGenerator getMuGeneratorFromXMLElement(Element element)
	{
		Element relative_rhythmic_position = (Element)element.getElementsByTagName("relative_rhythmic_position").item(0);
		RelativeRhythmicPosition rrp = RelativeRhythmicPosition.getRelativeRhythmicPositionFromXMLElement(relative_rhythmic_position);		
//		EscapeToneType ett = EscapeToneType.valueOf(element.getElementsByTagName("escape_tone_type").item(0).getTextContent());
		NeighbourToneType ntt = NeighbourToneType.valueOf(element.getElementsByTagName("neighbour_tone_type").item(0).getTextContent());
		AccentType at = AccentType.valueOf(element.getElementsByTagName("accent_type").item(0).getTextContent());
		return new MuG_ApproachTone_RRP(rrp, at, ntt);
	}
	
	 
	
	
}
