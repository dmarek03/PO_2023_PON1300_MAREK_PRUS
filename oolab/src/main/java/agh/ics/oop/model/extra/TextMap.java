//package agh.ics.oop.model.extra;
//
//import agh.ics.oop.model.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//public class TextMap extends AbstractWorldMap implements WorldMap<Text, Integer> {
//    private List<Text> texts = new ArrayList<>();
//    private int N = 0;
//
//    UUID uuid = UUID.randomUUID();
//
//
//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        int x = position.getX();
//        return (x == N);
//    }
//
//    public boolean place(Text text, Integer position) {
//        Vector2d positionVector = new Vector2d(position,0);
//        if (canMoveTo(positionVector)) {
//            texts.add(text);
//            N++;
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void move(Text text, MoveDirection direction) {
//        Integer position = text.getPosition();
//        int newPosition;
//        switch (direction) {
//            case FORWARD -> {newPosition = position + 1;}
//            case BACKWARD -> {newPosition = position - 1;}
//            default -> {return;}
//        }
//        if (isOccupied(newPosition)) {
//            Text temp = texts.get(newPosition);
//            text.setPosition(newPosition);
//            texts.set(newPosition,text);
//            temp.setPosition(position);
//            texts.set(position,temp);
//        }
//    }
//
//    @Override
//    public boolean isOccupied(Integer position) {
//        return ((position >= 0) && (position <N));
//    }
//
//    @Override
//    public Text objectAt(Integer position) {
//        return null;
//    }
//
//    @Override
//    public Map<Vector2d, WorldElement> getElement() {
//        return null;
//    }
//
//    @Override
//    public Boundary getCurrentBounds() {
//        return null;
//    }
//
//
//    public int getSize() {
//        return this.N;
//    }
//
//    @Override
//    public String toString() {
//        String output = "";
//        for (int i = 0; i < this.getSize(); i++) {
//            output += (texts.get(i)).toString() + " ";
//        }
//        return output.substring(0,output.length()-1);
//    }
//}
