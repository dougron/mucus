package main.java.com.dougron.mucus.algorithms.mu_generator;

import java.util.ArrayList;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.algorithms.superimposifier.DurationModel;
import main.java.com.dougron.mucus.algorithms.superimposifier.PitchModel;
import main.java.com.dougron.mucus.algorithms.superimposifier.RhythmModel;
import main.java.com.dougron.mucus.mu_framework.Mu;

public class MuG_Anticipation implements MuGenerator
{

	private Mu parent;
	private double noteLength = 0.5;
	private int noteCount = 3;
	private AccentType accentType;
//	private static final int DEFAULT_EMBELLISHMENT_VELOCITY = 48;
	private RhythmModel rhythmModel;
	private PitchModel pitchModel;
	private DurationModel durationModel;
	private boolean hasParameterObjectArray;
	private Object[] parameterObjectArray;

	
//	public SuFi_Anticipation(int aNoteCount, double aNoteLengthInQuarters, AccentType aAccentType)
//	{
//		noteLength = aNoteLengthInQuarters;
//		noteCount = aNoteCount;
//		accentType = aAccentType;
//	}
	
	
	
	@Override
	public JSONObject getJSONObject ()
	{
		JSONObject content = new JSONObject();
		content.put("noteLength", noteLength);
		content.put("noteCount", noteCount);
		content.put("accentType", accentType);
		content.put("rhythmModel", rhythmModel.getAsJSON());
		content.put("pitchModel", pitchModel.getAsJSON());
		content.put("durationModel", durationModel.getAsJSON());
		
		JSONObject json = new JSONObject();
		json.put("MuG_Anticipation_RRP", content);
		return json;
	}
	
	
	
	public String toString()
	{
		return "MuG_Anticipation: noteLength=" + noteLength + " noteCount=" + noteCount;
	}
	
	
	
	@Override
	public String toOneLineString ()
	{
		return "MuG_Anticipation: noteLength=" + noteLength + " noteCount=" + noteCount;
	}
	
	
	
	@Override
	public String toOneLineStringForJSON ()
	{
		return "MuG_Anticipation: noteLength=" + noteLength + " noteCount=" + noteCount;
	}
	
	
	
	public MuG_Anticipation(int aNoteCount, double aNoteLengthInQuarters)
	{
		noteLength = aNoteLengthInQuarters;
		noteCount = aNoteCount;
		accentType = AccentType.UNACCENTED;
		rhythmModel = makeRhythmModel(aNoteCount, aNoteLengthInQuarters);
		pitchModel = new PitchModel(new int[] {0});
		durationModel = new DurationModel(aNoteLengthInQuarters);
	}
	
	
	
	public MuG_Anticipation(int aNoteCount, double aNoteLengthInQuarters, AccentType aAccentType)
	{
		noteLength = aNoteLengthInQuarters;
		noteCount = aNoteCount;
		accentType = aAccentType;
		rhythmModel = makeRhythmModel(aNoteCount, aNoteLengthInQuarters);
		pitchModel = new PitchModel(new int[] {0});
		durationModel = new DurationModel(aNoteLengthInQuarters);
	}



	private RhythmModel makeRhythmModel(int aNoteCount, double aNoteLengthInQuarters)
	{
		double[] arr = new double[aNoteCount];
		for (int i = 0; i < aNoteCount; i++)
		{
			arr[i] = -1 * aNoteLengthInQuarters * (i + 1);
		}
		return new RhythmModel(arr);
	}
	
	
	
	@Override
	public void reset()
	{
		// TODO Auto-generated method stub

	}

	
	
	@Override
	public void generate()
	{
		Mu mu = parent;
//		int pitch = mu.getParent().getTopPitch();
		rhythmModel.addMus(mu);
		pitchModel.addPitchesToMus(mu);
		durationModel.setDurations(mu);
	}



	@Override
	public MuGenerator getDeepCopy()
	{
		return new MuG_Anticipation(noteCount, noteLength);
	}



	@Override
	public void setAccentType(AccentType aAccentType)
	{
		// TODO Auto-generated method stub	
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
		list.add("MuG_Anticipation");
		list.add(noteCount);
		list.add(noteLength);
		list.add(accentType);
		return list.toArray(new Object[list.size()]);
	}
	
	
	
	@Override
	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("mu_generator");
		element.setAttribute("type", "MuG_Anticipation");
		
		Element parentIndex = document.createElement("parent_index");
		parentIndex.appendChild(document.createTextNode("" + parent.getMuIndex()));
		element.appendChild(parentIndex);
		
		Element note_length = document.createElement("note_length");
		note_length.appendChild(document.createTextNode("" + noteLength));
		element.appendChild(note_length);
		
		Element note_count = document.createElement("note_count");
		note_count.appendChild(document.createTextNode("" + noteCount));
		element.appendChild(note_count);
		
		Element accent_type = document.createElement("accent_type");
		accent_type.appendChild(document.createTextNode("" + accentType));
		element.appendChild(accent_type);
	
		return element;
	}



	public static MuGenerator getMuGeneratorFromXMLElement(Element element)
	{
		double note_length = Double.parseDouble(element.getElementsByTagName("note_length").item(0).getTextContent());
		int note_count = Integer.parseInt(element.getElementsByTagName("note_count").item(0).getTextContent());
		AccentType at = AccentType.valueOf(element.getElementsByTagName("accent_type").item(0).getTextContent());
		return new MuG_Anticipation(note_count, note_length, at);
	}



	

}
