package agh.ics.oop.model;

public class Grass implements WorldElement {

    public Vector2d position;
    private final int grassEnergy;

    public Grass(Vector2d position, int energy){
        this.position = position;

        this.grassEnergy = energy;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getGrassEnergy() {return this.grassEnergy;}

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
    public void move(int direction, MoveValidator validator) {return;}

    public String toString() {
//        return this.getPosition().toString();
        return "*";
    }

    @Override
    public String path() {
        return "/grass.png";
    }

}
