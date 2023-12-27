package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private final List<Simulation> simulations;
    private final ExecutorService executorService;

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void runSync() {
        for (Simulation simulation : this.simulations) {
            simulation.run();
        }
    }

    public void runASync() {
        for (Simulation simulation : this.simulations) {
            Thread thread = new Thread((Runnable) simulation);
            thread.start();
            executorService.submit(thread);
        }
        awaitSimulationsEnd();
    }

    public void awaitSimulationsEnd() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                System.err.println("Forcing shutdown after waiting for 3 seconds");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runAsyncInThreadPool() {
        for (Simulation simulation : this.simulations) {
            executorService.submit((Runnable) simulation);
        }
        executorService.shutdown();
        awaitSimulationsEnd();
    }

}