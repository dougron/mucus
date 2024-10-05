package main.java.com.dougron.mucus.mucus_output_manager.mu_output_manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

@Data
@With
@AllArgsConstructor
public class PartTrackAndClip
{
	private MuTag partTag;
	private int trackIndex;
	private int clipIndex;
}
