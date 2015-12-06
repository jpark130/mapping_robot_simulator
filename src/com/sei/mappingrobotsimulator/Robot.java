package com.sei.mappingrobotsimulator;

import com.sei.mappingrobotsimulator.mapping.Map;
import com.sei.mappingrobotsimulator.mapping.MapMovement;

/**
 * Created by Kevin on 12/1/15.
 */
public class Robot {
    private float batteryLife;
    private MovementAlgorithm algorithm;
    //some sort of inner representation of maps and current position

    //Set this in order to tell the simulator whether to stop on arriving at a beacon
    private boolean robotShouldDoc;

    public boolean hasBatteryLife(){
        return (batteryLife > 0.0f);
    }

    public Map getConstructedMap(){
        //Build final map prediction based on
        return null;
    }

    public MapMovement getMoveToAttempt() {
        //decide how the movement is going
        return null;
    }

    public void resultingPhysicalMovement(MapMovement m){
        //process the movement that actually made
    }

    public boolean robotSeekingDoc(){
        return robotShouldDoc;
    }

}
