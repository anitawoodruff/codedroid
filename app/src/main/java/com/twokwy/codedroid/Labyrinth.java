package com.twokwy.codedroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Holds the state of the labyrinth, ie what is at each location.
 *
 * Created by anita on 19/03/17.
 */

class Labyrinth {
    private final Map<Location, Room> mRooms;

    Labyrinth() {
        mRooms = createRooms();
        dropWumpusInRandomRoom(new Random());
        setTwoRandomRoomsToState(Room.PIT);
        setTwoRandomRoomsToState(Room.BATS);
    }

    private void setTwoRandomRoomsToState(Room value) {
        List<Location> locations = Location.getValidLocations();
        Random random = new Random();
        int roomsUpdated = 0;
        while (roomsUpdated < 2) {
            int randomIndex = random.nextInt(locations.size());
            if (mRooms.get(locations.get(randomIndex)).equals(Room.EMPTY)) {
                mRooms.put(locations.get(randomIndex), value);
                roomsUpdated++;
            }
        }
    }

    private void dropWumpusInRandomRoom(Random random) {
        List<Location> locations = Location.getValidLocations();
        int randomIndex = random.nextInt(locations.size());
        mRooms.put(locations.get(randomIndex), Room.WUMPUS);
    }

    private static Map<Location, Room> createRooms() {
        Map<Location, Room> rooms;
        rooms = new HashMap<>(30);
        for (Location location : Location.getValidLocations()) {
            rooms.put(location, Room.EMPTY);
        }
        return rooms;
    }

    List<Room> getRooms() {
        return new ArrayList<>(mRooms.values());
    }
}
