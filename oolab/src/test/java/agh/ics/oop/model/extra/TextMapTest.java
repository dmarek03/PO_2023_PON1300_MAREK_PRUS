//package agh.ics.oop.model.extra;
//
//import agh.ics.oop.OptionsParser;
//import agh.ics.oop.model.MoveDirection;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TextMapTest {
//
//    @Test
//    public void firstTest() {
//        String[] input = {"g", "k", "f", "l", "l", "f", "r", "d", "p", "k", "n", "r", "l", "l", "b", "f"};
//        List<MoveDirection> directions = OptionsParser.specialParse(input);
//        String[] inputWords = {"Poduszka","bez","pierza"};
//        Text[] words = new Text[inputWords.length];
//        for (int i = 0; i < inputWords.length; i++) {
//            words[i] = new Text(inputWords[i],i);
//        }
//        TextMap map = new TextMap();
//        int noWords = words.length;
//        for (int i = 0; i < noWords; i++) {
//            map.place(words[i],i);
//        }
//        List<String> result = new ArrayList<>();
//        for (int i = 0; i < directions.size(); i++) {
//            int noWord = i % noWords;
//            map.move(words[noWord],directions.get(i));
//            result.add(map.toString());
//            System.out.println(map);
//        }
//        List<String> expectedResult = List.of("bez Poduszka pierza", "Poduszka bez pierza", "Poduszka pierza bez", "pierza Poduszka bez");
//        System.out.println(result);
//        System.out.println(expectedResult);
//        if (result.size() != expectedResult.size()) {
//            assertTrue(false);
//            return;
//        }
//        for (int i = 0; i < result.size(); i++) {
//            assertEquals(result.get(i),expectedResult.get(i));
//        }
//    }
//
//    @Test
//    public void secondTest() {
//        String[] input = {"b", "f", "b", "b", "p", "b", "f", "b", "b", "f", "f", "g", "b", "f", "f", "f"};
//        List<MoveDirection> directions = OptionsParser.specialParse(input);
//        System.out.println(directions);
//        String[] inputWords = {"Z","okazji","pierwszego","listopada","pojechalem","do","domu"};
//        Text[] words = new Text[inputWords.length];
//            for (int i = 0; i < inputWords.length; i++) {
//                words[i] = new Text(inputWords[i],i);
//            }
//            TextMap map = new TextMap();
//            int noWords = words.length;
//            for (int i = 0; i < noWords; i++) {
//                map.place(words[i],i);
//            }
//        List<String> result = new ArrayList<>();
//        for (int i = 0; i < directions.size(); i++) {
//            int noWord = i % noWords;
//            map.move(words[noWord],directions.get(i));
//            result.add(map.toString());
//            System.out.println(map);
//        }
//        List<String> expectedResult = List.of("Z okazji pierwszego listopada pojechalem do domu", "Z pierwszego okazji listopada pojechalem do domu", "pierwszego Z okazji listopada pojechalem do domu", "pierwszego Z listopada okazji pojechalem do domu", "pierwszego Z listopada pojechalem okazji do domu", "pierwszego Z listopada pojechalem okazji domu do", "pierwszego Z listopada pojechalem domu okazji do", "Z pierwszego listopada pojechalem domu okazji do", "Z pierwszego listopada pojechalem domu do okazji", "Z listopada pierwszego pojechalem domu do okazji", "listopada Z pierwszego pojechalem domu do okazji", "listopada Z pierwszego domu pojechalem do okazji", "listopada Z pierwszego domu pojechalem okazji do", "listopada Z pierwszego pojechalem domu okazji do");
//        System.out.println(result);
//        System.out.println(expectedResult);
//        if (result.size() != expectedResult.size()) {
//            assertTrue(false);
//            return;
//        }
//        for (int i = 0; i < result.size(); i++) {
//            assertEquals(result.get(i),expectedResult.get(i));
//        }
//    }
////
//    @Test
//    public void thirdTest() {
//        String[] input = {"f", "b", "b", "f", "b", "b", "b", "f", "m", "b", "f", "b", "f", "e", "b", "f", "l", "f", "b", "b", "f"};
//        List<MoveDirection> directions = OptionsParser.specialParse(input);
//        String[] inputWords = {"Spoleczenstwo","przemyslowe","i","jego","przyszlosc"};
//        Text[] words = new Text[inputWords.length];
//            for (int i = 0; i < inputWords.length; i++) {
//                words[i] = new Text(inputWords[i],i);
//            }
//            TextMap map = new TextMap();
//            int noWords = words.length;
//            for (int i = 0; i < noWords; i++) {
//                map.place(words[i],i);
//            }
//        List<String> result = new ArrayList<>();
//        for (int i = 0; i < directions.size(); i++) {
//            int noWord = i % noWords;
//            map.move(words[noWord],directions.get(i));
//            result.add(map.toString());
//            System.out.println(map);
//        }
//        List<String> expectedResult = List.of("przemyslowe Spoleczenstwo i jego przyszlosc", "przemyslowe Spoleczenstwo i jego przyszlosc", "przemyslowe i Spoleczenstwo jego przyszlosc", "przemyslowe i Spoleczenstwo przyszlosc jego", "przemyslowe i przyszlosc Spoleczenstwo jego", "przemyslowe i Spoleczenstwo przyszlosc jego", "przemyslowe i Spoleczenstwo przyszlosc jego", "przemyslowe Spoleczenstwo i przyszlosc jego", "przemyslowe Spoleczenstwo i jego przyszlosc", "przemyslowe Spoleczenstwo i jego przyszlosc", "Spoleczenstwo przemyslowe i jego przyszlosc", "Spoleczenstwo i przemyslowe jego przyszlosc", "i Spoleczenstwo przemyslowe jego przyszlosc", "i Spoleczenstwo przemyslowe przyszlosc jego", "i Spoleczenstwo przemyslowe jego przyszlosc", "Spoleczenstwo i przemyslowe jego przyszlosc", "Spoleczenstwo przemyslowe i jego przyszlosc", "Spoleczenstwo przemyslowe jego i przyszlosc");
//        System.out.println(result);
//        System.out.println(expectedResult);
//        if (result.size() != expectedResult.size()) {
//            assertTrue(false);
//            return;
//        }
//        for (int i = 0; i < result.size(); i++) {
//            assertEquals(result.get(i),expectedResult.get(i));
//        }
//    }
//}
