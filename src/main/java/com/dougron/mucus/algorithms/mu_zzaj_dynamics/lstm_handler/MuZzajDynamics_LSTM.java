package main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics.lstm_handler;

/*
 * java class that delivers a prediction about whether a note is
 * accented or not using a nn trained on the zzaj dataset and
 * taking the eight nearest notes position and melody contour, and the
 * beatstrength and syncopation status of the note in question
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics.MuZzajDynamics;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTagNamedParameter;

public class MuZzajDynamics_LSTM implements MuZzajDynamics
{

	
	private static final double DYNAMIC_ACCENT_THRESHOLD = 0.5;
	private static MuZzajDynamics_LSTM instance = null;
//	private static String modelPath = "D:/Documents/algorithm data for googledrive/CorpusCapture2020/zzaj corpus/models/closest8/model_8closest_error23.h5";
//	private static String modelPath = "model.h5";
	MultiLayerNetwork model;
//	int inputShape = 18;
//	int numberOfNeighbours = 8;
	int sequenceLength = 10;
	int featureCount = 5;
	public static String modelPath = "mu_zzaj_dynamics_models/";
	
	
	public MuZzajDynamics_LSTM(String modelFileName) throws IOException
	{
		try
		{
			String simpleMlp = new ClassPathResource(modelPath + modelFileName).getFile().getPath();
			model = KerasModelImport.importKerasSequentialModelAndWeights(simpleMlp);
		} 
		catch (InvalidKerasConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (UnsupportedKerasConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/*
	 * for this implementation, aSortedMuList is assumed to be sorted in absolute distance from
	 * the Mu in question, so the item at index 0 is the note to be decided upon and the subsequent 
	 * 8 items are the notes added to the neural net input
	 */
	@Override
	public double[] getPrediction (List<Mu> aSortedMuList)
	{

		double[][] arr; 
		double basePosition;
		int rollingPitch;
		List<Double> completePrediction = new ArrayList<Double>();
		
		System.out.println("aSortedMuList.size()=" + aSortedMuList.size());
		for (Mu mu: aSortedMuList)
		{
			System.out.println(mu.getGlobalPositionInQuarters());
		}
		for (int i = 0; i < aSortedMuList.size(); i += sequenceLength)
		{
			int endIndex = i + sequenceLength;
			if (endIndex > aSortedMuList.size()) endIndex = aSortedMuList.size();
			List<Mu> subList = aSortedMuList.subList(i, endIndex);
			System.out.println("subList.size()=" + subList.size() + " endIndex=" + endIndex);
			arr = new double[sequenceLength][featureCount];
			basePosition = subList.get(0).getGlobalPositionInQuarters();
			rollingPitch = subList.get(0).getTopPitch();
			
			for (int j = 0; j < subList.size(); j++)
			{
				System.out.println("j=" + j);
				arr[j] = addNextReadingToSequence(subList.get(j), basePosition, rollingPitch);
				rollingPitch = subList.get(j).getTopPitch();
			}
			double[] prediction = getPrediction(arr);
			for (double d: prediction)
			{
				completePrediction.add(d);
				System.out.println("prediction=" + d);
			}
		}
		for (int i = 0; i < aSortedMuList.size(); i++)
		{
			if (completePrediction.get(i) > DYNAMIC_ACCENT_THRESHOLD) 
			{
				aSortedMuList.get(i).addTag(MuTag.DYNAMIC_ACCENT);
			}
		}
		double[] finalPrediction = makeArray(completePrediction);
		return finalPrediction;
	}



	public double[] makeArray (List<Double> completePrediction)
	{
		double[] finalPrediction = new double[completePrediction.size()];
		for (int i = 0; i < finalPrediction.length; i++)
		{
			finalPrediction[i] = completePrediction.get(i);
		}
		return finalPrediction;
	}



	// the magic numbers below reflect the scaling used in building the training set in the relevant jupyter notebook
	public double[] addNextReadingToSequence (Mu aMu, double basePosition, int previousPitch)
	{
		double pitch = (aMu.getTopPitch() - previousPitch) / 24.0;
		double position = (aMu.getGlobalPositionInQuarters() - basePosition) / 25.0;
		double length = (aMu.getLengthInQuarters()) / 20.0;
		System.out.println("beatStrength=" + aMu.getBeatStrength());
		double beatStrength = (double)(aMu.getBeatStrengthConsideringSyncopation()) / 4.0;
		double syncopation = aMu.hasTag(MuTag.IS_SYNCOPATION) ? 1.0 : 0.0;
		return new double[] {pitch, position, length, beatStrength, syncopation};
	}
	
	
	
	private double[] getPrediction (double[][] arr)
	{
		INDArray timeSteps = Nd4j.zeros(1, arr.length, arr[0].length);
		for (int i = 0; i < arr.length; i++)
		{
			for (int j = 0; j < arr[i].length; j++)
			{
				timeSteps.putScalar(new int[] {0, i, j}, arr[i][j]);
			}
		}
		System.out.println(timeSteps);
		
		INDArray ret = model.output(timeSteps);
		double[] dArr = new double[sequenceLength];
		for (int i = 0; i < sequenceLength; i++)
		{
			dArr[i] = ret.getDouble(new int[] {i, 0});
		}
		return dArr;
	}
	
		
	
}
