package com.twokwy.codedroid;

/**
 * Simple wrapper for x,y co-ordinates.
 *
 * Created by anita on 19/03/17.
 */

class Location {
    final int x;
    final int y;

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

    boolean pointsUp() {
        return y == 2 || y == 1 && x % 2 == 1; // bottom row or odd-indexed middle row
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    Compass getDirectionLeftFrom(Compass direction) {
        if (pointsUp()) {
            switch (direction) {
                case NORTH:
                    throw new AssertionError(
                            String.format("Cannot be oriented %s at (%d, %d) since pointing up",
                                    direction, x, y));
                case EAST:
                    return Compass.NORTH;
                case SOUTH:
                    return Compass.EAST;
                case WEST:
                    return Compass.WEST;
                default:
                    throw new RuntimeException("Unknown direction: " + direction);
            }
        } else {
            switch (direction) {
                case NORTH:
                    return Compass.WEST;
                case EAST:
                    return Compass.EAST;
                case SOUTH:
                    throw new AssertionError(
                            String.format("Cannot be oriented %s at (%d, %d) since pointing down",
                                    direction, x, y));
                case WEST:
                    return Compass.SOUTH;
                default:
                    throw new RuntimeException("Unknown direction: " + direction);
            }

        }
    }

    Location getLocationInDirection(Compass direction) {
        switch (direction) {
            case NORTH:
                return new Location(x, y - 1);
            case EAST:
                return new Location(Compass.safeWrapX(x + Compass.xIncrement(y)), y);
            case SOUTH:
                return new Location(x, y + 1);
            case WEST:
                return new Location(Compass.safeWrapX(x - Compass.xIncrement(y)), y);
            default:
                throw new RuntimeException("Unknown direction: " + direction);
        }
    }

    Compass getDirectionRightFrom(Compass direction) {
        if (pointsUp()) {
            switch (direction) {
                case NORTH:
                    throw new AssertionError(
                            String.format("Cannot be oriented %s at (%d, %d) since pointing up",
                                    direction, x, y));
                case EAST:
                    return Compass.EAST;
                case SOUTH:
                    return Compass.WEST;
                case WEST:
                    return Compass.NORTH;
                default:
                    throw new RuntimeException("Unknown direction: " + direction);
            }
        } else {
            switch (direction) {
                case NORTH:
                    return Compass.EAST;
                case EAST:
                    return Compass.SOUTH;
                case SOUTH:
                    throw new AssertionError(
                            String.format("Cannot be oriented %s at (%d, %d) since pointing down",
                                    direction, x, y));
                case WEST:
                    return Compass.WEST;
                default:
                    throw new RuntimeException("Unknown direction: " + direction);
            }

        }
    }
}
