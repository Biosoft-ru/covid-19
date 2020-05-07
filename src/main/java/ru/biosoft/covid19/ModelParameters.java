package ru.biosoft.covid19;

public class ModelParameters
{
	private int endTime = 300;
	public int  getEndTime()					{ return endTime; 			}
	public void setEndTime(int endTime)			{ this.endTime = endTime; 	}
	
	private String realDataName = "nsk.csv";
	public String getRealDataName()				{ return realDataName;		}
	public void setRealDataName(String name)	{ realDataName = name;		}

	protected DistributionAge ageDistribution = new DistributionAge();
	public DistributionAge getAgeDistribution()			{ return ageDistribution;		}
	public void setAgeDistribution(DistributionAge d)	{ ageDistribution = d;		}

	private double pContactReuse = 0.5;
	public double getPContactReuse()			{ return pContactReuse;		}
	public void setPContactReuse(double v)		{ pContactReuse = v;		}
	
	private double pIsSusceptible = 0.8;
	public double getPIsSusceptible()			{ return pIsSusceptible;	}
	public void setPIsSusceptible(double v)		{ pIsSusceptible = v;		}
	
	private int populationSize = 1700000;
	public int getPopulationSize()				{ return populationSize;	}
	public void setPopulationSize(int v)		{ populationSize = v;		}
}
