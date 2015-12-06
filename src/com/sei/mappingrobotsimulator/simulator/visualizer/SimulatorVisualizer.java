package com.sei.mappingrobotsimulator.simulator.visualizer;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Kevin on 12/1/15.
 */
public class SimulatorVisualizer {


    public static void main(String[] args){
        JFrame frame = new MappingFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
