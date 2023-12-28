//package agh.ics.oop;
//
//import agh.ics.oop.model.*;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.List;
//
//public class SimulationTest {
//
//    @Test
//    public void wallCollisionTest() {
//        String[] input = {"f", "f", "r", "f", "f", "r", "f", "f", "r", "f", "f", "r"};
//        List<MoveDirection> directions = OptionsParser.parse(input);
//        List<Vector2d> positions = List.of(new Vector2d(0,0));
//        RectangularMap map = new RectangularMap(2,2);
//        ConsoleMapDisplay observator = new ConsoleMapDisplay();
//        map.registerObserver(observator);
//        Simulation simulation = new Simulation(directions, positions,map);
//        simulation.run();
//        List<String> result = observator.getAllMessages();
//        List<String> expectedResult = List.of("Animal ^ placed at (0,0)","Animal ^ moved from (0,0) to (0,1)","Animal ^ moved from (0,1) to (0,1)","Animal > moved from (0,1) to (0,1)","Animal > moved from (0,1) to (1,1)","Animal > moved from (1,1) to (1,1)","Animal v moved from (1,1) to (1,1)","Animal v moved from (1,1) to (1,0)","Animal v moved from (1,0) to (1,0)","Animal < moved from (1,0) to (1,0)","Animal < moved from (1,0) to (0,0)","Animal < moved from (0,0) to (0,0)","Animal ^ moved from (0,0) to (0,0)");
//        System.out.println(result);
//        System.out.println(expectedResult);
//
//        if (result.size() != expectedResult.size()) {
//            assertTrue(false);
//            return;
//        }
//        for (int i = 0; i < result.size(); i++) {
//            char[] res = result.get(i).toCharArray();
//            char[] expRes = expectedResult.get(i).toCharArray();
//            for (int j = 0; j < Math.min(expRes.length,res.length); j++) {
//                assertEquals(res[j],expRes[j]);
//            }
//        }
//    }
//
//    @Test
//    public void animalCollisionTest() {
//        String[] input = {"r", "l", "f", "f"};
//        List<MoveDirection> directions = OptionsParser.parse(input);
//        List<Vector2d> positions = List.of(new Vector2d(0,0),new Vector2d(1,0));
//        RectangularMap map = new RectangularMap(2,2);
//        ConsoleMapDisplay observator = new ConsoleMapDisplay();
//        map.registerObserver(observator);
//        Simulation simulation = new Simulation(directions, positions,map);
//        simulation.run();
//        List<String> result = observator.getAllMessages();
//        List<String> expectedResult = List.of("Animal ^ placed at (0,0)","Animal ^ placed at (1,0)","Animal > moved from (0,0) to (0,0)","Animal < moved from (1,0) to (1,0)","Animal > moved from (0,0) to (0,0)","Animal < moved from (1,0) to (1,0)");
//        System.out.println(result);
//        System.out.println(expectedResult);
//
//        if (result.size() != expectedResult.size()) {
//            assertTrue(false);
//            return;
//        }
//        for (int i = 0; i < result.size(); i++) {
//            char[] res = result.get(i).toCharArray();
//            char[] expRes = expectedResult.get(i).toCharArray();
//            for (int j = 0; j < Math.min(expRes.length,res.length); j++) {
//                assertEquals(res[j],expRes[j]);
//            }
//        }
//    }
//
//    @Test
//    public void animalPlacingTest() {
//        String[] input = {"r", "f"};
//        List<MoveDirection> directions = OptionsParser.parse(input);
//        List<Vector2d> positions = List.of(new Vector2d(0,0),new Vector2d(0,0));
//        RectangularMap map = new RectangularMap(2,2);
//        ConsoleMapDisplay observator = new ConsoleMapDisplay();
//        map.registerObserver(observator);
//        Simulation simulation = new Simulation(directions, positions,map);
//        simulation.run();
//        List<String> result = observator.getAllMessages();
//        List<String> expectedResult = List.of("Animal ^ placed at (0,0)","Animal > moved from (0,0) to (0,0)","Animal > moved from (0,0) to (1,0)");
//        System.out.println(result);
//        System.out.println(expectedResult);
//
//        if (result.size() != expectedResult.size()) {
//            assertTrue(false);
//            return;
//        }
//        for (int i = 0; i < result.size(); i++) {
//            char[] res = result.get(i).toCharArray();
//            char[] expRes = expectedResult.get(i).toCharArray();
//            for (int j = 0; j < Math.min(expRes.length,res.length); j++) {
//                assertEquals(res[j],expRes[j]);
//            }
//        }
//    }
//
//}
