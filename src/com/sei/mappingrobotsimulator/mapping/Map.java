package com.sei.mappingrobotsimulator.mapping;


/**
 * Created by Kevin on 12/1/15.
 */
public class Map {
    ///0
    private int[][] mapPrimitive;

    public Map(int[][] mapPrimitive){
        this.mapPrimitive = mapPrimitive;
    }

    public MapPosition getStartingLocation(){
        MapPosition startingLocation = null;
        for(int i = 0; i < mapPrimitive.length; i++){
            for(int j = 0; j < mapPrimitive[i].length; j++){
                if(mapPrimitive[i][j] == 0){
                    startingLocation = new MapPosition(i,j);
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

    public MapMovement simulateMovement(MapPosition currentPosition, MapMovement requestedMove){
        int currentX = currentPosition.getxCord();
        int currentY = currentPosition.getyCord();

        int distanceActuallyTraveled = 0;

        for(int i = 0; i < requestedMove.getDistance(); i++){
            switch(requestedMove.getDirection()){
                case MOVE_UP:
                    if(currentY - i >= 0 && mapPrimitive[currentY - i][currentX] != 1 ){
                        distanceActuallyTraveled++;
                    }
                    break;

                case MOVE_LEFT:
                    if(currentX - i >= 0 && mapPrimitive[currentY][currentX - i] != 1 ){
                        distanceActuallyTraveled++;
                    }
                    break;

                case MOVE_DOWN:
                    if(currentY + i <= mapPrimitive.length && mapPrimitive[currentY + i][currentX] != 1 ){
                        distanceActuallyTraveled++;
                    }
                    break;

                case MOVE_RIGHT:
                    if(currentX + i <= mapPrimitive[currentY].length && mapPrimitive[currentY][currentX + i] != 1 ){
                        distanceActuallyTraveled++;
                    }
                    break;
            }
        }

        return new MapMovement(requestedMove.getDirection(), distanceActuallyTraveled);

    }

    public boolean robotHasReturnedToStartingLocation(MapPosition p){
        if(mapPrimitive[p.getyCord()][p.getxCord()] == 0){
            return true;
        }
        return  false;
    }

    //should only be passed valid moves returned by simulateMovement
    public MapPosition calculateEndPosition(MapPosition currentPosition, MapMovement moveToMake){
        MapPosition endPosition = null;
        int xCord = currentPosition.getxCord();
        int yCord = currentPosition.getyCord();

        switch (moveToMake.getDirection()){
            case MOVE_UP:
                endPosition = new MapPosition(xCord, yCord - moveToMake.getDistance());
                break;

            case MOVE_LEFT:
                endPosition = new MapPosition(xCord - moveToMake.getDistance(), yCord);
                break;

            case MOVE_DOWN:
                endPosition = new MapPosition(xCord, yCord + moveToMake.getDistance());
                break;

            case MOVE_RIGHT:
                endPosition = new MapPosition(xCord + moveToMake.getDistance(), yCord);
                break;
        }

        return endPosition;
    }

    public float percentageSimilar (Map other){
        int numberCorrect = 0;

        for(int i = 0; i < mapPrimitive.length; i++){
            for(int j = 0; j < mapPrimitive[i].length; j++){
                MapPosition p = new MapPosition(j,i);
                if(other.comparePositionValue(p, mapPrimitive[j][i])){
                    numberCorrect++;
                }
            }
        }

        int arraySize = mapPrimitive.length * mapPrimitive[0].length;
        return numberCorrect/arraySize;
    }

    public boolean comparePositionValue(MapPosition p, int value){
        if(p.getxCord() >= 0
                && p.getxCord() < mapPrimitive[0].length
                && p.getyCord() >= 0
                && p.getyCord() < mapPrimitive.length){
            return value == mapPrimitive[p.getyCord()][p.getxCord()];
        }
        return  false;
    }

}
