package ru.biosoft.covid19;

public class AgentHealthcareSystem 
{
	byte mildSymptomsPolicy = MILD_SYMPTOMS_HOSPITALISE;   
	public static final byte MILD_SYMPTOMS_HOSPITALISE = 1;
	public static final byte MILD_SYMPTOMS_HOME        = 2;
	
	int availableBeds = TOTAL_BEDS;
	public static int TOTAL_BEDS    = 1500;
	public static int RESERVED_BEDS = 100;
	
	
	public void process(AgentPerson person)
	{
		// stub
		if( person.illnessDay == person.diseasePath.length-1 )
			person.state= AgentPerson.RECOVERED; 
		
		// no changes in health state
		if( person.symptoms == person.diseasePath[person.illnessDay] )
			return; 

		/*
		if( person.symptoms == AgentPerson.MILD )
		{
			test(person);
			if( mildSymptomsPolicy == MILD_SYMPTOMS_HOSPITALISE && availableBeds > RESERVED_BEDS )
				hospitlise(person);
			else
				isolate(person);
		}
		*/
	}
}
