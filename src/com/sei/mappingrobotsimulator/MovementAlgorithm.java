package com.sei.mappingrobotsimulator;

import com.sei.mappingrobotsimulator.mapping.MapMovement;

/**
 * Created by Kevin on 12/1/15.
 */
public interface MovementAlgorithm {

    public MapMovement determineNextMove(Robot r);
}
