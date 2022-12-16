package test.java.com.dougron.mucus.algorithms.mu_generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileReader;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import main.java.com.dougron.mucus.algorithms.generic_generator.AccentType;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_Anticipation;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_Anticipation_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_ApproachTone_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_ChordTone_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_EscapeTone;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_EscapeTone_RRP;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuG_NothingToAdd;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGenerator;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGeneratorAdapter;
import main.java.com.dougron.mucus.algorithms.mu_generator.MuGeneratorGsonDeserializationUtility;
import main.java.com.dougron.mucus.algorithms.mu_generator.enums.ChordToneType;
import main.java.com.dougron.mucus.algorithms.mu_generator.enums.EscapeToneType;
import main.java.com.dougron.mucus.mu_framework.data_types.RelativeRhythmicPosition;

class MuGenerator_SerializationTests
{

	@Test
	void MuG_Anticipation_RRP_is_deserialized_correctly() throws ClassNotFoundException
	{
		MuGenerator mug = new MuG_Anticipation_RRP(new RelativeRhythmicPosition(0, 0, 0, -1));
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(mug);
		MuGenerator newMuggle = MuGeneratorGsonDeserializationUtility.getMuGenerator(jsonString);
		
		assertThat(newMuggle instanceof MuG_Anticipation_RRP).isTrue();
	}
	
	
	@Test
	void MuG_Anticipation_is_deserialized_correctly() throws ClassNotFoundException
	{
		MuGenerator mug = new MuG_Anticipation(3, 0.5);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(mug);
		MuGenerator newMuggle = MuGeneratorGsonDeserializationUtility.getMuGenerator(jsonString);
		
		assertThat(newMuggle instanceof MuG_Anticipation).isTrue();
	}
	
	
	@Test
	void MuG_EscapeTone_RRP_is_deserialized_correctly() throws ClassNotFoundException
	{
		MuGenerator mug = new MuG_EscapeTone_RRP(
				new RelativeRhythmicPosition(0, 0, 0, -1),
				EscapeToneType.JUMP_STEP,
				AccentType.UNACCENTED
				);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(mug);
		MuGenerator newMuggle = MuGeneratorGsonDeserializationUtility.getMuGenerator(jsonString);
		
		assertThat(newMuggle instanceof MuG_EscapeTone_RRP).isTrue();
	}
	
	
	@Test
	void MuG_EscapeTone_is_deserialized_correctly() throws ClassNotFoundException
	{
		MuGenerator mug = new MuG_EscapeTone(
				0.5, 
				EscapeToneType.JUMP_STEP,
				AccentType.UNACCENTED
				);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(mug);
		MuGenerator newMuggle = MuGeneratorGsonDeserializationUtility.getMuGenerator(jsonString);
		
		assertThat(newMuggle instanceof MuG_EscapeTone).isTrue();
	}

	
	@Test
	void MuG_ApproachTone_RRP_is_deserialized_correctly() throws ClassNotFoundException
	{
		MuGenerator mug = new MuG_ApproachTone_RRP(
				new RelativeRhythmicPosition(0, 0, 0, -1),
				AccentType.UNACCENTED
				);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(mug);
		MuGenerator newMuggle = MuGeneratorGsonDeserializationUtility.getMuGenerator(jsonString);
		
		assertThat(newMuggle instanceof MuG_ApproachTone_RRP).isTrue();
	}

	
	@Test
	void MuG_ChordTone_RRP_is_deserialized_correctly() throws ClassNotFoundException
	{
		MuGenerator mug = new MuG_ChordTone_RRP(
				new RelativeRhythmicPosition(0, 0, 0, -1),
				ChordToneType.CLOSEST_ABOVE,
				-1
				);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(mug);
		MuGenerator newMuggle = MuGeneratorGsonDeserializationUtility.getMuGenerator(jsonString);
		
		assertThat(newMuggle instanceof MuG_ChordTone_RRP).isTrue();
	}
	
	
	@Test
	void MuG_NothingToAdd_is_deserialized_correctly() throws ClassNotFoundException
	{
		MuGenerator mug = new MuG_NothingToAdd();
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(mug);
		MuGenerator newMuggle = MuGeneratorGsonDeserializationUtility.getMuGenerator(jsonString);
		
		assertThat(newMuggle instanceof MuG_NothingToAdd).isTrue();
	}
	
	
	
	// TypeAdapter approach abandoned in favour of the LorezParameterObjectJsonDeserializerUtility
//	@Test
//	void using_custon_TypeAdapter_from_file() throws Exception
//	{
//		String path = "D:/Documents/miscForBackup/Mubot001_BigPersonalityTest/like_list/20221107_113555827/20221107_113555827.lorezparam";
//		JsonReader reader = new JsonReader(new FileReader(path));
//		GsonBuilder builder = new GsonBuilder(); 
//		builder.registerTypeAdapter(MuGenerator.class, new MuGeneratorAdapter()); 
//		Gson gson = builder.create(); 
//		TypeAdapter<MuGenerator> adapter = new MuGeneratorAdapter();
//		adapter.read(reader);
//	}
//	
//	
//	@Test
//	void using_a_custom_TypeAdapter() throws Exception
//	{
//		String jsonString = getJsonString();
//		System.out.println(jsonString);
//		GsonBuilder builder = new GsonBuilder(); 
//		builder.registerTypeAdapter(MuGenerator.class, new MuGeneratorAdapter()); 
//		Gson gson = builder.create(); 
////		JsonReader reader = new JsonReader( new StringReader(jsonString) );
//		MuGenerator mug = gson.fromJson(jsonString, MuGenerator.class);
//	}

	
// ------------ privates -----------------------------------------------------
	
 	private String getJsonString()
	{
		MuGenerator mug = new MuG_ChordTone_RRP(
				new RelativeRhythmicPosition(0, 0, 0, -1),
				ChordToneType.CLOSEST_ABOVE,
				-1
				);
		Gson gson = new GsonBuilder().create();
		String jsonString = gson.toJson(mug);
		return jsonString;
	}
	
}
