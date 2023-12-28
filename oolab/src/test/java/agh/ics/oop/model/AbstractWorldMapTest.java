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
//public class AbstractWorldMapTest {
//
//    @Test
//    public void getOrderedAnimalsTest() throws PositionAlreadyOccupiedException {
//        WorldMap map = new StandardMap(10, 10, 11, 2, new HashMap<>(), UUID.randomUUID());
//        List<Vector2d> positions = List.of(new Vector2d(3,4), new Vector2d(2,2), new Vector2d(0,0));
//        for (int i = 0; i < positions.size(); i++) {
//            map.place(new Animal(positions.get(i),MapDirection.NORTH, 0,0),false);
//        }
//
//        List<Animal> expectedResult = List.of(new Animal(new Vector2d(0, 0),MapDirection.NORTH, 0,0), new Animal(new Vector2d(2, 2),MapDirection.NORTH, 0,0), new Animal(new Vector2d(3, 4),MapDirection.NORTH, 0,0));
//
//        List<Animal> result = map.getOrderedAnimals();
//
//        for (int i = 0 ; i < result.size(); i++) {
//            Animal animal1 = result.get(i);
//            Animal animal2 = result.get(i);
//            String string1 = animal1.toString() + ' ' + animal1.getPosition().toString();
//            String string2 = animal2.toString() + ' ' + animal2.getPosition().toString();
//            assertEquals(string1,string2);
//        }
//    }
//
//}
