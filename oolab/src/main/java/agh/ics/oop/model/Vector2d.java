package agh.ics.oop.model;

import java.util.Objects;

public class Vector2d {


    final private int x;
    final private int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {
        return "(" + Integer.toString(this.getX()) + "," + Integer.toString(this.getY()) + ")";
    }

    public boolean precedes(Vector2d other) {
        return (other.getX() >= this.getX()) && (other.getY() >= this.getY());
    }

    public boolean follows(Vector2d other) {
        return (other.getX() <= this.getX()) && (other.getY() <= this.getY());
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.getX() + other.getX(),this.getY() + other.getY());
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.getX() - other.getX(),this.getY() - other.getY());
    }

    public Vector2d upperRight(Vector2d other) {
        if (this == null) {
            return other;
        }
        return new Vector2d(Math.max(this.getX(),other.getX()),Math.max(this.getY(),other.getY()));
    }

    public Vector2d lowerLeft(Vector2d other) {
        if (this == null) {
            return other;
        }
        return new Vector2d(Math.min(this.getX(),other.getX()),Math.min(this.getY(),other.getY()));
    }

    public Vector2d opposite() {
        return new Vector2d(-this.getX(),-this.getY());
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if ((other == null) || (this.getClass() != other.getClass())) return false;
        Vector2d output = (Vector2d) other;
        return ((this.getX() == output.getX()) && (this.getY() == output.getY()));
    }

    public int hashCode() {
        return Objects.hash(this.getX(),this.getY());
    }

}
