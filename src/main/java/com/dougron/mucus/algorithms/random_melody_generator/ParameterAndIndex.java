package main.java.com.dougron.mucus.algorithms.random_melody_generator;

import org.json.JSONObject;

public class ParameterAndIndex
{
	private Parameter parameter;
	private int listIndex;
	private int parameterIndex;
	// 'narrative' fields
	private double startValue = -1;
	private double endValue = -1;
	private boolean hasStartValue;
	private boolean hasEndValue;
	

//	public ParameterAndIndex(Parameter aParameter, int aIndex)
//	{
//		parameter = aParameter;
//		index = aIndex;
//	}
//	
	
	public JSONObject asJSON()
	{
		JSONObject json = new JSONObject();
		json.put("parameter", parameter);
		json.put("listIndex", listIndex);
		json.put("parameterIndex", parameterIndex);
		json.put("startValue", startValue);
		json.put("endValue", endValue);

		return json;
	}
	

	public ParameterAndIndex (Parameter aParameter, int aParameterIndex, int aListIndex)
	{
		parameterIndex = aParameterIndex;
		listIndex = aListIndex;
		parameter = aParameter;
	}



	public Parameter getParameter()
	{
		return parameter;
	}
	
	

	public void setParameter(Parameter parameter)
	{
		this.parameter = parameter;
	}
	
	

	public int getListIndex()
	{
		return listIndex;
	}
	
	

	public void setListIndex(int aListIndex)
	{
		this.listIndex = aListIndex;
	}
	
	
	
	public double getStartValue()
	{
		return startValue;
	}



	public void setStartValue(double startValue)
	{
		this.startValue = startValue;
		hasStartValue = true;
	}



	public boolean hasStartValue()
	{
		return hasStartValue;
	}



	public double getEndValue()
	{
		return endValue;
	}



	public void setEndValue(double endValue)
	{
		this.endValue = endValue;
		hasEndValue = true;
	}



	public boolean hasEndValue()
	{
		return hasEndValue;
	}



	public int getParameterIndex ()
	{
		return parameterIndex;
	}



	public void setParameterIndex (int parameterIndex)
	{
		this.parameterIndex = parameterIndex;
	}



	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(parameter + " parameterIndex=" + parameterIndex + " listIndex=" + listIndex);
		if (hasStartValue) sb.append(" startValue=" + startValue);
		if (hasEndValue) sb.append(" endValue=" + endValue);
		return sb.toString();
	}



	
}