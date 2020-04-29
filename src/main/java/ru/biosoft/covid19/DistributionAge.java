package ru.biosoft.covid19;

/**
 * Java bean to specify age distribution.
 */
public class DistributionAge 
{
	private double p_0_9;
	public double getP_0_9()			{ return p_0_9; 		} 
	public void   setP_0_9(double v)	{ dist[0] = p_0_9 = v; 	}

	private double p_10_19;
	public double getP_10_19()			{ return p_10_19; 		} 
	public void   setP_10_19(double v)	{ dist[1] = p_10_19 = v;}
	
	private double p_20_29;
	public double getP_20_29()			{ return p_20_29; 		} 
	public void   setP_20_29(double v)	{ dist[2] = p_20_29 = v;}

	private double p_30_39;
	public double getP_30_39()			{ return p_30_39; 		} 
	public void   setP_30_39(double v)	{ dist[3] = p_30_39 = v;}

	private double p_40_49;
	public double getP_40_49()			{ return p_40_49; 		} 
	public void   setP_40_49(double v)	{ dist[4] = p_40_49 = v;}

	private double p_50_59;
	public double getP_50_59()			{ return p_50_59; 		} 
	public void   setP_50_59(double v)	{ dist[5] = p_50_59 = v;}

	private double p_60_69;
	public double getP_60_69()			{ return p_60_69; 		} 
	public void   setP_60_69(double v)	{ dist[6] = p_60_69 = v;}

	private double p_70_79;
	public double getP_70_79()			{ return p_70_79; 		} 
	public void   setP_70_79(double v)	{ dist[7] = p_70_79 = v;}

	private double p_80_;
	public double getP_80_()			{ return p_80_; 		} 
	public void   setP_80_(double v)	{ dist[8] = p_80_ = v; 	}
	
	double[] dist;
	public double[] distribution()		{ return dist;      } 
	
	
	/** Init age distribution for Novosibirsk by default. */
	public DistributionAge()
	{
		p_0_9	= 0.13;
		p_10_19	= 0.10;
		p_20_29	= 0.12;
		p_30_39	= 0.17;
		p_40_49	= 0.14;
		p_50_59	= 0.13;
		p_60_69	= 0.12;
		p_70_79	= 0.05;
		p_80_	= 0.04;

		dist = new  double[]{p_0_9, p_10_19, p_20_29, p_30_39, p_40_49, p_50_59, p_60_69, p_70_79, p_80_};
	}

	public byte generateAge()
	{
		double p = Random.getUniform();
		byte age = 5;
		
		for( int i=0; i < dist.length; i++)
		{
			if( p < dist[i] )
				return age;

			p -= dist[i];	age += 10;
		}
		
		return 85;
	}
	
}
