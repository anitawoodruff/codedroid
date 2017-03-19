package com.twokwy.codedroid;

/**
 * Enum of the four compass directions.
 *
 * Each direction can calculate its opposite, and update an X and Y value for a particular
 * movement (left, right or backwards).
 *
 * Created by anita on 19/03/17.
 */

enum Compass {
    NORTH, EAST, SOUTH, WEST;  // this order is important!

    Compass getDirectionClockwise(boolean pointsUp) {
        return turn(1, pointsUp);
    }

    Compass getDirectionAnticlockwise(boolean pointsUp) {
        return turn(-1, pointsUp);
    }
    /**
     * @param offset +1 to turn once clockwise, -1 for one turn anticlockwise.
     * @param pointsUp true if the location has a link northwards, false if it has one southwards.
     * @return new compass direction.
     */
    Compass turn(int offset, boolean pointsUp) {
        Compass directionToSkip = pointsUp ? Compass.SOUTH : Compass.NORTH;
        if (directionToSkip == this) {
            throw new AssertionError(
                    String.format("Cannot skip direction %s if from %s", directionToSkip, this));
        }
        return getDirectionClockwiseWhileSkipping(offset, directionToSkip);
    }

    private Compass getDirectionClockwiseWhileSkipping(int offset, Compass directionToSkip) {
        Compass newDirection = getDirectionClockwise(offset);
        return newDirection == directionToSkip ? newDirection.getDirectionClockwise(offset) : newDirection;
    }

    Compass getDirectionClockwise(int offset) {
        return Compass.values()[safeWrapCompassIndex(this.ordinal() + offset)];
    }

    Compass getOpposite() {
        return Compass.values()[safeWrapCompassIndex(this.ordinal() + 2)];
    }

    private static int safeWrapCompassIndex(int i) {
        return ((i % 4) + 4) % 4;

    }

}
