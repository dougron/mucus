package main.java.com.dougron.mucus.mu_framework.length_model;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public interface LengthModel
{

	int getLengthInBars();
	double getLengthInQuarters();
	BarsAndBeats getLengthInBarsAndBeats();

	void calculateLength(List<Mu> mus);
	
	String positionToString();
	void setSameLengthModel(Mu aMu);
	String getContentString();
	Element getXMLFileContent(Document document);

	

	

}
