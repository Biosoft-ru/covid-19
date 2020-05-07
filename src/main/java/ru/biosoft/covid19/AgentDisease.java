package ru.biosoft.covid19;

import java.util.ArrayList;

import ru.biosoft.covid19.AgentPerson; 

public class AgentDisease extends Agent
{
	public static final double P_ASYMPTOMATIC = 0.5;
			
	public static final byte SYMPTOMS_DELAY = 3; 
	public static final double[] RELATIVE_INFECTVENESS = {0.2, 0.12, 0.27, // incubation period 
	                                                      0.27, 0.07, 0.05, 0.04, 0.03, 0.02, 0.02, 
	                                                      0.01, 0.01, 0.01, 0.01, 0.01};

	public static final double P_INFECT_NEAREST_CONTACT = 1.5;
	
	
	/** The person infects others in the population. */
	public void infect(AgentPerson source)
	{
		if( source.state == AgentPerson.ILLNESS || 
		   (source.state == AgentPerson.INCUBATION && source.illnessDay + SYMPTOMS_DELAY >= source.incubationLength) )
		{
			infectContacts(source);
//			infectTotalPopulation(person);
		}
	}

	protected void infectContacts(AgentPerson source)
	{
		if( source.nearestContacts == null )
			return;
		
		for(AgentPerson contact : source.nearestContacts )
		{
			if( contact.canBeInfected() )
				probablyInfectContact(contact, source);
		}
	}

	protected void probablyInfectContact(AgentPerson contact, AgentPerson source)
	{
		int shift = source.incubationLength - SYMPTOMS_DELAY;
		if( Random.getUniform() < P_INFECT_NEAREST_CONTACT * RELATIVE_INFECTVENESS[source.illnessDay-shift] )
			wasInfected(contact, source);
	}
		
	protected void wasInfected(AgentPerson contact, AgentPerson source)
	{
		if( !context.observedPopulation.persons.containsKey(contact.id) )
		{
//			System.out.print("\r\n!!! (" + source.id + "->" +  contact.id + ") - ");
			context.observedPopulation.persons.put(contact.id, contact);
			
			int a = context.totalPopulation.totallyRecovered;
			
/*			
			for(int i=0; i<source.nearestContacts.length; i++)
			{
				AgentPerson p = source.nearestContacts[i];
				System.out.print(p.id + ":" + p.state + "; ");
			}
*/			
		}

		contact.sourceId   = source.id;
		contact.sourceType = AgentPerson.CONTACT;
		
		generateDiseasePath(contact, 1);
		contact.illnessDay = -1;	// will be illed on next day
		contact.state = AgentPerson.INCUBATION;
		
		context.totalPopulation.generateContacts(contact, source);
		
//System.out.print(" (" + source.id + "->" +  contact.id + ") ");		
	}
		
	protected void generateDiseasePath(AgentPerson person, int minIncubationPeriod)
	{
		ArrayList<Byte> path = new ArrayList<>();
		
		person.incubationLength = (byte) Math.max(Math.round(Random.gamma(5.1, 0.86)), minIncubationPeriod);
		Byte incubation = new Byte(AgentPerson.SYMPTOMS_INCUBATION);
		for(int i=0; i<person.incubationLength; i++)
		{
			path.add(incubation);
		}

		person.diseasePath = new byte[path.size()];
		for(int i=0; i<path.size(); i++)
			person.diseasePath[i] = path.get(i).byteValue();
	}	
	
}
