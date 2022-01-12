package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.com.dougron.mucus.mucus_output_manager.continuous_integrator.ContinuousIntegrator;
import render_name.RenderName;
import time_signature_utilities.time_signature.TimeSignature;

public class NoteSplitThreeFourMusicXMLOutput
{

	
	
	public static void main (String[] args)
	{
		Mu mu = makeMu();
		ContinuousIntegrator.makeMusicXML("NoteSplitThreeFourMusicXMLOutput", mu);
		System.out.println("done");
	}
	
	
	
	private static Mu makeMu() 
	{
		Mu mu = new Mu("008");
		mu.setLengthInBars(1); 				// wont load BarsAndBeats Mus unless this is set so its not in lengthFromChildren mode.		
//		oneBarTest(mu);
		secondTest(mu);
		return mu;
	}
	
	
	private static void oneBarTest(Mu mu)
	{
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		Mu numu = new Mu("note");
		mu.addMu(numu, 0.0);
		numu.setLengthInBars(1);
		numu.addMuNote(new MuNote(56, 64));
		
	}
	
	
	
	private static void secondTest(Mu mu) 
	{
		int barIndex = 0;
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		for (double pos = 0.0; pos < 3.0; pos += 0.25) 
		{			
			for (double end = pos + 0.25; end <= 3.0; end += 0.25) 
			{
				Mu numu = new Mu("note" + barIndex);
				numu.addMuNote(new MuNote(56, 64));
				numu.setLengthInQuarters(end - pos);
				mu.addMu(numu, new BarsAndBeats(barIndex, pos));
				barIndex++;
			}
		}
		mu.setLengthInBars(barIndex);
		mu.setXMLKey(+1);
	}
	
	

}
