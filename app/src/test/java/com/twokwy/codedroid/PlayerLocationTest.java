package com.twokwy.codedroid;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for the PlayerLocation class which is an oriented location, so it stores
 * (x, y) co-ordinates for its location and the location in the backwards direction, and can work
 * out the co-ordinates of its neighbours to the left and right.
 */
public class PlayerLocationTest {
    @Test
    public void orientedEastInMiddleRowEven_leftShouldIncreaseXAndFaceEast() throws Exception {
        PlayerLocation playerLocation = new PlayerLocation(Compass.WEST, 6, 1);
        assertThat(playerLocation.left().getOrientation(), is(Compass.EAST));
        assertThat(playerLocation.left().getLocation(), is(new Location(7, 1)));
    }

    @Test
    public void orientedEastInTopRow_rightShouldIncreaseYAndFaceSouth() throws Exception {
        PlayerLocation playerLocation = new PlayerLocation(Compass.WEST, 1, 0);
        assertThat(playerLocation.right(), is(equalTo(new PlayerLocation(Compass.NORTH, 1, 1))));
    }

    @Test
    public void orientedNorthInMiddleRowOdd_leftShouldDecrementXAndFaceWest() throws Exception {
        PlayerLocation playerLocation = new PlayerLocation(Compass.SOUTH, 0, 1);
        assertThat(playerLocation.left(), is(equalTo(new PlayerLocation(Compass.EAST, 9, 1))));
    }

    @Test
    public void orientedNorthInMiddleRowEven_rightShouldIncrementXAndFaceEast() throws Exception {
        PlayerLocation playerLocation = new PlayerLocation(Compass.SOUTH, 0, 1);
        assertThat(playerLocation.right(), is(equalTo(new PlayerLocation(Compass.WEST, 1, 1))));
    }

    @Test
    public void fromWestInMiddleRowOdd_rightShouldIncrementXAndBeFromWest() throws Exception {
        PlayerLocation playerLocation = new PlayerLocation(Compass.WEST, 1, 1);
        assertThat(playerLocation.right(), is(equalTo(new PlayerLocation(Compass.WEST, 2, 1))));
    }

    @Test
    public void turningLeftFiveTimesShouldBeANoop() throws Exception {
        int n = 0;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 3; y++) {
                if (!Location.isValidLocation(x, y)) {
                    continue;
                }
                for (Compass direction : Compass.values()) {
                    PlayerLocation playerLocation;
                    try {
                        playerLocation = new PlayerLocation(direction, x, y);
                    } catch (IllegalStateException e) {
                        continue;
                    }
                    PlayerLocation newLocation = playerLocation.left().left().left().left().left();
                    assertThat(newLocation, is(equalTo(playerLocation)));
                    n++;
                }
            }
        }
        assertThat(n, is(20 * 3)); // twenty rooms with three valid directions
    }

    @Test
    public void turningRightFiveTimesShouldBeANoop() throws Exception {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 3; y++) {
                if (!Location.isValidLocation(x, y)) {
                    continue;
                }
                for (Compass direction : Compass.values()) {
                    try {
                        PlayerLocation playerLocation = new PlayerLocation(direction, x, y);
                        PlayerLocation newLocation = playerLocation.right().right().right().right().right();
                        assertThat(newLocation, is(equalTo(playerLocation)));
                    } catch (IllegalStateException ignored) { }
                }
            }
        }
    }

    @Test
    public void goingLeftOrRightThenBackwardsShouldStayStill() throws Exception {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 3; y++) {
                if (!Location.isValidLocation(x, y)) {
                    continue;
                }
                for (Compass direction : Compass.values()) {
                    try {
                        PlayerLocation playerLocation = new PlayerLocation(direction, x, y);
                        assertThat(playerLocation.left().backwards().getLocation(),
                                is(equalTo(playerLocation.getLocation())));
                        assertThat(playerLocation.right().backwards().getLocation(),
                                is(equalTo(playerLocation.getLocation())));
                    } catch (IllegalStateException ignored) { }
                }
            }
        }
    }


    @Test
    public void fromNorth_backwardsShouldDecreaseYAndBeFromSouth() throws Exception {
        PlayerLocation playerLocation = new PlayerLocation(Compass.NORTH, 5, 1);
        assertEquals(new PlayerLocation(Compass.SOUTH, 5, 0), playerLocation.backwards());
    }
}