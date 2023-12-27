package agh.ics.oop.model;

import agh.ics.oop.model.Vector2d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RandomPositionGenerator implements Iterable {

    int maxWidth;
    int maxHeight;
    int N;

    public List<Vector2d> positions = new ArrayList<>();

    public RandomPositionGenerator(int maxWidth, int maxHeight, int N) {
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.N = N;
        setPositions(maxWidth,maxHeight);
    }

    public int getN() {return N;}

    public static int randint(int range) {return (int)(Math.random()*range);}

    public void setPositions(int maxWidth, int maxHeight) {
        List<Vector2d> allPossibilities = new ArrayList<>();
        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxHeight; j++) {
                allPossibilities.add(new Vector2d(i,j));
            }
        }
        for (int i = 0; i < this.getN(); i++) {
            int random = randint(allPossibilities.size());
            this.positions.add(allPossibilities.get(random));
            allPossibilities.remove(random);
        }
    }


    @NotNull
    @Override
    public Iterator iterator() {
        return positions.iterator();
    }
}
