package com.twokwy.codedroid;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Tests for the Labyrinth - has right number of rooms with right number of states in them.
 */
public class LabyrinthTest {
    @Test
    public void constructsTwentyRooms() throws Exception {
        Labyrinth labyrinth = new Labyrinth();
        assertThat(labyrinth.getRooms().size(), is(equalTo(20)));
    }

    @Test
    public void oneRoomContainsTheWumpus() throws Exception {
        Labyrinth labyrinth = new Labyrinth();
        List<Room> rooms = labyrinth.getRooms();
        assertThat(rooms, hasItem(Room.WUMPUS));
        rooms.remove(Room.WUMPUS);
        assertThat(rooms, not(hasItem(Room.WUMPUS)));
    }

    @Test
    public void twoRoomsContainPits() throws Exception {
        Labyrinth labyrinth = new Labyrinth();
        List<Room> rooms = labyrinth.getRooms();
        int count = 0;
        for (Room room : rooms) {
            if (room == Room.PIT) {
                count++;
            }
        }
        assertThat(count, is(2));
    }

    @Test
    public void twoRoomsContainBats() throws Exception {
        Labyrinth labyrinth = new Labyrinth();
        List<Room> rooms = labyrinth.getRooms();
        int count = 0;
        for (Room room : rooms) {
            if (room == Room.BATS) {
                count++;
            }
        }
        assertThat(count, is(2));
    }
}