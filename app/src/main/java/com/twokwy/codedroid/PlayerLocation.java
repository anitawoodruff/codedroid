package com.twokwy.codedroid;

import android.support.annotation.NonNull;

/**
 * Stores current co-ordinates and calculates co-ordinates to the 'left' and 'right'.
 *
 * Also stores direction of previous location.
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

class PlayerLocation {

    private final Location mLocation;
    private final Compass mBackwardsDirection;

    /**
     * @param previousDirection - backwards direction. Must be valid given the co-ordinates.
     * @param x - index into the row - should be between 0 and 9 inclusive.
     * @param y - index of row - should be between 0 and 2 inclusive.
     */
    PlayerLocation(Compass previousDirection, int x, int y) {
        Location location = new Location(x, y);
        if (invalidPreviousDirection(location, previousDirection)) {
            throw new IllegalStateException(
                    String.format("Can't have arrived from %s at (%d, %d)", previousDirection, x, y));
        }
        mLocation = location;
        mBackwardsDirection = previousDirection;
    }

    PlayerLocation(Compass previousDirection, Location location) {
        if (invalidPreviousDirection(location, previousDirection)) {
            throw new IllegalStateException(
                    String.format("Can't have arrived from %s at %s", previousDirection, location));
        }
        mLocation = location;
        mBackwardsDirection = previousDirection;
    }

    private boolean invalidPreviousDirection(Location location, Compass previousDirection) {
        return location.pointsUp() && previousDirection == Compass.SOUTH
                || location.pointsDown() && previousDirection == Compass.NORTH;
    }

    @NonNull
    private PlayerLocation getNeighbour(Compass directionToTravel) {
        Location newLocation = mLocation.getLocationInDirection(directionToTravel);
        return new PlayerLocation(directionToTravel.getOpposite(), newLocation);
    }

    PlayerLocation left() {
        return getNeighbour(mLocation.getDirectionClockwiseFrom(mBackwardsDirection));
    }

    PlayerLocation right() {
        return getNeighbour(mLocation.getDirectionAnticlockwiseFrom(mBackwardsDirection));
    }

    PlayerLocation backwards() {
        return getNeighbour(mBackwardsDirection);
    }

    /**
     * Returns reference to current location, should not be modified.
     */
    Location getLocation() {
        return mLocation;
    }

    /**
     * Get which way we are facing (opposite of direction of approach).
     */
    Compass getOrientation() {
        return mBackwardsDirection.getOpposite();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerLocation that = (PlayerLocation) o;

        return mLocation.x == that.mLocation.x
                && mLocation.y == that.mLocation.y
                && mBackwardsDirection == that.mBackwardsDirection;
    }

    @Override
    public int hashCode() {
        int result = mBackwardsDirection.hashCode();
        result = 31 * result + mLocation.x;
        result = 31 * result + mLocation.y;
        return result;
    }

    @Override
    public String toString() {
        return "PlayerLocation{" +
                "mBackwardsDirection=" + mBackwardsDirection +
                ", mLocation.x=" + mLocation.x +
                ", mLocation.y=" + mLocation.y +
                '}';
    }
}
