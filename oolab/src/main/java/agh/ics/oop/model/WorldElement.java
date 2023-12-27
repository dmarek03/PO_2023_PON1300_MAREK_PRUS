package agh.ics.oop.model;

public interface WorldElement<P> {

    P getPosition();

    void setPosition(P position);

    void move(MoveValidator validator, MoveDirection direction);

    String toString();

    String path();


}
