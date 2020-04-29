package ru.biosoft.covid19;

import cern.colt.list.ObjectArrayList;
import cern.colt.map.OpenIntObjectHashMap;

public class AgentObservedPopulation 
{
	private int lastId = 0;
	OpenIntObjectHashMap persons = new OpenIntObjectHashMap();
	
	public void addPerson(AgentPerson person)
	{
		person.id = lastId++;
		persons.put(person.id, person);
	}

	public void process(AgentPerson person, byte previousState)
	{ }

	public void process(AgentPerson person)
	{
if( Context.scheduler.currentTime > 300) 
	System.out.println("Day " + Context.scheduler.currentTime + ", size=" + persons.size());

		if( person.state == AgentPerson.DEAD || person.state == AgentPerson.RECOVERED )
		{
			persons.removeKey(person.id);
		
			// TODO calc R0
			
			return;
		}
		
		if( person.state == AgentPerson.HEALTHY )
		{
			boolean canBeInfected = false;
			for(int i=0; i<person.sources.size(); i++ )
			{
				AgentPerson source = (AgentPerson)persons.get(person.sources.get(i));
				if( source != null && (source.state == AgentPerson.INCUBATION || source.state == AgentPerson.ILLNESS ) )
				{
					canBeInfected = true;
					break;
				}
					
				if( !canBeInfected )
					persons.removeKey(person.id);
			}
		}
	}
	
	public void doStep()
	{
//System.out.println("Day " + Context.scheduler.currentTime + ", size=" + persons.size());

		
		ObjectArrayList list = new ObjectArrayList(persons.size()); 
		persons.values(list);

		list.forEach( (Object person) ->
		{
			((AgentPerson)person).doStep();
			process((AgentPerson)person);

			return true;
//System.out.print(((AgentPerson)person).id + " ");
		});
		
/*
		persons.forEachPair( (int key, Object person) ->
		{
			((AgentPerson)person).doStep();
			process((AgentPerson)person);
			return true;
//System.out.print(((AgentPerson)person).id + " ");
		});
*/
		
//		System.out.println();
	}
	
}
