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

    // static int randint(int start, int end) {return (int)((Math.random()*(end + 1 - start)) + start);}
    public static void main(String[] args) {


        System.out.println("\nSystem zakończył działanie");
        StandardMap map = new StandardMap(10, 10, 10, 3, 0.8, 1);
        Animal animal = new Animal(new Vector2d(5, 5), MapDirection.NORTH, 59, 5);
        map.place(animal, true);
        ConsoleMapDisplay observer = new ConsoleMapDisplay();
        map.addObserver(observer);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);
        map.move(animal, 1);



    }


}
