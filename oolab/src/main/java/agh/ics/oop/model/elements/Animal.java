package agh.ics.oop.model.elements;
import agh.ics.oop.model.genes.Genotype;
import agh.ics.oop.model.map.mapUtils.MoveValidator;
import agh.ics.oop.model.map.mapUtils.Vector2d;
import agh.ics.oop.model.enums.HealthEnum;
import agh.ics.oop.model.enums.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.lang.Math.max;


public class Animal implements WorldElement {
    private MapDirection currentOrientation;
    private Vector2d currentPosition;
    private double energyToCopulation = 0.3;
    private int animalEnergy;
    public int numberOfGenes;
    private final Genotype genotype;
    private int age;
    private int children = 0;
    public static final int GENES_RANGE = 7;
    public static final Vector2d UPPER_RIGHT_LIMIT = new Vector2d(4, 4);
    public static final Vector2d LOWER_LEFT_LIMIT = new Vector2d(0, 0);

    private int numberOfConsumedGrass = 0;

    private int activatedGen = 0;

    private int maxHealth;

    public List<Animal> allChildren = new ArrayList<>();

    public List<Animal> parents;

    public UUID id = UUID.randomUUID();


    private boolean swapMutation;

    public Animal(){
        this.currentOrientation = MapDirection.NORTH;
        this.currentPosition = new Vector2d(2, 2);
        this.animalEnergy = 100;
        this.genotype = new Genotype(GENES_RANGE,numberOfGenes=10);
        this.age = 0;
        this.maxHealth = 100;

    }

    public Animal(Vector2d newPosition,MapDirection orientation ,int energy,int numberOfGenes, boolean swapMutation, double copEnergy){
        this.numberOfGenes = numberOfGenes;
        this.currentPosition = newPosition;
        this.currentOrientation = orientation;
        this.animalEnergy = energy;
        this.genotype = new Genotype(GENES_RANGE, numberOfGenes);
        this.age = 0;
        this.maxHealth = energy;
        this.swapMutation = swapMutation;
    }

    // Using during copulation
    public Animal(Vector2d newPosition ,MapDirection currentOrientation,int energy, Genotype genotype, double copEnergy){
        this.currentPosition = newPosition;
        this.currentOrientation = currentOrientation;
        this.animalEnergy = energy;
        this.genotype = genotype;
        this.age = 0;
        this.numberOfGenes = genotype.getNumberOfGenes();
    }



    public String orientationToString(){
        return this.currentOrientation.toString();


    }
    public void changeEnergy(int value){
        this.animalEnergy = max(animalEnergy + value, 0);
    }

    public boolean isDead(){
        return this.animalEnergy == 0;
    }

    public Animal copulate(Animal mother){
        int childEnergy = (int)((energyToCopulation)*(mother.animalEnergy + this.animalEnergy));
        List<Integer> childGenes;
        if(this.animalEnergy > mother.animalEnergy){
            childGenes = divideGenotype(this, mother);

        }else {
            childGenes =  divideGenotype(mother, this);
        }
        this.changeEnergy(-(int)((energyToCopulation)*this.animalEnergy));
        mother.changeEnergy(-(int)((energyToCopulation)*mother.animalEnergy));

        Genotype childGenotype = new Genotype(GENES_RANGE, numberOfGenes, childGenes);
        childGenotype.randomMutations();

        if (swapMutation) {
            childGenotype.genesSubstitution();
        }

        Animal newborn = new Animal(mother.currentPosition, mother.currentOrientation, childEnergy, childGenotype,this.energyToCopulation);

        this.allChildren.add(newborn);
        this.incrementNumberOfChildren();
        mother.allChildren.add(newborn);
        mother.incrementNumberOfChildren();
        newborn.parents = new ArrayList<>();
        newborn.parents.add(this);
        newborn.parents.add(mother);


        return newborn;

    }

    private List<Integer> divideGenotype(Animal animal1, Animal animal2) {
        int total_energy = animal1.animalEnergy + animal2.animalEnergy;
        Random random = new Random();
        boolean isRight = 1 == random.nextInt(2);
        List<Integer> genes = new ArrayList<>();
        int divideIdx = (animal1.animalEnergy*numberOfGenes / total_energy);
        if (isRight){
            for(int i =0; i < numberOfGenes;i++){
                if(i <= divideIdx) {
                    genes.add(animal1.genotype.getGenes().get(i));
                }else genes.add(animal2.genotype.getGenes().get(i));
            }
        }else{
            for(int i =0; i < numberOfGenes;i++){
                if(i >= numberOfGenes-divideIdx) {
                    genes.add(animal1.genotype.getGenes().get(i));
                }else genes.add(animal2.genotype.getGenes().get(i));
            }
        }
        return genes;
    }
    public String AnimalToString1(){
        return orientationToString()+" "+animalEnergy+" "+age+" "+children + " " + this.getPosition();
    }
    public String AnimalToString(){
        return id.toString();
    }

    @Override
    public String toString(){
        return AnimalToString();
    }


    public boolean isAt(Vector2d position){
        return currentPosition.equals(position);
    }

    public boolean canMoveTo(Vector2d position){
        return LOWER_LEFT_LIMIT.precedes(position) && UPPER_RIGHT_LIMIT.follows(position);
    }

    public Vector2d calculateNextPosition(int direction){
        return currentPosition.add(currentOrientation.changeOrientation(direction).toUnitVector());
    }
    public void move(int direction, MoveValidator validator){

        if (isDead()) {return;}

        Vector2d newPosition = calculateNextPosition(direction);
        if(validator.canMoveTo(newPosition)){
            currentOrientation = currentOrientation.changeOrientation(direction);
            currentPosition = newPosition;
        }
    }

    public void setPosition(Vector2d newPosition){this.currentPosition = newPosition;}

    public void changeOrientation(MapDirection newOrientation) {this.currentOrientation = newOrientation;}
    public MapDirection getOrientation() {
        return currentOrientation;
    }
    @Override
    public Vector2d getPosition() {
        return currentPosition;
    }

    public void  incrementAge() {
        this.age ++;
    }
    public void incrementNumberOfChildren(){this.children++;}
    public int getAnimalEnergy() {
        return animalEnergy;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int number) {this.age = number;}

    public int getChildren() {
        return children;
    }
    public List<Animal> getParents(){
        return parents;
    }

    public double getEnergyToCopulation(){
        return energyToCopulation;
    }

    public void setEnergyToCopulation(double energyToCopulation){
        this.energyToCopulation = energyToCopulation;
    }

    private int deathdate = 0;
    public int getDeathDate() {
        return this.deathdate;
    }

    public void setDeathDate(int date) {
        this.deathdate = date;
    }


    @Override
    public String path() {
        if (isDead()) {
            return "/fields/dead.png";
        }
        return switch (this.getOrientation()) {
            case NORTH -> "/orientations/north.png";
            case NORTHEAST -> "/orientations/northeast.png";
            case EAST -> "/orientations/east.png";
            case SOUTHEAST -> "/orientations/southeast.png";
            case SOUTH -> "/orientations/south.png";
            case SOUTHWEST -> "/orientations/southwest.png";
            case WEST -> "/orientations/west.png";
            case NORTHWEST -> "/orientations/northwest.png";
        };
    }


    public HealthEnum healthEnum(int energy) {
        if (this.age == 0) {
            maxHealth = energy;
        }

        if (((energy * 2) > maxHealth) && (energy < maxHealth)) {
            return HealthEnum.BAD;
        } else if ((energy < maxHealth) || (isDead())) {
            return HealthEnum.VERY_BAD;
        } else if (((energy / 2) < maxHealth) && (energy > maxHealth)) {
            return HealthEnum.GOOD;
        } else if (energy > maxHealth) {
            return HealthEnum.VERY_GOOD;
        } else {
            return HealthEnum.NEUTRAL;
        }
    }

    @Override
    public String healthPath() {
        return switch (healthEnum(this.getAnimalEnergy())) {
            case VERY_BAD -> "/masks/healthState/veryBadHealth.png";
            case BAD -> "/masks/healthState/badHealth.png";
            case NEUTRAL -> "/masks/healthState/neutralHealth.png";
            case GOOD -> "/masks/healthState/goodHealth.png";
            case VERY_GOOD -> "/masks/healthState/veryGoodHealth.png";
        };
    }

    @Override
    public String ASCII() {
        if (isDead()) {
            return "X";
        }
        return switch (this.getOrientation()) {
            case NORTH -> "↑";
            case NORTHEAST -> "↗";
            case EAST -> "→";
            case SOUTHEAST -> "↘";
            case SOUTH -> "↓";
            case SOUTHWEST -> "↙";
            case WEST -> "←";
            case NORTHWEST -> "↖";
        };
    }

    @Override
    public String color() {
        return switch (healthEnum(this.getAnimalEnergy())) {
            case VERY_BAD -> "#FF0000";
            case BAD -> "#FF9334";
            case NEUTRAL -> "#FFF500";
            case GOOD -> "#00E9FF";
            case VERY_GOOD -> "#00158B";
        };
    }


    public int getXPosition() {
        return this.getPosition().getX();
    }

    public int getYPosition() {
        return this.getPosition().getY();
    }

    public int getNumberOfConsumedGrass() {
        return numberOfConsumedGrass;
    }

    public void incrementNumberOfConsumedGrass() {
        this.numberOfConsumedGrass ++;
    }

    public int getActivatedGen() {
        return activatedGen;
    }

    public void setActivatedGen(int activatedGen) {
        this.activatedGen = activatedGen;
    }
}
