package agh.ics.oop.simulations;

import agh.ics.oop.simulations.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationEngine implements Runnable {
//    private  final List<Simulation> simulations;

    private List<Thread> threads = new ArrayList<>();

    public void addThread(Thread thread) {
        this.threads.add(thread);
        runAsyncInThreadPool();
    }



    public void runAsync(){
//        List<Thread> threads = new ArrayList<>();
//        for(Simulation sim : simulations){
//            threads.add(new Thread(sim));
//        }
        for(Thread t : threads){
            t.start();


        }

        for(Thread t : threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


    }


    public void runAsyncInThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (Thread thread: threads) {
            executorService.submit(thread);
        }



    }

    @Override
    public void run() {
        runAsyncInThreadPool();
    }
}