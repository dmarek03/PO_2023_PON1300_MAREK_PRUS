package agh.ics.oop.model;

import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {


    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the move is not valid.
     */
    boolean place(Animal object, boolean inform);

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal object, int direction);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    List<WorldElement> objectAt(Vector2d position);

    Map<Vector2d, WorldElement> getElements();

    public Boundary getCurrentBounds();

    public UUID getId();

    public List<Animal> getAnimals();
    public List<Grass> getGrasses();

    public List<Animal> getOrderedAnimals();

    void addObserver(MapChangeListener observer);

    void removeObserver(MapChangeListener observer);

    void mapChanged(String message);

    void notifyObservers(String message);

    int getWidth();

    int getHeight();

    boolean isOccupiedByGrass(Vector2d position);

    void setGrasses(List<Grass> newGrasses);

    void removeGrass(Grass grass);

    void addToFertilized(Vector2d pos);

    void removeFromFertilized(Vector2d pos);

    ArrayList<Grass> updateGrass();

}
