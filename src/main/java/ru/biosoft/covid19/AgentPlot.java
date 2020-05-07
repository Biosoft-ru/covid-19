package ru.biosoft.covid19;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class AgentPlot extends Agent
{
	XYSeriesCollection collection = new XYSeriesCollection();
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    JFreeChart chart;
    
    static BasicStroke STROKE_DAHED = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[] {7, 3}, 0);
    
    public ChartPanel generatePlot()
    {
        // add real data
        smoothedRealData("infected");
		collection.addSeries(new XYSeries("infected-sim"));
        
       
        chart = ChartFactory.createXYLineChart("", "Time", "", collection, PlotOrientation.VERTICAL, 
        		true, // legend
                true, // tool tips
                false // URLs
        );

        chart.getXYPlot().setRenderer(renderer);
        chart.getXYPlot().setBackgroundPaint(Color.white);
        chart.setBackgroundPaint(Color.white);

//        LogAxis logAxis = new LogAxis("Persons per day");
//        logAxis.setBase(10);
//        logAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());        
//        logAxis.setRange(1, 1000);
//        chart.getXYPlot().setRangeAxis(logAxis);        
        
        configRenderer("infected",        true,  false, Color.red);
        configRenderer("infected-smooth", false, true, Color.red, STROKE_DAHED);
        configRenderer("infected-sim",    true, true, Color.blue);
        
        return new ChartPanel(chart);
    }

    protected void smoothedRealData(String name)
    {
    	XYSeries real     = context.realData.getSeries(name);
    	XYSeries smoothed = RealData.smooth(real, 2);
    	
    	collection.addSeries(real);
    	collection.addSeries(smoothed);
    }

    protected void configRenderer(String series, boolean shapeVisible, boolean lineVisible, Color color)
    {
    	configRenderer(series, shapeVisible, lineVisible, color, null);
    }
    
    protected void configRenderer(String series, boolean shapeVisible, boolean lineVisible, Color color, Stroke stroke)
    {
    	int idx = collection.getSeriesIndex(series);
    	renderer.setSeriesShapesVisible(idx, shapeVisible);
    	renderer.setSeriesLinesVisible(idx, lineVisible);
    	renderer.setSeriesPaint(idx, color);
    	if( stroke != null )
    		renderer.setSeriesStroke(idx, stroke);
    }
 
    public void doStep()
    {
    	collection.getSeries("infected-sim").add( context.scheduler.currentTime, 
    											  context.statCounter.dailyMap.get("dailyInfected").doubleValue(), true );
    	
    	chart.fireChartChanged();
    }
}
