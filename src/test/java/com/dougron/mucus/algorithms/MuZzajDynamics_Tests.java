package test.java.com.dougron.mucus.algorithms;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics.lstm_handler.MuZzajDynamics_LSTM_128_128;
import main.java.com.dougron.mucus.algorithms.mu_zzaj_dynamics.lstm_handler.MuZzajDynamics_LSTM_256_256;

class MuZzajDynamics_Tests
{
	
	
	@Test
	void assert_that_the_lstm_128_model_path_is_correct () throws Exception
	{
		assertDoesNotThrow(() -> {new MuZzajDynamics_LSTM_128_128();});		
	}
	
	
	@Test
	void assert_that_the_lstm_256_model_path_is_correct () throws Exception
	{
		assertDoesNotThrow(() -> {new MuZzajDynamics_LSTM_256_256();});		
	}
	
	
	@Test
	void assert_that_the_dense_model_path_is_correct () throws Exception
	{
		assertDoesNotThrow(() -> {new MuZzajDynamics_LSTM_128_128();});		
	}

	
	
//	@Test
//	void testName () throws Exception
//	{
//		MuZzajDynamics_18 model = MuZzajDynamics_18.getInstance();
//		int beatStrenth = 1;
//		int syncopation = 0;
//		double[] positions = new double[] {1.0,-1.0,2.0,-2.0,2.5,-3.5,3.0,4.0};
//		int[] melodyContours = new int[] {1,1,0,-1,-1,1,0,-1};
//		assertThat(model.getPrediction(beatStrenth, syncopation, positions, melodyContours)).isGreaterThan(0.0).isLessThan(1.0);
//	}
	
	
	
//	@Test
//	void testName () throws Exception
//	{
//		double[][] arr = new double[][] {
//			new double[] {1.0,2.0,3.0},
//			new double[] {4.0,5.0,6.0},
//			new double[] {7.0,8.0,9.0},
//			new double[] {7.0,8.0,9.0}
//		};
//		INDArray ind = Nd4j.zeros(4, 3);
//		for (int i = 0; i < arr.length; i++)
//		{
//			for (int j = 0; j < arr[i].length; j++)
//			{
//				ind.putScalar(new int[] {i, j}, arr[i][j]);
//			}
//		}
//		System.out.println(ind.toString());
//	}
	
	
//	@Test
//	void testName () throws Exception
//	{
//		Mu mu = new Mu("parent");
//		for (double d = 0.0; d < 10.0; d += 1.0)
//		{
//			Mu newMu = new Mu("ch");
//			newMu.setLengthInQuarters(1.0);
//			mu.addMu(newMu, d);
//		}
//		System.out.println(mu.toString());
//		MuZzajDynamics model = new MuZzajDynamics_LSTM_256_256();
//		double[] object = model.getPrediction(mu.getMus());
//		for (double d: object)
//		{
//			System.out.println(d);
//		}
//	}
//	
	
	
//	@Test
//	void testName () throws Exception
//	{
//		INDArray ind = Nd4j.ones(1,4);
//		System.out.println(ind);
//		double[] arr = new double[4];
//		for (int i = 0; i < 4; i++)
//		{
//			arr[i] = ind.getDouble(new int[] {0, i});
//		}
//		for (double d: arr)
//		{
//			System.out.println(d);			
//		}
//	}

	
	
	
}
