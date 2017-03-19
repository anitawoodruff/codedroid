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
    private final int mX;
    private final int mY;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrientedLocation that = (OrientedLocation) o;

        if (mX != that.mX) return false;
        if (mY != that.mY) return false;
        return mDirection == that.mDirection;
    }

    @Override
    public int hashCode() {
        int result = mDirection.hashCode();
        result = 31 * result + mX;
        result = 31 * result + mY;
        return result;
    }

    @Override
    public String toString() {
        return "OrientedLocation{" +
                "mDirection=" + mDirection +
                ", mX=" + mX +
                ", mY=" + mY +
                '}';
    }

    OrientedLocation(Compass direction, int x, int y) {
        if (x < 0 || y < 0 || x > 9 || y > 2) {
            throw new IllegalArgumentException(
                    String.format("x and/or y out of bounds at (%d, %d)", x, y));
        }
        switch (y) {
            case 0:
                if (x % 2 == 0) {
                    throw new IllegalStateException(
                            String.format("x should be odd but was %d in row %d", x, y));
                }
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
                if (x % 2 == 1) {
                    throw new IllegalStateException(
                            String.format("x should be even but was %d in row %d", x, y));
                }
                if (direction == Compass.NORTH) {
                    throw new IllegalStateException(
                            String.format("Cannot have travelled %s to (%d, %d)", direction, x, y));
                }
                break;
        }
        mDirection = direction;
        mX = x;
        mY = y;
    }

    OrientedLocation left() {
        int newX = mDirection.xLeft(mX, mY);
        int newY = mDirection.yLeft(mX, mY);
        Compass newDirection = getNewDirection(newX, newY);
        return new OrientedLocation(newDirection, newX, newY);
    }

    OrientedLocation right() {
        int newX = mDirection.xRight(mX, mY);
        int newY = mDirection.yRight(mX, mY);
        Compass newDirection = getNewDirection(newX, newY);
        return new OrientedLocation(newDirection, newX, newY);
    }

    @NonNull
    private Compass getNewDirection(int newX, int newY) {
        Compass newDirection;
        if (newX != mX) {
            newDirection = Compass.safeWrapX(mX + Compass.xIncrement(mY)) == newX ? Compass.EAST : Compass.WEST;
        } else {
            assert(newY != mY);
            newDirection = newY > mY ? Compass.SOUTH : Compass.NORTH;
        }
        return newDirection;
    }

    OrientedLocation backwards() {
        return new OrientedLocation(mDirection.getOpposite(),
                mDirection.xBackwards(mX, mY), mDirection.yBackwards(mY));
    }

    Compass getOrientation() {
        return mDirection;
    }

    int getX() {
        return mX;
    }

    int getY() {
        return mY;
    }
}
