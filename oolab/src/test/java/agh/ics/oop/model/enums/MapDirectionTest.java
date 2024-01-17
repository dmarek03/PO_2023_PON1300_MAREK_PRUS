package agh.ics.oop.model.enums;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    public void isNextDirectionCorrect(){
        List<MapDirection> directions = List.of(
                MapDirection.NORTH,
                MapDirection.NORTHEAST,
                MapDirection.EAST,
                MapDirection.SOUTHEAST,
                MapDirection.SOUTH,
                MapDirection.SOUTHWEST,
                MapDirection.WEST,
                MapDirection.NORTHWEST
        );
        MapDirection testDirections = MapDirection.NORTHWEST;
        for (MapDirection direction : directions) {
            testDirections = testDirections.next();
            assertEquals(direction, testDirections);
        }
    }

    @Test
    public void isOppositeDirectionsCalculatedCorrectly(){
        List<MapDirection> directions = List.of(
                MapDirection.NORTH,
                MapDirection.NORTHEAST,
                MapDirection.EAST,
                MapDirection.SOUTHEAST,
                MapDirection.SOUTH,
                MapDirection.SOUTHWEST,
                MapDirection.WEST,
                MapDirection.NORTHWEST
        );
        List<MapDirection> oppositeDirections = List.of(
                MapDirection.SOUTH,
                MapDirection.SOUTHWEST,
                MapDirection.WEST,
                MapDirection.NORTHWEST,
                MapDirection.NORTH,
                MapDirection.NORTHEAST,
                MapDirection.EAST,
                MapDirection.SOUTHEAST
        );
        for(int i = 0;i< directions.size();i++){
            assertEquals(oppositeDirections.get(i), directions.get(i).opposite());
        }

    }

    @Test
    public void isPreviousDirectionCorrect(){
        List<MapDirection> directions = List.of(
                MapDirection.NORTH,
                MapDirection.NORTHEAST,
                MapDirection.EAST,
                MapDirection.SOUTHEAST,
                MapDirection.SOUTH,
                MapDirection.SOUTHWEST,
                MapDirection.WEST,
                MapDirection.NORTHWEST
        );
        MapDirection testDirections = MapDirection.NORTH;
        for(int i = directions.size()-1;i >= 0;i--){
            testDirections = testDirections.previous();
            assertEquals(directions.get(i), testDirections);
        }
    }

}