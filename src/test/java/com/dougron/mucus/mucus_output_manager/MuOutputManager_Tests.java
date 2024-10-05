package test.java.com.dougron.mucus.mucus_output_manager;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mucus_output_manager.mu_output_manager.MuOutputManager;
import main.java.com.dougron.mucus.mucus_output_manager.mu_output_manager.PartTrackAndClip;
import main.java.da_utils.render_name.RenderName;

class MuOutputManager_Tests
{
	/*
	 * tested for musicxml output by manually chacking the filesystem
	 * tested for output to Live by checking Live
	 * @Test annotation commented out as is not a fully formed automatable 
	 * integration test cos reasons, mostly would take too long
	 * 
	 * MuOutputManager works as expected at this stage
	 */

//	@Test
	void test()
	{
		MuOutputManager outputManager = MuOutputManager.builder()
				.path("D:/Documents/miscForBackup/honky_tonken_output/")
				.partTrackAndClip(new PartTrackAndClip(MuTag.PART_MELODY, 1, 0))
				.build();
		Mu mu = new Mu("mu");
		mu.setLengthInBars(1);
		mu.addMuNote(69, 72);
		mu.addTag(MuTag.PART_MELODY);
		String timestamp = RenderName.dateAndTime();
//		outputManager.outputToMusicXML(timestamp + "_honky", mu);
//		outputManager.outputToPlayback(timestamp, mu);
	}

}
