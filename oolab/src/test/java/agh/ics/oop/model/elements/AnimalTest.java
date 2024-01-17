package agh.ics.oop.model.elements;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.genes.Genotype;
import agh.ics.oop.model.map.mapUtils.Vector2d;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class AnimalTest {

    @Test
    public void isAnimalAtPosition(){
        Animal animal = new Animal();
        Vector2d pos = new Vector2d(2, 4);
        Animal animal1 = new Animal(pos, MapDirection.NORTH, 5, 5, false);
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
        List<Integer> directions = List.of(0, 1, 2,3 ,4, 5, 6, 7);
        List<Vector2d> expectedPositions = List.of
                (
                        new Vector2d(2, 3),
                        new Vector2d(3, 3),
                        new Vector2d(3, 2),
                        new Vector2d(3, 1),
                        new Vector2d(2, 1),
                        new Vector2d(1, 1),
                        new Vector2d(1, 2),
                        new Vector2d(1, 3)
                );

        for(int i = 0;i < expectedPositions.size() ;i++){
            assertEquals(expectedPositions.get(i), animal.calculateNextPosition(directions.get(i)));
        }


    }

    @Test
    public void shouldAnimalEnergyChangesCorrectly(){
        Animal animal = new Animal();
        Animal animal1 = new Animal(new Vector2d(3, 4), MapDirection.WEST, 50, 6,false);
        Animal animal2 = new Animal();
        List<Animal>  animals = List.of(animal, animal1, animal2);
        List<Integer> expectedAnimalEnergy = List.of(120, 34, 0);
        animal.changeEnergy(20);
        animal1.changeEnergy(-16);
        animal2.changeEnergy(-300);

        for(int i =0 ;i < expectedAnimalEnergy.size();i++){
            assertEquals(expectedAnimalEnergy.get(i), animals.get(i).getAnimalEnergy());
        }

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