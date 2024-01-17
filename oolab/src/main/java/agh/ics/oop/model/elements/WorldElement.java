package agh.ics.oop.model.elements;

import agh.ics.oop.model.map.mapUtils.MoveValidator;
import agh.ics.oop.model.map.mapUtils.Vector2d;

public interface WorldElement {

    Vector2d getPosition();

    void setPosition(Vector2d position);

    void move(int direction, MoveValidator validator);

    String toString();

    String path();

    String secondaryPath();


}
