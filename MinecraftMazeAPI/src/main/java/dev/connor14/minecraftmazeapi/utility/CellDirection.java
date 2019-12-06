package dev.connor14.minecraftmazeapi.utility;

import java.util.List;
import java.util.Collections;
import java.util.Arrays;

// todo need to clean this up
public enum CellDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static List<CellDirection> shuffle()  {
        List<CellDirection> cellDirections = Arrays.asList(values());
        Collections.shuffle(cellDirections);
        return cellDirections;
    }

    // changes based on Mineraft coordinate system
    //	switch(direction) {
    //	case NORTH:
    //		checkX = 0;
    //		checkZ = -1;
    //		break;
    //	case EAST:
    //		checkX = 1;
    //		checkZ = 0;
    //		break;
    //	case SOUTH:
    //		checkX = 0;
    //		checkZ = 1;
    //		break;
    //	case WEST:
    //		checkX = -1;
    //		checkZ = 0;
    //		break;
    //	}

    // just something to give the directions a value
    public static int valueOf(CellDirection cellDirection) {
        int value = 0;

        switch(cellDirection) {
            case NORTH:
                value = 1; // 0001
                break;
            case EAST:
                value = 2; // 0010
                break;
            case SOUTH:
                value = 4; // 0100
                break;
            case WEST:
                value = 8; // 1000
                break;
        }

        return value;
    }

    public static int oppositeValue(CellDirection cellDirection) {
        int value = 0;

        switch(cellDirection) {
            case NORTH:
                value = 4; // 0100
                break;
            case EAST:
                value = 8; // 1000
                break;
            case SOUTH:
                value = 1; // 0001
                break;
            case WEST:
                value = 2; // 0010
                break;
        }

        return value;
    }

    public static int changeZ(CellDirection cellDirection) {
        int change = 0;

        switch(cellDirection) {
            case NORTH:
                change = -1;
                break;
            case EAST:
                change = 0;
                break;
            case SOUTH:
                change = 1;
                break;
            case WEST:
                change = 0;
                break;
        }

        return change;
    }

    public static int changeX(CellDirection cellDirection) {
        int change = 0;

        switch(cellDirection) {
            case NORTH:
                change = 0;
                break;
            case EAST:
                change = 1;
                break;
            case SOUTH:
                change = 0;
                break;
            case WEST:
                change = -1;
                break;
        }

        return change;
    }
}