package com.sei.mappingrobotsimulator.simulator.visualizer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kevin on 12/5/15.
 */
public class MappingFrame extends JFrame {
    MapRenderingPanel drawingWindow;
    JButton forwardStep;
    JButton backwardStep;
    JLabel timeStepLabel;
    JTextField currentTimeStep;

    public MappingFrame(){
        super("Robot Mapper");
        this.setSize(600, 600);

        drawingWindow = new MapRenderingPanel(500,500);
        drawingWindow.setSize(500,500);
        timeStepLabel = new JLabel("Current Timestep:");
        currentTimeStep = new JTextField();
        forwardStep = new JButton("Step Forward");
        backwardStep = new JButton("Step Backwards");

        handleGridBagLayout();
    }

    private void handleGridBagLayout(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //create the drawing pannel
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 12;
        this.add(drawingWindow, c);

        //create the timestepLabel
        c.gridx = 0;
        c.gridy = 13;
        c.gridwidth = 1;
        this.add(timeStepLabel, c);

        //create the currentTimeStepField
        c.gridx = 0;
        c.gridy = 13;
        c.gridwidth = 3;
        this.add(currentTimeStep, c);


        //create the backward step button
        c.gridx = 0;
        c.gridy = 14;
        c.gridwidth = 2;
        this.add(backwardStep, c);

        //create the forward step button
        c.gridx = 1;
        c.gridy = 14;
        c.gridwidth = 2;
        this.add(forwardStep, c);

    }

}
