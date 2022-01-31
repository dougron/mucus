package test.java.com.dougron.mucus.algorithms;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.RMG_001;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.RandomMelodyGenerator;
import main.java.da_utils.render_name.RenderName;

public class RandomMelodyOptionsToFileTest
{
	public static void main(String[] args)
	{
		String timeStamp = RenderName.dateAndTime();
		RandomMelodyGenerator rmg = RMG_001.getInstance();
		String path = rmg.saveStaticVariablesAsTextFile(timeStamp);
		System.out.println(path);
	}
}
