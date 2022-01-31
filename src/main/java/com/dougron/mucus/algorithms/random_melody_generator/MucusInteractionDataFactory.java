package main.java.com.dougron.mucus.algorithms.random_melody_generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.common.base.Preconditions;

import main.java.com.dougron.mucus.algorithms.mu_chord_tone_and_embellishment.ChordToneAndEmbellishmentTagger;
import main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics.MuZzajDynamics;
import main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics.lstm_handler.MuZzajDynamics_LSTM_256_256;
import main.java.com.dougron.mucus.algorithms.part_generators.bass_part_generator.BassPartGenerator;
import main.java.com.dougron.mucus.algorithms.part_generators.chord_part_generator.ChordPartGenerator;
import main.java.com.dougron.mucus.algorithms.part_generators.drum_part_generator.DrumPartGenerator;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.parameter_specific_feedback.feedback_objects.FeedbackObject;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RNDValueObject;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RndContainerScoreWrapper;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.da_utils.combo_variables.IntAndString;

public class MucusInteractionDataFactory
{


	private static int phraseCount = 0;
	private static final int ACCENT_DYNAMIC = 96;
	private static final int NON_ACCENT_DYNAMIC = 32;
//	private static final double SOME_SMALL_AMOUNT_BELOW_WHICH_NOTES_COULD_BE_SEEN_TO_BE_CONTEMPORANEOUS = 0.005;
//	private static final double DYNAMIC_ACCENT_THRESHOLD = 0.5;
	private static final MuZzajDynamics dynamicsModel = new MuZzajDynamics_LSTM_256_256();
	
	

	public static MucusInteractionData generateUserOption
	(
			MucusInteractionData aMid, 
			Random rnd,
			int aUserOptionCount,
			double aMinimumMutation
			)
	{		
		Preconditions.checkArgument(aMid.getRandomMelodyGenerator() != null, "MID with null RandomMelodyGenerator error");
		Preconditions.checkArgument(aMid.getRndContainer() != null, "MID with null rndContainer error");
		Preconditions.checkArgument(aMid.getParameterSpecificFeedbackList() != null, "MID with no ParameterSpecificFeedback error");
		
		ArrayList<RndContainerScoreWrapper> rcswList = makeUserRndContainerList(aMid, rnd, aUserOptionCount, aMinimumMutation);
		Collections.sort(rcswList, RndContainerScoreWrapper.scoreComparator);
		StringBuilder sb = makeGenerateUserOptionsThinking(rcswList);
		JSONArray listOfScores = makeListOfScores(rcswList);
		
		MucusInteractionData mid = getMid(rcswList.get(0).rndContainer, "user", aMid.getRandomMelodyGenerator(), rnd);
		mid.setRndContainer(rcswList.get(0).rndContainer);
		mid.appendThinking(sb.toString());
		mid.appendJSONThinking("list_of_scores", listOfScores);
		return mid;
	}
	
	
	
	private static JSONArray makeListOfScores (
			ArrayList<RndContainerScoreWrapper> rcswList)
	{
//		List<JSONObject> list = rcswList.stream()
//				.map(RndContainerScoreWrapper::getJSON)
//				.collect(Collectors.toList());
		JSONArray arr = new JSONArray(
		rcswList.stream()
			.map(RndContainerScoreWrapper::getJSON)
			.collect(Collectors.toList()));

		return arr;
	}



	public static MucusInteractionData getMid
	(
			RMRandomNumberContainer rndContainer, 
			String agentName, 
			RandomMelodyGenerator rmg,
			Random rnd
			)
	{
		Mu parent = new Mu(agentName + "_" + phraseCount);
		phraseCount  ++;
		parent.addTag(MuTag.HAS_MULTIPART_CHILDREN);				
		RandomMelodyParameterObject po = rmg.getParameterObject(rndContainer, rnd);
		parent.setXMLKey(po.getXmlKey());
		
		Mu mu1 = getMelodyForMu(parent, po, rmg);
		ChordToneAndEmbellishmentTagger.tagSyncopations(mu1);
//		addMucusDynamics(mu1);
//		addBinaryComparisonDynamics(mu1);
//		addBinaryLengthComparisonDynamics(mu1);
		addLSTMMucusDynamics(mu1);
		
		getChordsPartForMu(parent, po, mu1);		
		getBassPartForMu(parent, po, mu1);		
		getDrumPartForMu(parent);
		
		MucusInteractionData mid = new MucusInteractionData(parent, po);
		mid.setRndContainer(rndContainer);
		mid.setRandomMelodyGenerator(rmg);
		return mid;
	}
	
	
	
	private static void addLSTMMucusDynamics (Mu aMu)
	{
		List<Mu> muList = aMu.getMusWithNotes();
		Collections.sort(muList, Mu.globalPositionInQuartersComparator);
		dynamicsModel.getPrediction(muList);
		setVelocitiesOfMuNotesBasedOnDynamicAccentTag(muList);
		
	}



	/*
	 * compares adjacent Mus, and the one with the higher (therefore less prominent) 
	 * beatStrength has its dynamic accent mutag removed
	 */
	private static void addBinaryComparisonDynamics (Mu aMu)
	{
		List<Mu> muList = aMu.getMusWithNotes();
		Collections.sort(muList, Mu.globalPositionInQuartersComparator);
		for (Mu mu: muList)
		{
			mu.addTag(MuTag.DYNAMIC_ACCENT);
		}
		for (int i = 0; i < muList.size() - 1; i++)
		{
			Mu mu1 = muList.get(i);
			Mu mu2 = muList.get(i + 1);
			int strength1 = mu1.getBeatStrengthConsideringSyncopation();
			int strength2 = mu2.getBeatStrengthConsideringSyncopation();
			if (strength1 == strength2)
			{
				// if both are still accented, deaccent the 2nd 1, otherwise only the first is likely to be accented in which case we do nothing
				if (mu1.hasTag(MuTag.DYNAMIC_ACCENT) && mu2.hasTag(MuTag.DYNAMIC_ACCENT))
				{
					mu2.removeMuTagBundleContainingTag(MuTag.DYNAMIC_ACCENT);
				}
			}
			else if (strength1 < strength2)
			{
				mu2.removeMuTagBundleContainingTag(MuTag.DYNAMIC_ACCENT);
			}
			else
			{
				mu1.removeMuTagBundleContainingTag(MuTag.DYNAMIC_ACCENT);
			}
		}
		setVelocitiesOfMuNotesBasedOnDynamicAccentTag(muList);
	}
	
	
	
	/*
	 * compares adjacent Mus, and the shorter one  has its dynamic accent mutag removed
	 */
	private static void addBinaryLengthComparisonDynamics (Mu aMu)
	{
		List<Mu> muList = aMu.getMusWithNotes();
		Collections.sort(muList, Mu.globalPositionInQuartersComparator);
		for (Mu mu: muList)
		{
			mu.addTag(MuTag.DYNAMIC_ACCENT);
		}
		for (int i = 0; i < muList.size() - 1; i++)
		{
			Mu mu1 = muList.get(i);
			Mu mu2 = muList.get(i + 1);
			double length1 = mu1.getLengthInQuarters();
			double length2 = mu2.getLengthInQuarters();
			if (length1 == length2)
			{
				// if both are still accented, deaccent the 2nd 1, otherwise only the first is likely to be accented in which case we do nothing
				if (mu1.hasTag(MuTag.DYNAMIC_ACCENT) && mu2.hasTag(MuTag.DYNAMIC_ACCENT))
				{
					mu2.removeMuTagBundleContainingTag(MuTag.DYNAMIC_ACCENT);
				}
			}
			else if (length1 > length2)
			{
				mu2.removeMuTagBundleContainingTag(MuTag.DYNAMIC_ACCENT);
			}
			else
			{
				mu1.removeMuTagBundleContainingTag(MuTag.DYNAMIC_ACCENT);
			}
		}
		setVelocitiesOfMuNotesBasedOnDynamicAccentTag(muList);
	}



	private static Mu addMucusDynamics (Mu aMu)
	{
		List<Mu> musWithNotes = aMu.getMusWithNotes();
		if (musWithNotes.size() > 7)
		{
			Collections.sort(musWithNotes, Mu.globalPositionInQuartersComparator);
			tagNotesWithDynamicAccentUsing8NearestNotesNN(musWithNotes);
			setVelocitiesOfMuNotesBasedOnDynamicAccentTag(musWithNotes);
		}
		
		return aMu;
	}



	public static void setVelocitiesOfMuNotesBasedOnDynamicAccentTag (
			List<Mu> musWithNotes)
	{
		for (Mu mu: musWithNotes)
		{
			if (mu.hasTag(MuTag.DYNAMIC_ACCENT))
			{
				mu.setVelocity(ACCENT_DYNAMIC);
			}
			else
			{
				mu.setVelocity(NON_ACCENT_DYNAMIC);
			}
		}
	}



//	public static void tagNotesWithDynamicAccentUsing8NearestNotesNN (
//			List<Mu> musWithNotes)
//	{
//		for (int i = 0; i < musWithNotes.size(); i++)
//		{
//			List<Mu> closestMus = getClosestMusByPingPong(i, musWithNotes, 8);
//			if (dynamicFromClosest8Notes(musWithNotes.get(i), closestMus) >= DYNAMIC_ACCENT_THRESHOLD)
//			{
//				musWithNotes.get(i).addTag(MuTag.DYNAMIC_ACCENT);
//			}
//		}
//	}
	
	
	public static void tagNotesWithDynamicAccentUsing8NearestNotesNN (
			List<Mu> musWithNotes)
	{
		List<Mu> sortList = new ArrayList<Mu>(musWithNotes);
		for (Mu mu: musWithNotes)
		{
			Collections.sort(sortList, Mu.getAbsoluteQuartersDistanceComparator(mu.getGlobalPositionInQuarters()));
			dynamicsModel.getPrediction(sortList);
		}
	}




//	private static double dynamicFromClosest8Notes (Mu aMu, List<Mu> closestMus)
//	{
////		MuZzajDynamics_18 model = MuZzajDynamics_18.getInstance();
//		int beatStrength = aMu.getBeatStrength();
//		int syncopation = aMu.hasTag(MuTag.IS_SYNCOPATION) ? 1 : 0;
//
//		double[] positions = closestMus.stream()
//				.mapToDouble(x -> x.getGlobalPositionInQuarters() - aMu.getGlobalPositionInQuarters())
//				.toArray();
//		int[] melodyContours = closestMus.stream()
//				.mapToInt(x -> (int)Math.signum(x.getTopPitch() - x.getTopPitch()))
//				.toArray();
//		return dynamicsModel.getPrediction(beatStrength, syncopation, positions, melodyContours);
//	}



//	private static List<Mu> getClosestMusByPingPong (int index,
//			List<Mu> musWithNotes,
//			int closestNoteCount)
//	{
//		List<Mu> list = new ArrayList<Mu>();
//		int previousIndex = index - 1;
//		int nextIndex = index + 1;
//		Mu thisMu = musWithNotes.get(index);
//		while (list.size() < closestNoteCount)
//		{
//			if (nextIndex >= musWithNotes.size())
//			{
//				
//			}
//			else
//			{
//				Mu mu = musWithNotes.get(nextIndex);
//				if (Math.abs(mu.getGlobalPositionInQuarters() - thisMu.getGlobalPositionInQuarters()) 
//						> SOME_SMALL_AMOUNT_BELOW_WHICH_NOTES_COULD_BE_SEEN_TO_BE_CONTEMPORANEOUS)
//				{
//					list.add(mu);
//					if (list.size() >= closestNoteCount) break;
//				}	
//				nextIndex++;
//			}
//			if (previousIndex < 0)
//			{
//				
//			}
//			else
//			{
//				Mu mu = musWithNotes.get(previousIndex);
//				if (Math.abs(mu.getGlobalPositionInQuarters() - thisMu.getGlobalPositionInQuarters()) 
//						> SOME_SMALL_AMOUNT_BELOW_WHICH_NOTES_COULD_BE_SEEN_TO_BE_CONTEMPORANEOUS)
//				{
//					list.add(mu);
//				}	
//				previousIndex--;
//			}
//		}
//		return list;
//	}



	static Mu getMelodyForMu(Mu parent, RandomMelodyParameterObject po, RandomMelodyGenerator rmg)
	{
		Mu mu1 = rmg.getMuPhrase(po);
		
		mu1.setName("melody");
		mu1.addTag(MuTag.PART_MELODY);
		mu1.addTag(MuTag.PRINT_CHORDS);
		mu1.setHasLeadingDoubleBar(true);
		mu1.setXMLKey(po.getXmlKey());
		ChordToneAndEmbellishmentTagger.makeTagsIntoXMLAnnotations(mu1);
		addNamesAsMuAnnotationsToStructureTones(mu1);
		parent.addMu(mu1, 0);
		parent.setStartTempo(mu1.getStartTempo());
		parent.setLengthInBars(mu1.getLengthInBars());
		return mu1;
	}
	
	

	static void getDrumPartForMu(Mu parent)
	{
		DrumPartGenerator.addTactusHiHatToDrumPart(parent);
		DrumPartGenerator.addKickAndSnareToDrumPart(parent);
		DrumPartGenerator.addSubTactusHiHatToDrumPart(parent);
		parent.getMu("drums").setHasLeadingDoubleBar(true);
	}



	static void getBassPartForMu(Mu parent, RandomMelodyParameterObject po, Mu mu1)
	{
		Mu mu3 = BassPartGenerator.addBassMuToParentMu(parent, mu1);
		mu3.setName("bass");
		mu3.addTag(MuTag.PART_BASS);
		mu3.setHasLeadingDoubleBar(true);
		mu3.setXMLKey(po.getXmlKey());
//		mu3.addTag(MuTag.BASS_CLEF);
	}



	static void getChordsPartForMu(Mu parent, RandomMelodyParameterObject po, Mu mu1)
	{
		Mu mu2 = ChordPartGenerator.addPadMuToParentMu(parent, mu1);
		mu2.setName("chords");
		mu2.addTag(MuTag.PART_CHORDS);
		mu2.setHasLeadingDoubleBar(true);
		mu2.setXMLKey(po.getXmlKey());
	}
	
	
	
	static void addNamesAsMuAnnotationsToStructureTones(Mu mu1)
	{
		for (Mu mu: mu1.getAllMus())
		{
			if (mu.hasTag(MuTag.IS_STRUCTURE_TONE))
			{
				mu.addMuAnnotation(new MuAnnotation(mu.getName(), MuAnnotation.TextPlacement.PLACEMENT_BELOW));
			}
		}
	}
	
	
	
	static StringBuilder makeGenerateUserOptionsThinking(ArrayList<RndContainerScoreWrapper> rcswList)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("list of scores:\n");
		for (RndContainerScoreWrapper sw: rcswList)
		{
			sb.append("score=" + sw.score + "\n" + sw.narrative);
		}
		return sb;
	}
	
	
	// only info used from aMid is the ParameterSpecificFeedbackList
	public static ArrayList<RndContainerScoreWrapper> makeUserRndContainerList
	(
			MucusInteractionData aMid, 
			Random rnd,
			int aUserOptionCount,
			double aMinimumMutation
			)
	{
		ArrayList<RndContainerScoreWrapper> list = new ArrayList<RndContainerScoreWrapper>();
		Parameter[] parameterList = getSetOfMutableParameters(aMid);
		int mutantAttackCount = 0;
		for (Parameter parameter: parameterList)
		{
			mutantAttackCount += aMid.getRndContainer().get(parameter).getValueCount();
		}
		for (int i = 0; i < aUserOptionCount; i++)
		{
//			System.out.println("makeUserContainerList i=" + i);
			RMRandomNumberContainer rndContainer 
			= aMid.getRandomMelodyGenerator().getMutatedRandomNumberContainer
			(
					aMinimumMutation, 
					mutantAttackCount,	//parameterList.length, 
					parameterList, 
					aMid.getRndContainer(),
					rnd
					);
			RndContainerScoreWrapper rcsw = getRndContainerScoreWrapper(aMid, rndContainer);
//			IntAndString scoreAndNarrative = getScoreAndNarrativeOfNewRndContainer(aMid, rndContainer);
			list.add(rcsw);
		}
		return list;
	}
	
	
	static RndContainerScoreWrapper getRndContainerScoreWrapper
	(
			MucusInteractionData aMid, 
			RMRandomNumberContainer rndContainer
			)
	{
		int score = 0;
//		JSONObject jobj = new JSONObject();
		JSONArray jarr = new JSONArray();
		StringBuilder sb = new StringBuilder();		
		for (FeedbackObject fo: aMid.getParameterSpecificFeedbackList())
		{
			RNDValueObject rvo = rndContainer.get(fo.getRelatedParameter());
			String str = "RNDValueObject is null";
			Object jsonStr = new JSONObject(new String[] {str});
			if (rvo != null)
			{
				str = rvo.getTextFileString(); 
				jsonStr = rvo.getAsJSON();
			}
			String descriptionText = fo.getDescription() + "\t" + str;
			sb.append(descriptionText);
			boolean isTrue = fo.isTrue(aMid.getRndContainer(), rndContainer, aMid.getRandomMelodyGenerator());
			if (isTrue) 
			{
				score++;
				sb.append("\tpasses\n");
				jarr.put(new JSONArray(new Object[] {fo.getDescription(), jsonStr, "passes"}));
			}
			else
			{
				sb.append("\tfails\n");
				jarr.put(new JSONArray(new Object[] {fo.getDescription(), jsonStr, "fails"}));
			}
		}
		RndContainerScoreWrapper r = new RndContainerScoreWrapper(rndContainer, score, sb.toString(), jarr);
		return r;
	}
	
	
	
	static IntAndString getScoreAndNarrativeOfNewRndContainer
	(
			MucusInteractionData aMid, 
			RMRandomNumberContainer rndContainer
			)
	{
		int score = 0;
		StringBuilder sb = new StringBuilder();		
		for (FeedbackObject fo: aMid.getParameterSpecificFeedbackList())
		{
			RNDValueObject rvo = rndContainer.get(fo.getRelatedParameter());
			String str = "RNDValueObject is null";
			if (rvo != null) str = rvo.getTextFileString();
			sb.append(fo.getDescription() + "\t" + str);
			boolean isTrue = fo.isTrue(aMid.getRndContainer(), rndContainer, aMid.getRandomMelodyGenerator());
			if (isTrue) 
			{
				score++;
				sb.append("\tpasses\n");
			}
			else
			{
				sb.append("\tfails\n");
			}
		}
		return new IntAndString(score, sb.toString());
	}
	
	
	
	static Parameter[] getSetOfMutableParameters(MucusInteractionData aMid)
	{
		HashSet<Parameter> parameterList = new HashSet<Parameter>();
		for (FeedbackObject fo: aMid.getParameterSpecificFeedbackList())
		{
			parameterList.add(fo.getRelatedParameter());
		}
		return parameterList.toArray(new Parameter[parameterList.size()]);
	}
}
