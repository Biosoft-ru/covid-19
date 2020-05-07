package ru.biosoft.covid19;

public class Scheduler
{
	private long start;
	
	protected int currentTime = 0;
	
	protected boolean stop = false; 
	
	public String run(Context context)
	{
		try
		{
			start = System.currentTimeMillis();

			int endTime = context.modelParameters.getEndTime();
			
			int unsuspectibleCheck  = context.totalPopulation.currentUnsusceptible + context.observedPopulation.currentUnsusceptible;
			int populationSizeCheck = context.totalPopulation.currentPopulationSize;

			for(currentTime=0; currentTime<endTime; currentTime++)
			{
				if( stop )
				{
					stop = false;
					break;
				}
				
				context.arrived.doStep();
			
				context.observedPopulation.doStep();
				context.totalPopulation.doStep();
				context.plot.doStep();
				context.statCounter.doStep();
				
				// consistency check
				if( unsuspectibleCheck != context.totalPopulation.currentUnsusceptible + context.observedPopulation.currentUnsusceptible )
				System.out.println("Warn, unsuspectibleCheck=" + unsuspectibleCheck + " - " +
						(context.totalPopulation.currentUnsusceptible + context.observedPopulation.currentUnsusceptible) +
						", day=" + currentTime);
			
				int size = context.totalPopulation.currentHealthy 	+ context.totalPopulation.currentUnsusceptible + 
						   context.totalPopulation.totallyRecovered	+ context.totalPopulation.totallyDead		+
						   context.observedPopulation.persons.size() - context.totalPopulation.totallyArrived;
				if( populationSizeCheck != size )
				{
					System.out.println("\r\nWarn, populationSizeCheck=" + (populationSizeCheck- size) + 
							", day=" + currentTime);
					
					System.out.println("Total population: " + context.totalPopulation.currentHealthy + ", "	
					                                        + context.totalPopulation.currentUnsusceptible + ", "
					                                        + context.totalPopulation.totallyRecovered	+ ", " 
					                                        + context.totalPopulation.totallyDead  
					                                        + "\r\nPersons:" + context.observedPopulation.persons.size());

				}
				
			}
			
			return "<html>Simulation time: " + (System.currentTimeMillis() - start) + "<br>"  +
				    "Population size: " + context.observedPopulation.persons.size() + "</html>";
		}
		catch(Throwable t)
		{
			t.printStackTrace();
			return "<html>Eror: " + t.getMessage() + "</html>";
		}
	}

	public void terminate()
	{
		stop = true;
	}
}
