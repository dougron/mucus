package test.java.com.dougron.mucus.mu_framework.musicxml_maker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MuXMLMaker;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;


public class NoteSplitSevenEightTwoBar
{

	@Test
	public void test()
	{
		Mu mu = makeMu();
		String correctResult = "part=008\n" + 
				"measure=1\n" + 
				"time signature=7/8\n" + 
				"key signature=1\n" + 
				"division=2\n" + 
				"note: notes=56, offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=2\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=3\n" + 
				"note: notes=56, offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=4\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=5\n" + 
				"note: notes=56, offset=0.0 length=1.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=6\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=7\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=8\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=9\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=10\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=11\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=12\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=13\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=14\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=15\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=16\n" + 
				"note: notes=56, offset=0.0 length=0.5 tieEnd\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=17\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=18\n" + 
				"note: notes=56, offset=0.0 length=1.0 tieEnd\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=19\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=20\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=21\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=22\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=23\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=24\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=25\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=26\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=27\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=28\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=29\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=0.5\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=30\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=31\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=32\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=33\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=34\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=35\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=36\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=37\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=38\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=39\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=40\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=41\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=42\n" + 
				"note: notes=56, offset=0.0 length=0.5 tieEnd\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=43\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=44\n" + 
				"note: notes=56, offset=0.0 length=1.0 tieEnd\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=45\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=46\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=47\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=48\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=49\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=50\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=51\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=52\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=53\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=54\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=55\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=56\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=57\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=58\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=59\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=60\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=61\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=62\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=63\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=64\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=65\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=66\n" + 
				"note: notes=56, offset=0.0 length=0.5 tieEnd\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=67\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=68\n" + 
				"note: notes=56, offset=0.0 length=1.0 tieEnd\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=69\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=70\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=71\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=72\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=73\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=74\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=75\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=76\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=77\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd tieStart\n" + 
				"measure=78\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=79\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=80\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=81\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=82\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=83\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=1.5\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=84\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=85\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0\n" + 
				"measure=86\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=87\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieStart\n" + 
				"measure=88\n" + 
				"note: notes=56, offset=0.0 length=0.5 tieEnd\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=89\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieStart\n" + 
				"measure=90\n" + 
				"note: notes=56, offset=0.0 length=1.0 tieEnd\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=91\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieStart\n" + 
				"measure=92\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=93\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieStart\n" + 
				"measure=94\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=95\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieStart\n" + 
				"measure=96\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=97\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieStart\n" + 
				"measure=98\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=99\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieStart\n" + 
				"measure=100\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=101\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=102\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=103\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.0\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=104\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=105\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5\n" + 
				"measure=106\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=107\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5 tieStart\n" + 
				"measure=108\n" + 
				"note: notes=56, offset=0.0 length=0.5 tieEnd\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=109\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5 tieStart\n" + 
				"measure=110\n" + 
				"note: notes=56, offset=0.0 length=1.0 tieEnd\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=111\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5 tieStart\n" + 
				"measure=112\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=113\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5 tieStart\n" + 
				"measure=114\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=115\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5 tieStart\n" + 
				"measure=116\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=117\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5 tieStart\n" + 
				"measure=118\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=119\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5 tieStart\n" + 
				"measure=120\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=121\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=0.5\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=122\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=123\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0\n" + 
				"measure=124\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=125\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieStart\n" + 
				"measure=126\n" + 
				"note: notes=56, offset=0.0 length=0.5 tieEnd\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=127\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieStart\n" + 
				"measure=128\n" + 
				"note: notes=56, offset=0.0 length=1.0 tieEnd\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=129\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieStart\n" + 
				"measure=130\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=131\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieStart\n" + 
				"measure=132\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=133\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieStart\n" + 
				"measure=134\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=135\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieStart\n" + 
				"measure=136\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=137\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0 tieStart\n" + 
				"measure=138\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=139\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5\n" + 
				"measure=140\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=141\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5 tieStart\n" + 
				"measure=142\n" + 
				"note: notes=56, offset=0.0 length=0.5 tieEnd\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=143\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5 tieStart\n" + 
				"measure=144\n" + 
				"note: notes=56, offset=0.0 length=1.0 tieEnd\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=145\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5 tieStart\n" + 
				"measure=146\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=147\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5 tieStart\n" + 
				"measure=148\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=149\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5 tieStart\n" + 
				"measure=150\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=151\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5 tieStart\n" + 
				"measure=152\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=153\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5 tieStart\n" + 
				"measure=154\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieEnd tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=155\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=156\n" + 
				"note: notes=56, offset=0.0 length=0.5\n" + 
				"rest: offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=157\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=158\n" + 
				"note: notes=56, offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=159\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=160\n" + 
				"note: notes=56, offset=0.0 length=1.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=161\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=162\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=163\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=164\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=165\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=166\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=167\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=168\n" + 
				"note: notes=56, offset=0.0 length=1.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=169\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=170\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=0.5\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=171\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=172\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=173\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=174\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=175\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=176\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=177\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=178\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=179\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=180\n" + 
				"rest: offset=0.0 length=0.5\n" + 
				"note: notes=56, offset=0.5 length=1.0 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=181\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=182\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=2.0\n" + 
				"measure=183\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=184\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=0.5 tieEnd\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=185\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=186\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.0 tieEnd\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=187\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=188\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=1.5 tieEnd\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=189\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=190\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"note: notes=56, offset=1.0 length=0.5 tieStart\n" + 
				"note: notes=56, offset=1.5 length=2.0 tieEnd\n" + 
				"measure=191\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=192\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=0.5\n" + 
				"rest: offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=193\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=194\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=195\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=196\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=1.5\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=197\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=198\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"note: notes=56, offset=1.5 length=2.0\n" + 
				"measure=199\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=200\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=0.5\n" + 
				"rest: offset=2.5 length=1.0\n" + 
				"measure=201\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=202\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.0\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=203\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=204\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=0.5\n" + 
				"note: notes=56, offset=2.0 length=1.5\n" + 
				"measure=205\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=206\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=0.5\n" + 
				"rest: offset=3.0 length=0.5\n" + 
				"measure=207\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=208\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"note: notes=56, offset=2.5 length=1.0\n" + 
				"measure=209\n" + 
				"rest: offset=0.0 length=3.5\n" + 
				"measure=210\n" + 
				"rest: offset=0.0 length=1.0\n" + 
				"rest: offset=1.0 length=0.5\n" + 
				"rest: offset=1.5 length=1.0\n" + 
				"rest: offset=2.5 length=0.5\n" + 
				"note: notes=56, offset=3.0 length=0.5\n";
		
		String result = MuXMLMaker.makeTestOutput(mu);
		assertEquals(correctResult, result);
	}
	
	
	
	private Mu makeMu() 
	{
		Mu mu = new Mu("008");
		mu.setLengthInBars(1); 				// wont load BarsAndBeats Mus unless this is set so its not in lengthFromChildren mode.
		sevenEightTwoBarTest(mu);
		return mu;
	}
	
	
	
	private static void sevenEightTwoBarTest(Mu mu) 
	{
		int barIndex = 0;
		mu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(TimeSignature.SEVEN_EIGHT_322));
		for (double pos = 0.0; pos < 7.0; pos += 0.5) 
		{			
			for (double end = pos + 0.5; end <= 7.0; end += 0.5)
			{
				Mu numu = new Mu("note" + barIndex);
				numu.addMuNote(new MuNote(56, 64));
				numu.setLengthInQuarters(end - pos);
				mu.addMu(numu, new BarsAndBeats(barIndex, pos));
				barIndex++;
				barIndex++;
			}
		}
		mu.setLengthInBars(barIndex);
		mu.setXMLKey(+1);
	}
	


}
