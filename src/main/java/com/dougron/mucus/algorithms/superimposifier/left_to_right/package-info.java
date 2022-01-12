/*
 * implements a timeline specific algorithm manager which generates output by processing
 * the timeline from beginning to end (left to right on the page, so to speak) 
 * 
 * algorithms are dependant on knowing the last note generated
 * 
 * as a general observation, this approach is a bit simplistic for a grammar based
 * approach to generation like chord-tone-and-embellishment, and was superseded by 
 * the overwritable_vectors package
 * 
 * 
 */
package main.java.com.dougron.mucus.algorithms.superimposifier.left_to_right;


