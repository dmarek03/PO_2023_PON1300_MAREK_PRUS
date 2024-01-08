package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.*;

public class Simulation implements Runnable {


    private final WorldMap map;

    private List<Grass> grasses;

    private List<Animal> animals = new ArrayList<>();

    private boolean wait = true;
    private final int time;

    private final int reproductionEnergy;

    private void simWait() {
        if (wait) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Simulation(WorldMap map,List<Vector2d> positions, int time, int reproductionEnergy) {
        this.time = time;
        this.map = map;
        this.reproductionEnergy = reproductionEnergy;
        int counter = 0;
        for (Vector2d position : positions) {
            List<Integer> list = new ArrayList<>();
            boolean done = false;
            for (int i = 0 ; i < 10; i++) {
                if (!(done) && (counter == 0)) {
                    list.add(7);
                    done = true;
                } else if (!(done) && (counter == 1)) {
                    list.add(3);
                    done = true;
                } else if (done) {
                    list.add(0);
                }
            }
            list.add(0);
            Genotype gene = new Genotype((ArrayList<Integer>) list);
            Animal animal = new Animal(position, MapDirection.NORTH, 3, gene);
            animals.add(animal);
            map.place(animal, true);
            counter++;
            simWait();
        }
        Animal animal = new Animal(new Vector2d(0,0),MapDirection.NORTH,3,11);
        animals.add(animal);
        map.place(animal,true);
        this.grasses = map.getGrasses();
    }

    @Override
    public synchronized void run() {
//        System.out.println("WIDTH " + map.getWidth());
//        System.out.println("HEIGHT " + map.getHeight());

        Deque<Animal> dead = new ArrayDeque<>();

        for (int day = 0; day < time; day++) {

//            System.out.println("=====================================================DAY " + day + "=====================================================");

            if (dead.size() != 0) {
                Animal curr = dead.removeFirst();
                map.getAnimals().remove(curr);
                map.removeFromFertilized(curr.getPosition());
                int count = 0;
                while (day - curr.getAge() >= 2) {
                    if (dead.size() == 0) {break;}
                    curr = dead.removeFirst();
                    map.getAnimals().remove(curr);
                    map.removeFromFertilized(curr.getPosition());
                    count += 1;
                    simWait();
                }
//                    System.out.println(count);
                if (count == 0) {
                    if (day - curr.getAge() < 2) {
                        dead.addFirst(curr);
                        map.getAnimals().add(curr);
                    }
                }
            }



            for (int i = 0; i < animals.size(); i++) {
                Animal currentAnimal = animals.get(i);
                Genotype genotype = currentAnimal.getGenotype();
                List<Integer> genes = genotype.getGenes();
                int moveInd = day % genes.size();
                int move = genes.get(moveInd);
//                System.out.println("CURRENT MOVE " + moveInd + " " + move );
                map.move(currentAnimal,move);
                currentAnimal.incrementAge();
                if (currentAnimal.isDead()) {
                    animals.remove(currentAnimal);
                    map.addToFertilized(currentAnimal.getPosition());
                    dead.addLast(currentAnimal);
                }
                simWait();



            }

            Map<Vector2d,List<Animal>> orderedMap = new HashMap<>();

            for (Animal animal : animals) {
                List<Animal> tempList = new ArrayList<>();
                List<Animal> atPos = orderedMap.get(animal.getPosition());
                if (atPos != null) {
                    tempList = atPos;
                }
                tempList.add(animal);
                orderedMap.put(animal.getPosition(),tempList);
            }

            for (Map.Entry<Vector2d,List<Animal>> entry : orderedMap.entrySet()) {
                Vector2d position = entry.getKey();
                List<Animal> currAnimals = entry.getValue();

                Animal strongest = currAnimals.get(0);
                Animal second = null;

                if (currAnimals.size() > 1) {

                    Competition currComp = new Competition((ArrayList<Animal>) currAnimals);
                    List<Animal> strength = currComp.getTheStrongestCouple();

                    strongest = strength.get(0);
                    second = strength.get(1);
                }

//                boolean grassed = false;
                Grass grass = null;
                for (Grass g : map.getGrasses()) {
                    if (g.getPosition().equals(position)) {
//                        grassed = true;
                        grass = g;
                        break;
                    }

                }

//                System.out.println(position + " " + map.getGrasses());
//                System.out.println(grass);

//                pierwszy zawsze je
                if (grass != null) {
                    strongest.changeEnergy(grass.getGrassEnergy());
//                    System.out.println("Grass " + grass + " deleted from " + grass.getPosition() + " and position is " + position);
                    map.removeGrass(grass);
                }

//                rozmnazanie
                if ((second != null) && (second.getAnimalEnergy() >= reproductionEnergy)) {
//                    System.out.println("Animals at position " + second.getPosition() + " are reproducing");

                    Animal newborn = strongest.copulate(second);
//                    System.out.println(strongest.getGenotype() + " " + strongest.numberOfGenes);
//                    System.out.println(second.getGenotype() + " " + second.numberOfGenes);
//                    System.out.println("FATHER >>> " + strongest.AnimalToString1());
//                    System.out.println("MOTHER >>> " + second.AnimalToString1());
//                    System.out.println("CHILD >>> " + newborn.AnimalToString1());
                    map.place(newborn,true);
                    animals.add(newborn);
                    simWait();


                }



            }

//            for (Animal animal : animals) {
//                System.out.println("Animal at " + animal.getPosition() + " energy " + animal.getAnimalEnergy() + " num of kids " + animal.getChildren());
//            }

//            List<Animal> unalived = new ArrayList<>();
//
//            while (dead.size() != 0) {
//                unalived.add(dead.removeFirst());
//            }
//
//            System.out.println("UNALIVED >>> " + unalived);
//
//
//            for (Animal deadAnimal : unalived) {
//                dead.addLast(deadAnimal);
//            }


//            System.out.println(map.getGrasses());
            map.setGrasses(map.updateGrass());
//            map.setGrasses(List.of(new Grass(new Vector2d(0,0),10)));
            this.grasses = map.getGrasses();

            simWait();

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
