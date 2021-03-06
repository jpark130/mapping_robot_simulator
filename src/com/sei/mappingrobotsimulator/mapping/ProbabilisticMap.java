package com.sei.mappingrobotsimulator.mapping;

import com.sei.mappingrobotsimulator.mapping.Map;
import com.sei.mappingrobotsimulator.mapping.MapPosition;
import com.sei.mappingrobotsimulator.mapping.ProbabilisticMapPosition;

import java.util.ArrayList;

/**
 * Created by Kevin on 12/13/15.
 */
public class ProbabilisticMap {
    private ArrayList<ArrayList<ArrayList<ProbabilisticMapPosition>>> probabilisticMap;
    private Map currentBestGuess;

    /**
     * Construct an empty probabilistic map
     *
     * @param mapWidth width of the map
     * @param mapHeight height of the map
     */
    public ProbabilisticMap(int mapWidth, int mapHeight){
        probabilisticMap = new ArrayList<ArrayList<ArrayList<ProbabilisticMapPosition>>>();

        for(int i = 0; i < mapHeight; i++){
            ArrayList<ArrayList<ProbabilisticMapPosition>> row =
                    new ArrayList<ArrayList<ProbabilisticMapPosition>>();
            for(int j = 0; j <mapWidth; j++){
                ArrayList<ProbabilisticMapPosition> possibilities = new ArrayList<ProbabilisticMapPosition>();
                ProbabilisticMapPosition defaultValue = new ProbabilisticMapPosition(new MapPosition(j, i, 1), 1, 0.0f);
                possibilities.add(defaultValue);
                row.add(possibilities);
            }
            probabilisticMap.add(row);
        }
        currentBestGuess = Map.constructMapWithDimensions(mapWidth, mapHeight);
    }

    /**
     * Construct a map given a pre-existing map
     *
     * @param initialKnoweldge the pre-existing map to consturct from
     */
    public ProbabilisticMap(ProbabilisticMap initialKnoweldge){
        probabilisticMap = new ArrayList<ArrayList<ArrayList<ProbabilisticMapPosition>>>();
        for(int i = 0; i < initialKnoweldge.getMapHeight(); i++){
            probabilisticMap.add(new ArrayList<ArrayList<ProbabilisticMapPosition>>());
            for(int j = 0; j < initialKnoweldge.getMapWidth(); j++){
                probabilisticMap.get(i).add(new ArrayList<ProbabilisticMapPosition>());
                probabilisticMap.get(i).get(j).addAll(initialKnoweldge.getAllValues(j, i));
            }
        }

        currentBestGuess = Map.constructMapWithDimensions(initialKnoweldge.getMapWidth(), initialKnoweldge.getMapHeight());
    }


    /**
     * Updates the map after parsing through the effects of the most recent move
     *
     * @param attemptedMove the movement that the robot tried to make
     * @param actualMove the movement that actually occured in the physical world
     * @param possiblePositions all of the locations the robot might currently be in
     * @param squareIdentifier the identifier of the square the robot is currently in
     */

    public void updateMapWithMovement(MapMovement attemptedMove, ProbabilisticMapMovement actualMove,
                                      ArrayList<ProbabilisticMapPosition> possiblePositions, int squareIdentifier)
    {
        for(ProbabilisticMapPosition position: possiblePositions){
            MapPosition endPosition = MapPosition.calculateTranslatedPosition(position, actualMove.getDirection(), actualMove.getDistance());
            ProbabilisticMapPosition probabilisticMapPosition = new ProbabilisticMapPosition(endPosition, squareIdentifier, actualMove.getCertainty());
            addPositionToMap(probabilisticMapPosition);

            if(attemptedMove.getDistance() != actualMove.getDistance()){
                MapPosition wallPosition = MapPosition.calculateTranslatedPosition(position, actualMove.getDirection(), actualMove.getDistance() + 1);
                ProbabilisticMapPosition probWallPosition = new ProbabilisticMapPosition(wallPosition, Map.wall, position.getCertainty()*actualMove.getCertainty());
            }
        }
    }

    /**
     * Updates the information in the ProbabilisticMap given a ProbablisticMapPostition
     *
     * @param position the position information to add to the map
     */
    public void addPositionToMap(ProbabilisticMapPosition position) {
        ArrayList<ProbabilisticMapPosition> allValues = probabilisticMap.get(position.getyCord()).get(position.getxCord());
        //check to see if this value already exists as a probablity
        ProbabilisticMapPosition highestProbability = new ProbabilisticMapPosition(position, -1, 0.0f);
        boolean valueAlreadyExists = false;
        float totalCertantyPercentage = 0.0f;

        for(int i = 0; i < allValues.size(); i++){
            ProbabilisticMapPosition current = allValues.get(i);

            if(current.getSquareNumber() == position.getSquareNumber()){
                probabilisticMap.get(position.getyCord()).get(position.getxCord()).get(i).addValueToProbability(position);
                valueAlreadyExists = true;
            }

            if(current.getCertainty() > highestProbability.getCertainty()){
                highestProbability = current;
            }
            totalCertantyPercentage += current.getCertainty();
        }


        //Normalize the certainty percentage
        //for(int i = 0; i < allValues.size(); i++){
        //    ProbabilisticMapPosition current = allValues.get(i);
        //    current.normalize(totalCertantyPercentage);
        //}

        //If the value hasn't been updated, set a new value
        if(!valueAlreadyExists){
            probabilisticMap.get(position.getyCord()).get(position.getxCord()).add(position);
        }

        currentBestGuess.setValueAtPosition(highestProbability);
    }


    ///////
    //Getter methods
    //////

    public  int getMapHeight(){
        return probabilisticMap.size();
    }

    public int getMapWidth(){
        return probabilisticMap.get(0).size();
    }

    public ArrayList<ProbabilisticMapPosition> getAllValues(int xCord, int yCord){
        return probabilisticMap.get(yCord).get(xCord);
    }

    public ProbabilisticMapPosition getBestGuessForPosition(MapPosition position){
        ArrayList<ProbabilisticMapPosition> possibilities =  probabilisticMap.get(position.getyCord()).get(position.getxCord());
        ProbabilisticMapPosition highestProbability = new ProbabilisticMapPosition(position, 1, 0.0f);

        for(ProbabilisticMapPosition p: possibilities){
            if(p.getCertainty() > highestProbability.getCertainty()){
                highestProbability = p;
            }
        }

        return  highestProbability;
    }

    public Map getBestGuessMap() {
        return currentBestGuess;
    }

}
