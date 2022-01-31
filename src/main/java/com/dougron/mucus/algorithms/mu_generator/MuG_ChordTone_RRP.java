package main.java.com.dougron.mucus.algorithms.mu_generator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.algorithms.mu_generator.enums.ChordToneType;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.da_utils.static_chord_scale_dictionary.ChordToneName;


/*
 * generates escape tones, including neighbour tones which are a special case of escape tone for repeated notes
 */

public class MuG_ChordTone_RRP implements MuGenerator
{
	
	
	
	
	private Mu parent;
	private RelativeRhythmicPosition relativeRhythmicPosition;
	private static final int DEFAULT_EMBELLISHMENT_VELOCITY = 48;
	private boolean hasParameterObjectArray;
	private Object[] parameterObjectArray;
	private int jumpCount = 1;	// the number of chord tones that will be jumped before settling on a final - 1
	private ChordToneType chordToneType;
	private ChordToneName[] chordToneNames;
	
	
	
	
	public MuG_ChordTone_RRP(
			RelativeRhythmicPosition aRelativeRhythmicPosition, 
			ChordToneType aChordToneType, 
			int aJumpCount
			)
	{
		relativeRhythmicPosition = aRelativeRhythmicPosition;
		chordToneType = aChordToneType;
		jumpCount = aJumpCount;
	}
	
	
	
	public MuG_ChordTone_RRP(
			RelativeRhythmicPosition aRelativeRhythmicPosition, 
			ChordToneType aChordToneType
			)
	{
		relativeRhythmicPosition = aRelativeRhythmicPosition;
		chordToneType = aChordToneType;
	}
	
	
	
	public MuG_ChordTone_RRP(
			RelativeRhythmicPosition aRelativeRhythmicPosition, 
			ChordToneType aChordToneType,
			ChordToneName[] aChordToneNames
			)
	{
		relativeRhythmicPosition = aRelativeRhythmicPosition;
		chordToneType = aChordToneType;
		chordToneNames = aChordToneNames;
	}
	
	
	
	public MuG_ChordTone_RRP(
			RelativeRhythmicPosition aRelativeRhythmicPosition, 
			ChordToneType aChordToneType,
			int aJumpCount,
			ChordToneName[] aChordToneNames
			)
	{
		relativeRhythmicPosition = aRelativeRhythmicPosition;
		chordToneType = aChordToneType;
		chordToneNames = aChordToneNames;
		jumpCount = aJumpCount;
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
		Mu embellishment = mu.addMuAtQuartersPosition(mu, relativeRhythmicPosition);
		embellishment.addTag(MuTag.IS_CHORD_TONE_EMBELLISHMENT);
		double gap = mu.getGlobalPositionInQuarters() - embellishment.getGlobalPositionInQuarters();
		embellishment.setLengthInQuarters(gap);
		
		Chord chord = getPrevailingChord(embellishment);
		for (MuNote note: mu.getMuNotes())
		{
//			int parentPitch = mu.getTopPitch();
			int parentPitch = note.getPitch();
			int pitch = 0;
			switch (chordToneType)
			{
			case CLOSEST:
				if (chordToneNames == null)
				{
					pitch = chord.getClosestChordTone(parentPitch, 0);
				} else
				{
					pitch = chord.getClosestChordTone(parentPitch, 0,
							chordToneNames);
				}

				break;
			case CLOSEST_ABOVE:
				pitch = getJumpedChordTone(chord, parentPitch, 1);
				break;
			case CLOSEST_ABOVE_OR_EQUAL:
				pitch = getClosestOrEqual(chord, parentPitch, 1);
				break;
			case CLOSEST_BELOW:
				pitch = getJumpedChordTone(chord, parentPitch, -1);
				break;
			case CLOSEST_BELOW_OR_EQUAL:
				pitch = getClosestOrEqual(chord, parentPitch, -1);
				break;
			default:
				break;
			}
			embellishment.addMuNote(
					new MuNote(pitch, DEFAULT_EMBELLISHMENT_VELOCITY));
		}
	}



	public Chord getPrevailingChord (Mu embellishment)
	{
		Chord chord = embellishment.getPrevailingChord();
		if (chord == null && embellishment.getGlobalPositionInQuarters() < 0.0)
		{
			Mu muGlobalParent = embellishment.ancestorSeach(e -> !e.hasParent());
			double quartersPosition = embellishment.getGlobalPositionInQuarters() + muGlobalParent.getLengthInQuarters();
			BarsAndBeats position = muGlobalParent.getGlobalPositionInBarsAndBeats(quartersPosition);
			chord = muGlobalParent.getChordAt(position);
		}
		return chord;
	}


	
	public int getClosestOrEqual (Chord chord, int parentPitch, int contour)
	// closestOrEqual does not consider jumping chord tones as the use case includes equal so why jump at all
	{
		int pitch;
		if (chordToneNames == null)
		{
			if (chord.isChordTone(parentPitch))
			{
				pitch = parentPitch;
			}
			else
			{
				pitch = chord.getClosestChordTone(parentPitch, contour);
			}
		} 
		else
		{
			if (chord.isChordTone(parentPitch, chordToneNames))
			{
				pitch = parentPitch;
			}
			else
			{
				pitch = chord.getClosestChordTone(parentPitch, contour, chordToneNames);
			}
		}
		
		return pitch;
	}


	
	public int getJumpedChordTone (Chord chord, int parentPitch, int contour)	// contour currently is 1 or -1 for search up or down
	{
		int tempPitch = parentPitch;
		int pitch = 0;

		if (chordToneNames == null)
		{
			pitch = chord.getClosestChordTone(tempPitch, contour * jumpCount);
		}
		else
		{
			pitch = chord.getClosestChordTone(tempPitch, contour * jumpCount, chordToneNames);
		}
		return pitch;
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
		return new MuG_ChordTone_RRP(relativeRhythmicPosition, chordToneType, jumpCount);
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
		list.add("MuG_EscapeTone_RRP");
		list.add(relativeRhythmicPosition.getBarOffset());
		list.add(relativeRhythmicPosition.getSuperTactusOffset());
		list.add(relativeRhythmicPosition.getTactusOffset());
		list.add(relativeRhythmicPosition.getSubTactusOffset());
		list.add(chordToneType);
//		list.add(neighbourToneType);
		list.add(jumpCount);
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
		
		Element escape_tone_type = document.createElement("escape_tone_type");
		escape_tone_type.appendChild(document.createTextNode("" + chordToneType));
		element.appendChild(escape_tone_type);
		
		Element jump_count = document.createElement("jump_count");
		jump_count.appendChild(document.createTextNode("" + jumpCount));
		element.appendChild(jump_count);
		
		element.appendChild(relativeRhythmicPosition.getXMLElement(document));
		return element;
	}


	
	public static MuGenerator getMuGeneratorFromXMLElement(Element element)
	{
		Element relative_rhythmic_position = (Element)element.getElementsByTagName("relative_rhythmic_position").item(0);
		RelativeRhythmicPosition rrp = RelativeRhythmicPosition.getRelativeRhythmicPositionFromXMLElement(relative_rhythmic_position);		
		ChordToneType ctt = ChordToneType.valueOf(element.getElementsByTagName("chord_tone_type").item(0).getTextContent());
		int jc = Integer.parseInt(element.getElementsByTagName("jump_count").item(0).getTextContent());
		return new MuG_ChordTone_RRP(rrp, ctt, jc);
	}
	
	
	
	@Override
	public JSONObject getJSONObject ()
	{
		JSONObject content = new JSONObject();
		content.put("relativeRhythmicPosition", relativeRhythmicPosition.getJSONObject());
		content.put("chordToneType", chordToneType);
		content.put("jumpCount", jumpCount);
		if (chordToneNames != null)
		{
			JSONArray arr = new JSONArray();
			for (ChordToneName ctn: chordToneNames)
			{
				arr.put(ctn);
			}
			content.put("chordToneNames", arr);
		}
		JSONObject json = new JSONObject();
		json.put("MuG_EscapeTone_RRP", content);
		return json;
	}
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_EscapeTone_RRP: " + chordToneType);
		sb.append("\nrelativeRhythmicPosition=" + relativeRhythmicPosition.toString());
		if (chordToneNames != null)
		{
			sb.append("\nchordToneNames=");
			for (ChordToneName ctn: chordToneNames)
			{
				sb.append(ctn + ",");
			}
		}
		return sb.toString();
	}
	
	
	
	public String toOneLineString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_EscapeTone_RRP: " + chordToneType);
		sb.append(",relativeRhythmicPosition=" + relativeRhythmicPosition.toString() + ",");
		if (chordToneNames != null)
		{
			sb.append(",chordTones=");
			for (ChordToneName ctn: chordToneNames)
			{
				sb.append(ctn + ",");
			}
		}		
		return sb.toString();
	}
	
	
	
	@Override
	public String toOneLineStringForJSON ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("MuG_EscapeTone_RRP:" + chordToneType);
		sb.append(" relativeRhythmicPosition=" + relativeRhythmicPosition.toString() + " ");
		if (chordToneNames != null)
		{
			sb.append(" chordTones=");
			for (ChordToneName ctn: chordToneNames)
			{
				sb.append(ctn + ",");
			}
		}
		return sb.toString();
	}
	

	
}
