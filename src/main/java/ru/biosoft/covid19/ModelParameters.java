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

	private double pContactReuse = 0.5;
	public double getPContactReuse()			{ return pContactReuse;		}
	public void setPContactReuse(double v)		{ pContactReuse = v;		}
	
	private double pIsSuspectable = 0.8;
	public double getPIsSuspectable()			{ return pIsSuspectable;	}
	public void setPIsSuspectable(double v)		{ pIsSuspectable = v;		}
	
	private int populationSize = 1700000;
	public int getPopulationSize()				{ return populationSize;	}
	public void setPopulationSize(int v)		{ populationSize = v;		}
}
