package ru.biosoft.covid19;

public class Scheduler 
{
	protected int currentTime = 0;
	protected int endTime     = 300;
	
	public void run()
	{
		for(currentTime=0; currentTime<endTime; currentTime++)
		{
			Context.arrived.doStep();
	
			Context.observedPopulation.doStep();
			Context.totalPopulation.doStep();
			Context.plot.doStep();
			Context.statCounter.doStep();
		}
	}
}
