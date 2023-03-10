package main.java.com.dougron.mucus.mucus_utils.mu_length_in_floatbars;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;

public class MuLengthInFloatBars
{

	public static double getLengthInFloatBars(Mu aMu)
	{
		int endBarIndex = aMu.getGlobalBarIndexOfEnd();
		TimeSignature ts = aMu.getTimeSignature(endBarIndex);
		int startBarIndex = aMu.getGlobalPositionInBars();
		double lengthInFloatBars = endBarIndex - startBarIndex;
		double lengthOfEndBar = ts.getLengthInQuarters();
		BarsAndBeats lengthInBarsAndBeats = aMu.getLengthInBarsAndBeats();
		lengthInFloatBars += lengthInBarsAndBeats.getOffsetInQuarters() / lengthOfEndBar;
		return lengthInFloatBars;
	}

}
