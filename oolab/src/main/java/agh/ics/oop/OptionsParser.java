package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OptionsParser {

    private static MoveDirection convert(String arg) {
        return switch (arg) {
            case "f" -> MoveDirection.FORWARD;
            case "b" -> MoveDirection.BACKWARD;
            case "r" -> MoveDirection.RIGHT;
            case "l" -> MoveDirection.LEFT;
            default -> throw new IllegalArgumentException(arg + " is not a legal move specification");
        };
    }

    public static List<MoveDirection> parse(String[] args) {
        return Arrays.stream(args)
                .map(OptionsParser::convert)
                .collect(Collectors.toList());
    }

//    public static List<MoveDirection> specialParse(String[] args) {
//        List<MoveDirection> table = new ArrayList<MoveDirection>();
//        for (String arg : args) {
//            switch (arg) {
//                case "f" -> table.add(MoveDirection.FORWARD);
//                case "b" -> table.add(MoveDirection.BACKWARD);
//            }
//        }
//        return table;
//    }



}