package com.sei.mappingrobotsimulator.decisionmakinginterfaces;

import com.sei.mappingrobotsimulator.mapping.MapMovement;

/**
 * Created by Kevin on 12/16/15.
 */
public interface BatteryDecay {

    public float calculateReducedBatteryStrength(float currentBatteryStrength, MapMovement m);
}
