package agh.ics.oop.model;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class AnimalTest {

    @Test
    public void isAnimalAtPosition(){
        Animal animal = new Animal();
        Vector2d pos = new Vector2d(2, 4);
        Animal animal1 = new Animal(pos, MapDirection.NORTH, 5, 5);
        assertFalse(animal.isAt(pos));
        assertTrue(animal1.isAt(pos));

    }

    @Test
    public void CanAnimalMoveToPosition(){
        Animal animal = new Animal();
        Vector2d pos = new Vector2d(4, 4);
        Vector2d pos1 = new Vector2d(5, 4);
        Vector2d pos2 = new Vector2d(-1, 5);
        Vector2d pos3 = new Vector2d(0, 0);
        assertTrue(animal.canMoveTo(pos));
        assertTrue(animal.canMoveTo(pos3));
        assertFalse(animal.canMoveTo(pos1));
        assertFalse(animal.canMoveTo(pos2));
    }


    @Test
    public void isNextPositionCalculateCorrectly(){
        Animal animal = new Animal();
        int direction0 = 0;
        int direction1 = 1;
        int direction2 = 2;
        int direction3 = 3;
        int direction4 = 4;
        int direction5 = 5;
        int direction6 = 6;
        int direction7 = 7;

        Vector2d expectedPos0 = new Vector2d(2, 3);
        Vector2d expectedPos1 = new Vector2d(3, 3);
        Vector2d expectedPos2 = new Vector2d(3, 2);
        Vector2d expectedPos3 = new Vector2d(3, 1);
        Vector2d expectedPos4 = new Vector2d(2, 1);
        Vector2d expectedPos5 = new Vector2d(1, 1);
        Vector2d expectedPos6 = new Vector2d(1, 2);
        Vector2d expectedPos7 = new Vector2d(1, 3);
        assertEquals(expectedPos0, animal.calculateNextPosition(direction0));
        assertEquals(expectedPos1, animal.calculateNextPosition(direction1));
        assertEquals(expectedPos2, animal.calculateNextPosition(direction2));
        assertEquals(expectedPos3, animal.calculateNextPosition(direction3));
        assertEquals(expectedPos4, animal.calculateNextPosition(direction4));
        assertEquals(expectedPos5, animal.calculateNextPosition(direction5));
        assertEquals(expectedPos6, animal.calculateNextPosition(direction6));
        assertEquals(expectedPos7, animal.calculateNextPosition(direction7));

    }

    @Test
    public void areAnimalsCopulateCorrectly(){
        List<Integer> fatherGenome = new ArrayList<>();
        List<Integer> motherGenome = new ArrayList<>();
        for(int i=0;i<10;i++){
            fatherGenome.add(i%8);
            motherGenome.add((i+1)%8);

        }
        Genotype fatherGenotype = new Genotype(fatherGenome);
        Genotype motherGenotype = new Genotype(motherGenome);

        Animal father = new Animal(new Vector2d(2, 2),MapDirection.NORTH, 15, fatherGenotype);
        Animal mother = new Animal(new Vector2d(2, 2),MapDirection.NORTH, 10, motherGenotype);

        Animal child = father.copulate(mother);
        int expectedChildEnergy = 7;
        int expectedFatherEnergy = 11;
        int expectedMotherEnergy = 7;
        List<Animal> expectedParents = new ArrayList<>();
        expectedParents.add(father);
        expectedParents.add(mother);
        assertEquals(expectedFatherEnergy, father.getAnimalEnergy());
        assertEquals(expectedMotherEnergy, mother.getAnimalEnergy());
        assertEquals(expectedChildEnergy, child.getAnimalEnergy());
        assertEquals(mother.getGenotype().getNumberOfGenes(), child.getGenotype().getNumberOfGenes());
        assertEquals(1, father.getChildren());
        assertEquals(1, mother.getChildren());
        assertEquals(expectedParents, child.getParents());
    }
}
