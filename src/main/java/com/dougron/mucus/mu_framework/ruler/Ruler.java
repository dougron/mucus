package main.java.com.dougron.mucus.mu_framework.ruler;

import java.util.Iterator;
import java.util.stream.Stream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.tempo_list.TempoChange;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureList;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGenerator;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public interface Ruler
{

	//getters
	BarsAndBeats getLengthInBarsAndBeats();
	double getLengthInQuarters();
	double getLengthInSeconds();
	double getPositionInQuarters(double aOffsetInQuarters);
	
	double getTempoAtBarsAndBeatsPosition(int aBarPosition, double aOffsetInQuarters);
	double getTempoAtQuartersPosition(double d);
	double getTempoAtSecondsPosition(double d);
	
	BarsAndBeats getPositionInBarsAndBeats(double positionInQuarters);

	String getTimeSignatureToString();
	Stream<TempoChange> getTempoStream();
	
	
	// setters
	void setLengthInBarsAndBeats(BarsAndBeats aBarsAndBeats);
	
	void setStartTempo(double aTempo);
	void addTempoChange(double aTempo, BarsAndBeats aBarsAndBeats);
	void addTempoChange(BarsAndBeats aBarsAndBeats, Ruler aRuler);
	void addTempoChange(double aTempo, double aPositionInFloatBars);
	void setTimeSignatureGenerator(TimeSignatureListGenerator aGenerator);
	void replaceTimeSignature(int aBarPosition, TimeSignature aTimeSignature);
	void replaceTimeSignature(BarsAndBeats aBarsAndBeats, Ruler aRuler);
	
	Iterator<TimeSignature> getTimeSignatureIterator();
	Iterator<TempoChange> getTempoChangeIterator();
	boolean hasTimeSignature();
	boolean hasTempo();
	TimeSignatureList getTimeSignatureList();
	void setHasTimeSignature(boolean b);
	TimeSignature getTimeSignature(int aBarIndex);
	void setDefaultTimeSignature(TimeSignature aTimeSignature);
	TimeSignatureGenAndMap getTSGenAndMap();
	void clearTimeSignatureMap();
	
	
	double getClosestTactusInQuarters(double aPositionInFloatBars);
	Ruler getDeepCopy();
	Object[] getParameterObjectArray();
	Element getXMLElement(Document document);
	
	
	
	
	
	
	
	
	
	

	

	

	

	
}
