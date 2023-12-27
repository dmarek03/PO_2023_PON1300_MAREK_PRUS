//package agh.ics.oop.model;
//
//import agh.ics.oop.OptionsParser;
//import org.junit.jupiter.api.Test;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class RectangularMapTest {
//
//    @Test
//    public void placeTest() {
//        List<Animal> animals = List.of(new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0),new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0),new Animal(new Vector2d(1, 1),MapDirection.NORTH, 0,0));
//        RectangularMap map = new RectangularMap(5,5);
//        Map<Vector2d, Animal> expected = new HashMap<>();
//        for (Animal animal : animals) {
//            try {
//                map.place(animal, animal.getPosition(),true);
//            } catch (PositionAlreadyOccupiedException e) {
//                System.err.println(e.getMessage());
//            }
//            if (expected.get(animal.getPosition()) == null) {expected.put(animal.getPosition(), animal);}
//        }
//        Map<Vector2d, WorldElement> result = map.getAnimals();
//        assertEquals(result,expected);
//    }
//
//    @Test
//    public void moveTest() {
//        List<Animal> startAnimals = List.of(new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0),new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0),new Animal(new Vector2d(1, 1),MapDirection.NORTH, 0,0));
//        RectangularMap map = new RectangularMap(5,5);
//        List<Animal> animals = new ArrayList<>();
//        for (Animal animal : startAnimals) {
//            try {
//                if (map.place(animal, animal.getPosition(),true)) {
//                    animals.add(animal);
//                }
//            } catch (PositionAlreadyOccupiedException e) {
//                System.err.println(e.getMessage());
//            }
//        }
//        String[] args = {"f","r","r","r","f","r","b","f"};
//        List<MoveDirection> directions = OptionsParser.parse(args);
//
//        for (int i = 0; i < directions.size(); i++) {
//            int animalInd = i % animals.size();
//            Animal animal = animals.get(animalInd);
//            map.move(animal,directions.get(i));
//        }
//        List<String> result = new ArrayList<>();
//        for (int i = 0; i < animals.size(); i++) {result.add(animals.get(i) + " " + animals.get(i).getPosition());}
//        List<String> expected = List.of("> (0,1)","< (1,1)");
//
//        assertEquals(result,expected);
//
//    }
//
//    @Test
//    public void isOccupiedTest() {
//        Animal animal = new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0);
//        RectangularMap map = new RectangularMap(5,5);
//        try {
//            map.place(animal, animal.getPosition(),true);
//        } catch (PositionAlreadyOccupiedException e) {
//            System.err.println(e.getMessage());
//        }
//        assertTrue(map.isOccupied(animal.getPosition()));
//    }
//
//    @Test
//    public void objectAtTest() {
//        Animal animal = new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0);
//        RectangularMap map = new RectangularMap(5,5);
//        try {
//            map.place(animal, animal.getPosition(),true);
//        } catch (PositionAlreadyOccupiedException e) {
//            System.err.println(e.getMessage());
//        }
//        assertEquals(map.objectAt(animal.getPosition()), Optional.ofNullable(animal));
//    }
//
//}
