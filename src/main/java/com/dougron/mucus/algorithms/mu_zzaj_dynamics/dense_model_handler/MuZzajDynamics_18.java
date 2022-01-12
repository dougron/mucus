package main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics.dense_model_handler;

/*
 * java class that delivers a prediction about whether a note is
 * accented or not using a nn trained on the zzaj dataset and
 * taking the eight nearest notes position and melody contour, and the
 * beatstrength and syncopation status of the note in question
 */

import java.io.IOException;
import java.util.List;

import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import com.google.common.base.Preconditions;

import main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics.MuZzajDynamics;
import main.java.com.dougron.mucus.mu_framework.Mu;
import main.java.com.dougron.mucus.mu_framework.mu_tags.MuTag;

public class MuZzajDynamics_18 implements MuZzajDynamics
{

	
	private static final double DYNAMIC_ACCENT_THRESHOLD = 0.5;
	private static MuZzajDynamics_18 instance = null;
//	private static String modelPath = "D:/Documents/algorithm data for googledrive/CorpusCapture2020/zzaj corpus/models/closest8/model_8closest_error23.h5";
//	private static String modelPath = "model.h5";
	MultiLayerNetwork model;
	int inputShape = 18;
	int numberOfNeighbours = 8;
	
	
	
	public MuZzajDynamics_18()
	{
		try
		{
			String simpleMlp = new ClassPathResource("mu_zzaj_dynamics/resources/model.h5").getFile().getPath();
			model = KerasModelImport.importKerasSequentialModelAndWeights(simpleMlp);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		Preconditions.checkArgument(aSortedMuList.size() > 8, "MuZzajDynamics_18 requires minimum 9 items in aSortedMuList");
		
		Mu theNote = aSortedMuList.get(0);
		int beatStrength = theNote.getBeatStrength();
		int isSyncoption = theNote.hasTag(MuTag.IS_SYNCOPATION) ? 0 : 1;
		double[] eightPositions = new double[numberOfNeighbours];
		int[] eightContours = new int[numberOfNeighbours];
		for (int i = 0; i < numberOfNeighbours; i++)
		{
			Mu mu = aSortedMuList.get(i + 1);
			eightPositions[i] = mu.getGlobalPositionInQuarters() - theNote.getGlobalPositionInQuarters();
			eightContours[i] = (int)Math.signum(mu.getTopPitch() - theNote.getTopPitch());
		}
		double prediction = getPrediction(beatStrength, isSyncoption, eightPositions, eightContours);
		if (prediction > DYNAMIC_ACCENT_THRESHOLD)
		{
			theNote.addTag(MuTag.DYNAMIC_ACCENT);
		}
		return new double[] {prediction};		// not strictly neccessary as the note has been tagged as a DYNAMIC_ACCENT
	}
	
	
		
	private double getPrediction(int beatStrength, int isSyncopation, double[] eightPositions, int[] eightContours)
	{
		double[] arr = new double[inputShape];
		arr[0] = beatStrength;
		arr[1] = isSyncopation;
		for (int i = 0; i < 8; i++)
		{
			arr[2 + (i * 2)] = eightPositions[i];
			arr[3 + (i * 2)] = eightContours[i];
		}
		INDArray features = Nd4j.zeros(1, inputShape);
		for (int i = 0; i < arr.length; i++)
		{
			features.putScalar(new int[] {0, i}, arr[i]);
		}
		
		INDArray ret = model.output(features);

		double prediction = ret.getDouble(0);
		return prediction;
	}



	
	
	
	
	
	
}
