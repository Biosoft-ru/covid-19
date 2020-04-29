package ru.biosoft.covid19;

import cern.jet.random.Uniform;

public class Random 
{
	public static double getUniform()
	{
		return Uniform.staticNextDouble();
	}
	
	public static int getUniform(int from, int to)
	{
		return Uniform.staticNextIntFromTo(from,  to);
	}

	static double gamma(double alpha, double lambda)
	{
		return cern.jet.random.Gamma.staticNextDouble(alpha,  lambda);
	}

}
