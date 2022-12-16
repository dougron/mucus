package main.java.com.dougron.mucus.algorithms.mu_generator;

import lombok.Getter;
import lombok.Setter;

public class DeserializableMuG
{
	
	@Getter @Setter private String className;

	public DeserializableMuG(Class clazz)
	{
		setClassName(clazz.getName());
	}
}
