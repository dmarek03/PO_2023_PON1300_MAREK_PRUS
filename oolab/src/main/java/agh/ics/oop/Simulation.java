package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {


    private final WorldMap map;

    private List<Grass> grasses;

    private List<Animal> animals = new ArrayList<>();

    private boolean wait = false;
    private final int time;


    public Simulation(WorldMap map,List<Vector2d> positions, int time) {
        this.time = time;
        this.map = map;
        int counter = 0;
        for (Vector2d position : positions) {
            List<Integer> list = new ArrayList<>();
            list.add(0);
            Genotype baseGene = new Genotype(7,1, (ArrayList<Integer>) list);
            MapDirection orient = MapDirection.WEST;
            if (counter == 0) {orient = MapDirection.EAST;}
            Animal animal = new Animal(position,orient,100,baseGene);
            animals.add(animal);
            try {
                map.place(animal, true);
            } catch (PositionAlreadyOccupiedException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        this.grasses = map.getGrasses();
    }

    @Override
    public void run() {
        for (int day = 0; day < time; day++) {
            for (int i = 0; i < animals.size(); i++) {
                Animal currentAnimal = animals.get(i);
                Genotype genotype = currentAnimal.getGenotype();
                List<Integer> genes = genotype.getGenes();
                int moveInd = day % genes.size();
                int move = genes.get(moveInd);
                map.move(currentAnimal,move);
            }
            this.grasses = map.getGrasses();
        }

    }

//    public Simulation(List<MoveDirection> moves, WorldMap map) {
//        this.moves = moves;
//        this.map = map;
//        this.wait = true;
//        List<Animal> allAnimals = new ArrayList<>();
//        map.getAnimals().get().forEach(animal -> {
//            allAnimals.add((Animal) animal);
//        });
//        this.animals = allAnimals;
//    }
//
//    public Simulation(List<MoveDirection> moves,List<Vector2d> positions, WorldMap<WorldElement , Vector2d> map) {
//        this.moves = moves;
//        List<Animal> all = new ArrayList<>();
//        positions.forEach(position -> {
//            Animal animal = new Animal(position,MapDirection.NORTH,100,8);
//            try {
//                if (map.place(animal,true)) {
//                    all.add(animal);
//                }
//            } catch (PositionAlreadyOccupiedException e) {
//                System.err.println("Error: " + e.getMessage());
//            }
//        });
//        this.animals = all;
//        this.map = map;
//    }
//
//    public Simulation(List<MoveDirection> moves,List<Vector2d> positions, WorldMap map, boolean placeInform) {
//        this.moves = moves;
//        List<Animal> all = new ArrayList<>();
//        positions.forEach(position -> {
//            Animal animal = new Animal(position,MapDirection.NORTH,100,8);
//            try {
//                if (map.place(animal,placeInform)) {
//                    all.add(animal);
//                }
//            } catch (PositionAlreadyOccupiedException e) {
//                System.err.println("Error: " + e.getMessage());
//            }
//        });
//        this.animals = all;
//        this.map = map;
//    }
//
//
//    public int getNoAnimals() {return this.animals.size();}
//    public int getNoMoves() {return this.moves.size();}
//    public void run() {
//        int allAnimals = this.getNoAnimals();
//        for (int i = 0; i < getNoMoves(); i++) {
//            int animalInd = (i % allAnimals);
//            Animal currAnimal = animals.get(animalInd);
//            map.move(currAnimal, this.moves.get(i).ordinal());
//            animals.set(animalInd,currAnimal);
//            if (wait) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }



}
