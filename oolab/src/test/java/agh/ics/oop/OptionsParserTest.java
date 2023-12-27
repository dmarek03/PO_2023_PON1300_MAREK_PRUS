package agh.ics.oop;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OptionsParserTest {
    public static int randint(int range) {return (int)(Math.random()*range);}

    @Test
    public void changeTest() {
        String[] moveTable = new String[100];
        List<MoveDirection> moves = new ArrayList<MoveDirection>();
        String[] characters = {"f","b","r","l","a"};
        for (int i = 0; i < 100; i++) {
            int check = randint(4);
            if (check == 4) characters[4] = Character.toString((char) ((new Random()).nextInt(26) + 'a'));

            moveTable[i] = characters[check];
            switch (characters[check]) {
                case "f" -> moves.add(MoveDirection.FORWARD);
                case "b" -> moves.add(MoveDirection.BACKWARD);
                case "r" -> moves.add(MoveDirection.RIGHT);
                case "l" -> moves.add(MoveDirection.LEFT);
            }
        }
        int size = moves.size();
        List<MoveDirection> output2 = OptionsParser.parse(moveTable);
        for (int i = 0; i < size; i++) {
            assertEquals(moves.get(i), output2.get(i));
        }
    }

    @Test
    public void throwTest() {
        String[] moveTable = {"t"};
        List<MoveDirection> moves = new ArrayList<MoveDirection>();
        assertThrows(IllegalArgumentException.class, () -> {
            OptionsParser.parse(moveTable);
        });

    }



}