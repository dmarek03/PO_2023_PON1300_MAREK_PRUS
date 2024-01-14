package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.RandomPositionGenerator;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class World {

    private static int randint(int start, int end) {return (int)((Math.random()*(end + 1 - start)) + start);}
    public static void main(String[] args) {
        System.out.println("System wystartował\n");

        List<Vector2d> positions = List.of(new Vector2d(3,1),new Vector2d(1,3));

        WorldMap map = new StandardMap(40,40,400,10,0.8,1);


        map.addObserver(new ConsoleMapDisplay());

        System.out.println(map);
        System.out.println(map.getGrasses());
        System.out.println(map.getAnimals());
        System.out.println();
        Simulation simulation = new Simulation(map,positions,8, 5,10, 0.3);
//        simulation.run();
        (new Thread(() -> {(simulation).run();})).start();
//        for (int i = 0; i < 5; i++) {
//            for (int j = 0; j < 5; j++) {
//                System.out.println(map.isOccupied(new Vector2d(i,j)));
//            }
//        }

        System.out.println("\nSystem zakończył działanie");

    }


}
