package main.java.com.dougron.mucus.algorithms.mu_generator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;

class MuG_AnticipationTest
{
	
	private Mu parent;
	private Mu chordTone;
	private MuGenerator mug;

	@BeforeEach
	void setup()
	{
		parent = new Mu("parent");
		chordTone = new Mu("chordtone");
		double aNoteLengthInQuarters = 0.5;
		int aNoteCount = 3;
		mug = new MuG_Anticipation(aNoteCount , aNoteLengthInQuarters);
		parent.addMu(chordTone, 1);
		chordTone.setLengthInQuarters(2.5);
		chordTone.addMuGenerator(mug);
		parent.generate();
		chordTone.addMuNote(new MuNote(57, 72));
	}

	@Test
	void enclosure_creates_correct_number_of_embellishments() throws Exception
	{
		assertEquals(3, chordTone.getMus().size());
	}
}
