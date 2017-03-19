package com.twokwy.codedroid;

/**
 * Enum of the four compass directions.
 *
 * Each direction can calculate its opposite, and update an X and Y value for a particular
 * movement (left, right or backwards).
 *
 * Created by anita on 19/03/17.
 */

enum Compass {
    NORTH, SOUTH, EAST, WEST;

    Compass getOpposite() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            default:
                return null; // shouldn't happen.
        }
    }

    static int xIncrement(int y) {
        switch (y) {
            case 0: // first row
                return 2;
            case 1: // middle row
                return 1;
            case 2: // bottom row
                return 2;
            default:
                throw new RuntimeException("y should be between 0-2 but was: " + y);
        }
    }

    static int safeWrapX(int x) {
        return ((x % 10) + 10) % 10;
    }
}
