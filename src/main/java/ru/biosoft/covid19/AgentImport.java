package ru.biosoft.covid19;

public class AgentImport 
{

	public void doStep()
	{
		arriveObserved();
//		arriveHidden();
	}
	
	public void arriveObserved()
	{
		if( Context.scheduler.currentTime+AgentDisease.SYMPTOMS_DELAY >= Context.realData.data.getRowCount() )
			return;
		
		int idx = Context.realData.data.getColumnIndex("imported");
		int arrived = Context.realData.data.getValue(Context.scheduler.currentTime+AgentDisease.SYMPTOMS_DELAY, idx).intValue();
		
		AgentPerson person;
		while( arrived-- > 0 )
		{
			person = Context.totalPopulation.generatePerson(null, false);
			Context.disease.generateDiseasePath(person, AgentDisease.SYMPTOMS_DELAY); 

			person.state         = AgentPerson.INCUBATION;
			person.illnessDay    = (byte) (person.incubationLength - AgentDisease.SYMPTOMS_DELAY);
			
		    person.sourceId      = -1;
		    person.sourceType    = AgentPerson.ARRIVED;

		    person.selfIsolation = 0;

		    person.isTested      = false;
		    person.isDetected    = false;

		    Context.observedPopulation.startObservation(person);

		    Context.totalPopulation.generateContacts(person, null);
			Context.totalPopulation.process(person, AgentPerson.HEALTHY);
			Context.totalPopulation.totallyArrived++;
		}
	}
}
