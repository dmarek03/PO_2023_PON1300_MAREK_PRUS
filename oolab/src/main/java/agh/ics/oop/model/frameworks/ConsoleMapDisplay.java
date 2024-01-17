package agh.ics.oop.model.frameworks;

import agh.ics.oop.model.frameworks.MapChangeListener;
import agh.ics.oop.model.map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class ConsoleMapDisplay implements MapChangeListener {
    int allUpdates = 0;
    List<String> mapStates = new ArrayList<>();
    List<String> allMessages = new ArrayList<>();
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        allUpdates += 1;
        System.out.println("==========");
        System.out.println("Map id: " + worldMap.getId());
        System.out.println("Number of update: " + allUpdates);
        System.out.println(message);
        System.out.println(worldMap);
        mapStates.add(worldMap.toString());
        allMessages.add(message);
        System.out.println("==========");
    }

    public List<String> getMapStates() {
        return this.mapStates;
    }

    public List<String> getAllMessages() {return allMessages;}
}
