package main.java.com.dougron.mucus.mucus_output_manager.musicxml_maker.voice_mu;


/*
 * Notations are symbols added to notes in a score, like staccatos and accents and so on....
 */
public enum Notation {

	STACCATO("articulations", "staccato"),
	ACCENT("articulations", "accent");
	
	
	public final String groupName;
	public final String name;
	
	private Notation(String groupName, String name)
	{
		this.groupName = groupName;
		this.name = name;
	}
	
}
