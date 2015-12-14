package com.sei.mappingrobotsimulator.mapping;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kevin on 12/1/15.
 */
public class MapBuilder {


    /**
     * Reads a map in from a text file at the provided file path
     *
     * @param filePath the file path to the map
     * @return a Map object representing the map specified in the text file
     */
    public static Map loadMapFromFile(String filePath){
        File file = new File(filePath);
        FileInputStream fis = null;
        BufferedReader reader = null;
        ArrayList<ArrayList<MapPosition>> mapRepresentation = new ArrayList<ArrayList<MapPosition>>();
        ArrayList<MapPosition> row = new ArrayList<MapPosition>();
        int uniqueIdentifier = 1;

        try{
            fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis));

            String line = reader.readLine();
            while(line != null){
                for(int i = 0; i < line.length(); i++){
                    char c = line.charAt(i);
                    if(c == 'w'){
                        row.add(new MapPosition(mapRepresentation.size(), i, -1));
                    }else if(c == 's'){
                        row.add(new MapPosition(mapRepresentation.size(), i, 0));
                    }else{
                        row.add(new MapPosition(mapRepresentation.size(), i, uniqueIdentifier));
                        uniqueIdentifier++;
                    }
                }

                mapRepresentation.add(row);
                row = new ArrayList<MapPosition>();
                line = reader.readLine();
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        return new Map(mapRepresentation);
    }
}
