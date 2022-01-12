package main.java.com.dougron.mucus.algorithms.bot_personality;

import java.util.Comparator;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.MucusInteractionData;

class ApprovedRejectedDistanceMeasure
{

	
	
	private MucusInteractionData mid;
	private double approvedDistance;
	private double rejectedDistance;
	private double approvedDistancePercent;
	private double rejectedDistancePercent;
	private double provocationDistanceScore;

	
	
	public ApprovedRejectedDistanceMeasure 
	(
			MucusInteractionData aMid,
			double aApprovedDistance,
			double aRejectededDistance
			)
	{
		mid = aMid;
		approvedDistance = aApprovedDistance;
		rejectedDistance = aRejectededDistance;
	}

	
	
	public ApprovedRejectedDistanceMeasure ()
	{
		// for testing only
	}



	public static Comparator<ApprovedRejectedDistanceMeasure> provocationDistanceComparator 
	= new Comparator<ApprovedRejectedDistanceMeasure>()
			{

				@Override
				public int compare (
						ApprovedRejectedDistanceMeasure o1,
						ApprovedRejectedDistanceMeasure o2)
				{
					if (o1.getProvocationDistanceScore() < o2.getProvocationDistanceScore()) return 1;
					if (o1.getProvocationDistanceScore() > o2.getProvocationDistanceScore()) return -1;
					return 0;
				}		
			};
	
	
	public MucusInteractionData getMid ()
	{
		return mid;
	}

	
	
	public double getApprovedDistance ()
	{
		return approvedDistance;
	}

	
	
	public double getRejectedDistance ()
	{
		return rejectedDistance;
	}



	public double getApprovedDistancePercent ()
	{
		return approvedDistancePercent;
	}



	public void setApprovedDistancePercent (
			double approvedDistancePercent)
	{
		this.approvedDistancePercent = approvedDistancePercent;
	}



	public double getRejectedDistancePercent ()
	{
		return rejectedDistancePercent;
	}



	public void setRejectedDistancePercent (
			double rejectedDistancePercent)
	{
		this.rejectedDistancePercent = rejectedDistancePercent;
	}
	
	
	public void setProvocationFactorDistanceScore (double d)
	{
		provocationDistanceScore = d;
		
	}



	public double getProvocationDistanceScore ()
	{
		return provocationDistanceScore;
	}
	
	
	@Override
	public String toString ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(mid.getThinkingToString() + "\n");
		sb.append("approvedDistance=" + approvedDistance + "\n");
		sb.append("rejectedDistance=" + rejectedDistance + "\n");
		sb.append("approvedDistancePercent=" + approvedDistancePercent + "\n");
		sb.append("rejectedDistancePercent=" + rejectedDistancePercent + "\n");
		sb.append("provocationDistanceScore=" + provocationDistanceScore + "\n");
		return sb.toString();	
	}
	
}