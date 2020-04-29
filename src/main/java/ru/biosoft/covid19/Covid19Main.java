package ru.biosoft.covid19;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.developmentontheedge.beans.swing.PropertyInspector;

public class Covid19Main 
{
    public static void main(String[] args) 
	{
    	Covid19Main instance = new Covid19Main();
    	instance.run();
	}

    private JButton startButton = new JButton("Start");
    private PropertyInspector inspector = new PropertyInspector();
    private JScrollPane graphsPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    public void run() 
	{
        JFrame frame = new JFrame("Covid-19 agent based model");

        JPanel content = new JPanel( new GridBagLayout() );
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(content);
        
        content.add(Context.plot.generatePlot(),
        		new GridBagConstraints(0, 0, 3, 5, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 0), 0, 0));

        content.add(new JLabel("Main model parameters"),
        		new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));

        content.add(inspector,
        		new GridBagConstraints(3, 1, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 0), 0, 0));

        content.add(startButton,
        		new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
       
        frame.setSize(1500, 900);
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                System.exit(0);
            }
        });
        frame.setVisible(true);

        inspector.explore(Context.modelParameters);
        
        startButton.addActionListener(e -> 
        {
            Context.scheduler.run();
        });
        
        Context.scheduler.run();

	}

}
