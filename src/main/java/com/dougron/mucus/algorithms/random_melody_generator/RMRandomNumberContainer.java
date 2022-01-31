package main.java.com.dougron.mucus.algorithms.random_melody_generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.IndexedSingleValue;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.IndexedValueList;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.RNDValueObject;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.SingleValue;
import main.java.com.dougron.mucus.algorithms.random_melody_generator.random_number_containers.ValueList;
import main.java.da_utils.combo_variables.IntAndString;

public class RMRandomNumberContainer
{
	
//	public enum Parameter 
//	{
//		PHRASE_LENGTH, TIME_SIGNATURE, PHRASE_START_PERCENT, PHRASE_END_PERCENT, 
//		STRUCTURE_TONE_SPACING, TEMPO, XMLKEY, START_NOTE, STRUCTURE_TONE_CONTOUR, 
//		STRUCTURE_TONE_MULTIPLIER, CHORD_LIST_GENERATOR, EMBELLISHMENT_CLEARANCE, EMBELLISHMENT_REPETITION_PATTERN, MUG_LISTS,
////		MUG_COUNT_0, MUG_COUNT_1, MUG_COUNT_2, MUG_COUNT_3,
////		MUG_PITCH_0, MUG_PITCH_1, MUG_PITCH_2, MUG_PITCH_3,
////		MUG_RHYTHM_0, MUG_RHYTHM_1, MUG_RHYTHM_2, MUG_RHYTHM_3, 
//		MUG_LIST_COUNT, MUG_LIST_RHYTHM, MUG_LIST_PITCH
//	}
	
//	private static final Parameter[] parameterSequenceForJSON = new Parameter[]
//			{
//				Parameter.PHRASE_LENGTH, Parameter.TIME_SIGNATURE, Parameter.TEMPO, 
//				Parameter.PHRASE_START_PERCENT, Parameter.PHRASE_END_PERCENT, 
//				Parameter.STRUCTURE_TONE_SPACING, Parameter.XMLKEY, Parameter.START_NOTE, 
//				Parameter.STRUCTURE_TONE_CONTOUR, Parameter.STRUCTURE_TONE_MULTIPLIER, Parameter.EMBELLISHMENT_CLEARANCE, 
//				Parameter.CHORD_LIST_GENERATOR, Parameter.EMBELLISHMENT_REPETITION_PATTERN, Parameter.MUG_LISTS,
//				Parameter.MUG_LIST_COUNT, Parameter.MUG_LIST_RHYTHM, Parameter.MUG_LIST_PITCH
//			};
	
	private HashMap<Parameter, RNDValueObject> map = new HashMap<Parameter, RNDValueObject>();
	private static String path = "D:/Documents/miscForBackup/RandomMelodyRandomNumberLists/";
	private static String fileExtension = ".rnd_container";
	private String narrative = "";
	private JSONObject jsonThinking = new JSONObject();
	
	
	
	
	
	
	public RMRandomNumberContainer deepCopy()
	{
		RMRandomNumberContainer por = new RMRandomNumberContainer();
		for (Parameter p: Parameter.values())
		{		
			if (map.containsKey(p)) por.put(p, map.get(p).deepCopy());		
		}
		return por;
	}
	
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Parameter p: Parameter.values())
		{
			sb.append(p + ": ");
//			ArrayList<Double> list = map.get(p);
			if (!map.containsKey(p))
			{
				sb.append("null");
			}
			else
			{
				sb.append(map.get(p).toString());	
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
	public RNDValueObject get(Parameter aParameter)
	{
		if (map.containsKey(aParameter)) return map.get(aParameter);
		return null;
	}
	
	
	// assumes a ValueListr
	public void put(Parameter aParameter, RNDValueObject aRndValueObject)
	{
		map.put(aParameter, aRndValueObject);
	}
	
	
	
	public boolean containsKey(Parameter aParameter)
	{
		return map.containsKey(aParameter);
	}
	
	
	
	public void setSingleValueVariable(Parameter aParameter, Random rnd)
	{
		map.put(aParameter, new SingleValue(rnd.nextDouble()));
	}
	
	
	
	public void setValueList(Parameter aParameter, ArrayList<Double> rndList)
	{
		map.put(aParameter, new ValueList(rndList));
	}
	
	
	
	public void setValueList(Parameter aParameter, double[] rndArr)
	{
		map.put(aParameter, new ValueList(rndArr));
	}
	
	
	
	public void setIndexedSingleValue 
	(
			Parameter aParameter, 
			int aParameterIndex,
			Random aRnd
			)
	{
		if (!map.containsKey(aParameter))
		{
			map.put(aParameter, new IndexedSingleValue());
		}
		RNDValueObject rvo = map.get(aParameter);
		rvo.add(aParameterIndex, aRnd.nextDouble());	
	}
	
	
	public void setIndexedValueList (Parameter aParameter, int aParameterIndex, double[] aRndArr)
	{
		if (!map.containsKey(aParameter))
		{
			map.put(aParameter, new IndexedValueList());
		}
		RNDValueObject rvo = map.get(aParameter);
		for (double value: aRndArr)
		{
			rvo.add(aParameterIndex, value);
		}
		
	}

	
	
	
	int getItemAsIntIndex(Parameter aParameter, int aRange)
	{
		if (map.containsKey(aParameter))
		{
			return (int)(map.get(aParameter).getValue() * aRange);
		}
		return 0;
	}
	
	
	
	static int getItemAsIntIndex(double aRndValue, int aRange)
	{
		return (int)(aRndValue * aRange);
	}
	
	
	
	public void saveAsTextDocument(String aDirectoryPath, String aFileName)
	{
		try 
		{
			// Creates a FileWriter
			FileWriter file = new FileWriter(aDirectoryPath + aFileName + fileExtension);

			// Creates a BufferedWriter
			BufferedWriter output = new BufferedWriter(file);
			
			
			// Writes the string to the file
			output.write("RMRandomNumberContainer v2.0");
			output.newLine();
			for (Parameter parameter: Parameter.values())
			{
				if (map.containsKey(parameter))
				{
					output.write(parameter + ":" + map.get(parameter).getTextFileString());
					output.newLine();
				}
			}
			output.close();
		}
		catch (Exception e) 
		{
			e.getStackTrace();
		}
	}
	
	
	
	public void saveAsTextDocument(String aFileName)
	{
		saveAsTextDocument(path, aFileName);
	}
	
	
	
	public String saveAsJSON(String aDirectoryPath, String aFileName)
	{
		JSONObject obj = new JSONObject();
		for (Parameter key: Parameter.values())
		{
			if (map.containsKey(key))
			{
				RNDValueObject rndvo = map.get(key);
				rndvo.addSelfToJSON(key, obj);
			}
			
		}
		
		try
		{
			String path = aDirectoryPath + aFileName + fileExtension;
			FileWriter file = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(file);
			bw.write(obj.toString());
			bw.close();
			return "file written successfully";
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
			return "exception caught";
		}
	}


	
	public static RMRandomNumberContainer loadFile(String aFileName)
	{
		return loadFile(new File(path + aFileName + fileExtension));
	}
	
	
	
	// this is for version 2.0 rndContainer files
	public static RMRandomNumberContainer loadFile(File file)
	{
		BufferedReader objReader = null;
		try {
			RMRandomNumberContainer rmrnc = new RMRandomNumberContainer();
			String strCurrentLine;
			objReader = new BufferedReader(new FileReader(file));
			boolean firstLine = true;
			while ((strCurrentLine = objReader.readLine()) != null) 
			{
				if (firstLine)
				{
					firstLine = false;
				}
				else
				{
					addRNDValueObjects(rmrnc, strCurrentLine);
				}				
			}
			return rmrnc;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (objReader != null) objReader.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}	
		return null;
	}


	public static void addRNDValueObjects (RMRandomNumberContainer rmrnc,
			String strCurrentLine)
	{
		String[] lineArr = strCurrentLine.split(":");
		Parameter parameter = Parameter.valueOf(lineArr[0]);
		String valueType = lineArr[1];
		String content = lineArr[2];
//		String[] sArr;
//		RNDValueObject rvo;
		switch (valueType)
		{
		case "SingleValue":
			rmrnc.map.put(parameter, new SingleValue(Double.valueOf(content)));
			break;		
		case "ValueList":
			double[] arr = getDoubleArrayFromCommaSeparatedString(content);
			rmrnc.map.put(parameter, new ValueList(arr));
			break;			
		case "IndexedSingleValue":
			handleIndexedSingleValue(rmrnc, parameter, content);
			break;			
		case "IndexedValueList":
			handleIndexedValueList(rmrnc, parameter, content);
			break;
		default:
			break;
		}
	}


	
	public static void handleIndexedValueList (RMRandomNumberContainer rmrnc,
			Parameter parameter, String content)
	{
		String[] sArr;
		RNDValueObject rvo;
		sArr = content.split(";");
		rvo = new IndexedValueList();
		for (String s: sArr)
		{
			IntAndString ias = getIndexAndContentFromEqualsSeparatedString(s);
			double[] dArr = getDoubleArrayFromCommaSeparatedString(ias.str);
			for (double d: dArr)
			{
				rvo.add(ias.i, d);
			}
		}
		rmrnc.map.put(parameter, rvo);
	}


	
	public static void handleIndexedSingleValue (RMRandomNumberContainer rmrnc,
			Parameter parameter, String content)
	{
		String[] sArr;
		RNDValueObject rvo;
		sArr = content.split(";");
		rvo = new IndexedSingleValue();
		for (String s: sArr)
		{
			IntAndString ias = getIndexAndContentFromEqualsSeparatedString(s);
			rvo.add(ias.i, Double.valueOf(ias.str));
		}
		rmrnc.map.put(parameter, rvo);
	}


	
	public static IntAndString getIndexAndContentFromEqualsSeparatedString (
			String s)
	{
		String[] ss = s.split("=");
		IntAndString ias = new IntAndString(Integer.valueOf(ss[0]), ss[1]);
		return ias;
	}


	
	public static double[] getDoubleArrayFromCommaSeparatedString (
			String content)
	{
		String[] sArr = content.split(",");
		double[] arr = new double[sArr.length];
		for (int i = 0; i < sArr.length; i++)
		{
			arr[i] = Double.valueOf(sArr[i]);
		}
		return arr;
	}


	
//	public static RMRandomNumberContainer loadFileVer1(File file)
//	{
//		BufferedReader objReader = null;
//		try {
//			RMRandomNumberContainer rmrnc = new RMRandomNumberContainer();
//			String strCurrentLine;
//			objReader = new BufferedReader(new FileReader(file));
//			while ((strCurrentLine = objReader.readLine()) != null) 
//			{
////				System.out.println(strCurrentLine);
//				String[] lineArr = strCurrentLine.split(":");
//				String[] valueArr = lineArr[1].split(",");
//				ArrayList<Double> list = new ArrayList<Double>();
//				for (String s: valueArr)
//				{
//					list.add(Double.valueOf(s));
//				}
//				Parameter parameter = Parameter.valueOf(lineArr[0]);
//				rmrnc.map.put(parameter, list);
//				
//			}
//			return rmrnc;
//		} 
//		catch (IOException e) 
//		{
//			e.printStackTrace();
//		} 
//		finally 
//		{
//			try 
//			{
//				if (objReader != null) objReader.close();
//			} 
//			catch (IOException ex) 
//			{
//				ex.printStackTrace();
//			}
//		}	
//		return null;
//	}

	

	public String getNarrative()
	{
		return narrative;
	}

	

	public void setNarrative(String narrative)
	{
		this.narrative = narrative;
	}
	
	
	


	public double getValue (ParameterAndIndex pai)
	{
		return map.get(pai.getParameter()).getValue(pai.getParameterIndex(), pai.getListIndex());
	}

	

	public void changeValue (ParameterAndIndex pai)
	{
		map.get(pai.getParameter()).change(pai);
		
	}
	
	
	


	public static RMRandomNumberContainer getRndContainerFromJSONFile (
			String aFilePath)
	{
		RMRandomNumberContainer rndContainer = new RMRandomNumberContainer();
		try
		{
			FileReader fr = new FileReader(aFilePath);    
			BufferedReader br = new BufferedReader(fr);
			JSONObject jobj = new JSONObject(br.readLine());
			
			for (String key: jobj.keySet())
			{
//				System.out.println(jobj.get(key).getClass());
				Object o = jobj.get(key);
				
				if (o instanceof BigDecimal)
				{
					rndContainer.put(Parameter.valueOf(key), new SingleValue(((BigDecimal)o).doubleValue()));
				}
				
				else if (o instanceof JSONArray)
				{
					ArrayList<Double> list = makeValueList(o);
					rndContainer.put(Parameter.valueOf(key), new ValueList(list));
				}
				
				else if (o instanceof JSONObject)
				{
					JSONObject jsonObj = (JSONObject)o;
					boolean typeIsArray = getTypeOfJSONObject(jsonObj);
					if (typeIsArray)
					{
						RNDValueObject vo = loadIndexedValueList(jsonObj);
						rndContainer.put(Parameter.valueOf(key), vo);
					}
					else
					{
						RNDValueObject vo = loadIndexedSingleValue(jsonObj);
						rndContainer.put(Parameter.valueOf(key), vo);
					}				
				}
			}
			br.close();
		}
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		return rndContainer;
	}


	public static ArrayList<Double> makeValueList (Object o)
	{
		ArrayList<Double> list = new ArrayList<Double>();
		JSONArray arr = (JSONArray)o;
		for (int i = 0; i < arr.length(); i++)
		{
			list.add(arr.getDouble(i));
		}
		return list;
	}
	
	
	public static RNDValueObject loadIndexedSingleValue (JSONObject jsonObj)
	{
		RNDValueObject vo = new IndexedSingleValue();
		for (String objkey: jsonObj.keySet())
		{
			BigDecimal bd = jsonObj.getBigDecimal(objkey);
			vo.add(Integer.parseInt(objkey), bd.doubleValue());			
		}
		return vo;
	}


	public static RNDValueObject loadIndexedValueList (JSONObject jsonObj)
	{
		RNDValueObject vo = new IndexedValueList();
		Iterator<String> it = jsonObj.keys();
		while (it.hasNext())
		{
			String objkey = it.next().strip();
			JSONArray jarr = jsonObj.getJSONArray(objkey);
			for (Object oo: jarr)
			{
				int key = Integer.parseInt(objkey);
				vo.add(key, ((BigDecimal)oo).doubleValue());
			}			
		}
		return vo;
	}


	public static boolean getTypeOfJSONObject (JSONObject jsonObj)
	{
		String testKey = jsonObj.keys().next();
		boolean typeIsArray = false;
		try
		{
			jsonObj.getJSONArray(testKey);
			typeIsArray = true;
		}
		catch (JSONException jex)
		{
			
		}
		return typeIsArray;
	}


	
	public void appendJSONThinking(String key, JSONObject aJSONObject)
	{
		jsonThinking.put(key, aJSONObject);
	}
	
	
	
	public void appendJSONThinking(String key, JSONArray aJSONArray)
	{
		jsonThinking.put(key, aJSONArray);
	}
	
	
	
	public JSONObject getJSONThinking()
	{
		return jsonThinking;
	}
	

	
}



interface ParameterKey
{
	Parameter getParameter();
	int getIndex();
}



class ParameterAndIndexKey implements ParameterKey
{

	private Parameter parameter;
	private int index;

	
	public ParameterAndIndexKey (Parameter aParameter, int aIndex)
	{
		parameter = aParameter;
		index = aIndex;
	}
	
	
	@Override
	public Parameter getParameter ()
	{
		return parameter;
	}

	
	
	@Override
	public int getIndex ()
	{
		return index;
	}
	
}


class ParameterOnlyKey implements ParameterKey
{

	private Parameter parameter;

	
	public ParameterOnlyKey (Parameter aParameter)
	{
		parameter = aParameter;
	}
	
	
	@Override
	public Parameter getParameter ()
	{
		return parameter;
	}

	
	
	@Override
	public int getIndex ()
	{
		return 0;
	}
	
}