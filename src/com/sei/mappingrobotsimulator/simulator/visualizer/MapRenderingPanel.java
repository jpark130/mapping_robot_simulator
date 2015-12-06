package com.sei.mappingrobotsimulator.simulator.visualizer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kevin on 12/1/15.
 */
public class MapRenderingPanel extends JPanel {
    private int width;
    private int height;

    public  MapRenderingPanel(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        setOpaque(false);
        this.setSize(width, height);
        this.setBackground(new Color(10,120,120));

        g.drawString("ROBOT STUFF", 20, 20);
        g.drawRect(100, 100, 100, 100);
    }

}
