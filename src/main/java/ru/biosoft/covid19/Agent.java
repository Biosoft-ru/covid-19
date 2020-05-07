package ru.biosoft.covid19;

public abstract class Agent 
{
	protected Context context;
	
	public void init(Context context)
	{
		this.context = context; 
	}

	public void doStep()
	{}
}
