package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    public void nextTest() {
        for (int i = 0; i < 4; i++) {
            assertEquals(MapDirection.values()[(i + 1) % 4],MapDirection.next(MapDirection.values()[i]));
        }
    }

    @Test
    public void previousTest() {
        for (int i = 0; i < 4; i++) {
            assertEquals(MapDirection.values()[(i + 3) % 4],MapDirection.previous(MapDirection.values()[i]));
        }
    }

    @Test
    public void unitVectorTest() {
        int curra = 0;
        int currb = 1;
        for (int i = 0; i < 4; i++) {
            assertEquals(MapDirection.unitVector(MapDirection.values()[i]), new Vector2d(curra,currb));
            int temp = curra;
            curra = currb;
            currb = -temp;
        }

        assertEquals(MapDirection.unitVector(MapDirection.NORTH), new Vector2d(0,1));
    }
}