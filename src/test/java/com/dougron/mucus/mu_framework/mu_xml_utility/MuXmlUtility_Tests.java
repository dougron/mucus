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
		Mu mu = new Mu("parent");
		mu.setLengthInBars(2);
		Mu child = new Mu("child");
		child.setLengthInQuarters(2.5);
		child.addMuNote(64, 48);
		mu.addMu(child, BarsAndBeats.at(0, 3.0));
		String xmlString = MuXMLUtility.getMuAsXmlString(mu);
//		System.out.println(xmlString);
		assertThat(xmlString.length()).isGreaterThan(0);
	}

}
