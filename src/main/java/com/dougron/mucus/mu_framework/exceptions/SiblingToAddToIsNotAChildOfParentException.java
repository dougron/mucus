package main.java.com.dougron.mucus.mu_framework.exceptions;


@SuppressWarnings("serial")
public class SiblingToAddToIsNotAChildOfParentException extends Exception
{


	public SiblingToAddToIsNotAChildOfParentException()
	{
		super("SiblingTAddTo is not a child of this Mu");
	}
}
