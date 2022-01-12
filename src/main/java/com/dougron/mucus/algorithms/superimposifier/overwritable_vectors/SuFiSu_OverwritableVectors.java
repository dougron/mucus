package main.java.com.dougron.mucus.algorithms.superimposifier.overwritable_vectors;

import java.util.ArrayList;
import java.util.List;

import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiNote;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

public class SuFiSu_OverwritableVectors implements SuFiSu
{

	
	private List<SuFi> sufis;
	private Mu mu;
	protected List<SuFiVector> vectors;


	
	public SuFiSu_OverwritableVectors(Mu aMu)
	{
		sufis = new ArrayList<SuFi>();
		vectors = new ArrayList<SuFiVector>();
		if (!setMu(aMu))
		{
			throw new RuntimeException("Unable to create SuFiSu due to aMu");
		}
	}

	
	
	@Override
	public boolean addSufi(SuFi aSufi)
	{
		boolean wasAdded = false;
		wasAdded = sufis.add(aSufi);
		return wasAdded;
	}

	
	
	@Override
	public Mu getMu()
	{
		return mu;
	}

	
	
	@Override
	public boolean setMu(Mu aNewMu)
	{
		boolean wasSet = false;
		if (aNewMu != null)
		{
			mu = aNewMu;
			wasSet = true;
		}
		return wasSet;
	}

	
	
	@Override
	public void generate()
	{
		for (SuFi sufi: sufis)
		{
			sufi.generate();
		}
		makeMusAndAddToMu();
	}
	
	
	
	public List<SuFi> getSufis()
	{
		return sufis;
	}

	
	
	private void makeMusAndAddToMu()
	{
		boolean isFirstNote = true;
		int pitch = mu.getStartPitch();
		for (SuFiVector sv: vectors)
		{
			if (isFirstNote)
			{
				pitch = getFirstPitch(sv, pitch);
			}
			else
			{
				pitch = getPitch(sv, pitch);
			}
			Mu numu = new Mu("note");
			numu.addMuNote(new MuNote(pitch, 64));
			
			mu.addMu(numu, sv.getPositionInBarsAndBeats());
			if (sv.hasEmbellishmentGenerator()) numu.addSuFi(sv.getEmbellishmentGenerator());
//			numu.generate();
			isFirstNote = false;
		}
		
	}



	private int getPitch(SuFiVector sv, int aPitch)
	{
		int vector = (int) Math.signum(sv.getVector());
		int count = Math.abs(sv.getVector());
		for (int i = 0; i < count; i++)
		{
			aPitch = sv.getIntervalType().getNearestNote(aPitch + vector, vector, mu);
		}
		return aPitch;
	}



	private int getFirstPitch(SuFiVector sv, int aPitch)
	{
		return sv.getIntervalType().getNearestNote(aPitch, 1, mu);
	}



	@Override
	public SuFiNote getLastNote()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public SuFi getSufi(int index)
	{
		SuFi aSufi = sufis.get(index);
		return aSufi;
	}
	
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SuFiSu_OverwritableVectors parent mu=" + mu.getName());
		int index = 0;
		for (SuFi sufi: sufis)
		{
			sb.append("\n   " + index + ")\t" + sufi.toString());
		}
		return sb.toString();
	}

	
	
	public static void main(String[] args)
	{
		Mu mu = new Mu("sufi");
		mu.setLengthInBars(8);
		mu.setStartPitch(55);
		SuFi_SimpleStructureTones sufi = new SuFi_SimpleStructureTones(-0.1, 0.77);
		mu.addSuFi(sufi);
		mu.generate();
		System.out.println(mu.stateOfMusToString());
		
	}
}
