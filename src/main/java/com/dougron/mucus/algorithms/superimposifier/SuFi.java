package main.java.com.dougron.mucus.algorithms.superimposifier;

/**
 * SuFi is an interface for items in the SuFiSu manager class.
 * Naming convention will be SuFi_<identifying name>_<Generator/Processor>
 * 
 * Processor and generator refer to similar activities in the old Pipeline based framework
 * 
 * have noticed on 2020_12_08 that the above naming convention has not been sustained in the overwritable_vectors package.
 */



public interface SuFi
{
	
	public static enum AccentType{ACCENTED, UNACCENTED};


	// ABSTRACT METHODS 

	public boolean isWithinScope(double pos);
	public SuFiNote getNextNote();
	public SuFiNote getFirstNote();
	public boolean setParent(SuFiSu sufisu);
	public void reset();
	public void generate();
	public SuFi getDeepCopy();
	public void setAccentType(AccentType aAccentType);
}