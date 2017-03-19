package com.twokwy.codedroid;

import android.support.annotation.NonNull;

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

    private final Compass mDirection;
    private final Location mLocation;

    OrientedLocation(Compass direction, int x, int y) {
        mLocation = new Location(x, y);
        switch (y) {
            case 0:
                if (direction == Compass.SOUTH) {
                    throw new IllegalStateException(
                            String.format("Cannot have travelled %s to (%d, %d)", direction, x, y));
                }
                break;
            case 1:
                if (x % 2 == 0 && direction == Compass.SOUTH) {
                    throw new IllegalStateException(
                            String.format("Cannot have travelled %s to (%d, %d)", direction, x, y));
                }
                if (x % 2 == 1 && direction == Compass.NORTH) {
                    throw new IllegalStateException(
                            String.format("Cannot have travelled %s to (%d, %d)", direction, x, y));
                }
                break;
            case 2:
                if (direction == Compass.NORTH) {
                    throw new IllegalStateException(
                            String.format("Cannot have travelled %s to (%d, %d)", direction, x, y));
                }
                break;
        }
        mDirection = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrientedLocation that = (OrientedLocation) o;

        if (mLocation.x != that.mLocation.x) return false;
        if (mLocation.y != that.mLocation.y) return false;
        return mDirection == that.mDirection;
    }

    @Override
    public int hashCode() {
        int result = mDirection.hashCode();
        result = 31 * result + mLocation.x;
        result = 31 * result + mLocation.y;
        return result;
    }

    @Override
    public String toString() {
        return "OrientedLocation{" +
                "mDirection=" + mDirection +
                ", mLocation.x=" + mLocation.x +
                ", mLocation.y=" + mLocation.y +
                '}';
    }

    OrientedLocation left() {
        int newX = mDirection.xLeft(mLocation.x, mLocation.y);
        int newY = mDirection.yLeft(mLocation.x, mLocation.y);
        return new OrientedLocation(getNewDirection(newX, newY), newX, newY);
    }

    OrientedLocation right() {
        int newX = mDirection.xRight(mLocation.x, mLocation.y);
        int newY = mDirection.yRight(mLocation.x, mLocation.y);
        return new OrientedLocation(getNewDirection(newX, newY), newX, newY);
    }

    @NonNull
    private Compass getNewDirection(int newX, int newY) {
        Compass newDirection;
        if (newX != mLocation.x) {
            newDirection = Compass.safeWrapX(mLocation.x + Compass.xIncrement(mLocation.y)) == newX ? Compass.EAST : Compass.WEST;
        } else {
            assert(newY != mLocation.y);
            newDirection = newY > mLocation.y ? Compass.SOUTH : Compass.NORTH;
        }
        return newDirection;
    }

    OrientedLocation backwards() {
        return new OrientedLocation(mDirection.getOpposite(),
                mDirection.xBackwards(mLocation.x, mLocation.y), mDirection.yBackwards(mLocation.y));
    }

    Compass getOrientation() {
        return mDirection;
    }

    Location getLocation() {
        return new Location(mLocation.x, mLocation.y);
    }
}
