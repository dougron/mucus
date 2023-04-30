package test.java.com.dougron.mucus.mu_framework.mu_xml_utility;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.mu_xml_utility.MuXMLUtility;

class MuXmlUtility_Tests
{

	@Test
	void test()
	{
		Mu mu = getMu();
		String xmlString = MuXMLUtility.getMuAsXmlString(mu);
//		System.out.println(xmlString);
		assertThat(xmlString.length()).isGreaterThan(0);
	}
	
	
	@Test
	void mu_encoded_as_string_is_same_when_decoded_from_string()
	{
		Mu mu = getMu();
		String xmlString = MuXMLUtility.getMuAsXmlString(mu);
		Mu decodedMu = MuXMLUtility.getMuFromXmlString(xmlString);
		assertThat(mu.toString()).isEqualTo(decodedMu.toString());
		System.out.println(mu.toString());
		System.out.println(decodedMu.toString());
	}
	
	
	private Mu getMu()
	{
		Mu mu = new Mu("parent");
		mu.setLengthInBars(2);
		Mu child = new Mu("child");
		child.setLengthInQuarters(2.5);
		child.addMuNote(64, 48);
		mu.addMu(child, BarsAndBeats.at(0, 3.0));
		return mu;
	}
	
	

}
