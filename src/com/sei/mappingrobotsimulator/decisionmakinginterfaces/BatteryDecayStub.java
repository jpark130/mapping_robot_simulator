package com.sei.mappingrobotsimulator.decisionmakinginterfaces;

import com.sei.mappingrobotsimulator.mapping.MapMovement;

/**
 * Created by Kevin on 12/16/15.
 */
public class BatteryDecayStub implements BatteryDecay {
    private float decayFactor;

    public BatteryDecayStub(float decayFactor){
        this.decayFactor = decayFactor;
    }

    @Override
    public float calculateReducedBatteryStrength(float currentBatteryStrength, MapMovement m) {
        return currentBatteryStrength - ((m.getDistance() * decayFactor) + 0.001f);
    }
}
