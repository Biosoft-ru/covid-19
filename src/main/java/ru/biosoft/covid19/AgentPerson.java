package ru.biosoft.covid19;

import cern.colt.list.IntArrayList;

public class AgentPerson extends Agent
{
	byte age;
    boolean isMale;
    
    byte state;
    public static final byte UNSUSCEPTIBLE = -1;
    public static final byte HEALTHY       = 0;
    public static final byte INCUBATION    = 1;
    public static final byte ILLNESS       = 2;
    public static final byte HOSPITALIZED  = 3;
    public static final byte IN_ICU        = 4; 
    public static final byte RECOVERED     = 5;
    public static final byte DEAD          = 6;

    byte symptoms; 
    public static final byte SYMPTOMS_INCUBATION   = 1;
    public static final byte SYMPTOMS_ASYMPTOMATIC = 2;
    public static final byte SYMPTOMS_MILD 		   = 3;
    public static final byte SYMPTOMS_SEVERE       = 4;
    public static final byte SYMPTOMS_CRITICAL     = 5;
    
    byte[] diseasePath; 	// array [day, state]
    byte illnessDay;		// started from 1
    byte incubationLength;
    
    int id;			// personId
    int sourceId;	// who infected
    IntArrayList infectedContacts;  
    
    byte sourceType;
    public static final byte ARRIVED     = 1;
    public static final byte CONTACT     = 2;
    public static final byte UNKNOWN     = 3;
    
    AgentPerson[] nearestContacts;		
    
    byte selfIsolation;
    public static final byte WORK           = 2;	// can visit work, nearest marketplaces
    public static final byte HOME           = 3;	// works from home, nearest marketplaces 
    public static final byte SELF_ISOLATION = 4;	// stays at home
    public static final byte HOSPITAL       = 5;	 

    boolean isTested;
    boolean isDetected;
    boolean isIsolated;

    public boolean canBeInfected()
    {
		return state == AgentPerson.HEALTHY; 
    }

    public boolean canInfect()
    {
    	return !isIsolated && (state == INCUBATION || state == ILLNESS);
    }
    
    public void doStep()
    {
    	if( state==UNSUSCEPTIBLE || state==HEALTHY || state==RECOVERED || state == DEAD || illnessDay >= diseasePath.length-1 )
    		return;
    	
    	illnessDay++;

    	byte previousState = illnessDay == 0 ? HEALTHY : state; 
  	  	symptoms = diseasePath[illnessDay];

    	context.disease.infect(this);				// this person infects others
    	context.healthcareSystem.process(this);		
    	context.totalPopulation.process(this, previousState);
    }
    
    public String toString()
    {
    	return "\r\nPerson id=" + id + ", state=" + state;	
    }
}
