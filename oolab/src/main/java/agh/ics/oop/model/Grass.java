package agh.ics.oop.model;

public class Grass implements WorldElement<Vector2d> {

    public Vector2d position;

    public Grass(Vector2d startLocation) {
        this.position = startLocation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
    public void move(MoveValidator validator, MoveDirection direction) {return;}

    public String toString() {
        return "*";
    }

    @Override
    public String path() {
        return "/grass.png";
    }

}
