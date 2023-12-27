package agh.ics.oop.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class  Vector2dTest {
    public static int randint(int range) {return (int)(Math.random()*range);}
    @Test
    public void equalsTest() {
        int randX = randint(100);
        int randY = randint(100);
        Vector2d tester = new Vector2d(randX,randY);

        assertEquals(tester.equals(new Vector2d(randX,randY)),true);
    }
    @Test
    public void toStringTest() {
        int randX = randint(100);
        int randY = randint(100);
        Vector2d tester = new Vector2d(randX,randY);

        assertEquals(tester.toString(),"(" + randX + "," + randY + ")");
    }

    @Test
    public void precedesTest() {
        int randX = randint(100);
        int randY = randint(100);
        Vector2d tester = new Vector2d(randX,randY);
        Vector2d tester2 = new Vector2d(randX + 1,randY + 1);

        assertEquals(tester.precedes(tester2),true);
    }

    @Test
    public void followsTest() {
        int randX = randint(100);
        int randY = randint(100);
        Vector2d tester = new Vector2d(randX,randY);
        Vector2d tester2 = new Vector2d(randX + 1,randY + 1);

        assertEquals(tester2.follows(tester),true);
    }

    @Test
    public void upperRightTest() {
        int randX = randint(100);
        int randY = randint(100);
        int randX1 = randint(100);
        int randY1 = randint(100);
        Vector2d tester = new Vector2d(randX,randY);
        Vector2d tester2 = new Vector2d(randX1,randY1);
        Vector2d output = new Vector2d(Math.max(randX,randX1),Math.max(randY,randY1));

        assertEquals(tester.upperRight(tester2),output);
    }

    @Test
    public void lowerLeftTest() {
        int randX = randint(100);
        int randY = randint(100);
        int randX1 = randint(100);
        int randY1 = randint(100);
        Vector2d tester = new Vector2d(randX,randY);
        Vector2d tester2 = new Vector2d(randX1,randY1);
        Vector2d output = new Vector2d(Math.min(randX,randX1),Math.min(randY,randY1));

        assertEquals(tester.lowerLeft(tester2),output);
    }

    @Test
    public void addTest() {
        int randX = randint(100);
        int randY = randint(100);
        int randX1 = randint(100);
        int randY1 = randint(100);
        Vector2d tester = new Vector2d(randX,randY);
        Vector2d tester2 = new Vector2d(randX1,randY1);
        Vector2d output = new Vector2d(randX + randX1,randY + randY1);

        assertEquals(tester.add(tester2),output);
    }


    @Test
    public void subtractTest() {
        int randX = randint(100);
        int randY = randint(100);
        int randX1 = randint(100);
        int randY1 = randint(100);
        Vector2d tester = new Vector2d(randX,randY);
        Vector2d tester2 = new Vector2d(randX1,randY1);
        Vector2d output = new Vector2d(randX - randX1,randY - randY1);

        assertEquals(tester.subtract(tester2),output);
    }

    @Test
    public void oppositeTest() {
        int randX = randint(100);
        int randY = randint(100);
        Vector2d tester = new Vector2d(randX,randY);
        Vector2d output = new Vector2d(-randX,-randY);

        assertEquals(tester.opposite(),output);
    }

}