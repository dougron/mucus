package main.java.com.dougron.mucus.algorithms.mu_generator;

import java.util.ArrayList;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

public class MuG_Anticipation_RRP implements MuGenerator
{
	
	private Mu parent;
	private RelativeRhythmicPosition relativeRhythmicPosition;
	private boolean hasParameterObjectArray;
	private Object[] parameterObjectArray;
	private static double DEFAULT_LENGTH = 0.5;
	private static int DEFAULT_EMBELLISHMENT_VELOCITY = 48;
	
	
	public MuG_Anticipation_RRP(RelativeRhythmicPosition aRelativeRhythmicPosition)
	{
		relativeRhythmicPosition = aRelativeRhythmicPosition;
	}

	
	
	@Override
	public void reset()
	{
		// TODO Auto-generated method stub

	}

	
	
	@Override
	public void generate()
	{
		if (parent != null)
		{
//			Mu child = new Mu("anticipation");
//			parent.addMu(child, relativeRhythmicPosition);
			Mu child = parent.addMuAtQuartersPosition(parent, relativeRhythmicPosition);
			setChildLength(child);
			setChildPitchAndVelocity(child);
			child.addTag(MuTag.IS_ANTICIPATION);
		}
	}



	private void setChildPitchAndVelocity(Mu child)
	{
//		int pitch = parent.getTopPitch();
//		child.addMuNote(new MuNote(pitch, DEFAULT_EMBELLISHMENT_VELOCITY));
		for (MuNote note: parent.getMuNotes())
		{
			child.addMuNote(note.getDeepCopy());
		}
	}



	private void setChildLength(Mu child)
	{
		double parentGlobalPositionInQuarters = parent.getGlobalPositionInQuarters();
		double childGlobalPositionInQuarters = child.getGlobalPositionInQuarters();
		double lengthInQuarters = parentGlobalPositionInQuarters - childGlobalPositionInQuarters;
		if (lengthInQuarters <= 0.0) lengthInQuarters = DEFAULT_LENGTH;
		child.setLengthInQuarters(lengthInQuarters);
	}

	
	
	@Override
	public MuGenerator getDeepCopy()
	{
		return new MuG_Anticipation_RRP(relativeRhythmicPosition);
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
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub

	}
	
	
	
	public String toString()
	{
		return "MuG_Anticipation_RRP: " + relativeRhythmicPosition.toString();
	}
	
	
	
	@Override
	public String toOneLineString ()
	{
		return "MuG_Anticipation_RRP: " + relativeRhythmicPosition.toString();
	}
	
	
	
	@Override
	public String toOneLineStringForJSON ()
	{
		return "MuG_Anticipation_RRP: " + relativeRhythmicPosition.toString();
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
		list.add("MuG_Anticipation_RRP");
		list.add(relativeRhythmicPosition.getBarOffset());
		list.add(relativeRhythmicPosition.getSuperTactusOffset());
		list.add(relativeRhythmicPosition.getTactusOffset());
		list.add(relativeRhythmicPosition.getSubTactusOffset());
		return list.toArray(new Object[list.size()]);
	}



	@Override
	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("mu_generator");
		element.setAttribute("type", "MuG_Anticipation_RRP");
		
		Element parentIndex = document.createElement("parent_index");
		parentIndex.appendChild(document.createTextNode("" + parent.getMuIndex()));
		element.appendChild(parentIndex);
		
		element.appendChild(relativeRhythmicPosition.getXMLElement(document));
		return element;
	}



	public static MuGenerator getMuGeneratorFromXMLElement(Element element)
	{
		Element relative_rhythmic_position = (Element)element.getElementsByTagName("relative_rhythmic_position").item(0);
		RelativeRhythmicPosition rrp = RelativeRhythmicPosition.getRelativeRhythmicPositionFromXMLElement(relative_rhythmic_position);
		return new MuG_Anticipation_RRP(rrp);
	}



	@Override
	public JSONObject getJSONObject ()
	{
		JSONObject content = new JSONObject();
		content.put("relativeRhythmicPosition", relativeRhythmicPosition.getJSONObject());
		
		JSONObject json = new JSONObject();
		json.put("MuG_Anticipation_RRP", content);
		return json;
	}



	

}
