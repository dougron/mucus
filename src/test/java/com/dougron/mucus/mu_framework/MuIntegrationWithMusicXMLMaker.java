package test.java.com.dougron.mucus.mu_framework;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import test.java.com.dougron.mucus.TestingStuff;

/*
 * integrating mu_with_position_relationship.Mu with musicxml_maker
 */


public class MuIntegrationWithMusicXMLMaker
{

	public static void main(String[] args)
	{
		Mu mu = new Mu("parent");
//		mu.setLengthInBars(4);
		mu.addMuAnnotation(new MuAnnotation("hello world", MuAnnotation.TextPlacement.PLACEMENT_ABOVE));
//		mu.addMuNote(new MuNote(64, 96));
		Mu child = new Mu("child");
		child.setLengthInBars(2);
		child.addMuAnnotation(new MuAnnotation("child", MuAnnotation.TextPlacement.PLACEMENT_BELOW));
		mu.addMu(child, 2);
		
		String result = MuXMLMaker.makeTestOutput(mu);
		System.out.println(result);
		
		String path = TestingStuff.getQuickNastyXMLPathString("MuIntegrationWithMusicXMLMaker");
		System.out.println(path);
		MuXMLMaker.makeXML(mu, path);
	}

}
