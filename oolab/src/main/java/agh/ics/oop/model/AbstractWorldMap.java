package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractWorldMap implements WorldMap<WorldElement, Vector2d> {
    public int height;
    public int width;

    public int animalCount = 0;
    public Map<Vector2d, WorldElement> animals = new HashMap<>();

    Map<Vector2d,WorldElement> grasses = new HashMap<>();

    public void setAnimals(Map<Vector2d, WorldElement> animals) {this.animals = animals;}
    public void setGrasses(Map<Vector2d, WorldElement> grasses) {this.grasses = grasses;}

    public Map<Vector2d,WorldElement> getGrasses() {return this.grasses;}


    public Map<Vector2d,WorldElement> getAnimals() {return this.animals;}

    List<MapChangeListener> observers = new ArrayList<>();

    private final UUID id = UUID.randomUUID();

    public UUID getId() {
        return this.id;
    }

    public void registerObserver(MapChangeListener observer) {
        observers.add(observer);
    }

    public void unregisterObserver(MapChangeListener observer) {
        observers.remove(observer);
    }

    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}

    public boolean isGrass(Optional<WorldElement> element) {
        return (Objects.equals(((WorldElement) element.get()).toString(), "*"));
    }
    public boolean isAnimal(Optional<WorldElement> element) {
        return ((!isGrass(element)) && (!element.equals(null)));
    }

    public void mapChanged(String event) {
        for (MapChangeListener observer : observers) {
            observer.mapChanged((WorldMap) this,event);
        }
    }

    public boolean place(WorldElement object, Vector2d position, boolean inform) throws PositionAlreadyOccupiedException {
        if ((isAnimal(Optional.ofNullable(object))) && (canMoveTo(position))) {
            animals.put(position, object);
            if (inform) {mapChanged("Animal " + object + " placed at " + position);}
            return true;
        } else if ((isGrass(Optional.ofNullable(object))) && (grasses.get(position) == null) && (animals.get(position) == null)) {
            grasses.put(position, object);
            if (inform) {mapChanged("Grass " + object + " placed at " + position);}
            return true;
        } else {throw new PositionAlreadyOccupiedException(position);}
    }


    public void move(WorldElement object, MoveDirection direction) {
        if (!isAnimal(Optional.ofNullable(object))) {return;}
        Vector2d startPosition = (Vector2d) object.getPosition();
        if (!objectAt(startPosition).equals(Optional.ofNullable(object))) {return;}
        animals.remove(startPosition);
        object.move((MoveValidator) this,direction);
        animals.put((Vector2d) object.getPosition(),object);
        mapChanged("Animal " + object + " moved from " + startPosition + " to " + object.getPosition());
    }


    boolean inMap(Vector2d position) {
        int x = position.getX();
        int y = position.getY();
        return (x < this.getWidth()) && (0 <= x) && (y < this.getHeight()) && (0 <= y);
    }

    public boolean isOccupied(Vector2d position) {
        return ((animals.get(position) != null) || (grasses.get(position) != null));
    }

    public Optional<WorldElement> objectAt(Vector2d position) {
        if (animals.get(position) != null) {return Optional.ofNullable(animals.get(position));}
        else if (grasses.get(position) != null) {return Optional.ofNullable(grasses.get(position));}
        else {return Optional.empty();}

    }

    public boolean canMoveTo(Vector2d position) {
        return (!isOccupied(position) || isGrass(objectAt(position)));
    }



    public Map<Vector2d, WorldElement> getElements() {
        return Stream.concat(grasses.entrySet().stream(), animals.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Vector2d[] findMax(Map<Vector2d, WorldElement> map,Vector2d lL, Vector2d uR) {
        Vector2d lowerLeft = lL;
        Vector2d upperRight = uR;
        for (Map.Entry<Vector2d, WorldElement> entry : map.entrySet()) {

            Vector2d vector = entry.getKey();
            if (lowerLeft == null) {lowerLeft = vector;}
            if (upperRight == null) {upperRight = vector;}
            upperRight = upperRight.upperRight(vector);
            lowerLeft = lowerLeft.lowerLeft(vector);
        }

        return new Vector2d[]{lowerLeft, upperRight};

    }

    public abstract Boundary getCurrentBounds();

    public String toString() {
        Boundary bounds = getCurrentBounds();
        Vector2d lowerleft = bounds.lowerleft();
        Vector2d upperright = bounds.upperright();
        return (new MapVisualizer((WorldMap) this)).draw(lowerleft, upperright);
    }

    public List<Animal> getOrderedAnimals() {
        return animals.values().stream()
                .filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .sorted(Comparator.comparing((Animal animal) -> animal.getPosition().getX())
                        .thenComparing(Comparator.comparing((Animal animal) -> animal.getPosition().getY())))
                .collect(Collectors.toList());
    }


}
