//package agh.ics.oop.model;
//
//import java.util.Objects;
//
//public class GrassField extends AbstractWorldMap implements WorldMap<WorldElement, Vector2d> {
//
//    int N;
//
//    public GrassField(int N,boolean generate) {
//        this.N = N;
//        if (generate) {
//            generatePositions(N,N,N);
//        }
//    }
//
//    public GrassField(int N) {
//        this.N = N;
//        generatePositions(N,N,N);
//    }
//
//    private void generatePositions(int width, int height, int number) {
//        double maxWSqrt = Math.sqrt(width*10);
//        int maxWidth = (int)maxWSqrt;
//        if ((maxWSqrt - maxWidth) > 0) {maxWidth++;}
//        double maxHSqrt = Math.sqrt(height*10);
//        int maxHeight = (int)maxWSqrt;
//        if ((maxHSqrt - maxHeight) > 0) {maxHeight++;}
//
//        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(maxWidth, maxHeight, number);
//        for(Object grassPosition : randomPositionGenerator) {
//            super.grasses.put((Vector2d) grassPosition, new Grass((Vector2d) grassPosition));
//        }
//    }
//
//
//    @Override
//    public Boundary getCurrentBounds() {
//
//        Vector2d lowerLeft = null;
//        Vector2d upperRight = null;
//
//        Vector2d[] animalData = findMax(this.animals,lowerLeft,upperRight);
//        lowerLeft = animalData[0];
//        upperRight = animalData[1];
//        Vector2d[] grassesData = findMax(this.grasses,lowerLeft,upperRight);
//        lowerLeft = grassesData[0];
//        upperRight = grassesData[1];
//
//        if ((lowerLeft == null) || (upperRight == null)) {lowerLeft = new Vector2d(0,0); upperRight = new Vector2d(0,0);}
//
//        return new Boundary(lowerLeft,upperRight);
//    }
//
//
//}
