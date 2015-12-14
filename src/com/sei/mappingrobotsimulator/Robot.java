package com.sei.mappingrobotsimulator;

import com.sei.mappingrobotsimulator.decisionmakinginterfaces.MovementAlgorithm;
import com.sei.mappingrobotsimulator.decisionmakinginterfaces.RotationalUncertainty;
import com.sei.mappingrobotsimulator.mapping.ProbabilisticMapMovement;
import com.sei.mappingrobotsimulator.mapping.*;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/1/15.
 */
public class Robot {
    //Classes that the robot lies on for decision making
    private MovementAlgorithm algorithm;
    private ProbabilisticMap mapper;
    private RotationalUncertainty rotationalMapping;

    //properties
    private float batteryLife;
    private float batteryDecay;
    private boolean robotShouldDoc;

    private MapPosition currentLocation;

    /**
     * Create a robot with batterLife information, a movement algorithm, and initial knowledge of the environment
     * Note that this function should be passed a Map that is accurately sized and contains a starting position for the robot
     *
     *
     * @param batteryLife
     * @param batteryDecay
     * @param algo
     * @param initialEnvironmentKnowledge
     */
    public Robot(float batteryLife, float batteryDecay, MovementAlgorithm algo,
                 RotationalUncertainty rotationalMapping, ProbabilisticMap initialEnvironmentKnowledge,
                 MapPosition startingLocation){
        if(batteryDecay <= 0){
            throw new RuntimeException("Battery Decay must be greater than 0");
        }

        this.batteryLife = batteryLife;
        this.batteryDecay = batteryDecay;
        this.algorithm = algo;
        this.robotShouldDoc = false;
        this.mapper = new ProbabilisticMap(initialEnvironmentKnowledge);
        this.currentLocation = startingLocation;
        this.rotationalMapping = rotationalMapping;
    }



    public boolean hasBatteryLife(){
        return (batteryLife > 0.0f);
    }

    public Map getConstructedMap(){
        return mapper.getBestGuessMap();
    }

    public MapMovement getMoveToAttempt() {
        //decide how the movement is going
        MapMovement move = algorithm.determineNextMove(this);
        if(move != null) {
            robotShouldDoc = move.getShouldRobotDoc();
        }else{
            robotShouldDoc = false;
        }
        return move;
    }

    public void resultingPhysicalMovement(MapMovement m, int squareIdentifier){
        //process the movement that actually made
        ArrayList<ProbabilisticMapMovement> movementPossibilities = rotationalMapping.getMappingForMovement(m);
        for(ProbabilisticMapMovement possibleMovement: movementPossibilities){
            MapPosition possiblePosition = currentLocation.calculateTranslatedPosition(currentLocation, possibleMovement.getDirection(), m.getDistance());
            ProbabilisticMapPosition probabilisticMapPosition = new ProbabilisticMapPosition(possiblePosition, squareIdentifier, possibleMovement.getCertainty());
            mapper.addNewInformation(probabilisticMapPosition);
        }
        batteryLife -= batteryDecay;
    }

    public boolean robotSeekingDoc(){
        return robotShouldDoc;
    }

}
