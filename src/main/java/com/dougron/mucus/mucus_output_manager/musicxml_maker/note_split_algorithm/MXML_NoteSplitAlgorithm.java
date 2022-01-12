package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.note_split_algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import DataObjects.combo_variables.DoubleAndDouble;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_NoteDurationType;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu.VoiceMu;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu.VoiceMuRest;
import time_signature_utilities.greatest_common_factor_calculator.GreatestCommonFactor;
import time_signature_utilities.time_signature.SplitListSource;
import time_signature_utilities.time_signature.TimeSignature;

/*
 * voice splitting algorithm for use with new TimeSignature, previously the TactusTimeSignature
 */

public class MXML_NoteSplitAlgorithm {
	
	
	//-------------------------------------------------
	// INTERFACE
	//-------------------------------------------------
		
	public static ArrayList<VoiceMu> splitVoiceMu(VoiceMu vm, MXML_Measure measure)
	{
		return splitLikeRest(vm, measure, MXML.xmlDurationRestType);
	}
	
	public static ArrayList<VoiceMu> splitVoiceMuRest(VoiceMu vm, MXML_Measure measure)
	{
		return splitLikeRest(vm, measure, MXML.xmlDurationRestType);
	}
	
	
	
	//-------------------------------------------------
	// PRIVATES
	//-------------------------------------------------

	
// %%%%%%%%%%%%%%%%%%% based on rest split and rejoin %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	private static ArrayList<VoiceMu> splitLikeRest(VoiceMu vm, MXML_Measure measure, Map<Double, MXML_NoteDurationType> map)
	{
		List<VoiceMu> input = new ArrayList<VoiceMu>();
    	input.add(vm);
    	List<VoiceMu> output = new ArrayList<VoiceMu>();
    	ArrayList<VoiceMu> finalList = new ArrayList<VoiceMu>();
    	
    	Iterator<SplitListSource> it = measure.getTimeSignature().getSplitLists().iterator();
    	
    	while (input.size() > 0 && it.hasNext())
    	{	
    		SplitListSource sls = it.next();
    		List<Double> bpoints = sls.getSplitList();
    		output = splitVoiceMuListLikeRests(input, bpoints, measure, sls.getGcf());
    		input.clear();
    		for (VoiceMu tempvm: output) 
    		{
    			if (isAcceptable(tempvm, measure, sls.getGcf(), map)) {
    				finalList.add(tempvm);
    			} else {
    				input.add(tempvm);
    			}
    		}
    		
    		
    	}
    	Collections.sort(finalList, VoiceMu.posComparator);
    	
		return finalList;
	}
	
	
	
	private static  List<VoiceMu> splitVoiceMuListLikeRests(List<VoiceMu> list, List<Double> bpoints, MXML_Measure measure, double gcfOfBPoints)
  	{
  		ArrayList<VoiceMu> input = new ArrayList<VoiceMu>();
		input.addAll(list);
		ArrayList<VoiceMu> output = new ArrayList<VoiceMu>();
    	for (Double d: bpoints)
    	{
    		for (VoiceMu vm: input)
    		{
//    			double vmGcf = GreatestCommonFactor.gcf(vm.getEndPositionInBar(), vm.getPositionInBar());
    			if (criteriaForSplittingRest(vm, d, measure, gcfOfBPoints))
  				{
  					output.add(vm);
  					output.add(getSecondBitOfSplitVoiceMu(vm, d, measure));	// remember this changes the length of vm, besides returning the new cut off part
  				}
  				else 
  				{
  					output.add(vm);
  				}
    		}
    		input = output;
    		output = new ArrayList<VoiceMu>();
    	}
    	return input;	// sounds counter intuitive but final output always converted to input to cycle to the next d....
  	}

	
	
// %%%%%%%%%%%%%%%%%% older split thing %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

//	private static ArrayList<VoiceMu> split(VoiceMu vm, MXML_Measure measure, HashMap<Double, MXML_NoteDurationType> map) {
//		List<VoiceMu> input = new ArrayList<VoiceMu>();
//    	input.add(vm);
//    	List<VoiceMu> output = new ArrayList<VoiceMu>();
//    	ArrayList<VoiceMu> finalList = new ArrayList<VoiceMu>();
//    	
//    	Iterator<SplitListSource> it = measure.getTimeSignature().getSplitLists().iterator();
//    	
//    	while (input.size() > 0 && it.hasNext())
//    	{	
//    		SplitListSource sls = it.next();
//    		List<Double> bpoints = sls.getSplitList();
//    		output = splitVoiceMuList(input, bpoints, measure, sls.getGcf());
//    		input.clear();
//    		for (VoiceMu tempvm: output) 
//    		{
//    			if (isAcceptable(tempvm, measure, sls.getGcf(), map)) {
//    				finalList.add(tempvm);
//    			} else {
//    				input.add(tempvm);
//    			}
//    		}
//    		
//    		
//    	}
//    	Collections.sort(finalList, VoiceMu.posComparator);
//    	
//		return finalList;
//	}
	
	




//	private static  List<VoiceMu> splitVoiceMuList(List<VoiceMu> list, List<Double> bpoints, MXML_Measure measure, double gcfOfBPoints)
//  	{
//  		ArrayList<VoiceMu> input = new ArrayList<VoiceMu>();
//		input.addAll(list);
//		ArrayList<VoiceMu> output = new ArrayList<VoiceMu>();
//    	for (Double d: bpoints)
//    	{
//    		for (VoiceMu vm: input)
//    		{
////    			double vmGcf = GreatestCommonFactor.gcf(vm.getEndPositionInBar(), vm.getPositionInBar());
//    			if (criteriaForSplitting(vm, d, measure, gcfOfBPoints))
//  				{
//  					output.add(vm);
//  					output.add(getSecondBitOfSplitVoiceMu(vm, d, measure));	// remember this changes the length of vm, besides returning the new cut off part
//  				}
//  				else 
//  				{
//  					output.add(vm);
//  				}
//    		}
//    		input = output;
//    		output = new ArrayList<VoiceMu>();
//    	}
//    	return input;	// sounds counter intuitive but final output always converted to input to cycle to the next d....
//  	}
	
	
	
	
	/* returns a VoiceMu representing the bit of vm after position d. vm length is adjusted. 
   	Position d is assumed to fall within the start and end of vm*/
   	
  	private static  VoiceMu getSecondBitOfSplitVoiceMu(VoiceMu vm, Double d, MXML_Measure measure) 
  	{
	  	VoiceMu newvm = vm.deepCopy();
	  	newvm.setMeasure(measure);
	  	newvm.setGlobalPositionInQuarters(measure.getPositionInQuarters() + d);
	  	newvm.setLengthInQuarters(vm.getEndPositionInBar() - d);
	  	newvm.setHasTieEnd(true);
	  	newvm.setPreviousVoiceMu(vm);
	  	newvm.setHasTieStart(vm.getHasTieStart());		// this is for if vm was already tied to a subsequent note
	  	newvm.setNextVoiceMu(vm.getNextVoiceMu());		// pick up voiceMu that vm might have been tied to
	  	vm.setLengthInQuarters(d - vm.getPositionInBar());
	  	vm.setHasTieStart(true);
	  	vm.setNextVoiceMu(newvm);
	  	return newvm;
	}
  	
  	
  	
  	
	// essentially isAcceptable for printing
  	
  	
  	
  	private static boolean isAcceptable(
			VoiceMu tempvm, 
			MXML_Measure measure, 
			double splitListGcf, 
			Map<Double, MXML_NoteDurationType> map
			) 
  	{
  		if (tempvm instanceof VoiceMuRest)
  		{
  			return isAcceptableRest(tempvm, measure, splitListGcf, map);
  		}
  		return isAcceptableNote(tempvm, measure, splitListGcf, map);
  	}
  	
  	
	
	private static boolean isAcceptableRest(
			VoiceMu tempvm, 
			MXML_Measure measure, 
			double splitListGcf, 
			Map<Double, MXML_NoteDurationType> map
			) 
	{
//		double muGcf = GreatestCommonFactor.gcf(tempvm.getPositionInBar(), tempvm.getEndPositionInBar());
		if (map.containsKey(tempvm.getLengthInQuarters())
//				&& muGcf * 2 >= splitListGcf
				) 
			{
				return true;
			}
		return false;
	}
	
	
	
	private static boolean isAcceptableNote(
			VoiceMu tempvm, 
			MXML_Measure measure, 
			double splitListGcf, 
			Map<Double, MXML_NoteDurationType> map
			) 
	{
		if (tempvm.getPositionInBar() >= measure.getLengthInQuarters()) return true;
		double muGcf = GreatestCommonFactor.gcf(tempvm.getPositionInBar(), tempvm.getEndPositionInBar());
		if (map.containsKey(tempvm.getLengthInQuarters())
				&& muGcf * 2 >= splitListGcf
				&& tempvm.getLengthInQuarters() < muGcf * 2
				) 
			{
				return true;
			}
		return false;
	}
	
	
	
//	private static boolean criteriaForSplitting(VoiceMu vm, Double d, MXML_Measure measure, double gcfOfBPoints)
//	{
//		if (vm instanceof VoiceMuRest)
//		{
//			return criteriaForSplittingRest(vm, d, measure, gcfOfBPoints);
//		} 
//		return criteriaForSplittingNote(vm, d, measure, gcfOfBPoints); 
//	}
	
	
	
	/* determines whether a note should be split at bar position d */
	
  	private static boolean criteriaForSplittingRest(VoiceMu vm, Double d, MXML_Measure measure, double gcfOfBPoints) 
  	{
//  		double vmGcf = GreatestCommonFactor.gcf(vm.getEndPositionInBar(), vm.getPositionInBar());
		if (vm.getPositionInBar() < d && vm.getEndPositionInBar() > d
//				&& vmGcf * 2 < gcfOfBPoints
				) 
		{
			double first = d - vm.getPositionInBar();
			double second = vm.getEndPositionInBar() - d;
			if (
				first + second == measure.getLengthInQuarters()
				&& first == second
			)
			{
				return false;
			}
			else 
			{
				return true;
			}
		}
			return false;
	}
  	
  	
//  	private static boolean criteriaForSplittingNote(VoiceMu vm, Double d, MXML_Measure measure, double gcfOfBPoints) 
//  	{
//  		double vmGcf = GreatestCommonFactor.gcf(new double[] {vm.getEndPositionInBar(), vm.getPositionInBar(), vm.getEndPositionInBar() % 1.0, vm.getPositionInBar() % 1.0});
//		if (vm.getPositionInBar() < d && vm.getEndPositionInBar() > d)
//		{
//			if (MXML.xmlDurationNoteType.containsKey(vm.getLengthInQuarters()))
//			{
//				if (vmGcf * 2 < gcfOfBPoints)
//				{
//					return true;
//				}
//			}
//			else
//			{
//				return true;
//			}
//		}
//				
//				
////		{
////			double first = d - vm.getPositionInBar();
////			double second = vm.getEndPositionInBar() - d;
////			if (
////				first + second == measure.getLengthInQuarters()
////				&& first == second
////			)
////			{
////				return false;
////			}
////			else 
////			{
////				return true;
////			}
////		}
//			return false;
//	}
  	
  	// the old one before I destroy it completely
//  	private static boolean criteriaForSplittingNote(VoiceMu vm, Double d, MXML_Measure measure, double gcfOfBPoints) 
//  	{
//  		double vmGcf = GreatestCommonFactor.gcf(new double[] {vm.getEndPositionInBar(), vm.getPositionInBar(), vm.getEndPositionInBar() % 1.0, vm.getPositionInBar() % 1.0});
//		if (vm.getPositionInBar() < d && vm.getEndPositionInBar() > d
//				&& vmGcf * 2 < gcfOfBPoints
//				) 
//		{
//			double first = d - vm.getPositionInBar();
//			double second = vm.getEndPositionInBar() - d;
//			if (
//				first + second == measure.getLengthInQuarters()
//				&& first == second
//			)
//			{
//				return false;
//			}
//			else 
//			{
//				return true;
//			}
//		}
//			return false;
//	}
  	
  	
//  	public static void main(String[] args)
//  	{
//  		testGetSecondBit();
//  	}
//
//
//
//	/**
//	 * testing for the return type to see if it will work for VoiceMuRest as well
//	 */
//	private static void testGetSecondBit() 
//	{
//		MXML_Measure measure = new MXML_Measure(1, 0, 0.0, 4.0, TimeSignature.FOUR_FOUR);
//		
//  		Mu mu = new Mu("parent");
//  		mu.addMuNote(new MuNote(64, 100));
//  		mu.setPositionRelati);(new BarsAndBeats(0, 1.0));
//  		mu.setLengthInQuarters(2.0);
//  		
////  		TSMapInterface aTsMap = new TimeSignatureMap();
////  		aTsMap.addTimeSignature(TimeSignature.FOUR_FOUR);
////  		mu.setTsMap(aTsMap);
//  		
//  		VoiceMuRest vm = new VoiceMuRest(null, 1.0, 2.0);
//  		
//  		VoiceMu vm2 = getSecondBitOfSplitVoiceMu(vm, 2.0, measure);
//  		System.out.println(vm2.toString());
//	}
	
	

	
// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

	public static ArrayList<VoiceMu> reTieNotes(ArrayList<VoiceMu> list, MXML_Measure measure) 
	{
//		System.out.println(list.get(0).toString());
		ArrayList<VoiceMu> input = new ArrayList<VoiceMu>();
		input.addAll(list);
		ArrayList<VoiceMu> output;
//		ArrayList<VoiceMu> finalList = new ArrayList<VoiceMu>();
		VoiceMu exclude = null;
		boolean madeChange = true;	// true to get it into the while loop
		while(madeChange)
		{
			madeChange = false;
			exclude = null;
			Collections.sort(input, VoiceMu.lengthThenPosComparator);
			output = new ArrayList<VoiceMu>();
			for (VoiceMu vm: input)
			{
				if (madeChange)
				{
					if (vm != exclude)
					{
						output.add(vm);
					}
				}
				else
				{
					if (vm instanceof VoiceMuRest) 
					{
						output.add(vm);
					}
					else
					{
						List<VoiceMu> tieList = getTieItem(vm);		// getTieItem tests for viable length of the resultant note
						if (tieList.size() == 0) 
						{
							output.add(vm);
						} 
						else 
						{
							boolean yes = false;
							for (VoiceMu tie: tieList) 
							{
								if (yes) break;
								if (doesNotCrossSplitBoundary(vm, tie, measure)) 
								{
									exclude = joinVms(exclude, output, vm, tie);
									madeChange = true;
									yes = true;
								} 
							}
							if (!yes) output.add(vm);
							
						} 
					}
				}
			}
			input = output;
		}
		
		return input;
	}

	/**
	 * @param exclude
	 * @param output
	 * @param vm
	 * @param tie
	 * @return
	 */
	private static VoiceMu joinVms(VoiceMu exclude, ArrayList<VoiceMu> output, VoiceMu vm, VoiceMu tie) {
		if (tie.getPositionInBar() < vm.getPositionInBar())
		{
			tie.setLengthInQuarters(tie.getLengthInQuarters() + vm.getLengthInQuarters());
			tie.setHasTieStart(vm.isHasTieStart());
			tie.setNextVoiceMu(vm.getNextVoiceMu());
			// tie not added to list as loop will come around to it later and add it in
			// vm not added as we don't need it and don't need to exclude as it wont be encountered again
		}
		else
		{
			vm.setLengthInQuarters(vm.getLengthInQuarters() + tie.getLengthInQuarters());
			vm.setHasTieStart(tie.getHasTieStart());
			vm.setNextVoiceMu(tie.getNextVoiceMu());
			if (vm.getNextVoiceMu() != null) vm.getNextVoiceMu().setPreviousVoiceMu(vm);
			exclude = tie;
			output.add(vm);
		}
		return exclude;
	}
	
	
	
	
	private static boolean doesNotCrossSplitBoundary(VoiceMu vm, VoiceMu tie, MXML_Measure measure) 
	{
		VoiceMu first, second;
		if (vm.getPositionInBar() < tie.getEndPositionInBar())
		{
			first = vm;
			second = tie;
		}
		else
		{
			first = tie;
			second = vm;
		}
		
		double vmgcf = GreatestCommonFactor.gcf(new double[] {first.getPositionInBar(), second.getEndPositionInBar(), first.getPositionInBar() % 1.0, second.getEndPositionInBar() % 1.0});
		double newLength = first.getLengthInQuarters() + second.getLengthInQuarters();
		TimeSignature ts = measure.getTimeSignature();
		DoubleAndDouble dnd = ts.getCrossingMapItem(second.getPositionInBar());
		
		if (vmgcf >= dnd.getD1()
				&& newLength >= dnd.getD2())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	
	

//	private static boolean doesNotCrossSplitBoundaryOLD(VoiceMu vm, VoiceMu tie, MXML_Measure measure) {
//		VoiceMu first, second;
//		if (vm.getPositionInBar() < tie.getEndPositionInBar())
//		{
//			first = vm;
//			second = tie;
//		}
//		else
//		{
//			first = tie;
//			second = vm;
//		}
//		
//		Set<Double> dontGoThere = measure.getTimeSignature().getAbsolutelyDoNotCrossList();
//		if (dontGoThere.contains(second.getPositionInBar())) return false;
//		
//		double vmgcf = GreatestCommonFactor.gcf(new double[] {first.getPositionInBar(), second.getEndPositionInBar(), first.getPositionInBar() % 1.0, second.getEndPositionInBar() % 1.0});
//		Iterator<SplitListSource> it = measure.getTimeSignature().getSplitLists().iterator();
//		while (it.hasNext())
//		{
//			SplitListSource sls = it.next();
//			List<Double> splits = sls.getSplitList();
//			if (splits.contains(second.getPositionInBar()))
//			{
//				double splitgcf = GreatestCommonFactor.gcf(new double[] {second.getPositionInBar(), second.getPositionInBar() % 1.0, measure.getLengthInQuarters() - second.getPositionInBar()});
//				double avg = sls.getAverageLength();
//				if (vmgcf * 2 >= splitgcf
//						&& vmgcf * 2 >= sls.getAverageLength()
//						)
//				{
//					return true;
//				}
//				else
//				{
//					return false;	// if it does not pass first time, will fail definitively
//				}
//			}
//		}
//		return false;
//	}

	// if previousVoiceMu and nextVoiceMu are both viable to retie, will return the shorter, null if there are no options
	private static List<VoiceMu> getTieItem(VoiceMu vm) 
	{
		ArrayList<VoiceMu> list = new ArrayList<VoiceMu>();
		if (vm.getHasTieEnd()
				&& vm.getPreviousVoiceMu().getMeasure() == vm.getMeasure())		// Tie item must be in the same measure, obviously
		{
			double totalLength = vm.getLengthInQuarters() + vm.getPreviousVoiceMu().getLengthInQuarters();
			if (MXML.xmlDurationNoteType.containsKey(totalLength))
			{
				list.add(vm.getPreviousVoiceMu());
			}			
		}
		if (vm.getHasTieStart() 
				&& vm.getNextVoiceMu().getMeasure() == vm.getMeasure())		// Tie item must be in the same measure, obviously
		{
			double totalLength = vm.getLengthInQuarters() + vm.getNextVoiceMu().getLengthInQuarters();
			if (MXML.xmlDurationNoteType.containsKey(totalLength))
			{
				list.add(vm.getNextVoiceMu());
			}
			
		}
		
		if (list.size() > 1) 
		{
			Collections.sort(list, VoiceMu.lengthComparator);
		}	
		return list;
	}

	

}























