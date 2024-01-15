package agh.ics.oop.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class StandardMapTest {


    @Test public void  isPositionOccupiedByAnimal(){
        StandardMap map = new StandardMap(10,10, 5, 5, 0.8, 3);
        List<Vector2d> positions = List.of(
                new Vector2d(2, 2),
                new Vector2d(3, 5),
                new Vector2d(9, 7)

        );
        Animal animal = new Animal();
        Animal animal1 = new Animal(positions.get(1), MapDirection.NORTH, 5, 5);
        List<Animal> animals = List.of(animal, animal1);
        for(Animal a : animals){
            map.place(a, false);
        }
        assertTrue(map.isOccupiedByAnimal(positions.get(0)));
        assertTrue(map.isOccupiedByAnimal(positions.get(1)));
        assertFalse(map.isOccupiedByAnimal(positions.get(2)));
    }

    @Test
    public void isGrassPlacedCorrectly(){
        StandardMap map = new StandardMap(10,10, 100, 5, 0.8, 3);
        map.placeGrass();
        for(int x = 0; x < map.getWidth();x++){
            for(int y=0;y<map.getHeight();y++){
                assertTrue(map.isOccupiedByGrass(new Vector2d(x, y)));
            }
        }

    }


    @Test
    public void areAnimalsPlacedCorrectly(){
        StandardMap map = new StandardMap(10,10, 5, 5, 0.8, 3);
        Animal animal =new Animal();
        Animal animal1 = new Animal(new Vector2d(3, 5), MapDirection.NORTH, 5, 5);
        Animal animal2 = new Animal(new Vector2d(20, 30), MapDirection.EAST, 4, 3);
        List<Animal> animals = List.of(animal, animal1, animal2);

        for(Animal a: animals){
            map.place(a, false);
        }
        assertTrue(map.isOccupiedByAnimal(new Vector2d(3, 5)));
        assertTrue(map.isOccupiedByAnimal(new Vector2d(2,2)));
        assertFalse(map.getAnimals().contains(animal2));
    }

    @Test
    public void shouldAnimalsMovesCorrectly(){
        StandardMap map = new StandardMap(10,10, 5, 5, 0.8, 3);
        List<Vector2d> positions = List.of(
                new Vector2d(2, 2),
                new Vector2d(5, 5),
                new Vector2d(5, 4),
                new Vector2d(2, 4)
        );

        List<Integer> moves = List.of(0,1,2,3,4,5,6,7,0,1,2,3);

        Animal animal  = new Animal();
        Animal animal1 = new Animal(positions.get(1), MapDirection.SOUTH, 5, 5);
        Animal animal2 = new Animal(positions.get(2), MapDirection.WEST, 4, 3);
        Animal animal3 = new Animal(positions.get(3), MapDirection.EAST, 4, 3);
        List<Animal> animals = List.of(animal, animal1, animal2, animal3);
        for(int i = 0; i< moves.size() ; i ++) {
            map.move(animals.get(i%4), moves.get(i));
        }
        List<Vector2d> expectedPositions = List.of(
                new Vector2d(2, 1),
                new Vector2d(6, 3),
                new Vector2d(4, 6),
                new Vector2d(0,3)
        );
        List<MapDirection> expectedOrientations = List.of(
                MapDirection.SOUTH,
                MapDirection.SOUTHEAST,
                MapDirection.NORTH,
                MapDirection.NORTHWEST
        );

        for(int i =0 ;i < expectedPositions.size();i ++){
            assertEquals(expectedPositions.get(i), animals.get(i).getPosition());
            assertEquals(expectedOrientations.get(i), animals.get(i).getOrientation());
        }

    }
}
