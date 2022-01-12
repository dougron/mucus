package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import time_signature_utilities.time_signature.TimeSignature;


public class NoteSplitSevenEightOneBar
{

	@Test
	public void test()
	{
		Mu mu = makeMu();
		String correctResult = "part=008\n" + 
				"measure=1\n" + 
				"time signature=7/8\n" + 
				"key signature=1\n" + 
				"division=4\n" + 
				"note: notes=56, offset=0.0 length=0.25\n" + 
				"rest: offset=0.25 length=0.25\n" + 
				"rest: offset=0.5 length=0.5\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=2\n" + 
				"note: notes=56, offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=3\n" + 
				"note: notes=56, offset=0.0 length=0.75\n" + 
				"rest: offset=0.75 length=0.25\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=4\n" + 
				"note: notes=56, offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=5\n" + 
				"note: notes=56, offset=0.0 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.25 tieEnd\n" + 
				"rest: offset=1.25 length=0.25\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=6\n" + 
				"note: notes=56, offset=0.0 length=1.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=7\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.25 tieEnd\n" + 
				"rest: offset=1.75 length=0.25\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=8\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=9\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.75 tieEnd\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=10\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=11\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=12\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=13\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=14\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=15\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.25\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=16\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.5\n" + 
				"rest: offset=0.75 length=0.25\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=17\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=18\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.25 tieEnd\n" + 
				"rest: offset=1.25 length=0.25\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=19\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=20\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.25 tieEnd\n" + 
				"rest: offset=1.75 length=0.25\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=21\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=22\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.75 tieEnd\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=23\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=24\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=25\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=26\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=27\n" + 
				"rest: offset=0.0 length=0.25\n" + 
				"note: notes=56, offset=0.25 length=0.75 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=28\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=0.25\n" + 
				"rest: offset=0.75 length=0.25\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=29\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=0.5\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=30\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.25 tieEnd\n" + 
				"rest: offset=1.25 length=0.25\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=31\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=32\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.25 tieEnd\n" + 
				"rest: offset=1.75 length=0.25\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=33\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=34\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.75 tieEnd\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=35\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=36\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=37\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=38\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=39\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=40\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=41\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.25 tieEnd\n" + 
				"rest: offset=1.25 length=0.25\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=42\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=43\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.25 tieEnd\n" + 
				"rest: offset=1.75 length=0.25\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=44\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=45\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.75 tieEnd\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=46\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=47\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=48\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=49\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=50\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=0.25\n" + 
				"note: notes=56, offset=0.75 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=51\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.25\n" + 
				"rest: offset=1.25 length=0.25\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=52\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=53\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.25 tieEnd\n" + 
				"rest: offset=1.75 length=0.25\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=54\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=55\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.75 tieEnd\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=56\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=57\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=58\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=59\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=60\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=61\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=62\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.25 tieEnd\n" + 
				"rest: offset=1.75 length=0.25\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=63\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=64\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.75 tieEnd\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=65\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=66\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=67\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=68\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=69\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.25\n" + 
				"note: notes=56, offset=1.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=70\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=0.25\n" + 
				"rest: offset=1.75 length=0.25\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=71\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=72\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=0.75\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=73\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=74\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=75\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=1.5\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=76\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=77\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0\n" + 
				"measure=78\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.25\n" + 
				"note: notes=56, offset=1.75 length=0.25\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=79\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.25\n" + 
				"note: notes=56, offset=1.75 length=0.5\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=80\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.25\n" + 
				"note: notes=56, offset=1.75 length=0.75\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=81\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.25\n" + 
				"note: notes=56, offset=1.75 length=0.75 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=82\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.25\n" + 
				"note: notes=56, offset=1.75 length=0.75 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=83\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.25\n" + 
				"note: notes=56, offset=1.75 length=0.75 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=84\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.25\n" + 
				"note: notes=56, offset=1.75 length=0.75 tieStart\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieEnd\n" + 
				"measure=85\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=0.25\n" + 
				"rest: offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=86\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=87\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=88\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.0\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=89\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=90\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5\n" + 
				"measure=91\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.25\n" + 
				"note: notes=56, offset=2.25 length=0.25\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=92\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.25\n" + 
				"note: notes=56, offset=2.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.25 tieEnd\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=93\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.25\n" + 
				"note: notes=56, offset=2.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=94\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.25\n" + 
				"note: notes=56, offset=2.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=2.5 length=0.75 tieEnd\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=95\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.25\n" + 
				"note: notes=56, offset=2.25 length=0.25 tieStart\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieEnd\n" + 
				"measure=96\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=0.25\n" + 
				"rest: offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=97\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=0.5\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=98\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=0.75\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=99\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0\n" + 
				"measure=100\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.25\n" + 
				"note: notes=56, offset=2.75 length=0.25\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=101\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.25\n" + 
				"note: notes=56, offset=2.75 length=0.5\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=102\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.25\n" + 
				"note: notes=56, offset=2.75 length=0.75\n" + 
				"measure=103\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.25\n" + 
				"rest: offset=3.25 length=0.25\n" + 
				"measure=104\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5\n" + 
				"measure=105\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"rest: offset=3.0 length=0.25\n" + 
				"note: notes=56, offset=3.25 length=0.25\n";
		
		String result = MuXMLMaker.makeTestOutput(mu);
		assertEquals(correctResult, result);
	}
	
	
	
	private static Mu makeMu() 
	{
		Mu mu = new Mu("008");
		mu.setLengthInBars(1); 				// wont load BarsAndBeats Mus unless this is set so its not in lengthFromChildren mode.
		sevenEightOneBarTest(mu);   		// 7/8
		return mu;
	}
	
	
	
	private static void sevenEightOneBarTest(Mu mu) 
	{
		int barIndex = 0;
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		for (double pos = 0.0; pos < 3.5; pos += 0.25) 
		{			
			for (double end = pos + 0.25; end <= 3.5; end += 0.25) 
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
