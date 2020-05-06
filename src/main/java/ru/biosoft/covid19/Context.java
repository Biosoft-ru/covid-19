package ru.biosoft.covid19;

public class Context 
{
	static AgentImport            	arrived            = new AgentImport();
	static AgentDisease            	disease            = new AgentDisease();
	static AgentHealthcareSystem   	healthcareSystem   = new AgentHealthcareSystem();
	static AgentObservedPopulation 	observedPopulation;
	static AgentTotalPopulation    	totalPopulation;
	static AgentStatCounter        	statCounter        = new AgentStatCounter(); 		  	
	static AgentPlot			   	plot               = new AgentPlot();
	
	static Scheduler 			   	scheduler          = new Scheduler();
	static ModelParameters			modelParameters    = new ModelParameters();
	static RealData					realData           = new RealData();
	
	public void init()
	{
		Context.totalPopulation =  new AgentTotalPopulation();
		totalPopulation.init(this);
		
		Context.observedPopulation = new AgentObservedPopulation();
		observedPopulation.init(this);
	}
}
