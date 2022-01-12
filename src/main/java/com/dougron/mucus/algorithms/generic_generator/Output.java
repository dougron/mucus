package main.java.com.dougron.mucus.algorithms.generic_generator;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/* 
 * replacement for the MucusIxxxxData object with more generic handling of logging
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import main.java.com.dougron.mucus.mu_framework.Mu;

@ToString
public class Output
{
	@Getter
	@Setter
	Mu mu;
	
	@Getter
//	@Setter
//	@Singular
	List<JSONObject> logs = new ArrayList<JSONObject>();
	
	public void addLog(JSONObject aJsonObject)
	{
		logs.add(aJsonObject);
	}
}
