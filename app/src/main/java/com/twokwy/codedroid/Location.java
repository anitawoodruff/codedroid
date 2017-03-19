package com.twokwy.codedroid;

/**
 * Represents a location and can retrieve locations to left/right given a previous direction.
 *
 * Created by anita on 19/03/17.
 */

class Location {
    final int x;
    final int y;

    Location(int x, int y) {
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
                break;
            case 2:
                if (x % 2 == 1) {
                    throw new IllegalStateException(
                            String.format("x should be even but was %d in row %d", x, y));
                }
                break;
        }
        this.x = x;
        this.y = y;
    }

    Location getLocationInDirection(Compass direction) {
        switch (direction) {
            case NORTH:
                return new Location(x, y - 1);
            case EAST:
                return new Location(safeWrapX(x + xIncrement(y)), y);
            case SOUTH:
                return new Location(x, y + 1);
            case WEST:
                return new Location(safeWrapX(x - xIncrement(y)), y);
            default:
                throw new RuntimeException("Unknown direction: " + direction);
        }
    }

    boolean pointsUp() {
        return y == 2 || y == 1 && x % 2 == 1; // bottom row or odd-indexed middle row
    }

    boolean pointsDown() {
        return !pointsUp();
    }

    private static int safeWrapX(int x) {
        return ((x % 10) + 10) % 10;
    }

    private static int xIncrement(int y) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (x != location.x) return false;
        return y == location.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
