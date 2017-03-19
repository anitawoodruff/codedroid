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

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
