package com.twokwy.codedroid;

/**
 * Stores current and previous co-ordinates and calculates co-ordinates to the 'left' and 'right'.
 *
 * Assumes co-ordinates reference twenty rooms linked in a dodecahedron shape represented thus:
 *
 *    0 1 2 3 4 5 6 7 8 9
 *  0 - R - R - R - R - R
 *  1 R-R-R-R-R-R-R-R-R-R-
 *  2 R - R - R - R - R -
 *
 *  where links are represented by dashes (which wrap around the rows) and vertical adjacency.
 *
 * Created by anita on 19/03/17.
 */

class OrientedLocation {

    OrientedLocation(Compass direction, int x, int y) {

    }

    public OrientedLocation left() {
        return null;
    }
}
