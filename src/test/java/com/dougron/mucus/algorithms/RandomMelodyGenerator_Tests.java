package test.java.com.dougron.mucus.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.Parameter;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_001;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_002;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMRandomNumberContainer;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyParameterObject;
import main.java.com.dougron.mucus.mu_framework.Mu;

class RandomMelodyGenerator_Tests
{

	@Test
	void randomMelodyRandNumberContainer_deepCopy_does_actually_make_the_same_mu() throws Exception
	{
		Random rnd = new Random();
		RandomMelodyGenerator rmg = RMG_001.getInstance();
		RMRandomNumberContainer por = rmg.getRandomNumberContainer(rnd);
		RandomMelodyParameterObject po = rmg.getParameterObject(por, rnd);
		Mu mu1 = rmg.getMuPhrase(po);
		
		RMRandomNumberContainer por2 = por.deepCopy();
		RandomMelodyParameterObject po2 = rmg.getParameterObject(por2, rnd);
		Mu mu2 = rmg.getMuPhrase(po2);
		
//		System.out.println(mu1.getDeepCopyContentString());
//		System.out.println("--------------------------");
//		System.out.println(mu2.getDeepCopyContentString());
		assertEquals(mu1.getDeepCopyContentString(), mu2.getDeepCopyContentString());
	}
	

	@Test
	void writing_random_number_container_to_file() throws Exception
	{
		Random rnd = new Random();
		RandomMelodyGenerator rmg = RMG_001.getInstance();
		RMRandomNumberContainer por = rmg.getRandomNumberContainer(rnd);
//		RandomMelodyParameterObject po = rmg.getParameterObject(por, rnd);
		por.saveAsTextDocument("testDoc");
		RMRandomNumberContainer newpor = RMRandomNumberContainer.loadFile("testDoc");
//		System.out.println(newpor.toString());
		assertEquals(por.get(Parameter.PHRASE_LENGTH).getValue(), newpor.get(Parameter.PHRASE_LENGTH).getValue());
		assertEquals(por.get(Parameter.START_NOTE).getValue(), newpor.get(Parameter.START_NOTE).getValue());
	}
	

//	@Test
//	void testName () throws Exception
//	{
//		RandomMelodyGenerator rmg = RMG_001.getInstance();
//		Random rnd = new Random();
//		RMRandomNumberContainer rndContainer = rmg.getRandomNumberContainer(rnd);
////		System.out.println("---------------------\n" + rndContainer.toString());
//		RandomMelodyParameterObject po = RMG_001.getInstance().getParameterObject(rndContainer, rnd);
////		System.out.println("---------------------\n" + rndContainer.toString());
//		Mu mu = rmg.getMuPhrase(po);
////		System.out.println(mu.toString());
//	}
	

	@Test
	void rnd_container_written_to_json_file_creates_the_sameOutput_when_read () throws Exception
	{
		String directory = "D:/Documents/miscForBackup/random_csv/";
		String filename = "rndContainserJSON";
		RMRandomNumberContainer rndContainer = RMG_002.getInstance().getRandomNumberContainer(new Random());
		RandomMelodyParameterObject po = RMG_002.getInstance().getParameterObject(rndContainer, new Random());
		rndContainer.saveAsJSON(directory, filename);
		Mu mu1 = RMG_002.getInstance().getMuPhrase(po);
		
		RMRandomNumberContainer rndContainer2 = RMRandomNumberContainer.getRndContainerFromJSONFile(directory + filename + ".rnd_container");
//		System.out.println(rndContainer2.toString());
		RandomMelodyParameterObject po2 = RMG_002.getInstance().getParameterObject(rndContainer2, new Random());
		Mu mu2 = RMG_002.getInstance().getMuPhrase(po2);
		assertEquals(mu1.getDeepCopyContentString(), mu2.getDeepCopyContentString());
		
	}
	
	// not entirely sure how to test this, as json is a one way process with paramaterObject files
	// i.e. no need to get a new parameterObject from a saved file, as can be generated from rndContainer and
	// .statvar file. saved parameterObject files are currently (June 2021) intended for use in datamining 
	// in panadas as they represent actual content rather than relative values as with rndContainer
//	@Test
//	void parameter_object_saves_like_the_python_converter () throws Exception
//	{
//		String directory = "D:/Documents/miscForBackup/random_csv/";
//		String name = "po_write_test";
//		RMRandomNumberContainer rndContainer = RMG_002.getInstance().getRandomNumberContainer(new Random());
//		RandomMelodyParameterObject po = RMG_002.getInstance().getParameterObject(rndContainer, new Random());
//		
//		po.saveToJSON(directory, name);
//	}

	
//	@Test
//	void render_json_statvar () throws Exception
//	{
//		String directory = "D:/Documents/miscForBackup/random_csv/";
//		String filename = "RMG_jsontest";
//		
//		RMG_002.getInstance().saveToJSON(directory, filename);
//	}
	
	
	
	
//	@Test
//	void testName () throws Exception
//	{
//		String directory = "D:/Documents/miscForBackup/random_csv/";
//		String filename = "parameterObject";
//		RMRandomNumberContainer rndContainer = RMG_002.getInstance().getRandomNumberContainer(new Random());
//		RandomMelodyParameterObject po = RMG_002.getInstance().getParameterObject(rndContainer, new Random());
//		po.saveToJSON(directory, filename);
//		
//	}
	
	
	@Test
	void json_returned_from_getProgressionAnalysisJson_contains_correct_chords () throws Exception
	{
		Random rnd = new Random();
		RandomMelodyGenerator rmg = RMG_001.getInstance();
		RMRandomNumberContainer por = rmg.getRandomNumberContainer(rnd);
		RandomMelodyParameterObject po = rmg.getParameterObject(por, rnd);
//		Mu mu1 = rmg.getMuPhrase(po);
		JSONObject json = po.makeJSONObject();
		JSONObject chordAnalysis = json.getJSONObject("chordAnalysis");
		assertTrue(chordAnalysis != null);
		assertTrue(chordAnalysis.get("0.0") != null);
		assertTrue(chordAnalysis.getJSONObject("0.0").get("name") instanceof String);
		
	}
	
	
	
	
	
}
