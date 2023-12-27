package agh.ics.oop.model;


public class Animal implements WorldElement<Vector2d> {

    public MapDirection orientation;

    public Vector2d position;

    public Animal() {
        this.position = new Vector2d(2,2);
        this.orientation = MapDirection.NORTH;
    }

    public Animal(Vector2d startLocation) {
        this.position = startLocation;
        this.orientation = MapDirection.NORTH;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }


    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }


    public String toString() {
        String[] orientationArray = {"^",">","v","<"};
        return orientationArray[(this.getOrientation()).ordinal()];
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public boolean facing(MapDirection direction) {return this.orientation.equals(direction);}

    public boolean inMap(Vector2d vector) {
        return (vector.getX() < 5) && (vector.getY() < 5) && (vector.getX() >= 0) && (vector.getY() >= 0);
    }

    public Vector2d predictMove(MoveDirection direction) {
        switch (direction) {
            case FORWARD -> {return this.getPosition().add(MapDirection.unitVector(this.orientation));}
            case BACKWARD -> {return this.getPosition().subtract(MapDirection.unitVector(this.orientation));}
            default -> {return this.getPosition();}
        }
    }

    public MapDirection predictOrientation(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> {return MapDirection.next(this.getOrientation());}
            case LEFT -> {return MapDirection.previous(this.getOrientation());}
            default -> {return this.getOrientation();}
        }
    }

    public void move(MoveValidator validator, MoveDirection direction) {
        Vector2d nextPosition = predictMove(direction);
        MapDirection newOrientation = predictOrientation(direction);
        if (validator.canMoveTo(nextPosition)) {
            this.setPosition(nextPosition);
            this.setOrientation(newOrientation);
        }
    }

    @Override
    public String path() {
        return switch (this.getOrientation()) {
            case NORTH -> "/up.png";
            case EAST -> "/right.png";
            case SOUTH -> "/down.png";
            case WEST -> "/left.png";
        };
    }

}
