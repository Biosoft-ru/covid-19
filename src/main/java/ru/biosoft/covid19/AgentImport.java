package ru.biosoft.covid19;

public class AgentImport extends Agent
{

	public void doStep()
	{
		arriveObserved();
//		arriveHidden();
	}
	
	public void arriveObserved()
	{
		if( context.scheduler.currentTime+AgentDisease.SYMPTOMS_DELAY >= context.realData.data.getRowCount() )
			return;
		
		int idx = context.realData.data.getColumnIndex("imported");
		int arrived = context.realData.data.getValue(context.scheduler.currentTime+AgentDisease.SYMPTOMS_DELAY, idx).intValue();
		
		AgentPerson person;
		while( arrived-- > 0 )
		{
			person = context.totalPopulation.generatePerson(null, false);
			context.disease.generateDiseasePath(person, AgentDisease.SYMPTOMS_DELAY); 

			person.state         = AgentPerson.INCUBATION;
			person.illnessDay    = (byte) (person.incubationLength - AgentDisease.SYMPTOMS_DELAY);
			
		    person.sourceId      = -1;
		    person.sourceType    = AgentPerson.ARRIVED;

		    person.selfIsolation = 0;

		    person.isTested      = false;
		    person.isDetected    = false;

		    context.observedPopulation.startObservation(person);

		    context.totalPopulation.generateContacts(person, null);
			context.totalPopulation.process(person, AgentPerson.HEALTHY);
			context.totalPopulation.totallyArrived++;
		}
	}
}
