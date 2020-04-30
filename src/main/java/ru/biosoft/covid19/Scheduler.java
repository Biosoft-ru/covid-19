package ru.biosoft.covid19;

public class Scheduler
{
	private long start;
	
	protected int currentTime = 0;
	protected int endTime     = 500;
	
	protected boolean stop = false; 
	
	public String run(Context context)
	{
		start = System.currentTimeMillis();
		
		for(currentTime=0; currentTime<endTime; currentTime++)
		{
			if( stop )
			{
				stop = false;
				break;
			}
			
			Context.arrived.doStep();
	
			Context.observedPopulation.doStep();
			Context.totalPopulation.doStep();
			Context.plot.doStep();
			Context.statCounter.doStep();
		}
		
		return "<html>Simulation time: " + (System.currentTimeMillis() - start) + "<br>"  +
			    "Population size: " + Context.observedPopulation.persons.size() + "</html>";
	}

	public void terminate()
	{
		stop = true;
	}
}
