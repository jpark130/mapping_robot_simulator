package com.sei.mappingrobotsimulator.simulator;

import com.sei.mappingrobotsimulator.Robot;
import com.sei.mappingrobotsimulator.decisionmakinginterfaces.MovementAlgorthimStub;
import com.sei.mappingrobotsimulator.decisionmakinginterfaces.RotationalUncertaintyStub;
import com.sei.mappingrobotsimulator.mapping.*;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/1/15.
 */
public class Simulator {
    private int currentTimestep;
    private int lastTimestep;
    private MapPosition currentPosition;

    //Provided data
    private Map map;
    private Robot robot;


    public static void main(String[] args) {
        Map fromFile = MapBuilder.loadMapFromFile("src/com/sei/mappingrobotsimulator/mapping/maps_database/bigGrid.txt");

        Robot r = new Robot(100.0f, 5.0f, new MovementAlgorthimStub(), new RotationalUncertaintyStub(),
                  new ProbabilisticMap(fromFile.getMapWidth(), fromFile.getMapHeight()),fromFile.getStartingLocation());
        Simulator sim = new Simulator(fromFile, r);
        float similarity = sim.runSimulation();
    }

    public Simulator(Map map, Robot robot) {
        this.map = map;
        this.robot = robot;
        currentTimestep = 0;
        lastTimestep = 0;
    }

    /**
     * Manages the run loop of the simulator, requesting moves until the robot runs out of battery life
     *
     * @return the percentage that the map developed by the robot matches the actual map
     */
    public float runSimulation(){

        currentPosition = new MapPosition(map.getStartingLocation(), 0);
        Map constructedMap = null;
        float simularity = 0.0f;

        //Perform
        while(robot.hasBatteryLife()){
            if(map.robotHasReturnedToStartingLocation(currentPosition) && robot.robotSeekingDoc()){
                constructedMap = robot.getConstructedMap();
                break;
            }

            currentTimestep++;
            goToTimestep(currentTimestep);
        }

        if(constructedMap != null) {
            simularity = constructedMap.calculatePercentageSimilar(map);
        }

        return simularity;
    }

    /**
     * Returns the MapMovement of a robot for a specified timestep
     *
     * This function is seperated out to allow for forward and backwards movement if a visual simulator were developed
     * This additional functionality would require that past moves are stored in the simulator which is not yet
     * implemented
     *
     * @param requestedTimestep the timestep the simulator would like information for
     * @return The Movement the robot will take for th timestep
     */
    public MapMovement goToTimestep(int requestedTimestep){
        MapMovement robotMovement =  robot.getMoveToAttempt();

        //Second requirement can be removed with visual simulator implementation
        if(robotMovement != null || requestedTimestep != lastTimestep++) {
            MapMovement resultingMovement = map.simulateMovement(currentPosition, robotMovement);
            int squareIdentifier = map.getValueAtPosition(currentPosition);

            robot.resultingPhysicalMovement(resultingMovement, squareIdentifier);
            currentPosition = map.calculateEndPosition(currentPosition, resultingMovement);
            return resultingMovement;
        }else {
            throw new RuntimeException("Movement algorthim returned illegal move.");
        }
    }
}
