package main.java.com.dougron.mucus.algorithms.mu_generator;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class MuGeneratorAdapter extends TypeAdapter<MuGenerator>
{
	

	@Override
	public MuGenerator read(JsonReader reader) throws IOException
	{
		
//		doStuff(reader);
		return null;
	}
	

	@Override
	public void write(JsonWriter writer, MuGenerator mug) throws IOException
	{
		
	}
	
	private void doStuff(JsonReader jsonReader)
	{
		try
		{
			while (jsonReader.hasNext()) 
			{
				JsonToken nextToken = jsonReader.peek();
				System.out.println("---" + nextToken.toString());
				
				if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {
					
					jsonReader.beginObject();
					
				} else if (JsonToken.NAME.equals(nextToken)) {
					
					String name = jsonReader.nextName();
					System.out.println("Token KEY >>>> " + name);
					
				} else if (JsonToken.STRING.equals(nextToken)) {
					
					String value = jsonReader.nextString();
					System.out.println("Token Value >>>> " + value);
					
				} else if (JsonToken.NUMBER.equals(nextToken)) {
					
					long value = jsonReader.nextLong();
					System.out.println("Token Value >>>> " + value);
					
				} else if (JsonToken.NULL.equals(nextToken)) {
					
					jsonReader.nextNull();
					System.out.println("Token Value >>>> null");
					
				} else if (JsonToken.END_OBJECT.equals(nextToken)) {
					
					jsonReader.endObject();
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try
			{
				jsonReader.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}


