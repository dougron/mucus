package main.java.com.dougron.mucus.algorithms.generic_generator;

import java.util.Random;

public interface Generator
{

	public Output process(Output aOutput, ParameterObject aParameterObject, Random aRnd);
}
