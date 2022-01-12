/**
 * ChordListGenerator is the interface for a class that generates a ChordList
 * 
 *  A ChordList contains a list of ChordEvents which position Chords by a BarsAndBeats position,
 *  for use by a Mu.
 *  
 *  classes implementing ChordListGenerator:
 *  
 *  	SingleChordGenerator - generates a one chord ChordList
 *  	SimpleEvenChordProgression - generates a chord progression that cycles with one chord per bar
 *  	FloatBarChordProgression - generates a ChordList from a list of Chords positioned with floatBars (bar position as a double)
 *  
 */


package main.java.com.dougron.mucus.mu_framework.chord_list;