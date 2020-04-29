package ru.biosoft.covid19;

public class Context 
{
	static AgentImport            	arrived            = new AgentImport();
	static AgentDisease            	disease            = new AgentDisease();
	static AgentHealthcareSystem   	healthcareSystem   = new AgentHealthcareSystem();
	static AgentObservedPopulation 	observedPopulation = new AgentObservedPopulation();
	static AgentTotalPopulation    	totalPopulation    = new AgentTotalPopulation();
	static AgentStatCounter        	statCounter        = new AgentStatCounter(); 		  	
	static AgentPlot			   	plot               = new AgentPlot();
	
	static Scheduler 			   	scheduler          = new Scheduler();
	static ModelParameters			modelParameters    = new ModelParameters();
	static RealData					realData           = new RealData();
}
