package com.sei.mappingrobotsimulator.mapping;

/**
 * Created by Kevin on 12/6/15.
 */
public class MapPosition {
    private int xCord;
    private int yCord;
    private int squareNumber;

    public MapPosition(int xCord, int yCord, int squareNumber){
        this.xCord = xCord;
        this.yCord = yCord;
        this.squareNumber = squareNumber;
    }

    public MapPosition(MapPosition m, int squareNumber){
        this.xCord = m.getxCord();
        this.yCord = m.getyCord();
        this.squareNumber =  squareNumber;
    }

    /**
     * Returns a new position given a map movement direction and distance
     *
     * @param currentPosition the position to move from
     * @param direction the direction to move in
     * @param distance the distance to move
     * @return the new map position of the robot
     */
    public static MapPosition calculateTranslatedPosition(MapPosition currentPosition, MovementDirection direction, int distance){
        MapPosition newPosition = null;
        switch (direction){
            case MOVE_DOWN:
                newPosition = new MapPosition(currentPosition.getxCord(), currentPosition.getyCord() - distance, currentPosition.squareNumber);
                break;

            case MOVE_UP:
                newPosition = new MapPosition(currentPosition.getxCord(), currentPosition.getyCord() + distance, currentPosition.squareNumber);
                break;

            case MOVE_LEFT:
                newPosition = new MapPosition(currentPosition.getxCord() - distance, currentPosition.getyCord(), currentPosition.squareNumber);
                break;

            case MOVE_RIGHT:
                newPosition = new MapPosition(currentPosition.getxCord() + distance, currentPosition.getyCord(), currentPosition.squareNumber);
                break;
        }

        return newPosition;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public int getSquareNumber(){ return  squareNumber;}


}
