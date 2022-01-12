package main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers;

import java.util.ArrayList;

import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.ParameterAndIndex;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;

public interface RNDValueObject
{
	
	
	double getValue();
	double getValue (int aParameterIndex);
	double getValue (int aParameterIndex, int aListIndex);
	ArrayList<Double> getValueList ();
	ArrayList<Double> getValueList (int aParameterIndex);
	
	void add (int aParameterIndex, double aValue);
	void change (ParameterAndIndex pai);
	
	int getValueCount ();
	

	String getTextFileString ();
	ArrayList<ParameterAndIndex> getEqualOpportunityParameterAndIndexList (Parameter aParameter);	
	RNDValueObject deepCopy ();
	RNDValueObject getQuantizedDeepCopy (Parameter aParameter, RandomMelodyGenerator aRmg);
	double getSquaredDistanceMeasure (RNDValueObject aRvo);
	void addSelfToJSON (Parameter aParameterKey, JSONObject aJSONObject);
	Object getAsJSON();
	
}