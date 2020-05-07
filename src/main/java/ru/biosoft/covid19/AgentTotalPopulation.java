package ru.biosoft.covid19;

import java.util.ArrayList;

import cern.colt.list.IntArrayList;
import cern.colt.list.ObjectArrayList;

public class AgentTotalPopulation extends Agent
{
	public int currentPopulationSize;
	
	public int dailyInfected	= 0;
	public int dailyRecovered	= 0;
	public int dailyDead		= 0;
	
	public int totallyInfected	= 0;
	public int totallyRecovered	= 0;
	public int totallyDead		= 0;
	public int totallyArrived   = 0;

	public int currentHealthy;
	public int currentUnsusceptible;
	
	
	public void init(Context context)
	{
		super.init(context);

		currentPopulationSize = context.modelParameters.getPopulationSize();
		currentUnsusceptible  = (int) (currentPopulationSize * (1.0 - context.modelParameters.getPIsSusceptible()));  
		currentHealthy        = currentPopulationSize - currentUnsusceptible;
	}
	
	protected void generateContacts(AgentPerson infected, AgentPerson source)
	{
		int contactsNumber = contactsNumber(infected);
		if( contactsNumber == 0 )
			return;

		ArrayList<AgentPerson> contactsList = new ArrayList<AgentPerson>();
		
		// contact and infected person share some common contacts 
		if( source != null )
		{
			for(AgentPerson c : source.nearestContacts )
			{
				if( infected.id != c.id && Random.getUniform() < context.modelParameters.getPContactReuse() )
				{
					contactsNumber--;
					contactsList.add(c);
			    	c.infectedContacts.add(infected.id);
			    	
			    	if( contactsNumber <= 0 )
			    		break;
				}
			}
		}

		// calculate probability to get person from observed population
		double p_reuseFromObserved = ((double)context.observedPopulation.currentNotIsolated) /
				(currentPopulationSize + context.observedPopulation.persons.size());

		int observedListSize = context.observedPopulation.personsList.size(); 
		
		while(contactsNumber-- > 0)
		{
			AgentPerson c = null;
			if( p_reuseFromObserved > 0.05 && Random.getUniform() < p_reuseFromObserved )
			{
				int idx = Random.getUniform(1, observedListSize);
				while( idx-- > 0 )
				{
					AgentPerson p = (AgentPerson)context.observedPopulation.personsList.get(idx);
					if( !p.isIsolated && context.observedPopulation.persons.containsKey(p.id) )
					{
						c = p; 
				    	c.infectedContacts.add(infected.id);
						break;
					}
				}
			}
			
			if( c== null )
				c = generatePerson(infected, true);
			
			contactsList.add(c);
		}
		
		
		infected.nearestContacts = contactsList.toArray(new AgentPerson[contactsList.size()]);
	}	

	public int contactsNumber(AgentPerson person)
	{
		// TODO
		return Random.getUniform(1, 10);
	}

	public AgentPerson generatePerson(AgentPerson source, boolean startObservation)
	{
		AgentPerson person = new AgentPerson();
	    person.init(context);

		double availablePopulation = currentHealthy + currentUnsusceptible + totallyRecovered; 
		double pHealthy   = currentHealthy/availablePopulation;
		double random = Random.getUniform();
		
		if( random < pHealthy )
			person.state = AgentPerson.HEALTHY;
		else
			person.state = AgentPerson.UNSUSCEPTIBLE;	// RECOVERED person we also consider here as UNSUSCEPTIBLE
														// otherwise recovered will be processed twice
		
		person.age    = context.modelParameters.ageDistribution.generateAge(); 
		person.isMale = (5 <= Random.getUniform(1, 10));
	    
	    person.diseasePath   = null; 
	    person.illnessDay    = -1;
	    
	    person.sourceId      = source == null ? 0 : source.id;
	    person.sourceType    = source == null ? AgentPerson.UNKNOWN : AgentPerson.CONTACT;
	    person.infectedContacts = new  IntArrayList(1);
	    if( source != null )
	    	person.infectedContacts.add(source.id);
	    
	    person.nearestContacts  = null;
	    person.selfIsolation   	= 0;
	    person.isTested      	= false;
	    person.isDetected    	= false;
	    
	    if( startObservation )
	    	context.observedPopulation.startObservation(person);

		return person;
	}

	public void process(AgentPerson person, byte previousState)
	{
		if( person.state == previousState )
			return;
		
		switch( person.state )
		{
			case AgentPerson.INCUBATION:	dailyInfected++;  break;
			case AgentPerson.RECOVERED:	    dailyRecovered++; break;
			case AgentPerson.DEAD      :    dailyDead++;	  break;	
		}			
	}

	public void doStep()
	{
		context.statCounter.add("dailyInfected",  dailyInfected);
		context.statCounter.add("dailyRecovered", dailyRecovered);
		context.statCounter.add("dailyDead",      dailyDead);

		context.statCounter.add("totallyInfected",  totallyInfected  += dailyInfected);
		context.statCounter.add("totallyRecovered", totallyRecovered += dailyRecovered);
		context.statCounter.add("totallyDead",      totallyDead      += dailyDead);
		
		// reset for new day
		dailyInfected = dailyRecovered = dailyDead = 0;
	}
}
