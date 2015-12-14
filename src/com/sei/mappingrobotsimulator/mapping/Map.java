package com.sei.mappingrobotsimulator.mapping;


import java.util.ArrayList;

/**
 * Created by Kevin on 12/1/15.
 */
public class Map {
    private ArrayList<ArrayList<MapPosition>>  mapPrimitive;

    public Map(ArrayList<ArrayList<MapPosition>>  mapPrimitive){
        this.mapPrimitive = mapPrimitive;
    }

    /**
     * Builds a blank map given a set of dimensions - for the robot's internal representation
     *
     * @param width the width of the map to build
     * @param height the height of the map to build
     * @return a map of the appropriate size with all squares set to empty space with id 1
     */
    public static Map constructMapWithDimensions (int width, int height){
        ArrayList<ArrayList<MapPosition>> map = new ArrayList<ArrayList<MapPosition>>();
        for(int i = 0; i < height; i++){
            ArrayList<MapPosition> row = new ArrayList<MapPosition>();
            for(int j = 0; j < width; j++){
                row.add(new MapPosition(j,i,1));
            }
            map.add(row);
        }

        return new Map(map);
    }


    /**
     * Returns the width of the map (So long as the map is the same width in all rows
     * @return the width of the map
     */
    public int getMapWidth(){
        return mapPrimitive.get(0).size();
    }

    /**
     * Returns the height of the map (assuming the map is the same height all the way through)
     *
     * @return the height of the map
     */
    public int getMapHeight(){
        return mapPrimitive.size();
    }

    /**
     * Sets the value for a position on the map.  This function allows the probabilistic map to be built
     * @param position the position to update the value of
     */
    public void setValueAtPosition(MapPosition position){
        mapPrimitive.get(position.getyCord()).set(position.getxCord(), position);
    }

    /**
     * Returns the map's starting location
     *
     * @return the map position of the starting location
     */
    public MapPosition getStartingLocation(){
        MapPosition startingLocation = null;
        for(int i = 0; i < mapPrimitive.size(); i++){
            for(int j = 0; j < mapPrimitive.get(i).size(); j++){
                if(mapPrimitive.get(i).get(j).getSquareNumber() == 0){
                    startingLocation = new MapPosition(j,i, 0);
                    break;
                }
            }
            if(startingLocation != null){
                break;
            }
        }

        if(startingLocation == null){
            System.out.println("No valid start location: should crash");
        }

        return  startingLocation;
    }

    /**
     * Returns the square identifier for the provided map position
     *
     * @param position the position in the map to get the identifier for
     * @return the squareIdentifier for the room
     */
    public int getValueAtPosition(MapPosition position){
        return mapPrimitive.get(position.getyCord()).get(position.getxCord()).getSquareNumber();
    }

    /**
     * Determines whether the robot is on the starting/docing station
     *
     * @param p the position of the robot
     * @return true if that is the starting location, false otherwise
     */
    public boolean robotHasReturnedToStartingLocation(MapPosition p){
        if(mapPrimitive.get(p.getyCord()).get(p.getxCord()).getSquareNumber() == 0){
            return true;
        }
        return  false;
    }

    /**
     * Given a position and a movement on a map, this function returns the movement that
     * the robot would actually end up performing (running into a wall etc)
     *
     * @param currentPosition the position the robot is currently in
     * @param requestedMove the move the robot would like to make
     * @return the movement the robot actually makes
     */
    public MapMovement simulateMovement(MapPosition currentPosition, MapMovement requestedMove){
        if(requestedMove != null) {
            int currentX = currentPosition.getxCord();
            int currentY = currentPosition.getyCord();

            int distanceActuallyTraveled = 0;

            for (int i = 1; i <= requestedMove.getDistance(); i++) {
                switch (requestedMove.getDirection()) {
                    case MOVE_UP:
                        if (currentY - i >= 0 && mapPrimitive.get(currentY - i).get(currentX).getSquareNumber() != 1) {
                            distanceActuallyTraveled++;
                        }
                        break;

                    case MOVE_LEFT:
                        if (currentX - i >= 0 && mapPrimitive.get(currentY).get(currentX - i).getSquareNumber() != 1) {
                            distanceActuallyTraveled++;
                        }
                        break;

                    case MOVE_DOWN:
                        if (currentY + i <= mapPrimitive.size() && mapPrimitive.get(currentY + i).get(currentX).getSquareNumber() != 1) {
                            distanceActuallyTraveled++;
                        }
                        break;

                    case MOVE_RIGHT:
                        if (currentX + i <= mapPrimitive.get(currentY).size() && mapPrimitive.get(currentY).get(currentX + i).getSquareNumber() != 1) {
                            distanceActuallyTraveled++;
                        }
                        break;
                }
            }
            return new MapMovement(requestedMove.getDirection(), distanceActuallyTraveled, requestedMove.getShouldRobotDoc());

        }
        return null;
    }

    /**
     * Calculates where a robot would end up following a movement from a given location
     * This function should only be passed moves returned by simulateMovement
     *
     * @param currentPosition the location to start the move from
     * @param moveToMake the movement to make
     * @return the final mapPostiion of the robot
     */
    public MapPosition calculateEndPosition(MapPosition currentPosition, MapMovement moveToMake){
        MapPosition endPosition = null;
        int xCord = currentPosition.getxCord();
        int yCord = currentPosition.getyCord();

        switch (moveToMake.getDirection()){
            case MOVE_UP:
                endPosition = new MapPosition(xCord, yCord - moveToMake.getDistance(), currentPosition.getSquareNumber());
                break;

            case MOVE_LEFT:
                endPosition = new MapPosition(xCord - moveToMake.getDistance(), yCord, currentPosition.getSquareNumber());
                break;

            case MOVE_DOWN:
                endPosition = new MapPosition(xCord, yCord + moveToMake.getDistance(), currentPosition.getSquareNumber());
                break;

            case MOVE_RIGHT:
                endPosition = new MapPosition(xCord + moveToMake.getDistance(), yCord, currentPosition.getSquareNumber());
                break;
        }

        return endPosition;
    }


    //Functions which can be used to tell how effective algorithms are

    /**
     * Calculates the percentage of map squares which overlap between this map and another
     *
     * @param other the map to compare against
     * @return the percentage of similarity between the maps
     */
    public float calculatePercentageSimilar (Map other){
        int numberCorrect = 0;

        for(int i = 0; i < mapPrimitive.size(); i++){
            for(int j = 0; j < mapPrimitive.get(i).size(); j++){
                MapPosition p = new MapPosition(j,i, other.getValueAtPosition(new MapPosition(j,i,-1)));
                if(other.comparePositionValue(p)){
                    numberCorrect++;
                }
            }
        }

        int arraySize = mapPrimitive.size() * mapPrimitive.get(0).size();
        return numberCorrect/arraySize;
    }

    /**
     * Determines whether a particular value is stared at a map position
     *
     * @param p the position on the map
     * @return true if the value is at that position, false otherwise
     */
    public boolean comparePositionValue(MapPosition p){
        if(p.getxCord() >= 0
                && p.getxCord() < mapPrimitive.get(0).size()
                && p.getyCord() >= 0
                && p.getyCord() < mapPrimitive.size()){
            return p.getSquareNumber() == mapPrimitive.get(p.getyCord()).get(p.getxCord()).getSquareNumber();
        }
        return  false;
    }

}
