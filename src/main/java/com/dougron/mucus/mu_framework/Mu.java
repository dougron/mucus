package main.java.com.dougron.mucus.mu_framework;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import main.java.com.dougron.mucus.algorithms.mu_relationship.MuRelationship;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFi;
import main.java.com.dougron.mucus.algorithms.superimposifier.SuFiSu;
import main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right.SuFiSu_LeftToRight;
import main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right.SuFi_IntervalAndRhythmModel_Generator;
import main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right.SuFi_IntervalModel_Generator;
import main.java.com.dougron.mucus.algorithms.superimposifier.overwritable_vectors.SuFiSu_OverwritableVectors;
import main.java.com.dougron.mucus.mu_framework.chord_list.Chord;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordEvent;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordList;
import main.java.com.dougron.mucus.mu_framework.chord_list.ChordListGenerator;
import main.java.com.dougron.mucus.mu_framework.chord_list.SingleChordGenerator;
import main.java.com.dougron.mucus.mu_framework.data_types.BarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.data_types.MuAnnotation;
import main.java.com.dougron.mucus.mu_framework.data_types.MuNote;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;
import main.java.com.dougron.mucus.mu_framework.exceptions.SiblingToAddToIsNotAChildOfParentException;
import main.java.com.dougron.mucus.mu_framework.functional_interfaces.MuEvaluator;
import main.java.com.dougron.mucus.mu_framework.length_model.FixedLengthInBars;
import main.java.com.dougron.mucus.mu_framework.length_model.FixedLengthInBarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.length_model.FixedLengthInQuarters;
import main.java.com.dougron.mucus.mu_framework.length_model.LengthFromChildren;
import main.java.com.dougron.mucus.mu_framework.length_model.LengthModel;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagBundle;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagNamedParameter;
import main.java.com.dougron.mucus.mu_framework.position_model.BeginningOfParentInBars;
import main.java.com.dougron.mucus.mu_framework.position_model.BeginningOfParentInBarsAnBeats;
import main.java.com.dougron.mucus.mu_framework.position_model.BeginningOfParentInQuarters;
import main.java.com.dougron.mucus.mu_framework.position_model.EndOfSiblingInBars;
import main.java.com.dougron.mucus.mu_framework.position_model.EndOfSiblingInBarsAndBeats;
import main.java.com.dougron.mucus.mu_framework.position_model.EndOfSiblingInQuarters;
import main.java.com.dougron.mucus.mu_framework.position_model.PositionIsZeroInBars;
import main.java.com.dougron.mucus.mu_framework.position_model.PositionModel;
import main.java.com.dougron.mucus.mu_framework.ruler.Ruler;
import main.java.com.dougron.mucus.mu_framework.ruler.TimeSignatureListAndGenAndTempoListRuler;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureGenAndMap;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureList;
import main.java.com.dougron.mucus.mu_framework.ruler.time_signature_list.TimeSignatureListGenerator;
import main.java.da_utils.combo_variables.IntAndDouble;
import main.java.da_utils.combo_variables.IntAndString;
import main.java.da_utils.time_signature_utilities.time_signature.TimeSignature;
import test.java.com.dougron.mucus.mu_framework.key_signature_utilities.KeySignatureMap;

public class Mu
{

	public static final double DEFAULT_FIXED_LENGTH_IN_QUARTERS = 0.5;

		
	private LengthModel lengthModel;
	private PositionModel positionModel;
	private Mu parent;
	private List<Mu> mus = new ArrayList<Mu>();
	@Getter	@Setter private String name;
	private Ruler ruler;
	private ChordList chordList;
	ChordListGenerator chordListGenerator = new SingleChordGenerator();
	
	ArrayList<MuTagBundle> muTagBundles = new ArrayList<MuTagBundle>();
	
	private boolean hasTimeSignatureGenerator = false;
	private boolean hasChordListGenerator = false;
	private List<MuAnnotation> muAnnotations = null;
	private KeySignatureMap ksm = KeySignatureMap.KEY_SIGNATURE_MAP_OF_C_MAJOR;
	private List<MuNote> muNotes = null;

	private boolean isTupletPrintContainer;
	private int tupletNumerator;
	private int tupletDenominator;
	
	private boolean hasLeadingDoubleBar = false;

	private int startPitch;

	private SuFiSu sufisu = null;
	private MuGenerator muGenerator = null;

	private List<MuRelationship> muRelationships = null;

	private Mu nextMu = null;
	private Mu previousMu = null;
	private int muIndex = 0;
	
	private double controllerValue;
	private boolean hasControllerValue = false;
	
	
	public Mu getDeepCopy()
	{
		Mu mu = new Mu(getName() + "_copy");
		lengthModel.setSameLengthModel(mu);
		if (ruler != null) mu.ruler = ruler.getDeepCopy();
		mu.chordListGenerator = chordListGenerator;
		// chordlist will be recompiled from generator
		// muTags kept clear as generally may want to tag things differently 
		// muAnnotations remains clear
		mu.ksm = ksm;
		getDeepCopyOfMus(mu);
		mu.muNotes = getDeepCopyOfMuNotes();
		mu.isTupletPrintContainer = isTupletPrintContainer;
		if (isTupletPrintContainer)
		{
			mu.tupletNumerator = tupletNumerator;
			mu.tupletDenominator = tupletDenominator;
		}
		mu.hasLeadingDoubleBar = hasLeadingDoubleBar;
		mu.startPitch = startPitch;
		for (MuTagBundle mtb: muTagBundles)
		{
			mu.addMuTagBundle(mtb.copy());
		}
		
		return mu;
	}
	


	private List<MuNote> getDeepCopyOfMuNotes()
	{
		if (muNotes == null)
		{
			return null;
		}
		else
		{
			List<MuNote> list = new ArrayList<MuNote>();
			for (MuNote mn: muNotes)
			{
				list.add(mn.getDeepCopy());
			}
			return list;
		}	
	}



	private void getDeepCopyOfMus(Mu aParentMu)
	{
		HashMap<Mu, Mu> oldMuNewMuMap = makeOldMuNewMuMap();
		for (Mu oldMu: oldMuNewMuMap.keySet())
		{
			oldMu.getPositionModel().addAMuToAParentWithSamePositionModel(oldMuNewMuMap.get(oldMu), aParentMu, oldMuNewMuMap);
		}	
		Collections.sort(aParentMu.getMus(), globalPositionInQuartersComparator);
	}
	
	
	
	private HashMap<Mu, Mu> makeOldMuNewMuMap()
	{
		HashMap<Mu, Mu> map = new HashMap<Mu, Mu>();
		for (Mu mu: mus)
		{
			map.put(mu, mu.getDeepCopy());
		}
		return map;
	}



	public PositionModel getPositionModel()
	{
		return positionModel;		
	}



	public String getDeepCopyContentString()
	{
		String str;
		StringBuilder sb = new StringBuilder();
		sb.append("lengthModel------------:\n" + lengthModel.getContentString() + "\n");
		sb.append("positionModel----------:\n" + positionModel.getContentString() + "\n");
		if (ruler == null) str = "null"; else str = ruler.toString();
		sb.append("ruler------------------:\n" + str + "\n");
		sb.append("chordListGenerator-----:\n" + chordListGenerator.toString() + "\n");
		sb.append("keySignatureMap--------:\n" + ksm.toString() + "\n");
		sb.append("isTupletPrintContainer=" + isTupletPrintContainer + "\n");
		if (isTupletPrintContainer)
		{
			sb.append("  tupletNumerator=" + tupletNumerator + "\n");
			sb.append("  tupletDenominator=" + tupletDenominator + "\n");
		}
		sb.append("hasLeadingDoubleBar=" + hasLeadingDoubleBar + "\n");
		sb.append("startPitch=" + startPitch + "\n");
		sb.append("muNotes-----------------:\n");
		if (muNotes == null)
		{
			sb.append("null\n");
		}
		else
		{
			for (MuNote muNote: muNotes)
			{
				sb.append(muNote.toString() + "\n");
			}
		}
		sb.append("mus---------------------:\n");
		if (mus.size() == 0) sb.append("empty\n");
		for (Mu mu: mus)
		{
			sb.append(mu.getDeepCopyContentString() + "\n");
		}
		return sb.toString();
	}



//	private void addMuToParentWithSamePositionModel(Mu aParentMu, Mu aMu)
//	{
//		positionModel.addMuToParentWithSamePositionModel(aParentMu, aMu);
//		
//	}



	public Mu(String aName)
	{
		lengthModel = new LengthFromChildren(this);		// default 
		positionModel = new PositionIsZeroInBars();		// default
		name = aName;
	}

	
	
	public Mu getParent()
	{
		return parent;
	}



	public int getPositionInBars()
	{	
		return positionModel.getPositionInBars();
	}
	
	
	
	public BarsAndBeats getPositionInBarsAndBeats()
	{
		return positionModel.getPositionInBarsAndBeats();
	}




	public int getLengthInBars()
	{
		return lengthModel.getLengthInBars();
	}


	
	public BarsAndBeats getLengthInBarsAndBeats()
	{
		return lengthModel.getLengthInBarsAndBeats();
	}
	
	

	public void setLengthInBars(int aLengthInBars)
	{
		lengthModel = new FixedLengthInBars(aLengthInBars, this);
		recalculateLengthAndRecompileTimeSignatureAndChords();
	}
	
	
	
	public void setLengthInBarsAndBeats(BarsAndBeats aBarsAndBeats)
	{
		lengthModel = new FixedLengthInBarsAndBeats(aBarsAndBeats, this);	
	}



	private void recalculateLengthAndRecompileTimeSignatureAndChords()
	{
		recompileTimeSignatures();
		if (parent != null) parent.recalculateLength();
		if (hasTimeSignatureGenerator) 
		{
			ruler.setLengthInBarsAndBeats(new BarsAndBeats(lengthModel.getLengthInBars(), 0.0));
		}
		recompileChordList();
	}
	
	
	
	public void setLengthInQuarters(double aLengthInQuarters)
	{
		lengthModel = new FixedLengthInQuarters(aLengthInQuarters, this);
		recalculateLengthAndRecompileTimeSignatureAndChords();
	}
	


	public void setPositionInBars(int aPositionInBars)
	{
		positionModel.setPositionInBars(aPositionInBars);
		
		if (!(positionModel instanceof PositionIsZeroInBars))
		{
			recalculateLengthAndRecompileTimeSignatureAndChords();
		}
	}

	
	
	private void recompileChordList()
	{
		if (parent == null)
		{
			checkForNullRulerAndCreate();
			int lengthInBars = getLengthInBars();
			TimeSignatureGenAndMap tsgm = ruler.getTSGenAndMap();
			chordList = chordListGenerator.getChordList(tsgm, lengthInBars);
			for (Mu mu: allDecendantMusWithChordList())	
			{
				int barPosition = mu.getGlobalPositionInBars(0);
				BarsAndBeats bab = new BarsAndBeats(barPosition, 0.0);
				chordList.addChordList(mu.getChordList(), bab, ruler.getTSGenAndMap());
			}
		}
		else
		{
			parent.recompileChordList();
		}
	}



	public void recompileTimeSignatures()
	{
		if (parent == null)
		{
			checkForNullRulerAndCreate();
			
			ruler.clearTimeSignatureMap();
			ruler.setLengthInBarsAndBeats(getLengthInBarsAndBeats());
			for (Mu mu: allDecendantMusWithTimeSignature())	
			{
				int barPosition = mu.getGlobalPositionInBars(0);
				BarsAndBeats bab = new BarsAndBeats(barPosition, 0.0);
				ruler.replaceTimeSignature(bab, mu.getRuler());
				ruler.setHasTimeSignature(true);
			}
			ruler.setLengthInBarsAndBeats(new BarsAndBeats(lengthModel.getLengthInBars(), 0.0));
		}
		else
		{
			parent.recompileTimeSignatures();
		}
	}



	public Ruler getRuler()
	{
		return ruler;
	}
	


	private void checkForNullChordListAndCreate()
	{
		if (chordList == null) chordList = new ChordList();
		chordList.setLengthInBarsAndBeats(getLengthInBarsAndBeats());
	}



	public void checkForNullRulerAndCreate()
	{
		if (ruler == null) ruler = new TimeSignatureListAndGenAndTempoListRuler();		
	}



	public void addMu(Mu aMu, int aBarPosition)
	{
//		System.out.println("addMu(" + aMu.getName() + ", " + aBarPosition + ")");
		aMu.setParent(this);
		aMu.setPositionModel(new BeginningOfParentInBars(aBarPosition, aMu));
		mus.add(aMu);
		recalculateLength();
		recompileTimeSignatures();
		recompileChordList();
	}
	
	
	
	public void addMu(Mu aMu, BarsAndBeats aBarsAndBeatsPosition)
	{
//		System.out.println("addMu(" + aMu.getName() + ", " + aBarsAndBeatsPosition.toString() + ")");
		if (lengthModel instanceof FixedLengthInBars || lengthModel instanceof FixedLengthInQuarters)
		{
			aMu.setParent(this);
			aMu.setPositionModel(new BeginningOfParentInBarsAnBeats(aBarsAndBeatsPosition, aMu));
			if (!aMu.isFixedLengthInQuarters()) aMu.setLengthInQuarters(DEFAULT_FIXED_LENGTH_IN_QUARTERS);
			aMu.setHasTimeSignatureGenerator(false);
			mus.add(aMu);
		}		
		else if (lengthModel instanceof LengthFromChildren || lengthModel instanceof FixedLengthInBarsAndBeats)
		{
			aMu.setParent(this);
			aMu.setPositionModel(new BeginningOfParentInBarsAnBeats(aBarsAndBeatsPosition, aMu));
			mus.add(aMu);
			lengthModel.calculateLength(mus);
		}
		Collections.sort(mus, globalPositionInQuartersComparator);
		
	}
	
	
	
	public static Comparator<Mu> globalPositionInQuartersComparator = new Comparator<Mu>()
	{

		@Override
		public int compare(Mu o1, Mu o2)
		{
			double pos1 = o1.getGlobalPositionInQuarters();
			double pos2 = o2.getGlobalPositionInQuarters();
			if (pos1 < pos2) return -1;
			if (pos2 < pos1) return 1;
			return 0;
		}		
	};
	
	
	
	public static Comparator<Mu> getAbsoluteQuartersDistanceComparator(double aGlobalPositionInQuarters) {
	    return Comparator.comparingDouble(p -> Math.abs(aGlobalPositionInQuarters - p.getGlobalPositionInQuarters()));
	}
	
	
	
	
	public void addMu(Mu aMu, double aPositionInQuarters)
	{
//		System.out.println("addMu(" + aMu.getName() + ", " + aPositionInQuarters + ")");
		aMu.setParent(this);
		aMu.setPositionModel(new BeginningOfParentInQuarters(aPositionInQuarters, aMu));
		mus.add(aMu);
		if (lengthModel instanceof LengthFromChildren) 
		{
			lengthModel.calculateLength(mus);
		}
		else
		{
			if (!aMu.isFixedLengthInQuarters()) aMu.setLengthInQuarters(DEFAULT_FIXED_LENGTH_IN_QUARTERS);
		}
//		addMu(aMu, new BarsAndBeats(0, aPositionInQuarters));	
		
	}



	private boolean isFixedLengthInQuarters()
	{
		return lengthModel instanceof FixedLengthInQuarters;
	}



	private void setHasTimeSignatureGenerator(boolean b)
	{
		if (ruler != null) ruler.setHasTimeSignature(b);
		hasTimeSignatureGenerator = b;
	}



	public void setPositionModel(PositionModel aPositionModel)
	{
		positionModel = aPositionModel;		
	}



	public void setParent(Mu mu)
	{
		parent = mu;		
	}



	public int getEndPositionInBars()
	{
		if (parent == null) return getGlobalBarIndexOfEnd();
		return getGlobalBarIndexOfEnd() - getParent().getGlobalPositionInBars();
	}
	
	
	
	public BarsAndBeats getEndPositionInBarsAndBeats()
	{
		int parentGlobalStartBar = parent.getGlobalPositionInBars();
		BarsAndBeats thisEnd = getGlobalEndPositionInBarsAndBeats();
		return new BarsAndBeats(thisEnd.getBarPosition() - parentGlobalStartBar, thisEnd.getOffsetInQuarters());
	}
	
	
	
	public double getEndPositionInQuarters()
	{
		double positionOfParentInQuarters = parent.getGlobalPositionInQuarters();
		return getGlobalEndPositionInQuarters() - positionOfParentInQuarters;
	}


	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append( "\nmu:" + name);
		sb.append("\n" + positionModel.positionToString() + "\n" + lengthModel.positionToString());		
		sb.append("\nglobalPositionInQuarters=" + getGlobalPositionInQuarters());
		addRulerToStringInfo(sb);
		addMuCount(sb);
		addMuNotes(sb);

		if (muTagBundles != null && muTagBundles.size() > 0 )sb.append("\n");
		for (MuTagBundle muTagBundle: muTagBundles)
		{
			sb.append(muTagBundle.toString() + ",");
		}
		sb.append("\n");
		return sb.toString();
	}



	private void addMuNotes(StringBuilder sb)
	{
		sb.append("\nmuNotes:");
		if (muNotes == null || muNotes.size() == 0)
		{
			sb.append("--none--");
		}
		else
		{
			for (MuNote note: muNotes)
			{
				sb.append("(" + note.getPitch() + "," + note.getVelocity() + ")");
			}
		}
	}



	private void addMuCount(StringBuilder sb)
	{
		int count = 0;
		if (mus != null) count  = mus.size();
		sb.append("\nmu count=" + count);
	}



	private void addRulerToStringInfo(StringBuilder sb)
	{
		if (ruler == null)
		{
			sb.append("\nruler=null");
		}
		else
		{
//			if (hasTimeSignatureGenerator)
//			{
//				sb.append("\n<<placeholder for timesignature(s)>>");	// + getTimeSignaturesToString());
//			}
//			else
//			{
//				sb.append("\n...");
//			}	
			sb.append("\n" + ruler.toString());
		}
	}



	// aMu is added to aSiblingToAddTo 
	public void addMuToEndOfSibling(Mu aMuToAdd, int aPositionInBars, Mu aSiblingToAddTo)
	{
//		System.out.println("addMuToEndOfSibling(" + aMuToAdd.getName() + ", " + aPositionInBars + "," + aSiblingToAddTo.getName() + ")");
		try
		{
			if (!mus.contains(aSiblingToAddTo))
			{
				throw new SiblingToAddToIsNotAChildOfParentException();
			}
			aMuToAdd.setParent(this);
			aMuToAdd.setPositionModel(new EndOfSiblingInBars(aPositionInBars, aMuToAdd, aSiblingToAddTo));
			mus.add(aMuToAdd);
			recalculateLength();
			recompileTimeSignatures();
			recompileChordList();
		}
		catch (SiblingToAddToIsNotAChildOfParentException e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	
	public void addMuToEndOfSibling(Mu aMuToAdd, BarsAndBeats aBarsAndBeats, Mu aSiblingToAddTo)
	{
//		System.out.println("addMuToEndOfSibling(" + aMuToAdd.getName() + ", " + aBarsAndBeats + "," + aSiblingToAddTo.getName() + ")");
		aMuToAdd.setParent(this);
		aMuToAdd.setPositionModel(new EndOfSiblingInBarsAndBeats(aBarsAndBeats, aMuToAdd, aSiblingToAddTo));
		mus.add(aMuToAdd);
		recalculateLength();
		recompileTimeSignatures();	
		recompileChordList();
	}
	
	
	
	public void addMuToEndOfSibling(Mu aMuToAdd, double aPositionInQuarters, Mu aSiblingToAddTo)
	{
//		System.out.println("addMuToEndOfSibling(" + aMuToAdd.getName() + ", " + aPositionInQuarters + "," + aSiblingToAddTo.getName() + ")");
		aMuToAdd.setParent(this);
		aMuToAdd.setPositionModel(new EndOfSiblingInQuarters(aPositionInQuarters, aMuToAdd, aSiblingToAddTo));
		mus.add(aMuToAdd);
		recalculateLength();
		recompileTimeSignatures();
		recompileChordList();	
	}



	public void setToGetLengthFromChildren()
	{
		int len = lengthModel.getLengthInBars();
		lengthModel = new LengthFromChildren(this);
		lengthModel.calculateLength(mus);
		if (len != lengthModel.getLengthInBars()) parent.recalculateLength();
	}



	public void recalculateLength()
	{
		int len = lengthModel.getLengthInBars();
		lengthModel.calculateLength(mus);
		if (len != lengthModel.getLengthInBars() && parent != null) parent.recalculateLength();		
	}



	public int getLocalPositionInBars(int aOffsetPositionInBars, Mu aAncestorMu)
	{
		return positionModel.getLocalPositionInBars(aAncestorMu) + aOffsetPositionInBars;
	}
	
	
	
	public int getLocalPositionInBars(Mu aAncestorMu)
	{
		return positionModel.getLocalPositionInBars(aAncestorMu);
	}
	
	
	
	public double getLocalPositionInQuarters(Mu aAncestorMu)
	{
		return positionModel.getLocalPositionInQuarters(aAncestorMu);
	}
	
	
	
	public BarsAndBeats getLocalPositionInBarsAndBeats(Mu aAncestorMu)
	{
		return positionModel.getLocalPositionInBarsAndBeats(aAncestorMu);
	}
	
	
	
	private BarsAndBeats getLocalPositionInBarsAndBeats(BarsAndBeats aGlobalPositionInBarsAndBeats)
	{
		return positionModel.getLocalPositionInBarsAndBeats(aGlobalPositionInBarsAndBeats);
	}
	
	
	
//	private TimeSignatureList getTimeSignatureList(int aGlobalStartPositionInBars, int aLengthInBars)
//	{
//		if (parent == null)
//		{
//			checkForNullRulerAndCreate();
//			return ruler.getTimeSignatureList().getSubsetTimeSignature(aGlobalStartPositionInBars, aLengthInBars);
//		}
//		else
//		{
//			return parent.getTimeSignatureList(aGlobalStartPositionInBars, aLengthInBars);
//		}
//	}



	public String getTimeSignaturesToString()
	{
		if (getLengthInBars() == 0)
		{
			return "";
		}
		else 
		{
			return getTimeSignatureToStringFromTop(getGlobalPositionInBars(), getLengthInBars());	
		}
	}



	private String getTimeSignatureToStringFromTop(int aGlobalStartPositionInBars, int aLengthInBars)
	{
		if (parent == null)
		{
			return ruler.getTimeSignatureList()
					.getSubsetTimeSignature(aGlobalStartPositionInBars, aLengthInBars)
					.getTimeSignaturesToString();
		}
		else
		{
			return parent.getTimeSignatureToStringFromTop(aGlobalStartPositionInBars, aLengthInBars);
		}
		
	}



	public void setTimeSignatureGenerator(TimeSignatureListGenerator aGenerator)
	{
		checkForNullRulerAndCreate();
		ruler.setTimeSignatureGenerator(aGenerator);	
		hasTimeSignatureGenerator = true;
		ruler.setLengthInBarsAndBeats(new BarsAndBeats(lengthModel.getLengthInBars(), 0.0));
		recompileTimeSignatures();
		lengthModel.calculateLength(mus);
	}



	public boolean hasTimeSignatureGenerator()
	{
		return hasTimeSignatureGenerator;
	}



	public boolean hasChordListGenerator()
	{
		return hasChordListGenerator;
	}
	
	
	
	private List<Mu> allDecendantMusWithTimeSignature()
	{
		List<Mu> list = new ArrayList<Mu>();
		for (Mu mu: mus)
		{
			List<Mu> tempList = mu.allDecendantMusWithTimeSignature();
			if (mu.hasTimeSignatureGenerator()) tempList.add(0, mu);
			if (tempList.size() > 0) list.addAll(tempList);
		}
		return list;
	}
	
	
	
	private List<Mu> allDecendantMusWithChordList()
	{
		List<Mu> list = new ArrayList<Mu>();
		for (Mu mu: mus)
		{
			List<Mu> tempList = mu.allDecendantMusWithChordList();
			if (mu.hasChordListGenerator()) tempList.add(0, mu);
			if (tempList.size() > 0) list.addAll(tempList);
		}
		return list;
	}



	public String getChordListToString()
	{
		if (lengthModel.getLengthInBars() == 0)
		{
			return "";
		}
		else
		{
			return getChordList().chordsToString();
		}		
	}

	
	
	public ChordList getChordList()
	{
		checkForNullChordListAndCreate();
		return chordList;
	}



	public void setChordListGenerator(ChordListGenerator aGenerator)
	{
		chordListGenerator = aGenerator;
		hasChordListGenerator = true;
		recompileChordList();
	}



	public List<ChordEvent> getChordEventList()
	{
		return chordList.getChordEventList();
	}



	public int getNumberOfMuAnnotations()
	{
		if (muAnnotations == null) return 0; else return muAnnotations.size();
	}
	
	
	
	public int getNumberOfMus()
	{
		return mus.size();
	}
	
	
	
	public int getNumberOfMuNotes()
	{
		if (muNotes == null) return 0; else	return muNotes.size();
	}




	public void addMuAnnotation(MuAnnotation aMuAnnotation)
	{
		if (muAnnotations == null) muAnnotations = new ArrayList<MuAnnotation>();
		muAnnotations.add(aMuAnnotation);		
	}



	public List<MuAnnotation> getMuAnnotations()
	{
		if (muAnnotations == null) return new ArrayList<MuAnnotation>();
		return muAnnotations;
	}

	

	public KeySignatureMap getKeySignatureMap()
	{
		if (ksm  == null) makeDefaultKeySignatureMap();
		return ksm;
	}
	
	
	
	private void makeDefaultKeySignatureMap()
	{
		ksm = new KeySignatureMap(0);		
	}
	
	
	
	public void setXMLKey(int aXMLKey)
	{
		ksm = new KeySignatureMap(aXMLKey);		
	}



	public List<Mu> getMusWithNotes()
	{
		List<Mu> list = new ArrayList<Mu>();
		if (isTupletPrintContainer)
		{
			list.add(this);
		}
		else
		{
			if (hasMuNotes()) list.add(this);
			for (Mu mu: mus)
			{
				list.addAll(mu.getMusWithNotes());
			}
		}		
		return list;
	}
	
	
	
	public List<Mu> getMusWithNotesIgnoringTupletHolders()
	{
		List<Mu> list = new ArrayList<Mu>();
		if (hasMuNotes()) list.add(this);
		for (Mu mu: mus)
		{
			list.addAll(mu.getMusWithNotesIgnoringTupletHolders());
		}
		return list;
	}
	
	
	
	public List<Mu> getMusWithAnnotationsButNoNotes()
	{
		List<Mu> list = new ArrayList<Mu>();
		if (hasMuAnnotations() && !hasMuNotes() && !isTupletPrintContainer) list.add(this);
		for (Mu mu: mus)
		{
			list.addAll(mu.getMusWithAnnotationsButNoNotes());
		}
		return list; 
	}
	
	
	
	public List<Mu> getMusWithControllerValues()
	{
		List<Mu> list = new ArrayList<Mu>();
		if (hasControllerValue()) list.add(this);
		for (Mu mu: mus)
		{
			if (mu.hasControllerValue()) list.add(mu);
		}
		return list; 	
	}
	
	
	
	public List<Mu> getAllMus()
	{
		List<Mu> list = new ArrayList<Mu>();
		list.add(this);
		for (Mu mu: mus)
		{
			list.addAll(mu.getAllMus());
		}
		return list;
	}



	public boolean isTupletPrintContainer()
	{
		return isTupletPrintContainer;
	}



	public int getTupletNumerator()
	{
		return tupletNumerator;
	}



	public int getTupletDenominator()
	{
		return tupletDenominator;
	}



	public double getPositionInQuarters()
	{
		return positionModel.getPositionInQuarters();
	}



	public double getLengthInQuarters()
	{
		return lengthModel.getLengthInQuarters();
	}



	public List<MuNote> getMuNotes()
	{
		testForNullMuNotesAndCreate();
		return muNotes;
	}



	private void testForNullMuNotesAndCreate()
	{
		if (muNotes == null) muNotes = new ArrayList<MuNote>();
	}

	
	
	public List<Mu> getMus()
	{
		return mus;
	}
	
	
	public void clearMus()
	{
		mus.clear();
	}



	public boolean hasMuNotes()
	{
		if (muNotes != null && muNotes.size() > 0) return true;
		return false;
	}
	
	
	
	public boolean hasMuAnnotations()
	{
		if (muAnnotations != null && muAnnotations.size() > 0) return true;
		return false;
	}



	public void addMuNote(MuNote aMuNote)
	{
		testForNullMuNotesAndCreate();
		muNotes.add(aMuNote);		
	}



	public void setIsTupletPrintContainer(boolean isTupletPrintContainer)
	{
		this.isTupletPrintContainer = isTupletPrintContainer;
	}



	public void setTupletNumerator(int tupletNumerator)
	{
		this.tupletNumerator = tupletNumerator;
	}



	public void setTupletDenominator(int tupletDenominator)
	{
		this.tupletDenominator = tupletDenominator;
	}



	public int getStartPitch()
	{
		return startPitch;
	}



	public void setStartPitch(int aPitch)
	{
		startPitch = aPitch;
	}



	// assumes at beginning of current Mu, but not relevant until KeySignatureMap has properly implemented key ranges
	// this derives diatonicism from the xmlKey, not from the prevailing chord in the chordList
	public boolean isDiatonicNoteInXMLKey(int aPitch)
	{
		if (parent == null)
		{
			return ksm.isDiatonicNote(aPitch);
		}
		return parent.isDiatonicNoteInXMLKey(aPitch);
	}



	public void setGenerator(SuFiSu_LeftToRight aSiFiSu)
	{
		sufisu = aSiFiSu;
	}



	public void runSuFiSuGenerator()
	{
		if (sufisu != null)
		{
			mus.clear();
			sufisu.generate();		
		}
	}
	
	
	
	// NB !!!!! If you change this you must resolve the test failures that it will cause
	public String stateOfMusToString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nMu=====" + name + "===============\n");
		sb.append(positionModel.positionToString() + "\n");
		sb.append(lengthModel.positionToString() + "\n");		
		if (isTupletPrintContainer)
		{
			sb.append("\n isTupletPrintContainer=true: " 
					+ getTupletNumerator() 
					+ " notes in the space of " 
					+ getTupletDenominator()); 
		}
		if (getParent() == null) 
		{
			sb.append(" no parent");
		} 
		else 
		{
			sb.append(" parent=" + getParent().getName());
		}
		sb.append("\n MuTags:");
		for (MuTagBundle muTagBundle: muTagBundles)
		{
			sb.append(muTagBundle.toString() + ",");
		}
		sb.append("\n MuNotes: " + getNumberOfMuNotes() + " items");
		for (MuNote mn : getMuNotes())
		{
			sb.append("\n   " + mn.testOutputToString());
		} 
		sb.append("\n MuAttonations: " + getNumberOfMuAnnotations() + " items");
		for (MuAnnotation ma: getMuAnnotations()) 
		{
			sb.append("\n   " + ma.toString());
		}
		sb.append( "\n Mus: " + getNumberOfMus() + " items");
		for (Mu mu: getMus()) 
		{
			sb.append("\n   " + mu.stateOfMusToString());
		}		
		return sb.toString();
	}



	public void addSuFi(SuFi aSuFi)
	{
		ifNullMakeSuFiSuToMatchIncomingSufi(aSuFi);
		aSuFi.setParent(sufisu);
		sufisu.addSufi(aSuFi);
	}
	


	private void ifNullMakeSuFiSuToMatchIncomingSufi(SuFi aSuFi)
	{
		if (sufisu == null)
		{
			if (aSuFi instanceof SuFi_IntervalModel_Generator 
					|| aSuFi instanceof SuFi_IntervalAndRhythmModel_Generator
					)
			{
				sufisu = new SuFiSu_LeftToRight(this);
			}
			else
			{
				sufisu = new SuFiSu_OverwritableVectors(this);
			}
		}		
	}



	public void addMuRelationship(MuRelationship aMuRelationship)
	{
		aMuRelationship.setDependantMu(this);
		if (muRelationships == null) muRelationships = new ArrayList<MuRelationship>();
		muRelationships .add(aMuRelationship);
	}



	public void generate()
	{
		renderMuRelationships();
		runSuFiSuGenerator();
		runMuGenerator();
		for (Mu mu: mus)
		{
			mu.generate();		
		}
	}



	private void runMuGenerator()
	{
		if (muGenerator != null)
		{
			mus.clear();
			muGenerator.generate();
		}
		
	}



	private void renderMuRelationships()
	{
		if (muRelationships != null)
		{
			for (MuRelationship muR: muRelationships)
			{
				muR.generate();
			}
		}
	}



	public SuFiSu getSuFiSu()
	{
		return sufisu;
	}



	public int getTopPitch()
	{
		int pitch = 0;
		if (muNotes != null)
		{
			for (MuNote mn : muNotes)
			{
				if (mn.getPitch() > pitch) pitch = mn.getPitch();
			} 
		}
		return pitch;
	}



	public boolean hasLeadingDoubleBar()
	{
		return hasLeadingDoubleBar;
	}



	public void setHasLeadingDoubleBar(boolean hasLeadingDoubleBar)
	{
		this.hasLeadingDoubleBar = hasLeadingDoubleBar;
	}



	public List<Mu> getListOfMusWithBarlineInfo()
	{
		List<Mu> list = new ArrayList<Mu>();
		if (hasLeadingDoubleBar) list.add(this);
		for (Mu mu: mus)
		{
			list.addAll(mu.getListOfMusWithBarlineInfo());
		}
		return list;
	}



	public void setPositionInBarsAndBeats(BarsAndBeats aBarsAndBeats)
	{
		setPositionModel(new BeginningOfParentInBarsAnBeats(aBarsAndBeats, this));		
	}



	public boolean hasMus()
	{
		if (mus.size() > 0) return true;
		return false;
	}



	public void removeMu(Mu numu)
	{
		mus.remove(numu);	
	}



	public TimeSignature getTimeSignature(int aPositionInBars)
	{
		if (parent == null)
		{
			checkForNullRulerAndCreate();
			return ruler.getTimeSignature(aPositionInBars);
		}
		else 
		{
			return parent.getTimeSignature(aPositionInBars + positionModel.getPositionInBars());
		}
	}
	
	
	
	public TimeSignature getTimeSignatureFromGlobalBarPosition(int aGlobalPositionInBars)
	{
		if (parent == null)
		{
			checkForNullRulerAndCreate();
			return ruler.getTimeSignature(aGlobalPositionInBars);
		}
		else 
		{
			return parent.getTimeSignatureFromGlobalBarPosition(aGlobalPositionInBars);
		}
	}



	public TimeSignatureGenAndMap getMasterTimeSignatureGenAndMap()
	{
		if (parent == null)
		{
			checkForNullRulerAndCreate();
			return ruler.getTSGenAndMap();
		}
		else
		{
			return parent.getMasterTimeSignatureGenAndMap();
		}

	}




// ----- global position stuff -------------------------------------------------------
	
	
	
	/*
	 * if the Mu ends on an exact bar then that is the bar, otherwise it is the next bar
	 */
	public int getGlobalBarIndexAfterEnd()
	{
		double globalPosition = getGlobalPositionInQuarters();
		double length = getLengthInQuarters();
		BarsAndBeats bab = getGlobalPositionInBarsAndBeats(globalPosition + length);
		return bab.getBarPosition() + (int)Math.signum(bab.getOffsetInQuarters());
	}
	
	
	/*
	 * if the Mu ends on an exact bar then that is the bar, otherwise it is the bar that the 
	 * Mu ends in (i.e. last bar before the end)
	 */
	public int getGlobalBarIndexOfEnd()
	{
		double globalPosition = getGlobalPositionInQuarters();
		double length = getLengthInQuarters();
		BarsAndBeats bab = getGlobalPositionInBarsAndBeats(globalPosition + length);
		return bab.getBarPosition();
	}
	
	
	
	public int getGlobalPositionInBars()
	{
		return getGlobalPositionInBars(0);
	}
	


	public int getGlobalPositionInBars(int aPositionInBars)
	// aPositionInBars here is an offset from the beginning of this Mu
	{
		return positionModel.getGlobalPositionInBars() + aPositionInBars;
	}

	
	
	public BarsAndBeats getGlobalPositionInBarsAndBeats()
	{		
		return getGlobalPositionInBarsAndBeats(positionModel.getGlobalPositionInQuarters());
	}


	
	public BarsAndBeats getGlobalPositionInBarsAndBeats(double globalPositionInQuarters)
	{
		if (parent == null)
		{
			checkForNullRulerAndCreate();
			return ruler.getPositionInBarsAndBeats(globalPositionInQuarters);
		}
		else
		{
			return parent.getGlobalPositionInBarsAndBeats(globalPositionInQuarters);
		}
	}
	
	
	
	public double getGlobalPositionInQuartersFromGlobalPositionInFloatBars(double aGlobalPositionInFloatBars)
	{
		if (parent == null)
		{
			int barPosition = (int)aGlobalPositionInFloatBars;
			double position = getGlobalPositionInQuarters(barPosition);
			TimeSignature ts = getTimeSignature(barPosition);
			double offset = ts.getLengthInQuarters() * (aGlobalPositionInFloatBars % 1.0);
			return position + offset;
		}
		else
		{
			return parent.getGlobalPositionInQuartersFromGlobalPositionInFloatBars(aGlobalPositionInFloatBars);
		}		
	}
	
	
	
	public BarsAndBeats getGlobalPositionInBarsAndBeatsFromGlobalPositionInFloatBars(double aGlobalPositionInFloatBars)
	{
		if (parent == null)
		{
			int barPosition = (int)aGlobalPositionInFloatBars;
//			double position = getGlobalPositionInQuarters(barPosition);
			TimeSignature ts = getTimeSignature(barPosition);
			double offset = ts.getLengthInQuarters() * (aGlobalPositionInFloatBars % 1.0);
			return new BarsAndBeats(barPosition, offset);
		}
		else
		{
			return parent.getGlobalPositionInBarsAndBeatsFromGlobalPositionInFloatBars(aGlobalPositionInFloatBars);
		}		
	}
	


	public double getGlobalPositionInQuarters()
	{
		return getGlobalPositionInQuarters(0.0);
	}



	public double getGlobalPositionInQuarters(double aOffsetInQuarters)
	{
		return positionModel.getGlobalPositionInQuarters() + aOffsetInQuarters;
	}
	
	
	
	public double getGlobalPositionInQuarters(int aGlobalBarPosition)
	{
		return getMasterTimeSignatureGenAndMap().getPositionInQuarters(aGlobalBarPosition);
	}



	public double getGlobalEndPositionInQuarters()
	{
		return getGlobalPositionInQuarters() + getLengthInQuarters();
	}



	public BarsAndBeats getGlobalEndPositionInBarsAndBeats()
	{
		return getGlobalPositionInBarsAndBeats(getGlobalEndPositionInQuarters());
	}



	public boolean hasLengthModelFromChildren()
	{
		if (lengthModel instanceof LengthFromChildren) return true;
		return false;
	}



	public boolean hasParent()
	{
		if (parent == null) return false;
		return true;
	}



	public double getPositionInQuarters(BarsAndBeats aBarsAndBeats)
	{
		BarsAndBeats bab = new BarsAndBeats(
				getGlobalPositionInBarsAndBeats().getBarPosition() + aBarsAndBeats.getBarPosition(),
				getGlobalPositionInBarsAndBeats().getOffsetInQuarters() + aBarsAndBeats.getOffsetInQuarters()
				);
		return getGlobalPositionInQuarters(bab) + getGlobalPositionInQuarters();
	}



	public double getGlobalPositionInQuarters(BarsAndBeats aGlobalBarsAndBeats)
	{
		return getGlobalPositionInQuarters(aGlobalBarsAndBeats.getBarPosition()) 
				+ aGlobalBarsAndBeats.getOffsetInQuarters();
	}



	public TimeSignatureList getTimeSignatureList()
	{
		TimeSignatureList tsl = new TimeSignatureList();
		int endBarIndex = getGlobalBarIndexAfterEnd();
		for (int i = 0; i < endBarIndex; i++)
		{
			tsl.add(getTimeSignature(i));
		}
		return tsl;
	}



	public void movePosition(double aOffsetInQuarters)
	{
		positionModel.movePosition(aOffsetInQuarters);
		
	}



	public void movePosition(BarsAndBeats aOffsetInBarsAndBeats)
	{
		positionModel.movePosition(aOffsetInBarsAndBeats);
		
	}
	
	
	public void movePosition(int aOffsetInBars)
	{
		positionModel.movePosition(aOffsetInBars);
	}
	
	
	public Class<? extends PositionModel> getClassOfPositionModel()
	{
		return positionModel.getClass();
	}



	public double getClosestTactus(double aPositionInFloatBars)
	{
		return ruler.getClosestTactusInQuarters(aPositionInFloatBars);
	}



	public Chord getChordAt(BarsAndBeats aBarsAndBeats)
	{	
		if (parent == null)
		{
			return chordList.getChord(aBarsAndBeats.getBarPosition(), aBarsAndBeats.getOffsetInQuarters());
		}
		else
		{
			BarsAndBeats globalPositionInBarsAndBeats = new BarsAndBeats(
					getGlobalPositionInBars() + aBarsAndBeats.getBarPosition(),
					aBarsAndBeats.getOffsetInQuarters());
			return getChordAtGlobalPosition(globalPositionInBarsAndBeats);
		}
		 
	}



	public Chord getChordAtGlobalPosition(BarsAndBeats aGlobalPositionInBarsAndBeats)
	{
		if (parent == null)
		{
			return chordList.getChord(
					aGlobalPositionInBarsAndBeats.getBarPosition(), 
					aGlobalPositionInBarsAndBeats.getOffsetInQuarters()
					);			
		}
		else
		{
			return parent.getChordAtGlobalPosition(aGlobalPositionInBarsAndBeats);
		}		
	}
	
	
	
	public Chord getPrevailingChord()
	{
		if (parent == null)
		{
			return getChordAtGlobalPosition(new BarsAndBeats(0, 0.0));
		}
		else
		{
			return getChordAtGlobalPosition(getGlobalPositionInBarsAndBeats());
		}
	}



	public Mu getMu(String aName)
	{
		for (Mu mu: mus)
		{
			if (mu.getName().equals(aName))
			{
				return mu;
			}
		}
		return null;
	}



	public void clearMuNotes()
	{
		muNotes.clear();	
	}



	public double getGlobalPositionInFloatBars()
	{
		BarsAndBeats globalPositionInBarsAndBeats = getGlobalPositionInBarsAndBeats();
		TimeSignature ts = getTimeSignature(globalPositionInBarsAndBeats.getBarPosition());
		return globalPositionInBarsAndBeats.getBarPosition() 
				+ globalPositionInBarsAndBeats.getOffsetInQuarters() 
				/ ts.getLengthInQuarters();
	}
	
	
	
	public double getGlobalPositionInFloatBars(double aGlobalPositionInQuarters)
	{
		BarsAndBeats globalPositionInBarsAndBeats = getGlobalPositionInBarsAndBeats(aGlobalPositionInQuarters);
		TimeSignature ts = getTimeSignature(globalPositionInBarsAndBeats.getBarPosition());
		return globalPositionInBarsAndBeats.getBarPosition() 
				+ globalPositionInBarsAndBeats.getOffsetInQuarters() 
				/ ts.getLengthInQuarters();
	}



	public void addMuGenerator(MuGenerator mug)
	{
		muGenerator = mug;	
		muGenerator.setParent(this);
	}



	public MuGenerator getMuGenerator()
	{
		return muGenerator;
	}



	public MuTagBundle addTag(MuTag aMuTag)
	{
		MuTagBundle mtb = new MuTagBundle(aMuTag);
		muTagBundles.add(mtb);
		return mtb;
	}
	
	
	
	public MuTagBundle addTag(MuTag[] aMuTags)
	{
		MuTagBundle mtb = new MuTagBundle(aMuTags);
		muTagBundles.add(mtb);
		return mtb;
	}
	
	
	public void removeMuTagBundleContainingTag(MuTag aMuTag)
	{
		List<MuTagBundle> list = muTagBundles.stream()
				.filter(e -> !e.containsMuTag(aMuTag))
				.collect(Collectors.toList());
		muTagBundles = (ArrayList<MuTagBundle>)list;
	}
	
	
	
	public void addMuTagBundle (MuTagBundle aMuTagBundle)
	{
		muTagBundles.add(aMuTagBundle);		
	}

	
	
	
	public boolean hasTag(MuTag aMuTag)
	{
		for (MuTagBundle muTagBundle: muTagBundles)
		{
			if (muTagBundle.containsMuTag(aMuTag)) return true;
		}
		return false;
	}
	
	
	
	public boolean hasBundledTags(MuTag[] muTags)
	{
		for (MuTagBundle muTagBundle: muTagBundles)
		{
			int count = 0;
			for (MuTag muTag: muTags)
			{
				if (muTagBundle.containsMuTag(muTag)) count++;
			}
			if (count == muTags.length) return true;
		}
		return false;
	}
	
	
	
	public ArrayList<MuTagBundle> getMuTagBundleContaining(MuTag aMuTag)
	{
		ArrayList<MuTagBundle> list = new ArrayList<MuTagBundle>();
		for (MuTagBundle muTagBundle: muTagBundles)
		{
			if (muTagBundle.containsMuTag(aMuTag)) list.add(muTagBundle);
		}
		return list;
	}
	
	
	public ArrayList<Mu> getMuWithTag(MuTag aMuTag)
	{
		ArrayList<Mu> list = new ArrayList<Mu>();
		for (Mu mu: getAllMus())
		{
			if (mu.hasTag(aMuTag)) list.add(mu);
		}
		return list;
	}



	public boolean isChordTone()
	{
		if (muNotes == null || muNotes.size() == 0)
		{
			return false;
		}
		else
		{
			Chord chord = getPrevailingChord();
			if (chord == null)
				chord = getChordAtGlobalPosition(new BarsAndBeats(0, 0.0));
			return chord.isChordTone(getTopPitch());
		}
	}



	public boolean isExtendedChordTone()
	{
		if (muNotes == null || muNotes.size() == 0)
		{
			return false;
		}
		else
		{
			Chord chord = getPrevailingChord();
			if (chord == null) chord = getChordAtGlobalPosition(new BarsAndBeats(0, 0.0));
			return chord.isExtendedChordTone(getTopPitch());
		}
	}



	public boolean isScaleTone()
	{
		if (muNotes == null || muNotes.size() == 0)
		{
			return false;
		}
		else
		{
			Chord chord = getPrevailingChord();
			if (chord == null) chord = getChordAtGlobalPosition(new BarsAndBeats(0, 0.0));
			return chord.isScaleTone(getTopPitch());
		}
	}
	
	
	
	public boolean isNonScaleTone()
	{
		if (muNotes == null || muNotes.size() == 0)
		{
			return false;
		}
		else
		{
			Chord chord = getPrevailingChord();
			if (chord == null) chord = getChordAtGlobalPosition(new BarsAndBeats(0, 0.0));
			return chord.isNonScaleTone(getTopPitch());
		}
	}



	// this is the rounding for all doubles in Mucus, to 11 decimal points to cater for 
	// 1024th notes as available in musicxml_maker (which to god I hope I never actually use)
	// dropped to 9 on 18 Jan 2021 due to emergung rounding errors greater than 10^-11
	// 
	// final word: the accuracy we need is 5 * 10^-8 which is resolution required for a 1024th note
	public static double round(double value) 
	{
	    return round (value, 8);
	}
	
	
//	public static double round(double value)
//	{
//		
//	}
	
	
	
	public static double round(double value, int amount, int places) 
	{
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(Math.ceil(value * amount) / amount);

	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
	
	public static double round(double value, int places) 
	{
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}



	public void makePreviousNextMusWithNotes()
	{
		List<Mu> list = getMusWithNotesIgnoringTupletHolders();
		if (list.size() > 1)
		{		
			Collections.sort(list, globalPositionInQuartersComparator);
			list.get(0).setNextMu(list.get(1));
			for (int i = 1; i < list.size() - 1; i++)
			{
				Mu mu = list.get(i);
				mu.setPreviousMu(list.get(i - 1));
				mu.setNextMu(list.get(i + 1));
			}
			list.get(list.size() - 1).setPreviousMu(list.get(list.size() - 2));
		}		
	}



	public void setPreviousMu(Mu aMu)
	{
		previousMu = aMu;		
	}



	public void setNextMu(Mu aMu)
	{
		nextMu = aMu;	
	}



	public Mu getNextMu()
	{
		return nextMu;
	}



	public Mu getPreviousMu()
	{
		return previousMu;
	}



	public IntAndString getSemitoneInterval(int aPitch)
	{
		int interval = aPitch - getTopPitch();
		StringBuilder sb = new StringBuilder();
		sb.append("s");
		if (interval < 0) sb.append("-"); else sb.append("+");
		sb.append(Math.abs(interval));
		return new IntAndString(interval, sb.toString());
	}



	public IntAndString getDiatonicInterval(int aPitch)
	{
		if (aPitch == getTopPitch()) return new IntAndString(0, "d+0");
		int inc = 1;
		if (aPitch < getTopPitch()) inc = -1;
		int count = getDiatonicCountToAPitch(aPitch, inc, getTopPitch());
		String intervalString = makeDiatonicIntervalString(inc, count);
		return new IntAndString(count * inc, intervalString);
	}



	private String makeDiatonicIntervalString(int inc, int count)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("d");
		if (inc == 1) sb.append("+"); else sb.append("-");
		sb.append(count);
		return sb.toString();
	}



	// this must be used together with isDiatonicNote(aPitch) because this does not explicitly test if the note is diatonic
	private int getDiatonicCountToAPitch(int aPitch, int inc, int pitch)
	{
		int count = 0;
		while (true)
		{
			pitch += inc;
			if (isDiatonicNote(pitch)) count++;
			if (pitch == aPitch) break;
		}
		return count;
	}



	public boolean isDiatonicNote(int aPitch)
	{
		Chord chord = getPrevailingChord();
		if (chord == null) chord = getChordAtGlobalPosition(new BarsAndBeats(0, 0.0));	// quick nasty way of dealing with pickups before first bar of form
		if (chord.isScaleTone(aPitch) || chord.isChordTone(aPitch)) return true;
		return false;
	}



	public int getStrengthOfPositionInBar()
	// NB - smaller is stronger !!!!!
	{		
		int globalPositionInBars = getGlobalPositionInBars();
		TimeSignature ts = getTimeSignature(globalPositionInBars);
		double globalBarPositionInQuarters = getGlobalPositionInQuarters(globalPositionInBars);
		double globalPositionInQuarters = getGlobalPositionInQuarters();
		double positionInBar = globalPositionInQuarters - globalBarPositionInQuarters;
		return ts.getStrengthOfPositionInQuarters(positionInBar);		
	}
	
	
	
	public int getStrengthOfPositionInBarConsideringSyncopation()
	// NB - smaller is stronger !!!!!
	{		
		ArrayList<MuTagBundle> mtbs = getMuTagBundleContaining(MuTag.IS_SYNCOPATION);
		if (mtbs.size() == 0)
		{
			return getStrengthOfPositionInBar();
		}
		else
		{
			MuTagBundle mtb = mtbs.get(0);	//only first one will be considered
			int globalPositionInBars = getGlobalPositionInBars();
			TimeSignature ts = getTimeSignature(globalPositionInBars);
			double globalBarPositionInQuarters = getGlobalPositionInQuarters(globalPositionInBars);
			double globalPositionInQuarters = (double)mtb.getNamedParameter(MuTagNamedParameter.SYNCOPATED_BEAT_GLOBAL_POSITION);
			double positionInBar = globalPositionInQuarters - globalBarPositionInQuarters;
			return ts.getStrengthOfPositionInQuarters(positionInBar);
		}				
	}
	
	
	
	public int getStrengthOfGlobalPositionInQuarters(double aGlobalPositionInQuarters)
	// NB - smaller is stronger !!!!!
	{
		BarsAndBeats globalPositionInBarsAndBeats = getGlobalPositionInBarsAndBeats(aGlobalPositionInQuarters);
		TimeSignature ts = getTimeSignature(globalPositionInBarsAndBeats.getBarPosition());		
		return ts.getStrengthOfPositionInQuarters(globalPositionInBarsAndBeats.getOffsetInQuarters());		
	}



	public double getEndOfTopLevelMuInQuarters()
	{
		if (parent == null)
		{
			return getGlobalEndPositionInQuarters();
		}
		else
		{
			return parent.getEndOfTopLevelMuInQuarters();
		}
	}



	public Mu getMuWithNoteAtGlobalQuartersPosition(double globalPositionInQuarters)
	{
		Mu chosenMu = null;
		double distance = 1000000;		// arbitrarily large
		for (Mu mu: getMusWithNotesIgnoringTupletHolders())
		{
			double tempDist = Math.abs(globalPositionInQuarters - mu.getGlobalPositionInQuarters());
			if (tempDist < distance)
			{
				chosenMu = mu;
				distance = tempDist;
			}
		}
		return chosenMu;
	}



	public Mu addDeepCopyAtSamePositionToParentMu(Mu aParentMu)
	{
		Mu mu = getDeepCopy();
		positionModel.addAMuToAParentWithSamePositionModelButOnlyBeginningOfParent(mu, aParentMu);		
		return mu;
	}



	public RelativeRhythmicPosition getRelativeRhythmicPosition(Mu aMu)
	{
		BarsAndBeats globalStartPosition = getGlobalPositionInBarsAndBeats();
		BarsAndBeats globalEndPosition = aMu.getGlobalPositionInBarsAndBeats();
		return getRelativeRhythmicPosition(globalStartPosition, globalEndPosition);
	}


	
	public RelativeRhythmicPosition getRelativeRhythmicPosition(
			BarsAndBeats aGlobalStartPosition, 
			BarsAndBeats aGlobalEndPosition
			)
	{
		int barOffset = getRelativeBarPosition(aGlobalStartPosition, aGlobalEndPosition);		
		BarsAndBeats positionForSuperTactusCalculation 
		= getPositionForSuperTactusCalculation(aGlobalStartPosition, barOffset);
		
		int superTactusOffset 
		= getRelativeSuperTactusOffset(positionForSuperTactusCalculation, aGlobalEndPosition);		
		BarsAndBeats positionForTactusCalculation 
		= getPositionForTactusCalulation(aGlobalEndPosition, positionForSuperTactusCalculation, superTactusOffset);
		
		int tactusOffset 
		= getRelativeTactusOffset(positionForTactusCalculation, aGlobalEndPosition);
		BarsAndBeats positionForSubTactusCalculation 
		= getPositionForSubTactusCalulation(aGlobalEndPosition, positionForTactusCalculation, tactusOffset);
		
		int subTactusOffset = getRelativeSubTactusOffset(positionForSubTactusCalculation, aGlobalEndPosition);
		
		return new RelativeRhythmicPosition(barOffset, superTactusOffset, tactusOffset, subTactusOffset);
	}
	
	
	
	public boolean isOnOrSyncopationOfSuperTactus ()
	{
		double globalPositionInQuarters = getGlobalPositionInQuarters();
		if (hasTag(MuTag.IS_SYNCOPATION))
		{
			ArrayList<MuTagBundle> mtb = getMuTagBundleContaining(MuTag.IS_SYNCOPATION);
			globalPositionInQuarters = (double)mtb.get(0).getNamedParameter(MuTagNamedParameter.SYNCOPATED_BEAT_GLOBAL_POSITION);
		}
		int barPosition = getGlobalPositionInBars();
		double globalBarPositionInQuarters = getGlobalPositionInQuarters(barPosition);
		TimeSignature ts = getTimeSignatureFromGlobalBarPosition(barPosition);
		Double[] superTactii = ts.getSuperTactusAsQuartersPositions();
		double barPositionInQuarters = globalPositionInQuarters - globalBarPositionInQuarters;
		for (Double d: superTactii) if (barPositionInQuarters == d) return true;
		if (barPositionInQuarters == ts.getLengthInQuarters()) return true;
		return false;
	}
	
	
	
	private int getRelativeSubTactusOffset(BarsAndBeats aGlobalStartPosition, BarsAndBeats aGlobalEndPosition)
	// assume they are in the same bar
	{
		TimeSignature ts = getTimeSignatureFromGlobalBarPosition(aGlobalEndPosition.getBarPosition());
		double startPos = aGlobalStartPosition.getOffsetInQuarters();
		double endPos = aGlobalEndPosition.getOffsetInQuarters();		
		int count = 0;
		if (aGlobalStartPosition.getBarPosition() > aGlobalEndPosition.getBarPosition()) 
		{
			startPos += ts.getLengthInQuarters();
		}
		if (startPos < endPos)
		{
			for (Double d: ts.getSubTactusAsQuartersPositions())
			{
				if (d > startPos && d <= endPos) count++;
			}
		}
		else
		{
//			if (startPos == 0.0 && endPos > 0.0) startPos = ts.getLengthInQuarters();
			for (Double d: ts.getSubTactusAsQuartersPositions())
			{
				if (d < startPos && d >= endPos) count--;
			}
		}
		return count;
	}



	private BarsAndBeats getPositionForSubTactusCalulation(BarsAndBeats aGlobalEndPosition,
			BarsAndBeats positionForTactusCalculation, int tactusOffset)
	{
		BarsAndBeats positionForSubTactusCalculation = positionForTactusCalculation.getDeepCopy();;
		TimeSignature ts = getTimeSignature(aGlobalEndPosition.getBarPosition());
		
		if (tactusOffset > 0)
		{
			positionForSubTactusCalculation = getPositionForSubTactusCalculationForEndGreaterThanBeginning
					(
							positionForTactusCalculation, 
							tactusOffset, 
							ts
							);
		}
		else if (tactusOffset < 0)
		{
			positionForSubTactusCalculation = getPositionForSubTactusCalculationForEndBeforeBeginning
					(
							positionForTactusCalculation, 
							tactusOffset, 
							ts
							);
		}
		return positionForSubTactusCalculation;
	}



	private BarsAndBeats getPositionForSubTactusCalculationForEndBeforeBeginning(
			BarsAndBeats positionForTactusCalculation, int tactusOffset, TimeSignature ts)
	{
		Double[] tactii = ts.getTactusAsQuartersPositions();
		double position = 0.0;
		int count = 0;
		double positionInBar = positionForTactusCalculation.getOffsetInQuarters();
		int barIndex = positionForTactusCalculation.getBarPosition();
		if (positionInBar == 0.0)
		{
			positionInBar = ts.getLengthInQuarters(); 
			barIndex--;
		}
		for (int i = tactii.length - 1; i > -1; i--)
		{
			if (count == tactusOffset)
			{
				return new BarsAndBeats(barIndex, position);
			}
			else
			{
				if (tactii[i] < positionInBar)
				{
					position = tactii[i];
					count--;
				}
			}
		}
		return positionForTactusCalculation;
	}



	private BarsAndBeats getPositionForSubTactusCalculationForEndGreaterThanBeginning(
			BarsAndBeats positionForTactusCalculation, int tactusOffset, TimeSignature ts)
	{
		Double[] tactii = ts.getTactusAsQuartersPositions();
		double position = 0.0;
		int count = 0;
		for (Double tactus: tactii)
		{
			if (count == tactusOffset)
			{
				return new BarsAndBeats(positionForTactusCalculation.getBarPosition(), position);
			}
			else
			{
				if (tactus > positionForTactusCalculation.getOffsetInQuarters())
				{
					position = tactus;
					count++;
				}
			}
		}
		if (count == tactusOffset)
		{
			return new BarsAndBeats(positionForTactusCalculation.getBarPosition(), position);
		}
		return positionForTactusCalculation;
	}



	private BarsAndBeats getPositionForTactusCalulation(BarsAndBeats aGlobalEndPosition,
			BarsAndBeats positionForSuperTactusCalculation, int superTactusOffset)
	{
		BarsAndBeats positionForTactusCalculation = positionForSuperTactusCalculation.getDeepCopy();;
		TimeSignature ts = getTimeSignatureFromGlobalBarPosition(aGlobalEndPosition.getBarPosition());
		
		if (superTactusOffset > 0)
		{
			positionForTactusCalculation = getPositionForTactusCalculationForEndGreaterThanBeginning
					(
							positionForSuperTactusCalculation, 
							superTactusOffset,  
							ts
							);
		}
		else if (superTactusOffset < 0)
		{
			positionForTactusCalculation = getPositionForTactusCalculationForEndBeforeBeginning(
					positionForSuperTactusCalculation, 
					superTactusOffset,
					ts);
		}
		return positionForTactusCalculation;
	}



	private BarsAndBeats getPositionForTactusCalculationForEndBeforeBeginning(
			BarsAndBeats positionForSuperTactusCalculation, int superTactusOffset,
			TimeSignature ts)
	{
		Double[] superTactii = ts.getSuperTactusAsQuartersPositions();
		double position = 0.0;
		int count = 0;
		double positionInBar = positionForSuperTactusCalculation.getOffsetInQuarters();
		int barIndex = positionForSuperTactusCalculation.getBarPosition();
		if (positionInBar == 0.0)
		{
			positionInBar = ts.getLengthInQuarters(); 
			barIndex--;
		}
		for (int i = superTactii.length - 1; i > -1; i--)
		{
			if (count == superTactusOffset)
			{
				return new BarsAndBeats(barIndex, position);
			}
			else
			{
				if (superTactii[i] < positionInBar)
				{
					position = superTactii[i];
					count--;
				}	
			}
		}
		return positionForSuperTactusCalculation;
	}



	private BarsAndBeats getPositionForTactusCalculationForEndGreaterThanBeginning(
			BarsAndBeats positionForSuperTactusCalculation, int superTactusOffset,
			TimeSignature ts)
	{
		double position = 0.0;
		int count = 0;
		for (Double superTactus: ts.getSuperTactusAsQuartersPositions())
		{
			if (count == superTactusOffset)
			{
				return new BarsAndBeats(positionForSuperTactusCalculation.getBarPosition(), position);
			}
			else
			{
				if (superTactus > positionForSuperTactusCalculation.getOffsetInQuarters())
				{
					position = superTactus;
					count++;
				}	
			}
		}
		if (count == superTactusOffset)
		{
			return new BarsAndBeats(positionForSuperTactusCalculation.getBarPosition(), position);
		}
		return positionForSuperTactusCalculation;
	}



	private int getRelativeTactusOffset(BarsAndBeats aGlobalStartPosition, BarsAndBeats aGlobalEndPosition)
	// assume they are in the same bar
	{
		TimeSignature ts = getTimeSignatureFromGlobalBarPosition(aGlobalEndPosition.getBarPosition());
		double startPos = aGlobalStartPosition.getOffsetInQuarters();
		if (aGlobalStartPosition.getBarPosition() > aGlobalEndPosition.getBarPosition()) 
		{
			startPos += ts.getLengthInQuarters();
		}
		double endPos = aGlobalEndPosition.getOffsetInQuarters();
		int count = 0;
		if (startPos < endPos)
		{
			for (Double d: ts.getTactusAsQuartersPositions())
			{
				if (d > startPos && d <= endPos) count++;
			}
		}
		else
		{
			for (Double d: ts.getTactusAsQuartersPositions())
			{
				if (d < startPos && d >= endPos) count--;
			}
		}
		return count;
	}



	private BarsAndBeats getPositionForSuperTactusCalculation(BarsAndBeats aGlobalStartPosition, int barOffset)
	{
		BarsAndBeats positionForSuperTactusCalculation;
		if (barOffset == 0)
		{
			positionForSuperTactusCalculation = aGlobalStartPosition.getDeepCopy();
		}
		else 
		{
			if (barOffset < 0 && aGlobalStartPosition.getOffsetInQuarters() > 0.0) barOffset++;
			positionForSuperTactusCalculation 
			= new BarsAndBeats(aGlobalStartPosition.getBarPosition() + barOffset, 0.0);
		}
		return positionForSuperTactusCalculation;
	}



	private int getRelativeSuperTactusOffset(BarsAndBeats aGlobalStartPosition, BarsAndBeats aGlobalEndPosition)
	{
		TimeSignature ts = getTimeSignatureFromGlobalBarPosition(aGlobalEndPosition.getBarPosition());

		if (aGlobalStartPosition.getBarPosition() == aGlobalEndPosition.getBarPosition())
		{
			return getRelativeSuperTactusCountInTheSameBar(aGlobalStartPosition, aGlobalEndPosition, ts);
		}
		else if (aGlobalStartPosition.getBarPosition() < aGlobalEndPosition.getBarPosition())
		{
			return countSuperTactusFromBeginningOfOtherBar(aGlobalEndPosition, ts);
		}
		else if (aGlobalStartPosition.getBarPosition() > aGlobalEndPosition.getBarPosition())
		{
			return countSuperTactusFromEndOfOtherBar(aGlobalEndPosition, ts);
		}
		return 0;
	}



	private int countSuperTactusFromEndOfOtherBar(
			BarsAndBeats otherGlobalPositionInBarsAndBeats, 
			TimeSignature ts
			)
	{
		Double[] superTactii = ts.getSuperTactusAsQuartersPositions();
		int count = 0;
		for (int i = superTactii.length - 1; i >= 0; i--)
		{
			if (otherGlobalPositionInBarsAndBeats.getOffsetInQuarters() > superTactii[i])
			{
				break;
			}
			else
			{
				count--;
			}
		}
		return count;
	}



	private int countSuperTactusFromBeginningOfOtherBar(
			BarsAndBeats otherGlobalPositionInBarsAndBeats,
			TimeSignature ts
			)
	{
		int count = -1;
		for (Double d: ts.getSuperTactusAsQuartersPositions())
		{
			if (otherGlobalPositionInBarsAndBeats.getOffsetInQuarters() >= d) count++; else break;
		}
		return count;
	}



	private int getRelativeSuperTactusCountInTheSameBar(
			BarsAndBeats thisGlobalPositionInBarsAndBeats,
			BarsAndBeats otherGlobalPositionInBarsAndBeats, 
			TimeSignature ts
			)
	{
		int count = 0;
		int increment = 1;
		double hi;
		double low = thisGlobalPositionInBarsAndBeats.getOffsetInQuarters();
		if (otherGlobalPositionInBarsAndBeats.getOffsetInQuarters() < low)
		{
			low = otherGlobalPositionInBarsAndBeats.getOffsetInQuarters();
			hi = thisGlobalPositionInBarsAndBeats.getOffsetInQuarters();
			increment = -1;
		}
		else
		{
			hi = otherGlobalPositionInBarsAndBeats.getOffsetInQuarters();
		}
		for (Double d: ts.getSuperTactusAsQuartersPositions())
		{
			if (d > low && d <= hi) count += increment;
		}
		return count;
	}



	private int getRelativeBarPosition(BarsAndBeats aGlobalStartPosition, BarsAndBeats aGlobalEndPosition)
	{
		if (aGlobalStartPosition.getBarPosition() == aGlobalEndPosition.getBarPosition()) return 0;
		if (aGlobalStartPosition.getBarPosition() < aGlobalEndPosition.getBarPosition())
		{
			return relativeBarPositionForOtherAfterThis(aGlobalStartPosition, aGlobalEndPosition);
		}
		else
		{
			return relativeBarPositionForOtherBeforeThis(aGlobalStartPosition, aGlobalEndPosition);
		}
	}



	private int relativeBarPositionForOtherBeforeThis(
			BarsAndBeats aGlobalStartPosition, 
			BarsAndBeats aGlobalEndPosition
			)
	{
		int pos = aGlobalEndPosition.getBarPosition() - aGlobalStartPosition.getBarPosition();
		if (aGlobalStartPosition.getOffsetInQuarters() == 0.0) pos++;
		if (aGlobalEndPosition.getOffsetInQuarters() == 0.0) pos--;
		return pos;
	}



	private int relativeBarPositionForOtherAfterThis(
			BarsAndBeats aGlobalStartPosition, 
			BarsAndBeats aGlobalEndPosition
			)
	{
		int pos = aGlobalEndPosition.getBarPosition() - aGlobalStartPosition.getBarPosition();
		return pos;
	}


	// new mu added to this mu, position calculated from aStartPositionMu, and converted to local position based on this Mu
	public Mu addMuAtBarsAndBeatsPosition(Mu aStartPositionMu, RelativeRhythmicPosition rrp)
	{
		Mu mu = new Mu("rrp_note");
		BarsAndBeats startPosition = aStartPositionMu.getGlobalPositionInBarsAndBeats();		
		BarsAndBeats globalPositionAfterBarOffset = getGlobalPositionAfterBarOffset(rrp, startPosition);
		BarsAndBeats globalPositionAfterSuperTactusOffset = getGlobalPositionAfterSuperTactusOffset(rrp, globalPositionAfterBarOffset);		
		BarsAndBeats globalPositionAfterTactusOffset = getGlobalPositionAfterTactusOffset(rrp, globalPositionAfterSuperTactusOffset) ;
		
		
		BarsAndBeats globalPositionAfterSubTactusOffset = getGlobalPositionAfterSubTactusOffset(rrp, globalPositionAfterTactusOffset);
		
		BarsAndBeats localPosition = getLocalPositionInBarsAndBeats(globalPositionAfterSubTactusOffset);
		addMu(mu, localPosition);
		return mu;
	}
	
	
	
	public Mu addMuAtQuartersPosition(Mu aStartPositionMu, RelativeRhythmicPosition rrp)
	{
		Mu mu = new Mu("rrp_note");
		BarsAndBeats startPosition = aStartPositionMu.getGlobalPositionInBarsAndBeats();		
		BarsAndBeats globalPositionAfterBarOffset = getGlobalPositionAfterBarOffset(rrp, startPosition);
		BarsAndBeats globalPositionAfterSuperTactusOffset = getGlobalPositionAfterSuperTactusOffset(rrp, globalPositionAfterBarOffset);		
		BarsAndBeats globalPositionAfterTactusOffset = getGlobalPositionAfterTactusOffset(rrp, globalPositionAfterSuperTactusOffset) ;
		
		
		BarsAndBeats globalPositionAfterSubTactusOffset = getGlobalPositionAfterSubTactusOffset(rrp, globalPositionAfterTactusOffset);
		
//		double localPosition = getLocalPositionInQuarters(globalPositionAfterSubTactusOffset);
		double startPositionInQuarters = aStartPositionMu.getGlobalPositionInQuarters();
		double endPositionInQuarters = getGlobalPositionInQuarters(globalPositionAfterSubTactusOffset);
		
		addMu(mu, endPositionInQuarters - startPositionInQuarters);
		return mu;
	}



	private BarsAndBeats getGlobalPositionAfterSubTactusOffset(
			RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition
			)
	{
		if (rrp.getSubTactusOffset() == 0)
		{
			return startPosition;
		}
		else
		{
			if (rrp.getSubTactusOffset() > 0)
			{
				return getFinalPositionForPositiveSubTactusOffset(rrp, startPosition);
			}
			else
			{
				return getFinalPositionForNegativeSubTactusOffset(rrp, startPosition);
			}
		}
	}



	private BarsAndBeats getFinalPositionForNegativeSubTactusOffset(RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition)
	{
		int barIndex = startPosition.getBarPosition();
		TimeSignature ts = getTimeSignature(barIndex);
		double positionInBar = startPosition.getOffsetInQuarters();
		if (positionInBar == 0.0)
		{
			barIndex--;
			ts = getTimeSignature(barIndex);
			positionInBar = ts.getLengthInQuarters();
		}
		int count = 0;
		while (count > rrp.getSubTactusOffset())
		{
			Double[] subTactii = ts.getSubTactusAsQuartersPositions();
			for (int i = subTactii.length - 1; i > -1; i--)
			{
				if (count == rrp.getSubTactusOffset()) break;
				if (subTactii[i] < positionInBar)
				{
					positionInBar = subTactii[i];
					count--;
				}	
			}
			if (count > rrp.getSubTactusOffset())
			{
				barIndex--;
				ts = getTimeSignature(barIndex);
				positionInBar = ts.getLengthInQuarters();
			}
		}	
		return new BarsAndBeats(barIndex, positionInBar);
	}
	
	
	
	private BarsAndBeats getFinalPositionForPositiveSubTactusOffset(RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition)
	{
		int barIndex = startPosition.getBarPosition();				
		double positionInBar = startPosition.getOffsetInQuarters();
		int count = 0;
		while (count < rrp.getSubTactusOffset())
		{
			TimeSignature ts = getTimeSignature(barIndex);
			for (Double subTactus: ts.getSubTactusAsQuartersPositions())
			{
				if (count == rrp.getSubTactusOffset()) break;
				if (subTactus > positionInBar)
				{
					positionInBar = subTactus;
					count++;
				}	
			}
			if (count < rrp.getSubTactusOffset())
			{
				barIndex++;
				count++;
				positionInBar = 0.0;
			}
		}	
		return new BarsAndBeats(barIndex, positionInBar);
	}
	


	private BarsAndBeats getGlobalPositionAfterTactusOffset(
			RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition
			)
	{
		if (rrp.getTactusOffset() == 0)
		{
			return startPosition;
		}
		else
		{
			if (rrp.getTactusOffset() > 0)
			{
				return getPositionForSubTactusCountForPositiveTactusOffset(rrp, startPosition);
			}
			else
			{
				return getPositionForSubTactusCountForNegativeTactusOffset(rrp, startPosition);
			}
		}
	}



	private BarsAndBeats getPositionForSubTactusCountForNegativeTactusOffset(RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition)
	{
		int barIndex = startPosition.getBarPosition();
		TimeSignature ts = getTimeSignature(barIndex);
		double positionInBar = startPosition.getOffsetInQuarters();
		if (positionInBar == 0.0)
		{
			barIndex--;
			ts = getTimeSignature(barIndex);
			positionInBar = ts.getLengthInQuarters();
		}
		int count = 0;
		while (count > rrp.getTactusOffset())
		{
			Double[] tactii = ts.getTactusAsQuartersPositions();
			for (int i = tactii.length - 1; i > -1; i--)
			{
				if (count == rrp.getTactusOffset()) break;
				if (tactii[i] < positionInBar)
				{
					positionInBar = tactii[i];
					count--;
				}	
			}
			if (count > rrp.getTactusOffset())
			{
				barIndex--;
				ts = getTimeSignature(barIndex);
				positionInBar = ts.getLengthInQuarters();
			}
		}	
		return new BarsAndBeats(barIndex, positionInBar);
	}
	
	
	
	private BarsAndBeats getPositionForSubTactusCountForPositiveTactusOffset(RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition)
	{
		int barIndex = startPosition.getBarPosition();				
		double positionInBar = startPosition.getOffsetInQuarters();
		int count = 0;
		while (count < rrp.getTactusOffset())
		{
			TimeSignature ts = getTimeSignature(barIndex);
			for (Double tactus: ts.getTactusAsQuartersPositions())
			{
				if (count == rrp.getTactusOffset()) break;
				if (tactus > positionInBar)
				{
					positionInBar = tactus;
					count++;
				}	
			}
			if (count < rrp.getTactusOffset())
			{
				barIndex++;
				count++;
				positionInBar = 0.0;
			}
		}	
		return new BarsAndBeats(barIndex, positionInBar);
	}



	private BarsAndBeats getGlobalPositionAfterSuperTactusOffset(
			RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition
			)
	{
		if (rrp.getSuperTactusOffset() == 0)
		{
			return startPosition;
		}
		else
		{
			if (rrp.getSuperTactusOffset() > 0)
			{
				return getPositionForTactusCountForPositiveSuperTactusOffset(rrp, startPosition);
			}
			else
			{
				return getPositionForTactusCountForNegativeSuperTactusOffset(rrp, startPosition);
			}
		}
	}



	private BarsAndBeats getPositionForTactusCountForNegativeSuperTactusOffset(RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition)
	{
		int barIndex = startPosition.getBarPosition();
		TimeSignature ts = getTimeSignature(barIndex);
		double positionInBar = startPosition.getOffsetInQuarters();
		if (positionInBar == 0.0)
		{
			barIndex--;
			ts = getTimeSignature(barIndex);
			positionInBar = ts.getLengthInQuarters();
		}
		int count = 0;
		while (count > rrp.getSuperTactusOffset())
		{
			Double[] superTactii = ts.getSuperTactusAsQuartersPositions();
			for (int i = superTactii.length - 1; i > -1; i--)
			{
				if (count == rrp.getSuperTactusOffset()) break;
				if (superTactii[i] < positionInBar)
				{
					positionInBar = superTactii[i];
					count--;
				}	
			}
			if (count > rrp.getSuperTactusOffset())
			{
				barIndex--;
				ts = getTimeSignature(barIndex);
				positionInBar = ts.getLengthInQuarters();
			}
		}	
		return new BarsAndBeats(barIndex, positionInBar);
	}



	private BarsAndBeats getPositionForTactusCountForPositiveSuperTactusOffset(RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition)
	{
		int barIndex = startPosition.getBarPosition();				
		double positionInBar = startPosition.getOffsetInQuarters();
		int count = 0;
		while (count < rrp.getSuperTactusOffset())
		{
			TimeSignature ts = getTimeSignature(barIndex);
			for (Double superTatcus: ts.getSuperTactusAsQuartersPositions())
			{
				if (count == rrp.getSuperTactusOffset()) break;
				if (superTatcus > positionInBar)
				{
					positionInBar = superTatcus;
					count++;
				}	
			}
			if (count < rrp.getSuperTactusOffset())
			{
				barIndex++;
				count++;
				positionInBar = 0.0;
			}
		}	
		return new BarsAndBeats(barIndex, positionInBar);
	}



	private BarsAndBeats getGlobalPositionAfterBarOffset(RelativeRhythmicPosition rrp,
			BarsAndBeats startPosition)
	{
		if (rrp.getBarOffset() == 0)
		{
			return startPosition;
		}
		else
		{
			int barPosition = startPosition.getBarPosition();
			if (rrp.getBarOffset() < 0 && startPosition.getOffsetInQuarters() > 0.0)
			{
				barPosition++;
			}
			barPosition += rrp.getBarOffset();
			return new BarsAndBeats(barPosition, 0.0);
		}
	}



	public void sortMus(Comparator<Mu> aComparator)
	{
		mus.sort(aComparator);
		
	}
	
	
	
	public void sortMus()
	{
		mus.sort(globalPositionInQuartersComparator);
	}



	public String getCommaSeparatedPitchString()
	{
		StringBuilder sb = new StringBuilder();
		for (Mu mu: getMusWithNotes())
		{
			for (MuNote munote: mu.getMuNotes())
			{
				sb.append(munote.getPitch() + ",");
			}
		}
		return sb.toString();
	}



	public void setStartTempo(double aTempo)
	{
		checkForNullRulerAndCreate();
		ruler.setStartTempo(aTempo);		
	}
	
	
	
	public void addTempoChange(double aTempo, double aPositionInFloatBars)
	{
		checkForNullRulerAndCreate();
		ruler.addTempoChange(aTempo, aPositionInFloatBars);
	}



	public double getStartTempo()
	{
		return ruler.getTempoAtQuartersPosition(0.0);
	}



	public String selectionListEntry()
	{
		String parentName = "null";
		if (parent != null) parentName = parent.getName();
		return "Mu:" + name + " parent=" + parentName;
	}
	
	
	public int getLevel()
	{
		if (parent == null) return 0;
		return parent.getLevel() + 1;
	}



	public MuTagBundle[] getMuTagBundles()
	{
		return muTagBundles.toArray(new MuTagBundle[muTagBundles.size()]);
	}



	public void setMuIndices()
	{
		List<Mu> list = this.getAllMus();
		int index = 0;
		for (Mu mu: list)
		{
			mu.setMuIndex(index);
			index++;
		}
	}

	
	
	public int getMuIndex()
	{
		return muIndex;
	}



	public void setMuIndex(int muIndex)
	{
		this.muIndex = muIndex;
	}



	public enum PrintParameter {CHORD_LIST, CHORD_LIST_GENERATOR,
		HAS_CHORD_LIST_GENERATOR, HAS_LEADING_DOUBLE_BAR, HAS_TIME_SIGNATURE_GENERATOR,
		IS_TUPLET_PRINT_CONTAINER, KEY_SIGNTURE_MAP, LENGTH_MODEL,
		MU_ANNOTATIONS, MU_GENERATOR, MU_NOTES, MU_TAG_BUNDLES,
		NAME, PARENT, POSITION_MODEL, RULER, START_PITCH, TUPLET_NUMERATOR,
		TUPLET_DENOMINATOR, MUS
	}

	
	
	public String getParameterToStringFromList(PrintParameter[] aPrintParameterArray)
	{
		StringBuilder sb = new StringBuilder();
		for (PrintParameter pp: aPrintParameterArray)
		{
			switch (pp)
			{
			case CHORD_LIST:
				addPrintParameterChordList(sb, pp);
				break;
			case CHORD_LIST_GENERATOR:
				sb.append("\n" + pp + "\n" + chordListGenerator.toString());
				break;
			case HAS_CHORD_LIST_GENERATOR:
				sb.append("\n" + pp + ": " + hasChordListGenerator);
				break;
			case HAS_LEADING_DOUBLE_BAR:
				sb.append("\n" + pp + ": " + hasLeadingDoubleBar);
				break;
			case HAS_TIME_SIGNATURE_GENERATOR:
				sb.append("\n" + pp + ": " + hasTimeSignatureGenerator);
				break;
			case IS_TUPLET_PRINT_CONTAINER:
				sb.append("\n" + pp + ": " + isTupletPrintContainer);
				break;
			case KEY_SIGNTURE_MAP:
				sb.append("\n" + pp + ":" + ksm.toString());
				break;
			case LENGTH_MODEL:
				sb.append("\n" + pp + "\n" + lengthModel.getContentString());
				break;
			case MUS:
				addPrintParameterMus(aPrintParameterArray, sb, pp);
				break;
			case MU_ANNOTATIONS:
				addPrintParameterMuAnnotations(sb, pp);
				break;
			case MU_GENERATOR:
				addPrintParameterMuGenerator(sb, pp);
				break;
			case MU_NOTES:
				addPrintParameterMuNotes(sb, pp);
				break;
			case MU_TAG_BUNDLES:
				addPrintParameterMuTagBundles(sb, pp);
				break;
			case NAME:
				sb.append("\n" + pp + ": " + name);
				break;
			case PARENT:
				addPrintParameterParent(sb, pp);
				break;
			case POSITION_MODEL:
				sb.append("\n" + pp + "\n" + positionModel.getContentString());
				break;
			case RULER:
				addPrintParameterRuler(sb, pp);
				break;
			case START_PITCH:
				sb.append("\n" + pp + ": " + startPitch);
				break;
			case TUPLET_DENOMINATOR:
				sb.append("\n" + pp + ": " + tupletNumerator);
				break;
			case TUPLET_NUMERATOR:
				sb.append("\n" + pp + ": " + tupletDenominator);
				break;
			default:
				break;
			
			}
		}
		return sb.toString();
	}
	
	
	
	private void addPrintParameterChordList(StringBuilder sb, PrintParameter pp)
	{
		sb.append("\n" + pp);
		if (chordList == null)
		{
			sb.append("=null");
		}
		else
		{
			sb.append("=" + chordList.toString());
		}
	}



	private void addPrintParameterRuler(StringBuilder sb, PrintParameter pp)
	{
		sb.append("\n" + pp);
		if (ruler == null)
		{
			sb.append("=null");
		}
		else
		{
			sb.append("=" + ruler.toString());
		}
	}



	private void addPrintParameterParent(StringBuilder sb, PrintParameter pp)
	{
		sb.append("\n" + pp);
		if (parent == null)
		{
			sb.append("=null");
		}
		else
		{
			sb.append("=" + parent.getName());
		}
	}



	private void addPrintParameterMuTagBundles(StringBuilder sb, PrintParameter pp)
	{
		sb.append("\n" + pp);
		if (muTagBundles == null)
		{
			sb.append("=null");
		}
		else
		{
			for (MuTagBundle mtb: muTagBundles)
			{
				sb.append("\n" + mtb.toString());
			}
		}
	}



	private void addPrintParameterMuNotes(StringBuilder sb, PrintParameter pp)
	{
		sb.append("\n" + pp);
		if (muNotes == null)
		{
			sb.append("=null");
		}
		else
		{
			for (MuNote mn: muNotes)
			{
				sb.append("\n" + mn.toString());
			}
		}
	}



	private void addPrintParameterMuGenerator(StringBuilder sb, PrintParameter pp)
	{
		sb.append("\n" + pp);
		if (muGenerator == null)
		{
			sb.append("=null");
		}
		else
		{
			sb.append("\n" + muGenerator.toString());
		}
	}



	private void addPrintParameterMuAnnotations(StringBuilder sb, PrintParameter pp)
	{
		sb.append("\n" + pp);
		if (muAnnotations == null)
		{
			sb.append("=null");
		}
		else
		{
			for (MuAnnotation muan: muAnnotations)
			{
				sb.append("\n" + muan.parametersToString());
			}
		}
	}



	private void addPrintParameterMus(PrintParameter[] aPrintParameterArray, StringBuilder sb, PrintParameter pp)
	{
		sb.append("\n" + pp);
		if (mus == null)
		{
			sb.append("=null");
		}
		else
		{
			for (Mu mu: mus)
			{
				sb.append("\n" + mu.getParameterToStringFromList(aPrintParameterArray));
			}
		}
	}



	public ChordListGenerator getChordListGenerator()
	{
		return chordListGenerator;
	}



	public LengthModel getLengthModel()
	{
		return lengthModel;
	}



	public ArrayList<Integer> getListOfTopPitches ()
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (Mu mu: getMusWithNotes())
		{
			list.add(mu.getTopPitch());
		}
		return list;
	}



	public ArrayList<IntAndDouble> getListOfTopPitchesAndGlobalPositionsInQuarters ()
	{
		ArrayList<IntAndDouble> list = new ArrayList<IntAndDouble>();
		for (Mu mu: getMusWithNotes())
		{
			list.add(new IntAndDouble(mu.getTopPitch(), mu.getGlobalPositionInQuarters()));
		}
		return list;
	}



	public Mu ancestorSeach(MuEvaluator eval)
	{
		if (eval.evaluate(this))
		{
			return this;
		}
		else
		{
			if (parent == null)
			{
				return null;
			}
			else
			{
				return parent.ancestorSeach(eval);
			}
		}
	}



	public int getBeatStrength()
	{
		double positionInBar = getGlobalPositionInQuarters() - getGlobalPositionInQuarters(getGlobalPositionInBars());
		return getTimeSignature(getGlobalPositionInBars()).getStrengthOfPositionInQuarters(positionInBar);
	}
	
	
	
	public int getBeatStrengthConsideringSyncopation ()
	{
		List<MuTagBundle> mtbList = getMuTagBundleContaining(MuTag.IS_SYNCOPATION);
		double globalPosition = 0.0;
		if (mtbList.size() > 0)
		{
			globalPosition = (double)mtbList.get(0).getNamedParameter(MuTagNamedParameter.SYNCOPATED_BEAT_GLOBAL_POSITION);	
			int barIndex = getGlobalPositionInBarsAndBeats(globalPosition).getBarPosition();
			double barPosition = getGlobalPositionInQuarters(barIndex);
			double positionInBar = globalPosition - barPosition;
			return getTimeSignature(barIndex).getStrengthOfPositionInQuarters(positionInBar);
		}
		return getBeatStrength();
	}



	public void setVelocity (int aVelocity)
	{
		for (MuNote mn: muNotes)
		{
			mn.setVelocity(aVelocity);
		}	
	}



	public int getHighestMuNoteVelocity ()
	{
		if (muNotes == null || muNotes.size() == 0)
		{
			return 0;
		}
		else
		{
			int velocity = 0;
			for (MuNote mn: muNotes)
			{
				if (mn.getVelocity() > velocity)
				{
					velocity = mn.getVelocity();
				}
			}
			return velocity;
		}
	}


	// this will change when a Mu can acquire many controller values.....
	public double getControllerValue() 
	{
		return controllerValue;
	}



	// this will change when a Mu can acquire many controller values.....
	public void setControllerValue(double controllerValue) 
	{
		this.controllerValue = controllerValue;
		this.hasControllerValue = true;
	}
	
	
	
	public boolean hasControllerValue()
	{
		return hasControllerValue;
	}




	
	
	



	
	
}
