package com.twokwy.codedroid;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for the OrientedLocation class which is an oriented location, so it stores
 * (x, y) co-ordinates for its location and the location in the backwards direction, and can work
 * out the co-ordinates of its neighbours to the left and right.
 */
public class OrientedLocationTest {
    @Test
    public void orientedEastInMiddleRowEven_leftShouldIncreaseXAndFaceEast() throws Exception {
        OrientedLocation orientedLocation = new OrientedLocation(Compass.EAST, 6, 1);
        OrientedLocation newLocation = orientedLocation.left();
        assertThat(newLocation.getOrientation(), is(Compass.EAST));
        assertThat(newLocation.getX(), is(7));
        assertThat(newLocation.getY(), is(1));
    }

    @Test
    public void orientedNorthInMiddleRowOdd_leftShouldWrapXAndFaceWest() throws Exception {
        OrientedLocation orientedLocation = new OrientedLocation(Compass.NORTH, 0, 1);
        assertThat(orientedLocation.left(), is(equalTo(new OrientedLocation(Compass.WEST, 9, 1))));
    }

    @Test
    public void turningLeftFiveTimesShouldBeANoop() throws Exception {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 3; y++) {
                for (Compass direction : Compass.values()) {
                    try {
                        OrientedLocation orientedLocation = new OrientedLocation(direction, x, y);
                        OrientedLocation newLocation = orientedLocation.left().left().left().left().left();
                        assertThat(newLocation, is(equalTo(orientedLocation)));
                    } catch (IllegalStateException e) {
                        break;
                    }
                }
            }
        }
    }

    @Test
    public void turningRightFiveTimesShouldBeANoop() throws Exception {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 3; y++) {
                for (Compass direction : Compass.values()) {
                    try {
                        OrientedLocation orientedLocation = new OrientedLocation(direction, x, y);
                        OrientedLocation newLocation = orientedLocation.right().right().right().right().right();
                        assertThat(newLocation, is(equalTo(orientedLocation)));
                    } catch (IllegalStateException e) {
                        break;
                    }
                }
            }
        }
    }

    @Test
    public void goingLeftOrRightThenBackwardsShouldStayStill() throws Exception {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 3; y++) {
                for (Compass direction : Compass.values()) {
                    try {
                        OrientedLocation orientedLocation = new OrientedLocation(direction, x, y);
                        assertThat(orientedLocation.left().backwards().getX(), is(equalTo(orientedLocation.getX())));
                        assertThat(orientedLocation.left().backwards().getY(), is(equalTo(orientedLocation.getY())));
                        assertThat(orientedLocation.right().backwards().getX(), is(equalTo(orientedLocation.getX())));
                        assertThat(orientedLocation.right().backwards().getY(), is(equalTo(orientedLocation.getY())));
                    } catch (IllegalStateException e) {
                        break;
                    }
                }
            }
        }
    }


    @Test
    public void orientedSouth_backwardsShouldDecreaseYAndFaceNorth() throws Exception {
        OrientedLocation orientedLocation = new OrientedLocation(Compass.SOUTH, 5, 1);
        assertEquals(new OrientedLocation(Compass.NORTH, 5, 0), orientedLocation.backwards());
    }
}