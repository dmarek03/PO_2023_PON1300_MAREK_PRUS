package agh.ics.oop.model.elements;

import agh.ics.oop.model.map.mapUtils.MoveValidator;
import agh.ics.oop.model.map.mapUtils.Vector2d;

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
        return "/fields/grass.png";
    }

    @Override
    public String healthPath() {
        return path();
    }

    @Override
    public String ASCII() {
        return "*";
    }

    @Override
    public String color() {
        return "#0B4300";
    }

}
