package main.java.com.dougron.mucus.algorithms.mu_generator;

import java.util.ArrayList;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.algorithms.mu_generator.enums.EscapeToneType;
import main.java.com.dougron.mucus.algorithms.mu_generator.enums.NeighbourToneType;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

/*
 * generates escape tones, including neighbour tones which are a special case of escape tone for repeated notes
 */

public class MuG_EscapeTone implements MuGenerator
{
	

	
	private Mu parent;
	private double lengthInQuarters;
	private EscapeToneType escapeToneType;
	private static final int DEFAULT_EMBELLISHMENT_VELOCITY = 48;
	private AccentType accentType;
	private NeighbourToneType neighbourToneType;
	private boolean hasParameterObjectArray;
	private Object[] parameterObjectArray;
	
	
	@Override
	public JSONObject getJSONObject ()
	{
		JSONObject content = new JSONObject();
		content.put("lengthInQuarters", lengthInQuarters);
		content.put("escapeToneType", escapeToneType);
		content.put("accentType", accentType);
		content.put("neighbourToneType", neighbourToneType);
		
		JSONObject json = new JSONObject();
		json.put("MuG_Anticipation_RRP", content);
		return json;
	}
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_EscapeTone: " + escapeToneType);
		sb.append("\nlengthInQuarters=" + lengthInQuarters + " " + accentType);
		sb.append("\n" + neighbourToneType);
		return sb.toString();
	}
	
	
	
	public String toOneLineString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_EscapeTone:" + escapeToneType);
		sb.append(",lengthInQuarters=" + lengthInQuarters + "," + accentType);
		sb.append("," + neighbourToneType);
		return sb.toString();
	}
	
	
	
	@Override
	public String toOneLineStringForJSON ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_EscapeTone:" + escapeToneType);
		sb.append(" lengthInQuarters=" + lengthInQuarters + " " + accentType);
		sb.append(" " + neighbourToneType);
		return sb.toString();
	}
	
	
	
	public MuG_EscapeTone(double aLengthInQuarters, EscapeToneType aEscapeToneType, AccentType aAccentType)
	{
		lengthInQuarters = aLengthInQuarters;
		escapeToneType = aEscapeToneType;
		accentType = aAccentType;
		neighbourToneType = NeighbourToneType.UPPER_NEIGHBOUR;
	}
	
	
	
	public MuG_EscapeTone
	(
			double aLengthInQuarters, 
			EscapeToneType aEscapeToneType, 
			AccentType aAccentType, 
			NeighbourToneType aNeighbourToneType
			)
	{
		lengthInQuarters = aLengthInQuarters;
		escapeToneType = aEscapeToneType;
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
		if (accentType == MuGenerator.AccentType.ACCENTED) moveAssociatedChordTone(mu, lengthInQuarters);
		switch (contour)
		{
		case 0:	
			dealWithZeroContour(mu, previousMu);
			break;
		case 1:	
			dealWithPositiveContour(mu, previousMu);
			break;
		case -1:
			dealWithNegativeContour(mu, previousMu);
		}
	}

	
	
	private void moveAssociatedChordTone(Mu mu, double aLengthInQuarters)
	{
		BarsAndBeats bab = mu.getPositionInBarsAndBeats();
		BarsAndBeats newbab = new BarsAndBeats(bab.getBarPosition(), bab.getOffsetInQuarters() + aLengthInQuarters);
		mu.setPositionInBarsAndBeats(newbab);
	}



	private void dealWithNegativeContour(Mu mu, Mu previousMu)
	{
		int contour = -1;
		dealWithContourAndMakeNote(mu, previousMu, contour);
	}



	private void dealWithPositiveContour(Mu mu, Mu previousMu)
	{
		int contour = 1;
		dealWithContourAndMakeNote(mu, previousMu, contour);	
	}



	private void dealWithContourAndMakeNote(Mu mu, Mu previousMu, int contour)
	{
		switch (escapeToneType)
		{
		case JUMP_STEP:
			makeNote(mu.getTopPitch(), contour, lengthInQuarters, mu);
			break;
		case STEP_JUMP:
			makeNote(previousMu.getTopPitch(), -contour, lengthInQuarters, mu);
		}
	}



	private void dealWithZeroContour(Mu mu, Mu previousMu)
	{
		int noteVector = 0;
		switch (neighbourToneType)
		{
		case UPPER_NEIGHBOUR: 
			noteVector = 1;
			break;
		case LOWER_NEIGHBOUR:
			noteVector = -1;
			break;
		}
		makeNote(mu.getTopPitch(), noteVector, lengthInQuarters, mu);
	}



//	private int getRandomNoteVectorThatIsNotZero(Random rnd)
//	{
//		int noteVector = rnd.nextInt(2) * 2 - 1;
//		return noteVector;
//	}



	private void makeNote(int aPitch, int noteVector, double lengthInQuarters, Mu mu)
	{
//		TimeSignature ts = mu.getTimeSignature(0);
		double barPosInQuarters = lengthInQuarters * -1.0;
		Mu emb = new Mu("escape-tone");
		emb.setLengthInQuarters(lengthInQuarters);
		parent.addMu(emb, barPosInQuarters);
		int pitch = getNextDiatonicNote(aPitch, noteVector, emb);
		emb.addMuNote(new MuNote(pitch, DEFAULT_EMBELLISHMENT_VELOCITY));
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
		return new MuG_EscapeTone(lengthInQuarters, escapeToneType, accentType);
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
		list.add("MuG_EscapeTone");
		list.add(lengthInQuarters);
		list.add(escapeToneType);
		list.add(neighbourToneType);
		list.add(accentType);
		return list.toArray(new Object[list.size()]);
	}
	
	
	
	@Override
	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("mu_generator");
		element.setAttribute("type", "MuG_EscapeTone");
		
		Element parentIndex = document.createElement("parent_index");
		parentIndex.appendChild(document.createTextNode("" + parent.getMuIndex()));
		element.appendChild(parentIndex);
		
		Element length_in_quarters = document.createElement("length_in_quarters");
		length_in_quarters.appendChild(document.createTextNode("" + lengthInQuarters));
		element.appendChild(length_in_quarters);
		
		Element escape_tone_type = document.createElement("escape_tone_type");
		escape_tone_type.appendChild(document.createTextNode("" + escapeToneType));
		element.appendChild(escape_tone_type);
		
		Element neighbour_tone_type = document.createElement("neighbour_tone_type");
		neighbour_tone_type.appendChild(document.createTextNode("" + neighbourToneType));
		element.appendChild(neighbour_tone_type);
		
		Element accent_type = document.createElement("accent_type");
		accent_type.appendChild(document.createTextNode("" + accentType));
		element.appendChild(accent_type);
	
		return element;
	}


	public static MuGenerator getMuGeneratorFromXMLElement(Element element)
	{
		double length_in_quarters = Double.parseDouble(element.getElementsByTagName("length_in_quarters").item(0).getTextContent());
		EscapeToneType ett = EscapeToneType.valueOf(element.getElementsByTagName("escape_tone_type").item(0).getTextContent());
		NeighbourToneType ntt = NeighbourToneType.valueOf(element.getElementsByTagName("neighbour_tone_type").item(0).getTextContent());
		AccentType at = AccentType.valueOf(element.getElementsByTagName("accent_type").item(0).getTextContent());
		
		return new MuG_EscapeTone(length_in_quarters, ett, at, ntt);
	}

}
