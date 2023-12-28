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
//public class GrassFieldTest {
//    @Test
//    public void placeTest() {
//        GrassField map = new GrassField(1,false);
//        Animal animal1 = new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0);
//        Grass grass1 = new Grass(new Vector2d(1,0));
//        List<WorldElement> elements = List.of(animal1,grass1,new Grass(new Vector2d(0,0)),new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0));
//        for (WorldElement element : elements) {
//            try {
//                map.place(element, (Vector2d) element.getPosition(),true);
//            } catch (PositionAlreadyOccupiedException e) {
//                System.err.println(e.getMessage());
//            }
//        }
//        assertEquals(map.getAnimals().get(new Vector2d(0,0)),animal1);
//        assertEquals(map.getGrasses().get(new Vector2d(1,0)),grass1);
//    }
//
//    @Test
//    public void moveTest() {
//        GrassField map = new GrassField(1,false);
//        Animal animal1 = new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0);
//        Grass grass1 = new Grass(new Vector2d(1,0));
//        List<WorldElement> elements = List.of(animal1,grass1,new Grass(new Vector2d(0,0)),new Animal(new Vector2d(0, 1),MapDirection.NORTH, 0,0));
//        List<Animal> animals = new ArrayList<>();
//        System.out.println("so far so good");
//        for (WorldElement element : elements) {
//            System.out.println(map);
//            try {
//                boolean placer = map.place(element, (Vector2d) element.getPosition(),true);
//                System.out.print(map.isAnimal(Optional.of(element)));
//                System.out.print(' ');
//                System.out.print(map.isGrass(Optional.of(element)));
//                System.out.println(' ' + element.toString());
//                if (placer && map.isAnimal(Optional.of(element))) {animals.add((Animal) element);}
//            } catch (PositionAlreadyOccupiedException e) {
//                System.err.println(e.getMessage());
//            }
//        }
//        System.out.println("so far so good");
//
//        String[] args = {"f","r","r","r","f","r","b","f"};
//        List<MoveDirection> directions = OptionsParser.parse(args);
//        for (int i = 0; i < directions.size(); i++) {
//            int animalInd = i % animals.size();
//            Animal animal = animals.get(animalInd);
//            map.move(animal,directions.get(i));
//        }
//        List<String> result = new ArrayList<>();
//        List<String> expected = List.of("> (0,0)","< (-1,1)","* (1,0)");
//        System.out.println(result);
//        for (Map.Entry<Vector2d, WorldElement> entry : map.getAnimals().entrySet()) {result.add(entry.getValue().toString() + " " + entry.getKey().toString());}
//        for (Map.Entry<Vector2d, WorldElement> entry : map.getGrasses().entrySet()) {
//            String addon = entry.getValue().toString() + " " + entry.getKey().toString();
//            for (String check : expected) {
//                if (addon.equals(check)) {
//                    result.add(addon);
//                    break;
//                }
//            }
//        }
//        System.out.println(expected);
//        System.out.println(result);
//        assertEquals(expected,result);
//    }
//
//    @Test
//    public void isOccupiedTest() {
//        Animal animal = new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0);
//        Grass grass = new Grass(new Vector2d(1,1));
//        GrassField map = new GrassField(1,false);
//        try {
//            map.place(animal, animal.getPosition(),true);
//            map.place(grass, grass.getPosition(),true);
//        } catch (PositionAlreadyOccupiedException e) {
//            System.err.println(e.getMessage());
//        }
//        assertTrue(map.isOccupied(animal.getPosition()));
//        assertTrue(map.isOccupied(grass.getPosition()));
//    }
//
//    @Test
//    public void objectAtTest() {
//        Animal animal = new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0);
//        Grass grass = new Grass(new Vector2d(1,1));
//        GrassField map = new GrassField(1,false);
//        try {
//            map.place(animal, animal.getPosition(),true);
//            map.place(grass, grass.getPosition(),true);
//        } catch (PositionAlreadyOccupiedException e) {
//            System.err.println(e.getMessage());
//        }
//        assertEquals(map.objectAt(animal.getPosition()),Optional.ofNullable(animal));
//        assertEquals(map.objectAt(grass.getPosition()),Optional.ofNullable(grass));
//    }
//
//}
