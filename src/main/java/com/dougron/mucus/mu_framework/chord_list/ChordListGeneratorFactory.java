package main.java.com.dougron.mucus.mu_framework.chord_list;

public class ChordListGeneratorFactory
{

	public static ChordListGenerator getGenerator(Object[] aParameterObjectArray)
	{
		switch((String)aParameterObjectArray[0])
		{
		case "SingleChordGenerator":
			return new SingleChordGenerator(new Chord((String)aParameterObjectArray[1]));
		
		case "SimpleEvenChordProgression":
			String[] arr = new String[aParameterObjectArray.length - 1];
			for (int i = 0; i < arr.length; i++)
			{
				arr[i] = (String)aParameterObjectArray[i + 1];
			}
			return new SimpleEvenChordProgression(arr);
		
		case "FloatBarChordProgression":
			Object[] orr = new Object[aParameterObjectArray.length - 2];
			for (int i = 0; i < orr.length; i++)
			{
				orr[i] = aParameterObjectArray[i + 2];
			}
			return new FloatBarChordProgression((double)aParameterObjectArray[1], orr);
		}
		return null;
	}
	
	
	public static ChordListGenerator getCopyOfGenerator(ChordListGenerator aChordListGenerator)
	{
		switch (aChordListGenerator.getClass().getSimpleName())
		{
		case "SingleChordGenerator":
			SingleChordGenerator singleChordGenerator = (SingleChordGenerator)aChordListGenerator;
			return new SingleChordGenerator(singleChordGenerator.chord);
		case "SimpleEvenChordProgression":
			SimpleEvenChordProgression simpleEvenChordGenerator = (SimpleEvenChordProgression)aChordListGenerator;
			return new SimpleEvenChordProgression(simpleEvenChordGenerator.argumentString);
		case "FloatBarChordProgression":
			FloatBarChordProgression floatBarChordProgression = (FloatBarChordProgression)aChordListGenerator;
			return new FloatBarChordProgression(
					floatBarChordProgression.lengthInFloatBars,
					floatBarChordProgression.positionInFloatBarsCombosAndChordName
					);
		default:
			return new SingleChordGenerator();
		}
	}

}
