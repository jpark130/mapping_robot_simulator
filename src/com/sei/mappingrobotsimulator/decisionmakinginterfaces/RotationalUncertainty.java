package com.sei.mappingrobotsimulator.decisionmakinginterfaces;

import com.sei.mappingrobotsimulator.mapping.MapMovement;
import com.sei.mappingrobotsimulator.mapping.ProbabilisticMapMovement;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/14/15.
 */
public interface RotationalUncertainty {

    /**
     * This function provides the probabilities that the robot's attempt to move in a certain direction results in it actually moving
     * any number of other directions
     *
     * @param movement the movement the robot would like to perform
     * @return an array of the moves the robot might have made, with the appropriate probabilities associated with each move
     */
    public ArrayList<ProbabilisticMapMovement> getMappingForMovement(MapMovement movement);

}
