package main.java.com.dougron.mucus.mu_framework.position_model;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public interface PositionModel
{

	int getPositionInBars();
	double getPositionInQuarters();
	BarsAndBeats getPositionInBarsAndBeats();

	int getGlobalPositionInBars();
	double getGlobalPositionInQuarters();
	BarsAndBeats getGlobalPositionInBarsAndBeats();
//	double getGlobalPositionInQuartersFromGlobalPositionInFloatBars(double aGlobalPositionInFloatBars);

	int getLocalPositionInBars(Mu aAncestorMu);
	double getLocalPositionInQuarters(Mu aAncestorMu);
	BarsAndBeats getLocalPositionInBarsAndBeats(Mu aAncestorMu);
	
	BarsAndBeats getLocalPositionInBarsAndBeats(BarsAndBeats aGlobalPositionInBarsAndBeats);

	void setPositionInBars(int aPositionInBars);
	
	String positionToString();
	
	void movePosition(int aOffsetInBars);
	public void movePosition(double aOffsetInQuarters);
	public void movePosition(BarsAndBeats aOffsetInBarsAndBeats);
	void addAMuToAParentWithSamePositionModel(Mu aChildMu, Mu aParentMu, HashMap<Mu, Mu> oldMuNewMuMap);
	void addAMuToAParentWithSamePositionModelButOnlyBeginningOfParent(Mu deepCopy, Mu aParentMu);	// end of sibling relations will get converted to beginning of parent in bars and beats
	String getContentString();
	Element getXMLFileContent(Document document);
	
	
	
		
	
	

	

	

	

}
