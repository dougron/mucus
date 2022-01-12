package main.java.com.dougron.mucus.algorithms.bot_personality;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_distance_measure.ParameterDistanceMeasure;

public class BotPersonality
{

	
	private double safeRisky;
	private double pleaseProvoke;

	public BotPersonality(double aSafeRisky, double aPleaseProvoke)
	{
		safeRisky = aSafeRisky;
		pleaseProvoke = aPleaseProvoke;
	}
	
	
	public String toString()
	{
		return "safe: " + safeRisky + " :risky\nplease: " + pleaseProvoke + " :provoke";
	}
	
	
	
	public JSONObject asJSON()
	{
		JSONObject json = new JSONObject();
		json.put("safeRisky", safeRisky);
		json.put("pleaseProvoke", pleaseProvoke);	
		return json;
	}

	
	
	public double getSafeRisky()
	{
		return safeRisky;
	}

	
	
	public void setSafeRisky(double safeRisky)
	{
		this.safeRisky = safeRisky;
	}

	
	
	public double getPleaseProvoke()
	{
		return pleaseProvoke;
	}

	
	
	public void setPleaseProvoke(double pleaseProvoke)
	{
		this.pleaseProvoke = pleaseProvoke;
	}
	
	
	
	public MucusInteractionData getBestOption
	(
			ArrayList<MucusInteractionData> optionList,
			ArrayList<MucusInteractionData> approvedList,
			ArrayList<MucusInteractionData> rejectedList, 
			RandomMelodyGenerator aRmg
			)
	{
		ArrayList<ApprovedRejectedDistanceMeasure> list 
		= getApprovedRejectedDistanceMeasure(optionList, approvedList, rejectedList, aRmg); 
		addDistancePercentagesToDistanceMeasure(list);
		addProvocationFactorSortScore(list);
		return getTopOfSortedList(list);
	}


	
	protected MucusInteractionData getTopOfSortedList (
			ArrayList<ApprovedRejectedDistanceMeasure> list)
	{
		Collections.sort(list, ApprovedRejectedDistanceMeasure.provocationDistanceComparator);
		JSONArray json = new JSONArray();
		for (ApprovedRejectedDistanceMeasure ardm: list)
		{
			JSONObject json2 = new JSONObject();
			json2.put("bot_variation", ardm.getMid().getThinkingJSON().get("bot_variation"));
			json2.put("approvedDistance", ardm.getApprovedDistance());
			json2.put("rejectedDistance", ardm.getRejectedDistance());
			json2.put("approvedDistancePercent", ardm.getApprovedDistancePercent());
			json2.put("rejectedDistancePercent", ardm.getRejectedDistancePercent());
			json2.put("provocationDistanceScore", ardm.getProvocationDistanceScore());
			json.put(json2);
		}
		MucusInteractionData mid = list.get(0).getMid();
		mid.appendJSONThinking("sorted_distance_scores", json);
		return mid;
	}


	protected void addProvocationFactorSortScore (
			ArrayList<ApprovedRejectedDistanceMeasure> list)
	{
		for (ApprovedRejectedDistanceMeasure ardm: list)
		{
			double approvedProximityValue = Math.abs(ardm.getApprovedDistancePercent() - pleaseProvoke) * -1.0;
			approvedProximityValue = Math.cos(approvedProximityValue);
			ardm.setProvocationFactorDistanceScore(approvedProximityValue * ardm.getRejectedDistancePercent());
		}
		
	}


	protected void addDistancePercentagesToDistanceMeasure (
			ArrayList<ApprovedRejectedDistanceMeasure> list)
	{
		double lowApproved = 1000000;
		double highApproved = 0;
		double lowRejected = 1000000;
		double highRejected = 0;
		for (ApprovedRejectedDistanceMeasure ardm: list)
		{
			if (ardm.getApprovedDistance() < lowApproved) lowApproved = ardm.getApprovedDistance();
			if (ardm.getApprovedDistance() > highApproved) highApproved = ardm.getApprovedDistance();
			if (ardm.getRejectedDistance() < lowRejected) lowRejected = ardm.getRejectedDistance();
			if (ardm.getRejectedDistance() > highRejected) highRejected = ardm.getRejectedDistance();			
		}
		double approvedRange = highApproved - lowApproved;
		double rejectedRange = highRejected - lowRejected;
		for (ApprovedRejectedDistanceMeasure ardm: list)
		{
			ardm.setApprovedDistancePercent((ardm.getApprovedDistance() - lowApproved) / approvedRange);
			ardm.setRejectedDistancePercent((ardm.getRejectedDistance() - lowRejected) / rejectedRange);
		}		
	}


	
	private ArrayList<ApprovedRejectedDistanceMeasure> getApprovedRejectedDistanceMeasure 
	(
			ArrayList<MucusInteractionData> optionList,
			ArrayList<MucusInteractionData> approvedList,
			ArrayList<MucusInteractionData> rejectedList,
			RandomMelodyGenerator aRmg
			)
	{
		ArrayList<ApprovedRejectedDistanceMeasure> list = new ArrayList<ApprovedRejectedDistanceMeasure>();
		for (MucusInteractionData mid: optionList)
		{
			RMRandomNumberContainer rndContainer = mid.getRndContainer();
			
			double approvedDistance = 10000000;		// arbitrarily large
			for (MucusInteractionData mid2: approvedList)
			{
				RMRandomNumberContainer rndContainer2 = mid2.getRndContainer();
				double tempDistance = ParameterDistanceMeasure.getSquaredDistanceMeasure(rndContainer, rndContainer2, aRmg);
				if (tempDistance < approvedDistance) approvedDistance = tempDistance;
			}
			
			double rejectededDistance = 10000000;		// arbitrarily large
			for (MucusInteractionData mid2: approvedList)
			{
				RMRandomNumberContainer rndContainer2 = mid2.getRndContainer();
				double tempDistance = ParameterDistanceMeasure.getSquaredDistanceMeasure(rndContainer, rndContainer2, aRmg);
				if (tempDistance < rejectededDistance) rejectededDistance = tempDistance;
			}
			
			list.add(new ApprovedRejectedDistanceMeasure(mid, approvedDistance, rejectededDistance));
		}
		return list;
	}
	
}



