package agh.ics.oop.main;

import agh.ics.oop.simulations.SimulationApp;
import javafx.application.Application;

public class WorldGUI {
    public static void main(String[] args) {


        System.out.println("\nSimulation started");

        Application.launch(SimulationApp.class, args);

        System.out.println("\nSimulation ended");

    }
}