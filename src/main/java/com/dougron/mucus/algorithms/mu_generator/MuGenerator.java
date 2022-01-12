package main.java.com.dougron.mucus.algorithms.mu_generator;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;

public interface MuGenerator
{
	
	public static enum AccentType{ACCENTED, UNACCENTED};
	public static enum AccentBehaviour{ONLY_ON_STRUCTURE_TONES, ON_ALL_TONES};
	public static AccentBehaviour accentBehaviour = AccentBehaviour.ONLY_ON_STRUCTURE_TONES;

	
	
	void reset();
	void generate();
	MuGenerator getDeepCopy();	
	void setParent(Mu aMu);
	Mu getParent ();
	void setAccentType(AccentType aAccentType);
	public Object[] getParameterObjectArray();
	Element getXMLElement(Document document);
	public JSONObject getJSONObject();
	
	
	
	public static MuGenerator getMuGeneratorFromXMLElement(Element element)
	{
		String type = element.getAttribute("type");
		switch (type)
		{
		case "MuG_Anticipation_RRP":
			return MuG_Anticipation_RRP.getMuGeneratorFromXMLElement(element);
		case "MuG_EscapeTone_RRP":
			return MuG_EscapeTone_RRP.getMuGeneratorFromXMLElement(element);
		case "MuG_Anticipation":
			return MuG_Anticipation.getMuGeneratorFromXMLElement(element);
		case "MuG_EscapeTone":
			return MuG_EscapeTone.getMuGeneratorFromXMLElement(element);
		case "MuG_NothingToAdd":
			return MuG_NothingToAdd.getMuGeneratorFromXMLElement(element);
		default: return null;
		
		}
	}
	String toOneLineString ();
	String toOneLineStringForJSON ();

	
}