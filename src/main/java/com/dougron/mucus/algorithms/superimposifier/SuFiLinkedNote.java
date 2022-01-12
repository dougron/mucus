package main.java.com.dougron.mucus.algorithms.superimposifier;

import java.util.Comparator;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;

public class SuFiLinkedNote
{

	
	private BarsAndBeats positionInBarsAndBeats;
	private int pitch;
	private SuFiLinkedNote linkedNote;
	private double positionInQuarters;

	
	
	public SuFiLinkedNote(BarsAndBeats aPositionInBarsAndBeats, Mu aMu)
	{
		positionInBarsAndBeats = aPositionInBarsAndBeats;
		positionInQuarters = aMu.getPositionInQuarters();
	}



	public void setPitch(int aPitch)
	{
		pitch = aPitch;		
	}



	public void setLinkedNote(SuFiLinkedNote aSufiLinkedNote)
	{
		linkedNote = aSufiLinkedNote;
		
	}



	public SuFiLinkedNote getLinkedNote()
	{
		return linkedNote;
	}



	public int getPitch()
	{
		return pitch;
	}
	
	
	
	public static Comparator<SuFiLinkedNote> positionInQuartersComparantor = new Comparator<SuFiLinkedNote>()
	{

		@Override
		public int compare(SuFiLinkedNote o1, SuFiLinkedNote o2)
		{
			if (o1.getPositionInQuarters() > o2.getPositionInQuarters()) return 1;
			if (o1.getPositionInQuarters() < o2.getPositionInQuarters()) return -1;
			return 0;
		}
		
	};



	public  double getPositionInQuarters()
	{
		return positionInQuarters;
	}
	
	
	
	public  BarsAndBeats getPositionInBarsAndBeats()
	{
		return positionInBarsAndBeats;
	}
	
	
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();	
		 sb.append("SuFiLinkedNote pitch=" + pitch 
				 + " " + positionInBarsAndBeats.toString());
		 if (linkedNote != null)
		 {
			 sb.append(" linked to note at " + linkedNote.getPositionInBarsAndBeats().toString());
		 }
		 return sb.toString();
	}
	
}
