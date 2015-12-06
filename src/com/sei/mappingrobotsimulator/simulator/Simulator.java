package com.sei.mappingrobotsimulator.simulator;

import com.sei.mappingrobotsimulator.Robot;
import com.sei.mappingrobotsimulator.mapping.Map;
import com.sei.mappingrobotsimulator.mapping.MapMovement;
import com.sei.mappingrobotsimulator.mapping.MapPosition;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/1/15.
 */
public class Simulator {
    private int currentTimestep;
    private MapPosition currentPosition;

    //Provided data
    private Map map;
    private Robot robot;


    public static void main(String[] args) {


    }

    public float Simulator(Map map, Robot robot){
        this.map = map;
        this.robot = robot;

        currentPosition = new MapPosition(map.getStartingLocation());
        currentTimestep = 0;
        Map constructedMap = null;

        //Perform
        while(robot.hasBatteryLife()){
            if(map.robotHasReturnedToStartingLocation(currentPosition) && robot.robotSeekingDoc()){
                constructedMap = robot.getConstructedMap();
                break;
            }

            currentTimestep++;
            goToTimestep(currentTimestep);
        }
        return constructedMap.percentageSimilar(map);
    }

    public MapMovement goToTimestep(int requestedTimestep){
        MapMovement robotMovement =  robot.getMoveToAttempt();
        MapMovement resultingMovement = map.simulateMovement(currentPosition, robotMovement);
        robot.resultingPhysicalMovement(resultingMovement);
        currentPosition = map.calculateEndPosition(currentPosition, resultingMovement);
        return resultingMovement;
    }

}
