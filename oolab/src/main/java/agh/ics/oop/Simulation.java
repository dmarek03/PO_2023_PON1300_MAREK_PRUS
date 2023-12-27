package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<MoveDirection> moves;

    private final WorldMap<WorldElement, Vector2d> map;

    private final List<Animal> animals;

    private boolean wait = false;


    public Simulation(List<MoveDirection> moves, WorldMap<WorldElement, Vector2d> map) {
        this.moves = moves;
        this.map = map;
        this.wait = true;
        List<Animal> allAnimals = new ArrayList<>();
        map.getAnimals().values().forEach(animal -> {
            allAnimals.add((Animal) animal);
        });
        this.animals = allAnimals;
    }

    public Simulation(List<MoveDirection> moves,List<Vector2d> positions, WorldMap<WorldElement , Vector2d> map) {
        this.moves = moves;
        List<Animal> all = new ArrayList<>();
        positions.forEach(position -> {
            Animal animal = new Animal(position,MapDirection.NORTH,100,8);
            try {
                if (map.place(animal,true)) {
                    all.add(animal);
                }
            } catch (PositionAlreadyOccupiedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        });
        this.animals = all;
        this.map = map;
    }

    public Simulation(List<MoveDirection> moves,List<Vector2d> positions, WorldMap map, boolean placeInform) {
        this.moves = moves;
        List<Animal> all = new ArrayList<>();
        positions.forEach(position -> {
            Animal animal = new Animal(position,MapDirection.NORTH,100,8);
            try {
                if (map.place(animal,placeInform)) {
                    all.add(animal);
                }
            } catch (PositionAlreadyOccupiedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        });
        this.animals = all;
        this.map = map;
    }


    public int getNoAnimals() {return this.animals.size();}
    public int getNoMoves() {return this.moves.size();}
    public void run() {
        int allAnimals = this.getNoAnimals();
        for (int i = 0; i < getNoMoves(); i++) {
            int animalInd = (i % allAnimals);
            Animal currAnimal = animals.get(animalInd);
            map.move(currAnimal, this.moves.get(i).ordinal());
            animals.set(animalInd,currAnimal);
            if (wait) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }



}
