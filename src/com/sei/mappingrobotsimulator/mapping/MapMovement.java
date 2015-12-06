package com.sei.mappingrobotsimulator.mapping;

/**
 * Created by Kevin on 12/6/15.
 */
public class MapMovement {
    private MovementDirection direction;
    private int distance;

    public MapMovement(MovementDirection direction, int distance){
        this.direction = direction;
        this.distance = distance;
    }

    public MovementDirection getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }
}
