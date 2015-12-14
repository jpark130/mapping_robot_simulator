package com.sei.mappingrobotsimulator.decisionmakinginterfaces;

import com.sei.mappingrobotsimulator.Robot;
import com.sei.mappingrobotsimulator.mapping.MapMovement;
import com.sei.mappingrobotsimulator.mapping.MovementDirection;

/**
 * Created by Kevin on 12/13/15.
 */
public class MovementAlgorthimStub implements MovementAlgorithm {
    @Override
    public MapMovement determineNextMove(Robot r) {
        return new MapMovement(MovementDirection.MOVE_UP, 1, false);
    }
}
