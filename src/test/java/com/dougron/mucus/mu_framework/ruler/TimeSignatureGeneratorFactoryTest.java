package test.java.com.dougron.mucus.mu_framework.ruler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureList;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGenerator;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;


/**
 * this tests the TimeSignatureListGeneratorFactory and related classes:
 * 
 * 	-all classes implementing TimeSignatureGenerator, for now SingleTimeSignature and RepeatingTimeSignature
 * 	-TimeSignatureList
 * 
 * @author dougr
 *
 */
public class TimeSignatureGeneratorFactoryTest
{

	private double doubleResolution = 0.001;


	@Test
	public void nothing()
	{
		TimeSignatureListGenerator tsg = TimeSignatureListGeneratorFactory.getGenerator();
		assertNotNull(tsg);
	}
	
	
	
	@Test
	public void returnedGeneratorIsNotNullObject() throws Exception
	{
		assertFalse(TimeSignatureListGeneratorFactory.getGenerator() == null);
	}
	
	
	@Test
	public void returnedDefaultGeneratorCreatesTimeSignatureListWithOneBar() throws Exception
	{
		TimeSignatureListGenerator tsg = TimeSignatureListGeneratorFactory.getGenerator();
		TimeSignatureList tsl = tsg.getTimeSignatureList(1);
		assertEquals(tsl.getBarCount(), 1);
		assertEquals(tsl.getLengthInQuarters(), 4.0, doubleResolution);
		assertEquals(tsg.getTimeSignatureList(11).getBarCount(), 11);
	}
	
	
	@Test
	public void givenGeneratorWithASevenEightBar_WhenGettingATimeSignatureListLengthOne_ThenLengthInQuartersIsThreePointFive() throws Exception
	{
		TimeSignatureListGenerator tsg = TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322);
		assertEquals(tsg.getTimeSignatureList(1).getLengthInQuarters(), 3.5, doubleResolution);
	}
	
	
	
	@Test
	public void givenGeneratorWithAgumentList78_44_WhenGettingATimeSignatureListOfLengthTwo_ThenLengthInQuartersIsSevenPointFive() throws Exception
	{
		TimeSignatureListGenerator tsg = TimeSignatureListGeneratorFactory.getGenerator(
				new TimeSignature[] {TimeSignature.SEVEN_EIGHT_322, TimeSignature.FOUR_FOUR});
		assertEquals(tsg.getTimeSignatureList(2).getLengthInQuarters(), 7.5, doubleResolution);
	}
	
	
	@Test
	public void givenGeneratorWithAgumentList78_44_WhenGettingLengthInQuartersFromBarIndexOneToThree_ThenLengthInQuartersIsSevenPointFive() throws Exception
	{
		TimeSignatureListGenerator tsg = TimeSignatureListGeneratorFactory.getGenerator(
				new TimeSignature[] {TimeSignature.SEVEN_EIGHT_322, TimeSignature.FOUR_FOUR});
		TimeSignatureList tsl = tsg.getTimeSignatureList(4);
		assertEquals(tsl.getLengthInQuarters(1, 3), 7.5, doubleResolution);
	}

}
