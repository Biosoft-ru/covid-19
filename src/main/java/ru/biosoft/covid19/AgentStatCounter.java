package ru.biosoft.covid19;

import java.util.ArrayList;
import java.util.HashMap;

public class AgentStatCounter 
{
	protected ArrayList<HashMap> dayValues = new ArrayList<>(); 
	protected HashMap<String, Double> dailyMap = new HashMap<>();

	public void add(String name, double value)
	{
		dailyMap.put(name, new Double(value));
	}
	
	public void doStep()
	{
		dayValues.add(dailyMap);
		dailyMap = new HashMap<>();
	}
}
