package test.java.com.dougron.mucus;

import main.java.da_utils.render_name.RenderName;

public class TestingStuff {


	
		
	
	public static String xmlpath = "D:/Documents/miscForBackup/QuickNastyOutput/";
	
	public static String getQuickNastyXMLPathString(String testName) {
		String path = xmlpath + testName + "_" + RenderName.dateAndTime() + ".musicxml";
		return path;
	}
	
	
	
	public static String getQuickNastyXMLPathStringNoTimeStamp(String testName) {
		String path = xmlpath + testName + ".musicxml";
		return path;
	}
}
