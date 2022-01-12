/*CAN BE EDITED - Umple no longer generates this class*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package main.java.com.dougron.mucus.mu_framework.data_types;

import main.java.com.dougron.mucus.mu_framework.Mu;

//import mu_tdd.Mu;

// line 301 "../mu.ump"
public class MuNote
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

	
  public static final int DEFAULT_STRUCTURE_TONE_VELOCITY = 64;
//MuNote Attributes
  private int pitch;
  private int velocity;

  
  //MuNote Associations
  private Mu mu;

  
  //------------------------
  // CONSTRUCTOR
  //------------------------

  
  
  public MuNote(int aPitch, int aVelocity)
  {
	pitch = aPitch;
    velocity = aVelocity;
  }

  
  
  //------------------------
  // INTERFACE
  //------------------------

  
  public boolean setNote(int aPitch)
  {
    boolean wasSet = false;
    pitch = aPitch;
    wasSet = true;
    return wasSet;
  }

  
  
  public boolean setVelocity(int aVelocity)
  {
    boolean wasSet = false;
    velocity = aVelocity;
    wasSet = true;
    return wasSet;
  }

  
  
  public int getPitch()
  {
    return pitch;
  }

  
  
  public int getVelocity()
  {
    return velocity;
  }
  
  
  
  /* Code from template association_GetOne */
  public Mu getMu()
  {
    return mu;
  }

  
  
  public boolean hasMu()
  {
    boolean has = mu != null;
    return has;
  }
  
  
  
  /* Code from template association_SetOptionalOneToMany */
//  public boolean setMu(Mu aMu)
//  {
//    boolean wasSet = false;
//    Mu existingMu = mu;
//    mu = aMu;
//    if (existingMu != null && !existingMu.equals(aMu))
//    {
//      existingMu.removeMuNote(this);
//    }
//    if (aMu != null)
//    {
//      aMu.addMuNote(this);
//    }
//    wasSet = true;
//    return wasSet;
//  }

  
  
  public void delete()
  {
//    if (mu != null)
//    {
//      Mu placeholderMu = mu;
//      this.mu = null;
//      placeholderMu.removeMuNote(this);
//    }
  }

  
  
  // line 309 "../mu.ump"
   public String toString(){
    return "MuNote: pitch=" + getPitch() + " velocity=" + getVelocity();
  }


   
  /**
   * cannot be changed without checking Junit test
   */
  // line 318 "../mu.ump"
   public String testOutputToString(){
    return "MuNote: pitch=" + getPitch() + " velocity=" + getVelocity();
  }



   public MuNote getDeepCopy()
   {
	   return new MuNote(pitch, velocity);
   }

}