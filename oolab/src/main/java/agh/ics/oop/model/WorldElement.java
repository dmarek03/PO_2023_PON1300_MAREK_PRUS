package agh.ics.oop.model;

public interface WorldElement {

    Vector2d getPosition();

    void setPosition(Vector2d position);

    void move(int direction,MoveValidator validator);

    String toString();

    String path();


}
