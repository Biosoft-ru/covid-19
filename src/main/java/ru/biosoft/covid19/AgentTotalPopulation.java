package ru.biosoft.covid19;

import java.util.ArrayList;

import cern.colt.list.IntArrayList;

public class AgentTotalPopulation
{
	protected Context context;
	
	public int currentPopulationSize;
	
	public int dailyInfected;
	public int dailyRecovered;
	public int dailyDead;
	
	public int totallyInfected;
	public int totallyRecovered;
	public int totallyDead;

	protected void init(Context context)
	{
		this.context = context;
		currentPopulationSize = context.modelParameters.getPopulationSize();
	}
	
	protected void generateContacts(AgentPerson contact, AgentPerson source)
	{
		int contactsNumber = contactsNumber(contact);
		if( contactsNumber == 0 )
			return;

		// contact and infected person share some common contacts 
		ArrayList<AgentPerson> contactsList = new ArrayList<AgentPerson>();
		if( source != null )
		{
			for(AgentPerson c : source.contacts )
			{
				if( Random.getUniform() < Context.modelParameters.getPContactReuse() )
				{
					
					contactsList.add(c);
					c.sources.add(contact.id);
					contactsNumber--;
				}
			}
		}

		// TO DO
		// currently we neglect contacts in observed population
		
		// get contacts from total population
		while(contactsNumber-- > 0)
			contactsList.add(generatePerson(contact));
			
		contact.contacts = contactsList.toArray(new AgentPerson[contactsList.size()]);
	}	

	public int contactsNumber(AgentPerson person)
	{
		// TODO
		return Random.getUniform(1, 10);
	}

	public AgentPerson generatePerson(AgentPerson source)
	{
		AgentPerson person = new AgentPerson();
		Context.observedPopulation.addPerson(person);

		person.age    = Context.modelParameters.ageDistribution.generateAge(); 
		person.isMale = (5 <= Random.getUniform(1, 10));
	    
	    person.state         = AgentPerson.HEALTHY;
	    person.diseasePath   = null; 
	    person.illnessDay    = -1;
	    
	    person.sourceId      = source == null ? 0 : source.id;
	    person.sourceType    = source == null ? AgentPerson.UNKNOWN : AgentPerson.CONTACT;
	    person.sources       = new  IntArrayList();
	    if( source != null )
	    	person.sources.add(source.id);
	    
	    person.contacts      = null;
	    person.selfIsolation = 0;
	    person.isTested      = false;
	    person.isDetected    = false;
	    
	    person.hasImmunity   = Random.getUniform() < (double)totallyRecovered/currentPopulationSize;
	    person.isSuspectable = Random.getUniform() < Context.modelParameters.getPIsSuspectable();
	    
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
		Context.statCounter.add("dailyInfected",  dailyInfected);
		Context.statCounter.add("dailyRecovered", dailyRecovered);
		Context.statCounter.add("dailyDead",      dailyDead);

		Context.statCounter.add("totallyInfected",  totallyInfected  += dailyInfected);
		Context.statCounter.add("totallyRecovered", totallyRecovered += dailyRecovered);
		Context.statCounter.add("totallyDead",      totallyDead      += dailyDead);
		
		// reset for new day
		dailyInfected = dailyRecovered = dailyDead = 0;
	}
}
