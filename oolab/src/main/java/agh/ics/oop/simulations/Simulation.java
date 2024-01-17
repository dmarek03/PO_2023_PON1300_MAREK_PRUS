package agh.ics.oop.simulations;

import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.elements.Grass;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.model.map.mapUtils.Vector2d;

import java.util.*;

public class Simulation implements Runnable {


    private Timer timer;
    private final WorldMap map;

    private List<Grass> grasses;

    private List<Animal> animals = new ArrayList<>();

    private final int geneLength;


    private final double percentageNumberofEnergyUsingCopulation;

    private final int  reproductionEnergy;

    private final int animalEnergy;
    private final Object pauseLock = new Object(); // Object for synchronization
    private boolean isRunning = false;

    private boolean swapMutation = false;

    private int time;


    public Simulation(WorldMap map, List<Vector2d> positions, int geneLength , int animalEnergy, double copulateEnergy, int reproductionEnergy, String mutationType, int time) {
        this.map = map;
        this.geneLength = geneLength;
        this.animalEnergy = animalEnergy;
        this.percentageNumberofEnergyUsingCopulation = copulateEnergy;
        this.reproductionEnergy = reproductionEnergy;
        this.grasses = map.getGrasses();
        if (mutationType.equals("Swap")) {
            this.swapMutation = true;
        }
        for (Vector2d position : positions) {
            Animal animal = new Animal(position, MapDirection.NORTH,animalEnergy,geneLength,this.swapMutation);
            animals.add(animal);
            map.place(animal, true);
        }
        this.time = time;
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

            timer.cancel();
            timer.purge();
        }
    }

    private void setUpTimer(){
        timer = new Timer(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                map.updateAll();
                map.mapChanged("Animal updated");
            }
        }, 0, time);


    }

}


