package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.StandardMap;
import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationIntegrationTest {

    @Test
    public void shouldSimulationWorksCorrectly(){
        StandardMap map = new StandardMap(10,10, 25, 5, 0.8, 3);
        List<Vector2d> positions = List.of(new Vector2d(0,5), new Vector2d(5, 5), new Vector2d(3, 4), new Vector2d(3, 2));
        Simulation simulation = new Simulation(map, positions, 11, 7,12,0.4, 6);
        for(int i = 0; i< 4 ;i++){
            System.out.println(map.getAnimals().get(i).getGenotype().getGenes());
        }
        simulation.run();


        List<Vector2d> expectedPositions = List.of
                (
                new Vector2d(0,3),
                new Vector2d(8, 5),
                new Vector2d(3, 3),
                new Vector2d(4,2)
                );

        List<MapDirection> expectedOrientations = List.of(
                MapDirection.SOUTH,
                MapDirection.SOUTHEAST,
                MapDirection.WEST,
                MapDirection.NORTHEAST
        );

        for (int i = 0 ;i<expectedPositions.size();i++){
            assertEquals(expectedPositions.get(i), map.getAnimals().get(i).getPosition());
            assertEquals(expectedOrientations.get(i), map.getAnimals().get(i).getOrientation());
        }


    }

}

