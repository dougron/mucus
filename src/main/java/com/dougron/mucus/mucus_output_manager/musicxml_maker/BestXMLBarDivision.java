package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import main.java.da_utils.combo_variables.IntAndInt;

/*
 * static class to calculate best subdivision for a bar when creating musicxml files
 */

public class BestXMLBarDivision {
	
	// logic behind this list is primes included until 7, thereafter any subdivision which only has a prime
	// higher than 7 as a factor is ommited e.g. 21 is included (7 is a factor) 22 is omitted (11 and 2 as factors) 
	static int[] possibleSubdivision = new int[] {1,2,3,4,5,6,7,8,9,10,12,14,15,16,18,20,21,24,25,28,30,32};


	public static void main(String[] args) {
		BarDivisionInputNote[] noteArr = new BarDivisionInputNote[] {
			new BarDivisionInputNote(60, 0.0, 0.8),
			new BarDivisionInputNote(60, 0.8, 0.8),
			new BarDivisionInputNote(60, 1.6, 0.8),
			new BarDivisionInputNote(60, 2.4, 0.8),
			new BarDivisionInputNote(60, 3.2, 0.8),
		};
		
		int bestSubdivision = getBestSubdivision(noteArr);
		
		System.out.println("best subdivision=" + bestSubdivision);

	}
	
	
	
	public static int getBestSubdivision(BarDivisionInputNote[] noteArr) {
				
				IntAndInt[] arr1 = makeErrorsBasedOnStartAndEndPoints(noteArr, possibleSubdivision);
				IntAndInt[] arr2 = makeErrorsBasedOnStartPointsOnly(noteArr, possibleSubdivision);
		 		
				ArrayList<Error> errList = new ArrayList<Error>();
				HashMap<Integer, Error> map = new HashMap<Integer, Error>();
				for (IntAndInt iai: arr1) {
					map.put(iai.i1, new Error(iai.i1, iai.i2));
				}
				for (IntAndInt iai: arr2) {
					map.get(iai.i1).err += iai.i2;
				}
				for (Error err: map.values()) {
					errList.add(err);
				}
				
//				System.out.println("-------------------------------");
		 		Collections.sort(errList, Error.ErrorAndSubComparator);
//		 		for (Error err: errList) {
//		 			System.out.println(err.toString());
//		 		}
		return errList.get(0).sub;
	}

	private static IntAndInt[] makeErrorsBasedOnStartPointsOnly(BarDivisionInputNote[] noteArr, int[] possibleSubdivision) {
		// make errors based on start points only
			//make list of start and ends points
			ArrayList<Double> pointList = new ArrayList<Double>();
			for (BarDivisionInputNote note: noteArr) {
				pointList.add(note.pos);
				
			}
			
			ArrayList<Error> errList = new ArrayList<Error>();
	 		for (int i: possibleSubdivision) {
				double totalError = 0.0;
				for (double point: pointList) {
					totalError += Math.abs(Math.round(point * i) - point * i);
				}
				errList.add(new Error(i, totalError));
//				System.out.println("subdiv=" + i + " totalError=" + totalError);
			}
//	 		System.out.println("-------------------------------");
	 		Collections.sort(errList, Error.ErrorAndSubComparator);
//	 		for (Error err: errList) {
//	 			System.out.println(err.toString());
//	 		}
//	 		
	 		
	 		IntAndInt[] arr = new IntAndInt[errList.size()];
	 		for (int i = 0; i < errList.size(); i++) {
	 			arr[i] = new IntAndInt(errList.get(i).sub, i);					
	 		}
	 		return arr;
		
	}

	private static IntAndInt[] makeErrorsBasedOnStartAndEndPoints(BarDivisionInputNote[] noteArr, int[] possibleSubdivision) {
		// make errors based on start and end points
				//make list of start and ends points
				ArrayList<Double> pointList = new ArrayList<Double>();
				for (BarDivisionInputNote note: noteArr) {
					pointList.add(note.pos);
					pointList.add(note.pos + note.len);
				}
				
				ArrayList<Error> errList = new ArrayList<Error>();
		 		for (int i: possibleSubdivision) {
					double totalError = 0.0;
					for (double point: pointList) {
						totalError += Math.abs(Math.round(point * i) - point * i);
					}
					errList.add(new Error(i, totalError));
//					System.out.println("subdiv=" + i + " totalError=" + totalError);
				}
//		 		System.out.println("-------------------------------");
		 		Collections.sort(errList, Error.ErrorAndSubComparator);
//		 		for (Error err: errList) {
//		 			System.out.println(err.toString());
//		 		}
		 		
		 		
		 		IntAndInt[] arr = new IntAndInt[errList.size()];
		 		for (int i = 0; i < errList.size(); i++) {
		 			arr[i] = new IntAndInt(errList.get(i).sub, i);					
		 		}
		 		return arr;
		
	}

}

class Error {
	
	int sub;
	double err;

	public Error(int i, double d) {
		this.sub = i;
		this.err = d;
	}
	
	public static Comparator<Error> ErrorAndSubComparator = new Comparator<Error>(){

		@Override
		public int compare(Error o1, Error o2) {
			if (o1.err < o2.err) return -1;
			if (o1.err > o2.err) return 1;
			if (o1.sub < o2.sub) return -1;
			if (o1.sub > o2.sub) return 1;
			
			return 0;
		}
		
	};
	
	public static Comparator<Error> SubComparator = new Comparator<Error>(){

		@Override
		public int compare(Error o1, Error o2) {

			if (o1.sub < o2.sub) return 1;
			if (o1.sub > o2.sub) return -1;
			
			return 0;
		}
		
	};
	
	public String toString() {
		return "subdiv=" + sub + " totalError=" + err;
	}
}

class BarDivisionInputNote{

	int note;
	double pos;
	double len;

	public BarDivisionInputNote(int aNote, double aPos, double aLen) {
		note = aNote;
		pos = aPos;
		len = aLen;
	}
	
}
