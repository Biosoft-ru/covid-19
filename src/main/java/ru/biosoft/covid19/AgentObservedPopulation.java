package ru.biosoft.covid19;

//import gnu.trove.map.hash.TIntObjectHashMap;	
import cern.colt.map.OpenIntObjectHashMap;

public class AgentObservedPopulation 
{
	private int lastId = 0;
//	TIntObjectHashMap<AgentPerson> persons = new TIntObjectHashMap<>();
	OpenIntObjectHashMap persons = new OpenIntObjectHashMap();
	
	public void addPerson(AgentPerson person)
	{
		person.id = lastId++;
		persons.put(person.id, person);
	}
	
	public void process(AgentPerson person, byte previousState)
	{
		if( person.state == AgentPerson.DEAD || person.state == AgentPerson.RECOVERED )
			persons.removeKey(person.id);
	}
	
	public void doStep()
	{
//System.out.println("Day " + Context.scheduler.currentTime + ", size=" + persons.size());
		persons.forEachPair( (int key, Object person) ->
		{
			((AgentPerson)person).doStep();
//System.out.print(((AgentPerson)person).id + " ");
			return true;
		});
		
//		System.out.println();
	}
}
