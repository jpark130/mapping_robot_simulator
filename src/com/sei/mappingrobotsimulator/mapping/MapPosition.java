package com.sei.mappingrobotsimulator.mapping;

/**
 * Created by Kevin on 12/6/15.
 */
public class MapPosition {
    private int xCord;
    private int yCord;

    public MapPosition(MapPosition m){
        this.xCord = m.getxCord();
        this.yCord = m.getyCord();
    }

    public MapPosition(int xCord, int yCord){
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }
}
