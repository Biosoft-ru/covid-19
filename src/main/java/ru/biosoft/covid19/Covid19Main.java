package ru.biosoft.covid19;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import com.developmentontheedge.beans.swing.PropertyInspector;

public class Covid19Main implements Runnable
{
    public static void main(String[] args) 
	{
    	Covid19Main instance = new Covid19Main();
    	instance.run();
	}

    private JButton startButton = new JButton("Start");
    private JButton stopButton  = new JButton("Stop");
    private JLabel  infoLabel   = new JLabel();
    private PropertyInspector inspector = new PropertyInspector();
    
    private JPanel graphsPane = new JPanel();
    private JComponent scrollPane; // = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    private Context context;
    
    public void run() 
	{
    	JFrame frame = new JFrame("Covid-19 agent based model");

        JPanel content = new JPanel( new GridBagLayout() );
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(content);

        graphsPane.setLayout(new BoxLayout(graphsPane, BoxLayout.PAGE_AXIS));
        
//        scrollPane = new JScrollPane(graphsPane, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
scrollPane = graphsPane;

        scrollPane.setMinimumSize(new Dimension(1000, 850));
		scrollPane.setPreferredSize(new Dimension(1000, 850));
		
        content.add(scrollPane,
        		new GridBagConstraints(0, 0, 3, 5, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 0), 0, 0));

        content.add(new JLabel("Main model parameters"),
        		new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));

        content.add(inspector,
        		new GridBagConstraints(3, 1, 2, 1, 0.7, 0.7, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 0), 0, 0));

        content.add(infoLabel,
        		new GridBagConstraints(3, 2, 2, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(5, 5, 0, 0), 0, 0));
        content.add(startButton,
        		new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        content.add(stopButton,
        		new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));

        scrollPane.add(new JLabel("test"));	        
        
        frame.setSize(1500, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        

    	context = new Context();
        inspector.explore(context.modelParameters);
        inspector.setPreferredSize(new Dimension(150, 300));

        startButton.addActionListener(e -> 
        {
	        setEnableStart(false);				

	        context.init();
			graphsPane.add(context.plot.generatePlot());
			graphsPane.validate();
			
        	SwingWorker task = new SwingWorker()
   			{
        		String msg;
        		public String doInBackground() 		
        		{
        			msg = context.scheduler.run(context); 
        			return msg; 
       			}
        		
        		protected void done()				
        		{ 
        			setEnableStart(true);
        			infoLabel.setText(msg);
       			} 
   			};
   			
   			task.execute();  
        });

        stopButton.addActionListener(e -> 
        {
        	context.scheduler.terminate();
        });
        
	}

	protected void setEnableStart(boolean enabled)
	{
		startButton.setEnabled(enabled);
		stopButton.setEnabled(!enabled);
	}

    
}
