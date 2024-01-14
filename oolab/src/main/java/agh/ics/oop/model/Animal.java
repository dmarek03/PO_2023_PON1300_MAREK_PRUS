package agh.ics.oop.model;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.max;


public class Animal implements WorldElement {
    private MapDirection currentOrientation;
    private Vector2d currentPosition;
    public final double energyToCopulation = 0.3;
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

    public ArrayList<Animal> allChildren = new ArrayList<>();

    public ArrayList<Animal> parents;

    // Trzeba DFS zeby dostać potomków zwierzaka


    public Animal(){
        this.currentOrientation = MapDirection.NORTH;
        this.currentPosition = new Vector2d(2, 2);
        this.animalEnergy = 100;
        this.genotype = new Genotype(GENES_RANGE,numberOfGenes=10);
        this.age = 0;

    }

    public Animal(Vector2d newPosition,MapDirection orientation ,int energy,int numberOfGenes){
        this.numberOfGenes = numberOfGenes;
        this.currentPosition = newPosition;
        this.currentOrientation = orientation;
        this.animalEnergy = energy;
        this.genotype = new Genotype(GENES_RANGE, numberOfGenes);
        this.age = 0;
    }

    // Using during copulation
    public Animal(Vector2d newPosition ,MapDirection currentOrientation,int energy, Genotype genotype){
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
    // The method will be used in class GlobalMap to add energy to animalEnergy when it ate the grass
    public void changeEnergy(int value){
        this.animalEnergy = max(animalEnergy + value, 0);
    }

    public boolean isDead(){
        return this.animalEnergy == 0;
    }

    public Animal copulate(Animal mother){
//        System.out.println(numberOfGenes);
        int childEnergy = (int)((energyToCopulation)*(mother.animalEnergy + this.animalEnergy));
        ArrayList<Integer> childGenes;
        if(this.animalEnergy > mother.animalEnergy){
            childGenes = divideGenotype(this, mother);

        }else {
            childGenes =  divideGenotype(mother, this);
        }
        this.changeEnergy(-(int)((energyToCopulation)*this.animalEnergy));
        mother.changeEnergy(-(int)((energyToCopulation)*mother.animalEnergy));

        Genotype childGenotype = new Genotype(GENES_RANGE, numberOfGenes, childGenes);
        childGenotype.randomMutations();
//        System.out.println("numberOfGenes " + numberOfGenes);

        Animal newborn = new Animal(mother.currentPosition, mother.currentOrientation, childEnergy, childGenotype);

        this.allChildren.add(newborn);
        this.children++;
        mother.allChildren.add(newborn);
        mother.children++;
        newborn.parents = new ArrayList<>();
        newborn.parents.add(this);
        newborn.parents.add(mother);


        return newborn;

    }

    private ArrayList<Integer> divideGenotype(Animal animal1, Animal animal2) {
        int total_energy = animal1.animalEnergy + animal2.animalEnergy;
        Random random = new Random();
        boolean isRight = 1 == random.nextInt(2);
//        System.out.println(isRight);
        ArrayList<Integer> genes = new ArrayList<>();
        int divideIdx = (animal1.animalEnergy*numberOfGenes / total_energy);
//        System.out.println(divideIdx);
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
        if (this.isDead()) {
            return "X";
        }
        return orientationToString();
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

    public Vector2d calculateNextPosition(Integer direction){
        return currentPosition.add(currentOrientation.changeOrientation(direction).toUnitVector());
    }
    public void move(int direction, MoveValidator validator){
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

    public int getChildren() {
        return children;
    }

    @Override
    public String path() {
        if (isDead()) {
            return "/dead.png";
        }
        return switch (this.getOrientation()) {
            case NORTH -> "/north.png";
            case NORTHEAST -> "/northeast.png";
            case EAST -> "/east.png";
            case SOUTHEAST -> "/southeast.png";
            case SOUTH -> "/south.png";
            case SOUTHWEST -> "/southwest.png";
            case WEST -> "/west.png";
            case NORTHWEST -> "/northwest.png";
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
