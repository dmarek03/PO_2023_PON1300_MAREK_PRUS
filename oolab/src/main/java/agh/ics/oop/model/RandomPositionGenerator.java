package agh.ics.oop.model;

import agh.ics.oop.model.Vector2d;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RandomPositionGenerator implements Iterable {

    int maxWidth;
    int maxHeight;
    int N;

    Map<Vector2d,Grass> grassMap = new HashMap<>();

    double size;

    List<Vector2d> fertilized;

    public List<Vector2d> positions = new ArrayList<>();

    public RandomPositionGenerator(int maxWidth, int maxHeight, int N, double size) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.N = N;
        this.fertilized = null;
        this.size = size;
        setPositions(maxWidth,maxHeight);
    }

    public RandomPositionGenerator(int maxWidth, int maxHeight, List<Grass> grasses, List<Vector2d> fertilized, double size) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.N = -1;
        this.size = size;
        this.fertilized = fertilized;
        this.grassMap = new HashMap<>();
        for (Grass grass : grasses) {positions.add(grass.getPosition());}
        for (Grass grass : grasses) {grassMap.put(grass.getPosition(),grass);}
        setPositions(maxWidth,maxHeight);
    }



    public int getN() {return N;}

    public static int randint(int range) {return (int)(Math.random()*range);}

    public void setPositions(int maxWidth, int maxHeight) {
        List<Vector2d> allPossibilities = new ArrayList<>();

        Map<Vector2d,Double> possibs = new HashMap<>();
        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                Vector2d vect = new Vector2d(x,y);
                if (grassMap.containsKey(vect)) {continue;}

                double prob = gauss(linearization(y));
                int times = (int)(Math.round(5*prob));
                if (fertilized != null) {
                    if (fertilized.contains(vect)) {
                        times = 4;
                        prob = 0.8;
                    }
                }
                possibs.put(vect,prob);
                for (int i = 0; i < times; i++) {allPossibilities.add(vect);}
            }
        }

        if ( N >= 0 ) {
            for (int i = 0; i < this.getN(); i++) {
                int random = randint(allPossibilities.size());
                Vector2d position = allPossibilities.get(random);
                this.positions.add(position);
                while (allPossibilities.contains(position)) {
                    allPossibilities.remove(position);
                }
            }
        } else {
            for (Map.Entry<Vector2d,Double> entry : possibs.entrySet()) {
                double rand = Math.random();
                if (rand <= entry.getValue()) {this.positions.add(entry.getKey());}
            }
        }


    }

    private double linearization(double y) {
        double a = this.maxHeight;
        double c = this.size;
        return (((c/a)*y) - (c/2));
    }

    private double gauss(double x) {
        return 0.8 * Math.exp(-(x*x));
    }


    @NotNull
    @Override
    public Iterator iterator() {
        return positions.iterator();
    }
}
