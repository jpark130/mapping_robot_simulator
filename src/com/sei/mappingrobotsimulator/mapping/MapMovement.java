package com.sei.mappingrobotsimulator.mapping;

/**
 * Created by Kevin on 12/6/15.
 */
public class MapMovement {
    private MovementDirection direction;
    private int distance;
    private boolean shouldRobotDoc;

    /**
     * Defines a movement for the robot
     *
     * @param direction the direction of the movement
     * @param distance the distance to move in that direction
     * @param shouldRobotDoc a boolean representing whether the robot should stop if it reaches its charging station
     */
    public MapMovement(MovementDirection direction, int distance, boolean shouldRobotDoc){
        this.direction = direction;
        this.distance = distance;
        this.shouldRobotDoc = shouldRobotDoc;
    }

    public MovementDirection getDirection() {
        return direction;
    }

    public int getDistance() {
        return distance;
    }

    public boolean getShouldRobotDoc() {
        return shouldRobotDoc;
    }
}
