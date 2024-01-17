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


