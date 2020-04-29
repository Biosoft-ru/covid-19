package ru.biosoft.covid19;

public class ModelParameters 
{
	public int  getEndTime()					{ return Context.scheduler.endTime; 	}
	public void setEndTime(int endTime)			{ Context.scheduler.endTime = endTime; 	}
	
	public String getRealDataName()				{ return Context.realData.resource;		}
	public void setRealDataName(String name)	{ Context.realData.resource = name;		}

	protected DistributionAge ageDistribution = new DistributionAge();
	public DistributionAge getAgeDistribution()			{ return ageDistribution;		}
	public void setAgeDistribution(DistributionAge d)	{ ageDistribution = d;		}
	
}
