/*
 * important stuff that could be confusing:
 * 
 * Parameter is an enum from random_melody_generator.RMRandomNumberContainer.Parameter
 * 		- this identifies the overall parameter
 * 
 * ParameterIndex - is an int and identifies the index of Parameter reffered to
 * 		- e.g. Parameter.MUG_LIST_COUNT parameterIndex 0 - this would the count parameter for the 
 * 				collection of MuGs related to an item indexed as 0 in the embellishment repetition pattern
 * 
 * ListIndex - in the case of the ValueList or the IndexedValueList, this refers to an item in the ArrayList<Double> 
 * accessed by a Parameter (in the former case) or a Parameter and ParameterIndex (in the latter case)
 * 
 * I have no idea how I got to make this so complicated. facepalm
 */

package main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers;