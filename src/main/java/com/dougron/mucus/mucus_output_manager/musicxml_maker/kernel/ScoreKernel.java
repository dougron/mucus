package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.kernel;

import test.java.com.dougron.mucus.TestingStuff;

public class ScoreKernel {
	
	
//	public static void makeXML(String path, MXML_Score score, TSMapInterface globalTSMap, KeySignatureMap globalKSMap) 
//	{
	   	
//  	}




	
		
	
	
	
	
	public static void main(String[] args)
	{
		String rname =  TestingStuff.getQuickNastyXMLPathString("SplittingRulesTest");
		System.out.println(rname);
		String[] split = rname.split("/");
		System.out.println(split[split.length - 1]);
	}

}
