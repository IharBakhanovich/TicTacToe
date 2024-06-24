package com.bakhanovich.tictactoe.model;

import lombok.Builder;
import lombok.Getter;

/**
 * A {@link Point} encapsulates the pair of Integer.
 */
@Getter
@Builder
public class Point implements Comparable<Point> {
    private int positionI;
    private int positionJ;

    /**
     * Gets the string representation of this point as the tupel
     * (positionI, positionJ).
     *
     * @return the string representation of the point.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(positionI).append(",").
                append(" ").append(positionJ).append(")");
        return builder.toString();
    }

    /**
     * Defines whether this {@link Point} is less than, equal to, or greater
     * than the other {@link Point}.
     *
     * @param other
     *         is the {@link Point} the object to be compared with.
     *
     * @return -1 , zero, or 1 as this object is less than, equal to,
     *        or greater than the specified object
     */
    @Override
    public int compareTo(Point other) {

        if (this.positionI > other.positionI) {
            return 1;
        } else if (this.positionI < other.positionI) {
            return -1;
        } else if (this.positionJ > other.positionJ) {
            return 1;
        } else if (this.positionJ < other.positionJ) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Defines whether this {@link Point} equal to another {@link Point}.
     *
     * @param point
     *         is the object to be compared with.
     * @return {@code true} if this {@link Point} is equal
     *         to another {@link Point} or {@code false} otherwise.
     */
    public boolean equals(Point point) {
        return  (this.compareTo(point) == 0);
    }
}
