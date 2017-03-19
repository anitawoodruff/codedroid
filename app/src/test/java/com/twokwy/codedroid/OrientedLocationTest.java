package com.twokwy.codedroid;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the OrientedLocation class which is an oriented location, so it stores
 * (x, y) co-ordinates for its location and the location in the backwards direction, and can work
 * out the co-ordinates of its neighbours to the left and right.
 */
public class OrientedLocationTest {
    @Test
    public void orientedSouth_leftShouldIncreaseXAndFaceEast() throws Exception {
        OrientedLocation orientedLocation = new OrientedLocation(Compass.SOUTH, 6, 1);
        assertEquals(new OrientedLocation(Compass.EAST, 7, 1), orientedLocation.left());
    }
}