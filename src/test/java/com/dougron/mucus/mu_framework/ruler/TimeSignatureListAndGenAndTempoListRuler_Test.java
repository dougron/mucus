package test.java.com.dougron.mucus.mu_framework.ruler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.ruler.TimeSignatureListAndGenAndTempoListRuler;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

class TimeSignatureListAndGenAndTempoListRuler_Test
{

	@Test
	void deepcopy_returns_equivalent_copy()
	{
		TimeSignatureListAndGenAndTempoListRuler ruler = new TimeSignatureListAndGenAndTempoListRuler();
		ruler.setLengthInBarsAndBeats(new BarsAndBeats(8, 1.0));
		ruler.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		ruler.getTSGenAndMap().addTimeSignature(5, TimeSignature.SEVEN_EIGHT_322);
		ruler.addTempoChange(135, new BarsAndBeats(2, 3.0));
//		System.out.println(ruler.getTimeSignatureList().toString());
//		System.out.println(ruler.contentString());
		assertEquals(ruler.contentString(), ruler.getDeepCopy().contentString());
	}
	
	
	@Test
	void set_tempo() throws Exception
	{
		TimeSignatureListAndGenAndTempoListRuler ruler = new TimeSignatureListAndGenAndTempoListRuler();
		ruler.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.THREE_FOUR));
		ruler.setTempo(114.0, 0);
		assertEquals(114.0, ruler.getTempoAtBarsAndBeatsPosition(0, 0.0));
	}
	
	
	
	@Test
	void parameter_object_list_returns_correct_content() throws Exception
	{
		TimeSignatureListAndGenAndTempoListRuler ruler = new TimeSignatureListAndGenAndTempoListRuler();
		ruler.setLengthInBarsAndBeats(new BarsAndBeats(8, 1.0));
		TimeSignature[] tsArr = new TimeSignature[] {TimeSignature.FOUR_FOUR, TimeSignature.FIVE_EIGHT_32, TimeSignature.THREE_FOUR};
		ruler.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(tsArr));
		ruler.getTSGenAndMap().addTimeSignature(5, TimeSignature.SEVEN_EIGHT_322);
		ruler.addTempoChange(135, new BarsAndBeats(2, 3.0));
		Object[] arr = ruler.getParameterObjectArray();
		for (Object o: arr) System.out.println(o);
	}
	
	
	
	
}
