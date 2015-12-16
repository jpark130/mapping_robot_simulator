package com.sei.mappingrobotsimulator;

import com.sei.mappingrobotsimulator.decisionmakinginterfaces.BatteryDecay;
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
    private BatteryDecay batteryDecay;
    private boolean robotShouldDoc;

    private ArrayList<ProbabilisticMapPosition> possibleLocations;
    private MapMovement attemptedMove;

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
    public Robot(float batteryLife, BatteryDecay batteryDecay, MovementAlgorithm algo,
                 RotationalUncertainty rotationalMapping, ProbabilisticMap initialEnvironmentKnowledge,
                 MapPosition startingLocation){
        this.batteryLife = batteryLife;
        this.batteryDecay = batteryDecay;
        this.algorithm = algo;
        this.robotShouldDoc = false;
        this.rotationalMapping = rotationalMapping;
        this.mapper = new ProbabilisticMap(initialEnvironmentKnowledge);

        this.possibleLocations = new ArrayList<ProbabilisticMapPosition>();
        possibleLocations.add(new ProbabilisticMapPosition(startingLocation, 0, 100.0f));
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
        attemptedMove = move;
        return move;
    }

    public void resultingPhysicalMovement(MapMovement m, int squareIdentifier){
        //process the movement that actually made
        ArrayList<ProbabilisticMapMovement> movementPossibilities = rotationalMapping.getMappingForMovement(m);
        ArrayList<ProbabilisticMapPosition> updatedPossibleLocations = new ArrayList<ProbabilisticMapPosition>();
        for(ProbabilisticMapMovement possibleMovement: movementPossibilities){
            mapper.updateMapWithMovement(attemptedMove, possibleMovement, possibleLocations, squareIdentifier);
            for(ProbabilisticMapPosition oldLocation: possibleLocations) {
                MapPosition newPosition = MapPosition.calculateTranslatedPosition(oldLocation, possibleMovement.getDirection(), possibleMovement.getDistance());
                ProbabilisticMapPosition newLocation = new ProbabilisticMapPosition(newPosition, squareIdentifier, oldLocation.getCertainty()*possibleMovement.getCertainty());
                updatedPossibleLocations.add(newLocation);
            }
        }
        possibleLocations = updatedPossibleLocations;
        batteryLife = batteryDecay.calculateReducedBatteryStrength(batteryLife, m);
    }

    public boolean robotSeekingDoc(){
        return robotShouldDoc;
    }

}
