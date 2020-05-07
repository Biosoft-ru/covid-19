package ru.biosoft.covid19;

public class Context 
{
	AgentImport            	arrived;
	AgentDisease           	disease;
	AgentHealthcareSystem  	healthcareSystem;
	AgentObservedPopulation	observedPopulation;
	AgentTotalPopulation   	totalPopulation;

	RealData				realData;
	AgentStatCounter       	statCounter; 		  	
	AgentPlot			   	plot;
	
	Scheduler 			   	scheduler;

	ModelParameters			modelParameters = new ModelParameters();
	
	public void init()
	{
		scheduler = new Scheduler();
		
		arrived = new AgentImport();							arrived.init(this);
		disease = new AgentDisease();							disease.init(this); 
		healthcareSystem = new AgentHealthcareSystem();		  	healthcareSystem.init(this);

		totalPopulation =  new AgentTotalPopulation();			totalPopulation.init(this);
		observedPopulation = new AgentObservedPopulation();		observedPopulation.init(this);

		realData = new RealData();								realData.init(this);
		statCounter = new AgentStatCounter();			       	statCounter.init(this); 		  	
		plot = new AgentPlot();									plot.init(this);			
	}
}
