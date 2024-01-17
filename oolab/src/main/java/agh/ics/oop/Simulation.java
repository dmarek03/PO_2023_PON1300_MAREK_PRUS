package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.*;

public class Simulation implements Runnable {


    private Timer timer;
    private final WorldMap map;

    private List<Grass> grasses;

    private List<Animal> animals = new ArrayList<>();

    private boolean wait = true;

    private boolean paused = false;
    private final int time;
    private final int geneLength;

    private int currentDay = 0;

    private final double percentageNumberofEnergyUsingCopulation;

    private final int  reproductionEnergy;

    private final int animalEnergy;
    private final Object pauseLock = new Object(); // Object for synchronization
    private boolean isRunning = false;


    public Simulation(WorldMap map,List<Vector2d> positions, int time,int geneLength , int animalEnergy,double copulateEnergy, int reproductionEnergy) {
        this.time = time;
        this.map = map;
        this.geneLength = geneLength;
        this.animalEnergy = animalEnergy;
        this.percentageNumberofEnergyUsingCopulation = copulateEnergy;
        this.reproductionEnergy = reproductionEnergy;
        for (Vector2d position : positions) {
            Animal animal = new Animal(position, MapDirection.NORTH,animalEnergy,geneLength);
            animals.add(animal);
            map.place(animal, true);
        }
        this.grasses = map.getGrasses();
    }

    @Override
    public void run() {
        startSimulation();
    }

    public void startSimulation() {
        if (!isRunning) {
            isRunning = true;

            setUpTimer();
        }
    }

    public void stopSimulation() {
        if (isRunning) {
            isRunning = false;

            // Zatrzymywanie timera
            timer.cancel();
            timer.purge();
        }
    }

    private void setUpTimer(){
        // Tworzenie i uruchamianie timera
        timer = new Timer(true);
        for (Animal animal : animals) {
            map.moveAnimal(animal);
        }


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                map.updateAll();
                map.mapChanged("Animal updated");
            }
        }, 0, 300);


    }

}

//package agh.ics.oop;
//
//import agh.ics.oop.model.*;
//
//import java.util.*;
//
//public class Simulation implements Runnable {
//
//
//    private final WorldMap map;
//
//    private List<Grass> grasses;
//
//    private List<Animal> animals = new ArrayList<>();
//
//    private boolean wait = true;
//
//    private boolean paused = false;
//    private final int time;
//    private final int geneLength;
//
//    private int currentDay = 0;
//
//    private final double percentageNumberofEnergyUsingCopulation;
//
//    private final int  reproductionEnergy;
//
//    private final int animalEnergy;
//    private final Object pauseLock = new Object(); // Object for synchronization
//    private boolean stopped = false;
//
//    public synchronized void stop() {
//        stopped = true;
//    }
//
//    public synchronized void resume() {
//        stopped = false;
//        notifyAll();
//    }
//
//    private void simWait() {
//        if (wait) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public Simulation(WorldMap map,List<Vector2d> positions, int time,int geneLength , int animalEnergy,double copulateEnergy, int reproductionEnergy) {
//        this.time = time;
//        this.map = map;
//        this.geneLength = geneLength;
//        this.animalEnergy = animalEnergy;
//        this.percentageNumberofEnergyUsingCopulation = copulateEnergy;
//        this.reproductionEnergy = reproductionEnergy;
//        for (Vector2d position : positions) {
//            ArrayList<Integer> list = new ArrayList<>();
//            for (int i = 0 ; i < 10; i++) {
//                list.add(positions.indexOf(position));
//            }
//            Genotype gene = new Genotype(list);
//            Animal animal = new Animal(position, MapDirection.NORTH, animalEnergy, gene);
//            animals.add(animal);
//            map.place(animal, true);
//            simWait();
//        }
//        this.grasses = map.getGrasses();
//    }
//
//    @Override
//    public synchronized void run() {
//
//        Deque<Animal> dead = new ArrayDeque<>();
//
//        for (int day = 0; day < time; day++) {
//
//            if (paused || stopped) {
//                currentDay = day; // Save the current day
//                synchronized (pauseLock) {
//                    while (paused || stopped) {
//                        try {
//                            pauseLock.wait(); // Wait for resume
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//            }
//
////            System.out.println("=====================================================DAY " + day + "=====================================================");
//            map.mapChanged("Day " + day);
//            if (!dead.isEmpty()) {
//                Animal curr = dead.removeFirst();
//                map.getAnimals().remove(curr);
//                map.removeAnimalBool(curr);
//                map.removeFromFertilized(curr.getPosition());
//                int count = 0;
//                while (day - curr.getAge() >= 2) {
//                    if (dead.isEmpty()) {break;}
//                    curr = dead.removeFirst();
//                    map.getAnimals().remove(curr);
//                    map.removeAnimalBool(curr);
//                    map.removeFromFertilized(curr.getPosition());
////                    System.out.println("Animal " + curr + " died more than 2 days ago, and it's body should be removed");
//                    count += 1;
////                    simWait();
//                }
//
//                if (count == 0) {
//                    if (day - curr.getAge() < 2) {
//                        dead.addFirst(curr);
//                        map.getAnimals().add(curr);
//                        map.addAnimalBool(curr);
//                    }
//                }
//            }
//
//
//
//            for (int i = 0; i < animals.size(); i++) {
//                Animal currentAnimal = animals.get(i);
//                Genotype genotype = currentAnimal.getGenotype();
//                List<Integer> genes = genotype.getGenes();
//                int moveInd = day % genes.size();
//                int move = genes.get(moveInd);
////                System.out.println("CURRENT MOVE " + moveInd + " " + move );
//                map.move(currentAnimal,move);
//                currentAnimal.incrementAge();
//                if (currentAnimal.isDead()) {
//                    animals.remove(currentAnimal);
//                    map.addToFertilized(currentAnimal.getPosition());
//                    dead.addLast(currentAnimal);
//                }
//                simWait();
//
//
//
//            }
//
//            Map<Vector2d,List<Animal>> orderedMap = new HashMap<>();
//
//            for (Animal animal : animals) {
//                List<Animal> tempList = new ArrayList<>();
//                List<Animal> atPos = orderedMap.get(animal.getPosition());
//                if (atPos != null) {
//                    tempList = atPos;
//                }
//                tempList.add(animal);
//                orderedMap.put(animal.getPosition(),tempList);
//            }
//
//            for (Map.Entry<Vector2d,List<Animal>> entry : orderedMap.entrySet()) {
//                Vector2d position = entry.getKey();
//                List<Animal> currAnimals = entry.getValue();
//
//                Animal strongest = currAnimals.get(0);
//                Animal second = null;
//
//                if (currAnimals.size() > 1) {
//
//                    Competition currComp = new Competition((ArrayList<Animal>) currAnimals);
//                    List<Animal> strength = currComp.getTheStrongestCouple();
//
//                    strongest = strength.get(0);
//                    second = strength.get(1);
//                }
//
////                boolean grassed = false;
//                Grass grass = null;
//                for (Grass g : map.getGrasses()) {
//                    if (g.getPosition().equals(position)) {
////                        grassed = true;
//                        grass = g;
//                        break;
//                    }
//
//                }
//
////                System.out.println(position + " " + map.getGrasses());
////                System.out.println(grass);
//
////                pierwszy zawsze je
//                if (grass != null) {
//                    strongest.changeEnergy(grass.getGrassEnergy());
//                    strongest.incrementNumberOfConsumedGrass();
////                    System.out.println("Grass " + grass + " deleted from " + grass.getPosition() + " and position is " + position);
//                    map.removeGrass(grass);
//                    map.removeGrassBool(grass);
//                }
//
////                rozmnazanie
//                if ((second != null) && (second.getAnimalEnergy() >= reproductionEnergy)) {
////                    System.out.println("Animals at position " + second.getPosition() + " are reproducing");
//
//                    Animal newborn = strongest.copulate(second);
////                    System.out.println(strongest.getGenotype() + " " + strongest.numberOfGenes);
////                    System.out.println(second.getGenotype() + " " + second.numberOfGenes);
////                    System.out.println("FATHER >>> " + strongest.AnimalToString1());
////                    System.out.println("MOTHER >>> " + second.AnimalToString1());
////                    System.out.println("CHILD >>> " + newborn.AnimalToString1());
//                    map.place(newborn,true);
//                    animals.add(newborn);
//                    simWait();
//
//
//                }
//
//
//
//            }
//
////            for (Animal animal : animals) {
////                System.out.println("Animal at " + animal.getPosition() + " energy " + animal.getAnimalEnergy() + " num of kids " + animal.getChildren());
////            }
//
////            List<Animal> unalived = new ArrayList<>();
////
////            while (dead.size() != 0) {
////                unalived.add(dead.removeFirst());
////            }
////
////            System.out.println("UNALIVED >>> " + unalived);
////
////
////            for (Animal deadAnimal : unalived) {
////                dead.addLast(deadAnimal);
////            }
//
//
////            System.out.println(map.getGrasses());
//            map.setGrasses(map.updateGrass());
////            map.setGrasses(List.of(new Grass(new Vector2d(0,0),10)));
//            this.grasses = map.getGrasses();
//            currentDay ++;
//            simWait();
//
//        }
//
//    }
//
//    public int getCurrentDay() {
//        return currentDay;
//    }
//
//    public int getTime(){
//        return time;
//    }
//
////    public Simulation(List<MoveDirection> moves, WorldMap map) {
////        this.moves = moves;
////        this.map = map;
////        this.wait = true;
////        List<Animal> allAnimals = new ArrayList<>();
////        map.getAnimals().get().forEach(animal -> {
////            allAnimals.add((Animal) animal);
////        });
////        this.animals = allAnimals;
////    }
////
////    public Simulation(List<MoveDirection> moves,List<Vector2d> positions, WorldMap<WorldElement , Vector2d> map) {
////        this.moves = moves;
////        List<Animal> all = new ArrayList<>();
////        positions.forEach(position -> {
////            Animal animal = new Animal(position,MapDirection.NORTH,100,8);
////            try {
////                if (map.place(animal,true)) {
////                    all.add(animal);
////                }
////            } catch (PositionAlreadyOccupiedException e) {
////                System.err.println("Error: " + e.getMessage());
////            }
////        });
////        this.animals = all;
////        this.map = map;
////    }
////
////    public Simulation(List<MoveDirection> moves,List<Vector2d> positions, WorldMap map, boolean placeInform) {
////        this.moves = moves;
////        List<Animal> all = new ArrayList<>();
////        positions.forEach(position -> {
////            Animal animal = new Animal(position,MapDirection.NORTH,100,8);
////            try {
////                if (map.place(animal,placeInform)) {
////                    all.add(animal);
////                }
////            } catch (PositionAlreadyOccupiedException e) {
////                System.err.println("Error: " + e.getMessage());
////            }
////        });
////        this.animals = all;
////        this.map = map;
////    }
////
////
////    public int getNoAnimals() {return this.animals.size();}
////    public int getNoMoves() {return this.moves.size();}
////    public void run() {
////        int allAnimals = this.getNoAnimals();
////        for (int i = 0; i < getNoMoves(); i++) {
////            int animalInd = (i % allAnimals);
////            Animal currAnimal = animals.get(animalInd);
////            map.move(currAnimal, this.moves.get(i).ordinal());
////            animals.set(animalInd,currAnimal);
////            if (wait) {
////                try {
////                    Thread.sleep(500);
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
////            }
////        }
////    }
//
//
//
//}
