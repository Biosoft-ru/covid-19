package ru.biosoft.covid19;

import java.util.Iterator;

import cern.colt.list.ObjectArrayList;
import cern.colt.map.OpenIntObjectHashMap;

public class AgentObservedPopulation 
{
	protected Context context;

	private int lastId = 0;
	OpenIntObjectHashMap persons = new OpenIntObjectHashMap();
	ObjectArrayList personsList  = new ObjectArrayList(0);	
	
	public int currentHealthy       = 0;
	public int currentUnsusceptible = 0;
	public int currentNotIsolated   = 0;

	protected void init(Context context)
	{
		this.context = context;
	}
	
	public void startObservation(AgentPerson person)
	{
		person.id = lastId++;
		persons.put(person.id, person);
		
		if( person.state == AgentPerson.HEALTHY )
		{
			currentHealthy++;
			context.totalPopulation.currentHealthy--;
		}
		else if( person.state == AgentPerson.UNSUSCEPTIBLE )
		{
			currentUnsusceptible++;
			context.totalPopulation.currentUnsusceptible--;
		}
	}

	public void stopObservation(AgentPerson person)
	{
		persons.removeKey(person.id);
		
		if( person.state == AgentPerson.HEALTHY )
		{
			currentHealthy--;
			context.totalPopulation.currentHealthy++;
		}
		else if( person.state == AgentPerson.UNSUSCEPTIBLE )
		{
			currentUnsusceptible--;
			context.totalPopulation.currentUnsusceptible++;
		}

		// TODO calc R0
	}
	
	
	public void process(AgentPerson person, byte previousState)
	{ }

	public void process(AgentPerson person)
	{
//if( Context.scheduler.currentTime > 300) 
//	System.out.println("Day " + Context.scheduler.currentTime + ", size=" + persons.size());

		if( person.state == AgentPerson.DEAD || person.state == AgentPerson.RECOVERED )
		{
			stopObservation(person);
			return;
		}
		
		if( person.state == AgentPerson.HEALTHY || person.state == AgentPerson.UNSUSCEPTIBLE )
		{
			boolean canBeInfected = false;
			
			for(int i=0; i<person.infectedContacts.size(); i++ )
			{
				AgentPerson source = (AgentPerson)persons.get(person.infectedContacts.get(i));
				if(source ==null )
					continue;
				
				if( source.canInfect() )
				{
					canBeInfected = true;
					break;
				}
				
			}

			if( !canBeInfected )
				stopObservation(person);
		}
	}
	
	int notIsolated;
	public void doStep()
	{
		notIsolated = 0;

//System.out.println("Day " + Context.scheduler.currentTime + ", size=" + persons.size());

		personsList = new ObjectArrayList(new Object[0]); 
		persons.values(personsList);

		personsList.forEach( (Object obj) ->
		{
			AgentPerson person = (AgentPerson)obj;
			person.doStep();
			process(person);

			if( ! person.isIsolated )
				notIsolated++;			

			return true;
//System.out.print(((AgentPerson)person).id + " ");
		});

		currentNotIsolated = notIsolated;
	}
	
}
