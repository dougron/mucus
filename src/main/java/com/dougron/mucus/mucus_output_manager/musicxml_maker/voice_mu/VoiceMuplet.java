/*CODE CAN BE EDITED - Umple no longer used*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import DataObjects.combo_variables.DoubleAndDouble;
import main.java.com.dougron.mucus.mu_framework.Mu;

/**
 * a VoiceMu that wraps a Mu that is a tuplet wrapper. Mostly cos I wanted to have the name
 */
// line 293 "../musicxml_voicemu.ump"
public class VoiceMuplet extends VoiceMu
{

	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//VoiceMuplet Attributes
	private List<DoubleAndDouble> rests;

	
	
	//------------------------
	// CONSTRUCTOR
	//------------------------

	
	public VoiceMuplet(Mu aMu)
	{
		super(aMu);
		rests = new ArrayList<DoubleAndDouble>();
	}

	
	
	//------------------------
	// INTERFACE
	//------------------------



	public boolean addRest(DoubleAndDouble aRest)
	{
		boolean wasAdded = false;
		wasAdded = rests.add(aRest);
		return wasAdded;
	}



	public boolean removeRest(DoubleAndDouble aRest)
	{
		boolean wasRemoved = false;
		wasRemoved = rests.remove(aRest);
		return wasRemoved;
	}



	public DoubleAndDouble getRest(int index)
	{
		DoubleAndDouble aRest = rests.get(index);
		return aRest;
	}



	public DoubleAndDouble[] getRests()
	{
		DoubleAndDouble[] newRests = rests.toArray(new DoubleAndDouble[rests.size()]);
		return newRests;
	}



	public int numberOfRests()
	{
		int number = rests.size();
		return number;
	}



	public boolean hasRests()
	{
		boolean has = rests.size() > 0;
		return has;
	}



	public int indexOfRest(DoubleAndDouble aRest)
	{
		int index = rests.indexOf(aRest);
		return index;
	}



	public void delete()
	{
		super.delete();
	}



	public void makeRests(){
		rests = getMupletRests(this);
	}
	
	
	
	private List<DoubleAndDouble> getMupletRests(VoiceMuplet vm)
	{
		List<DoubleAndDouble> list = new ArrayList<DoubleAndDouble>();
		double pos = 0.0;
		for (Mu mu: vm.getMus())
		{
			double len = mu.getPositionInBarsAndBeats().getOffsetInQuarters() - pos;
//			double len = mu.getPositionInQuarters() - pos;
//			System.out.println("len=" + len + " minimumLength=" + getMinimumLength());
			if (len > getMinimumLength())
			{
//				System.out.println("**** ABOVE PASSED ******");
				list.add(new DoubleAndDouble(pos, len));
			}
			pos = mu.getPositionInBarsAndBeats().getOffsetInQuarters() + mu.getLengthInQuarters();
		}
		return list;
	}



	public int getTupletActualNotes(){
		return getMu().getTupletNumerator();
	}



	public int getTupletNormalNotes(){
		return getMu().getTupletDenominator();
	}



	public Set<Double> getSetOfOnsAndOffs(){
		return getSetOfOnsAndOffs(this);
	}
	
	
	
	private Set<Double> getSetOfOnsAndOffs(VoiceMuplet vm)
	{
		Set<Double> set = new TreeSet<Double>();
		set.add(vm.getPositionInBar());
		set.add(vm.getEndPositionInBar());
		for (Mu mu: vm.getMus())
		{
			double on = vm.getPositionInBar() + mu.getPositionInBarsAndBeats().getOffsetInQuarters();
			double off = on + vm.getLengthInQuarters();
			set.add(on);
			set.add(off);
		}
		return set;
	}

}