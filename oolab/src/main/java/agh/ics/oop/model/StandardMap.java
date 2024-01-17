package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;
import com.google.common.base.Optional;

import java.util.*;
import java.util.stream.Collectors;

public class StandardMap implements WorldMap{
    private final int width;
    private final int height;
    private final  int grassNumber;
    private final  List<Animal> animals = new ArrayList<>();
    private List<Grass> grasses;
    public List<Vector2d> fertilized = new ArrayList<>();
    private final  int grassEnergy;
    private final List<MapChangeListener> observers = new ArrayList<>();

    private final  int userGrass;

    private final double copulateEnergy;

    private final int reproductionEnergy;

    private final UUID id;

    private final double size;

    private final Vector2d lowerLeftLimit;
    private final Vector2d upperRightLimit ;

    private boolean[][] grassMatrix;
    private boolean[][] animalMatrix;

    Deque<Animal> dead = new ArrayDeque<>();

    public int day = 0;

    private Map<Vector2d,Boolean> todayUpdated = new HashMap<>();

    private Map<Vector2d,List<Animal>> animalMap = new HashMap<>();
    private Map<Vector2d,Grass> grassMap = new HashMap<>();

    public StandardMap(int width, int height, int grassNumber,int grassEnergy, double size, int userGrass, double copulateEnergy,int reproductionEnergy) {
        this.width = width;
        this.height = height;
        this.grassNumber = grassNumber;
        this.grassEnergy = grassEnergy;
        this.size = size;
        this.userGrass = userGrass;
        this.lowerLeftLimit = new Vector2d(0, 0);
        this.upperRightLimit = new Vector2d(width-1, height-1);
        this.copulateEnergy = copulateEnergy;
        this.reproductionEnergy = reproductionEnergy;
        this.grassMatrix = new boolean[width][height];
        this.animalMatrix = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grassMatrix[x][y] = false;
                animalMatrix[x][y] = false;
            }
        }
        this.grasses = placeGrass();
        this.id = UUID.randomUUID();

    }

    private List<Animal> allEverLivedAnimals = new ArrayList<>();

    public List<Animal> getAllEverLivedAnimals() {
        return allEverLivedAnimals;
    }

    public void addGrassBool(Grass grass) {
        Vector2d position = grass.getPosition();
        grassMap.put(position,grass);
        int x = position.getX();
        int y = position.getY();
        grassMatrix[x][y] = true;
        this.todayUpdated.put(position,true);
        mapChanged("%s Grass placed".formatted(position));
    }

    public void removeGrassBool(Grass grass) {
        Vector2d position = grass.getPosition();
        grassMap.remove(position);
        int x = position.getX();
        int y = position.getY();
        grassMatrix[x][y] = false;
        this.todayUpdated.put(position,true);
        mapChanged("%s Grass removed".formatted(position));
    }


    public void addAnimalBool(Animal animal) {
        allEverLivedAnimals.add(animal);
        Vector2d position = animal.getPosition();
        List<Animal> templist = animalMap.get(position);
        if (templist == null) {
            templist = new ArrayList<>();
        }
        templist.add(animal);
        animalMap.put(position,templist);
        int x = position.getX();
        int y = position.getY();
        animalMatrix[x][y] = true;
        this.todayUpdated.put(position,true);
        mapChanged("%s Animal placed".formatted(position));
    }

    public void removeAnimalBool(Animal animal) {
        Vector2d position = animal.getPosition();
        int x = position.getX();
        int y = position.getY();
        List<Animal> templist = animalMap.get(position);
        if (templist == null) {
            animalMatrix[x][y] = false;
        } else if (templist.size() > 1) {
            templist.remove(animal);
            animalMatrix[x][y] = true;
        } else if (templist.size() == 1) {
            templist.remove(animal);
            animalMatrix[x][y] = false;
        } else {
            animalMatrix[x][y] = false;
        }
        animalMap.put(position,templist);
        this.todayUpdated.put(position,true);
        mapChanged("%s Animal removed".formatted(position));

    }

    private boolean checkAnimalMatrix(Vector2d position) {
        int x = position.getX();
        int y = position.getY();
        return animalMatrix[x][y];
    }

    private boolean checkGrassMatrix(Vector2d position) {
        int x = position.getX();
        int y = position.getY();
        return grassMatrix[x][y];
    }

    public Map<Vector2d,List<Animal>> getAnimalMap() {
        return this.animalMap;
    }

    public Map<Vector2d,Grass> getGrassMap() {
        return this.grassMap;
    }

    public Animal strongestAnimalAt(Vector2d position) {
        List<Animal> thisAnimals = animalMap.get(position);
        if (thisAnimals.size() == 1) {
            return thisAnimals.get(0);
        }
        Competition viewComp = new Competition((ArrayList<Animal>) thisAnimals);
        return viewComp.getTheStrongestCouple().get(0);
    }

    public Grass grassAt(Vector2d position) {
        return grassMap.get(position);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return lowerLeftLimit.precedes(position) && upperRightLimit.follows(position);
    }



    @Override
    public boolean place(Animal animal, boolean inform) {
        if(canMoveTo(animal.getPosition())){
            animals.add(animal);
//            mapChanged("Animal placed at %s".formatted(animal.getPosition()));
            addAnimalBool(animal);
            return true;
        } else {
//            mapChanged("Animal not able to be placed at %s".formatted(animal.getPosition()));
            return false;
        }


    }



    public ArrayList<Grass> placeGrass(){
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, grassNumber, size);
        ArrayList<Grass> grasses1 = new ArrayList<>();
        for(Object grassPosition : randomPositionGenerator) {
            Grass grass = new Grass((Vector2d) grassPosition, grassEnergy);
            grasses1.add(grass);
            addGrassBool(grass);
//            mapChanged("Grass placed at %s".formatted((Vector2d)grassPosition));
        }
        return grasses1;

    }

    public ArrayList<Grass> updateGrass() {
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, grasses, fertilized, userGrass, size);
        ArrayList<Grass> grasses1 = new ArrayList<>();
        for(Object grassPosition : randomPositionGenerator) {
            Grass grass = new Grass((Vector2d) grassPosition, grassEnergy);
            grasses1.add(grass);
            addGrassBool(grass);
//            mapChanged("Grass placed at %s".formatted((Vector2d)grassPosition));
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
//            mapChanged("Animal moved from %s to %s in direction %s".formatted(previousPosition,animal.getPosition(), animal.orientationToString()));
        }
        else if(newPosition.getX() == width | newPosition.getX() < 0){
            animals.remove(animal);
            removeAnimalBool(animal);
            int newX = newPosition.getX();
            if (newX == -1) {newX = width-1;}
            animal.setPosition(new Vector2d(newX % getWidth(), newPosition.getY()));
            animals.add(animal);
            addAnimalBool(animal);
//            mapChanged("Animal moved from %s to %s in direction %s".formatted(previousPosition,animal.getPosition(), animal.orientationToString()));
        } else {
            animals.remove(animal);
            removeAnimalBool(animal);
            animal.move(direction, this);
            animal.setActivatedGen(animal.getGenotype().getGenes().indexOf(direction));
            animals.add(animal);
            addAnimalBool(animal);
//            mapChanged("Animal moved from %s to %s in direction %s".formatted(previousPosition, animal.getPosition(), animal.orientationToString()));
        }

    }

    @Override
    public boolean isOccupied(Vector2d position) {

        if (isOccupiedByAnimal(position)) {return true;}
        return isOccupiedByGrass(position);

    }
    public boolean isOccupiedByAnimal(Vector2d position){
        return checkAnimalMatrix(position);
    }

    public boolean isOccupiedByGrass(Vector2d position){
        return checkGrassMatrix(position);
    }

    public List<Animal> animalsAt(Vector2d position) {
        return this.animalMap.get(position);
    }



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
        return elements;
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
    @Override
    public List<Animal> getAnimals() {return this.animals;}

    // to samo co wyżej
    @Override
    public List<Grass> getGrasses() {
        return this.grasses;
    }

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


    public int countFreePositions(){
        int cnt = 0;
        for(int x= 0 ;x < width;x++){
            for(int y = 0; y < height;y++){
                if(objectAt(new Vector2d(x, y)) == null){
                    cnt++;
                }
            }

        }
        return cnt;
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




    private int stage = 0;
    public void updateAll() {
        int maxStage = 4 + animals.size();
//        System.out.println(maxStage + " " + stage);
//        System.out.println(animals + " " + animals.size());
        if (stage == 0) {
            deadRemoval();
            stage++;
        } else if (stage == maxStage - 3) {
            eatingAndCopulating();
//            System.out.println("Gotten to eating and copulating");
            stage++;
        } else if (stage == maxStage - 2) {
            setGrasses(updateGrass());
//            System.out.println("Gotten to grass updating");
            stage++;
        } else if (stage == maxStage - 1) {
            day++;
            mapChanged("Day " + day);
//            System.out.println("Gotten to day increase");
            stage = 0;
        } else {
            Animal mover = animals.get(0);
            if (!mover.isDead()) {
                moveAnimal(mover);
//                System.out.println("Gotten to animal number " + (stage - 1));
            }
            stage++;
        }

    }

    private void deadRemoval() {
        if (!dead.isEmpty()) {
            Animal curr = dead.removeFirst();
            getAnimals().remove(curr);
            removeAnimalBool(curr);
            removeFromFertilized(curr.getPosition());
            int count = 0;
            while (day - curr.getAge() >= 2) {
                if (dead.isEmpty()) {break;}
                curr = dead.removeFirst();
                getAnimals().remove(curr);
                removeAnimalBool(curr);
                removeFromFertilized(curr.getPosition());
                count += 1;
            }
            if (count == 0) {
                if (day - curr.getAge() < 2) {
                    dead.addFirst(curr);
                    getAnimals().add(curr);
                    addAnimalBool(curr);
                }
            }
        }
    }

    public void moveAnimal(Animal currentAnimal) {
        Genotype genotype = currentAnimal.getGenotype();
        List<Integer> genes = genotype.getGenes();
        int moveInd = day % genes.size();
        int move = genes.get(moveInd);
        move(currentAnimal,move);
        currentAnimal.incrementAge();
        if (currentAnimal.isDead()) {
            animals.remove(currentAnimal);
            addToFertilized(currentAnimal.getPosition());
            dead.addLast(currentAnimal);
        }
    }


    private void eatingAndCopulating() {
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
                for (Grass g : getGrasses()) {
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
                    strongest.incrementNumberOfConsumedGrass();
//                    System.out.println("Grass " + grass + " deleted from " + grass.getPosition() + " and position is " + position);
                    removeGrass(grass);
                    removeGrassBool(grass);
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
                    place(newborn,true);
                    animals.add(newborn);

                }

            }
    }


}
