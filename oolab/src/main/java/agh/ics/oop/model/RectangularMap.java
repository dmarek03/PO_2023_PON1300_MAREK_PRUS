//package agh.ics.oop.model;
//
//import java.util.Map;
//
//public class RectangularMap extends AbstractWorldMap implements WorldMap<WorldElement, Vector2d> {
//
//
//    public RectangularMap(int width, int height) {
//        super.height = height;
//        super.width = width;
//    }
//
//    @Override
//    public boolean canMoveTo(Vector2d position) {return inMap(position) && !isOccupied(position);}
//
//    @Override
//    public Boundary getCurrentBounds() {
//        return new Boundary(new Vector2d(0,0),new Vector2d(this.getWidth() - 1,this.getHeight() - 1));
//    }
//
//}
