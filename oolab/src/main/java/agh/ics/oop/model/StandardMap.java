package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;
import com.google.common.base.Optional;

import java.util.*;
import java.util.stream.Collectors;

public class StandardMap implements WorldMap{
    private final int width;
    private final int height;
    private int grassNumber;
    private List<Animal> animals = new ArrayList<>();
    private List<Grass> grasses;
    public List<Vector2d> fertilized = new ArrayList<>();
    private int grassEnergy;
    private List<MapChangeListener> observers = new ArrayList<>();

    private int userGrass;

    private final UUID id;

    private final double size;

    private final Vector2d lowerLeftLimit;
    private final Vector2d upperRightLimit ;

    public StandardMap(int width, int height, int grassNumber,int grassEnergy, double size, int userGrass) {
        this.width = width;
        this.height = height;
        this.grassNumber = grassNumber;
        this.grassEnergy = grassEnergy;
        this.size = size;
        this.userGrass = userGrass;
        this.lowerLeftLimit = new Vector2d(0, 0);
        this.upperRightLimit = new Vector2d(width-1, height-1);
        this.grasses = placeGrass();
        this.id = UUID.randomUUID();

    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return lowerLeftLimit.precedes(position) && upperRightLimit.follows(position);
    }



    @Override
    public boolean place(Animal animal, boolean inform) {
        if(canMoveTo(animal.getPosition())){
            animals.add(animal);
            mapChanged("Animal placed at %s".formatted(animal.getPosition()));

            return true;
        } else {
            mapChanged("Animal not able to be placed at %s".formatted(animal.getPosition()));
            return false;
        }


    }


    // to jak zrobisz RandomPositionGenerator to trzeba zmienić

    // pa ->
    // są dwie opcje, do pierwszego stawiania trawy i do update'owania.
    // Dodałem pole size, żeby zmieniało wartość rozkładu do gaussa
    public ArrayList<Grass> placeGrass(){
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, grassNumber, size);
        ArrayList<Grass> grasses1 = new ArrayList<>();
        for(Object grassPosition : randomPositionGenerator) {
            grasses1.add(new Grass((Vector2d) grassPosition, grassEnergy));

        }
        return grasses1;

    }

    public ArrayList<Grass> updateGrass() {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, grasses, fertilized, userGrass, size);
        ArrayList<Grass> grasses1 = new ArrayList<>();
        for(Object grassPosition : randomPositionGenerator) {
            grasses1.add(new Grass((Vector2d) grassPosition, grassEnergy));
        }
        return grasses1;
    }

    @Override
    public void move(Animal animal, int direction) {
        Vector2d newPosition = animal.calculateNextPosition(direction);
        MapDirection oldOrientation = animal.getOrientation();

        Vector2d previousPosition = animal.getPosition();

        animal.changeEnergy(-1);

        if (newPosition.getY() == height | newPosition.getY() < 0) {
            animals.remove(animal);
            animal.changeOrientation(oldOrientation.changeOrientation(4));
            animals.add(animal);
            mapChanged("Animal moved from %s to %s in direction %s".formatted(previousPosition,animal.getPosition(), animal.orientationToString()));
        }
        else if(newPosition.getX() == width | newPosition.getX() < 0){
            animals.remove(animal);
            int newX = newPosition.getX();
            if (newX == -1) {newX = width-1;}
            animal.setPosition(new Vector2d(newX % getWidth(), newPosition.getY()));
            animals.add(animal);
            mapChanged("Animal moved from %s to %s in direction %s".formatted(previousPosition,animal.getPosition(), animal.orientationToString()));
        }else {
            animals.remove(animal);
            animal.move(direction, this);
            animal.setActivatedGen(animal.getGenotype().getGenes().indexOf(direction));
            animals.add(animal);
            mapChanged("Animal moved from %s to %s in direction %s".formatted(previousPosition, animal.getPosition(), animal.orientationToString()));
        }

    }

    @Override
    public boolean isOccupied(Vector2d position) {

        if (isOccupiedByAnimal(position)) {return true;}
        return isOccupiedByGrass(position);
    }
    public boolean isOccupiedByAnimal(Vector2d position){
        for(Animal a: animals){
            if(a.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }

    public boolean isOccupiedByGrass(Vector2d position){
        for(Grass g : grasses){
            if (g.getPosition().equals(position)){
                return true;
            }
        }
        return false;
    }
    // Zmieniłem objectAt tak,że zwraca teraz listę zwierząt na danej pozycji,albo traw jeśli nie ma zwierząt
    // a jeśli dana pozycja jest pusta to zwraca null
    @Override
    public List<WorldElement> objectAt(Vector2d position) {
        List<WorldElement> animalsAtPosition = new  ArrayList<>();
        List<WorldElement>  grassesAtPosition = new ArrayList<>();
        if(isOccupiedByAnimal(position)){
            for(Animal a : animals){
                if (a.getPosition().equals(position)){
                    animalsAtPosition.add(a);

                }
            }
        }else if(isOccupiedByGrass(position)){
            for(Grass g : grasses){
                if(g.getPosition().equals(position)){
                    grassesAtPosition.add(g);

                }
            }
        }

        if(!animalsAtPosition.isEmpty()) {return  animalsAtPosition;}
        if(!grassesAtPosition.isEmpty()) {return  grassesAtPosition;}

        return null;
    }

    @Override
    public Map<Vector2d, WorldElement> getElements() {
        Map<Vector2d, WorldElement> elements = new HashMap<>();
        for(Animal a : animals){
            elements.put(a.getPosition(), a);
        }
        for(Grass g : grasses){
            elements.put(g.getPosition(), g);
        }
        return elements ;
    }
    public String toString(){
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(getCurrentBounds().lowerleft(), getCurrentBounds().upperright());

    }

    @Override
    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    @Override
    public void mapChanged(String message) {
        notifyObservers(message);

    }

    @Override
    public void notifyObservers(String message) {
        for(MapChangeListener o : observers){
            o.mapChanged(this, message);
        }

    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(lowerLeftLimit, upperRightLimit);
    }

    @Override
    public UUID getId() {
        return id;
    }
    // tutaj jest problem dlatego, że AbstractWorldMap oczekuje tutaj Hashmapy ale jak nie będziemy tej klasy używać to lepiej zostawić tak
    @Override
    public List<Animal> getAnimals() {return this.animals;}

    // to samo co wyżej
    @Override
    public List<Grass> getGrasses() {
        return this.grasses;
    }

    // tutaj to pytanie jaki chcesz mieć porządek zwracania czy taki jak w Competition czy po pozycjach

    // pa ->
    // nie wiem czy będziemy tego w ogóle używać, ale jak już, to po pozycjach, a w każdej pozycji wg competition
    // w sumie można zrobic tak, że po tym właśnie będziemy liczyć kto je i kto się rozmnaża.
    // wtedy mogłoby to zwracać Map<Vector2d,List<Animal>>
//    public Map<Vector2d,List<WorldElement>> getOrderedAnimals() {
//        return null;
//    }
    // coś w tym stylu


    public List<Animal> getOrderedAnimals() {
        this.getAnimals().sort(
                Comparator.comparing(Animal::getXPosition)
                        .thenComparing(Animal::getYPosition)
                        .thenComparing(Animal::getAnimalEnergy)
                        .thenComparing(Animal::getAge)
                        .thenComparing(Animal::getChildren)
//                        .reversed()
        );
        return this.getAnimals();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setGrasses(List<Grass> newGrasses) {
        this.grasses = newGrasses;
    }

    public void removeGrass(Grass grass) {
//        System.out.println("Grass " + grass + " deleted from " + grass.getPosition());
        this.grasses.remove(grass);
    }

    public Vector2d getLowerLeftLimit() {
        return lowerLeftLimit;
    }

    public Vector2d getUpperRightLimit() {
        return upperRightLimit;
    }

    public int getGrassNumber() {
        return grassNumber;
    }

    public void addToFertilized(Vector2d pos) {
        this.fertilized.add(pos);
    }

    public void removeFromFertilized(Vector2d pos) {
        this.fertilized.remove(pos);
    }
}
