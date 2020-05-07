 package ru.biosoft.covid19;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.io.CSV;
import org.jfree.data.xy.XYSeries;

public class RealData extends Agent
{
	DefaultCategoryDataset data;
	
	public void init(Context context) 
	{
		super.init(context);
		data = readDataset( context.modelParameters.getRealDataName() );
	}
	
	public XYSeries getSeries(String yName)
	{
		if( data == null )
			throw new NullPointerException("Dataset is not initialised (read)");
		
		return getSeries(data, yName);
	}
	
	// /////////////////////////////////////////////////////////////////////////
	// Static utility methods
	// 
	
	public  DefaultCategoryDataset readDataset(String resource) 
	{
		try
		{
			InputStream is = RealData.class.getResourceAsStream(".\\" + resource);
			CSV csv = new CSV();
		
			return (DefaultCategoryDataset)csv.readCategoryDataset(new InputStreamReader(is));
		}
		catch(Exception e)
		{
			System.out.println("Can not read file " + resource + ", error: " + e.getMessage());
		}
		
		return null;
	}

	public static XYSeries getSeries(DefaultCategoryDataset data, String yName)
	{
		int yIndex = data.getColumnIndex(yName);
		if( yIndex < 0 ) throw new IllegalArgumentException("Can not find column " + yName + " in dataset.");
		
		XYSeries series = new XYSeries(yName);
		for(int i=0; i<data.getRowCount(); i++)
		{
			series.add(i, data.getValue(i, yIndex));
		}
		
		return series;
	}
	
	public static XYSeries smooth(XYSeries series, int window)
	{
		XYSeries smooth = new XYSeries(series.getKey() + "-smooth");
		
		double sum;
		double n;
		for(int i=0; i<series.getItemCount(); i++)
		{
			sum = 0;
			n = 1;
			
			for(int j=Math.max(0, i-window); j<Math.min(i+window, series.getItemCount()); j++)
			{
				sum += series.getY(j).doubleValue(); 
				n++;
			}

			smooth.add(i, sum/n);
		}
		
		return smooth;
	}
	
}

