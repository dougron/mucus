package main.java.com.dougron.mucus.mu_framework.ruler.tempo_list;

@SuppressWarnings("serial")
public class InvalidTempoChangePosition extends Exception
{
	public InvalidTempoChangePosition()
	{
		super("TempoChange position must be >= 0.0\n");
	}
}
