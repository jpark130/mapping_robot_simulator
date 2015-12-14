package com.sei.mappingrobotsimulator.decisionmakinginterfaces;

import com.sei.mappingrobotsimulator.mapping.MapMovement;
import com.sei.mappingrobotsimulator.mapping.ProbabilisticMapMovement;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/14/15.
 */
public class RotationalUncertaintyStub implements RotationalUncertainty {

    @Override
    public ArrayList<ProbabilisticMapMovement> getMappingForMovement(MapMovement movement) {
        ArrayList<ProbabilisticMapMovement> allMoves  = new ArrayList<ProbabilisticMapMovement>();
        allMoves.add(new ProbabilisticMapMovement(movement.getDirection(), movement.getDistance(), 1.0f));
        return allMoves;
    }
}
