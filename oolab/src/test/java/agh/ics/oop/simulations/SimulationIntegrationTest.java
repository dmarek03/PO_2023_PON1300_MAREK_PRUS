package agh.ics.oop.simulations;

import agh.ics.oop.model.elements.Animal;
import agh.ics.oop.model.enums.MapDirection;
import agh.ics.oop.model.genes.Genotype;
import agh.ics.oop.model.map.StandardMap;
import agh.ics.oop.model.map.mapUtils.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationIntegrationTest {

    @Test
    public void shouldSimulationWorksCorrectly(){
        StandardMap map = new StandardMap(10,10, 25, 5, 0.8, 3, 2, 4, "Normal");
        List<Vector2d> positions = List.of(new Vector2d(0,5), new Vector2d(5, 5), new Vector2d(3, 4), new Vector2d(3, 2));
        for(Vector2d p: positions){
            Genotype genotype = new Genotype(7, 5, List.of(0, 0,0, 0, 0));
            map.place( new Animal(p, MapDirection.NORTH, 5, genotype), false);
        }
        Simulation simulation = new Simulation(map, List.of(new Vector2d(-1, 0)), 11, 7,0.3,4, "Full random", 5);
        for(int i = 0; i< 4 ;i++){
            System.out.println(map.getAnimals().get(i).getGenotype().getGenes());
        }
        simulation.run();


        List<Vector2d> expectedPositions = List.of
                (
                        new Vector2d(0,5),
                        new Vector2d(5, 5),
                        new Vector2d(3, 4),
                        new Vector2d(3,2)
                );

        List<MapDirection> expectedOrientations = List.of(
                MapDirection.NORTH,
                MapDirection.NORTH,
                MapDirection.NORTH,
                MapDirection.NORTH
        );

        for (int i = 0 ;i<expectedPositions.size();i++){
            assertEquals(expectedPositions.get(i), map.getAnimals().get(i).getPosition());
            assertEquals(expectedOrientations.get(i), map.getAnimals().get(i).getOrientation());
        }


    }

}