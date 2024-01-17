package agh.ics.oop.model.frameworks;

import agh.ics.oop.model.map.WorldMap;

@FunctionalInterface
public interface MapChangeListener {

    void mapChanged(WorldMap worldMap, String message);

}
