package com.sei.mappingrobotsimulator.mapping;

import com.sei.mappingrobotsimulator.mapping.MovementDirection;

/**
 * Created by Kevin on 12/14/15.
 */
public class ProbabilisticMapMovement extends MapMovement {
    private float certainty;

    /**
     * Extends the MapMovement class to include a certainty of movement
     * Does not allow for specifiyng of robot docing because this is not used for actual movement calcultaions
     *
     * @param direction the direction of the map movement
     * @param distance the distance of the map movement
     * @param certainty the certainty that the movement was in that direction
     */
    public ProbabilisticMapMovement(MovementDirection direction, int distance, float certainty){
        super(direction, distance, false);
        this.certainty = certainty;
    }

    public float getCertainty() {
        return certainty;
    }
}
