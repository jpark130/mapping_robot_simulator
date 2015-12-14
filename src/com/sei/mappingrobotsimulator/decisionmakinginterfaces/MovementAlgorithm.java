package com.sei.mappingrobotsimulator.decisionmakinginterfaces;

import com.sei.mappingrobotsimulator.Robot;
import com.sei.mappingrobotsimulator.mapping.MapMovement;

/**
 * Created by Kevin on 12/1/15.
 */
public interface MovementAlgorithm {
    /**
     * This function should return the next move the robot will make based solely on its internal knowledge of the world
     *
     * @param r the robot to determine a move for
     * @return the movement the robot should perform given its internal world state
     */
    public MapMovement determineNextMove(Robot r);
}
