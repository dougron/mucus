package main.java.com.dougron.mucus.algorithms.mu_generator;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;

public class MuG_NothingToAdd implements MuGenerator
{
	
	Mu parent;
	private Object[] parameterObjectArray = new Object[] {"MuG_NothingToAdd"};
	
	
	@Override
	public JSONObject getJSONObject ()
	{		
		JSONObject json = new JSONObject();
		json.put("MuG_NothingToAdd", "MuG_NothingToAdd");
		return json;
	}
	

	@Override
	public void reset()
	{}

	
	
	@Override
	public void generate()
	{}

	
	
	@Override
	public MuGenerator getDeepCopy()
	{
		return new MuG_NothingToAdd();
	}

	
	
	@Override
	public void setParent(Mu aMu)
	{
		parent = aMu;
	}
	
	
	
	public Mu getParent ()
	{
		return parent;
	}

	
	
	@Override
	public void setAccentType(AccentType aAccentType)
	{}
	
	
	
	public String toString()
	{
		return "MuG_NothingToAdd";
	}
	
	
	
	public String toOneLineString()
	{
		return "MuG_NothingToAdd";
	}
	
	
	
	public String toOneLineStringForJSON()
	{
		return "MuG_NothingToAdd";
	}



	@Override
	public Object[] getParameterObjectArray()
	{
		return parameterObjectArray ;
	}
	
	
	
	@Override
	public Element getXMLElement(Document document)
	{
		Element element = document.createElement("mu_generator");
		element.setAttribute("type", "MuG_NothingToAdd");
		
		return element;
	}



	public static MuGenerator getMuGeneratorFromXMLElement(Element element)
	{
		return new MuG_NothingToAdd();
	}

}
