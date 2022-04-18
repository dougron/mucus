package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureList;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGeneratorFactory;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;
import main.java.da_utils.time_signature_utilities.time_signature_map.TimeSignatureMap;
import test.java.com.dougron.mucus.mu_framework.key_signature_utilities.KeySignatureMap;

/*
 * interface class for music_xml
 */

public class MuXMLMaker {
	
	
	
	private static final String NOT_VALID_MU_MESSAGE = "not a valid Mu";
	private static ArrayList<MXML_BarLineStyleForMu> barlines = new ArrayList<MXML_BarLineStyleForMu>();
	
	private static MuTag[] scorePartOrder = new MuTag[] 
			{
					MuTag.PART_MELODY, 
					MuTag.PART_CHORDS, 
					MuTag.PART_BASS, 
					MuTag.PART_1, 
					MuTag.PART_2, 
					MuTag.PART_3,
					MuTag.PART_4,
			};
	private static MuTag[] defaultScorePartClef = new MuTag[] 
			{
					MuTag.TREBLE_CLEF, 
					MuTag.TREBLE_CLEF, 
					MuTag.BASS_CLEF, 
					MuTag.TREBLE_CLEF, 
					MuTag.TREBLE_CLEF, 
					MuTag.TREBLE_CLEF,
					MuTag.TREBLE_CLEF
			};
	
	/* for mu with single part */
	public static String makeXML(Mu mu, String path) 
	{
		if (isValidMu(mu))
		{
			mu = makeAllGlobalPositionsInQuartersPositive(mu);
			MXML_Score score = makeMXMLScore(mu);	
			addPart(mu, score);	
			addScoreLevelNotations(mu, score);
			score.makeXML(path);
			return "MuXMLMaker.makeXML() done";
		}
		else
		{
			return NOT_VALID_MU_MESSAGE;
		}	
	}
	
	
	
	public static String makeMultiPartXML(Mu aMu, String aPath)
	{
		if (isValidMu(aMu))
		{
			int offsetInBars = getBarOffsetForAllNotesToHavePositivePositions(aMu);
			Mu newBigMu = getMuWithPickupBars(aMu, offsetInBars);
						
			MXML_Score score = makeMXMLScore(newBigMu);	
			HashMap<MuTag, ArrayList<Mu>> partMap = getOrderedPartMus(newBigMu);
			
			int index = 0;
			for (MuTag key: scorePartOrder)
			{
				ArrayList<Mu> partMus= partMap.get(key);
				if (partMus.size() > 0)
				{
					MXML_Part part = addPart(partMus, score, partMus.get(0).getName(), newBigMu);
					part.setDefaultClef(defaultScorePartClef[index]);
					addScoreLevelNotations(partMus, part);
				}	
				index++;
			}
			score.makeXML(aPath);
			return "MuXMLMaker.makeMultiPartXML() done";
		}
		else
		{
			return NOT_VALID_MU_MESSAGE;
		}	
	}



	private static Mu getMuWithPickupBars(Mu aMu, int offsetInBars)
	{
		Mu newBigMu = new Mu("bigMuWithOffset");
		TimeSignature firstTimeSignature = aMu.getTimeSignature(0);
		newBigMu.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(firstTimeSignature));
		Chord firstChord = aMu.getChordAtGlobalPosition(new BarsAndBeats(0, 0.0));
		newBigMu.setChordListGenerator(new SingleChordGenerator(firstChord));
		newBigMu.addMu(aMu, offsetInBars);
		return newBigMu;
	}



	private static HashMap<MuTag, ArrayList<Mu>> getOrderedPartMus(Mu aMu)
	{
		HashMap<MuTag, ArrayList<Mu>> map = new HashMap<MuTag, ArrayList<Mu>>();
		for (MuTag muTag: scorePartOrder)
		{
			ArrayList<Mu> partMus = new ArrayList<Mu>();
			List<Mu> allMus = aMu.getAllMus();
			for (Mu mu: allMus)
			{
				if (mu.hasTag(muTag)) partMus.add(mu);
			}
			map.put(muTag, partMus);
		}		
		return map;
	}
	
	
// privates ----------------------------------------------------------------------------------
	
	
	
	private static void addScoreLevelNotations(ArrayList<Mu> aMuList, MXML_Part aPart)
	{
		for (Mu moos: aMuList)
		{
			List<Mu> mus = moos.getListOfMusWithBarlineInfo();
			for (Mu mu: mus)
			{
				aPart.addMusWithOtherNotationIndicators(mu);
			}	
		}		
	}
	


	private static void addScoreLevelNotations(Mu aMu, MXML_Score score)
	{
		List<Mu> mus = aMu.getListOfMusWithBarlineInfo();
		for (Mu mu: mus)
		{
			for (MXML_Part part: score.getMXMLParts())
			{
				part.addMusWithOtherNotationIndicators(mu);
			}
		}		
	}
	
	
	
	private static int getBarOffsetForAllNotesToHavePositivePositions(Mu aMu)
	{
		
		List<Mu> list = aMu.getMusWithNotes();
		Collections.sort(list, Mu.globalPositionInQuartersComparator);
//		for (Mu mu: list)
//		{
//			System.out.println(mu.getGlobalPositionInBarsAndBeats().toString());
//		}
		if (list.size() > 0 && list.get(0).getGlobalPositionInBarsAndBeats().getBarPosition() < 0)
		{
			return list.get(0).getGlobalPositionInBarsAndBeats().getBarPosition() * -1;
		}
		return 0;
	
//		if (!hasNotesWithNegativePosition(aMu)) return 0;
//		
//		Mu nuParent = new Mu(aMu.getName());
//		TimeSignature firstTimeSignature = aMu.getTimeSignatureList().getTimeSignature(0);
//		int barPos = 1;
//		while (true)
//		{
//			nuParent.addMu(aMu, barPos);
////			if (aMu.hasTag(MuTag.PRINT_CHORDS)) nuParent.addTag(MuTag.PRINT_CHORDS);
//			if (!hasNotesWithNegativePosition(nuParent)) return barPos;
//			barPos++;
//			nuParent.getMus().clear();
//		}	
	}




	private static Mu makeAllGlobalPositionsInQuartersPositive(Mu mu)
	{
//		List<Mu> musWithNotes;
//		boolean hasNegatives = hasNotesWithNegativePosition(mu);
		if (!hasNotesWithNegativePosition(mu)) return mu;
		
		mu.setHasLeadingDoubleBar(true);
		Mu nuParent = new Mu(mu.getName());
		TimeSignature firstTimeSignature = mu.getTimeSignatureList().getTimeSignature(0);
		nuParent.setTimeSignatureGenerator(TimeSignatureListGeneratorFactory.getGenerator(firstTimeSignature));
		nuParent.setXMLKey(mu.getKeySignatureMap().getKey());
		int barPos = 1;
		while (true)
		{
			nuParent.addMu(mu, barPos);
			if (mu.hasTag(MuTag.PRINT_CHORDS)) nuParent.addTag(MuTag.PRINT_CHORDS);
			if (!hasNotesWithNegativePosition(nuParent)) return nuParent;
			barPos++;
			nuParent.getMus().clear();
		}
	}



	private static boolean hasNotesWithNegativePosition(Mu mu)
	{
		List<Mu> musWithNotes = mu.getMusWithNotes();
		boolean hasNegatives = false;
		for (Mu mun: musWithNotes) 
		{
			if (mun.getGlobalPositionInQuarters() < 0)
			{
				hasNegatives = true;
				break;
			}
		}
		return hasNegatives;
	}



	public static String makeTestOutput(Mu mu)
	{
		if (isValidMu(mu))
		{
			MXML_Score score = makeMXMLScore(mu);		
			addPart(mu, score);		
			return score.makeTestOutput();
		}
		return NOT_VALID_MU_MESSAGE;
	}


	
	private static boolean isValidMu(Mu mu)
	{
		if (mu.getLengthInBars() > 0) return true;
		return false;
	}



	private static void addPart(Mu mu, MXML_Score score)
	{
		score.addMXMLPart(new MXML_Part(mu.getName()));
//		List<Mu> mus;
		addMusWithNotes(mu, score);
		addMusWithOnlyAnnotations(mu, score);
		addChordListIfPrintChordTagIsPresent(mu, score);
	}
	
	
	
	private static MXML_Part addPart(ArrayList<Mu> aMuList, MXML_Score aScore, String aName, Mu muWithChordList)
	{
		MXML_Part part = new MXML_Part(aName);
		aScore.addMXMLPart(part);
		addMusWithNotes(aMuList, part);
		addMusWithOnlyAnnotations(aMuList, part);
		for (Mu mu: aMuList) 
		{
			if (mu.hasTag(MuTag.PRINT_CHORDS))
			{
				part.addChordListForPrint(muWithChordList.getChordList());
			}
		}
		return part;
	}



	
	private static void addChordListIfPrintChordTagIsPresent(Mu mu, MXML_Score score)
	{
		if (mu.hasTag(MuTag.PRINT_CHORDS))
		{
			score.addChordListToPartForPrinting(mu.getChordList(), mu.getName());
		}
	}



	private static void addMusWithOnlyAnnotations(Mu mu, MXML_Score score)
	{
		List<Mu> mus;
		mus = mu.getMusWithAnnotationsButNoNotes();
		for (Mu muu: mus) 
		{
			score.addAnnotatedMuToPart(muu, mu.getName());
		}
	}
	
	
	
	private static void addMusWithOnlyAnnotations(ArrayList<Mu> aMuList, MXML_Part aPart)
	{
		for (Mu mu: aMuList)
		{
			List<Mu> muList = mu.getMusWithAnnotationsButNoNotes();
			for (Mu mu2: muList)
			{
				aPart.addMuWithOnlyAnnotations(mu2);
			}
		}	
	}



	private static void addMusWithNotes(Mu mu, MXML_Score score)
	{
		List<Mu> mus = mu.getMusWithNotes();
		for (Mu muu: mus) 
		{
			score.addMuToPart(muu, mu.getName());
		}
	}
	
	
	
	private static void addMusWithNotes(ArrayList<Mu> aMuList, MXML_Part aPart)
	{
		for (Mu mu: aMuList)
		{
			List<Mu> muList = mu.getMusWithNotes();
			for (Mu mu2: muList)
			{
				aPart.addMus(mu2);
			}
		}	
	}







	private static MXML_Score makeMXMLScore(Mu mu)
	{
		TimeSignatureMap tsm = getTimeSignatureMap(mu.getTimeSignatureList());
		KeySignatureMap ksm = mu.getKeySignatureMap();							
		MXML_Score score = new MXML_Score(tsm, ksm);
		return score;
	}	
	
	
	
	private static TimeSignatureMap getTimeSignatureMap(TimeSignatureList aTimeSignatureList)
	{
		TimeSignatureMap tsm = new TimeSignatureMap();
		Iterator<TimeSignature> tsit = aTimeSignatureList.iterator();
		while (tsit.hasNext())
		{
			tsm.addTimeSignature(tsit.next());
		}
		return tsm;
	}




	
	
	
	/*
	 * level from Mu
	 * placement from MXML_BarLineStyleForMu
	 * barstyle from MXML
	 */
	public static void setBarStyleForMuLevel(int aLevel, int aPlacement, int aBarstyle)
	{
		barlines.add(new MXML_BarLineStyleForMu(aLevel, aPlacement, aBarstyle));
	}
	
	
	public static void clearBarStyles()
	{
		barlines.clear();
	}



	

	
	
	
//	public static void main(String[] args) 
//	{
//		String xmlpath = "D:/Documents/miscForBackup/QuickNastyOutput/";
//		Mu mu = makeMu();
//		
//		
//		System.out.println(mu.toString());
////		MuXMLMaker.makeXML(mu, xmlpath + "MuXMLVoiceAndTieTest_" + RenderName.dateAndTime() + ".musicxml");
//	}
//
//	private static Mu makeMu() {
//		Mu mu = new Mu("shlong");
//		TSMapInterface tsm = new TimeSignatureMap();
////		TimeSignature fourFour = new TimeSignature(4, 4, new SubDivItem(4, 4, 0, 
////				new SubDivItem[] {
////						new SubDivItem(2, 4, 1),
////						new SubDivItem(2, 4, 2)
////				}));
//		tsm.addTimeSignature(TimeSignature.FOUR_FOUR);
//		tsm.addTimeSignature(TimeSignature.FOUR_FOUR);		
//		mu.setTsMap(tsm);
//		mu.setPosition(new BarPositionObject(0, 0.0, mu));
//		
//		Mu mu1 = new Mu("note0");
//		mu1.addMuNote(new MuNote(60, 64));
//		mu1.setPosition(new BarPositionObject(0, 0.0, mu1));
//		mu1.setLengthInQuarters(4.0);
//		mu.addMus(mu1);
//		
//		Mu mu2 = new Mu("note1");
//		mu2.addMuNote(new MuNote(55, 64));
//		mu2.setPosition(new BarPositionObject(0, 1.0, mu2));
//		mu2.setLengthInQuarters(3.0);
//		mu.addMus(mu2);
//		
//		Mu mu3 = new Mu("note2");
//		mu3.addMuNote(new MuNote(64, 64));
//		mu3.setPosition(new BarPositionObject(0, 2.0, mu3));
//		mu3.setLengthInQuarters(2.0);
//		mu.addMus(mu3);
//		return mu;
//	}

}

class MXML_BarLineStyleForMu
{
	//options for placement
	public static final int AT_BEGINNING = 0;
	public static final int AT_END = 1;
	
	private int level;
	
	private int placement;
	private int barstyle;
	
	public MXML_BarLineStyleForMu(int aLevel, int aPlacement, int aBarstyle)
	{
		level = aLevel;
		placement = aPlacement;
		barstyle = aBarstyle;			// from MXML barstyle constants
	}
	
	
	public int getLevel()
	{
		return level;
	}
	

	public int getPlacement()
	{
		return placement;
	}
	

	public int getBarstyle()
	{
		return barstyle;
	}

}
