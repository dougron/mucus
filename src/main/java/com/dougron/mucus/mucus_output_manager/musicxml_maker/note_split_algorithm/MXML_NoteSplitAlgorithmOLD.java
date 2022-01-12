/**
 * 
 */
package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.note_split_algorithm;

import java.util.ArrayList;
import java.util.Collections;

import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.MXML_Measure;
import main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu.VoiceMu;

/**
 * Class wrapping static versions of the note splitting algorithm for MXML_Measure.
 * 
 * Separated out to avoid the monolithic problem that using Umple has become. 
 * 
 * @author dougr
 *
 */
public class MXML_NoteSplitAlgorithmOLD {
	
	//-------------------------------------------------
	// INTERFACE
	//-------------------------------------------------
	
	public static ArrayList<VoiceMu> splitVoiceMu(VoiceMu vm, MXML_Measure measure)
	{
		return splitVoiceMuLikeRest(vm, measure);
	}
	
	
	
	public static ArrayList<VoiceMu> rejoinDottedNotes(ArrayList<VoiceMu> list) 
	{
//		setPreviousAndNextVoiceMu(list); // this will be handled in the splitting algorithm
		sortByNoteLength(list);			// consistent with the way Sibelius and Musescore do it, shorter notes get joined first
		return rejoinNotes(list);
	}
	
/* calculates div by adding 
 * div is a denominator for the note that would need to be used to print the note
 */
	
	public static int getDiv(double d)
	{
		int i = 1;
		double sum = d % 1.0;
		if (sum == 0) 
		{
			return 1;
		}
		else 
		{
			while (!closeEnoughToRounded(sum, 0.01))
			{
				sum += d % 1.0;
				i++;
			}
			return i;
		}
		
	}
	
	
	
	/* calculates whether a double is close enough to its rounded version to be within the errorMargin */
	
	public static boolean closeEnoughToRounded(double sum, double errorMargin)
	{
		double round = Math.round(sum);
		double err = Math.abs(round - sum) / round;
		if (err <= errorMargin)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	
	
	public static boolean closeEnough(double d1, double d2, double errorMargin)
	{
		if (Math.abs(d1 - d2) / d1 <= errorMargin) return true;
		return false;
	}
	
	

	//-------------------------------------------------
	// PRIVATES
	//-------------------------------------------------
	
	
	// rejoining algorithm for splitting notes like rests --------------------------------------------
	
	private static ArrayList<VoiceMu> rejoinNotes(ArrayList<VoiceMu> list) {
		ArrayList<VoiceMu> input = new ArrayList<VoiceMu>();
		input.addAll(list);
		
//		boolean found = false;	
//		VoiceMu leaveOut;
		while (true)	// can only process one possibility at a time, so if one is found, found is set to true, all other VioceMus are copied to output and then output becomes input. Process exits when found is false after everything has been tested
		{
			boolean found = false;
			ArrayList<VoiceMu> output = new ArrayList<VoiceMu>();
			VoiceMu exclude = null;
			for (VoiceMu vm: input) 
			{		
				boolean addVM = false;
				if (vm == exclude) 
				{
					// do nothing
				}
				else
				{
					if (found) 
					{
						addVM = true;			
					}
					else
					{
						if (previousIsViableToJoin(vm))
						{
							joinCurrentAndPrevious(vm);
//							exclude = vm.getPreviousVoiceMu();	// must not be excluded as it must be loaded to output when the iterator gets to it, which it will cos its longer
							addVM = false;
							found = true;							
						}
						else
						{
							addVM = true;	
						}
						if (found)	// will only get here if it has just discovered that the previous and current are viable. Do not add vm, as it has been joined to its previousVoiceMu
						{
							// do nothing
						}
						else
						{
							if (nextIsViableToJoin(vm))
							{
								exclude = vm.getNextVoiceMu();	
								joinCurrentAndNext(vm);
								addVM = true;
								found = true;
							}
						}
					}
				}
				if (addVM) output.add(vm);	
			}
			input = output;
			Collections.sort(input, VoiceMu.lengthComparator);
			if (!found) break;
		}
		return input;
	}
	
	
	
	private static void joinCurrentAndNext(VoiceMu vm) 
	{
		VoiceMu next = vm.getNextVoiceMu();
		vm.setLengthInQuarters(next.getLengthInQuarters() + vm.getLengthInQuarters());	
		vm.setHasTieStart(next.getHasTieStart());
		vm.setNextVoiceMu(next.getNextVoiceMu());
	}



	private static void joinCurrentAndPrevious(VoiceMu vm) 
	{
		VoiceMu prev = vm.getPreviousVoiceMu();
		prev.setLengthInQuarters(prev.getLengthInQuarters() + vm.getLengthInQuarters());
		prev.setHasTieStart(vm.getHasTieStart());
		prev.setNextVoiceMu(vm.getNextVoiceMu());
	}
	
	
	
	private static boolean nextIsViableToJoin(VoiceMu vm) {	// must add is same note layer
		if (vm.getHasTieStart()) {
//			if (!vm.getIsRest() && !vm.getNextVoiceMu().getIsRest()) {
//				if (closeEnough(vm.getLengthInQuarters() * 2, vm.getNextVoiceMu().getLengthInQuarters(), 0.01)) {
					if (isAPrintableNote(vm.getLengthInQuarters() + vm.getNextVoiceMu().getLengthInQuarters())) {
						if (doesNotCrossADivisionRelatedSplitPoint(vm, vm.getNextVoiceMu())
								&& doesNotCrossALevelZeroSubDiv(vm, vm.getNextVoiceMu())) {
							return true;
						}
					}
//				}
//			} 
		}
		return false;
	}



	private static boolean previousIsViableToJoin(VoiceMu vm) {
		if (vm.getHasTieEnd()) {
//			if (!vm.getIsRest() && !vm.getPreviousVoiceMu().getIsRest()) {
//				if (closeEnough(vm.getLengthInQuarters() * 2, vm.getPreviousVoiceMu().getLengthInQuarters(), 0.01)) {
					if (isAPrintableNote(vm.getLengthInQuarters() + vm.getPreviousVoiceMu().getLengthInQuarters())) {
						if (doesNotCrossADivisionRelatedSplitPoint(vm.getPreviousVoiceMu(), vm)
								&& doesNotCrossALevelZeroSubDiv(vm.getPreviousVoiceMu(), vm)) {
							return true;
						}
					}
//				}
//			} 
		}
		return false;
	}



	private static boolean doesNotCrossALevelZeroSubDiv(VoiceMu vm1, VoiceMu vm2) 
	{
		// commented out to avoid error, until such time as this entire file is ditched
//		ArrayList<Double> list = vm1.getMeasure().getTimeSignature().getBreakPoints(0);
//		for (Double d: list) 
//		{
//			System.out.println("d=" + d);
//			if (d > vm1.getPositionInBar() && d < vm2.getEndPositionInBar()) 
//			{
//				// some stuff to cater for full bar notes.....
//				return false;
//			}
//		}
		return true;
	}



	private static boolean isAPrintableNote(double d) 
	{
		for (Double key: MXML.xmlDurationNoteType.keySet()) 
		{
			if (closeEnough(d, key, 0.01)) return true;
		}
		return false;
	}



	private static boolean doesNotCrossADivisionRelatedSplitPoint(VoiceMu vm1, VoiceMu vm2) // order of arguments important vm1 must be the first one and vm2 the 2nd.
	{
		double rez = getDivisionResolution(vm1.getPositionInBar(), vm2.getEndPositionInBar());
		double testPos = 0.0;
		while (testPos <= vm1.getEndPositionInBar()) 
		{
			if (closeEnough(testPos, vm1.getEndPositionInBar(), 0.01)) 
			{
				return false;
			}
			testPos += rez * 4;		// this assumes its not a tuplet. Not sure how I will negotiate that.....
		}
		return true;
	}



	private static double getDivisionResolution(double positionInBar, double endPositionInBar) 
	{
		int div1 = getDiv(positionInBar);
		int div2 = getDiv(endPositionInBar);
		if (div1 > div2) return 1.0 / div1;
		return 1.0 / div2;
	}



	private static void sortByNoteLength(ArrayList<VoiceMu> list) 
	{
		Collections.sort(list, VoiceMu.lengthComparator);
	}

	
	
//	private static void setPreviousAndNextVoiceMu(ArrayList<VoiceMu> list) 
//	{
//		// assumes that the list is sorted by position
//		for (int i = 0; i < list.size(); i++) 
//		{
//			VoiceMu vm = list.get(i);
//			if (i == 0) 
//			{
//				vm.setPreviousVoiceMu(null);
//			}
//			else 
//			{
//				vm.setPreviousVoiceMu(list.get(i - 1));
//			}
//			if (i == list.size() - 1) 
//			{
//				vm.setNextVoiceMu(null);
//			}
//			else 
//			{
//				vm.setNextVoiceMu(list.get(i + 1));
//			}
//		}	
//	}
	
	

	
	
// rest splitting algorithm for notes -------------------------------------------------------------------
// this is for an approach based on using the rest splitting algoritrhm and then reconnecting------------
	
	



	private static  ArrayList<VoiceMu> splitVoiceMuLikeRest(VoiceMu vm, MXML_Measure measure)
	{
	    ArrayList<VoiceMu> input = new ArrayList<VoiceMu>();
	    	input.add(vm);
	    	ArrayList<VoiceMu> output = new ArrayList<VoiceMu>();
	    	ArrayList<VoiceMu> finalList = new ArrayList<VoiceMu>();
//	    	int breakPointLevel = 0;
	    	while (input.size() > 0)
	    	{	
	    		// commented out to remove errors
//	    		ArrayList<Double> bpoints = measure.getTimeSignature().getBreakPoints(breakPointLevel);
//	    		output = splitVoiceMuList(input, bpoints, measure);
	    		input.clear();
	    		for (VoiceMu tempvm: output) 
	    		{
	    			if (isPrintableVoiceMu(tempvm, measure) 
	    					&& !containsDivisionRelatedSplitPoint(vm.getPositionInBar(), vm.getLengthInQuarters())) {
	    				finalList.add(tempvm);
	    			} else {
	    				input.add(tempvm);
	    			}
	    		}
//	    		breakPointLevel++;
	    	}
	    	Collections.sort(finalList, VoiceMu.posComparator);
	    	return finalList;
	  	}
	   
	   
	   
	   	private static boolean containsDivisionRelatedSplitPoint(double pos, double len) 
	   	{
	   		double end = pos + len;
	   		double rez = getDivisionResolution(pos, len);
			double testPos = 0.0;
			while (testPos < end) 
			{
				if (testPos > pos && testPos < end) 
				{
					return true;
				}
				testPos += rez * 4;		// this assumes its not a tuplet. Not sure how I will negotiate that.....
			}
			return false;
	
	   	}



		/* calculates if length of each note is available in MXML.xmlDurationNoteType. Does not consider notes beyond the end of the bar
	  	as they will be passed to a new measure and have this done to them all over again*/
	   
	   	private static  boolean isPrintableVoiceMu(VoiceMu vm, MXML_Measure measure) 
	   	{
		   if (vm.getPositionInBar() < measure.getLengthInQuarters())
	  			{
			   		// remember, xmlDurationRestType contains no dotted notes
	  				if (MXML.xmlDurationRestType.containsKey(vm.getLengthInQuarters())) return true;
	  			} 
		   return false;
	   	}
	  	
	  	
	  	
	  	
//	  	private static  ArrayList<VoiceMu> splitVoiceMuList(ArrayList<VoiceMu> list, ArrayList<Double> bpoints, MXML_Measure measure)
//	  	{
//	  		ArrayList<VoiceMu> input = new ArrayList<VoiceMu>();
//			input.addAll(list);
//			ArrayList<VoiceMu> output = new ArrayList<VoiceMu>();
//	    	for (Double d: bpoints)
//	    	{
//	    		for (VoiceMu vm: input)
//	    		{
//	    			if (criteriaForSplittingRest(vm, d, measure))
//	  				{
//	  					output.add(vm);
//	  					output.add(getSecondBitOfSplitVoiceMu(vm, d, measure));	// remember this changes the length of vm, besides returning the new cut off part
//	  				}
//	  				else 
//	  				{
//	  					output.add(vm);
//	  				}
//	    		}
//	    		input = output;
//	    		output = new ArrayList<VoiceMu>();
//	    	}
//	    	return input;	// sounds counter intuitive but final output always converted to input to cycle to the next d....
//	  	}
	  	
	  	
	   
	   	/* returns a VoiceMu representing the bit of vm after position d. vm length is adjusted. 
	   	Position d is assumed to fall within the start and end of vm*/
	   	
//	  	private static  VoiceMu getSecondBitOfSplitVoiceMu(VoiceMu vm, Double d, MXML_Measure measure) 
//	  	{
//		  	VoiceMu newvm = vm.deepCopy();
//		  	newvm.setMeasure(measure);
//		  	newvm.setGlobalPositionInQuarters(measure.getPositionInQuarters() + d);
//		  	newvm.setLengthInQuarters(vm.getEndPositionInBar() - d);
//		  	newvm.setHasTieEnd(true);
//		  	newvm.setPreviousVoiceMu(vm);
//		  	newvm.setHasTieStart(vm.getHasTieStart());		// this is for if vm was already tied to a subsequent note
//		  	newvm.setNextVoiceMu(vm.getNextVoiceMu());		// pick up voiceMu that vm might have been tied to
//		  	vm.setLengthInQuarters(d - vm.getPositionInBar());
//		  	vm.setHasTieStart(true);
//		  	vm.setNextVoiceMu(newvm);
//		  	return newvm;
//		}



		/* determines whether a note should be split at bar position d */
		
//	  	private static  boolean criteriaForSplittingRest(VoiceMu vm, Double d, MXML_Measure measure) 
//	  	{
//			if (vm.getPositionInBar() < d && vm.getEndPositionInBar() > d) 
//			{
//				double first = d - vm.getPositionInBar();
//				double second = vm.getEndPositionInBar() - d;
//				if (
//					first + second == measure.getLengthInQuarters()
//					&& first == second
//				)
//				{
//					return false;
//				}
//				else 
//				{
//					return true;
//				}
//			}
//				return false;
//		}

	  	
	  	
	  	public static void main(String[] args) 
	  	{
	  		System.out.println(MXML_NoteSplitAlgorithmOLD.getDivisionResolution(0.0, 2.667));
	  	}

		
}
