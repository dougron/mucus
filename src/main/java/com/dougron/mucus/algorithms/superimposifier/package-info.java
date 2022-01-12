/**
 * a package that handles algorithmic processes that can be superimposed over other processes on a timeline.
 * 
 * This is the current framework for the generative engine for the Mucus project. The ability to superimpose 
 * generative processes allows for the user to define an area in the original generated deemed good or bad and the area 
 * that is considered ripe for improvement can be overlayed by a new process.
 * 
 * Any generative process implements the SuFi interface SUperimosiFIer (ffs)
 * The SuFiSu (SuFiSUperimposer, again ffs) arranges Sufis on a timeline.
 * 
 * These classes work within a Mu hierarchy. The idea is that they can be instantiated with parameters, but no
 * parameters are passed when a render is triggered. Any SuFi will access the Mu hierarchy for information that can
 * inform its generative process, and by the same token, should no information be available, it will have recourse 
 * to making its own decisions about things. This allows for the Mu hierarchy to not require a top down completeness of information.
 *  
 *  
 *  as of 24 October 2020, unfolding experiments have suggested the following changes:
 *  
 *  	1)	SuFiSu becomes SuFiSu_LeftToRight - 
 *  	2)	SuFiSu becomes an interface.
 *  
 *  New classes implementing SuFiSu will be developed that may not be compatible with existing 
 *  SuFis, until such time as a clearer and more open ended algorithm crafting mechanic has been
 *  developed. 
 *  
 *   8 Jan 2022:
 *   As of the introduction of the MuGenerator system (beginning of 2021), Superimposifier is no longer used. May take a 
 *   while to get rid of the last traces of it.
 */



package main.java.com.dougron.mucus.algorithms.superimposifier;