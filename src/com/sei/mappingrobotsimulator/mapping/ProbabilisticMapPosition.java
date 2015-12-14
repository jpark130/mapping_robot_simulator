package com.sei.mappingrobotsimulator.mapping;

/**
 * Created by Kevin on 12/13/15.
 */
public class ProbabilisticMapPosition extends MapPosition {
    private float certainty;
    private int numberOfDataPoints;

    /**
     * A map position which also monitors its certainty
     *
     * @param postion the position of the mapPosition square
     * @param squareNumber the squareNumber identifier
     * @param certainty the certainty that the value is correct at that position
     */
    public ProbabilisticMapPosition(MapPosition postion, int squareNumber, float certainty){
        super(postion, squareNumber);
        this.certainty = certainty;
        this.numberOfDataPoints = 1;
    }

    public int getNumberOfDataPoints(){
        return numberOfDataPoints;
    }

    public void addValueToProbability(ProbabilisticMapPosition newData){
        if(newData.getSquareNumber() == super.getSquareNumber()) {
            float unNormalizedCertainty = certainty *numberOfDataPoints;
            numberOfDataPoints++;

            unNormalizedCertainty += newData.getCertainty();
            certainty = unNormalizedCertainty/numberOfDataPoints;
        }
    }

    public  void normalize(float normalizer){
        this.certainty = (this.certainty/normalizer);
    }

    public int getSquareNumber(){ return super.getSquareNumber();}

    public float getCertainty(){return certainty;}
}
